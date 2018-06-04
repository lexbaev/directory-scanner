package my.app;

import my.app.config.Configuration;
import my.app.config.ConfigurationImpl;
import my.app.menu.MenuOption;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Scanner;

/**
 * The entry point for running the application.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class DirectoryScannerConsole {

  private final static Logger logger = Logger.getLogger(DirectoryScannerConsole.class);

  private static final String EXIT_COMMAND = "exit";

  public static void main(String[] args) {
    initLogger();
    Configuration configuration = new ConfigurationImpl();

    Map<String, Class> menuMap = null;
    try {
      menuMap = configuration.initMenuMap();
    } catch (IOException e) {
      logger.error("File properties is not found. " + e.getStackTrace());
    }
    if (logger.isInfoEnabled()) {
      for (String key : menuMap.keySet()) {
        logger.info("Initialized menu option: " + key);
      }
    }

    Scanner sc = new Scanner(System.in);
    String command;

    int processId = 1;
    while (!EXIT_COMMAND.equals(command = sc.nextLine().trim())) {
      logger.info("Entered command is: " + command);
      MenuOption menuOption = getMenuOption(menuMap, command);
      if (menuOption == null) {
        System.out.println("The command you entered is invalid. Please try again.");
        continue;
      }
      menuOption.select(command, processId);
      processId++;
    }
    System.exit(1);
  }

  /**
   * Initializes logger settings.
   */
  private static void initLogger() {
    String log4jConfigFile = System.getProperty("user.dir")
      + File.separator + "resources" + File.separator + "log4j.xml";
    DOMConfigurator.configure(log4jConfigFile);
  }

  /**
   * Retrieves selected menu option.
   *
   * @param menuMap
   * @param command
   * @return
   */
  private static MenuOption getMenuOption(Map<String, Class> menuMap, String command) {
    Class aClass = menuMap.get(command.split(" ")[0]);
    if (aClass == null) {
      return null;
    }
    Class<?> clazz = null;
    try {
      clazz = Class.forName(aClass.getName());
    } catch (ClassNotFoundException e) {
      logger.error("Command " + command + " is not correct. \n" + e.getStackTrace());
    }
    Constructor<?> ctor = null;
    try {
      ctor = clazz.getConstructor();
    } catch (NoSuchMethodException e) {
      logger.error("The class " + clazz.getName() + "should contain constructor without arguments. \n" + e.getStackTrace());
    }
    MenuOption menuOption = null;
    try {
      menuOption = (MenuOption) ctor.newInstance();
    } catch (InstantiationException e) {
      logger.error("Creating instance of class " + clazz.getName() + " failed. \n" + e.getStackTrace());
    } catch (IllegalAccessException | InvocationTargetException e) {
      logger.error(e.getStackTrace());
    }
    return menuOption;
  }
}
