package com.orangehrm.admin.page;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import com.orangehrm.base.page.BasePage;

public class AddUserPage extends BasePage{
	
	
	String strEmpName;
	String strUserName;
	
	@FindBy(xpath="//button[text()=' Add ']")
	WebElement btnAddUser;
	
	@FindBy(xpath= "//label[text()='User Role']//ancestor::div[2]//following-sibling::div//i")
	WebElement drpUserRole;
		
	@FindBys({@FindBy(xpath="//div[@class='oxd-select-option']//span")})
	List<WebElement>  lstAddUser;
	
	@FindBy(xpath="//label[text()='Employee Name']//parent::div//following-sibling::div//input")
	WebElement txtEmpName;
	
	@FindBy(xpath= "//label[text()='Status']//ancestor::div[2]//following-sibling::div//i")
	WebElement drpStatus;
	
	@FindBy(xpath="//label[text()='Username']//parent::div//following-sibling::div//input")
	WebElement txtUsername;
	
	@FindBy(xpath="//label[text()='Password']//parent::div//following-sibling::div//input")
	WebElement txtPassword;
	
	@FindBy(xpath="//label[text()='Confirm Password']//parent::div//following-sibling::div//input")
	WebElement txtConfirmPassword;


	public AddUserPage(WebDriver driver) {
		super(driver);
		
		logger.info("Starting  UserManagementPage Constructor  method");
		logger.info("Ending  UserManagementPage  Constructor method");
	}
	
	public void clickOnAddButton() {
		
		logger.info("Starting  clickOnAddButton  method");

		explicit(btnAddUser);
		btnAddUser.click();

		logger.info("Ending  clickOnAddButton  method");
	}
	
	public void clickOnUserRoleDropdown()
	{
		logger.info("Starting  clickOnUserRoleDropdown  method");

		explicit(drpUserRole);
		drpUserRole.click();

		logger.info("Ending  clickOnUserRoleDropdown  method");
	}
	
	
	public void clickOnStatusDropdown()
	{
		logger.info("Starting  clickOnStatusDropdown  method");

		explicit(drpStatus);
		drpStatus.click();

		logger.info("Ending  clickOnStatusDropdown  method");
	}
	public void setUserRoleStatusDropdown(String strUserRoleStatus)
	{		
		logger.info("Starting  setUserRoleStatusDropdown  method");

		explicitWait(lstAddUser);
		for(WebElement user:lstAddUser)
		{
			if(user.getText().equalsIgnoreCase(strUserRoleStatus))
			{
				explicit(user);
				user.click();
				break;				
			}
		}		

		logger.info("Ending  setUserRoleStatusDropdown  method");
	}
	
	public void setEmployeeName(String strEmpName)
	{
		logger.info("Starting  setEmployeeName  method");

		explicit(txtEmpName);		
		txtEmpName.sendKeys(strEmpName+Keys.ENTER);
		logger.info("Ending  setEmployeeName  method");
	}
	
	public String getEmpName()
	{
		return strEmpName;
	}
	
	public void setUserName()
	{
		logger.info("Starting  setUserName  method");

		explicit(txtUsername);
		int randomNumber = new Random().nextInt(100000) + 1;
		strUserName="EmployeeUser"+randomNumber;	
		txtEmpName.sendKeys(strUserName);

		logger.info("Ending  setUserName  method");
	}
	
	public String getUserName()
	{
		return strUserName;
	}
	
	public void setPassword(String strPassword)
	{
		logger.info("Starting  setPassword  method");

		explicit(txtPassword);
		txtEmpName.sendKeys(strPassword);

		logger.info("Ending  setPassword  method");
	}
	
	public void setConfirmPassword(String strConfirmPassword)
	{
		logger.info("Starting  setConfirmPassword  method");

		explicit(txtConfirmPassword);
		txtEmpName.sendKeys(strConfirmPassword);

		logger.info("Ending  setConfirmPassword  method");
	}
}
