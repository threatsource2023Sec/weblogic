package weblogic.management.mbeanservers.domainruntime.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.internal.ServiceImplBeanInfo;
import weblogic.management.runtime.RuntimeMBean;

public class DomainRuntimeServiceMBeanImplBeanInfo extends ServiceImplBeanInfo {
   public static final Class INTERFACE_CLASS = DomainRuntimeServiceMBean.class;

   public DomainRuntimeServiceMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DomainRuntimeServiceMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.mbeanservers.domainruntime.internal.DomainRuntimeServiceMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.mbeanservers.domainruntime.internal");
      String description = (new String("<p>Provides a common access point for navigating to all runtime and configuration MBeans in the domain as well as to MBeans that provide domain-wide services (such as controlling and monitoring the life cycles of servers and message-driven EJBs and coordinating the migration of migratable services).</p>  <p>This MBean is available only on the Administration Server.</p>  <p>The <code>javax.management.ObjectName</code> of this MBean is \"<code>com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean</code>\".</p>  <p>This is the only object name that a JMX client needs to navigate the hierarchy available from this MBean.</p>  <p>Note: If your JMX client uses the Domain Runtime MBean Server to access runtime or configuration MBeans by constructing object names (instead of by using this <code>DomainRuntimeServiceMBean</code> to navigate the MBean hierarchy), the client must add a <code>Location=<i>servername</i></code> key property to the MBean object name. The MBean server uses this key property to route the JMX request to the appropriate WebLogic Server instance. If your client uses the <code>DomainRuntimeServiceMBean</code> to navigate the MBean hierarchy, the object names that it obtains automatically contain the location key property.</p>  <p>To start navigating, a JMX client invokes the <code>javax.management.MBeanServerConnection.getAttribute()</code> method and passes the following as parameters:</p>  <ul> <li>The <code>ObjectName</code> of this service MBean</li> <li>A <code>String</code> representation for the name of an attribute in this MBean that contains the root of an MBean hierarchy</li> </ul>  <p>This method call returns the <code>ObjectName</code> for the root MBean. To access MBeans below the root, the JMX client passes the root MBean's <code>ObjectName</code> and the name of a root MBean attribute that contains a child MBean to the <code>MBeanServerConnection.getAttribute()</code> method. This method call returns the <code>ObjectName</code> of the child MBean.</p>  <p>For example:</p>  <p><code> ObjectName drs = <br> new ObjectName(\"com.bea:Name=DomainRuntimeService,Type=weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean\");<br> <br> // Get the ObjectName of the domain's DomainMBean by gettingthe<br> // of the DomainRuntimeServiceMBean DomainConfiguration<br> <br> ObjectName domainconfig = (ObjectName) MBeanServerConnection.getAttribute(drs,\"DomainConfiguration\");<br> <br> // Get the ObjectNames for all ServerMBeans in the domain by getting<br> // the value of the DomainMBean Servers attribute<br> <br> ObjectName[] servers = (ObjectName[]) MBeanServerConnection.getAttribute(domainconfig,\"Servers\");<br> </code></p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("rolePermitAll", Boolean.TRUE);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean");
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
         currentResult = new PropertyDescriptor("DomainConfiguration", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainConfiguration", currentResult);
         currentResult.setValue("description", "<p>Contains the active <code>DomainMBean</code> for the current WebLogic Server domain.</p>  <p>Get this MBean to learn about the active configuration of all servers and resources in the domain. Any command line options that were used to start servers in this domain override the values in this <code>DomainMBean</code>. For example, if you used a command line option to override a server's listen port, the <code>ServerMBean</code> that you navigate to from this <code>DomainMBean</code> will show the value persisted in the <code>config.xml</code> file; it will not show the value that was passed in the command line option.</p>  <p>Note: The <code>DomainMBean</code> that can be accessed from this (<code>DomainRuntimeServiceMBean</code>) MBean attribute represents the active configuration of the domain and cannot be edited. The <i>pending</i> <code>DomainMBean</code>, which can be edited, is returned by the {@link weblogic.management.mbeanservers.edit.ConfigurationManagerMBean#startEdit startEdit} operation in the <code>ConfigurationManagerMBean</code>.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#findDomainConfiguration"), BeanInfoHelper.encodeEntities("#findServerConfiguration")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("DomainPending")) {
         getterName = "getDomainPending";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainPending", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainPending", currentResult);
         currentResult.setValue("description", "<p>Contains a read-only version of the pending <code>DomainMBean</code> for the current WebLogic Server domain. You cannot use this MBean to modify a domain's configuration. </p>  <p>If you want to modify a domain's configuration, use the {@link weblogic.management.mbeanservers.edit.ConfigurationManagerMBean#startEdit(int, int) startEdit} operation in the <code>ConfigurationManagerMBean</code> to start an edit session. The <code>startEdit</code> operation returns an editable <code>DomainMBean</code>.</p>  <p>Get this read-only version of the MBean to learn about the pending configuration of all servers and resources in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainRuntime")) {
         getterName = "getDomainRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainRuntime", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("DomainRuntime", currentResult);
         currentResult.setValue("description", "<p>Contains the <code>DomainRuntimeMBean</code> for the current WebLogic Server domain.</p>  <p>This MBean provides access to the special service interfaces that exist only on the Administration Server and provide life cycle control over the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>A unique key that WebLogic Server generates to identify the current instance of this MBean type.</p>  <p>For a singleton, such as <code>DomainRuntimeServiceMBean</code>, this key is often just the bean's short class name.</p> ");
      }

