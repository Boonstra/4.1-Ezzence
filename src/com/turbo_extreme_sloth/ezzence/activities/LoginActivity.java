package com.turbo_extreme_sloth.ezzence.activities;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest.RESTRequestException;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequestEvent;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequestListener;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements RESTRequestListener
{
	protected EditText userNameEditText;
	protected EditText passwordEditText;

	protected Button loginButtonButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		userNameEditText = (EditText) findViewById(R.id.loginUserNameEditText);
		passwordEditText = (EditText) findViewById(R.id.loginPasswordEditText);
		
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
	public void login(String userName, String password)
	{
//		// User name must not be empty
//		if (userName.length() <= 0)
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//			
//			builder.setMessage(R.string.login_empty_userName);
//			builder.setPositiveButton(R.string.ok, null);
//			builder.show();
//			
//			return;
//		}
//		
//		// Password must not be empty
//		if (password.length() <= 0)
//		{
//			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
//
//			builder.setMessage(R.string.login_empty_password);
//			builder.setPositiveButton(R.string.ok, null);
//			builder.show();
//			
//			return;
//		}
		
		userName = password = "10";
		
		String hashedPassword = password;
		
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
		RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + "login.php");
		
		restRequest.putString("username", userName);
		restRequest.putString("password", hashedPassword);
		
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
		System.out.println("RESTRequest event finished. Result: " + event.getResult());
	}
	
	/**
	 * The click listener for the login button.
	 */
	private OnClickListener loginButtonButtonOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			login(userNameEditText.getText().toString(), passwordEditText.getText().toString());
		}
	};
}
