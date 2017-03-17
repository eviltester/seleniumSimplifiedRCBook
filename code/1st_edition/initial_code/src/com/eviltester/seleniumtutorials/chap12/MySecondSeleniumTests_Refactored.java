package com.eviltester.seleniumtutorials.chap12;

import static org.junit.Assert.*;

import org.junit.*;
import com.thoughtworks.selenium.*;

/**
 * This class is actually MySecondSeleniumTests but the refactored
 * version from chapter 12 to remove start and stop selenium duplication
 * 
 * @author Alan
 *
 */
public class MySecondSeleniumTests_Refactored {
	
		private Selenium startSeleniumAndSearchForSeleniumRC() {
			Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", 
					"http://www.compendiumdev.co.uk");
			selenium.start();
			selenium.open("http://www.compendiumdev.co.uk/selenium/search.php");
			selenium.type("xpath=//input[@name='q']", "Selenium-RC");
			selenium.click("xpath=//input[@name='btnG' and @type='submit']");
			selenium.waitForPageToLoad("30000");
			return selenium;
		}	
		
		@Test
		public void typeInASearchTermAndAssertThatHomePageTextIsPresent(){
			Selenium selenium = startSeleniumAndSearchForSeleniumRC();
			
			assertTrue(selenium.isTextPresent(
							"Selenium Remote-Control Selenium RC comes in two parts." +
							" A server which automatically"));
			assertTrue(selenium.isTextPresent(
						"launches and kills browsers, and acts as" + 
						" a HTTP proxy for web requests from them."));
				
			stopSeleniumAndCloseBrowser(selenium);
		}	
		
		
		
		@Test
		public void typeInASearchTermAndAssertThatHomePageURLExistsWithFewerXpath(){
			Selenium selenium = startSeleniumAndSearchForSeleniumRC();
			
			// create a new variable called matchingCountTotal to calculate 
			// the total number of times our xpath matches, initially setting
			// it to 0
			int matchingCountTotal = 0;
			
			// call getXpathCount with our xpath for the url and
			// add the intValue version to the total
			matchingCountTotal += selenium.getXpathCount("//a[starts-with(@href,'http://selenium-rc.seleniumhq.org')]").intValue();
			matchingCountTotal += selenium.getXpathCount("//a[starts-with(@href,'http://seleniumhq.org/projects/remote-control')]").intValue();

			assertTrue("No homepage URL found",matchingCountTotal>0);
			
			stopSeleniumAndCloseBrowser(selenium);
		}

		private void stopSeleniumAndCloseBrowser(Selenium selenium) {
			selenium.close();
			selenium.stop();
		}	
		
		
		@Test
		public void typeInASearchTermAndCheckPageTitleHasSearchTermInIt(){
			Selenium selenium = startSeleniumAndSearchForSeleniumRC();
			
			String pageTitle = selenium.getTitle();
			assertTrue("Page Title does not contain Selenium-RC search term: " + pageTitle, pageTitle.contains("Selenium-RC"));
			
			stopSeleniumAndCloseBrowser(selenium);
		}	
		
		@Test
		public void typeInASearchTermAndCheckSearchInputHasSearchTermInIt(){
			Selenium selenium = startSeleniumAndSearchForSeleniumRC();
			
			String searchTerm = selenium.getValue("xpath=//input[@name='q' and @title='Search']");
			
			assertTrue("Search Input does not contain Selenium-RC : " + searchTerm,
					searchTerm.equals("Selenium-RC"));
			
			stopSeleniumAndCloseBrowser(selenium);
		}
		
}

	
	
