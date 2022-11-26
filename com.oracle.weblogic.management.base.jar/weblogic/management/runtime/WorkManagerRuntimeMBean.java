package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface WorkManagerRuntimeMBean extends RuntimeMBean, HealthFeedback {
   String getApplicationName();

   String getModuleName();

   int getPendingRequests();

   long getCompletedRequests();

   int getPendingDaemonRequests();

   long getCompletedDaemonRequests();

   int getStuckThreadCount();

   MinThreadsConstraintRuntimeMBean getMinThreadsConstraintRuntime();

   MaxThreadsConstraintRuntimeMBean getMaxThreadsConstraintRuntime();

   void setMinThreadsConstraintRuntime(MinThreadsConstraintRuntimeMBean var1);

   void setMaxThreadsConstraintRuntime(MaxThreadsConstraintRuntimeMBean var1);

   void setRequestClassRuntime(RequestClassRuntimeMBean var1);

   RequestClassRuntimeMBean getRequestClassRuntime();

   HealthState getHealthState();

   String getPartitionName();

   CapacityRuntimeMBean getCapacityRuntime();

   void setCapacityRuntime(CapacityRuntimeMBean var1);
}
