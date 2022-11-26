package weblogic.management.mbeanservers.runtime.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.RuntimeMBean;

public class RuntimeServiceMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = RuntimeServiceMBean.class;

   public RuntimeServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public RuntimeServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.runtime.internal.RuntimeServiceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.runtime.internal");
      String description = (new String("<p>Provides an entry point for navigating the hierarchy of WebLogic Server runtime MBeans and active configuration MBeans for the current server.</p>  <p>Each server instance in a domain provides its own instance of this MBean.</p>  <p>The <code>javax.management.ObjectName</code> of this MBean is \"<code>com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean</code>\".</p>  <p>This is the only object name that a JMX client needs to navigate the hierarchy available from this MBean. To start navigating, a JMX client invokes the <code>javax.management.MBeanServerConnection.getAttribute()</code> method and passes the following as parameters:</p>  <ul> <li>The <code>ObjectName</code> of this service MBean</li> <li>A <code>String</code> representation for the name of an attribute in this MBean that contains the root of an MBean hierarchy</li> </ul>  <p>This method call returns the <code>ObjectName</code> for the root MBean. To access MBeans below the root, the JMX client passes the root MBean's <code>ObjectName</code> and the name of a root MBean attribute that contains a child MBean to the <code>MBeanServerConnection.getAttribute()</code> method. This method call returns the <code>ObjectName</code> of the child MBean.</p>  <p>For example:</p>  <p><code> ObjectName rs = new ObjectName(\"com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean\");<br> <br> // Get the ObjectName of the server's ServerRuntimeMBean by getting the value<br> // of the RuntimeServiceMBean ServerRuntime attribute<br> <br> ObjectName serverrt = (ObjectName) MBeanServerConnection.getAttribute(rs,\"ServerRuntime\");<br> <br> // Get the ObjectNames for all ApplicationRuntimeMBeans on the server by getting<br> // the value of the ServerRuntimeMBean ApplicationRuntimes attribute<br> <br> ObjectName[] apprt = (ObjectName[]) MBeanServerConnection.getAttribute(serverrt,\"ApplicationRuntimes\");<br> </code></p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("rolePermitAll", Boolean.TRUE);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("DomainConfiguration")) {
         getterName = "getDomainConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainConfiguration", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainConfiguration", currentResult);
         currentResult.setValue("description", "<p>Contains the active <code>DomainMBean</code> for the current WebLogic Server domain.</p>  <p>Get this MBean to learn about the active configuration of all servers and resources in the domain.</p>  <p>Note: The <code>DomainMBean</code> that can be accessed from this (<code>RuntimeServiceMBean</code>) MBean attribute represents the active configuration of the domain and cannot be edited. The <i>pending</i> <code>DomainMBean</code>, which can be edited, is available only from the Edit MBean Server and its <code>EditServiceMBean</code>.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
      }

      if (!descriptors.containsKey("ServerConfiguration")) {
         getterName = "getServerConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerConfiguration", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ServerConfiguration", currentResult);
         currentResult.setValue("description", "<p>Contains the active <code>ServerMBean</code> for the current server instance.</p>  <p>Get this MBean to learn about the configuration of the current server, including any values that were overridden by the server's startup command.</p>  <p>Note: The <code>ServerMBean</code> that can be accessed from this (<code>RuntimeServiceMBean</code>) MBean attribute represents the active configuration of the server and cannot be edited.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "<p>The name of the current WebLogic Server instance as defined in the domain configuration.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ServerRuntime")) {
         getterName = "getServerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerRuntime", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ServerRuntime", currentResult);
         currentResult.setValue("description", "<p>Contains <code>ServerRuntimeMBean</code> for the current server.</p>  <p>The <code>ServerRuntimeMBean</code> is the root of runtime MBean hierarchy for this server instance. Each runtime MBean in the hierarchy provides access to the server's status and control as well as statistical information about any deployed or configured service on the server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Services")) {
         getterName = "getServices";
         setterName = null;
         currentResult = new PropertyDescriptor("Services", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Services", currentResult);
         currentResult.setValue("description", "<p>Returns all the services that do not have a parent (i.e., all the root services)</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", RuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Type", currentResult);
         currentResult.setValue("description", "<p>The MBean type for this instance. This is useful for MBean types that support multiple intances, such as <code>ActivationTaskMBean</code>.</p> ");
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
      Method mth = RuntimeServiceMBean.class.getMethod("findRuntime", DescriptorBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configurationMBean", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables a JMX client to retrieve monitoring statistics for a specified resource on the current server. To use this operation, a JMX client passes a single configuration MBean. The operation returns the corresponding runtime MBean for the resource on the current server.</p>  <p>For example, given the <code>JMSServerMBean</code> for a JMS server named \"JS1\" on a the current server, this operation returns the <code>JMSServerRuntimeMBean</code> for \"JS1.\"</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = RuntimeServiceMBean.class.getMethod("findConfiguration", RuntimeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("runtimeMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables a JMX client to retrieve configuration data for a specific instance of a resource. To use this operation, a JMX client passes a single runtime MBean and the operation returns the active configuration MBean for the resource.</p>  <p>For example, given the <code>JMSServerRuntimeMBean</code> for a JMS server named \"JS1\" on the current server instance, this operation returns the active <code>JMSServerMBean</code> for \"JS1.\"</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = RuntimeServiceMBean.class.getMethod("findService", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serviceName", (String)null), createParameterDescriptor("type", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables client to retrieve a specific named service </p> ");
         currentResult.setValue("role", "operation");
      }

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
