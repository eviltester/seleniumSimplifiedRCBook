package com.eviltester.seleniumtutorials.chap34.javascript;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumtutorials.chap34.javascript.BasicAjaxPage;
import com.eviltester.seleniumtutorials.refactored.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class MoreJavaScriptTests {


		
		static SeleniumManager sm;
		static Selenium selenium;
		BasicAjaxPage ajaxPage;
		
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
			ajaxPage = new BasicAjaxPage(selenium);
			ajaxPage.open();
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
