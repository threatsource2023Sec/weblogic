package com.solarmetric.manage.jmx.remote.jboss4;

import java.io.Serializable;
import java.rmi.RemoteException;
import javax.management.Notification;
import javax.management.NotificationListener;
import org.jboss.jmx.adaptor.rmi.RMINotificationListener;

public class JBoss4DelegatingNotificationListener implements NotificationListener, Serializable {
   private RMINotificationListener remoteListener;

   public JBoss4DelegatingNotificationListener(RMINotificationListener remoteListener) {
      this.remoteListener = remoteListener;
   }

   public void handleNotification(Notification notification, Object handback) {
      try {
         this.remoteListener.handleNotification(notification, handback);
      } catch (RemoteException var4) {
         throw new RuntimeException("remote listener failed to handle a notification: " + var4.getMessage());
      }
   }
}
