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
	private final static Matrix MATRIX = Matrix.INSTANCE;
	private static int threadCounter = 0;
	private String file;
	private CountDownLatch latch;

	public MatrixHandlerThread(String file, CountDownLatch latch) {
		this.file = file;
		this.latch = latch;
		threadCounter++;
	}

	public void run() {
		try {
			int diagonalNumber = MATRIX_SERVICE.writeThreadNumberInMatrixAndGetDiagonalNumber(MATRIX, threadCounter);
			MATRIX_SERVICE.writeThreadNumberInColumnOrRow(MATRIX, diagonalNumber, threadCounter);
			int sum = MATRIX_SERVICE.getSumOfRowAndColumnInDiagonalNumber(MATRIX, diagonalNumber);
			String result = "Thread #" + threadCounter + "\tDiagonal #" + diagonalNumber + "\tSum = " + sum + "\r\n";
			WRITER.writeStringToFile(file, result);
		} catch (MatrixServiceException e) {
			LOGGER.warn("Thread did not complete its task", e);
		} finally {
			latch.countDown();
		}
	}

}
