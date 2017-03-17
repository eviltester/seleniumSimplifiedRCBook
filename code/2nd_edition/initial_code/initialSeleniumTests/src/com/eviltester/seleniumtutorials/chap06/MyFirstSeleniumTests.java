package com.eviltester.seleniumtutorials.chap06;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyFirstSeleniumTests{

	Selenium selenium=null;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://compendiumdev.co.uk/");
		selenium.start();
	}

	@Test
	public void searchForSeleniumRC() throws Exception {
		selenium.open("/selenium/search.php");
		selenium.type("q", "Selenium-RC");
		selenium.click("btnG");
	}

	@Test
	public void searchForEvilTester() throws Exception {
		selenium.open("/selenium/search.php");
		selenium.type("q", "evil tester");
		selenium.click("btnG");
		selenium.waitForPageToLoad("30000");
	}	

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}