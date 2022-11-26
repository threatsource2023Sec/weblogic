package weblogic.management.runtime;

public interface PartitionResourceMetricsRuntimeMBean extends RuntimeMBean {
   boolean isRCMMetricsDataAvailable();

   long getCpuTimeNanos();

   long getAllocatedMemory();

   long getThreadCount();

   long getTotalOpenedSocketCount();

   long getCurrentOpenSocketCount();

   long getNetworkBytesRead();

   long getNetworkBytesWritten();

   long getTotalOpenedFileCount();

   long getCurrentOpenFileCount();

   long getFileBytesRead();

   long getFileBytesWritten();

   long getTotalOpenedFileDescriptorCount();

   long getCurrentOpenFileDescriptorCount();

   Long[][] getRetainedHeapHistoricalData();

   Long[][] getCpuUtilizationHistoricalData();
}
