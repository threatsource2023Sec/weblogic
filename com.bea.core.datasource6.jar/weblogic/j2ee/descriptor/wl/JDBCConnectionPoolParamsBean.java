package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCConnectionPoolParamsBean extends SettableBean {
   int getInitialCapacity();

   void setInitialCapacity(int var1);

   int getMaxCapacity();

   void setMaxCapacity(int var1);

   int getMinCapacity();

   void setMinCapacity(int var1);

   /** @deprecated */
   @Deprecated
   int getCapacityIncrement();

   /** @deprecated */
   @Deprecated
   void setCapacityIncrement(int var1);

   int getShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(int var1);

   int getHighestNumWaiters();

   void setHighestNumWaiters(int var1);

   int getConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(int var1);

   int getConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   int getTestFrequencySeconds();

   void setTestFrequencySeconds(int var1);

   boolean isTestConnectionsOnReserve();

   void setTestConnectionsOnReserve(boolean var1);

   int getProfileHarvestFrequencySeconds();

   void setProfileHarvestFrequencySeconds(int var1);

   boolean isIgnoreInUseConnectionsEnabled();

   void setIgnoreInUseConnectionsEnabled(boolean var1);

   int getInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(int var1);

   String getTestTableName();

   void setTestTableName(String var1);

   int getLoginDelaySeconds();

   void setLoginDelaySeconds(int var1);

   String getInitSql();

   void setInitSql(String var1);

   int getStatementCacheSize();

   void setStatementCacheSize(int var1);

   String getStatementCacheType();

   void setStatementCacheType(String var1);

   boolean isRemoveInfectedConnections();

   void setRemoveInfectedConnections(boolean var1);

   int getSecondsToTrustAnIdlePoolConnection();

   void setSecondsToTrustAnIdlePoolConnection(int var1);

   int getStatementTimeout();

   void setStatementTimeout(int var1);

   int getProfileType();

   void setProfileType(int var1);

   int getJDBCXADebugLevel();

   void setJDBCXADebugLevel(int var1);

   boolean isCredentialMappingEnabled();

   void setCredentialMappingEnabled(boolean var1);

   String getDriverInterceptor();

   void setDriverInterceptor(String var1);

   boolean isPinnedToThread();

   void setPinnedToThread(boolean var1);

   boolean isIdentityBasedConnectionPoolingEnabled();

   void setIdentityBasedConnectionPoolingEnabled(boolean var1);

   boolean isWrapTypes();

   void setWrapTypes(boolean var1);

   String getFatalErrorCodes();

   void setFatalErrorCodes(String var1);

   String getConnectionLabelingCallback();

   void setConnectionLabelingCallback(String var1);

   int getConnectionHarvestMaxCount();

   void setConnectionHarvestMaxCount(int var1);

   int getConnectionHarvestTriggerCount();

   void setConnectionHarvestTriggerCount(int var1);

   int getCountOfTestFailuresTillFlush();

   void setCountOfTestFailuresTillFlush(int var1);

   int getCountOfRefreshFailuresTillDisable();

   void setCountOfRefreshFailuresTillDisable(int var1);

   boolean isWrapJdbc();

   void setWrapJdbc(boolean var1);

   int getProfileConnectionLeakTimeoutSeconds();

   void setProfileConnectionLeakTimeoutSeconds(int var1);

   boolean isInvokeBeginEndRequest();

   void setInvokeBeginEndRequest(boolean var1);
}
