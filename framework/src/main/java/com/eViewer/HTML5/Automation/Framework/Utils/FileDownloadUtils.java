package com.eViewer.HTML5.Automation.Framework.Utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;
import com.eViewer.HTML5.Automation.Framework.Common.TestContext;
import com.eViewer.HTML5.Automation.Framework.Config.Configuration;

public class FileDownloadUtils {

	public void moveFilesToDirectory(String desDir, String fileName) {
		try {
			String name = "";

			File srcDir = new File(Configuration.getDownloadDir());
			File[] srcFiles = srcDir.listFiles();
			if (srcFiles.length > 0) {
				for (int i = 0; i < srcFiles.length; i++) {
					name = TestContext.getCurrentMethodName() + "_" + fileName + "_" + (i + 1) + "."
							+ StringUtils.split(srcFiles[i].getName(), '.')[1];

					File desFile = new File(desDir + File.separator + name);
					if (desFile.exists()) {
						desFile.delete();
					}
					FileUtils.copyFile(srcFiles[i], desFile);
				}
				FileUtils.cleanDirectory(srcDir);
			} else {
				throw new Exception("no file(s) exists in download directory");
			}
		} catch (Exception e) {
			CustomLogger.logError("moveFilesToDirectory: " + e.getMessage());
		}
	}

	public void waitForFileToAppearInDownloadDir(int downloadCount) {
		try {
			File downloadDir = new File(Configuration.getDownloadDir());

			if (downloadDir.listFiles().length == 0) {
				Thread.sleep(2000);
			}

			int i = 0;
			while (downloadDir.listFiles().length != downloadCount) {
				Thread.sleep(2000);
				if (i > 20) {
					break;
				}
				i++;
			}
		} catch (Exception e) {
			CustomLogger.logError("waitForFileToAppearInDownloadDir: " + e.getMessage());
		}
	}

//	public boolean is_cacheCleared() {
//		try {
//			File cacheDir = new File(Configuration.getCacheDir());
//			if (cacheDir.listFiles().length == 0) {
//				return true;
//			}
//
//		} catch (Exception e) {
//			CustomLogger.logError("CacheDirCleared: " + e.getMessage());
//		}
//
//		return false;
//	}
}