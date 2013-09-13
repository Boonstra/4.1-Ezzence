package com.turbo_extreme_sloth.ezzence;

import com.turbo_extreme_sloth.ezzence.REST.RESTRequest;
import com.turbo_extreme_sloth.ezzence.REST.RESTRequestListener;

import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * The User class assists in transferring a user's data.
 * 
 * As this class implements Parcelable it can be passed through intents and stored in SharedPreferences.
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
	
	protected SharedPreferences userCredentials;
	
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
	 * @param type
	 */
	public User(String name, String password, String sessionID, String pin, int type)
	{
		System.out.println("USER: New user created, named: " + name + " - Password: " + password + " - SessionID: " + sessionID + " - Pin: " + pin + " - Type: " + type);
		
		this.name      = name;
		this.password  = password;
		this.sessionID = sessionID;
		this.pin       = pin;
		
		// Set type checks if the passed type is an existing value in the Type enum
		setType(type);
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
	
	/**
	 * Check if the user is logged in.
	 * 
	 * This method does not check whether or not a server request will be accepted,
	 * but rather checks whether or not a sessionID is set.
	 * 
	 * @return isLoggedIn
	 */
	public boolean isLoggedIn()
	{
		return sessionID != null && sessionID.length() > 0;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags)
	{
		parcel.writeString(name);
		parcel.writeString(password);
		parcel.writeString(sessionID);
		parcel.writeString(pin);
		parcel.writeInt(type);
	}
	
	/**
	 * 
	 */
	public static final User.Creator<User> CREATOR = new User.Creator<User>()
	{
		@Override
		public User createFromParcel(Parcel parcel)
		{
			return new User(parcel);
		}

		@Override
		public User[] newArray(int size)
		{
			return new User[size];
		}
	};
	
	/**
	 * @param source
	 */
	protected User(Parcel parcel)
	{
		name      = parcel.readString();
		password  = parcel.readString();
		sessionID = parcel.readString();
		pin       = parcel.readString();
		type      = parcel.readInt();
	}
}
