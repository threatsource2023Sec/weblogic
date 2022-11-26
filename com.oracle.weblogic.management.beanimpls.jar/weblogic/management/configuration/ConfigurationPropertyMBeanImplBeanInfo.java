package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class ConfigurationPropertyMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConfigurationPropertyMBean.class;

   public ConfigurationPropertyMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConfigurationPropertyMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ConfigurationPropertyMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Encapsulates information about a property, such as its value and whether it is encrypted.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ConfigurationPropertyMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("EncryptedValue")) {
         getterName = "getEncryptedValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptedValue";
         }

         currentResult = new PropertyDescriptor("EncryptedValue", ConfigurationPropertyMBean.class, getterName, setterName);
         descriptors.put("EncryptedValue", currentResult);
         currentResult.setValue("description", "<p>Specifies the decrypted value of the property.</p>  <p>Note: In release 10.3.1 of WebLogic Server, the behavior of the MBean encryption algorithm changed. In previous releases, if the newly set value was identical to the existing value, the encrypted value did not change.  That is, you would always get the same encrypted value for a given password   The action was not treated as a (non-dynamic) change. The behavior has been modified so that use of the setter on any existing encrypted value is considered to be a (dynamic) change, regardless of whether the new value matches the old value. Therefore, even if you set the password to the existing value, the setter now generates a different encrypted value for the given password.</p>  <p>Use this attribute if you have specified that property should be encrypted.</p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncryptedValueEncrypted")) {
         getterName = "getEncryptedValueEncrypted";
         setterName = null;
         currentResult = new PropertyDescriptor("EncryptedValueEncrypted", ConfigurationPropertyMBean.class, getterName, setterName);
         descriptors.put("EncryptedValueEncrypted", currentResult);
         currentResult.setValue("description", "<p>Get the encrytped bytes from EncryptedValue attribute</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Value")) {
         getterName = "getValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValue";
         }

         currentResult = new PropertyDescriptor("Value", ConfigurationPropertyMBean.class, getterName, setterName);
         descriptors.put("Value", currentResult);
         currentResult.setValue("description", "<p>Specifies the value of the property.</p>  <p>If the property is encrypted, then attribute is null and one should use the EncryptedValue attribute to get the decrypted value.</p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncryptValueRequired")) {
         getterName = "isEncryptValueRequired";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptValueRequired";
         }

         currentResult = new PropertyDescriptor("EncryptValueRequired", ConfigurationPropertyMBean.class, getterName, setterName);
         descriptors.put("EncryptValueRequired", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the property should be encrypted.</p>  <p> By default, the value of a property is not encrypted and anyone using the Administration Console can view the value of the property. If this attribute is set to true, then the value of the property on the Administration Console will be set to all asterisks.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
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
