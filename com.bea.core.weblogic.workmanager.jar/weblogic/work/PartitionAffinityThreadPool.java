package weblogic.work;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.utils.collections.ConcurrentPool;

public class PartitionAffinityThreadPool {
   private final ConcurrentPool commonPool;
   private final ConcurrentHashMap partitionedCache = new ConcurrentHashMap();
   static final int PER_PARTITION_POOL_SIZE = 10;
   static final boolean DISABLE_RCM_MODE = Boolean.getBoolean("weblogic.work.disableRCMmode");
   final boolean isStandbyPool;
   final boolean isolatePartitionThreadLocal;
   volatile boolean RCM_MODE;
   AtomicInteger getMatchingThreadSucceed = new AtomicInteger();
   AtomicInteger getMatchingThreadFailed = new AtomicInteger();
   AtomicInteger switchIdleThreadNotNeeded = new AtomicInteger();
   AtomicInteger switchIdleThreadGotFromStandby = new AtomicInteger();
   AtomicInteger switchIdleThreadFailed = new AtomicInteger();
   AtomicInteger switchStandbyThreadNotNeeded = new AtomicInteger();
   AtomicInteger switchStandbyThreadSucceeded = new AtomicInteger();
   AtomicInteger switchStandbyThreadFailed = new AtomicInteger();

   public PartitionAffinityThreadPool(int commonPoolSize, boolean isStandbyPool, boolean isoloatePartitionThreadLocal) {
      this.commonPool = new ConcurrentPool(commonPoolSize);
      this.isStandbyPool = isStandbyPool;
      this.isolatePartitionThreadLocal = isoloatePartitionThreadLocal;
   }

   boolean addPartition(String partitionName, boolean rcmEnabled) {
      if (DISABLE_RCM_MODE) {
         return true;
      } else if (!rcmEnabled && !this.isolatePartitionThreadLocal) {
         return false;
      } else {
         if (!this.RCM_MODE) {
            this.RCM_MODE = true;
            if (debugEnabled()) {
               log("RCM mode is being turned on. isolatePartitionThreadLocal is: " + this.isolatePartitionThreadLocal);
            }

            this.internalAddPartition("DOMAIN");
         }

         return this.internalAddPartition(partitionName);
      }
   }

   boolean internalAddPartition(String partitionName) {
      PartitionedPoolEntry newPartitionPool = new PartitionedPoolEntry();
      return this.partitionedCache.putIfAbsent(partitionName, newPartitionPool) == null;
   }

   List removePartition(String partitionName) {
      ArrayList extraThreads = new ArrayList();
      if (this.RCM_MODE && partitionName != null) {
         PartitionedPoolEntry removedPartitionPool = (PartitionedPoolEntry)this.partitionedCache.remove(partitionName);
         if (removedPartitionPool != null) {
            for(ExecuteThread threadToMove = removedPartitionPool.poll(); threadToMove != null; threadToMove = removedPartitionPool.poll()) {
               if (!this.commonPool.offer(threadToMove)) {
                  extraThreads.add(threadToMove);
               }
            }
         }
      }

      return extraThreads;
   }

   boolean offer(ExecuteThread t) {
      if (this.RCM_MODE) {
         String partitionName = t.getPreviousPartitionName();
         if (partitionName != null) {
            PartitionedPoolEntry pool = (PartitionedPoolEntry)this.partitionedCache.get(partitionName);
            if (pool != null && pool.offer(t)) {
               return true;
            }
         }
      }

      return this.commonPool.offer(t);
   }

   ExecuteThread getMatching(String partitionName) {
      if (partitionName != null) {
         PartitionedPoolEntry pool = (PartitionedPoolEntry)this.partitionedCache.get(partitionName);
         if (pool != null) {
            ExecuteThread matchingThread = pool.poll();
            if (matchingThread != null) {
               if (debugEnabled()) {
                  this.getMatchingThreadSucceed.getAndIncrement();
               }

               return matchingThread;
            }
         }
      }

      if (debugEnabled()) {
         this.getMatchingThreadFailed.getAndIncrement();
      }

      return null;
   }

   ExecuteThread poll(WorkAdapter workEntry) {
      if (!this.RCM_MODE) {
         ExecuteThread result = (ExecuteThread)this.commonPool.poll();
         if (result != null && !this.isStandbyPool && workEntry != null) {
            this.clearThreadLocalIfDifferentPartition(result, this.getPartitionName(workEntry));
         }

         return result;
      } else {
         String partitionForWorkEntry = this.getPartitionName(workEntry);
         ExecuteThread result = this.getMatching(partitionForWorkEntry);
         if (result == null) {
            result = (ExecuteThread)this.commonPool.poll();
            if (result == null) {
               Iterator iterator = this.partitionedCache.values().iterator();

               while(iterator.hasNext() && result == null) {
                  PartitionedPoolEntry pool = (PartitionedPoolEntry)iterator.next();
                  if (pool != null) {
                     result = pool.poll();
                  }
               }
            }

            if (result != null && !this.isStandbyPool && workEntry != null) {
               this.clearThreadLocalIfDifferentPartition(result, partitionForWorkEntry);
            }
         }

         return result;
      }
   }

