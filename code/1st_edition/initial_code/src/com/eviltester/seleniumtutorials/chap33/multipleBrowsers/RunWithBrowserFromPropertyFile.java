package com.eviltester.seleniumtutorials.chap33.multipleBrowsers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class RunWithBrowserFromPropertyFile {

	static SeleniumServer server;
	static Selenium selenium;
	String currentBrowser;
	
	@Before	
	public void startServer() throws Exception{
		server = new SeleniumServer();
		server.start();
		selenium = new DefaultSelenium("localhost", server.getPort(), currentBrowser, "http://www.compendiumdev.co.uk");
		selenium.start();
	}
		
	// amend the seleniumManager to load in default values from the properties file
	
	public RunWithBrowserFromPropertyFile() throws FileNotFoundException, IOException{
		Properties props;
		props = new Properties();
		String filePath = /*System.getProperty("user.dir") + File.separator + */ "selenium.properties";
		props.load(new FileInputStream(filePath));
		
		this.currentBrowser = props.getProperty("browser","*firefox");
	}
	
	@Test
	public void openHomePageAndCheckForText(){
			selenium.open("/");
			assertTrue(selenium.isTextPresent("compendiumdev.co.uk"));
	}
	
	@After
	public void stopServer(){
		selenium.close();
		selenium.stop();
		server.stop();
	}

}
