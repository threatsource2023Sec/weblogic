package weblogic.management.runtime;

public interface JDBCOracleDataSourceInstanceRuntimeMBean extends RuntimeMBean {
   String getInstanceName();

   String getSignature();

   int getCurrentWeight();

   int getActiveConnectionsCurrentCount();

   long getReserveRequestCount();

   int getConnectionsTotalCount();

   int getCurrCapacity();

   int getNumAvailable();

   int getNumUnavailable();

   boolean isEnabled();

   String getState();

   boolean isAffEnabled();
}
