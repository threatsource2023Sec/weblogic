package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface ThreadPoolRuntimeMBean extends RuntimeMBean, HealthFeedback {
   ExecuteThread[] getExecuteThreads();

   ExecuteThread getExecuteThread(String var1);

   ExecuteThread[] getStuckExecuteThreads();

   int getExecuteThreadTotalCount();

   int getExecuteThreadIdleCount();

   int getQueueLength();

   int getPendingUserRequestCount();

   int getSharedCapacityForWorkManagers();

   long getCompletedRequestCount();

   int getHoggingThreadCount();

   int getStandbyThreadCount();

   double getThroughput();

   int getMinThreadsConstraintsPending();

   long getMinThreadsConstraintsCompleted();

   boolean isSuspended();

   HealthState getHealthState();

   int getOverloadRejectedRequestsCount();

   int getStuckThreadCount();
}
