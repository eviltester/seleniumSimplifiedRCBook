package com.eviltester.seleniumtutorials.chap18;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class HTML_form_tests {

	private static Selenium selenium;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", "http://www.eviltester.com");
		selenium.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		selenium.close();
		selenium.stop();
	}
	
	@Before
	public void setUp() throws Exception {
		selenium.open("/selenium/basic_html_form.html");
	}

	@Test
	public void test_submit_form_with_default_values(){	
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}
	
	@Test
	public void test_submit_form_without_clicking_submit(){
		selenium.submit("//form[@id='HTMLFormElements']");
		selenium.waitForPageToLoad("30000");
	}
	
	@Test
	public void test_submit_form_with_new_username(){
		selenium.type("username","eviltester");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_submit_form_with_new_password(){
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test	
	public void test_submit_form_with_html_escaped_text(){
		selenium.type("comments", "\\\n\t\"I said, give me your pound sign\"\u00A3"); 
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_submit_form_with_filename(){
		selenium.type("filename", "C:\\Selenium\\readme.txt");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_submit_form_with_click_check_and_radio(){
		selenium.click("//input[@name='checkboxes[]' and @value='cb2']");
		selenium.click("//input[@name='checkboxes[]' and @value='cb3']");
		selenium.click("//input[@name='radioval' and @value='rd3']");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}
	
	@Test
	public void test_submit_form_with_multiple_select_values(){
		selenium.removeSelection("multipleselect[]", "label=Selection Item 3");
		selenium.addSelection("multipleselect[]", "label=Selection Item 4");
		selenium.addSelection("multipleselect[]", "label=Selection Item 1");
		selenium.removeSelection("multipleselect[]", "label=Selection Item 4");
		selenium.addSelection("multipleselect[]", "value=ms2");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_submit_form_with_dropdown_values(){
		// by default 3 is selected, so select item 1
		selenium.select("dropdown", "label=Drop Down Item 1");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_submit_form_with_hidden_field(){
		selenium.getEval(
		"this.browserbot.findElement(\"name=hiddenField\").value=\"amended value\"");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
	}	
	
	@Test
	public void test_check_text_entered_values(){
		selenium.type("username","eviltester");
		selenium.type("filename", "C:\\Selenium\\readme.txt");
		selenium.type("password","myPassword");
		selenium.type("comments", "simple text");

		assertEquals("username not as entered",
				"eviltester",selenium.getValue("username"));
		assertEquals("password not as entered",
				"myPassword",selenium.getValue("password"));
		assertEquals("filename not as entered",
				"C:\\Selenium\\readme.txt",selenium.getValue("filename"));
		assertEquals("comments not as entered",
				"simple text",selenium.getValue("comments"));
	}	
	
	@Test
	public void test_check_checkbox_values(){

		assertEquals("by default checkbox 1 is not selected", "off", 
			selenium.getValue("//input[@name='checkboxes[]' and @value='cb1']"));
		assertEquals("by default checkbox 2 is not selected", "off", 
			selenium.getValue("//input[@name='checkboxes[]' and @value='cb2']"));
		assertEquals("by default checkbox 3 is selected", "on", 
			selenium.getValue("//input[@name='checkboxes[]' and @value='cb3']"));

		selenium.check("//input[@name='checkboxes[]' and @value='cb2']");
		selenium.uncheck("//input[@name='checkboxes[]' and @value='cb3']");

		assertFalse("checkbox 1 is still not selected",  
			selenium.isChecked("//input[@name='checkboxes[]' and @value='cb1']"));
		assertTrue("checkbox 2 is now selected",  
			selenium.isChecked("//input[@name='checkboxes[]' and @value='cb2']"));
		assertFalse("default checkbox 3 is no longer selected",  
			selenium.isChecked("//input[@name='checkboxes[]' and @value='cb3']"));
	}

	@Test
	public void test_check_radio_values(){

		assertEquals("by default radio 1 is not selected", "off", 
			selenium.getValue("//input[@name='radioval' and @value='rd1']"));
		assertEquals("by default radio 2 is selected", "on", 
			selenium.getValue("//input[@name='radioval' and @value='rd2']"));
		assertEquals("by default radio 3 is not selected", "off", 
			selenium.getValue("//input[@name='radioval' and @value='rd3']"));

		selenium.check("//input[@name='radioval' and @value='rd1']");

		assertTrue("radio 1 is now selected",  
			selenium.isChecked("//input[@name='radioval' and @value='rd1']"));
		assertFalse("radio 2 is no longer selected",  
			selenium.isChecked("//input[@name='radioval' and @value='rd2']"));
		assertFalse("radio 3 is not selected",  
			selenium.isChecked("//input[@name='radioval' and @value='rd3']"));
	}	
	
	@Test
	public void test_randomly_select_value_from_dropdown(){

		// get all options in the dropdown
		String dropDownOptions[] = selenium.getSelectOptions("dropdown");

		// get the index of the currently selected item in the dropdown
		String currentDropDownItem = selenium.getSelectedIndex("dropdown");
		int previouslySelectedIndex =Integer.valueOf(currentDropDownItem);

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

		// select this new one
		selenium.select("dropdown", "label=" + dropDownOptions[dropDownIndex]);

		// check that something is selected
		assertTrue(selenium.isSomethingSelected("dropdown"));

		// check that what we wanted is selected
		assertEquals(dropDownIndex,
				Integer.valueOf(selenium.getSelectedIndex("dropdown")).intValue());

		//double check we didn't select the same thing
		assertTrue(dropDownIndex!=previouslySelectedIndex);
	}	
}
