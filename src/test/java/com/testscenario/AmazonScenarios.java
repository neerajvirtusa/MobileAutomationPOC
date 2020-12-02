package com.testscenario;

import java.net.URL;

import org.aspectj.lang.annotation.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.loginlocators.LoginLocators;
import com.utility.GlobalComponents;
import com.utility.UtilitMethods;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;

public class AmazonScenarios {
	
	static AppiumDriver<WebElement> driver;

	
	@BeforeTest
	public static void openRegisteration() throws Exception
	{
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setCapability("deviceName", "Galaxy M31");
		cap.setCapability("udid", "RZ8N721Y9MP");
		cap.setCapability("platformName", "Android");
		cap.setCapability(AndroidMobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability("platformVersion", "10");
		cap.setCapability("appPackage", "in.amazon.mShop.android.shopping");
		cap.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		
		URL  url=new URL("http://127.0.0.1:4723/wd/hub");
		driver=new AppiumDriver<WebElement>(url,cap);
		System.out.println("Application started...");
		
	}
	
	@Test	
	public void selectLanguage() {
		
		UtilitMethods.click1(LoginLocators.language_Start, "Select Language" );
		UtilitMethods.click1(LoginLocators.already_Signin, "Already Signin" );
	
		}
		
	@Test	
	public void loginApp() {
		
		UtilitMethods.setText(LoginLocators.userName,GlobalComponents.getUserName() ,"Enter Username" );
		UtilitMethods.setText(LoginLocators.Password,GlobalComponents.getPwd() ,"Enter Password" );
		UtilitMethods.click1(LoginLocators.click, "Signin" );
	
		}	
	
	@AfterTest
	public void closeApp()
	{
		driver.close();
	}
		
		
	}
	
