package weblogic.elasticity;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ElasticServiceManagerRuntimeMBean;

public class ElasticServiceManagerRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ElasticServiceManagerRuntimeMBean.class;

   public ElasticServiceManagerRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ElasticServiceManagerRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.elasticity.ElasticServiceManagerRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.elasticity");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ElasticServiceManagerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ScalingTasks")) {
         String getterName = "getScalingTasks";
         String setterName = null;
         currentResult = new PropertyDescriptor("ScalingTasks", ElasticServiceManagerRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ScalingTasks", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         Method mth = ElasticServiceManagerRuntimeMBean.class.getMethod("lookupScalingTask", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Scaling task name ")};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Look up named scaling task ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ScalingTasks");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ElasticServiceManagerRuntimeMBean.class.getMethod("getScalingTasks", String.class, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("clusterName", "Cluster name "), createParameterDescriptor("size", "The number of ScalingTaskRuntimeMBean to return starting with the most recent. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
      }

      mth = ElasticServiceManagerRuntimeMBean.class.getMethod("scaleUp", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("clusterName", "Cluster name "), createParameterDescriptor("scaleFactor", "the size by which the service must be scaled up ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Perform a scale up operation ");
         currentResult.setValue("role", "operation");
      }

      mth = ElasticServiceManagerRuntimeMBean.class.getMethod("scaleDown", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("clusterName", "Cluster name "), createParameterDescriptor("scaleFactor", "the size by which the service must be scaled up ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Perform a scale down operation ");
         currentResult.setValue("role", "operation");
      }

      mth = ElasticServiceManagerRuntimeMBean.class.getMethod("purgeScalingTasks", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("clusterName", "Cluster name "), createParameterDescriptor("age", "Age of tasks to be purged in seconds ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Purge scaling tasks for a service which have completed at least \"age\" seconds ago. Tasks which are currently running will not be purged. ");
         currentResult.setValue("role", "operation");
      }

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
