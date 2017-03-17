package com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel;

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
		if(hasUserName()){
			return selenium.getText("_valueusername");
		}else{
			return selenium.getText("//p/strong[.='No Value for username']");
		}
	}		
	
	public boolean hasUserName() {
		return selenium.isElementPresent("_username");
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
			return selenium.getText("//p/strong[.='No Value for filename']");
		}
	}

	public boolean hasPassword() {
		return 	selenium.isElementPresent("_password");
	}

	public String getPassword() {
		if(hasPassword()){
			return selenium.getText("_password");
		}else{
			/* this is a bit of a 'cheat' because I have the value in here
			 * but this will fail if the text changes so this is a useful
			 * xpath for letting me know if the app gets amended
			 */
			return selenium.getText("//p/strong[.='No Value for password']");
		}
	}

	public String getComment() {
		if(hasComments()){
			return selenium.getText("_valuecomments");
		}else{
			return selenium.getText("//p/strong[.='No Value for comments']");
		}
	}
	


}
