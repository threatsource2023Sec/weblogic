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

public class ConnectionPropertiesBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConnectionPropertiesBean.class;

   public ConnectionPropertiesBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionPropertiesBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ConnectionPropertiesBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML connection-propertiesType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ConnectionPropertiesBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionParams")) {
         getterName = "getConnectionParams";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionParams", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("ConnectionParams", currentResult);
         currentResult.setValue("description", "Gets array of all \"connection-params\" elements ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConnectionParams");
         currentResult.setValue("destroyer", "destroyConnectionParams");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DriverClassName")) {
         getterName = "getDriverClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDriverClassName";
         }

         currentResult = new PropertyDescriptor("DriverClassName", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("DriverClassName", currentResult);
         currentResult.setValue("description", "Gets the \"driver-class-name\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "The password that will be used in conjunction with the user name specified in the \"Username\" parameter. Gets the \"password\" element ");
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PasswordEncrypted")) {
         getterName = "getPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("PasswordEncrypted", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("PasswordEncrypted", currentResult);
         currentResult.setValue("description", "The encrypted password that will be used in conjunction with the user name specified in the \"Username\" parameter. Gets the \"password-encrypted\" element ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Url")) {
         getterName = "getUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUrl";
         }

         currentResult = new PropertyDescriptor("Url", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("Url", currentResult);
         currentResult.setValue("description", "Gets the \"url\" element ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUserName";
         }

         currentResult = new PropertyDescriptor("UserName", ConnectionPropertiesBean.class, getterName, setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "Gets the \"user-name\" element ");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConnectionPropertiesBean.class.getMethod("createConnectionParams");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionParams");
      }

      mth = ConnectionPropertiesBean.class.getMethod("destroyConnectionParams", ConnectionParamsBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConnectionParams");
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
