package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCPropertyBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCPropertyBean.class;

   public JDBCPropertyBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCPropertyBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCPropertyBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Represents a specific JDBC property. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCPropertyBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] roleObjectArrayGet;
      if (!descriptors.containsKey("EncryptedValue")) {
         getterName = "getEncryptedValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptedValue";
         }

         currentResult = new PropertyDescriptor("EncryptedValue", JDBCPropertyBean.class, getterName, setterName);
         descriptors.put("EncryptedValue", currentResult);
         currentResult.setValue("description", "<p>The plaintext value of the encrypted property value.</p>  <p>The value is stored in an encrypted form in the descriptor file and when displayed in an administration console.</p>  <p><code>setEncryptedValue('string')</code> works in online WLST but not offline WLST.  Use <code>setEncryptedValueEncrypted(encrypt('string'))</code> for online or offline WLST.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         String[] roleObjectArraySet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedSet", roleObjectArraySet);
      }

      if (!descriptors.containsKey("EncryptedValueEncrypted")) {
         getterName = "getEncryptedValueEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEncryptedValueEncrypted";
         }

         currentResult = new PropertyDescriptor("EncryptedValueEncrypted", JDBCPropertyBean.class, getterName, setterName);
         descriptors.put("EncryptedValueEncrypted", currentResult);
         currentResult.setValue("description", "<p>The encrypted value is stored in the descriptor file. For example, use <code>setEncryptedValueEncrypted(encrypt('string'))</code> in online or offline WLST.</p> ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JDBCPropertyBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of the property. The name of each property must be unique in the list of properties. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SysPropValue")) {
         getterName = "getSysPropValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSysPropValue";
         }

         currentResult = new PropertyDescriptor("SysPropValue", JDBCPropertyBean.class, getterName, setterName);
         descriptors.put("SysPropValue", currentResult);
         currentResult.setValue("description", "The value of the property defined as a system property. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Value")) {
         getterName = "getValue";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setValue";
         }

         currentResult = new PropertyDescriptor("Value", JDBCPropertyBean.class, getterName, setterName);
         descriptors.put("Value", currentResult);
         currentResult.setValue("description", "The value of the property. ");
         currentResult.setValue("configurable", Boolean.TRUE);
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
