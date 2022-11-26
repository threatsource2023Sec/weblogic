package com.bea.diagnostics.notifications;

import javax.jms.Message;
import javax.jms.Session;

public interface JMSNotificationCustomizer extends NotificationCustomizer {
   Message createMessage(Session var1, Notification var2) throws NotificationPropagationException;
}
