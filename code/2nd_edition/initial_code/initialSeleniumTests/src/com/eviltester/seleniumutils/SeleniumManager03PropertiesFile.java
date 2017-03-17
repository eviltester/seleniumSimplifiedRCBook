package com.eviltester.seleniumutils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumManager03PropertiesFile {

	SeleniumServer seleniumServer=null;
	Selenium selenium=null;
	
	String host;
	String port;
	String browser;
	
	private void readProperties(){
			Properties props;
			props = new Properties();
			
			String filePath = "selenium.properties";
			
			try {
				props.load(new FileInputStream(filePath));
			} catch (FileNotFoundException e) {
				System.out.println("No Properties file found");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Error loading properties file");
				e.printStackTrace();
			}
			
			this.browser = props.getProperty("browser","*firefox");
			this.host = props.getProperty("host","localhost");
			this.port = props.getProperty("port","4444");		
	}
	
	public void start(String baseURL) throws IOException {
		
		String shutdownCommand = "http://%s:%s/selenium-server/driver/?cmd=shutDownSeleniumServer";
		readProperties();
		
		String stopSeleniumCommand = String.format(shutdownCommand,host,port);
		
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
					throw new IllegalStateException("Could not stop existing server on blocked port " + port, e);
				}
			}
		}
		catch (Exception e) {
			// any other exception stop and start
			throw new IllegalStateException("Can't start selenium server", e);
		}
		
		selenium = new DefaultSelenium(host, new Integer(port), browser, baseURL);
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
