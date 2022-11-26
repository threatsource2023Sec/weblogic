package weblogic.management.configuration;

import weblogic.utils.NestedError;

public final class ConfigurationError extends NestedError {
   private static final long serialVersionUID = -3034774634846232555L;

   public ConfigurationError(String message) {
      super(message);
   }

   public ConfigurationError(Throwable t) {
      super(t);
   }

   public ConfigurationError(String message, Throwable t) {
      super(message, t);
   }
}
