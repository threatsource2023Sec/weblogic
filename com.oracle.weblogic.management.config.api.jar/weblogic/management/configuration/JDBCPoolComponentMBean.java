package weblogic.management.configuration;

import weblogic.management.ManagementException;

public interface JDBCPoolComponentMBean extends ComponentMBean, DeploymentMBean {
   int getInitialCapacity() throws ManagementException;

   void setInitialCapacity(int var1) throws ManagementException;

   int getMaxCapacity() throws ManagementException;

   void setMaxCapacity(int var1) throws ManagementException;

   int getCapacityIncrement() throws ManagementException;

   void setCapacityIncrement(int var1) throws ManagementException;

   int getHighestNumWaiters() throws ManagementException;

   void setHighestNumWaiters(int var1) throws ManagementException;

   int getHighestNumUnavailable() throws ManagementException;

   void setHighestNumUnavailable(int var1) throws ManagementException;

   int getInactiveConnectionTimeoutSeconds() throws ManagementException;

   void setInactiveConnectionTimeoutSeconds(int var1) throws ManagementException;

   int getConnectionCreationRetryFrequencySeconds() throws ManagementException;

   void setConnectionCreationRetryFrequencySeconds(int var1) throws ManagementException;

   int getConnectionReserveTimeoutSeconds() throws ManagementException;

   void setConnectionReserveTimeoutSeconds(int var1) throws ManagementException;

   int getShrinkFrequencySeconds() throws ManagementException;

   void setShrinkFrequencySeconds(int var1) throws ManagementException;

   int getTestFrequencySeconds() throws ManagementException;

   void setTestFrequencySeconds(int var1) throws ManagementException;

   int getCacheSize() throws ManagementException;

   void setCacheSize(int var1) throws ManagementException;

   boolean isShrinkingEnabled() throws ManagementException;

   void setShrinkingEnabled(boolean var1) throws ManagementException;

   boolean isCheckOnReserveEnabled() throws ManagementException;

   void setCheckOnReserveEnabled(boolean var1) throws ManagementException;

   boolean isCheckOnReleaseEnabled() throws ManagementException;

   void setCheckOnReleaseEnabled(boolean var1) throws ManagementException;

   boolean isCheckOnCreateEnabled() throws ManagementException;

   void setCheckOnCreateEnabled(boolean var1) throws ManagementException;

   int getMaxIdleTime() throws ManagementException;

   void setMaxIdleTime(int var1) throws ManagementException;

   boolean isProfilingEnabled() throws ManagementException;

   void setProfilingEnabled(boolean var1) throws ManagementException;

   boolean isLoggingEnabled() throws ManagementException;

   void setLoggingEnabled(boolean var1) throws ManagementException;
}