   private void clearThreadLocalIfDifferentPartition(ExecuteThread thread, String partitionForNextWork) {
      if (this.isolatePartitionThreadLocal && !ExecuteThread.isCleanupTLAfterEachRequest() && !this.partitionNameMatches(thread.getPreviousPartitionName(), partitionForNextWork)) {
         thread.forceEraseThreadLocals();
      }

   }

   ExecuteThread switchThreadWithPartitionAffinity(ExecuteThread activeThread, WorkAdapter workAdapter, boolean isIdleThread) {
      if (this.RCM_MODE && this.isStandbyPool) {
         String previousPartitionName = activeThread.getPreviousPartitionName();
         String nextPartitionName = this.getPartitionName(workAdapter);
         if (this.partitionNameMatches(previousPartitionName, nextPartitionName)) {
            if (debugEnabled()) {
               if (isIdleThread) {
                  this.switchIdleThreadNotNeeded.getAndIncrement();
               } else {
                  this.switchStandbyThreadNotNeeded.getAndIncrement();
               }
            }

            return activeThread;
         } else {
            ExecuteThread matchingStandbyThread = this.getMatching(nextPartitionName);
            if (matchingStandbyThread != null) {
               activeThread.setStandby(true);
               if (RequestManager.getInstance().doForceCleanupThreadlocal(activeThread)) {
                  this.offer(activeThread);
               }

               if (debugEnabled()) {
                  if (isIdleThread) {
                     this.switchIdleThreadGotFromStandby.getAndIncrement();
                  } else {
                     this.switchStandbyThreadSucceeded.getAndIncrement();
                  }
               }

               matchingStandbyThread.setStandby(false);
               activeThread = matchingStandbyThread;
            } else {
               this.clearThreadLocalIfDifferentPartition(activeThread, nextPartitionName);
               if (debugEnabled()) {
                  if (isIdleThread) {
                     this.switchIdleThreadFailed.getAndIncrement();
                  } else {
                     this.switchStandbyThreadFailed.getAndIncrement();
                  }
               }
            }

            return activeThread;
         }
      } else {
         this.clearThreadLocalIfDifferentPartition(activeThread, this.getPartitionName(workAdapter));
         return activeThread;
      }
   }

   private boolean partitionNameMatches(String name1, String name2) {
      if (name1 == null) {
         return name2 == null;
      } else {
         return name1.equals(name2);
      }
   }

   void setLimit(int value) {
      this.commonPool.setLimit(value);
   }

   int size() {
      int total = this.commonPool.size();
      Map.Entry next;
      if (this.RCM_MODE) {
         for(Iterator iterator = this.partitionedCache.entrySet().iterator(); iterator.hasNext(); total += ((PartitionedPoolEntry)next.getValue()).size()) {
            next = (Map.Entry)iterator.next();
         }
      }

      return total;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      Iterator iterator = this.partitionedCache.entrySet().iterator();
      int total = this.commonPool.size();
      builder.append("common : ").append(total);

      while(iterator.hasNext()) {
         Map.Entry next = (Map.Entry)iterator.next();
         total += ((PartitionedPoolEntry)next.getValue()).size();
         builder.append(", " + (String)next.getKey()).append(" : ").append(((PartitionedPoolEntry)next.getValue()).size());
      }

      builder.append(", total : ").append(total);
      return builder.toString();
   }

   private String getPartitionName(WorkAdapter workAdapter) {
      if (workAdapter == null) {
         return null;
      } else {
         ComponentInvocationContext cic = null;
         if (workAdapter instanceof ComponentRequest) {
            ComponentInvocationContext workAdapterContext = ((ComponentRequest)workAdapter).getComponentInvocationContext();
            if (workAdapterContext != null) {
               cic = workAdapterContext;
            }
         }

         if (cic == null) {
            SelfTuningWorkManagerImpl workManager = workAdapter.getWorkManager();
            if (workManager != null) {
               cic = workManager.getComponentInvocationContext();
            }
         }

         return cic == null ? null : cic.getPartitionName();
      }
   }

   private static boolean debugEnabled() {
      return SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void log(String str) {
      SelfTuningWorkManagerImpl.debug("<PartitionAffinityThreadPool> " + str);
   }

   static class PartitionedPoolEntry {
      ConcurrentPool pool = new ConcurrentPool(10);
      volatile long lastPollTime = 0L;

      public PartitionedPoolEntry() {
      }

      ConcurrentPool getPool() {
         return this.pool;
      }

      void setPool(ConcurrentPool pool) {
         this.pool = pool;
      }

      ExecuteThread poll() {
         return (ExecuteThread)this.pool.poll();
      }

      boolean offer(ExecuteThread executeThread) {
         return this.pool.offer(executeThread);
      }

      int size() {
         return this.pool.size();
      }
   }
}
