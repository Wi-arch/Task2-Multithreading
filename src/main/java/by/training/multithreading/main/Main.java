package by.training.multithreading.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import by.training.multithreading.entity.Matrix;
import by.training.multithreading.entity.MatrixHandlerThread;
import by.training.multithreading.exception.MatrixServiceException;
import by.training.multithreading.service.MatrixService;
import by.training.multithreading.util.StringParser;
import by.training.multithreading.util.TextFileReader;
import by.training.multithreading.util.TextFileWriter;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(Main.class);
	private static final TextFileReader READER = TextFileReader.getInstance();
	private static final TextFileWriter WRITER = TextFileWriter.getInstance();
	private static final MatrixService MATRIX_SERVICE = MatrixService.getInstance();
	private static final String FILE_NAME = "InitializationData.txt";
	private static final String OUTPUT_FILE_NAME = "Matrix.txt";
	private static final String SPLITERATOR = "-------------------------";
	private static final int MAX_TIME_TO_WAIT = 3;

	public static void main(String[] args) {

		String dataString = READER.readStringFromFile(FILE_NAME);
		int iterationNumber = StringParser.parseStringToIterationNumber(dataString);
		int matrixSize = StringParser.parseStringToSizeOfMatrix(dataString);
		Matrix matrix = Matrix.INSTANCE;
		matrix.initMatrix(matrixSize);
		CountDownLatch latch = null;
		for (int i = 0; i < iterationNumber; i++) {
			latch = new CountDownLatch(matrixSize);
			for (int t = 0; t < matrixSize; t++) {
				new MatrixHandlerThread(OUTPUT_FILE_NAME, latch);
			}
			try {
				latch.await(MAX_TIME_TO_WAIT, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				LOGGER.warn("Thread is interrupted", e);
			}
			WRITER.writeStringToFile(OUTPUT_FILE_NAME, matrix.toString());
			try {
				MATRIX_SERVICE.resetMatrixIsUsedFlag(matrix.getElementArray());
			} catch (MatrixServiceException e) {
				LOGGER.error("Cannot reset isUsed matrix elements", e);
			}
			WRITER.writeStringToFile(OUTPUT_FILE_NAME, getIntermediateResult(i));
		}
	}

	private static String getIntermediateResult(int iterationNumber) {
		StringBuilder iterationResult = new StringBuilder();
		iterationResult.append(SPLITERATOR + "\r\n");
		iterationResult.append("Iteration #" + (iterationNumber + 1) + " finished\r\n");
		iterationResult.append(SPLITERATOR + "\r\n");
		return iterationResult.toString();
	}
}
