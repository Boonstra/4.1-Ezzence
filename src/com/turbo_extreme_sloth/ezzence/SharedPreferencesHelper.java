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
	
	/**
	 * Get the number of consecutive failed login attempts.
	 * 
	 * @param context
	 * @return consecutiveFailedLoginAttempts
	 */
	public static int getConsecutiveFailedLoginAttempts(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("consecutiveFailedLoginAttempts", Context.MODE_PRIVATE); 
		
		return sharedPreferences.getInt("consecutiveFailedLoginAttempts", 0);
	}
	
	/**
	 * Stores the number of consecutive failed login attempts.
	 * 
	 * @param context
	 * @param consecutiveFailedLoginAttempts
	 */
	public static void storeConsecutiveFailedLoginAttempts(Context context, int consecutiveFailedLoginAttempts)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("consecutiveFailedLoginAttempts", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();
		
		preferencesEditor.putInt("consecutiveFailedLoginAttempts" , consecutiveFailedLoginAttempts);
		
		preferencesEditor.apply();
	}
	
	/**
	 * Delete the number of consecutive failed login attempts.
	 * 
	 * @param context
	 */
	public static void deleteConsecutiveFailedLoginAttempts(Context context)
	{
		SharedPreferences sharedPreferences = context.getSharedPreferences("consecutiveFailedLoginAttempts", Context.MODE_PRIVATE);
		
		SharedPreferences.Editor preferencesEditor = sharedPreferences.edit();

		preferencesEditor.remove("consecutiveFailedLoginAttempts");
		
		preferencesEditor.apply();
	}
}
