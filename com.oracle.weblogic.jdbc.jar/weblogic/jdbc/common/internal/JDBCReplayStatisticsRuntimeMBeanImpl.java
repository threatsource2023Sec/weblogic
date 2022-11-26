package weblogic.jdbc.common.internal;

import weblogic.management.ManagementException;
import weblogic.management.runtime.JDBCReplayStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WorkManagerRuntimeMBean;

public class JDBCReplayStatisticsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements JDBCReplayStatisticsRuntimeMBean {
   JDBCConnectionPool pool;

   public JDBCReplayStatisticsRuntimeMBeanImpl(JDBCConnectionPool pool, String beanName, RuntimeMBean parent) throws ManagementException {
      super(beanName, parent, true);
      if (pool == null) {
         throw new ManagementException("Pool not set");
      } else {
         this.pool = pool;
      }
   }

   public JDBCReplayStatisticsRuntimeMBeanImpl(JDBCConnectionPool pool, String beanName) throws ManagementException {
      if (pool == null) {
         throw new ManagementException("Pool not set");
      } else {
         this.pool = pool;
      }
   }

   public void refreshStatistics() {
      this.pool.getReplayStatistics();
   }

   public void clearStatistics() {
      this.pool.clearReplayStatistics();
   }

   public long getTotalRequests() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalRequests;
   }

   public long getTotalCompletedRequests() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalCompletedRequests;
   }

   public long getTotalCalls() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalCalls;
   }

   public long getTotalProtectedCalls() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalProtectedCalls;
   }

   public long getTotalCallsAffectedByOutages() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalCallsAffectedByOutages;
   }

   public long getTotalCallsTriggeringReplay() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalCallsTriggeringReplay;
   }

   public long getTotalCallsAffectedByOutagesDuringReplay() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalCallsAffectedByOutagesDuringReplay;
   }

   public long getSuccessfulReplayCount() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().successfulReplayCount;
   }

   public long getFailedReplayCount() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().failedReplayCount;
   }

   public long getReplayDisablingCount() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().replayDisablingCount;
   }

   public long getTotalReplayAttempts() {
      return this.pool.getReplayStatisticsSnapshot() == null ? -1L : this.pool.getReplayStatisticsSnapshot().totalReplayAttempts;
   }

   public WorkManagerRuntimeMBean[] getWorkManagerRuntimes() {
      return null;
   }

   public boolean addWorkManagerRuntime(WorkManagerRuntimeMBean wmRuntime) {
      return true;
   }

   public void setDeploymentState(int state) {
   }

   public int getDeploymentState() {
      return 0;
   }

   public String getModuleId() {
      return this.getName();
   }
}
