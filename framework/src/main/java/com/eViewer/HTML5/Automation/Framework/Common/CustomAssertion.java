package com.eViewer.HTML5.Automation.Framework.Common;

import org.testng.Assert;

public class CustomAssertion {

	private static String failureMessage = "";

	public static <T> boolean assertEquals(T actual, T expected, String message) {
		try {
			Assert.assertEquals(actual, expected, message);
		} catch (AssertionError e) {
			failureMessage = e.getMessage();
			CustomLogger.logError("assertEquals: check failed, " + failureMessage);
			return false;
		}
		return true;
	}

	public static <T> boolean assertEquals(T actual, T expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			CustomLogger.logError("assertEquals: check failed, " + e.getMessage());
			return false;
		}
		return true;
	}

	public static <T> boolean assertNotEquals(T actual, T expected, String message) {
		try {
			Assert.assertNotEquals(actual, expected,
					message + " expected [" + expected + "] while found is [" + actual + "]");
		} catch (AssertionError e) {
			failureMessage = e.getMessage();
			CustomLogger.logError("assertNotEquals: check failed, " + failureMessage);
			return false;
		}
		return true;
	}

	public static boolean assertTrue(boolean condition, String message) {
		try {
			Assert.assertTrue(condition, message);
		} catch (AssertionError e) {
			failureMessage = e.getMessage();
			CustomLogger.logError("assertTrue: check failed, " + failureMessage);
			return false;
		}
		return true;
	}

	public static boolean assertFalse(boolean condition, String message) {
		try {
			Assert.assertFalse(condition, message);
		} catch (AssertionError e) {
			failureMessage = e.getMessage();
			CustomLogger.logError("assertFalse: check failed, " + failureMessage);
			return false;
		}
		return true;
	}

	public static boolean assertStringContains(String actualString, String expectedToContain) {
		try {
			if (!actualString.contains(expectedToContain)) {
				throw new AssertionError(
						"expected [" + expectedToContain + "] to be present in [" + actualString + "]");
			}
		} catch (AssertionError e) {
			failureMessage = e.getMessage();
			CustomLogger.logError("assertStringContains: check failed, " + failureMessage);
			return false;
		}
		return true;
	}

	public static void assertTestPass(boolean actual, boolean expected) {
		if (!assertEquals(actual, expected)) {
			throw new AssertionError(
					"assertTestPass: final check for test case failed. Failure reason: " + failureMessage);
		}
	}

}
