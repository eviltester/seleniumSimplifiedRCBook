package com.eviltester.seleniumSimplified.htmlform;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.eviltester.seleniumSimplified.pageObjectModel.BasicHTMLForm;
import com.eviltester.seleniumSimplified.pageObjectModel.HTMLFormResultsPage;
import com.eviltester.seleniumSimplified.testClasses.SeleniumSimplifiedTest;

public class Chrome_only_form_tests extends SeleniumSimplifiedTest{

	BasicHTMLForm htmlForm;
	
	@Before
	public void setUp() throws Exception {
		htmlForm = new BasicHTMLForm(selenium);
		htmlForm.open();
	}
	
	@Test @Ignore("File handling now very unreliable")
	public void test_submit_form_with_filename(){
		
		// skip this test if it is not chrome or firefox
		if(!"|chrome|".contains("|" + currentBrowser))
			return;
		
		htmlForm.typeFilename("c:\\selenium\\readme.txt");
		HTMLFormResultsPage results = htmlForm.clickSubmitButton();

		assertTrue(results.hasFilename());
		assertEquals("readme.txt",results.getFilename());		
	}	
}
