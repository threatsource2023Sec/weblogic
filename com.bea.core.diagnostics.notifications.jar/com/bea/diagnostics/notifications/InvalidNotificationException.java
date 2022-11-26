package com.bea.diagnostics.notifications;

public class InvalidNotificationException extends IllegalArgumentException {
   public InvalidNotificationException() {
   }

   public InvalidNotificationException(String msg) {
      super(msg);
   }

   public InvalidNotificationException(Throwable t) {
      this.initCause(t);
   }

   public InvalidNotificationException(String msg, Throwable t) {
      super(msg);
      this.initCause(t);
   }
}
