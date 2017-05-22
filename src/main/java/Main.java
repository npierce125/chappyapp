package main.java;

/**
 * Main entry point of our backend
 * 
 * @author Freddy
 *
 */
public class Main
{
	private static final int PORT = 4567; //port we'll run the embedded Jetty server on
	
	/**
	 * Simply create the application object and call the start method on it
	 * 
	 * @param args
	 */
	//TODO POTENTIALLY IN FUTURE args[] WILL BE PORT NUMBER?
	public static void main(String[] args)
	{
		Application app = new Application(PORT);
		app.start();
	}

}
