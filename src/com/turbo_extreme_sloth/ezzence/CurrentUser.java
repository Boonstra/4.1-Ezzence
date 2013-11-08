package com.turbo_extreme_sloth.ezzence;

import android.content.Context;


/**
 * Singleton class for storing and retrieving the current user
 */
public class CurrentUser
{
	protected static User currentUser;
	
	/**
	 * Returns the current user when set, or null when no current user is set.
	 * 
	 * @return currentUser | null
	 */
	public static User getCurrentUser()
	{
		if (currentUser != null)
		{
			return currentUser;
		}
		
		return null;
	}
	
	/**
	 * Set the current user. Stores the user in shared preferences as well, using the SharedPreferencesHelper.
	 * 
	 * @param user
	 */
	public static void setCurrentUser(User currentUser)
	{
		CurrentUser.currentUser = currentUser;
	}

	/**
	 * Removes the current user instance from this class, as well as from the shared preferences.
	 * 
	 * @param context
	 */
	public static void unsetCurrentUser(Context context)
	{
		CurrentUser.currentUser = null;
		
		SharedPreferencesHelper.deleteUser(context);
	}
	
	/**
	 * Test if current user is logged in.
	 * 
	 * @return isLoggedIn
	 */
	public static boolean isLoggedIn()
	{
		if (currentUser instanceof User)
		{
			return currentUser.isLoggedIn();
		}
		
		return false;
	}
}
