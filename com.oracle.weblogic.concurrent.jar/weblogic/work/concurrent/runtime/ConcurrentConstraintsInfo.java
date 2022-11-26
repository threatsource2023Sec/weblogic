package weblogic.work.concurrent.runtime;

import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;

public class ConcurrentConstraintsInfo {
   private final int max_long_running;
   private final int max_new_threads;

   public ConcurrentConstraintsInfo(DomainMBean domain) {
      this.max_long_running = domain.getMaxConcurrentLongRunningRequests();
      this.max_new_threads = domain.getMaxConcurrentNewThreads();
   }

   public ConcurrentConstraintsInfo(ServerMBean server) {
      this.max_long_running = server.getMaxConcurrentLongRunningRequests();
      this.max_new_threads = server.getMaxConcurrentNewThreads();
   }

   public ConcurrentConstraintsInfo(PartitionMBean partition) {
      this.max_long_running = partition.getMaxConcurrentLongRunningRequests();
      this.max_new_threads = partition.getMaxConcurrentNewThreads();
   }

   public ConcurrentConstraintsInfo(int max_long_running, int max_new_threads) {
      this.max_long_running = max_long_running;
      this.max_new_threads = max_new_threads;
   }

   public int getMaxConcurrentLongRunningRequests() {
      return this.max_long_running;
   }

   public int getMaxConcurrentNewThreads() {
      return this.max_new_threads;
   }
}
