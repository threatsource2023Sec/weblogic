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

public class ChannelBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ChannelBean.class;

   public ChannelBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ChannelBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.ChannelBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Channel-mapping bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.ChannelBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ChannelPattern")) {
         getterName = "getChannelPattern";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setChannelPattern";
         }

         currentResult = new PropertyDescriptor("ChannelPattern", ChannelBean.class, getterName, setterName);
         descriptors.put("ChannelPattern", currentResult);
         currentResult.setValue("description", "Define channel pattern, for examples: /foo/bar, /foo/bar/*, /foo/bar/** ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ChannelPersistence")) {
         getterName = "getChannelPersistence";
         setterName = null;
         currentResult = new PropertyDescriptor("ChannelPersistence", ChannelBean.class, getterName, setterName);
         descriptors.put("ChannelPersistence", currentResult);
         currentResult.setValue("description", "Configure persistence setting for one given channel pattern ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createChannelPersistence");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsHandlerName")) {
         getterName = "getJmsHandlerName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsHandlerName";
         }

         currentResult = new PropertyDescriptor("JmsHandlerName", ChannelBean.class, getterName, setterName);
         descriptors.put("JmsHandlerName", currentResult);
         currentResult.setValue("description", "Enable JMS channel. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageFilters")) {
         getterName = "getMessageFilters";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageFilters", ChannelBean.class, getterName, setterName);
         descriptors.put("MessageFilters", currentResult);
         currentResult.setValue("description", "Configure message filter chain. The message chain consists of message filter names. The sequence of message filters in the chain is their definition sequence in pubsub server descriptor file. ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ChannelBean.class.getMethod("createChannelPersistence");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ChannelPersistence");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ChannelBean.class.getMethod("addMessageFilter", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "MessageFilters");
      }

      mth = ChannelBean.class.getMethod("removeMessageFilter", String.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "MessageFilters");
      }

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
