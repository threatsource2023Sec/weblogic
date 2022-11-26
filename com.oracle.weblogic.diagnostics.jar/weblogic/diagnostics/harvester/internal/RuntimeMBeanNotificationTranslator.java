package weblogic.diagnostics.harvester.internal;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class RuntimeMBeanNotificationTranslator {
   private final DebugLogger dbg = DebugSupport.getDebugLogger();

   public RuntimeMBeanNotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator notifGen) {
      this.dbg.debug("RuntimeMBeanNotificationTranslator=" + this + " ,managedResource=" + managedResource + ",notifGen=" + notifGen + " ");
      if (managedResource instanceof PartitionHarvesterRuntime) {
         ((PartitionHarvesterRuntime)managedResource).setNotificationGenerator(notifGen);
      }

   }
}
