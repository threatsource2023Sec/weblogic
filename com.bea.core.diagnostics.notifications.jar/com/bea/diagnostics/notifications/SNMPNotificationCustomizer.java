package com.bea.diagnostics.notifications;

import java.util.List;

public interface SNMPNotificationCustomizer extends NotificationCustomizer {
   List processNotification(Notification var1);
}
