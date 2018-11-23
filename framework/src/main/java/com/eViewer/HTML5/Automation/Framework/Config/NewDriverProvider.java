package com.eViewer.HTML5.Automation.Framework.Config;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class NewDriverProvider {

	private static WebDriver driver;

	public static WebDriver getWebDriver() {
		return driver;
	}

	public static WebDriver createWebDriverInstance() throws Exception {
		try {
			String browser = Configuration.getBrowser();
			switch (Browser.valueOf(browser)) {
			case IE:

				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.introduceFlakinessByIgnoringSecurityDomains().ignoreZoomSettings()
						.setPageLoadStrategy(PageLoadStrategy.NORMAL);

				driver = new InternetExplorerDriver(ieOptions);
				break;

			// case FIREFOX:
			//
			// FirefoxOptions firefoxOptions = new FirefoxOptions();
			// FirefoxProfile firefoxProfile = new ProfilesIni().getProfile("test");
			//
			// firefoxProfile.setPreference("browser.download.folderList", 2);
			// firefoxProfile.setPreference("browser.download.dir",
			// Configuration.getDownloadDir());
			// firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
			// "application/pdf");
			// firefoxProfile.setPreference("pdfjs.disabled", true);
			//
			// firefoxOptions.setProfile(firefoxProfile);
			//
			// driver = new FirefoxDriver(firefoxOptions);
			// break;

			case CHROME:

				ChromeOptions chromeOptions = new ChromeOptions();
				Map<String, Object> prefs = new HashMap<String, Object>();

				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.default_directory", Configuration.getDownloadDir());

				chromeOptions.addArguments("chrome.switches", "--disable-extensions")
						.addArguments("--disable-notifications").addArguments("disable-infobars")
						.setExperimentalOption("prefs", prefs).setPageLoadStrategy(PageLoadStrategy.NORMAL);

				driver = new ChromeDriver(chromeOptions);
				break;

			case REMOTE:

				DesiredCapabilities capabilities;
				if (Configuration.getRemoteBrowser().equals(Browser.CHROME.name())) {
					capabilities = DesiredCapabilities.chrome();
				}
				// else if
				// (Configuration.getRemoteBrowser().equals(Browser.FIREFOX.name()))
				// {
				// capabilities = DesiredCapabilities.firefox();
				// }
				else if (Configuration.getRemoteBrowser().equals(Browser.IE.name())) {
					capabilities = DesiredCapabilities.internetExplorer();
				} else {
					throw new Exception(
							"createWebDriverInstance:  provided remote browser [" + browser + "] is not supported");
				}

				try {
					driver = new RemoteWebDriver(new URL(Configuration.getHubURL()), capabilities);
				} catch (Exception e) {
					CustomLogger.logError("createWebDriverInstance: " + e.getMessage());
				}
				break;

			default:

				CustomLogger.logError("createWebDriverInstance: provided driver [" + browser + "] is not supported");
				throw new Exception("createWebDriverInstance: provided driver [" + browser + "] is not supported");
			}

			CustomLogger.logInfo("createWebDriverInstance: [" + browser + "] driver started successfully");
			driver.manage().timeouts().implicitlyWait(Configuration.getImplicitWaitTimeout(), TimeUnit.SECONDS);
			driver.manage().timeouts().setScriptTimeout(Configuration.getImplicitWaitTimeout(), TimeUnit.SECONDS);
		} catch (Exception e) {
			CustomLogger.logError("createWebDriverInstance: " + e.getMessage());
		}
		return driver;
	}

}
