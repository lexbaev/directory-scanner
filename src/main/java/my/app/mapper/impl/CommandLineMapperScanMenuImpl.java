package my.app.mapper.impl;

import my.app.exceptions.ValidationException;
import my.app.mapper.CommandLineMapper;
import my.app.mapper.MappedScanRequest;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.log4j.Logger;

import static my.app.menu.impl.ScanMenuOption.Argument.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Validation and Mapping of arguments to MappedScanRequest instance.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class CommandLineMapperScanMenuImpl implements CommandLineMapper {

  private final static Logger logger = Logger.getLogger(CommandLineMapperScanMenuImpl.class);

  private CommandLineMapperScanMenuImpl() {
  }

  private static CommandLineMapper commandLineMapper = new CommandLineMapperScanMenuImpl();

  public static CommandLineMapper getInstance() {
    return commandLineMapper;
  }

  @Override
  public MappedScanRequest map(CommandLine commandLine) throws ValidationException {
    if (commandLine == null) {
      System.out.println("The required arguments are not found. Please try again.");
      return null;
    }
    boolean isValidSource, isValidDestination, isValidWaitInterval, isValidIncludeSubfolder, isValidAutodelete;

    String source = commandLine.getOptionValue(INPUT.getName());
    isValidSource = isValidPath(source);

    String destination = commandLine.getOptionValue(OUTPUT.getName());
    isValidDestination = isValidPath(destination);

    String mask = commandLine.getOptionValue(MASK.getName());

    long waitInterval = 0L;
    try {
      waitInterval = Long.parseLong(commandLine.getOptionValue(WAIT_INTERVAL.getName()));
      isValidWaitInterval = true;
    } catch (NumberFormatException e) {
      isValidWaitInterval = false;
    }

    String includeSubfolders = commandLine.getOptionValue(INCLUDE_SUBFOLDERS.getName());
    isValidIncludeSubfolder = isValidBoolean(includeSubfolders);
    boolean isIncludeSubfolders = BooleanUtils.toBoolean(includeSubfolders);

    String autodelete = commandLine.getOptionValue(AUTODELETE.getName());
    isValidAutodelete = isValidBoolean(autodelete);
    boolean isAutodelete = BooleanUtils.toBoolean(autodelete);

    if (!isValidSource || !isValidDestination || !isValidWaitInterval || !isValidIncludeSubfolder || !isValidAutodelete) {
      throw new ValidationException(isValidSource, isValidDestination, isValidWaitInterval, isValidIncludeSubfolder, isValidAutodelete);
    }

    return new MappedScanRequestImpl(source, destination, mask, waitInterval, isIncludeSubfolders, isAutodelete);
  }

  /**
   * Validation text for boolean values
   *
   * @param text
   * @return
   */
  protected boolean isValidBoolean(String text) {
    return !isBlank(text) && text.toLowerCase().matches("true|false");
  }

  /**
   * The path validation
   *
   * @param path
   * @return
   */
  protected boolean isValidPath(String path) {
    return path.matches("^[a-zA-Z]:[\\\\S|*\\S]?.*$") || path.matches("^[a-zA-Z]:[/\\S|*\\S]?.*$");
  }
}
