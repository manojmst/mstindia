package com.eViewer.HTML5.Automation.Framework.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriverException;
import org.yaml.snakeyaml.Yaml;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class Configuration {

	private static Yaml yaml = null;
	private static Map<String, String> defaultConfig = null;
	private static String URL_MASK = "http://%s/%s/%s";

	public static void initConfig(String file) throws Exception {
		readConfiguration(file);
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> readConfiguration(String file) throws Exception {
		try {
			yaml = new Yaml();
			defaultConfig = (Map<String, String>) yaml.load(new FileInputStream(new File(file)));
		} catch (FileNotFoundException e) {
			CustomLogger.logError("readConfiguration: CANNOT FIND DEFAULT CONFIG FILE [" + file + "]");
			throw new Exception("readConfiguration: CANNOT FIND DEFAULT CONFIG FILE [" + file + "]");
		}
		 catch (Exception e) {
				CustomLogger.logError("readConfiguration: " + e.getMessage());
				throw new Exception("readConfiguration: " + e.getMessage());
			}
		return defaultConfig;
	}

	private static String getProp(String propertyName) {
		String value = null;
		try {
			value = String.valueOf(defaultConfig.get(propertyName));
		} catch (Exception e) {
			CustomLogger
					.logError("[" + propertyName + "] seems to be a invalid property. Please check default_cofig file");
		}
		return Objects.isNull(value) ? StringUtils.EMPTY : value.trim();
	}

	public static String getViewerURL() {
		return String.format(URL_MASK, getServerURL(), getProp("CONTEXT_NAME"), getProp("RELEASE_PATH"));
	}

	public static String getServerURL() {
		return getProp("SERVER_URL");
	}

	public static String getBrowser() {
		return getProp("BROWSER");
	}

	public static String getRemoteBrowser() {
		return getProp("REMOTE_BROWSER");
	}

	public static String getHubURL() {
		return String.format("http://%s/wd/hub", getProp("HUB_URL"));
	}

	public static Dimension getBrowserSize() {
		String size = getProp("BROWSER_SIZE");

		if (StringUtils.isNotBlank(size) || size.equals("MAXIMIZED") || size.split("x").length == 2) {
			if (size.equals("MAXIMIZED")) {
				return null;
			} else {
				return new Dimension(Integer.valueOf(size.split("x")[0]), Integer.valueOf(size.split("x")[1]));
			}
		} else {
			throw new WebDriverException("getBrowserSize: [" + size + "] is not a proper value");
		}
	}

	public static String getUserName() {
		return getProp("USER_NAME");
	}

	public static String getViewerVersion() {
		return getProp("VERSION_NO");
	}

	public static int getImplicitWaitTimeout() {
		return Integer.parseInt(getProp("DEFAULT_TIMEOUT_FOR_IMPLICIT_WAIT"));
	}

	public static int getExplicitWaitTimeout() {
		return Integer.parseInt(getProp("DEFAULT_TIMEOUT_FOR_EXPLICIT_WAIT"));
	}

	public static int getSleepTimeout() {
		return Integer.parseInt(getProp("DEFAULT_WAIT"));
	}

	public static String getItemTypeForContentManager() {
		return getProp("ITEM_TYPE");
	}

	public static String getSharepointIntegratorURL() {
		return getProp("INTEGRATOR_URL");
	}

	public static String getSharepointHostName() {
		return getProp("SHAREPOINT_HOST_NAME");
	}

	public static String getSharepointUserLoginName() {
		return getProp("SHAREPOINT_USER_LOGIN_NAME");
	}

	public static String getSharepointLibraryName() {
		return getProp("SHAREPOINT_LIBRARY_NAME");
	}

	public static String getSharepointSubSiteName() {
		return getProp("SHAREPOINT_SUBSITE_NAME");
	}

	public static String getSingleUserPdfDocumentID() {
		return getProp("ID_SINGLE_PDF");
	}

	public static String getSingleUserTifDocumentID() {
		return getProp("ID_SINGLE_TIF");
	}

	public static String getMultipleUserPdfDocumentID() {
		return getProp("ID_MULTIPLE_PDF");
	}

	public static String getTestFileForSharepoint() {
		return getProp("TEST_FILE");
	}

	public static String getDataDirectory() {
		return getProp("DATA_DIRECTORY");
	}

	public static String getAnnModifiedAlert() {
		return getProp("ANN_MODIFIED_ALERT");
	}
	
	public static String getDocModifiedAlert() {
		return getProp("DOC_MODIFIED_ALERT");
	}
	
	public static String getDataDirectoryUnixFormat() {
		return FilenameUtils.separatorsToUnix(getDataDirectory());
	}

	public static boolean isVideoRequired() {
		return Boolean.parseBoolean(getProp("VIDEO_MODE_REQUIRED"));
	}

	public static boolean isUNCPath() {
		return getDataDirectoryUnixFormat().contains("database") || getDataDirectoryUnixFormat().contains("server");
	}

	public static String getDownloadDir() {
		return System.getProperty("user.dir") + File.separator + "downloads";
	}
	
	public static String getcrossDomainURL() {
		return getProp("CROSS_DOMAINURL");
	}
	
	public static String getCacheDir() {
		return getProp("CACHE_DIRECTORY");	
	}

	
	public static void endConfig() {
		yaml = null;
		defaultConfig = null;
	}

}
