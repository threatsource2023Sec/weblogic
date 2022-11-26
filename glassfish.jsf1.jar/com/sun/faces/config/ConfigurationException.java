package com.sun.faces.config;

import javax.faces.FacesException;

public class ConfigurationException extends FacesException {
   public ConfigurationException() {
   }

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
