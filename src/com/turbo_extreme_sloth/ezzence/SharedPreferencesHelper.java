package com.turbo_extreme_sloth.ezzence;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * A helper class that provides methods to manage shared preferences. 
 * 
 * TODO Use the reflection pattern to save duplicated code.
 */
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
		
		user.setName     (sharedPreferences.getString("userName" , ""));
		user.setPassword (sharedPreferences.getString("password" , ""));
		user.setSessionID(sharedPreferences.getString("sessionID", ""));
		user.setPin      (sharedPreferences.getString("pin"      , ""));
		user.setType     (sharedPreferences.getInt   ("type"     , User.Type.NORMAL_USER.getValue()));

		return user;
	}
	
	/**
	 * Stores user in shared preferences.
	 */
	public static void storeUser(Context context, User user)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("userSharedPreferences", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
		
		preferencesEditor.putString("userName" , user.getName());
		preferencesEditor.putString("password" , user.getPassword());
		preferencesEditor.putString("sessionID", user.getSessionID());
		preferencesEditor.putString("pin"      , user.getPin());
		preferencesEditor.putInt   ("type"     , user.getType());
		
		preferencesEditor.apply();
	}
	
	/**
	 * Removes user 
	 */
	public static void deleteUser(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("userSharedPreferences", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.remove("userName");
		preferencesEditor.remove("password");
		preferencesEditor.remove("sessionID");
		preferencesEditor.remove("pin");
		preferencesEditor.remove("type");
		
		preferencesEditor.apply();
	}
}
