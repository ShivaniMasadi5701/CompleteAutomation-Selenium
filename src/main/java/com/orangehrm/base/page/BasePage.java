package com.orangehrm.base.page;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.annotations.Until;

public class BasePage {
	public  WebDriver driver; //present class driver
	
	public static Logger logger=Logger.getLogger(BasePage.class);
	
	public BasePage(WebDriver driver)
	{
		this.driver=driver;//WebDriver instance from the test   It assigns this.driver so that the class can use it
		PageFactory.initElements(driver, this);//Refers to the current page object class (where the WebElements are defined).
		                                      //This line initializes all the @FindBy elements
	}

	public void clickOnWebElement(WebElement webelement) {
		logger.info("Starting of clickOnWebElement method");

		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].click()", webelement);

		logger.info("Ending of clickOnWebElement method");
	}

	public void scrollIntoView(WebElement element) {
		logger.info("Starting of scrollIntoView method");

		JavascriptExecutor jsExec = (JavascriptExecutor) driver;
		jsExec.executeScript("arguments[0].scrollIntoView(true);", element);

		logger.info("Ending of scrollIntoView method");
	}

	public void clickOutside() {
		logger.info("Starting of clickOutside method");

		Actions action = new Actions(driver);
		action.moveByOffset(0, 0).click().build().perform();

		logger.info("Ending of clickOutside method");
	}

	public void implicitwait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

	}
	public void explicit(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void explicitWait(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	

	public void explicitWait(List<WebElement> element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
	}
	
	public void explicitWaitPresence(WebElement menu) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.presenceOfElementLocated((By) menu));
	}
	
	public void waitForElementToBeClikable(WebElement categoryOptions) {
		logger.info("Starting of explicitWait method");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(categoryOptions));
 
		logger.info("Ending of explicitWait method");
	}	
	
	
	public void fluentWait(WebElement element)
	{
		FluentWait<WebDriver> wait = new FluentWait<>(driver)
				.withTimeout(Duration.ofSeconds(10000))
				.pollingEvery(Duration.ofSeconds(1000))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By) element));

                
	}
	
	/*
	 * public void clickElementsByMatchingText(IWebDriver driver, By element, String
	 * expected, String stepDescription) { try { IList<IWebElement> elements =
	 * driver.FindElements(element);
	 * 
	 * foreach (IWebElement ele in elements) { String Actual = ele.Text; if
	 * (ele.Text.Equals(expected)) { ele.Click(); _test.Pass("Passed : " +
	 * stepDescription, captureScreenshot(driver, filename)); break; } } } catch
	 * (Exception e) { _test.Fail("Failed: " + stepDescription + " " + e.Message,
	 * captureScreenshot(driver, filename)); throw new
	 * InvalidOperationException("Error in click Elements objects." +
	 * Environment.NewLine + e.ToString()); } }
	 */
	
	
	 public void clickElement(WebDriver driver, By element)
	 {
	     try
	     {
	         driver.findElement(element).click();	         
	     }
	     catch (Exception e)
	     {       
	         logger.info( "Exception: at clickElement : " + e.getMessage());	       
	     }
	 }
	 
	 
	public void selectDropdown(WebElement element,String element_text)
	{
		Select select_option=new Select(element);
		select_option.selectByVisibleText(element_text);
	}
	
	public void selectDropdown(WebElement element,int element_index)
	{
		Select select_option=new Select(element);
		select_option.selectByIndex(element_index);
	}
	
	
	
	

}
