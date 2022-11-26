package com.bea.diagnostics.notifications;

public interface JMXNotificationCustomizer extends NotificationCustomizer {
   javax.management.Notification createJMXNotification(Notification var1);
}
