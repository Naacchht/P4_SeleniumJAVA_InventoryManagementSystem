package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.ProductPage;
import utils.LoggerUtils;

public class ProductTest extends BaseTest {

	String name = "Test Widget";
	String sku = "SKU-001";
	String price = "50";
	String qty = "10";

	private ProductPage setupProductPage() {
		LoginPage login = new LoginPage(driver);
		login.login("admin@example.com", "password123");

		ProductPage page = new ProductPage(driver);

		LoggerUtils.step(1, "Click on Product Menu");
		page.goToProducts();
		LoggerUtils.step(2, "Verify Product Page title");
		Assert.assertEquals(page.getTitle(), "Products");
		LoggerUtils.step(3, "Verify Product Page description");
		Assert.assertEquals(page.getDescription(), "Manage your inventory products");
		return page;
	}

	@Test(priority = 1, groups = { "product" }, enabled = true)
	public void addProductTest() {
		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC1 --> Add a new product");
		LoggerUtils.logSeparator();

		ProductPage page = setupProductPage();

		LoggerUtils.step(4, "Click Add Product button");
		page.clickAddProduct();
		LoggerUtils.step(5, "Verify Add Product section");
		Assert.assertTrue(page.isAddProductSectionVisible());
		LoggerUtils.step(6, "Enter Product Name: " + name);
		LoggerUtils.step(7, "Enter SKU: " + sku);
		LoggerUtils.step(8, "Enter Price: " + price);
		LoggerUtils.step(9, "Enter Quantity: " + qty);
		LoggerUtils.step(10, "Select Status: Active");
		page.addProduct(name, sku, price, qty, "Active");
		String toast = page.getToastMessage();
		LoggerUtils.info("Toast Message: " + toast);
		if (toast.toLowerCase().contains("already exists")) {
			LoggerUtils.fail("Duplicate SKU detected → Stopping execution");
			Assert.fail("SKU already exists. Test stopped.");
		}
		LoggerUtils.step(11, "Search product by SKU: " + sku);
		page.search(sku, "Active");
		page.printProductDetails(name, sku);
		boolean exists = page.isProductPresent(name, sku);
		LoggerUtils.info("Product found: " + exists);
		Assert.assertTrue(exists, "Product not found after creation");
		LoggerUtils.pass("TC1 PASSED → Product created successfully");
	}

	@Test(priority = 2, groups = { "product" }, enabled = true)
	public void searchProductTest() {
		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC2 --> Search Product");
		LoggerUtils.logSeparator();

		ProductPage page = setupProductPage();

		LoggerUtils.step(4, "Search product by Name: " + name + " and Status: Active");
		page.search(name, "Active");
		int count = page.getRowCount();
		LoggerUtils.info("Total records found: " + count);
		if (count == 0) {
			LoggerUtils.info("No product found in table");
			Assert.fail("No records found for search");
		}
		page.printProductDetails(name, sku);
		boolean found = page.isProductPresent(name, sku);
		LoggerUtils.info("Product found with Name & SKU: " + found);
		Assert.assertTrue(found, "Product not found in search results");
		LoggerUtils.pass("TC2 PASSED → Search validation successful");
	}

	@Test(priority = 3, groups = { "product" }, enabled = true)
	public void editProductTest() {
		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC3 --> Edit Product");
		LoggerUtils.logSeparator();

		ProductPage page = setupProductPage();
		LoggerUtils.step(4, "Search product for edit: " + name);
		page.search(name, "Active");
		int count = page.getRowCount();
		LoggerUtils.info("Total records found: " + count);
		if (count == 0) {
			LoggerUtils.fail("No product found for editing");
			Assert.fail("No records available for edit");
		}

		page.printProductDetails(name, sku);
		LoggerUtils.step(5, "Click Edit on matching product");
		page.clickEdit(name, sku);
		String updatedName = "Updated Widget";
		LoggerUtils.step(6, "Update product name to: " + updatedName);
		page.updateName(updatedName);
		String toast = page.getToastMessage();
		LoggerUtils.info("Toast Message: " + toast);
		LoggerUtils.step(7, "Search updated product");
		page.search(updatedName, "Active");
		page.printProductDetails(updatedName, sku);
		boolean updated = page.isProductPresent(updatedName, sku);
		LoggerUtils.info("Updated product found: " + updated);
		Assert.assertTrue(updated, "Product was not updated");
		LoggerUtils.pass("TC3 PASSED → Product updated successfully");
	}

	@Test(priority = 4, groups = { "product" }, enabled = true)
	public void deleteProductTest() {
		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC4 --> Delete Product");
		LoggerUtils.logSeparator();

		ProductPage page = setupProductPage();
		String updatedName = "Updated Widget";
		LoggerUtils.step(4, "Search product for delete: " + updatedName);
		page.search(updatedName, "Active");
		int count = page.getRowCount();
		LoggerUtils.info("Total records found: " + count);
		if (count == 0) {
			LoggerUtils.fail("No product found for deletion");
			Assert.fail("No records available for delete");
		}

		page.printProductDetails(updatedName, sku);
		LoggerUtils.step(5, "Click Delete on matching product");
		page.delete(updatedName, sku);
		String toast = page.getToastMessage();
		LoggerUtils.info("Toast Message: " + toast);
		LoggerUtils.step(6, "Verify product is deleted");
		page.search(updatedName, "Active");
		int afterDeleteCount = page.getRowCount();
		LoggerUtils.info("Records after delete: " + afterDeleteCount);
		boolean stillExists = page.isProductPresent(updatedName, sku);
		LoggerUtils.info("Product still exists: " + stillExists);
		Assert.assertFalse(stillExists, "Product still exists after deletion");
		LoggerUtils.pass("TC4 PASSED → Product deleted successfully");
	}
}