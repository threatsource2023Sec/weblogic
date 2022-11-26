package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface TransactionResourceRuntimeMBean extends JTAStatisticsRuntimeMBean, HealthFeedback {
   String getResourceName();

   long getTransactionHeuristicCommitTotalCount();

   long getTransactionHeuristicRollbackTotalCount();

   long getTransactionHeuristicMixedTotalCount();

   long getTransactionHeuristicHazardTotalCount();

   HealthState getHealthState();
}
