package weblogic.management.deploy.internal;

import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class AppDeploymentRuntimeNotificationTranslator {
   public AppDeploymentRuntimeNotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator notifGen) {
      if (managedResource instanceof AppDeploymentRuntimeImpl) {
         ((AppDeploymentRuntimeImpl)managedResource).setNotificationGenerator(notifGen);
      }

   }
}
