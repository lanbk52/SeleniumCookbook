package com.secookbook.examples.chapter01;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;
import static org.junit.Assert.*;

public class LoginLogoutTest {
	private WebDriver driver;

	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "..\\..\\geckodriver.exe");
		// Launch a new Firefox instance
		driver = new FirefoxDriver();
		// Maximize the browser window
		driver.manage().window().maximize();
		// Navigate to Google
		driver.get("http://10.15.12.148:8080");
		// wait for loading the page
		WebDriverWait wait = new WebDriverWait(driver, 4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("email"))));
	}

	@Test
	public void login2Success() {
		// find the email and password of user
		WebElement email = driver.findElement(By.id("email"));
		email.clear();
		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		// enter the matched email and password
		email.sendKeys("admin");
		password.sendKeys("admin");
		// find the submit button and login
		WebElement submitBtn = driver.findElement(By.className("md-btn-primary"));
		submitBtn.submit();
		// wait for logging in
		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("dashboard");
			}
		});
		// check if testcase login is passed
		assertEquals("Dashboard", driver.getTitle());
		
		// wait for loading the page
		WebDriverWait wait = new WebDriverWait(driver, 4);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.className("user_action_image"))));
		// click the avatar to show logout link
		WebElement userAvatar = driver.findElement(By.className("user_action_image"));
		userAvatar.click();
		// Logout
		WebElement logoutParent = driver.findElement(By.className("uk-dropdown-bottom"));
		WebElement logout = logoutParent.findElement(By.linkText("Logout"));
		logout.click();
		// wait for logging out
		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("login");
			}
		});
		// check if testcase logout is passed
		assertEquals("Login", driver.getTitle());
	}
	
	@Test
	public void login1Fail() {
		// find the email and password of user
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));
		// enter the matched email and password
		email.sendKeys("admin");
		password.sendKeys("xxx");
		// find the submit button and login
		WebElement submitBtn = driver.findElement(By.className("md-btn-primary"));
		submitBtn.submit();
		// check if testcase login fail is passed
		assertEquals("Login", driver.getTitle());
	}
	/* start google search test
	@Before
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "..\\..\\geckodriver.exe");
		// Launch a new Firefox instance
		driver = new FirefoxDriver();
		// Maximize the browser window
		driver.manage().window().maximize();
		// Navigate to Google
		driver.get("http://www.google.com");
	}
	
	@Test
	public void testGoogleSearch() {
		// Find the text input element by its name
		WebElement element = driver.findElement(By.id("email"));
		// Clear the existing text value
		element.clear();
		// Enter something to search for
		element.sendKeys("Selenium testing tools cookbook");
		// Now submit the form
		element.submit();
		// Google's search is rendered dynamically with JavaScript.
		// wait for the page to load, timeout after 10 seconds
		new WebDriverWait(driver, 10).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				return d.getTitle().toLowerCase().startsWith("selenium testing tools cookbook");
			}
		});

		assertEquals("Selenium testing tools cookbook - Tìm với Google", driver.getTitle());
	}
	*/ //end google search test
	@After
	public void tearDown() throws Exception {
		// Close the browser
		driver.quit();
	}
}
