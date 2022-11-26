package weblogic.diagnostics.harvester.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WLDFPartitionHarvesterRuntimeMBean;

public class PartitionHarvesterRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFPartitionHarvesterRuntimeMBean.class;

   public PartitionHarvesterRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionHarvesterRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.harvester.internal.PartitionHarvesterRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("notificationTranslator", "weblogic.diagnostics.harvester.internal.RuntimeMBeanNotificationTranslator");
      beanDescriptor.setValue("package", "weblogic.diagnostics.harvester.internal");
      String description = (new String("<p>Provides aggregated information about all active Harvester configurations for a partition. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFPartitionHarvesterRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AverageSamplingTime")) {
         getterName = "getAverageSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageSamplingTime", WLDFPartitionHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The average amount of time, in nanoseconds, spent in sampling cycles. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaximumSamplingTime")) {
         getterName = "getMaximumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaximumSamplingTime", WLDFPartitionHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaximumSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The maximum sampling time, in nanoseconds. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinimumSamplingTime")) {
         getterName = "getMinimumSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MinimumSamplingTime", WLDFPartitionHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MinimumSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The minimum sampling time, in nanoseconds. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSamplingCycles")) {
         getterName = "getTotalSamplingCycles";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingCycles", WLDFPartitionHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingCycles", currentResult);
         currentResult.setValue("description", "<p> The total number of sampling cycles taken thus far. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TotalSamplingTime")) {
         getterName = "getTotalSamplingTime";
         setterName = null;
         currentResult = new PropertyDescriptor("TotalSamplingTime", WLDFPartitionHarvesterRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TotalSamplingTime", currentResult);
         currentResult.setValue("description", "<p> The total amount of time, in nanoseconds, spent in sampling cycles. </p> ");
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
