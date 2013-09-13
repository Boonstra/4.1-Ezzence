package com.turbo_extreme_sloth.ezzence.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest.RESTRequestException;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequestEvent;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements RESTRequestListener
{
	protected EditText userNameEditText;
	protected EditText passwordEditText;
	protected EditText loginPinEditText;

	protected Button loginButtonButton;
	
	protected User lastLoginAttempt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
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
		// TODO Remove temporary quick login code
		userName = password = "10";
		pin = "10000";
		
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
		lastLoginAttempt = new User(userName, password, (String) null, pin, User.Type.NORMAL_USER.getType());
		
		// TODO This algorithm seems to differ a bit from the algorithm used by PHP.
		// TODO We haven't been able to resolve the issue in time. so we've chosen to send the password in plain-text for now.
		// Hash password once with sha-256 algorithm
//		try
//		{
//			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
//
//			messageDigest.update(password.getBytes("UTF-8"));
//
//			byte[] hashedPasswordBytes = messageDigest.digest();
//
//			// Convert byte array to hex String
//			StringBuffer hashedPasswordBuffer = new StringBuffer();
//
//			for (int i=0; i < hashedPasswordBytes.length; i++)
//			{
//				hashedPasswordBuffer.append(Integer.toHexString(0xFF & hashedPasswordBytes[i]));
//			}
//
//			hashedPassword = hashedPasswordBuffer.toString();
//		}
//		catch (Exception e)
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//			
//			builder.setMessage(R.string.login_password_encryption_error);
//			builder.setPositiveButton(R.string.ok, null);
//			builder.show();
//			
//			return;
//		}
		
		// Create new RESTRequest instance and fill it with user data
		RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + getString(R.string.rest_request_login));
		
		restRequest.putString("username", userName);
		restRequest.putString("password", password);
		
		restRequest.addEventListener(this);
		
		try
		{
			// Send an asynchronous RESTful request
			restRequest.send();
		}
		catch (RESTRequestException e)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

			builder.setMessage(R.string.rest_not_found);
			builder.setPositiveButton(R.string.ok, null);			
			builder.show();
			
			return;
		}
	}
	
	@Override
	public void handleRESTRequestEvent(RESTRequestEvent event)
	{
		String result = event.getResult();
		
		try
		{
			// Parse JSON
			JSONObject jsonObject = new JSONObject(result);
			
			String message   = jsonObject.getString("message");
			String sessionID = jsonObject.getString("sessionID");
			
			int userType = jsonObject.getInt("userType");
			
			// Message should equal success and sessionID should be available to be logged in successfully
			if (message == null ||
				!message.equals("success") ||
				sessionID == null ||
				sessionID.length() <= 0)
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

				builder.setTitle(R.string.login_failed);
				builder.setMessage(R.string.login_wrong_credentials);
				builder.setPositiveButton(R.string.ok, null);			
				builder.show();
				
				return;
			}
			
			// Get the User instance created by this login
			User user = lastLoginAttempt;
			
			user.setSessionID(sessionID);
			user.setType(userType);
			
			Intent intent = new Intent(this, MainActivity.class);
			
			intent.putExtra("user", user);
			
			startActivity(intent);
			
			//finish();
		}
		catch (JSONException e)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

			builder.setMessage(R.string.rest_not_found);
			builder.setPositiveButton(R.string.ok, null);			
			builder.show();
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
}
