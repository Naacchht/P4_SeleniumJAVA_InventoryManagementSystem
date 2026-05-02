package utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	private static final int TIMEOUT = 30;

	public static void slowDownIfNeeded() {
		String speed = ConfigReader.get("executionSpeed");
		if ("slow".equalsIgnoreCase(speed)) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
		}
	}

	public static WebElement waitForVisibility(WebDriver driver, By locator) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement waitForClickable(WebDriver driver, By locator) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
				.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static WebElement waitForPresence(WebDriver driver, By locator) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
				.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static boolean waitForText(WebDriver driver, By locator, String text) {
		return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT))
				.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}
}