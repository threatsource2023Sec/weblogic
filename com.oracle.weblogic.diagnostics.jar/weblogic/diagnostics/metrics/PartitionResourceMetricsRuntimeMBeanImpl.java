package weblogic.diagnostics.metrics;

import java.security.AccessController;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PartitionResourceMetricsRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionResourceMetricsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PartitionResourceMetricsRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public PartitionResourceMetricsRuntimeMBeanImpl(PartitionRuntimeMBean partitionRuntimeMBean, String name, String id) throws ManagementException {
      super(name, partitionRuntimeMBean);
   }

   public boolean isRCMMetricsDataAvailable() {
      return false;
   }

   public long getCpuTimeNanos() {
      return -1L;
   }

   public long getAllocatedMemory() {
      return -1L;
   }

   public long getThreadCount() {
      return -1L;
   }

   public long getTotalOpenedSocketCount() {
      return -1L;
   }

   public long getCurrentOpenSocketCount() {
      return -1L;
   }

   public long getNetworkBytesRead() {
      return -1L;
   }

   public long getNetworkBytesWritten() {
      return -1L;
   }

   public long getTotalOpenedFileCount() {
      return -1L;
   }

   public long getCurrentOpenFileCount() {
      return -1L;
   }

   public long getFileBytesRead() {
      return -1L;
   }

   public long getFileBytesWritten() {
      return -1L;
   }

   public long getTotalOpenedFileDescriptorCount() {
      return -1L;
   }

   public long getCurrentOpenFileDescriptorCount() {
      return -1L;
   }

   public Long[][] getRetainedHeapHistoricalData() {
      Long[][] items = new Long[0][];
      return items;
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   public synchronized void postDeregister() {
   }

   public Long[][] getCpuUtilizationHistoricalData() {
      Long[][] items = new Long[0][];
      return items;
   }
}
