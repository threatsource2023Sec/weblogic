package com.bea.core.repackaged.springframework.jmx.export.notification;

import com.bea.core.repackaged.springframework.jmx.JmxException;

public class UnableToSendNotificationException extends JmxException {
   public UnableToSendNotificationException(String msg) {
      super(msg);
   }

   public UnableToSendNotificationException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
