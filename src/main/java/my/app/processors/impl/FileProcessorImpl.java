package my.app.processors.impl;

import my.app.mapper.MappedScanRequest;
import my.app.processors.FileProcessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Processor of scanning and copying files.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class FileProcessorImpl implements FileProcessor {

  private final static Logger logger = Logger.getLogger(FileProcessorImpl.class);

  /**
   * Locks file for reading.
   */
  private Lock lock = new ReentrantLock();

  private FileProcessorImpl() {
  }

  private static FileProcessor fileProcessor = new FileProcessorImpl();

  public static FileProcessor getInstance() {
    return fileProcessor;
  }

  public void process(Integer processId, MappedScanRequest mappedScanRequest) {
    File sourceDir = new File(mappedScanRequest.getSourcePath());
    if (!sourceDir.isDirectory()) {
      logger.error("The input path is not a folder: " + mappedScanRequest.getSourcePath());
      return;
    }

    File destinationDir = createDirectory(mappedScanRequest.getDestinationPath());
    File[] directoryListing = sourceDir.listFiles();

    if (directoryListing == null || directoryListing.length == 0) {
      logger.info("The selected folder does not contain any file.");
      return;
    }

    scanAndCopyFiles(processId, sourceDir, destinationDir, mappedScanRequest.getMask(),
      mappedScanRequest.isIncludeSubfolders(), mappedScanRequest.isAutodelete());
  }

  /**
   * Scans tree of directories
   *
   * @param sourceDir
   * @param mask
   * @return
   */
  protected void scanAndCopyFiles(Integer processId, File sourceDir, File destinationDir, String mask,
                                  boolean isIncludeSubfolders, boolean isAutodelete) {
    for (File file : sourceDir.listFiles()) {
      if (file.isDirectory() && isIncludeSubfolders) {
        File newFolder = new File(destinationDir.getPath() + File.separator + file.getName());
        newFolder.mkdir();
        scanAndCopyFiles(processId, file, newFolder, mask, isIncludeSubfolders, isAutodelete);
      } else {
        if (accept(file.getName(), mask)) {
          copyMoveFiles(processId, destinationDir, isAutodelete, file);
        }
      }
    }
  }

  /**
   * The method copies or if it is required then removes original files after copying.
   *
   * @param processId
   * @param destinationDir
   * @param isAutodelete
   * @param file
   */
  private void copyMoveFiles(Integer processId, File destinationDir, boolean isAutodelete, File file) {
    lock.lock();
    logger.debug("Found file: " + file.getPath() + " by process id = " + processId);
    logger.debug("Locked file: " + file.getName() + " by process id = " + processId);
    try {
      FileUtils.copyFileToDirectory(file, destinationDir);
      logger.debug("File " + file.getName() + " has been copied by process id = " + processId);
    } catch (IOException e) {
      logger.error("Copying files process failed. " + e.getMessage());
    }
    if (isAutodelete) {
      try {
        FileUtils.forceDelete(file);
        logger.debug("File " + file.getName() + " has been deleted by process id = " + processId);
      } catch (IOException e) {
        logger.error("Deleting files process failed. " + e.getMessage());
      }
    }
    logger.debug("Unlocked: " + file.getName() + " by process id = " + processId);
    lock.unlock();
  }

  /**
   * Checks if name of the file matches the mask.
   *
   * @param name
   * @param mask
   * @return
   */
  protected boolean accept(String name, String mask) {
    return name.matches(
      StringUtils.replaceEach(
        mask,
        new String[]{".", "*", "?"},
        new String[]{"\\.", ".*", ".{1}"}));
  }

  /**
   * Creates the new directory with destination path if it is not exist.
   *
   * @param destination
   * @return
   */
  private File createDirectory(String destination) {
    File destinationDir = new File(destination);
    if (!destinationDir.exists()) {
      destinationDir.mkdir();
    }
    return destinationDir;
  }
}
