package weblogic.management.runtime;

public interface JoltConnectionPoolRuntimeMBean extends RuntimeMBean {
   String getPoolName();

   int getMaxCapacity();

   String getPoolState();

   boolean isSecurityContextPropagation();

   JoltConnectionRuntimeMBean[] getConnections();

   int resetConnectionPool();
}
