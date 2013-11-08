package com.turbo_extreme_sloth.ezzence;

import android.os.Bundle;
import android.view.Menu;

import com.turbo_extreme_sloth.ezzence.activities.BaseActivity;

public class ChangePINActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pin);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		super.onPrepareOptionsMenu(menu);
		
		menu.findItem(R.id.action_change_pin).setVisible(false);
		
		return true;
	}
}
