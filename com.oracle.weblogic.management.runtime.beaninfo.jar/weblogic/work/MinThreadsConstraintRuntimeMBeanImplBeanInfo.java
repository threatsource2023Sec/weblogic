package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.MinThreadsConstraintRuntimeMBean;

public class MinThreadsConstraintRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MinThreadsConstraintRuntimeMBean.class;

   public MinThreadsConstraintRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MinThreadsConstraintRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.MinThreadsConstraintRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("Monitoring information for MinThreadsConstraint ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MinThreadsConstraintRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("CompletedRequests")) {
         getterName = "getCompletedRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("CompletedRequests", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CompletedRequests", currentResult);
         currentResult.setValue("description", "<p>Completed request count.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConfiguredCount")) {
         getterName = "getConfiguredCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredCount", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredCount", currentResult);
         currentResult.setValue("description", "<p>The configured count, or minimum concurrency value.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Count")) {
         getterName = "getCount";
         setterName = null;
         currentResult = new PropertyDescriptor("Count", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Count", currentResult);
         currentResult.setValue("description", "<p>The current minimum concurrency value. This could be different from the configured value as Resource Consumption Management could dynamically reduce the allowed minimum concurrency value based on configured RCM policies and actual thread usage by the partition</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("CurrentWaitTime")) {
         getterName = "getCurrentWaitTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentWaitTime", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentWaitTime", currentResult);
         currentResult.setValue("description", "<p>The last measured time a request had to wait for a thread, in milliseconds. Only requests whose execution is needed to satisfy the constraint are considered.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutingRequests")) {
         getterName = "getExecutingRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutingRequests", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutingRequests", currentResult);
         currentResult.setValue("description", "<p>Number of requests that are currently executing.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxWaitTime")) {
         getterName = "getMaxWaitTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxWaitTime", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxWaitTime", currentResult);
         currentResult.setValue("description", "<p>The max time a request had to wait for a thread, in milliseconds. Only requests whose execution is needed to satisfy the constraint are considered.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MustRunCount")) {
         getterName = "getMustRunCount";
         setterName = null;
         currentResult = new PropertyDescriptor("MustRunCount", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MustRunCount", currentResult);
         currentResult.setValue("description", "<p>Number of requests that must be executed to satisfy the constraint.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OutOfOrderExecutionCount")) {
         getterName = "getOutOfOrderExecutionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OutOfOrderExecutionCount", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OutOfOrderExecutionCount", currentResult);
         currentResult.setValue("description", "<p>Number of requests executed out of turn to satisfy this constraint.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingRequests")) {
         getterName = "getPendingRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRequests", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingRequests", currentResult);
         currentResult.setValue("description", "<p>Pending requests that are waiting for an available thread.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionLimitReached")) {
         getterName = "isPartitionLimitReached";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionLimitReached", MinThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionLimitReached", currentResult);
         currentResult.setValue("description", "<p>Whether the partition-level minimum threads constraint limit has reached, which could cause the number of executing requests to be less than the configured value in this minimum threads constraint. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
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
