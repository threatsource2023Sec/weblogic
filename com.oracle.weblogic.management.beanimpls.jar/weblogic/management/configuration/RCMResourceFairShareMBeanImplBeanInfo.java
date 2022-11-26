package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class RCMResourceFairShareMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = RCMResourceFairShareMBean.class;

   public RCMResourceFairShareMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RCMResourceFairShareMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.RCMResourceFairShareMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> The {@code RCMResourceFairShareMBean} is used to configure the Fair Share policy for a resource type. </p> <p> A Fair share policy is typically used to ensure that a fixed-size shared resource is shared effectively (yet fairly) by competing consumers. A Fair share policy may also be employed by a system administrator when a clear understanding of the exact usage of a resource by a Partition cannot be accurately determined in advance, and the system administrator would like efficient utilization of resources while ensuring fair allocation of shared resources to co-resident Partitions. </p> <p> A RCM fair-share policy provides the following assurance to the system administrator (in line with work manager's definition of fair-share): When there is \"contention\" for a resource, and there is \"uniform load\" across multiple Partitions \"over a period of time\", the share of the resource allocated to these Partitions is \"roughly\" in ratio of their configured fair shares. </p> <p> A system administrator allocates a 'share' to a Partition by specifying an integer value to let the fair share policy know the share of resources that must be allocated to the Partition during contention over time. The sum of all Partition shares need not equal 100. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.RCMResourceFairShareMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("FairShareConstraint")) {
         String getterName = "getFairShareConstraint";
         String setterName = null;
         currentResult = new PropertyDescriptor("FairShareConstraint", RCMResourceFairShareMBean.class, getterName, (String)setterName);
         descriptors.put("FairShareConstraint", currentResult);
         currentResult.setValue("description", "Gets the Fair Share policy configured for this resource type. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFairShareConstraint");
         currentResult.setValue("creator", "createFairShareConstraint");
         currentResult.setValue("creator", "createFairShareConstraint");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = RCMResourceFairShareMBean.class.getMethod("createFairShareConstraint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the fair share policy configuration ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for fair share policy configurations for this resource type. <p> The new {@code FairShareConstraintMBean} that is created will have the resource type MBean as its parent and must be destroyed with the {@link #destroyFairShareConstraint(FairShareConstraintMBean)} method. </p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FairShareConstraint");
            currentResult.setValue("since", "12.2.1.3.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      mth = RCMResourceFairShareMBean.class.getMethod("createFairShareConstraint", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the fair share policy configuration "), createParameterDescriptor("value", "The share of resources that must be allocated to the Partition during contention over time ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for fair share policy configurations for this resource type. <p> The new {@code FairShareConstraintMBean} that is created will have the resource type MBean as its parent and must be destroyed with the {@link #destroyFairShareConstraint(FairShareConstraintMBean)} method. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FairShareConstraint");
      }

      mth = RCMResourceFairShareMBean.class.getMethod("destroyFairShareConstraint", FairShareConstraintMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("fairShareConstraintMBean", "The FairShareConstraintMBean to be removed from this resource type MBean. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys and removes a fair share policy configuration corresponding to the {code fairshareConstraintMBean} parameter, which is a child of this resource type MBean. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FairShareConstraint");
      }

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
