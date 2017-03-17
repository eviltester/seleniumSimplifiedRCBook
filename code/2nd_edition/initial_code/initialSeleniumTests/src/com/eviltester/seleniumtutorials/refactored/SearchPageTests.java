package com.eviltester.seleniumtutorials.refactored;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import com.eviltester.seleniumtutorials.refactored.pageObjectModel.SearchPage;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.*;

public class SearchPageTests {
		static SeleniumManager sm;
		static Selenium selenium;
		static SearchPage searchPage;
		
		@BeforeClass
		static public void automateTestSetup() throws IOException{
			sm = new SeleniumManager();
			sm.start("http://www.compendiumdev.co.uk");
			selenium = sm.getSelenium();
			startSeleniumAndSearchForSeleniumRC();
		}

		@AfterClass
		static public void stopSeleniumAndCloseBrowser() {
			sm.stop();
		}	
		
		static void startSeleniumAndSearchForSeleniumRC() {
			searchPage = new SearchPage(selenium);
			searchPage.open();
			searchPage.typeSearchTerm("Selenium-RC");
			searchPage.clickSearchButton();
		}	
		
		@Test
		public void typeInASearchTermAndAssertThatHomePageTextIsPresent(){
			assertTrue(searchPage.isTextPresent(
							"Selenium Remote-Control Selenium RC comes in two parts." +
							" A server which automatically"));
			assertTrue(searchPage.isTextPresent(
						"launches and kills browsers, and acts as" + 
						" a HTTP proxy for web requests from them."));	
		}	
		
		@Test
		public void typeInASearchTermAndAssertThatHomePageURLExists(){
			
			int matchingCountTotal = 0;
			
			matchingCountTotal += searchPage.numberOfURLsThatStartWith(
										"http://selenium-rc.seleniumhq.org");
			matchingCountTotal += searchPage.numberOfURLsThatStartWith(
										"http://seleniumhq.org/projects/remote-control");
			
			assertTrue("No homepage URL found",matchingCountTotal>0);
		}
		
		@Test
		public void typeInASearchTermAndCheckPageTitleHasSearchTermInIt(){
			
			String pageTitle = searchPage.getTitle();
			assertTrue("Page Title does not contain Selenium-RC search term: " + pageTitle, pageTitle.contains("Selenium-RC"));
			
		}	
		
		@Test
		public void typeInASearchTermAndCheckSearchInputHasSearchTermInIt(){
			
			String searchTerm = searchPage.getSearchTerm();
			
			assertTrue("Search Input does not contain Selenium-RC : " + searchTerm,
					searchTerm.equals("Selenium-RC"));
			
		}
}

	
	


