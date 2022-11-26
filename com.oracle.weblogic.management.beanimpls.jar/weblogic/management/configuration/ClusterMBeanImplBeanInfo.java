package weblogic.management.configuration;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class ClusterMBeanImplBeanInfo extends TargetMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ClusterMBean.class;

   public ClusterMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ClusterMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.management.configuration.ClusterMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.management.configuration");
      String description = (new String("<p>This bean represents a cluster in the domain. Servers join a cluster by calling ServerMBean.setCluster with the logical name of the cluster. A configuration may define zero or more clusters. They may be looked up by logical name.</p>  <p>The name of a cluster denotes its logical cluster name.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.configuration.ClusterMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AdditionalAutoMigrationAttempts")) {
         getterName = "getAdditionalAutoMigrationAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAdditionalAutoMigrationAttempts";
         }

         currentResult = new PropertyDescriptor("AdditionalAutoMigrationAttempts", ClusterMBean.class, getterName, setterName);
         descriptors.put("AdditionalAutoMigrationAttempts", currentResult);
         currentResult.setValue("description", "<p>A migratable server could fail to come up on every possible configured machine. This attribute controls how many further attempts, after the first one, should be tried.</p>  <p>Note that each attempt specified here indicates another full circuit of migrations amongst all the configured machines. So for a 3-server cluster, and the default value of 3, a total of 9 migrations will be attempted.</p>  <p>If it is set to -1, migrations will go on forever until the server starts.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AsyncSessionQueueTimeout")) {
         getterName = "getAsyncSessionQueueTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAsyncSessionQueueTimeout";
         }

         currentResult = new PropertyDescriptor("AsyncSessionQueueTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("AsyncSessionQueueTimeout", currentResult);
         currentResult.setValue("description", "<p>Interval in seconds until the producer thread will wait for the AsyncSessionQueue to become unblocked.  Should be similar to the RequestTimeOut as that will determine the longest that the queue should remain full.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("AutoMigrationTableCreationDDLFile")) {
         getterName = "getAutoMigrationTableCreationDDLFile";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoMigrationTableCreationDDLFile";
         }

         currentResult = new PropertyDescriptor("AutoMigrationTableCreationDDLFile", ClusterMBean.class, getterName, setterName);
         descriptors.put("AutoMigrationTableCreationDDLFile", currentResult);
         currentResult.setValue("description", "<p>The absolute file path of a custom DDL file for creating the automatic migration database table.</p>  <p>This setting applies only if the <code>MigrationBasis</code> attribute is set to <code>database</code>, the <code>AutoMigrationTableCreationPolicy</code> attribute is set to <code>Always</code>, and the table name specified by the <code>AutoMigrationTableName</code> attribute does not already exist in the database.</p>  <p>If the above conditions apply, and this setting is set to the default, then the system will try to find a default DDL file for the creating the leasing database table in <code>WL_HOME/server/db/DB_TYPE/leasing.ddl</code>.</p>  <p>Note that the system ignores (skips) any <code>DROP</code> commands in the DDL file, and substitutes the value configured in the <code>AutoMigrationTableName</code> attribute for all occurrences of the word <code>ACTIVE</code> in the file.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationBasis"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableName"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationPolicy"), BeanInfoHelper.encodeEntities("#getDataSourceForAutomaticMigration")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "true");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("AutoMigrationTableCreationPolicy")) {
         getterName = "getAutoMigrationTableCreationPolicy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoMigrationTableCreationPolicy";
         }

         currentResult = new PropertyDescriptor("AutoMigrationTableCreationPolicy", ClusterMBean.class, getterName, setterName);
         descriptors.put("AutoMigrationTableCreationPolicy", currentResult);
         currentResult.setValue("description", "<p>Control automatic leasing table creation behavior.</p>  <p>This setting applies only if the <code>MigrationBasis</code> attribute is set to <code>database</code>.</p>  <ul> <li><code>Disabled</code> <p>Disables automatic leasing table creation. The behavior is same as manual table creation. Any singletons dependent on cluster leasing will fail to start unless the table defined by the <code>AutoMigrationTableName</code> already exists in the database.</p> </li>  <li><code>Always</code> <p>Force automatic leasing table creation if the table is not found. The default table name is \"ACTIVE\" and can be customized using the <code>AutoMigrationTableName</code> attribute. The SQL used to create the table when it is not found can be customized using the <code>AutoMigrationTableCreationDDLFile</code> attribute.</p> </li> </ul> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationBasis"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableName"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationDDLFile"), BeanInfoHelper.encodeEntities("#getDataSourceForAutomaticMigration")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "Disabled");
         currentResult.setValue("legalValues", new Object[]{"Disabled", "Always"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "true");
      }

      if (!descriptors.containsKey("AutoMigrationTableName")) {
         getterName = "getAutoMigrationTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoMigrationTableName";
         }

         currentResult = new PropertyDescriptor("AutoMigrationTableName", ClusterMBean.class, getterName, setterName);
         descriptors.put("AutoMigrationTableName", currentResult);
         currentResult.setValue("description", "<p>Return the name of the table to be used for server migration.</p>  <p>This setting applies only if the <code>MigrationBasis</code> attribute is set to <code>database</code>.</p>  <p>If the <code>AutoMigrationTableCreationPolicy</code> is set to <code>Always</code>, then the table name format must be specified in the form <code>[[[mycatalog.]myschema.]mytablename</code>; for example <code>myschema.mytablename</code>.  Each period symbol format is significant, where schema generally corresponds to username in many databases.</p>  <p>When no table name is specified, the table name is simply <code>ACTIVE</code> and the database implicitly determines the schema according to the JDBC data source user.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationBasis"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationDDLFile"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationPolicy"), BeanInfoHelper.encodeEntities("#getDataSourceForAutomaticMigration")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "ACTIVE");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CandidateMachinesForMigratableServers")) {
         getterName = "getCandidateMachinesForMigratableServers";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCandidateMachinesForMigratableServers";
         }

         currentResult = new PropertyDescriptor("CandidateMachinesForMigratableServers", ClusterMBean.class, getterName, setterName);
         descriptors.put("CandidateMachinesForMigratableServers", currentResult);
         currentResult.setValue("description", "<p>The set of machines (and order of preference) on which Node Manager will restart failed servers. (Requires you to enable each server for automatic migration.)</p>  <p>Each server can specify a subset of these cluster-wide candidates, which limits the machines on which the server can be restarted. Servers can also specify their own order of preference.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("ServerMBean#getCandidateMachines")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterAddress")) {
         getterName = "getClusterAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterAddress";
         }

         currentResult = new PropertyDescriptor("ClusterAddress", ClusterMBean.class, getterName, setterName);
         descriptors.put("ClusterAddress", currentResult);
         currentResult.setValue("description", "<p>The address that forms a portion of the URL a client uses to connect to this cluster, and that is used for generating EJB handles and entity EJB failover addresses. (This address may be either a DNS host name that maps to multiple IP addresses or a comma-separated list of single address host names or IP addresses.)</p>  <p>Defines the address to be used by clients to connect to this cluster. This address may be either a DNS host name that maps to multiple IP addresses or a comma separated list of single address host names or IP addresses. If network channels are configured, it is possible to set the cluster address on a per channel basis.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.NetworkChannelMBean#getClusterAddress")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterBroadcastChannel")) {
         getterName = "getClusterBroadcastChannel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterBroadcastChannel";
         }

         currentResult = new PropertyDescriptor("ClusterBroadcastChannel", ClusterMBean.class, getterName, setterName);
         descriptors.put("ClusterBroadcastChannel", currentResult);
         currentResult.setValue("description", "<p>Specifies the channel used to handle communications within a cluster. If no channel is specified the default channel is used.</p> <p>ClusterBroadcastChannel is only are supported if the unicast messaging type is used.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterMessagingMode")) {
         getterName = "getClusterMessagingMode";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterMessagingMode";
         }

         currentResult = new PropertyDescriptor("ClusterMessagingMode", ClusterMBean.class, getterName, setterName);
         descriptors.put("ClusterMessagingMode", currentResult);
         currentResult.setValue("description", "<p>Specifies the messaging type used in the cluster.</p> <p>Multicast messaging is provided for backwards compatibility.</p> ");
         setPropertyDescriptorDefault(currentResult, "unicast");
         currentResult.setValue("legalValues", new Object[]{"multicast", "unicast"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterType")) {
         getterName = "getClusterType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClusterType";
         }

         currentResult = new PropertyDescriptor("ClusterType", ClusterMBean.class, getterName, setterName);
         descriptors.put("ClusterType", currentResult);
         currentResult.setValue("description", "<p>Optimizes cross-cluster replication for the type of network that servers in the clusters use for administrative communication.</p>  <p>To enhance the reliability of HTTP sessions, you can configure servers in one cluster to replicate the session data to servers in a different cluster. In such an environment, configure the clusters to be one of the following types:</p>  <ul> <li><code>man</code> <p>If the clustered servers can send their data through a metro area network (man) in which latency is negligible. With this ClusterType value, servers replicate session state synchronously and in memory only. For example, when serverA in cluster1 starts an HTTP session, its backup server, serverB in cluster2, immediately replicates this session in memory to Server B.</p> </li>  <li><code>wan</code> <p>If the clusters are far apart or send their data through a wide area network (wan) that experiences significant network latency. With this ClusterType value, a server replicates session state synchronously to the backup server in the same cluster and asynchronously to a server in the remote cluster. For example, when serverA in cluster1 starts an HTTP session, it sends the data to serverB in cluster1 and then asynchronously sends data to serverX in cluster 2. ServerX will persist the session state in the database.</p>  <p>If you persist session data in a replicating database, and if you prefer to use the database to replicate the data instead of WebLogic Server, choose a cluster type of <code>wan</code> and leave the remote cluster address undefined. WebLogic Server saves the session data to the local database and assumes that the database replicates data as needed.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("legalValues", new Object[]{"none", "wan", "man"});
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceClusterSystemResource")) {
         getterName = "getCoherenceClusterSystemResource";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCoherenceClusterSystemResource";
         }

         currentResult = new PropertyDescriptor("CoherenceClusterSystemResource", ClusterMBean.class, getterName, setterName);
         descriptors.put("CoherenceClusterSystemResource", currentResult);
         currentResult.setValue("description", "Coherence Cluster associated with this cluster. ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CoherenceTier")) {
         getterName = "getCoherenceTier";
         setterName = null;
         currentResult = new PropertyDescriptor("CoherenceTier", ClusterMBean.class, getterName, setterName);
         descriptors.put("CoherenceTier", currentResult);
         currentResult.setValue("description", "Coherence Tier associated with this WLS cluster. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConsensusParticipants")) {
         getterName = "getConsensusParticipants";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConsensusParticipants";
         }

         currentResult = new PropertyDescriptor("ConsensusParticipants", ClusterMBean.class, getterName, setterName);
         descriptors.put("ConsensusParticipants", currentResult);
         currentResult.setValue("description", "<p>Controls the number of cluster participants in determining consensus.</p> ");
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceForAutomaticMigration")) {
         getterName = "getDataSourceForAutomaticMigration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceForAutomaticMigration";
         }

         currentResult = new PropertyDescriptor("DataSourceForAutomaticMigration", ClusterMBean.class, getterName, setterName);
         descriptors.put("DataSourceForAutomaticMigration", currentResult);
         currentResult.setValue("description", "<p>The data source used by servers in the cluster during migration. (You must configure each Migratable Server within the cluster to use this data source.)</p>  <p>This setting applies only if the <code>MigrationBasis</code> attribute is set to <code>database</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getMigrationBasis"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationDDLFile"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationPolicy"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableName")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceForJobScheduler")) {
         getterName = "getDataSourceForJobScheduler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceForJobScheduler";
         }

         currentResult = new PropertyDescriptor("DataSourceForJobScheduler", ClusterMBean.class, getterName, setterName);
         descriptors.put("DataSourceForJobScheduler", currentResult);
         currentResult.setValue("description", "<p>Data source required to support persistence of jobs scheduled with the job scheduler</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceForSessionPersistence")) {
         getterName = "getDataSourceForSessionPersistence";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceForSessionPersistence";
         }

         currentResult = new PropertyDescriptor("DataSourceForSessionPersistence", ClusterMBean.class, getterName, setterName);
         descriptors.put("DataSourceForSessionPersistence", currentResult);
         currentResult.setValue("description", "<p>To support HTTP Session failover across data centers, a datasource is required to dump session state on disk.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatabaseLeasingBasisConnectionRetryCount")) {
         getterName = "getDatabaseLeasingBasisConnectionRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatabaseLeasingBasisConnectionRetryCount";
         }

         currentResult = new PropertyDescriptor("DatabaseLeasingBasisConnectionRetryCount", ClusterMBean.class, getterName, setterName);
         descriptors.put("DatabaseLeasingBasisConnectionRetryCount", currentResult);
         currentResult.setValue("description", "The maximum number of times Database Leasing will try to obtain a valid connection from the Data Source. ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatabaseLeasingBasisConnectionRetryDelay")) {
         getterName = "getDatabaseLeasingBasisConnectionRetryDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatabaseLeasingBasisConnectionRetryDelay";
         }

         currentResult = new PropertyDescriptor("DatabaseLeasingBasisConnectionRetryDelay", ClusterMBean.class, getterName, setterName);
         descriptors.put("DatabaseLeasingBasisConnectionRetryDelay", currentResult);
         currentResult.setValue("description", "The length of time, in milliseconds,Database Leasing will wait before attempting to obtain a new connection from the Data Source when a connection has failed. ");
         setPropertyDescriptorDefault(currentResult, new Long(1000L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatabaseLessLeasingBasis")) {
         getterName = "getDatabaseLessLeasingBasis";
         setterName = null;
         currentResult = new PropertyDescriptor("DatabaseLessLeasingBasis", ClusterMBean.class, getterName, setterName);
         descriptors.put("DatabaseLessLeasingBasis", currentResult);
         currentResult.setValue("description", "<p>Get attributes associated with database less leasing basis used for server migration and singleton services.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeathDetectorHeartbeatPeriod")) {
         getterName = "getDeathDetectorHeartbeatPeriod";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDeathDetectorHeartbeatPeriod";
         }

         currentResult = new PropertyDescriptor("DeathDetectorHeartbeatPeriod", ClusterMBean.class, getterName, setterName);
         descriptors.put("DeathDetectorHeartbeatPeriod", currentResult);
         currentResult.setValue("description", "<p>Gets the DeathDetectory HeartbeatPeriod value. The ClusterMaster sends a heartbeat every period seconds to ascertian the health of a member. Members monitor this heartbeat in order to ascertain the health of the server hosting the DeathDetector.  In this case, the ClusterMaster.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultLoadAlgorithm")) {
         getterName = "getDefaultLoadAlgorithm";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDefaultLoadAlgorithm";
         }

         currentResult = new PropertyDescriptor("DefaultLoadAlgorithm", ClusterMBean.class, getterName, setterName);
         descriptors.put("DefaultLoadAlgorithm", currentResult);
         currentResult.setValue("description", "<p>Defines the algorithm to be used for load-balancing between replicated services if none is specified for a particular service. The <code>round-robin</code> algorithm cycles through a list of WebLogic Server instances in order. <code>Weight-based</code> load balancing improves on the round-robin algorithm by taking into account a pre-assigned weight for each server. In <code>random</code> load balancing, requests are routed to servers at random.</p> ");
         setPropertyDescriptorDefault(currentResult, "round-robin");
         currentResult.setValue("legalValues", new Object[]{"round-robin", "weight-based", "random", "round-robin-affinity", "weight-based-affinity", "random-affinity"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicServers")) {
         getterName = "getDynamicServers";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicServers", ClusterMBean.class, getterName, setterName);
         descriptors.put("DynamicServers", currentResult);
         currentResult.setValue("description", "DynamicServers associated with this WLS cluster. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FencingGracePeriodMillis")) {
         getterName = "getFencingGracePeriodMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFencingGracePeriodMillis";
         }

         currentResult = new PropertyDescriptor("FencingGracePeriodMillis", ClusterMBean.class, getterName, setterName);
         descriptors.put("FencingGracePeriodMillis", currentResult);
         currentResult.setValue("description", "<p>During automatic migration, if the Cluster Master determines a server to be dead, it waits for this period of time (in milliseconds) before the Cluster Master migrates the service to another server in the cluster.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30000));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FrontendHTTPPort")) {
         getterName = "getFrontendHTTPPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHTTPPort";
         }

         currentResult = new PropertyDescriptor("FrontendHTTPPort", ClusterMBean.class, getterName, setterName);
         descriptors.put("FrontendHTTPPort", currentResult);
         currentResult.setValue("description", "<p>The name of the HTTP port to which all redirected URLs will be sent.</p>  <p>Sets the FrontendHTTPPort for the default webserver (not virtual hosts) for all the servers in the cluster. Provides a method to ensure that the webapp will always have the correct PORT information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place, when constructing the absolute urls for redirects.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getFrontendHTTPPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FrontendHTTPSPort")) {
         getterName = "getFrontendHTTPSPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHTTPSPort";
         }

         currentResult = new PropertyDescriptor("FrontendHTTPSPort", ClusterMBean.class, getterName, setterName);
         descriptors.put("FrontendHTTPSPort", currentResult);
         currentResult.setValue("description", "<p>The name of the secure HTTP port to which all redirected URLs will be sent.</p>  <p>Sets the FrontendHTTPSPort for the default webserver (not virtual hosts) for all the servers in the cluster. Provides a method to ensure that the webapp will always have the correct PORT information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place, when constructing the absolute urls for redirects.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getFrontendHTTPSPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FrontendHost")) {
         getterName = "getFrontendHost";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFrontendHost";
         }

         currentResult = new PropertyDescriptor("FrontendHost", ClusterMBean.class, getterName, setterName);
         descriptors.put("FrontendHost", currentResult);
         currentResult.setValue("description", "<p>The name of the host to which all redirected URLs will be sent.</p>  <p>Sets the HTTP FrontendHost for the default webserver (not virtual hosts) for all the servers in the cluster. Provides a method to ensure that the webapp will always have the correct HOST information, even when the request is coming through a firewall or a proxy. If this parameter is configured, the HOST header will be ignored and the information in this parameter will be used in its place, when constructing the absolute urls for redirects.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebServerMBean#getFrontendHost")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GreedySessionFlushInterval")) {
         getterName = "getGreedySessionFlushInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGreedySessionFlushInterval";
         }

         currentResult = new PropertyDescriptor("GreedySessionFlushInterval", ClusterMBean.class, getterName, setterName);
         descriptors.put("GreedySessionFlushInterval", currentResult);
         currentResult.setValue("description", "<p>Interval in seconds until HTTP Sessions are periodically flushed to secondary server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HTTPPingRetryCount")) {
         getterName = "getHTTPPingRetryCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHTTPPingRetryCount";
         }

         currentResult = new PropertyDescriptor("HTTPPingRetryCount", ClusterMBean.class, getterName, setterName);
         descriptors.put("HTTPPingRetryCount", currentResult);
         currentResult.setValue("description", "<p>Get the number of HTTP pings to execute before declaring a server unreachable. This comes into effect only when MaxServerCountForHTTPPing is > 0.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthCheckIntervalMillis")) {
         getterName = "getHealthCheckIntervalMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckIntervalMillis";
         }

         currentResult = new PropertyDescriptor("HealthCheckIntervalMillis", ClusterMBean.class, getterName, setterName);
         descriptors.put("HealthCheckIntervalMillis", currentResult);
         currentResult.setValue("description", "<p>Interval in milliseconds at which Migratable Servers and Cluster Masters prove their liveness via the database.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HealthCheckPeriodsUntilFencing")) {
         getterName = "getHealthCheckPeriodsUntilFencing";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHealthCheckPeriodsUntilFencing";
         }

         currentResult = new PropertyDescriptor("HealthCheckPeriodsUntilFencing", ClusterMBean.class, getterName, setterName);
         descriptors.put("HealthCheckPeriodsUntilFencing", currentResult);
         currentResult.setValue("description", "<p>Maximum number of periods that a cluster member will wait before timing out a Cluster Master and also the maximum number of periods the Cluster Master will wait before timing out a Migratable Server.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(6));
         currentResult.setValue("legalMin", new Integer(2));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdlePeriodsUntilTimeout")) {
         getterName = "getIdlePeriodsUntilTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdlePeriodsUntilTimeout";
         }

         currentResult = new PropertyDescriptor("IdlePeriodsUntilTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("IdlePeriodsUntilTimeout", currentResult);
         currentResult.setValue("description", "<p>Maximum number of periods that a cluster member will wait before timing out a member of a cluster.</p>  <p>Maximum number of periods that a cluster member will wait before timing out a member of a cluster.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(3));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InterClusterCommLinkHealthCheckInterval")) {
         getterName = "getInterClusterCommLinkHealthCheckInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInterClusterCommLinkHealthCheckInterval";
         }

         currentResult = new PropertyDescriptor("InterClusterCommLinkHealthCheckInterval", ClusterMBean.class, getterName, setterName);
         descriptors.put("InterClusterCommLinkHealthCheckInterval", currentResult);
         currentResult.setValue("description", "<p>If the cluster link between two clusters goes down, a trigger will run periodically to see if the link is restored. The duration is specified in milliseconds.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JTACluster")) {
         getterName = "getJTACluster";
         setterName = null;
         currentResult = new PropertyDescriptor("JTACluster", ClusterMBean.class, getterName, setterName);
         descriptors.put("JTACluster", currentResult);
         currentResult.setValue("description", "JTA associated with this WLS cluster. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JobSchedulerTableName")) {
         getterName = "getJobSchedulerTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJobSchedulerTableName";
         }

         currentResult = new PropertyDescriptor("JobSchedulerTableName", ClusterMBean.class, getterName, setterName);
         descriptors.put("JobSchedulerTableName", currentResult);
         currentResult.setValue("description", "<p>The table name to use for storing timers active with the job scheduler</p> ");
         setPropertyDescriptorDefault(currentResult, "WEBLOGIC_TIMERS");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxServerCountForHttpPing")) {
         getterName = "getMaxServerCountForHttpPing";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxServerCountForHttpPing";
         }

         currentResult = new PropertyDescriptor("MaxServerCountForHttpPing", ClusterMBean.class, getterName, setterName);
         descriptors.put("MaxServerCountForHttpPing", currentResult);
         currentResult.setValue("description", "<p>Get the maximum number of servers that can be pinged via HTTP when the local server has lost multicast heartbeats from remote members. By default the server is taken out of the cluster when 3 consecutive heartbeats are lost. With this value > 0, the server attempts to ping the remote server point-to-point before declaring it unreachable. The ping is considered successful only when the cluster is in a stable state which means that the servers have already exchanged annoucements and the data on multicast is primarily liveliness heartbeat.</p> <p> NOTE: This mechanism is useful only as a substitute for multicast heartbeats. If subsystems rely on sending data over multicast then they will continue to fail. If an application relies on WebLogic features that use multicast for sending and receiving data over multicast, this option is not useful. It is useful for HTTP session replication based applications where replication updates are sent point-to-point and multicast is only used to determine liveliness. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MemberWarmupTimeoutSeconds")) {
         getterName = "getMemberWarmupTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberWarmupTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("MemberWarmupTimeoutSeconds", ClusterMBean.class, getterName, setterName);
         descriptors.put("MemberWarmupTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>Maximum number of seconds that a cluster member will wait to discover and synchronize with other servers in the cluster. Normally, the member will be able to sync in 30 seconds. If the value of this attribute is higher, that does not necessarily mean that it will take longer for the member to warmup. Instead it defines an upper bound on the time that a server will wait to sync with the servers that it has discovered. If the value is set 0, servers will not attempt to discover other running server in the cluster during server initialization</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigratableTargets")) {
         getterName = "getMigratableTargets";
         setterName = null;
         currentResult = new PropertyDescriptor("MigratableTargets", ClusterMBean.class, getterName, setterName);
         descriptors.put("MigratableTargets", currentResult);
         currentResult.setValue("description", "<p>Returns all the MigratableTargets for this cluster</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MigrationBasis")) {
         getterName = "getMigrationBasis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMigrationBasis";
         }

         currentResult = new PropertyDescriptor("MigrationBasis", ClusterMBean.class, getterName, setterName);
         descriptors.put("MigrationBasis", currentResult);
         currentResult.setValue("description", "<p>Controls the mechanism used for server and service migration.</p>  <ul> <li><b>Database</b> -- Requires the availability of a high-availability database, such as Oracle RAC, to store leasing information. Requires also configuring the <code>DataSourceForAutomaticMigration</code> attribute. Optionally also configure <code>AutoMigrationTableName</code> (default is \"ACTIVE\"), <code>AutoMigrationTableCreationPolicy</code>, and <code>AutoMigrationTableCreationDDLFile</code>.</li>  <li><b>Consensus</b> -- Stores the leasing information in-memory within a cluster member. This option requires Node Manager to be configured and running.</li> </ul>  <p><b>Note:</b> Within a WebLogic Server installation, you can only use one type of leasing. Although it is possible to implement multiple features that use leasing within your environment, each must use the same kind of leasing.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getAutoMigrationTableName"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationDDLFile"), BeanInfoHelper.encodeEntities("#getAutoMigrationTableCreationPolicy"), BeanInfoHelper.encodeEntities("#getDataSourceForAutomaticMigration")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "database");
         currentResult.setValue("legalValues", new Object[]{"database", "consensus"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MillisToSleepBetweenAutoMigrationAttempts")) {
         getterName = "getMillisToSleepBetweenAutoMigrationAttempts";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMillisToSleepBetweenAutoMigrationAttempts";
         }

         currentResult = new PropertyDescriptor("MillisToSleepBetweenAutoMigrationAttempts", ClusterMBean.class, getterName, setterName);
         descriptors.put("MillisToSleepBetweenAutoMigrationAttempts", currentResult);
         currentResult.setValue("description", "<p>Controls how long of a pause there should be between the migration attempts described in getAdditionalAutoMigrationAttempts(). Note that this delay only happens when the server has failed to come up on every machine.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getAdditionalAutoMigrationAttempts")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Long(180000L));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastAddress")) {
         getterName = "getMulticastAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastAddress";
         }

         currentResult = new PropertyDescriptor("MulticastAddress", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastAddress", currentResult);
         currentResult.setValue("description", "<p>The multicast address used by cluster members to communicate with each other.</p>  <p>The valid range is from from 224.0.0.0 to 239.255.255.255. The default value used by WebLogic Server is 239.192.0.0.  You should avoid using multicast addresses in the range x.0.0.1</p>  <p>This address should be unique to this cluster and should not be shared by other applications.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getInterfaceAddress"), BeanInfoHelper.encodeEntities("#getMulticastPort")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "239.192.0.0");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastBufferSize")) {
         getterName = "getMulticastBufferSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastBufferSize";
         }

         currentResult = new PropertyDescriptor("MulticastBufferSize", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastBufferSize", currentResult);
         currentResult.setValue("description", "<p>The multicast socket send/receive buffer size (at least 64 kilobytes).</p>  <p>Returns the multicast socket send/receive buffer size.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(64));
         currentResult.setValue("legalMin", new Integer(64));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastDataEncryption")) {
         getterName = "getMulticastDataEncryption";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastDataEncryption";
         }

         currentResult = new PropertyDescriptor("MulticastDataEncryption", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastDataEncryption", currentResult);
         currentResult.setValue("description", "<p>Enables multicast data to be encrypted. Only the multicast data is encrypted. Multicast header information is not encrypted.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("MulticastPort")) {
         getterName = "getMulticastPort";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastPort";
         }

         currentResult = new PropertyDescriptor("MulticastPort", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastPort", currentResult);
         currentResult.setValue("description", "<p>The multicast port (between 1 and 65535) used by cluster members to communicate with each other.</p>  <p>Defines the multicast port used by cluster members to communicate with each other.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setMulticastPort"), BeanInfoHelper.encodeEntities("#getMulticastAddress")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(7001));
         currentResult.setValue("legalMax", new Integer(65535));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.0.0");
      }

      if (!descriptors.containsKey("MulticastSendDelay")) {
         getterName = "getMulticastSendDelay";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastSendDelay";
         }

         currentResult = new PropertyDescriptor("MulticastSendDelay", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastSendDelay", currentResult);
         currentResult.setValue("description", "<p>The amount of time (between 0 and 250 milliseconds) to delay sending message fragments over multicast in order to avoid OS-level buffer overflow.</p>  <p>Defines the number of milliseconds to delay sending message fragments over multicast in order to avoid OS-level buffer overflow.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMax", new Integer(250));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MulticastTTL")) {
         getterName = "getMulticastTTL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMulticastTTL";
         }

         currentResult = new PropertyDescriptor("MulticastTTL", ClusterMBean.class, getterName, setterName);
         descriptors.put("MulticastTTL", currentResult);
         currentResult.setValue("description", "<p>The number of network hops (between 1 and 255) that a cluster multicast message is allowed to travel.</p>  <p>Defines the number of network hops that a cluster multicast message is allowed to travel. 1 restricts the cluster to one subnet.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(255));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", ClusterMBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "<p>The user-specified name of this MBean instance.</p>  <p>This name is included as one of the key properties in the MBean's <code>javax.management.ObjectName</code>:</p>  <p><code>Name=<i>user-specified-name</i></code></p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("legalNull", Boolean.TRUE);
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (!descriptors.containsKey("NumberOfServersInClusterAddress")) {
         getterName = "getNumberOfServersInClusterAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNumberOfServersInClusterAddress";
         }

         currentResult = new PropertyDescriptor("NumberOfServersInClusterAddress", ClusterMBean.class, getterName, setterName);
         descriptors.put("NumberOfServersInClusterAddress", currentResult);
         currentResult.setValue("description", "<p>Number of servers to be listed from this cluster when generating a cluster address automatically. This setting has no effect if Cluster Address is explicitly set.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getClusterAddress")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Integer(3));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OverloadProtection")) {
         getterName = "getOverloadProtection";
         setterName = null;
         currentResult = new PropertyDescriptor("OverloadProtection", ClusterMBean.class, getterName, setterName);
         descriptors.put("OverloadProtection", currentResult);
         currentResult.setValue("description", "<p>Get attributes related to server overload protection. The default values for all cluster members are set here. Individual servers can override them as needed.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistSessionsOnShutdown")) {
         getterName = "getPersistSessionsOnShutdown";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPersistSessionsOnShutdown";
         }

         currentResult = new PropertyDescriptor("PersistSessionsOnShutdown", ClusterMBean.class, getterName, setterName);
         descriptors.put("PersistSessionsOnShutdown", currentResult);
         currentResult.setValue("description", "<p>When shutting down servers, sessions are not updated. If the primary and secondary servers of a session are shut down with no session updates, the session will be lost. Turning on PersistSessionsOnShutdown will save any active sessions to the database specified in {@link ClusterMBean#getDataSourceForSessionPersistence()} when a server is shutdown. The sessions will not be written at any other time. (For example, they are not saved via this mechanism if there is a server crash.)</p>  <p>This attribute is applicable both to session persistence on server shutdown or session persistence across a WAN.</p>  <p>Rolling upgrade can potentially have a bad interaction with traditional in-memory session replication.  As managed servers are shutdown and upgraded, in-memory servlet sessions will be lost if both primary and secondary are rebooted before a new request arrives for the session.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoteClusterAddress")) {
         getterName = "getRemoteClusterAddress";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoteClusterAddress";
         }

         currentResult = new PropertyDescriptor("RemoteClusterAddress", ClusterMBean.class, getterName, setterName);
         descriptors.put("RemoteClusterAddress", currentResult);
         currentResult.setValue("description", "<p>Set the foreign cluster. Cluster infrastructure uses this address to connect to foreign cluster for HTTP Session WAN/MAN failover.</p> ");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReplicationChannel")) {
         getterName = "getReplicationChannel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicationChannel";
         }

         currentResult = new PropertyDescriptor("ReplicationChannel", ClusterMBean.class, getterName, setterName);
         descriptors.put("ReplicationChannel", currentResult);
         currentResult.setValue("description", "<p>The channel name to be used for replication traffic. Cluster infrastructure uses this channel to send updates for HTTP sessions and stateful session beans. If none is set then the default channel will be used.</p>  <p>In order for this feature to work, the named channel must exist on all members of the cluster and must be configured to use the same protocol. It is valid for the selected channel to be configured to use a secure protocol.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#getRemoteClusterAddress")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, "ReplicationChannel");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Servers")) {
         getterName = "getServers";
         setterName = null;
         currentResult = new PropertyDescriptor("Servers", ClusterMBean.class, getterName, setterName);
         descriptors.put("Servers", currentResult);
         currentResult.setValue("description", "<p>The servers which have declared membership in this cluster.</p> ");
         currentResult.setValue("relationship", "reference");
         currentResult.setValue("transient", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("VisibleToPartitions", "ALWAYS");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ServiceActivationRequestResponseTimeout")) {
         getterName = "getServiceActivationRequestResponseTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceActivationRequestResponseTimeout";
         }

         currentResult = new PropertyDescriptor("ServiceActivationRequestResponseTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("ServiceActivationRequestResponseTimeout", currentResult);
         currentResult.setValue("description", "The maximum time, in milliseconds seconds, for services to wait for response from cluster members. ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ServiceAgeThresholdSeconds")) {
         getterName = "getServiceAgeThresholdSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServiceAgeThresholdSeconds";
         }

         currentResult = new PropertyDescriptor("ServiceAgeThresholdSeconds", ClusterMBean.class, getterName, setterName);
         descriptors.put("ServiceAgeThresholdSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds (between 0 and 65534) by which the age of two conflicting services must differ before one is considered older than the other.</p>  <p>Defines the number of seconds by which the age of two conflicting services must differ before one is considered older than the other.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(180));
         currentResult.setValue("legalMax", new Integer(65534));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionFlushInterval")) {
         getterName = "getSessionFlushInterval";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionFlushInterval";
         }

         currentResult = new PropertyDescriptor("SessionFlushInterval", ClusterMBean.class, getterName, setterName);
         descriptors.put("SessionFlushInterval", currentResult);
         currentResult.setValue("description", "<p>Interval in seconds until HTTP Sessions are periodically flushed to the backup cluster to dump session state on disk.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(180));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SessionFlushThreshold")) {
         getterName = "getSessionFlushThreshold";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionFlushThreshold";
         }

         currentResult = new PropertyDescriptor("SessionFlushThreshold", ClusterMBean.class, getterName, setterName);
         descriptors.put("SessionFlushThreshold", currentResult);
         currentResult.setValue("description", "<p>When number of sessions to be flushed reaches this threshold limit, sessions will be flushed to the backup cluster before the flush interval. This helps the server to flush sessions faster under load.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SessionStateQueryRequestTimeout")) {
         getterName = "getSessionStateQueryRequestTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionStateQueryRequestTimeout";
         }

         currentResult = new PropertyDescriptor("SessionStateQueryRequestTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("SessionStateQueryRequestTimeout", currentResult);
         currentResult.setValue("description", "The maximum time, in seconds, for session state query request to wait for response from cluster members. ");
         setPropertyDescriptorDefault(currentResult, new Integer(30));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("SingletonSQLQueryHelper")) {
         getterName = "getSingletonSQLQueryHelper";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingletonSQLQueryHelper";
         }

         currentResult = new PropertyDescriptor("SingletonSQLQueryHelper", ClusterMBean.class, getterName, setterName);
         descriptors.put("SingletonSQLQueryHelper", currentResult);
         currentResult.setValue("description", "<p>Singleton Services uses certain SQL commands to talk to the database. By default, the commands are obtained from a WebLogic-supplied implementation of weblogic.cluster.singleton.QueryHelper. If the database is not suported, or the SQL needs to be more optimized or altered for a particular use case, one can change the class used by setting this variable. The classname provided will be loaded at boot time, and used to execute the various SQL queries.</p> ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("SingletonServiceRequestTimeout")) {
         getterName = "getSingletonServiceRequestTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSingletonServiceRequestTimeout";
         }

         currentResult = new PropertyDescriptor("SingletonServiceRequestTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("SingletonServiceRequestTimeout", currentResult);
         currentResult.setValue("description", "The maximum time, in milliseconds, for service activation requests will wait for response from cluster members.  <p>A value of <code>0</code> means that the request will wait indefinitely to complete.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(30000));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.2.0", (String)null, this.targetVersion) && !descriptors.containsKey("SiteName")) {
         getterName = "getSiteName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSiteName";
         }

         currentResult = new PropertyDescriptor("SiteName", ClusterMBean.class, getterName, setterName);
         descriptors.put("SiteName", currentResult);
         currentResult.setValue("description", "<p>The name of the site this cluster is associated with.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.2.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tags")) {
         getterName = "getTags";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTags";
         }

         currentResult = new PropertyDescriptor("Tags", ClusterMBean.class, getterName, setterName);
         descriptors.put("Tags", currentResult);
         currentResult.setValue("description", "<p>Return all tags on this Configuration MBean</p> ");
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("TxnAffinityEnabled")) {
         getterName = "getTxnAffinityEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTxnAffinityEnabled";
         }

         currentResult = new PropertyDescriptor("TxnAffinityEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("TxnAffinityEnabled", currentResult);
         currentResult.setValue("description", "If enabled, a server's transaction requests go to servers in the cluster that are already participating in the global transaction. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.3.0");
      }

      if (!descriptors.containsKey("UnicastDiscoveryPeriodMillis")) {
         getterName = "getUnicastDiscoveryPeriodMillis";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastDiscoveryPeriodMillis";
         }

         currentResult = new PropertyDescriptor("UnicastDiscoveryPeriodMillis", ClusterMBean.class, getterName, setterName);
         descriptors.put("UnicastDiscoveryPeriodMillis", currentResult);
         currentResult.setValue("description", "<p>The timer period that determines how  often other members in the cluster are discovered in unicast messaging scheme. This is not applicable to multicast mode. It applies only to unicast mode.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(3000));
         currentResult.setValue("legalMin", new Integer(1000));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UnicastReadTimeout")) {
         getterName = "getUnicastReadTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUnicastReadTimeout";
         }

         currentResult = new PropertyDescriptor("UnicastReadTimeout", ClusterMBean.class, getterName, setterName);
         descriptors.put("UnicastReadTimeout", currentResult);
         currentResult.setValue("description", "The specified read timeout, in milliseconds, on a unicast connection.  A timeout of zero is interpreted as an infinite timeout, that is blocking read forever. ");
         setPropertyDescriptorDefault(currentResult, new Integer(15000));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WANSessionPersistenceTableName")) {
         getterName = "getWANSessionPersistenceTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWANSessionPersistenceTableName";
         }

         currentResult = new PropertyDescriptor("WANSessionPersistenceTableName", ClusterMBean.class, getterName, setterName);
         descriptors.put("WANSessionPersistenceTableName", currentResult);
         currentResult.setValue("description", "<p>Return the name of the table to be used for WAN session persistence.</p> ");
         setPropertyDescriptorDefault(currentResult, "WLS_WAN_PERSISTENCE_TABLE");
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientCertProxyEnabled")) {
         getterName = "isClientCertProxyEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClientCertProxyEnabled";
         }

         currentResult = new PropertyDescriptor("ClientCertProxyEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("ClientCertProxyEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether to honor the WL-Proxy-Client-Cert header coming with the request or not. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isClientCertProxyEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isClientCertProxyEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ConcurrentSingletonActivationEnabled")) {
         getterName = "isConcurrentSingletonActivationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConcurrentSingletonActivationEnabled";
         }

         currentResult = new PropertyDescriptor("ConcurrentSingletonActivationEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("ConcurrentSingletonActivationEnabled", currentResult);
         currentResult.setValue("description", "Specifies whether to allow the concurrent activation, deactivation, or restart of two or more singleton services. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("DynamicallyCreated")) {
         getterName = "isDynamicallyCreated";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicallyCreated", ClusterMBean.class, getterName, setterName);
         descriptors.put("DynamicallyCreated", currentResult);
         currentResult.setValue("description", "<p>Return whether the MBean was created dynamically or is persisted to config.xml</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("transient", Boolean.TRUE);
      }

      if (!descriptors.containsKey("HttpTraceSupportEnabled")) {
         getterName = "isHttpTraceSupportEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHttpTraceSupportEnabled";
         }

         currentResult = new PropertyDescriptor("HttpTraceSupportEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("HttpTraceSupportEnabled", currentResult);
         currentResult.setValue("description", "<p>Returns the value of HttpTraceSupportEnabled. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isHttpTraceSupportEnabled"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isHttpTraceSupportEnabled")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant((String)null, (String)null, this.targetVersion) && !descriptors.containsKey("MemberDeathDetectorEnabled")) {
         getterName = "isMemberDeathDetectorEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMemberDeathDetectorEnabled";
         }

         currentResult = new PropertyDescriptor("MemberDeathDetectorEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("MemberDeathDetectorEnabled", currentResult);
         currentResult.setValue("description", "Enables faster Automatic Service Migration times with Database Leasing Basis. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "true");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("MessageOrderingEnabled")) {
         getterName = "isMessageOrderingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMessageOrderingEnabled";
         }

         currentResult = new PropertyDescriptor("MessageOrderingEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("MessageOrderingEnabled", currentResult);
         currentResult.setValue("description", "<p>Forces unicast messages to be processed in order. There are scenarios where JMS may update JNDI very frequently. It will result in a lot of messages over unicast. Due to the close proximity of messages the probability of out of order handling of messages increases which would trigger frequent state dumps. Frequent JNDI tree refresh may result in NameNotFoundException. Use this property to prevent out of order handling of messages.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("OneWayRmiForReplicationEnabled")) {
         getterName = "isOneWayRmiForReplicationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOneWayRmiForReplicationEnabled";
         }

         currentResult = new PropertyDescriptor("OneWayRmiForReplicationEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("OneWayRmiForReplicationEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates if one-way RMI is being used for replication. One-way RMI also requires configuring replication ports on each server in the cluster.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#getReplicationPorts()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (!descriptors.containsKey("ReplicationTimeoutEnabled")) {
         getterName = "isReplicationTimeoutEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setReplicationTimeoutEnabled";
         }

         currentResult = new PropertyDescriptor("ReplicationTimeoutEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("ReplicationTimeoutEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates if timeout should be applied to session replication calls.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("7.0.8.0", (String)null, this.targetVersion) && !descriptors.containsKey("SecureReplicationEnabled")) {
         getterName = "isSecureReplicationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecureReplicationEnabled";
         }

         currentResult = new PropertyDescriptor("SecureReplicationEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("SecureReplicationEnabled", currentResult);
         currentResult.setValue("description", "<p>Servers in a cluster replicate session data. If a replication channel is defined then the session data will be sent using the replication channel protocol and secured replication settings will be ignored. If no replication channel is defined and secured replication is enabled then session data for in-memory replication will be sent over SSL using the default secured channel. However, this added security for replication traffic comes with a significant cluster performance degradation. It should only be enabled if security is of greater concern than performance degradation.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "7.0.8.0");
      }

      if (BeanInfoHelper.isVersionCompliant("10.3.4.0", (String)null, this.targetVersion) && !descriptors.containsKey("SessionLazyDeserializationEnabled")) {
         getterName = "isSessionLazyDeserializationEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionLazyDeserializationEnabled";
         }

         currentResult = new PropertyDescriptor("SessionLazyDeserializationEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("SessionLazyDeserializationEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables increased efficiency with session replication. Enabling this attribute should be used only when configuring a WebLogic domain for Oracle Exalogic.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "10.3.4.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("SessionStateQueryProtocolEnabled")) {
         getterName = "isSessionStateQueryProtocolEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSessionStateQueryProtocolEnabled";
         }

         currentResult = new PropertyDescriptor("SessionStateQueryProtocolEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("SessionStateQueryProtocolEnabled", currentResult);
         currentResult.setValue("description", "Indicates if session state query protocol is enabled.  The Session State Query protocol will query the local cluster for the location of a session state instance if a request arrives at a server that is neither the primary or secondary server. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("WeblogicPluginEnabled")) {
         getterName = "isWeblogicPluginEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWeblogicPluginEnabled";
         }

         currentResult = new PropertyDescriptor("WeblogicPluginEnabled", ClusterMBean.class, getterName, setterName);
         descriptors.put("WeblogicPluginEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies if this cluster will receive requests from a proxy plug-in or <code>HttpClusterServlet</code>.</p>  <p>Set this attribute to <code>true</code> if the cluster will receive requests from a proxy plug-in or <code>HttpClusterServlet</code>. A call to <code>getRemoteAddr</code> will return the address of the browser client from the proprietary <code>WL-Proxy-Client-IP</code> header instead of the Web server.</p>  <p>Set this attribute to <code>false</code> to disable the <code>weblogic-plugin-enabled</code> parameter, <code>weblogic-plugin-enabled=false</code>, in the <code>config.xml</code>file.</p>  <p>Note: If you are using Oracle HTTP Server, the WebLogic Proxy Plug-In, or Oracle Traffic Director to distribute client requests to a Managed Server or a cluster, Oracle recommends setting this attribute to <code>true</code>.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.WebAppContainerMBean#isWeblogicPluginEnabled()"), BeanInfoHelper.encodeEntities("weblogic.management.configuration.ServerMBean#isWeblogicPluginEnabled()")};
         currentResult.setValue("see", seeObjectArray);
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("secureValue", new Boolean(false));
         currentResult.setValue("dynamic", Boolean.FALSE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      MethodDescriptor currentResult;
      Method mth;
      ParameterDescriptor[] parameterDescriptors;
      String methodKey;
      String[] throwsObjectArray;
      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion)) {
         mth = ClusterMBean.class.getMethod("addTag", String.class);
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
         mth = ClusterMBean.class.getMethod("removeTag", String.class);
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
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = ClusterMBean.class.getMethod("freezeCurrentValue", String.class);
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

      mth = ClusterMBean.class.getMethod("restoreDefaultValue", String.class);
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

      mth = ClusterMBean.class.getMethod("start");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Used to start all the servers belonging to the Cluster. HashMap contains references to TaskRuntimeMBeans corresponding to each server in the Cluster, keyed using the server name.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ClusterMBean.class.getMethod("kill");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         currentResult.setValue("deprecated", "9.0.0.0 ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Used to force a Shutdown of all the servers belonging to the Cluster.</p> ");
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
