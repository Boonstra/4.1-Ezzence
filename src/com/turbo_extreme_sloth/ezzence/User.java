package com.turbo_extreme_sloth.ezzence;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class User implements Parcelable
{
	private String name;
	private String sessionID;
	
	/**
	 * Constructor
	 */
	public User()
	{
		this((String) null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 */
	public User(String name)
	{
		this(name, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param sessionID
	 */
	public User(String name, String sessionID)
	{
		System.out.println("New user created, named: " + name + " - SessionID: " + sessionID);
		
		this.name      = name;
		this.sessionID = sessionID;
	}
	
	/**
	 * 
	 */
	public User(Parcel source)
	{
		System.out.println("-------------- This is freaking awesome");
	}
	
	/**
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return sessionID
	 */
	public String getSessionID()
	{
		return sessionID;
	}
	
	/**
	 * @param sessionID
	 */
	public void setSessionID(String sessionID)
	{
		this.sessionID = sessionID;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(name);
		dest.writeString(sessionID);
	}
	
	public static final User.Creator<User> CREATOR = new User.Creator<User>()
	{
		@Override
		public User createFromParcel(Parcel source)
		{
			return new User(source);
		}

		@Override
		public User[] newArray(int size)
		{
			return new User[size];
		}
	};
}