package com.orangehrm.login.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orangehrm.base.page.BasePage;

public class ForgotYourPasswordPage extends BasePage {

	@FindBy(xpath = "//div[@class='orangehrm-login-forgot']//p")
	WebElement lnkForgotYourPassword;

	@FindBy(xpath = "//h6[@class='oxd-text oxd-text--h6 orangehrm-forgot-password-title']")
	WebElement lblResetPassword;

	@FindBy(xpath = "//input[@class='oxd-input oxd-input--active']")
	WebElement txtUsername;

	@FindBy(xpath = "(//div[@class='orangehrm-forgot-password-button-container']//button)[last()]")
	WebElement btnResetPassword;

	@FindBy(xpath="//h6[@class='oxd-text oxd-text--h6 orangehrm-forgot-password-title']")
	WebElement lblResetPasswordLinkSentSuccessfully;
	
	public static Logger logger = Logger.getLogger(ForgotYourPasswordPage.class);

	public ForgotYourPasswordPage(WebDriver driver) {
		super(driver);
		logger.info("Starting  ForgotYourPasswordPage Constructor  method");
		logger.info("Ending  ForgotYourPasswordPage  Constructor method");
	}

	public void clickOnForgotYourPassword() {
		logger.info("Starting  clickOnForgotYourPassword method");

		explicitWait(lnkForgotYourPassword);
		lnkForgotYourPassword.click();

		logger.info("Ending  clickOnForgotYourPassword method");
	}

	public String getResetPasswordText() {
		logger.info("Starting  clickOnForgotYourPassword method");
		explicit(lblResetPassword);
		logger.info("Ending  clickOnForgotYourPassword method");

		return lblResetPassword.getText();
	}

	public void setUsername(String strUsername) {
		logger.info("Starting  setUsername method");

		explicitWait(txtUsername);
		txtUsername.sendKeys(strUsername);

		logger.info("Ending  setUsername method");
	}

	public void clickOnResetPasswordButton() {
		logger.info("Starting  clickOnResetPassword method");

		explicitWait(btnResetPassword);
		btnResetPassword.click();

		logger.info("Ending  clickOnResetPassword method");
	}
	
	public String getResetPasswordLinkSentSuccessfullyText()
	{
		logger.info("Starting  getResetPasswordLinkSentSuccessfullyText method");
		explicit(lblResetPasswordLinkSentSuccessfully);
		logger.info("Ending  getResetPasswordLinkSentSuccessfullyText method");

		return lblResetPasswordLinkSentSuccessfully.getText();
	}

}
