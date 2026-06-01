package com.orangehrm.util.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DataProviderExcelUtil 
	//C:\Users\DELL\eclipse-workspace\eclipse-HONO\automation-web-orangehrm\src\test\resources	
	{

	public static Object[][] getExcelData(String filePath, String sheetName) {
	    Object[][] data = null;

	    try (FileInputStream fis = new FileInputStream(new File(filePath));
	         Workbook workbook = new XSSFWorkbook(fis)) {
	        Sheet sheet = workbook.getSheet(sheetName);

	        if (sheet == null) {
	            throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
	        }

	        int rowCount = sheet.getLastRowNum();  // `getLastRowNum()` ignores empty trailing rows
	        int colCount = sheet.getRow(0).getLastCellNum(); // Get column count from the first row

	        data = new Object[rowCount][colCount];

	        for (int i = 1; i <= rowCount; i++) {  // Start from 1 to skip the header
	            Row row = sheet.getRow(i);

	            for (int j = 0; j < colCount; j++) {
	                if (row == null) {
	                    data[i - 1][j] = "";  // If row is empty, set empty string
	                } else {
	                    Cell cell = row.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	                    data[i - 1][j] = cell.toString().trim();
	                }
	            }
	        }
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to read Excel file: " + e.getMessage(), e);
	    }
	    return data;
	}
	    
	    @DataProvider(name = "excelDataProvider")
	    public Object[][] fetchDataFromExcel() {
	        String filePath = "C:\\Users\\DELL\\eclipse-workspace\\eclipse-HONO\\automation-web-orangehrm\\src\\test\\resources\\login.xlsx";  // Adjust path as needed
	        return getExcelData(filePath, "login");
	    }
	    
	    @DataProvider(name = "loginData")
	    public Object[][] getLoginData() {
	        return new Object[][] {
	            {"admin", "admin12"},
	            {"admin123", "admin123"},
	            {"admin", "admin123"}
	        };
	    }
	}

