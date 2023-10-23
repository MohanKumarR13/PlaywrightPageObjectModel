package com.qa.opencart.base;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Page;
import com.qa.opencart.factory.PlaywrightFactory;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;

public class BaseTest {
	PlaywrightFactory playwrightFactory;
	Page page;
	protected HomePage homePage;
	protected LoginPage loginPage;

	static public Properties properties;
	@BeforeTest
	public void setup() {
		playwrightFactory = new PlaywrightFactory();
		properties=playwrightFactory.init_property();
		page = playwrightFactory.initBrowser(properties);
		homePage = new HomePage(page);
		
	}

	@AfterTest
	public void tearDown() {
		//page.context().browser().close();
	}
}
