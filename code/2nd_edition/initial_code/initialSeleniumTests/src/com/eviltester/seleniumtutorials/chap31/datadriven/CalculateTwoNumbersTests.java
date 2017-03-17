package com.eviltester.seleniumtutorials.chap31.datadriven;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.eviltester.seleniumtutorials.chap31.datadriven.pageObjectModel.CalculateForm;
import com.eviltester.seleniumutils.SeleniumManager;
import com.thoughtworks.selenium.Selenium;

@RunWith(Parameterized.class)
public class CalculateTwoNumbersTests {

	static SeleniumManager sm;
	static Selenium selenium;
	static CalculateForm calculate;
	
	private String number1;
	private String function;
	private String number2;
	private String answer;
	
    public CalculateTwoNumbersTests(String num1, String function, String num2, String answer){
    	this.number1 = num1;
    	this.function = function;
    	this.number2 = num2;
    	this.answer = answer;
    }	

    @Parameters
    public static Collection data() {
        return Arrays.asList(
        			new Object[][] { 
        					{ "1", "plus", "1", "2" }, 
        					{ "2", "times", "2", "4" },
        					{ "5", "divide", "2", "2.5" },
        					{ "10", "minus", "4", "6" },
        			}
        		);
    }	
	
	@BeforeClass
	static public void startServer() throws IOException{
		 sm = new SeleniumManager();
		 sm.start("http://www.compendiumdev.co.uk");
		 selenium = sm.getSelenium();

	}
	
	@Test
	public void test_calculate_two_values(){	
		calculate = new CalculateForm(selenium);
		calculate.open();
		
		calculate.setNumber1(this.number1);
		calculate.setFunction(this.function);
		calculate.setNumber2(this.number2);
		
		calculate = calculate.doCalculation();
		
		assertEquals(this.answer,calculate.getAnswer());
	}    
    
	@AfterClass
	static public void stopServer(){
		sm.stop();
	}		
}
