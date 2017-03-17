package com.eviltester.seleniumtutorials.refactored;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumtutorials.refactored.pageObjectModel.BasicAjaxPage;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class JavaScript_With_Selenium_Tests {

	static SeleniumManager sm;
	static Selenium selenium;
	BasicAjaxPage basicAjax;
	
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
		basicAjax = new BasicAjaxPage(selenium);
		basicAjax.open();
	}
	
	@Test
	public void enterAnInvalidValueInBlurInput(){
		basicAjax.typeInputValue("45");
		basicAjax.selectCategory("Desktop");
		
		assertFalse(basicAjax.hasShownValidationAlert());	
	}	
	
	@Test
	public void enterAnInvalidValueInBlurInputAndTriggerValidation(){	
		basicAjax.typeInputValue("45");
		basicAjax.triggerInputValidation();
		
		assertTrue(basicAjax.hasShownValidationAlert());
		assertEquals("Enter a value less than 30", basicAjax.getValidationAlertMessage());
	}
	
	// Deleted tests:
	// 		showHTMLOfCombo1WithGetEval
	//		showValueOfInjectedJavaScriptFromRunScriptExpectedException
	//		showValueOfInjectedJavaScriptFromRunScript
	//		showValueOfInjectedJavaScriptFromAddScript
	// because these, illustrated points of how to use JavaScript but were
	// not 'tests' with the page object model so have no place in the 
	// refactored test set
	
}