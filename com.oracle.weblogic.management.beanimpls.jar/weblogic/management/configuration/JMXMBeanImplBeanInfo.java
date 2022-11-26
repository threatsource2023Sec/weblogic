package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JMXMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JMXMBean.class;

   public JMXMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JMXMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JMXMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>Controls which JMX agents are initialized in the current WebLogic Server domain. Each JMX agent supports specific functions such as monitoring run-time statistics or modifying the domain's configuration.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JMXMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InvocationTimeoutSeconds")) {
         getterName = "getInvocationTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInvocationTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("InvocationTimeoutSeconds", JMXMBean.class, getterName, setterName);
         descriptors.put("InvocationTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that internal WebLogic Server processes wait to connect to an MBean server, invoke an MBean server method, and return the results of the invocation. If the MBean server method does not complete (return) within the timeout period, WebLogic Server abandons its invocation attempt.</p> <p>Some internal management processes within WebLogic Server require a server instance to connect to MBean servers in other WebLogic Server instances and invoke an MBean server method. The timeout period prevents the internal process from locking up if an MBean server cannot successfully return a method invocation.</p> <p>A value of <code>0</code> (zero) prevents the method invocation from timing out. With such a value, the internal process will wait indefinitely until the MBean server's method returns. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("CompatibilityMBeanServerEnabled")) {
         getterName = "isCompatibilityMBeanServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCompatibilityMBeanServerEnabled";
         }

         currentResult = new PropertyDescriptor("CompatibilityMBeanServerEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("CompatibilityMBeanServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables JMX clients to use the deprecated <code>MBeanHome</code> interface.</p> <p>Prior to 9.0, WebLogic Server supported a typed API layer over its JMX layer. Your JMX application classes could import type-safe interfaces for WebLogic Server MBeans, retrieve a reference to the MBeans through the <code>weblogic.management.MBeanHome</code> interface, and invoke the MBean methods directly.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainMBeanServerEnabled")) {
         getterName = "isDomainMBeanServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDomainMBeanServerEnabled";
         }

         currentResult = new PropertyDescriptor("DomainMBeanServerEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("DomainMBeanServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server initializes the Domain MBean Server, which provides federated access to all run-time MBeans and read-only configuration MBeans in the domain. Through it, JMX clients can access all MBeans in a domain through a single connection.</p>  <p>The Administration Console and the WebLogic Scripting Tool use this MBean server for many (but not all) of their read operations.</p>  <p>This MBean server exists only on the Administration Server. The Administration Server initializes it the first time a JMX client requests a connection to it. If you set this attribute to <code>false</code>, the Administration Server will not start this MBean server and JMX clients cannot connect to it.</p>  <p>While this MBean server is instantiated lazily (only when requested), once it is instantiated it does use some memory and network traffic. Disabling this MBean server can conserve a minimal amount of resources, but the trade off is that JMX clients must maintain separate connections for each WebLogic Server's Runtime MBean Server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EditMBeanServerEnabled")) {
         getterName = "isEditMBeanServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEditMBeanServerEnabled";
         }

         currentResult = new PropertyDescriptor("EditMBeanServerEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("EditMBeanServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server initializes the Edit MBean Server, which contains the hierarchy of MBeans used to make modifications to the domain's configuration. All JMX clients, including utilities such as the Administration Console and the WebLogic Scripting Tool, use the Edit MBean Server to modify a domain's configuration.</p>  <p>This MBean server exists only on the Administration Server. By default, when the Administration Server starts, it starts the Edit MBean Server. If you set the <code>EditMBeanServerEnabled</code> attribute to <code>false</code>, the Administration Server will not start this MBean server. If you disable this MBean server, JMX clients cannot modify the domain's configuration. You can, however, modify the domain configuration through the offline editing feature of WebLogic Scripting Tool.</p>  <dl> <dt>Note:</dt>  <dd> <p>Disabling the Edit MBean Server is not sufficient to prevent changes to a domain configuration. Because the WebLogic Server deployment service does not use JMX, the Administration Console and WLST can deploy or undeploy applications even if you have disabled the Edit MBean Server.</p> </dd> </dl> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedServerNotificationsEnabled")) {
         getterName = "isManagedServerNotificationsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagedServerNotificationsEnabled";
         }

         currentResult = new PropertyDescriptor("ManagedServerNotificationsEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("ManagedServerNotificationsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the WebLogic Server Domain Runtime MBean Server will support notifications from the federated Runtime MBean Servers on the managed and administration servers.</p> <p>Supporting notifications requires a significant amount of memory resources. A list of ObjectNames is maintained for each MBean from the Runtime MBean Servers. If notifications are not required for this domain, then disabling them will save on CPU and memory resources. This is particularly critical if there are large numbers of runtime MBeans and/or large numbers of servers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagementAppletCreateEnabled")) {
         getterName = "isManagementAppletCreateEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagementAppletCreateEnabled";
         }

         currentResult = new PropertyDescriptor("ManagementAppletCreateEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("ManagementAppletCreateEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the WebLogic Server MBean Servers will support creation of management applet (MLet) MBeans.</p>  <p>MLet MBeans allow a user to upload the MBean implementation from a remote server and should only be enabled if the environment and security manager restrict access to trusted JMX clients.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ManagementEJBEnabled")) {
         getterName = "isManagementEJBEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setManagementEJBEnabled";
         }

         currentResult = new PropertyDescriptor("ManagementEJBEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("ManagementEJBEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables access to the Management EJB (MEJB), which is part of the Java EE Management APIs (JSR-77). </p> <p>The Management API specification provides a standardized management data model for common resources on Java EE Web application servers.</p>  <p>The MEJB provides access to Java EE Managed Objects (JMOs), which describe the common Java EE resources. If you set this attribute to <code>false</code>, WebLogic Server does not register the MEJB in the JNDI tree, and effectively disables the Java EE Management APIs for the domain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PlatformMBeanServerEnabled")) {
         getterName = "isPlatformMBeanServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatformMBeanServerEnabled";
         }

         currentResult = new PropertyDescriptor("PlatformMBeanServerEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("PlatformMBeanServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether each server instance initializes the JDK's platform MBean server. Enabling it, along with <code>isPlatformMBeanServerUsed</code>, causes WebLogic Server to use the platform MBean server as its Runtime MBean Server.</p>  <p>As of JDK 1.5, JVMs provide a platform MBean server that local processes can instantiate. There can be only one instance of the platform MBean server for each JVM. When a process instantiates this MBean server, the JVM creates several platform MXBeans that provide monitoring data for the JVM itself.</p>  <p>If you set this attribute to <code>true</code>, each WebLogic Server instance invokes the method <code>java.lang.management.ManagementFactory.getPlatformMBeanServer()</code> and thus causes the initialization of the JVM's MXBeans.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PlatformMBeanServerUsed")) {
         getterName = "isPlatformMBeanServerUsed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPlatformMBeanServerUsed";
         }

         currentResult = new PropertyDescriptor("PlatformMBeanServerUsed", JMXMBean.class, getterName, setterName);
         descriptors.put("PlatformMBeanServerUsed", currentResult);
         currentResult.setValue("description", "<p>Specifies whether WebLogic Server will use the platform MBean server for its Runtime MBean Server. Previously, WebLogic Server used the platform MBean server by default if it was enabled. This attribute provides a separation between enabling the platform MBean server and using it for WebLogic Server MBeans.</p> <p>The default value for this attribute is based on the <code>DomainVersion</code> attribute of the DomainMBean. If the domain version is prior to 10.3.3.0, then the default value of this attribute is <code>false</code> and the platform MBean server is not used. If the domain version is 10.3.3.0 or higher, then the default value of this attribute is <code>true</code> and the platform MBean server is used. </p> <p>In this case, the server's Runtime MBean Server uses the <code>MBeanServer</code> returned by <code>java.lang.management.ManagementFactory.getPlatformMBeanServer()</code> method as its MBean Server. This makes it possible to access the WebLogic Server MBeans and the JVM platform MXBeans from a single MBean server. In addition, if you enable the Runtime MBean Server to be the Platform MBean Server, local processes in the JVM can directly access this MBean server through the <code>MBeanServer</code> interface that <code>java.lang.management.ManagementFactory.getPlatformMBeanServer()</code> returns.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RuntimeMBeanServerEnabled")) {
         getterName = "isRuntimeMBeanServerEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRuntimeMBeanServerEnabled";
         }

         currentResult = new PropertyDescriptor("RuntimeMBeanServerEnabled", JMXMBean.class, getterName, setterName);
         descriptors.put("RuntimeMBeanServerEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether each server instance in the domain initializes its Runtime MBean Server, which provides access to a server's run-time MBeans and read-only configuration MBeans.</p>  <p>The Administration Console and the WebLogic Scripting Tool use this MBean server for some (but not all) of their read operations.</p>  <p>If <code>RuntimeMBeanServerEnabled</code> is <code>true</code>, each server starts its Runtime MBean Server during the server's startup cycle. If this attribute is <code>false</code>, no server instance in the domain will start its Runtime MBean Server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
