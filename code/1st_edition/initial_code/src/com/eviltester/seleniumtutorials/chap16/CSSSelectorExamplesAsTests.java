package com.eviltester.seleniumtutorials.chap16;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManagerEnd;
import com.thoughtworks.selenium.Selenium;

/**
 * This class supports Chapter 16 : Basic CSS Theory
 * @author Alan
 *
 */
public class CSSSelectorExamplesAsTests {

	static SeleniumManagerEnd sManager = new SeleniumManagerEnd();
	static Selenium selenium;
	
	@BeforeClass
	public static void classSetup() throws IOException{
		sManager.start("http://www.compendiumdev.co.uk");
		selenium = sManager.getSelenium();
		selenium.open("http://www.compendiumdev.co.uk/selenium/basic_web_page.html");
	}
	
	@AfterClass
	public static void classTeardown(){
		sManager.stop();
		selenium = null;
	}

	@Test
	public void useCSSSelectorsToGetAttributes(){
		assertEquals("para1", selenium.getAttribute("css=p.main@id"));
	}

	@Test
	public void selections(){
		assertEquals("Basic Web Page Title",selenium.getText("css=head > title"));
		assertEquals("Basic Web Page Title",selenium.getText("css=html title"));
	}

	@Test
	public void attributeMatching(){
		assertEquals("A paragraph of text",selenium.getText("css=p[id='para1']"));
		assertEquals("A paragraph of text",selenium.getText("css=p[class='main']"));
		assertEquals("A paragraph of text",selenium.getText("css=p[id]"));
		assertEquals(2,getCSSCount("p[id]"));
		assertEquals(1,getCSSCount("p[id='para1']"));
	}

	@Test
	public void specialAttributeSelectors(){
		assertEquals("A paragraph of text",selenium.getText("css=p#para1"));
		assertEquals("A paragraph of text",selenium.getText("css=p.main"));
		assertEquals("A paragraph of text",selenium.getText("css=#para1"));
	}	

	@Test
	public void indexedMatching(){
		// according to http://selenium.googlecode.com/svn/selenium-core/trunk/src/main/resources/tests/TestCssLocators.html
		// -of-type have not been implemented
		//assertEquals("A paragraph of text",selenium.getText("css=* p:first-of-type"));
		//assertEquals("Another paragraph of text",selenium.getText("css=* p:last-of-type"));
		//assertEquals("Another paragraph of text",selenium.getText("css=* p:nth-of-type(2)"));
		//assertEquals("A paragraph of text",selenium.getText("css=* p:nth-last-of-type(2)"));
		
		/*
		first-of-type, matches the first element of a specific type
		p:first-of-type, matches the first paragraph
		last-of-type, matches the last element of a specific type
		p:last-of-type, matches the last paragraph
		nth-of-type(), matches the nth element of a specific type
		p:nth-of-type(2) selects the second matching element where the element type is 'p'
		nth-last-of-type(), matches  the nth last child, counting backwards 
		e.g. p:nth-last-of-type(2) would match the 2nd last p element
		A paragraph of text 
		*/
		
		assertEquals("A paragraph of text",selenium.getText("css=body *:first-child"));
		assertEquals("Another paragraph of text",selenium.getText("css=body *:last-child"));
		assertEquals("A paragraph of text",selenium.getText("css=body *:nth-child(1)"));
		assertEquals("A paragraph of text",selenium.getText("css=body p:nth-child(1)"));
		assertEquals("Another paragraph of text",selenium.getText("css=body *:nth-child(2)"));
		
		assertEquals("Another paragraph of text",selenium.getText("css=body p:nth-last-child(1)"));
		assertEquals("A paragraph of text",selenium.getText("css=body p:nth-last-child(2)"));
		
	}	
	
	@Test
	public void isElementPresentWithCSS(){
		assertTrue(selenium.isElementPresent("css=p[id='para1']"));
		assertEquals(1,getCSSCount("p[id='para1']"));
		assertFalse(selenium.isElementPresent("css=p[id='para3']"));
	}
	
	@Test
	public void attributeSubstringMatching(){
		assertEquals("A paragraph of text",selenium.getText("css=p[class^='ma']"));
		assertEquals("A paragraph of text",selenium.getText("css=p[class$='n']"));
		assertEquals("Another paragraph of text",selenium.getText("css=p[class*='u']"));
	}

	@Test
	public void booleanOperators(){
		assertEquals("A paragraph of text",selenium.getText("css=p[class='main'][id='para1']"));
		assertEquals("Another paragraph of text",selenium.getText("css=p:not([class='main'])[id^='para']"));
		assertEquals(0,getCSSCount("p:not([class='main'])[id^='para']:not([class='sub'])"));
	}	
	
	@Test
	public void siblingCombinators(){
		assertEquals("Another paragraph of text",selenium.getText("css=p + p"));
	}		
	
	@Test
	public void combinedMatches(){
		// The order of the items is not guaranteed
		// the asserts on text have been commented out because of this
		// try enabling them and see if they pass of fail in
		// different browsers
		//assertEquals("A paragraph of text",selenium.getText("css=p, title"));
		//assertEquals("Basic Web Page Title",selenium.getText("css=title, p"));
		assertEquals(3, getCSSCount("title, p"));
		assertEquals(3, getCSSCount("p, title"));
		assertEquals(1, getCSSCount("title"));
		assertEquals(2, getCSSCount("p"));
	}	
	
	@Test
	public void wildCardMatches(){
		assertEquals("Basic Web Page Title A paragraph of text\n Another paragraph of text",selenium.getText("css=*"));
		assertEquals(6, getCSSCount("*"));
		assertEquals("A paragraph of text",selenium.getText("css=body > *"));
		assertEquals(2, getCSSCount("body > *"));
	}	
	
	// based on http://www.ivaturi.org/home/addgetcsscountcommandtoselenium
	private int getCSSCount(String aCSSLocator){
		String jsScript = "var cssMatches = eval_css(\"%s\", window.document);cssMatches.length;";
		return Integer.parseInt(selenium.getEval(String.format(jsScript, aCSSLocator)));
	}
	
	@Test
	public void getAllParas(){
		assertEquals(2,getCSSCount("p"));
		
		// although we match multiple, the first one is returned
		assertEquals("A paragraph of text", selenium.getText("css=p"));
	}
	
	@Test
	public void someCounts(){
		assertEquals(2,getCSSCount("p"));
		assertEquals(6, getCSSCount("*"));
		assertEquals(2, getCSSCount("body > *"));
	}
	
}
