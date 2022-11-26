package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFConfigurationPropertiesBeanImplBeanInfo extends WLDFConfigurationPropertyBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFConfigurationPropertiesBean.class;

   public WLDFConfigurationPropertiesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFConfigurationPropertiesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p>Represents a set of {@link WLDFPropertyBean} and {@link WLDFEncryptedPropertyBean} instances, akin to a <code>java.util.Properties</code> object.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFConfigurationPropertiesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ConfigurationProperties")) {
         getterName = "getConfigurationProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationProperties", WLDFConfigurationPropertiesBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationProperties", currentResult);
         currentResult.setValue("description", "<p>Returns the list of all WLDFPropertyBean and WLDFEncryptedPropertyBean objects that are associated with this container object.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncryptedProperties")) {
         getterName = "getEncryptedProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("EncryptedProperties", WLDFConfigurationPropertiesBean.class, getterName, (String)setterName);
         descriptors.put("EncryptedProperties", currentResult);
         currentResult.setValue("description", "Returns all the encrypted properties for the action. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyEncryptedProperty");
         currentResult.setValue("creator", "createEncryptedProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", WLDFConfigurationPropertiesBean.class, getterName, (String)setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Returns all WLDFPopertyBean instances. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createProperty");
         currentResult.setValue("destroyer", "destroyProperty");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFConfigurationPropertiesBean.class.getMethod("createProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of WLDFPropertyBean ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an unencrypted WLDFPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFConfigurationPropertiesBean.class.getMethod("destroyProperty", WLDFPropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyBean", "The WLDFPropertyBean instance to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys a WLDFPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFConfigurationPropertiesBean.class.getMethod("createEncryptedProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WLDFEncryptedPropertyBean ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates an encrypted WLDFEncryptedPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EncryptedProperties");
      }

      mth = WLDFConfigurationPropertiesBean.class.getMethod("destroyEncryptedProperty", WLDFEncryptedPropertyBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("propertyBean", "The WLDFEncryptedPropertyBean instance to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys a WLDFEncryptedPropertyBean object on the container</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EncryptedProperties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = WLDFConfigurationPropertiesBean.class.getMethod("lookupProperty", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of WLDFPropertyBean to find ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up WLDFPropertyBean.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Properties");
      }

      mth = WLDFConfigurationPropertiesBean.class.getMethod("lookupEncryptedProperty", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of WLDFEncryptedPropertyBean to find ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Look up a WLDFEncryptedPropertyBean.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "EncryptedProperties");
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
