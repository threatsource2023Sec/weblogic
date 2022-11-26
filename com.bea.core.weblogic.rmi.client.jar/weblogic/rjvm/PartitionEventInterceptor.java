package weblogic.rjvm;

import java.util.HashSet;
import javax.management.AttributeChangeNotification;
import javax.management.AttributeChangeNotificationFilter;
import javax.management.InstanceNotFoundException;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;

public class PartitionEventInterceptor implements NotificationListener {
   private static final DebugLogger debugConnection = DebugLogger.getDebugLogger("PartitionEventInterceptor");
   private static MBeanServer server;
   private static ObjectName partitionMB;
   private final ConnectionManager manager;

   protected PartitionEventInterceptor(ConnectionManager manager) {
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("PartitionEvenInterceptor.<init> is called. " + this);
      }

      this.manager = manager;

      try {
         String combea = "com.bea:Name=";
         String serviceName = "RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean";
         partitionMB = new ObjectName(combea + serviceName);
         InitialContext ctx = new InitialContext();
         server = (MBeanServer)ctx.lookup("java:comp/jmx/runtime");
      } catch (MalformedObjectNameException | NamingException var5) {
         debugConnection.debug("Error looking up PartitionRuntimes MBean", var5);
      }

      if (server != null && partitionMB != null) {
         AttributeChangeNotificationFilter filter = new AttributeChangeNotificationFilter();
         filter.enableAttribute("Partitions");

         try {
            if (debugConnection.isDebugEnabled()) {
               debugConnection.debug("Successfully Registered PartitionEvenInterceptor for partition property changes");
            }

            server.addNotificationListener(partitionMB, this, filter, (Object)null);
         } catch (Exception var6) {
            if (debugConnection.isDebugEnabled()) {
               debugConnection.debug("Error adding PartitionEvenInterceptor as listener", var6);
            }
         }

      }
   }

   public void handleNotification(Notification notification, Object handback) {
      if (notification instanceof AttributeChangeNotification) {
         AttributeChangeNotification attributeChange = (AttributeChangeNotification)notification;
         ObjectName[] oldvalue = (ObjectName[])((ObjectName[])attributeChange.getOldValue());
         ObjectName[] newvalue = (ObjectName[])((ObjectName[])attributeChange.getNewValue());
         HashSet set = new HashSet();
         ObjectName[] var7 = newvalue;
         int var8 = newvalue.length;

         int var9;
         ObjectName anOldvalue;
         for(var9 = 0; var9 < var8; ++var9) {
            anOldvalue = var7[var9];
            set.add(anOldvalue.getCanonicalName());
         }

         var7 = oldvalue;
         var8 = oldvalue.length;

         for(var9 = 0; var9 < var8; ++var9) {
            anOldvalue = var7[var9];
            if (!set.contains(anOldvalue.getCanonicalName())) {
               String partName = anOldvalue.getCanonicalName();
               if (debugConnection.isDebugEnabled()) {
                  debugConnection.debug("Closing connections for partition :" + partName);
               }

               this.manager.closeConnectionsForPartition(partName);
            }
         }
      }

   }

   public void close() {
      if (debugConnection.isDebugEnabled()) {
         debugConnection.debug("PartitionEvenInterceptor.close() is called. " + this);
      }

      try {
         if (server != null && partitionMB != null) {
            server.removeNotificationListener(partitionMB, this);
            if (debugConnection.isDebugEnabled()) {
               debugConnection.debug("Successfully unRegistered PartitionEvenInterceptor for partition property changes");
            }
         }
      } catch (ListenerNotFoundException | InstanceNotFoundException var2) {
         if (debugConnection.isDebugEnabled()) {
            debugConnection.debug("Error removing PartitionEvenInterceptor as listener", var2);
         }
      }

   }
}
