package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JVMRuntimeBeanImpl extends AbstractDescriptorBean implements JVMRuntimeBean, Serializable {
   private String _BootClassPath;
   private boolean _BootClassPathSupported;
   private String _ClassPath;
   private long _CurrentThreadCpuTime;
   private boolean _CurrentThreadCpuTimeSupported;
   private long _CurrentThreadUserTime;
   private int _DaemonThreadCount;
   private long _HeapMemoryCommittedBytes;
   private long _HeapMemoryInitBytes;
   private long _HeapMemoryMaxBytes;
   private long _HeapMemoryUsedBytes;
   private String _LibraryPath;
   private int _LoadedClassCount;
   private String _ManagementSpecVersion;
   private long _NonHeapMemoryCommittedBytes;
   private long _NonHeapMemoryInitBytes;
   private long _NonHeapMemoryMaxBytes;
   private long _NonHeapMemoryUsedBytes;
   private String _OSArch;
   private int _OSAvailableProcessors;
   private String _OSName;
   private String _OSVersion;
   private int _ObjectPendingFinalizationCount;
   private long _PartitionAllocatedMemory;
   private long _PartitionCpuTimeNanos;
   private long _PartitionCurrentOpenFileCount;
   private long _PartitionCurrentOpenSocketCount;
   private long _PartitionFileBytesRead;
   private long _PartitionFileBytesWritten;
   private long _PartitionNetworkBytesRead;
   private long _PartitionNetworkBytesWritten;
   private long _PartitionThreadCount;
   private long _PartitionTotalOpenedFileCount;
   private long _PartitionTotalOpenedSocketCount;
   private int _PeakThreadCount;
   private String _RetainedHeapHistoricalData;
   private String _RunningJVMName;
   private String _SpecName;
   private String _SpecVendor;
   private String _SpecVersion;
   private long _StartTime;
   private boolean _ThreadContentionMonitoringEnabled;
   private boolean _ThreadContentionMonitoringSupported;
   private int _ThreadCount;
   private boolean _ThreadCpuTimeEnabled;
   private boolean _ThreadCpuTimeSupported;
   private String _ThreadDump;
   private String _ThreadRequestExecutionDetails;
   private long _TotalLoadedClassCount;
   private long _TotalStartedThreadCount;
   private long _UnloadedClassCount;
   private long _Uptime;
   private String _VmName;
   private String _VmVendor;
   private String _VmVersion;
   private static SchemaHelper2 _schemaHelper;

   public JVMRuntimeBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JVMRuntimeBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JVMRuntimeBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void setObjectPendingFinalizationCount(int param0) {
      int _oldVal = this._ObjectPendingFinalizationCount;
      this._ObjectPendingFinalizationCount = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getObjectPendingFinalizationCount() {
      return this._ObjectPendingFinalizationCount;
   }

   public boolean isObjectPendingFinalizationCountInherited() {
      return false;
   }

   public boolean isObjectPendingFinalizationCountSet() {
      return this._isSet(0);
   }

   public void setHeapMemoryUsedBytes(long param0) {
      long _oldVal = this._HeapMemoryUsedBytes;
      this._HeapMemoryUsedBytes = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getHeapMemoryUsedBytes() {
      return this._HeapMemoryUsedBytes;
   }

   public boolean isHeapMemoryUsedBytesInherited() {
      return false;
   }

   public boolean isHeapMemoryUsedBytesSet() {
      return this._isSet(1);
   }

   public void setHeapMemoryMaxBytes(long param0) {
      long _oldVal = this._HeapMemoryMaxBytes;
      this._HeapMemoryMaxBytes = param0;
      this._postSet(2, _oldVal, param0);
   }

   public long getHeapMemoryMaxBytes() {
      return this._HeapMemoryMaxBytes;
   }

   public boolean isHeapMemoryMaxBytesInherited() {
      return false;
   }

   public boolean isHeapMemoryMaxBytesSet() {
      return this._isSet(2);
   }

   public void setHeapMemoryInitBytes(long param0) {
      long _oldVal = this._HeapMemoryInitBytes;
      this._HeapMemoryInitBytes = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getHeapMemoryInitBytes() {
      return this._HeapMemoryInitBytes;
   }

   public boolean isHeapMemoryInitBytesInherited() {
      return false;
   }

   public boolean isHeapMemoryInitBytesSet() {
      return this._isSet(3);
   }

   public void setHeapMemoryCommittedBytes(long param0) {
      long _oldVal = this._HeapMemoryCommittedBytes;
      this._HeapMemoryCommittedBytes = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getHeapMemoryCommittedBytes() {
      return this._HeapMemoryCommittedBytes;
   }

   public boolean isHeapMemoryCommittedBytesInherited() {
      return false;
   }

   public boolean isHeapMemoryCommittedBytesSet() {
      return this._isSet(4);
   }

   public void setNonHeapMemoryUsedBytes(long param0) {
      long _oldVal = this._NonHeapMemoryUsedBytes;
      this._NonHeapMemoryUsedBytes = param0;
      this._postSet(5, _oldVal, param0);
   }

   public long getNonHeapMemoryUsedBytes() {
      return this._NonHeapMemoryUsedBytes;
   }

   public boolean isNonHeapMemoryUsedBytesInherited() {
      return false;
   }

   public boolean isNonHeapMemoryUsedBytesSet() {
      return this._isSet(5);
   }

   public void setNonHeapMemoryMaxBytes(long param0) {
      long _oldVal = this._NonHeapMemoryMaxBytes;
      this._NonHeapMemoryMaxBytes = param0;
      this._postSet(6, _oldVal, param0);
   }

   public long getNonHeapMemoryMaxBytes() {
      return this._NonHeapMemoryMaxBytes;
   }

   public boolean isNonHeapMemoryMaxBytesInherited() {
      return false;
   }

   public boolean isNonHeapMemoryMaxBytesSet() {
      return this._isSet(6);
   }

   public void setNonHeapMemoryInitBytes(long param0) {
      long _oldVal = this._NonHeapMemoryInitBytes;
      this._NonHeapMemoryInitBytes = param0;
      this._postSet(7, _oldVal, param0);
   }

   public long getNonHeapMemoryInitBytes() {
      return this._NonHeapMemoryInitBytes;
   }

   public boolean isNonHeapMemoryInitBytesInherited() {
      return false;
   }

   public boolean isNonHeapMemoryInitBytesSet() {
      return this._isSet(7);
   }

   public void setNonHeapMemoryCommittedBytes(long param0) {
      long _oldVal = this._NonHeapMemoryCommittedBytes;
      this._NonHeapMemoryCommittedBytes = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getNonHeapMemoryCommittedBytes() {
      return this._NonHeapMemoryCommittedBytes;
   }

   public boolean isNonHeapMemoryCommittedBytesInherited() {
      return false;
   }

   public boolean isNonHeapMemoryCommittedBytesSet() {
      return this._isSet(8);
   }

   public void setThreadCount(int param0) {
      int _oldVal = this._ThreadCount;
      this._ThreadCount = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getThreadCount() {
      return this._ThreadCount;
   }

   public boolean isThreadCountInherited() {
      return false;
   }

   public boolean isThreadCountSet() {
      return this._isSet(9);
   }

   public void setPeakThreadCount(int param0) {
      int _oldVal = this._PeakThreadCount;
      this._PeakThreadCount = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getPeakThreadCount() {
      return this._PeakThreadCount;
   }

   public boolean isPeakThreadCountInherited() {
      return false;
   }

   public boolean isPeakThreadCountSet() {
      return this._isSet(10);
   }

   public void setTotalStartedThreadCount(long param0) {
      long _oldVal = this._TotalStartedThreadCount;
      this._TotalStartedThreadCount = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getTotalStartedThreadCount() {
      return this._TotalStartedThreadCount;
   }

   public boolean isTotalStartedThreadCountInherited() {
      return false;
   }

   public boolean isTotalStartedThreadCountSet() {
      return this._isSet(11);
   }

   public void setDaemonThreadCount(int param0) {
      int _oldVal = this._DaemonThreadCount;
      this._DaemonThreadCount = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getDaemonThreadCount() {
      return this._DaemonThreadCount;
   }

   public boolean isDaemonThreadCountInherited() {
      return false;
   }

   public boolean isDaemonThreadCountSet() {
      return this._isSet(12);
   }

   public void setThreadContentionMonitoringSupported(boolean param0) {
      boolean _oldVal = this._ThreadContentionMonitoringSupported;
      this._ThreadContentionMonitoringSupported = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isThreadContentionMonitoringSupported() {
      return this._ThreadContentionMonitoringSupported;
   }

   public boolean isThreadContentionMonitoringSupportedInherited() {
      return false;
   }

   public boolean isThreadContentionMonitoringSupportedSet() {
      return this._isSet(13);
   }

   public void setThreadContentionMonitoringEnabled(boolean param0) {
      boolean _oldVal = this._ThreadContentionMonitoringEnabled;
      this._ThreadContentionMonitoringEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isThreadContentionMonitoringEnabled() {
      return this._ThreadContentionMonitoringEnabled;
   }

   public boolean isThreadContentionMonitoringEnabledInherited() {
      return false;
   }

   public boolean isThreadContentionMonitoringEnabledSet() {
      return this._isSet(14);
   }

   public void setCurrentThreadCpuTime(long param0) {
      long _oldVal = this._CurrentThreadCpuTime;
      this._CurrentThreadCpuTime = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getCurrentThreadCpuTime() {
      return this._CurrentThreadCpuTime;
   }

   public boolean isCurrentThreadCpuTimeInherited() {
      return false;
   }

   public boolean isCurrentThreadCpuTimeSet() {
      return this._isSet(15);
   }

   public void setCurrentThreadUserTime(long param0) {
      long _oldVal = this._CurrentThreadUserTime;
      this._CurrentThreadUserTime = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getCurrentThreadUserTime() {
      return this._CurrentThreadUserTime;
   }

   public boolean isCurrentThreadUserTimeInherited() {
      return false;
   }

   public boolean isCurrentThreadUserTimeSet() {
      return this._isSet(16);
   }

   public void setThreadCpuTimeSupported(boolean param0) {
      boolean _oldVal = this._ThreadCpuTimeSupported;
      this._ThreadCpuTimeSupported = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isThreadCpuTimeSupported() {
      return this._ThreadCpuTimeSupported;
   }

   public boolean isThreadCpuTimeSupportedInherited() {
      return false;
   }

   public boolean isThreadCpuTimeSupportedSet() {
      return this._isSet(17);
   }

   public void setCurrentThreadCpuTimeSupported(boolean param0) {
      boolean _oldVal = this._CurrentThreadCpuTimeSupported;
      this._CurrentThreadCpuTimeSupported = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isCurrentThreadCpuTimeSupported() {
      return this._CurrentThreadCpuTimeSupported;
   }

   public boolean isCurrentThreadCpuTimeSupportedInherited() {
      return false;
   }

   public boolean isCurrentThreadCpuTimeSupportedSet() {
      return this._isSet(18);
   }

   public void setThreadCpuTimeEnabled(boolean param0) {
      boolean _oldVal = this._ThreadCpuTimeEnabled;
      this._ThreadCpuTimeEnabled = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isThreadCpuTimeEnabled() {
      return this._ThreadCpuTimeEnabled;
   }

   public boolean isThreadCpuTimeEnabledInherited() {
      return false;
   }

   public boolean isThreadCpuTimeEnabledSet() {
      return this._isSet(19);
   }

   public void setRunningJVMName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunningJVMName;
      this._RunningJVMName = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getRunningJVMName() {
      return this._RunningJVMName;
   }

   public boolean isRunningJVMNameInherited() {
      return false;
   }

   public boolean isRunningJVMNameSet() {
      return this._isSet(20);
   }

   public void setManagementSpecVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ManagementSpecVersion;
      this._ManagementSpecVersion = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getManagementSpecVersion() {
      return this._ManagementSpecVersion;
   }

   public boolean isManagementSpecVersionInherited() {
      return false;
   }

   public boolean isManagementSpecVersionSet() {
      return this._isSet(21);
   }

   public void setVmName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VmName;
      this._VmName = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getVmName() {
      return this._VmName;
   }

   public boolean isVmNameInherited() {
      return false;
   }

   public boolean isVmNameSet() {
      return this._isSet(22);
   }

   public void setVmVendor(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VmVendor;
      this._VmVendor = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getVmVendor() {
      return this._VmVendor;
   }

   public boolean isVmVendorInherited() {
      return false;
   }

   public boolean isVmVendorSet() {
      return this._isSet(23);
   }

   public void setVmVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._VmVersion;
      this._VmVersion = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getVmVersion() {
      return this._VmVersion;
   }

   public boolean isVmVersionInherited() {
      return false;
   }

   public boolean isVmVersionSet() {
      return this._isSet(24);
   }

   public void setSpecName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SpecName;
      this._SpecName = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getSpecName() {
      return this._SpecName;
   }

   public boolean isSpecNameInherited() {
      return false;
   }

   public boolean isSpecNameSet() {
      return this._isSet(25);
   }

   public void setSpecVendor(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SpecVendor;
      this._SpecVendor = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getSpecVendor() {
      return this._SpecVendor;
   }

   public boolean isSpecVendorInherited() {
      return false;
   }

   public boolean isSpecVendorSet() {
      return this._isSet(26);
   }

   public void setSpecVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SpecVersion;
      this._SpecVersion = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getSpecVersion() {
      return this._SpecVersion;
   }

   public boolean isSpecVersionInherited() {
      return false;
   }

   public boolean isSpecVersionSet() {
      return this._isSet(27);
   }

   public void setClassPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassPath;
      this._ClassPath = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getClassPath() {
      return this._ClassPath;
   }

   public boolean isClassPathInherited() {
      return false;
   }

   public boolean isClassPathSet() {
      return this._isSet(28);
   }

   public void setLibraryPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LibraryPath;
      this._LibraryPath = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getLibraryPath() {
      return this._LibraryPath;
   }

   public boolean isLibraryPathInherited() {
      return false;
   }

   public boolean isLibraryPathSet() {
      return this._isSet(29);
   }

   public void setBootClassPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BootClassPath;
      this._BootClassPath = param0;
      this._postSet(30, _oldVal, param0);
   }

   public String getBootClassPath() {
      return this._BootClassPath;
   }

   public boolean isBootClassPathInherited() {
      return false;
   }

   public boolean isBootClassPathSet() {
      return this._isSet(30);
   }

   public void setUptime(long param0) {
      long _oldVal = this._Uptime;
      this._Uptime = param0;
      this._postSet(31, _oldVal, param0);
   }

   public long getUptime() {
      return this._Uptime;
   }

   public boolean isUptimeInherited() {
      return false;
   }

   public boolean isUptimeSet() {
      return this._isSet(31);
   }

   public void setStartTime(long param0) {
      long _oldVal = this._StartTime;
      this._StartTime = param0;
      this._postSet(32, _oldVal, param0);
   }

   public long getStartTime() {
      return this._StartTime;
   }

   public boolean isStartTimeInherited() {
      return false;
   }

   public boolean isStartTimeSet() {
      return this._isSet(32);
   }

   public void setBootClassPathSupported(boolean param0) {
      boolean _oldVal = this._BootClassPathSupported;
      this._BootClassPathSupported = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isBootClassPathSupported() {
      return this._BootClassPathSupported;
   }

   public boolean isBootClassPathSupportedInherited() {
      return false;
   }

   public boolean isBootClassPathSupportedSet() {
      return this._isSet(33);
   }

   public void setOSName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OSName;
      this._OSName = param0;
      this._postSet(34, _oldVal, param0);
   }

   public String getOSName() {
      return this._OSName;
   }

   public boolean isOSNameInherited() {
      return false;
   }

   public boolean isOSNameSet() {
      return this._isSet(34);
   }

   public void setOSVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OSVersion;
      this._OSVersion = param0;
      this._postSet(35, _oldVal, param0);
   }

   public String getOSVersion() {
      return this._OSVersion;
   }

   public boolean isOSVersionInherited() {
      return false;
   }

   public boolean isOSVersionSet() {
      return this._isSet(35);
   }

   public void setOSArch(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OSArch;
      this._OSArch = param0;
      this._postSet(36, _oldVal, param0);
   }

   public String getOSArch() {
      return this._OSArch;
   }

   public boolean isOSArchInherited() {
      return false;
   }

   public boolean isOSArchSet() {
      return this._isSet(36);
   }

   public void setOSAvailableProcessors(int param0) {
      int _oldVal = this._OSAvailableProcessors;
      this._OSAvailableProcessors = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getOSAvailableProcessors() {
      return this._OSAvailableProcessors;
   }

   public boolean isOSAvailableProcessorsInherited() {
      return false;
   }

   public boolean isOSAvailableProcessorsSet() {
      return this._isSet(37);
   }

   public void setLoadedClassCount(int param0) {
      int _oldVal = this._LoadedClassCount;
      this._LoadedClassCount = param0;
      this._postSet(38, _oldVal, param0);
   }

   public int getLoadedClassCount() {
      return this._LoadedClassCount;
   }

   public boolean isLoadedClassCountInherited() {
      return false;
   }

   public boolean isLoadedClassCountSet() {
      return this._isSet(38);
   }

   public void setTotalLoadedClassCount(long param0) {
      long _oldVal = this._TotalLoadedClassCount;
      this._TotalLoadedClassCount = param0;
      this._postSet(39, _oldVal, param0);
   }

   public long getTotalLoadedClassCount() {
      return this._TotalLoadedClassCount;
   }

   public boolean isTotalLoadedClassCountInherited() {
      return false;
   }

   public boolean isTotalLoadedClassCountSet() {
      return this._isSet(39);
   }

   public void setUnloadedClassCount(long param0) {
      long _oldVal = this._UnloadedClassCount;
      this._UnloadedClassCount = param0;
      this._postSet(40, _oldVal, param0);
   }

   public long getUnloadedClassCount() {
      return this._UnloadedClassCount;
   }

   public boolean isUnloadedClassCountInherited() {
      return false;
   }

   public boolean isUnloadedClassCountSet() {
      return this._isSet(40);
   }

   public String getThreadDump() {
      return this._ThreadDump;
   }

   public boolean isThreadDumpInherited() {
      return false;
   }

   public boolean isThreadDumpSet() {
      return this._isSet(41);
   }

   public void setThreadDump(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ThreadDump;
      this._ThreadDump = param0;
      this._postSet(41, _oldVal, param0);
   }

   public String getThreadRequestExecutionDetails() {
      return this._ThreadRequestExecutionDetails;
   }

   public boolean isThreadRequestExecutionDetailsInherited() {
      return false;
   }

   public boolean isThreadRequestExecutionDetailsSet() {
      return this._isSet(42);
   }

   public void setThreadRequestExecutionDetails(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ThreadRequestExecutionDetails;
      this._ThreadRequestExecutionDetails = param0;
      this._postSet(42, _oldVal, param0);
   }

   public long getPartitionCpuTimeNanos() {
      return this._PartitionCpuTimeNanos;
   }

   public boolean isPartitionCpuTimeNanosInherited() {
      return false;
   }

   public boolean isPartitionCpuTimeNanosSet() {
      return this._isSet(43);
   }

   public void setPartitionCpuTimeNanos(long param0) {
      long _oldVal = this._PartitionCpuTimeNanos;
      this._PartitionCpuTimeNanos = param0;
      this._postSet(43, _oldVal, param0);
   }

   public long getPartitionAllocatedMemory() {
      return this._PartitionAllocatedMemory;
   }

   public boolean isPartitionAllocatedMemoryInherited() {
      return false;
   }

   public boolean isPartitionAllocatedMemorySet() {
      return this._isSet(44);
   }

   public void setPartitionAllocatedMemory(long param0) {
      long _oldVal = this._PartitionAllocatedMemory;
      this._PartitionAllocatedMemory = param0;
      this._postSet(44, _oldVal, param0);
   }

   public long getPartitionThreadCount() {
      return this._PartitionThreadCount;
   }

   public boolean isPartitionThreadCountInherited() {
      return false;
   }

   public boolean isPartitionThreadCountSet() {
      return this._isSet(45);
   }

   public void setPartitionThreadCount(long param0) {
      long _oldVal = this._PartitionThreadCount;
      this._PartitionThreadCount = param0;
      this._postSet(45, _oldVal, param0);
   }

   public long getPartitionTotalOpenedSocketCount() {
      return this._PartitionTotalOpenedSocketCount;
   }

   public boolean isPartitionTotalOpenedSocketCountInherited() {
      return false;
   }

   public boolean isPartitionTotalOpenedSocketCountSet() {
      return this._isSet(46);
   }

   public void setPartitionTotalOpenedSocketCount(long param0) {
      long _oldVal = this._PartitionTotalOpenedSocketCount;
      this._PartitionTotalOpenedSocketCount = param0;
      this._postSet(46, _oldVal, param0);
   }

   public long getPartitionCurrentOpenSocketCount() {
      return this._PartitionCurrentOpenSocketCount;
   }

   public boolean isPartitionCurrentOpenSocketCountInherited() {
      return false;
   }

   public boolean isPartitionCurrentOpenSocketCountSet() {
      return this._isSet(47);
   }

   public void setPartitionCurrentOpenSocketCount(long param0) {
      long _oldVal = this._PartitionCurrentOpenSocketCount;
      this._PartitionCurrentOpenSocketCount = param0;
      this._postSet(47, _oldVal, param0);
   }

   public long getPartitionNetworkBytesRead() {
      return this._PartitionNetworkBytesRead;
   }

   public boolean isPartitionNetworkBytesReadInherited() {
      return false;
   }

   public boolean isPartitionNetworkBytesReadSet() {
      return this._isSet(48);
   }

   public void setPartitionNetworkBytesRead(long param0) {
      long _oldVal = this._PartitionNetworkBytesRead;
      this._PartitionNetworkBytesRead = param0;
      this._postSet(48, _oldVal, param0);
   }

   public long getPartitionNetworkBytesWritten() {
      return this._PartitionNetworkBytesWritten;
   }

   public boolean isPartitionNetworkBytesWrittenInherited() {
      return false;
   }

   public boolean isPartitionNetworkBytesWrittenSet() {
      return this._isSet(49);
   }

   public void setPartitionNetworkBytesWritten(long param0) {
      long _oldVal = this._PartitionNetworkBytesWritten;
      this._PartitionNetworkBytesWritten = param0;
      this._postSet(49, _oldVal, param0);
   }

   public long getPartitionTotalOpenedFileCount() {
      return this._PartitionTotalOpenedFileCount;
   }

   public boolean isPartitionTotalOpenedFileCountInherited() {
      return false;
   }

   public boolean isPartitionTotalOpenedFileCountSet() {
      return this._isSet(50);
   }

   public void setPartitionTotalOpenedFileCount(long param0) {
      long _oldVal = this._PartitionTotalOpenedFileCount;
      this._PartitionTotalOpenedFileCount = param0;
      this._postSet(50, _oldVal, param0);
   }

   public long getPartitionCurrentOpenFileCount() {
      return this._PartitionCurrentOpenFileCount;
   }

   public boolean isPartitionCurrentOpenFileCountInherited() {
      return false;
   }

   public boolean isPartitionCurrentOpenFileCountSet() {
      return this._isSet(51);
   }

   public void setPartitionCurrentOpenFileCount(long param0) {
      long _oldVal = this._PartitionCurrentOpenFileCount;
      this._PartitionCurrentOpenFileCount = param0;
      this._postSet(51, _oldVal, param0);
   }

   public long getPartitionFileBytesRead() {
      return this._PartitionFileBytesRead;
   }

   public boolean isPartitionFileBytesReadInherited() {
      return false;
   }

   public boolean isPartitionFileBytesReadSet() {
      return this._isSet(52);
   }

   public void setPartitionFileBytesRead(long param0) {
      long _oldVal = this._PartitionFileBytesRead;
      this._PartitionFileBytesRead = param0;
      this._postSet(52, _oldVal, param0);
   }

   public long getPartitionFileBytesWritten() {
      return this._PartitionFileBytesWritten;
   }

   public boolean isPartitionFileBytesWrittenInherited() {
      return false;
   }

   public boolean isPartitionFileBytesWrittenSet() {
      return this._isSet(53);
   }

   public void setPartitionFileBytesWritten(long param0) {
      long _oldVal = this._PartitionFileBytesWritten;
      this._PartitionFileBytesWritten = param0;
      this._postSet(53, _oldVal, param0);
   }

   public String getRetainedHeapHistoricalData() {
      return this._RetainedHeapHistoricalData;
   }

   public boolean isRetainedHeapHistoricalDataInherited() {
      return false;
   }

   public boolean isRetainedHeapHistoricalDataSet() {
      return this._isSet(54);
   }

   public void setRetainedHeapHistoricalData(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RetainedHeapHistoricalData;
      this._RetainedHeapHistoricalData = param0;
      this._postSet(54, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 30;
      }

      try {
         switch (idx) {
            case 30:
               this._BootClassPath = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._ClassPath = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._CurrentThreadCpuTime = 0L;
               if (initOne) {
                  break;
               }
            case 16:
               this._CurrentThreadUserTime = 0L;
               if (initOne) {
                  break;
               }
            case 12:
               this._DaemonThreadCount = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._HeapMemoryCommittedBytes = 0L;
               if (initOne) {
                  break;
               }
            case 3:
               this._HeapMemoryInitBytes = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._HeapMemoryMaxBytes = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._HeapMemoryUsedBytes = 0L;
               if (initOne) {
                  break;
               }
            case 29:
               this._LibraryPath = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._LoadedClassCount = 0;
               if (initOne) {
                  break;
               }
            case 21:
               this._ManagementSpecVersion = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._NonHeapMemoryCommittedBytes = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._NonHeapMemoryInitBytes = 0L;
               if (initOne) {
                  break;
               }
            case 6:
               this._NonHeapMemoryMaxBytes = 0L;
               if (initOne) {
                  break;
               }
            case 5:
               this._NonHeapMemoryUsedBytes = 0L;
               if (initOne) {
                  break;
               }
            case 36:
               this._OSArch = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._OSAvailableProcessors = 0;
               if (initOne) {
                  break;
               }
            case 34:
               this._OSName = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._OSVersion = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ObjectPendingFinalizationCount = 0;
               if (initOne) {
                  break;
               }
            case 44:
               this._PartitionAllocatedMemory = 0L;
               if (initOne) {
                  break;
               }
            case 43:
               this._PartitionCpuTimeNanos = 0L;
               if (initOne) {
                  break;
               }
            case 51:
               this._PartitionCurrentOpenFileCount = 0L;
               if (initOne) {
                  break;
               }
            case 47:
               this._PartitionCurrentOpenSocketCount = 0L;
               if (initOne) {
                  break;
               }
            case 52:
               this._PartitionFileBytesRead = 0L;
               if (initOne) {
                  break;
               }
            case 53:
               this._PartitionFileBytesWritten = 0L;
               if (initOne) {
                  break;
               }
            case 48:
               this._PartitionNetworkBytesRead = 0L;
               if (initOne) {
                  break;
               }
            case 49:
               this._PartitionNetworkBytesWritten = 0L;
               if (initOne) {
                  break;
               }
            case 45:
               this._PartitionThreadCount = 0L;
               if (initOne) {
                  break;
               }
            case 50:
               this._PartitionTotalOpenedFileCount = 0L;
               if (initOne) {
                  break;
               }
            case 46:
               this._PartitionTotalOpenedSocketCount = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._PeakThreadCount = 0;
               if (initOne) {
                  break;
               }
            case 54:
               this._RetainedHeapHistoricalData = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._RunningJVMName = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._SpecName = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._SpecVendor = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._SpecVersion = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._StartTime = 0L;
               if (initOne) {
                  break;
               }
            case 9:
               this._ThreadCount = 0;
               if (initOne) {
                  break;
               }
            case 41:
               this._ThreadDump = null;
               if (initOne) {
                  break;
               }
            case 42:
               this._ThreadRequestExecutionDetails = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._TotalLoadedClassCount = 0L;
               if (initOne) {
                  break;
               }
            case 11:
               this._TotalStartedThreadCount = 0L;
               if (initOne) {
                  break;
               }
            case 40:
               this._UnloadedClassCount = 0L;
               if (initOne) {
                  break;
               }
            case 31:
               this._Uptime = 0L;
               if (initOne) {
                  break;
               }
            case 22:
               this._VmName = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._VmVendor = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._VmVersion = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._BootClassPathSupported = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._CurrentThreadCpuTimeSupported = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._ThreadContentionMonitoringEnabled = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._ThreadContentionMonitoringSupported = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._ThreadCpuTimeEnabled = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._ThreadCpuTimeSupported = false;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("uptime")) {
                  return 31;
               }
               break;
            case 7:
               if (s.equals("os-arch")) {
                  return 36;
               }

               if (s.equals("os-name")) {
                  return 34;
               }

               if (s.equals("vm-name")) {
                  return 22;
               }
            case 8:
            case 13:
            case 14:
            case 30:
            case 34:
            case 37:
            default:
               break;
            case 9:
               if (s.equals("spec-name")) {
                  return 25;
               }

               if (s.equals("vm-vendor")) {
                  return 23;
               }
               break;
            case 10:
               if (s.equals("class-path")) {
                  return 28;
               }

               if (s.equals("os-version")) {
                  return 35;
               }

               if (s.equals("start-time")) {
                  return 32;
               }

               if (s.equals("vm-version")) {
                  return 24;
               }
               break;
            case 11:
               if (s.equals("spec-vendor")) {
                  return 26;
               }

               if (s.equals("thread-dump")) {
                  return 41;
               }
               break;
            case 12:
               if (s.equals("library-path")) {
                  return 29;
               }

               if (s.equals("spec-version")) {
                  return 27;
               }

               if (s.equals("thread-count")) {
                  return 9;
               }
               break;
            case 15:
               if (s.equals("boot-class-path")) {
                  return 30;
               }
               break;
            case 16:
               if (s.equals("running-jvm-name")) {
                  return 20;
               }
               break;
            case 17:
               if (s.equals("peak-thread-count")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("loaded-class-count")) {
                  return 38;
               }
               break;
            case 19:
               if (s.equals("daemon-thread-count")) {
                  return 12;
               }
               break;
            case 20:
               if (s.equals("unloaded-class-count")) {
                  return 40;
               }
               break;
            case 21:
               if (s.equals("heap-memory-max-bytes")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("heap-memory-init-bytes")) {
                  return 3;
               }

               if (s.equals("heap-memory-used-bytes")) {
                  return 1;
               }

               if (s.equals("partition-thread-count")) {
                  return 45;
               }
               break;
            case 23:
               if (s.equals("current-thread-cpu-time")) {
                  return 15;
               }

               if (s.equals("management-spec-version")) {
                  return 21;
               }

               if (s.equals("os-available-processors")) {
                  return 37;
               }

               if (s.equals("thread-cpu-time-enabled")) {
                  return 19;
               }
               break;
            case 24:
               if (s.equals("current-thread-user-time")) {
                  return 16;
               }

               if (s.equals("partition-cpu-time-nanos")) {
                  return 43;
               }

               if (s.equals("total-loaded-class-count")) {
                  return 39;
               }
               break;
            case 25:
               if (s.equals("non-heap-memory-max-bytes")) {
                  return 6;
               }

               if (s.equals("partition-file-bytes-read")) {
                  return 52;
               }

               if (s.equals("boot-class-path-supported")) {
                  return 33;
               }

               if (s.equals("thread-cpu-time-supported")) {
                  return 17;
               }
               break;
            case 26:
               if (s.equals("non-heap-memory-init-bytes")) {
                  return 7;
               }

               if (s.equals("non-heap-memory-used-bytes")) {
                  return 5;
               }

               if (s.equals("partition-allocated-memory")) {
                  return 44;
               }

               if (s.equals("total-started-thread-count")) {
                  return 11;
               }
               break;
            case 27:
               if (s.equals("heap-memory-committed-bytes")) {
                  return 4;
               }
               break;
            case 28:
               if (s.equals("partition-file-bytes-written")) {
                  return 53;
               }

               if (s.equals("partition-network-bytes-read")) {
                  return 48;
               }
               break;
            case 29:
               if (s.equals("retained-heap-historical-data")) {
                  return 54;
               }
               break;
            case 31:
               if (s.equals("non-heap-memory-committed-bytes")) {
                  return 8;
               }

               if (s.equals("partition-network-bytes-written")) {
                  return 49;
               }
               break;
            case 32:
               if (s.equals("thread-request-execution-details")) {
                  return 42;
               }
               break;
            case 33:
               if (s.equals("object-pending-finalization-count")) {
                  return 0;
               }

               if (s.equals("partition-current-open-file-count")) {
                  return 51;
               }

               if (s.equals("partition-total-opened-file-count")) {
                  return 50;
               }

               if (s.equals("current-thread-cpu-time-supported")) {
                  return 18;
               }
               break;
            case 35:
               if (s.equals("partition-current-open-socket-count")) {
                  return 47;
               }

               if (s.equals("partition-total-opened-socket-count")) {
                  return 46;
               }
               break;
            case 36:
               if (s.equals("thread-contention-monitoring-enabled")) {
                  return 14;
               }
               break;
            case 38:
               if (s.equals("thread-contention-monitoring-supported")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "jvm-runtime";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "object-pending-finalization-count";
            case 1:
               return "heap-memory-used-bytes";
            case 2:
               return "heap-memory-max-bytes";
            case 3:
               return "heap-memory-init-bytes";
            case 4:
               return "heap-memory-committed-bytes";
            case 5:
               return "non-heap-memory-used-bytes";
            case 6:
               return "non-heap-memory-max-bytes";
            case 7:
               return "non-heap-memory-init-bytes";
            case 8:
               return "non-heap-memory-committed-bytes";
            case 9:
               return "thread-count";
            case 10:
               return "peak-thread-count";
            case 11:
               return "total-started-thread-count";
            case 12:
               return "daemon-thread-count";
            case 13:
               return "thread-contention-monitoring-supported";
            case 14:
               return "thread-contention-monitoring-enabled";
            case 15:
               return "current-thread-cpu-time";
            case 16:
               return "current-thread-user-time";
            case 17:
               return "thread-cpu-time-supported";
            case 18:
               return "current-thread-cpu-time-supported";
            case 19:
               return "thread-cpu-time-enabled";
            case 20:
               return "running-jvm-name";
            case 21:
               return "management-spec-version";
            case 22:
               return "vm-name";
            case 23:
               return "vm-vendor";
            case 24:
               return "vm-version";
            case 25:
               return "spec-name";
            case 26:
               return "spec-vendor";
            case 27:
               return "spec-version";
            case 28:
               return "class-path";
            case 29:
               return "library-path";
            case 30:
               return "boot-class-path";
            case 31:
               return "uptime";
            case 32:
               return "start-time";
            case 33:
               return "boot-class-path-supported";
            case 34:
               return "os-name";
            case 35:
               return "os-version";
            case 36:
               return "os-arch";
            case 37:
               return "os-available-processors";
            case 38:
               return "loaded-class-count";
            case 39:
               return "total-loaded-class-count";
            case 40:
               return "unloaded-class-count";
            case 41:
               return "thread-dump";
            case 42:
               return "thread-request-execution-details";
            case 43:
               return "partition-cpu-time-nanos";
            case 44:
               return "partition-allocated-memory";
            case 45:
               return "partition-thread-count";
            case 46:
               return "partition-total-opened-socket-count";
            case 47:
               return "partition-current-open-socket-count";
            case 48:
               return "partition-network-bytes-read";
            case 49:
               return "partition-network-bytes-written";
            case 50:
               return "partition-total-opened-file-count";
            case 51:
               return "partition-current-open-file-count";
            case 52:
               return "partition-file-bytes-read";
            case 53:
               return "partition-file-bytes-written";
            case 54:
               return "retained-heap-historical-data";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JVMRuntimeBeanImpl bean;

      protected Helper(JVMRuntimeBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ObjectPendingFinalizationCount";
            case 1:
               return "HeapMemoryUsedBytes";
            case 2:
               return "HeapMemoryMaxBytes";
            case 3:
               return "HeapMemoryInitBytes";
            case 4:
               return "HeapMemoryCommittedBytes";
            case 5:
               return "NonHeapMemoryUsedBytes";
            case 6:
               return "NonHeapMemoryMaxBytes";
            case 7:
               return "NonHeapMemoryInitBytes";
            case 8:
               return "NonHeapMemoryCommittedBytes";
            case 9:
               return "ThreadCount";
            case 10:
               return "PeakThreadCount";
            case 11:
               return "TotalStartedThreadCount";
            case 12:
               return "DaemonThreadCount";
            case 13:
               return "ThreadContentionMonitoringSupported";
            case 14:
               return "ThreadContentionMonitoringEnabled";
            case 15:
               return "CurrentThreadCpuTime";
            case 16:
               return "CurrentThreadUserTime";
            case 17:
               return "ThreadCpuTimeSupported";
            case 18:
               return "CurrentThreadCpuTimeSupported";
            case 19:
               return "ThreadCpuTimeEnabled";
            case 20:
               return "RunningJVMName";
            case 21:
               return "ManagementSpecVersion";
            case 22:
               return "VmName";
            case 23:
               return "VmVendor";
            case 24:
               return "VmVersion";
            case 25:
               return "SpecName";
            case 26:
               return "SpecVendor";
            case 27:
               return "SpecVersion";
            case 28:
               return "ClassPath";
            case 29:
               return "LibraryPath";
            case 30:
               return "BootClassPath";
            case 31:
               return "Uptime";
            case 32:
               return "StartTime";
            case 33:
               return "BootClassPathSupported";
            case 34:
               return "OSName";
            case 35:
               return "OSVersion";
            case 36:
               return "OSArch";
            case 37:
               return "OSAvailableProcessors";
            case 38:
               return "LoadedClassCount";
            case 39:
               return "TotalLoadedClassCount";
            case 40:
               return "UnloadedClassCount";
            case 41:
               return "ThreadDump";
            case 42:
               return "ThreadRequestExecutionDetails";
            case 43:
               return "PartitionCpuTimeNanos";
            case 44:
               return "PartitionAllocatedMemory";
            case 45:
               return "PartitionThreadCount";
            case 46:
               return "PartitionTotalOpenedSocketCount";
            case 47:
               return "PartitionCurrentOpenSocketCount";
            case 48:
               return "PartitionNetworkBytesRead";
            case 49:
               return "PartitionNetworkBytesWritten";
            case 50:
               return "PartitionTotalOpenedFileCount";
            case 51:
               return "PartitionCurrentOpenFileCount";
            case 52:
               return "PartitionFileBytesRead";
            case 53:
               return "PartitionFileBytesWritten";
            case 54:
               return "RetainedHeapHistoricalData";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BootClassPath")) {
            return 30;
         } else if (propName.equals("ClassPath")) {
            return 28;
         } else if (propName.equals("CurrentThreadCpuTime")) {
            return 15;
         } else if (propName.equals("CurrentThreadUserTime")) {
            return 16;
         } else if (propName.equals("DaemonThreadCount")) {
            return 12;
         } else if (propName.equals("HeapMemoryCommittedBytes")) {
            return 4;
         } else if (propName.equals("HeapMemoryInitBytes")) {
            return 3;
         } else if (propName.equals("HeapMemoryMaxBytes")) {
            return 2;
         } else if (propName.equals("HeapMemoryUsedBytes")) {
            return 1;
         } else if (propName.equals("LibraryPath")) {
            return 29;
         } else if (propName.equals("LoadedClassCount")) {
            return 38;
         } else if (propName.equals("ManagementSpecVersion")) {
            return 21;
         } else if (propName.equals("NonHeapMemoryCommittedBytes")) {
            return 8;
         } else if (propName.equals("NonHeapMemoryInitBytes")) {
            return 7;
         } else if (propName.equals("NonHeapMemoryMaxBytes")) {
            return 6;
         } else if (propName.equals("NonHeapMemoryUsedBytes")) {
            return 5;
         } else if (propName.equals("OSArch")) {
            return 36;
         } else if (propName.equals("OSAvailableProcessors")) {
            return 37;
         } else if (propName.equals("OSName")) {
            return 34;
         } else if (propName.equals("OSVersion")) {
            return 35;
         } else if (propName.equals("ObjectPendingFinalizationCount")) {
            return 0;
         } else if (propName.equals("PartitionAllocatedMemory")) {
            return 44;
         } else if (propName.equals("PartitionCpuTimeNanos")) {
            return 43;
         } else if (propName.equals("PartitionCurrentOpenFileCount")) {
            return 51;
         } else if (propName.equals("PartitionCurrentOpenSocketCount")) {
            return 47;
         } else if (propName.equals("PartitionFileBytesRead")) {
            return 52;
         } else if (propName.equals("PartitionFileBytesWritten")) {
            return 53;
         } else if (propName.equals("PartitionNetworkBytesRead")) {
            return 48;
         } else if (propName.equals("PartitionNetworkBytesWritten")) {
            return 49;
         } else if (propName.equals("PartitionThreadCount")) {
            return 45;
         } else if (propName.equals("PartitionTotalOpenedFileCount")) {
            return 50;
         } else if (propName.equals("PartitionTotalOpenedSocketCount")) {
            return 46;
         } else if (propName.equals("PeakThreadCount")) {
            return 10;
         } else if (propName.equals("RetainedHeapHistoricalData")) {
            return 54;
         } else if (propName.equals("RunningJVMName")) {
            return 20;
         } else if (propName.equals("SpecName")) {
            return 25;
         } else if (propName.equals("SpecVendor")) {
            return 26;
         } else if (propName.equals("SpecVersion")) {
            return 27;
         } else if (propName.equals("StartTime")) {
            return 32;
         } else if (propName.equals("ThreadCount")) {
            return 9;
         } else if (propName.equals("ThreadDump")) {
            return 41;
         } else if (propName.equals("ThreadRequestExecutionDetails")) {
            return 42;
         } else if (propName.equals("TotalLoadedClassCount")) {
            return 39;
         } else if (propName.equals("TotalStartedThreadCount")) {
            return 11;
         } else if (propName.equals("UnloadedClassCount")) {
            return 40;
         } else if (propName.equals("Uptime")) {
            return 31;
         } else if (propName.equals("VmName")) {
            return 22;
         } else if (propName.equals("VmVendor")) {
            return 23;
         } else if (propName.equals("VmVersion")) {
            return 24;
         } else if (propName.equals("BootClassPathSupported")) {
            return 33;
         } else if (propName.equals("CurrentThreadCpuTimeSupported")) {
            return 18;
         } else if (propName.equals("ThreadContentionMonitoringEnabled")) {
            return 14;
         } else if (propName.equals("ThreadContentionMonitoringSupported")) {
            return 13;
         } else if (propName.equals("ThreadCpuTimeEnabled")) {
            return 19;
         } else {
            return propName.equals("ThreadCpuTimeSupported") ? 17 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isBootClassPathSet()) {
               buf.append("BootClassPath");
               buf.append(String.valueOf(this.bean.getBootClassPath()));
            }

            if (this.bean.isClassPathSet()) {
               buf.append("ClassPath");
               buf.append(String.valueOf(this.bean.getClassPath()));
            }

            if (this.bean.isCurrentThreadCpuTimeSet()) {
               buf.append("CurrentThreadCpuTime");
               buf.append(String.valueOf(this.bean.getCurrentThreadCpuTime()));
            }

            if (this.bean.isCurrentThreadUserTimeSet()) {
               buf.append("CurrentThreadUserTime");
               buf.append(String.valueOf(this.bean.getCurrentThreadUserTime()));
            }

            if (this.bean.isDaemonThreadCountSet()) {
               buf.append("DaemonThreadCount");
               buf.append(String.valueOf(this.bean.getDaemonThreadCount()));
            }

            if (this.bean.isHeapMemoryCommittedBytesSet()) {
               buf.append("HeapMemoryCommittedBytes");
               buf.append(String.valueOf(this.bean.getHeapMemoryCommittedBytes()));
            }

            if (this.bean.isHeapMemoryInitBytesSet()) {
               buf.append("HeapMemoryInitBytes");
               buf.append(String.valueOf(this.bean.getHeapMemoryInitBytes()));
            }

            if (this.bean.isHeapMemoryMaxBytesSet()) {
               buf.append("HeapMemoryMaxBytes");
               buf.append(String.valueOf(this.bean.getHeapMemoryMaxBytes()));
            }

            if (this.bean.isHeapMemoryUsedBytesSet()) {
               buf.append("HeapMemoryUsedBytes");
               buf.append(String.valueOf(this.bean.getHeapMemoryUsedBytes()));
            }

            if (this.bean.isLibraryPathSet()) {
               buf.append("LibraryPath");
               buf.append(String.valueOf(this.bean.getLibraryPath()));
            }

            if (this.bean.isLoadedClassCountSet()) {
               buf.append("LoadedClassCount");
               buf.append(String.valueOf(this.bean.getLoadedClassCount()));
            }

            if (this.bean.isManagementSpecVersionSet()) {
               buf.append("ManagementSpecVersion");
               buf.append(String.valueOf(this.bean.getManagementSpecVersion()));
            }

            if (this.bean.isNonHeapMemoryCommittedBytesSet()) {
               buf.append("NonHeapMemoryCommittedBytes");
               buf.append(String.valueOf(this.bean.getNonHeapMemoryCommittedBytes()));
            }

            if (this.bean.isNonHeapMemoryInitBytesSet()) {
               buf.append("NonHeapMemoryInitBytes");
               buf.append(String.valueOf(this.bean.getNonHeapMemoryInitBytes()));
            }

            if (this.bean.isNonHeapMemoryMaxBytesSet()) {
               buf.append("NonHeapMemoryMaxBytes");
               buf.append(String.valueOf(this.bean.getNonHeapMemoryMaxBytes()));
            }

            if (this.bean.isNonHeapMemoryUsedBytesSet()) {
               buf.append("NonHeapMemoryUsedBytes");
               buf.append(String.valueOf(this.bean.getNonHeapMemoryUsedBytes()));
            }

            if (this.bean.isOSArchSet()) {
               buf.append("OSArch");
               buf.append(String.valueOf(this.bean.getOSArch()));
            }

            if (this.bean.isOSAvailableProcessorsSet()) {
               buf.append("OSAvailableProcessors");
               buf.append(String.valueOf(this.bean.getOSAvailableProcessors()));
            }

            if (this.bean.isOSNameSet()) {
               buf.append("OSName");
               buf.append(String.valueOf(this.bean.getOSName()));
            }

            if (this.bean.isOSVersionSet()) {
               buf.append("OSVersion");
               buf.append(String.valueOf(this.bean.getOSVersion()));
            }

            if (this.bean.isObjectPendingFinalizationCountSet()) {
               buf.append("ObjectPendingFinalizationCount");
               buf.append(String.valueOf(this.bean.getObjectPendingFinalizationCount()));
            }

            if (this.bean.isPartitionAllocatedMemorySet()) {
               buf.append("PartitionAllocatedMemory");
               buf.append(String.valueOf(this.bean.getPartitionAllocatedMemory()));
            }

            if (this.bean.isPartitionCpuTimeNanosSet()) {
               buf.append("PartitionCpuTimeNanos");
               buf.append(String.valueOf(this.bean.getPartitionCpuTimeNanos()));
            }

            if (this.bean.isPartitionCurrentOpenFileCountSet()) {
               buf.append("PartitionCurrentOpenFileCount");
               buf.append(String.valueOf(this.bean.getPartitionCurrentOpenFileCount()));
            }

            if (this.bean.isPartitionCurrentOpenSocketCountSet()) {
               buf.append("PartitionCurrentOpenSocketCount");
               buf.append(String.valueOf(this.bean.getPartitionCurrentOpenSocketCount()));
            }

            if (this.bean.isPartitionFileBytesReadSet()) {
               buf.append("PartitionFileBytesRead");
               buf.append(String.valueOf(this.bean.getPartitionFileBytesRead()));
            }

            if (this.bean.isPartitionFileBytesWrittenSet()) {
               buf.append("PartitionFileBytesWritten");
               buf.append(String.valueOf(this.bean.getPartitionFileBytesWritten()));
            }

            if (this.bean.isPartitionNetworkBytesReadSet()) {
               buf.append("PartitionNetworkBytesRead");
               buf.append(String.valueOf(this.bean.getPartitionNetworkBytesRead()));
            }

            if (this.bean.isPartitionNetworkBytesWrittenSet()) {
               buf.append("PartitionNetworkBytesWritten");
               buf.append(String.valueOf(this.bean.getPartitionNetworkBytesWritten()));
            }

            if (this.bean.isPartitionThreadCountSet()) {
               buf.append("PartitionThreadCount");
               buf.append(String.valueOf(this.bean.getPartitionThreadCount()));
            }

            if (this.bean.isPartitionTotalOpenedFileCountSet()) {
               buf.append("PartitionTotalOpenedFileCount");
               buf.append(String.valueOf(this.bean.getPartitionTotalOpenedFileCount()));
            }

            if (this.bean.isPartitionTotalOpenedSocketCountSet()) {
               buf.append("PartitionTotalOpenedSocketCount");
               buf.append(String.valueOf(this.bean.getPartitionTotalOpenedSocketCount()));
            }

            if (this.bean.isPeakThreadCountSet()) {
               buf.append("PeakThreadCount");
               buf.append(String.valueOf(this.bean.getPeakThreadCount()));
            }

            if (this.bean.isRetainedHeapHistoricalDataSet()) {
               buf.append("RetainedHeapHistoricalData");
               buf.append(String.valueOf(this.bean.getRetainedHeapHistoricalData()));
            }

            if (this.bean.isRunningJVMNameSet()) {
               buf.append("RunningJVMName");
               buf.append(String.valueOf(this.bean.getRunningJVMName()));
            }

            if (this.bean.isSpecNameSet()) {
               buf.append("SpecName");
               buf.append(String.valueOf(this.bean.getSpecName()));
            }

            if (this.bean.isSpecVendorSet()) {
               buf.append("SpecVendor");
               buf.append(String.valueOf(this.bean.getSpecVendor()));
            }

            if (this.bean.isSpecVersionSet()) {
               buf.append("SpecVersion");
               buf.append(String.valueOf(this.bean.getSpecVersion()));
            }

            if (this.bean.isStartTimeSet()) {
               buf.append("StartTime");
               buf.append(String.valueOf(this.bean.getStartTime()));
            }

            if (this.bean.isThreadCountSet()) {
               buf.append("ThreadCount");
               buf.append(String.valueOf(this.bean.getThreadCount()));
            }

            if (this.bean.isThreadDumpSet()) {
               buf.append("ThreadDump");
               buf.append(String.valueOf(this.bean.getThreadDump()));
            }

            if (this.bean.isThreadRequestExecutionDetailsSet()) {
               buf.append("ThreadRequestExecutionDetails");
               buf.append(String.valueOf(this.bean.getThreadRequestExecutionDetails()));
            }

            if (this.bean.isTotalLoadedClassCountSet()) {
               buf.append("TotalLoadedClassCount");
               buf.append(String.valueOf(this.bean.getTotalLoadedClassCount()));
            }

            if (this.bean.isTotalStartedThreadCountSet()) {
               buf.append("TotalStartedThreadCount");
               buf.append(String.valueOf(this.bean.getTotalStartedThreadCount()));
            }

            if (this.bean.isUnloadedClassCountSet()) {
               buf.append("UnloadedClassCount");
               buf.append(String.valueOf(this.bean.getUnloadedClassCount()));
            }

            if (this.bean.isUptimeSet()) {
               buf.append("Uptime");
               buf.append(String.valueOf(this.bean.getUptime()));
            }

            if (this.bean.isVmNameSet()) {
               buf.append("VmName");
               buf.append(String.valueOf(this.bean.getVmName()));
            }

            if (this.bean.isVmVendorSet()) {
               buf.append("VmVendor");
               buf.append(String.valueOf(this.bean.getVmVendor()));
            }

            if (this.bean.isVmVersionSet()) {
               buf.append("VmVersion");
               buf.append(String.valueOf(this.bean.getVmVersion()));
            }

            if (this.bean.isBootClassPathSupportedSet()) {
               buf.append("BootClassPathSupported");
               buf.append(String.valueOf(this.bean.isBootClassPathSupported()));
            }

            if (this.bean.isCurrentThreadCpuTimeSupportedSet()) {
               buf.append("CurrentThreadCpuTimeSupported");
               buf.append(String.valueOf(this.bean.isCurrentThreadCpuTimeSupported()));
            }

            if (this.bean.isThreadContentionMonitoringEnabledSet()) {
               buf.append("ThreadContentionMonitoringEnabled");
               buf.append(String.valueOf(this.bean.isThreadContentionMonitoringEnabled()));
            }

            if (this.bean.isThreadContentionMonitoringSupportedSet()) {
               buf.append("ThreadContentionMonitoringSupported");
               buf.append(String.valueOf(this.bean.isThreadContentionMonitoringSupported()));
            }

            if (this.bean.isThreadCpuTimeEnabledSet()) {
               buf.append("ThreadCpuTimeEnabled");
               buf.append(String.valueOf(this.bean.isThreadCpuTimeEnabled()));
            }

            if (this.bean.isThreadCpuTimeSupportedSet()) {
               buf.append("ThreadCpuTimeSupported");
               buf.append(String.valueOf(this.bean.isThreadCpuTimeSupported()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            JVMRuntimeBeanImpl otherTyped = (JVMRuntimeBeanImpl)other;
            this.computeDiff("BootClassPath", this.bean.getBootClassPath(), otherTyped.getBootClassPath(), false);
            this.computeDiff("ClassPath", this.bean.getClassPath(), otherTyped.getClassPath(), false);
            this.computeDiff("CurrentThreadCpuTime", this.bean.getCurrentThreadCpuTime(), otherTyped.getCurrentThreadCpuTime(), false);
            this.computeDiff("CurrentThreadUserTime", this.bean.getCurrentThreadUserTime(), otherTyped.getCurrentThreadUserTime(), false);
            this.computeDiff("DaemonThreadCount", this.bean.getDaemonThreadCount(), otherTyped.getDaemonThreadCount(), false);
            this.computeDiff("HeapMemoryCommittedBytes", this.bean.getHeapMemoryCommittedBytes(), otherTyped.getHeapMemoryCommittedBytes(), false);
            this.computeDiff("HeapMemoryInitBytes", this.bean.getHeapMemoryInitBytes(), otherTyped.getHeapMemoryInitBytes(), false);
            this.computeDiff("HeapMemoryMaxBytes", this.bean.getHeapMemoryMaxBytes(), otherTyped.getHeapMemoryMaxBytes(), false);
            this.computeDiff("HeapMemoryUsedBytes", this.bean.getHeapMemoryUsedBytes(), otherTyped.getHeapMemoryUsedBytes(), false);
            this.computeDiff("LibraryPath", this.bean.getLibraryPath(), otherTyped.getLibraryPath(), false);
            this.computeDiff("LoadedClassCount", this.bean.getLoadedClassCount(), otherTyped.getLoadedClassCount(), false);
            this.computeDiff("ManagementSpecVersion", this.bean.getManagementSpecVersion(), otherTyped.getManagementSpecVersion(), false);
            this.computeDiff("NonHeapMemoryCommittedBytes", this.bean.getNonHeapMemoryCommittedBytes(), otherTyped.getNonHeapMemoryCommittedBytes(), false);
            this.computeDiff("NonHeapMemoryInitBytes", this.bean.getNonHeapMemoryInitBytes(), otherTyped.getNonHeapMemoryInitBytes(), false);
            this.computeDiff("NonHeapMemoryMaxBytes", this.bean.getNonHeapMemoryMaxBytes(), otherTyped.getNonHeapMemoryMaxBytes(), false);
            this.computeDiff("NonHeapMemoryUsedBytes", this.bean.getNonHeapMemoryUsedBytes(), otherTyped.getNonHeapMemoryUsedBytes(), false);
            this.computeDiff("OSArch", this.bean.getOSArch(), otherTyped.getOSArch(), false);
            this.computeDiff("OSAvailableProcessors", this.bean.getOSAvailableProcessors(), otherTyped.getOSAvailableProcessors(), false);
            this.computeDiff("OSName", this.bean.getOSName(), otherTyped.getOSName(), false);
            this.computeDiff("OSVersion", this.bean.getOSVersion(), otherTyped.getOSVersion(), false);
            this.computeDiff("ObjectPendingFinalizationCount", this.bean.getObjectPendingFinalizationCount(), otherTyped.getObjectPendingFinalizationCount(), false);
            this.computeDiff("PartitionAllocatedMemory", this.bean.getPartitionAllocatedMemory(), otherTyped.getPartitionAllocatedMemory(), false);
            this.computeDiff("PartitionCpuTimeNanos", this.bean.getPartitionCpuTimeNanos(), otherTyped.getPartitionCpuTimeNanos(), false);
            this.computeDiff("PartitionCurrentOpenFileCount", this.bean.getPartitionCurrentOpenFileCount(), otherTyped.getPartitionCurrentOpenFileCount(), false);
            this.computeDiff("PartitionCurrentOpenSocketCount", this.bean.getPartitionCurrentOpenSocketCount(), otherTyped.getPartitionCurrentOpenSocketCount(), false);
            this.computeDiff("PartitionFileBytesRead", this.bean.getPartitionFileBytesRead(), otherTyped.getPartitionFileBytesRead(), false);
            this.computeDiff("PartitionFileBytesWritten", this.bean.getPartitionFileBytesWritten(), otherTyped.getPartitionFileBytesWritten(), false);
            this.computeDiff("PartitionNetworkBytesRead", this.bean.getPartitionNetworkBytesRead(), otherTyped.getPartitionNetworkBytesRead(), false);
            this.computeDiff("PartitionNetworkBytesWritten", this.bean.getPartitionNetworkBytesWritten(), otherTyped.getPartitionNetworkBytesWritten(), false);
            this.computeDiff("PartitionThreadCount", this.bean.getPartitionThreadCount(), otherTyped.getPartitionThreadCount(), false);
            this.computeDiff("PartitionTotalOpenedFileCount", this.bean.getPartitionTotalOpenedFileCount(), otherTyped.getPartitionTotalOpenedFileCount(), false);
            this.computeDiff("PartitionTotalOpenedSocketCount", this.bean.getPartitionTotalOpenedSocketCount(), otherTyped.getPartitionTotalOpenedSocketCount(), false);
            this.computeDiff("PeakThreadCount", this.bean.getPeakThreadCount(), otherTyped.getPeakThreadCount(), false);
            this.computeDiff("RetainedHeapHistoricalData", this.bean.getRetainedHeapHistoricalData(), otherTyped.getRetainedHeapHistoricalData(), false);
            this.computeDiff("RunningJVMName", this.bean.getRunningJVMName(), otherTyped.getRunningJVMName(), false);
            this.computeDiff("SpecName", this.bean.getSpecName(), otherTyped.getSpecName(), false);
            this.computeDiff("SpecVendor", this.bean.getSpecVendor(), otherTyped.getSpecVendor(), false);
            this.computeDiff("SpecVersion", this.bean.getSpecVersion(), otherTyped.getSpecVersion(), false);
            this.computeDiff("StartTime", this.bean.getStartTime(), otherTyped.getStartTime(), false);
            this.computeDiff("ThreadCount", this.bean.getThreadCount(), otherTyped.getThreadCount(), false);
            this.computeDiff("ThreadDump", this.bean.getThreadDump(), otherTyped.getThreadDump(), false);
            this.computeDiff("ThreadRequestExecutionDetails", this.bean.getThreadRequestExecutionDetails(), otherTyped.getThreadRequestExecutionDetails(), false);
            this.computeDiff("TotalLoadedClassCount", this.bean.getTotalLoadedClassCount(), otherTyped.getTotalLoadedClassCount(), false);
            this.computeDiff("TotalStartedThreadCount", this.bean.getTotalStartedThreadCount(), otherTyped.getTotalStartedThreadCount(), false);
            this.computeDiff("UnloadedClassCount", this.bean.getUnloadedClassCount(), otherTyped.getUnloadedClassCount(), false);
            this.computeDiff("Uptime", this.bean.getUptime(), otherTyped.getUptime(), false);
            this.computeDiff("VmName", this.bean.getVmName(), otherTyped.getVmName(), false);
            this.computeDiff("VmVendor", this.bean.getVmVendor(), otherTyped.getVmVendor(), false);
            this.computeDiff("VmVersion", this.bean.getVmVersion(), otherTyped.getVmVersion(), false);
            this.computeDiff("BootClassPathSupported", this.bean.isBootClassPathSupported(), otherTyped.isBootClassPathSupported(), false);
            this.computeDiff("CurrentThreadCpuTimeSupported", this.bean.isCurrentThreadCpuTimeSupported(), otherTyped.isCurrentThreadCpuTimeSupported(), false);
            this.computeDiff("ThreadContentionMonitoringEnabled", this.bean.isThreadContentionMonitoringEnabled(), otherTyped.isThreadContentionMonitoringEnabled(), false);
            this.computeDiff("ThreadContentionMonitoringSupported", this.bean.isThreadContentionMonitoringSupported(), otherTyped.isThreadContentionMonitoringSupported(), false);
            this.computeDiff("ThreadCpuTimeEnabled", this.bean.isThreadCpuTimeEnabled(), otherTyped.isThreadCpuTimeEnabled(), false);
            this.computeDiff("ThreadCpuTimeSupported", this.bean.isThreadCpuTimeSupported(), otherTyped.isThreadCpuTimeSupported(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JVMRuntimeBeanImpl original = (JVMRuntimeBeanImpl)event.getSourceBean();
            JVMRuntimeBeanImpl proposed = (JVMRuntimeBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BootClassPath")) {
                  original.setBootClassPath(proposed.getBootClassPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("ClassPath")) {
                  original.setClassPath(proposed.getClassPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("CurrentThreadCpuTime")) {
                  original.setCurrentThreadCpuTime(proposed.getCurrentThreadCpuTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("CurrentThreadUserTime")) {
                  original.setCurrentThreadUserTime(proposed.getCurrentThreadUserTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DaemonThreadCount")) {
                  original.setDaemonThreadCount(proposed.getDaemonThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("HeapMemoryCommittedBytes")) {
                  original.setHeapMemoryCommittedBytes(proposed.getHeapMemoryCommittedBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("HeapMemoryInitBytes")) {
                  original.setHeapMemoryInitBytes(proposed.getHeapMemoryInitBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("HeapMemoryMaxBytes")) {
                  original.setHeapMemoryMaxBytes(proposed.getHeapMemoryMaxBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("HeapMemoryUsedBytes")) {
                  original.setHeapMemoryUsedBytes(proposed.getHeapMemoryUsedBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("LibraryPath")) {
                  original.setLibraryPath(proposed.getLibraryPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("LoadedClassCount")) {
                  original.setLoadedClassCount(proposed.getLoadedClassCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("ManagementSpecVersion")) {
                  original.setManagementSpecVersion(proposed.getManagementSpecVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("NonHeapMemoryCommittedBytes")) {
                  original.setNonHeapMemoryCommittedBytes(proposed.getNonHeapMemoryCommittedBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("NonHeapMemoryInitBytes")) {
                  original.setNonHeapMemoryInitBytes(proposed.getNonHeapMemoryInitBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("NonHeapMemoryMaxBytes")) {
                  original.setNonHeapMemoryMaxBytes(proposed.getNonHeapMemoryMaxBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("NonHeapMemoryUsedBytes")) {
                  original.setNonHeapMemoryUsedBytes(proposed.getNonHeapMemoryUsedBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("OSArch")) {
                  original.setOSArch(proposed.getOSArch());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("OSAvailableProcessors")) {
                  original.setOSAvailableProcessors(proposed.getOSAvailableProcessors());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("OSName")) {
                  original.setOSName(proposed.getOSName());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("OSVersion")) {
                  original.setOSVersion(proposed.getOSVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("ObjectPendingFinalizationCount")) {
                  original.setObjectPendingFinalizationCount(proposed.getObjectPendingFinalizationCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PartitionAllocatedMemory")) {
                  original.setPartitionAllocatedMemory(proposed.getPartitionAllocatedMemory());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("PartitionCpuTimeNanos")) {
                  original.setPartitionCpuTimeNanos(proposed.getPartitionCpuTimeNanos());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("PartitionCurrentOpenFileCount")) {
                  original.setPartitionCurrentOpenFileCount(proposed.getPartitionCurrentOpenFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("PartitionCurrentOpenSocketCount")) {
                  original.setPartitionCurrentOpenSocketCount(proposed.getPartitionCurrentOpenSocketCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (prop.equals("PartitionFileBytesRead")) {
                  original.setPartitionFileBytesRead(proposed.getPartitionFileBytesRead());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("PartitionFileBytesWritten")) {
                  original.setPartitionFileBytesWritten(proposed.getPartitionFileBytesWritten());
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("PartitionNetworkBytesRead")) {
                  original.setPartitionNetworkBytesRead(proposed.getPartitionNetworkBytesRead());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("PartitionNetworkBytesWritten")) {
                  original.setPartitionNetworkBytesWritten(proposed.getPartitionNetworkBytesWritten());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("PartitionThreadCount")) {
                  original.setPartitionThreadCount(proposed.getPartitionThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("PartitionTotalOpenedFileCount")) {
                  original.setPartitionTotalOpenedFileCount(proposed.getPartitionTotalOpenedFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 50);
               } else if (prop.equals("PartitionTotalOpenedSocketCount")) {
                  original.setPartitionTotalOpenedSocketCount(proposed.getPartitionTotalOpenedSocketCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("PeakThreadCount")) {
                  original.setPeakThreadCount(proposed.getPeakThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RetainedHeapHistoricalData")) {
                  original.setRetainedHeapHistoricalData(proposed.getRetainedHeapHistoricalData());
                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("RunningJVMName")) {
                  original.setRunningJVMName(proposed.getRunningJVMName());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("SpecName")) {
                  original.setSpecName(proposed.getSpecName());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("SpecVendor")) {
                  original.setSpecVendor(proposed.getSpecVendor());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("SpecVersion")) {
                  original.setSpecVersion(proposed.getSpecVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("StartTime")) {
                  original.setStartTime(proposed.getStartTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("ThreadCount")) {
                  original.setThreadCount(proposed.getThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("ThreadDump")) {
                  original.setThreadDump(proposed.getThreadDump());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("ThreadRequestExecutionDetails")) {
                  original.setThreadRequestExecutionDetails(proposed.getThreadRequestExecutionDetails());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("TotalLoadedClassCount")) {
                  original.setTotalLoadedClassCount(proposed.getTotalLoadedClassCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("TotalStartedThreadCount")) {
                  original.setTotalStartedThreadCount(proposed.getTotalStartedThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("UnloadedClassCount")) {
                  original.setUnloadedClassCount(proposed.getUnloadedClassCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("Uptime")) {
                  original.setUptime(proposed.getUptime());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("VmName")) {
                  original.setVmName(proposed.getVmName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("VmVendor")) {
                  original.setVmVendor(proposed.getVmVendor());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("VmVersion")) {
                  original.setVmVersion(proposed.getVmVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("BootClassPathSupported")) {
                  original.setBootClassPathSupported(proposed.isBootClassPathSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("CurrentThreadCpuTimeSupported")) {
                  original.setCurrentThreadCpuTimeSupported(proposed.isCurrentThreadCpuTimeSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("ThreadContentionMonitoringEnabled")) {
                  original.setThreadContentionMonitoringEnabled(proposed.isThreadContentionMonitoringEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ThreadContentionMonitoringSupported")) {
                  original.setThreadContentionMonitoringSupported(proposed.isThreadContentionMonitoringSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ThreadCpuTimeEnabled")) {
                  original.setThreadCpuTimeEnabled(proposed.isThreadCpuTimeEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ThreadCpuTimeSupported")) {
                  original.setThreadCpuTimeSupported(proposed.isThreadCpuTimeSupported());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            JVMRuntimeBeanImpl copy = (JVMRuntimeBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BootClassPath")) && this.bean.isBootClassPathSet()) {
               copy.setBootClassPath(this.bean.getBootClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("ClassPath")) && this.bean.isClassPathSet()) {
               copy.setClassPath(this.bean.getClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentThreadCpuTime")) && this.bean.isCurrentThreadCpuTimeSet()) {
               copy.setCurrentThreadCpuTime(this.bean.getCurrentThreadCpuTime());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentThreadUserTime")) && this.bean.isCurrentThreadUserTimeSet()) {
               copy.setCurrentThreadUserTime(this.bean.getCurrentThreadUserTime());
            }

            if ((excludeProps == null || !excludeProps.contains("DaemonThreadCount")) && this.bean.isDaemonThreadCountSet()) {
               copy.setDaemonThreadCount(this.bean.getDaemonThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("HeapMemoryCommittedBytes")) && this.bean.isHeapMemoryCommittedBytesSet()) {
               copy.setHeapMemoryCommittedBytes(this.bean.getHeapMemoryCommittedBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("HeapMemoryInitBytes")) && this.bean.isHeapMemoryInitBytesSet()) {
               copy.setHeapMemoryInitBytes(this.bean.getHeapMemoryInitBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("HeapMemoryMaxBytes")) && this.bean.isHeapMemoryMaxBytesSet()) {
               copy.setHeapMemoryMaxBytes(this.bean.getHeapMemoryMaxBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("HeapMemoryUsedBytes")) && this.bean.isHeapMemoryUsedBytesSet()) {
               copy.setHeapMemoryUsedBytes(this.bean.getHeapMemoryUsedBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryPath")) && this.bean.isLibraryPathSet()) {
               copy.setLibraryPath(this.bean.getLibraryPath());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadedClassCount")) && this.bean.isLoadedClassCountSet()) {
               copy.setLoadedClassCount(this.bean.getLoadedClassCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagementSpecVersion")) && this.bean.isManagementSpecVersionSet()) {
               copy.setManagementSpecVersion(this.bean.getManagementSpecVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("NonHeapMemoryCommittedBytes")) && this.bean.isNonHeapMemoryCommittedBytesSet()) {
               copy.setNonHeapMemoryCommittedBytes(this.bean.getNonHeapMemoryCommittedBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("NonHeapMemoryInitBytes")) && this.bean.isNonHeapMemoryInitBytesSet()) {
               copy.setNonHeapMemoryInitBytes(this.bean.getNonHeapMemoryInitBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("NonHeapMemoryMaxBytes")) && this.bean.isNonHeapMemoryMaxBytesSet()) {
               copy.setNonHeapMemoryMaxBytes(this.bean.getNonHeapMemoryMaxBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("NonHeapMemoryUsedBytes")) && this.bean.isNonHeapMemoryUsedBytesSet()) {
               copy.setNonHeapMemoryUsedBytes(this.bean.getNonHeapMemoryUsedBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("OSArch")) && this.bean.isOSArchSet()) {
               copy.setOSArch(this.bean.getOSArch());
            }

            if ((excludeProps == null || !excludeProps.contains("OSAvailableProcessors")) && this.bean.isOSAvailableProcessorsSet()) {
               copy.setOSAvailableProcessors(this.bean.getOSAvailableProcessors());
            }

            if ((excludeProps == null || !excludeProps.contains("OSName")) && this.bean.isOSNameSet()) {
               copy.setOSName(this.bean.getOSName());
            }

            if ((excludeProps == null || !excludeProps.contains("OSVersion")) && this.bean.isOSVersionSet()) {
               copy.setOSVersion(this.bean.getOSVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("ObjectPendingFinalizationCount")) && this.bean.isObjectPendingFinalizationCountSet()) {
               copy.setObjectPendingFinalizationCount(this.bean.getObjectPendingFinalizationCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionAllocatedMemory")) && this.bean.isPartitionAllocatedMemorySet()) {
               copy.setPartitionAllocatedMemory(this.bean.getPartitionAllocatedMemory());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionCpuTimeNanos")) && this.bean.isPartitionCpuTimeNanosSet()) {
               copy.setPartitionCpuTimeNanos(this.bean.getPartitionCpuTimeNanos());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionCurrentOpenFileCount")) && this.bean.isPartitionCurrentOpenFileCountSet()) {
               copy.setPartitionCurrentOpenFileCount(this.bean.getPartitionCurrentOpenFileCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionCurrentOpenSocketCount")) && this.bean.isPartitionCurrentOpenSocketCountSet()) {
               copy.setPartitionCurrentOpenSocketCount(this.bean.getPartitionCurrentOpenSocketCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionFileBytesRead")) && this.bean.isPartitionFileBytesReadSet()) {
               copy.setPartitionFileBytesRead(this.bean.getPartitionFileBytesRead());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionFileBytesWritten")) && this.bean.isPartitionFileBytesWrittenSet()) {
               copy.setPartitionFileBytesWritten(this.bean.getPartitionFileBytesWritten());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionNetworkBytesRead")) && this.bean.isPartitionNetworkBytesReadSet()) {
               copy.setPartitionNetworkBytesRead(this.bean.getPartitionNetworkBytesRead());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionNetworkBytesWritten")) && this.bean.isPartitionNetworkBytesWrittenSet()) {
               copy.setPartitionNetworkBytesWritten(this.bean.getPartitionNetworkBytesWritten());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionThreadCount")) && this.bean.isPartitionThreadCountSet()) {
               copy.setPartitionThreadCount(this.bean.getPartitionThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionTotalOpenedFileCount")) && this.bean.isPartitionTotalOpenedFileCountSet()) {
               copy.setPartitionTotalOpenedFileCount(this.bean.getPartitionTotalOpenedFileCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionTotalOpenedSocketCount")) && this.bean.isPartitionTotalOpenedSocketCountSet()) {
               copy.setPartitionTotalOpenedSocketCount(this.bean.getPartitionTotalOpenedSocketCount());
            }

            if ((excludeProps == null || !excludeProps.contains("PeakThreadCount")) && this.bean.isPeakThreadCountSet()) {
               copy.setPeakThreadCount(this.bean.getPeakThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("RetainedHeapHistoricalData")) && this.bean.isRetainedHeapHistoricalDataSet()) {
               copy.setRetainedHeapHistoricalData(this.bean.getRetainedHeapHistoricalData());
            }

            if ((excludeProps == null || !excludeProps.contains("RunningJVMName")) && this.bean.isRunningJVMNameSet()) {
               copy.setRunningJVMName(this.bean.getRunningJVMName());
            }

            if ((excludeProps == null || !excludeProps.contains("SpecName")) && this.bean.isSpecNameSet()) {
               copy.setSpecName(this.bean.getSpecName());
            }

            if ((excludeProps == null || !excludeProps.contains("SpecVendor")) && this.bean.isSpecVendorSet()) {
               copy.setSpecVendor(this.bean.getSpecVendor());
            }

            if ((excludeProps == null || !excludeProps.contains("SpecVersion")) && this.bean.isSpecVersionSet()) {
               copy.setSpecVersion(this.bean.getSpecVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("StartTime")) && this.bean.isStartTimeSet()) {
               copy.setStartTime(this.bean.getStartTime());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadCount")) && this.bean.isThreadCountSet()) {
               copy.setThreadCount(this.bean.getThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadDump")) && this.bean.isThreadDumpSet()) {
               copy.setThreadDump(this.bean.getThreadDump());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadRequestExecutionDetails")) && this.bean.isThreadRequestExecutionDetailsSet()) {
               copy.setThreadRequestExecutionDetails(this.bean.getThreadRequestExecutionDetails());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalLoadedClassCount")) && this.bean.isTotalLoadedClassCountSet()) {
               copy.setTotalLoadedClassCount(this.bean.getTotalLoadedClassCount());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalStartedThreadCount")) && this.bean.isTotalStartedThreadCountSet()) {
               copy.setTotalStartedThreadCount(this.bean.getTotalStartedThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("UnloadedClassCount")) && this.bean.isUnloadedClassCountSet()) {
               copy.setUnloadedClassCount(this.bean.getUnloadedClassCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Uptime")) && this.bean.isUptimeSet()) {
               copy.setUptime(this.bean.getUptime());
            }

            if ((excludeProps == null || !excludeProps.contains("VmName")) && this.bean.isVmNameSet()) {
               copy.setVmName(this.bean.getVmName());
            }

            if ((excludeProps == null || !excludeProps.contains("VmVendor")) && this.bean.isVmVendorSet()) {
               copy.setVmVendor(this.bean.getVmVendor());
            }

            if ((excludeProps == null || !excludeProps.contains("VmVersion")) && this.bean.isVmVersionSet()) {
               copy.setVmVersion(this.bean.getVmVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("BootClassPathSupported")) && this.bean.isBootClassPathSupportedSet()) {
               copy.setBootClassPathSupported(this.bean.isBootClassPathSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("CurrentThreadCpuTimeSupported")) && this.bean.isCurrentThreadCpuTimeSupportedSet()) {
               copy.setCurrentThreadCpuTimeSupported(this.bean.isCurrentThreadCpuTimeSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadContentionMonitoringEnabled")) && this.bean.isThreadContentionMonitoringEnabledSet()) {
               copy.setThreadContentionMonitoringEnabled(this.bean.isThreadContentionMonitoringEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadContentionMonitoringSupported")) && this.bean.isThreadContentionMonitoringSupportedSet()) {
               copy.setThreadContentionMonitoringSupported(this.bean.isThreadContentionMonitoringSupported());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadCpuTimeEnabled")) && this.bean.isThreadCpuTimeEnabledSet()) {
               copy.setThreadCpuTimeEnabled(this.bean.isThreadCpuTimeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadCpuTimeSupported")) && this.bean.isThreadCpuTimeSupportedSet()) {
               copy.setThreadCpuTimeSupported(this.bean.isThreadCpuTimeSupported());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
