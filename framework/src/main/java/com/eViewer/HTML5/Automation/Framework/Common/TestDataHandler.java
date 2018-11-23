package com.eViewer.HTML5.Automation.Framework.Common;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestDataHandler {

	private static int testDataPageCount;
	private static Map<String, Integer> fileAndPageMap = new LinkedHashMap<>();

	public static void setFilePageCount(int pageCount) {
		testDataPageCount = pageCount;
	}

	public static int getFilePageCount() {
		return testDataPageCount;
	}

	public static Map<String, Integer> getFileAndPageMap() {
		return fileAndPageMap;
	}

	public static void setFileAndPageMap(String[] files, String[] pages) {
		for (int i = 0; i < files.length; i++) {
			fileAndPageMap.put(files[i], Integer.parseInt(pages[i]));
		}
	}

	public static void removeKeyFromFileAndPageMap(int keyIndex) {
		String keyFile = fileAndPageMap.keySet().toArray()[keyIndex - 1].toString();
		fileAndPageMap.remove(keyFile);
	}

	public static void updateValueOfFileAndPageMap(int keyIndex, int newValue) {
		String keyFile = fileAndPageMap.keySet().toArray()[keyIndex].toString();
		fileAndPageMap.computeIfPresent(keyFile, (fileName, page) -> newValue);
	}

	public static void emptyFileAndPageMap() {
		fileAndPageMap.clear();
	}

}
