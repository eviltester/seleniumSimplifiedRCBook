package com.eviltester.bookSanity;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class SeleniumRCSiteTests {
	
	static SeleniumManager sManager = new SeleniumManager();
	static Selenium selenium;
	
	@BeforeClass
	public static void classSetup() throws IOException{
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
	 * If this test failes then we need to update:
	 * - Chapter 2: Install and Run Selenium-RC (images and text) 
	 */
	public void checkSeleniumVersionMatchesBook(){
			
		selenium.open("http://seleniumhq.org/download/");
		// find the td with Selenium RC then check that a sibling has 1.0.3 as version number
		assertEquals(1,selenium.getXpathCount("//td[starts-with(.,'Selenium RC')]/following-sibling::td[contains(.,'1.0.3')]").intValue());
		
	}
	
}