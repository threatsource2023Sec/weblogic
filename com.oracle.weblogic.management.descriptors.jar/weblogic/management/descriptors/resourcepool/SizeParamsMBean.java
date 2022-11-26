package weblogic.management.descriptors.resourcepool;

import weblogic.management.descriptors.XMLElementMBean;

public interface SizeParamsMBean extends XMLElementMBean {
   int DEFAULT_INITIAL_CAPACITY = 1;
   int DEFAULT_MAX_CAPACITY = 15;
   int DEFAULT_CAPACITY_INCREMENT = 1;
   boolean DEFAULT_SHRINKING_ENABLED = true;
   int DEFAULT_SHRINKPERIOD_MINUTES = 15;
   int DEFAULT_SHRINKFREQUENCY_SECONDS = 900;
   int DEFAULT_HIGHEST_NUM_UNAVAILABLE = 0;
   int DEFAULT_HIGHEST_NUM_WAITERS = Integer.MAX_VALUE;

   int getInitialCapacity();

   void setInitialCapacity(int var1);

   int getMaxCapacity();

   void setMaxCapacity(int var1);

   int getCapacityIncrement();

   void setCapacityIncrement(int var1);

   boolean isShrinkingEnabled();

   void setShrinkingEnabled(boolean var1);

   int getShrinkPeriodMinutes();

   void setShrinkPeriodMinutes(int var1);

   int getShrinkFrequencySeconds();

   void setShrinkFrequencySeconds(int var1);

   int getHighestNumWaiters();

   void setHighestNumWaiters(int var1);

   int getHighestNumUnavailable();

   void setHighestNumUnavailable(int var1);
}
