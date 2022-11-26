package org.jboss.weld.exceptions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Workaround for exception classes poor i8ln support"
)
public class IllegalStateException extends java.lang.IllegalStateException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public IllegalStateException(String message) {
      this.message = new WeldExceptionStringMessage(message);
   }

   public IllegalStateException(String message, Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(message);
   }

   public IllegalStateException(Throwable cause) {
      super(cause);
      this.message = new WeldExceptionStringMessage(cause.getLocalizedMessage());
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.message.getAsString();
   }
}
