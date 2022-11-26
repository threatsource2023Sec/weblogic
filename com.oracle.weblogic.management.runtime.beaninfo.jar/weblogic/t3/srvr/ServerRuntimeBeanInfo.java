package weblogic.t3.srvr;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ServerRuntimeMBean;

public class ServerRuntimeBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ServerRuntimeMBean.class;

   public ServerRuntimeBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ServerRuntimeBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.t3.srvr.ServerRuntime");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.t3.srvr");
      String description = (new String("<p>Provides methods for retrieving runtime information about a server instance and for transitioning a server from one state to another.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ServerRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ActivationTime")) {
         getterName = "getActivationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ActivationTime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ActivationTime", currentResult);
         currentResult.setValue("description", "<p>The time when the server was started.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdminServerHost")) {
         getterName = "getAdminServerHost";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminServerHost", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdminServerHost", currentResult);
         currentResult.setValue("description", "<p>The address on which the Administration Server is listening for connections. For example, this might return the string: santiago</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AdminServerListenPort")) {
         getterName = "getAdminServerListenPort";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminServerListenPort", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdminServerListenPort", currentResult);
         currentResult.setValue("description", "<p>The port on which the Administration Server is listening for connections.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AdministrationPort")) {
         getterName = "getAdministrationPort";
         setterName = null;
         currentResult = new PropertyDescriptor("AdministrationPort", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdministrationPort", currentResult);
         currentResult.setValue("description", "<p>The port on which this server is listening for administrative requests.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link #getAdministrationURL} ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdministrationURL")) {
         getterName = "getAdministrationURL";
         setterName = null;
         currentResult = new PropertyDescriptor("AdministrationURL", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdministrationURL", currentResult);
         currentResult.setValue("description", "<p>The URL that the server and its clients use for administrative connections.</p>  <p>If no administration channel is enabled, then this method returns the URL for connections through the default channel. If the default channel is de-activated, this method returns the URL for a secure channel. If no secure channel is enabled, the method returns null.</p>  <p>The returned URL will be consistent with dynamic channel updates.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] roleObjectArrayGet;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("AggregateProgress")) {
         getterName = "getAggregateProgress";
         setterName = null;
         currentResult = new PropertyDescriptor("AggregateProgress", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AggregateProgress", currentResult);
         currentResult.setValue("description", "Gets the progress meter bean which can be used to view the progress of a booting server ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ApplicationRuntimes")) {
         getterName = "getApplicationRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ApplicationRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ApplicationRuntimes", currentResult);
         currentResult.setValue("description", "<p> Returns the list of currently running Applications </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("AsyncReplicationRuntime")) {
         getterName = "getAsyncReplicationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("AsyncReplicationRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AsyncReplicationRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents this server's view of its AsyncReplicationRuntime, if any.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("BatchJobRepositoryRuntime")) {
         getterName = "getBatchJobRepositoryRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("BatchJobRepositoryRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("BatchJobRepositoryRuntime", currentResult);
         currentResult.setValue("description", "<p>Gets the BatchJobRepositoryRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ClassLoaderRuntime")) {
         getterName = "getClassLoaderRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassLoaderRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ClassLoaderRuntime", currentResult);
         currentResult.setValue("description", "<p>Get statistics for system-level class loading.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ClusterRuntime")) {
         getterName = "getClusterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ClusterRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents this server's view of its cluster, if any.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConcurrentManagedObjectsRuntime")) {
         getterName = "getConcurrentManagedObjectsRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConcurrentManagedObjectsRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ConcurrentManagedObjectsRuntime", currentResult);
         currentResult.setValue("description", "<p>The ConcurrentManagedObjectsRuntimeMBean for this server.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("ConcurrentManagedObjectsRuntimeMBean")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ConnectorServiceRuntime")) {
         getterName = "getConnectorServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorServiceRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ConnectorServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>The access point for server wide control and monitoring of the Connector Container.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentDirectory")) {
         getterName = "getCurrentDirectory";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentDirectory", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("CurrentDirectory", currentResult);
         currentResult.setValue("description", "<p>The absolute path of the directory from which the server was started.</p>  <p>This may be used in conjunction with other relative paths in ServerMBean to compute full paths.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentMachine")) {
         getterName = "getCurrentMachine";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCurrentMachine";
         }

         currentResult = new PropertyDescriptor("CurrentMachine", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("CurrentMachine", currentResult);
         currentResult.setValue("description", "<p>Return the machine on which the server is running. This will be different from the configuration if the server gets migrated automatically.</p> ");
         currentResult.setValue("setterDeprecated", "12.2.1.3.0  @exclude in 2 releases.  See Bug 25183527 ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultExecuteQueueRuntime")) {
         getterName = "getDefaultExecuteQueueRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultExecuteQueueRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("DefaultExecuteQueueRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which exposes this server's default execute queue.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultURL")) {
         getterName = "getDefaultURL";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultURL", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("DefaultURL", currentResult);
         currentResult.setValue("description", "<p>The URL that clients use to connect to this server's default network channel.</p>  <p>The returned value indicates the default protocol, listen address and listen port:</p>  <p><i>protocol</i>://<i>listen-address</i>:<i>listen-port</i></p>  <p>Note: The default protocol, listen address and listen port are persisted in the domain's <code>config.xml</code> file, however when a server instance is started, command-line options can override these persisted values. This <code>getDefaultURL</code> method returns the URL values that are currently being used, not necessarily the values that are specified in <code>config.xml</code>.</p>  <p>The returned URL will be consistent with dynamic channel updates.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("EntityCacheCumulativeRuntime")) {
         getterName = "getEntityCacheCumulativeRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("EntityCacheCumulativeRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("EntityCacheCumulativeRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents Cumulative Status of the XML Cache.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityCacheCurrentStateRuntime")) {
         getterName = "getEntityCacheCurrentStateRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("EntityCacheCurrentStateRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("EntityCacheCurrentStateRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents Current Status of the XML Cache.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EntityCacheHistoricalRuntime")) {
         getterName = "getEntityCacheHistoricalRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("EntityCacheHistoricalRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("EntityCacheHistoricalRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents Historical Status of the XML Cache.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExecuteQueueRuntimes")) {
         getterName = "getExecuteQueueRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ExecuteQueueRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ExecuteQueueRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns an array of MBeans which exposes this server's active execute queues.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The health state of the server as reported by the server's self-health monitoring.</p>  <p>For example, the server can report if it is overloaded by too many requests, if it needs more memory resources, or if it will soon fail for other reasons.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("HealthStateJMX")) {
         getterName = "getHealthStateJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthStateJMX", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("HealthStateJMX", currentResult);
         currentResult.setValue("description", "<p>The HealthState object returned as a CompositeData type.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("ServerRuntimeMBean#getHealthState()"), BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("JDBCServiceRuntime")) {
         getterName = "getJDBCServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCServiceRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("JDBCServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>The JDBCServiceRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSRuntime")) {
         getterName = "getJMSRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("JMSRuntime", currentResult);
         currentResult.setValue("description", "<p>The JMSRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTARuntime")) {
         getterName = "getJTARuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JTARuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("JTARuntime", currentResult);
         currentResult.setValue("description", "<p>The transaction RuntimeMBean for this server.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("JTARuntimeMBean")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JVMRuntime")) {
         getterName = "getJVMRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JVMRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("JVMRuntime", currentResult);
         currentResult.setValue("description", "<p>The JVMRuntimeMBean for this server.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("JVMRuntimeMBean")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JoltRuntime")) {
         getterName = "getJoltRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JoltRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("JoltRuntime", currentResult);
         currentResult.setValue("description", "<p>The JoltConnectionServiceRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LibraryRuntimes")) {
         getterName = "getLibraryRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("LibraryRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("LibraryRuntimes", currentResult);
         currentResult.setValue("description", "<p> Returns all deployed Libraries </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ListenAddress")) {
         getterName = "getListenAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("ListenAddress", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ListenAddress", currentResult);
         currentResult.setValue("description", "<p>The address on which this server is listening for connections through the default network channel.</p>  <p>For example this might return the string: <code>santiago/172.17.9.220</code>.</p>  <p>You can configure other network channels for this server, and the other channels can use different listen addresses.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("#getServerChannel(String)")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link #getURL} ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restInternal", "needed by the WLS console");
      }

      if (!descriptors.containsKey("ListenPort")) {
         getterName = "getListenPort";
         setterName = null;
         currentResult = new PropertyDescriptor("ListenPort", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ListenPort", currentResult);
         currentResult.setValue("description", "<p>The port on which this server is listening for connections.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link #getURL} ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restInternal", "needed by the WLS console");
      }

      if (!descriptors.containsKey("LogBroadcasterRuntime")) {
         getterName = "getLogBroadcasterRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogBroadcasterRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("LogBroadcasterRuntime", currentResult);
         currentResult.setValue("description", "<p>The object which generates notifications on behalf of the logging subystem.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to the control interface for WLS server logging.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.1.3.0 ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MANAsyncReplicationRuntime")) {
         getterName = "getMANAsyncReplicationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MANAsyncReplicationRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MANAsyncReplicationRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents this server's view of its MANAsyncReplicationRuntime, if any.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (!descriptors.containsKey("MANReplicationRuntime")) {
         getterName = "getMANReplicationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MANReplicationRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MANReplicationRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents this server's view of its MANReplicationRuntime, if any.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MailSessionRuntimes")) {
         getterName = "getMailSessionRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MailSessionRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MailSessionRuntimes", currentResult);
         currentResult.setValue("description", "<p>Return the runtimeMBeans for JavaMail Mail Sessions</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MaxThreadsConstraintRuntimes")) {
         getterName = "getMaxThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxThreadsConstraintRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MaxThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns an array of RuntimeMBeans which exposes this server's globally defined MaxThreadsConstraints.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("MessagingBridgeRuntime")) {
         getterName = "getMessagingBridgeRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagingBridgeRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MessagingBridgeRuntime", currentResult);
         currentResult.setValue("description", "<p> The MessagingBridgeRuntimeMBean for this server </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.1.3.0 Replaced by {@link #getMessagingBridgeRuntimes} ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MessagingBridgeRuntimes")) {
         getterName = "getMessagingBridgeRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MessagingBridgeRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MessagingBridgeRuntimes", currentResult);
         currentResult.setValue("description", "<p> The MessagingBridgeRuntimeMBeans for this server </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MiddlewareHome")) {
         getterName = "getMiddlewareHome";
         setterName = null;
         currentResult = new PropertyDescriptor("MiddlewareHome", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MiddlewareHome", currentResult);
         currentResult.setValue("description", "<p>The Oracle Middleware installation directory. </p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MinThreadsConstraintRuntimes")) {
         getterName = "getMinThreadsConstraintRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("MinThreadsConstraintRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("MinThreadsConstraintRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns an array of RuntimeMBeans which exposes this server's globally defined MinThreadsConstraints.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         currentResult = new PropertyDescriptor("Name", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The name of this configuration. WebLogic Server uses an MBean to implement and persist the configuration.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("OpenSocketsCurrentCount")) {
         getterName = "getOpenSocketsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("OpenSocketsCurrentCount", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("OpenSocketsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current number of sockets registered for socket muxing on this server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("OracleHome")) {
         getterName = "getOracleHome";
         setterName = null;
         currentResult = new PropertyDescriptor("OracleHome", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("OracleHome", currentResult);
         currentResult.setValue("description", "<p>The directory where Oracle products are installed. Deprecated since 10.3.3</p> ");
         currentResult.setValue("deprecated", "WLS can no longer find out what ORACLE_HOME is ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("OverallHealthState")) {
         getterName = "getOverallHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthState", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("OverallHealthState", currentResult);
         currentResult.setValue("description", "<p> Determine the overall health state of this server, taking into account the health of each of its subsystems.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("OverallHealthStateJMX")) {
         getterName = "getOverallHealthStateJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("OverallHealthStateJMX", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("OverallHealthStateJMX", currentResult);
         currentResult.setValue("description", "<p>The overall health state of this server returned as a CompositeData type.</p> ");
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("ServerRuntimeMBean#getOverallHealthState()"), BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PartitionRuntimes")) {
         getterName = "getPartitionRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PartitionRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PartitionRuntimes", currentResult);
         currentResult.setValue("description", "<p> Returns the list of partition runtime beans</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
         currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("PatchList")) {
         getterName = "getPatchList";
         setterName = null;
         currentResult = new PropertyDescriptor("PatchList", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PatchList", currentResult);
         currentResult.setValue("description", "Returns information about installed patches on the server as an array of Strings. Each element of the array describes a patch, which is of the form: <p>BugNumber;PatchID;DateApplied;Description</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         roleObjectArrayGet = new String[]{BeanInfoHelper.encodeEntities("Monitor")};
         currentResult.setValue("rolesAllowedGet", roleObjectArrayGet);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("PathServiceRuntime")) {
         getterName = "getPathServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("PathServiceRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PathServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>Domain scoped PathServiceRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("deprecated", "12.2.1.0.0 replaced by {@link #getPathServiceRuntimes} ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PathServiceRuntimes")) {
         getterName = "getPathServiceRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PathServiceRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PathServiceRuntimes", currentResult);
         currentResult.setValue("description", "<p>Array of all PathServiceRuntimeMBean instances for Domain partition.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("setterDeprecated", "12.2.1.0.0 replaced by {@link #addPathServiceRuntime} ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PendingRestartPartitions")) {
         getterName = "getPendingRestartPartitions";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRestartPartitions", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PendingRestartPartitions", currentResult);
         currentResult.setValue("description", "<p> Returns all the partitions that need to be restarted in order to activate configuration changes</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("PendingRestartSystemResources")) {
         getterName = "getPendingRestartSystemResources";
         setterName = null;
         currentResult = new PropertyDescriptor("PendingRestartSystemResources", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PendingRestartSystemResources", currentResult);
         currentResult.setValue("description", "<p> Returns all the System Resources that need to be restarted </p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("PersistentStoreRuntimes")) {
         getterName = "getPersistentStoreRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistentStoreRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("PersistentStoreRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns the mbeans that provides runtime information for each PersistentStore.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RequestClassRuntimes")) {
         getterName = "getRequestClassRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("RequestClassRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("RequestClassRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns an array of RuntimeMBeans which exposes this server's globally defined Request Classes.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("RestartsTotalCount")) {
         getterName = "getRestartsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RestartsTotalCount", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("RestartsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of restarts for this server since the cluster was last started.</p> ");
         currentResult.setValue("deprecated", "This attribute always returns a value of 0. Please use {@link ServerLifeCycleRuntimeMBean#getNodeManagerRestartCount()} if the NodeManager is used to start servers ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SAFRuntime")) {
         getterName = "getSAFRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("SAFRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SAFRuntime", currentResult);
         currentResult.setValue("description", "<p>The SAFRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SNMPAgentRuntime")) {
         getterName = "getSNMPAgentRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("SNMPAgentRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SNMPAgentRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to the monitoring statistics for WLS SNMP Agent.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.0.0.0");
      }

      if (!descriptors.containsKey("SSLListenAddress")) {
         getterName = "getSSLListenAddress";
         setterName = null;
         currentResult = new PropertyDescriptor("SSLListenAddress", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SSLListenAddress", currentResult);
         currentResult.setValue("description", "<p>The address on which this server is listening for SSL connections. For example this might return the string: santiago/172.17.9.220</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link #getURL} ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SSLListenPort")) {
         getterName = "getSSLListenPort";
         setterName = null;
         currentResult = new PropertyDescriptor("SSLListenPort", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SSLListenPort", currentResult);
         currentResult.setValue("description", "<p>The port on which this server is listening for SSL connections.</p> ");
         currentResult.setValue("deprecated", "9.0.0.0 Replaced by {@link #getURL} ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("restInternal", "needed by the WLS console");
      }

      if (!descriptors.containsKey("ServerChannelRuntimes")) {
         getterName = "getServerChannelRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerChannelRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerChannelRuntimes", currentResult);
         currentResult.setValue("description", "<p>The network channels that are currently configured on the server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerClasspath")) {
         getterName = "getServerClasspath";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerClasspath", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerClasspath", currentResult);
         currentResult.setValue("description", "<p>Get the classpath for this server including domain/lib contents that are automatically picked up and appended to the classpath.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServerLogRuntime")) {
         getterName = "getServerLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerLogRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerLogRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to the control interface for WLS server logging.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("ServerSecurityRuntime")) {
         getterName = "getServerSecurityRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerSecurityRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerSecurityRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the ServerSecurityRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("ServerServiceVersions")) {
         getterName = "getServerServiceVersions";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerServiceVersions", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerServiceVersions", currentResult);
         currentResult.setValue("description", "<p>Returns a map of ServerService names and their versions. The key is the service name and the value is the version string. This method is provided primarily for console and is not intended for remote use.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerStartupTime")) {
         getterName = "getServerStartupTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ServerStartupTime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ServerStartupTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time taken for the server to transition from <code>STARTING</code> to <code>RUNNING</code> state.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleSignOnServicesRuntime")) {
         getterName = "getSingleSignOnServicesRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("SingleSignOnServicesRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SingleSignOnServicesRuntime", currentResult);
         currentResult.setValue("description", "<p>Get the runtime interface to publish single sign-on services information.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SocketsOpenedTotalCount")) {
         getterName = "getSocketsOpenedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SocketsOpenedTotalCount", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SocketsOpenedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of registrations for socket muxing on this sever.</p> ");
         currentResult.setValue("deprecated", "Use {@link #getOpenSocketsCurrentCount} instead. Both methods return the same value. This method is being deprecated in favor of the other method. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("StableState")) {
         getterName = "getStableState";
         setterName = null;
         currentResult = new PropertyDescriptor("StableState", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("StableState", currentResult);
         currentResult.setValue("description", "<p>It returns the end state for the server if it's transitioning or the current state if it is already in stable state</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.0.0");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current life cycle state of this server.</p>  <p>For example, a server can be in a RUNNING state in which it can receive and process requests or in an ADMIN state in which it can receive only administrative requests.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("StateVal")) {
         getterName = "getStateVal";
         setterName = null;
         currentResult = new PropertyDescriptor("StateVal", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("StateVal", currentResult);
         currentResult.setValue("description", "<p>Returns current state of the server as in integer. {@link weblogic.management.runtime.ServerStates} has more information about the available server states</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SubsystemHealthStates")) {
         getterName = "getSubsystemHealthStates";
         setterName = null;
         currentResult = new PropertyDescriptor("SubsystemHealthStates", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SubsystemHealthStates", currentResult);
         currentResult.setValue("description", "<p>Returns an array of health states for major subsystems in the server. Exposed only to console to display a table of health states</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ThreadPoolRuntime")) {
         getterName = "getThreadPoolRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ThreadPoolRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ThreadPoolRuntime", currentResult);
         currentResult.setValue("description", "<p>Get the self-tuning thread pool's runtime information. This call will return <code>null</code> if the self-tuning implementation is not enabled.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("TimeServiceRuntime")) {
         getterName = "getTimeServiceRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeServiceRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("TimeServiceRuntime", currentResult);
         currentResult.setValue("description", "<p>Get the runtime information about the WebLogic timer implementation. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimerRuntime")) {
         getterName = "getTimerRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("TimerRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("TimerRuntime", currentResult);
         currentResult.setValue("description", "<p>Get the runtime information about the WebLogic timer implementation. </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WANReplicationRuntime")) {
         getterName = "getWANReplicationRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WANReplicationRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WANReplicationRuntime", currentResult);
         currentResult.setValue("description", "<p>Return an MBean which represents this server's view of its WANReplicationRuntime, if any.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WLDFRuntime")) {
         getterName = "getWLDFRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WLDFRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WLDFRuntime", currentResult);
         currentResult.setValue("description", "<p>Return the MBean which provides access to all Diagnostic runtime MBeans.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "Context");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
         currentResult.setValue("owner", "Context");
      }

      if (!descriptors.containsKey("WTCRuntime")) {
         getterName = "getWTCRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WTCRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WTCRuntime", currentResult);
         currentResult.setValue("description", "<p>The WTCRuntimeMBean for this server.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WebServerRuntimes")) {
         getterName = "getWebServerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WebServerRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WebServerRuntimes", currentResult);
         currentResult.setValue("description", "<p> Returns all the initialized webservers </p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("WeblogicHome")) {
         getterName = "getWeblogicHome";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicHome", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WeblogicHome", currentResult);
         currentResult.setValue("description", "<p>The directory where the WebLogic Server instance (server) is installed, without the trailing \"/server\".</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.0");
      }

      if (!descriptors.containsKey("WeblogicVersion")) {
         getterName = "getWeblogicVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("WeblogicVersion", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WeblogicVersion", currentResult);
         currentResult.setValue("description", "<p>The version of this WebLogic Server instance (server).</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Returns an array of MBeans which exposes this server's active internal WorkManagers.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.2", (String)null, this.targetVersion) && !descriptors.containsKey("WseeClusterFrontEndRuntime")) {
         getterName = "getWseeClusterFrontEndRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeClusterFrontEndRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WseeClusterFrontEndRuntime", currentResult);
         currentResult.setValue("description", "<p>This is non-null only when this server is running as a host to a front-end proxy (HttpClusterServlet) instance.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.2");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.2", (String)null, this.targetVersion) && !descriptors.containsKey("WseeWsrmRuntime")) {
         getterName = "getWseeWsrmRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("WseeWsrmRuntime", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("WseeWsrmRuntime", currentResult);
         currentResult.setValue("description", "<p>Get statistics for web services reliable messaging across the entire server if any web service is deployed that employs reliable messaging. This MBean is null otherwise.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.1.2");
      }

      if (!descriptors.containsKey("AdminServer")) {
         getterName = "isAdminServer";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminServer", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdminServer", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the server is an Administration Server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AdminServerListenPortSecure")) {
         getterName = "isAdminServerListenPortSecure";
         setterName = null;
         currentResult = new PropertyDescriptor("AdminServerListenPortSecure", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdminServerListenPortSecure", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the port that the server uses for administrative traffic is configured to use a secure protocol.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("AdministrationPortEnabled")) {
         getterName = "isAdministrationPortEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("AdministrationPortEnabled", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("AdministrationPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the administration port is enabled on the server</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterMaster")) {
         getterName = "isClusterMaster";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterMaster", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ClusterMaster", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the server is the ClusterMaster of a cluster which is configured for server migration.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("InSitConfigState")) {
         getterName = "isInSitConfigState";
         setterName = null;
         currentResult = new PropertyDescriptor("InSitConfigState", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("InSitConfigState", currentResult);
         currentResult.setValue("description", "Returns true if sitconfig is applied. ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ListenPortEnabled")) {
         getterName = "isListenPortEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("ListenPortEnabled", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ListenPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the default listen port is enabled on the server.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("RestartRequired")) {
         getterName = "isRestartRequired";
         setterName = null;
         currentResult = new PropertyDescriptor("RestartRequired", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("RestartRequired", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the server must be restarted in order to activate configuration changes.</p> ");
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "9.0.0.0");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("SSLListenPortEnabled")) {
         getterName = "isSSLListenPortEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("SSLListenPortEnabled", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("SSLListenPortEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the default SSL listen port is enabled on the server</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShuttingDown")) {
         getterName = "isShuttingDown";
         setterName = null;
         currentResult = new PropertyDescriptor("ShuttingDown", ServerRuntimeMBean.class, getterName, setterName);
         descriptors.put("ShuttingDown", currentResult);
         currentResult.setValue("description", "<p>Check if the server is shutting down.</p> ");
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
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("lookupMessagingBridgeRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.1.3.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MessagingBridgeRuntimes");
            currentResult.setValue("since", "12.1.3.0");
         }
      }

      mth = ServerRuntimeMBean.class.getMethod("lookupMinThreadsConstraintRuntime", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "MinThreadsConstraintRuntimes");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("lookupRequestClassRuntime", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "RequestClassRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("lookupMaxThreadsConstraintRuntime", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "9.0.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "MaxThreadsConstraintRuntimes");
            currentResult.setValue("since", "9.0.0.0");
         }
      }

      mth = ServerRuntimeMBean.class.getMethod("lookupApplicationRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns the ApplicationRuntimeMBean asked for, by name. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "ApplicationRuntimes");
         String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      mth = ServerRuntimeMBean.class.getMethod("lookupLibraryRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Returns the LibraryRuntimeMBean asked for, by name. </p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "LibraryRuntimes");
      }

      mth = ServerRuntimeMBean.class.getMethod("lookupPersistentStoreRuntime", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Returns the Runtime mbean for the persistent store with the specified short name.</p> ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "PersistentStoreRuntimes");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("lookupPartitionRuntime", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("name", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p> Returns named partition runtime bean</p> ");
            currentResult.setValue("role", "finder");
            currentResult.setValue("property", "PartitionRuntimes");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "${excludeFromRest}");
         }
      }

   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ServerRuntimeMBean.class.getMethod("suspend");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] seeObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Suspend server. Deny new requests (except by privileged users). Allow pending requests to complete. This operation transitions the server into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspend(int, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
      }

      mth = ServerRuntimeMBean.class.getMethod("suspend", Integer.TYPE, Boolean.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Seconds to wait for server to transition gracefully. The server calls {@link #forceSuspend()} after timeout. "), createParameterDescriptor("ignoreSessions", "drop inflight HTTP sessions during graceful suspend ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerLifecycleException server failed to suspend gracefully.  A {@link #forceSuspend()} or a {@link #forceShutdown()} operation can be  invoked.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the server from <code>RUNNING</code> to <code>ADMIN</code> state gracefully.</p>  <p>Applications are in admin mode. Inflight work is completed. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerLifecycleException server failed to force suspend.  A {@link #forceShutdown()} operation can be invoked.")};
         currentResult.setValue("throws", seeObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Transitions the server from <code>RUNNING</code> to <code>ADMIN</code> state forcefully cancelling inflight work.</p>  <p>Work that cannot be cancelled is dropped. Applications are brought into the admin mode. This is the supported way of force suspending the server and getting it into <code>ADMIN</code> state.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ServerRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resume suspended server. Allow new requests. This operation transitions the server into <code>RUNNING</code> state.</p> ");
         currentResult.setValue("role", "operation");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", seeObjectArray);
      }

      mth = ServerRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the server after handling inflight work.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", throwsObjectArray);
      }

      mth = ServerRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      String[] roleObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully shuts down the server after handling inflight work.</p>  <p>This method is the same as calling: <code>shutdown(timeout, ignoreSessions, false);</code></p> ");
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdown(int, boolean, boolean)")};
         currentResult.setValue("see", throwsObjectArray);
         currentResult.setValue("role", "operation");
         roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", roleObjectArray);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("shutdown", Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the server. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "<code>true</code> indicates waiting for all HTTP sessions during inflight work handling; <code>false</code> indicates waiting for non-persisted HTTP sessions only. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully shuts down the server after handling inflight work; optionally ignores pending HTTP sessions while handling inflight work.</p>  <p>The following inflight work is allowed to complete before shutdown:</p>  <ul> <li>Pending transaction's and TLOG checkpoint</li> <li>Pending HTTP sessions</li> <li>Pending JMS work</li> <li>Pending work in the execute queues</li> <li>RMI requests with transaction context</li> </ul>  <p>Further administrative calls are accepted while the server is completing inflight work. For example a forceShutdown command can be issued to quickly shutdown the server if graceful shutdown takes a long time.</p> ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      mth = ServerRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Force shutdown the server. Causes the server to reject new requests and fail pending requests.</p> ");
         currentResult.setValue("role", "operation");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", seeObjectArray);
      }

      mth = ServerRuntimeMBean.class.getMethod("start");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "Use {@link #suspend} and {@link #resume} operations instead of lock/unlock. ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Unlocks a server and enables it to receive new requests.</p>  <p>Servers can be locked with the <code>java weblogic.Admin LOCK</code> command. In a locked state, a server instance accepts only administrative logins.</p> ");
         currentResult.setValue("role", "operation");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
         currentResult.setValue("rolesAllowed", seeObjectArray);
      }

      mth = ServerRuntimeMBean.class.getMethod("getServerChannel", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The address on which this server is listening for connections that use the specified protocol.</p>  <p>Note: The listen address is persisted in the domain's <code>config.xml</code> file, however when a server instance is started, a command-line option can override the persisted listen address. This <code>getServerChannel</code> method returns the listen address that is currently being used, not necessarily the address that is specified in <code>config.xml</code>.</p>  <p>The returned address will always be resolved.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
      }

      mth = ServerRuntimeMBean.class.getMethod("getURL", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>The URL that clients use when connecting to this server using the specified protocol.</p>  <p>Note: The listen address and listen port for a given protocol are persisted in the domain's <code>config.xml</code> file, however when a server instance is started, command-line options can override these persisted values. This <code>getURL</code> method returns the URL values that are currently being used, not necessarily the values that are specified in <code>config.xml</code>.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("getIPv4URL", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The URL that clients use when connecting to this server using the specified protocol.</p>  <p>Note: The listen address and listen port for a given protocol are persisted in the domain's <code>config.xml</code> file, however when a server instance is started, command-line options can override these persisted values. This <code>getURL</code> method returns the URL values that are currently being used, not necessarily the values that are specified in <code>config.xml</code>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.1.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("getIPv6URL", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("protocol", "the desired protocol ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "10.3.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>The URL that clients use when connecting to this server using the specified protocol.</p>  <p>Note: The listen address and listen port for a given protocol are persisted in the domain's <code>config.xml</code> file, however when a server instance is started, command-line options can override these persisted values. This <code>getURL</code> method returns the URL values that are currently being used, not necessarily the values that are specified in <code>config.xml</code>.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.1.0");
         }
      }

      mth = ServerRuntimeMBean.class.getMethod("setHealthState", Integer.TYPE, String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("state", "The new healthState value "), createParameterDescriptor("reason", "The new healthState value ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>For Server Health Monitoring.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ServerRuntimeMBean.class.getMethod("restartSSLChannels");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Restart all SSL channels on which the server is listening. This could be necessary because of some change that the server is not aware of, for instance updates to the keystore.</p> ");
         currentResult.setValue("role", "operation");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("isPartitionRestartRequired", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Indicates whether the partition must be restarted in order to activate configuration changes.</p> ");
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("isServiceAvailable", String.class);
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "10.3.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>returns true if the named service is available (configured, licensed & running)</p>  <p>The service String is either ServerService.EJB, ServerService.CONNECTOR, ServerService.JMS or the Bundle-SymbolicName of a service plugin</p> ");
            seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.server.ServerService")};
            currentResult.setValue("see", seeObjectArray);
            currentResult.setValue("exclude", Boolean.TRUE);
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "10.3.0.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("bootPartition", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "name of the partition to boot ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to boot.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Boots the partition </p> ");
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("startPartition", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "The resource group name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to start.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>starts the partition.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("startPartitionInAdmin", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException partition failed to start in admin mode.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>starts the Partition in ADMIN state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("startResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", "The resource group name ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException partition failed to start.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>starts the resource group.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("startResourceGroupInAdmin", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException resource group failed to start in admin mode.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>starts the resource group in ADMIN state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("suspendResourceGroup", String.class, Integer.TYPE, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Suspend resource group. Deny new requests (except by privileged users). Allow pending requests to complete. This operation transitions the partition into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("suspendResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Suspend resource group. Deny new requests (except by privileged users). Allow pending requests to complete. This operation transitions the partition into <code>ADMIN</code> state. Applications and resources are fully available to administrators in <code>ADMIN</code> state. But non-admin users are denied access to applications and resources</p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#suspendResourceGroup(String, int, boolean)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("forceSuspendResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.ResourceGroupLifecycleException partition failed to force suspend.  A {@link #forceShutdown()} operation can be invoked.")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Transitions the resource group from <code>RUNNING</code> to <code>ADMIN</code> state forcefully cancelling inflight work.</p>  <p>Work that cannot be cancelled is dropped. Applications are brought into the admin mode. This is the supported way of force suspending the resource group and getting it into <code>ADMIN</code> state.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("resumeResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Resume suspended resource group. Allow new requests. This operation transitions the resource group into <code>RUNNING</code> state.</p> ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("forceShutdownResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Force shutdown the resource group. Causes the resource group to reject new requests and fail pending requests.</p> ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", "Number of seconds to wait before aborting inflight work and shutting down the partition. "), createParameterDescriptor("ignoreSessions", "<code>true</code> indicates ignore pending HTTP sessions during inflight work handling. "), createParameterDescriptor("waitForAllSessions", "<code>true</code> indicates waiting for all HTTP sessions during inflight work handling; <code>false</code> indicates waiting for non-persisted HTTP sessions only. ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully shuts down the resource group after handling inflight work; optionally ignores pending HTTP sessions while handling inflight work.</p>  <p>The following inflight work is allowed to complete before shutdown:</p>  <ul> <li> <p>Pending transaction's and TLOG checkpoint</p> </li>  <li> <p>Pending HTTP sessions</p> </li>  <li> <p>Pending JMS work</p> </li>  <li> <p>Pending work in the execute queues</p> </li>  <li> <p>RMI requests with transaction context</p> </li> </ul>  <p>Further administrative calls are accepted while the server is completing inflight work. For example a forceShutdown command can be issued to quickly shutdown the partition if graceful shutdown takes a long time.</p> ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class, Integer.TYPE, Boolean.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null), createParameterDescriptor("timeout", (String)null), createParameterDescriptor("ignoreSessions", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully shuts down the resource group after handling inflight work.</p>  <p>This method is same to call: <code>shutdown(timeout, ignoreSessions, false);</code></p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdownResourceGroup(String, int, boolean, boolean)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("shutdownResourceGroup", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            currentResult.setValue("since", "12.2.1.0.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully shuts down the resource group after handling inflight work.</p> ");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("#shutdownResourceGroup(String, int, boolean, boolean)")};
            currentResult.setValue("see", throwsObjectArray);
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("getRgState", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("resourceGroupName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ResourceGroupLifecycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", " ");
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer"), BeanInfoHelper.encodeEntities("Operator"), BeanInfoHelper.encodeEntities("Monitor")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.0.0");
            currentResult.setValue("excludeFromRest", "No default REST mapping for InetSocketAddress");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("forceRestartPartition", String.class);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", (String)null)};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Performs a forced restart of the specified partition. ");
            currentResult.setValue("role", "operation");
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", throwsObjectArray);
            currentResult.setValue("owner", "Context");
            currentResult.setValue("since", "12.2.1.1.0");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = ServerRuntimeMBean.class.getMethod("forceRestartPartition", String.class, Long.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("partitionName", "the partition to be restarted "), createParameterDescriptor("restartDelayMillis", "time in milliseconds to wait after halt before restarting ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("PartitionLifeCycleException")};
            currentResult.setValue("throws", throwsObjectArray);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("VisibleToPartitions", "ALWAYS");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Performs a forced restart of the specified partition, waiting the specified time after the halt completes before executing the operation to return the partition to its state in this server at the time the <code>forceRestartPartition</code> method was invoked. ");
            currentResult.setValue("role", "operation");
            roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Operator")};
            currentResult.setValue("rolesAllowed", roleObjectArray);
            currentResult.setValue("owner", "Context");
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
