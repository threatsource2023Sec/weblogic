package com.bea.logging;

public class InvalidSeverityStringException extends IllegalArgumentException {
   public InvalidSeverityStringException(String severity) {
      super("Invalid Severity " + severity);
   }
}
