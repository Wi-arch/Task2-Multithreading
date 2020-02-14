package by.training.multithreading.entity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.service.MatrixService;
import by.training.multithreading.util.TextFileWriter;

public class MatrixHandlerThread implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(MatrixHandlerThread.class);
	private static final MatrixService MATRIX_SERVICE = MatrixService.getInstance();
	private static final TextFileWriter WRITER = TextFileWriter.getInstance();
	private static final Element[][] MATRIX = Matrix.INSTANCE.getElementArray();
	private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(1);
	private String file;
	private CountDownLatch latch;
	private int id;

	public MatrixHandlerThread(String file, CountDownLatch latch) {
		this.file = file;
		this.latch = latch;
		id = THREAD_COUNTER.getAndIncrement();
	}

	public void run() {
		try {
			int diagonalNumber = MATRIX_SERVICE.writeNumberInMatrixAndGetDiagonalNumber(MATRIX, id);
			MATRIX_SERVICE.writeNumberInColumnOrRow(MATRIX, diagonalNumber, id);
			int sum = MATRIX_SERVICE.getSumOfRowAndColumn(MATRIX, diagonalNumber);
			String result = "Thread #" + id + "\tDiagonal #" + diagonalNumber + "\tSum = " + sum + "\r\n";
			WRITER.writeStringToFile(file, result);
		} catch (MatrixServiceException e) {
			LOGGER.warn("Thread did not complete its task", e);
		} finally {
			latch.countDown();
		}
	}

}
