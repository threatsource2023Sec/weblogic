package weblogic.connector.external;

public interface PoolInfo {
   int getInitialCapacity();

   int getMaxCapacity();

   int getCapacityIncrement();

   boolean isShrinkingEnabled();

   int getShrinkFrequencySeconds();

   int getHighestNumWaiters();

   int getHighestNumUnavailable();

   int getConnectionCreationRetryFrequencySeconds();

   int getConnectionReserveTimeoutSeconds();

   int getTestFrequencySeconds();

   boolean isTestConnectionsOnCreate();

   boolean isTestConnectionsOnRelease();

   boolean isTestConnectionsOnReserve();

   int getProfileHarvestFrequencySeconds();

   boolean isIgnoreInUseConnectionsEnabled();

   boolean isMatchConnectionsSupported();
}
