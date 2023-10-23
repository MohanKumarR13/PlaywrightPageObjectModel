package com.qa.opencart.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
	private Page page;
//1.String Locator -OR
	private String emailId = "//input[@id='input-email']";
	private String password = "//input[@id='input-password']";

	private String loginBtn = "//input[@value='Login']";

	private static String forgotPwdLink = "//div[@class='form-group']//a[normalize-space()='Forgotten Password']";
	private String logOutLink = "//a[@class='list-group-item'][normalize-space()='Logout']";

	public LoginPage(Page page) {
		this.page = page;
	}

	public String getLoginPageTitle() {
		return page.title();
	}

	public boolean isForgotPwdLinkExist() {
		return page.isVisible(forgotPwdLink);
	}

	public boolean doLogin(String appUserName, String appPwd) {
		System.out.println("App Credentials :" + appUserName + ":" + appPwd);
		page.fill(emailId, appUserName);
		page.fill(password, appPwd);
		page.click(loginBtn);
		if (page.isVisible(logOutLink)) {
			System.out.println("User is Logged in Successfully");
			return true;
		}
		return false;
	}
}
