package org.jboss.weld.exceptions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Workaround for exception classes poor i8ln support"
)
public class CreationException extends javax.enterprise.inject.CreationException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public CreationException(String message) {
      this.message = new WeldExceptionStringMessage(message);
   }

   public CreationException(String message, Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(message);
   }

   public String getLocalizedMessage() {
      return this.getMessage();
   }

   public String getMessage() {
      return this.message.getAsString();
   }
}
