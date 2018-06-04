package my.app.config;

import java.io.IOException;
import java.util.Map;

public interface Configuration {
  Map<String, Class> initMenuMap() throws IOException;
}
