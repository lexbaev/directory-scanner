package my.app.parser;

/**
 * Root interface for parsers.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public interface Parser<Command, Parsed> {

  /**
   * Method for parsing argument.
   * @param command
   * @return
   */
  Parsed parse(Command command);
}
