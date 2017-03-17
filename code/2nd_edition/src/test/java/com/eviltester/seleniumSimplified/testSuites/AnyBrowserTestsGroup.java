package com.eviltester.seleniumSimplified.testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eviltester.seleniumSimplified.calculate.CalculateTwoNumbersMultipleTests;
import com.eviltester.seleniumSimplified.calculate.CalculateTwoNumbersTestsTabFile;
import com.eviltester.seleniumSimplified.cookies.CookieTests;
import com.eviltester.seleniumSimplified.htmlform.HTML_form_tests;
import com.eviltester.seleniumSimplified.javascript.AjaxPageTests;
import com.eviltester.seleniumSimplified.search.SearchPageTests;

 
@RunWith(Suite.class)
@Suite.SuiteClasses({
	CalculateTwoNumbersMultipleTests.class,
	CalculateTwoNumbersTestsTabFile.class,
	CookieTests.class,
	HTML_form_tests.class,
	SearchPageTests.class,
	AjaxPageTests.class
})

public class AnyBrowserTestsGroup {
}
