package my.app.mapper.impl;

import my.app.mapper.MappedScanRequest;

/**
 * Request with mapped arguments of scan command.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class MappedScanRequestImpl implements MappedScanRequest {

  private String sourcePath;

  private String destinationPath;

  private String mask;

  private long waitInterval;

  private boolean isIncludeSubfolders;

  private boolean isAutodelete;

  public MappedScanRequestImpl(String sourcePath, String destinationPath, String mask, long waitInterval, boolean isIncludeSubfolders, boolean isAutodelete) {
    this.sourcePath = sourcePath;
    this.destinationPath = destinationPath;
    this.mask = mask;
    this.waitInterval = waitInterval;
    this.isIncludeSubfolders = isIncludeSubfolders;
    this.isAutodelete = isAutodelete;
  }

  @Override
  public String getSourcePath() {
    return sourcePath;
  }

  @Override
  public String getDestinationPath() {
    return destinationPath;
  }

  @Override
  public String getMask() {
    return mask;
  }

  @Override
  public long getWaitInterval() {
    return waitInterval;
  }

  @Override
  public boolean isIncludeSubfolders() {
    return isIncludeSubfolders;
  }

  @Override
  public boolean isAutodelete() {
    return isAutodelete;
  }
}
