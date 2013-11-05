package com.turbo_extreme_sloth.ezzence.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequest;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequestEvent;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements RESTRequestListener
{
	protected static final String LOGIN_EVENT_ID = "loginEvent";
	
	protected SharedPreferences userCredentials;
	
	protected EditText userNameEditText;
	protected EditText passwordEditText;
	protected EditText loginPinEditText;

	protected Button loginButtonButton;
	
	protected User lastLoginAttemptUser;
	
	protected ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		userCredentials = getSharedPreferences("userCredentials", MODE_PRIVATE);
		
		userNameEditText = (EditText) findViewById(R.id.loginUserNameEditText);
		passwordEditText = (EditText) findViewById(R.id.loginPasswordEditText);
		loginPinEditText = (EditText) findViewById(R.id.loginPinEditText);
		
		loginButtonButton = (Button) findViewById(R.id.loginButtonButton);
		
		loginButtonButton.setOnClickListener(loginButtonButtonOnClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		
		return true;
	}
	
	/**
	 * Login
	 */
	public void login(String userName, String password, String pin)
	{
		// User name must not be empty
		if (userName.length() <= 0)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
			
			builder.setMessage(R.string.login_empty_userName);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();
			
			return;
		}
		
		// Password must not be empty
		if (password.length() <= 0)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

			builder.setMessage(R.string.login_empty_password);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();
			
			return;
		}
		
		// Pin code must be at least 5 digits
		if (pin.length() < 5)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

			builder.setMessage(R.string.login_pin_too_short);
			builder.setPositiveButton(R.string.ok, null);
			builder.show();
			
			return;
		}

		// Store these user credentials in the user class
		lastLoginAttemptUser = new User(userName, password, (String) null, pin, 0);
		
		// Create new RESTRequest instance and fill it with user data
		RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + getString(R.string.rest_request_login), LOGIN_EVENT_ID);
		
		restRequest.putString("username", userName);
		restRequest.putString("password", password);
		
		restRequest.addEventListener(this);
		
		// Send an asynchronous RESTful request
		restRequest.execute();
	}
	
	/**
	 * Saves credentials of passed user
	 */
	protected void saveUserCredentials(User user)
	{
		SharedPreferences.Editor preferencesEditor = userCredentials.edit();
		
		preferencesEditor.putString("userName", user.getName());
		preferencesEditor.putString("password", user.getPassword());
		preferencesEditor.putString("pin"     , user.getPin());
		
		preferencesEditor.apply();
	}
	
	/**
	 * @param event
	 */
	private void handleRESTRequestLoginEvent(RESTRequestEvent event)
	{
		String result = event.getResult();
		
		try
		{
			// Parse JSON
			JSONObject jsonObject = new JSONObject(result);
			
			String message   = jsonObject.getString("message");
			String sessionID = jsonObject.getString("sessionID");
			
			int userType = jsonObject.getInt("userType");
			
			// Message should be equal to success and sessionID should be available to be logged in successfully
			if (message == null ||
				!message.equals("success") ||
				sessionID == null ||
				sessionID.length() <= 0)
			{
				return;
			}
			
			lastLoginAttemptUser.setSessionID(sessionID);
			lastLoginAttemptUser.setType(userType);
		}
		catch (JSONException e) { }
		
		// Correct login, start main activity
		if (lastLoginAttemptUser.isLoggedIn())
		{
			saveUserCredentials(lastLoginAttemptUser);
			
			Intent intent = new Intent(this, MainActivity.class);
			
			intent.putExtra("user", lastLoginAttemptUser);
			
			startActivity(intent);
			
			finish();
		}
		else
		{
			String message = event.getMessageFromResult();
			
			// The server couldn't be reached, as no message is set
			if (message == null)
			{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.rest_not_found), Toast.LENGTH_SHORT).show();
			}
			// The server did not accept the passed user credentials
			else
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

				builder.setTitle(R.string.login_failed);
				builder.setMessage(R.string.login_wrong_credentials);
				builder.setPositiveButton(R.string.ok, null);			
				builder.show();
			}
		}
	}
	
	/**
	 * The click listener for the login button.
	 */
	protected OnClickListener loginButtonButtonOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			login(userNameEditText.getText().toString(), passwordEditText.getText().toString(), loginPinEditText.getText().toString());
		}
	};

	@Override
	public void RESTRequestOnPreExecute(RESTRequestEvent event)
	{
		progressDialog = new ProgressDialog(this);
		progressDialog.setTitle(getResources().getString(R.string.loading));
		progressDialog.show();
	}

	@Override
	public void RESTRequestOnPostExecute(RESTRequestEvent event)
	{
		progressDialog.dismiss();
		
		if (event.getID().equals(LOGIN_EVENT_ID))
		{
			handleRESTRequestLoginEvent(event);
		}
	}
}
