package com.eviltester.seleniumutils.seleniumManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

// Uncomment lines below if you want to use Selenium 2.0 for experiments 
/*
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

*/
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.server.SeleniumServer;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class SeleniumManager {


	SeleniumServer seleniumServer=null;
	Selenium selenium=null;
	WebDriver driver=null;
	
	String host="";
	String port="";
	String browser="";
	String baseURL="";
	
	protected SeleniumManager(){
		// to minimise usage by other classes and force TheSeleniumManager redirection
	}
	
	private boolean useSeleniumTwo=true;
	
	public void useSeleniumTwo(){
		this.useSeleniumTwo = true;
	}
	public void useSeleniumOne(){
		this.useSeleniumTwo = false;
	}
	
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
			System.out.println("SETTING BROWSER as " + this.browser + " during property file processing");
			this.host = props.getProperty("host","localhost");
			this.port = props.getProperty("port","4444");		
	}
	
	private static final String SELENIUM_PORT = "selenium.port";
	private static final String SELENIUM_HOST = "selenium.host";
	private static final String SELENIUM_BROWSER = "selenium.browser";	
	
	public void readSystemProperties() {
		Properties systemProperties = System.getProperties();
		
		if(systemProperties.containsKey(SELENIUM_BROWSER)){
			this.browser = systemProperties.getProperty(SELENIUM_BROWSER);
			System.out.println("SETTING BROWSER as " + this.browser + " during system property file processing");
		}
			
		
		if(systemProperties.containsKey(SELENIUM_HOST))
			this.host = systemProperties.getProperty(SELENIUM_HOST);

		if(systemProperties.containsKey(SELENIUM_PORT))
			this.port = systemProperties.getProperty(SELENIUM_PORT);
	}	

	public void resetProperties(){
		readProperties(); // get the properties from a file
		readSystemProperties(); // allow system properties to override the properties file
	}
	
	public SeleniumManager start(String baseURL, String browser) throws IOException {
		// baseurl is for selenium
		// server does not care about base url
							

					
		if(useSeleniumTwo){
			if(createASelenium2Session(baseURL, browser))
				return this;
		}
			
			
		if(this.seleniumServer==null){
			startNewSeleniumServer();
		}

		// if baseurl is different then we need a new selenium session
		if(selenium==null || !this.baseURL.equalsIgnoreCase(baseURL) || !this.browser.equalsIgnoreCase(browser)){
			
			if(selenium!=null){
				selenium.close();
				selenium.stop();
			}	
			
			this.baseURL = baseURL;
			
			if(browser!="")
				this.browser = browser;
			
			selenium = new DefaultSelenium(host, new Integer(port), this.browser, baseURL);
			selenium.start();
		}
		
		return this;
				
	}
	private boolean createASelenium2Session(String baseURL, String browser) {
		
		if(!this.baseURL.equalsIgnoreCase(baseURL) || !this.browser.equalsIgnoreCase(browser)){
			
			if(driver!=null){
				driver.close();
			}	
			
			this.baseURL = baseURL;
			
			if(browser!="")
				this.browser = browser;				
		}
		
		String firefoxBrowsers="|*firefox|*firefoxproxy|*pifirefox|*chrome|*firefoxchrome|";
		if(firefoxBrowsers.contains("|" + browser + "|")){
			driver= new FirefoxDriver();
		}

		String ieBrowsers="|*iexplore|*iexploreproxy|*piiexplore|*iehta|";
		if(ieBrowsers.contains("|" + browser + "|")){
			driver = new InternetExplorerDriver();
		}

		String htmlUnitBrowsers="|*htmlunit|";
		if(htmlUnitBrowsers.contains("|" + browser + "|")){
			driver = new HtmlUnitDriver();
		}

		String chromeBrowsers="|*googlechrome|";
		if(chromeBrowsers.contains("|" + browser + "|")){
			driver = new ChromeDriver();
		}
		
		if(driver!=null){
			System.out.println("Using web driver");
			selenium = new WebDriverBackedSelenium(driver, baseURL);
			return true;
		}else{
			System.out.println("Default to Selenium 1.x");
			// default to selenium 1.x
			useSeleniumTwo = false;
			return false;
		}
	}
	private void startNewSeleniumServer() throws IOException {
		String shutdownCommand = "http://%s:%s/selenium-server/driver/?cmd=shutDownSeleniumServer";
		
		String stopSeleniumCommand = String.format(shutdownCommand,host,port);
		
		try {
			seleniumServer = new SeleniumServer();
			seleniumServer.start();
		} catch (java.net.BindException bE){
			// could not bind, assume that server is currently running
			// and carry on
			System.out.println("could not bind - carrying on");
			
				// try and stop the server and create a new one
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
	}
	
	public SeleniumManager start(String baseURL) throws IOException {
		
		return start(baseURL,this.browser);
		
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

	public void stopSelenium() {
		
		if(driver!=null){
			driver.close();
		}	
		
		if(selenium!=null){
			selenium.close();
			selenium.stop();
			selenium=null;
		}

	}
	
	public void shutDownServer(){
		
		// remember we stopped it so that we start a new one
		baseURL="";
		
		if(seleniumServer!=null){
			seleniumServer.stop();
			seleniumServer=null;
		}		
	}
	
	public String getBrowser() {
		return this.browser;
	}
	public SeleniumServer getServer() {
		return seleniumServer;
	}

}
