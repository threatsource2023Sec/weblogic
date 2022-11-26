package weblogic.io.common.internal;

import java.util.Hashtable;

class WaitHashtable extends Hashtable {
   boolean cancelled = false;
   private String error = "";
   int numPut = 0;
   private static final int CLIENT_TIMEOUT_MILLSECS = 120000;

   public WaitHashtable(int initialCapacity) {
      super(initialCapacity);
   }

   public WaitHashtable(int initialCapacity, float loadFactor) {
      super(initialCapacity, loadFactor);
   }

   public synchronized Object put(Object key, Object value) {
      Object result = super.put(key, value);
      ++this.numPut;
      this.notifyAll();
      return result;
   }

   public synchronized void cancel(String err) {
      this.cancelled = true;
      this.error = err;
      this.notifyAll();
   }

   public synchronized String getError() {
      return this.error;
   }

   public synchronized Object getWait(Object key) {
      if (this.cancelled) {
         return null;
      } else {
         Object value;
         for(value = this.get(key); value == null && !this.cancelled; value = this.get(key)) {
            int oldNumPut = this.numPut;
            boolean wasIE = false;

            try {
               this.wait(120000L);
            } catch (InterruptedException var6) {
               wasIE = true;
            }

            if (oldNumPut == this.numPut && !this.cancelled) {
               if (wasIE) {
                  this.cancel("T3RemoteInputStream was interrupted before numPut could increase from " + this.numPut);
               }

               this.cancel("Supplier to T3RemoteInputStream made no progress in 120000 ms, numPut=" + this.numPut);
            }
         }

         return value;
      }
   }

   public synchronized Object removeWait(Object key) {
      if (this.cancelled) {
         return null;
      } else {
         Object value;
         for(value = this.remove(key); value == null && !this.cancelled; value = this.remove(key)) {
            int oldNumPut = this.numPut;
            boolean wasIE = false;

            try {
               this.wait(120000L);
            } catch (InterruptedException var6) {
               wasIE = true;
            }

            if (oldNumPut == this.numPut && !this.cancelled) {
               if (wasIE) {
                  this.cancel("T3RemoteInputStream was interrupted before numPut could increase from " + this.numPut);
               }

               this.cancel("Supplier to T3RemoteInputStream made no progress in 120000 ms, numPut=" + this.numPut);
            }
         }

         return value;
      }
   }
}
