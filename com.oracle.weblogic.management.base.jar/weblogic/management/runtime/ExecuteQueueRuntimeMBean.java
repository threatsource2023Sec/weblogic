package weblogic.management.runtime;

public interface ExecuteQueueRuntimeMBean extends RuntimeMBean {
   ExecuteThread[] getExecuteThreads();

   ExecuteThread[] getStuckExecuteThreads();

   int getExecuteThreadTotalCount();

   int getExecuteThreadCurrentIdleCount();

   long getPendingRequestOldestTime();

   int getPendingRequestCurrentCount();

   int getServicedRequestTotalCount();
}
