package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ElementUtils;
import utils.LoggerUtils;
import utils.WaitUtils;

public class OrderPage extends BasePage {

	// Locators
	private By ordersMenu = By.xpath("//a[normalize-space()='Orders']");
	private By createOrderBtn = By.xpath("//button[contains(text(),'Create Order')]");
	private By productDropdown = By.xpath("//select");
	private By qtyField = By.xpath("//input[@type='number']");
	private By createBtn = By.xpath("//button[@type='submit']");
	private By toast = By.xpath("//*[@role='status']");
	private By orderCards = By.xpath("//div[contains(@class,'rounded')]");
	private By ordersTitle = By.xpath("//h1[normalize-space()='Orders']");

	public OrderPage(WebDriver driver) {
		super(driver);
	}

	// ---------- Navigation ----------
	public void goToOrders() {
		ElementUtils.click(driver, ordersMenu);
	}

	// ---------- Create Order ----------
	public void clickCreateOrder() {
		ElementUtils.click(driver, createOrderBtn);
	}

	public String selectProduct(String productName) {
		Select select = new Select(WaitUtils.waitForVisibility(driver, productDropdown));

		for (WebElement option : select.getOptions()) {
			String text = option.getText();

			if (text.toLowerCase().contains(productName.toLowerCase())) {
				select.selectByVisibleText(text);
				return text; // return selected full text
			}
		}

		throw new RuntimeException("Product not found: " + productName);
	}

	public void enterQty(String qty) {
		ElementUtils.sendKeys(driver, qtyField, qty);
	}

	public void submitOrder() {
		WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(createBtn));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}

	public void waitForOrdersToLoad() {
		WaitUtils.waitForVisibility(driver, By.xpath("//div[contains(@class,'rounded')]"));
	}

	// ---------- Toast ----------
	public String getToastMessage() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
			return wait.until(d -> d.findElement(toast)).getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ---------- Page Validation ----------
	public boolean isOrdersPageLoaded() {
		try {
			return WaitUtils.waitForVisibility(driver, ordersTitle).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Order Cards ----------
	public void printLatestOrderCard() {
		List<WebElement> cards = driver.findElements(orderCards);
		if (cards.isEmpty()) {
			LoggerUtils.info("No orders found on page");
			return;
		}
		LoggerUtils.info("Total Orders Found: " + cards.size());
		WebElement latest = cards.get(0);
		LoggerUtils.info("Latest Order Card → " + latest.getText());
	}

	public WebElement getLatestOrderCard() {
		return driver.findElements(orderCards).get(0);
	}

	public boolean isLatestOrderVisible() {
		try {
			return getLatestOrderCard().isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}