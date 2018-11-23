package com.eViewer.HTML5.Automation.Framework.Common;

import java.lang.reflect.Method;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class CustomLogger {

	private static Logger logger;

	public static void initLogger(String loggerName) {
		logger = Logger.getLogger(loggerName);
		logger.addAppender(new ConsoleAppender(new PatternLayout("%d{dd-MM-yyyy HH:mm:ss} %-5p %c{1}:%L - %m%n")));
	}

	public static void logInfo(String message) {
		logger.info(message);
	}

	public static void logWarning(String message) {
		logger.warn(message);
	}

	public static void logError(String message) {
		logger.error(message);
	}

	public static void startTestCase(Method testMethod) {
		logger.info("--------------------------------------------------------------------------------------");
		logger.info("			" + testMethod.getDeclaringClass().getSimpleName() + "_" + testMethod.getName());
		logger.info("--------------------------------------------------------------------------------------");
	}

	public static void writeTestDataInfo(String testData) {
		logger.info(
				TestContext.getCurrentMethodName() + ": test started for [" + FilenameUtils.getName(testData) + "]");
	}

	public static void endTestCase(Method testMethod) {
		logger.info("--------------------------------------------------------------------------------------");
		logger.info("			End Of " + testMethod.getDeclaringClass().getSimpleName() + "_" + testMethod.getName());
		logger.info("--------------------------------------------------------------------------------------");
	}

	public static void endLogger() {
		if (logger != null) {
			logger = null;
		}
	}

}
