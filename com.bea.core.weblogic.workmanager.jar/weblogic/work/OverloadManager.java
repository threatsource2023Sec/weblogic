package weblogic.work;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.utils.concurrent.WaterMark;

public class OverloadManager {
   static final String SHARED_OVERLOAD_MANAGER_NAME = "global overload manager";
   static final String PARTITION_OVERLOAD_MANAGER_NAME = "partition overload manager";
   private AtomicInteger queueDepth;
   private AtomicInteger rejectedRequestsCount;
   private String name;
   private final boolean isSharedOverloadManager;
   private String partitionName;
   private WaterMark capacity;
   private int percentage;
   private int nearCapacityThreshold;
   private HashSet activeRequestClassNamesInOverload;
   private static final List partitionOverloadManagers = new ArrayList();
   static final float NEAR_CAPACITY_THRESHOLD = 0.1F;
   final OverloadManager parent;

   OverloadManager(String name) {
      this(name, 0);
   }

   public OverloadManager(String name, int count) {
      this.queueDepth = new AtomicInteger();
      this.rejectedRequestsCount = null;
      if (count < 0) {
         count = 0;
      }

      this.name = name;
      this.capacity = new WaterMark(count);
      if ("global overload manager".equals(name)) {
         this.isSharedOverloadManager = true;
      } else {
         this.isSharedOverloadManager = false;
      }

      this.nearCapacityThreshold = (int)(0.1F * (float)count);
      this.parent = null;
   }

   private OverloadManager(String partitionName, OverloadManager parent, int percentage) {
      this.queueDepth = new AtomicInteger();
      this.rejectedRequestsCount = null;
      this.name = "partition overload manager$" + partitionName;
      this.isSharedOverloadManager = false;
      this.partitionName = partitionName;
      this.percentage = percentage;
      this.parent = parent;
      this.capacity = new WaterMark(this.calculateCapacity());
      if (parent.isRejectedRequestsCounterEnabled()) {
         this.enableRejectedRequestsCounter();
      }

   }

   void setPercentage(int percentage) {
      if (this.parent != null) {
         if (this.percentage != percentage) {
            this.percentage = percentage;
            this.setCapacity(this.calculateCapacity());
         }
      }
   }

   private int calculateCapacity() {
      if (this.percentage == 0) {
         return 0;
      } else {
         int baseCount = this.parent.getCapacity();
         int capacity = baseCount * this.percentage / 100;
         return capacity < 1 ? 1 : capacity;
      }
   }

   static OverloadManager createPartitionOverloadManager(String partitionName, OverloadManager parent, int percentage) {
      if (parent == null) {
         throw new IllegalArgumentException("parent cannot be null");
      } else {
         OverloadManager overloadManager = new OverloadManager(partitionName, parent, percentage);
         synchronized(partitionOverloadManagers) {
            partitionOverloadManagers.add(overloadManager);
            return overloadManager;
         }
      }
   }

   static List getListOfNearCapacityPartitionOverloadManager() {
      List list = null;
      synchronized(partitionOverloadManagers) {
         Iterator var2 = partitionOverloadManagers.iterator();

         while(var2.hasNext()) {
            OverloadManager overloadManager = (OverloadManager)var2.next();
            if (overloadManager.nearCapacity()) {
               if (list == null) {
                  list = new ArrayList();
               }

               list.add(overloadManager);
            } else {
               overloadManager.resetActiveRequestClassesInOverload();
            }
         }

         return list;
      }
   }

   void enableRejectedRequestsCounter() {
      if (this.rejectedRequestsCount == null) {
         this.rejectedRequestsCount = new AtomicInteger();
      }

   }

   boolean isRejectedRequestsCounterEnabled() {
      return this.rejectedRequestsCount != null;
   }

   public final int getCapacity() {
      return this.capacity.getOriginalLevel();
   }

   public final void setCapacity(int count) {
      if (count < 0) {
         count = 0;
      }

      this.capacity.resetLevel(count);
      if (this.isSharedOverloadManager) {
         updatePartitionOverloadManagersCapacity();
      }

      this.nearCapacityThreshold = (int)(0.1F * (float)count);
   }

   private static void updatePartitionOverloadManagersCapacity() {
      synchronized(partitionOverloadManagers) {
         Iterator var1 = partitionOverloadManagers.iterator();

         while(var1.hasNext()) {
            OverloadManager overloadManager = (OverloadManager)var1.next();
            overloadManager.setCapacity(overloadManager.calculateCapacity());
         }

      }
   }

   boolean canAcceptMore() {
      return !this.capacity.isEnabled() || this.capacity.getCurrentLevel() > 0;
   }

   boolean nearCapacity() {
      return this.capacity.isEnabled() && this.capacity.getCurrentLevel() <= this.nearCapacityThreshold;
   }

   public String getName() {
      return this.name;
   }

   public int getInProgress() {
      return this.capacity.diff();
   }

   int getQueueDepth() {
      return this.queueDepth.get();
   }

   void acceptWork() {
      this.capacity.decreaseLevel(1);
   }

   void finishWork() {
      this.capacity.increaseLevel(1);
   }

   void incrementQueueDepth() {
      this.queueDepth.getAndIncrement();
   }

   void decrementQueueDepth() {
      this.queueDepth.getAndDecrement();
   }

   void incrementRejectedRequestsCounter() {
      if (this.rejectedRequestsCount != null) {
         this.rejectedRequestsCount.getAndIncrement();
      }

   }

   public int getRejectedRequestsCount() {
      return this.rejectedRequestsCount != null ? this.rejectedRequestsCount.get() : 0;
   }

   void activeRequestClassNamesInOverload(List sortedRequestClasses) {
      if (this.activeRequestClassNamesInOverload == null) {
         this.activeRequestClassNamesInOverload = new HashSet();
      }

      this.activeRequestClassNamesInOverload.clear();
      int count = 0;
      int myCapacity = this.getCapacity();
      Iterator i = sortedRequestClasses.iterator();

      while(i.hasNext()) {
         RequestClass requestClass = (RequestClass)i.next();
         if (requestClass != null && this.isRequestClassForSamePartition(requestClass)) {
            count += requestClass.getPendingRequestsCount();
            if (count >= myCapacity) {
               return;
            }

            this.activeRequestClassNamesInOverload.add(requestClass.getName());
         }
      }

   }

   boolean isRequestClassForSamePartition(RequestClass requestClass) {
      if (this.partitionName == null) {
         return true;
      } else {
         PartitionFairShare partitionFairShare = requestClass.getPartitionFairShare();
         return partitionFairShare != null ? this.partitionName.equals(partitionFairShare.getName()) : false;
      }
   }

   boolean acceptRequestClass(RequestClass rc) {
      return this.activeRequestClassNamesInOverload != null && this.activeRequestClassNamesInOverload.contains(rc.getName());
   }

   void resetActiveRequestClassesInOverload() {
      if (this.activeRequestClassNamesInOverload != null && this.activeRequestClassNamesInOverload.size() > 0) {
         this.activeRequestClassNamesInOverload.clear();
      }

   }

   HashSet getActiveRequestClassNamesInOverload() {
      return this.activeRequestClassNamesInOverload;
   }

   static void reset() {
      partitionOverloadManagers.clear();
   }

   static List getPartitionOverloadManagers() {
      return partitionOverloadManagers;
   }
}
