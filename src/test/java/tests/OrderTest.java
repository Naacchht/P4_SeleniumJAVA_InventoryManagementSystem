package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.LoginPage;
import pages.OrderPage;
import utils.LoggerUtils;

public class OrderTest extends BaseTest {

	@Test(priority = 1, groups = { "order" }, enabled = true)
	public void createAndVerifyOrderTest() {

		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC1 --> Create and Verify Order");
		LoggerUtils.logSeparator();

		LoginPage login = new LoginPage(driver);
		login.login("admin@example.com", "password123");

		OrderPage order = new OrderPage(driver);

		// Step 1
		LoggerUtils.step(1, "Navigate to Orders page");
		order.goToOrders();

		// Step 2
		LoggerUtils.step(2, "Click Create Order");
		order.clickCreateOrder();

		// Step 3
		String baseProduct = "Cranabbies";
		LoggerUtils.step(3, "Select product: " + baseProduct);

		String selectedFullText = order.selectProduct(baseProduct);
		LoggerUtils.info("Selected Product Full Text: " + selectedFullText);

		String productName = selectedFullText.split("\\(")[0].trim();
		LoggerUtils.info("Extracted Product Name: " + productName);

		// Step 4
		LoggerUtils.step(4, "Enter quantity = 1");
		order.enterQty("1");

		// Step 5
		LoggerUtils.step(5, "Click Create Order");
		order.submitOrder();

		// Step 6 → Toast handling
		String toast = order.getToastMessage();
		LoggerUtils.info("Alert Toast Message: " + toast);
		if (toast.toLowerCase().contains("insufficient")) {
			LoggerUtils.fail("Order failed due to stock issue");
			Assert.fail("Insufficient stock");
		}

		boolean toastSuccess = toast.toLowerCase().contains("order created successfully");
		boolean pageLoaded = order.isOrdersPageLoaded();

		LoggerUtils.info("Toast success: " + toastSuccess);
		LoggerUtils.info("Orders page loaded: " + pageLoaded);

		Assert.assertTrue(toastSuccess || pageLoaded, "Order creation failed: No toast or page confirmation");
		LoggerUtils.step(6, "Validate success confirmation (toast/page)");

		// Step 7
		LoggerUtils.step(7, "Verify user is navigated to Orders Dashboard");
		Assert.assertTrue(pageLoaded, "User not navigated to Orders page");

		// Step 8 → Correct Validation
		LoggerUtils.step(8, "Verify latest order is created and visible");
		order.printLatestOrderCard();
		boolean latestOrderVisible = order.isLatestOrderVisible();
		LoggerUtils.info("Latest order card visible: " + latestOrderVisible);
		Assert.assertTrue(latestOrderVisible, "Latest order not visible after creation");
		LoggerUtils.pass("TC1 PASSED → Order created and verified successfully");
	}

	@Test(priority = 2, groups = { "order" }, enabled = true)
	public void verifyOrderTest() {

		LoggerUtils.logSeparator();
		LoggerUtils.tc("TC2 --> Verify Latest Order in List");
		LoggerUtils.logSeparator();

		LoginPage login = new LoginPage(driver);
		login.login("admin@example.com", "password123");

		OrderPage order = new OrderPage(driver);

		// Step 1
		LoggerUtils.step(1, "Navigate to Orders page");
		order.goToOrders();

		// Step 2
		LoggerUtils.step(2, "Wait for Orders to load");
		order.waitForOrdersToLoad();

		// Step 3
		LoggerUtils.step(3, "Verify latest order card is visible");
		order.printLatestOrderCard();
		boolean latestOrderVisible = order.isLatestOrderVisible();
		LoggerUtils.info("Latest order visible: " + latestOrderVisible);
		Assert.assertTrue(latestOrderVisible, "No orders found in list");
		LoggerUtils.pass("TC2 PASSED → Latest order verified successfully");
	}
}