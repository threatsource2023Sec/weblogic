package weblogic.jdbc.common.rac.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.common.ResourceException;

class ONSUtil {
   static final String USE_COMMONJ_SYSPROP = "weblogic.jdbc.ons.useCommonj";

   static void initializeNotificationManager() throws ResourceException {
      if (Boolean.valueOf(System.getProperty("weblogic.jdbc.ons.useCommonj", "true"))) {
         try {
            Class workloadManagerClass = Class.forName("oracle.ons.spi.WorkloadManager");
            Class socketManagerClass = Class.forName("oracle.ons.spi.SocketManager");
            Class notificationManagerClass = Class.forName("oracle.ons.NotificationManager");
            Class defaultSocketManagerClass = Class.forName("oracle.ons.DefaultSocketManager");
            Object socketManager = defaultSocketManagerClass.newInstance();
            Method initialize = notificationManagerClass.getMethod("initialize", workloadManagerClass, socketManagerClass);
            initialize.invoke((Object)null, new WLWorkloadManager(), socketManager);
         } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var6) {
            throw new ResourceException(var6.toString(), var6);
         }
      }
   }
}
