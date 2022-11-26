package weblogic.common.resourcepool;

public interface ResourcePoolGroup {
   String getName();

   String getCategoryName();

   String getState();

   void enable();

   void disable();

   boolean isEnabled();

   int getNumReserved();

   int getCurrCapacity();

   int getHighestCurrCapacity();

   int getNumAvailable();

   int getNumUnavailable();

   int getNumReserveRequests();

   int getTotalNumAllocated();

   void resetStatistics();
}
