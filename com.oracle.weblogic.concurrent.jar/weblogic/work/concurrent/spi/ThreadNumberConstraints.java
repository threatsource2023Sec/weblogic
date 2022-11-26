package weblogic.work.concurrent.spi;

import weblogic.diagnostics.debug.DebugLogger;

public class ThreadNumberConstraints implements ThreadCreationChecker {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private int max;
   private int used;
   private final String limitType;
   public static final String CLUSTER_LIMIT = "cluster";
   public static final String SERVER_LIMIT = "server";
   public static final String PARTITION_LIMIT = "partition";
   public static final String LOCAL_LIMIT = "local";

   public ThreadNumberConstraints(int max, String limitType) {
      this.checkConstraintsNumber(max);
      synchronized(this) {
         this.max = max;
         this.used = 0;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("after undo " + this.toInternalString());
         }
      }

      this.limitType = limitType;
   }

   public void acquire() throws RejectException {
      synchronized(this) {
         if (this.max <= this.used) {
            throw new ExceedThreadLimitException(this.max, this.limitType);
         } else {
            ++this.used;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("after undo " + this.toInternalString());
            }

         }
      }
   }

   public void undo() {
      synchronized(this) {
         if (this.used == 0) {
            throw new IllegalStateException();
         } else {
            --this.used;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("after undo " + this.toInternalString());
            }

         }
      }
   }

   public int getLimit() {
      synchronized(this) {
         return this.max;
      }
   }

   private String toInternalString() {
      return super.toString() + "(limitType=" + this.limitType + ", max=" + this.max + ", used=" + this.used + ")";
   }

   public String toString() {
      synchronized(this) {
         return this.toInternalString();
      }
   }

   private void checkConstraintsNumber(int max) {
      if (max < 0) {
         throw new IllegalArgumentException("Thread constraints number should not below zero");
      }
   }

   public void adjustMax(int newMax) {
      this.checkConstraintsNumber(newMax);
      synchronized(this) {
         this.max = newMax;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("after undo " + this.toInternalString());
         }

      }
   }
}
