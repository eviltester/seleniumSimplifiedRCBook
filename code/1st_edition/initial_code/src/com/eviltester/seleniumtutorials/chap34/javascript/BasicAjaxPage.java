package com.eviltester.seleniumtutorials.chap34.javascript;

import com.thoughtworks.selenium.Selenium;

public class BasicAjaxPage {

	Selenium selenium;
	
	public BasicAjaxPage(Selenium selenium) {
		this.selenium = selenium;
	}

	public void open() {
		selenium.open("/selenium/basic_ajax.html");
	}

	public void typeInputValue(String inputValue) {
		selenium.type("xpath=//input[@id='lteq30']", inputValue);
	}

	public void selectCategory(String category) {
		selenium.select("combo1", category);
	}

	public boolean hasShownValidationAlert() {
		return selenium.isAlertPresent();
	}

	public void triggerInputValidation() {
		selenium.fireEvent("xpath=//input[@id='lteq30']", "blur");
	}

	public String getValidationAlertMessage() {
		return selenium.getAlert();
	}

	public String getLanguage() {
		return selenium.getSelectedLabel("combo2");
	}

	public void waitForAjaxSymbolToGoAway() {
		selenium.waitForCondition("window.document.getElementById('ajaxBusy').style.display=='none'", "4000");
	}

}
