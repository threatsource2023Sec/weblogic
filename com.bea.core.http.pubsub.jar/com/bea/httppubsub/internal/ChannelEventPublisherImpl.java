package com.bea.httppubsub.internal;

import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import java.util.Iterator;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class ChannelEventPublisherImpl implements ChannelEventPublisher {
   private static final String DEFAULT_WORK_MANAGER_PREFIX = "com.bea.httppubsub.";
   private String serverName = null;
   private String workManagerName = null;
   private WorkManager workManager = null;
   Boolean disableWorkManager = null;

   public ChannelEventPublisherImpl(String serverName, String workManagerName) {
      if (serverName == null) {
         throw new IllegalArgumentException("PubSub server name can't be null");
      } else {
         this.serverName = serverName;
         this.workManagerName = workManagerName;
      }
   }

   public void notifyClients(ChannelEvent event) {
      Iterator clients = event.getInterestedClients();

      while(true) {
         while(clients.hasNext()) {
            Object next = clients.next();
            if (next instanceof LocalClient) {
               this.notifyLocalClient((LocalClient)next, event);
            } else {
               ClientImpl client = (ClientImpl)next;
               client.addSubscribedMessage((DeliverEventMessage)event.getMessage());
               if (!client.isScheduled()) {
                  synchronized(client) {
                     if (client.isScheduled()) {
                        continue;
                     }

                     client.setScheduled(true);
                  }

                  if (!this.isWorkManagerDisable()) {
                     WorkManager workManager = this.getWorkManager();
                     workManager.schedule(client);
                  } else {
                     client.run();
                  }
               }
            }
         }

         return;
      }
   }

   private synchronized boolean isWorkManagerDisable() {
      if (this.disableWorkManager == null) {
         this.disableWorkManager = "true".equalsIgnoreCase(System.getProperty("com.bea.httppubsub.internal.ChannelEventPublisher.disableWorkManager"));
      }

      return this.disableWorkManager;
   }

   private synchronized WorkManager getWorkManager() {
      if (this.workManager == null) {
         if (this.workManagerName != null) {
            this.workManager = WorkManagerFactory.getInstance().find(this.workManagerName);
         }

         if (this.workManager == null || this.workManager == WorkManagerFactory.getInstance().getDefault()) {
            this.workManager = WorkManagerFactory.getInstance().findOrCreate("com.bea.httppubsub." + this.serverName, 5, -1);
         }
      }

      return this.workManager;
   }

   private void notifyLocalClient(LocalClient client, ChannelEvent event) {
      ((LocalClientImpl)client).addSubscribedMessage((DeliverEventMessage)event.getMessage());
   }
}
