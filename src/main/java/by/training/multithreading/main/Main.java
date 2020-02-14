package by.training.multithreading.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	private static final String FILE_NAME = "InitializationData.txt";
	private static final String OUTPUT_FILE_NAME = "Matrix.txt";
	private static final String SPLITERATOR = "--------";

	public static void main(String[] args) {

		String dataString = READER.readStringFromFile(FILE_NAME);
		int iterationNumber = StringParser.parseStringToIterationNumber(dataString);
		int matrixSize = StringParser.parseStringToSizeOfMatrix(dataString);
		Matrix matrix = Matrix.INSTANCE;
		MatrixService matrixService = MatrixService.getInstance();

		matrix.initMatrix(matrixSize);

		CountDownLatch latch = null;
		ExecutorService executor = null;

		for (int i = 0; i < iterationNumber; i++) {
			latch = new CountDownLatch(matrixSize);
			executor = Executors.newFixedThreadPool(matrixSize);
			for (int t = 0; t < matrixSize; t++) {
				executor.submit(new MatrixHandlerThread(OUTPUT_FILE_NAME, latch));
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				LOGGER.warn("Thread is interrupted", e);
			} finally {
				executor.shutdown();
			}
			WRITER.writeStringToFile(OUTPUT_FILE_NAME, matrix.toString());
			try {
				matrixService.resetMatrixIsUsedFlag(matrix.getElementArray());
			} catch (MatrixServiceException e) {
				LOGGER.warn("Cannot reset isUsed matrix elements", e);
			}
			WRITER.writeStringToFile(OUTPUT_FILE_NAME, getIntermediateResult(matrixSize, i));
		}
	}

	private static String getIntermediateResult(int matrixSize, int iteration) {

		StringBuilder iterationResult = new StringBuilder();
		StringBuilder tempSpliterator = new StringBuilder();
		for (int i = 0; i < matrixSize - 1; i++) {
			tempSpliterator.append(SPLITERATOR);
		}
		tempSpliterator.append("-\r\n");
		iterationResult.append(tempSpliterator);
		iterationResult.append("Iteration #" + (iteration + 1) + " finished\r\n");
		return iterationResult.append(tempSpliterator + "\r\n").toString();
	}
}
