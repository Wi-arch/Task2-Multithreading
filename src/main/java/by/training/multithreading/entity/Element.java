package by.training.multithreading.entity;

import java.util.concurrent.atomic.AtomicBoolean;

public class Element {

	private int value;
	private AtomicBoolean isUsed;

	public Element() {
		this.isUsed = new AtomicBoolean(false);
	}

	public int getValue() {
		return value;
	}

	public boolean trySetValue(int value) {
		if (isUsed.compareAndSet(false, true)) {
			this.value = value;
			return true;
		}
		return false;
	}

	public boolean isUsed() {
		return isUsed.get();
	}

	public void setIsUsed(boolean flag) {
		isUsed.set(flag);
	}

}
