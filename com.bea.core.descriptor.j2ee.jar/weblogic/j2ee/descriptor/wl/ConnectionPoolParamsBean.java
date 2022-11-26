package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface ConnectionPoolParamsBean extends SettableBean {
   int getInitialCapacity();

   void setInitialCapacity(int var1);

   int getMaxCapacity();

   void setMaxCapacity(int var1);

   int getCapacityIncrement();

   void setCapacityIncrement(int var1);

   boolean isShrinkingEnabled();

   void setShrinkingEnabled(boolean var1);

   int getShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(int var1);

   int getHighestNumWaiters();

   void setHighestNumWaiters(int var1);

   int getHighestNumUnavailable();

   void setHighestNumUnavailable(int var1);

   int getConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(int var1);

   int getConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   int getTestFrequencySeconds();

   void setTestFrequencySeconds(int var1);

   boolean isTestConnectionsOnCreate();

   void setTestConnectionsOnCreate(boolean var1);

   boolean isTestConnectionsOnRelease();

   void setTestConnectionsOnRelease(boolean var1);

   boolean isTestConnectionsOnReserve();

   void setTestConnectionsOnReserve(boolean var1);

   int getProfileHarvestFrequencySeconds();

   void setProfileHarvestFrequencySeconds(int var1);

   boolean isIgnoreInUseConnectionsEnabled();

   void setIgnoreInUseConnectionsEnabled(boolean var1);
}
