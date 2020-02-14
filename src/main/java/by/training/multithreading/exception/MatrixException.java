package by.training.multithreading.exception;

@SuppressWarnings("serial")
public class MatrixException extends Exception {

	public MatrixException() {
	}

	public MatrixException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MatrixException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatrixException(String message) {
		super(message);
	}

	public MatrixException(Throwable cause) {
		super(cause);
	}

}
