package com.eViewer.HTML5.Automation.Framework.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.eViewer.HTML5.Automation.Framework.Common.CustomLogger;

public class FileComparison {

	private final String IMAGE_MAGICK_APP_DIR = System.getProperty("user.dir") + File.separator + "src" + File.separator
			+ "resources" + File.separator + "comparison_tool" + File.separator + "ImageMagick" + File.separator
			+ "compare.exe";

	public boolean areFilesSame(String srcFile, String compFile) throws Exception {
		byte[] srcFileInBytes = null;
		byte[] compFileInBytes = null;
		try {
			srcFileInBytes = FileUtils.readFileToByteArray(new File(srcFile));
			compFileInBytes = FileUtils.readFileToByteArray(new File(compFile));

			if (!Arrays.equals(srcFileInBytes, compFileInBytes)) {
				CustomLogger.logError("areFilesSame: source and comparison files are different");
				return false;
			}
		} catch (Exception e) {
			CustomLogger.logError("arePDFSame: " + e.getMessage());
			throw new Exception("arePDFSame: " + e.getMessage());
		} finally {
			srcFileInBytes = null;
			compFileInBytes = null;
		}
		return true;
	}

	public boolean areOfDifferentSize(String srcFile, String compFile) throws Exception {
		BufferedImage srcImage = null;
		BufferedImage compImage = null;
		try {
			srcImage = fileToImage(srcFile);
			compImage = fileToImage(compFile);

			if (srcImage.getHeight() != compImage.getHeight() || srcImage.getWidth() != compImage.getWidth()) {
				CustomLogger.logWarning("areOfDifferentSize: source and comparison images have different size");
				return true;
			}
		} catch (Exception e) {
			CustomLogger.logError("areOfDifferentSize: " + e.getMessage());
			throw new Exception("areOfDifferentSize: " + e.getMessage());
		} finally {
			srcImage = null;
			compImage = null;
		}
		return false;
	}

	public boolean compareImages(String srcImagePath, String compImagePath, String diffImagePath) throws Exception {
		String consoleOutput = "";
		Launcher launcher = new Launcher();
		try {
			consoleOutput = launcher.runApplication(
					new String[] { IMAGE_MAGICK_APP_DIR, "-metric", "mae", srcImagePath, compImagePath, "null:" });

			float firstResult = 0;
			try {
				firstResult = Float.parseFloat(StringUtils.split(consoleOutput, ' ')[0]);
			} catch (Exception e) {
			}

			if (consoleOutput.contains("error")) {
				CustomLogger.logError("compareImages: error occured during comparison");
				return false;
			} else if (consoleOutput.contains("ImageWidthsOrHeightsDiffer")) {
				CustomLogger.logError("compareImages: images have different size");
				return false;
			} else if (consoleOutput.contains("0 (0)")) {
				CustomLogger.logInfo("compareImages: images are similar");
				return true;
			} else if (firstResult > 0.00 && firstResult <= 5.00) {
				CustomLogger.logInfo("compareImages: images are almost similar and are under the tolerance value");
				return true;
			} else {
				launcher.runApplication(new String[] { IMAGE_MAGICK_APP_DIR, "-compose", "src", srcImagePath,
						compImagePath, "difference.png" });

				File srcfile = new File("difference.png");
				File desFile = new File(diffImagePath);
				if (desFile.exists()) {
					desFile.delete();
				}
				FileUtils.moveFile(srcfile, desFile);
				CustomLogger.logInfo(
						"compareImages: images are different, created a image showing differences [" + desFile + "]");
				return false;
			}
		} catch (Exception e) {
			CustomLogger.logError("compareImages: " + e.getMessage());
			throw new Exception("compareImages: " + e.getMessage());
		} finally {
			launcher = null;
		}
	}

	private BufferedImage fileToImage(String filePath) {
		BufferedImage image = null;
		try {
			File f = new File(filePath);
			if (f.exists() && f.isFile()) {
				image = ImageIO.read(f);
			}
		} catch (Exception e) {
			CustomLogger.logError("fileToImage: " + e.getMessage());
		}
		return image;
	}

}
