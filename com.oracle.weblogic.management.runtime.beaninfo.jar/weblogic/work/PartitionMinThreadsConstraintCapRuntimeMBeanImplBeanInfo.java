package weblogic.work;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.PartitionMinThreadsConstraintCapRuntimeMBean;

public class PartitionMinThreadsConstraintCapRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = PartitionMinThreadsConstraintCapRuntimeMBean.class;

   public PartitionMinThreadsConstraintCapRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PartitionMinThreadsConstraintCapRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.work.PartitionMinThreadsConstraintCapRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.work");
      String description = (new String("Monitoring information for PartitionMinThreadsConstraint ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.PartitionMinThreadsConstraintCapRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ExecutingRequests")) {
         getterName = "getExecutingRequests";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecutingRequests", PartitionMinThreadsConstraintCapRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ExecutingRequests", currentResult);
         currentResult.setValue("description", "<p>Number of requests that are currently executing.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SumMinThreadsConstraints")) {
         getterName = "getSumMinThreadsConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("SumMinThreadsConstraints", PartitionMinThreadsConstraintCapRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SumMinThreadsConstraints", currentResult);
         currentResult.setValue("description", "<p>Sum of all configured minimum threads constraints in the partition. This is the number of threads that is needed in order to satisfy all configured minimum threads constraints in the partition if all of them have more than that amount of concurrent work requests.</p> ");
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
