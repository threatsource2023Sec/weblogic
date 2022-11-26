package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ExecuteQueueMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ExecuteQueueMBean.class;

   public ExecuteQueueMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ExecuteQueueMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ExecuteQueueMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This bean is used to configure an execute queue and its associated thread pool. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ExecuteQueueMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("QueueLength")) {
         getterName = "getQueueLength";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQueueLength";
         }

         currentResult = new PropertyDescriptor("QueueLength", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("QueueLength", currentResult);
         currentResult.setValue("description", "<p>The maximum number of simultaneous requests that this server can hold in the queue.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(65536));
         currentResult.setValue("legalMax", new Integer(1073741824));
         currentResult.setValue("legalMin", new Integer(256));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueueLengthThresholdPercent")) {
         getterName = "getQueueLengthThresholdPercent";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setQueueLengthThresholdPercent";
         }

         currentResult = new PropertyDescriptor("QueueLengthThresholdPercent", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("QueueLengthThresholdPercent", currentResult);
         currentResult.setValue("description", "<p>The percentage of the Queue Length size that can be reached before this server indicates an overflow condition for the queue. If the overflow condition is reached and the current thread count has not reached the ThreadsMaximum value, then ThreadsIncrease number of threads are added.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(90));
         currentResult.setValue("legalMax", new Integer(99));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadCount")) {
         getterName = "getThreadCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreadCount";
         }

         currentResult = new PropertyDescriptor("ThreadCount", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("ThreadCount", currentResult);
         currentResult.setValue("description", "<p>The number of threads assigned to this queue.</p> ");
         currentResult.setValue("restProductionModeDefault", new Integer(25));
         setPropertyDescriptorDefault(currentResult, new Integer(15));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadsIncrease")) {
         getterName = "getThreadsIncrease";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreadsIncrease";
         }

         currentResult = new PropertyDescriptor("ThreadsIncrease", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("ThreadsIncrease", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of threads to increase the queue length when the queue length theshold is reached. This threshold is determined by the QueueLengthThresholdPercent value.</p>  <p>The following consideration applies to the dynamic nature of ThreadsMaximum and ThreadsIncrease attributes. If any of these attributes change during runtime, the changed value comes into effect when the next request is submitted to the execute queue and the scheduler decides to increase threads depending on the queue threshold conditions.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadsMaximum")) {
         getterName = "getThreadsMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreadsMaximum";
         }

         currentResult = new PropertyDescriptor("ThreadsMaximum", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("ThreadsMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of threads that this queue is allowed to have; this value prevents WebLogic Server from creating an overly high thread count in the queue in response to continual overflow conditions.</p>  <p>A note about dynamic nature of ThreadsMaximum and ThreadsIncrease attributes. If any of these attributes change during runtime, the changed value comes into effect when the next request is submitted to the execute queue and the scheduler decides to increase threads depending on the queue threshold conditions. Imagine a scenario where the queue capacity has already reached the max threshold and the current thread count is already equal to ThreadsMaximum value. If more work is coming into the queue and the administrator wishes to increase the ThreadsMaximum a little to add a few more threads, he/she can do so by changing these attributes dynamically. Please note that the changed value is evaluated when the next request is submitted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(400));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadsMinimum")) {
         getterName = "getThreadsMinimum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreadsMinimum";
         }

         currentResult = new PropertyDescriptor("ThreadsMinimum", ExecuteQueueMBean.class, getterName, setterName);
         descriptors.put("ThreadsMinimum", currentResult);
         currentResult.setValue("description", "<p>The minimum number of threads that WebLogic Server will maintain in the queue.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(0));
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
