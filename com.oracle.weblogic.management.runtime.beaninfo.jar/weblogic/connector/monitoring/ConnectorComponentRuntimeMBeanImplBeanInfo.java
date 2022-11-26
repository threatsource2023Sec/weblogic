package weblogic.connector.monitoring;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;
import weblogic.j2ee.ComponentRuntimeMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;

public class ConnectorComponentRuntimeMBeanImplBeanInfo extends ComponentRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorComponentRuntimeMBean.class;

   public ConnectorComponentRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectorComponentRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.ConnectorComponentRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "8.1.0.0");
      beanDescriptor.setValue("package", "weblogic.connector.monitoring");
      String description = (new String("<p>Generates notifications about the deployment state of resource adapters. (Each resource adapter is represented by an instance of {@link weblogic.management.configuration.ConnectorComponentMBean}.)</p>  <p>In 2-phase deployment, if a resource adapter's state is <code>PREPARED</code> then it has achieved the first phase of deployment (everything is set up and all that remains is to enable a reference to the adapter). When the resource adapter is in an <code>ACTIVATED</code> state, it has achieved the second phase of deployment, in which applications can obtain a reference to the adapter.</p>  <p>A server instance creates an instance of this interface when it creates an instance of <code>weblogic.management.configuration.ConnectorComponentMBean</code>.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorComponentRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveVersionId")) {
         getterName = "getActiveVersionId";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveVersionId", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveVersionId", currentResult);
         currentResult.setValue("description", "<p>Get the active version Id.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AppDeploymentMBean")) {
         getterName = "getAppDeploymentMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeploymentMBean", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AppDeploymentMBean", currentResult);
         currentResult.setValue("description", "<p> Gets the AppDeploymentMBean for the Connector Component </p> ");
         currentResult.setValue("deprecated", "9.1.0.0 Acquire this by looking up the AppDeploymentMBean with the same name as the parent ApplicationRuntime ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ComponentName")) {
         getterName = "getComponentName";
         setterName = null;
         currentResult = new PropertyDescriptor("ComponentName", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ComponentName", currentResult);
         currentResult.setValue("description", "<p>Get the name of the connector component.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Configuration")) {
         getterName = "getConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("Configuration", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Configuration", currentResult);
         currentResult.setValue("description", "<p>Return the xml string representing the RA configuration. The xml corresponding to the latest schema is returned. The current supported version is \"1.0\"</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationVersion")) {
         getterName = "getConfigurationVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfigurationVersion", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfigurationVersion", currentResult);
         currentResult.setValue("description", "<p>Return the latest configuration version.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfiguredProperties")) {
         getterName = "getConfiguredProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConfiguredProperties", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConfiguredProperties", currentResult);
         currentResult.setValue("description", "<p> Gets a subset of the resource adapter descriptor information. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPoolCount")) {
         getterName = "getConnectionPoolCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPoolCount", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPoolCount", currentResult);
         currentResult.setValue("description", "<p>The number of connection pools.</p> ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ConnectionPools")) {
         getterName = "getConnectionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionPools", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionPools", currentResult);
         currentResult.setValue("description", "<p>An array of <code>ConnectorConnectionPoolRuntimeMBeans</code>, each of which represents the runtime data for a connection pool in the resource adapter.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorComponentMBean")) {
         getterName = "getConnectorComponentMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorComponentMBean", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorComponentMBean", currentResult);
         currentResult.setValue("description", "<p>Gets the ConnectorComponentMBean for the Connector Component.</p> ");
         currentResult.setValue("deprecated", "9.1.0.0 The connector component mbean was already deprecated. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorServiceRuntime")) {
         getterName = "getConnectorServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorServiceRuntime", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the connector service runtime.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorWorkManagerRuntime")) {
         getterName = "getConnectorWorkManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorWorkManagerRuntime", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorWorkManagerRuntime", currentResult);
         currentResult.setValue("description", "<p>Runtime information for adapter's work manager that beyond WebLogic's standard work manager. It provides addisional runtime information specific to Connector and not contained by WebLogic work manager.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         currentResult = new PropertyDescriptor("Description", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "<p>Get the Description for the resource adapter.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Descriptions")) {
         getterName = "getDescriptions";
         setterName = null;
         currentResult = new PropertyDescriptor("Descriptions", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Descriptions", currentResult);
         currentResult.setValue("description", "<p>Get the Descriptions for the resource adapter.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EISResourceId")) {
         getterName = "getEISResourceId";
         setterName = null;
         currentResult = new PropertyDescriptor("EISResourceId", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EISResourceId", currentResult);
         currentResult.setValue("description", "<p>Returns the EISResourceId for the component.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EISType")) {
         getterName = "getEISType";
         setterName = null;
         currentResult = new PropertyDescriptor("EISType", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EISType", currentResult);
         currentResult.setValue("description", "<p>Get the EIS type.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2", (String)null, this.targetVersion) && !descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The HealthState mbean for the application. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2");
      }

      if (!descriptors.containsKey("InboundConnections")) {
         getterName = "getInboundConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("InboundConnections", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InboundConnections", currentResult);
         currentResult.setValue("description", "<p>An array of runtime information for all inbound connections for the resource adapter.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InboundConnectionsCount")) {
         getterName = "getInboundConnectionsCount";
         setterName = null;
         currentResult = new PropertyDescriptor("InboundConnectionsCount", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InboundConnectionsCount", currentResult);
         currentResult.setValue("description", "<p>The number of inbound connections for the resource adapter.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JndiName")) {
         getterName = "getJndiName";
         setterName = null;
         currentResult = new PropertyDescriptor("JndiName", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JndiName", currentResult);
         currentResult.setValue("description", "<p>Get the Jndi name of the resource adapter.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Linkref")) {
         getterName = "getLinkref";
         setterName = null;
         currentResult = new PropertyDescriptor("Linkref", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Linkref", currentResult);
         currentResult.setValue("description", "<p>Get the linkref.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Schema")) {
         getterName = "getSchema";
         setterName = null;
         currentResult = new PropertyDescriptor("Schema", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Schema", currentResult);
         currentResult.setValue("description", "<p>Get the latest schema for RA configuration.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SpecVersion")) {
         getterName = "getSpecVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("SpecVersion", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SpecVersion", currentResult);
         currentResult.setValue("description", "<p>Get the spec version.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>Get the state of the resource adapter.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Stats")) {
         getterName = "getStats";
         setterName = null;
         currentResult = new PropertyDescriptor("Stats", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Stats", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("SuspendedState")) {
         getterName = "getSuspendedState";
         setterName = null;
         currentResult = new PropertyDescriptor("SuspendedState", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuspendedState", currentResult);
         currentResult.setValue("description", "<p>Gets the suspended state information of the resource adapter.</p>  <p>If getState() returns SUSPENDED then getSuspendedState() returns an integer describing which functions of the resource adapter are suspended: one or more of INBOUND, OUTBOUND or WORK (or ALL) or 0 for nothing suspended</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.connector.extensions.Suspendable")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VendorName")) {
         getterName = "getVendorName";
         setterName = null;
         currentResult = new PropertyDescriptor("VendorName", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("VendorName", currentResult);
         currentResult.setValue("description", "<p>Get the vendor name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Version")) {
         getterName = "getVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("Version", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Version", currentResult);
         currentResult.setValue("description", "<p>Get the version.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionId")) {
         getterName = "getVersionId";
         setterName = null;
         currentResult = new PropertyDescriptor("VersionId", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("VersionId", currentResult);
         currentResult.setValue("description", "<p>Get the version Id.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkManagerRuntime")) {
         getterName = "getWorkManagerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntime", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntime", currentResult);
         currentResult.setValue("description", "<p>Runtime information for WebLogic's work manager that serves the adapter. It provides general runtime information by WebLogic work manager.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.WorkManagerRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("ActiveVersion")) {
         getterName = "isActiveVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveVersion", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveVersion", currentResult);
         currentResult.setValue("description", "<p>Return true if this version is the active version. Returns true if this resource adapter is not versioned.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Versioned")) {
         getterName = "isVersioned";
         setterName = null;
         currentResult = new PropertyDescriptor("Versioned", ConnectorComponentRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Versioned", currentResult);
         currentResult.setValue("description", "<p>Check if the resource adapter is versioned. Returns true if it is.</p> ");
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
      Method mth = ConnectorComponentRuntimeMBean.class.getMethod("getConnectionPool", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("key", "JNDI name or resource-link name of the connection pool. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a <code>ConnectorConnectionPoolRuntimeMBean</code> that represents the statistics for a connection pool. The pool that is accessed in this call must be part of the resource adapter that is being accessed. A null is returned if the JNDI name or resource-link name is not found.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("getInboundConnection", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("messageListenerType", "Message listener type. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Runtime information for the specified inbound connection. A null is returned if the inbound connection is not found in the resource adapter.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("suspendAll");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes all activities of this resource adapter.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("suspend", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspend a particular type of activity for this resource adapter</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable "), createParameterDescriptor("props", "Properties to pass on to the RA or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspends the specified type of activity for this resource adapter</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("resumeAll");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes all activities of this resource adapter.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("resume", Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specified type of activity for this resource adapter</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("resume", Integer.TYPE, Properties.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "int The type of activity(ies), @see weblogic.connector.extensions.Suspendable "), createParameterDescriptor("props", "Properties to pass on to the RA or null ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resumes the specified type of activity for this resource adapter</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("getSchema", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("version", "String ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Get the schema for RA configuration based on the version that is provided. Return null if the version is not found. The current supported version is \"1.0\"</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorComponentRuntimeMBean.class.getMethod("getConfiguration", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("version", "String ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Return the xml string representing the RA configuration. The xml corresponding to the version specified is returned.</p> ");
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
