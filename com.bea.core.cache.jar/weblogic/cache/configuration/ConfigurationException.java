package weblogic.cache.configuration;

import weblogic.cache.CacheRuntimeException;

public class ConfigurationException extends CacheRuntimeException {
   public ConfigurationException(String message) {
      super(message);
   }

   public ConfigurationException(String message, String property, String value) {
      super(message + "(Property[" + property + "], Value[" + value + "])");
   }

   public ConfigurationException(String message, String property, String value, Throwable t) {
      super(message + "(Property[" + property + "], Value[" + value + "])", t);
   }
}
