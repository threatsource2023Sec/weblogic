package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PartitionFairShareRuntimeMBean;

public class PartitionFairShareRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionFairShareRuntimeMBean.class;

   public PartitionFairShareRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionFairShareRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.PartitionFairShareRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("Monitoring information for PartitionFairShare ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionFairShareRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConfiguredFairShare")) {
         getterName = "getConfiguredFairShare";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredFairShare", PartitionFairShareRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredFairShare", currentResult);
         currentResult.setValue("description", "<p>The configured fair share value.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FairShare")) {
         getterName = "getFairShare";
         setterName = null;
         currentResult = new PropertyDescriptor("FairShare", PartitionFairShareRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FairShare", currentResult);
         currentResult.setValue("description", "<p>The current fair share value. This could be different from the configured value as Resource Consumption Management could dynamically adjust the actual fair share value based on configured RCM policies and actual thread usage by the partition</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PartitionAdjuster")) {
         getterName = "getPartitionAdjuster";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionAdjuster", PartitionFairShareRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PartitionAdjuster", currentResult);
         currentResult.setValue("description", "<p>The multiplier to be used to adjust the priority of requests from the partition in the request queue.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ThreadUse")) {
         getterName = "getThreadUse";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadUse", PartitionFairShareRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ThreadUse", currentResult);
         currentResult.setValue("description", "<p>Total amount of thread use time in milliseconds used by the partition during the past period.</p> ");
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
