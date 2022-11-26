package weblogic.management.runtime;

public interface JMSPooledConnectionRuntimeMBean extends RuntimeMBean {
   int getNumLeaked();

   int getNumFailuresToRefresh();

   int getCreationDelayTime();

   int getNumWaiters();

   int getHighestNumWaiters();

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

   int getCurrCapacity();

   int getAverageReserved();

   int getNumConnectionObjects();
}
