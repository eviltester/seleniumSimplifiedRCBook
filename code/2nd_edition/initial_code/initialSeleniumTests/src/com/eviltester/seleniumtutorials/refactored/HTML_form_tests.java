package com.eviltester.seleniumtutorials.refactored;

/** based on chap18 html_form_tests.java
 * use selenium Manager
 * use the page object
 */


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.eviltester.seleniumtutorials.refactored.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class HTML_form_tests {

	static SeleniumManager sm;
	static Selenium selenium;
	BasicHTMLForm htmlForm;
	
	@BeforeClass
	static public void startServer() throws IOException{
		 sm = new SeleniumManager();
		 sm.start("http://www.compendiumdev.co.uk");
		 selenium = sm.getSelenium();
	}
	
	@AfterClass
	static public void stopServer(){
		sm.stop();
	}	

	@Before
	public void setUp() throws Exception {
		htmlForm = new BasicHTMLForm(selenium);
		htmlForm.open();
	}

	@Test
	public void test_submit_form_with_default_values(){	
		htmlForm.clickSubmitButton();
	}
	
	@Test
	public void test_submit_form_without_clicking_submit(){
		htmlForm.submitForm();
	}
	
	@Test
	public void test_submit_form_with_new_username(){
		htmlForm.typeUserName("eviltester");
		htmlForm.clickSubmitButton();
	}	
	
	@Test
	public void test_submit_form_with_new_password(){
		htmlForm.typePassword("myPassword");
		htmlForm.clickSubmitButton();
	}	
	
	@Test	
	public void test_submit_form_with_html_escaped_text(){
		htmlForm.typeComments("\\\n\t\"I said, give me your pound sign\"\u00A3");
		htmlForm.clickSubmitButton();
	}	
	
	@Test @Ignore("File Processing unreliable in Selenium between versions")
	public void test_submit_form_with_filename(){
		//WebDriverBackedSelenium requires a full url for attachFile
		htmlForm.typeFilename("file://c:/selenium/readme.txt");
		//htmlForm.typeFilename("file://c:\\selenium\\readme.txt");
		htmlForm.clickSubmitButton();
	}	
	
	@Test
	public void test_submit_form_with_click_check_and_radio(){
		htmlForm.clickCheckBox("2");
		htmlForm.clickCheckBox("3");
		htmlForm.clickRadioButton("3");
		htmlForm.clickSubmitButton();		
	}
	
	@Test
	public void test_submit_form_with_multiple_select_values(){
		htmlForm.removeMultiSelection("3");
		htmlForm.selectMultiSelection("4");
		htmlForm.selectMultiSelection("1");		
		htmlForm.removeMultiSelection("4");
		htmlForm.selectMultiSelection("2");
		htmlForm.clickSubmitButton();
	}	
	
	@Test
	public void test_submit_form_with_dropdown_values(){
		
		htmlForm.selectDropDownItem("1");
		htmlForm.clickSubmitButton();
	}	
	
	@Test
	public void test_submit_form_with_hidden_field(){
		
		htmlForm.setHiddenField("amended value");
		htmlForm.clickSubmitButton();
		
	}	
	
	@Test
	public void test_check_text_entered_values(){
		htmlForm.typeUserName("eviltester");
		htmlForm.typePassword("myPassword");
		htmlForm.typeComments("simple text");
		
		
		assertEquals("username not as entered", "eviltester",htmlForm.getUserName());
		assertEquals("password not as entered", "myPassword",htmlForm.getPassword());
		
		assertEquals("comments not as entered", "simple text",htmlForm.getComments());
	}	
	
	@Test
	public void test_check_checkbox_values(){

		assertFalse("by default checkbox 1 is not selected", htmlForm.isCheckBoxSelected("1")); 
		assertFalse("by default checkbox 2 is not selected", htmlForm.isCheckBoxSelected("2")); 
		assertTrue("by default checkbox 3 is selected", htmlForm.isCheckBoxSelected("3"));

		htmlForm.setChecked_CheckBox("2");
		htmlForm.setUnChecked_CheckBox("3");
				
		assertFalse("checkbox 1 is still not selected",  htmlForm.isCheckBoxSelected("1"));
		assertTrue("checkbox 2 is now selected", htmlForm.isCheckBoxSelected("2"));
		assertFalse("default checkbox 3 is no longer selected", htmlForm.isCheckBoxSelected("3"));
	}

	@Test
	public void test_check_radio_values(){

		assertFalse("by default radio 1 is not selected", htmlForm.isRadioButtonSelected("1")); 
		assertTrue("by default radio 2 is selected", htmlForm.isRadioButtonSelected("2"));
		assertFalse("by default radio 3 is not selected", htmlForm.isRadioButtonSelected("3"));

		htmlForm.setChecked_RadioButton("1");
		
		assertTrue("radio 1 is now selected", htmlForm.isRadioButtonSelected("1"));
		assertFalse("radio 2 is no longer selected", htmlForm.isRadioButtonSelected("2"));
		assertFalse("radio 3 is not selected", htmlForm.isRadioButtonSelected("3"));
	}	
	
	@Test
	public void test_randomly_select_value_from_dropdown(){

		String dropDownOptions[] = htmlForm.getAllDropDownValues();
		int previouslySelectedIndex = htmlForm.getCurrentDropDownSelectionIndex();

		// randomly choose an item in the dropdown
		Random generator = new Random(); // requires import java.util.Random to be added
		int dropDownIndex = generator.nextInt(dropDownOptions.length);

		// make sure it isn't the same as the current one
		if(dropDownIndex==previouslySelectedIndex){
			// it is the same, so add one
			dropDownIndex++;
			// but this might push it out of bounds so modulus it
			dropDownIndex = dropDownIndex % dropDownOptions.length;
		}

		htmlForm.selectDropDown(dropDownOptions[dropDownIndex]);
		
		
		assertTrue(htmlForm.isADropDownItemSelected());
		assertEquals(dropDownIndex,htmlForm.getCurrentDropDownSelectionIndex());

		//double check we didn't select the same thing
		assertTrue(dropDownIndex!=previouslySelectedIndex);
	}	
}
