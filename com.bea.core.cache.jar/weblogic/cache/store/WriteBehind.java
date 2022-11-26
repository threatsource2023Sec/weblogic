package weblogic.cache.store;

import commonj.work.WorkManager;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.configuration.BEACacheLogger;
import weblogic.cache.util.ListenerSupport;
import weblogic.cache.util.Logger;

public class WriteBehind extends WritePolicy {
   private final StoreBuffer buffer;
   private final StoreWork work;
   private final Timer timer = new Timer(true);
   private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

   public WriteBehind(CacheStore store, WorkManager storeWorkManager, int maxCapacity, long offerTimeout, int offerAttempts, int batchSize, long waitTimeOnEmptyQueue, WorkManager listenerWorkManager) throws IllegalArgumentException {
      super(store, listenerWorkManager);
      this.buffer = new StoreBuffer(maxCapacity, offerTimeout, offerAttempts);
      this.work = new StoreWork(this.buffer, store, batchSize, this.listeners, storeWorkManager, this.timer, waitTimeOnEmptyQueue);
      this.work.schedule();
   }

   public void setCacheStore(CacheStore store) {
      super.setCacheStore(store);
      this.work.setStore(store);
   }

   public void setBatchSize(int batchSize) {
      this.work.setBatchSize(batchSize);
   }

   public void setStoreWorkManager(WorkManager manager) {
      this.work.setWorkManager(manager);
   }

   public void setOfferTimeout(long offerTimeout) {
      this.buffer.setOfferTimeout(offerTimeout);
   }

   public void setOfferAttempts(int offerAttempts) {
      this.buffer.setOfferAttempts(offerAttempts);
   }

   public void store(Object key, Object value) {
      if (!this.started) {
         if (this.logger.isEnabled()) {
            this.logger.info("Policy not started. Not enqueuing to the buffer");
         }

      } else {
         try {
            try {
               boolean enqueued = this.buffer.enqueue(Collections.singletonMap(key, value));
               if (!enqueued) {
                  this.fireFailureListeners(key, value, new CacheRuntimeException("Store buffer full"));
                  if (this.logger.isEnabled()) {
                     this.logger.info("Store buffer full. Could not enqueue " + key + ", " + value + ". Failure listeners fired.");
                  }
               } else if (this.logger.isEnabled()) {
                  this.logger.info("Successfully enqueued " + key + ", " + value);
               }
            } catch (InterruptedException var4) {
               this.fireFailureListeners(key, value, new CacheRuntimeException("Unable to enqueue the update for store", var4));
            }
         } catch (ListenerSupport.ListenerSupportException var5) {
            BEACacheLogger.logUnableToFireListeners(var5);
         }

      }
   }

   public void storeAll(Map map) {
      if (!this.started) {
         if (this.logger.isEnabled()) {
            this.logger.info("Policy not started. Not enqueuing to the buffer");
         }

      } else {
         try {
            try {
               boolean enqueued = this.buffer.enqueue(map);
               if (!enqueued) {
                  this.fireFailureListeners(map, new CacheRuntimeException("Store buffer full"));
                  if (this.logger.isEnabled()) {
                     this.logger.info("Store buffer full. Could not enqueue " + map + ". Failure listeners fired.");
                  }
               } else if (this.logger.isEnabled()) {
                  this.logger.info("Successfully enqueued " + map);
               }
            } catch (InterruptedException var3) {
               this.fireFailureListeners(map, new CacheRuntimeException("Unable to enqueue the updates for store", var3));
            }
         } catch (ListenerSupport.ListenerSupportException var4) {
            BEACacheLogger.logUnableToFireListeners(var4);
         }

      }
   }

   public void shutdown(boolean graceful) {
      this.work.shutdown(graceful);
      super.shutdown(graceful);
   }

   public void start() {
      this.work.schedule();
      super.start();
   }
}
