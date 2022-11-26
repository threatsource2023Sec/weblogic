package com.bea.core.repackaged.springframework.jmx.export.notification;

import com.bea.core.repackaged.springframework.util.Assert;
import javax.management.AttributeChangeNotification;
import javax.management.MBeanException;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanNotificationBroadcaster;

public class ModelMBeanNotificationPublisher implements NotificationPublisher {
   private final ModelMBeanNotificationBroadcaster modelMBean;
   private final ObjectName objectName;
   private final Object managedResource;

   public ModelMBeanNotificationPublisher(ModelMBeanNotificationBroadcaster modelMBean, ObjectName objectName, Object managedResource) {
      Assert.notNull(modelMBean, (String)"'modelMBean' must not be null");
      Assert.notNull(objectName, (String)"'objectName' must not be null");
      Assert.notNull(managedResource, "'managedResource' must not be null");
      this.modelMBean = modelMBean;
      this.objectName = objectName;
      this.managedResource = managedResource;
   }

   public void sendNotification(Notification notification) {
      Assert.notNull(notification, (String)"Notification must not be null");
      this.replaceNotificationSourceIfNecessary(notification);

      try {
         if (notification instanceof AttributeChangeNotification) {
            this.modelMBean.sendAttributeChangeNotification((AttributeChangeNotification)notification);
         } else {
            this.modelMBean.sendNotification(notification);
         }

      } catch (MBeanException var3) {
         throw new UnableToSendNotificationException("Unable to send notification [" + notification + "]", var3);
      }
   }

   private void replaceNotificationSourceIfNecessary(Notification notification) {
      if (notification.getSource() == null || notification.getSource().equals(this.managedResource)) {
         notification.setSource(this.objectName);
      }

   }
}
