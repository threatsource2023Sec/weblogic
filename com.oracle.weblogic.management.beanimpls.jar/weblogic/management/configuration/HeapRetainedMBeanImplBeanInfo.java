package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class HeapRetainedMBeanImplBeanInfo extends RCMResourceMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = HeapRetainedMBean.class;

   public HeapRetainedMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HeapRetainedMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.HeapRetainedMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("RCMResourceMBean"), BeanInfoHelper.encodeEntities("RCMResourceFairShareMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p> A {@code HeapRetainedMBean} represents a resource consumption management policy for the \"Heap Retained\" resource type. </p> <p> The \"Heap Retained\" resource type tracks the amount of Heap memory retained (in use) by the Partition. </p> <p> Triggers (Usage limits) and Fair share policies can be defined for a \"Heap Retained\" resource type through this MBena. </p> <p> The valid set of recourse action types for the \"Heap Retained\" resource type are: </p> <ul> <li>notify</li> <li>slow</li> <li>shutdown</li> </ul> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.HeapRetainedMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("FairShareConstraint")) {
         String getterName = "getFairShareConstraint";
         String setterName = null;
         currentResult = new PropertyDescriptor("FairShareConstraint", HeapRetainedMBean.class, getterName, (String)setterName);
         descriptors.put("FairShareConstraint", currentResult);
         currentResult.setValue("description", "Gets the Fair Share policy configured for this resource type. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFairShareConstraint");
         currentResult.setValue("creator", "createFairShareConstraint");
         currentResult.setValue("creator", "createFairShareConstraint");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion)) {
         mth = HeapRetainedMBean.class.getMethod("createFairShareConstraint", String.class);
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

      mth = HeapRetainedMBean.class.getMethod("createFairShareConstraint", String.class, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "Name of the fair share policy configuration "), createParameterDescriptor("value", "The share of resources that must be allocated to the Partition during contention over time ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This is the factory method for fair share policy configurations for this resource type. <p> The new {@code FairShareConstraintMBean} that is created will have the resource type MBean as its parent and must be destroyed with the {@link #destroyFairShareConstraint(FairShareConstraintMBean)} method. </p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FairShareConstraint");
      }

      mth = HeapRetainedMBean.class.getMethod("destroyFairShareConstraint", FairShareConstraintMBean.class);
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
