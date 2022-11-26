package weblogic.diagnostics.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class WLDFEncryptedPropertyBeanImplBeanInfo extends WLDFConfigurationPropertyBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = WLDFEncryptedPropertyBean.class;

   public WLDFEncryptedPropertyBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public WLDFEncryptedPropertyBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.diagnostics.descriptor");
      String description = (new String("<p> Represents an encrypted configuration property </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.diagnostics.descriptor.WLDFEncryptedPropertyBean");
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

         currentResult = new PropertyDescriptor("EncryptedValue", WLDFEncryptedPropertyBean.class, getterName, setterName);
         descriptors.put("EncryptedValue", currentResult);
         currentResult.setValue("description", "<p> Returns the decrypted value of the property. </p>  <p> When you get the value of this attribute, WebLogic Server does the following: </p> <ol> <li>Retrieves the value of the <code>ValueEncrypted</code> attribute.</li> <li>Decrypts the value and returns the unencrypted password as a String.</li> </ol>  <p> When you set the value of this attribute, WebLogic Server does the following: </p> <ol> <li>Encrypts the value.</li> <li>Sets the value of the <code>ValueEncrypted</code> attribute to the encrypted value.</li> </ol>  <p> Using this attribute is a potential security risk because the String object (which contains the unencrypted password) remains in the JVM's memory until garbage collection removes it and the memory is reallocated. Depending on how memory is allocated in the JVM, a significant amount of time could pass before this unencrypted data is removed from memory. </p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EncryptedValueEncrypted")) {
         getterName = "getEncryptedValueEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptedValueEncrypted";
         }

         currentResult = new PropertyDescriptor("EncryptedValueEncrypted", WLDFEncryptedPropertyBean.class, getterName, setterName);
         descriptors.put("EncryptedValueEncrypted", currentResult);
         currentResult.setValue("description", "<p>Get the encrypted bytes of the <code>Value</code> attribute</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
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
