package org.opensaml.core.xml.config;

import javax.annotation.Nullable;

public class XMLConfigurationException extends Exception {
   private static final long serialVersionUID = -6777602050296807774L;

   public XMLConfigurationException() {
   }

   public XMLConfigurationException(@Nullable String message) {
      super(message);
   }

   public XMLConfigurationException(@Nullable Exception wrappedException) {
      super(wrappedException);
   }

   public XMLConfigurationException(@Nullable String message, @Nullable Exception wrappedException) {
      super(message, wrappedException);
   }
}
