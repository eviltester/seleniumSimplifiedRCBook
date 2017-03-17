package com.eviltester.seleniumSimplified.testSuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.eviltester.seleniumSimplified.htmlform.Chrome_only_form_tests;

 
@RunWith(Suite.class)
@Suite.SuiteClasses({
	Chrome_only_form_tests.class
})

public class ChromeOrFirefoxBrowserTestsGroup {
}
