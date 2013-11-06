package com.turbo_extreme_sloth.ezzence;

import com.turbo_extreme_sloth.ezzence.activities.LoginActivity;

import android.content.Context;
import android.content.Intent;

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
	 * @param user
	 */
	public static void setCurrentUser(User currentUser)
	{
		CurrentUser.currentUser = currentUser;
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
		
//	/**
//	 * @param savedInstanceState
//	 */
//	public static void performLogin(Context packageContext, Bundle savedInstanceState)
//	{
//		
//		
////		String userName = userCredentials.getString("userName", null);
////		String password = userCredentials.getString("password", null);
////		String pin      = userCredentials.getString("pin"     , null);
////		
////		// Saved instance is still in memory, no need to do a login.
////		if (savedInstanceState != null)
////		{
////			currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
////		}
////		// If user credentials have been retrieved, try to log in and show the unlocking screen
////		else if (userName != null &&
////				 password != null &&
////				 pin      != null)
////		{
////			currentUser = new User(userName, password, (String) null, pin, 0);
////			
////			// Create new RESTRequest instance and fill it with user data
////			RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + getString(R.string.rest_request_login), UNLOCK_EVENT_ID);
////			
////			restRequest.putString("username", userName);
////			restRequest.putString("password", password);
////			
////			// Add event listener
////			restRequest.addEventListener(this);
////			
////			restRequest.execute();
////			
////			return;
////		}
////		
////		// If at this point no user was found, perform a new login
////		startActivity(new Intent(packageContext, LoginActivity.class));
////		
////		finish();
//	}
}
