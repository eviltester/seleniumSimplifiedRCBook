package com.eviltester.seleniumutils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumManager02FirstEvolution {

	SeleniumServer seleniumServer=null;
	Selenium selenium=null;
	
	public void start(String baseURL) throws IOException {
		
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
		
		
		
		selenium = new DefaultSelenium("localhost", 4444, 
				"*firefox", baseURL);
		selenium.start();
		
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
	
	public Selenium getSelenium() {
		return selenium;
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

}
