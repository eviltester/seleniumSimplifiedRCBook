package com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel;

import com.thoughtworks.selenium.Selenium;

public class CalculateForm {

	private Selenium selenium;

	public CalculateForm(Selenium selenium) {
		this.selenium = selenium;
	}

	public void open() {
		selenium.open("/selenium/calculate.php");
	}

	public void setNumber1(String number1) {
		selenium.type("number1", number1);
	}

	public void setNumber2(String number2) {
		selenium.type("number2", number2);	
	}

	public void setFunction(String function) {
		selenium.select("function", "value=" + function);
	}

	public CalculateForm doCalculation() {
		selenium.click("calculate");
		selenium.waitForPageToLoad("30000");
		return this;
	}

	public String getAnswer() {
		return selenium.getText("answer");
	}

}
