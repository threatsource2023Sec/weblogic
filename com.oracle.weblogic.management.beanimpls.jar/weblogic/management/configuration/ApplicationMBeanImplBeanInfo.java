package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ApplicationMBeanImplBeanInfo extends ConfigurationMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ApplicationMBean.class;

   public ApplicationMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ApplicationMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ApplicationMBeanImpl");
      } catch (Throwable var6) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("obsolete", "9.0.0.0");
      beanDescriptor.setValue("deprecated", "9.0.0.0 Replaced by {@link AppDeploymentMBean} ");
      beanDescriptor.setValue("notificationTranslator", "weblogic.management.deploy.internal.NotificationTranslator");
      String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("TargetMBean"), BeanInfoHelper.encodeEntities("EJBComponentMBean"), BeanInfoHelper.encodeEntities("WebAppComponentMBean"), BeanInfoHelper.encodeEntities("ConnectorComponentMBean")};
      beanDescriptor.setValue("see", seeObjectArray);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("An application represents a Java EE application contained in an EAR file or EAR directory. The EAR file contains a set of components such as WAR, EJB, and RAR connector components, each of which can be deployed on one or more targets. A target is a server or a cluster.  If the application is provided as a standalone module, then this MBean is a synthetic wrapper application only. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ApplicationMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AltDescriptorPath")) {
         getterName = "getAltDescriptorPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAltDescriptorPath";
         }

         currentResult = new PropertyDescriptor("AltDescriptorPath", ApplicationMBean.class, getterName, setterName);
         descriptors.put("AltDescriptorPath", currentResult);
         currentResult.setValue("description", "<p>A path on the file system for the application descriptor for this application. If null, the usual location within the ear is used (META-INF/application.xml);</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AltWLSDescriptorPath")) {
         getterName = "getAltWLSDescriptorPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAltWLSDescriptorPath";
         }

         currentResult = new PropertyDescriptor("AltWLSDescriptorPath", ApplicationMBean.class, getterName, setterName);
         descriptors.put("AltWLSDescriptorPath", currentResult);
         currentResult.setValue("description", "<p>A path on the file system for the WLS-specific application descriptor for this application. If null, the usual location within the EAR file is used (META-INF/weblogic-application.xml);</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Components")) {
         getterName = "getComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("Components", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Components", currentResult);
         currentResult.setValue("description", "<p>The Java EE modules (components) that make up this application.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorComponents")) {
         getterName = "getConnectorComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorComponents", ApplicationMBean.class, getterName, setterName);
         descriptors.put("ConnectorComponents", currentResult);
         currentResult.setValue("description", "<p>Returns the Connector components that make up this application. Components represent the Java EE modules associated with this application.<p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConnectorComponent");
         currentResult.setValue("destroyer", "destroyConnectorComponent");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentTimeout")) {
         getterName = "getDeploymentTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentTimeout";
         }

         currentResult = new PropertyDescriptor("DeploymentTimeout", ApplicationMBean.class, getterName, setterName);
         descriptors.put("DeploymentTimeout", currentResult);
         currentResult.setValue("description", "<p>Milliseconds granted for a cluster deployment task on this application. If any deployment tasks remain active for longer, the task will be cancelled.</p>  <p>The larger the application, the larger the timeout value should be, as the gating factor is associated with download time and processing time required to load the application files.</p>  <p>A server instance checks for timed out deployments about once a minute.</p>  <p>Only cluster deployments can be timed out.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3600000));
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link weblogic.deploy.api.spi.DeploymentOptions#getClusterDeploymentTimeout()} ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DeploymentType")) {
         getterName = "getDeploymentType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentType";
         }

         currentResult = new PropertyDescriptor("DeploymentType", ApplicationMBean.class, getterName, setterName);
         descriptors.put("DeploymentType", currentResult);
         currentResult.setValue("description", "<p>Specifies the category of this application. This attribute will be derived if not specified in the configuration.</p> ");
         setPropertyDescriptorDefault(currentResult, "UNKNOWN");
         currentResult.setValue("legalValues", new Object[]{"EAR", "EXPLODED EAR", "COMPONENT", "EXPLODED COMPONENT", "UNKNOWN"});
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("EJBComponents")) {
         getterName = "getEJBComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBComponents", ApplicationMBean.class, getterName, setterName);
         descriptors.put("EJBComponents", currentResult);
         currentResult.setValue("description", "<p> Returns the EJB components that make up this application. Components represent the Java EE modules associated with this application. <p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEJBComponent");
         currentResult.setValue("destroyer", "destroyEJBComponent");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FullPath")) {
         getterName = "getFullPath";
         setterName = null;
         currentResult = new PropertyDescriptor("FullPath", ApplicationMBean.class, getterName, setterName);
         descriptors.put("FullPath", currentResult);
         currentResult.setValue("description", "<p>The fully qualified source path of an application on an Administration Server.</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InternalType")) {
         getterName = "getInternalType";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalType", ApplicationMBean.class, getterName, setterName);
         descriptors.put("InternalType", currentResult);
         currentResult.setValue("description", "<p>Returns the internal type of the application. (EAR, COMPONENT, EXPLODED_EAR, EXPLODED_COMPONENT) This is needed because j2ee.Component needs to be able to determine how the application is packaged in order to correctly deploy it on the managed server.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCPoolComponents")) {
         getterName = "getJDBCPoolComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCPoolComponents", ApplicationMBean.class, getterName, setterName);
         descriptors.put("JDBCPoolComponents", currentResult);
         currentResult.setValue("description", "<p>Returns the JDBCPool components (JDBC modules) included in this application. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBCPoolComponent");
         currentResult.setValue("destroyer", "destroyJDBCPoolComponent");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LoadOrder")) {
         getterName = "getLoadOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoadOrder";
         }

         currentResult = new PropertyDescriptor("LoadOrder", ApplicationMBean.class, getterName, setterName);
         descriptors.put("LoadOrder", currentResult);
         currentResult.setValue("description", "<p>A numerical value that indicates when this module or application is deployed, relative to other deployable modules and applications. Modules with lower Load Order values are deployed before those with higher values. (Requires that you enable the two-phase deployment protocol.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isTwoPhase")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
      }

      if (!descriptors.containsKey("Notes")) {
         getterName = "getNotes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNotes";
         }

         currentResult = new PropertyDescriptor("Notes", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Notes", currentResult);
         currentResult.setValue("description", "<p>Optional information that you can include to describe this configuration.</p>  <p>WebLogic Server saves this note in the domain's configuration file (<code>config.xml</code>) as XML PCDATA. All left angle brackets (&lt;) are converted to the XML entity <code>&amp;lt;</code>. Carriage returns/line feeds are preserved.</p>  <p>Note: If you create or edit a note from the Administration Console, the Administration Console does not preserve carriage returns/line feeds.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowedSet", seeObjectArray);
         currentResult.setValue("owner", "Context");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("Path")) {
         getterName = "getPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPath";
         }

         currentResult = new PropertyDescriptor("Path", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Path", currentResult);
         currentResult.setValue("description", "<p>The URI, located on the Administration Server, of the original source files for this application.</p>  <p>Relative paths are based on the root of the Administration Server installation directory. It is highly recommended that you use absolute paths to minimize possible issues when upgrading the server.</p>  <p>If the application is not being staged (StagingMode==nostage) then the path must be valid on the target server.</p>  <p>The path to an Enterprise application (EAR) is the location of the EAR file or the root of the EAR if it is unarchived, e.g., Path=\"myapps/app.ear\" is valid. If the application is a standalone module, then the path is the parent directory of the module. For example, if the module is located at myapps/webapp/webapp.war, the Path=\"myapps/webapp\" is correct, whereas Path=\"myapps/webapp/webapp.war\" is incorrect.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StagedTargets")) {
         getterName = "getStagedTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("StagedTargets", ApplicationMBean.class, getterName, setterName);
         descriptors.put("StagedTargets", currentResult);
         currentResult.setValue("description", "<p>List of servers on which this application is known to be staged. This makes no distinction regarding the version or state of the staged files, just that they are staged. The array returned contains the names of the target servers. This list should not include cluster names.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StagingMode")) {
         getterName = "getStagingMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStagingMode";
         }

         currentResult = new PropertyDescriptor("StagingMode", ApplicationMBean.class, getterName, setterName);
         descriptors.put("StagingMode", currentResult);
         currentResult.setValue("description", "<p>The mode that specifies whether an application's files are copied from a source on the Administration Server to the Managed Server's staging area during application preparation. Staging mode for an application can only be set the first time the application is deployed. Once staging mode for an application is set, it cannot be changed while the application is configured in the domain. The only way to change staging mode is to undeploy then redeploy the application.</p>  <p>Staging involves distributing the application files from the Administration Server to the targeted Managed Servers staging directory. This attribute is used to override the Managed Server's StagingMode attribute.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{ApplicationMBean.DEFAULT_STAGE, "nostage", "stage", "external_stage"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StagingPath")) {
         getterName = "getStagingPath";
         setterName = null;
         currentResult = new PropertyDescriptor("StagingPath", ApplicationMBean.class, getterName, setterName);
         descriptors.put("StagingPath", currentResult);
         currentResult.setValue("description", "<p>The directory that a Managed Server uses to prepare and activate an application.</p>  <p>The directory path is relative to the Managed Server's Staging Path. It is derived from the Path attribute, and depends on whether the application is being staged. If the Path attribute for application, myapp, is foo.ear, the staging path is set to myapp/foo.ear. If the path is C:/myapp.ear, the staging path is myapp/myapp.ear. If the application is not being staged (StagingMode==nostage), then the staging path is the same as the Path attribute. If this application is not being staged, the staging path is equivalent to the source path (Path attribute).</p> ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("WebAppComponents")) {
         getterName = "getWebAppComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("WebAppComponents", ApplicationMBean.class, getterName, setterName);
         descriptors.put("WebAppComponents", currentResult);
         currentResult.setValue("description", "<p>The WebApp components (Java EE modules) that make up this application.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWebAppComponent");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServiceComponents")) {
         getterName = "getWebServiceComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServiceComponents", ApplicationMBean.class, getterName, setterName);
         descriptors.put("WebServiceComponents", currentResult);
         currentResult.setValue("description", "<p>Returns the WebService components that make up this application. Components represent the Java EE modules associated with this application. <p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWebServiceComponent");
         currentResult.setValue("creator", "createWebServiceComponent");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Deployed")) {
         getterName = "isDeployed";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeployed";
         }

         currentResult = new PropertyDescriptor("Deployed", ApplicationMBean.class, getterName, setterName);
         descriptors.put("Deployed", currentResult);
         currentResult.setValue("description", "<p>The deployed attribute is no longer supported as of version 9.x It remains here to support parsing of existing configuration files in which this value was stored</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ApplicationMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("InternalApp")) {
         getterName = "isInternalApp";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalApp", ApplicationMBean.class, getterName, setterName);
         descriptors.put("InternalApp", currentResult);
         currentResult.setValue("description", "<p>Indicates whether this application is an internal application. Such applications are not displayed in the console. For OAM internal use only.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("TwoPhase")) {
         getterName = "isTwoPhase";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTwoPhase";
         }

         currentResult = new PropertyDescriptor("TwoPhase", ApplicationMBean.class, getterName, setterName);
         descriptors.put("TwoPhase", currentResult);
         currentResult.setValue("description", "<p>Specifies whether this application is deployed using the two-phase deployment protocol.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.DeployerRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "Always returns true ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("destroyWebAppComponent", WebAppComponentMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys WebAppComponents</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebAppComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      String methodKey;
      ParameterDescriptor[] parameterDescriptors;
      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("createEJBComponent", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create EJBComponentMBean instance in the domain<p>  This method is here to force the binding code to generate correctly. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "EJBComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("destroyEJBComponent", EJBComponentMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys EJBComponents</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "EJBComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("createConnectorComponent", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create ConnectorComponentMBean instance in the domain<p>  This method is here to force the binding code to generate correctly. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectorComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("destroyConnectorComponent", ConnectorComponentMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys ConnectorComponents</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ConnectorComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("createWebServiceComponent", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create WebServiceComponentMBean instance in the domain<p>  This method is here to force the binding code to generate correctly. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebServiceComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("destroyWebServiceComponent", WebServiceComponentMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys WebServiceComponents</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebServiceComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("createJDBCPoolComponent", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create JDBCPoolComponentMBean instances in the domain<p>  This method is here to force the binding code to generate correctly. ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCPoolComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("destroyJDBCPoolComponent", JDBCPoolComponentMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>destroys JDBCPoolComponent</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCPoolComponents");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("addTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be added to the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Add a tag to this Configuration MBean.  Adds a tag to the current set of tags on the Configuration MBean.  Tags may contain white spaces.</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("removeTag", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("tag", "tag to be removed from the MBean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException if the tag contains illegal punctuation")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Remove a tag from this Configuration MBean</p> ");
            currentResult.setValue("role", "collection");
            currentResult.setValue("property", "Tags");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationMBean.class.getMethod("lookupWebAppComponent", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WebAppComponents");
      }

      mth = ApplicationMBean.class.getMethod("lookupEJBComponent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "EJBComponents");
      }

      mth = ApplicationMBean.class.getMethod("lookupConnectorComponent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ConnectorComponents");
      }

      mth = ApplicationMBean.class.getMethod("lookupWebServiceComponent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WebServiceComponents");
      }

      mth = ApplicationMBean.class.getMethod("lookupJDBCPoolComponent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns a JDBCComponentMBean with the specified name.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JDBCPoolComponents");
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ApplicationMBean.class.getMethod("freezeCurrentValue", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has not been set explicitly, and if the attribute has a default value, this operation forces the MBean to persist the default value.</p>  <p>Unless you use this operation, the default value is not saved and is subject to change if you update to a newer release of WebLogic Server. Invoking this operation isolates this MBean from the effects of such changes.</p>  <p>Note: To insure that you are freezing the default value, invoke the <code>restoreDefaultValue</code> operation before you invoke this.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute for which some other value has been set.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ApplicationMBean.class.getMethod("restoreDefaultValue", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("attributeName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey) && !this.readOnly) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>If the specified attribute has a default value, this operation removes any value that has been set explicitly and causes the attribute to use the default value.</p>  <p>Default values are subject to change if you update to a newer release of WebLogic Server. To prevent the value from changing if you update to a newer release, invoke the <code>freezeCurrentValue</code> operation.</p>  <p>This operation has no effect if you invoke it on an attribute that does not provide a default value or on an attribute that is already using the default.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("impact", "action");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("stagingEnabled", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("server", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Convenience method for determining whether this application is to be staged on a particular server.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("staged", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("server", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Convenience method for determining whether this application is currently staged on a particular server.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion)) {
         mth = ApplicationMBean.class.getMethod("useStagingDirectory", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("server", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "7.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Convenience method for determining where the file will be loaded from on the managed servers.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "7.0.0.0");
         }
      }

      mth = ApplicationMBean.class.getMethod("refreshDDsIfNeeded", String[].class, String[].class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ApplicationMBean.class.getMethod("returnDeployableUnit");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
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
