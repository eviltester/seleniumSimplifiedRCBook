package com.eviltester.seleniumtutorials.refactored;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class Selenium_Manager_Tests {

	static SeleniumManager sm; 
	static Selenium selenium;
	
	@BeforeClass
	static public void startServer() throws IOException{
		 sm = new SeleniumManager();
		 sm.start("http://www.compendiumdev.co.uk");
		 selenium = sm.getSelenium();
	}
	
	@AfterClass
	static public void stopServer(){
		sm.stop();
	}
	
	@Test
	public void openCompendiumDevHomePage(){
		selenium.open("/");
	}
}
