package weblogic.work;

import java.util.BitSet;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

abstract class AbstractStuckThreadAction implements StuckThreadAction {
   private static final DebugCategory debug = Debug.getCategory("weblogic.StuckThreadHandling");
   int maxCount;
   long maxStuckTime;
   private BitSet stuckThreads = new BitSet();
   private boolean actionTaken;

   AbstractStuckThreadAction(long maxStuckTime, int count) {
      this.maxStuckTime = maxStuckTime * 1000L;
      this.maxCount = count;
   }

   public synchronized boolean threadStuck(int threadId, long elapsedTime, long maxTime) {
      if (this.stuckThreads.get(threadId)) {
         return true;
      } else if (!this.isStuck(elapsedTime, maxTime)) {
         if (this.isDebugEnabled()) {
            this.debug("thread " + threadId + " is not stuck");
         }

         return false;
      } else {
         if (this.isDebugEnabled()) {
            this.debug("thread " + threadId + " is stuck !");
         }

         this.stuckThreads.set(threadId);
         if (!this.actionTaken && this.stuckThreads.cardinality() >= this.maxCount) {
            if (this.isDebugEnabled()) {
               this.debug("Stuck thread count is >= " + this.maxCount + ", execute action");
            }

            this.execute();
            this.actionTaken = true;
         }

         return true;
      }
   }

   public synchronized void threadUnStuck(int threadId) {
      if (this.stuckThreads.get(threadId)) {
         if (this.isDebugEnabled()) {
            this.debug("thread " + threadId + " is unstuck. Removing from stuck thread list");
         }

         this.stuckThreads.clear(threadId);
         if (this.actionTaken && this.stuckThreads.cardinality() == 0) {
            if (this.isDebugEnabled()) {
               this.debug("All threads unstuck. Withdraw action");
            }

            this.withdraw();
            this.actionTaken = false;
         }

      }
   }

   private boolean isStuck(long elapsedTime, long maxTime) {
      long limit = this.maxStuckTime <= 0L ? maxTime : this.maxStuckTime;
      return limit > 0L && elapsedTime >= limit;
   }

   public synchronized int getStuckThreadCount() {
      return this.stuckThreads.cardinality();
   }

   public long getMaxStuckTime() {
      return this.maxStuckTime;
   }

   public String getName() {
      return this.toString();
   }

   public String toString() {
      return this.getClass().getName() + " with stuck time " + this.maxStuckTime + " and count " + this.maxCount;
   }

   protected boolean isDebugEnabled() {
      return debug.isEnabled();
   }

   private void debug(String str) {
      WorkManagerLogger.logDebug("[" + this.toString() + "]" + str);
   }
}
