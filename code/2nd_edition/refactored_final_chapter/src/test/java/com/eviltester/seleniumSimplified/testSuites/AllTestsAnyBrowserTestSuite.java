package com.eviltester.seleniumSimplified.testSuites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eviltester.seleniumutils.seleniumManager.TheSeleniumManager;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
	AnyBrowserTestsGroup.class
})

// see comments on http://radio.javaranch.com/lasse/2006/07/27/1154024535662.html for
// additional information on triggering the tests as a main method 

public class AllTestsAnyBrowserTestSuite {

	@BeforeClass
	public static void run_before_suite(){
		// If I want to run the suite from within eclipse (and not use ant)
		// and I want to have the tests run quickly I can set the 
		// system property to control the Selenium Manager interaction here
		//System.setProperty("selenium.stopAfterSuite", "TRUE");
	}
	
	@AfterClass
	public static void run_after_suite(){
		TheSeleniumManager.getSeleniumManager().stopSelenium();
	}	
}
