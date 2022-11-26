package weblogic.j2ee.descriptor.wl;

public interface SizeParamsBean {
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
