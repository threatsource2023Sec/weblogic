package weblogic.server.embed.internal;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationListener;

public final class EmbeddedServerStartupClass {
   public static void main(String[] args) throws Exception {
      EmbeddedServerProvider.get().registerServerStateNotifier(new ServerStateNotificationListener());
      ServerRunner.monitorServerStart();
   }

   public static final class ServerStateNotificationListener implements NotificationListener {
      public void handleNotification(Notification notification, Object handback) {
         if (notification instanceof AttributeChangeNotification) {
            AttributeChangeNotification acn = (AttributeChangeNotification)notification;
            if (!acn.getAttributeName().equals("State")) {
               return;
            }

            Object newValue = acn.getNewValue();
            if (EmbeddedServerImpl.DEBUG) {
               EmbeddedServerImpl.LOGGER.info("Server state changed to " + newValue);
            }

            if (newValue.equals("RUNNING")) {
               ServerRunner.notifyStarterThread();
            }
         }

      }
   }
}
