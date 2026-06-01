package com.orangehrm.util.DataProvider;

import org.testng.annotations.DataProvider;

public class BrowserDataProvider {
	
	   @DataProvider(name = "browserData", parallel = true)
	    public Object[][] getBrowserData() {
	        return new Object[][] {
	            {"chrome"},		          
	            {"edge"}
	        };
	    }
	   
	   

}
