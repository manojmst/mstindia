package com.eViewer.HTML5.Automation.Framework.Utils;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.monte.media.Format;
import org.monte.media.Registry;
import org.monte.screenrecorder.ScreenRecorder;

class Recoder extends ScreenRecorder {

	private String name;

	public Recoder(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
			Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
		super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
		this.name = name;
	}

	@Override
	protected File createMovieFile(Format fileFormat) throws IOException {
		if (!movieFolder.exists()) {
			movieFolder.mkdirs();
		}
		if (!movieFolder.isDirectory()) {
			throw new IOException("createMovieFile: [" + movieFolder + "] is not a directory");
		}
		String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MMM.uuu_HH.mm.ss"));
		return new File(movieFolder, name + "_" + currentTime + "." + Registry.getInstance().getExtension(fileFormat));
	}

}
