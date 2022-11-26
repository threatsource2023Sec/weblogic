package weblogic.work;

import java.util.HashMap;

public class WorkManagerControl {
   private static WorkManagerControl SINGLETON;
   HashMap partitionLevelMap = new HashMap();
   final int CANNOT_UPDATE_LEVEL = -1;

   public int getThreadPriorityDeltaFromExtent(Extent extent) {
      switch (extent) {
         case SMALL:
            return 1;
         case MEDIUM:
            return 2;
         default:
            return 3;
      }
   }

   private static synchronized void initSingleton() {
      if (SINGLETON == null) {
         SINGLETON = new WorkManagerControl();
      }

   }

   public static WorkManagerControl getInstance() {
      if (SINGLETON == null) {
         initSingleton();
      }

      return SINGLETON;
   }

   public boolean interrupt(Thread thread) {
      return thread instanceof ExecuteThread ? ThreadPriorityManager.interrupt((ExecuteThread)thread) : false;
   }

   public boolean lowerPriority(Thread thread, Extent extent) {
      return thread instanceof ExecuteThread ? ThreadPriorityManager.lowerPriority((ExecuteThread)thread, this.getThreadPriorityDeltaFromExtent(extent)) : false;
   }

   public boolean restorePriority(Thread thread) {
      return thread instanceof ExecuteThread ? ThreadPriorityManager.restorePriority((ExecuteThread)thread) : false;
   }

   public boolean slowDown(String partitionName, int numExtents) {
      this.verifyNumExtentsArg(numExtents);
      return this.setPartitionSlowDownLevel(partitionName, -numExtents);
   }

   public boolean speedUp(String partitionName, int numExtents) {
      this.verifyNumExtentsArg(numExtents);
      return this.setPartitionSlowDownLevel(partitionName, numExtents);
   }

   public boolean resume(String partitionName) {
      return this.setPartitionSlowDownLevel(partitionName, 10);
   }

   private boolean setPartitionSlowDownLevel(String partitionName, int numExtents) {
      GlobalWorkManagerComponentsFactory factory = GlobalWorkManagerComponentsFactory.getInstance(partitionName, false);
      if (factory != null) {
         int newLevel = this.getAndUpdateLevel(partitionName, numExtents);
         if (newLevel != -1) {
            PartitionFairShare partitionFairShare = factory.getPartitionFairShare();
            PartitionMaxThreadsConstraint partitionMaxThreadsConstraint = factory.getPartitionMaxThreadsConstraint();
            if (partitionFairShare != null && partitionMaxThreadsConstraint != null) {
               partitionFairShare.setSlowDownLevel(newLevel);
               partitionMaxThreadsConstraint.setSlowDownLevel(newLevel);
               return true;
            }
         }
      }

      return false;
   }

   private void verifyNumExtentsArg(int numExtents) {
      if (numExtents < 1 || numExtents >= 10) {
         throw new IllegalArgumentException("numExtents must be between 1 and 9");
      }
   }

   private synchronized int getAndUpdateLevel(String partitionName, int numExtentsDelta) {
      Integer origLevel = (Integer)this.partitionLevelMap.get(partitionName);
      if (origLevel == null) {
         origLevel = 10;
      }

      if (origLevel <= 1 && numExtentsDelta < 0 || origLevel == 10 && numExtentsDelta > 0) {
         return -1;
      } else {
         int newLevel = origLevel + numExtentsDelta;
         if (newLevel < 1) {
            newLevel = 1;
         } else if (newLevel > 10) {
            newLevel = 10;
         }

         this.partitionLevelMap.put(partitionName, newLevel);
         return newLevel;
      }
   }

   synchronized void shutdownPartition(String partitionName) {
      this.partitionLevelMap.remove(partitionName);
   }

   public static enum Extent {
      SMALL,
      MEDIUM,
      LARGE;
   }
}
