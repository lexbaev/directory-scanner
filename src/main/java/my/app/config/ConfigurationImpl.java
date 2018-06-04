package my.app.config;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Initializes map with menu options.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public class ConfigurationImpl implements Configuration {

  private final static Logger logger = Logger.getLogger(ConfigurationImpl.class);

  @Override
  public Map<String, Class> initMenuMap() {
    InputStream prop = getClass().getResourceAsStream("/config/config.properties");
    Properties config = new Properties();
    try {
      config.load(prop);
    } catch (IOException e) {
      logger.error("The properties file is not found or invalid. " + e.getStackTrace());
      System.exit(1);
    }

    Map<String, Class> menuMap = new HashMap<>();
    for (String key : config.stringPropertyNames()) {
      try {
        menuMap.put(key, Class.forName(config.getProperty(key)));
      } catch (ClassNotFoundException e) {
        logger.error("The class " + config.getProperty(key) + "from configuration properties is not found. " + e.getStackTrace());
        System.exit(1);
      }
    }
    return Collections.unmodifiableMap(menuMap);
  }
}
