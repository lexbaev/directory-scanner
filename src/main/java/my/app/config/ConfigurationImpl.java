package my.app.config;

import my.app.menu.impl.ScanMenuOption;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationImpl implements Configuration {

  /**
   * Initializes map with menu options.
   * <p>
   * Developed by Aliaksei Lizunou.
   *
   * @return
   */
  @Override
  public Map<String, Class> initMenuMap() {
    Map<String, Class> menuMap = new HashMap<>();
    menuMap.put("scan", ScanMenuOption.class);
    return Collections.unmodifiableMap(menuMap);
  }
}
