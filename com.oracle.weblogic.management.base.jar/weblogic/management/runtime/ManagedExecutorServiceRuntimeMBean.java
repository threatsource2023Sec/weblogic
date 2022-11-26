package weblogic.management.runtime;

public interface ManagedExecutorServiceRuntimeMBean extends RuntimeMBean {
   String getPartitionName();

   String getApplicationName();

   String getModuleName();

   long getRunningLongRunningRequests();

   long getCompletedShortRunningRequests();

   long getCompletedLongRunningRequests();

   long getSubmitedShortRunningRequests();

   long getSubmittedLongRunningRequests();

   long getRejectedShortRunningRequests();

   long getRejectedLongRunningRequests();

   long getFailedRequests();

   WorkManagerRuntimeMBean getWorkManager();
}
