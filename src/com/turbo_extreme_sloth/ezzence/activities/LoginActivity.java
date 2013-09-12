package com.turbo_extreme_sloth.ezzence.activities;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequest.RESTRequestException;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity
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
		// Username must not be empty
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
		
		// Create new RESTRequest instance and fill it with user data
		RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + "login.php");
		
		restRequest.putString("username", userName);
		restRequest.putString("password", password);
		
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
		
		// TODO Do something on successful login
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
