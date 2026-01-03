package com.orangehrm.admin.page;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.orangehrm.base.page.BasePage;

public class UserManagementPage extends BasePage {
	Dimension recordsSize;

	public static Logger logger = Logger.getLogger(UserManagementPage.class);

	@FindBys({ @FindBy(xpath = "//ul[@class='oxd-main-menu']//li") })
	List<WebElement> lstMainMenu;

	@FindBy(xpath = "//div[@class='oxd-topbar-header-title']//span//h6")
	WebElement lblAdmin;

	@FindBy(xpath = "(//div[@class='oxd-topbar-header-title']//span//h6)[last()]")
	WebElement lbluserManagement;

	@FindBy(xpath = "//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input")
	WebElement txtUsername;

	@FindBy(xpath = "//div[@class='oxd-select-text--after']//i")
	WebElement drpUserRole;

	@FindBy(xpath = "//div[@class='oxd-select-dropdown --positon-bottom']//div//span")
	List<WebElement> lstUserRole;

	@FindBy(xpath = "(//div[@class='oxd-form-actions']//button)[last()]")
	WebElement btnSearch;
	
	@FindBy(xpath="//div[@class='card-center']")
	List<WebElement> txtRecordsSize;
	
    @FindBy(xpath="//span[@class='oxd-text oxd-text--span']")
    WebElement txtRecordsFound;

	public UserManagementPage(WebDriver driver) {
		super(driver);
		logger.info("Starting  UserManagementPage Constructor  method");
		logger.info("Ending  UserManagementPage  Constructor method");
	}

	public void clickOnMainMenuListItem(String strMenuItem) {
		logger.info("Starting  clickOnMainMenuListItem  method");

		
		explicitWait(lstMainMenu);
		for (WebElement  menu: lstMainMenu) {
			if (menu.getText().equalsIgnoreCase(strMenuItem)) {
				explicit(menu);
				menu.click();
				break;
			}
		}

		logger.info("Ending  clickOnMainMenuListItem  method");
	}

	public String getAdminText() {

		logger.info("Starting  getAdminText  method");
		explicit(lblAdmin);
		logger.info("Ending  getAdminText  method");

		return lblAdmin.getText();
	}

	public String getUserManagementText() {

		logger.info("Starting  getUserManagementText  method");
		explicit(lbluserManagement);
		logger.info("Ending  getUserManagementText  method");

		return lbluserManagement.getText();
	}

	public void setUsername(String strUsername) {
		logger.info("Starting  setUsername  method");

		explicit(txtUsername);
		txtUsername.sendKeys(strUsername);

		logger.info("Ending  setUsername  method");
	}

	public void clickOnUserRoleDropdown() {
		logger.info("Starting  clickOnUserRoleDropdown  method");

		explicit(drpUserRole);
		drpUserRole.click();

		logger.info("Ending  clickOnUserRoleDropdown  method");
	}

	public void selectUserRole(String strUserRole) {
		logger.info("Starting  setUserRole  method");

		for (WebElement userrole : lstUserRole) {
			if (userrole.getText().equalsIgnoreCase(strUserRole)) {
				userrole.click();
				break;
			}
		}

		logger.info("Ending  setUserRole  method");
	}

	public void clickOnSearchButton() {

		logger.info("Starting  clickOnSearchButton  method");
		explicit(btnSearch);
		btnSearch.click();
		logger.info("Ending  clickOnSearchButton  method");

	}
	
	public int getRecordsSize()
	{
		explicitWait(txtRecordsSize);
		return txtRecordsSize.size();
	}
	
	
	
	public String getRecordsFoundText()
	{
		return txtRecordsFound.getText();

	}
}
