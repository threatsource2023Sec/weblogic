package com.bea.httppubsub.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ChannelPersistenceBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ChannelPersistenceBean.class;

   public ChannelPersistenceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ChannelPersistenceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("com.bea.httppubsub.descriptor.ChannelPersistenceBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.0.0");
      beanDescriptor.setValue("package", "com.bea.httppubsub.descriptor");
      String description = (new String("Channel persistence bean interface. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "com.bea.httppubsub.descriptor.ChannelPersistenceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("MaxPersistentMessageDurationSecs")) {
         getterName = "getMaxPersistentMessageDurationSecs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPersistentMessageDurationSecs";
         }

         currentResult = new PropertyDescriptor("MaxPersistentMessageDurationSecs", ChannelPersistenceBean.class, getterName, setterName);
         descriptors.put("MaxPersistentMessageDurationSecs", currentResult);
         currentResult.setValue("description", "Define max persistent period in seconds for how long persistent messages should be persisted on one given channel pattern. The default value is 3600 seconds. ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistentStore")) {
         getterName = "getPersistentStore";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistentStore";
         }

         currentResult = new PropertyDescriptor("PersistentStore", ChannelPersistenceBean.class, getterName, setterName);
         descriptors.put("PersistentStore", currentResult);
         currentResult.setValue("description", "Define name of file store used for message persistence. ");
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
