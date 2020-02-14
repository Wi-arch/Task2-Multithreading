package by.training.multithreading.exception;

@SuppressWarnings("serial")
public class MatrixServiceException extends Exception {

	public MatrixServiceException() {
	}

	public MatrixServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MatrixServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatrixServiceException(String message) {
		super(message);
	}

	public MatrixServiceException(Throwable cause) {
		super(cause);
	}

}
