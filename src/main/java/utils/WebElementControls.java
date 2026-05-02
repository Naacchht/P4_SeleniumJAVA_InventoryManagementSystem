package utils;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class WebElementControls {

	private static final int DEFAULT_TIMEOUT = 10;

	public static void AlertControl(WebDriver driver, String clickOK_CANCEL) {
		try {
			Alert alert = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
					.until(ExpectedConditions.alertIsPresent());
			String alertText = alert.getText().trim();
			Reporter.log("Alert text   = " + alertText, true);
			if (clickOK_CANCEL.equalsIgnoreCase("Ok")) {
				alert.accept();
				Reporter.log("Alert action = OK clicked", true);
			} else {
				alert.dismiss();
				Reporter.log("Alert action = Cancel clicked", true);
			}
		} catch (Exception e) {
			Reporter.log("Alert not present or failed: " + e.getMessage(), true);
		}
	}

	// Get alert text only
	public static String getAlertText(WebDriver driver) {
		try {
			Alert alert = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
					.until(ExpectedConditions.alertIsPresent());
			return alert.getText();
		} catch (Exception e) {
			System.out.println("Alert not present: " + e.getMessage());
			return "";
		}
	}
}