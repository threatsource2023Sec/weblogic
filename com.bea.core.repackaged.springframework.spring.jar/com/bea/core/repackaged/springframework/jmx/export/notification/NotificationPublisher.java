package com.bea.core.repackaged.springframework.jmx.export.notification;

import javax.management.Notification;

@FunctionalInterface
public interface NotificationPublisher {
   void sendNotification(Notification var1) throws UnableToSendNotificationException;
}
