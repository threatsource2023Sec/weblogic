package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class JmsHandlerBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JmsHandlerBean.class;

   public JmsHandlerBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JmsHandlerBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.JmsHandlerBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Jms-handler bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.JmsHandlerBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionFactoryJndiName")) {
         getterName = "getConnectionFactoryJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryJndiName";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryJndiName", JmsHandlerBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryJndiName", currentResult);
         currentResult.setValue("description", "Specify JMS connection factory's JNDI name. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JmsProviderUrl")) {
         getterName = "getJmsProviderUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJmsProviderUrl";
         }

         currentResult = new PropertyDescriptor("JmsProviderUrl", JmsHandlerBean.class, getterName, setterName);
         descriptors.put("JmsProviderUrl", currentResult);
         currentResult.setValue("description", "Specify JMS provider's URL. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TopicJndiName")) {
         getterName = "getTopicJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTopicJndiName";
         }

         currentResult = new PropertyDescriptor("TopicJndiName", JmsHandlerBean.class, getterName, setterName);
         descriptors.put("TopicJndiName", currentResult);
         currentResult.setValue("description", "Specify JNDI name of JMS topics mapped to the given channel pattern. ");
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
