package by.training.multithreading.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class StringParserTest {

	private final static String SOURCE = "Iteration count (Y) = 15\r\nMatrix size (N) = 6";

	@Test
	public void testParseStringToIterationNumberPositive() {
		int expected = 15;
		int actual = StringParser.parseStringToIterationNumber(SOURCE);
		assertEquals(expected, actual);
	}

	@Test
	public void testParseStringToIterationNumberNegative() {
		int expected = 0;
		int actual = StringParser.parseStringToIterationNumber(SOURCE);
		assertNotEquals(expected, actual);
	}

	@Test(expected = RuntimeException.class)
	public void testParseStringToIterationNumberNegativeWithException() {
		int expected = 3;
		int actual = StringParser.parseStringToIterationNumber(null);
		assertEquals(expected, actual);
	}

	@Test
	public void testParseStringToSizeOfMatrixPositive() {
		int expected = 6;
		int actual = StringParser.parseStringToSizeOfMatrix(SOURCE);
		assertEquals(expected, actual);
	}

	@Test
	public void testParseStringToSizeOfMatrixNegative() {
		int expected = 0;
		int actual = StringParser.parseStringToSizeOfMatrix(SOURCE);
		assertNotEquals(expected, actual);
	}

	@Test(expected = RuntimeException.class)
	public void testParseStringToSizeOfMatrixNegativeWithException() {
		int expected = 3;
		int actual = StringParser.parseStringToSizeOfMatrix(null);
		assertEquals(expected, actual);
	}

}
