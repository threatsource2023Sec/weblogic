package com.sun.faces.config;

import javax.faces.FacesException;

public class ConfigurationException extends FacesException {
   private static final long serialVersionUID = 5088235742267602695L;

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
