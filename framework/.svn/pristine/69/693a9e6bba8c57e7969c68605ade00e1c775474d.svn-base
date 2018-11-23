package com.eViewer.HTML5.Automation.Framework.Common;

import java.lang.reflect.Method;

public class TestContext {

	private static Method testMethod;

	public static void writeMethodName(Method method) {
		testMethod = method;
	}

	public static String getCurrentMethodName() {
		return testMethod.getName();
	}

	public static Method getCurrentTestMethod() {
		return testMethod;
	}

}
