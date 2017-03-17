package com.eviltester.seleniumtutorials.chap15;


import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManagerEnd;
import com.thoughtworks.selenium.Selenium;

public class XpathExamplesAsTests {

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
	public void getAllParaElementsWithRelativePathSelection(){
		assertEquals(2,selenium.getXpathCount("//p"));
	}
	
	@Test
	public void paraNodeAbsolutePathSelection(){
		assertEquals(2,selenium.getXpathCount("/html/body/p"));
	}
	
	@Test
	public void attributeRelativePathSelection(){
		assertEquals(2,selenium.getXpathCount("//@id"));
	}
	
	@Test
	public void predicateExamples(){
		assertEquals("Another paragraph of text",selenium.getText("//p[2]"));
		
		assertEquals("A paragraph of text",selenium.getText("//p[1]"));
		
		assertEquals("A paragraph of text",selenium.getText("//p[@id='para1']"));
		
		assertEquals("Another paragraph of text",selenium.getText("//p[last()]"));
		
		assertEquals("Another paragraph of text",selenium.getText("//p[position()>1]"));
		
		assertEquals("A paragraph of text",selenium.getText("//p[position()>0]"));
		
		assertEquals("A paragraph of text", selenium.getText("//p[@class='main']"));
		
		assertEquals("Another paragraph of text", selenium.getText("//p[@class='sub']"));
	}
	
	@Test
	public void combiningXpathMatches(){
		assertEquals(3,selenium.getXpathCount("//p | //head"));
	}
	
	@Test
	public void wildcardMatches(){
		assertEquals(16,selenium.getXpathCount("//node()"));
		assertEquals(5,selenium.getXpathCount("//body/node()"));
		assertEquals(1,selenium.getXpathCount("//node()[@id='para1']"));
		
		assertEquals(1,selenium.getXpathCount("//p[@*='para1']"));
		
		assertEquals(2,selenium.getXpathCount("//*[@*]"));
		assertEquals(2,selenium.getXpathCount("//*[@id]"));
		assertEquals(2,selenium.getXpathCount("/html/*"));
	}
	
	@Test
	public void booleanOperators(){
		assertEquals("Another paragraph of text",selenium.getText("//p[starts-with(@id,'para') and contains(.,'Another')]"));
		assertEquals(2,selenium.getXpathCount("//*[@id='para1' or @id='para2']"));
	}
	
	@Test
	public void xpathFunctionsContains(){
		assertEquals(2,selenium.getXpathCount("//p[contains(.,'text')]"));

		assertEquals(1,selenium.getXpathCount("//p[contains(.,'Another')]"));
		assertEquals("Another paragraph of text",selenium.getText("//p[contains(.,'Another')]"));
		
		assertEquals(1,selenium.getXpathCount("//p[contains(@id,'1')]"));
		assertEquals("A paragraph of text",selenium.getText("//p[contains(@id,'1')]"));
	}
	
	@Test
	public void xpathFunctionsStartsWith(){
		assertEquals(1,selenium.getXpathCount("//*[starts-with(.,'Basic')]"));
		assertEquals("Basic Web Page Title",selenium.getText("//*[starts-with(.,'Basic')]"));
		
		assertEquals(2,selenium.getXpathCount("//*[starts-with(@id,'p')]"));
	}
	
	public void optimisedXPath(){
		String xpathToMatch;
		
		xpathToMatch = "//*[@id='para2']";
		assertEquals(1,selenium.getXpathCount(xpathToMatch));
		assertEquals("A paragraph of text",selenium.getText(xpathToMatch));

		xpathToMatch = "//p[@id='para2']";
		assertEquals(1,selenium.getXpathCount(xpathToMatch));
		assertEquals("A paragraph of text",selenium.getText(xpathToMatch));		
		
	}

	@Test
	public void aGetAttributeExample(){
		assertEquals("para2", selenium.getAttribute("xpath=//p[2]@id"));
		assertEquals("para2", selenium.getAttribute("//p[2]@id"));
	}

	
	@Test
	public void xpathInAnIsElementPresent(){
		assertTrue(selenium.isElementPresent("xpath=//p[@id='para1']"));
	}
	
	@Test
	public void xpathLocatorWithoutXPathEquals(){
		assertTrue(selenium.isElementPresent("//p[@id='para1']"));
	}
	
	@Test
	public void doesNotTestForAnything(){
		selenium.getXpathCount("//p"); // return a count of the <p> elements
	}
	
	
	
}
