package com.turbo_extreme_sloth.ezzence.activities;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity
{
	private static final String CURRENT_USER_KEY = "CURRENT_USER";
	
	public User currentUser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		startActivity(new Intent(this, LoginActivity.class));
		
		//showLoginScreen();
		
//		// When no saved instance state is found, the app has just started
//		if (savedInstanceState == null)
//		{
//			currentUser = new User("Harry", "leet1337");
//		}
//		else
//		{
//			currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
//		}

//		setContentView(R.layout.activity_login);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
	}
	
	@Override
	/**
	 * Saves instance state
	 */
	protected void onSaveInstanceState(Bundle outState)
	{	
		super.onSaveInstanceState(outState);
		
		outState.putParcelable(CURRENT_USER_KEY, currentUser);
	}
	
	public void showLoginScreen()
	{
    	Intent intent = new Intent(this, LoginActivity.class);
    	
//    	EditText editText = (EditText) findViewById(R.id.edit_message);
//    	
//    	String message = editText.getText().toString();
//    	
//    	intent.putExtra(EXTRA_MESSAGE, message);
    	
    	startActivity(intent);
    }
}
