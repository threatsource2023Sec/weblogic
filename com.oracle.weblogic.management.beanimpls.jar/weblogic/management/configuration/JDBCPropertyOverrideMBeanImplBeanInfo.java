package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCPropertyOverrideMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCPropertyOverrideMBean.class;

   public JDBCPropertyOverrideMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCPropertyOverrideMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JDBCPropertyOverrideMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.1.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Defines a JDBC driver property override. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JDBCPropertyOverrideMBean");
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

         currentResult = new PropertyDescriptor("EncryptedValue", JDBCPropertyOverrideMBean.class, getterName, setterName);
         descriptors.put("EncryptedValue", currentResult);
         currentResult.setValue("description", "<p>The plaintext value of the encrypted property value.</p>  <p>The value is stored in an encrypted form in the descriptor file and when displayed in an administration console.</p>  <p><code>setEncryptedValue('string')</code> works in online WLST but not offline WLST.  Use <code>setEncryptedValueEncrypted(encrypt('string'))</code> for online or offline WLST.</p> ");
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

         currentResult = new PropertyDescriptor("EncryptedValueEncrypted", JDBCPropertyOverrideMBean.class, getterName, setterName);
         descriptors.put("EncryptedValueEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value is stored in the descriptor file. For example, use <code>setEncryptedValueEncrypted(encrypt('string'))</code> in online or offline WLST.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SysPropValue")) {
         getterName = "getSysPropValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSysPropValue";
         }

         currentResult = new PropertyDescriptor("SysPropValue", JDBCPropertyOverrideMBean.class, getterName, setterName);
         descriptors.put("SysPropValue", currentResult);
         currentResult.setValue("description", "The value of the property defined as a system property. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Value")) {
         getterName = "getValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValue";
         }

         currentResult = new PropertyDescriptor("Value", JDBCPropertyOverrideMBean.class, getterName, setterName);
         descriptors.put("Value", currentResult);
         currentResult.setValue("description", "The value of the property. ");
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
