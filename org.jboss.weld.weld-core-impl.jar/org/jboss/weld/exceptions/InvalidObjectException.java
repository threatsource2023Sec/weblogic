package org.jboss.weld.exceptions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Workaround for exception classes poor i8ln support"
)
public class InvalidObjectException extends java.io.InvalidObjectException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public InvalidObjectException(String message) {
      super((String)null);
      this.message = new WeldExceptionStringMessage(message);
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.message.getAsString();
   }
}
