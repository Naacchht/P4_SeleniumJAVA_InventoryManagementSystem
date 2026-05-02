package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.LoggerUtils;
import utils.WaitUtils;

public class LoginTest extends BaseTest {

	@Test(priority = 1, groups = { "login" }, enabled = true)
	public void validLoginTest() {

		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC1 --> Login with valid credentials");
		LoggerUtils.logSeparator();

		LoginPage login = new LoginPage(driver);

		LoggerUtils.step(1, "Enter Email: " + ConfigReader.get("validEmail"));
		LoggerUtils.step(2, "Enter Password: ********");
		LoggerUtils.step(3, "Click Login button");

		DashboardPage dashboard = login.login(ConfigReader.get("validEmail"), ConfigReader.get("validPassword"));
		LoggerUtils.step(4, "Verify Dashboard page is displayed");
		boolean isLoaded = dashboard.isDashboardLoaded();
		LoggerUtils.info("Dashboard loaded: " + isLoaded);
		Assert.assertTrue(isLoaded, "User not navigated to dashboard page");
		LoggerUtils.pass("TC1 PASSED → Login successful and Dashboard displayed");
	}

	@Test(priority = 2, groups = { "login" }, enabled = true)
	public void invalidPasswordTest() {

		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC2 --> Login with invalid password");
		LoggerUtils.logSeparator();

		LoginPage login = new LoginPage(driver);

		LoggerUtils.step(1, "Enter Email: " + ConfigReader.get("validEmail"));
		LoggerUtils.step(2, "Enter Password: wrongpass");
		LoggerUtils.step(3, "Click Login button");

		login.login(ConfigReader.get("validEmail"), ConfigReader.get("invalidPassword"));

		LoggerUtils.step(4, "Capture alert toast message");
		String msg = login.getToastMessage();
		LoggerUtils.info("Actual Message: " + msg);
		boolean isErrorDisplayed = msg.toLowerCase().contains("password is wrong");
		LoggerUtils.info("Error message validation: " + isErrorDisplayed);
		Assert.assertTrue(isErrorDisplayed, "Error message not displayed correctly");
		LoggerUtils.pass("TC2 PASSED → Invalid password rejected with proper message");
	}

	@Test(priority = 3, groups = { "login" }, enabled = true)
	public void emptyFieldsTest() {

		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC3 --> Login with empty fields");
		LoggerUtils.logSeparator();

		LoginPage login = new LoginPage(driver);

		LoggerUtils.step(1, "Leave Email field empty");
		LoggerUtils.step(2, "Leave Password field empty");
		LoggerUtils.step(3, "Click Login button");

		login.login("", "");

		By emailField = By.id("email");
		LoggerUtils.step(4, "Capture browser validation message");
		String validationMessage = (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
				"return arguments[0].validationMessage;", WaitUtils.waitForVisibility(driver, emailField));
		LoggerUtils.info("Validation Message: " + validationMessage);
		boolean isValidationCorrect = validationMessage.toLowerCase().contains("please fill");
		LoggerUtils.info("Validation working: " + isValidationCorrect);
		Assert.assertTrue(isValidationCorrect, "Expected field validation message for empty fields");
		LoggerUtils.pass("TC3 PASSED → Empty fields validation working correctly");
	}
}