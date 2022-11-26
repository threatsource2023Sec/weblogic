package weblogic.management.logging;

import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class NotificationTranslator {
   public NotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator generator) {
      LogBroadcaster logBroadcaster = (LogBroadcaster)managedResource;
      logBroadcaster.addNotificationGenerator(generator);
   }
}
