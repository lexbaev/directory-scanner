package my.app.menu.impl;

import my.app.exceptions.ValidationException;
import my.app.menu.MenuOption;
import my.app.mapper.CommandLineMapper;
import my.app.parser.CommandLineParser;
import my.app.mapper.MappedScanRequest;
import my.app.mapper.impl.CommandLineMapperScanMenuImpl;
import my.app.parser.impl.CommandLineParserImpl;
import my.app.threads.ScannerThread;
import org.apache.commons.cli.CommandLine;
import org.apache.log4j.Logger;

/**
 * The menu option of scanning and copying files.
 * <p>
 * Developed by Aliaksei Lizunou
 */
public class ScanMenuOption implements MenuOption {

  private final static Logger logger = Logger.getLogger(ScanMenuOption.class);

  /**
   * Command line parser
   */
  private CommandLineParser parser = CommandLineParserImpl.getInstance();

  /**
   * Mapper of arguments to MappedScanRequest instance.
   */
  private CommandLineMapper mapper = CommandLineMapperScanMenuImpl.getInstance();

  /**
   * The arguments enumeration of scan command.
   */
  public enum Argument {
    INPUT("input", true, true, "Path to input directory to be scanned"),
    OUTPUT("output", true, true, "Path to output directory, where files will be copied"),
    MASK("mask", true, true, "File names mask, which is used to detect required files"),
    WAIT_INTERVAL("waitinterval", true, true, "Time interval with which application poll the filesystem"),
    INCLUDE_SUBFOLDERS("includesubfolders", true, true, "Turns on/off scanning of subfolders within input folder"),
    AUTODELETE("autodelete", true, true, "Turns on/off automatic deleting of copied files");

    private String name;
    private boolean argument;
    private boolean required;
    private String description;

    Argument(String name, boolean argument, boolean required, String description) {
      this.name = name;
      this.argument = argument;
      this.required = required;
      this.description = description;
    }

    public String getName() {
      return name;
    }

    public boolean hasArgument() {
      return argument;
    }

    public boolean isRequired() {
      return required;
    }

    public String getDescription() {
      return description;
    }
  }

  @Override
  public void select(String command, int processId) {
    CommandLine commandLine = parser.parse(command);
    MappedScanRequest mappedRequest;
    try {
      mappedRequest = mapper.map(commandLine);
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
      return;
    }
    if (mappedRequest == null) {
      logger.error("Mapping of arguments is invalid.");
      return;
    }
    Thread thread = new ScannerThread(processId, mappedRequest);
    thread.start();
  }
}
