package com.bea.diagnostics.notifications;

public class NotificationException extends Exception {
   public NotificationException() {
   }

   public NotificationException(String msg) {
      super(msg);
   }

   public NotificationException(Throwable t) {
      super(t);
   }

   public NotificationException(String msg, Throwable t) {
      super(msg, t);
   }
}
