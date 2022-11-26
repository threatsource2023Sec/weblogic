package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;

public interface ConnectorConnectionPoolRuntimeMBean extends LogRuntimeMBean, HealthFeedback {
   String getPoolName();

   /** @deprecated */
   @Deprecated
   String getJNDIName();

   String getConnectionFactoryName();

   /** @deprecated */
   @Deprecated
   String getResourceAdapterLinkRefName();

   boolean isLoggingEnabled();

   String getLogFileName();

   LogRuntimeMBean getLogRuntime();

   String getTransactionSupport();

   String getRuntimeTransactionSupport();

   int getMaxCapacity();

   int getInitialCapacity();

   int getCapacityIncrement();

   boolean isShrinkingEnabled();

   int getShrinkPeriodMinutes();

   int getActiveConnectionsCurrentCount();

   int getActiveConnectionsHighCount();

   int getFreeConnectionsCurrentCount();

   int getFreeConnectionsHighCount();

   int getAverageActiveUsage();

   int getShrinkCountDownTime();

   int getRecycledTotal();

   int getConnectionsCreatedTotalCount();

   int getConnectionsMatchedTotalCount();

   int getConnectionsDestroyedTotalCount();

   int getConnectionsRejectedTotalCount();

   ConnectorConnectionRuntimeMBean[] getConnections();

   /** @deprecated */
   @Deprecated
   int getConnectionIdleProfileCount();

   /** @deprecated */
   @Deprecated
   ConnectionLeakProfile[] getConnectionIdleProfiles(int var1, int var2);

   /** @deprecated */
   @Deprecated
   ConnectionLeakProfile[] getConnectionLeakProfiles();

   /** @deprecated */
   @Deprecated
   ConnectionLeakProfile[] getConnectionIdleProfiles();

   /** @deprecated */
   @Deprecated
   int getConnectionLeakProfileCount();

   /** @deprecated */
   @Deprecated
   ConnectionLeakProfile[] getConnectionLeakProfiles(int var1, int var2);

   /** @deprecated */
   @Deprecated
   boolean getConnectionProfilingEnabled();

   /** @deprecated */
   @Deprecated
   int getMaxIdleTime();

   /** @deprecated */
   @Deprecated
   int getNumberDetectedIdle();

   /** @deprecated */
   @Deprecated
   int getNumberDetectedLeaks();

   String getConnectorEisType();

   String getEISResourceId();

   long getCloseCount();

   long getFreePoolSizeHighWaterMark();

   long getFreePoolSizeLowWaterMark();

   long getCurrentCapacity();

   long getPoolSizeHighWaterMark();

   long getPoolSizeLowWaterMark();

   String getManagedConnectionFactoryClassName();

   String getConnectionFactoryClassName();

   long getNumWaiters();

   long getHighestNumWaiters();

   boolean isTestable();

   long getLastShrinkTime();

   int getConnectionsDestroyedByErrorTotalCount();

   int getConnectionsDestroyedByShrinkingTotalCount();

   int getNumWaitersCurrentCount();

   int getNumUnavailableCurrentCount();

   int getNumUnavailableHighCount();

   boolean isProxyOn();

   boolean testPool();

   String getState();

   String getMCFClassName();

   String getKey();

   void forceReset() throws ManagementException;

   boolean reset() throws ManagementException;

   HealthState getHealthState();
}
