package com.turbo_extreme_sloth.ezzence.activities;

import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;
import com.turbo_extreme_sloth.ezzence.R.id;
import com.turbo_extreme_sloth.ezzence.R.layout;
import com.turbo_extreme_sloth.ezzence.R.menu;
import com.turbo_extreme_sloth.ezzence.R.string;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UnlockActivity extends Activity
{
	protected EditText unlockPinEditText;
	
	protected Button unlockButton;
	
	protected User user;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		user = intent.getParcelableExtra("user");
		
		// If no user was passed to this activity, redirect to login screen
		if (!(user instanceof User))
		{
			startActivity(new Intent(this, LoginActivity.class));
			
			finish();
			
			return;
		}

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
	 * Unlock application with pin
	 * 
	 * @param pin
	 */
	protected void unlock(String pin)
	{
		if (pin.equals(user.getPin()))
		{
			Intent intent = new Intent(this, MainActivity.class);
			
			intent.putExtra("user", user);
			
			startActivity(intent);
			
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
	
	/**
	 * 
	 */
	protected OnClickListener unlockButtonOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View view)
		{
			unlock(unlockPinEditText.getText().toString());
		}
	};
}
