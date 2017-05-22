package main.java;

import java.io.File;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import spark.Spark;

/**
 * This class represents our application. It will handle the start / stop cycle of the app.
 * 
 * @author Freddy
 *
 */
public class Application
{
	private static boolean DEV_MODE = true;
	
	//HTTPS constants
	private static final String KEY_STORE_PATH = "";
	private static final String KEY_STORE_PASS = "";
	
	private static final Logger LOG = Log.getLogger(Application.class);
	
	//instance vars
	private int port = 0;
	
	/**
	 * Default constructor for the Application class. The purpose of this
	 * is so that we don't have to specify a port, and therefore Spark
	 * will automatically choose an arbitrary port for us. This is useful
	 * for cases relating to JUnit tests, since we don't want Applications
	 * not being able to bind their port, if they're all using the same port.
	 * 
	 * Since the order that the JUnit tests are ran in is not static,
	 * we this will avoid port binding problems related to testing. 
	 */
	public Application()
	{
		//Spark will choose an arbitrary port since our specified port = 0
	}
	
	/**
	 * Creates an Application object that will run on the specified port
	 * @param port port to run the application on
	 */
	public Application(int port)
	{
		this.port = port;
	}
	
	/**
	 * Attempts to run through the start process for the Application
	 * 
	 * @author Freddy
	 * 
	 * @return true if the Application started successfully, false otherwise
	 */
	public boolean start()
	{
		//Use Spark's built in initExceptionHandler to handle startup failure
		Spark.initExceptionHandler((e) -> {LOG.warn("Application failed to start", e);});
		
		//Make the backend run on specified port
		if(!bindPort(port))
			return false;
      
		//Only need to make sure HTTPS is enabled in production mode
		if(!DEV_MODE && !secure())
			return false;
       
		//If we fail to setup web service routes, obviously we need to return false
		if(!setupRoutes())
			return false;
		
		//wait until the server is ready to handle requests
		Spark.awaitInitialization();
       
		return true;
	}
	
	/**
	 * Sets up the routes for our web service
	 * 
	 * @author Freddy
	 * 
	 * @return true if successfully setup routes, false otherwise
	 */
	private boolean setupRoutes()
	{
		Spark.get("/hello", (request, response) -> "Hello World!!");
		
		return true;
	}
	
	/**
	 * Attempts to stop the Application
	 * 
	 * @author Freddy
	 * 
	 * @return true if the Application stopped successfully, false otherwise
	 */
	public boolean stop()
	{
		Spark.stop();
		
		//TODO verify somehow that the server stopped. Thanks for making stop a void, Spark devs.... :(
		return true;
	}
	
	/**
	 * Bind the Spark server on the specified port
	 * 
	 * @author Freddy
	 * 
	 * @param port the port to bind the server on
	 * @return true if successfully bound, false otherwise
	 */
	private boolean bindPort(int port)
	{
		//if this line fails, the internal jetty server will throw a BindException,
		//which will be handled by the initExceptionHandler we have at the top of the start() method
		Spark.port(port);
		
		LOG.info("Successfuly bound Spark on port: " + (port == 0 ? "unknown" : port));
		return true;
	}
		
	/**
	 * Secure Spark with HTTPS, specified by the appropriate keystore credentials
	 * 
	 * @author Freddy
	 */
	public boolean secure()
	{
		if(keystoreExists())
		{
			try
			{
				Spark.secure(KEY_STORE_PATH, KEY_STORE_PASS, null, null);
			}
			//TODO HANDLE SPECIFIC EXCEPTION
			catch(Exception e)
			{
				LOG.warn("Failed to secure connection with HTTPS", e);
				return false;
			}
			
			LOG.info("Connection secured with HTTPS");
			return true;
    	}
       
		LOG.warn("Keystore file doesn't exist");
		return false;
	}
	
	/**
	 * Check if the keystore file exists
	 * 
	 * @author Freddy
	 * 
	 * @return true if the keystore file exists at KEY_STORE_PATH, false otherwise
	 */
	public boolean keystoreExists()
	{
		File f = new File(KEY_STORE_PATH);
		return f != null && f.exists() && !f.isDirectory();
	}
}
