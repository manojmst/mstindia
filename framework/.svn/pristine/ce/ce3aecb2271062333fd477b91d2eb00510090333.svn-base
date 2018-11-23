package com.eViewer.HTML5.Automation.Framework.Utils;

import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class ImageConversion {

	public void createImageFromString(String srcString, String desPath) {
		byte[] image = null;
		try {
			image = Base64.decodeBase64(StringUtils.split(srcString, ',')[1]);
			FileUtils.writeByteArrayToFile(new File(desPath), image);
		} catch (Exception e) {
			CustomLogger.logError("createImageFromString: " + e.getMessage());
		} finally {
			image = null;
		}
	}

}
