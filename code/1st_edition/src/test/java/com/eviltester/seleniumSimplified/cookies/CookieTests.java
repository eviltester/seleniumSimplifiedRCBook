package com.eviltester.seleniumSimplified.cookies;

import static org.junit.Assert.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.junit.Test;

import com.eviltester.seleniumSimplified.pageObjectModel.SearchPage;
import com.eviltester.seleniumSimplified.testClasses.SeleniumSimplifiedTest;


// for more info on cookies see...
// http://www.cookiecentral.com/faq/
// http://www.cookiecentral.com/demomain.htm
// initial demo for:
// http://www.cookiecentral.com/code/js_cookie8.htm
// http://en.wikipedia.org/wiki/HTTP_cookie
// http://www.comptechdoc.org/independent/web/cgi/javamanual/javacookie.html
// http://techpatterns.com/downloads/javascript_cookies.php
// http://www.perlscriptsjavascripts.com/js/cookies.html
// http://www.hccp.org/java-net-cookie-how-to.html
//http://www.htmlgoodies.com/beyond/javascript/article.php/3470821
// http://www.elated.com/articles/javascript-and-cookies/

public class CookieTests extends SeleniumSimplifiedTest{

	
	SearchPage searchPage;

	
	/**
	 * use http://www.compendiumdev.co.uk/selenium/search.php as an example
	 * creates a cookie
	 * @throws IOException
	 */
	

	

	@Test
	public void checkCookiesGetCreated() {
		searchPage = new SearchPage(selenium);
		searchPage.open();
		
		selenium.deleteAllVisibleCookies();

		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		assertCookieDoesNotExist("seleniumSimplifiedSearchLastVisit");
		assertCookieDoesNotExist("seleniumSimplifiedLastSearch");

		searchPage.open();
		assertCookieExists("seleniumSimplifiedSearchNumVisits");
		assertCookieExists("seleniumSimplifiedSearchLastVisit");
		assertCookieDoesNotExist("seleniumSimplifiedLastSearch");
		
		searchPage.typeSearchTerm("selenium");
		searchPage.clickSearchButton();
		assertCookieExists("seleniumSimplifiedSearchNumVisits");
		assertCookieExists("seleniumSimplifiedSearchLastVisit");
		assertCookieExists("seleniumSimplifiedLastSearch");
	}

	private void assertCookieDoesNotExist(String cookieName) {
		String cookieMessage = "We deleted all cookies, %s should not exist";
		assertFalse(	String.format(cookieMessage,cookieName), selenium.isCookiePresent(cookieName));
	}
	private void assertCookieExists(String cookieName) {
		String cookieMessage = "We added cookie %s, it should exist";
		assertTrue(	String.format(cookieMessage,cookieName),
					selenium.isCookiePresent(cookieName));
	}	

	@Test
	public void parseResultsOfGetCookieExplored() throws UnsupportedEncodingException {
		searchPage = new SearchPage(selenium);
		searchPage.open();

		// split the cookie string so that each entry in the array is a cookie name=value
		String []cookies = selenium.getCookie().split(";");
		
		for (int i = 0; i < cookies.length; i++) {

			// split the cookie=name value into name[0] and value[1] entries
			String []cookieNameValue = cookies[i].split("=");
			
			// remove extra spaces and encoding on the name and value
			String cookieName = cookieNameValue[0].trim();
			String cookieValue = URLDecoder.decode(cookieNameValue[1].trim(),"UTF-8");
			
			// compare against the 'byName' methods
			assertTrue("expected to find cookie by name " + cookieName,selenium.isCookiePresent(cookieName));
			assertEquals(cookieValue,URLDecoder.decode(selenium.getCookieByName(cookieName),"UTF-8"));
		}
	}
		
	@Test
	public void getCookieByNameExplored() {

		String lastSearchTerm="";
		
		// open page and set to no cookies
		searchPage = new SearchPage(selenium);
		searchPage.open();
		selenium.deleteAllVisibleCookies();

		/* open the page 10 times and check cookie increments */
		for (int i = 1; i < 11; i++) {
			
			lastSearchTerm = "Selenium " + i;
			searchPage.typeSearchTerm(lastSearchTerm);
			searchPage.clickSearchButton();

			assertEquals(i + "", selenium.getCookieByName("seleniumSimplifiedSearchNumVisits"));
			assertEquals(lastSearchTerm, selenium.getCookieByName("seleniumSimplifiedLastSearch"));
			
			assertCookieExists("seleniumSimplifiedSearchLastVisit");
		}
	}


	@Test
	public void deleteCookieExplored() {

		searchPage = new SearchPage(selenium);
		searchPage.open();

		// simple delete works fine
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits","");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		
		// full domain and path work fine - you can see these in firecookie
		// issues getting this working in firefox, but works fine in IE
		if(sm.getBrowser().contains("ie")){
			searchPage.open();
			selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=www.compendiumdev.co.uk, path=/selenium/");
			assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		}
		
		// delete example using just the domain
		if(sm.getBrowser().contains("ie")){
			searchPage.open();			
			selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=www.compendiumdev.co.uk");
			assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		}

		searchPage.open();
		// missing domain and having path will delete the cookie
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "path=/selenium/");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		
		// fail to delete when missing domain and getting path wrong
		searchPage.open();			
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "path=/wrongpath/");
		assertCookieExists("seleniumSimplifiedSearchNumVisits");		
		
		searchPage.open();			
		// missing out the path and recurse succeed
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=www.compendiumdev.co.uk, recurse=true");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");
		
		searchPage.open();
		// fail to delete when domain wrong even if path right
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=compendiumdev.co.uk, path=/selenium/");
		assertCookieExists("seleniumSimplifiedSearchNumVisits");

		searchPage.open();			
		// getting the domain wrong will pass if we recurse through subdomains
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=compendiumdev.co.uk, recurse=true");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");

		searchPage.open();			
		// getting the domain wrong can pass if we recurse through subdomains, path makes no difference
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=compendiumdev.co.uk, recurse=true, path=/selenium/");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");		

		searchPage.open();			
		// getting the domain wrong can pass if we recurse through subdomains even if we get the path wrong
		selenium.deleteCookie("seleniumSimplifiedSearchNumVisits", "domain=compendiumdev.co.uk, recurse=true, path=/sele/");
		assertCookieDoesNotExist("seleniumSimplifiedSearchNumVisits");		
		
	}
	
	@Test
	public void createCookieExplored() throws IOException {

		searchPage = new SearchPage(selenium);
		searchPage.open();
		selenium.deleteAllVisibleCookies();
		
		searchPage.open();
		assertCookieDoesNotExist("seleniumSimplifiedLastSearch");
		
		String lastSearch = "Search For This";
		String lastSearchAsCookieValue = URLEncoder.encode(lastSearch,"UTF-8");
		selenium.createCookie(	"seleniumSimplifiedLastSearch=" + lastSearchAsCookieValue,
								"max_age=6000");
		
		// create a cookie with an encoded value
		searchPage.open();
		assertCookieExists("seleniumSimplifiedLastSearch");
		assertEquals(selenium.getCookieByName("seleniumSimplifiedLastSearch"), lastSearchAsCookieValue);		
	}




}