package com.turbo_extreme_sloth.ezzence.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.turbo_extreme_sloth.ezzence.CurrentUser;
import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.SharedPreferencesHelper;
import com.turbo_extreme_sloth.ezzence.config.Config;
import com.turbo_extreme_sloth.ezzence.core.ListOption;
import com.turbo_extreme_sloth.ezzence.exceptions.UncaughtExceptionHandler;

public class MainActivity extends BaseActivity implements OnItemClickListener
{
	private ListView listView;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// Set the default exceptions handler
		Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler.getUncaughtExceptionHandler(this, getResources().getString(R.string.error_unknown_exception)));
		
		// Redirect a user to the login page when not logged in
		if (!CurrentUser.isLoggedIn() &&
			!Config.DEBUG)
		{
			startActivity(new Intent(this, LoginActivity.class));
	
			finish();
		
			return;
		}
		else
		{
			// Bypass login in DEBUG mode by setting user
			CurrentUser.setCurrentUser(SharedPreferencesHelper.getUser(this));
		}
		
		setContentView(R.layout.activity_main);
		
		//initiate main options for user
		ArrayAdapter<ListOption> adapter = new ArrayAdapter<ListOption>(this, 
		        android.R.layout.simple_list_item_1, getOptions());
		
		listView = (ListView) findViewById(R.id.id_listview_main);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}
	
	/**
	 * returns the options which are available at the main activity
	 * @return the options
	 */
	private ListOption[] getOptions()
	{
		ListOption[] options = null;
		
		options = new ListOption[]
		{
			new ListOption(getString(R.string.set_temperature), SetTemperatureActivity.class),
			new ListOption(getString(R.string.consumption_overview), ConsumptionOverviewActivity.class)
		};
		
		return options;
	}
	
	/**
	 * Gets the listview of this activity
	 * @return ListView of this activity
	 */
	public ListView getListView()
	{
		return this.listView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		ListOption lo = (ListOption)listView.getItemAtPosition(position);
	    Log.e("<tag>", lo.toString());
		//start the activity that is paired with the clicked option
		Intent intent = new Intent(this, lo.getValue());
		this.startActivity(intent);
	}
}
