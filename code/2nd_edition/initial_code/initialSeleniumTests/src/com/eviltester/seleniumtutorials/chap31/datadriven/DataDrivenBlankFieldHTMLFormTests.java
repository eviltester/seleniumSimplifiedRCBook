package com.eviltester.seleniumtutorials.chap31.datadriven;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel.HTMLFormResultsPage;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;


/** 
 * test combinations of blank fields and check that message displayed
 * 
 * when no username or password or comments "No Value for username" etc.
 * when no checkboxItems p name="_checkboxes" is missing
 * @author Alan
 *
 */

@RunWith(Parameterized.class)
public class DataDrivenBlankFieldHTMLFormTests {

		static SeleniumManager sm;
		static Selenium selenium;
		static BasicHTMLForm htmlForm;
		
		String[] fieldsToBlank;
		
		
	    @Parameters
	    public static Collection data() {
	        return Arrays.asList(
	        			new Object[][] { 
	        					{ "username" }, 
	        					{ "username,password" },
	        					{ "username,password,comment" },
	        			}
	        		);
	    }

	    public DataDrivenBlankFieldHTMLFormTests(String fieldsToBlankCSV){
	    	this.fieldsToBlank = fieldsToBlankCSV.split(",");
	    }

		private void setFieldsToBlanks() {
			for (int i = 0; i < fieldsToBlank.length; i++) {
	    		if(fieldsToBlank[i].equalsIgnoreCase("username"))
	    			htmlForm.typeUserName("");
	    		if(fieldsToBlank[i].equalsIgnoreCase("password"))
	    			htmlForm.typePassword("");
	    		if(fieldsToBlank[i].equalsIgnoreCase("comment"))
	    			htmlForm.typeComments("");
			}
		}
		
		@BeforeClass
		static public void startServer() throws IOException{
			 sm = new SeleniumManager();
			 sm.start("http://www.compendiumdev.co.uk");
			 selenium = sm.getSelenium();

		}
		
		@AfterClass
		static public void stopServer(){
			sm.stop();
		}	

		@Test
		public void test_no_values(){	
			htmlForm = new BasicHTMLForm(selenium);
			htmlForm.open();
			htmlForm.typeUserName("dataDrivenUsername");
			htmlForm.typePassword("dataDrivenPassword");
			
			setFieldDefaultsForTest();
			setFieldsToBlanks();
			
			HTMLFormResultsPage resultsPage =htmlForm.clickSubmitButton();
			
			assertCorrectErrorMessages(resultsPage);
		}

		private void assertCorrectErrorMessages(HTMLFormResultsPage resultsPage) {
			for (int i = 0; i < fieldsToBlank.length; i++) {
	    		if(fieldsToBlank[i].equalsIgnoreCase("username"))
	    			assertEquals(resultsPage.getUserName(),"No Value for username");
	    		if(fieldsToBlank[i].equalsIgnoreCase("password"))
	    			assertEquals(resultsPage.getPassword(),"No Value for password");
	    		if(fieldsToBlank[i].equalsIgnoreCase("comment"))
	    			assertEquals(resultsPage.getComment(),"No Value for comments");
			}
		}

		private void setFieldDefaultsForTest() {
			htmlForm.typeUserName("defaultUserName");
			htmlForm.typePassword("defaultPassword");
			htmlForm.typeComments("default comments");
			htmlForm.typeFilename("defaultFilename.txt");
			htmlForm.setUnChecked_CheckBox("1");
			htmlForm.setUnChecked_CheckBox("2");
			htmlForm.setUnChecked_CheckBox("3");
			htmlForm.setChecked_CheckBox("1");	
			htmlForm.selectMultiSelection("1");
		}
}
