package com.turbo_extreme_sloth.ezzence.activities;

import android.os.Bundle;
import android.view.Menu;

import com.turbo_extreme_sloth.ezzence.R;

public class SettingsActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		
		menu.findItem(R.id.action_settings).setVisible(false);
		
		return true;
	}
}
