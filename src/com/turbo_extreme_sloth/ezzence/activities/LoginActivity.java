package com.turbo_extreme_sloth.ezzence.activities;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;

import android.os.Bundle;
import android.app.Activity;
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
	public void login(String name, String password)
	{
		finish();
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
