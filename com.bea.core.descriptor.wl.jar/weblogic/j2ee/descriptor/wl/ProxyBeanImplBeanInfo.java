package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class ProxyBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = ProxyBean.class;

   public ProxyBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ProxyBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.ProxyBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("exclude", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("An XML proxyType(@http://www.bea.com/ns/weblogic/90). This is a complex type. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.ProxyBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("InactiveConnectionTimeoutSeconds")) {
         getterName = "getInactiveConnectionTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInactiveConnectionTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", ProxyBean.class, getterName, setterName);
         descriptors.put("InactiveConnectionTimeoutSeconds", currentResult);
         currentResult.setValue("description", "Gets the \"inactive-connection-timeout-seconds\" element ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UseConnectionProxies")) {
         getterName = "getUseConnectionProxies";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUseConnectionProxies";
         }

         currentResult = new PropertyDescriptor("UseConnectionProxies", ProxyBean.class, getterName, setterName);
         descriptors.put("UseConnectionProxies", currentResult);
         currentResult.setValue("description", "Gets the \"use-connection-proxies\" element ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionProfilingEnabled")) {
         getterName = "isConnectionProfilingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionProfilingEnabled";
         }

         currentResult = new PropertyDescriptor("ConnectionProfilingEnabled", ProxyBean.class, getterName, setterName);
         descriptors.put("ConnectionProfilingEnabled", currentResult);
         currentResult.setValue("description", "Gets the \"connection-profiling-enabled\" element ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("exclude", Boolean.TRUE);
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
