package main.java.utils;

import spark.ResponseTransformer;

import com.google.gson.Gson;

public class JsonUtils
{
	/**
	 * Converts a Java Object to a JSON String
	 * 
	 * @author Freddy
	 * 
	 * @param object the object we'll convert to JSON format
	 * @return JSON representation of the object, as a String
	 */
	public static String toJson(Object object)
	{
		return new Gson().toJson(object);
	}
	
	/**
	 * ResponseTransformer used by Spark to convert a response to JSON
	 * 
	 * @author Freddy
	 *  
	 * @return the appropriate ResponseTransformer object to convert a response to JSON
	 */
	public static ResponseTransformer json()
	{
		return JsonUtils::toJson;
	}
}
