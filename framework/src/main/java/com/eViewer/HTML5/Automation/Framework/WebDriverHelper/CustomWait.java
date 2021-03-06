package com.eViewer.HTML5.Automation.Framework.WebDriverHelper;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;
import com.eViewer.HTML5.Automation.Framework.Config.Configuration;
import com.google.common.base.Function;

public class CustomWait {

	private final WebDriverWait wait;
	private final int DEFAULT_TIMEOUT_FOR_EXPLICIT_WAIT = Configuration.getExplicitWaitTimeout();

	public CustomWait(WebDriver driver) {
		this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT_FOR_EXPLICIT_WAIT);
	}

	public WebElement forElementPresent(By by) {
		try {
			return wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			CustomLogger.logError("forElementPresent: " + e.getMessage());
		}
		return null;
	}

	public WebElement forElementVisible(WebElement element) {
		try {
			return wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			CustomLogger.logError("forElementVisible: " + e.getMessage());
		}
		return null;
	}

	public WebElement forElementClickable(WebElement element) {
		try {
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		} catch (Exception e) {
			CustomLogger.logError("forElementClickable: " + e.getMessage());
		}
		return null;
	}

	public Alert forAlertPresent() {
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public boolean forTitleIs(String title) {
		return wait.until(ExpectedConditions.titleIs(title));
	}

	public boolean forWindowCountToBe(int count) {
		return wait.until(ExpectedConditions.numberOfWindowsToBe(count));
	}

	public boolean forElementTextToBePresentInElement(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public void forFrameToBeAvailableAndSwitchToIt(WebElement frame) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
	}

	// public boolean forNewWindowPresent() {
	// Function<WebDriver, Boolean> newWindowPresent = (WebDriver d) -> {
	// Object[] windows = d.getWindowHandles().toArray();
	// return windows.length > 1;
	// };
	//
	// return wait.until(newWindowPresent);
	// }

	public boolean forTitleNotEquals(String title) {
		Function<WebDriver, Boolean> titleNotEquals = (WebDriver d) -> {
			return !StringUtils.equals(title, d.getTitle());
		};

	return wait.until(titleNotEquals);
		
	}

	public boolean forTitleIsNotEmpty() {
		Function<WebDriver, Boolean> titleNotEmpty = (WebDriver d) -> {
			return StringUtils.isNotBlank(d.getTitle());
		};

		return wait.until(titleNotEmpty);
	}

	public boolean forElementSizeToBe(List<WebElement> elementList, int size) {
		Function<WebDriver, Boolean> loadCompletely = (WebDriver d) -> {
			return elementList.size() == size ? true : false;
		};

		return wait.until(loadCompletely);
	}

	public boolean forDocumentLoadCompletely(List<WebElement> elementList, int allPages) {
		return forElementSizeToBe(elementList, allPages);
	}

	public boolean forElementNotVisible(WebElement element) {
		Function<WebDriver, Boolean> elementNotVisible = (WebDriver d) -> {
			try {
				return !element.isDisplayed();
			} catch (NoSuchElementException e) {
				return true;
			} catch (StaleElementReferenceException e) {
				return true;
			} catch (WebDriverException e) {
				return true;
			}
		};

		return wait.until(elementNotVisible);
	}

	public void forSeconds(int timeOut) {
		try {
			Thread.sleep(timeOut * 1000);
		} catch (InterruptedException e) {
			CustomLogger.logError("forSeconds: " + e.getMessage());
		}
	}

}
