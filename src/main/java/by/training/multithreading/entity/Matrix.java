package by.training.multithreading.entity;

import org.apache.log4j.Logger;

import by.training.multithreading.validator.MatrixValidator;

public enum Matrix {

	INSTANCE;
	private static final Logger LOGGER = Logger.getLogger(Matrix.class);
	private Element[][] elementArray;

	public void initMatrix(int size) {
		if (MatrixValidator.isMatrixSizeNegative(size)) {
			LOGGER.fatal("Cannot initialize matrix");
			throw new RuntimeException("Cannot initialize matrix");
		}
		this.elementArray = new Element[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				elementArray[i][j] = new Element();
			}
		}
	}

	public Element[][] getElementArray() {
		return elementArray;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elementArray.length; i++) {
			for (int j = 0; j < elementArray.length; j++) {
				sb.append(elementArray[i][j].getValue() + "\t");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
}
