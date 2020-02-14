package by.training.multithreading.entity;

import java.util.concurrent.atomic.AtomicBoolean;

public enum Matrix {

	INSTANCE;
	private Element[][] elementArray;

	private class Element {
		private int value;
		private AtomicBoolean flag;

		private Element() {
			this.flag = new AtomicBoolean(false);
		}
	}

	public void initMatrix(int size) {

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

	public int getValue(int i, int j) {

		return elementArray[i][j].value;
	}

	public boolean setValue(int i, int j, int value) {

		Element currentElement = elementArray[i][j];
		if (!currentElement.flag.compareAndExchange(false, true)) {
			currentElement.value = value;
			return true;
		}
		return false;
	}

}
