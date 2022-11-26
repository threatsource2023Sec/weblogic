package weblogic.diagnostics.harvester.internal;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import weblogic.diagnostics.harvester.HarvesterCollectorStatistics;
import weblogic.work.WorkManagerFactory;

class HarvesterSamplesQueue {
   private static AtomicBoolean archivalWorkInProgress = new AtomicBoolean(false);

   private static PriorityBlockingQueue createQueue() {
      return new PriorityBlockingQueue(16, new Comparator() {
         public int compare(HarvesterCycleData o1, HarvesterCycleData o2) {
            long o1Time = o1.getSnapshotTimeMillis();
            long o2Time = o2.getSnapshotTimeMillis();
            if (o1Time < o2Time) {
               return -1;
            } else {
               return o1Time > o2Time ? 1 : 0;
            }
         }
      });
   }

   static void enqueue(String partitionName, HarvesterSnapshot snapshot, HarvesterCollectorStatistics stats) {
      HarvesterSamplesQueue.SingletonHolder.queue.put(new HarvesterCycleData(partitionName, snapshot, stats));
      scheduleArchivalWork();
   }

   private static HarvesterCycleData dequeue() throws InterruptedException {
      return (HarvesterCycleData)HarvesterSamplesQueue.SingletonHolder.queue.poll(2L, TimeUnit.SECONDS);
   }

   private static void scheduleArchivalWork() {
      if (!archivalWorkInProgress.get()) {
         WorkManagerFactory.getInstance().getDefault().schedule(new Runnable() {
            public synchronized void run() {
               if (HarvesterSamplesQueue.archivalWorkInProgress.compareAndSet(false, true)) {
                  try {
                     for(HarvesterCycleData cycleData = HarvesterSamplesQueue.dequeue(); cycleData != null; cycleData = HarvesterSamplesQueue.dequeue()) {
                        String partitionName = cycleData.getPartitionName();
                        MetricArchiver.findOrCreateMetricArchiver(partitionName).archive(cycleData);
                     }
                  } catch (InterruptedException var6) {
                     throw new RuntimeException(var6);
                  } finally {
                     HarvesterSamplesQueue.archivalWorkInProgress.set(false);
                  }
               }

            }
         });
      }

   }

   private static class SingletonHolder {
      private static PriorityBlockingQueue queue = HarvesterSamplesQueue.createQueue();
   }
}
