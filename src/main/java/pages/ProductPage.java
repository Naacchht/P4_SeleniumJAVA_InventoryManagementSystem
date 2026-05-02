package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import utils.ElementUtils;
import utils.WaitUtils;

public class ProductPage extends BasePage {

	// Header
	private By title = By.xpath("//h1[normalize-space()='Products']");
	private By description = By.xpath("//h1[normalize-space()='Products']/following-sibling::p");

	// Add Product
	private By addBtn = By.xpath("//button[contains(text(),'Add Product')]");
	private By addSection = By.xpath("//h2[normalize-space()='Add Product']");
	private By name = By.xpath("//label[contains(text(),'Name')]/following-sibling::input");
	private By sku = By.xpath("//label[contains(text(),'SKU')]/following-sibling::input");
	private By price = By.xpath("//label[contains(text(),'Price')]/following-sibling::input");
	private By qty = By.xpath("//label[contains(text(),'Quantity')]/following-sibling::input");
	private By status = By.xpath("//select");
	private By createBtn = By.xpath("//button[contains(.,'Create')]");
	private By updateBtn = By.xpath("//button[contains(.,'Update')]");

	// Search
	private By searchBox = By.xpath("//input[contains(@placeholder,'Search by name or SKU')]");
	private By searchStatus = By
			.xpath("//input[contains(@placeholder,'Search by name or SKU')]/following-sibling::select");

	private By toastMsg = By.xpath("//*[@role='status']");

	// Table
	private By rows = By.xpath("//table//tbody/tr");

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	// ---------- Navigation ----------
	public void goToProducts() {
		ElementUtils.click(driver, BasePage.PRODUCTS_MENU);
	}

	// ---------- Validation ----------
	public String getTitle() {
		return ElementUtils.getText(driver, title);
	}

	public String getDescription() {
		return ElementUtils.getText(driver, description);
	}

	public boolean isAddProductSectionVisible() {
		try {
			return WaitUtils.waitForVisibility(driver, addSection).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Add ----------
	public void clickAddProduct() {
		ElementUtils.click(driver, addBtn);
	}

	public void addProduct(String n, String s, String p, String q, String st) {

		ElementUtils.sendKeys(driver, name, n);
		ElementUtils.sendKeys(driver, sku, s);
		ElementUtils.sendKeys(driver, price, p);
		ElementUtils.sendKeys(driver, qty, q);

		new Select(WaitUtils.waitForVisibility(driver, status)).selectByVisibleText(st);
		WaitUtils.slowDownIfNeeded();

		ElementUtils.click(driver, createBtn);
	}

	// ---------- Search ----------
	public void search(String keyword, String statusValue) {
		ElementUtils.sendKeys(driver, searchBox, keyword);
		new Select(WaitUtils.waitForVisibility(driver, searchStatus)).selectByVisibleText(statusValue);
	}

	// ---------- Table ----------
	public int getRowCount() {
		List<WebElement> list = driver.findElements(rows);
		if (list.size() == 1 && list.get(0).getText().toLowerCase().contains("no")) {
			return 0;
		}
		return list.size();
	}

	public boolean isProductPresent(String nameValue, String skuValue) {
		List<WebElement> list = driver.findElements(rows);

		for (WebElement row : list) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.size() >= 2 && cols.get(0).getText().equalsIgnoreCase(nameValue)
					&& cols.get(1).getText().equalsIgnoreCase(skuValue)) {
				return true;
			}
		}
		return false;
	}

	public String[] getRowData(String nameValue, String skuValue) {
		List<WebElement> list = driver.findElements(rows);

		for (WebElement row : list) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.size() >= 5 && cols.get(0).getText().equalsIgnoreCase(nameValue)
					&& cols.get(1).getText().equalsIgnoreCase(skuValue)) {

				return new String[] { cols.get(0).getText(), cols.get(1).getText(), cols.get(2).getText(),
						cols.get(3).getText(), cols.get(4).getText() };
			}
		}
		return new String[] {};
	}

	// ---------- Edit ----------
	public void clickEdit(String nameValue, String skuValue) {
		for (WebElement row : driver.findElements(rows)) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.get(0).getText().equalsIgnoreCase(nameValue) && cols.get(1).getText().equalsIgnoreCase(skuValue)) {

				row.findElement(By.xpath(".//button[normalize-space()='Edit']")).click();
				return;
			}
		}
		throw new RuntimeException("Edit row not found");
	}

	public void updateName(String newName) {
		WebElement field = WaitUtils.waitForVisibility(driver, name);
		field.clear();
		field.sendKeys(newName);
		ElementUtils.click(driver, updateBtn);
		WaitUtils.slowDownIfNeeded();
	}

	// ---------- Delete ----------
	public void delete(String nameValue, String skuValue) {
		for (WebElement row : driver.findElements(rows)) {
			List<WebElement> cols = row.findElements(By.tagName("td"));

			if (cols.get(0).getText().equalsIgnoreCase(nameValue) && cols.get(1).getText().equalsIgnoreCase(skuValue)) {

				row.findElement(By.xpath(".//button[normalize-space()='Delete']")).click();
				driver.switchTo().alert().accept();
				return;
			}
		}
		throw new RuntimeException("Delete row not found");
	}

	public void printProductDetails(String nameValue, String skuValue) {
		int count = getRowCount();
		if (count == 0) {
			utils.LoggerUtils.info("No records found in table");
			return;
		}

		utils.LoggerUtils.info("Records found: " + count);

		String[] data = getRowData(nameValue, skuValue);
		if (data.length == 0) {
			utils.LoggerUtils.info("Matching product not found with Name & SKU");
			return;
		}

		utils.LoggerUtils.info("Product Name: " + data[0]);
		utils.LoggerUtils.info("SKU: " + data[1]);
		utils.LoggerUtils.info("Price: " + data[2]);
		utils.LoggerUtils.info("Quantity: " + data[3]);
		utils.LoggerUtils.info("Status: " + data[4]);
	}

	public String getToastMessage() {
		try {
			return WaitUtils.waitForVisibility(driver, toastMsg).getText();
		} catch (Exception e) {
			return "";
		}
	}
}