      if (!descriptors.containsKey("ParentAttribute")) {
         getterName = "getParentAttribute";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentAttribute", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentAttribute", currentResult);
         currentResult.setValue("description", "<p>The name of the attribute of the parent that refers to this bean</p> ");
      }

      if (!descriptors.containsKey("ParentService")) {
         getterName = "getParentService";
         setterName = null;
         currentResult = new PropertyDescriptor("ParentService", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ParentService", currentResult);
         currentResult.setValue("description", "<p>The MBean that created the current MBean instance.</p>  <p>In the data model for WebLogic Server MBeans, an MBean that creates another MBean is called a <i>parent</i>. MBeans at the top of the hierarchy have no parents.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("excludeFromRest", "No default REST mapping for weblogic.management.provider.Service");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         currentResult = new PropertyDescriptor("Path", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>Returns the path to the bean relative to the reoot of the heirarchy of services</p> ");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerName", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "<p>The name of this WebLogic Server instance as defined in the domain configuration.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerRuntimes")) {
         getterName = "getServerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerRuntimes", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
         descriptors.put("ServerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Contains all <code>ServerRuntimeMBean</code> instances on all servers in the domain.</p>  <p>Get these MBeans to learn about the current runtime statistics for all server instances in the domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("Type")) {
         getterName = "getType";
         setterName = null;
         currentResult = new PropertyDescriptor("Type", DomainRuntimeServiceMBean.class, getterName, (String)setterName);
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
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         Method mth = DomainRuntimeServiceMBean.class.getMethod("lookupServerRuntime", String.class);
         ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         String methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            MethodDescriptor currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the <code>ServerRuntimeMBean</code> for the specified server instance. The operation will return a null value if the named server is not currently running.</p>  <p>The <code>ServerRuntimeMBean</code> is the root of runtime MBean hierarchy for a server instance. Each runtime MBean in the hierarchy provides access to the server's status and control as well as statistical information about any deployed or configured service on the server.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ServerRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DomainRuntimeServiceMBean.class.getMethod("findDomainConfiguration", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the active <code>DomainMBean</code> for the specified server.</p>  <p>Get this MBean to learn about the current configuration of the server, including any values that were overridden by the server's startup command. For example, if you used a command line option to override a server's listen port, the <code>ServerMBean</code> that you navigate to from this <code>DomainMBean</code> will show the value that was passed in the command line option.</p>  <p>Note: The <i>pending</i> <code>DomainMBean</code>, which can be edited, is available only from the Edit MBean Server and its <code>EditServiceMBean</code>. The <code>DomainMBean</code> that can be accessed from this (<code>DomainRuntimeServiceMBean</code>) MBean attribute represents the active configuration of the specified server and cannot be edited.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("findServerConfiguration", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the active <code>ServerMBean</code> for the specified server.</p>  <p>Get this MBean to learn about the current configuration of the server, including any values that were overridden by the server's startup command. For example, if you used a command line option to override a server's listen port, this <code>ServerMBean</code> will show the value that was passed in the command line option.</p>  <p>Note: The <i>pending</i> <code>ServerMBean</code>, which can be edited, is available only from the Edit MBean Server and its <code>EditServiceMBean</code>. The <code>ServerMBean</code> that can be accessed from this (<code>DomainRuntimeServiceMBean</code>) MBean attribute represents the active configuration of the specified server and cannot be edited.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("findRuntimes", DescriptorBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configurationMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables a JMX client to retrieve monitoring statistics for all instances of a specific resource on all servers in a domain. To use this operation, a JMX client passes a single configuration MBean and the operation returns runtime MBeans for this resource from all servers in the domain.</p>  <p>For example, a JMX client connects to the Domain Runtime MBean server and gets the <code>JMSServerMBean</code> for a JMS server named \"JS1.\" The JMX client then invokes this operation and the operation determines the active server instances on which the \"JS1\" JMS server has been targeted. It then returns all of the <code>JMSServerRuntimeMBean</code>s for \"JS1\" from all servers in the domain.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("findRuntime", DescriptorBean.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("configurationMBean", (String)null), createParameterDescriptor("serverName", "that owns that runtime mbean. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables a JMX client to retrieve monitoring statistics for a specific resource on a specific server. To use this operation, a JMX client passes a single configuration MBean and the name of a server instance. The operation returns the corresponding runtime MBean for the resource on the named server, assuming that the resource has been targeted or deployed to the server.</p>  <p>For example, given the <code>JMSServerMBean</code> for a JMS server named \"JS1\" on a server instance named \"ManagedServer1,\" this operation returns the <code>JMSServerRuntimeMBean</code> for \"JS1\" on \"ManagedServer1.\"</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("findConfiguration", RuntimeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("runtimeMBean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Enables a JMX client to retrieve configuration data for a specific instance of a resource. To use this operation, a JMX client passes a single runtime MBean and the operation returns the active configuration MBean for the resource.</p>  <p>For example, given the <code>JMSServerRuntimeMBean</code> for a JMS server named \"JS1\" on the current server instance, this operation returns the active <code>JMSServerMBean</code> for \"JS1.\"</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeServiceMBean.class.getMethod("findPartitionRuntimes", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns all current <code>PartitionRuntimeMBean</code> instances for given  partition on all targeted servers in the domain.</p>  <p>Get these MBeans to learn about the current runtime statistics for all partition runtime instances in the domain.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainRuntimeServiceMBean.class.getMethod("findPartitionRuntime", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", (String)null), createParameterDescriptor("serverName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Returns the <code>PartitionRuntimeMBean</code> for the specified partition and server instance. The operation will return a null value if partition is not currently running on the named server.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("findService", String.class, String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("type", (String)null), createParameterDescriptor("location", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the Service on the specified Server or in the primary MBeanServer if the location is not specified.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = DomainRuntimeServiceMBean.class.getMethod("getServices", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("serverName", "String ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns all the services that do not have a parent (i.e, all the root services)</p> ");
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
