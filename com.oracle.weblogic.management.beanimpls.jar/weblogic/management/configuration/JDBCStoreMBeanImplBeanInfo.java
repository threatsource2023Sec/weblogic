package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCStoreMBeanImplBeanInfo extends GenericJDBCStoreMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCStoreMBean.class;

   public JDBCStoreMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCStoreMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.JDBCStoreMBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "9.0.0.0");
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("Defines an instance of the persistent store that stores its persistent records in a JDBC-accessible database. It may be used by JMS and by other subsystems. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.JDBCStoreMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConnectionCachingPolicy")) {
         getterName = "getConnectionCachingPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionCachingPolicy";
         }

         currentResult = new PropertyDescriptor("ConnectionCachingPolicy", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("ConnectionCachingPolicy", currentResult);
         currentResult.setValue("description", "<p>Advanced use only: gets the connection caching policy for the JDBC store.</p>  <p>The return values will be one of:</p> <ul> <li>JMSConstants.JDBCSTORE_CONNECTION_CACHING_POLICY_DEFAULT (&quot;DEFAULT&quot;)</li> <li>JMSConstants.JDBCSTORE_CONNECTION_CACHING_POLICY_MINIMAL (&quot;MINIMAL&quot;)</li> <li>JMSConstants.JDBCSTORE_CONNECTION_CACHING_POLICY_NONE (&quot;NONE&quot;)</li> </ul>  <p>IMPORTANT: See the below for additional usage information, particularly regarding NONE</p>  <p>DEFAULT</p> <p>The default mode of operation for the JDBC store.  The JDBC store will open two connections to the database and it will keep these connections open for the life of the store. In addition, if the worker count for the store is two or more, then it will open one connection for each of the workers and those connections will also stay open for the life of the JDBC store.  If the worker count is 3 then the JDBC store will use 5 database connections.  If the worker count is the default of 1 then the JDBC store will use just 2 connections.The DEFAULT setting is the recommended setting to be used when the database backing the JDBC store is not constrained with respect to the number of open connections.</p>  <p>MINIMAL</p> <p>The JDBC store will open one connection to the database and it will keep that connection open for the life of the store. In addition, if the worker count for the store is two or more, then it will open one connection for each of the workers and those connections will also stay open for the life of the JDBC store. If the worker count is 3 then the JDBC store will use 4 database connections. If the worker count is the default of 1 then the JDBC store will use just 1 connection. The MINIMAL setting may result in a slight reduction of through-put for low concurrency messaging scenarios in comparison to DEFAULT.</p>  <p>NONE</p> <p>The NONE connection caching policy is for 'advanced usage only'. The JDBC store will open one connection to the database on an as-needed basis to perform I/O and it will release that connection when the operation is complete. The NONE value is not compatible with a configured worker count of 2 or more and will result in a configuration validation exception. The NONE setting may result in a slight reduction of through-put for low concurrency messaging scenarios in comparison to DEFAULT or MINIMAL.</p>  <p>NOTE: It is strongly recommended that a JDBC store be configured with a dedicated data source when the store is configured with the NONE connection caching policy.  A store that shares a data source with other non-store components or applications runs the risk of failing due to dead-locks.  Please note that a JDBC store will require more than one connection when first started. As such, a data source should be configured to grow and shrink so that the JDBC store can initialize.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setConnectionCachingPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "DEFAULT");
         currentResult.setValue("legalValues", new Object[]{"DEFAULT", "MINIMAL", "NONE"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (!descriptors.containsKey("DataSource")) {
         getterName = "getDataSource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSource";
         }

         currentResult = new PropertyDescriptor("DataSource", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DataSource", currentResult);
         currentResult.setValue("description", "<p>The JDBC data source used by this JDBC store to access its backing table.</p>  <p>The specified data source must use a non-XA JDBC driver since connection pools for XA JDBC drivers are not supported.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeletesPerBatchMaximum")) {
         getterName = "getDeletesPerBatchMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeletesPerBatchMaximum";
         }

         currentResult = new PropertyDescriptor("DeletesPerBatchMaximum", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DeletesPerBatchMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of table rows that are deleted per database call.</p>  <ul> <li> When possible, a JDBC store uses JDBC 3.0 batching to batch concurrent client requests. </li>  <li> Both the maximum batch size for concurrent inserts and for concurrent writes are configurable. </li>  <li> To disable JDBC 3.0 batching, set the maximum batch size to 1. </li>  <li> The maximum batch size has no effect on the maximum number of concurrent client requests. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getInsertsPerBatchMaximum"), BeanInfoHelper.encodeEntities("#getDeletesPerStatementMaximum")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeletesPerStatementMaximum")) {
         getterName = "getDeletesPerStatementMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeletesPerStatementMaximum";
         }

         currentResult = new PropertyDescriptor("DeletesPerStatementMaximum", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DeletesPerStatementMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of table rows that are deleted per database call.</p>  <ul> <li> Applies only when a JDBC store does not use JDBC 3.0 batching to batch concurrent client requests. </li>  <li> The maximum deletes per statement has no effect on the maximum number of concurrent client requests. </li>  <li> For some databases, the JDBC store may choose a lower value than the one configured. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDeletesPerBatchMaximum"), BeanInfoHelper.encodeEntities("#getInsertsPerBatchMaximum")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeploymentOrder")) {
         getterName = "getDeploymentOrder";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeploymentOrder";
         }

         currentResult = new PropertyDescriptor("DeploymentOrder", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DeploymentOrder", currentResult);
         currentResult.setValue("description", "<p>A priority that the server uses to determine when it deploys an item. The priority is relative to other deployable items of the same type.</p>  <p>For example, the server prioritizes and deploys all EJBs before it prioritizes and deploys startup classes.</p>  <p>Items with the lowest Deployment Order value are deployed first. There is no guarantee on the order of deployments with equal Deployment Order values. There is no guarantee of ordering across clusters.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("DistributionPolicy")) {
         getterName = "getDistributionPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDistributionPolicy";
         }

         currentResult = new PropertyDescriptor("DistributionPolicy", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("DistributionPolicy", currentResult);
         currentResult.setValue("description", "<p>Specifies how the instances of a configured JMS artifact are named and distributed when cluster-targeted.  A JMS artifact is cluster-targeted when its target is directly set to a cluster, or when it is scoped to a resource group and the resource group is in turn targeted to a cluster.  When this setting is configured on a store, it applies to all JMS artifacts that reference the store.  Valid options:</p> <ul> <li> <code>Distributed</code> Creates an instance on each server JVM in a cluster. Required for all SAF agents and for cluster-targeted or resource-group-scoped JMS servers that host distributed destinations. </li>  <li> <code>Singleton</code> Creates a single instance on a single server JVM within a cluster. Required for cluster-targeted or resource-group-scoped JMS servers that host standalone (non-distributed) destinations and for cluster-targeted or resource-group-scoped path services.  The <code>Migration Policy</code> must be <code>On-Failure</code> or <code>Always</code> when using this option with a JMS server, <code>On-Failure</code> when using this option with a messaging bridge, and <code>Always</code> when using this option with a path service. </li> </ul>  <p><b>Instance Naming Note:</b></p> <ul> <li> The <code>DistributionPolicy</code> determines the instance name suffix for cluster-targeted JMS artifacts.  The suffix for a cluster-targeted <code>Singleton</code> is <code>-01</code> and for a cluster-targeted <code>Distributed</code> is <code>@ClusterMemberName</code>. </li> </ul>  <p><b>Messaging Bridge Notes:</b></p> <ul> <li> When an instance per server is desired for a cluster-targeted messaging bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Distributed/Off</code>, respectively; these are the defaults. </li> <li> When a single instance per cluster is desired for a cluster-targeted bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Singleton/On-Failure</code>, respectively. </li> <li> If you cannot cluster-target a bridge and still need singleton behavior in a configured cluster, you can target the bridge to a migratable target and configure the <code>Migration Policy</code> on the migratable target to <code>Exactly-Once</code>. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationPolicy"), BeanInfoHelper.encodeEntities("#setDistributionPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Distributed");
         currentResult.setValue("legalValues", new Object[]{"Distributed", "Singleton"});
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("FailOverLimit")) {
         getterName = "getFailOverLimit";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailOverLimit";
         }

         currentResult = new PropertyDescriptor("FailOverLimit", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("FailOverLimit", currentResult);
         currentResult.setValue("description", "<p>Specify a limit for the number of cluster-targeted JMS artifact instances that can fail over to a particular JVM.</p>  <p>This can be used to prevent too many instances from starting on a server, avoiding a system failure when starting too few servers of a formerly large cluster.</p>  <p>A typical limit value should allow all instances to run in the smallest desired cluster size, which means (smallest-cluster-size * (limit + 1)) should equal or exceed the total number of instances. </p>  <ul> <li>A value of <code>-1</code> means there is no fail over limit (unlimited).</li> <li>A value of <code>0</code> prevents any fail overs of cluster-targeted JMS artifact instances, so no more than 1 instance will run per server (this is an instance that has not failed over).</li> <li>A value of <code>1</code> allows one fail-over instance on each server, so no more than two instances will run per server (one failed over instance plus an instance that has not failed over).</li> </ul>  <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
      }

      if (!descriptors.containsKey("FailbackDelaySeconds")) {
         getterName = "getFailbackDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailbackDelaySeconds";
         }

         currentResult = new PropertyDescriptor("FailbackDelaySeconds", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("FailbackDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before failing a cluster-targeted JMS artifact instance back to its preferred server after the preferred server failed and was restarted.</p>  <p>This delay allows time for the system to stabilize and dependent services to be restarted, preventing a system failure during a reboot.</p>  <ul> <li>A value > <code>0</code> specifies the time, in seconds, to delay before failing a JMS artifact back to its user preferred server.</li> <li>A value of <code>0</code> indicates that the instance would never failback.</li> <li>A value of <code>-1</code> indicates that there is no delay and the instance would failback immediately.</li> </ul>  <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         setPropertyDescriptorDefault(currentResult, new Long(-1L));
      }

      if (!descriptors.containsKey("InitialBootDelaySeconds")) {
         getterName = "getInitialBootDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialBootDelaySeconds";
         }

         currentResult = new PropertyDescriptor("InitialBootDelaySeconds", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("InitialBootDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before starting a cluster-targeted JMS instance on a newly booted WebLogic Server instance. When this setting is configured on a store, it applies to all JMS artifacts that reference the store. </p> <p>This allows time for the system to stabilize and dependent services to be restarted, preventing a system failure during a reboot.</p>  <ul> <li>A value > <code>0</code> is the time, in seconds, to delay before before loading resources after a failure and restart.</li> <li>A value of <code>0</code> specifies no delay.</li> </ul> <b>Note:</b> This setting only applies when the JMS artifact is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#PartialClusterStabilityDelaySeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(60L));
      }

      if (!descriptors.containsKey("InsertsPerBatchMaximum")) {
         getterName = "getInsertsPerBatchMaximum";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInsertsPerBatchMaximum";
         }

         currentResult = new PropertyDescriptor("InsertsPerBatchMaximum", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("InsertsPerBatchMaximum", currentResult);
         currentResult.setValue("description", "<p>The maximum number of table rows that are inserted per database call.</p>  <ul> <li> When possible, a JDBC store uses JDBC 3.0 batching to batch concurrent client requests. </li>  <li> Both the maximum batch size for concurrent inserts and for concurrent writes are configurable. </li>  <li> To disable JDBC 3.0 batching, set the maximum batch size to 1. </li>  <li> The maximum batch size has no effect on the maximum number of concurrent client requests. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getDeletesPerBatchMaximum"), BeanInfoHelper.encodeEntities("#getDeletesPerStatementMaximum")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(20));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogicalName")) {
         getterName = "getLogicalName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLogicalName";
         }

         currentResult = new PropertyDescriptor("LogicalName", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("LogicalName", currentResult);
         currentResult.setValue("description", "<p>The name used by subsystems to refer to different stores on different servers using the same name.</p>  <p>For example, an EJB that uses the timer service may refer to its store using the logical name, and this name may be valid on multiple servers in the same cluster, even if each server has a store with a different physical name.</p>  <p>Multiple stores in the same domain or the same cluster may share the same logical name. However, a given logical name may not be assigned to more than one store on the same server.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("MigrationPolicy")) {
         getterName = "getMigrationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMigrationPolicy";
         }

         currentResult = new PropertyDescriptor("MigrationPolicy", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("MigrationPolicy", currentResult);
         currentResult.setValue("description", "<p>Controls migration and restart behavior of cluster-targeted JMS service artifact instances. When this setting is configured on a cluster-targeted store, it applies to all JMS artifacts that reference the store. See the migratable target settings for enabling migration and restart on migratable-targeted JMS artifacts.</p> <ul> <li><code>Off</code> Disables migration support for cluster-targeted JMS service objects, and changes the default for Restart In Place to false. If you want a restart to be enabled when the Migration Policy is Off, then Restart In Place must be explicitly configured to true. This policy cannot be combined with the <code>Singleton</code> Migration Policy. </li> <li><code>On-Failure</code> Enables automatic migration and restart of instances on the failure of a subsystem Service or WebLogic Server instance, including automatic fail-back and load balancing of instances. </li>  <li><code>Always</code> Provides the same behavior as <code>On-Failure</code> and automatically migrates instances even in the event of a graceful shutdown or a partial cluster start. </li> </ul> <b>Note:</b> Cluster leasing must be configured for <code>On-Failure</code> and <code>Always</code>.  <p><b>Messaging Bridge Notes:</b></p> <ul> <li> When an instance per server is desired for a cluster-targeted messaging bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Distributed/Off</code>, respectively; these are the defaults. </li> <li> When a single instance per cluster is desired for a cluster-targeted bridge, Oracle recommends setting the bridge <code>Distributed Policy</code> and <code>Migration Policy</code> to <code>Singleton/On-Failure</code>, respectively. </li> <li> A <code>Migration Policy</code> of <code>Always</code> is not recommended for bridges. </li> <li> If you cannot cluster-target a bridge and still need singleton behavior in a configured cluster, you can target the bridge to a migratable target and configure the <code>Migration Policy</code> on the migratable target to <code>Exactly-Once</code>. </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMigrationPolicy")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Off");
         currentResult.setValue("legalValues", new Object[]{"Off", "On-Failure", "Always"});
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("NumberOfRestartAttempts")) {
         getterName = "getNumberOfRestartAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfRestartAttempts";
         }

         currentResult = new PropertyDescriptor("NumberOfRestartAttempts", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("NumberOfRestartAttempts", currentResult);
         currentResult.setValue("description", "<p>Specifies the maximum number of restart attempts.</p>  <ul> <li>A value > <code>0</code> specifies the maximum number of restart attempts.</li> <li>A value of <code>0</code> specifies the same behavior as setting {@link #getRestartInPlace} to <code>false</code>.</li> <li>A value of <code>-1</code> means infinite retry restart until it either starts or the server instance shuts down.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("legalMin", new Integer(-1));
      }

      if (!descriptors.containsKey("PartialClusterStabilityDelaySeconds")) {
         getterName = "getPartialClusterStabilityDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPartialClusterStabilityDelaySeconds";
         }

         currentResult = new PropertyDescriptor("PartialClusterStabilityDelaySeconds", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("PartialClusterStabilityDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to delay before a partially started cluster starts all cluster-targeted JMS artifact instances that are configured with a Migration Policy of <code>Always</code> or <code>On-Failure</code>. </p>  <p>Before this timeout expires or all servers are running, a cluster starts a subset of such instances based on the total number of servers running and the configured cluster size. Once the timeout expires or all servers have started, the system considers the cluster stable and starts any remaining services.</p>  <p>This delay ensures that services are balanced across a cluster even if the servers are started sequentially. It is ignored after a cluster is fully started (stable) or when individual servers are started.</p>  <ul> <li>A value > <code>0</code> specifies the time, in seconds, to delay before a partially started cluster starts dynamically configured services.</li> <li>A value of <code>0</code> specifies no delay.</li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getInitialBootDelaySeconds")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(240L));
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ReconnectRetryIntervalMillis")) {
         getterName = "getReconnectRetryIntervalMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectRetryIntervalMillis";
         }

         currentResult = new PropertyDescriptor("ReconnectRetryIntervalMillis", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("ReconnectRetryIntervalMillis", currentResult);
         currentResult.setValue("description", "<p>The length of time in milliseconds between reconnection attempts during the reconnection retry period.</p>  <p>The reconnection interval applies to JDBC connections regardless of the database that is used for the JDBC store.</p>  <p>The default value is 200 milliseconds</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getReconnectRetryPeriodMillis()"), BeanInfoHelper.encodeEntities("#setReconnectRetryPeriodMillis(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(200));
         currentResult.setValue("legalMax", new Integer(10000));
         currentResult.setValue("legalMin", new Integer(100));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ReconnectRetryPeriodMillis")) {
         getterName = "getReconnectRetryPeriodMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReconnectRetryPeriodMillis";
         }

         currentResult = new PropertyDescriptor("ReconnectRetryPeriodMillis", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("ReconnectRetryPeriodMillis", currentResult);
         currentResult.setValue("description", "<p>Returns the length of time in milliseconds during which the persistent store will attempt to re-establish a connection to the database.  Successive reconnection attempts will be attempted after a fixed delay that is specified by the reconnection retry interval.</p>  <p>The reconnection period applies to JDBC connections regardless of the database that is used for the JDBC store.</p>  <p>The default value is 1000</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setReconnectRetryPeriodMillis(int)"), BeanInfoHelper.encodeEntities("#getReconnectRetryIntervalMillis()"), BeanInfoHelper.encodeEntities("#setReconnectRetryIntervalMillis(int)")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(1000));
         currentResult.setValue("legalMax", new Integer(300000));
         currentResult.setValue("legalMin", new Integer(200));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RestartInPlace")) {
         getterName = "getRestartInPlace";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRestartInPlace";
         }

         currentResult = new PropertyDescriptor("RestartInPlace", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("RestartInPlace", currentResult);
         currentResult.setValue("description", "<p> Enables a periodic automatic in-place restart of failed cluster-targeted or standalone-server-targeted JMS artifact instance(s) running on healthy WebLogic Server instances. See the migratable target settings for in-place restarts of migratable-targeted JMS artifacts. When the Restart In Place setting is configured on a store, it applies to all JMS artifacts that reference the store.</p>  <ul>  <li>If the Migration Policy of the JMS artifact is set to <code>Off</code>, Restart In Place is disabled by default.</li>  <li>If the Migration Policy of the JMS artifact is set to <code>On-Failure</code> or <code>Always</code>, Restart In Place is enabled by default.</li>  <li>This attribute is not used by WebLogic messaging bridges which automatically restart internal connections as needed.</li>  <li>For a JMS artifact that is cluster-targeted and the Migration Policy is set to <code>On-Failure</code> or <code>Always</code>, if restart fails after the configured maximum retry attempts, it will migrate to a different server within the cluster. </li>  </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setRestartInPlace")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
      }

      if (!descriptors.containsKey("SecondsBetweenRestarts")) {
         getterName = "getSecondsBetweenRestarts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecondsBetweenRestarts";
         }

         currentResult = new PropertyDescriptor("SecondsBetweenRestarts", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("SecondsBetweenRestarts", currentResult);
         currentResult.setValue("description", "<p>Specifies the amount of time, in seconds, to wait in between attempts to restart a failed service instance.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(1));
      }

      if (!descriptors.containsKey("Targets")) {
         getterName = "getTargets";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTargets";
         }

         currentResult = new PropertyDescriptor("Targets", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("Targets", currentResult);
         currentResult.setValue("description", "<p>The server instances, clusters, or migratable targets defined in the current domain that are candidates for hosting a file store, JDBC store, or replicated store. If scoped to a Resource Group or Resource Group Template, the target is inherited from the Virtual Target.</p>  <p>When selecting a cluster, the store must be targeted to the same cluster as the JMS server. When selecting a migratable target, the store must be targeted it to the same migratable target as the migratable JMS server or SAF agent. As a best practice, a path service should use its own custom store and share the same target as the store.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("adder", "addTarget");
         currentResult.setValue("remover", "removeTarget");
         currentResult.setValue("dynamic", Boolean.TRUE);
      }

      if (!descriptors.containsKey("ThreeStepThreshold")) {
         getterName = "getThreeStepThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setThreeStepThreshold";
         }

         currentResult = new PropertyDescriptor("ThreeStepThreshold", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("ThreeStepThreshold", currentResult);
         currentResult.setValue("description", "<p>Specifies the threshold, in bytes, when the JDBC store uses 3 steps (insert, select, populate) instead of 1 step (insert) to populate an Oracle Blob data type. </p> <p>Applies only to Oracle databases where a Blob data type is used instead of the default Long Raw data type for record data. </p> <p>The default value is 200000.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(200000));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(4000));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkerCount")) {
         getterName = "getWorkerCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkerCount";
         }

         currentResult = new PropertyDescriptor("WorkerCount", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("WorkerCount", currentResult);
         currentResult.setValue("description", "<p>The number of JDBC store worker threads to process the workerload.</p> <ul> <li>A value of 1 indicates a single thread is used (the default).</li> <li>A value greater than 1 indicates that multiple threads are used.</li> <li>For Oracle databases, Oracle recommends users rebuild the primary key index into a reverse index for the JDBC Store table when the worker count is greater than 1.</li> <li>For non-Oracle databases, refer to the database provider's documentation for help with indexing.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(1000));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WorkerPreferredBatchSize")) {
         getterName = "getWorkerPreferredBatchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWorkerPreferredBatchSize";
         }

         currentResult = new PropertyDescriptor("WorkerPreferredBatchSize", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("WorkerPreferredBatchSize", currentResult);
         currentResult.setValue("description", "<p>Specifies the batch size when the <code>Worker Count</code> attribute is configured to a value greater than 1.</p> <p>Used to configure the workload the JDBC store incrementally puts on each worker thread. The workload consists of IO requests which are grouped and pushed to each JDBC worker thread for processing. If the IO request is very large (for example 1M), then tune this attribute to a smaller value.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("XAResourceName")) {
         getterName = "getXAResourceName";
         setterName = null;
         currentResult = new PropertyDescriptor("XAResourceName", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("XAResourceName", currentResult);
         currentResult.setValue("description", "<p>Overrides the name of the XAResource that this store registers with JTA.</p>  <p>You should not normally set this attribute. Its purpose is to allow the name of the XAResource to be overridden when a store has been upgraded from an older release and the store contained prepared transactions. The generated name should be used in all other cases.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
      }

      if (!descriptors.containsKey("OraclePiggybackCommitEnabled")) {
         getterName = "isOraclePiggybackCommitEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOraclePiggybackCommitEnabled";
         }

         currentResult = new PropertyDescriptor("OraclePiggybackCommitEnabled", JDBCStoreMBean.class, getterName, setterName);
         descriptors.put("OraclePiggybackCommitEnabled", currentResult);
         currentResult.setValue("description", "Enables committing a batch of INSERT or DELETE operations with the last operation of the transaction instead of issuing a separate commit call to database server which saves a server round trip. This feature benefits applications that have many transactions of a small number of operations or small messages. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.3.1 ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JDBCStoreMBean.class.getMethod("addTarget", TargetMBean.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Targets a server instance to a store.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

      mth = JDBCStoreMBean.class.getMethod("removeTarget", TargetMBean.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("target", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Untargets a server instance from a store.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "Targets");
      }

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
