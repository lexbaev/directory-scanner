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

  final static Logger logger = Logger.getLogger(ScannerThread.class);

  private int processId;
  private MappedScanRequest mappedScanRequest;

  public ScannerThread(int processId, MappedScanRequest mappedScanRequest) {
    this.processId = processId;
    this.mappedScanRequest = mappedScanRequest;
  }

  @Override
  public void run() {
    createScanThreadExecutor(processId, mappedScanRequest);
  }

  /**
   * Creates single thread for scan tree of directories and copy files.
   * It is necessary for timeout condition.
   *
   * @param processId
   * @param mappedScanRequest
   */
  private static void createScanThreadExecutor(int processId, MappedScanRequest mappedScanRequest) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future future = executorService.submit(() -> {
      FileProcessor fileHandler = new FileProcessorImpl();
      try {
        logger.info("Process id = " + processId + ". Scan and copy files started");
        fileHandler.process(processId, mappedScanRequest);
        logger.info("Process id = " + processId + ". Scan and copy files finished");
      } catch (IOException e) {
        logger.error(e.getStackTrace());
      }
    });
    try {
      future.get(mappedScanRequest.getWaitInterval(), TimeUnit.MILLISECONDS);
    } catch (TimeoutException ex) {
      future.cancel(true); // cancel and send a thread interrupt
      logger.error("Scan process id = " + processId + " timeout");
    } catch (InterruptedException | ExecutionException e) {
      logger.error(e.getStackTrace());
    } finally {
      executorService.shutdownNow();
    }
  }

  //  @Override
//  public void run() {
//    System.out.println("Scan and copy files (process id = " + processId + ") started");
//    FileProcessor fileHandler = new FileProcessorImpl();
//    try {
//      fileHandler.process(mappedScanRequest);
//      Thread.sleep(5000);
//    } catch (IOException e) {
//      e.printStackTrace();  //todo add logging
//    } catch (InterruptedException e) {
//      e.printStackTrace();  //todo add logging
//      return;
//    }
//    System.out.println("Scan and copy files (process id = " + processId + ") finished");
//  }
}
