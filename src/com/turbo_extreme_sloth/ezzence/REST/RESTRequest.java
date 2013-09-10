package com.turbo_extreme_sloth.ezzence.REST;

/**
 * 
 */
public class RESTRequest
{
	private String address;
	
	/**
	 * TODO Implement
	 * 
	 * @param address
	 */
	public RESTRequest(String address)
	{
		this.address = address;
	}
	
	/**
	 * TODO Implement
	 * 
	 * @return success
	 */
	public boolean send()
	{
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
}