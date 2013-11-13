package com.turbo_extreme_sloth.ezzence.config;

public class Config
{
	/** REST */
	public static final String REST_REQUEST_BASE_URL                = "http://rawr.eu/ezzence/api/";
	public static final String REST_REQUEST_LOGIN                   = "login.php";
	public static final String REST_REQUEST_TEMPERATURE             = "temperature.php";
	public static final String REST_REQUEST_REQUESTED_TEMPERATURES  = "requestedTemperatures.php"; 
	public static final String REST_REQUEST_SET_TEMPERATURE         = "setTemperature.php";
	public static final String REST_REQUEST_SET_TEMPERATURE_PROFILE = "setTemperatureProfile.php"; 
	public static final String REST_REQUEST_USAGE_STATS             = "usageStats.php";
	public static final String REST_REQUEST_GET_USERS               = "getUsers.php";
	
	/** Debug mode */
	public static boolean DEBUG = true;
}
