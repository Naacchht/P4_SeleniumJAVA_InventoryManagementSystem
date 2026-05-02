package utils;

import org.testng.Reporter;

public class LoggerUtils {

	public static void logSeparator() {
		Reporter.log("========================================", true);
	}

	public static void tc(String message) {
		Reporter.log(message, true);
	}

	public static void step(int step, String message) {
		Reporter.log(step + ") " + message, true);
	}

	public static void info(String message) {
		Reporter.log("   → " + message, true);
	}

	public static void pass(String message) {
		Reporter.log("   ✅ " + message, true);
	}

	public static void fail(String message) {
		Reporter.log("   ❌ " + message, true);
	}
}