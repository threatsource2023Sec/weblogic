package weblogic.management.runtime;

public interface JDBCReplayStatisticsRuntimeMBean extends ComponentRuntimeMBean {
   void refreshStatistics();

   void clearStatistics();

   long getTotalRequests();

   long getTotalCompletedRequests();

   long getTotalCalls();

   long getTotalProtectedCalls();

   long getTotalCallsAffectedByOutages();

   long getTotalCallsTriggeringReplay();

   long getTotalCallsAffectedByOutagesDuringReplay();

   long getSuccessfulReplayCount();

   long getFailedReplayCount();

   long getReplayDisablingCount();

   long getTotalReplayAttempts();
}
