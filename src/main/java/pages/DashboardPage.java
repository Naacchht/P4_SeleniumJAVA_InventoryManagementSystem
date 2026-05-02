package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ElementUtils;

public class DashboardPage extends BasePage {

	private By dashboardText = By.xpath("//a[normalize-space()='Inventory Pro']");

	public DashboardPage(WebDriver driver) {
		super(driver);
	}

	public String getDashboardTitle() {
		return ElementUtils.getText(driver, dashboardText);
	}

	public boolean isDashboardLoaded() {
		return getDashboardTitle().contains("Inventory Pro");
	}
}