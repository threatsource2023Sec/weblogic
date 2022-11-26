package com.solarmetric.manage.jmx.remote.jboss4;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import javax.management.Notification;
import javax.management.NotificationListener;
import org.jboss.jmx.adaptor.rmi.RMINotificationListener;

public class JBoss4RMINotificationListener extends UnicastRemoteObject implements RMINotificationListener {
   private NotificationListener listener;

   public JBoss4RMINotificationListener(NotificationListener listener) throws RemoteException {
      this.listener = listener;
   }

   public void handleNotification(Notification notification, Object handback) throws RemoteException {
      try {
         this.listener.handleNotification(notification, handback);
      } catch (Exception var4) {
         throw new RemoteException("Failed to handle notfication", var4);
      }
   }
}
