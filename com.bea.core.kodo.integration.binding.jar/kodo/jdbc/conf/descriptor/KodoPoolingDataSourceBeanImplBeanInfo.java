package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class KodoPoolingDataSourceBeanImplBeanInfo extends DriverDataSourceBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = KodoPoolingDataSourceBean.class;

   public KodoPoolingDataSourceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public KodoPoolingDataSourceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.KodoPoolingDataSourceBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.KodoPoolingDataSourceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ConnectionDriverName")) {
         getterName = "getConnectionDriverName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionDriverName";
         }

         currentResult = new PropertyDescriptor("ConnectionDriverName", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("ConnectionDriverName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPassword")) {
         getterName = "getConnectionPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPassword";
         }

         currentResult = new PropertyDescriptor("ConnectionPassword", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("ConnectionPassword", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPasswordEncrypted")) {
         getterName = "getConnectionPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("ConnectionPasswordEncrypted", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("ConnectionPasswordEncrypted", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionURL")) {
         getterName = "getConnectionURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionURL";
         }

         currentResult = new PropertyDescriptor("ConnectionURL", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("ConnectionURL", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionUserName")) {
         getterName = "getConnectionUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionUserName";
         }

         currentResult = new PropertyDescriptor("ConnectionUserName", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("ConnectionUserName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeout")) {
         getterName = "getLoginTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeout";
         }

         currentResult = new PropertyDescriptor("LoginTimeout", KodoPoolingDataSourceBean.class, getterName, setterName);
         descriptors.put("LoginTimeout", currentResult);
         currentResult.setValue("description", " ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
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
