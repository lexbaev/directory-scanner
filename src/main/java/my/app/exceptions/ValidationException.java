package my.app.exceptions;

public class ValidationException extends Exception {

  private String message;

  public ValidationException(boolean isValidSource, boolean isValidDestination, boolean isValidWaitInterval, boolean isValidIncludeSubfolder, boolean isValidAutoDelete) {
    StringBuilder message = new StringBuilder("Error: Scanner was not started due to following reasons: \n");
    if (!isValidSource) {
      message.append("\tInvalid value for input \n");
    }
    if (!isValidDestination) {
      message.append("\tInvalid value for output \n");
    }
    if (!isValidWaitInterval) {
      message.append("\tInvalid value for waitInterval \n");
    }
    if (!isValidIncludeSubfolder) {
      message.append("\tInvalid value for includeSubfolder \n");
    }
    if (!isValidAutoDelete) {
      message.append("\tInvalid value for autoDelete \n");
    }
    this.message = message.toString();
  }

  @Override
  public String getMessage() {
    return message;
  }
}
