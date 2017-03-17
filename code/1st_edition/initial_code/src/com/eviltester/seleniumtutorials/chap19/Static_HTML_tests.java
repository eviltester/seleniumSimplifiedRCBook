package com.eviltester.seleniumtutorials.chap19;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class Static_HTML_tests {

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
	public void submit_form_with_values_and_check_static_html(){
		selenium.type("username","eviltester");
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		
		// check the title on the page
		assertEquals("Processed Form Details",selenium.getTitle());
		
		// check the username entered
		assertEquals( "eviltester",selenium.getText("_valueusername"));
		
		// check the submitted values text is on the page
		assertTrue(selenium.isTextPresent("Submitted Values"));
		
		//isElementPresent to check comments and filename
		assertTrue(selenium.isElementPresent("_comments"));
		assertFalse(selenium.isElementPresent("_filename"));
		
		// use getXPathCount to check the number of checkboxes
		assertEquals(1,selenium.getXpathCount("//div[@id='_checkboxes']/ul/li").intValue());
		
		// click to go back to the form
		selenium.click("back_to_form");
		selenium.waitForPageToLoad("30000");
		
		// check the page title with getTitle
		assertEquals("HTML Form Elements",selenium.getTitle());
		
		//add a filename
		selenium.type("filename", "C:\\Selenium\\readme.txt");
		
		//check all the checkboxes
		selenium.check("//input[@name='checkboxes[]' and @value='cb1']");
		selenium.check("//input[@name='checkboxes[]' and @value='cb2']");
		selenium.check("//input[@name='checkboxes[]' and @value='cb3']");
		
		// submit the form
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		
		//check filename is present
		assertTrue(selenium.isElementPresent("_filename"));
		assertEquals("readme.txt",selenium.getText("_valuefilename"));
		
		//check the checkbox count
		assertEquals(3,
				selenium.getXpathCount("//div[@id='_checkboxes']/ul/li").intValue());
	}	

	@Test
	public void check_static_html_password_name(){
		selenium.type("username","eviltester");
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		
		// check the name of the password display paragraph is _password 
		assertEquals("_password",
				selenium.getAttribute("xpath=//div[@id='_password']/p@name"));
	}
	
	@Test
	public void useAssignIDToCheckFilenameBlank(){
		selenium.type("username","eviltester");
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		
		selenium.assignId("xpath=//body/p[2]/strong", "_filename");
		assertEquals("No Value for filename",selenium.getText("_filename"));
	}	
	
	@Test
	public void getText_equals_getTitle(){
		assertEquals("HTML Form Elements",selenium.getTitle());
		assertEquals(selenium.getTitle(),selenium.getText("//head/title"));		
	}	
	
	@Test
	public void getBodyText_getHtmlSource(){
		selenium.open("/selenium/basic_html_form.html");
		selenium.type("username","eviltester");
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");
		
		System.out.println(selenium.getBodyText());
		System.out.println("-----");
		System.out.println(selenium.getHtmlSource());
	}	
	
	@Test
	public void submit_form_with_values_and_go_back(){
		// check the title on the form page
		assertEquals("HTML Form Elements",selenium.getTitle());			

		selenium.type("username","eviltester");
		selenium.type("password","myPassword");
		selenium.click("//input[@name='submitbutton' and @value='submit']");
		selenium.waitForPageToLoad("30000");		
		// check the title on the detail page
		assertEquals("Processed Form Details",selenium.getTitle());
		selenium.goBack();
		//check title for the form page again
		assertEquals("HTML Form Elements",selenium.getTitle());				
	}	
	
}
