package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ConnectionFactoryResourceBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ConnectionFactoryResourceBean.class;

   public ConnectionFactoryResourceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionFactoryResourceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.ConnectionFactoryResourceBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String("Configuration of a Connector Connection Factory resource. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.ConnectionFactoryResourceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Description of this resource. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterfaceName")) {
         getterName = "getInterfaceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterfaceName";
         }

         currentResult = new PropertyDescriptor("InterfaceName", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("InterfaceName", currentResult);
         currentResult.setValue("description", "The fully qualified class name of the connection factory interface. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxPoolSize")) {
         getterName = "getMaxPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPoolSize";
         }

         currentResult = new PropertyDescriptor("MaxPoolSize", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("MaxPoolSize", currentResult);
         currentResult.setValue("description", "The maximum number of physical connections that should be concurrently allocated for a connection pool. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinPoolSize")) {
         getterName = "getMinPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinPoolSize";
         }

         currentResult = new PropertyDescriptor("MinPoolSize", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("MinPoolSize", currentResult);
         currentResult.setValue("description", "The minimum number of physical connections that should be concurrently allocated for a connection pool. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name element specifies the JNDI name of the resource being defined. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Resource property.  This may be a vendor-specific property. ");
         setPropertyDescriptorDefault(currentResult, new JavaEEPropertyBean[0]);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createProperty");
         currentResult.setValue("destroyer", "destroyProperty");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceAdapter")) {
         getterName = "getResourceAdapter";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setResourceAdapter";
         }

         currentResult = new PropertyDescriptor("ResourceAdapter", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("ResourceAdapter", currentResult);
         currentResult.setValue("description", "Resource adapter name. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionSupport")) {
         getterName = "getTransactionSupport";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactionSupport";
         }

         currentResult = new PropertyDescriptor("TransactionSupport", ConnectionFactoryResourceBean.class, getterName, setterName);
         descriptors.put("TransactionSupport", currentResult);
         currentResult.setValue("description", "The level of transaction support the connection factory needs to support. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("legalValues", new Object[]{"NoTransaction", "LocalTransaction", "XATransaction"});
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConnectionFactoryResourceBean.class.getMethod("createProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = ConnectionFactoryResourceBean.class.getMethod("destroyProperty", JavaEEPropertyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ConnectionFactoryResourceBean.class.getMethod("lookupProperty", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Properties");
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
