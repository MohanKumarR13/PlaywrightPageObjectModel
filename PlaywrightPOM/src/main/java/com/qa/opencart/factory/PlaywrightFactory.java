package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightFactory {
	static Playwright playwright;
	static Browser browser;
	static BrowserContext browserContext;
	static Page page;

	Properties properties;

	private static ThreadLocal<Browser> tlBrowser = new ThreadLocal<Browser>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();
	private static ThreadLocal<Page> tlPage = new ThreadLocal<>();
	private static ThreadLocal<Playwright> tlPlaywright = new ThreadLocal<>();

	public static Browser getBrowser() {
		return tlBrowser.get();
	}

	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}

	public static Playwright getPlaywright() {
		return tlPlaywright.get();
	}

	public static Page getPage() {
		return tlPage.get();
	}

	public Page initBrowser(Properties properties) {
		String browserName = properties.getProperty("browser").trim();
		System.out.println("Browser name is : " + browserName);
		Playwright playwright = Playwright.create();

		switch (browserName.toLowerCase()) {
		case "chromium":
			// browser=playwright.chromium().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		case "firefox":
			// browser=playwright.firefox().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(browser);
			playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
			break;
		case "safari":
			// browser=playwright.webkit().launch(new
			// BrowserType.LaunchOptions().setHeadless(false));
			tlBrowser.set(playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false)));
			break;
		case "chrome":
			// browser=playwright.chromium().launch(new
			// LaunchOptions().setChannel("chrome").setHeadless(false));
			tlBrowser.set(browser = playwright.chromium()
					.launch(new LaunchOptions().setChannel("chrome").setHeadless(false)));
			break;
		default:
			System.out.println("Please Pass the Right Browser Name");
			break;
		}

		tlBrowserContext.set(getBrowser().newContext());
		tlPage.set(getBrowserContext().newPage());
		getPage().navigate(properties.getProperty("url").trim());
		return getPage();
		// browserContext=browser.newContext();
		// page=browserContext.newPage();
		// page.navigate(properties.getProperty("url").trim());
		// return page;
	}

	public Properties init_property() {
		try {
			String loc = "C:\\Users\\santhosh\\eclipse-workspace\\PlaywrightPOM\\src\\test\\resources\\config\\config.properties";
			FileInputStream fileInputStream = new FileInputStream(loc);
			properties = new Properties();
			properties.load(fileInputStream);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return properties;
	}

	public static String takenScreenShot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		return path;
	}
}
