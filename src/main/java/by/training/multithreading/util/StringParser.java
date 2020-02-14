package by.training.multithreading.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

public class StringParser {

	private final static Logger LOGGER = Logger.getLogger(StringParser.class);
	private static final String ITERATION_COUNT_REGEX = "Iteration count \\(Y\\)\\s?=\\s?(\\d+).*";
	private static final String MATRIX_SIZE_REGEX = "Matrix size \\(N\\)\\s?=\\s?(\\d+).*";
	private static final String LINE_SPLETEROTOR_REGEX = "\\r?\\n";

	public static int parseStringToIterationNumber(String string) {
		if (string == null) {
			LOGGER.fatal("Cannot initialize iteration number from null string");
			throw new RuntimeException("Cannot initialize iteration number");
		}
		for (String line : Arrays.stream(string.split(LINE_SPLETEROTOR_REGEX)).collect(Collectors.toList())) {
			if (line.matches(ITERATION_COUNT_REGEX)) {
				return Integer.valueOf(line.replaceAll(ITERATION_COUNT_REGEX, "$1"));
			}
		}
		LOGGER.fatal("Cannot parse string to iteration number");
		throw new RuntimeException("Cannot initialize iteration number");
	}

	public static int parseStringToSizeOfMatrix(String string) {
		if (string == null) {
			LOGGER.fatal("Cannot initialize matrix size from null string");
			throw new RuntimeException("Cannot initialize matrix size");
		}
		for (String line : Arrays.stream(string.split(LINE_SPLETEROTOR_REGEX)).collect(Collectors.toList())) {
			if (line.matches(MATRIX_SIZE_REGEX)) {
				return Integer.valueOf(line.replaceAll(MATRIX_SIZE_REGEX, "$1"));
			}
		}
		LOGGER.fatal("Cannot parse string to matrix size");
		throw new RuntimeException("Cannot initialize matrix size");
	}
}
