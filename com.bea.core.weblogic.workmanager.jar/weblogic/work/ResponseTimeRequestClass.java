package weblogic.work;

import weblogic.utils.Debug;

public class ResponseTimeRequestClass extends ServiceClassSupport {
   private static boolean DEBUG = Debug.getCategory("weblogic.responsetimerequestclass").isEnabled() || Debug.getCategory("weblogic.requestclass").isEnabled();
   private static final double PERIOD = 2000.0;
   private static final double HALF_LIFE = 20000.0;
   private static final double W = Math.pow(0.5, 0.1);
   private static final double WC;
   private static final long DEFAULT_INCR = 100L;
   private int responseTime;
   private long previouslyCompleted;
   private long previouslyUsed;
   private double interval;

   public ResponseTimeRequestClass(String n, int rt) {
      this(n, rt, (PartitionFairShare)null);
   }

   public ResponseTimeRequestClass(String n, int rt, PartitionFairShare partitionFairShare) {
      super(n, partitionFairShare);
      this.previouslyCompleted = 0L;
      this.setResponseTime(rt);
   }

   protected double getInterval() {
      return this.interval;
   }

   public void timeElapsed(long elapsedTime, ServiceClassesStats stats) {
      long pc = this.previouslyCompleted;
      this.previouslyCompleted = this.getCompleted();
      int completed = (int)(this.previouslyCompleted - pc);
      if (completed != 0 && this.previouslyCompleted != 0L) {
         this.interval = W * this.interval + WC * (double)this.responseTime / (double)completed;
         long pu = this.previouslyUsed;
         this.previouslyUsed = this.getThreadUse();
         int used = (int)(this.previouslyUsed - pu);
         long incr = stats.adjustResponseTime(this.interval, used, completed, this.partitionFairShare);
         if (incr <= 0L) {
            incr = 1L;
         }

         if (DEBUG) {
            log(this.getName() + " -  used " + used + ", interval " + this.interval + ", incr " + incr);
         }

         long acceptableWait = (long)((double)this.responseTime - 2.3 * (double)this.getThreadUse() / (double)this.previouslyCompleted);
         if (acceptableWait > incr) {
            acceptableWait = incr;
         }

         if (acceptableWait < 1L) {
            acceptableWait = 1L;
         }

         this.setIncrements(acceptableWait, incr);
         if (DEBUG) {
            log("** RT ** " + this + "\nCompleted=" + completed + ", interval=" + this.interval + ", responseTime=" + this.responseTime + ", incr=" + incr + ", acceptableWait=" + acceptableWait + ", previouslyCompleted=" + this.previouslyCompleted + ", threadUse=" + this.getThreadUse());
         }

      }
   }

   void setResponseTime(int rt) {
      if (this.responseTime != rt) {
         this.setIncrements(100L, 100L);
         this.responseTime = rt;
         this.interval = (double)this.responseTime;
      }
   }

   int getResponseTime() {
      return this.responseTime;
   }

   ServiceClassSupport createCopy(PartitionFairShare partitionFairShare) {
      return new ResponseTimeRequestClass(this.getName(), this.responseTime, partitionFairShare);
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<ResponeTimeRequestClass>" + str);
      }

   }

   static {
      WC = 1.0 - W;
   }
}
