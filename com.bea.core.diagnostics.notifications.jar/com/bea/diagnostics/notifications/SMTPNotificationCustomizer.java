package com.bea.diagnostics.notifications;

public interface SMTPNotificationCustomizer extends NotificationCustomizer {
   String getSubject(Notification var1);

   String getBody(Notification var1);
}
