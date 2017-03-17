package com.eviltester.seleniumSimplified.javascript;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.eviltester.seleniumSimplified.pageObjectModel.BasicAjaxPage;
import com.eviltester.seleniumSimplified.testClasses.SeleniumSimplifiedTest;

public class AjaxPageTests extends SeleniumSimplifiedTest{


	BasicAjaxPage ajaxPage;
	
	@Before
	public void setUp() throws Exception {
		ajaxPage = new BasicAjaxPage(selenium);
		ajaxPage.open();
	}
	
	@Test
	public void enterAnInvalidValueInBlurInput(){
		ajaxPage.typeInputValue("45");
		ajaxPage.selectCategory("Desktop");
		
		assertFalse(ajaxPage.hasShownValidationAlert());	
	}	
	
	@Test
	public void enterAnInvalidValueInBlurInputAndTriggerValidation(){	
		ajaxPage.typeInputValue("45");
		ajaxPage.triggerInputValidation();
		
		assertTrue(ajaxPage.hasShownValidationAlert());
		assertEquals("Enter a value less than 30", ajaxPage.getValidationAlertMessage());
	}
	
	@Test
	public void chooseACategoryAndCheckTheLanguage(){
		ajaxPage.selectCategory("Server");
		ajaxPage.waitForAjaxSymbolToGoAway();
		
		assertEquals("Cobol",ajaxPage.getLanguage());

		ajaxPage.selectCategory("Web");
		ajaxPage.waitForAjaxSymbolToGoAway();
		
		assertEquals("Javascript",ajaxPage.getLanguage());

		ajaxPage.selectCategory("Desktop");
		ajaxPage.waitForAjaxSymbolToGoAway();
		
		assertEquals("C++",ajaxPage.getLanguage());		
	}

	@Test(expected=org.junit.ComparisonFailure.class)
	public void chooseACategoryAndCheckTheLanguageThrowsExceptionDueToNoWait(){
		ajaxPage.selectCategory("Server");
		assertEquals("Cobol",ajaxPage.getLanguage());
		
		ajaxPage.selectCategory("Web");
		assertEquals("Javascript",ajaxPage.getLanguage());

		ajaxPage.selectCategory("Desktop");
		assertEquals("C++",ajaxPage.getLanguage());		
	}
	
}