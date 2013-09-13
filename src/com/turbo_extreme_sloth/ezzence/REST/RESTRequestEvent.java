package com.turbo_extreme_sloth.ezzence.REST;

import java.util.EventObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 */
public class RESTRequestEvent extends EventObject
{
	private static final long serialVersionUID = 5562246542886494790L;
	
	protected String result;
	protected String ID;

	public RESTRequestEvent(Object source, String result, String ID)
	{
		super(source);
		
		this.result = result;
		this.ID     = ID;
	}
	
	/**
	 * @return result
	 */
	public String getResult()
	{
		return result;
	}
	
	/**
	 * @return ID
	 */
	public String getID()
	{
		return ID;
	}
	
	/**
	 * Parses the result into a JSON object to extract the message key from the first level of the JSON array.
	 * 
	 * @return message
	 */
	public String getMessageFromResult()
	{
		try
		{
			// Parse JSON
			JSONObject jsonObject = new JSONObject(result);
			
			String message = jsonObject.getString("message");
			
			return message;
		}
		catch (JSONException e) { }
		
		return null;
	}
}
