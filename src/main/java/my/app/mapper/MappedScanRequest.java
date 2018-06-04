package my.app.mapper;

/**
 * Interface for request with mapped arguments of scan command.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public interface MappedScanRequest extends MappedRequest {

  /**
   * Returns the source path parameter.
   *
   * @return
   */
  String getSourcePath();

  /**
   * Returns the destination path.
   *
   * @return
   */
  String getDestinationPath();

  /**
   * Returns the mask.
   *
   * @return
   */
  String getMask();

  /**
   * Returns the timeout interval.
   *
   * @return
   */
  long getWaitInterval();

  /**
   * Returns the flag to scan folders recursive.
   *
   * @return
   */
  boolean isIncludeSubfolders();

  /**
   * Returns the flag to delete source files after copying.
   *
   * @return
   */
  boolean isAutodelete();
}
