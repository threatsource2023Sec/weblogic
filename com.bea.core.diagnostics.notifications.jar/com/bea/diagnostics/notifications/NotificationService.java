package com.bea.diagnostics.notifications;

public interface NotificationService {
   String getName();

   String getType();

   boolean isEnabled();

   void setEnabled(boolean var1);

   void send(Notification var1) throws NotificationPropagationException;

   void initialize();

   void destroy();
}
