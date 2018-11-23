package com.eViewer.HTML5.Automation.Framework.WebDriverHelper;

import java.util.Objects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;
import com.google.common.base.Function;

public class JavascriptActions {

	private final JavascriptExecutor js;
	private final WebDriver driver;

	public JavascriptActions(WebDriver driver) {
		js = (JavascriptExecutor) driver;
		this.driver = driver;
	}

	public void click(WebElement element) {
		js.executeScript("$(arguments[0])[0].click()", element);
	}

	public void enterText(String idLocator, String text) {
		js.executeScript(String.format("document.getElementById('%s').value='%s'", idLocator, text));
	}

	public void focus(WebElement element) {
		js.executeScript("$(arguments[0]).focus()", element);
	}

	public void mouseOver(WebElement element) {
		js.executeScript("$(arguments[0]).mouseenter()", element);
	}

	public void scrollBy(int x, int y) {
		js.executeScript("window.scrollBy(arguments[0], arguments[1])", x, y);
	}

	public void scrollToBottom() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollToTop() {
		js.executeScript("window.scrollTo(0, 0)");
	}

	public void scrollElementIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	public String executeScript(String script) {
		Object value = null;
		try {
			waitForJavascriptToLoad();
			value = js.executeScript("return " + script);
		} catch (Exception e) {
			CustomLogger.logError("executeScript: " + e.getMessage());
		}
		return Objects.nonNull(value) ? value.toString() : "";
	}

	public String executeASyncScript(String script) {
		Object value = null;
		try {
			waitForJavascriptToLoad();
			value = js.executeAsyncScript(script);
		} catch (Exception e) {
			CustomLogger.logError("executeASyncScript: " + e.getMessage());
		}
		return Objects.nonNull(value) ? value.toString() : "";
	}

	public String getWindowErrors() {
		return (String) js.executeScript("return window.errors || ''");
	}

	private boolean waitForJavascriptToLoad() {
		WebDriverWait wait = new WebDriverWait(driver, 10);

		// wait for jQuery to load
		Function<WebDriver, Boolean> jQueryLoad = (WebDriver d) -> {
			try {
				return ((Long) js.executeScript("return jQuery.active") == 0);
			} catch (Exception e) {
				return true;
			}
		};

		// wait for JS to load
		Function<WebDriver, Boolean> jsLoad = (WebDriver d) -> {
			return js.executeScript("return document.readyState").toString().equals("complete");
		};
		return wait.until(jQueryLoad) && wait.until(jsLoad);
	}

}
