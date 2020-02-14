package by.training.multithreading.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import by.training.multithreading.exception.MatrixServiceException;

public class MatrixServiceValidatorTest {

	@Test
	public void testIsMatrixSizeNegativePositive() {
		boolean actual = MatrixValidator.isMatrixSizeNegative(-5);
		assertEquals(true, actual);
	}

	@Test
	public void testIsMatrixSizeNegativeNegative() {
		boolean actual = MatrixValidator.isMatrixSizeNegative(7);
		assertEquals(false, actual);
	}

	@Test(expected = MatrixServiceException.class)
	public void testValidateIndexOutOfSizeNegative() throws MatrixServiceException {
		int index = 15;
		int size = 10;
		MatrixValidator.validateIndexOutOfSize(index, size);
	}

	@Test
	public void testValidateIndexOutOfSizePositive() throws MatrixServiceException {
		int index = 5;
		int size = 10;
		MatrixValidator.validateIndexOutOfSize(index, size);
	}

}
