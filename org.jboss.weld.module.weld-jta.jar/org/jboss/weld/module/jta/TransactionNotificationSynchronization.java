package org.jboss.weld.module.jta;

import java.util.Iterator;
import java.util.List;
import javax.transaction.Synchronization;

class TransactionNotificationSynchronization implements Synchronization {
   private final List notifications;

   public TransactionNotificationSynchronization(List notifications) {
      this.notifications = notifications;
   }

   public void afterCompletion(int status) {
      Iterator var2 = this.notifications.iterator();

      while(var2.hasNext()) {
         DeferredEventNotification notification = (DeferredEventNotification)var2.next();
         if (!notification.isBefore() && notification.getStatus().matches(status)) {
            notification.run();
         }
      }

   }

   public void beforeCompletion() {
      Iterator var1 = this.notifications.iterator();

      while(var1.hasNext()) {
         DeferredEventNotification notification = (DeferredEventNotification)var1.next();
         if (notification.isBefore()) {
            notification.run();
         }
      }

   }
}
