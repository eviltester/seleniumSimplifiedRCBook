package com.eviltester.intermediateBookAmends;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * This is typeInASearchTermAndCheckPageURLHasSearchTermInIt
 * from Chapter 12.1 based on MySecondSeleniumTests.java
 * 
 * @author Alan
 *
 */
public class UnrefactoredAnnotatedTest {

	@Test
	public void typeInASearchTermAndCheckPageURLHasSearchTermInIt(){
		/****************************************************************
		 * 			Start the test and goto Search 
		 ****************************************************************/
		Selenium selenium = new DefaultSelenium("localhost", 4444, 
								"*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("http://www.compendiumdev.co.uk/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		
		/****************************************************************
		 * 			Implement the Acceptance Criteria
		 ****************************************************************/
		String pageTitle = selenium.getTitle();
		assertTrue("Page Title does not contain Selenium-RC search term: " + pageTitle, 
				pageTitle.contains("Selenium-RC"));
		
		/****************************************************************
		 * 			Tidy up the test
		 ****************************************************************/
		selenium.close();
		selenium.stop();
	}
}
