package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Utils {

	public static String getFileExtension(String name) {
		int pointIndex = name.lastIndexOf(".");

		if (pointIndex == -1) {
			return null;
		}
		if (pointIndex == name.length() - 1) {
			return null;
		}

		return name.substring(pointIndex, name.length());
	}

	public ImageIcon createIcon(String path) {
//		URL url = System.class.getResource(path);
		URL url = getClass().getResource(path);
		System.out.println("url is " + url);

		if (url == null) {
			System.out.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url);

		return icon;
	}

	public Font createFont(String path) {
//		URL url = System.class.getResource(path);
		URL url = getClass().getResource(path);
		System.out.println("url is " + url);

		if (url == null) {
			System.out.println("Unable to load font: " + path);
		}

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
		} catch (FontFormatException e) {
			System.out.println("Bad format in font file: " + path);
		} catch (IOException e) {
			System.out.println("Unable to read font file: " + path);
		}

		return font;
	}
}
