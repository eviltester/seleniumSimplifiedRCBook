package com.eviltester.seleniumtutorials.refactored.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class SearchPage {

	private Selenium selenium;
	
	public SearchPage(Selenium selenium) {
		this.selenium = selenium;
	}

	public void open() {
		selenium.open("/selenium/search.php");
	}

	public void typeSearchTerm(String searchTerm) {
		selenium.type("xpath=//input[@name='q']", searchTerm);
	}

	public void clickSearchButton() {
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("30000");
	}

	public boolean isTextPresent(String textToSearchFor) {
		return selenium.isTextPresent(textToSearchFor);
	}
	
	public int numberOfURLsThatStartWith(String aHREF) {
		return selenium.getXpathCount(
			"//a[starts-with(@href,'" + aHREF + "')]").intValue();
	}
	
	public String getTitle() {
		return selenium.getTitle();
	}

	public String getSearchTerm() {
		return selenium.getValue("xpath=//input[@name='q' and @title='Search']");
	}


}
