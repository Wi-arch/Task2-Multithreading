package by.training.multithreading.validator;

import by.training.multithreading.exception.MatrixServiceException;

public class MatrixValidator {

	private MatrixValidator() {
	}

	public static boolean isMatrixSizeNegative(int size) {
		return size < 0;
	}

	public static void validateIndexOutOfSize(int index, int size) throws MatrixServiceException {
		if (index < 0 || index >= size) {
			throw new MatrixServiceException();
		}
	}
}
