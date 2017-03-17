package com.eviltester.seleniumSimplified.testClasses;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatchman;
import org.junit.runners.model.FrameworkMethod;
import com.eviltester.seleniumutils.seleniumManager.SeleniumManager;
import com.eviltester.seleniumutils.seleniumManager.TheSeleniumManager;
import com.thoughtworks.selenium.Selenium;

public class SeleniumSimplifiedTest {

	protected static SeleniumManager sm;
	protected static Selenium selenium;
	protected static String screenShotsFolder;
	protected static String currentBrowser;	
	
	@BeforeClass
	static public void startServer() throws IOException{
		sm = TheSeleniumManager.getSeleniumManager().
				start("http://www.compendiumdev.co.uk");
		selenium = sm.getSelenium();
		screenShotsFolder = null;
		currentBrowser = sm.getBrowser().replace("*", "");
	}
		
	@Rule
	public MethodRule watchman= new TestWatchman() {

		@Override
		public void failed(Throwable e, FrameworkMethod method) {
			captureScreenShots(screenShotsFolder, currentBrowser,method.getName() + "_" + e.getClass().getSimpleName());
		}

		@Override
		public void succeeded(FrameworkMethod method) {
		}
	};

	private void captureScreenShots(String screenShotsFolder, 
								String browser, String fileNameAppend) {
		// since captureScreenshot takes the screen, we should maximise the 
		// browser before we take the screenshot
		selenium.windowMaximize();
		selenium.windowFocus();
		
		if(screenShotsFolder==null){
			screenShotsFolder = TheSeleniumManager.getScreenshotsFolder();
		}
		
		// give the browser a chance to maximise properly
		try { Thread.sleep(1000);	} catch (Exception e) {}
			
		String filename = 	screenShotsFolder + browser + "_" +
							fileNameAppend + "_screenshot.png";
		
		try{ selenium.captureScreenshot(filename);	}catch(Exception e){}

		try{ selenium.captureEntirePageScreenshot(
				filename.replace(".png","full.png"),"");   }catch(Exception e){}
		
	}
	
	@AfterClass
	static public void closeItDown(){
		
		// default to closing after test
		boolean closeAfterTest = true;
		
		// unless we set the system property selenium.stopAfterSuite=TRUE
		if(System.getProperties().containsKey("selenium.stopAfterSuite")){
			if(System.getProperty("selenium.stopAfterSuite").
						equalsIgnoreCase("TRUE")){
				closeAfterTest = false;
			}
		}
		
		if(closeAfterTest)
			sm.stopSelenium();
	}
}