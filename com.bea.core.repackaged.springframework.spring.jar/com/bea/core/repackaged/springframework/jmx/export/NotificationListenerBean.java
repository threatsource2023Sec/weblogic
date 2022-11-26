package com.bea.core.repackaged.springframework.jmx.export;

import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.support.NotificationListenerHolder;
import com.bea.core.repackaged.springframework.util.Assert;
import javax.management.NotificationListener;

public class NotificationListenerBean extends NotificationListenerHolder implements InitializingBean {
   public NotificationListenerBean() {
   }

   public NotificationListenerBean(NotificationListener notificationListener) {
      Assert.notNull(notificationListener, (String)"NotificationListener must not be null");
      this.setNotificationListener(notificationListener);
   }

   public void afterPropertiesSet() {
      if (this.getNotificationListener() == null) {
         throw new IllegalArgumentException("Property 'notificationListener' is required");
      }
   }

   void replaceObjectName(Object originalName, Object newName) {
      if (this.mappedObjectNames != null && this.mappedObjectNames.contains(originalName)) {
         this.mappedObjectNames.remove(originalName);
         this.mappedObjectNames.add(newName);
      }

   }
}
