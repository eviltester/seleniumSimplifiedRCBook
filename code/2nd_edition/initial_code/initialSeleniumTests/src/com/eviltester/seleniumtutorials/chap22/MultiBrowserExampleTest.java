package com.eviltester.seleniumtutorials.chap22;


import java.io.IOException;


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
	public void checkSeleniumVersionMatchesBook(){
			
		selenium.open("http://seleniumhq.org/download/");
		
	}	
	
}
