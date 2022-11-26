package com.bea.diagnostics.notifications;

public class NotificationRuntimeException extends RuntimeException {
   public NotificationRuntimeException() {
   }

   public NotificationRuntimeException(String msg) {
      super(msg);
   }

   public NotificationRuntimeException(Throwable t) {
      super(t);
   }

   public NotificationRuntimeException(String msg, Throwable t) {
      super(msg, t);
   }
}
