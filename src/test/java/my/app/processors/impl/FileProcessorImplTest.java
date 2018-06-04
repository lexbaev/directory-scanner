package my.app.processors.impl;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileProcessorImplTest {

  private FileProcessorImpl fileProcessor = (FileProcessorImpl) FileProcessorImpl.getInstance();

  @Test
  public void testAccept() {
    assertTrue(fileProcessor.accept("111.txt","*.txt"));
    assertTrue(fileProcessor.accept("111.xml","*.???"));
    assertFalse(fileProcessor.accept("111.xml","*.txt"));
  }
}
