package com.eviltester.seleniumSimplified.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class BasicAjaxPage {

	private static final String COMBO2_LOCATOR = "combo2";
	private static final String COMBO1_LOCATOR = "combo1";
	private static final String INPUT_VALUE_LOCATOR = "xpath=//input[@id='lteq30']";
	Selenium selenium;
	
	public BasicAjaxPage(Selenium selenium) {
		this.selenium = selenium;
	}

	public void open() {
		selenium.open("/selenium/basic_ajax.html");
	}

	public void typeInputValue(String inputValue) {
		selenium.type(INPUT_VALUE_LOCATOR, inputValue);
	}

	public void selectCategory(String category) {
		selenium.select(COMBO1_LOCATOR, category);
	}

	public boolean hasShownValidationAlert() {
		return selenium.isAlertPresent();
	}

	public void triggerInputValidation() {
		selenium.fireEvent(INPUT_VALUE_LOCATOR, "blur");
	}

	public String getValidationAlertMessage() {
		return selenium.getAlert();
	}

	public String getLanguage() {
		return selenium.getSelectedLabel(COMBO2_LOCATOR);
	}

	public void waitForAjaxSymbolToGoAway() {
		selenium.waitForCondition("window.document.getElementById('ajaxBusy').style.display=='none'", "4000");
	}

}









