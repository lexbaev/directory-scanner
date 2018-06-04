package my.app.mapper.impl;

import my.app.exceptions.ValidationException;
import my.app.mapper.MappedScanRequest;
import my.app.parser.impl.CommandLineParserImpl;
import org.apache.commons.cli.CommandLine;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandLineMapperScanMenuImplTest {

  private CommandLineParserImpl commandLineParser = (CommandLineParserImpl) CommandLineParserImpl.getInstance();
  private CommandLineMapperScanMenuImpl commandLineMapper = (CommandLineMapperScanMenuImpl) CommandLineMapperScanMenuImpl.getInstance();

  @Test
  public void testParseAndMap() throws ValidationException {
    String command = "scan -input C:/test/in/ -output C:/test/out/new/ -mask *.txt -waitInterval 10000 -includeSubfolders true -autoDelete false";
    CommandLine parsedCommandLine = commandLineParser.parse(command);

    assertEquals(1, parsedCommandLine.getArgs().length);
    assertEquals(6, parsedCommandLine.getOptions().length);

    MappedScanRequest mappedScanRequest = commandLineMapper.map(parsedCommandLine);

    assertEquals("c:/test/in/", mappedScanRequest.getSourcePath());
    assertEquals("c:/test/out/new/", mappedScanRequest.getDestinationPath());
    assertEquals("*.txt", mappedScanRequest.getMask());
    assertEquals(10000l, mappedScanRequest.getWaitInterval());
    assertEquals(true, mappedScanRequest.isIncludeSubfolders());
    assertEquals(false, mappedScanRequest.isAutodelete());
  }

  @Test
  public void testParseWithoutRequiredArgument() {
    String command = "scan -input C:/test/in/";
    org.apache.commons.cli.CommandLine parsedCommandLine = commandLineParser.parse(command);
    assertNull(parsedCommandLine);
  }

  @Test
  public void testParseNull() {
    String command = "scan";
    org.apache.commons.cli.CommandLine parsedCommandLine = commandLineParser.parse(command);
    assertNull(parsedCommandLine);
  }

  @Test (expected = ValidationException.class)
  public void testMapValidationException() throws ValidationException {
    String command = "scan -input aaa -output bbb -mask *.txt -waitInterval ccc -includeSubfolders ddd -autoDelete eee";
    CommandLine parsedCommandLine = commandLineParser.parse(command);

    assertEquals(1, parsedCommandLine.getArgs().length);
    assertEquals(6, parsedCommandLine.getOptions().length);

    commandLineMapper.map(parsedCommandLine);
  }

  @Test
  public void isValidBoolean() {
    assertTrue(commandLineMapper.isValidBoolean("true"));
    assertTrue(commandLineMapper.isValidBoolean("FALSE"));
    assertFalse(commandLineMapper.isValidBoolean("FAL"));
  }

  @Test
  public void isValidPath() {
    assertTrue(commandLineMapper.isValidPath("c:/111/"));
    assertTrue(commandLineMapper.isValidPath("c:\\111"));
    assertFalse(commandLineMapper.isValidPath("\\111"));
  }
}