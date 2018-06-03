package my.app.mapper;

/**
 * Interface for request with mapped arguments of scan command.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public interface MappedScanRequest extends MappedRequest{

  String getSourcePath();

  String getDestinationPath();

  String getMask();

  long getWaitInterval();

  boolean isIncludeSubfolders();

  boolean isAutodelete();
}
