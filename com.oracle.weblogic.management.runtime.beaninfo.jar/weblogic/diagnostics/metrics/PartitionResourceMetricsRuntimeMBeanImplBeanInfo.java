package weblogic.diagnostics.metrics;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.PartitionResourceMetricsRuntimeMBean;

public class PartitionResourceMetricsRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionResourceMetricsRuntimeMBean.class;

   public PartitionResourceMetricsRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionResourceMetricsRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.metrics.PartitionResourceMetricsRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.metrics");
      String description = (new String("The runtime MBean interface for partition specific resource consumption metrics. The resource meters are added lazily for a partition. Therefore, the first time these metrics are queried, it might return zero values. Subsequent gets would return non-zero values based on the resource consumption. These metrics are applicable in the context of a partition since either server start or partition creation/restart, whichever was later. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionResourceMetricsRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AllocatedMemory")) {
         getterName = "getAllocatedMemory";
         setterName = null;
         currentResult = new PropertyDescriptor("AllocatedMemory", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AllocatedMemory", currentResult);
         currentResult.setValue("description", "Total allocated memory in bytes for the partition. This metric value increases monotonically over time, it never decreases. Retained memory should be used to get current net values. ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("getRetainedHeapHistoricalData() which provides the values of the net memory usage over a period of time.")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CpuTimeNanos")) {
         getterName = "getCpuTimeNanos";
         setterName = null;
         currentResult = new PropertyDescriptor("CpuTimeNanos", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CpuTimeNanos", currentResult);
         currentResult.setValue("description", "Total CPU time spent measured in nanoseconds in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CpuUtilizationHistoricalData")) {
         getterName = "getCpuUtilizationHistoricalData";
         setterName = null;
         currentResult = new PropertyDescriptor("CpuUtilizationHistoricalData", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CpuUtilizationHistoricalData", currentResult);
         currentResult.setValue("description", "Returns a snapshot of the historical data for CPU usage for the partition. CPU Utilization percentage indicates the percentage of CPU utilized by a partition with respect to available CPU to Weblogic Server. Data is returned as a two-dimensional array for the CPU usage scoped to the partition over time. Each item in the array contains a tuple of [timestamp (long), cpuUsage(long)] values. ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentOpenFileCount")) {
         getterName = "getCurrentOpenFileCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentOpenFileCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentOpenFileCount", currentResult);
         currentResult.setValue("description", "Number of files currently open in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentOpenFileDescriptorCount")) {
         getterName = "getCurrentOpenFileDescriptorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentOpenFileDescriptorCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentOpenFileDescriptorCount", currentResult);
         currentResult.setValue("description", "Number of file descriptors currently open in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentOpenSocketCount")) {
         getterName = "getCurrentOpenSocketCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentOpenSocketCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentOpenSocketCount", currentResult);
         currentResult.setValue("description", "Number of sockets currently open in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileBytesRead")) {
         getterName = "getFileBytesRead";
         setterName = null;
         currentResult = new PropertyDescriptor("FileBytesRead", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FileBytesRead", currentResult);
         currentResult.setValue("description", "Total number of file bytes read in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileBytesWritten")) {
         getterName = "getFileBytesWritten";
         setterName = null;
         currentResult = new PropertyDescriptor("FileBytesWritten", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FileBytesWritten", currentResult);
         currentResult.setValue("description", "Total number of file bytes written in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NetworkBytesRead")) {
         getterName = "getNetworkBytesRead";
         setterName = null;
         currentResult = new PropertyDescriptor("NetworkBytesRead", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NetworkBytesRead", currentResult);
         currentResult.setValue("description", "Total number of bytes read from sockets for a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NetworkBytesWritten")) {
         getterName = "getNetworkBytesWritten";
         setterName = null;
         currentResult = new PropertyDescriptor("NetworkBytesWritten", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NetworkBytesWritten", currentResult);
         currentResult.setValue("description", "Total number of bytes written to sockets for a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetainedHeapHistoricalData")) {
         getterName = "getRetainedHeapHistoricalData";
         setterName = null;
         currentResult = new PropertyDescriptor("RetainedHeapHistoricalData", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RetainedHeapHistoricalData", currentResult);
         currentResult.setValue("description", "Returns a snapshot of the historical data for retained heap memory usage for the partition. Data is returned as a two-dimensional array for the usage of retained heap scoped to the partition over time. Each item in the array contains a tuple of [timestamp (long), retainedHeap(long)] values. ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadCount")) {
         getterName = "getThreadCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ThreadCount", currentResult);
         currentResult.setValue("description", "Number of threads currently assigned to the partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalOpenedFileCount")) {
         getterName = "getTotalOpenedFileCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalOpenedFileCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalOpenedFileCount", currentResult);
         currentResult.setValue("description", "Total number of files opened in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalOpenedFileDescriptorCount")) {
         getterName = "getTotalOpenedFileDescriptorCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalOpenedFileDescriptorCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalOpenedFileDescriptorCount", currentResult);
         currentResult.setValue("description", "Total number of file descriptors opened in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalOpenedSocketCount")) {
         getterName = "getTotalOpenedSocketCount";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalOpenedSocketCount", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalOpenedSocketCount", currentResult);
         currentResult.setValue("description", "Total number of sockets opened in the context of a partition. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RCMMetricsDataAvailable")) {
         getterName = "isRCMMetricsDataAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("RCMMetricsDataAvailable", PartitionResourceMetricsRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RCMMetricsDataAvailable", currentResult);
         currentResult.setValue("description", "Checks whether RCM metrics data is available for this partition. ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   protected void buildMethodDescriptors(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      this.fillinFinderMethodInfos(descriptors);
      if (!this.readOnly) {
         this.fillinCollectionMethodInfos(descriptors);
         this.fillinFactoryMethodInfos(descriptors);
      }

      this.fillinOperationMethodInfos(descriptors);
      super.buildMethodDescriptors(descriptors);
   }

   protected void buildEventSetDescriptors(Map descriptors) throws IntrospectionException {
   }
}
