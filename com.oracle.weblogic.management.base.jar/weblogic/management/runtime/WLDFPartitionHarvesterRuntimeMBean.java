package weblogic.management.runtime;

public interface WLDFPartitionHarvesterRuntimeMBean extends RuntimeMBean {
   long getAverageSamplingTime();

   long getMaximumSamplingTime();

   long getMinimumSamplingTime();

   long getTotalSamplingCycles();

   long getTotalSamplingTime();

   long getTotalConfiguredDataSampleCount();

   long getTotalImplicitDataSampleCount();
}
