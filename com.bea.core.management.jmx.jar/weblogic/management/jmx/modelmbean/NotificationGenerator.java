package weblogic.management.jmx.modelmbean;

import javax.management.MBeanException;
import javax.management.Notification;
import javax.management.ObjectName;

public interface NotificationGenerator {
   long incrementSequenceNumber();

   void sendNotification(Notification var1) throws MBeanException;

   ObjectName getObjectName();

   boolean isSubscribed();
}
