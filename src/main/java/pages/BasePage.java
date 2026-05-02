package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {

	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	public static final By PRODUCTS_MENU = By.xpath("//a[normalize-space()='Products' and @href='/products']");
}