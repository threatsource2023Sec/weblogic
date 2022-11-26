package weblogic.diagnostics.image.descriptor;

public interface HarvesterModuleStatisticsBean {
   long getAverageSamplingTime();

   void setAverageSamplingTime(long var1);

   long getCurrentDataSampleCount();

   void setCurrentDataSampleCount(long var1);

   long getMaximumSamplingTime();

   void setMaximumSamplingTime(long var1);

   long getMinimumSamplingTime();

   void setMinimumSamplingTime(long var1);

   long getTotalDataSampleCount();

   void setTotalDataSampleCount(long var1);

   long getTotalSamplingCycles();

   void setTotalSamplingCycles(long var1);
}
