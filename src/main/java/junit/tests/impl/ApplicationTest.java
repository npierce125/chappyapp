package main.java.junit.tests.impl;

import static org.junit.Assert.assertTrue;
import main.java.Application;

import org.junit.Test;

/**
 * Handles testing of the Application object and it's functions.
 * 
 * Primary functions of the Application include starting and stopping
 * the backend.
 * 
 * @author Freddy
 *
 */
public class ApplicationTest
{
	private Application app = new Application();
	
	/**
	 * Test that the start & stop cycle of the application works as intended
	 * 
	 * @author Freddy
	 */
	@Test
	public void startAndStopCycle()
	{
		assertTrue("Spark server failed to run start / stop cycle", app.start() && app.stop());
	}
}
