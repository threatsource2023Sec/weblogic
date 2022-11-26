package weblogic.management.internal;

import weblogic.management.configuration.ConfigurationException;

public class InteractiveConfigurationException extends ConfigurationException {
   public InteractiveConfigurationException(String message) {
      super(message);
   }

   public InteractiveConfigurationException(Throwable t) {
      super(t);
   }

   public InteractiveConfigurationException(String message, Throwable t) {
      super(message, t);
   }
}
