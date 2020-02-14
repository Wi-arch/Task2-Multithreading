package by.training.multithreading.service;

import org.apache.log4j.Logger;

import by.training.multithreading.entity.Matrix;
import by.training.multithreading.exception.MatrixException;
import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.validator.MatrixValidator;

public class MatrixService {

	private final static Logger LOGGER = Logger.getLogger(MatrixService.class);

	private MatrixService() {
	}

	private static class MatrixServiceInstance {
		private final static MatrixService INSTANCE = new MatrixService();
	}

	public static MatrixService getInstance() {
		return MatrixServiceInstance.INSTANCE;
	}

	public int writeThreadNumberInMatrixAndGetDiagonalNumber(Matrix matrix, int threadName)
			throws MatrixServiceException {
		checkMatrixForNull(matrix);
		try {
			for (int i = 0; i < matrix.getSize(); i++) {
				if (matrix.trySetValue(i, i, threadName)) {
					return i;
				}
			}
		} catch (MatrixException e) {
			throw new MatrixServiceException("Index out of matrix size");
		}
		LOGGER.warn("No free cell found in diagonal");
		throw new MatrixServiceException("No free cell found in diagonal");
	}

	public void writeThreadNumberInColumnOrRow(Matrix matrix, int diagonalNumber, int threadName)
			throws MatrixServiceException {
		checkMatrixForNull(matrix);
		try {
			MatrixValidator.validateIndexOfMatrix(diagonalNumber, matrix.getSize());
			for (int i = 0; i < matrix.getSize(); i++) {
				for (int j = 0; j < matrix.getSize(); j++) {
					if ((i == diagonalNumber || j == diagonalNumber) && matrix.trySetValue(i, j, threadName)) {
						return;
					}
				}
			}
		} catch (MatrixException e) {
			LOGGER.warn("Cannot write number in matrix");
			throw new MatrixServiceException("Cannot write number in matrix");
		}
	}

	public int getSumOfRowAndColumnInDiagonalNumber(Matrix matrix, int diagonalNumber) throws MatrixServiceException {
		checkMatrixForNull(matrix);
		int sum = 0;
		try {
			MatrixValidator.validateIndexOfMatrix(diagonalNumber, matrix.getSize());
			for (int i = 0; i < matrix.getSize(); i++) {
				for (int j = 0; j < matrix.getSize(); j++) {
					if (i == diagonalNumber || j == diagonalNumber) {
						sum += matrix.getValue(i, j);
					}
				}
			}
		} catch (MatrixException e) {
			LOGGER.warn("Diagonal number outside the matrix");
			throw new MatrixServiceException("Diagonal number outside the matrix");
		}
		return sum;
	}

	private void checkMatrixForNull(Matrix matrix) throws MatrixServiceException {
		if (matrix == null) {
			LOGGER.warn("Null matrix or flagMatrix");
			throw new MatrixServiceException("Null matrix or flagMatrix");
		}
	}
}
