package weblogic.connector.external.impl;

import weblogic.connector.external.PoolInfo;

public class PoolInfoImpl implements PoolInfo {
   private int initialCapacity;
   private int maxCapacity;
   private int capacityIncrement;
   private boolean shrinkingEnabled;
   private int shrinkFrequencySeconds;
   private int highestNumWaiters;
   private int highestNumUnavailable;
   private int connectionCreationRetryFrequencySeconds;
   private int connectionReserveTimeoutSeconds;
   private int testFrequencySeconds;
   private boolean testConnectionsOnCreate;
   private boolean testConnectionsOnRelease;
   private boolean testConnectionsOnReserve;
   private int profileHarvestFrequencySeconds;
   private boolean ignoreInUseConnectionsEnabled;
   private boolean matchConnectionsSupported;

   public PoolInfoImpl(int initialCapacity, int maxCapacity, int capacityIncrement, boolean shrinkingEnabled, int shrinkFrequencySeconds, int highestNumWaiters, int highestNumUnavailable, int connectionCreationRetryFrequencySeconds, int connectionReserveTimeoutSeconds, int testFrequencySeconds, boolean testConnectionsOnCreate, boolean testConnectionsOnRelease, boolean testConnectionsOnReserve, int profileHarvestFrequencySeconds, boolean ignoreInUseConnectionsEnabled, boolean matchConnectionsSupported) {
      this.initialCapacity = initialCapacity;
      this.maxCapacity = maxCapacity;
      this.capacityIncrement = capacityIncrement;
      this.shrinkingEnabled = shrinkingEnabled;
      this.shrinkFrequencySeconds = shrinkFrequencySeconds;
      this.highestNumWaiters = highestNumWaiters;
      this.highestNumUnavailable = highestNumUnavailable;
      this.connectionCreationRetryFrequencySeconds = connectionCreationRetryFrequencySeconds;
      this.connectionReserveTimeoutSeconds = connectionReserveTimeoutSeconds;
      this.testFrequencySeconds = testFrequencySeconds;
      this.testConnectionsOnCreate = testConnectionsOnCreate;
      this.testConnectionsOnRelease = testConnectionsOnRelease;
      this.testConnectionsOnReserve = testConnectionsOnReserve;
      this.profileHarvestFrequencySeconds = profileHarvestFrequencySeconds;
      this.ignoreInUseConnectionsEnabled = ignoreInUseConnectionsEnabled;
      this.matchConnectionsSupported = matchConnectionsSupported;
   }

   public int getInitialCapacity() {
      return this.initialCapacity;
   }

   public int getMaxCapacity() {
      return this.maxCapacity;
   }

   public int getCapacityIncrement() {
      return this.capacityIncrement;
   }

   public boolean isShrinkingEnabled() {
      return this.shrinkingEnabled;
   }

   public int getShrinkFrequencySeconds() {
      return this.shrinkFrequencySeconds;
   }

   public int getHighestNumWaiters() {
      return this.highestNumWaiters;
   }

   public int getHighestNumUnavailable() {
      return this.highestNumUnavailable;
   }

   public int getConnectionCreationRetryFrequencySeconds() {
      return this.connectionCreationRetryFrequencySeconds;
   }

   public int getConnectionReserveTimeoutSeconds() {
      return this.connectionReserveTimeoutSeconds;
   }

   public int getTestFrequencySeconds() {
      return this.testFrequencySeconds;
   }

   public boolean isTestConnectionsOnCreate() {
      return this.testConnectionsOnCreate;
   }

   public boolean isTestConnectionsOnRelease() {
      return this.testConnectionsOnRelease;
   }

   public boolean isTestConnectionsOnReserve() {
      return this.testConnectionsOnReserve;
   }

   public int getProfileHarvestFrequencySeconds() {
      return this.profileHarvestFrequencySeconds;
   }

   public boolean isIgnoreInUseConnectionsEnabled() {
      return this.ignoreInUseConnectionsEnabled;
   }

   public boolean isMatchConnectionsSupported() {
      return this.matchConnectionsSupported;
   }
}
