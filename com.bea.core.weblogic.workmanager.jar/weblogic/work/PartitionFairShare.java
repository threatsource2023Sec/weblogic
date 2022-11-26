package weblogic.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.Debug;

public class PartitionFairShare {
   private static boolean DEBUG = Debug.getCategory("weblogic.PartitionFairShare").isEnabled() || Debug.getCategory("weblogic.requestclass").isEnabled();
   static final double DEFAULT_ADJUSTER = 1.0;
   static long THREAD_USE_THRESHOLD = Long.getLong("weblogic.work.PartitionFairShare.threadUseThreshold", 1000L);
   static int MAX_RC_COMPLETION_COUNT_THRESHOLD = Integer.getInteger("weblogic.work.PartitionFairShare.maxRcCompletionCountThreshold", 10);
   static final int DEFAULT_PARTITION_FAIR_SHARE = 50;
   private int slowDownLevel = 10;
   private int fairShare;
   private int configuredFairShare;
   private String name;
   double partitionAdjuster = 1.0;
   double weighedIncrementSum;
   long threadUseSum;
   long maxThreadUse;
   int maxThreadUseCompletedCount;
   volatile double partitionAdjusterSnapShot;
   volatile long threadUseSumSnapShot;
   static long sumPartitionFairShares;
   static double avgPartitionFairShares;
   static final List partitionFairShares = new ArrayList();

   static void clear() {
      partitionFairShares.clear();
      sumPartitionFairShares = 0L;
      avgPartitionFairShares = 0.0;
   }

   public static PartitionFairShare createPartitionFairShare(String name) {
      return createPartitionFairShare(name, 50);
   }

   public static PartitionFairShare createPartitionFairShare(String name, int fairShare) {
      PartitionFairShare partitionFairShare = new PartitionFairShare(name, fairShare);
      synchronized(partitionFairShares) {
         partitionFairShares.add(partitionFairShare);
         sumPartitionFairShares += (long)fairShare;
         avgPartitionFairShares = (double)sumPartitionFairShares / (double)partitionFairShares.size();
         return partitionFairShare;
      }
   }

   private PartitionFairShare(String name, int fairShare) {
      this.name = name;
      this.fairShare = fairShare;
      this.configuredFairShare = fairShare;
   }

   public int getFairShare() {
      return this.fairShare;
   }

   int getConfiguredFairShare() {
      return this.configuredFairShare;
   }

   long getThreadUseSum() {
      return this.threadUseSum;
   }

   public String getName() {
      return this.name;
   }

   static void resetAll(ServiceClassesStats stats) {
      double allPartitionsWeighedIncrementsSum = 0.0;
      long allPartitionsThreadUseSum = 0L;
      int allPartitionsMaxUseCompleted = 0;
      long allPartitionsMaxThreadUse = 0L;
      synchronized(partitionFairShares) {
         Iterator var9 = partitionFairShares.iterator();

         while(var9.hasNext()) {
            PartitionFairShare partitionFairShare = (PartitionFairShare)var9.next();
            if (partitionFairShare.threadUseSum > THREAD_USE_THRESHOLD) {
               allPartitionsWeighedIncrementsSum += partitionFairShare.weighedIncrementSum;
               allPartitionsThreadUseSum += partitionFairShare.threadUseSum;
               long partitionMaxUseCompleted = (long)partitionFairShare.maxThreadUseCompletedCount;
               if (partitionMaxUseCompleted > (long)MAX_RC_COMPLETION_COUNT_THRESHOLD) {
                  allPartitionsMaxThreadUse += partitionFairShare.maxThreadUse;
                  allPartitionsMaxUseCompleted = (int)((long)allPartitionsMaxUseCompleted + partitionMaxUseCompleted);
               }
            }
         }

         double avgIncrement = allPartitionsThreadUseSum == 0L ? 0.0 : allPartitionsWeighedIncrementsSum / (double)allPartitionsThreadUseSum;
         double avgRequestTime = allPartitionsMaxUseCompleted == 0 ? 0.0 : (double)allPartitionsMaxThreadUse / (double)allPartitionsMaxUseCompleted;
         Iterator var13 = partitionFairShares.iterator();

         while(var13.hasNext()) {
            PartitionFairShare partitionFairShare = (PartitionFairShare)var13.next();
            partitionFairShare.reset(stats, avgIncrement, avgRequestTime);
         }

      }
   }

