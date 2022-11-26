package org.opensaml.core.config.provider;

import java.util.Properties;
import org.opensaml.core.config.ConfigurationPropertiesSource;

public class ThreadLocalConfigurationPropertiesSource implements ConfigurationPropertiesSource {
   public Properties getProperties() {
      return ThreadLocalConfigurationPropertiesHolder.getProperties();
   }
}
