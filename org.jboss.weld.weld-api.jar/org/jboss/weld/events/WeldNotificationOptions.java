package org.jboss.weld.events;

import javax.enterprise.event.NotificationOptions;

public interface WeldNotificationOptions extends NotificationOptions {
   String MODE = "weld.async.notification.mode";
   String TIMEOUT = "weld.async.notification.timeout";

   static NotificationOptions withParallelMode() {
      return NotificationOptions.builder().set("weld.async.notification.mode", WeldNotificationOptions.NotificationMode.PARALLEL).build();
   }

   static NotificationOptions withTimeout(long timeout) {
      return NotificationOptions.builder().set("weld.async.notification.timeout", timeout).build();
   }

   public static enum NotificationMode {
      SERIAL,
      PARALLEL;

      public boolean isEqual(Object value) {
         return this.equals(of(value));
      }

      public static NotificationMode of(Object value) {
         if (value != null) {
            if (value instanceof NotificationMode) {
               return (NotificationMode)value;
            }

            try {
               return valueOf(value.toString().toUpperCase());
            } catch (IllegalArgumentException var2) {
            }
         }

         return null;
      }
   }
}
