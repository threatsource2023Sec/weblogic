package weblogic.management.runtime;

public interface ManagedThreadFactoryRuntimeMBean extends RuntimeMBean {
   String getPartitionName();

   String getApplicationName();

   String getModuleName();

   int getRunningThreadsCount();

   long getCompletedThreadsCount();

   long getRejectedNewThreadRequests();
}
