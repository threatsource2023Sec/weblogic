package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class CustomModuleBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = CustomModuleBean.class;

   public CustomModuleBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public CustomModuleBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.CustomModuleBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML custom-moduleType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.CustomModuleBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConfigurationSupport")) {
         getterName = "getConfigurationSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationSupport", CustomModuleBean.class, getterName, setterName);
         descriptors.put("ConfigurationSupport", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConfigurationSupport");
         currentResult.setValue("creator", "createConfigurationSupport");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProviderName")) {
         getterName = "getProviderName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProviderName";
         }

         currentResult = new PropertyDescriptor("ProviderName", CustomModuleBean.class, getterName, setterName);
         descriptors.put("ProviderName", currentResult);
         currentResult.setValue("description", "Gets the \"provider-name\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Uri")) {
         getterName = "getUri";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUri";
         }

         currentResult = new PropertyDescriptor("Uri", CustomModuleBean.class, getterName, setterName);
         descriptors.put("Uri", currentResult);
         currentResult.setValue("description", "Gets the \"uri\" element ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = CustomModuleBean.class.getMethod("createConfigurationSupport");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationSupport");
      }

      mth = CustomModuleBean.class.getMethod("destroyConfigurationSupport", ConfigurationSupportBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationSupport");
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
