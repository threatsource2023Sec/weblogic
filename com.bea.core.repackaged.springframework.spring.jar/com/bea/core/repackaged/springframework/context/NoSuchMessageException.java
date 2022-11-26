package com.bea.core.repackaged.springframework.context;

import java.util.Locale;

public class NoSuchMessageException extends RuntimeException {
   public NoSuchMessageException(String code, Locale locale) {
      super("No message found under code '" + code + "' for locale '" + locale + "'.");
   }

   public NoSuchMessageException(String code) {
      super("No message found under code '" + code + "' for locale '" + Locale.getDefault() + "'.");
   }
}
