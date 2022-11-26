package weblogic.diagnostics.image.descriptor;

public interface JVMRuntimeBean {
   void setObjectPendingFinalizationCount(int var1);

   int getObjectPendingFinalizationCount();

   void setHeapMemoryUsedBytes(long var1);

   long getHeapMemoryUsedBytes();

   void setHeapMemoryMaxBytes(long var1);

   long getHeapMemoryMaxBytes();

   void setHeapMemoryInitBytes(long var1);

   long getHeapMemoryInitBytes();

   void setHeapMemoryCommittedBytes(long var1);

   long getHeapMemoryCommittedBytes();

   void setNonHeapMemoryUsedBytes(long var1);

   long getNonHeapMemoryUsedBytes();

   void setNonHeapMemoryMaxBytes(long var1);

   long getNonHeapMemoryMaxBytes();

   void setNonHeapMemoryInitBytes(long var1);

   long getNonHeapMemoryInitBytes();

   void setNonHeapMemoryCommittedBytes(long var1);

   long getNonHeapMemoryCommittedBytes();

   void setThreadCount(int var1);

   int getThreadCount();

   void setPeakThreadCount(int var1);

   int getPeakThreadCount();

   void setTotalStartedThreadCount(long var1);

   long getTotalStartedThreadCount();

   void setDaemonThreadCount(int var1);

   int getDaemonThreadCount();

   void setThreadContentionMonitoringSupported(boolean var1);

   boolean isThreadContentionMonitoringSupported();

   void setThreadContentionMonitoringEnabled(boolean var1);

   boolean isThreadContentionMonitoringEnabled();

   void setCurrentThreadCpuTime(long var1);

   long getCurrentThreadCpuTime();

   void setCurrentThreadUserTime(long var1);

   long getCurrentThreadUserTime();

   void setThreadCpuTimeSupported(boolean var1);

   boolean isThreadCpuTimeSupported();

   void setCurrentThreadCpuTimeSupported(boolean var1);

   boolean isCurrentThreadCpuTimeSupported();

   void setThreadCpuTimeEnabled(boolean var1);

   boolean isThreadCpuTimeEnabled();

   void setRunningJVMName(String var1);

   String getRunningJVMName();

   void setManagementSpecVersion(String var1);

   String getManagementSpecVersion();

   void setVmName(String var1);

   String getVmName();

   void setVmVendor(String var1);

   String getVmVendor();

   void setVmVersion(String var1);

   String getVmVersion();

   void setSpecName(String var1);

   String getSpecName();

   void setSpecVendor(String var1);

   String getSpecVendor();

   void setSpecVersion(String var1);

   String getSpecVersion();

   void setClassPath(String var1);

   String getClassPath();

   void setLibraryPath(String var1);

   String getLibraryPath();

   void setBootClassPath(String var1);

   String getBootClassPath();

   void setUptime(long var1);

   long getUptime();

   void setStartTime(long var1);

   long getStartTime();

   void setBootClassPathSupported(boolean var1);

   boolean isBootClassPathSupported();

   void setOSName(String var1);

   String getOSName();

   void setOSVersion(String var1);

   String getOSVersion();

   void setOSArch(String var1);

   String getOSArch();

   void setOSAvailableProcessors(int var1);

   int getOSAvailableProcessors();

   void setLoadedClassCount(int var1);

   int getLoadedClassCount();

   void setTotalLoadedClassCount(long var1);

   long getTotalLoadedClassCount();

   void setUnloadedClassCount(long var1);

   long getUnloadedClassCount();

   String getThreadDump();

   void setThreadDump(String var1);

   String getThreadRequestExecutionDetails();

   void setThreadRequestExecutionDetails(String var1);

   long getPartitionCpuTimeNanos();

   void setPartitionCpuTimeNanos(long var1);

   long getPartitionAllocatedMemory();

   void setPartitionAllocatedMemory(long var1);

   long getPartitionThreadCount();

   void setPartitionThreadCount(long var1);

   long getPartitionTotalOpenedSocketCount();

   void setPartitionTotalOpenedSocketCount(long var1);

   long getPartitionCurrentOpenSocketCount();

   void setPartitionCurrentOpenSocketCount(long var1);

   long getPartitionNetworkBytesRead();

   void setPartitionNetworkBytesRead(long var1);

   long getPartitionNetworkBytesWritten();

   void setPartitionNetworkBytesWritten(long var1);

   long getPartitionTotalOpenedFileCount();

   void setPartitionTotalOpenedFileCount(long var1);

   long getPartitionCurrentOpenFileCount();

   void setPartitionCurrentOpenFileCount(long var1);

   long getPartitionFileBytesRead();

   void setPartitionFileBytesRead(long var1);

   long getPartitionFileBytesWritten();

   void setPartitionFileBytesWritten(long var1);

   String getRetainedHeapHistoricalData();

   void setRetainedHeapHistoricalData(String var1);
}
