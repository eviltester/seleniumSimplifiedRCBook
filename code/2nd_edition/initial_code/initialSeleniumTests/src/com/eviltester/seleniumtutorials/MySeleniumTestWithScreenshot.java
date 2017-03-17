package com.eviltester.seleniumtutorials;

import com.thoughtworks.selenium.*;

public class MySeleniumTestWithScreenshot extends SeleneseTestCase {
	
	public MySeleniumTestWithScreenshot(){
		setCaptureScreenShotOnFailure(true);
	}
	
	public void setUp() throws Exception {
		setUp("http://www.compendiumdev.co.uk", "*firefox");
	}
	
	public void testAsserts_for_wrong_value() throws Exception {
	
		selenium.open("/selenium/search.php");
		selenium.type("q", "wrongvalue");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		assertEquals("selenium-rc - Google Search", selenium.getTitle());
		assertEquals("selenium-rc", selenium.getValue("q"));
	}	
	
	public void testVerifys_for_wrong_value() throws Exception {

		selenium.open("/selenium/search.php");
		selenium.type("q", "wrongvalue");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		// verify will not trigger screenshots		
		verifyEquals("selenium-rc - Google Search", selenium.getTitle());
		verifyEquals("selenium-rc", selenium.getValue("q"));
	}	
	
}
