package com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class BasicHTMLForm {

	private static final String DROPDOWN_LOCATOR = "dropdown";
	private static final String FILENAME_LOCATOR = "filename";
	private static final String COMMENTS_LOCATOR = "comments";
	private static final String PASSWORD_LOCATOR = "password";
	private static final String USERNAME_LOCATOR = "username";
	private static final String MULTIPLESELECT_LOCATOR = "multipleselect[]";
	private static final String RADIOBUTTON_LOCATOR = "//input[@name='radioval' and @value='rd%s']";
	private static final String CHECKBOXES_LOCATOR = "//input[@name='checkboxes[]' and @value='cb%s']";
	Selenium selenium;
	
	public BasicHTMLForm(Selenium selenium) {
		this.selenium = selenium;
	}

	public HTMLFormResultsPage clickSubmitButton() {
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		return new HTMLFormResultsPage(selenium);		
	}

	public void submitForm() {
		selenium.submit("//form[@id='HTMLFormElements']");
		selenium.waitForPageToLoad("30000");		
	}

	public void typeUserName(String userName) {
		selenium.type(USERNAME_LOCATOR,userName);		
	}

	public void typePassword(String password) {
		selenium.type(PASSWORD_LOCATOR,password);
	}

	public void typeComments(String comments) {
		selenium.type(COMMENTS_LOCATOR, comments);
	}

	public void typeFilename(String fileName) {
		selenium.type(FILENAME_LOCATOR, fileName);
	}

	public void clickCheckBox(String checkBoxIDNumber){
		selenium.click(String.format(CHECKBOXES_LOCATOR,checkBoxIDNumber));	
	}
	
	public void clickRadioButton(String radioButtonIDNumber) {
		selenium.click(String.format(RADIOBUTTON_LOCATOR,radioButtonIDNumber));
	}

	public void removeMultiSelection(String selectionID) {
		selenium.removeSelection(MULTIPLESELECT_LOCATOR, "label=Selection Item " + selectionID);
	}

	public void selectMultiSelection(String selectionID) {
		selenium.addSelection(MULTIPLESELECT_LOCATOR, "label=Selection Item " + selectionID);
	}

	public void open() {
		selenium.open("/selenium/basic_html_form.html");
		
	}

	public void selectDropDownItem(String itemID) {
		selectDropDown("Drop Down Item " + itemID);
	}

	public void selectDropDown(String itemLabel) {
		selenium.select(DROPDOWN_LOCATOR, "label=" + itemLabel);
	}	

	public void setHiddenField(String newHiddenFieldValue) {
		selenium.getEval(
		"this.browserbot.findElement(\"name=hiddenField\").value=\"" + newHiddenFieldValue + "\"");
		
	}

	public String getUserName() {
		return selenium.getValue(USERNAME_LOCATOR);
	}

	public String getPassword() {
		return selenium.getValue(PASSWORD_LOCATOR);
	}

	public String getFileName() {
		return selenium.getValue(FILENAME_LOCATOR);
	}

	public String getComments() {
		return selenium.getValue(COMMENTS_LOCATOR);
	}

	public boolean isCheckBoxSelected(String checkBoxIDNumber ) {
		return selenium.isChecked(String.format(CHECKBOXES_LOCATOR,checkBoxIDNumber));
	}

	public void setChecked_CheckBox(String checkBoxIDNumber) {
		selenium.check(String.format(CHECKBOXES_LOCATOR,checkBoxIDNumber));
	}

	public void setUnChecked_CheckBox(String checkBoxIDNumber) {
		selenium.uncheck(String.format(CHECKBOXES_LOCATOR,checkBoxIDNumber));		
	}

	public boolean isRadioButtonSelected(String radioButtonIDNumber) {
		return selenium.isChecked(String.format(RADIOBUTTON_LOCATOR,radioButtonIDNumber));
	}

	public void setChecked_RadioButton(String radioButtonIDNumber) {
		selenium.check(String.format(RADIOBUTTON_LOCATOR,radioButtonIDNumber));
	}

	public String[] getAllDropDownValues() {
		return selenium.getSelectOptions(DROPDOWN_LOCATOR);
	}

	public int getCurrentDropDownSelectionIndex() {
		// get the index of the currently selected item in the dropdown
		String currentDropDownItem = selenium.getSelectedIndex(DROPDOWN_LOCATOR);
		return Integer.valueOf(currentDropDownItem);
	}

	public boolean isADropDownItemSelected() {
		return selenium.isSomethingSelected(DROPDOWN_LOCATOR);
	}

	public String getTitle() {
		return selenium.getTitle();
	}
}
