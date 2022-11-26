package weblogic.management.runtime;

public interface ConnectorWorkManagerRuntimeMBean extends RuntimeMBean {
   int getMaxConcurrentLongRunningRequests();

   int getActiveLongRunningRequests();

   int getCompletedLongRunningRequests();
}
