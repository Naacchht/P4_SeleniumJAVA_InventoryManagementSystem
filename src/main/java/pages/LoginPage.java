package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ElementUtils;

public class LoginPage extends BasePage {

	private By email = By.id("email");
	private By password = By.id("password");
	private By loginBtn = By.xpath("//button[@type='submit']");
	private By toastMsg = By.xpath("//*[@role='status']");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public DashboardPage login(String user, String pass) {
		ElementUtils.sendKeys(driver, email, user);
		ElementUtils.sendKeys(driver, password, pass);
		ElementUtils.click(driver, loginBtn);
		return new DashboardPage(driver);
	}

	public String getToastMessage() {
		return ElementUtils.getText(driver, toastMsg);
	}
}