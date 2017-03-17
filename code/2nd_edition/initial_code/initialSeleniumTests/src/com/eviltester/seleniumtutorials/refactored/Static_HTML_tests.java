package com.eviltester.seleniumtutorials.refactored;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.eviltester.seleniumtutorials.refactored.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumtutorials.refactored.pageObjectModel.HTMLFormResultsPage;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

/** based on chap19 Static_HTML_tests.java
 * use selenium Manager
 * use the page object
 */

public class Static_HTML_tests {

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
	
	HTMLFormResultsPage resultsPage;
	
	private void submitFormWithUserNameAndPassword() {
		htmlForm.typeUserName("eviltester");
		htmlForm.typePassword("myPassword");
		resultsPage = htmlForm.clickSubmitButton();
	}
	
	@Test
	public void submit_form_with_values_and_check_static_html(){
		submitFormWithUserNameAndPassword();
		
		assertEquals("Processed Form Details",resultsPage.getTitle());
		assertEquals( "eviltester",resultsPage.getUserName());
		
		assertTrue(resultsPage.hasHeading());	
		assertTrue(resultsPage.hasComments());
		assertFalse(resultsPage.hasFilename());
		assertEquals(1,resultsPage.getCheckboxCount());
		
		htmlForm = resultsPage.goBackToForm();
	
		assertEquals("HTML Form Elements",htmlForm.getTitle());
		
		// file name handling unreliable
		//htmlForm.typeFilename("C:\\Selenium\\readme.txt");
		htmlForm.setChecked_CheckBox("1");
		htmlForm.setChecked_CheckBox("2");
		htmlForm.setChecked_CheckBox("3");
		resultsPage = htmlForm.clickSubmitButton();
	
		// file name handling unreliable
		//assertTrue(resultsPage.hasFilename());
		
		// This will fail on IE, and GoogleChrome as they do 
		// not let you type text into a File box
		// filename handling unreliable
		//assertEquals("readme.txt",resultsPage.getFilename());
		
		assertEquals(3,resultsPage.getCheckboxCount());
	}	

/*
 * This test adds no value in the refactored world
 * using the page objects, we don't really care what the field is 'called'
 * or if we did we could just check that hasPassword works
 */
 	@Test
	public void check_password_field_exists(){
		submitFormWithUserNameAndPassword();
		assertTrue("Does not have a password field",resultsPage.hasPassword());
	}

	@Test
	public void useAssignIDToCheckFilenameBlank(){
		submitFormWithUserNameAndPassword();
		assertEquals("No Value for filename",resultsPage.getFilename());
	}	
	
	@Test
	public void checkPageTitleAsExpected(){
		assertEquals("HTML Form Elements",htmlForm.getTitle());	
	}

}
