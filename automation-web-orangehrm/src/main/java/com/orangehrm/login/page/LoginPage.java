package com.orangehrm.login.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.orangehrm.base.page.BasePage;

public class LoginPage extends BasePage {
	
	@FindBy(xpath = "//input[contains(@class,'oxd-input oxd-input')]")
	private WebElement txtUsername;

	@FindBy(xpath = "(//input[contains(@class,'oxd-input oxd-input')])[last()]")
	private WebElement txtPassword;

	@FindBy(xpath = "//button[text()=' Login ']")
	private WebElement btnLogin;  
	
	@FindBy(xpath="//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message']")
	private WebElement txtUsernameRequired;
	
	@FindBy(xpath="(//span[@class='oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message'])[last()]")
	private WebElement txtPasswordRequired;
	
	@FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
	private WebElement txtInvalidcredentials;
	
	@FindBy(xpath="//div[@class='oxd-brand-banner']")
	private WebElement imgOrangeHRMLogo;

	public static Logger logger = Logger.getLogger(LoginPage.class);

	public LoginPage(WebDriver driver) {
		super(driver);
		logger.info("Starting  LoginPage Constructor  method");		
		logger.info("Ending  LoginPage  Constructor method");

	}

	public void setUsername(String strUsername) {
		logger.info("starting setUsername Method");

		explicitWait(txtUsername);
		txtUsername.clear();
		txtUsername.sendKeys(strUsername);

		logger.info("Ending  setUsername method");
	}

	public void setPassword(String strPassword) {
		logger.info("starting setPassword Method");

		explicitWait(txtPassword);
		txtPassword.clear();
		txtPassword.sendKeys(strPassword);

		logger.info("Ending  setPassword method");
	}

	public void clickOnLoginButton() {

		logger.info("starting clickOnLoginButton Method");

		explicit(btnLogin);
		btnLogin.click();

		logger.info("Ending  clickOnLoginButton method");
	}
	
	public String getUsernameRequiredText()
	{
		logger.info("starting getRequiredText Method");
		logger.info("Ending  getRequiredText method");
		explicitWait(txtUsernameRequired);
		return txtUsernameRequired.getText();
	}
	
	public String getPasswordRequiredText()
	{
		logger.info("starting getRequiredText Method");
		logger.info("Ending  getRequiredText method");
		
		explicitWait(txtPasswordRequired);
		return txtPasswordRequired.getText();
	}
	
	public String getInvalidCredentialsText()
	{
		logger.info("starting getInvalidCredentialsText Method");
		logger.info("Ending  getInvalidCredentialsText method");
		
		explicitWait(txtInvalidcredentials);
		return txtInvalidcredentials.getText();
	}
	
	public boolean isDisplayedOrangeHRMLogo()
	{
		logger.info("starting isDisplayedOrangeHRMLogo Method");
		logger.info("Ending  isDisplayedOrangeHRMLogo method");
		
		explicit(imgOrangeHRMLogo);
		return imgOrangeHRMLogo.isDisplayed();
	}
}
