package weblogic.common.resourcepool;

public interface ResourcePoolProfiler {
   boolean isResourceUsageProfilingEnabled();

   boolean isResourceReserveWaitProfilingEnabled();

   boolean isResourceReserveFailProfilingEnabled();

   boolean isResourceLeakProfilingEnabled();

   void dumpData();

   void harvestData();

   void deleteData();

   void addUsageData(PooledResource var1);

   void deleteUsageData(PooledResource var1);

   void addWaitData();

   void deleteWaitData();

   void addLeakData(PooledResource var1);

   void addResvFailData(String var1);
}
