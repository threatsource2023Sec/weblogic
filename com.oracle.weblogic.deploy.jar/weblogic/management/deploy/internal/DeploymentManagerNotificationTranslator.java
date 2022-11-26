package weblogic.management.deploy.internal;

import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class DeploymentManagerNotificationTranslator {
   public DeploymentManagerNotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator notifGen) {
      if (managedResource instanceof DeploymentManagerImpl) {
         ((DeploymentManagerImpl)managedResource).setNotificationGenerator(notifGen);
      }

   }
}
