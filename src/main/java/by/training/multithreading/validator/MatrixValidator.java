package by.training.multithreading.validator;

import by.training.multithreading.exception.MatrixException;

public class MatrixValidator {

	public static void validateMatrixSize(int size) throws MatrixException {
		if (size < 0) {
			throw new MatrixException();
		}
	}

	public static void validateIndexOfMatrix(int i, int j, int size) throws MatrixException {
		if (i < 0 || i >= size || j < 0 || j >= size) {
			throw new MatrixException();
		}
	}

	public static void validateIndexOfMatrix(int i, int size) throws MatrixException {
		if (i < 0 || i >= size) {
			throw new MatrixException();
		}
	}
}
