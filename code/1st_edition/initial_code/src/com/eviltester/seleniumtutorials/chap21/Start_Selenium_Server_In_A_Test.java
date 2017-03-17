
package com.eviltester.seleniumtutorials.chap21;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class Start_Selenium_Server_In_A_Test {
	
	@Test
	public void doASearchOnGoogle(){
		
		SeleniumServer seleniumServer=null;
		
		try {
			seleniumServer = new SeleniumServer();
			seleniumServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Selenium selenium=null;
		
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", "http://www.google.com");
		selenium.start();
		
		selenium.open("http://www.google.com");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");
		
		selenium.close();
		selenium.stop();
		
		if(seleniumServer!=null){
			seleniumServer.stop();
			
		}
	}
	
	@Ignore
	@Test
	public void checkForBindException(){
		
		SeleniumServer seleniumServer=null;
		
		try {
			seleniumServer = new SeleniumServer();
			seleniumServer.start();
		} catch (java.net.BindException bE){
			// could not bind, assume that server is currently running
			// and carry on
			System.out.println("could not bind - carrying on");
		}
		catch (Exception e) {
			// any other exception stop and start
			throw new IllegalStateException("Can't start selenium server", e);
		}
		
		Selenium selenium=null;
		
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", "http://www.google.com");
		selenium.start();
		
		selenium.open("http://www.google.com");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");
		
		selenium.close();
		selenium.stop();
		
		if(seleniumServer!=null){
			seleniumServer.stop();
			
		}
	}	
	
	@Test
	public void checkForBindExceptionAndStopExistingServer() throws IOException{
		
		SeleniumServer seleniumServer=null;
		String stopSeleniumCommand = 
			"http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer";
		try {
			seleniumServer = new SeleniumServer();
			seleniumServer.start();
		} catch (java.net.BindException bE){
			// could not bind, assume that server is currently running
			// and carry on
			System.out.println("could not bind - carrying on");
			// try and stop it
			if( runHTTPCommand(stopSeleniumCommand)){
				try {
					seleniumServer = new SeleniumServer();
					seleniumServer.start();
				} catch (Exception e) {
					throw new IllegalStateException("Could not stop existing server on blocked port 4444", e);
				}
			}
		}
		catch (Exception e) {
			// any other exception stop and start
			throw new IllegalStateException("Can't start selenium server", e);
		}
		
		Selenium selenium=null;
		
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", "http://www.google.com");
		selenium.start();
		
		selenium.open("http://www.google.com");
		selenium.type("xpath=//input[@name='q']", "Selenium-RC");
		selenium.click("xpath=//input[@name='btnG' and @type='submit']");
		selenium.waitForPageToLoad("10000");
		
		selenium.close();
		selenium.stop();
		
		if(seleniumServer!=null){
			seleniumServer.stop();
			
		}
	}		
	
	private boolean runHTTPCommand(String theCommand) throws IOException{
		URL url = new URL(theCommand);
		
		URLConnection seleniumConnection = url.openConnection();
		seleniumConnection.connect();

		InputStream inputStream = seleniumConnection.getInputStream();
        ByteArrayOutputStream outputSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int streamLength;
        while ((streamLength = inputStream.read(buffer)) != -1) {
        	outputSteam.write(buffer, 0, streamLength);
        }
        inputStream.close();

        
        // give command some time to finish
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        String stringifiedOutput = outputSteam.toString();

        if (stringifiedOutput.startsWith("OK"))
        	return true;
                    
        return false;
	}
	
	@Test
	public void remoteControlConfigExample(){
		 
		final int SELENIUM_PORT = 8888;
		final int SELENIUM_TIMEOUT = 45;
		
		RemoteControlConfiguration rcConfig = new RemoteControlConfiguration();
		rcConfig.setTimeoutInSeconds(SELENIUM_TIMEOUT);
		rcConfig.setPort(SELENIUM_PORT);

   		SeleniumServer seleniumServer=null;
    		
    	try {
    		seleniumServer = new SeleniumServer(rcConfig);
    		seleniumServer.start();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	
    	Selenium selenium=null;
    	
    	selenium = new DefaultSelenium("localhost", SELENIUM_PORT, 
    			"*firefox", "http://www.google.com");
    	selenium.start();
    	
    	selenium.open("http://www.google.com");
    	selenium.type("xpath=//input[@name='q']", "Selenium-RC");
    	selenium.click("xpath=//input[@name='btnG' and @type='submit']");
    	selenium.waitForPageToLoad("10000");
    	
    	selenium.close();
    	selenium.stop();
    	
    	if(seleniumServer!=null){
    		seleniumServer.stop();
    	}	
	}
}