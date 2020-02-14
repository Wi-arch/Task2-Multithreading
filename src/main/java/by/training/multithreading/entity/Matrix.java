package by.training.multithreading.entity;

import java.util.concurrent.atomic.AtomicBoolean;

import by.training.multithreading.exception.MatrixException;
import by.training.multithreading.validator.MatrixValidator;

public enum Matrix {

	INSTANCE;
	private Element[][] elementArray;
	private int size;

	private class Element {
		private int value;
		private AtomicBoolean flag;

		private Element() {
			this.flag = new AtomicBoolean(false);
		}
	}

	public void initMatrix(int size) throws MatrixException {
		MatrixValidator.validateMatrixSize(size);
		this.size = size;
		this.elementArray = new Element[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				elementArray[i][j] = new Element();
			}
		}
	}

	public void resetMatrixFlag() {
		for (int i = 0; i < elementArray.length; i++) {
			for (int j = 0; j < elementArray[i].length; j++) {
				elementArray[i][j].flag.set(false);
			}
		}
	}

	public int getValue(int i, int j) throws MatrixException {
		MatrixValidator.validateIndexOfMatrix(i, j, elementArray.length);
		return elementArray[i][j].value;
	}

	public boolean trySetValue(int i, int j, int value) throws MatrixException {
		MatrixValidator.validateIndexOfMatrix(i, j, elementArray.length);
		Element currentElement = elementArray[i][j];
		if (!currentElement.flag.compareAndExchange(false, true)) {
			currentElement.value = value;
			return true;
		}
		return false;
	}

	public int getSize() {
		return size;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < elementArray.length; i++) {
			for (int j = 0; j < elementArray.length; j++) {
				sb.append(elementArray[i][j].value + "\t");
			}
			sb.append("\r\n");
		}
		return sb.toString();
	}
}
