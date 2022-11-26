package com.bea.httppubsub.internal;

import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.bayeux.messages.DeliverEventMessage;
import com.bea.httppubsub.descriptor.ChannelPersistenceBean;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import weblogic.common.CompletionRequest;
import weblogic.store.ObjectHandler;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public class DefaultChannelPersistenceManager implements ChannelPersistenceManager {
   private static final String TIMEMANGER_PREFIX = "com.bea.httppubsub.channelmanager.timemanager.";
   private final String storeName;
   private final int duration;
   private PersistentStore store = null;
   private PersistentStoreConnection channelConnection = null;
   private ConcurrentLinkedQueue records = new ConcurrentLinkedQueue();
   private TimerManager tm = null;
   private final int scavangeInterval;
   private final ChannelId id;
   private final String name;
   private ChannelPersistenceBean bean = null;

   public DefaultChannelPersistenceManager(ChannelId chID, ChannelPersistenceBean channelPersistenceBean) {
      this.id = chID;
      this.name = this.id.getChannelName();
      this.bean = channelPersistenceBean;
      this.duration = channelPersistenceBean.getMaxPersistentMessageDurationSecs() * 1000;
      if (this.duration <= 0) {
         PubSubLogger.logInvalidPersistentDuration(this.duration);
         throw new IllegalArgumentException(PubSubLogger.logInvalidPersistentDurationLoggable(this.duration).getMessage());
      } else {
         this.storeName = channelPersistenceBean.getPersistentStore();
         this.store = this.getStore();
         this.scavangeInterval = Math.max(this.duration / 10, 1000);
      }
   }

   private PersistentStore getStore() {
      try {
         PersistentStore store = DefaultPersistentStoreManager.getInstance().getStore(this.storeName);
         if (store == null) {
            if (this.storeName != null) {
               PubSubLogger.logCannotFindPersistentStore(this.storeName, this.name);
            }

            store = DefaultPersistentStoreManager.getInstance().getDefaultStore();
         }

         return store;
      } catch (PersistentStoreException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public ChannelPersistenceBean getChannelPersistenceBean() {
      return this.bean;
   }

   public List loadEvents(PersistedClientRecord clientRecord, ChannelId subscriptionId) {
      if (this.store != null && this.channelConnection != null && !this.records.isEmpty()) {
         long now = System.currentTimeMillis();
         List events = new ArrayList();
         CompletionRequest completion = new CompletionRequest();
         PersistentStoreTransaction tx = null;

         try {
            tx = this.store.begin();
            Iterator it = this.records.iterator();

            while(true) {
               DeliverMessageRecord record;
               do {
                  do {
                     if (!it.hasNext()) {
                        tx.commit();
                        return events;
                     }

                     record = (DeliverMessageRecord)it.next();
                  } while(record.createdTime < clientRecord.getLastAccessTime());
               } while(this.isStale(record.createdTime, now));

               this.channelConnection.read(tx, record.handle, completion);
               PersistentStoreRecord storeRecord = (PersistentStoreRecord)completion.getResult();
               DeliverEventMessage event = (DeliverEventMessage)storeRecord.getData();
               ChannelId publishId = ChannelId.newInstance(event.getChannel());
               if (publishId.contains(subscriptionId) || subscriptionId.contains(publishId)) {
                  events.add(event);
               }

               completion.reset();
            }
         } catch (PersistentStoreException var13) {
            var13.printStackTrace();
            this.rollbackStoreTranscation(tx);
            return events;
         } catch (Throwable var14) {
            var14.printStackTrace();
            throw new RuntimeException(var14);
         }
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public void storeEvent(DeliverEventMessage event) {
      if (this.store != null && this.channelConnection != null && event != null) {
         ChannelId publishId = ChannelId.newInstance(event.getChannel());
         if (this.id.contains(publishId) || publishId.contains(this.id)) {
            PersistentStoreTransaction tx = null;

            try {
               tx = this.store.begin();
               PersistentHandle handle = this.channelConnection.create(tx, event, 4);
               this.records.offer(new DeliverMessageRecord(handle, event.getCreatedTime()));
               tx.commit();
            } catch (PersistentStoreException var5) {
               var5.printStackTrace();
               this.rollbackStoreTranscation(tx);
            }

         }
      }
   }

   public int getMessageCount() {
      return this.records.size();
   }

   public void init() {
      this.loadPersistentMessages();
      this.startScavenger();
   }

   public void destory() {
      this.stopScavenger();
      this.closeStoreConnection(this.channelConnection);
   }

   private void startScavenger() {
      TimerListener tunscav = new TimerListener() {
         public void timerExpired(Timer timer) {
            if (DefaultChannelPersistenceManager.this.channelConnection == null) {
               timer.cancel();
            } else if (!DefaultChannelPersistenceManager.this.records.isEmpty()) {
               long now = System.currentTimeMillis();
               PersistentStoreTransaction tx = null;

               try {
                  tx = DefaultChannelPersistenceManager.this.store.begin();
                  Iterator it = DefaultChannelPersistenceManager.this.records.iterator();

                  while(it.hasNext()) {
                     DeliverMessageRecord record = (DeliverMessageRecord)it.next();
                     if (DefaultChannelPersistenceManager.this.isStale(record.createdTime, now)) {
                        it.remove();
                        DefaultChannelPersistenceManager.this.channelConnection.delete(tx, record.handle, 1);
                     }
                  }

                  tx.commit();
               } catch (PersistentStoreException var7) {
                  DefaultChannelPersistenceManager.this.rollbackStoreTranscation(tx);
               }

            }
         }
      };
      this.tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager("com.bea.httppubsub.channelmanager.timemanager." + this.name, WorkManagerFactory.getInstance().getSystem());
      this.tm.scheduleAtFixedRate(tunscav, 0L, (long)this.scavangeInterval);
   }

   private void stopScavenger() {
      this.tm.stop();
   }

   private void loadPersistentMessages() {
      PersistentStoreTransaction tx = null;

      try {
         if (this.channelConnection == null) {
            this.channelConnection = this.store.createConnection(this.name, new PubSubObjectHandler());
         }

         tx = this.store.begin();
         PersistentStoreConnection.Cursor cursor = this.channelConnection.createCursor(0);
         long now = System.currentTimeMillis();
         List staleHandlers = new ArrayList();

         PersistentStoreRecord record;
         while((record = cursor.next()) != null) {
            PersistentHandle handle = record.getHandle();
            DeliverEventMessage event = (DeliverEventMessage)record.getData();
            if (this.isStale(event.getCreatedTime(), now)) {
               staleHandlers.add(handle);
            } else {
               this.records.offer(new DeliverMessageRecord(handle, event.getCreatedTime()));
            }
         }

         if (!staleHandlers.isEmpty()) {
            Iterator var10 = staleHandlers.iterator();

            while(var10.hasNext()) {
               PersistentHandle handle = (PersistentHandle)var10.next();
               this.channelConnection.delete(tx, handle, 1);
            }
         }

         tx.commit();
      } catch (PersistentStoreException var9) {
         var9.printStackTrace();
         this.rollbackStoreTranscation(tx);
      }

   }

   private boolean isStale(long createdTime, long now) {
      return createdTime + (long)this.duration <= now;
   }

   private void rollbackStoreTranscation(PersistentStoreTransaction tx) {
      if (tx != null) {
         try {
            tx.rollback();
         } catch (Exception var3) {
         }
      }

   }

   private void closeStoreConnection(PersistentStoreConnection conn) {
      if (conn != null) {
         conn.close();
      }

   }

   private void closeStore(PersistentStore store) {
      if (store != null) {
         try {
            store.close();
         } catch (PersistentStoreException var3) {
         }
      }

   }

   private class PubSubObjectHandler implements ObjectHandler {
      private PubSubObjectHandler() {
      }

      public Object readObject(ObjectInput in) throws ClassNotFoundException, IOException {
         DeliverEventMessage msg = new DeliverEventMessage();
         msg.readExternal(in);
         return msg;
      }

      public void writeObject(ObjectOutput out, Object obj) throws IOException {
         if (obj instanceof DeliverEventMessage) {
            ((DeliverEventMessage)obj).writeExternal(out);
         }

      }

      // $FF: synthetic method
      PubSubObjectHandler(Object x1) {
         this();
      }
   }

   private class DeliverMessageRecord {
      public PersistentHandle handle;
      public long createdTime;

      public DeliverMessageRecord(PersistentHandle handle, long createdTime) {
         this.handle = handle;
         this.createdTime = createdTime;
      }
   }
}
