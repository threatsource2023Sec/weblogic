package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class MessageFilterBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = MessageFilterBean.class;

   public MessageFilterBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public MessageFilterBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.MessageFilterBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("MessageFilter bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.MessageFilterBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MessageFilterClass")) {
         getterName = "getMessageFilterClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageFilterClass";
         }

         currentResult = new PropertyDescriptor("MessageFilterClass", MessageFilterBean.class, getterName, setterName);
         descriptors.put("MessageFilterClass", currentResult);
         currentResult.setValue("description", "Specify message filter's class name. A message filter class must implement interface com.bea.httppubsub.MessageFilter ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MessageFilterName")) {
         getterName = "getMessageFilterName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageFilterName";
         }

         currentResult = new PropertyDescriptor("MessageFilterName", MessageFilterBean.class, getterName, setterName);
         descriptors.put("MessageFilterName", currentResult);
         currentResult.setValue("description", "Define a message filter's name ");
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
