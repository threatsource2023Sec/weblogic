package weblogic.cache.store;

import commonj.work.Work;
import commonj.work.WorkManager;
import java.util.Map;
import java.util.Timer;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.CacheStore;
import weblogic.cache.configuration.BEACacheLogger;
import weblogic.cache.util.ListenerSupport;
import weblogic.cache.util.Logger;

public class StoreWork implements Work {
   private final StoreBuffer buffer;
   private CacheStore store;
   private int batchSize;
   private final ListenerSupport listeners;
   private boolean readyForForcedShutdown = false;
   private boolean readyForGracefulShutdown = false;
   private final Timer timer;
   private final long waitTimeOnEmptyBuffer;
   private WorkManager workManager;
   private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

   public StoreWork(StoreBuffer buffer, CacheStore store, int batchSize, ListenerSupport listeners, WorkManager workManager, Timer timer, long waitTimeOnEmptyBuffer) {
      this.buffer = buffer;
      this.setStore(store);
      this.setBatchSize(batchSize);
      this.listeners = listeners;
      this.timer = timer;
      this.waitTimeOnEmptyBuffer = waitTimeOnEmptyBuffer;
      this.setWorkManager(workManager);
   }

   public void setStore(CacheStore store) {
      this.store = store;
   }

   public void setBatchSize(int batchSize) {
      this.batchSize = batchSize;
   }

   public void setWorkManager(WorkManager manager) {
      this.workManager = manager;
   }

   public void run() {
      boolean bufferEmpty = false;

      while(!bufferEmpty && !this.readyForForcedShutdown) {
         Map entry = this.buffer.dequeue(this.batchSize);
         if (entry != null) {
            if (this.logger.isEnabled()) {
               this.logger.info("Trying to store entry: " + entry);
            }

            try {
               this.store.storeAll(entry);
               this.listeners.fireEvent(WritePolicy.WriteEvent.SUCCESS, entry, (Object)null);
            } catch (CacheRuntimeException var6) {
               CacheRuntimeException e = var6;

               try {
                  this.listeners.fireEvent(WritePolicy.WriteEvent.FAILURE, entry, e);
               } catch (ListenerSupport.ListenerSupportException var5) {
                  BEACacheLogger.logUnableToFireListeners(var5);
               }
            }
         } else {
            if (this.readyForGracefulShutdown) {
               if (this.logger.isEnabled()) {
                  this.logger.info("Did not find any entry, gracefully shutting down");
               }
            } else {
               if (this.logger.isEnabled()) {
                  this.logger.info("Did not find any entry, rescheduling for later");
               }

               this.schedule();
            }

            bufferEmpty = true;
         }
      }

   }

   public void release() {
      this.shutdown(false);
   }

   public boolean isDaemon() {
      return false;
   }

   public void shutdown(boolean graceful) {
      if (graceful) {
         this.readyForGracefulShutdown = true;
      } else {
         this.readyForForcedShutdown = true;
      }

      if (this.logger.isEnabled()) {
         this.logger.info("Store work marked for shutdown. Graceful = " + graceful);
      }

   }

   public void schedule() {
      this.timer.schedule(new StoreTimer(this.buffer, this, this.workManager), this.waitTimeOnEmptyBuffer, this.waitTimeOnEmptyBuffer);
      if (this.logger.isEnabled()) {
         this.logger.info("Scheduled store work for later");
      }

   }
}
