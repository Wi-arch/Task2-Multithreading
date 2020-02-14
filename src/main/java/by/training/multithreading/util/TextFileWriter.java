package by.training.multithreading.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class TextFileWriter {

	private final static Logger LOGGER = Logger.getLogger(TextFileWriter.class);

	private TextFileWriter() {
	}

	private static class TextFileWriterInstance {
		private final static TextFileWriter INSTANCE = new TextFileWriter();
	}

	public static TextFileWriter getInstance() {
		return TextFileWriterInstance.INSTANCE;
	}

	public void writeStringToFile(String fileName, String string) {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
			writer.write(string);
		} catch (IOException e) {
			LOGGER.error("Cannot write text to file");
		}
	}
}
