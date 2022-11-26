package weblogic.diagnostics.partition;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.WLDFPartitionRuntimeMBean;

public class WLDFPartitionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFPartitionRuntimeMBean.class;

   public WLDFPartitionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFPartitionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.partition.WLDFPartitionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.partition");
      String description = (new String("<p>This interface provides access to all the runtime MBeans for the WebLogic Diagnostic Framework (WLDF).</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.WLDFPartitionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("WLDFPartitionAccessRuntime")) {
         getterName = "getWLDFPartitionAccessRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFPartitionAccessRuntime", WLDFPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFPartitionAccessRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this partition's view of its diagnostic accessor.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFPartitionHarvesterRuntime")) {
         getterName = "getWLDFPartitionHarvesterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFPartitionHarvesterRuntime", WLDFPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFPartitionHarvesterRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this partition's view of its diagnostic harvester.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFPartitionImageRuntime")) {
         getterName = "getWLDFPartitionImageRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFPartitionImageRuntime", WLDFPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFPartitionImageRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this partition's view of its diagnostic image source.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLDFWatchNotificationRuntime")) {
         getterName = "getWLDFWatchNotificationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFWatchNotificationRuntime", WLDFPartitionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WLDFWatchNotificationRuntime", currentResult);
         currentResult.setValue("description", "<p>The MBean that represents this partition's view of its diagnostic policy and action component.</p> ");
         currentResult.setValue("relationship", "containment");
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
