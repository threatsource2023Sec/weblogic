package weblogic.management.internal;

import java.util.Date;
import javax.management.Notification;
import javax.management.ObjectName;

/** @deprecated */
@Deprecated
public final class ApplicationNotification extends Notification {
   public static final String LOADED = "weblogic.management.application.loaded";
   public static final String RELOADED = "weblogic.management.application.reloaded";
   public static final String DEPLOYED = "weblogic.management.application.deployed";
   public static final String UNDEPLOYED = "weblogic.management.application.undeployed";
   private static final long serialVersionUID = 154577046124260765L;
   private static long sequenceNumber = 0L;
   private static String appName = null;

   public ApplicationNotification(ObjectName source, String appNameParam, String type) {
      super(type, source, generateSequenceNumber(), (new Date()).getTime(), "Application  " + appNameParam + " Reloaded ");
      setAppName(appNameParam);
   }

   private static void setAppName(String param) {
      appName = param;
   }

   public static long getChangeNotificationCount() {
      return sequenceNumber;
   }

   public static String getAppName() {
      return appName;
   }

   private static synchronized long generateSequenceNumber() {
      return (long)(sequenceNumber++);
   }
}
