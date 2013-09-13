package com.turbo_extreme_sloth.ezzence.REST;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
	
	private ArrayList<RESTRequestListener> eventListeners;
	
	/**
	 * @param address
	 */
	public RESTRequest(String address)
	{
		this.address = address;
		
		values = new HashMap<String, Object>();
		
		eventListeners = new ArrayList<RESTRequestListener>();
	}
	
	/**
	 * The send method is an asynchronous method. After finishing, this method does not return any data.
	 * 
	 * When the RESTRequest has finished, a RESTRequestEvent is fired. This event contains the result data
	 * of the RESTful request.
	 * 
	 * @throws RESTRequestException
	 */
	public void send() throws RESTRequestException
	{
		String url = address;
		
		if (!values.isEmpty())
		{
			url += "?";
			
			Iterator<HashMap.Entry<String, Object>> iterator = values.entrySet().iterator();
			
			while (iterator.hasNext())
			{
				HashMap.Entry<String, Object> entry = (HashMap.Entry<String, Object>) iterator.next();
				
				url += entry.getKey() + "=" + entry.getValue().toString();
				
				if (iterator.hasNext())
				{
					url += "&";
				}
			}
		}
		
		try
		{
			System.out.println(url);
			
			new RESTRequestIssuer().execute(new URL(url));
		}
		catch (MalformedURLException e)
		{
			System.out.println(e.getMessage());
			
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
	 * @param eventListener
	 */
	public void addEventListener(RESTRequestListener eventListener)
	{
		eventListeners.add(eventListener);
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
				return "";
			}
			
			try
			{				
				// Open http connection
				HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
				
				InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

				// Trick to read all data from a stream in one line: https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
				Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
				
				String result = "";
				
				while (scanner.hasNext())
				{
					result += scanner.next();
				}
				
				scanner.close();
				
				inputStream.close();
				
				urlConnection.disconnect();

				return result;
			}
			catch (IOException e)
			{
				System.out.println("IOException - " + e.getMessage());
			}
			
			return "";
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			// Create new RESTRequestEvent to be handled by all listeners
			RESTRequestEvent event = new RESTRequestEvent(this, result);
			
			for (RESTRequestListener eventListener : eventListeners)
			{
				eventListener.handleRESTRequestEvent(event);
			}
		}
	}
	
	/**
	 * 
	 */
	public class RESTRequestException extends Exception { private static final long serialVersionUID = -1259925635751254377L; }
}