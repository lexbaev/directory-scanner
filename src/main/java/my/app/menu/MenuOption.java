package my.app.menu;

/**
 * All the menu options should implement this interface.
 */
public interface MenuOption {

  /**
   * The method runs the process according to the command.
   *
   * @param command
   * @param processId
   */
  void select(String command, int processId);
}
