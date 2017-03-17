package com.eviltester.seleniumSimplified.htmlform;

/** based on chap18 html_form_tests.java
 * use selenium Manager
 * use the page object
 */


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.eviltester.seleniumSimplified.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumSimplified.pageObjectModel.HTMLFormResultsPage;
import com.eviltester.seleniumSimplified.testClasses.SeleniumSimplifiedTest;


public class HTML_form_tests extends SeleniumSimplifiedTest{


	BasicHTMLForm htmlForm;
	
	@Before
	public void setUp() throws Exception {
		htmlForm = new BasicHTMLForm(selenium);
		htmlForm.open();
	}

	@Test
	public void test_submit_form_with_default_values(){	
		
		assertEquals("HTML Form Elements",htmlForm.getTitle());	
		
		HTMLFormResultsPage results = htmlForm.clickSubmitButton();
		
		assertEquals("No Value for username",results.getUserName());
		assertEquals("No Value for password",results.getPassword());
		assertEquals("Comments...", results.getComment());
		assertEquals("No Value for filename", results.getFilename());
		assertEquals("Hidden Field Value", results.getHiddenFieldValue());
		assertEquals(1, results.getCheckboxCount());
		assertEquals("cb3", results.getCheckBoxValue(1));
		assertEquals("rd2", results.getRadioButtonValue());
		assertEquals(1, results.getMultiSelectCount());
		assertEquals("ms4", results.getMultiSelectValue(1));
		assertEquals("dd3", results.getDropDownValue());
		assertEquals("submit", results.getSubmitButton());
		
		assertEquals("Processed Form Details", results.getTitle());
		assertTrue(results.hasHeading());
	}
	
	@Test
	public void test_submit_form_without_clicking_submit(){
		
		HTMLFormResultsPage results = htmlForm.submitForm();
		
		assertEquals("No Value for username",results.getUserName());
		assertEquals("No Value for password",results.getPassword());
		assertEquals("Comments...", results.getComment());
		assertEquals("No Value for filename", results.getFilename());
		assertEquals("Hidden Field Value", results.getHiddenFieldValue());
		assertEquals(1, results.getCheckboxCount());
		assertEquals("cb3", results.getCheckBoxValue(1));
		assertEquals("rd2", results.getRadioButtonValue());
		assertEquals(1, results.getMultiSelectCount());
		assertEquals("ms4", results.getMultiSelectValue(1));
		assertEquals("dd3", results.getDropDownValue());
		
		assertEquals("You did not click the submit button", results.getSubmitButton());
		
		assertEquals("Processed Form Details", results.getTitle());
		assertTrue(results.hasHeading());
	}
	
	@Test
	public void test_submit_form_with_new_details(){
		htmlForm.typeUserName("eviltester");
		htmlForm.typePassword("myPassword");
		htmlForm.typeComments("This is a simple comment");
		
		htmlForm.setChecked_CheckBox("1");
		htmlForm.setChecked_CheckBox("2");
		htmlForm.setChecked_CheckBox("3");
		
		htmlForm.clickRadioButton("1");
		
		htmlForm.selectMultiSelection("1");
		htmlForm.selectMultiSelection("2");
		htmlForm.selectMultiSelection("3");
		htmlForm.selectMultiSelection("4");
		
		htmlForm.selectDropDownItem("1");
		
		htmlForm.setHiddenField("amended value");
		
		HTMLFormResultsPage results = htmlForm.clickSubmitButton();
		
		assertEquals("eviltester",results.getUserName());
		assertEquals("myPassword",results.getPassword());
		assertEquals("This is a simple comment", results.getComment());
		assertEquals("No Value for filename", results.getFilename());
		assertEquals("amended value", results.getHiddenFieldValue());
		
		assertEquals(3, results.getCheckboxCount());
		
		assertEquals("cb1", results.getCheckBoxValue(1));
		assertEquals("cb2", results.getCheckBoxValue(2));
		assertEquals("cb3", results.getCheckBoxValue(3));
		
		assertEquals("rd1", results.getRadioButtonValue());
		
		assertEquals(4, results.getMultiSelectCount());
		
		assertEquals("ms1", results.getMultiSelectValue(1));
		assertEquals("ms2", results.getMultiSelectValue(2));
		assertEquals("ms3", results.getMultiSelectValue(3));
		assertEquals("ms4", results.getMultiSelectValue(4));
		
		assertEquals("dd1", results.getDropDownValue());
		
		assertEquals("submit", results.getSubmitButton());
		
		assertEquals("Processed Form Details", results.getTitle());
		assertTrue(results.hasHeading());
		
	}	
	

	
	
	@Test
	public void navigationTests(){
				
		assertEquals("HTML Form Elements",htmlForm.getTitle());
		
		HTMLFormResultsPage results = htmlForm.clickSubmitButton();
		
		assertEquals("Processed Form Details", results.getTitle());
		
		htmlForm = results.goBackToForm();
	
		assertEquals("HTML Form Elements",htmlForm.getTitle());
		
	}
	
}
