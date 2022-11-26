package weblogic.diagnostics.image;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.security.AccessController;
import java.util.Arrays;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.context.CorrelationManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.image.descriptor.JVMRuntimeBean;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JVMRuntimeMBean;
import weblogic.management.runtime.PartitionResourceMetricsRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PlatformConstants;
import weblogic.work.ExecuteThread;
import weblogic.work.RequestManager;
import weblogic.work.WorkAdapter;

class JVMSource implements PartitionAwareImageSource {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private JVMRuntimeBean root;
   private RuntimeMXBean mxRuntimeBean = ManagementFactory.getRuntimeMXBean();
   private ThreadMXBean mxThreadBean = ManagementFactory.getThreadMXBean();
   private OperatingSystemMXBean mxOSBean = ManagementFactory.getOperatingSystemMXBean();
   private MemoryMXBean mxMemoryBean = ManagementFactory.getMemoryMXBean();
   private ClassLoadingMXBean mxClassLoadingBean = ManagementFactory.getClassLoadingMXBean();
   private boolean timeoutRequested;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal(partitionName, out);
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal((String)null, out);
   }

   private void createDiagnosticImageInternal(String partitionName, OutputStream out) throws ImageSourceCreationException {
      DescriptorManager dm = new DescriptorManager();
      Descriptor desc = dm.createDescriptorRoot(JVMRuntimeBean.class);
      this.root = (JVMRuntimeBean)desc.getRootBean();
      if (partitionName == null) {
         this.writeOSMxBean();
         this.writeClassLoadingMxBean();
         this.writeRuntimeMxBean();
         this.writeMemoryMxBean();
         this.writeThreadMxBean();
         this.writeThreadDump();
         this.writeThreadRequestExecutionDetails();
      }

      this.writePartitionResourceMetrics(partitionName);

      try {
         dm.writeDescriptorBeanAsXML((DescriptorBean)this.root, out);
      } catch (IOException var6) {
         throw new ImageSourceCreationException(var6);
      }
   }

   private void writeThreadRequestExecutionDetails() {
      RequestManager rm = RequestManager.getInstance();
      ExecuteThread[] threads = rm.getAllThreads();
      StringBuffer buffer = new StringBuffer();
      ExecuteThread[] var4 = threads;
      int var5 = threads.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ExecuteThread execThread = var4[var6];
         buffer.append("Thread name:");
         buffer.append(execThread.getName());
         long threadId = execThread.getId();
         buffer.append(", ThreadID: ");
         buffer.append(threadId);
         String ecId = CorrelationManager.getECID(threadId);
         buffer.append(", ECID: ");
         buffer.append(ecId);
         buffer.append(", Work:");
         WorkAdapter work = execThread.getCurrentWork();
         if (work != null) {
            buffer.append('{');
            buffer.append(work);
            buffer.append('}');
         } else {
            buffer.append(" No work currently allocated to thread.");
         }

         buffer.append(PlatformConstants.EOL);
      }

      this.root.setThreadRequestExecutionDetails(buffer.toString());
   }

   public void timeoutImageCreation() {
      this.timeoutRequested = true;
   }

   private void writeOSMxBean() {
      this.root.setOSName(this.mxOSBean.getName());
      this.root.setOSVersion(this.mxOSBean.getVersion());
      this.root.setOSArch(this.mxOSBean.getArch());
      this.root.setOSAvailableProcessors(this.mxOSBean.getAvailableProcessors());
   }

   private void writeClassLoadingMxBean() {
      this.root.setLoadedClassCount(this.mxClassLoadingBean.getLoadedClassCount());
      this.root.setTotalLoadedClassCount(this.mxClassLoadingBean.getTotalLoadedClassCount());
      this.root.setUnloadedClassCount(this.mxClassLoadingBean.getUnloadedClassCount());
   }

   private void writeRuntimeMxBean() {
      this.root.setRunningJVMName(this.mxRuntimeBean.getName());
      this.root.setManagementSpecVersion(this.mxRuntimeBean.getManagementSpecVersion());
      this.root.setVmName(this.mxRuntimeBean.getVmName());
      this.root.setVmVendor(this.mxRuntimeBean.getVmVendor());
      this.root.setVmVersion(this.mxRuntimeBean.getVmVersion());
      this.root.setSpecName(this.mxRuntimeBean.getSpecName());
      this.root.setSpecVendor(this.mxRuntimeBean.getSpecVendor());
      this.root.setSpecVersion(this.mxRuntimeBean.getSpecVersion());
      this.root.setClassPath(this.mxRuntimeBean.getClassPath());
      this.root.setLibraryPath(this.mxRuntimeBean.getLibraryPath());
      String bcp = null;

      try {
         bcp = this.mxRuntimeBean.getBootClassPath();
      } catch (UnsupportedOperationException var3) {
      }

      this.root.setBootClassPath(bcp);
      this.root.setUptime(this.mxRuntimeBean.getUptime());
      this.root.setStartTime(this.mxRuntimeBean.getStartTime());
      this.root.setBootClassPathSupported(this.mxRuntimeBean.isBootClassPathSupported());
   }

   private void writeMemoryMxBean() {
      this.root.setObjectPendingFinalizationCount(this.mxMemoryBean.getObjectPendingFinalizationCount());
      this.root.setHeapMemoryInitBytes(this.mxMemoryBean.getHeapMemoryUsage().getInit());
      this.root.setHeapMemoryUsedBytes(this.mxMemoryBean.getHeapMemoryUsage().getUsed());
      this.root.setHeapMemoryCommittedBytes(this.mxMemoryBean.getHeapMemoryUsage().getCommitted());
      this.root.setHeapMemoryMaxBytes(this.mxMemoryBean.getHeapMemoryUsage().getMax());
      this.root.setNonHeapMemoryInitBytes(this.mxMemoryBean.getNonHeapMemoryUsage().getInit());
      this.root.setNonHeapMemoryUsedBytes(this.mxMemoryBean.getNonHeapMemoryUsage().getUsed());
      this.root.setNonHeapMemoryCommittedBytes(this.mxMemoryBean.getNonHeapMemoryUsage().getCommitted());
      this.root.setNonHeapMemoryMaxBytes(this.mxMemoryBean.getNonHeapMemoryUsage().getMax());
   }

   private void writeThreadMxBean() {
      this.root.setThreadCount(this.mxThreadBean.getThreadCount());
      this.root.setPeakThreadCount(this.mxThreadBean.getPeakThreadCount());
      this.root.setTotalStartedThreadCount(this.mxThreadBean.getTotalStartedThreadCount());
      this.root.setDaemonThreadCount(this.mxThreadBean.getDaemonThreadCount());
      this.root.setThreadContentionMonitoringSupported(this.mxThreadBean.isThreadContentionMonitoringSupported());
      this.root.setThreadContentionMonitoringEnabled(this.mxThreadBean.isThreadContentionMonitoringEnabled());
      this.root.setCurrentThreadCpuTime(this.mxThreadBean.getCurrentThreadCpuTime());
      this.root.setCurrentThreadUserTime(this.mxThreadBean.getCurrentThreadUserTime());
      this.root.setThreadCpuTimeSupported(this.mxThreadBean.isThreadCpuTimeSupported());
      this.root.setThreadCpuTimeEnabled(this.mxThreadBean.isThreadCpuTimeEnabled());
      this.root.setCurrentThreadCpuTimeSupported(this.mxThreadBean.isCurrentThreadCpuTimeSupported());
   }

   private void writeThreadDump() {
      if (Kernel.isServer()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("JVMSource Adding ThreadDump using JVMRuntime MBean");
         }

         JVMRuntimeMBean runtime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getJVMRuntime();
         this.root.setThreadDump(runtime.getThreadStackDump());
      }
   }

   private void writePartitionResourceMetrics(String partitionName) {
      if (partitionName != null) {
         PartitionRuntimeMBean prmb = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionName);
         if (prmb == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("JVMSource partition " + partitionName + " was not found");
            }

         } else {
            PartitionResourceMetricsRuntimeMBean prcm = prmb.getPartitionResourceMetricsRuntime();
            if (prcm != null && prcm.isRCMMetricsDataAvailable()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("JVMSource Adding PartitionResourceMetrics for " + partitionName);
               }

               this.root.setPartitionCpuTimeNanos(prcm.getCpuTimeNanos());
               this.root.setPartitionAllocatedMemory(prcm.getAllocatedMemory());
               this.root.setPartitionThreadCount(prcm.getThreadCount());
               this.root.setPartitionTotalOpenedSocketCount(prcm.getTotalOpenedSocketCount());
               this.root.setPartitionCurrentOpenSocketCount(prcm.getCurrentOpenSocketCount());
               this.root.setPartitionNetworkBytesRead(prcm.getNetworkBytesRead());
               this.root.setPartitionNetworkBytesWritten(prcm.getNetworkBytesWritten());
               this.root.setPartitionTotalOpenedFileCount(prcm.getTotalOpenedFileCount());
               this.root.setPartitionCurrentOpenFileCount(prcm.getCurrentOpenFileCount());
               this.root.setPartitionFileBytesRead(prcm.getFileBytesRead());
               this.root.setPartitionFileBytesWritten(prcm.getFileBytesWritten());
               Long[][] retainedHeapData = prcm.getRetainedHeapHistoricalData();
               StringBuilder sb = new StringBuilder();
               if (retainedHeapData != null) {
                  Long[][] var6 = retainedHeapData;
                  int var7 = retainedHeapData.length;

                  for(int var8 = 0; var8 < var7; ++var8) {
                     Long[] tuple = var6[var8];
                     if (tuple != null) {
                        sb.append(Arrays.toString(tuple));
                     }
                  }
               }

               this.root.setRetainedHeapHistoricalData(sb.toString());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("cpu: " + this.root.getPartitionCpuTimeNanos());
                  debugLogger.debug("mem: " + this.root.getPartitionAllocatedMemory());
                  debugLogger.debug("thread: " + this.root.getPartitionThreadCount());
                  debugLogger.debug("opened socket " + this.root.getPartitionTotalOpenedSocketCount());
                  debugLogger.debug("current socket " + this.root.getPartitionCurrentOpenSocketCount());
                  debugLogger.debug("net read " + this.root.getPartitionNetworkBytesRead());
                  debugLogger.debug("net written " + this.root.getPartitionNetworkBytesWritten());
                  debugLogger.debug("opened file " + this.root.getPartitionTotalOpenedFileCount());
                  debugLogger.debug("file open " + this.root.getPartitionCurrentOpenFileCount());
                  debugLogger.debug("file byte r " + this.root.getPartitionFileBytesRead());
                  debugLogger.debug("file byte w " + this.root.getPartitionFileBytesWritten());
               }

            } else {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("JVMSource RCM Metrics are not available for " + partitionName);
               }

            }
         }
      }
   }
}
