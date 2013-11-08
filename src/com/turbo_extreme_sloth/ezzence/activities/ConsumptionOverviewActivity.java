package com.turbo_extreme_sloth.ezzence.activities;

import android.os.Bundle;
import android.view.Menu;

import com.turbo_extreme_sloth.ezzence.R;

public class ConsumptionOverviewActivity extends BaseActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumption_overview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.consumption_overview, menu);
		return true;
	}
}
