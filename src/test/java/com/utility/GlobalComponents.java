package com.utility;

public class GlobalComponents {

	public static String testDataRegression = "Amazon_Testdata.xlsx";

	public static String downloadFilepath = System.getProperty("user.dir") + "\\TestData\\Downloads";

	
	public static ExcelReader readTestData = new ExcelReader(
			System.getProperty("user.dir") + "\\TestData\\" + testDataRegression);
	
	// Read user name from excel sheet
	public static String getUserName() {
		return readTestData.getkeyData("SignUpDetails", "Key", "Value", "userName");
	}
	// Read Password from excel sheet
	public static String getPwd() {
		return readTestData.getkeyData("SignUpDetails", "Key", "Value", "Password");
	}
	
	
}
