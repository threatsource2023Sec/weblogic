package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PartitionWorkManagerRuntimeMBean;

public class PartitionWorkManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionWorkManagerRuntimeMBean.class;

   public PartitionWorkManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionWorkManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.PartitionWorkManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("Monitoring information for PartitionWorkManager ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionWorkManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("FairShareRuntime")) {
         getterName = "getFairShareRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("FairShareRuntime", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FairShareRuntime", currentResult);
         currentResult.setValue("description", "<p>The PartitionFairShareRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "Copyright (c) 2014,2015, Oracle and/or its affiliates. All rights reserved.");
      }

      if (!descriptors.containsKey("MaxThreadsConstraintRuntime")) {
         getterName = "getMaxThreadsConstraintRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraintRuntime", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxThreadsConstraintRuntime", currentResult);
         currentResult.setValue("description", "<p>The MaxThreadsConstraintRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinThreadsConstraintCapRuntime")) {
         getterName = "getMinThreadsConstraintCapRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintCapRuntime", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinThreadsConstraintCapRuntime", currentResult);
         currentResult.setValue("description", "<p>The PartitionMinThreadsConstraintRuntimeMBean for this partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("excludeFromRest", "Copyright (c) 2014,2015, Oracle and/or its affiliates. All rights reserved.");
      }

      if (!descriptors.containsKey("OverloadRejectedRequestsCount")) {
         getterName = "getOverloadRejectedRequestsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OverloadRejectedRequestsCount", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("OverloadRejectedRequestsCount", currentResult);
         currentResult.setValue("description", "<p>Number of requests rejected due to configured Shared Capacity for work managers have been reached.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PendingUserRequestCount")) {
         getterName = "getPendingUserRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingUserRequestCount", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PendingUserRequestCount", currentResult);
         currentResult.setValue("description", "<p>The number of pending user requests in the priority queue. The priority queue contains requests from internal subsystems and users. This is just the count of all user requests.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SharedCapacityForWorkManagers")) {
         getterName = "getSharedCapacityForWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("SharedCapacityForWorkManagers", PartitionWorkManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SharedCapacityForWorkManagers", currentResult);
         currentResult.setValue("description", "<p>Maximum amount of requests that can be accepted in the priority queue. Note that a request with higher priority will be accepted in place of a lower priority request already in the queue even after the threshold is reached. The lower priority request is kept waiting in the queue till all high priority requests are executed. Also note that further enqueues of the low priority requests are rejected right away. </p> ");
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
