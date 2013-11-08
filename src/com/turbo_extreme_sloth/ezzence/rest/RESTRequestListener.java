package com.turbo_extreme_sloth.ezzence.rest;

import com.turbo_extreme_sloth.ezzence.rest.RESTRequestEvent;

public interface RESTRequestListener
{
	void RESTRequestOnPreExecute(RESTRequestEvent event);
	
	void RESTRequestOnPostExecute(RESTRequestEvent event);
}
