package main.java.junit.tests;

import main.java.junit.tests.impl.ApplicationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses
({
	ApplicationTest.class
})

/**
 * This class functions as an easy way to run all of our JUnit tests,
 * simply by executing this class as a JUnit test.
 * 
 * @author Freddy
 *
 */
public class AllTests
{}
