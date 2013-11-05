package com.turbo_extreme_sloth.ezzence;

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
}
