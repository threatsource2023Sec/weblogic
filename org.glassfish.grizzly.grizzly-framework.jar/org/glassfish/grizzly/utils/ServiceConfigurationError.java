package org.glassfish.grizzly.utils;

public class ServiceConfigurationError extends Error {
   public ServiceConfigurationError(String message) {
      super(message);
   }

   public ServiceConfigurationError(Throwable throwable) {
      super(throwable);
   }
}
