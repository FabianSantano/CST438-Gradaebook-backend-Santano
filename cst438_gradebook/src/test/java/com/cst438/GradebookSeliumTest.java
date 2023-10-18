package com.cst438;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GradebookSeliumTest {
	public static final String CHROME_DRIVER_FILE_LOCATION =
            "/Users/santa/Downloads/chromedriver-mac-arm64/chromedriver";
public static final String URL = "http://localhost:3000";
public static final String ALIAS_NAME = "test";
public static final int SLEEP_DURATION = 1000; // 1 second.
	WebDriver driver;
	@BeforeEach
	public void testSetup() throws Exception {
             // if you are not using Chrome, 
             // the following lines will be different. 
		System.setProperty(
                 "webdriver.chrome.driver", 
                 CHROME_DRIVER_FILE_LOCATION);
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(ops);


		driver.get(URL);
        // must have a short wait to allow time for the page to download 
		Thread.sleep(SLEEP_DURATION);
	}
	@Test
	public void addAssignmentTest() throws Exception {

		
		WebElement w = driver.findElement(By.xpath("//a[@href='/createAssignment']"));
		w.click();
		WebElement courseID = driver.findElement(By.name("courseId"));
		WebElement Date = driver.findElement(By.name("dueDate"));
		WebElement assignmentName = driver.findElement(By.name("assignmentName"));
		WebElement sgrade = driver.findElement(By.id("sgrade"));
		
		courseID.sendKeys("31045");
		Date.sendKeys("01/21/2001");
		assignmentName.sendKeys("AddAssignemntTest");
		sgrade.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement gmessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gmessage")));
		assertEquals("Assignment saved. ", gmessage.getText());
		//driver.quit();
		
	}
	@Test
	public void updateAssignmentTest() throws Exception {

		
		WebElement w = driver.findElement(By.xpath("//a[@href='/updateAssignment/1']"));
		w.click();
		Thread.sleep(SLEEP_DURATION);
		

		WebElement Date = driver.findElement(By.name("dueDate"));
		WebElement assignmentName = driver.findElement(By.name("assignmentName"));
		WebElement sgrade = driver.findElement(By.id("sgrade"));
		assignmentName.clear();
		assignmentName.sendKeys("SelTestUpdate");
		Date.sendKeys("01/21/2001");
		

		sgrade.click();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement gmessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gmessage")));
		assertEquals("Assignment saved. ", gmessage.getText());
	
		//driver.quit();
		
	}
	@Test
	public void deleteAssignmentTest() throws Exception {
		
		//Deletes assignment 5 which is new and has no grades inputed.
		WebElement message = driver.findElement(By.id("message"));
		WebElement w = driver.findElement(By.id("dAssignment5"));
		w.click();
		Thread.sleep(SLEEP_DURATION);
		assertEquals("Assignment deleted. ",  message.getText());
		
		//Deletes assignment 1 which has grades inputed so it needs a force delete
		w = driver.findElement(By.id("dAssignment1"));
		w.click();
		Thread.sleep(SLEEP_DURATION);
        Alert simpleAlert = driver.switchTo().alert();
        simpleAlert.accept();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		message = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
		assertEquals("Assignment deleted. ",  message.getText());
		
		//driver.quit();
		
	}

	@AfterEach
	public void cleanup() {
		if (driver!=null) {
			driver.close();
			driver.quit();
			driver=null;
		}
	}

}
