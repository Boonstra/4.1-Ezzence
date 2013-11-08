package com.turbo_extreme_sloth.ezzence.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.turbo_extreme_sloth.ezzence.ChangePINActivity;
import com.turbo_extreme_sloth.ezzence.CurrentUser;
import com.turbo_extreme_sloth.ezzence.R;

public abstract class BaseActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Redirect a user to the login page when not logged in
		if (!CurrentUser.isLoggedIn())
		{
			startActivity(new Intent(this, LoginActivity.class));
	
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Set base menu as options menu
		getMenuInflater().inflate(R.menu.base_menu, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_logout:

				// Unset user to be able to login again
				CurrentUser.unsetCurrentUser(this);

				startActivity(new Intent(this, LoginActivity.class));
				
				return true;

			case R.id.action_change_pin:
				
				startActivity(new Intent(this, ChangePINActivity.class));
				
				return true;

			default:

				return super.onOptionsItemSelected(item);
		}
	}
}