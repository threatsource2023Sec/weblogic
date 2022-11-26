package weblogic.work;

import weblogic.utils.Debug;

public class FairShareRequestClass extends ServiceClassSupport {
   private static final boolean DEBUG = Debug.getCategory("weblogic.FairShareRequestClass").isEnabled() || Debug.getCategory("weblogic.requestclass").isEnabled();
   public static final int DEFAULT_FAIR_SHARE = 50;
   private static final double DEFAULT_INCR = 1000.0;
   private static final double PERIOD = 2000.0;
   private static final double HALF_LIFE = 300000.0;
   private static final double W = Math.pow(0.5, 0.006666666666666667);
   private static final double WC;
   private static final double A = 0.5;
   private static final double AC = 0.5;
   private int share;
   private int effectiveShare;
   private long previouslyCompleted;
   private long previouslyUsed;
   private double smoothedIncr;
   private long initialIncrement;
   private static int INTERNAL_FAIRSHARE_BOOST_FACTOR;
   private static final int MAX_INTERNAL_FAIRSHARE_BOOST_FACTOR = 20;

   FairShareRequestClass(String name, PartitionFairShare partitionFairShare) {
      this(name, 50, partitionFairShare);
   }

   FairShareRequestClass(String name, String appName, String moduleName, PartitionFairShare partitionFairShare) {
      this(name + "@" + appName + "@" + moduleName, 50, partitionFairShare);
   }

   public FairShareRequestClass(String name, int share) {
      this(name, share, (PartitionFairShare)null);
   }

   public FairShareRequestClass(String name, int share, PartitionFairShare partitionFairShare) {
      super(name, partitionFairShare);
      this.setShare(share);
   }

   void setShare(int share) {
      if (this.share != share) {
         if (share > 100) {
            WorkManagerLogger.logFairShareValueTooHigh(this.getName(), share);
         }

         this.internalSetShare(share);
      }
   }

   private void internalSetShare(int share) {
      this.share = share;
      if (this.isInternal() && INTERNAL_FAIRSHARE_BOOST_FACTOR > 0) {
         if (INTERNAL_FAIRSHARE_BOOST_FACTOR > 20) {
            INTERNAL_FAIRSHARE_BOOST_FACTOR = 20;
         }

         this.effectiveShare = share * INTERNAL_FAIRSHARE_BOOST_FACTOR;
      } else {
         this.effectiveShare = share;
      }

      if (DEBUG) {
         log(this.getName() + " -  configured fairshare = " + share + ", effectiveShare = " + this.effectiveShare);
      }

      this.smoothedIncr = 1000.0 / (double)this.effectiveShare;
      long incr = (long)(this.smoothedIncr + 1.0);
      this.initialIncrement = incr;
      this.setIncrements(incr, incr);
   }

   int getShare() {
      return this.share;
   }

   void setInternal(boolean internal) {
      super.setInternal(internal);
      this.internalSetShare(this.share);
   }

   protected long getIncrementForThreadPriorityCalculation() {
      return this.initialIncrement;
   }

   public void timeElapsed(long elapsedTime, ServiceClassesStats stats) {
      long pc = this.previouslyCompleted;
      this.previouslyCompleted = this.getCompleted();
      int completed = (int)(this.previouslyCompleted - pc);
      long pu = this.previouslyUsed;
      this.previouslyUsed = this.getThreadUse();
      int used = (int)(this.previouslyUsed - pu);
      if (completed == 0) {
         this.smoothedIncr = W * this.smoothedIncr + WC * 1000.0 / (double)this.effectiveShare;
      } else {
         this.smoothedIncr = 0.5 * this.smoothedIncr + 0.5 * (double)used / (double)(completed * this.effectiveShare);
      }

      long incr = stats.adjustFairShare(this.smoothedIncr, used, completed, this.partitionFairShare);
      if (incr <= 0L) {
         incr = 1L;
      }

      if (DEBUG) {
         log(this.getName() + " -  used = " + used + ", smoothedIncr = " + this.smoothedIncr + ", incr = " + incr);
      }

      this.setIncrements(incr, incr);
   }

   ServiceClassSupport createCopy(PartitionFairShare partitionFairShare) {
      return new FairShareRequestClass(this.getName(), this.share, partitionFairShare);
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<FairShareRequestClass>" + str);
      }

   }

   static {
      WC = 1.0 - W;
      INTERNAL_FAIRSHARE_BOOST_FACTOR = Integer.getInteger("weblogic.work.internalFairShareBoostFactor", 10);
   }
}
