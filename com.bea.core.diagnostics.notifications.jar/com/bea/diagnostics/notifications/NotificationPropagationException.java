package com.bea.diagnostics.notifications;

public class NotificationPropagationException extends NotificationRuntimeException {
   public NotificationPropagationException() {
   }

   public NotificationPropagationException(String message) {
      super(message);
   }

   public NotificationPropagationException(String message, Throwable cause) {
      super(message, cause);
   }

   public NotificationPropagationException(Throwable cause) {
      super(cause);
   }
}
