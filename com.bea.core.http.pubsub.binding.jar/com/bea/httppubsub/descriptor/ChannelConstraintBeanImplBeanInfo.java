package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ChannelConstraintBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ChannelConstraintBean.class;

   public ChannelConstraintBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ChannelConstraintBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.ChannelConstraintBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Channel-constraint bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.ChannelConstraintBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("AuthConstraint")) {
         getterName = "getAuthConstraint";
         setterName = null;
         currentResult = new PropertyDescriptor("AuthConstraint", ChannelConstraintBean.class, getterName, (String)setterName);
         descriptors.put("AuthConstraint", currentResult);
         currentResult.setValue("description", "Authorization constraint defines a list of roles, who have the priviledge to access the defined channel resources. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAuthConstraint");
         currentResult.setValue("creator", "createAuthConstraint");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelResourceCollections")) {
         getterName = "getChannelResourceCollections";
         setterName = null;
         currentResult = new PropertyDescriptor("ChannelResourceCollections", ChannelConstraintBean.class, getterName, (String)setterName);
         descriptors.put("ChannelResourceCollections", currentResult);
         currentResult.setValue("description", "A collection of channel resources. A channel resource defines a list of channels, and a list of operations executing on these channels. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createChannelResourceCollection");
         currentResult.setValue("destroyer", "destroyChannelResourceCollection");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ChannelConstraintBean.class.getMethod("createChannelResourceCollection");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ChannelResourceCollections");
      }

      mth = ChannelConstraintBean.class.getMethod("destroyChannelResourceCollection", ChannelResourceCollectionBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ChannelResourceCollections");
      }

      mth = ChannelConstraintBean.class.getMethod("createAuthConstraint");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthConstraint");
      }

      mth = ChannelConstraintBean.class.getMethod("destroyAuthConstraint", AuthConstraintBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AuthConstraint");
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
