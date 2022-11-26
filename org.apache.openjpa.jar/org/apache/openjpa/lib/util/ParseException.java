package org.apache.openjpa.lib.util;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class ParseException extends NestableRuntimeException {
   public ParseException() {
   }

   public ParseException(String msg) {
      super(msg);
   }

   public ParseException(Localizer.Message msg) {
      super(msg.getMessage());
   }

   public ParseException(Throwable cause) {
      super(cause);
   }

   public ParseException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public ParseException(Localizer.Message msg, Throwable cause) {
      super(msg.getMessage(), cause);
   }
}
