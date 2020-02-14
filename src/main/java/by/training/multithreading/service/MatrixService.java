package by.training.multithreading.service;

import org.apache.log4j.Logger;

import by.training.multithreading.entity.Element;
import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.validator.MatrixValidator;

public class MatrixService {

	private static final Logger LOGGER = Logger.getLogger(MatrixService.class);

	private MatrixService() {
	}

	private static class MatrixServiceInstance {
		private static final MatrixService INSTANCE = new MatrixService();
	}

	public static MatrixService getInstance() {
		return MatrixServiceInstance.INSTANCE;
	}

	public int writeNumberInMatrixAndGetDiagonalNumber(Element[][] matrix, int number) throws MatrixServiceException {
		checkMatrixForNull(matrix);
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][i].trySetValue(number)) {
				return i;
			}
		}
		LOGGER.warn("No free cell found in diagonal");
		throw new MatrixServiceException("No free cell found in diagonal");
	}

	public void writeNumberInColumnOrRow(Element[][] matrix, int diagonalNumber, int number)
			throws MatrixServiceException {
		checkMatrixForNull(matrix);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if ((i == diagonalNumber || j == diagonalNumber) && matrix[i][j].trySetValue(number)) {
					return;
				}
			}
		}
		LOGGER.warn("Cannot write number in matrix, no free cell");
		throw new MatrixServiceException("Cannot write number in matrix");
	}

	public int getSumOfRowAndColumn(Element[][] matrix, int diagonalNumber) throws MatrixServiceException {
		checkMatrixForNull(matrix);
		MatrixValidator.validateIndexOutOfSize(diagonalNumber, matrix.length);
		int sum = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (i == diagonalNumber || j == diagonalNumber) {
					sum += matrix[i][j].getValue();
				}
			}
		}
		return sum;
	}

	public void resetMatrixIsUsedFlag(Element[][] matrix) throws MatrixServiceException {
		checkMatrixForNull(matrix);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j].setIsUsed(false);
			}
		}
	}

	private void checkMatrixForNull(Element[][] matrix) throws MatrixServiceException {
		if (matrix == null) {
			LOGGER.warn("Null matrix");
			throw new MatrixServiceException("Null matrix");
		}
	}

}
