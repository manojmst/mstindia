package com.eViewer.HTML5.Automation.Framework.Config;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class UserAgentSwitcher {

	public FirefoxProfile changeAgentForFirefox(String userProfile, String userAgentString) {
		FirefoxProfile firefoxProfile = new ProfilesIni().getProfile(userProfile);
		firefoxProfile.setPreference("general.useragent.override", userAgentString);
		return firefoxProfile;
	}

	public ChromeOptions changeAgentForChrome(String userAgentString) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(String.format("--user-agent=%s", userAgentString));
		return chromeOptions;
	}

}
