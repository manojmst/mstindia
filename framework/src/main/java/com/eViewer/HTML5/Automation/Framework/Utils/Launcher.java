package com.eViewer.HTML5.Automation.Framework.Utils;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class Launcher {

	public String runApplication(String[] args) {
		String consoleText = "";
		ProcessBuilder processbuilder;
		try {
			processbuilder = new ProcessBuilder(args);
			processbuilder.redirectErrorStream(true);
			Process process = processbuilder.start();

			InputStream stream = process.getInputStream();
			consoleText = IOUtils.toString(stream, Charset.defaultCharset());
			CustomLogger.logInfo("runApplication: console output is [" + consoleText + "]");
			process.destroy();
			IOUtils.closeQuietly(stream);
		} catch (Exception e) {
			CustomLogger.logError("runApplication: " + e.getMessage());
		} finally {
		//	processbuilder = null;
		}
		return consoleText;
	}

}
