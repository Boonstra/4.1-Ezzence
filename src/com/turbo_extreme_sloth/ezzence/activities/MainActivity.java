package com.turbo_extreme_sloth.ezzence.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;
import com.turbo_extreme_sloth.ezzence.config.Config;
import com.turbo_extreme_sloth.ezzence.exceptions.UncaughtExceptionHandler;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequest;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequestEvent;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequestListener;

public class MainActivity extends BaseActivity implements RESTRequestListener
{
	protected static final String CURRENT_USER_KEY           = "CURRENT_USER";
	protected static final String UNLOCK_EVENT_ID            = "unlockEvent";
	protected static final String GET_CURRENT_TEMPERATURE_ID = "getCurrentTemperature";
	protected static final String SET_TEMPERATURE_ID         = "setTemperature";

	protected SharedPreferences userCredentials;

	protected User currentUser;

	protected TextView currentTemperatureTextView;

	protected EditText setTemperatureEditText;

	protected Button setTemperatureButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set the default exceptions handler
		Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler.getUncaughtExceptionHandler(this, getResources().getString(R.string.error_unknown_exception)));

		setContentView(R.layout.activity_main);

		//		System.out.println("------------------------- lookhere");

		//		userCredentials = getSharedPreferences("userCredentials", MODE_PRIVATE);
		//		
		//		// When the intent contains a parceled user instance, the user just logged in
		//		Intent intent = getIntent();
		//		
		//		currentUser = intent.getParcelableExtra("user");
		//		
		//		// Login user
		//		if (!(currentUser instanceof User))
		//		{
		//			login(savedInstanceState);
		//			
		//			return;
		//		}
		//		
		//		setContentView(R.layout.activity_main);
		//		
		//		currentTemperatureTextView = (TextView) findViewById(R.id.currentTemperatureTextView);
		//		
		//		setTemperatureEditText = (EditText) findViewById(R.id.setTemperatureEditText);
		//		
		//		setTemperatureButton = (Button) findViewById(R.id.setTemperatureButton);
		//		
		//		setTemperatureButton.setOnClickListener(setTemperatureButtonOnClickListener);
		//		
		//		updateCurrentTemperature();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);

		currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
	}

	/**
	 * 
	 */
	protected OnClickListener setTemperatureButtonOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			setTemperature(setTemperatureEditText.getText().toString());

			// Force the keyboard to close
			InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

			inputMethodManager.hideSoftInputFromWindow(setTemperatureEditText.getWindowToken(), 0);
		}
	};

	/**
	 * Send a RESTful request to the API to change the temperature according to the passed temperature
	 */
	public void setTemperature(String temperature)
	{
		if (temperature == null ||
			temperature.length() <= 0)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			builder.setMessage(R.string.main_empty_temperature);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();

			return;
		}

		// Create new RESTRequest instance and fill it with user data
		RESTRequest restRequest = new RESTRequest(Config.REST_REQUEST_BASE_URL + Config.REST_REQUEST_SET_TEMPERATURE, SET_TEMPERATURE_ID);

		restRequest.putString("sessionID"  , currentUser.getSessionID());
		restRequest.putString("temperature", temperature);

		restRequest.addEventListener(this);

		restRequest.execute();
	}

	/**
	 * Gets the current temperature from the RESTful API and updates the current temperature view
	 */
	public void updateCurrentTemperature()
	{
		// Create new RESTRequest instance and fill it with user data
		RESTRequest restRequest = new RESTRequest(Config.REST_REQUEST_BASE_URL + Config.REST_REQUEST_TEMPERATURE, GET_CURRENT_TEMPERATURE_ID);

		restRequest.putString("sessionID", currentUser.getSessionID());

		restRequest.addEventListener(this);

		restRequest.execute();
	}

	/**
	 * @param event
	 */
	protected void handleRESTRequestGetCurrentTemperature(RESTRequestEvent event)
	{
		String result = event.getResult();

		try
		{
			// Parse JSON
			JSONObject jsonObject = new JSONObject(result);

			String message     = jsonObject.getString("message");
			String temperature = jsonObject.getString("temperature");

			// Message should be equal to success and temperature should be set
			if (message == null ||
				!message.equals("success") ||
				temperature == null ||
				temperature.length() <= 0)
			{
				return;
			}

			// \u2103 stands for degrees Celcius. This unit is hard coded, it would've been nicer to have this as a setting.
			currentTemperatureTextView.setText(temperature + "\u2103");
		}
		catch (JSONException e) { }
	}

	/**
	 * @param event
	 */
	protected void handleRESTRequestSetTemperature(RESTRequestEvent event)
	{
		String result = event.getResult();

		try
		{
			// Parse JSON
			JSONObject jsonObject = new JSONObject(result);

			String message     = jsonObject.getString("message");

			// Check if temperature was changed succesfully
			if (message == null ||
				!message.equals("success"))
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

				builder.setMessage(R.string.main_temperature_not_set);
				builder.setPositiveButton(R.string.ok, null);
				builder.show();
			}

			// Success message
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

			builder.setMessage(R.string.main_temperature_set);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();

			updateCurrentTemperature();
		}
		catch (JSONException e) { }
	}

	@Override
	public void RESTRequestOnPreExecute(RESTRequestEvent event) { }

	@Override
	public void RESTRequestOnPostExecute(RESTRequestEvent event)
	{
		/*if (event.getID().equals(UNLOCK_EVENT_ID))
		{
			handleRESTRequestUnlockEvent(event);
		}
		else */if (event.getID().equals(GET_CURRENT_TEMPERATURE_ID))
		{
			handleRESTRequestGetCurrentTemperature(event);
		}
		else if (event.getID().equals(SET_TEMPERATURE_ID))
		{
			handleRESTRequestSetTemperature(event);
		}
	}
}
