package com.turbo_extreme_sloth.ezzence;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 */
public class User implements Parcelable
{
	public enum Type
	{
		SUPER_USER (1), NORMAL_USER (2);
		
		private int type;
		
		private Type(int type)
		{
			this.type = type;
		}
		
		public int getType()
		{
			return type;
		}
	};
	
	protected String name;
	protected String password;
	protected String sessionID;
	protected String pin;
	protected int    type;
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param password
	 * @param sessionID
	 * @param pin
	 */
	public User(String name, String password, String sessionID, String pin, int type)
	{
		System.out.println("New user created, named: " + name + " - Password: " + password + " - SessionID: " + sessionID + " - Pin: " + pin + " - Type: " );
		
		this.name      = name;
		this.password  = password;
		this.sessionID = sessionID;
		this.pin       = pin;
		
		setType(type);
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
	 * @return password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password)
	{
		this.password = password;
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
	
	/**
	 * @return pin
	 */
	public String getPin()
	{
		return pin;
	}

	/**
	 * @param pin
	 */
	public void setPin(String pin)
	{
		this.pin = pin;
	}
	
	/**
	 * @return type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(int type)
	{
		for (Type enumValue : Type.values())
		{
			if (enumValue.getType() == type)
			{
				this.type = type;
				
				return;
			}
		}
		
		this.type = Type.NORMAL_USER.getType();
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
		dest.writeString(pin);
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