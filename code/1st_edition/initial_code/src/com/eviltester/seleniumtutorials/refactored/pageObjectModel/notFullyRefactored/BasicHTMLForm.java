package com.eviltester.seleniumtutorials.refactored.pageObjectModel.notFullyRefactored;

import com.eviltester.seleniumtutorials.refactored.pageObjectModel.HTMLFormResultsPage;
import com.thoughtworks.selenium.Selenium;

public class BasicHTMLForm {

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
		selenium.type("username",userName);		
	}

	public void typePassword(String password) {
		selenium.type("password",password);
	}

	public void typeComments(String comments) {
		selenium.type("comments", comments);
	}

	public void typeFilename(String fileName) {
		selenium.type("filename", fileName);
	}

	public void clickCheckBox(String checkBoxNumber){
		selenium.click("//input[@name='checkboxes[]' and @value='cb" + checkBoxNumber +"']");	
	}
	
	public void clickRadioButton(String radioButtonNumber) {
		selenium.click("//input[@name='radioval' and @value='rd" + radioButtonNumber + "']");
	}

	public void removeMultiSelection(String selectionID) {
		selenium.removeSelection("multipleselect[]", "label=Selection Item " + selectionID);
	}

	public void selectMultiSelection(String selectionID) {
		selenium.addSelection("multipleselect[]", "label=Selection Item " + selectionID);
	}

	public void open() {
		selenium.open("/selenium/basic_html_form.html");
		
	}

	public void selectDropDownItem(String itemID) {
		selectDropDown("Drop Down Item " + itemID);
	}

	public void selectDropDown(String itemLabel) {
		selenium.select("dropdown", "label=" + itemLabel);
	}	

	public void setHiddenField(String newHiddenFieldValue) {
		selenium.getEval(
		"this.browserbot.findElement(\"name=hiddenField\").value=\"" + newHiddenFieldValue + "\"");
		
	}

	public String getUserName() {
		return selenium.getValue("username");
	}

	public String getPassword() {
		return selenium.getValue("password");
	}

	public String getFileName() {
		return selenium.getValue("filename");
	}

	public String getComments() {
		return selenium.getValue("comments");
	}

	public boolean isCheckBoxSelected(String checkBoxIDNumber ) {
		return selenium.isChecked("//input[@name='checkboxes[]' and @value='cb" + checkBoxIDNumber +"']");
	}

	public void setChecked_CheckBox(String checkBoxIDNumber) {
		selenium.check("//input[@name='checkboxes[]' and @value='cb" + checkBoxIDNumber + "']");
	}

	public void setUnChecked_CheckBox(String checkBoxIDNumber) {
		selenium.uncheck("//input[@name='checkboxes[]' and @value='cb" + checkBoxIDNumber + "']");		
	}

	public boolean isRadioButtonSelected(String radioButtonIDNumber) {
		return selenium.isChecked("//input[@name='radioval' and @value='rd" + radioButtonIDNumber + "']");
	}

	public void setChecked_RadioButton(String radioButtonIDNumber) {
		selenium.check("//input[@name='radioval' and @value='rd" + radioButtonIDNumber + "']");
	}

	public String[] getAllDropDownValues() {
		return selenium.getSelectOptions("dropdown");
	}

	public int getCurrentDropDownSelectionIndex() {
		// get the index of the currently selected item in the dropdown
		String currentDropDownItem = selenium.getSelectedIndex("dropdown");
		return Integer.valueOf(currentDropDownItem);
	}

	public boolean isADropDownItemSelected() {
		return selenium.isSomethingSelected("dropdown");
	}

	public String getTitle() {
		return selenium.getTitle();
	}
}
