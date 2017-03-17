package com.eviltester.seleniumtutorials.chap20;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class JavaScript_With_Selenium_Tests {

	private static Selenium selenium;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", "http://compendiumdev.co.uk");
		selenium.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		selenium.close();
		selenium.stop();
	}
	
	@Before
	public void setUp() throws Exception {
		selenium.open("/selenium/basic_ajax.html");
	}
	
	@Test
	public void enterAnInvalidValueInBlurInput(){
		selenium.type("xpath=//input[@id='lteq30']", "45");
		selenium.select("combo1", "Desktop");
		assertFalse(selenium.isAlertPresent());	
	}	
	
	@Test
	public void enterAnInvalidValueInBlurInputAndTriggerValidation(){	
		selenium.type("xpath=//input[@id='lteq30']", "45");
		selenium.fireEvent("xpath=//input[@id='lteq30']", "blur");
		assertTrue(selenium.isAlertPresent());
		assertEquals("Enter a value less than 30", selenium.getAlert());
	}
	
	@Test
	public void showHTMLOfCombo1WithGetEval(){
		String javascript="";
		javascript = "window.document.getElementById('combo1').innerHTML;";
		String theInnerHTML = selenium.getEval(javascript);
		System.out.println(theInnerHTML);			
	}	
	
	// a suspected bug with Selenium 2.0.0
	// http://code.google.com/p/selenium/issues/detail?id=2121
	@Test(expected=SeleniumException.class)
	public void showHTMLOfFormWithGetEvalAndAssignId(){
		
		selenium.assignId("xpath//form", "form1");
		
		String javascript="";
		javascript = "window.document.getElementById('form1').innerHTML;";
		String theInnerHTML = selenium.getEval(javascript);
		System.out.println(theInnerHTML);			
	}	

	@Test
	public void showHTMLOfFormWithGetEvalAndAssignIdAlternative(){

		// using JavaScript instead of AssignId
		String result = selenium.getEval("window.document.forms[0].setAttribute('id','form1');");
		
		String javascript="";
		javascript = "window.document.getElementById('form1').innerHTML;";
		String theInnerHTML = selenium.getEval(javascript);
		System.out.println(theInnerHTML);			
	}	
	
	@Test(expected=com.thoughtworks.selenium.SeleniumException.class)
	public void showValueOfInjectedJavaScriptFromRunScriptExpectedException(){
			
		// using runScript the function does not persist
		selenium.runScript("function EvilTester(){}; EvilTester.returnBob = function(){return 'bob';}; alert(EvilTester.returnBob());");
		assertTrue(selenium.isAlertPresent());
		assertEquals("bob",selenium.getAlert());
		selenium.getEval("EvilTester.returnBob();");
	}	
	
	@Test
	public void showValueOfInjectedJavaScriptFromRunScript(){
			
		// using runScript the function does not persist
		selenium.runScript("function EvilTester(){}; EvilTester.returnBob = function(){return 'bob';}; alert(EvilTester.returnBob());");
		assertTrue(selenium.isAlertPresent());
		assertEquals("bob",selenium.getAlert());
		try{
			selenium.getEval("EvilTester.returnBob();");
		}catch(Exception e){
			assertEquals(e.getMessage(),"ERROR: Threw an exception: EvilTester is not defined");	
		}
	}
	
	@Test
	public void showValueOfInjectedJavaScriptFromAddScript(){
		// using addScript the function persists for future use

		selenium.addScript(
		"function EvilTester(){}; EvilTester.returnBob = function(){return 'bob';};",
		"");

		assertEquals("bob",selenium.getEval("EvilTester.returnBob();"));	
			
		
	}
		
}