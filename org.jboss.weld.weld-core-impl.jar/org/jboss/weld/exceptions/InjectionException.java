package org.jboss.weld.exceptions;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings(
   value = {"NM_SAME_SIMPLE_NAME_AS_SUPERCLASS"},
   justification = "Workaround for exception classes poor i8ln support"
)
public class InjectionException extends javax.enterprise.inject.InjectionException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public InjectionException(Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(throwable.getLocalizedMessage());
   }

   public InjectionException(String message, Throwable throwable) {
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
