package com.turbo_extreme_sloth.ezzence.REST;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import android.os.AsyncTask;

/**
 * The RESTRequest class simplifies the process of making RESTful requests to the web address of choice.
 */
public class RESTRequest
{
	private String address;
	
	private HashMap<String, Object> values;
	
	/**
	 * @param address
	 */
	public RESTRequest(String address)
	{
		this.address = address;
		
		values = new HashMap<String, Object>();
	}
	
	/**
	 * @throws RESTRequestException
	 */
	public void send() throws RESTRequestException
	{
		String URI = address;
		
		if (!values.isEmpty())
		{
			URI += "?";
			
			Iterator<HashMap.Entry<String, Object>> iterator = values.entrySet().iterator();
			
			while (iterator.hasNext())
			{
				HashMap.Entry<String, Object> entry = (HashMap.Entry<String, Object>) iterator.next();
				
				URI += entry.getKey() + "=" + entry.getValue().toString();
				
				if (iterator.hasNext())
				{
					URI += "&";
				}
			}
		}
		
		try
		{
			System.out.println(URI);
			
			new RESTRequestIssuer().execute(new URL(URI));
		}
		catch (MalformedURLException e)
		{
			throw new RESTRequestException();
		}
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
	 * RESTRequestIssuer issues all RESTful requests asynchronously.
	 */
	private class RESTRequestIssuer extends AsyncTask<URL, Void, String>
	{
		@Override
		protected String doInBackground(URL... urls)
		{
			if (urls.length <= 0)
			{
				return null;
			}
			
			try
			{
				// Open http connection
				HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
				
				try
				{
					InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
					
					// Trick to read all data from a stream in one line: https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
					Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
					
					String result = scanner.hasNext() ? scanner.next() : null;
					
					scanner.close();

					return result;
				}
				finally
				{
					urlConnection.disconnect();
				}
			}
			catch (IOException e)
			{
				return null;
			}
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			if (result == null)
			{
				System.out.println("No result was returned");
			}
			else
			{			
				System.out.println(result);
			}
		}
	}
	
	/**
	 * 
	 */
	public class RESTRequestException extends Exception { private static final long serialVersionUID = -1259925635751254377L; }
}