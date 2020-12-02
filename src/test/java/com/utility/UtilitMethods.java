package com.utility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class UtilitMethods {
	private static WebDriver driver = null;
	private static String dateFormat = "dd_MM_yyyy";
	protected static String imgeHtmlPath;
	protected static ExtentTest extentTest;
	protected static String extentDate = new SimpleDateFormat(dateFormat).format(new Date());
	
	protected static String extentReportPath = System.getProperty("user.dir") + "\\LogsAndReports\\Reports_"
			+ extentDate + "\\Reports_" + extentDate;
	protected static ExtentReports extentReport = new ExtentReports(extentReportPath + ".html", true);
	static WebDriverWait getWait=new WebDriverWait(driver,20);

	public static void takeScreenshot(String SSname) {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {

			String screenShotPath = System.getProperty("user.dir") + "\\LogsAndReports\\Reports_" + extentDate
					+ "\\Screenshots\\";
			FileUtils.copyFile(scrFile, new File(screenShotPath + SSname + ".png"));
			imgeHtmlPath = extentTest.addScreenCapture(screenShotPath + SSname + ".png")
					.replace(screenShotPath, "Screenshots\\").replace("file:///", "")
					.replace("<img", "<img width=\"150\" height=\"70\"");

		} catch (IOException e) {
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Exception occur during take ScreenShot: " + SSname);
		}
		System.out.println("Screenshot has been generated for " + SSname);

	}
	public static void click1(By element, String elementInfo) {
		try {
			
			getWait.until(ExpectedConditions.elementToBeClickable(element));
			driver.findElement(element).click();
			System.out.println("Clicked successfully on: " + elementInfo);
			extentTest.log(LogStatus.INFO, "Clicked successfully on: " + elementInfo);
		} catch (Exception e) {
			System.out.println("Not able to click on: " + elementInfo);
			takeScreenshot(elementInfo.replaceAll(" ", "_"));
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to click on: " + elementInfo);
		}
	}
	
	public static void setText(By element, String inputText, String elementInfo) {
		try {
			
			getWait.until(ExpectedConditions.visibilityOfElementLocated(element));
			driver.findElement(element).sendKeys(inputText);
			System.out.println("Text entered successfully in: " + elementInfo);
			extentTest.log(LogStatus.INFO, "Text entered successfully in: " + elementInfo);
		} catch (Exception e) {
			System.out.println("Not able to enter text in: " + elementInfo);
			takeScreenshot(elementInfo.replaceAll(" ", "_"));
			throw new java.lang.RuntimeException("RUNTIME_ERROR : : Not able to enter text in: " + elementInfo);
		}
	}


}
