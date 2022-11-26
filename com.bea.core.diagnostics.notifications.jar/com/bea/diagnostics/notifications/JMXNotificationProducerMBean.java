package com.bea.diagnostics.notifications;

import javax.management.NotificationBroadcaster;

public interface JMXNotificationProducerMBean extends NotificationBroadcaster {
   void sendNotification(javax.management.Notification var1);

   long generateSequenceNumber();

   boolean isSubscribed();
}
