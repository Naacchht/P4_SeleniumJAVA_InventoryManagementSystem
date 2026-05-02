package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtils {

	public static void click(WebDriver driver, By locator) {
		WaitUtils.waitForClickable(driver, locator).click();
		WaitUtils.slowDownIfNeeded();
	}

	public static void sendKeys(WebDriver driver, By locator, String value) {
		WebElement el = WaitUtils.waitForVisibility(driver, locator);
		el.clear();
		el.sendKeys(value);
		WaitUtils.slowDownIfNeeded();
	}

	public static String getText(WebDriver driver, By locator) {
		return WaitUtils.waitForVisibility(driver, locator).getText();
	}
}