package weblogic.diagnostics.harvester;

public interface HarvesterStatistics {
   long getMaximumSamplingTimeNanos();

   long getMinimumSamplingTimeNanos();

   long getTotalConfiguredDataSampleCount();

   int getTotalSamplingCycles();

   long getTotalSamplingTimeNanos();

   long getAverageSamplingTimeNanos();
}
