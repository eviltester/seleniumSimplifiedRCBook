package com.eviltester.seleniumtutorials;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyFirstSeleniumTests{
	
	Selenium selenium;
	
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://compendiumdev.co.uk/");
		selenium.start();
	}

	@Test
	public void testExported() throws Exception {
		selenium.open("/selenium/search.php");
		selenium.type("q", "eviltester");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
		selenium.type("q", "eviltester selenium");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
