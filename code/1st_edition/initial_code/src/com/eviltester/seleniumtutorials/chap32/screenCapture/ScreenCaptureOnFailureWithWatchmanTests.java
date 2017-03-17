package com.eviltester.seleniumtutorials.chap32.screenCapture;


import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

// for the @Rule annotation
import org.junit.rules.MethodRule;
import org.junit.Rule;
import org.junit.rules.TestWatchman;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.model.FrameworkMethod;


@RunWith(Parameterized.class)
public class ScreenCaptureOnFailureWithWatchmanTests {

	static SeleniumServer server;
	static Selenium selenium;
	static String screenShotsFolder;
	String currentBrowser;
	
	final String WRONG_TITLE = 
			"WRONG_TITLESoftware Testing Essays, Book Reviews and Information";

	@BeforeClass	
	public static void startServer() throws Exception{
		server = new SeleniumServer();
		server.start();
		screenShotsFolder = createNowfolder();
	}
		
	@Rule
	public MethodRule watchman= new TestWatchman() {

		@Override
		public void failed(Throwable e, FrameworkMethod method) {
			captureScreenShots(screenShotsFolder, currentBrowser,method.getName() + "_" + e.getClass().getSimpleName());
			stopSelenium();
		}

		@Override
		public void succeeded(FrameworkMethod method) {
			stopSelenium();
		}
	};

	public ScreenCaptureOnFailureWithWatchmanTests(String currentBrowser){
		this.currentBrowser = currentBrowser;
	}

	@Parameters
	public static Collection testData(){
		return Arrays.asList(new Object[][]{{"firefox"},{"chrome"},{"iexplore"},{"googlechrome"}});
		//{"iexploreproxy"}, 	add this into the mix on most machines - people have occasional problems
		// 						with this depending on their local environment setup
	}

	@Test
	public void captureScreenshots(){
			startServerAndOpenRoot(currentBrowser);
			assertEquals(WRONG_TITLE,selenium.getTitle());
	}

	private void captureScreenShots(String screenShotsFolder, String browser, String fileNameAppend) {
		// since captureScreenshot takes the screen, we should maximise the browser
		// before we take the screenshot
		selenium.windowMaximize();
		
		// give the browser a chance to maximise properly
		try { Thread.sleep(1000);	} catch (Exception e) {}
			
		String filename = 	screenShotsFolder + browser + "_" +
							fileNameAppend + "_screenshot.png";
		selenium.captureScreenshot(filename);

		try{ selenium.captureEntirePageScreenshot(
				filename.replace(".png","full.png"),"");   }catch(Exception e){}
		
	}

	private void startServerAndOpenRoot(String browser) {
		selenium = new DefaultSelenium("localhost", server.getPort(), "*" + browser, "http://www.compendiumdev.co.uk");
		selenium.start();
		selenium.open("/");
	}

	static private String createNowfolder() {
		// setup the directory structure to create screenshots into
		String screenShotsFolder = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator;
		SimpleDateFormat sdfmth = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		Calendar cal = Calendar.getInstance();
		screenShotsFolder = screenShotsFolder + sdfmth.format(cal.getTime());
		new File(screenShotsFolder).mkdirs();
		return screenShotsFolder + File.separator;
	}
	
	public void stopSelenium(){
		selenium.close();
		selenium.stop();
	}
	
	@AfterClass
	public static void stopServer(){
		server.stop();
	}

}
