package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ServiceBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ServiceBean.class;

   public ServiceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServiceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.ServiceBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Service bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.ServiceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ServiceChannel")) {
         getterName = "getServiceChannel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceChannel";
         }

         currentResult = new PropertyDescriptor("ServiceChannel", ServiceBean.class, getterName, setterName);
         descriptors.put("ServiceChannel", currentResult);
         currentResult.setValue("description", "Define a service channel, for example: /service/echo ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceClass")) {
         getterName = "getServiceClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceClass";
         }

         currentResult = new PropertyDescriptor("ServiceClass", ServiceBean.class, getterName, setterName);
         descriptors.put("ServiceClass", currentResult);
         currentResult.setValue("description", "Define the class to service this service, for example: EchoService ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServiceMethod")) {
         getterName = "getServiceMethod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceMethod";
         }

         currentResult = new PropertyDescriptor("ServiceMethod", ServiceBean.class, getterName, setterName);
         descriptors.put("ServiceMethod", currentResult);
         currentResult.setValue("description", "Define a service method in the service class. The service method must have only one payload parameter with type 'Object'. For example: Object echo(Object payload). ");
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
