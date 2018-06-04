package my.app.parser.impl;

import my.app.menu.impl.ScanMenuOption;
import my.app.parser.CommandLineParser;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * Command line parser.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class CommandLineParserImpl implements CommandLineParser {

  private final static Logger logger = Logger.getLogger(CommandLineParserImpl.class);

  private CommandLineParserImpl() {
  }

  private static CommandLineParser commandLineParser = new CommandLineParserImpl();

  public static CommandLineParser getInstance() {
    return commandLineParser;
  }

  /**
   * Parses command line.
   *
   * @param command
   * @return
   */
  public CommandLine parse(String command) {
    logger.debug("Parsing command: " + command);
    String[] args = StringUtils.split(command.toLowerCase(), " ");
    Options options = new Options();

    for (ScanMenuOption.Argument argument : ScanMenuOption.Argument.values()) {
      Option input = new Option(argument.getName(), argument.hasArgument(), argument.getDescription());
      input.setRequired(argument.isRequired());
      options.addOption(input);
    }

    org.apache.commons.cli.CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine commandLine = null;

    try {
      commandLine = parser.parse(options, args);
    } catch (ParseException e) {
      logger.warn(e.getMessage());
      formatter.printHelp("utility-name", options);
    }
    return commandLine;
  }
}
