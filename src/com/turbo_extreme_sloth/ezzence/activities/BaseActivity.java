package com.turbo_extreme_sloth.ezzence.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.turbo_extreme_sloth.ezzence.CurrentUser;
import com.turbo_extreme_sloth.ezzence.R;

public abstract class BaseActivity extends Activity
{
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
				
				finish();
				
				return true;
				
			case R.id.action_settings:
				
				startActivity(new Intent(this, SettingsActivity.class));
				
				return true;

			default:

				return super.onOptionsItemSelected(item);
		}
	}
}
