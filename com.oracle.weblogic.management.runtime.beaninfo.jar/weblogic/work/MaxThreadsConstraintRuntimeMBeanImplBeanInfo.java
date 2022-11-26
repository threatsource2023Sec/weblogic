package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean;

public class MaxThreadsConstraintRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = MaxThreadsConstraintRuntimeMBean.class;

   public MaxThreadsConstraintRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MaxThreadsConstraintRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.MaxThreadsConstraintRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("Runtime information for MaxThreadsConstraint ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.MaxThreadsConstraintRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConfiguredCount")) {
         getterName = "getConfiguredCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredCount", MaxThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredCount", currentResult);
         currentResult.setValue("description", "<p>The configured count, or maximum concurrency value.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Count")) {
         getterName = "getCount";
         setterName = null;
         currentResult = new PropertyDescriptor("Count", MaxThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Count", currentResult);
         currentResult.setValue("description", "<p>The current maximum concurrency value. This could be different from the configured value as Resource Consumption Management could dynamically reduce the allowed maximum concurrency value based on configured RCM policies and actual thread usage by the partition</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DeferredRequests")) {
         getterName = "getDeferredRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("DeferredRequests", MaxThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeferredRequests", currentResult);
         currentResult.setValue("description", "<p>Number of requests that are denied a thread for execution because the constraint is exceeded.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecutingRequests")) {
         getterName = "getExecutingRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutingRequests", MaxThreadsConstraintRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutingRequests", currentResult);
         currentResult.setValue("description", "<p>Number of requests that are currently executing.</p> ");
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
