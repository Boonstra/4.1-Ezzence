package com.turbo_extreme_sloth.ezzence.REST;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;

/**
 * 
 */
public class RESTRequest
{
	private String address;
	
	private HashMap<String, Object> values;
	
	/**
	 * TODO Implement
	 * 
	 * @param address
	 */
	public RESTRequest(String address)
	{
		this.address = address;
		
		values = new HashMap<String, Object>();
	}
	
	/**
	 * TODO Implement
	 * 
	 * @return success
	 */
	public boolean send()
	{
		String URI = address;
		
		if (!values.isEmpty())
		{
			URI += "?";
			
			Iterator iterator = values.entrySet().iterator();
			
			while (iterator.hasNext())
			{
				Entry<String, Object> entry = (Entry<String, Object>) iterator.next();
				
				URI += entry.getKey() + "=" + entry.getValue();
				
				//Locale.US;
				//finalBillEditText.setText(String.format("%.02f", finalBill));
				
				if (iterator.hasNext())
				{
					URI += "&";
				}
			}
		}
		
		// TODO Send in new thread
		
		return false;
	}

	/**
	 * @return address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value)
	{
		values.put(key, value);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void putInt(String key, int value)
	{
		values.put(key, value);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void putDouble(String key, double value)
	{
		values.put(key, value);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void putFloat(String key, float value)
	{
		values.put(key, value);
	}
}