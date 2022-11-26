package weblogic.work.concurrent.runtime;

import java.util.HashMap;
import java.util.Map;
import weblogic.work.concurrent.spi.ThreadNumberConstraints;

public class GlobalConstraints {
   private ThreadNumberConstraints serverLimit;
   private final Map partitionLimitMap = new HashMap();
   private final Object serverLock = new Object();
   private final boolean isExecutorConstraints;
   private static final GlobalConstraints executorConstraints = new GlobalConstraints(true);
   private static final GlobalConstraints mtfConstraints = new GlobalConstraints(false);

   public static GlobalConstraints getExecutorConstraints() {
      return executorConstraints;
   }

   public static GlobalConstraints getMTFConstraints() {
      return mtfConstraints;
   }

   private GlobalConstraints(boolean isExecutorConstraints) {
      this.isExecutorConstraints = isExecutorConstraints;
   }

   public void reset() {
      synchronized(this.serverLock) {
         this.serverLimit = null;
      }

      synchronized(this.partitionLimitMap) {
         this.partitionLimitMap.clear();
      }
   }

   public ThreadNumberConstraints getServerLimit() {
      synchronized(this.serverLock) {
         if (this.serverLimit == null) {
            ConcurrentConstraintsInfo serverConfig = RuntimeAccessUtils.getServerConstraints();
            int constraints;
            if (this.isExecutorConstraints) {
               constraints = serverConfig.getMaxConcurrentLongRunningRequests();
            } else {
               constraints = serverConfig.getMaxConcurrentNewThreads();
            }

            this.serverLimit = new ThreadNumberConstraints(constraints, "server");
         }

         return this.serverLimit;
      }
   }

   public ThreadNumberConstraints getPartitionLimit(String partitionName) {
      synchronized(this.partitionLimitMap) {
         ThreadNumberConstraints partitionContains = (ThreadNumberConstraints)this.partitionLimitMap.get(partitionName);
         if (partitionContains == null) {
            ConcurrentConstraintsInfo partitionConfig = RuntimeAccessUtils.getPartitionConstraints(partitionName);
            int constraints;
            if (this.isExecutorConstraints) {
               constraints = partitionConfig.getMaxConcurrentLongRunningRequests();
            } else {
               constraints = partitionConfig.getMaxConcurrentNewThreads();
            }

            partitionContains = new ThreadNumberConstraints(constraints, "partition");
            this.partitionLimitMap.put(partitionName, partitionContains);
         }

         return partitionContains;
      }
   }

   public void removeForPartition(String partitionName) {
      synchronized(this.partitionLimitMap) {
         this.partitionLimitMap.remove(partitionName);
      }
   }
}
