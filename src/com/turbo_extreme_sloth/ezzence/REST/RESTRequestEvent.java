package com.turbo_extreme_sloth.ezzence.REST;

import java.util.EventObject;

/**
 * 
 */
public class RESTRequestEvent extends EventObject
{
	private static final long serialVersionUID = 5562246542886494790L;
	
	protected String result;

	public RESTRequestEvent(Object source, String result)
	{
		super(source);
		
		this.result = result;
	}
	
	/**
	 * @return result
	 */
	public String getResult()
	{
		return result;
	}
}
