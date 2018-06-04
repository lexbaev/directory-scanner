package my.app.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValidationExceptionTest {

  @Test
  public void getMessage() {
    String outputMessage = "Error: Scanner was not started due to following reasons: \n" +
      "\tInvalid value for input \n" +
      "\tInvalid value for output \n" +
      "\tInvalid value for waitInterval \n" +
      "\tInvalid value for includeSubfolder \n" +
      "\tInvalid value for autoDelete \n";
    ValidationException validationException = new ValidationException(false, false, false, false, false);
    assertEquals(outputMessage, validationException.getMessage());
  }
}