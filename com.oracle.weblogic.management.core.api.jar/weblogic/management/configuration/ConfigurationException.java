package weblogic.management.configuration;

import weblogic.management.ManagementException;

public class ConfigurationException extends ManagementException {
   public ConfigurationException(String message) {
      super(message);
   }

   public ConfigurationException(Throwable t) {
      super(t);
   }

   public ConfigurationException(String message, Throwable t) {
      super(message, t);
   }
}
