package com.eviltester.seleniumutils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumManagerBasic {

	SeleniumServer seleniumServer=null;
	Selenium selenium=null;
	String browserCode = "";
	
	public SeleniumManagerBasic(){
		set_a_DefaultBrowser();
	}
	
	public void set_a_DefaultBrowser(){
		browserCode ="*firefox";	
	}
	
	public void start(String domain) throws IOException {
		
		String stopSeleniumCommand = 
			"http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer";
		
		try {
			
			startSeleniumServerProgrammatically();

		} catch (java.net.BindException bE){
			// could not bind, assume that server is currently running
			// so carry on and try to stop it
			System.out.println("could not bind - carrying on");

			// run the http command to stop the server, then start it
			if( runHTTPCommand(stopSeleniumCommand)){
				try {
					
					startSeleniumServerProgrammatically();
					
				} catch (Exception e) {
					throw new IllegalStateException("Could not stop existing server on blocked port 4444", e);
				}
			}
		}
		catch (Exception e) {
			// any other exception stop and start
			throw new IllegalStateException("Can't start selenium server", e);
		}
		
		
		// connect to the server
		selenium = new DefaultSelenium("localhost", 4444, browserCode, domain);
		selenium.start();
		
	}

	private void startSeleniumServerProgrammatically() throws Exception {
		seleniumServer = new SeleniumServer();
		seleniumServer.start();
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

	public void stop() {
		if(selenium!=null){
			selenium.close();
			selenium.stop();
		}
		
		if(seleniumServer!=null){
			seleniumServer.stop();
		}
	}

	public Selenium getSelenium() {
		return selenium;
	}

	public void setBrowser(String browserCode) {
		this.browserCode = browserCode;
	}
}
