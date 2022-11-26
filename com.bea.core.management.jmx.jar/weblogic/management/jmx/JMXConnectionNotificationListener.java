package weblogic.management.jmx;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;

class JMXConnectionNotificationListener implements NotificationListener {
   private MBeanServerConnection mConn = null;
   private JMXConnector conn = null;

   public JMXConnectionNotificationListener(MBeanServerConnection mConn, JMXConnector conn) {
      this.mConn = mConn;
      this.conn = conn;
   }

   public MBeanServerConnection getMBeanServerConnection() {
      return this.mConn;
   }

   public void handleNotification(Notification notification, Object handback) {
      if (notification instanceof JMXConnectionNotification) {
         JMXConnectionNotification jmxCn = (JMXConnectionNotification)notification;
         if (jmxCn.getType().equals("jmx.remote.connection.closed")) {
            MBeanServerInvocationHandler.ObjectNameManagerFactory.disconnected(this.mConn);

            try {
               ((JMXConnector)handback).removeConnectionNotificationListener(this);
            } catch (ListenerNotFoundException var5) {
            }

            MBeanServerInvocationHandler.connectionIDMap.remove(this.conn);
         }
      }

   }
}
