package com.turbo_extreme_sloth.ezzence.exceptions;

import com.turbo_extreme_sloth.ezzence.config.Config;

import android.app.Activity;
import android.widget.Toast;

public class UncaughtExceptionHandler
{
	protected UncaughtExceptionHandler(){ }
	
	/**
	 * Catches all exception. Ensuring the application doesn't crash
	 * @return uncaughtExceptionHandler
	 */
	public static java.lang.Thread.UncaughtExceptionHandler getUncaughtExceptionHandler(final Activity activity, final String message)
	{
		return new Thread.UncaughtExceptionHandler()
		{
			public void uncaughtException(Thread t, Throwable e)
			{
				if (Config.DEBUG)
				{
					System.err.println("---------------------------------------------");
					System.err.println("");
					
					System.err.println("Exception in " + t + ":");
					e.printStackTrace();
					
					System.err.println("");
					System.err.println("---------------------------------------------");
				}
				
				// Show a toast on the UI thread
				activity.runOnUiThread(new Runnable() {
				    public void run() {
				        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
				    }
				});
			}
		};
	}
}
