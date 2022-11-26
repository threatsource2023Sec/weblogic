package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ConnectionDefinitionBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConnectionDefinitionBean.class;

   public ConnectionDefinitionBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionDefinitionBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.ConnectionDefinitionBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.ConnectionDefinitionBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConfigProperties")) {
         getterName = "getConfigProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigProperties", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ConfigProperties", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConfigProperty");
         currentResult.setValue("creator", "createConfigProperty");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryImplClass")) {
         getterName = "getConnectionFactoryImplClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryImplClass";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryImplClass", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryImplClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryInterface")) {
         getterName = "getConnectionFactoryInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryInterface";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryInterface", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryInterface", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionImplClass")) {
         getterName = "getConnectionImplClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionImplClass";
         }

         currentResult = new PropertyDescriptor("ConnectionImplClass", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ConnectionImplClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionInterface")) {
         getterName = "getConnectionInterface";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionInterface";
         }

         currentResult = new PropertyDescriptor("ConnectionInterface", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ConnectionInterface", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedConnectionFactoryClass")) {
         getterName = "getManagedConnectionFactoryClass";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagedConnectionFactoryClass";
         }

         currentResult = new PropertyDescriptor("ManagedConnectionFactoryClass", ConnectionDefinitionBean.class, getterName, setterName);
         descriptors.put("ManagedConnectionFactoryClass", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConnectionDefinitionBean.class.getMethod("createConfigProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigProperties");
      }

      mth = ConnectionDefinitionBean.class.getMethod("destroyConfigProperty", ConfigPropertyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigProperties");
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
