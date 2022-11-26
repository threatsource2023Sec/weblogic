package weblogic.diagnostics.watch;

import java.io.Serializable;
import javax.management.Notification;

/** @deprecated */
@Deprecated
public final class JMXWatchNotification extends Notification implements Serializable {
   static final long serialVersionUID = -7648375988167013033L;
   public static final String GLOBAL_JMX_NOTIFICATION_PRODUCER_NAME = "DiagnosticsJMXNotificationSource";
   private WatchNotification watchNotification = null;

   JMXWatchNotification(String notifType, Object watchName, long sequenceNumber, long timeStamp, String message, WatchNotification watchNotification) {
      super(notifType, watchName, sequenceNumber, timeStamp, message);
      this.watchNotification = watchNotification;
   }

   public WatchNotification getExtendedInfo() {
      return this.watchNotification;
   }
}
