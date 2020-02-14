package by.training.multithreading.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class TextFileReader {

	private final static Logger LOGGER = Logger.getLogger(TextFileReader.class);

	private TextFileReader() {
	}

	private static class TextFileReaderInstance {
		private final static TextFileReader INSTANCE = new TextFileReader();
	}

	public static TextFileReader getInstance() {
		return TextFileReaderInstance.INSTANCE;
	}

	public String readStringFromFile(String fileName) {

		StringBuilder result = new StringBuilder();
		String file = getClass().getClassLoader().getResource(fileName).getFile();
		String string = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			string = reader.readLine();
			while (string != null) {
				result.append(string + "\r\n");
				string = reader.readLine();
			}
		} catch (IOException e) {
			LOGGER.fatal("Exception while read file " + file);
			throw new RuntimeException("Exception while read file");
		}
		return result.toString();
	}

}