   private void reset(ServiceClassesStats stats, double allPartitionsAvgIncrement, double allPartitionsMaxRCAvgRequestTime) {
      double avgIncrement = this.threadUseSum == 0L ? 0.0 : this.weighedIncrementSum / (double)this.threadUseSum;
      if (this.threadUseSum > THREAD_USE_THRESHOLD) {
         double incrementAdjuster = allPartitionsAvgIncrement / avgIncrement * (double)(this.threadUseSum / this.maxThreadUse);
         this.partitionAdjuster = 1.0;
         double partitionMaxRCAvgRequestTime = (double)this.maxThreadUse / (double)this.maxThreadUseCompletedCount;
         double perRequestThreadUseAdjustment = 1.0;
         if (this.maxThreadUseCompletedCount > MAX_RC_COMPLETION_COUNT_THRESHOLD) {
            perRequestThreadUseAdjustment = partitionMaxRCAvgRequestTime / allPartitionsMaxRCAvgRequestTime;
         }

         if (this.threadUseSum > 0L) {
            this.partitionAdjuster = incrementAdjuster * avgPartitionFairShares / (double)this.fairShare * perRequestThreadUseAdjustment;
         }

         if (DEBUG) {
            double threadUsePercent = 0.0;
            if ((double)stats.getThreadUseSum() > 0.0) {
               threadUsePercent = (double)this.threadUseSum / (double)stats.getThreadUseSum();
            }

            double partitionAllocation = (double)this.fairShare / (double)sumPartitionFairShares;
            log("partition '" + this.name + "' - threadUsePercent = " + threadUsePercent * 100.0 + "%, target ratio = " + partitionAllocation * 100.0 + "%, partitionMaxRCAvgRequestTime = " + partitionMaxRCAvgRequestTime + ", allPartitionMaxRCAvgRequestTime = " + allPartitionsMaxRCAvgRequestTime + ", perRequestThreadUseAdjuster = " + perRequestThreadUseAdjustment + ", avgIncrement = " + avgIncrement + ", allPartitionsAvgIncrement = " + allPartitionsAvgIncrement + ", incrementAdjuster is " + incrementAdjuster + ", partitionAdjuster = " + this.partitionAdjuster);
         }
      }

      this.partitionAdjusterSnapShot = this.partitionAdjuster;
      this.threadUseSumSnapShot = this.threadUseSum;
      this.weighedIncrementSum = 0.0;
      this.threadUseSum = 0L;
      this.maxThreadUse = 0L;
      this.maxThreadUseCompletedCount = 0;
   }

   static void deregister(PartitionFairShare partitionFairShare) {
      synchronized(partitionFairShares) {
         partitionFairShares.remove(partitionFairShare);
         sumPartitionFairShares -= (long)partitionFairShare.getFairShare();
         avgPartitionFairShares = (double)sumPartitionFairShares / (double)partitionFairShares.size();
      }
   }

   void setFairShare(int newConfiguredFairShare) {
      if (this.configuredFairShare != newConfiguredFairShare) {
         int delta = false;
         int delta;
         synchronized(this) {
            int oldFairShare = this.fairShare;
            int newFairShare = newConfiguredFairShare;
            if (this.configuredFairShare != this.fairShare) {
               if (this.slowDownLevel == 10) {
                  newFairShare = newConfiguredFairShare * oldFairShare / this.configuredFairShare;
               } else {
                  newFairShare = (int)((double)((float)newConfiguredFairShare / 10.0F * (float)this.slowDownLevel) + 0.5);
               }
            }

            delta = newFairShare - this.fairShare;
            if (DEBUG) {
               log("setFairShare(" + newConfiguredFairShare + "). Original configuredFairShare=" + this.configuredFairShare + ", original fairShare=" + oldFairShare + ", new fairShare=" + newFairShare);
            }

            this.configuredFairShare = newConfiguredFairShare;
            this.fairShare = newFairShare;
         }

         this.updatePartitionFairSharesValues(delta);
      }
   }

   void updatePartitionFairSharesValues(int delta) {
      if (delta != 0) {
         synchronized(partitionFairShares) {
            sumPartitionFairShares += (long)delta;
            avgPartitionFairShares = (double)sumPartitionFairShares / (double)partitionFairShares.size();
         }
      }
   }

   boolean setSlowDownLevel(int newLevel) {
      if (newLevel >= 1 && newLevel <= 10) {
         int updatedFairShare = (int)((double)((float)this.configuredFairShare / 10.0F * (float)newLevel) + 0.5);
         if (updatedFairShare < 1) {
            updatedFairShare = 1;
         }

         double origPartitionAdjuster = this.partitionAdjuster;
         int delta;
         synchronized(this) {
            delta = updatedFairShare - this.fairShare;
            int originalFairShare = this.fairShare;
            this.fairShare = updatedFairShare;
            this.slowDownLevel = newLevel;
            this.partitionAdjuster = this.partitionAdjuster * (double)originalFairShare / (double)this.fairShare;
         }

         this.updatePartitionFairSharesValues(delta);
         if (DEBUG) {
            log("setSlowDownLevel(" + newLevel + ") updated fairShare to " + updatedFairShare + " for partition '" + this.name + "', partitionAdjuster:" + origPartitionAdjuster + "-->" + this.partitionAdjuster);
         }

         return true;
      } else {
         return false;
      }
   }

   double getPartitionAdjuster(long threadUse, int completed, double increment) {
      if (threadUse > 0L) {
         if (DEBUG) {
            log("Adding usage sample: (" + threadUse + "ms, " + completed + ", " + increment + ") for partition '" + this.name + "'");
         }

         this.weighedIncrementSum += increment * (double)threadUse;
         this.threadUseSum += threadUse;
         if (threadUse > this.maxThreadUse) {
            this.maxThreadUse = threadUse;
            this.maxThreadUseCompletedCount = completed;
         }
      }

      return this.partitionAdjuster;
   }

   double getPartitionAdjuster() {
      return this.partitionAdjuster;
   }

   double getPartitionAdjusterSnapShot() {
      return this.partitionAdjusterSnapShot;
   }

   long getThreadUseSumSnapShot() {
      return this.threadUseSumSnapShot;
   }

   private static void log(String str) {
      if (DEBUG) {
         WorkManagerLogger.logDebug("<PartitionFairShare>" + str);
      }

   }
}
