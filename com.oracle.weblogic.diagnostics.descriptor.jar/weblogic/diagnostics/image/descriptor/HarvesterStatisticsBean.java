package weblogic.diagnostics.image.descriptor;

public interface HarvesterStatisticsBean {
   int getActiveModulesCount();

   void setActiveModulesCount(int var1);

   long getAverageSamplingTime();

   void setAverageSamplingTime(long var1);

   long getMaximumSamplingTime();

   void setMaximumSamplingTime(long var1);

   long getMinimumSamplingTime();

   void setMinimumSamplingTime(long var1);

   long getTotalDataSampleCount();

   void setTotalDataSampleCount(long var1);

   long getTotalSamplingCycles();

   void setTotalSamplingCycles(long var1);
}
