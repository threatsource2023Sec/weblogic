package weblogic.work;

interface RequestClass {
   String getName();

   int getThreadPriority();

   boolean isInternal();

   void workCompleted(long var1, long var3);

   void timeElapsed(long var1, ServiceClassesStats var3);

   long getVirtualTimeIncrement(long var1, long var3);

   int getPendingRequestsCount();

   PartitionFairShare getPartitionFairShare();

   void cleanup();
}
