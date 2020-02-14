package by.training.multithreading.main;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import by.training.multithreading.entity.Matrix;
import by.training.multithreading.entity.MatrixHandlerThread;
import by.training.multithreading.exception.MatrixException;
import by.training.multithreading.util.StringParser;
import by.training.multithreading.util.TextFileReader;
import by.training.multithreading.util.TextFileWriter;

public class Main {

	private final static Logger LOGGER = Logger.getLogger(Main.class);
	private final static TextFileReader READER = TextFileReader.getInstance();
	private final static TextFileWriter WRITER = TextFileWriter.getInstance();
	private final static String FILE_NAME = "InitializationData.txt";
	private final static String OUTPUT_FILE_NAME = "Matrix.txt";
	private final static String SPLITERATOR = "--------";

	public static void main(String[] args) {

		String dataString = READER.readStringFromFile(FILE_NAME);
		int iterationNumber = StringParser.parseStringToIterationNumber(dataString);
		int matrixSize = StringParser.parseStringToSizeOfMatrix(dataString);
		Matrix matrix = Matrix.INSTANCE;

		try {
			matrix.initMatrix(matrixSize);
		} catch (MatrixException e) {
			LOGGER.fatal("Cannot initialize matrix");
			throw new RuntimeException("Cannot initialize matrix");
		}

		int threadNameCounter = 1;
		CountDownLatch latch = null;
		ExecutorService executor = null;

		for (int i = 0; i < iterationNumber; i++) {
			latch = new CountDownLatch(matrixSize);
			executor = Executors.newFixedThreadPool(matrixSize);
			for (int t = 0; t < matrixSize; t++, threadNameCounter++) {
				executor.submit(new MatrixHandlerThread(threadNameCounter, OUTPUT_FILE_NAME, latch));
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				LOGGER.warn("Thread is interrupted", e);
			} finally {
				executor.shutdown();
			}
			WRITER.writeStringToFile(OUTPUT_FILE_NAME, matrix.toString());
			matrix.resetMatrixFlag();
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
