package weblogic.management.runtime;

public interface JoltConnectionRuntimeMBean extends RuntimeMBean {
   String getAddress();

   String getLastAccessTime();

   boolean isAlive();

   int getRequestCount();

   int getPendingRequestCount();

   int getErrorCount();

   boolean isInTransaction();
}
