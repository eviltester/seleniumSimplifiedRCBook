package com.eviltester.seleniumtutorials.chap08;

import org.junit.*;

import com.thoughtworks.selenium.*;

public class MySecondSeleniumTests {

	@Test
	public void initialiseSeleniumRCServer(){
		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox",
								"http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/");
		selenium.close();
		selenium.stop();
	}

}
