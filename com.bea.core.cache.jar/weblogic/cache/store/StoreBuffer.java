package weblogic.cache.store;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import weblogic.cache.util.Logger;

public class StoreBuffer extends LinkedBlockingQueue {
   private long offerTimeout;
   private int offerAttempts;
   private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

   public StoreBuffer(int maxCapacity, long offerTimeout, int offerAttempts) {
      super(maxCapacity);
      this.setOfferTimeout(offerTimeout);
      this.setOfferAttempts(offerAttempts);
   }

   public void setOfferTimeout(long offerTimeout) {
      this.offerTimeout = offerTimeout;
   }

   public void setOfferAttempts(int offerAttempts) {
      assert offerAttempts > 0;

      this.offerAttempts = offerAttempts;
   }

   public boolean enqueue(Map e) throws InterruptedException {
      boolean buffered = false;

      int attempts;
      for(attempts = 1; !buffered && attempts <= this.offerAttempts; ++attempts) {
         buffered = this.offer(e, this.offerTimeout, TimeUnit.MILLISECONDS);
      }

      if (this.logger.isEnabled()) {
         this.logger.info("Attempt to enqueue " + e + " in " + (attempts - 1) + " attempts succeeded or not: " + buffered);
      }

      return buffered;
   }

   public Map dequeue(int batchSize) {
      Map m = null;
      if (batchSize <= 1) {
         m = (Map)this.poll();
      } else {
         boolean entryFound = true;

         for(int count = 1; count <= batchSize && entryFound; ++count) {
            Map entry = (Map)this.poll();
            if (entry == null) {
               entryFound = false;
            } else {
               if (m == null) {
                  m = new HashMap();
               }

               ((Map)m).putAll(entry);
            }
         }
      }

      if (this.logger.isEnabled()) {
         this.logger.info("Dequeuing " + m);
      }

      return (Map)m;
   }
}
