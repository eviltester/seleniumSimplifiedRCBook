package com.eviltester.seleniumSimplified.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class HTMLFormResultsPage {

	private static final String ID_PREFIX = "_";
	private static final String VALUE_PREFIX = "value";
	
	private static final String FN_USERNAME = "username";
	private static final String FN_FILENAME = "filename";
	private static final String FN_PASSWORD = "password";
	private static final String FN_COMMENTS = "comments";
	private static final String FN_HIDDENFIELD = "hiddenField";
	private static final String FN_CHECKBOXES = "checkboxes";
	private static final String FN_MULTISELECT = "multipleselect";
	private static final String FN_RADIOBUTTON = "radioval";
	private static final String FN_DROPDOWN = "dropdown";
	private static final String FN_SUBMITBUTTON = "submitbutton";
	
	Selenium selenium;

	private String getValueID(String fieldName) {
		return ID_PREFIX + VALUE_PREFIX + fieldName;
	}
	
	private String getID(String fieldName) {
		return ID_PREFIX + fieldName;
	}

	private String getNoValueLocator(String fieldName){
		/* this is a bit of a 'cheat' because I have the value in here
		 * but this will fail if the text changes so this is a useful
		 * xpath for letting me know if the app gets amended
		 */
		return "//p/strong[.='No Value for " + fieldName +"']";
	}
	
	public HTMLFormResultsPage(Selenium selenium) {
		this.selenium = selenium;
	}

	public String getTitle() {
		return selenium.getTitle();
	}

	public boolean hasUserName() {
		return selenium.isElementPresent(getID(FN_USERNAME));
	}	
	
	public String getUserName() {
		if(hasUserName()){
			return selenium.getText(getValueID(FN_USERNAME));
		}else{
			return selenium.getText(getNoValueLocator(FN_USERNAME));
		}
	}		
	
	public boolean hasHeading() {
		return selenium.isTextPresent("Submitted Values");
	}

	public boolean hasComments() {
		return selenium.isElementPresent(getID(FN_COMMENTS));
	}

	public boolean hasFilename() {
		return selenium.isElementPresent(getID(FN_FILENAME));
	}

	public int getCheckboxCount() {
		return selenium.getXpathCount("//div[@id='" + getID(FN_CHECKBOXES) + "']/ul/li").intValue();
	}

	public String getCheckBoxValue(int i) {
		return selenium.getText("//div[@id='" + getID(FN_CHECKBOXES) + "']/ul/li[" + i + "]");
	}	

	public int getMultiSelectCount() {
		return selenium.getXpathCount("//div[@id='" + getID(FN_MULTISELECT) + "']/ul/li").intValue();
	}

	public String getMultiSelectValue(int i) {
		return selenium.getText("//div[@id='" + getID(FN_MULTISELECT) + "']/ul/li[" + i + "]");
	}	
	
	public BasicHTMLForm goBackToForm() {
		// click to go back to the form
		selenium.click("back_to_form");
		selenium.waitForPageToLoad("30000");
		return new BasicHTMLForm(selenium);
	}

	public String getFilename() {
		if(hasFilename()){
			return selenium.getText(getValueID(FN_FILENAME));
		}else{
			return selenium.getText(getNoValueLocator(FN_FILENAME));
		}
	}

	public boolean hasPassword() {
		return 	selenium.isElementPresent(getID(FN_PASSWORD));
	}

	public String getPassword() {
		if(hasPassword()){
			return selenium.getText(getValueID(FN_PASSWORD));
		}else{
			return selenium.getText(getNoValueLocator(FN_PASSWORD));
		}
	}

	public String getComment() {
		if(hasComments()){
			return selenium.getText(getValueID(FN_COMMENTS));
		}else{
			return selenium.getText(getNoValueLocator(FN_COMMENTS));
		}
	}

	public String getHiddenFieldValue() {
		if(hasHiddenField()){
			return selenium.getText(getValueID(FN_HIDDENFIELD));
		}else{
			return selenium.getText(getNoValueLocator(FN_HIDDENFIELD));
		}
	}

	public boolean hasHiddenField() {
		return 	selenium.isElementPresent(getID(FN_HIDDENFIELD));
	}

	public String getRadioButtonValue() {
		if(hasRadioButton()){
			return selenium.getText(getValueID(FN_RADIOBUTTON));
		}else{
			return selenium.getText(getNoValueLocator(FN_RADIOBUTTON));
		}
	}

	public boolean hasRadioButton() {
		return selenium.isElementPresent(getID(FN_RADIOBUTTON));
	}

	public String getDropDownValue() {
		if(hasDropDown()){
			return selenium.getText(getValueID(FN_DROPDOWN));
		}else{
			return selenium.getText(getNoValueLocator(FN_DROPDOWN));
		}
	}

	public boolean hasDropDown() {
		return selenium.isElementPresent(getID(FN_DROPDOWN));
	}

	public String getSubmitButton() {
			return selenium.getText(getValueID(FN_SUBMITBUTTON));
	}	
	
	public boolean hasSubmitButton() {
		return selenium.isElementPresent(getID(FN_SUBMITBUTTON));
	}
	



	


}
