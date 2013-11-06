package com.turbo_extreme_sloth.ezzence;

import android.content.Intent;
import android.os.Bundle;

import com.turbo_extreme_sloth.ezzence.activities.LoginActivity;
import com.turbo_extreme_sloth.ezzence.rest.RESTRequest;

/**
 * Singleton class for storing and retrieving the current user
 */
public class CurrentUser
{
	protected User currentUser;
	
	/**
	 * @param user
	 */
	protected CurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}
	
	/**
	 * Returns the current user when set, or null when no current user is set.
	 * 
	 * @return currentUser | null
	 */
	public User getCurrentUser()
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
	public void setCurrentUser(User currentUser)
	{
		this.currentUser = currentUser;
	}
	
	/**
	 * Test if current user is logged in.
	 * 
	 * @return isLoggedIn
	 */
	public boolean isLoggedIn()
	{
		if (currentUser instanceof User)
		{
			return currentUser.isLoggedIn();
		}
		
		return false;
	}
		
	/**
	 * @param savedInstanceState
	 */
	public void login(Bundle savedInstanceState)
	{
		String userName = userCredentials.getString("userName", null);
		String password = userCredentials.getString("password", null);
		String pin      = userCredentials.getString("pin"     , null);
		
		// Saved instance is still in memory, no need to do a login.
		if (savedInstanceState != null)
		{
			currentUser = savedInstanceState.getParcelable(CURRENT_USER_KEY);
		}
		// If user credentials have been retrieved, try to log in and show the unlocking screen
		else if (userName != null &&
				 password != null &&
				 pin      != null)
		{
			currentUser = new User(userName, password, (String) null, pin, 0);
			
			// Create new RESTRequest instance and fill it with user data
			RESTRequest restRequest = new RESTRequest(getString(R.string.rest_request_base_url) + getString(R.string.rest_request_login), UNLOCK_EVENT_ID);
			
			restRequest.putString("username", userName);
			restRequest.putString("password", password);
			
			// Add event listener
			restRequest.addEventListener(this);
			
			restRequest.execute();
			
			return;
		}
		
		// If at this point no user was found, perform a new login
		startActivity(new Intent(this, LoginActivity.class));
		
		finish();
	}
}
