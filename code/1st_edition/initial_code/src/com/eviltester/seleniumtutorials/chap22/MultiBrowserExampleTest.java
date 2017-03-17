package com.eviltester.seleniumtutorials.chap22;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManagerEnd;
import com.thoughtworks.selenium.Selenium;

// this test uses a parameter setup in ant via hudson to control the browser

public class MultiBrowserExampleTest {

	static SeleniumManagerEnd sManager = new SeleniumManagerEnd();
	static Selenium selenium;
	static String browserCode;
	
	@BeforeClass
	public static void classSetup() throws IOException{
		sManager.setBrowserFromSystemProperties();
		sManager.start("http://seleniumhq.org");
		selenium = sManager.getSelenium();
	}


	
	@AfterClass
	public static void classTeardown(){
		sManager.stop();
		selenium = null;
	}
	
	@Test
	/**
	 * This test checks that the selenium version we have written the book
	 * against has not changed.
	 * If this test fails then we need to update:
	 * - Chapter 2: Install and Run Selenium-RC (images and text) 
	 */
	public void checkSeleniumVersionMatchesBook(){
			
		selenium.open("http://seleniumhq.org/download/");
		// find the td with Selenium RC then check that a sibling has 1.0.3 as version number
		assertEquals(1,selenium.getXpathCount("//td[starts-with(.,'Selenium RC')]/following-sibling::td[contains(.,'1.0.3')]").intValue());
		
	}	
	
}
