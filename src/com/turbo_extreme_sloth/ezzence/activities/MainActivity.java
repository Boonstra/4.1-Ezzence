package com.turbo_extreme_sloth.ezzence.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.turbo_extreme_sloth.ezzence.CurrentUser;
import com.turbo_extreme_sloth.ezzence.R;
import com.turbo_extreme_sloth.ezzence.User;
import com.turbo_extreme_sloth.ezzence.core.ListOption;
import com.turbo_extreme_sloth.ezzence.exceptions.UncaughtExceptionHandler;

public class MainActivity extends Activity implements OnItemClickListener
{
	protected static final String CURRENT_USER_KEY           = "CURRENT_USER";
	
	protected SharedPreferences userCredentials;
	
	protected User currentUser;
	
	private ListView listView;
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return false;
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		// Set the default exceptions handler
		Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler.getUncaughtExceptionHandler(this, getResources().getString(R.string.error_unknown_exception)));
		
		if (!CurrentUser.isLoggedIn())
		{
			startActivity(new Intent(this, LoginActivity.class));
			finish();
			return;
			
			//CurrentUser.performLogin(this, savedInstanceState);
		}
		
		setContentView(R.layout.activity_main);
		
		//initiate main options for user
		ArrayAdapter<ListOption> adapter = new ArrayAdapter<ListOption>(this, 
		        android.R.layout.simple_list_item_1, getOptions());
		
		listView = (ListView) findViewById(R.id.id_listview_main);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * returns the options which are available at the main activity
	 * @return the options
	 */
	private ListOption[] getOptions(){
		ListOption[] options = null;
		
		options = new ListOption[]{ new ListOption(getString(R.string.set_temperature), SetTemperatureActivity.class)
								  };
		
		
		return options;
	}
	
	/**
	 * Gets the listview of this activity
	 * @return ListView of this activity
	 */
	public ListView getListView(){
		return this.listView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ListOption lo = (ListOption)listView.getItemAtPosition(position);
	    Log.e("<tag>", lo.toString());
		//start the activity that is paired with the clicked option
		Intent intent = new Intent(this, lo.getValue());
		this.startActivity(intent);
		
		
	}

}
