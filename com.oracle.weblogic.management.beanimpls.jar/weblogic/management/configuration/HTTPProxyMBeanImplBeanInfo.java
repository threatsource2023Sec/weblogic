package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;

public class HTTPProxyMBeanImplBeanInfo extends DeploymentMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = HTTPProxyMBean.class;

   public HTTPProxyMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public HTTPProxyMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.HTTPProxyMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("This class represents the HTTP proxy configuration. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.HTTPProxyMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("HealthCheckInterval")) {
         getterName = "getHealthCheckInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckInterval";
         }

         currentResult = new PropertyDescriptor("HealthCheckInterval", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("HealthCheckInterval", currentResult);
         currentResult.setValue("description", "<p>The health check interval in milliseconds between pings.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(5));
         currentResult.setValue("legalMax", new Integer(300));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialConnections")) {
         getterName = "getInitialConnections";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialConnections";
         }

         currentResult = new PropertyDescriptor("InitialConnections", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("InitialConnections", currentResult);
         currentResult.setValue("description", "<p>The number of initial connections that should be opened to each server in the back end servers.</p> ");
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConnections")) {
         getterName = "getMaxConnections";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConnections";
         }

         currentResult = new PropertyDescriptor("MaxConnections", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("MaxConnections", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connections that each server can open to the back end servers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxHealthCheckInterval")) {
         getterName = "getMaxHealthCheckInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxHealthCheckInterval";
         }

         currentResult = new PropertyDescriptor("MaxHealthCheckInterval", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("MaxHealthCheckInterval", currentResult);
         currentResult.setValue("description", "<p>The maximum interval between health checks.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(60));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxRetries")) {
         getterName = "getMaxRetries";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxRetries";
         }

         currentResult = new PropertyDescriptor("MaxRetries", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("MaxRetries", currentResult);
         currentResult.setValue("description", "<p>The max retries after which the server will be marked dead.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMax", new Integer(200));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerList")) {
         getterName = "getServerList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerList";
         }

         currentResult = new PropertyDescriptor("ServerList", HTTPProxyMBean.class, getterName, setterName);
         descriptors.put("ServerList", currentResult);
         currentResult.setValue("description", "<p>The list of servers in the back end that the HCS should proxy to.</p> ");
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
