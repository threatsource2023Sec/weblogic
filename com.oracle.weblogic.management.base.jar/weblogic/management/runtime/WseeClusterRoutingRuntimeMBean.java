package weblogic.management.runtime;

public interface WseeClusterRoutingRuntimeMBean extends RuntimeMBean {
   int getRequestCount();

   int getRoutedRequestCount();

   int getResponseCount();

   int getRoutedResponseCount();

   int getRoutingFailureCount();

   String getLastRoutingFailure();

   long getLastRoutingFailureTime();
}
