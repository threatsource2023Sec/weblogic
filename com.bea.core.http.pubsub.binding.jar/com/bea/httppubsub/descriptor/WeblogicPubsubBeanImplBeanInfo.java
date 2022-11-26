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

public class WeblogicPubsubBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = WeblogicPubsubBean.class;

   public WeblogicPubsubBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WeblogicPubsubBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.WeblogicPubsubBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Weblogic-pubsub bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.WeblogicPubsubBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ChannelConstraints")) {
         getterName = "getChannelConstraints";
         setterName = null;
         currentResult = new PropertyDescriptor("ChannelConstraints", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("ChannelConstraints", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createChannelConstraint");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Channels")) {
         getterName = "getChannels";
         setterName = null;
         currentResult = new PropertyDescriptor("Channels", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("Channels", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createChannel");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsHandlerMappings")) {
         getterName = "getJmsHandlerMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("JmsHandlerMappings", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("JmsHandlerMappings", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJmsHandlerMapping");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageFilters")) {
         getterName = "getMessageFilters";
         setterName = null;
         currentResult = new PropertyDescriptor("MessageFilters", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("MessageFilters", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMessageFilter");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerConfig")) {
         getterName = "getServerConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerConfig", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("ServerConfig", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createServerConfig");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Services")) {
         getterName = "getServices";
         setterName = null;
         currentResult = new PropertyDescriptor("Services", WeblogicPubsubBean.class, getterName, (String)setterName);
         descriptors.put("Services", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createService");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WeblogicPubsubBean.class.getMethod("createServerConfig");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServerConfig");
      }

      mth = WeblogicPubsubBean.class.getMethod("createJmsHandlerMapping");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JmsHandlerMappings");
      }

      mth = WeblogicPubsubBean.class.getMethod("createChannel");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Channels");
      }

      mth = WeblogicPubsubBean.class.getMethod("createChannelConstraint");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ChannelConstraints");
      }

      mth = WeblogicPubsubBean.class.getMethod("createMessageFilter");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessageFilters");
      }

      mth = WeblogicPubsubBean.class.getMethod("createService");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Services");
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
