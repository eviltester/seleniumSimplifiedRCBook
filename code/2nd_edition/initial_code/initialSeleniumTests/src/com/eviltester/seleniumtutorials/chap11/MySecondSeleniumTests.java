package com.eviltester.seleniumtutorials.chap11;

import org.junit.*;
import com.thoughtworks.selenium.*;
import static org.junit.Assert.*;

public class MySecondSeleniumTests {

	@Ignore("temporarily exclude to only run typeInASearchTerm") @Test
	public void initialiseSeleniumRCServer(){
		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox",
								"http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/");
		selenium.close();
		selenium.stop();
	}

	@Test
	public void typeInASearchTerm(){

		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", 
				"http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		selenium.close();
		selenium.stop();
	}	
	
	@Test
	public void typeInASearchTermAndAssertThatHomePageTextIsPresent(){
		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
			"Selenium Remote-Control Selenium RC comes in two parts." +
			" A server which automatically"));
		assertTrue(selenium.isTextPresent(
			"launches and kills browsers, and acts as" +
			" a HTTP proxy for web requests from them."));
		selenium.close();
		selenium.stop();
	}
	
	@Test(expected=AssertionError.class)
	public void typeInASearchTermAndAssertThatHomePageTextIsPresentInSource(){
		
		// this fails deliberately, hence the expected at the top to allow
		// CI to pass, this illustrates bad coding in the book
		
		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox",
				 "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");
		String pageSource = selenium.getHtmlSource().toLowerCase();
		assertTrue(pageSource.contains("Selenium Remote-Control Selenium RC comes in two parts. A server which automatically".toLowerCase()));
		assertTrue(pageSource.contains("launches and kills browsers, and acts as a HTTP proxy for web requests from them.".toLowerCase()));
		selenium.close();
		selenium.stop();
	}	
	
	@Test
	public void typeInASearchTermAndAssertThatHomePageURLExists(){
		Selenium selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");

		/* create a new variable called matchingCountTotal to calculate
		   the total number of times our xpath matches, initially setting
		   it to 0   */
		int matchingCountTotal = 0;

		// call getXpathCount with our first xpath for the url and
		// add the intValue version to the total
		matchingCountTotal += selenium.getXpathCount("//a[@href='http://selenium-rc.seleniumhq.org']").intValue();
		matchingCountTotal += selenium.getXpathCount("//a[@href='http://selenium-rc.seleniumhq.org/']").intValue();

		// call the getXpathCount again with our next url and add
		// the returned intValue to the total
		matchingCountTotal += selenium.getXpathCount("//a[@href='http://seleniumhq.org/projects/remote-control']").intValue();

		// call the getXpathCount again with our next url and add
		// the returned intValue to the total
		matchingCountTotal += selenium.getXpathCount("//a[@href='http://seleniumhq.org/projects/remote-control/index.html']").intValue();

		// call getXpathCount with our last url xpath and add
		// the returned intValue to the total
		matchingCountTotal += selenium.getXpathCount("//a[@href='http://seleniumhq.org/projects/remote-control/']").intValue();

		assertTrue("No homepage URL found",matchingCountTotal>0);

		selenium.close();
		selenium.stop();
	}
	
	@Test
	public void typeInASearchTermAndAssertThatHomePageURLExistsWithFewerXpath(){
		Selenium selenium = new DefaultSelenium(
						"localhost", 4444, "*firefox", 				                                                "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");

		// create a new variable called matchingCountTotal to calculate
		// the total number of times our xpath matches, initially setting
		// it to 0
		int matchingCountTotal = 0;

		// call getXpathCount with our xpath for the url and
		// add the intValue version to the total
		matchingCountTotal += selenium.getXpathCount(
				"//a[starts-with(@href,'http://selenium-rc.seleniumhq.org')]")
				.intValue();
		matchingCountTotal += selenium.getXpathCount(
			"//a[starts-with(@href,'http://seleniumhq.org/projects/remote-control')]").intValue();

		assertTrue("No homepage URL found",matchingCountTotal>0);

		selenium.close();
		selenium.stop();
	}	
	
	@Test
	public void typeInASearchTermAndCheckPageTitleHasSearchTermInIt(){
		Selenium selenium = new DefaultSelenium(
						"localhost", 4444,
						"*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");

		String pageTitle = selenium.getTitle();
		assertTrue("Page Title does not contain Selenium-RC search term: " + pageTitle, pageTitle.contains("Selenium-RC"));

		selenium.close();
		selenium.stop();
	}	
	@Test
	public void typeInASearchTermAndCheckSearchInputHasSearchTermInIt(){
		Selenium selenium = new DefaultSelenium("localhost", 4444,
						"*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");
		String searchTerm = selenium.getValue(
			"xpath=//input[@name='q' and @title='Search']");
		assertTrue("Search Input does not contain Selenium-RC : " + searchTerm,
			searchTerm.equals("Selenium-RC"));
		selenium.close();
		selenium.stop();
	}	
	
	@Test
	public void typeInASearchTermAndCheckResultsPage(){
		Selenium selenium = new DefaultSelenium("localhost", 4444,
					"*firefox", "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/selenium/search.php");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");

		String searchTerm = selenium.getValue(
			"xpath=//input[@name='q' and @title='Search']");

		assertTrue("Search Input does not contain Selenium-RC : " + searchTerm,
				searchTerm.equals("Selenium-RC"));
		String pageTitle = selenium.getTitle();
		assertTrue("Page Title does not contain Selenium-RC search term: " + pageTitle,
				pageTitle.contains("Selenium-RC"));

		int matchingCountTotal =
			selenium.getXpathCount(
			"//a[starts-with(@href,'http://seleniumhq.org/projects/remote-control')]").intValue();

		matchingCountTotal += selenium.getXpathCount(
		   "//a[starts-with(@href,'http://selenium-rc.seleniumhq.org')]").intValue();
		assertTrue("No homepage URL found ",matchingCountTotal>0);

		selenium.close();
		selenium.stop();
	}	
}

