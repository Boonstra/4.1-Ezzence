package com.turbo_extreme_sloth.ezzence.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.SharedPreferencesHelper;
import com.turbo_extreme_sloth.ezzence.User;

public class UnlockActivity extends Activity
{
	/** User. */
	protected User user;
	
	/** Elements. */
	protected EditText unlockPinEditText;
	
	protected Button unlockButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		user = SharedPreferencesHelper.getUser(this);
		
		// If a user is not set, start the login activity
		if (user == null ||
			user.getPin() == null ||
			user.getPin().length() <= 0)
		{
			startActivity(new Intent(this, LoginActivity.class));
			
			finish();
			
			return;
		}
		
//		Intent intent = getIntent();
//		
//		user = intent.getParcelableExtra("user");
//		
//		// If no user was passed to this activity, redirect to login screen
//		if (!(user instanceof User))
//		{
//			startActivity(new Intent(this, LoginActivity.class));
//			
//			finish();
//			
//			return;
//		}

		setContentView(R.layout.activity_unlock);
		
		unlockPinEditText = (EditText) findViewById(R.id.unlockPinEditText);
		
		unlockButton = (Button) findViewById(R.id.unlockButton);
		
		unlockButton.setOnClickListener(unlockButtonOnClickListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unlock, menu);
		
		return true;
	}
	
	/**
	 * 
	 */
	protected OnClickListener unlockButtonOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			performUnlock(unlockPinEditText.getText().toString());
		}
	};
	
	/**
	 * Unlock application with pin
	 * 
	 * @param pin
	 */
	protected void performUnlock(String pin)
	{
		// Test if pin is correct
		if (pin.equals(user.getPin()))
		{
//			Intent intent = new Intent(this, MainActivity.class);
//			
//			intent.putExtra("user", user);
//			
//			startActivity(intent);
//			
//			finish();
//			
//			return;
			
			startActivity(new Intent(this, MainActivity.class));
			
			finish();
			
			return;
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(UnlockActivity.this);

			builder.setTitle(R.string.unlock_failed);
			builder.setMessage(R.string.unlock_wrong_pin);
			builder.setPositiveButton(R.string.ok, null);			
			builder.show();
		}
	}
}
