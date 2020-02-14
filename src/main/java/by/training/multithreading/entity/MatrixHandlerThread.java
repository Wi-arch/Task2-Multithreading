package by.training.multithreading.entity;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.service.MatrixService;
import by.training.multithreading.util.TextFileWriter;

public class MatrixHandlerThread implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(MatrixHandlerThread.class);
	private final static MatrixService MATRIX_SERVICE = MatrixService.getInstance();
	private final static TextFileWriter WRITER = TextFileWriter.getInstance();
	private String file;
	private CountDownLatch latch;
	private final int threadName;

	public MatrixHandlerThread(int threadName, String file, CountDownLatch latch) {
		this.threadName = threadName;
		this.file = file;
		this.latch = latch;
	}

	public void run() {
		Matrix matrix = Matrix.INSTANCE;
		try {
			int diagonalNumber = MATRIX_SERVICE.writeThreadNumberInMatrixAndGetDiagonalNumber(matrix, threadName);
			MATRIX_SERVICE.writeThreadNumberInColumnOrRow(matrix, diagonalNumber, threadName);
			int sum = MATRIX_SERVICE.getSumOfRowAndColumnInDiagonalNumber(matrix, diagonalNumber);
			String result = "Thread #" + threadName + "\tDiagonal #" + diagonalNumber + "\tSum = " + sum + "\r\n";
			WRITER.writeStringToFile(file, result);
		} catch (MatrixServiceException e) {
			LOGGER.warn("Thread did not complete its task", e);
		} finally {
			latch.countDown();
		}
	}

}
