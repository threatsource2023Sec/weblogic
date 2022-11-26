package weblogic.diagnostics.watch;

import com.bea.diagnostics.notifications.Notification;

interface WatchNotificationListener {
   String getNotificationName();

   boolean isEnabled();

   boolean isDisabled();

   void setEnabled();

   void setDisabled();

   int getNotificationTimeout();

   void processWatchNotification(Notification var1);

   void cancel();

   void reset();
}
