package weblogic.management.runtime;

public interface JoltConnectionServiceRuntimeMBean extends RuntimeMBean {
   int getConnectionPoolCount();

   JoltConnectionPoolRuntimeMBean[] getConnectionPools();
}
