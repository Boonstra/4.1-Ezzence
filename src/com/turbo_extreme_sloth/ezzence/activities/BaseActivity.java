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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.base_menu, menu);

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.action_logout:

				CurrentUser.unsetCurrentUser(this);

				startActivity(new Intent(this, LoginActivity.class));

				return true;

			case R.id.action_change_pin:
				
				

			default:

				return super.onOptionsItemSelected(item);
		}
	}
}
