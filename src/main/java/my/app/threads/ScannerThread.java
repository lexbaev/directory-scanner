package my.app.threads;

import my.app.mapper.MappedScanRequest;
import my.app.processors.FileProcessor;
import my.app.processors.impl.FileProcessorImpl;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * The implicit thread for scan tree of directories and copy files.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class ScannerThread extends Thread {

  private final static Logger logger = Logger.getLogger(ScannerThread.class);

  /**
   * The process id.
   */
  private int processId;

  /**
   * Request with parameters from command line.
   */
  private MappedScanRequest mappedScanRequest;

  /**
   * Processor which provides scanning and copying files.
   */
  private FileProcessor fileProcessor = FileProcessorImpl.getInstance();

  public ScannerThread(int processId, MappedScanRequest mappedScanRequest) {
    this.processId = processId;
    this.mappedScanRequest = mappedScanRequest;
  }

  @Override
  public void run() {
    createScanThreadExecutor(processId, mappedScanRequest, fileProcessor);
  }

  /**
   * Creates single thread for scan tree of directories and copy files.
   * It is necessary for timeout condition.
   *
   * @param processId
   * @param mappedScanRequest
   */
  private static void createScanThreadExecutor(int processId, MappedScanRequest mappedScanRequest, FileProcessor fileProcessor) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future future = executorService.submit(() -> {
      try {
        logger.debug("Process id = " + processId + ". Scan and copy files started");
        fileProcessor.process(processId, mappedScanRequest);
        logger.debug("Process id = " + processId + ". Scan and copy files finished");
      } catch (IOException e) {
        logger.error(e.getMessage());
      }
    });
    try {
      future.get(mappedScanRequest.getWaitInterval(), TimeUnit.MILLISECONDS);
    } catch (TimeoutException ex) {
      future.cancel(true); // cancel and send a thread interrupt
      logger.error("Scan process id = " + processId + " timeout");
    } catch (InterruptedException | ExecutionException e) {
      logger.error(e.getMessage());
    } finally {
      executorService.shutdownNow();
    }
  }
}
