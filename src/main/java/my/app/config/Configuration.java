package my.app.config;

import java.io.IOException;
import java.util.Map;

/**
 * Interface for initialization of map with menu options.
 * <p>
 * Developed by Aliaksei Lizunou.
 */
public interface Configuration {
  Map<String, Class> initMenuMap() throws IOException;
}
