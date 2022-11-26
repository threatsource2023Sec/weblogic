package org.jboss.weld.bootstrap.events;

import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.logging.BootstrapLogger;

public abstract class ContainerEvent implements NotificationListener {
   private Extension receiver;

   public void preNotify(Extension extension) {
      this.receiver = extension;
   }

   public void postNotify(Extension extension) {
      this.receiver = null;
   }

   protected Extension getReceiver() {
      return this.receiver;
   }

   protected void checkWithinObserverNotification() {
      if (this.receiver == null) {
         throw BootstrapLogger.LOG.containerLifecycleEventMethodInvokedOutsideObserver();
      }
   }
}
