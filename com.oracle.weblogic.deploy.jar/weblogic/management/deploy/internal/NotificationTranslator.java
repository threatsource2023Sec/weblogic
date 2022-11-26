package weblogic.management.deploy.internal;

import javax.management.MBeanException;
import javax.management.ObjectName;
import weblogic.management.DeploymentNotification;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.deploy.DeploymentCompatibilityEvent;
import weblogic.management.deploy.DeploymentCompatibilityEventHandler;
import weblogic.management.deploy.DeploymentCompatibilityEventManager;
import weblogic.management.jmx.modelmbean.NotificationGenerator;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;

public class NotificationTranslator implements DeploymentCompatibilityEventHandler {
   private ObjectName objName;
   private NotificationGenerator generator;

   public NotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator generator) {
      this.generator = generator;
      this.objName = generator.getObjectName();
      if (managedResource instanceof ApplicationMBean) {
         ((ApplicationMBean)managedResource).addHandler(this);
      } else {
         ((DeploymentCompatibilityEventManager)managedResource).addHandler(this);
      }

   }

   public void handleEvent(DeploymentCompatibilityEvent event) {
      if (this.generator.isSubscribed()) {
         DeploymentNotification notification = null;
         if ("weblogic.deployment.application.module".equals(event.getEventType())) {
            notification = new DeploymentNotification(this.objName, this.generator.incrementSequenceNumber(), event.getServerName(), event.getApplicationName(), event.getModuleName(), event.getTransition(), event.getCurrentState(), event.getTargetState());
         } else if ("weblogic.deployment.application".equals(event.getEventType())) {
            notification = new DeploymentNotification(this.objName, this.generator.incrementSequenceNumber(), event.getServerName(), event.getApplicationName(), event.getApplicationPhase());
            notification.setTask(event.getTaskID());
         }

         if (notification == null) {
            throw new AssertionError("Invalid Deployment Notfication Type " + event);
         } else {
            try {
               this.generator.sendNotification(notification);
            } catch (MBeanException var4) {
               throw new AssertionError(var4);
            }
         }
      }
   }
}
