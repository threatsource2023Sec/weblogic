package weblogic.diagnostics.watch;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class NotificationTranslator {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");

   public NotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator notifGen) {
      if (managedResource != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Setting notification generator for " + managedResource.getClass().getSimpleName());
      }

      if (managedResource instanceof JMXNotificationProducer) {
         ((JMXNotificationProducer)managedResource).setNotificationGenerator(notifGen);
      } else if (managedResource instanceof WatchNotificationRuntimeMBeanImpl) {
         ((WatchNotificationRuntimeMBeanImpl)managedResource).setNotificationGenerator(notifGen);
      } else if (managedResource instanceof JMXNotificationSource) {
         ((JMXNotificationSource)managedResource).setNotificationGenerator(notifGen);
      }

   }
}
