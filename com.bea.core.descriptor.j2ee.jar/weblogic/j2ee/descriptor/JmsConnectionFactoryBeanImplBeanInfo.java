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

public class JmsConnectionFactoryBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JmsConnectionFactoryBean.class;

   public JmsConnectionFactoryBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JmsConnectionFactoryBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.JmsConnectionFactoryBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String("Configuration of a JMS Connection Factory. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.JmsConnectionFactoryBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassName";
         }

         currentResult = new PropertyDescriptor("ClassName", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "Fully-qualified name of the JMS connection factory implementation class.  Ignored if a resource adapter is used. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientId")) {
         getterName = "getClientId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientId";
         }

         currentResult = new PropertyDescriptor("ClientId", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("ClientId", currentResult);
         currentResult.setValue("description", "Client id to use for connection. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Description of this JMS Connection Factory. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", JmsConnectionFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("InterfaceName", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("InterfaceName", currentResult);
         currentResult.setValue("description", "Fully-qualified name of the JMS connection factory interface.  Permitted values are javax.jms.ConnectionFactory, javax.jms.QueueConnectionFactory, or javax.jms.TopicConnectionFactory.  If not specified, javax.jms.ConnectionFactory will be used. ");
         setPropertyDescriptorDefault(currentResult, "javax.jms.ConnectionFactory");
         currentResult.setValue("legalValues", new Object[]{"javax.jms.ConnectionFactory", "javax.jms.QueueConnectionFactory", "javax.jms.TopicConnectionFactory"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxPoolSize")) {
         getterName = "getMaxPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPoolSize";
         }

         currentResult = new PropertyDescriptor("MaxPoolSize", JmsConnectionFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("MinPoolSize", JmsConnectionFactoryBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("Name", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name element specifies the JNDI name of the JMS connection factory being defined. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "Password to use for connection authentication. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "JMS Connection Factory property.  This may be a vendor-specific property or a less commonly used ConnectionFactory property. ");
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

         currentResult = new PropertyDescriptor("ResourceAdapter", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("ResourceAdapter", currentResult);
         currentResult.setValue("description", "Resource adapter name.  If not specified, the application server will define the default behavior, which may or may not involve the use of a resource adapter. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("User")) {
         getterName = "getUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUser";
         }

         currentResult = new PropertyDescriptor("User", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("User", currentResult);
         currentResult.setValue("description", "User name to use for connection authentication. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transactional")) {
         getterName = "isTransactional";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactional";
         }

         currentResult = new PropertyDescriptor("Transactional", JmsConnectionFactoryBean.class, getterName, setterName);
         descriptors.put("Transactional", currentResult);
         currentResult.setValue("description", "Set to false if connections should not participate in transactions. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JmsConnectionFactoryBean.class.getMethod("createProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = JmsConnectionFactoryBean.class.getMethod("destroyProperty", JavaEEPropertyBean.class);
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
      Method mth = JmsConnectionFactoryBean.class.getMethod("lookupProperty", String.class);
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
