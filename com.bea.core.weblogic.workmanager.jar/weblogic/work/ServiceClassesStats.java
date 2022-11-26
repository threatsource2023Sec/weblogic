package weblogic.work;

public class ServiceClassesStats {
   public static final double TARGET_RESPONSE_TIME_INCR = 100.0;
   public static final double TARGET_FAIR_SHARE_INCR = 1000.0;
   int fairShareCount;
   double fairShareSum;
   long threadUseSum;
   double fairShareCorrection;
   int responseTimeCount;
   double responseTimeSum;
   double responseTimeCorrection;

   void reset() {
      PartitionFairShare.resetAll(this);
      this.fairShareCorrection = this.fairShareSum == 0.0 ? 1000.0 : 1000.0 * (double)this.fairShareCount / this.fairShareSum;
      this.responseTimeCorrection = this.responseTimeSum == 0.0 ? 100.0 : 100.0 * (double)this.responseTimeCount / this.responseTimeSum;
      this.fairShareCount = 0;
      this.fairShareSum = 0.0;
      this.threadUseSum = 0L;
      this.responseTimeCount = 0;
      this.responseTimeSum = 0.0;
   }

   public long adjustFairShare(double incr, int threadUse, int completed, PartitionFairShare partitionFairShare) {
      ++this.fairShareCount;
      this.fairShareSum += incr;
      this.threadUseSum += (long)threadUse;
      double partitionFairShareAdjuster = 1.0;
      if (partitionFairShare != null) {
         partitionFairShareAdjuster = partitionFairShare.getPartitionAdjuster((long)threadUse, completed, incr * this.fairShareCorrection);
      }

      return (long)(incr * this.fairShareCorrection * partitionFairShareAdjuster);
   }

   public long adjustIncrement(double incr, PartitionFairShare partitionFairShare) {
      double partitionFairShareAdjuster = 1.0;
      if (partitionFairShare != null) {
         partitionFairShareAdjuster = partitionFairShare.getPartitionAdjuster();
      }

      return (long)(incr * partitionFairShareAdjuster);
   }

   public long adjustResponseTime(double incr, int threadUse, int completed, PartitionFairShare partitionFairShare) {
      ++this.responseTimeCount;
      this.responseTimeSum += incr;
      this.threadUseSum += (long)threadUse;
      double partitionFairShareAdjuster = 1.0;
      if (partitionFairShare != null) {
         partitionFairShareAdjuster = partitionFairShare.getPartitionAdjuster((long)threadUse, completed, incr * this.responseTimeCorrection);
      }

      return (long)(incr * this.responseTimeCorrection * partitionFairShareAdjuster);
   }

   long getThreadUseSum() {
      return this.threadUseSum;
   }
}
