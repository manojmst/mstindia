package com.eViewer.HTML5.Automation.Framework.WebDriverHelper;

import java.util.List;

import org.openqa.selenium.WebElement;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class CustomLocator {

	public WebElement getElement(List<WebElement> elements) {
		WebElement foundElement = null;

		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				foundElement = element;
			}
		}

		if (foundElement == null) {
			CustomLogger.logWarning("getElement: element not found in the list");
		}

		return foundElement;
	}
	
	public WebElement getSingleElement(WebElement element) {
		WebElement foundElement = null;

			if (element.isDisplayed()) {
				foundElement = element;
			}
		

		if (foundElement == null) {
			CustomLogger.logWarning("getElement: element not found in the list");
		}

		return foundElement;
	}

	public WebElement getElementByValue(List<WebElement> elements, String attribute, String value) {
		WebElement foundElement = null;

		for (WebElement element : elements) {
			if (element.getAttribute(attribute).equals(value))
				foundElement = element;
		}

		if (foundElement == null) {
			CustomLogger.logWarning("getElementByValue: element with attribute [" + attribute.toUpperCase()
					+ "] and value [" + value + "] is not found in the list");
		}
		return foundElement;
	}

	public WebElement getElementByText(List<WebElement> elements, String text) {
		WebElement foundElement = null;

		for (WebElement element : elements) {
			if (element.getText().equalsIgnoreCase(text))
				foundElement = element;
		}

		if (foundElement == null) {
			CustomLogger.logWarning("getElementByText: element with text [" + text + "] is not found in the list");
		}
		return foundElement;
	}

	
	public WebElement getElementContainingText(List<WebElement> elements, String text) {
		WebElement foundElement = null;

		for (WebElement element : elements) {
			if (element.getText().contains(text))
				foundElement = element;
		}

		if (foundElement == null) {
			CustomLogger.logWarning("getElementByText: element with text [" + text + "] is not found in the list");
		}
		return foundElement;
	}

}
