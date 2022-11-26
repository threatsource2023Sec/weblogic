package weblogic.common.resourcepool;

import java.util.List;
import java.util.Properties;
import weblogic.common.ResourceException;

public interface ResourcePool extends ObjectLifeCycle {
   String SHUTDOWN_STR = "Shutdown";
   String RUNNING_STR = "Running";
   String SUSPENDED_STR = "Suspended";
   String UNHEALTHY_STR = "Unhealthy";
   String OVERLOADED_STR = "Overloaded";
   String UNKNOWN_STR = "Unknown";
   int SHUTDOWN = 100;
   int RUNNING = 101;
   int SUSPENDED = 102;
   int UNHEALTHY = 103;
   int UNKNOWN = 104;
   int NO_WAIT = -1;
   int INFINITE_WAIT = 0;
   String RP_PROP_NAME = "name";
   String RP_PROP_MAX_CAPACITY = "maxCapacity";
   String RP_PROP_MIN_CAPACITY = "minCapacity";
   String RP_PROP_INITIAL_CAPACITY = "initialCapacity";
   String RP_PROP_CAPACITY_INCREMENT = "capacityIncrement";
   String RP_PROP_TEST_ON_RESERVE = "testOnReserve";
   String RP_PROP_TEST_ON_RELEASE = "testOnRelease";
   String RP_PROP_TEST_ON_CREATE = "testOnCreate";
   String RP_PROP_TEST_FREQUENCY_SECS = "testFrequencySeconds";
   String RP_PROP_SHRINK_FREQUENCY_SECS = "shrinkFrequencySeconds";
   String RP_PROP_SHRINK_ENABLED = "shrinkEnabled";
   String RP_PROP_RESV_TIMEOUT_SECS = "resvTimeoutSeconds";
   String RP_PROP_INACTIVE_RES_TIMEOUT_SECS = "inactiveResTimeoutSeconds";
   String RP_PROP_RES_CREATION_RETRY_SECS = "resCreationRetrySeconds";
   String RP_PROP_MAX_WAITERS = "maxWaiters";
   String RP_PROP_MAX_RESV_RETRY = "maxResvRetry";
   String RP_PROP_MAX_UNAVL = "maxUnavl";
   String RP_PROP_CREATE_DELAY = "createDelay";
   String RP_PROP_IGNORE_IN_USE_RESOURCES = "ignoreInUseResources";
   String RP_PROP_POOL_PURGE_THRESHOLD_CNT = "countOfTestFailuresTillFlush";
   String RP_PROP_POOL_DISABLE_THRESHOLD_CNT = "countOfRefreshFailuresTillDisable";
   String RP_PROP_PROFILE_HARVEST_FREQ_SECS = "harvestFreqSecsonds";
   String RP_PROP_MAINT_FREQ_SECS = "maintenanceFrequencySeconds";
   String RP_PROP_QUIET_MESSAGES = "quietMessages";
   String RP_GROUP_DEFAULT_NAME = "DEFAULT_GROUP_ID";

   PooledResourceFactory initPooledResourceFactory(Properties var1) throws ResourceException;

   void shrink() throws ResourceException;

   void refresh() throws ResourceException;

   String getState();

   PooledResource[] getResources();

   PooledResource reserveResource(int var1, PooledResourceInfo var2) throws ResourceException;

   PooledResource reserveResource(PooledResourceInfo var1) throws ResourceException;

   void releaseResource(PooledResource var1) throws ResourceException;

   void createResources(int var1, PooledResourceInfo[] var2) throws ResourceException;

   void createResources(int var1, PooledResourceInfo[] var2, List var3) throws ResourceException;

   PooledResource matchResource(PooledResourceInfo var1) throws ResourceException;

   int getNumLeaked();

   void incrementNumLeaked();

   int getNumFailuresToRefresh();

   int getCreationDelayTime();

   int getNumWaiters();

   int getHighestNumWaiters();

   long getTotalWaitingForConnection();

   long getTotalWaitingForConnectionSuccess();

   long getTotalWaitingForConnectionFailure();

   int getHighestWaitSeconds();

   int getNumReserved();

   int getHighestNumReserved();

   int getNumAvailable();

   int getHighestNumAvailable();

   int getNumUnavailable();

   int getHighestNumUnavailable();

   int getTotalNumAllocated();

   int getTotalNumDestroyed();

   int getMaxCapacity();

   int getMinCapacity();

   int getCurrCapacity();

   int getHighestCurrCapacity();

   int getAverageReserved();

   long getNumReserveRequests();

   long getNumFailedReserveRequests();

   void setMaximumCapacity(int var1) throws ResourceException, IllegalArgumentException;

   void setMinimumCapacity(int var1) throws ResourceException, IllegalArgumentException;

   void setInitialCapacity(int var1) throws ResourceException, IllegalArgumentException;

   void setCapacityIncrement(int var1);

   void setHighestNumWaiters(int var1);

   void setHighestNumUnavailable(int var1);

   void setInactiveResourceTimeoutSeconds(int var1);

   void setResourceCreationRetrySeconds(int var1);

   void setResourceReserveTimeoutSeconds(int var1);

   void setShrinkFrequencySeconds(int var1);

   void setTestFrequencySeconds(int var1);

   void setShrinkEnabled(boolean var1);

   void setTestOnReserve(boolean var1);

   void setTestOnRelease(boolean var1);

   void setTestOnCreate(boolean var1);

   void setIgnoreInUseResources(boolean var1);

   void setCountOfTestFailuresTillFlush(int var1);

   void setCountOfRefreshFailuresTillDisable(int var1);

   ResourcePoolProfiler getProfiler();

   void setProfileHarvestFrequencySeconds(int var1);

   ResourcePoolMaintainer getMaintainer();

   void setMaintenanceFrequencySeconds(int var1);

   ReserveReleaseInterceptor getReserveReleaseInterceptor();

   void resetStatistics();
}
