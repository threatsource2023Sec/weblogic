package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class PartitionWorkManagerMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionWorkManagerMBean.class;

   public PartitionWorkManagerMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionWorkManagerMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.PartitionWorkManagerMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Specifies partition-level work manager policies that are configured by domain system administrators. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.PartitionWorkManagerMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("FairShare")) {
         getterName = "getFairShare";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFairShare";
         }

         currentResult = new PropertyDescriptor("FairShare", PartitionWorkManagerMBean.class, getterName, setterName);
         descriptors.put("FairShare", currentResult);
         currentResult.setValue("description", "A desired percentage of thread usage by a partition compared to the thread usage by all partitions. It is recommended that the sum of this value for all the partitions running in a WLS domain add up to 100, but it is not strictly enforced. When they do not add up to 100, WLS assigns thread-usage times to different partitions based on their relative values. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxThreadsConstraint")) {
         getterName = "getMaxThreadsConstraint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxThreadsConstraint";
         }

         currentResult = new PropertyDescriptor("MaxThreadsConstraint", PartitionWorkManagerMBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraint", currentResult);
         currentResult.setValue("description", "The maximum number of concurrent requests that the self-tuning thread pool can be processing on behalf of a partition at any given time. The default value of -1 means that this is only limited by the size of the self-tuning thread pool. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxThreadsConstraintQueueSize")) {
         getterName = "getMaxThreadsConstraintQueueSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxThreadsConstraintQueueSize";
         }

         currentResult = new PropertyDescriptor("MaxThreadsConstraintQueueSize", PartitionWorkManagerMBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraintQueueSize", currentResult);
         currentResult.setValue("description", "<p>Desired size of the Partition MaxThreadsConstraint queue for requests pending execution.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(8192));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(256));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraintCap")) {
         getterName = "getMinThreadsConstraintCap";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinThreadsConstraintCap";
         }

         currentResult = new PropertyDescriptor("MinThreadsConstraintCap", PartitionWorkManagerMBean.class, getterName, setterName);
         descriptors.put("MinThreadsConstraintCap", currentResult);
         currentResult.setValue("description", "This imposes an upper limit on the number of standby threads that can be created for satisfying the minimum threads constraints configured in a partition. If the sum of the configured values of all minimum threads constraints in a partition exceeds this configured value, a warning message will be logged and WLS will limit the number of threads the self-tuning thread pool will allocate to the partition to satisfy its minimum threads constraints. A value of 0 means no limit is imposed on the partition. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SharedCapacityPercent")) {
         getterName = "getSharedCapacityPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSharedCapacityPercent";
         }

         currentResult = new PropertyDescriptor("SharedCapacityPercent", PartitionWorkManagerMBean.class, getterName, setterName);
         descriptors.put("SharedCapacityPercent", currentResult);
         currentResult.setValue("description", "The total number of requests that can be present in the server for a partition, as a percentage of the sharedCapacityForWorkManagers attribute value in OverloadProtection MBean. This includes requests that are enqueued and those under execution. ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
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
