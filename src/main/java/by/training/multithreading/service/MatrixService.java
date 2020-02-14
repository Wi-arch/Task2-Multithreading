package by.training.multithreading.service;

import java.util.Random;

import org.apache.log4j.Logger;

import by.training.multithreading.entity.Element;
import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.validator.MatrixValidator;

public class MatrixService {

	private static final Logger LOGGER = Logger.getLogger(MatrixService.class);
	private static final Random RANDOM = new Random();

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
		boolean run = true;
		int randomIndex;
		do {
			randomIndex = getRandomIndex(diagonalNumber, matrix.length - 1);
			if (RANDOM.nextBoolean()) {
				run = matrix[randomIndex][diagonalNumber].trySetValue(number) ? false : run;
			} else {
				run = matrix[diagonalNumber][randomIndex].trySetValue(number) ? false : run;
			}
		} while (run);

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

	private int getRandomIndex(int diagonalNumber, int size) {
		int index = RANDOM.nextInt(size);
		while (index == diagonalNumber) {
			index = RANDOM.nextInt(size);
		}
		return index;
	}

}
