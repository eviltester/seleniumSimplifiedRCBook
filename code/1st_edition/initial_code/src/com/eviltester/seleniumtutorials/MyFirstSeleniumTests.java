package com.eviltester.seleniumtutorials;

import org.junit.Ignore;

import com.thoughtworks.selenium.*;

public class MyFirstSeleniumTests extends SeleneseTestCase {
	
	public void setUp() throws Exception {
		
		setUp("http://www.google.co.uk/", "*firefox");
	}
	
	public void testGoogle_for_selenium_rc() throws Exception {
		selenium.open("http://www.google.co.uk/");
		selenium.type("q", "Selenium-RC");
		selenium.click("btnG");
	}
	
	public void testGoogleForEvilTesterImages() throws Exception {
		selenium.open("/");
		selenium.type("q", "evil tester");
		selenium.click("link=Images");
		selenium.waitForPageToLoad("30000");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
	}	
	
	/*
	public void testVerifys_for_wrong_value() throws Exception {
		
		selenium.open("/");
		selenium.type("q", "wrongvalue");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		verifyEquals("selenium-rc - Google Search", selenium.getTitle());
		verifyEquals("selenium-rc", selenium.getValue("q"));
	}
	
	
	public void testAsserts_for_wrong_value() throws Exception {
	
		selenium.open("/");
		selenium.type("q", "wrongvalue");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		assertEquals("selenium-rc - Google Search", selenium.getTitle());
		assertEquals("selenium-rc", selenium.getValue("q"));
	}	
	*/
}
