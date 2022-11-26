package com.bea.diagnostics.notifications;

public class NotificationCreateException extends NotificationException {
   public NotificationCreateException() {
   }

   public NotificationCreateException(String msg) {
      super(msg);
   }

   public NotificationCreateException(Throwable t) {
      super(t);
   }

   public NotificationCreateException(String msg, Throwable t) {
      super(msg, t);
   }
}
