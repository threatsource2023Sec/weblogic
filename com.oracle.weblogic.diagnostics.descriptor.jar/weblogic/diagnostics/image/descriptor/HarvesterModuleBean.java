package weblogic.diagnostics.image.descriptor;

public interface HarvesterModuleBean {
   String getModuleName();

   void setModuleName(String var1);

   String getHarvesterCycleStartTime();

   void setHarvesterCycleStartTime(String var1);

   void setHarvesterCycleDurationNanos(long var1);

   long getHarvesterCycleDurationNanos();

   void setHarvesterSamples(String[] var1);

   boolean addHarvesterSample(String var1);

   boolean removeHarvesterSample(String var1);

   String[] getHarvesterSamples();

   HarvesterModuleStatisticsBean getModuleStatistics();

   HarvesterModuleStatisticsBean createModuleStatistics();
}
