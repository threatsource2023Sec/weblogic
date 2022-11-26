package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ConfigurationPropertiesMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConfigurationPropertiesMBean.class;

   public ConfigurationPropertiesMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConfigurationPropertiesMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ConfigurationPropertiesMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("The ConfigurationPropertiesMBean is used when performing macro substitution from elements in the ServerTemplateMBeans or ResourceGroupTemplateMBean. If a macro is encountered in the template attribute, then the macro name will be used to locate the corresponding ConfigurationPropertyMBean in a parent MBean. This may be at the Partition, Server, ServerTemplate or Domain level. If found, the value will be substituted accordingly. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ConfigurationPropertiesMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      if (!descriptors.containsKey("ConfigurationProperties")) {
         String getterName = "getConfigurationProperties";
         String setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationProperties", ConfigurationPropertiesMBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationProperties", currentResult);
         currentResult.setValue("description", "<p>Specifies the list of properties that are associated with this container object.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConfigurationProperty");
         currentResult.setValue("destroyer", "destroyConfigurationProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConfigurationPropertiesMBean.class.getMethod("createConfigurationProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of ConfigurationProperty ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create ConfigurationProperty object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationProperties");
      }

      mth = ConfigurationPropertiesMBean.class.getMethod("destroyConfigurationProperty", ConfigurationPropertyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "ConfigurationProperty mbean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy ConfigurationProperty object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConfigurationProperties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConfigurationPropertiesMBean.class.getMethod("lookupConfigurationProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of ConfigurationProperty ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up ConfigurationProperty object</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConfigurationProperties");
      }

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
