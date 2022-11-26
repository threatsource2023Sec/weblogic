package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class CalloutMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = CalloutMBean.class;

   public CalloutMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CalloutMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.CalloutMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents a Callout that resides under <code>DomainMBean</code>.  Callouts are not effective until the transaction is committed.  So a change that adds a callout SampleCallout is not called until the next change after SampleCallout is added. </p>  Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.CalloutMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Argument")) {
         getterName = "getArgument";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArgument";
         }

         currentResult = new PropertyDescriptor("Argument", CalloutMBean.class, getterName, setterName);
         descriptors.put("Argument", currentResult);
         currentResult.setValue("description", "<p>Returns the argument that will be passed to the class </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassName";
         }

         currentResult = new PropertyDescriptor("ClassName", CalloutMBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "<p>Returns the  value of the className attribute which is the fully qualified name of a class to load and run.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HookPoint")) {
         getterName = "getHookPoint";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHookPoint";
         }

         currentResult = new PropertyDescriptor("HookPoint", CalloutMBean.class, getterName, setterName);
         descriptors.put("HookPoint", currentResult);
         currentResult.setValue("description", "<p>Returns the hookpoint that the callout is tied to. </p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
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
