package weblogic.jdbc.common.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;

public class OracleDataSourceRuntimeImplBeanInfo extends DataSourceRuntimeMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCOracleDataSourceRuntimeMBean.class;

   public OracleDataSourceRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OracleDataSourceRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jdbc.common.internal.OracleDataSourceRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.4.0");
      beanDescriptor.setValue("package", "weblogic.jdbc.common.internal");
      String description = (new String("Runtime MBean that represents a JDBC RAC JDBC data source. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveConnectionsAverageCount")) {
         getterName = "getActiveConnectionsAverageCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveConnectionsAverageCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsAverageCount", currentResult);
         currentResult.setValue("description", "<p>Average number of active connections in this instance of the data source.</p>  <p>Active connections are connections in use by an application.  This value is only valid if the resource is configured to allow shrinking.</p> ");
      }

      if (!descriptors.containsKey("ActiveConnectionsCurrentCount")) {
         getterName = "getActiveConnectionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveConnectionsCurrentCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The number of connections currently in use by applications.</p> ");
      }

      if (!descriptors.containsKey("ActiveConnectionsHighCount")) {
         getterName = "getActiveConnectionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveConnectionsHighCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsHighCount", currentResult);
         currentResult.setValue("description", "<p>Highest number of active database connections in this instance of the data source since the data source was instantiated. </p>  <p>Active connections are connections in use by an application.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("CommitOutcomeRetryTotalCount")) {
         getterName = "getCommitOutcomeRetryTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CommitOutcomeRetryTotalCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CommitOutcomeRetryTotalCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative total number of commit outcome query retries conducted before resolving the outcome or exceeding the retry seconds in this data source since the data source was deployed.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ConnectionDelayTime")) {
         getterName = "getConnectionDelayTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionDelayTime", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionDelayTime", currentResult);
         currentResult.setValue("description", "<p>The average amount of time, in milliseconds, that it takes to create a physical connection to the database.</p>  <p>The value is calculated as summary of all times to connect divided by the total number of connections.</p> ");
      }

      if (!descriptors.containsKey("ConnectionsTotalCount")) {
         getterName = "getConnectionsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsTotalCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsTotalCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative total number of database connections created in this data source since the data source was deployed.</p> ");
      }

      if (!descriptors.containsKey("CurrCapacity")) {
         getterName = "getCurrCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrCapacity", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrCapacity", currentResult);
         currentResult.setValue("description", "<p>The current count of JDBC connections in the connection pool in the data source.</p> ");
      }

      if (!descriptors.containsKey("CurrCapacityHighCount")) {
         getterName = "getCurrCapacityHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrCapacityHighCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrCapacityHighCount", currentResult);
         currentResult.setValue("description", "<p>Highest number of database connections available or in use (current capacity) in this instance of the data source since the data source was deployed.</p> ");
      }

      if (!descriptors.containsKey("DatabaseProductName")) {
         getterName = "getDatabaseProductName";
         setterName = null;
         currentResult = new PropertyDescriptor("DatabaseProductName", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DatabaseProductName", currentResult);
         currentResult.setValue("description", "<p> The product name of the database that this data source is connected to. </p> ");
      }

      if (!descriptors.containsKey("DatabaseProductVersion")) {
         getterName = "getDatabaseProductVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("DatabaseProductVersion", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DatabaseProductVersion", currentResult);
         currentResult.setValue("description", "<p> The product version of the database that this data source is connected to. </p> ");
      }

      if (!descriptors.containsKey("DeploymentState")) {
         getterName = "getDeploymentState";
         setterName = null;
         currentResult = new PropertyDescriptor("DeploymentState", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DeploymentState", currentResult);
         currentResult.setValue("description", "<p>The current deployment state of the module.</p>  <p>A module can be in one and only one of the following states. State can be changed via deployment or administrator console.</p>  <ul> <li>UNPREPARED. State indicating at this  module is neither  prepared or active.</li>  <li>PREPARED. State indicating at this module of this application is prepared, but not active. The classes have been loaded and the module has been validated.</li>  <li>ACTIVATED. State indicating at this module  is currently active.</li>  <li>NEW. State indicating this module has just been created and is being initialized.</li> </ul> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("#setDeploymentState(int)")};
         currentResult.setValue("see", seeObjectArray);
      }

      if (!descriptors.containsKey("DriverName")) {
         getterName = "getDriverName";
         setterName = null;
         currentResult = new PropertyDescriptor("DriverName", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DriverName", currentResult);
         currentResult.setValue("description", "<p> The product name of the JDBC driver that this data source is configured to use. </p> ");
      }

      if (!descriptors.containsKey("DriverVersion")) {
         getterName = "getDriverVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("DriverVersion", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("DriverVersion", currentResult);
         currentResult.setValue("description", "<p> The version of the JDBC driver that this data source is configured to use. </p> ");
      }

      if (!descriptors.containsKey("FailedAffinityBasedBorrowCount")) {
         getterName = "getFailedAffinityBasedBorrowCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedAffinityBasedBorrowCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedAffinityBasedBorrowCount", currentResult);
         currentResult.setValue("description", "The number of reserve requests for which an existing connection for the affinity policy was not found. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailedRCLBBasedBorrowCount")) {
         getterName = "getFailedRCLBBasedBorrowCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedRCLBBasedBorrowCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedRCLBBasedBorrowCount", currentResult);
         currentResult.setValue("description", "The number of reserve requests for which a connection was not found based on the runtime connection load balancing policy. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("FailedRepurposeCount")) {
         getterName = "getFailedRepurposeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedRepurposeCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedRepurposeCount", currentResult);
         currentResult.setValue("description", "<p>The number of repurpose errors that have occurred since the datasource was deployed.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("FailedReserveRequestCount")) {
         getterName = "getFailedReserveRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailedReserveRequestCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailedReserveRequestCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of requests for a connection from this data source that could not be fulfilled.</p> ");
      }

      if (!descriptors.containsKey("FailuresToReconnectCount")) {
         getterName = "getFailuresToReconnectCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FailuresToReconnectCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FailuresToReconnectCount", currentResult);
         currentResult.setValue("description", "<p>The number of times that the data source attempted to refresh a database connection and failed.</p>  <p>Failures may occur when the database is unavailable or when the network connection to the database is interrupted.</p> ");
      }

      if (!descriptors.containsKey("HighestNumAvailable")) {
         getterName = "getHighestNumAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumAvailable", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumAvailable", currentResult);
         currentResult.setValue("description", "<p>Highest number of database connections that were idle and available to be used by an application at any time in this instance of the data source since the data source was deployed.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("HighestNumUnavailable")) {
         getterName = "getHighestNumUnavailable";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumUnavailable", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumUnavailable", currentResult);
         currentResult.setValue("description", "<p>Highest number of database connections that were in use by applications or being tested by the system in this instance of the data source since the data source was deployed.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("Instances")) {
         getterName = "getInstances";
         setterName = null;
         currentResult = new PropertyDescriptor("Instances", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Instances", currentResult);
         currentResult.setValue("description", "The set of data source RAC instance runtime MBeans that are associated with this data source. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCDriverRuntime")) {
         getterName = "getJDBCDriverRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCDriverRuntime", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCDriverRuntime", currentResult);
         currentResult.setValue("description", "<p>Gets the JDBCDriverRuntimeMBean associated with this data source.</p> ");
         currentResult.setValue("relationship", "containment");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("JDBCReplayStatisticsRuntimeMBean")) {
         getterName = "getJDBCReplayStatisticsRuntimeMBean";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCReplayStatisticsRuntimeMBean", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JDBCReplayStatisticsRuntimeMBean", currentResult);
         currentResult.setValue("description", "<p>Get the statistics for replay only if the datasource is using an Oracle replay driver for 12.1.0.2 and later.  Otherwise, null is returned. The statistics are a snapshort.  To update the snapshot, call refreshStatistics() on the mbean.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("LastTask")) {
         getterName = "getLastTask";
         setterName = null;
         currentResult = new PropertyDescriptor("LastTask", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastTask", currentResult);
         currentResult.setValue("description", "<p>Get the last datasource Task</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("restRelationship", "reference");
      }

      if (!descriptors.containsKey("LeakedConnectionCount")) {
         getterName = "getLeakedConnectionCount";
         setterName = null;
         currentResult = new PropertyDescriptor("LeakedConnectionCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LeakedConnectionCount", currentResult);
         currentResult.setValue("description", "<p>The number of leaked connections. A leaked connection is a connection that was reserved from the data source but was not returned to the data source by calling <code>close()</code>.</p> ");
      }

      if (!descriptors.containsKey("ModuleId")) {
         getterName = "getModuleId";
         setterName = null;
         currentResult = new PropertyDescriptor("ModuleId", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ModuleId", currentResult);
         currentResult.setValue("description", "<p>Returns the identifier for this Component.  The identifier is unique within the application.</p>  <p>Typical modules will use the URI for their id.  Web Modules will return their context-root since the web-uri may not be unique within an EAR.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
      }

      if (!descriptors.containsKey("NumAvailable")) {
         getterName = "getNumAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumAvailable", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumAvailable", currentResult);
         currentResult.setValue("description", "<p>The number of database connections that are currently idle and  available to be used by applications in this instance of the data source.</p> ");
      }

      if (!descriptors.containsKey("NumUnavailable")) {
         getterName = "getNumUnavailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumUnavailable", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumUnavailable", currentResult);
         currentResult.setValue("description", "<p>The number of connections currently in use by applications or being tested in this instance of the data source.</p> ");
      }

      if (!descriptors.containsKey("ONSClientRuntime")) {
         getterName = "getONSClientRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("ONSClientRuntime", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ONSClientRuntime", currentResult);
         currentResult.setValue("description", "The ONS client runtime MBean associated with this data source. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PrepStmtCacheAccessCount")) {
         getterName = "getPrepStmtCacheAccessCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheAccessCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheAccessCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of the number of times that the statement cache was accessed.</p> ");
      }

      if (!descriptors.containsKey("PrepStmtCacheAddCount")) {
         getterName = "getPrepStmtCacheAddCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheAddCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheAddCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of the number of statements added to the statement cache.</p>  <p>Each connection in the connection pool has its own cache of statements. This number is the sum of the number of statements added to the caches for all connections in the connection pool.</p> ");
      }

      if (!descriptors.containsKey("PrepStmtCacheCurrentSize")) {
         getterName = "getPrepStmtCacheCurrentSize";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheCurrentSize", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheCurrentSize", currentResult);
         currentResult.setValue("description", "<p>The number of prepared and callable statements currently cached in the statement cache.</p>  <p>Each connection in the connection pool has its own cache of statements. This number is the sum of the number of statements in the caches for all connections in the connection pool.</p> ");
      }

      if (!descriptors.containsKey("PrepStmtCacheDeleteCount")) {
         getterName = "getPrepStmtCacheDeleteCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheDeleteCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheDeleteCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of statements discarded from the cache.</p>  <p>Each connection in the connection pool has its own cache of statements. This number is the sum of the number of statements that were discarded from the caches for all connections in the connection pool.</p> ");
      }

      if (!descriptors.containsKey("PrepStmtCacheHitCount")) {
         getterName = "getPrepStmtCacheHitCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheHitCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheHitCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of the number of times that statements from the cache were used.</p> ");
      }

      if (!descriptors.containsKey("PrepStmtCacheMissCount")) {
         getterName = "getPrepStmtCacheMissCount";
         setterName = null;
         currentResult = new PropertyDescriptor("PrepStmtCacheMissCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PrepStmtCacheMissCount", currentResult);
         currentResult.setValue("description", "<p>The number of times that a statement request could not be satisfied with a statement from the cache.</p> ");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "<p>The list of properties for a data source that are passed to the JDBC driver when creating database connections.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("RepurposeCount")) {
         getterName = "getRepurposeCount";
         setterName = null;
         currentResult = new PropertyDescriptor("RepurposeCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RepurposeCount", currentResult);
         currentResult.setValue("description", "<p>The number of times connections have been repurposed since the datasource was deployed.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ReserveRequestCount")) {
         getterName = "getReserveRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReserveRequestCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReserveRequestCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of requests for a connection from this data source.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResolvedAsCommittedTotalCount")) {
         getterName = "getResolvedAsCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResolvedAsCommittedTotalCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResolvedAsCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative total number of commit outcomes successfully resolved as committed in this data source since the data source was deployed. This does not refer to the number of outcome query retries used to resolve the outcomes.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("ResolvedAsNotCommittedTotalCount")) {
         getterName = "getResolvedAsNotCommittedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ResolvedAsNotCommittedTotalCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResolvedAsNotCommittedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative total number of commit outcomes successfully resolved as not committed in this data source since the data source was deployed. This does not refer to the number of outcome query retries used to resolve the outcomes.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("ServiceName")) {
         getterName = "getServiceName";
         setterName = null;
         currentResult = new PropertyDescriptor("ServiceName", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ServiceName", currentResult);
         currentResult.setValue("description", "The database service name for this RAC data source. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>The current state of the data source.</p> <p>Possible states are:</p> <ul><li><code>Running</code> - the data source is enabled (deployed and not <code>Suspended</code>). This is the normal state of the data source. This state includes conditions when the database server is not available and the data source is created (creation retry must be enabled) or when all connections have failed connection tests (on creation, on reserve, or periodic testing).</li> <li><code>Suspended</code> - the data source has been disabled.</li> <li><code>Shutdown</code> - the data source is shutdown and all database connections have been closed.</li> <li><code>Overloaded</code> - all resources in pool are in use.</li> <li><code>Unknown</code> - the data source state is unknown.</li></ul> ");
      }

      if (!descriptors.containsKey("SuccessfulAffinityBasedBorrowCount")) {
         getterName = "getSuccessfulAffinityBasedBorrowCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessfulAffinityBasedBorrowCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessfulAffinityBasedBorrowCount", currentResult);
         currentResult.setValue("description", "The number of reserve requests for which an existing connection was found that satisfied the affinity policy. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SuccessfulRCLBBasedBorrowCount")) {
         getterName = "getSuccessfulRCLBBasedBorrowCount";
         setterName = null;
         currentResult = new PropertyDescriptor("SuccessfulRCLBBasedBorrowCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("SuccessfulRCLBBasedBorrowCount", currentResult);
         currentResult.setValue("description", "The number of reserve requests for which existing connections were found that satisfied the runtime connection load balancing policy. ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion) && !descriptors.containsKey("Tasks")) {
         getterName = "getTasks";
         setterName = null;
         currentResult = new PropertyDescriptor("Tasks", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Tasks", currentResult);
         currentResult.setValue("description", "<p>Get preexisting datasource Tasks</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "12.2.1.1.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.3.0", (String)null, this.targetVersion) && !descriptors.containsKey("UnresolvedTotalCount")) {
         getterName = "getUnresolvedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("UnresolvedTotalCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UnresolvedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The cumulative total number of commit outcomes unsuccessfully resolved in this data source since the data source was deployed. This does not refer to the number of outcome query retries used to resolve the outcomes.</p> ");
         currentResult.setValue("since", "12.2.1.3.0");
      }

      if (!descriptors.containsKey("VersionJDBCDriver")) {
         getterName = "getVersionJDBCDriver";
         setterName = null;
         currentResult = new PropertyDescriptor("VersionJDBCDriver", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("VersionJDBCDriver", currentResult);
         currentResult.setValue("description", "<p>The driver class name of the JDBC driver used to create database connections. </p> ");
      }

      if (!descriptors.containsKey("WaitSecondsHighCount")) {
         getterName = "getWaitSecondsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitSecondsHighCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitSecondsHighCount", currentResult);
         currentResult.setValue("description", "<p>The highest number of seconds that an application waited for a connection (the longest connection reserve wait time) from this instance of the connection pool since the connection pool was instantiated.</p> <p>This value is updated when a completed <code>getConnection</code> request takes longer to return a connection than any previous request.</p> ");
      }

      if (!descriptors.containsKey("WaitingForConnectionCurrentCount")) {
         getterName = "getWaitingForConnectionCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForConnectionCurrentCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForConnectionCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The number of connection requests waiting for a database connection.</p> ");
      }

      if (!descriptors.containsKey("WaitingForConnectionFailureTotal")) {
         getterName = "getWaitingForConnectionFailureTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForConnectionFailureTotal", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForConnectionFailureTotal", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of requests for a connection from this data source that had to wait before getting a connection and eventually failed to get a connection.</p>  <p>Waiting connection requests can fail for a variety of reasons, including waiting for longer than the ConnectionReserveTimeoutSeconds.</p> ");
      }

      if (!descriptors.containsKey("WaitingForConnectionHighCount")) {
         getterName = "getWaitingForConnectionHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForConnectionHighCount", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForConnectionHighCount", currentResult);
         currentResult.setValue("description", "<p>Highest number of application requests concurrently waiting for a connection from this instance of the data source.</p> ");
      }

      if (!descriptors.containsKey("WaitingForConnectionSuccessTotal")) {
         getterName = "getWaitingForConnectionSuccessTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForConnectionSuccessTotal", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForConnectionSuccessTotal", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of requests for a connection from this data source that had to wait before getting a connection and eventually succeeded in getting a connection.</p> ");
      }

      if (!descriptors.containsKey("WaitingForConnectionTotal")) {
         getterName = "getWaitingForConnectionTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("WaitingForConnectionTotal", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WaitingForConnectionTotal", currentResult);
         currentResult.setValue("description", "<p>The cumulative, running count of requests for a connection from this data source that had to wait before getting a connection, including those that eventually got a connection and those that did not get a connection.</p> ");
      }

      if (BeanInfoHelper.isVersionCompliant("9.0.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("WorkManagerRuntimes")) {
         getterName = "getWorkManagerRuntimes";
         setterName = null;
         currentResult = new PropertyDescriptor("WorkManagerRuntimes", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("WorkManagerRuntimes", currentResult);
         currentResult.setValue("description", "<p>Get the runtime mbeans for all work managers defined in this component</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("since", "9.0.0.0");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("Enabled", JDBCOracleDataSourceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the data source is enabled or disabled:</p>  <ul> <li><code>true</code> if the data source is enabled.</li> <li><code>false</code> if the data source is disabled.</li> </ul> ");
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
      Method mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("testPool");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Tests the connection pool in the data source by reserving and releasing a connection from it.</p>  <p>If the pool configuration attribute TestConnectionsOnReserve is enabled, the acquired connection is also tested as part of the reserve operation.</p> ");
         String[] seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.configuration.JDBCConnectionPoolMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("shrink");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Shrinks the database connection pool in the data source to either the current number of reserved connections or the initial size of the connection pool, which ever is greater.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("reset");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Resets the connection pool in the data source by shutting down and recreating all available database connections in the pool.</p>  <p>Use when a data source is in the health state of <code>Unhealthy</code> and needs to be reinitialized.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("suspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Synchronously, gracefully suspends a data source that has the health state of <code>Running</code> and  disables existing connections. This operation immediately marks the data source as suspended and no further connections are created.  Idle (not reserved) connections are marked as disabled. After a timeout period for the suspend operation, all remaining connections in the pool are marked as suspended and the following exception is thrown for any operations on the connection, indicating that the data source is suspended: java.sql.SQLRecoverableException: Connection has been administratively disabled. Try later. If graceful suspend is done as part of a graceful shutdown operation, connections are immediately closed when no longer reserved or at the end of the timeout period. If not done as part of a shutdown operation, these connections remain in the pool and are not closed because the pool may be resumed.</p>  <p>By default, the timeout period is 60 seconds. You can change the value of this timeout period by configuring or dynamically setting Inactive Connection Timeout Seconds to a non-zero value.</p>  <p>Setting IgnoreInUseConnectionsEnabled to false causes the operation to fail if in-use connections exist.</p>  <p>If successful, the health state is set to <code>Suspended</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("forceSuspend");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forcefully suspends a data source that has the health state of <code>Running</code>, including disconnecting all current connection users. All current connections are closed and recreated.</p>  <p>If successful, the health state is set to <code>Suspended</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("shutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Gracefully, synchronously shuts down a data source that has a health state of <code>Running</code>. A graceful (non-forced) datasource shutdown operation involves first gracefully suspending the data source and then releasing the associated resources including the connections. See the description above for details of gracefully suspending the datasource.  After the datasource is gracefully suspended, all remaining in-use connections are closed and the datasource is marked as shut down. </p>  <p>If successful, the health state is set to <code>Shutdown</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("forceShutdown");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Forcefully shuts down a data source that has a health state of <code>Running</code>, including forcing the disconnection of all current connection users.</p>  <p>If successful, the health state is set to <code>Shutdown</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("resume");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Restores all access to and operations on a data source that has a health state of <code>Suspended</code>.</p>  <p>If successful, the health state is set to <code>Running</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user. </p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("start");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Starts a data source that has a health state of <code>Shutdown</code>.</p>  <p>If successful, the health state is set to <code>Running</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("poolExists", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("poolName", "Name of the pool being looked for ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Specifies whether a data source with the given name exists.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("clearStatementCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>For each connection in the connection pool, clears the statement cache of Prepared and Callable Statements.</p> ");
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("dumpPool");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Prints out information about all the connections in the connection pool in the data source.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("dumpPoolProfile");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Prints out profile information about the data source.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("isOperationAllowed", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("operation", "The name of the operation to be performed. Valid values include: Start, Shutdown, Suspend, Resume, Reset, Shrink, Clear ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         String[] throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("IllegalArgumentException Thrown when operation parameter is not recognized as a supported operation.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Indicates whether the specified operation is valid based on the state of the underlying DataSource.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
         currentResult.setValue("rolePermitAll", Boolean.TRUE);
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("suspend", Integer.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("operationSecs", "The number of seconds to allow the operation to run before stopping processing.  If set to 0, the default is used. The default is to use Inactive Connection Timeout Seconds if set or 60 seconds.  If you want a minimal timeout, set the value to 1. If you want no timeout, set it to a large value (not recommended). ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("restName", "asyncSuspend");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Asynchronously, gracefully suspends a data source that has the health state of <code>Running</code> and  disables existing connections. This operation immediately marks the data source as suspended and no further connections are created.  Idle (not reserved) connections are marked as disabled. After a timeout period for the suspend operation, all remaining connections in the pool are marked as suspended and the following exception is thrown for any operations on the connection, indicating that the data source is suspended: java.sql.SQLRecoverableException: Connection has been administratively disabled. Try later. These connections remain in the pool and are not closed because the pool may be resumed.</p>  <p>Setting IgnoreInUseConnectionsEnabled to false causes the operation to fail if in-use connections exist.</p>  <p>If successful, the health state is set to <code>Suspended</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("restName", "asyncSuspend");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("shutdown", Integer.TYPE);
         parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("operationSecs", "The number of seconds to allow the operation to run before stopping processing.  If set to 0, the default is used. The default is to use Inactive Connection Timeout Seconds if set or 60 seconds.  If you want a minimal timeout, set the value to 1. If you want no timeout, set it to a large value (not recommended). ")};
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, parameterDescriptors);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("restName", "asyncShutdown");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "<p>Gracefully, asynchronously shuts down a data source that has a health state of <code>Running</code>. A graceful (non-forced) datasource shutdown operation involves first gracefully suspending the data source and then releasing the associated resources including the connections. See the description above for details of gracefully suspending the datasource.  After the datasource is gracefully suspended, all remaining in-use connections are closed and the datasource is marked as shut down. </p>  <p>Setting IgnoreInUseConnectionsEnabled to false causes the operation to fail if in-use connections exist.</p>  <p>If successful, the health state is set to <code>Shutdown</code>.</p>  <p>This is a privileged operation that can only be invoked by an authorized user.</p> ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
            currentResult.setValue("since", "12.2.1.1.0");
            currentResult.setValue("restName", "asyncShutdown");
         }
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.1.0", (String)null, this.targetVersion)) {
         mth = JDBCOracleDataSourceRuntimeMBean.class.getMethod("resetStatistics");
         methodKey = BeanInfoHelper.buildMethodKey(mth);
         if (!descriptors.containsKey(methodKey)) {
            currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
            currentResult.setValue("since", "12.2.1.1.0");
            descriptors.put(methodKey, currentResult);
            currentResult.setValue("description", "Reset statistics counters to zero. ");
            currentResult.setValue("role", "operation");
            currentResult.setValue("rolePermitAll", Boolean.TRUE);
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
