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

public class JmsDestinationBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = JmsDestinationBean.class;

   public JmsDestinationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JmsDestinationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.JmsDestinationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "12.2.1.0.0");
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String("Configuration of a JMS Destination. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.JmsDestinationBean");
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

         currentResult = new PropertyDescriptor("ClassName", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "Fully-qualified name of the JMS destination implementation class.  Ignored if a resource adapter is used unless the resource adapter defines more than one destination implementation class for the specified interface. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "Description of this JMS Destination. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DestinationName")) {
         getterName = "getDestinationName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDestinationName";
         }

         currentResult = new PropertyDescriptor("DestinationName", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("DestinationName", currentResult);
         currentResult.setValue("description", "Name of the queue or topic. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", JmsDestinationBean.class, getterName, setterName);
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

         currentResult = new PropertyDescriptor("InterfaceName", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("InterfaceName", currentResult);
         currentResult.setValue("description", "Fully-qualified name of the JMS destination interface. Permitted values are javax.jms.Queue and javax.jms.Topic ");
         currentResult.setValue("legalValues", new Object[]{"javax.jms.Queue", "javax.jms.Topic"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name element specifies the JNDI name of the JMS destination being defined. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "JMS Destination property.  This may be a vendor-specific property or a less commonly used Destination property. ");
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

         currentResult = new PropertyDescriptor("ResourceAdapter", JmsDestinationBean.class, getterName, setterName);
         descriptors.put("ResourceAdapter", currentResult);
         currentResult.setValue("description", "Resource adapter name.  If not specified, the application server will define the default behavior, which may or may not involve the use of a resource adapter. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JmsDestinationBean.class.getMethod("createProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = JmsDestinationBean.class.getMethod("destroyProperty", JavaEEPropertyBean.class);
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
      Method mth = JmsDestinationBean.class.getMethod("lookupProperty", String.class);
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
