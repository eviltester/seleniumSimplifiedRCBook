package com.eviltester.bookSanity;


import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class HelperToolsTests {

	static SeleniumManager sManager = new SeleniumManager();
	static Selenium selenium;
	
	
	@After
	public void teardown(){
		
		sManager.stop();
		selenium = null;
	}
	
	public void startUpAndIncreaseTimeOutFor(String mainURL) throws IOException{
		sManager.start(mainURL);
		selenium = sManager.getSelenium();
		selenium.setTimeout("120000");
	}
	
	@Test
	/**
	 * This test checks that cmdhere.exe still exists on powertoys page
	 * If this test failss then we need to update:
	 * - Step Two - Run Selenium-RC 
	 */
	public void checkCmdHereStillExists() throws IOException{	
		startUpAndIncreaseTimeOutFor("http://windows.microsoft.com");

		selenium.open("http://windows.microsoft.com/en-US/windows/downloads/windows-xp");
		String powerToysTabID = "ctl00_FormlessContentPlaceholder_ContentPlaceholder_B03_ctl00_PT";
		selenium.waitForCondition("window.document.getElementById('" + powerToysTabID + "');", "5000");
		selenium.click(powerToysTabID);
		selenium.waitForCondition("window.document.getElementById('ID0EDDLBBBBA');","5000");
		// find the cmdHere.exe link
		assertTrue(selenium.getXpathCount("//a[@href='http://go.microsoft.com/fwlink/?LinkId=211471']").intValue()>0);
		
	}
	
	@Test
	/**
	 * This test checks that the Console tool still exists
	 * If this test fails then we need to update:
	 * - Step Two - Run Selenium-RC 
	 * and the relevant appendix
	 */
	public void checkConsoleToolStillExists() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.sourceforge.net");
		selenium.open("http://www.sourceforge.net/projects/console/");
		assertEquals("Console | Download Console software for free at SourceForge.net", selenium.getTitle());		
	}	
	
	
	@Test
	/**
	 * This test checks that the IDE still exists at this url
	 * If this test fails then we need to update:
	 * - Chapter 3 : Selenium IDE Basics
	 * 
	 * If the version changes then we may need to update the screenshots in the IDE sections
	 * - Chapter 3: Selenium IDE Basics
	 */
	public void checkSeleniumIDEStillExists() throws IOException{
		// 28/03/10 IDE moved to hq - was using https://addons.mozilla.org/en-US/firefox/addon/2079
		// but this no longer keeps up to date
		startUpAndIncreaseTimeOutFor("http://seleniumhq.org");
		
		selenium.open("http://seleniumhq.org/download/");
		assertEquals("Downloads", selenium.getTitle());
		assertEquals(1,selenium.getXpathCount("//a[@href='http://release.seleniumhq.org/selenium-ide/1.1.0/selenium-ide-1.1.0.xpi']").intValue());
	}		
	
	@Test
	/**
	 * This test checks that the IDE still exists at this url
	 * If this test fails then we need to update:
	 * - Chapter 5 : The Eclipse IDE
	 * 
	 */
	public void checkEclipseIDEStillExistsWithDownloads() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.eclipse.org/");
		
		selenium.open("http://www.eclipse.org/");
		assertTrue(selenium.isElementPresent("xpath=//a[@id='downloadButton']"));
		selenium.open("http://www.eclipse.org/downloads");
		String eclipseIDEForJavaXPath ="xpath=//a[starts-with(.,'Eclipse IDE for Java Developers')]";
		assertTrue(selenium.isElementPresent(eclipseIDEForJavaXPath));
		selenium.click(eclipseIDEForJavaXPath);
		selenium.waitForPageToLoad("30000");
		assertEquals("Eclipse IDE for Java Developers | Eclipse Packages", selenium.getTitle());
	}	
	
	@Test
	/**
	 * This test checks that JUnit still resides at junit.org
	 * If this test fails then we need to update:
	 * - "A little about Junit" search for junit.org
	 * 
	 */
	public void checkJUnitORgStillExists() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.junit.org");
		
		selenium.open("http://www.junit.org");
		assertTrue(selenium.isElementPresent("xpath=//a[@title='JUnit.org Resources for Test Driven Development']"));
		
		// check download JUnit link still exists, otherwise have to amend the chapter on
		// installing Ant
		assertTrue(selenium.isElementPresent("css=a[href='http://github.com/KentBeck/junit/downloads']"));
		assertEquals("Download JUnit",selenium.getText("css=a[href='http://github.com/KentBeck/junit/downloads']"));
		
	}		
	
	/**
	 * These tests checks links at end of Chapter 8
	 * 
	 */
	@Test
	public void checkJavaBasics() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.perfectknowledgedb.com");
		
		selenium.open("http://www.perfectknowledgedb.com/Tutorials/H2R/main.htm");
		assertTrue(selenium.isTextPresent("Java basics - A basic introduction to some of the concepts of the Java programming language."));
	}	
	
	@Test
	public void checkOfficialSunDocumentation() throws IOException{
		startUpAndIncreaseTimeOutFor("http://java.sun.com");
		
		selenium.open("http://java.sun.com/docs/books/tutorial/java/index.html");
		assertEquals("Trail: Learning the Java Language (The Java™ Tutorials)", selenium.getTitle());

		selenium.open("http://java.sun.com/docs/books/tutorial/java/package/index.html");
		assertEquals("Lesson: Packages (The Java™ Tutorials > Learning the Java Language)", selenium.getTitle());
		
		selenium.open("http://java.sun.com/docs/books/tutorial/java/package/usepkgs.html");
		assertEquals("Using Package Members (The Java™ Tutorials > Learning the Java Language > Packages)", selenium.getTitle());
		
		selenium.open("http://java.sun.com/docs/books/tutorial/java/javaOO/index.html");
		assertEquals("Lesson: Classes and Objects (The Java™ Tutorials > Learning the Java Language)", selenium.getTitle());

		selenium.open("http://java.sun.com/docs/books/tutorial/java/concepts/inheritance.html");
		assertEquals("What Is Inheritance? (The Java™ Tutorials > Learning the Java Language > Object-Oriented Programming Concepts)", selenium.getTitle());
		
		selenium.open("http://java.sun.com/docs/books/tutorial/essential/exceptions/");
		assertEquals("Lesson: Exceptions (The Java™ Tutorials > Essential Classes)", selenium.getTitle());

		selenium.open("http://java.sun.com/docs/books/tutorial/");
		assertEquals("The Java™ Tutorials", selenium.getTitle());
		
		selenium.open("http://java.sun.com/docs/books/tutorial/java/javaOO/accesscontrol.html");
		assertEquals("Controlling Access to Members of a Class (The Java™ Tutorials > Learning the Java Language > Classes and Objects)", selenium.getTitle());

		selenium.open("http://java.sun.com/docs/books/tutorial/java/javaOO/classvars.html");
		assertEquals("Understanding Instance and Class Members (The Java™ Tutorials > Learning the Java Language > Classes and Objects)", selenium.getTitle());	
		
	}		

	@Test
	public void checkThinkingInJava() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.mindview.net");
		
		selenium.open("http://www.mindview.net/Books/TIJ/");
		
		assertTrue(selenium.isTextPresent("Free Electronic Book: Thinking in Java, 3rd Edition"));
	}		
	
	@Test
	public void checkJavaFaq() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.javafaq.nu/");
		
		selenium.open("http://www.javafaq.nu/");
		assertEquals("Easy Learn Java: Programming Articles, Examples and Tips", selenium.getTitle());
	}	
	
	@Test
	public void checkaboutJava() throws IOException{
		startUpAndIncreaseTimeOutFor("http://java.about.com/");
		
		selenium.open("http://java.about.com/");
		assertEquals("Learn Java – Tutorials, Tips, Help, and Resources for Learning Java", selenium.getTitle());
	}	
	
	
	
	@Test
	public void checkJavaLessons() throws IOException{
		startUpAndIncreaseTimeOutFor("http://javalessons.com");
		
		selenium.open("http://javalessons.com");
		assertEquals("Learn Java, Tutorials Based On Examples", selenium.getTitle());
	}	
	
	@Test
	public void checkJavaCoffeeBreak() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.javacoffeebreak.com/");
		
		selenium.open("http://www.javacoffeebreak.com/");
		assertEquals("Java Coffee Break - your free guide to the world of Java programming, packed full of free articles, tutorials, book reviews, and FAQs", selenium.getTitle());
	}		


	
	// check these urls for Chapter 15

	@Test
	public void checkw3Schools() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.w3schools.com");
		
		selenium.open("http://www.w3schools.com/XPath/xpath_functions.asp");
		assertEquals("XPath, XQuery, and XSLT Function Reference", selenium.getTitle());
	}		

	@Test
	public void checkMicrosoftXPathReference() throws IOException{
		startUpAndIncreaseTimeOutFor("http://msdn.microsoft.com");
		
		selenium.open("http://msdn.microsoft.com/en-us/library/ms256115.aspx");
		assertEquals("XPath Reference", selenium.getTitle());
	}	
	
	@Test
	public void checkXMLPitstop() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.xmlpitstop.com");
		
		selenium.open("http://www.xmlpitstop.com/ListTutorials/DispContentType/XPath/PageNumber/1.aspx");
		assertEquals("XML Pitstop : Largest Source of XML Examples on the Web", selenium.getTitle());
		assertTrue(selenium.isElementPresent("//span[@id='CtrlLinksDetail1_lblHeading' and .='xpath Tutorials']"));
	}	
	
	// Appendix - Java Hints and Tips
	@Test
	public void checkJavaDocsAgain() throws IOException{
		startUpAndIncreaseTimeOutFor("http://java.sun.com");
		
		selenium.open("http://java.sun.com/j2se/1.4.2/docs/api/java/util/ArrayList.html");
		assertEquals("ArrayList (Java 2 Platform SE v1.4.2)", selenium.getTitle());
		
		selenium.open("http://java.sun.com/docs/books/tutorial/java/data/strings.html");
		assertEquals("Strings (The Java™ Tutorials > Learning the Java Language > Numbers and Strings)", selenium.getTitle());
		
		selenium.open("http://java.sun.com/j2se/1.4.2/docs/api/java/util/HashMap.html");
		assertEquals("HashMap (Java 2 Platform SE v1.4.2)", selenium.getTitle());
		
	}	

	@Test
	public void checkJavaTips() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.java-tips.org");
		
		selenium.open("http://www.java-tips.org/java-se-tips/java.util/how-to-use-of-hashmap.html");
		assertEquals("Java Tips - How to use of HashMap", selenium.getTitle());
		
		selenium.open("http://www.java-tips.org/java-se-tips/java.lang/use-of-arraylist-class.html");
		assertEquals("Java Tips - Use of ArrayList Class", selenium.getTitle());
		
	}	
	
	 
	@Test
	public void check_dom4J() throws IOException{
		startUpAndIncreaseTimeOutFor("http://dom4j.sourceforge.net");
		
		selenium.open("http://dom4j.sourceforge.net");

		assertTrue(selenium.isElementPresent("xpath=//a[@name='Welcome_to_dom4j_2.0']"));
		assertTrue(selenium.isElementPresent("xpath=//h2[.='Welcome to dom4j 2.0!']"));
		assertTrue(selenium.isTextPresent("dom4j is an easy to use, open source library for working with XML, XPath and XSLT on the Java platform using the Java Collections Framework and with full support for DOM, SAX and JAXP"));
	}	
	
	
	@Test
	public void check_Ant() throws IOException{
		startUpAndIncreaseTimeOutFor("http://ant.apache.org");

		selenium.open("http://ant.apache.org");
		assertEquals("Apache Ant - Welcome", selenium.getTitle());		
		
		//check download page still exists
		selenium.open("http://ant.apache.org/bindownload.cgi");
		assertEquals("Apache Ant - Binary Distributions", selenium.getTitle());
		
		// Check the documentation pages exist
		// referenced at end of ant chapter
		selenium.open("http://ant.apache.org/manual");
		selenium.open("http://ant.apache.org/manual/CoreTasks/property.html");			
		selenium.open("http://ant.apache.org/manual/CoreTasks/antcall.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/delete.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/javac.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/get.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/mkdir.html");
		selenium.open("http://ant.apache.org/manual/OptionalTasks/junit.html");
		selenium.open("http://ant.apache.org/manual/OptionalTasks/junitreport.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/fail.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/waitfor.html");
		selenium.open("http://ant.apache.org/manual/CoreTasks/echo.html");
		

	}		
	
	// http://java.sun.com/developer/Books/javaprogramming/ant/
	@Test
	public void check_JavaProgrammingAnt() throws IOException{
		startUpAndIncreaseTimeOutFor("http://java.sun.com");

		selenium.open("http://java.sun.com/developer/Books/javaprogramming/ant/");
	}
	
	//  http://en.wikibooks.org/wiki/Apache_Ant
	@Test
	public void check_WikiBookesAnt() throws IOException{
		startUpAndIncreaseTimeOutFor("http://en.wikibooks.org");
		
		selenium.open("http://en.wikibooks.org/wiki/Apache_Ant");
	}

	//  http://www.vogella.de/articles/ApacheAnt/ar01s04.html 
	@Test
	public void check_vogella_de() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.vogella.de/");
		
		selenium.open("http://www.vogella.de/articles/ApacheAnt/ar01s04.html");
	}	
	
	//  http://pub.admc.com/howtos/junit4x/ant-chapt.html 
	@Test
	public void check_junit4_ant_chap() throws IOException{
		startUpAndIncreaseTimeOutFor("http://pub.admc.com");
		
		selenium.open("http://pub.admc.com/howtos/junit4x/ant-chapt.html");
	}
	
	// http://wiki.openqa.org/display/SRC/Selenium-RC+and+Continuous+Integration
	@Test
	public void check_openqa_wiki() throws IOException{
		startUpAndIncreaseTimeOutFor("http://wiki.openqa.org");
		
		selenium.open("http://wiki.openqa.org/display/SRC/Selenium-RC+and+Continuous+Integration");
	}
	
	
	//http://java.sun.com/javase/downloads/index.jsp
	@Test
	public void check_JavaJDK() throws IOException{
		startUpAndIncreaseTimeOutFor("http://www.oracle.com");
		
		selenium.open("http://www.oracle.com/technetwork/java/javase/downloads/index.html");
		assertEquals("Java SE Downloads", selenium.getTitle());
		
				
		
	}
	
	
	/**
	 * This test checks that Firefinder still exists at this url
	 * If this test fails then we need to update:
	 * - Chapter 16 : Basic CSS Selector Theory
	 */
	
	
		@Test
		public void check_Firefinder() throws IOException{
			startUpAndIncreaseTimeOutFor("https://addons.mozilla.org");
			
			selenium.open("https://addons.mozilla.org/en-US/firefox/addon/11905");
			assertEquals("Firefinder for Firebug :: Add-ons for Firefox", selenium.getTitle());
		}	
}
