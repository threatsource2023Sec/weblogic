package org.jboss.weld.exceptions;

import java.util.List;

public class WeldException extends RuntimeException {
   private static final long serialVersionUID = 2L;
   private final WeldExceptionMessage message;

   public WeldException(Throwable throwable) {
      super(throwable);
      this.message = new WeldExceptionStringMessage(throwable.getLocalizedMessage());
   }

   public WeldException(List errors) {
      this.message = new WeldExceptionListMessage(errors);
   }

   public WeldException(String message) {
      this.message = new WeldExceptionStringMessage(message);
   }

   public WeldException(String message, Throwable throwable) {
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
