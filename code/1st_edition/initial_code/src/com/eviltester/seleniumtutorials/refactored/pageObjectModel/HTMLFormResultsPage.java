package com.eviltester.seleniumtutorials.refactored.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class HTMLFormResultsPage {

	Selenium selenium;
	
	public HTMLFormResultsPage(Selenium selenium) {
		this.selenium = selenium;
	}

	public String getTitle() {
		return selenium.getTitle();
	}

	public String getUserName() {
		return selenium.getText("_valueusername");
	}

	public boolean hasHeading() {
		return selenium.isTextPresent("Submitted Values");
	}

	public boolean hasComments() {
		return selenium.isElementPresent("_comments");
	}

	public boolean hasFilename() {
		return selenium.isElementPresent("_filename");
	}

	public int getCheckboxCount() {
		return selenium.getXpathCount("//div[@id='_checkboxes']/ul/li").intValue();
	}

	public BasicHTMLForm goBackToForm() {
		// click to go back to the form
		selenium.click("back_to_form");
		selenium.waitForPageToLoad("30000");
		return new BasicHTMLForm(selenium);
	}

	public String getFilename() {
		if(hasFilename()){
			return selenium.getText("_valuefilename");
		}else{
			return selenium.getText("xpath=//body/p[2]/strong");
		}
	}

	public boolean hasPassword() {
		return 	selenium.isElementPresent("_password");
	}

}
