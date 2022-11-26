package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ConfigurationSupportBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConfigurationSupportBean.class;

   public ConfigurationSupportBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConfigurationSupportBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConfigurationSupportBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConfigurationSupportBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("BaseNamespace")) {
         getterName = "getBaseNamespace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseNamespace";
         }

         currentResult = new PropertyDescriptor("BaseNamespace", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("BaseNamespace", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BasePackageName")) {
         getterName = "getBasePackageName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBasePackageName";
         }

         currentResult = new PropertyDescriptor("BasePackageName", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("BasePackageName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseRootElement")) {
         getterName = "getBaseRootElement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseRootElement";
         }

         currentResult = new PropertyDescriptor("BaseRootElement", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("BaseRootElement", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BaseUri")) {
         getterName = "getBaseUri";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBaseUri";
         }

         currentResult = new PropertyDescriptor("BaseUri", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("BaseUri", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigNamespace")) {
         getterName = "getConfigNamespace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigNamespace";
         }

         currentResult = new PropertyDescriptor("ConfigNamespace", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("ConfigNamespace", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigPackageName")) {
         getterName = "getConfigPackageName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigPackageName";
         }

         currentResult = new PropertyDescriptor("ConfigPackageName", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("ConfigPackageName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigRootElement")) {
         getterName = "getConfigRootElement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigRootElement";
         }

         currentResult = new PropertyDescriptor("ConfigRootElement", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("ConfigRootElement", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigUri")) {
         getterName = "getConfigUri";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigUri";
         }

         currentResult = new PropertyDescriptor("ConfigUri", ConfigurationSupportBean.class, getterName, setterName);
         descriptors.put("ConfigUri", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("legalNull", Boolean.TRUE);
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
