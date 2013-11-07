package com.turbo_extreme_sloth.ezzence;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper
{
	/**
	 * Retrieves user from shared preferences.
	 * 
	 * @param mode
	 */
	public static User getUser(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("userSharedPreferences", Context.MODE_PRIVATE); 
		
		User user = new User();
		
		user.setName    (sharedPreferences.getString("userName", ""));
		user.setPassword(sharedPreferences.getString("password", ""));
		user.setPin     (sharedPreferences.getString("pin", ""));

		return user;
	}
	
	/**
	 * Stores user in shared preferences.
	 */
	public static void storeUser(Context context, User user)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("userSharedPreferences", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
		
		preferencesEditor.putString("userName", user.getName());
		preferencesEditor.putString("password", user.getPassword());
		preferencesEditor.putString("pin"     , user.getPin());
		
		preferencesEditor.apply();
	}
	
	/**
	 * 
	 */
	public static void deleteUser(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("userSharedPreferences", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.remove("userName");
		preferencesEditor.remove("password");
		preferencesEditor.remove("pin");
		
		preferencesEditor.commit();
	}
}
