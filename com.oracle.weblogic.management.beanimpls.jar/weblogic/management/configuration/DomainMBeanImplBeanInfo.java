package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DomainMBeanImplBeanInfo extends ConfigurationPropertiesMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DomainMBean.class;

   public DomainMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DomainMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.DomainMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>A WebLogic Domain is a group of servers and/or clusters which are administered as a group.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.DomainMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AdminConsole")) {
         getterName = "getAdminConsole";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminConsole", DomainMBean.class, getterName, setterName);
         descriptors.put("AdminConsole", currentResult);
         currentResult.setValue("description", "get AdminConsoleMBean object, a console specific MBean to configure weblogic administration console attributes. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("AdminServerMBean")) {
         getterName = "getAdminServerMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminServerMBean", DomainMBean.class, getterName, setterName);
         descriptors.put("AdminServerMBean", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAdminServerMBean");
         currentResult.setValue("creator", "createAdminServerMBean");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("AdminServerName")) {
         getterName = "getAdminServerName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdminServerName";
         }

         currentResult = new PropertyDescriptor("AdminServerName", DomainMBean.class, getterName, setterName);
         descriptors.put("AdminServerName", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("AdministrationPort")) {
         getterName = "getAdministrationPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationPort";
         }

         currentResult = new PropertyDescriptor("AdministrationPort", DomainMBean.class, getterName, setterName);
         descriptors.put("AdministrationPort", currentResult);
         currentResult.setValue("description", "<p>The common secure administration port for this WebLogic Server domain. (Requires you to enable the administration port.)</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isAdministrationPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getAdministrationPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(9002));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AdministrationProtocol")) {
         getterName = "getAdministrationProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationProtocol";
         }

         currentResult = new PropertyDescriptor("AdministrationProtocol", DomainMBean.class, getterName, setterName);
         descriptors.put("AdministrationProtocol", currentResult);
         currentResult.setValue("description", "<p>The default protocol for communicating through the administration port or administration channels. (Requires you to enable the administration port or to create an administration channel.)</p>  <p>If requests through the administration port or an administration channel do not specify a protocol, WebLogic Server uses the protocol specified here.</p>  <p>Valid admin protocols are:</p> <ul> <li> t3s</li> <li> https</li> <li> iiops</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isAdministrationPortEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "t3s");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AppDeployments")) {
         getterName = "getAppDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("AppDeployments", DomainMBean.class, getterName, setterName);
         descriptors.put("AppDeployments", currentResult);
         currentResult.setValue("description", "<p>The collection of deployable entities in this domain. This replaces the Application in previous versions. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("Applications")) {
         getterName = "getApplications";
         setterName = null;
         currentResult = new PropertyDescriptor("Applications", DomainMBean.class, getterName, setterName);
         descriptors.put("Applications", currentResult);
         currentResult.setValue("description", "<p>Define applications for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createApplication");
         currentResult.setValue("destroyer", "destroyApplication");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("ArchiveConfigurationCount")) {
         getterName = "getArchiveConfigurationCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setArchiveConfigurationCount";
         }

         currentResult = new PropertyDescriptor("ArchiveConfigurationCount", DomainMBean.class, getterName, setterName);
         descriptors.put("ArchiveConfigurationCount", currentResult);
         currentResult.setValue("description", "<p>The number of archival versions of <code>config.xml</code> saved by the Administration Server each time the domain configuration is modified.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BatchConfig")) {
         getterName = "getBatchConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("BatchConfig", DomainMBean.class, getterName, setterName);
         descriptors.put("BatchConfig", currentResult);
         currentResult.setValue("description", "<p>Returns the BatchConfigMBean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BatchJobsDataSourceJndiName")) {
         getterName = "getBatchJobsDataSourceJndiName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchJobsDataSourceJndiName";
         }

         currentResult = new PropertyDescriptor("BatchJobsDataSourceJndiName", DomainMBean.class, getterName, setterName);
         descriptors.put("BatchJobsDataSourceJndiName", currentResult);
         currentResult.setValue("description", "<p>Returns the Batch DataSource jndi name. This jndi name will be used to lookup an instance of a DataSource that will be used to store the Batch jobs data.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BatchJobsExecutorServiceName")) {
         getterName = "getBatchJobsExecutorServiceName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setBatchJobsExecutorServiceName";
         }

         currentResult = new PropertyDescriptor("BatchJobsExecutorServiceName", DomainMBean.class, getterName, setterName);
         descriptors.put("BatchJobsExecutorServiceName", currentResult);
         currentResult.setValue("description", "Returns the name of the application-scoped ManagedExecutorService. This ManagedExecutorService instance will be used to run batch jobs that are submitted from applications deployed to the domain. The ManagedExecutorServiceTemplate by the same name must exit when a batch job is submitted in the domain. If this returns null, then the batch runtime will look to use the default Java EE ManagedExecutorService that is bound to the JNDI name: <code>java:comp/DefaultManagedExecutorService</code>. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("BridgeDestinations")) {
         getterName = "getBridgeDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("BridgeDestinations", DomainMBean.class, getterName, setterName);
         descriptors.put("BridgeDestinations", currentResult);
         currentResult.setValue("description", "<p>Return the BridgeDestinations for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createBridgeDestination");
         currentResult.setValue("destroyer", "destroyBridgeDestination");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("Callouts")) {
         getterName = "getCallouts";
         setterName = null;
         currentResult = new PropertyDescriptor("Callouts", DomainMBean.class, getterName, setterName);
         descriptors.put("Callouts", currentResult);
         currentResult.setValue("description", "<p>Retrieve Callouts for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCallout");
         currentResult.setValue("creator", "createCallout");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "14.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CdiContainer")) {
         getterName = "getCdiContainer";
         setterName = null;
         currentResult = new PropertyDescriptor("CdiContainer", DomainMBean.class, getterName, setterName);
         descriptors.put("CdiContainer", currentResult);
         currentResult.setValue("description", "Collection of global properties to be applied on all apps that need or support CDI in this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Clusters")) {
         getterName = "getClusters";
         setterName = null;
         currentResult = new PropertyDescriptor("Clusters", DomainMBean.class, getterName, setterName);
         descriptors.put("Clusters", currentResult);
         currentResult.setValue("description", "<p>Returns the ClusterMBeans representing the cluster that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCluster");
         currentResult.setValue("creator", "createCluster");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceClusterSystemResources")) {
         getterName = "getCoherenceClusterSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceClusterSystemResources", DomainMBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterSystemResources", currentResult);
         currentResult.setValue("description", "<p>The CoherenceClusterSystemResourceMBeans that have been defined for this domain. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceClusterSystemResource");
         currentResult.setValue("creator", "createCoherenceClusterSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceManagementClusters")) {
         getterName = "getCoherenceManagementClusters";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceManagementClusters", DomainMBean.class, getterName, setterName);
         descriptors.put("CoherenceManagementClusters", currentResult);
         currentResult.setValue("description", "<p>The CoherenceManagementClusterMBean that have been defined for this domain. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCoherenceManagementCluster");
         currentResult.setValue("destroyer", "destroyCoherenceManagementCluster");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("CoherenceServers")) {
         getterName = "getCoherenceServers";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceServers", DomainMBean.class, getterName, setterName);
         descriptors.put("CoherenceServers", currentResult);
         currentResult.setValue("description", "<p>The CoherenceServerMBeans that have been defined for this domain. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCoherenceServer");
         currentResult.setValue("creator", "createCoherenceServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (!descriptors.containsKey("ConfigurationAuditType")) {
         getterName = "getConfigurationAuditType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigurationAuditType";
         }

         currentResult = new PropertyDescriptor("ConfigurationAuditType", DomainMBean.class, getterName, setterName);
         descriptors.put("ConfigurationAuditType", currentResult);
         currentResult.setValue("description", "Returns the criteria used for auditing configuration events  (configuration changes and other operations): <ul> <li><code>CONFIG_CHANGE_NONE</code> Configuration events will neither be written to the server log or directed to the Security Audit Framework.</li> <li><code>CONFIG_CHANGE_LOG</code> Configuration events will be written to the server log.</li> <li><code>CONFIG_CHANGE_AUDIT</code>Configuration events will be directed to the Security Audit Framework.</li> <li><code>CONFIG_CHANGE_LOG_AND_AUDIT</code> Configuration events will be written to the server log and directed to the Security Audit Framework.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("secureValue", "audit");
         currentResult.setValue("legalValues", new Object[]{"none", "log", "audit", "logaudit"});
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigurationVersion")) {
         getterName = "getConfigurationVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigurationVersion";
         }

         currentResult = new PropertyDescriptor("ConfigurationVersion", DomainMBean.class, getterName, setterName);
         descriptors.put("ConfigurationVersion", currentResult);
         currentResult.setValue("description", "<p>The release identifier for the configuration. This identifier will be used to indicate the version of the configuration. All server generated configurations will be established with the release identifier of the running server. The form of the version is major.minor.servicepack.rollingpatch. Not all parts of the version are required. i.e. \"7\" is acceptable.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ConsoleContextPath")) {
         getterName = "getConsoleContextPath";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsoleContextPath";
         }

         currentResult = new PropertyDescriptor("ConsoleContextPath", DomainMBean.class, getterName, setterName);
         descriptors.put("ConsoleContextPath", currentResult);
         currentResult.setValue("description", "<p>The context path that you want to use in URLs that specify the Administration Console. (Requires you to enable the Administration Console for the current domain.)</p>  <p>To access the Administration Console, you use the following URL: http://<i>listen-addess</i>:<i>listen-port</i>/<i>context-path</i>. For example, if you set the context path to <code>myconsole</code>, then you use the following URL to access the Administration Console: <code>http://localhost:7001/myconsole</code>.</p>  <p>To specify the listen address and listen port that you use to access the Administration Console, configure the listen address and listen port of the Administration Server.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isConsoleEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getListenAddress"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getListenPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "console");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsoleExtensionDirectory")) {
         getterName = "getConsoleExtensionDirectory";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsoleExtensionDirectory";
         }

         currentResult = new PropertyDescriptor("ConsoleExtensionDirectory", DomainMBean.class, getterName, setterName);
         descriptors.put("ConsoleExtensionDirectory", currentResult);
         currentResult.setValue("description", "<p>Returns the directory path that console extensions are loaded from.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#isConsoleEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "console-ext");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("CustomResources")) {
         getterName = "getCustomResources";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomResources", DomainMBean.class, getterName, setterName);
         descriptors.put("CustomResources", currentResult);
         currentResult.setValue("description", "<p>Returns the JMSSystemResourceMBeans that have been defined for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomResource");
         currentResult.setValue("creator", "createCustomResource");
         currentResult.setValue("destroyer", "destroyCustomResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DBPassiveModeGracePeriodSeconds")) {
         getterName = "getDBPassiveModeGracePeriodSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDBPassiveModeGracePeriodSeconds";
         }

         currentResult = new PropertyDescriptor("DBPassiveModeGracePeriodSeconds", DomainMBean.class, getterName, setterName);
         descriptors.put("DBPassiveModeGracePeriodSeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum amount of time, in seconds, that is allowed for in-progress work to complete before entering database passive mode.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DebugPatches")) {
         getterName = "getDebugPatches";
         setterName = null;
         currentResult = new PropertyDescriptor("DebugPatches", DomainMBean.class, getterName, setterName);
         descriptors.put("DebugPatches", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DeploymentConfiguration")) {
         getterName = "getDeploymentConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentConfiguration", DomainMBean.class, getterName, setterName);
         descriptors.put("DeploymentConfiguration", currentResult);
         currentResult.setValue("description", "<p>Return the deployment configuration for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Deployments")) {
         getterName = "getDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("Deployments", DomainMBean.class, getterName, setterName);
         descriptors.put("Deployments", currentResult);
         currentResult.setValue("description", "Returns the DeploymentsMBeans representing the deployments that have been deployed to be part of this domain. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainLibraries")) {
         getterName = "getDomainLibraries";
         setterName = null;
         currentResult = new PropertyDescriptor("DomainLibraries", DomainMBean.class, getterName, setterName);
         descriptors.put("DomainLibraries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DomainVersion")) {
         getterName = "getDomainVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDomainVersion";
         }

         currentResult = new PropertyDescriptor("DomainVersion", DomainMBean.class, getterName, setterName);
         descriptors.put("DomainVersion", currentResult);
         currentResult.setValue("description", "<p>Defines the common version of all servers in a domain. In a domain containing servers that are not all at the same release version, this attribute is used to determine the feature level that servers will assume.</p>  <p>The value must be less than or equal to the version of any managed server in the domain.</p>  <p>If this value is not equal to the version of the release version of the admin server, then the admin server will not be allowed to make modifications to the configuration.</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EJBContainer")) {
         getterName = "getEJBContainer";
         setterName = null;
         currentResult = new PropertyDescriptor("EJBContainer", DomainMBean.class, getterName, setterName);
         descriptors.put("EJBContainer", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEJBContainer");
         currentResult.setValue("destroyer", "destroyEJBContainer");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EmbeddedLDAP")) {
         getterName = "getEmbeddedLDAP";
         setterName = null;
         currentResult = new PropertyDescriptor("EmbeddedLDAP", DomainMBean.class, getterName, setterName);
         descriptors.put("EmbeddedLDAP", currentResult);
         currentResult.setValue("description", "<p>Returns the embedded LDAP configuration for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("FileStores")) {
         getterName = "getFileStores";
         setterName = null;
         currentResult = new PropertyDescriptor("FileStores", DomainMBean.class, getterName, setterName);
         descriptors.put("FileStores", currentResult);
         currentResult.setValue("description", "<p>Return file stores defined in this domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFileStore");
         currentResult.setValue("creator", "createFileStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("FileT3s")) {
         getterName = "getFileT3s";
         setterName = null;
         currentResult = new PropertyDescriptor("FileT3s", DomainMBean.class, getterName, setterName);
         descriptors.put("FileT3s", currentResult);
         currentResult.setValue("description", "<p>Returns the FileT3MBeans representing the FileT3s that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFileT3");
         currentResult.setValue("creator", "createFileT3");
         currentResult.setValue("deprecated", "8.1.0.0 ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ForeignJMSConnectionFactories")) {
         getterName = "getForeignJMSConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJMSConnectionFactories", DomainMBean.class, getterName, setterName);
         descriptors.put("ForeignJMSConnectionFactories", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignJMSConnectionFactory");
         currentResult.setValue("creator", "createForeignJMSConnectionFactory");
         currentResult.setValue("destroyer", "destroyForeignJMSConnectionFactory");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ForeignJMSDestinations")) {
         getterName = "getForeignJMSDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJMSDestinations", DomainMBean.class, getterName, setterName);
         descriptors.put("ForeignJMSDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignJMSDestination");
         currentResult.setValue("creator", "createForeignJMSDestination");
         currentResult.setValue("creator", "createForeignJMSDestination");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("ForeignJMSServers")) {
         getterName = "getForeignJMSServers";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJMSServers", DomainMBean.class, getterName, setterName);
         descriptors.put("ForeignJMSServers", currentResult);
         currentResult.setValue("description", "Get all the defined Foreign JMS Servers ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyForeignJMSServer");
         currentResult.setValue("creator", "createForeignJMSServer");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ForeignJNDIProviders")) {
         getterName = "getForeignJNDIProviders";
         setterName = null;
         currentResult = new PropertyDescriptor("ForeignJNDIProviders", DomainMBean.class, getterName, setterName);
         descriptors.put("ForeignJNDIProviders", currentResult);
         currentResult.setValue("description", "Get all the defined Foreign JNDI Providers ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createForeignJNDIProvider");
         currentResult.setValue("destroyer", "destroyForeignJNDIProvider");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("InstalledSoftwareVersion")) {
         getterName = "getInstalledSoftwareVersion";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInstalledSoftwareVersion";
         }

         currentResult = new PropertyDescriptor("InstalledSoftwareVersion", DomainMBean.class, getterName, setterName);
         descriptors.put("InstalledSoftwareVersion", currentResult);
         currentResult.setValue("description", "<p>Installed software version that can be used for rolling updates of config changes</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Interceptors")) {
         getterName = "getInterceptors";
         setterName = null;
         currentResult = new PropertyDescriptor("Interceptors", DomainMBean.class, getterName, setterName);
         descriptors.put("Interceptors", currentResult);
         currentResult.setValue("description", "Get the InterceptorMBeans for this domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InternalAppDeployments")) {
         getterName = "getInternalAppDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalAppDeployments", DomainMBean.class, getterName, setterName);
         descriptors.put("InternalAppDeployments", currentResult);
         currentResult.setValue("description", "<p>The collection of internal application deployments in this domain</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("restRelationship", "containment");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InternalLibraries")) {
         getterName = "getInternalLibraries";
         setterName = null;
         currentResult = new PropertyDescriptor("InternalLibraries", DomainMBean.class, getterName, setterName);
         descriptors.put("InternalLibraries", currentResult);
         currentResult.setValue("description", "<p>The collection of internal libraries in this domain</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("restRelationship", "containment");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JDBCStores")) {
         getterName = "getJDBCStores";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCStores", DomainMBean.class, getterName, setterName);
         descriptors.put("JDBCStores", currentResult);
         currentResult.setValue("description", "<p>Return file stores defined in this domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBCStore");
         currentResult.setValue("destroyer", "destroyJDBCStore");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JDBCSystemResources")) {
         getterName = "getJDBCSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCSystemResources", DomainMBean.class, getterName, setterName);
         descriptors.put("JDBCSystemResources", currentResult);
         currentResult.setValue("description", "<p>Returns the JDBCSystemResourceMBeans that have been defined for this domain </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDBCSystemResource");
         currentResult.setValue("creator", "createJDBCSystemResource");
         currentResult.setValue("creator", "createJDBCSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("JMSBridgeDestinations")) {
         getterName = "getJMSBridgeDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSBridgeDestinations", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSBridgeDestinations", currentResult);
         currentResult.setValue("description", "<p>Return the JMSBridgeDestinations for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSBridgeDestination");
         currentResult.setValue("destroyer", "destroyJMSBridgeDestination");
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSConnectionConsumers")) {
         getterName = "getJMSConnectionConsumers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSConnectionConsumers", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSConnectionConsumers", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSConnectionConsumer");
         currentResult.setValue("destroyer", "destroyJMSConnectionConsumer");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSConnectionFactories")) {
         getterName = "getJMSConnectionFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSConnectionFactories", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSConnectionFactories", currentResult);
         currentResult.setValue("description", "<p>Return the JMSConnectionFactorys for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSConnectionFactory");
         currentResult.setValue("destroyer", "destroyJMSConnectionFactory");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDestinationKeys")) {
         getterName = "getJMSDestinationKeys";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDestinationKeys", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDestinationKeys", currentResult);
         currentResult.setValue("description", "<p>Retrieve JMSDestinationKeys for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSDestinationKey");
         currentResult.setValue("destroyer", "destroyJMSDestinationKey");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDestinations")) {
         getterName = "getJMSDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDestinations", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDestinations", currentResult);
         currentResult.setValue("description", "<p>Retrieve JMSDestinations for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDistributedQueueMembers")) {
         getterName = "getJMSDistributedQueueMembers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDistributedQueueMembers", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDistributedQueueMembers", currentResult);
         currentResult.setValue("description", "<p>Define JMSDistributedQueueMembers for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMSDistributedQueueMember");
         currentResult.setValue("creator", "createJMSDistributedQueueMember");
         currentResult.setValue("creator", "createJMSDistributedQueueMember");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDistributedQueues")) {
         getterName = "getJMSDistributedQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDistributedQueues", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDistributedQueues", currentResult);
         currentResult.setValue("description", "<p>Define JMSDistributedQueues for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSDistributedQueue");
         currentResult.setValue("destroyer", "destroyJMSDistributedQueue");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDistributedTopicMembers")) {
         getterName = "getJMSDistributedTopicMembers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDistributedTopicMembers", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDistributedTopicMembers", currentResult);
         currentResult.setValue("description", "<p>Define JMSDistributedTopicMembers for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSDistributedTopicMember");
         currentResult.setValue("creator", "createJMSDistributedTopicMember");
         currentResult.setValue("destroyer", "destroyJMSDistributedTopicMember");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSDistributedTopics")) {
         getterName = "getJMSDistributedTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSDistributedTopics", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSDistributedTopics", currentResult);
         currentResult.setValue("description", "<p>Define JMSDistributedTopics for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMSDistributedTopic");
         currentResult.setValue("creator", "createJMSDistributedTopic");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSFileStores")) {
         getterName = "getJMSFileStores";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSFileStores", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSFileStores", currentResult);
         currentResult.setValue("description", "<p>Define JMSFileStores for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSFileStore");
         currentResult.setValue("destroyer", "destroyJMSFileStore");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSJDBCStores")) {
         getterName = "getJMSJDBCStores";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSJDBCStores", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSJDBCStores", currentResult);
         currentResult.setValue("description", "<p>Define JMSJDBCStores for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSJDBCStore");
         currentResult.setValue("destroyer", "destroyJMSJDBCStore");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSQueues")) {
         getterName = "getJMSQueues";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSQueues", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSQueues", currentResult);
         currentResult.setValue("description", "<p>Define JMSQueues for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSQueue");
         currentResult.setValue("destroyer", "destroyJMSQueue");
         currentResult.setValue("creator", "createJMSQueue");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("JMSServers")) {
         getterName = "getJMSServers";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSServers", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSServers", currentResult);
         currentResult.setValue("description", "<p>Define JMSServers for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSServer");
         currentResult.setValue("destroyer", "destroyJMSServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSSessionPools")) {
         getterName = "getJMSSessionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSessionPools", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSSessionPools", currentResult);
         currentResult.setValue("description", "<p>Return the JMSSessionPools for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSSessionPool");
         currentResult.setValue("creator", "createJMSSessionPool");
         currentResult.setValue("destroyer", "destroyJMSSessionPool");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSStores")) {
         getterName = "getJMSStores";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSStores", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSStores", currentResult);
         currentResult.setValue("description", "<p>Define JMSStores for this Domain</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JMSSystemResources")) {
         getterName = "getJMSSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSSystemResources", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSSystemResources", currentResult);
         currentResult.setValue("description", "<p>Returns the JMSSystemResourceMBeans that have been defined for this domain. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJMSSystemResource");
         currentResult.setValue("creator", "createJMSSystemResource");
         currentResult.setValue("creator", "createJMSSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSTemplates")) {
         getterName = "getJMSTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSTemplates", currentResult);
         currentResult.setValue("description", "<p>Define JMSTemplates for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSTemplate");
         currentResult.setValue("destroyer", "destroyJMSTemplate");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("JMSTopics")) {
         getterName = "getJMSTopics";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSTopics", DomainMBean.class, getterName, setterName);
         descriptors.put("JMSTopics", currentResult);
         currentResult.setValue("description", "<p>Define JMSTopics for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSTopic");
         currentResult.setValue("creator", "createJMSTopic");
         currentResult.setValue("destroyer", "destroyJMSTopic");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JMX")) {
         getterName = "getJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("JMX", DomainMBean.class, getterName, setterName);
         descriptors.put("JMX", currentResult);
         currentResult.setValue("description", "The configuration of the JMX Subsystem. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("JPA")) {
         getterName = "getJPA";
         setterName = null;
         currentResult = new PropertyDescriptor("JPA", DomainMBean.class, getterName, setterName);
         descriptors.put("JPA", currentResult);
         currentResult.setValue("description", "<p>Return the JPA configuration for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTA")) {
         getterName = "getJTA";
         setterName = null;
         currentResult = new PropertyDescriptor("JTA", DomainMBean.class, getterName, setterName);
         descriptors.put("JTA", currentResult);
         currentResult.setValue("description", "<p>Return the JTA configuration for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoltConnectionPools")) {
         getterName = "getJoltConnectionPools";
         setterName = null;
         currentResult = new PropertyDescriptor("JoltConnectionPools", DomainMBean.class, getterName, setterName);
         descriptors.put("JoltConnectionPools", currentResult);
         currentResult.setValue("description", "<p>Return the JoltConnectionPools for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJoltConnectionPool");
         currentResult.setValue("creator", "createJoltConnectionPool");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastModificationTime")) {
         getterName = "getLastModificationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastModificationTime", DomainMBean.class, getterName, setterName);
         descriptors.put("LastModificationTime", currentResult);
         currentResult.setValue("description", "<p>Return the last time this domain was updated. This is guaranteed to be unique for a given transactional modification.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Libraries")) {
         getterName = "getLibraries";
         setterName = null;
         currentResult = new PropertyDescriptor("Libraries", DomainMBean.class, getterName, setterName);
         descriptors.put("Libraries", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LifecycleManagerConfig")) {
         getterName = "getLifecycleManagerConfig";
         setterName = null;
         currentResult = new PropertyDescriptor("LifecycleManagerConfig", DomainMBean.class, getterName, setterName);
         descriptors.put("LifecycleManagerConfig", currentResult);
         currentResult.setValue("description", "<p>Returns the LifecycleManagerConfigMBean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LifecycleManagerEndPoints")) {
         getterName = "getLifecycleManagerEndPoints";
         setterName = null;
         currentResult = new PropertyDescriptor("LifecycleManagerEndPoints", DomainMBean.class, getterName, setterName);
         descriptors.put("LifecycleManagerEndPoints", currentResult);
         currentResult.setValue("description", "<p>Returns the LifecycleManager endpoints that have been configured within this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLifecycleManagerEndPoint");
         currentResult.setValue("creator", "createLifecycleManagerEndPoint");
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Log")) {
         getterName = "getLog";
         setterName = null;
         currentResult = new PropertyDescriptor("Log", DomainMBean.class, getterName, setterName);
         descriptors.put("Log", currentResult);
         currentResult.setValue("description", "<p>Return the domain logfile configuration for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFilters")) {
         getterName = "getLogFilters";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFilters", DomainMBean.class, getterName, setterName);
         descriptors.put("LogFilters", currentResult);
         currentResult.setValue("description", "Gets the array of log filters defined in the domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLogFilter");
         currentResult.setValue("creator", "createLogFilter");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("Machines")) {
         getterName = "getMachines";
         setterName = null;
         currentResult = new PropertyDescriptor("Machines", DomainMBean.class, getterName, setterName);
         descriptors.put("Machines", currentResult);
         currentResult.setValue("description", "<p>Define machines for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMachine");
         currentResult.setValue("destroyer", "destroyMachine");
         currentResult.setValue("creator.UnixMachineMBean", "createUnixMachine");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MailSessions")) {
         getterName = "getMailSessions";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessions", DomainMBean.class, getterName, setterName);
         descriptors.put("MailSessions", currentResult);
         currentResult.setValue("description", "<p>Retrieve MailSessions for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMailSession");
         currentResult.setValue("creator", "createMailSession");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedExecutorServiceTemplates")) {
         getterName = "getManagedExecutorServiceTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServiceTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServiceTemplates", currentResult);
         currentResult.setValue("description", "Get all the ManagedExecutorService templates ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedExecutorServiceTemplate");
         currentResult.setValue("destroyer", "destroyManagedExecutorServiceTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedExecutorServices")) {
         getterName = "getManagedExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedExecutorServices", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedExecutorServices", currentResult);
         currentResult.setValue("description", "Get all the ManagedExecutorServices ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedExecutorService");
         currentResult.setValue("creator", "createManagedExecutorService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedScheduledExecutorServiceTemplates")) {
         getterName = "getManagedScheduledExecutorServiceTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServiceTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServiceTemplates", currentResult);
         currentResult.setValue("description", "Get all the ManagedScheduledExecutorService ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorServiceTemplate");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorServiceTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedScheduledExecutorServices")) {
         getterName = "getManagedScheduledExecutorServices";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedScheduledExecutorServices", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedScheduledExecutorServices", currentResult);
         currentResult.setValue("description", "Get all the ManagedScheduledExecutorService ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedScheduledExecutorService");
         currentResult.setValue("destroyer", "destroyManagedScheduledExecutorService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedThreadFactories")) {
         getterName = "getManagedThreadFactories";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactories", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactories", currentResult);
         currentResult.setValue("description", "Get all the ManagedThreadFactories ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyManagedThreadFactory");
         currentResult.setValue("creator", "createManagedThreadFactory");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ManagedThreadFactoryTemplates")) {
         getterName = "getManagedThreadFactoryTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedThreadFactoryTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("ManagedThreadFactoryTemplates", currentResult);
         currentResult.setValue("description", "Get all the ManagedThreadFactory templates ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createManagedThreadFactoryTemplate");
         currentResult.setValue("destroyer", "destroyManagedThreadFactoryTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxConcurrentLongRunningRequests")) {
         getterName = "getMaxConcurrentLongRunningRequests";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentLongRunningRequests";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentLongRunningRequests", DomainMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentLongRunningRequests", currentResult);
         currentResult.setValue("description", "The maximum number of running long-running requests that can be submitted to all the Managed Executor Services or Managed Scheduled Executor Services on the current server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxConcurrentNewThreads")) {
         getterName = "getMaxConcurrentNewThreads";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxConcurrentNewThreads";
         }

         currentResult = new PropertyDescriptor("MaxConcurrentNewThreads", DomainMBean.class, getterName, setterName);
         descriptors.put("MaxConcurrentNewThreads", currentResult);
         currentResult.setValue("description", "The maximum number of running threads that can be created by all the Managed Thread Factories on the current server. ");
         setPropertyDescriptorDefault(currentResult, new Integer(50));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("MessagingBridges")) {
         getterName = "getMessagingBridges";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagingBridges", DomainMBean.class, getterName, setterName);
         descriptors.put("MessagingBridges", currentResult);
         currentResult.setValue("description", "<p>Returns the MessagingBridgeMBean representing the messaging bridges that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMessagingBridge");
         currentResult.setValue("destroyer", "destroyMessagingBridge");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigratableRMIServices")) {
         getterName = "getMigratableRMIServices";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratableRMIServices", DomainMBean.class, getterName, setterName);
         descriptors.put("MigratableRMIServices", currentResult);
         currentResult.setValue("description", "<p>Returns an array of the contained MigratableRMIService MBeans</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMigratableRMIService");
         currentResult.setValue("creator", "createMigratableRMIService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigratableTargets")) {
         getterName = "getMigratableTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratableTargets", DomainMBean.class, getterName, setterName);
         descriptors.put("MigratableTargets", currentResult);
         currentResult.setValue("description", "<p>Returns an array of MigratableTarget MBeans.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMigratableTarget");
         currentResult.setValue("creator", "createMigratableTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", DomainMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>An alphanumeric name for this domain.</p>  <p>For more information on domain naming conventions, see <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=DOMCF237\" rel=\"noopener noreferrer\" target=\"_blank\">Domain and Server Name Restrictions</a> in Understanding Domain Configuration for Oracle WebLogic Server.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("NetworkChannels")) {
         getterName = "getNetworkChannels";
         setterName = null;
         currentResult = new PropertyDescriptor("NetworkChannels", DomainMBean.class, getterName, setterName);
         descriptors.put("NetworkChannels", currentResult);
         currentResult.setValue("description", "<p>Define NetworkChannels for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createNetworkChannel");
         currentResult.setValue("destroyer", "destroyNetworkChannel");
         currentResult.setValue("deprecated", "9.0.0.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("OptionalFeatureDeployment")) {
         getterName = "getOptionalFeatureDeployment";
         setterName = null;
         currentResult = new PropertyDescriptor("OptionalFeatureDeployment", DomainMBean.class, getterName, setterName);
         descriptors.put("OptionalFeatureDeployment", currentResult);
         currentResult.setValue("description", "The configuration of OptionalFeature deployment. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("OsgiFrameworks")) {
         getterName = "getOsgiFrameworks";
         setterName = null;
         currentResult = new PropertyDescriptor("OsgiFrameworks", DomainMBean.class, getterName, setterName);
         descriptors.put("OsgiFrameworks", currentResult);
         currentResult.setValue("description", "<p>OSGi framework definition for use by applications wishing to share services and code</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOsgiFramework");
         currentResult.setValue("creator", "createOsgiFramework");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionUriSpace")) {
         getterName = "getPartitionUriSpace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartitionUriSpace";
         }

         currentResult = new PropertyDescriptor("PartitionUriSpace", DomainMBean.class, getterName, setterName);
         descriptors.put("PartitionUriSpace", currentResult);
         currentResult.setValue("description", "<p>The URI prefix used for partition administrative virtual targets.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionWorkManagers")) {
         getterName = "getPartitionWorkManagers";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionWorkManagers", DomainMBean.class, getterName, setterName);
         descriptors.put("PartitionWorkManagers", currentResult);
         currentResult.setValue("description", "The partition work manager policy configurations. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPartitionWorkManager");
         currentResult.setValue("destroyer", "destroyPartitionWorkManager");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Partitions")) {
         getterName = "getPartitions";
         setterName = null;
         currentResult = new PropertyDescriptor("Partitions", DomainMBean.class, getterName, setterName);
         descriptors.put("Partitions", currentResult);
         currentResult.setValue("description", "<p> The partitions that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPartition");
         currentResult.setValue("creator", "createPartition");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("PathServices")) {
         getterName = "getPathServices";
         setterName = null;
         currentResult = new PropertyDescriptor("PathServices", DomainMBean.class, getterName, setterName);
         descriptors.put("PathServices", currentResult);
         currentResult.setValue("description", "Define PathService for this Domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPathService");
         currentResult.setValue("creator", "createPathService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowedGet", seeObjectArray);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ReplicatedStores")) {
         getterName = "getReplicatedStores";
         setterName = null;
         currentResult = new PropertyDescriptor("ReplicatedStores", DomainMBean.class, getterName, setterName);
         descriptors.put("ReplicatedStores", currentResult);
         currentResult.setValue("description", "<p>Return replicated memory stores defined in this domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyReplicatedStore");
         currentResult.setValue("creator", "createReplicatedStore");
         currentResult.setValue("deprecated", "12.2.1.3.0 Replace with custom file store or JDBC store. ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResourceGroupTemplates")) {
         getterName = "getResourceGroupTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceGroupTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("ResourceGroupTemplates", currentResult);
         currentResult.setValue("description", "<p>The resource group templates that have been configured in this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyResourceGroupTemplate");
         currentResult.setValue("creator", "createResourceGroupTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResourceGroups")) {
         getterName = "getResourceGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceGroups", DomainMBean.class, getterName, setterName);
         descriptors.put("ResourceGroups", currentResult);
         currentResult.setValue("description", "The resource groups at the domain level. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createResourceGroup");
         currentResult.setValue("destroyer", "destroyResourceGroup");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResourceManagement")) {
         getterName = "getResourceManagement";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceManagement", DomainMBean.class, getterName, setterName);
         descriptors.put("ResourceManagement", currentResult);
         currentResult.setValue("description", "Get the Resource Management Configuration for this domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.6.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestfulManagementServices")) {
         getterName = "getRestfulManagementServices";
         setterName = null;
         currentResult = new PropertyDescriptor("RestfulManagementServices", DomainMBean.class, getterName, setterName);
         descriptors.put("RestfulManagementServices", currentResult);
         currentResult.setValue("description", "The configuration of the Management Services Subsystem. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.6.0");
      }

      if (!descriptors.containsKey("RootDirectory")) {
         getterName = "getRootDirectory";
         setterName = null;
         currentResult = new PropertyDescriptor("RootDirectory", DomainMBean.class, getterName, setterName);
         descriptors.put("RootDirectory", currentResult);
         currentResult.setValue("description", "<p>Return the root directory for the domain. In other words for a server process [ServerMBean.getRootDirectory] or [ServerMBean.getDomainDirectory]</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFAgents")) {
         getterName = "getSAFAgents";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFAgents", DomainMBean.class, getterName, setterName);
         descriptors.put("SAFAgents", currentResult);
         currentResult.setValue("description", "<p>Get SAFAgentMBean for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySAFAgent");
         currentResult.setValue("creator", "createSAFAgent");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SNMPAgent")) {
         getterName = "getSNMPAgent";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAgent", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPAgent", currentResult);
         currentResult.setValue("description", "<p>Return the SNMPAgentMBean for this domain. This is a singleton MBean describing SNMP Agent configuration details. This MBean has getters and setters for other SNMP related configuration MBeans.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPAgentDeployments")) {
         getterName = "getSNMPAgentDeployments";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAgentDeployments", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPAgentDeployments", currentResult);
         currentResult.setValue("description", "The SNMPAgentDeployments defined in the domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPAgentDeployment");
         currentResult.setValue("creator", "createSNMPAgentDeployment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPAttributeChanges")) {
         getterName = "getSNMPAttributeChanges";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAttributeChanges", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPAttributeChanges", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPAttributeChange");
         currentResult.setValue("creator", "createSNMPAttributeChange");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPCounterMonitors")) {
         getterName = "getSNMPCounterMonitors";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPCounterMonitors", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPCounterMonitors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSNMPCounterMonitor");
         currentResult.setValue("destroyer", "destroySNMPCounterMonitor");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPGaugeMonitors")) {
         getterName = "getSNMPGaugeMonitors";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPGaugeMonitors", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPGaugeMonitors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPGaugeMonitor");
         currentResult.setValue("creator", "createSNMPGaugeMonitor");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPLogFilters")) {
         getterName = "getSNMPLogFilters";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPLogFilters", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPLogFilters", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPLogFilter");
         currentResult.setValue("creator", "createSNMPLogFilter");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPProxies")) {
         getterName = "getSNMPProxies";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPProxies", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPProxies", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSNMPProxy");
         currentResult.setValue("destroyer", "destroySNMPProxy");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPStringMonitors")) {
         getterName = "getSNMPStringMonitors";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPStringMonitors", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPStringMonitors", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPStringMonitor");
         currentResult.setValue("creator", "createSNMPStringMonitor");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("SNMPTrapDestinations")) {
         getterName = "getSNMPTrapDestinations";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPTrapDestinations", DomainMBean.class, getterName, setterName);
         descriptors.put("SNMPTrapDestinations", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySNMPTrapDestination");
         currentResult.setValue("creator", "createSNMPTrapDestination");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("SecurityConfiguration")) {
         getterName = "getSecurityConfiguration";
         setterName = null;
         currentResult = new PropertyDescriptor("SecurityConfiguration", DomainMBean.class, getterName, setterName);
         descriptors.put("SecurityConfiguration", currentResult);
         currentResult.setValue("description", "<p>Return the (new) security configuration for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SelfTuning")) {
         getterName = "getSelfTuning";
         setterName = null;
         currentResult = new PropertyDescriptor("SelfTuning", DomainMBean.class, getterName, setterName);
         descriptors.put("SelfTuning", currentResult);
         currentResult.setValue("description", "Get the WorkManager configuration pieces for this domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerMigrationHistorySize")) {
         getterName = "getServerMigrationHistorySize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerMigrationHistorySize";
         }

         currentResult = new PropertyDescriptor("ServerMigrationHistorySize", DomainMBean.class, getterName, setterName);
         descriptors.put("ServerMigrationHistorySize", currentResult);
         currentResult.setValue("description", "<p>Gets the history size of server migrations.</p>  <p>A value of -1 indicates that the history size is unlimited.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (!descriptors.containsKey("ServerTemplates")) {
         getterName = "getServerTemplates";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerTemplates", DomainMBean.class, getterName, setterName);
         descriptors.put("ServerTemplates", currentResult);
         currentResult.setValue("description", "<p>Returns the ServerTempateMBeans representing the server templates that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyServerTemplate");
         currentResult.setValue("creator", "createServerTemplate");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Servers")) {
         getterName = "getServers";
         setterName = null;
         currentResult = new PropertyDescriptor("Servers", DomainMBean.class, getterName, setterName);
         descriptors.put("Servers", currentResult);
         currentResult.setValue("description", "<p>Returns the ServerMBeans representing the servers that have been configured to be part of this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyServer");
         currentResult.setValue("creator", "createServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServiceMigrationHistorySize")) {
         getterName = "getServiceMigrationHistorySize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceMigrationHistorySize";
         }

         currentResult = new PropertyDescriptor("ServiceMigrationHistorySize", DomainMBean.class, getterName, setterName);
         descriptors.put("ServiceMigrationHistorySize", currentResult);
         currentResult.setValue("description", "<p>Gets the history size of service migrations.</p>  <p>A value of -1 indicates that the history size is unlimited.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(100));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (!descriptors.containsKey("ShutdownClasses")) {
         getterName = "getShutdownClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("ShutdownClasses", DomainMBean.class, getterName, setterName);
         descriptors.put("ShutdownClasses", currentResult);
         currentResult.setValue("description", "<p>Retrieve ShutdownClasses for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyShutdownClass");
         currentResult.setValue("creator", "createShutdownClass");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingletonServices")) {
         getterName = "getSingletonServices";
         setterName = null;
         currentResult = new PropertyDescriptor("SingletonServices", DomainMBean.class, getterName, setterName);
         descriptors.put("SingletonServices", currentResult);
         currentResult.setValue("description", "<p>Retrieve SingletonServicees for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSingletonService");
         currentResult.setValue("destroyer", "destroySingletonService");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SiteName")) {
         getterName = "getSiteName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSiteName";
         }

         currentResult = new PropertyDescriptor("SiteName", DomainMBean.class, getterName, setterName);
         descriptors.put("SiteName", currentResult);
         currentResult.setValue("description", "<p>The name of the site this domain is associated with.</p> ");
         currentResult.setValue("deprecated", "12.2.1.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("StartupClasses")) {
         getterName = "getStartupClasses";
         setterName = null;
         currentResult = new PropertyDescriptor("StartupClasses", DomainMBean.class, getterName, setterName);
         descriptors.put("StartupClasses", currentResult);
         currentResult.setValue("description", "<p>Retrieve StartupClasses for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStartupClass");
         currentResult.setValue("destroyer", "destroyStartupClass");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemComponentConfigurations")) {
         getterName = "getSystemComponentConfigurations";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemComponentConfigurations", DomainMBean.class, getterName, setterName);
         descriptors.put("SystemComponentConfigurations", currentResult);
         currentResult.setValue("description", "Returns the SystemComponentConfigurationMBeans, representing the system system component configurations that have been configured to be part of this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySystemComponentConfiguration");
         currentResult.setValue("creator", "createSystemComponentConfiguration");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemComponents")) {
         getterName = "getSystemComponents";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemComponents", DomainMBean.class, getterName, setterName);
         descriptors.put("SystemComponents", currentResult);
         currentResult.setValue("description", "Returns the SystemComponentMBeans, representing the system system components that have been configured to be part of this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSystemComponent");
         currentResult.setValue("destroyer", "destroySystemComponent");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SystemResources")) {
         getterName = "getSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("SystemResources", DomainMBean.class, getterName, setterName);
         descriptors.put("SystemResources", currentResult);
         currentResult.setValue("description", "<p>Return the SystemResourceMBeans in this Domain.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", DomainMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("Targets", DomainMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>Define targets for this Domain</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VirtualHosts")) {
         getterName = "getVirtualHosts";
         setterName = null;
         currentResult = new PropertyDescriptor("VirtualHosts", DomainMBean.class, getterName, setterName);
         descriptors.put("VirtualHosts", currentResult);
         currentResult.setValue("description", "<p>Defines virtual hosts for this domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createVirtualHost");
         currentResult.setValue("destroyer", "destroyVirtualHost");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("VirtualTargets")) {
         getterName = "getVirtualTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("VirtualTargets", DomainMBean.class, getterName, setterName);
         descriptors.put("VirtualTargets", currentResult);
         currentResult.setValue("description", "<p>Defines virtual targets for a domain partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyVirtualTarget");
         currentResult.setValue("creator", "createVirtualTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("obsolete", "true");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WLDFSystemResources")) {
         getterName = "getWLDFSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFSystemResources", DomainMBean.class, getterName, setterName);
         descriptors.put("WLDFSystemResources", currentResult);
         currentResult.setValue("description", "<p>Returns the WLDFSystemResourceMBeans that have been defined for this domain. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWLDFSystemResource");
         currentResult.setValue("creator", "createWLDFSystemResource");
         currentResult.setValue("creator", "createWLDFSystemResource");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("WSReliableDeliveryPolicies")) {
         getterName = "getWSReliableDeliveryPolicies";
         setterName = null;
         currentResult = new PropertyDescriptor("WSReliableDeliveryPolicies", DomainMBean.class, getterName, setterName);
         descriptors.put("WSReliableDeliveryPolicies", currentResult);
         currentResult.setValue("description", "<p>Define wSReliableDeliveryPolicies for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWSReliableDeliveryPolicy");
         currentResult.setValue("destroyer", "destroyWSReliableDeliveryPolicy");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WTCServers")) {
         getterName = "getWTCServers";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCServers", DomainMBean.class, getterName, setterName);
         descriptors.put("WTCServers", currentResult);
         currentResult.setValue("description", "<p>Return the WTCServerMBeans for this Domain.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWTCServer");
         currentResult.setValue("destroyer", "destroyWTCServer");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebAppContainer")) {
         getterName = "getWebAppContainer";
         setterName = null;
         currentResult = new PropertyDescriptor("WebAppContainer", DomainMBean.class, getterName, setterName);
         descriptors.put("WebAppContainer", currentResult);
         currentResult.setValue("description", "Collection of global properties to be applied on all webapps in this domain. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceSecurities")) {
         getterName = "getWebserviceSecurities";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceSecurities", DomainMBean.class, getterName, setterName);
         descriptors.put("WebserviceSecurities", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyWebserviceSecurity");
         currentResult.setValue("creator", "createWebserviceSecurity");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("WebserviceTestpage")) {
         getterName = "getWebserviceTestpage";
         setterName = null;
         currentResult = new PropertyDescriptor("WebserviceTestpage", DomainMBean.class, getterName, setterName);
         descriptors.put("WebserviceTestpage", currentResult);
         currentResult.setValue("description", "The configuration of Web Service Test Page. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (!descriptors.containsKey("XMLEntityCaches")) {
         getterName = "getXMLEntityCaches";
         setterName = null;
         currentResult = new PropertyDescriptor("XMLEntityCaches", DomainMBean.class, getterName, setterName);
         descriptors.put("XMLEntityCaches", currentResult);
         currentResult.setValue("description", "Returns all the XMLEntityCache objects defined in this domain ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyXMLEntityCache");
         currentResult.setValue("creator", "createXMLEntityCache");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XMLRegistries")) {
         getterName = "getXMLRegistries";
         setterName = null;
         currentResult = new PropertyDescriptor("XMLRegistries", DomainMBean.class, getterName, setterName);
         descriptors.put("XMLRegistries", currentResult);
         currentResult.setValue("description", "<p>Define xMLRegistries for this Domain</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyXMLRegistry");
         currentResult.setValue("creator", "createXMLRegistry");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdministrationMBeanAuditingEnabled")) {
         getterName = "isAdministrationMBeanAuditingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationMBeanAuditingEnabled";
         }

         currentResult = new PropertyDescriptor("AdministrationMBeanAuditingEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("AdministrationMBeanAuditingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server generates a log message when this WebLogic Server domain's configuration has been modified.</p>  <p>Any change to a server, module, or other item in the domain (either through the Administration Console, command-line utilities, or the APIs) will cause the Administration Server to generate this informational message.</p>  <p> This attribute has been deprecated in favor of ConfigurationAuditType. If values for both attributes are specified, the resultant behavior will be the logical OR condition of the two settings.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "Please use <code>DomainMBean.getConfigurationAuditType()</code> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdministrationPortEnabled")) {
         getterName = "isAdministrationPortEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdministrationPortEnabled";
         }

         currentResult = new PropertyDescriptor("AdministrationPortEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("AdministrationPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the domain-wide administration port should be enabled for this WebLogic Server domain. Because the administration port uses SSL, enabling the administration port requires that SSL must be configured for all servers in the domain.</p>  <p>The domain-wide administration port enables you to start a WebLogic Server instance in <code>STANDBY</code> state. It also allows you to separate administration traffic from application traffic in your domain. Because all servers in the domain must enable or disable the administration port at once, you configure the default administration port settings at the domain level.</p>  <p>If you enable the administration port:</p>  <ul> <li> <p>The administration port accepts only connections that specify administrator credentials.</p> </li>  <li> <p>Connections that specify administrator credentials can use only the administration port.</p> </li>  <li> <p>The command that starts managed servers must specify a secure protocol and the administration port: <code>-Dweblogic.management.server=https://<i>admin_server:administration_port</i></code></p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getAdministrationPort"), BeanInfoHelper.encodeEntities("#getAdministrationProtocol"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isAdministrationPortEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getAdministrationPort"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.KernelMBean#getSSL")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion) && !descriptors.containsKey("AutoConfigurationSaveEnabled")) {
         getterName = "isAutoConfigurationSaveEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoConfigurationSaveEnabled";
         }

         currentResult = new PropertyDescriptor("AutoConfigurationSaveEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("AutoConfigurationSaveEnabled", currentResult);
         currentResult.setValue("description", "<p>Causes the server to periodically persist changes to its configuration.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "9.0.0.0 The configuration is explicit written on a save call. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "9.0.0.0");
      }

      if (!descriptors.containsKey("AutoDeployForSubmodulesEnabled")) {
         getterName = "isAutoDeployForSubmodulesEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoDeployForSubmodulesEnabled";
         }

         currentResult = new PropertyDescriptor("AutoDeployForSubmodulesEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("AutoDeployForSubmodulesEnabled", currentResult);
         currentResult.setValue("description", "Indicates whether autodeployed applications could include JMS modules. If true then any submodules defined in the application's JMS modules will be deployed with default targets. The submodules define the different destinations in the JMS module, eg topics and queues, and if they aren't provided with explicit targets they may not be properly deployed. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterConstraintsEnabled")) {
         getterName = "isClusterConstraintsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterConstraintsEnabled";
         }

         currentResult = new PropertyDescriptor("ClusterConstraintsEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("ClusterConstraintsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies that deployments targeted to a cluster succeed only if all servers in the cluster are running.</p>  <p>By default, cluster constraints are disabled and deployment is attempted only on the servers that are reachable at the time of deployment from the Administration Server. Any servers that have been shut down or are temporarily partitioned from the Administration Server will retrieve the deployment during server startup or shortly after the network partition is resolved.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConfigBackupEnabled")) {
         getterName = "isConfigBackupEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConfigBackupEnabled";
         }

         currentResult = new PropertyDescriptor("ConfigBackupEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("ConfigBackupEnabled", currentResult);
         currentResult.setValue("description", "<p>If true, then backups of the configuration will be made during server boot.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsoleEnabled")) {
         getterName = "isConsoleEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsoleEnabled";
         }

         currentResult = new PropertyDescriptor("ConsoleEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("ConsoleEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server automatically deploys the Administration Console in the current domain.</p>  <p>If the Administration Console is not deployed, you can still use the WebLogic Scripting Tool or the management APIs to configure and monitor the domain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("secureValueDocOnly", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("DBPassiveMode")) {
         getterName = "isDBPassiveMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDBPassiveMode";
         }

         currentResult = new PropertyDescriptor("DBPassiveMode", DomainMBean.class, getterName, setterName);
         descriptors.put("DBPassiveMode", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the domain should enter database passive mode.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("DiagnosticContextCompatibilityModeEnabled")) {
         getterName = "isDiagnosticContextCompatibilityModeEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDiagnosticContextCompatibilityModeEnabled";
         }

         currentResult = new PropertyDescriptor("DiagnosticContextCompatibilityModeEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("DiagnosticContextCompatibilityModeEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether WLDF sends a pre-12.2.1-style diagnostic context along with the current-style diagnostic context.</p>  <p>If outbound communication to pre-12.2.1 servers is possible, this setting should always be enabled to ensure that those servers receive a diagnostic context that they can process. Disabling this setting in those scenarios can result in correlation information being lost across tiers, and in information reported by WLDF and DMS to become of sync in some situations.</p>  <p>If there is no outbound communication to pre-12.2.1 servers possible, it is suggested to disable this mode as only the current-style diagnostic context is needed to be propagated outbound (propagating the old style will not cause a failure, but is unnecessary overhead in that situation).</p>  <p>This setting affects outbound only, inbound pre-12.2.1 style diagnostic contexts are always understood and handled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", DomainMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("EnableEECompliantClassloadingForEmbeddedAdapters")) {
         getterName = "isEnableEECompliantClassloadingForEmbeddedAdapters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setEnableEECompliantClassloadingForEmbeddedAdapters";
         }

         currentResult = new PropertyDescriptor("EnableEECompliantClassloadingForEmbeddedAdapters", DomainMBean.class, getterName, setterName);
         descriptors.put("EnableEECompliantClassloadingForEmbeddedAdapters", currentResult);
         currentResult.setValue("description", "<p>Specifies the class loading behavior for embedded adapters. If you enable this option, embedded adapters in the domain will use Java EE compliant class loading. The embedded adapter's classes will be accessible from other modules in the same application.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("ExalogicOptimizationsEnabled")) {
         getterName = "isExalogicOptimizationsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setExalogicOptimizationsEnabled";
         }

         currentResult = new PropertyDescriptor("ExalogicOptimizationsEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("ExalogicOptimizationsEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether optimizations for Oracle Exalogic should be enabled. Optimizations include improved thread management and request processing, and reduced lock contention. This attribute should be enabled only when configuring a WebLogic domain for Oracle Exalogic.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("GuardianEnabled")) {
         getterName = "isGuardianEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGuardianEnabled";
         }

         currentResult = new PropertyDescriptor("GuardianEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("GuardianEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Guardian Agent is deployed when starting servers in the current domain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
         currentResult.setValue("obsolete", "true");
      }

      if (!descriptors.containsKey("InternalAppsDeployOnDemandEnabled")) {
         getterName = "isInternalAppsDeployOnDemandEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInternalAppsDeployOnDemandEnabled";
         }

         currentResult = new PropertyDescriptor("InternalAppsDeployOnDemandEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("InternalAppsDeployOnDemandEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether internal applications such as the console, uddi, wlstestclient, and uddiexplorer are deployed on demand (first access) instead of during server startup. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JavaServiceConsoleEnabled")) {
         getterName = "isJavaServiceConsoleEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaServiceConsoleEnabled";
         }

         currentResult = new PropertyDescriptor("JavaServiceConsoleEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("JavaServiceConsoleEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether the Administration Server automatically deploys the Java Service Administration Console in the current domain.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("JavaServiceEnabled")) {
         getterName = "isJavaServiceEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJavaServiceEnabled";
         }

         currentResult = new PropertyDescriptor("JavaServiceEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("JavaServiceEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether features to support the use of this WebLogic Server as a Java Service in a cloud environment should be enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFormatCompatibilityEnabled")) {
         getterName = "isLogFormatCompatibilityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogFormatCompatibilityEnabled";
         }

         currentResult = new PropertyDescriptor("LogFormatCompatibilityEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("LogFormatCompatibilityEnabled", currentResult);
         currentResult.setValue("description", "Configures whether log messages will be logged in legacy format without supplemental attributes. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("OCMEnabled")) {
         getterName = "isOCMEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOCMEnabled";
         }

         currentResult = new PropertyDescriptor("OCMEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("OCMEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether OCM functionality should be enabled for this domain. </p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ParallelDeployApplicationModules")) {
         getterName = "isParallelDeployApplicationModules";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelDeployApplicationModules";
         }

         currentResult = new PropertyDescriptor("ParallelDeployApplicationModules", DomainMBean.class, getterName, setterName);
         descriptors.put("ParallelDeployApplicationModules", currentResult);
         currentResult.setValue("description", "Determines if the modules of applications will be deployed in parallel. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ParallelDeployApplications")) {
         getterName = "isParallelDeployApplications";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setParallelDeployApplications";
         }

         currentResult = new PropertyDescriptor("ParallelDeployApplications", DomainMBean.class, getterName, setterName);
         descriptors.put("ParallelDeployApplications", currentResult);
         currentResult.setValue("description", "Determines if applications will be deployed in parallel. ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ProductionModeEnabled")) {
         getterName = "isProductionModeEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProductionModeEnabled";
         }

         currentResult = new PropertyDescriptor("ProductionModeEnabled", DomainMBean.class, getterName, setterName);
         descriptors.put("ProductionModeEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether all servers in this domain run in production mode.</p> <p>You can configure servers in your domain to start in one of two modes, development or production. You use development mode while you are developing your applications. Development mode uses a relaxed security configuration and enables you to auto-deploy applications. You use production mode when your application is running in its final form. A production domain uses full security and may use clusters or other advanced features.</p>  <p>The runtime mode is a domain-wide setting. As each Managed Server starts, it refers to the mode of the Administration Server to determine its runtime mode. If you configure the domain to run in production mode, the Administration Server saves this setting to the domain's configuration document.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DomainMBean.class.getMethod("createWTCServer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create WTCServer instance in the domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCServers");
      }

      mth = DomainMBean.class.getMethod("destroyWTCServer", WTCServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wtcServer", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes WTCServer object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WTCServers");
      }

      mth = DomainMBean.class.getMethod("createSNMPAgentDeployment", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a SNMPAgentDeploymentMBean with the specified name ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SNMPAgentDeployments");
      }

      mth = DomainMBean.class.getMethod("destroySNMPAgentDeployment", SNMPAgentDeploymentMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("mbean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Destroys the specified SNMPAgentDeploymentMBean ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SNMPAgentDeployments");
      }

      mth = DomainMBean.class.getMethod("createServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This is the factory method for Servers that are scoped at the domain level. The short name which is specified must be unique among all object instances of type ServerMBean. The new Server which is create will have this Domain as its parent and must be destroyed with the destroyServer method.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Servers");
      }

      mth = DomainMBean.class.getMethod("destroyServer", ServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("server", "to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys and removes a server which is a child of this Domain with the specified short name .</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Servers");
      }

      mth = DomainMBean.class.getMethod("createServerTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This is the factory method for server templates that are scoped at the domain level. The short name which is specified must be unique among all object instances of type ServerTemplateMBean. The new server template which is create will have this domain as its parent and must be destroyed with the destroyServerTemplate method.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServerTemplates");
      }

      mth = DomainMBean.class.getMethod("destroyServerTemplate", ServerTemplateMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("server", "to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys and removes a server template which is a child of this domain with the specified short name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ServerTemplates");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCoherenceServer", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.4.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>This is the factory method for Coherence servers that are scoped at the domain level. The short name which is specified must be unique among all object instances of type CoherenceServerMBean. The new Coherence server which is created will have this domain as its parent and must be destroyed with the destroyCoherenceServer method.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceServers");
            currentResult.setValue("since", "10.3.4.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyCoherenceServer", CoherenceServerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.4.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given Coherence server. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceServers");
            currentResult.setValue("since", "10.3.4.0");
         }
      }

      mth = DomainMBean.class.getMethod("createCluster", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory for creating Clusters.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Clusters");
      }

      mth = DomainMBean.class.getMethod("destroyCluster", ClusterMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cluster", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Deletes the cluster object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Clusters");
      }

      mth = DomainMBean.class.getMethod("createFileT3", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create FileT3 objects</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileT3s");
      }

      mth = DomainMBean.class.getMethod("destroyFileT3", FileT3MBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("fileT3", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes the FileT3 Object</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileT3s");
      }

      mth = DomainMBean.class.getMethod("createMessagingBridge", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory for creating MessagingBridges</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("destroyMessagingBridge", MessagingBridgeMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bridge", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes MessagingBridge object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("createApplication", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>factory to create Applications</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Applications");
      }

      mth = DomainMBean.class.getMethod("destroyApplication", ApplicationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("application", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>destroys Applications</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Applications");
      }

      mth = DomainMBean.class.getMethod("createWSReliableDeliveryPolicy", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create WSReliableDeliveryPolicy object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSReliableDeliveryPolicies");
      }

      mth = DomainMBean.class.getMethod("destroyWSReliableDeliveryPolicy", WSReliableDeliveryPolicyMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("policy", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a WSReliableDeliveryPolicy from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WSReliableDeliveryPolicies");
      }

      mth = DomainMBean.class.getMethod("createMachine", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create a Machine object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Machines");
      }

      mth = DomainMBean.class.getMethod("createUnixMachine", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Creates a UnixMachineMBean and adds it to the list returned by getMachines.  You may use destroyMachine to destroy beans of this type. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Machines");
      }

      mth = DomainMBean.class.getMethod("destroyMachine", MachineMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("machine", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a Machine from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Machines");
      }

      mth = DomainMBean.class.getMethod("createXMLEntityCache", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the XMLEntityCache to be created. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Factory method to create an XMLEntityCache Object ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLEntityCaches");
      }

      mth = DomainMBean.class.getMethod("destroyXMLEntityCache", XMLEntityCacheMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("cache", "object to be destroyed ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Factory method to destroys an XMLEntityCache Object ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLEntityCaches");
      }

      mth = DomainMBean.class.getMethod("createXMLRegistry", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create XMLRegistry object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLRegistries");
      }

      mth = DomainMBean.class.getMethod("destroyXMLRegistry", XMLRegistryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("registry", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a XMLRegistry from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "XMLRegistries");
      }

      mth = DomainMBean.class.getMethod("createJMSServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create JMSServer object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("destroyJMSServer", JMSServerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsServer", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a JMSServer from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSJDBCStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSJDBCStore object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSJDBCStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSJDBCStore", JMSJDBCStoreMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSJDBCStore from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSJDBCStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSFileStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSFileStore object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSFileStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSFileStore", JMSFileStoreMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSFileStore from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSFileStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSQueue", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSQueue object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSQueue", JMSQueueMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queue", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSQueue from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSTopic", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSTopic object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSTopic", JMSTopicMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("topic", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSTopic from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSDistributedQueue", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSDistributedQueue object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSDistributedQueue", JMSDistributedQueueMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("member", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSDistributedQueue from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSDistributedTopic", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSDistributedTopic object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSDistributedTopic", JMSDistributedTopicMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("member", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSDistributedTopic from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSTemplate object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTemplates");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSTemplate", JMSTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("template", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSTemplate from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSTemplates");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createNetworkChannel", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create NetworkChannel object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "NetworkChannels");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyNetworkChannel", NetworkChannelMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("channel", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a NetworkChannel from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "NetworkChannels");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createVirtualHost", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates virtual hosts.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VirtualHosts");
      }

      mth = DomainMBean.class.getMethod("destroyVirtualHost", VirtualHostMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("host", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes virtual hosts from this domain.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VirtualHosts");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createVirtualTarget", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates virtual targets.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "VirtualTargets");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyVirtualTarget", VirtualTargetMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes virtual targets from this domain.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "VirtualTargets");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("obsolete", "true");
         }
      }

      mth = DomainMBean.class.getMethod("createMigratableTarget", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates Migratable Targets.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MigratableTargets");
      }

      mth = DomainMBean.class.getMethod("destroyMigratableTarget", MigratableTargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys and removes a Migratable Target with the specified short name.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MigratableTargets");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createEJBContainer");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "creates EJBContainer object ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "EJBContainer");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyEJBContainer");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy EJBContainer object ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "EJBContainer");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createPathService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Factory method to create PathService object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PathServices");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("destroyPathService", PathServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("pathService", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a PathService from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PathServices");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSDestinationKey", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSDestination object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDestinationKeys");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSDestinationKey", JMSDestinationKeyMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("destination", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSDestinationKey from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDestinationKeys");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSConnectionFactory", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create JMSConnectionFactory instance in the domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSConnectionFactories");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSConnectionFactory", JMSConnectionFactoryMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsConnectionFactory", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>deletes JMSConnectionFactory object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSConnectionFactories");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSSessionPool", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create JMSSessionPool instance in the domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSessionPools");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSSessionPool", String.class, JMSSessionPoolMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create JMSSessionPool instance in the domain</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSessionPools");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSSessionPool", JMSSessionPoolMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsSessionPool", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>deletes JMSSessionPool object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSessionPools");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createJMSBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create JMSBridgeDestination instance in the domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("destroyJMSBridgeDestination", JMSBridgeDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsBridgeDestination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes JMSBridgeDestination object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("createBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create BridgeDestination instance in the domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("destroyBridgeDestination", BridgeDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bridgeDestination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes BridgeDestination object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createForeignJMSServer", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new diagnostic deployment that can be targeted to a server</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ForeignJMSServers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyForeignJMSServer", ForeignJMSServerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jmsServer", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Delete a diagnostic deployment configuration from the domain.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ForeignJMSServers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createShutdownClass", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create ShutdownClass object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ShutdownClasses");
      }

      mth = DomainMBean.class.getMethod("destroyShutdownClass", ShutdownClassMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sc", "The Shutdown class to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a ShutdownClass from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ShutdownClasses");
      }

      mth = DomainMBean.class.getMethod("createStartupClass", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create StartupClass object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StartupClasses");
      }

      mth = DomainMBean.class.getMethod("destroyStartupClass", StartupClassMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sc", "the Shutdown class to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a StartupClass from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StartupClasses");
      }

      mth = DomainMBean.class.getMethod("createSingletonService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create SingletonService object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingletonServices");
      }

      mth = DomainMBean.class.getMethod("destroySingletonService", SingletonServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sc", "the SingletonService class to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a SingletonService from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingletonServices");
      }

      mth = DomainMBean.class.getMethod("createMailSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create MailSession objects</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessions");
      }

      mth = DomainMBean.class.getMethod("destroyMailSession", MailSessionMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("ms", "the MailSession to destroy ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a MailSession from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MailSessions");
      }

      mth = DomainMBean.class.getMethod("createJoltConnectionPool", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory to create a JoltConnectionPool instance in the domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JoltConnectionPools");
      }

      mth = DomainMBean.class.getMethod("destroyJoltConnectionPool", JoltConnectionPoolMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("joltConnectionPool", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>deletes a JoltConnectionPool object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JoltConnectionPools");
      }

      mth = DomainMBean.class.getMethod("createLogFilter", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Creates a log filter MBean instance</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogFilters");
      }

      mth = DomainMBean.class.getMethod("destroyLogFilter", LogFilterMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("logFilter", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroy the given log filter MBean</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogFilters");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createFileStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new FileStore</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FileStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyFileStore", FileStoreMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroy a file store</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "FileStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createReplicatedStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            currentResult.setValue("deprecated", "12.2.1.3.0 Replace with custom file store or JDBC store. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new ReplicatedStore</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ReplicatedStores");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyReplicatedStore", ReplicatedStoreMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("replicatedMemoryStore", "to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            currentResult.setValue("deprecated", "12.2.1.3.0 Replace with custom file store or JDBC store. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroy a replicated memory store</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ReplicatedStores");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJDBCStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new JDBCStore</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJDBCStore", JDBCStoreMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("store", "to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroy a file store</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new JMS system resource.  The file for this resource will be DOMAIN_DIR/config/<name>.xml ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSSystemResource", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean "), createParameterDescriptor("descriptorFileName", "- name of descriptor file relative to DOMAIN_DIR/config/jms.. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new JMS system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid JMS descriptor, the new bean will link to that descriptor.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSSystemResource", JMSSystemResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCustomResource", String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. "), createParameterDescriptor("resourceClass", "- the name of the class that manages resource's lifecycle. "), createParameterDescriptor("descriptorBeanClass", "- the interface class name for this descriptor bean ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "9.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new Custom system resource.  The file for this resource will be DOMAIN_DIR/config/custom/<name>.xml ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CustomResources");
            currentResult.setValue("since", "9.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCustomResource", String.class, String.class, String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean "), createParameterDescriptor("resourceClass", "- the name of the class that manages resource's lifecycle. "), createParameterDescriptor("descriptorBeanClass", "- the interface class name for this descriptor bean "), createParameterDescriptor("descriptorFileName", "- name of descriptor file relative to DOMAIN_DIR/config/custom/.. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new Custom system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config/custom. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid descriptor, the new bean will link to that descriptor.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CustomResources");
            currentResult.setValue("since", "9.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyCustomResource", CustomResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CustomResources");
            currentResult.setValue("since", "9.1.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createForeignJNDIProvider", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Create a new diagnostic deployment that can be targeted to a server</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviders");
      }

      mth = DomainMBean.class.getMethod("destroyForeignJNDIProvider", ForeignJNDIProviderMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("provider", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Delete a diagnostic deployment configuration from the domain.</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJNDIProviders");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createWLDFSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new JMS system resource.  The file for this resource will be DOMAIN_DIR/config/<name>.xml ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WLDFSystemResources");
            currentResult.setValue("since", "9.0.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createWLDFSystemResource", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean "), createParameterDescriptor("descriptorFileName", "- name of descriptor file relative to DOMAIN_DIR/config/diagnostics. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new WLDF system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid WLDF descriptor, the new bean will link to that descriptor.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WLDFSystemResources");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createWLDFSystemResourceFromBuiltin", String.class, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean "), createParameterDescriptor("builtinSystemResource", "The type of builtin system resource to clone from i,e Low, Medium, High ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "This method creates a WLDF System Resource based upon builtin system resources. The builtin system resource name can be Low, Medium or High. ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SystemResources");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyWLDFSystemResource", WLDFSystemResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WLDFSystemResources");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJDBCSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean and base name for descriptor file. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new JDBC system resource.  The file for this resource will be DOMAIN_DIR/config/<name>.xml ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJDBCSystemResource", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean "), createParameterDescriptor("descriptorFileName", "- name of descriptor file relative to DOMAIN_DIR/config/jdbc. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Create a new JDBC system resource whose descriptor is stored in the given fileName relative to DOMAIN_DIR/config. If not file by this name is defined, it will be created. If a file by this name exists and contains a valid JDBC descriptor, the new bean will link to that descriptor.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJDBCSystemResource", JDBCSystemResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given system resource bean and delete the descriptor file that it refers to. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JDBCSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createSAFAgent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Factory method to create SAFAgent object</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("destroySAFAgent", SAFAgentMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("sAFAgent", "object ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Removes a SAFAgent from this domain</p> ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("createMigratableRMIService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>This is the factory method for MigratableRMIServices</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MigratableRMIServices");
      }

      mth = DomainMBean.class.getMethod("destroyMigratableRMIService", MigratableRMIServiceMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Destroys and removes a MigratableRMIService which with the specified short name .</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MigratableRMIServices");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createAdminServerMBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdminServerMBean");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyAdminServerMBean");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "AdminServerMBean");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSDistributedQueueMember", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSDistributedQueueMember object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedQueueMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSDistributedQueueMember", JMSDistributedQueueMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("queue", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSDistributedQueueMember from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedQueueMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createJMSDistributedTopicMember", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create JMSDistributedTopicMember object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyJMSDistributedTopicMember", JMSDistributedTopicMemberMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("topic", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a JMSDistributedTopicMember from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPTrapDestination", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPTrapDestination", SNMPTrapDestinationMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPTrapDestinations");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPProxy", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPProxy", SNMPProxyMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPProxies");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPGaugeMonitor", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPGaugeMonitor", SNMPGaugeMonitorMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPGaugeMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPStringMonitor", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPStringMonitor", SNMPStringMonitorMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPStringMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPCounterMonitor", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPCounterMonitor", SNMPCounterMonitorMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPCounterMonitors");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPLogFilter", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPLogFilter", SNMPLogFilterMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPLogFilters");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSNMPAttributeChange", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySNMPAttributeChange", SNMPAttributeChangeMBean.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SNMPAttributeChanges");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createWebserviceSecurity", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of WebserviceSecurity ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "create WebserviceSecurity object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceSecurities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyWebserviceSecurity", WebserviceSecurityMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "WebserviceSecurity ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "destroy WebserviceSecurity object ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "WebserviceSecurities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("createForeignJMSConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of ForeignJMSConnectionFactory ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "create ForeignJMSConnectionFactory object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = DomainMBean.class.getMethod("destroyForeignJMSConnectionFactory", ForeignJMSConnectionFactoryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "ForeignJMSConnectionFactory ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "destroy ForeignJMSConnectionFactory object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = DomainMBean.class.getMethod("createForeignJMSDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of ForeignJMSDestination ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "create ForeignJMSDestination object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = DomainMBean.class.getMethod("destroyForeignJMSDestination", ForeignJMSDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "ForeignJMSDestination ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "destroy ForeignJMSDestination object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = DomainMBean.class.getMethod("createJMSConnectionConsumer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of JMSConnectionConsumer ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "create JMSConnectionConsumer object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

      mth = DomainMBean.class.getMethod("destroyJMSConnectionConsumer", JMSConnectionConsumerMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("wsc", "JMSConnectionConsumer ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "destroy JMSConnectionConsumer object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

      mth = DomainMBean.class.getMethod("createForeignJMSDestination", String.class, ForeignJMSDestinationMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("destination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = DomainMBean.class.getMethod("createForeignJMSConnectionFactory", String.class, ForeignJMSConnectionFactoryMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("factory", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = DomainMBean.class.getMethod("createJMSDistributedQueueMember", String.class, JMSDistributedQueueMemberMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("member", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSDistributedQueueMembers");
      }

      mth = DomainMBean.class.getMethod("createJMSDistributedTopicMember", String.class, JMSDistributedTopicMemberMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("member", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSDistributedTopicMembers");
      }

      mth = DomainMBean.class.getMethod("createJMSTopic", String.class, JMSTopicMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("destination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSTopics");
      }

      mth = DomainMBean.class.getMethod("createJMSQueue", String.class, JMSQueueMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null), createParameterDescriptor("destination", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSQueues");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCoherenceClusterSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "- name of bean. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new CoherenceClusterSystemResource.  The file for this resource will be in DOMAIN_DIR/config/coherence/<name>/<name_xxx>.xml ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceClusterSystemResources");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyCoherenceClusterSystemResource", CoherenceClusterSystemResourceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "- bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroy the given CoherenceClusterSystemResource. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceClusterSystemResources");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSystemComponent", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the system component "), createParameterDescriptor("componentType", "the type of the system component ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for system components that are scoped at the domain level. The short name which is specified must be unique among all object instances of type SystemComponentMBean. The new system component which is created will have this Domain as its parent and must be destroyed with the {@link #destroySystemComponent(SystemComponentMBean)} method. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SystemComponents");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySystemComponent", SystemComponentMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "system component to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a system component which is a child of this Domain with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SystemComponents");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createSystemComponentConfiguration", String.class, String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the system component configuration "), createParameterDescriptor("componentType", "the type of the system component ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for system component configurations that are scoped at the domain level. The short name that is specified must be unique among all object instances of type SystemComponentConfigurationMBean. The new system component configuration which is created will have this Domain as its parent and must be destroyed with the {@link #destroySystemComponentConfiguration(SystemComponentConfigurationMBean)} method. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SystemComponentConfigurations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroySystemComponentConfiguration", SystemComponentConfigurationMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "system component configuration to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a system component configuration that is a child of this Domain with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "SystemComponentConfigurations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createOsgiFramework", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory to create a new OSGi framework instance for this Server.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "OsgiFrameworks");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyOsgiFramework", OsgiFrameworkMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("osgiFramework", "to be destroyed ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroys an OSGi framework object.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "OsgiFrameworks");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCoherenceManagementCluster", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of the Coherence cluster ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Create a new CoherenceManagementClusterMBean MBean representing the specified Coherence cluster. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceManagementClusters");
            currentResult.setValue("since", "12.1.3");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyCoherenceManagementCluster", CoherenceManagementClusterMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys the bean representing the specified cluster and removes it from the list of currently existing beans. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "CoherenceManagementClusters");
            currentResult.setValue("since", "12.1.3");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createPartition", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the partition to create ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates a partition with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Partitions");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyPartition", PartitionMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partition", "partition bean to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroys the specified partition.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Partitions");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of resource group to create ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates a resource group at the domain level with the specified name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResourceGroups");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyResourceGroup", ResourceGroupMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroup", "the resource group to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys the specified resource group. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResourceGroups");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createResourceGroupTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of resource group template to create ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Creates resource group templates with the specified name.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResourceGroupTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyResourceGroupTemplate", ResourceGroupTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("template", "the resource group template to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Destroys the specified resource group template.</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ResourceGroupTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedExecutorServiceTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedExecutorServiceTemplate ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedExecutorServiceTemplate", ManagedExecutorServiceTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedExecutorServiceTemplate which with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedScheduledExecutorServiceTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedScheduledExecutorServiceTemplate ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedScheduledExecutorServiceTemplate", ManagedScheduledExecutorServiceTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedScheduledExecutorServiceTemplate ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedThreadFactoryTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedThreadFactoryTemplate ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactoryTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedThreadFactoryTemplate", ManagedThreadFactoryTemplateMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedThreadFactory template which with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactoryTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedExecutorService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedExecutorService ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedExecutorService", ManagedExecutorServiceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedExecutorService ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedScheduledExecutorService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedScheduledExecutorService ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedScheduledExecutorService", ManagedScheduledExecutorServiceMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedScheduledExecutorService ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createManagedThreadFactory", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "This is the factory method for ManagedThreadFactory ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyManagedThreadFactory", ManagedThreadFactoryMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys and removes a ManagedThreadFactory ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createLifecycleManagerEndPoint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the LifecycleManager endpoint configuration ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("deprecated", "12.2.1.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory for creating a new LifecycleManager endpoint configuration.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "LifecycleManagerEndPoints");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyLifecycleManagerEndPoint", LifecycleManagerEndPointMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("endpoint", "to remove ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("deprecated", "12.2.1.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Deletes a LifecycleManager endpoint.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "LifecycleManagerEndPoints");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createPartitionWorkManager", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the PartitionWorkManager to create ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Creates a PartitionWorkManager with the specified name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PartitionWorkManagers");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyPartitionWorkManager", PartitionWorkManagerMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("bean", "the PartitionWorkManager to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Destroys a partition work manager policy configuration with the specified short name. ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "PartitionWorkManagers");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("createCallout", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "14.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Factory method to create Callout object</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Callouts");
            currentResult.setValue("since", "14.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("destroyCallout", CalloutMBean.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("callout", "the Callout  to destroy ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "14.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Removes a Callout from this domain</p> ");
            currentResult.setValue("role", "factory");
            currentResult.setValue("property", "Callouts");
            currentResult.setValue("since", "14.1.1.0");
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
         mth = DomainMBean.class.getMethod("addTag", String.class);
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
         mth = DomainMBean.class.getMethod("removeTag", String.class);
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
      Method mth = DomainMBean.class.getMethod("lookupWTCServer", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WTCServers");
      }

      mth = DomainMBean.class.getMethod("lookupSNMPAgentDeployment", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Finds a SNMPAgentDeploymentMBean with the specified name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SNMPAgentDeployments");
      }

      mth = DomainMBean.class.getMethod("lookupServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup a particular server from the list.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Servers");
      }

      mth = DomainMBean.class.getMethod("lookupServerTemplate", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup a particular server template from the list.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ServerTemplates");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupCoherenceServer", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the Coherence server ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.4.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Find a Coherence server with the given name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "CoherenceServers");
            currentResult.setValue("since", "10.3.4.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupCluster", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Clusters");
      }

      mth = DomainMBean.class.getMethod("lookupFileT3", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "8.1.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "FileT3s");
      }

      mth = DomainMBean.class.getMethod("lookupMessagingBridge", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MessagingBridges");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("lookupApplication", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Applications");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupAppDeployment", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "AppDeployments");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupInternalAppDeployment", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the internal application deployment ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "InternalAppDeployments");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupLibrary", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Look up the named module. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Libraries");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupDomainLibrary", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Look up the named module. ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "DomainLibraries");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupInternalLibrary", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the internal library ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "InternalLibraries");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupWSReliableDeliveryPolicy", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "WSReliableDeliveryPolicies");
      }

      mth = DomainMBean.class.getMethod("lookupMachine", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Machines");
      }

      mth = DomainMBean.class.getMethod("lookupXMLEntityCache", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "of the XMLEntityCache ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Returns an XMLEntityCache object if the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "XMLEntityCaches");
      }

      mth = DomainMBean.class.getMethod("lookupXMLRegistry", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "XMLRegistries");
      }

      mth = DomainMBean.class.getMethod("lookupJMSServer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSServers");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSJDBCStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSJDBCStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSFileStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSFileStores");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupJMSDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSDestinations");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSQueue", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSTopic", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSDistributedQueue", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSDistributedQueues");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSDistributedTopic", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSDistributedTopics");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSTemplates");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupNetworkChannel", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "NetworkChannels");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupVirtualHost", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "VirtualHosts");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupVirtualTarget", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the virtual target to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Lookup a particular virtual target by name. </p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "VirtualTargets");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      mth = DomainMBean.class.getMethod("lookupMigratableTarget", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Looks up a particular Migratable Target.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MigratableTargets");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupPathService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "PathServices");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSDestinationKey", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSDestinationKeys");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSConnectionFactory", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSConnectionFactories");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSSessionPool", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSSessionPools");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupJMSBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSBridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("lookupBridgeDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "BridgeDestinations");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupForeignJMSServer", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Locates a Foreign JMS Server ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ForeignJMSServers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupShutdownClass", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ShutdownClasses");
      }

      mth = DomainMBean.class.getMethod("lookupStartupClass", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "StartupClasses");
      }

      mth = DomainMBean.class.getMethod("lookupSingletonService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SingletonServices");
      }

      mth = DomainMBean.class.getMethod("lookupMailSession", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MailSessions");
      }

      mth = DomainMBean.class.getMethod("lookupJoltConnectionPool", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Find a JoltConnectionPool object with this name</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JoltConnectionPools");
      }

      mth = DomainMBean.class.getMethod("lookupLogFilter", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Looks up a log filter by name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "LogFilters");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupFileStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "FileStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupReplicatedStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            currentResult.setValue("deprecated", "12.2.1.3.0 Replace with custom file store or JDBC store. ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ReplicatedStores");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJDBCStore", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCStores");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupJMSSystemResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the JMS system resource ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a JMSSystem resource with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSSystemResources");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("lookupCustomResource", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the JMS system resource ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Find a JMSSystem resource with the given name ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "CustomResources");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupForeignJNDIProvider", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Find a ForeignJNDIProvider resource with the given name ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ForeignJNDIProviders");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupWLDFSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the WLDFSystemResource. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Looks up a WLDFSystemResourceMBean by name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WLDFSystemResources");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJDBCSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JDBCSystemResources");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupSAFAgent", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "SAFAgents");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = DomainMBean.class.getMethod("lookupMigratableRMIService", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Lookup a particular MigratableRMIService from the list.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MigratableRMIServices");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSDistributedQueueMember", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSDistributedQueueMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupJMSDistributedTopicMember", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "JMSDistributedTopicMembers");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupWebserviceSecurity", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of WebserviceSecurityConfiguration ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "look up WebserviceSecurityConfiguration object ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "WebserviceSecurities");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupForeignJMSConnectionFactory", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of ForeignJMSConnectionFactory ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "look up ForeignJMSConnectionFactory object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJMSConnectionFactories");
      }

      mth = DomainMBean.class.getMethod("lookupForeignJMSDestination", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of ForeignJMSDestination ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "look up ForeignJMSDestinationMBean object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ForeignJMSDestinations");
      }

      mth = DomainMBean.class.getMethod("lookupJMSConnectionConsumer", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of the JMSConnectionConsumer ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "look up JMSConnectionConsumer object ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "JMSConnectionConsumers");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupCoherenceClusterSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the CoherenceClusterSystemResource ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Find a CoherenceClusterSystemResource with the given name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "CoherenceClusterSystemResources");
            currentResult.setValue("since", "10.3.3.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupSystemComponent", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name to be looked up ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular system component defined in the Domain ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SystemComponents");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupSystemComponentConfiguration", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name to be looked up ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular system component configuration defined in the Domain ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "SystemComponentConfigurations");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupOsgiFramework", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the key of the osgi framework. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.2.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Look up an OSGi framework by name</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "OsgiFrameworks");
            currentResult.setValue("since", "12.1.2.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupCoherenceManagementCluster", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "name of the cluster ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Find the CoherenceManagementClusterMBean representing the specified cluster if it exists. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "CoherenceManagementClusters");
            currentResult.setValue("since", "12.1.3");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupPartition", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the partition to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up a partition by name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Partitions");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the resource group's name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a resource group by name. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ResourceGroups");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupResourceGroupTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the resource group template to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up a resource group template by name.</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ResourceGroupTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedExecutorServiceTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedExecutorServiceTemplate from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedScheduledExecutorServiceTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedScheduledExecutorServiceTemplate from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedScheduledExecutorServiceTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedThreadFactoryTemplate", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedThreadFactory template from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedThreadFactoryTemplates");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedExecutorService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedExecutorService from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedScheduledExecutorService", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedScheduledExecutorService from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedScheduledExecutorServices");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupManagedThreadFactory", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular ManagedThreadFactory from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "ManagedThreadFactories");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupLifecycleManagerEndPoint", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "The name of the LifecycleManager endpoint configuration ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("deprecated", "12.2.1.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Lookup a LifecycleManager EndPoint by name.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "LifecycleManagerEndPoints");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupPartitionWorkManager", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the PartitionWorkManager to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Lookup a particular PartitionWorkManager policy configuration from the list. ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "PartitionWorkManagers");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupCallout", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "14.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "Callouts");
            currentResult.setValue("since", "14.1.1.0");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DomainMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = DomainMBean.class.getMethod("restoreDefaultValue", String.class);
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

      String methodKey;
      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("discoverManagedServers");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Admin Server's knowledge of running Managed Servers is refreshed. Particularly useful when Admin Server is re-started.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("discoverManagedServer", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Admin Server's knowledge of running Managed Servers is refreshed. Particularly useful when Admin Server is re-started.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("getDisconnectedManagedServers");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "Use the ServerLifecycleRuntime ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> this method provides an array of strings that are the names of the managed servers that are not currently connected.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("start");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>These operations are used to start and forceShutdown all the servers belonging to the Domain. HashMap contains references to TaskRuntimeMBeans corresponding to each server in the Domain, keyed using the server name.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, "9.0.0.0", this.targetVersion)) {
         mth = DomainMBean.class.getMethod("kill");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("obsolete", "9.0.0.0");
            currentResult.setValue("deprecated", "9.0.0.0 ");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("obsolete", "9.0.0.0");
         }
      }

      mth = DomainMBean.class.getMethod("lookupTarget", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
         currentResult.setValue("owner", "Context");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupSystemResource", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Lookup a particular SystemResource in this domain.</p> ");
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("findPartitionByID", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("id", "the id of the partition to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Looks up a partition by the partition ID. The ID is a unique string that is NOT necessarily the same as the MBean name.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("arePartitionsPresent");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("obsolete", "true");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Specifies whether at least one partition has been configured. ");
            currentResult.setValue("role", "operation");
            String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "REST only supports one creator");
            currentResult.setValue("obsolete", "true");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("findConfigBeansWithTags", String.class, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "type of the MBean (shortened version of the class name less the MBean) "), createParameterDescriptor("tags", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Find all ConfigurationMBeans of the specified config bean type that have the specified tag(s). The tag param may be a single tag or multiple tags. The config bean must have a matching tag for every tag specified. The type may be an empty string to get all types of config beans with the specified tags. The tag may include white spaces.  White spaces will not be treated specially. If the user specifies the tags param as \"santa clara\", this is considered to be a single tag 'santa clara', not two.  Spaces are not delimiters.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("findConfigBeansWithTags", String.class, Boolean.TYPE, String[].class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "type of the MBean (shortened version of the class name less the MBean) "), createParameterDescriptor("matchAll", "false to match on any single tag, true to match on all tags "), createParameterDescriptor("tags", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Find all ConfigurationMBeans of the specified config bean type that have the specified tag(s). The tag param may be a single tag or multiple tags. If matchAll is true the config bean must have a matching tag for every tag specified. If matchAll is false the config bean may match on any single tag specified. The type may be an empty string to get all types of config beans with the specified tags. The tag may include white spaces.  White spaces will not be treated specially. If the user specifies the tags param as \"santa clara\", this is considered to be a single tag 'santa clara', not two.  Spaces are not delimiters.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("listTags", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "type of the MBean (shortened version of the class name less the MBean) ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>List all tags of the specified config bean type.  If the type param is empty string, then all tags used in the domain are returned.</p> ");
            currentResult.setValue("transient", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("findAllVirtualTargets");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Returns the list of virtual targets including all partitions' AdminVirtualTargets.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupInAllVirtualTargets", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the virtual target to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Looks up a particular virtual target by name from the list of all virtual targets of the domain including AdminVirtualTargets of all partitions.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("findAllTargets");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Returns the list of targets.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = DomainMBean.class.getMethod("lookupInAllTargets", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", "the name of the target to find ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Looks up a particular target by name from the list of all targets of the domain.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.1.0");
         }
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
