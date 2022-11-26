package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCConnectionPoolParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCConnectionPoolParamsBean.class;

   public JDBCConnectionPoolParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCConnectionPoolParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("<p>Contains the connection pool parameters of a data source.</p>  <p>Configuration parameters for a data source's connection pool are specified using the connection pool parameters bean.</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCConnectionPoolParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("CapacityIncrement")) {
         getterName = "getCapacityIncrement";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCapacityIncrement";
         }

         currentResult = new PropertyDescriptor("CapacityIncrement", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("CapacityIncrement", currentResult);
         currentResult.setValue("description", "<p>The increment by which this JDBC connection pool's capacity is expanded. In WebLogic Server 10.3.1 and higher releases, the <code>capacityIncrement</code> is no longer configurable and is set to a value of 1.</p>  <p>When there are no more available physical connections to service requests, the connection pool will create this number of additional physical database connections and add them to the connection pool. The connection pool will ensure that it does not exceed the maximum number of physical connections.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "10.3.6.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionCreationRetryFrequencySeconds")) {
         getterName = "getConnectionCreationRetryFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionCreationRetryFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionCreationRetryFrequencySeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionCreationRetryFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between attempts to establish connections to the database.</p>  <p>If you do not set this value, data source creation fails if the database is unavailable. If set and if the database is unavailable when the data source is created, WebLogic Server will attempt to create connections in the pool again after the number of seconds you specify, and will continue to attempt to create the connections until it succeeds.</p>  <p>When set to <code>0</code>, connection retry is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionHarvestMaxCount")) {
         getterName = "getConnectionHarvestMaxCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionHarvestMaxCount";
         }

         currentResult = new PropertyDescriptor("ConnectionHarvestMaxCount", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionHarvestMaxCount", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connections that may be harvested when the connection harvesting occurs. The range of valid values is 1 to MaxCapacity.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionHarvestTriggerCount")) {
         getterName = "getConnectionHarvestTriggerCount";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionHarvestTriggerCount";
         }

         currentResult = new PropertyDescriptor("ConnectionHarvestTriggerCount", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionHarvestTriggerCount", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of available connections (trigger value) used to determine when connection harvesting occurs.</p> <ul><li>Harvesting occurs when the number of available connections is below the trigger value for a connection pool.</li> <li>The range of valid values is -1 to <code>MaxCapacity</code>.</li> <li>Default value is <code>-1</code>.</li> <li>Setting the value to <code>-1</code> disables connection harvesting.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionLabelingCallback")) {
         getterName = "getConnectionLabelingCallback";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionLabelingCallback";
         }

         currentResult = new PropertyDescriptor("ConnectionLabelingCallback", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionLabelingCallback", currentResult);
         currentResult.setValue("description", "<p>The class name of the connection labeling callback. This is automatically passed to registerConnectionLabelingCallback when the datasource is created. The class must implement <code>oracle.ucp.ConnectionLabelingCallback</code>.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionReserveTimeoutSeconds")) {
         getterName = "getConnectionReserveTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionReserveTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ConnectionReserveTimeoutSeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionReserveTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds after which a call to reserve a connection from the connection pool will timeout.</p> <p>When set to <code>0</code>, a call will never timeout.</p> <p>When set to <code>-1</code>, a call will timeout immediately.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CountOfRefreshFailuresTillDisable")) {
         getterName = "getCountOfRefreshFailuresTillDisable";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCountOfRefreshFailuresTillDisable";
         }

         currentResult = new PropertyDescriptor("CountOfRefreshFailuresTillDisable", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("CountOfRefreshFailuresTillDisable", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of reconnect failures allowed before WebLogic Server disables a connection pool to minimize the delay in handling the connection request caused by a database failure.  Zero means it is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CountOfTestFailuresTillFlush")) {
         getterName = "getCountOfTestFailuresTillFlush";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCountOfTestFailuresTillFlush";
         }

         currentResult = new PropertyDescriptor("CountOfTestFailuresTillFlush", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("CountOfTestFailuresTillFlush", currentResult);
         currentResult.setValue("description", "<p>Specifies the number of test failures allowed before WebLogic Server closes all unused connections in a connection pool to minimize the delay caused by further database testing.  Zero means it is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(2));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DriverInterceptor")) {
         getterName = "getDriverInterceptor";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDriverInterceptor";
         }

         currentResult = new PropertyDescriptor("DriverInterceptor", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("DriverInterceptor", currentResult);
         currentResult.setValue("description", "<p>Specifies the absolute name of the application class used to intercept method calls to the JDBC driver. The application specified must implement the weblogic.jdbc.extensions.DriverInterceptor interface. </p>  <p>Weblogic Server will invoke the preInvokeCallback(), postInvokeExceptionCallback(), and postInvokeCallback() methods of the registered application before and after invoking any method inside the JDBC driver. You can use this feature to profile JDBC driver usage and monitor:</p>  <ul> <li>Methods being executed</li> <li>Any exceptions thrown</li> <li>Time spent inside the driver executing methods</li> </ul> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FatalErrorCodes")) {
         getterName = "getFatalErrorCodes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFatalErrorCodes";
         }

         currentResult = new PropertyDescriptor("FatalErrorCodes", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("FatalErrorCodes", currentResult);
         currentResult.setValue("description", "<p>Specifies a comma-separated list of error codes that are treated as fatal errors. These errors include deployment errors that cause a server to fail to boot and connection errors that prevent a connection from being put back in the connection pool.</p>  <p>This optional attribute is used to define fatal error codes, that when specified as the exception code within a <code>SQLException</code> (retrieved by <code>sqlException.getErrorCode()</code>), indicate that a fatal error has occurred and the connection is no longer usable. For Oracle databases the following fatal error codes are predefined within WLS and do not need to be placed in the configuration file: </p>  <ul><li>3113: \"end-of-file on communication channel\" </li> <li>3114: \"not connected to ORACLE\" </li> <li>1033: \"ORACLE initialization or shutdown in progress\" </li> <li>1034: \"ORACLE not available\" </li> <li>1089: \"immediate shutdown in progress - no operations are permitted\"</li> <li>1090: \"shutdown in progress - connection is not permitted\" </li> <li>17002: \"I/O exception\" </li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setHighestNumWaiters";
         }

         currentResult = new PropertyDescriptor("HighestNumWaiters", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", "<p>The maximum number of connection requests that can concurrently block threads while waiting to reserve a connection from the data source's connection pool.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InactiveConnectionTimeoutSeconds")) {
         getterName = "getInactiveConnectionTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInactiveConnectionTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("InactiveConnectionTimeoutSeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("InactiveConnectionTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of inactive seconds on a reserved connection before WebLogic Server reclaims the connection and releases it back into the connection pool.</p>  <p>You can use the Inactive Connection Timeout feature to reclaim leaked connections - connections that were not explicitly closed by the application. Note that this feature is not intended to be used in place of properly closing connections.</p>  <p>When set to <code>0</code>, the feature is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitSql")) {
         getterName = "getInitSql";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitSql";
         }

         currentResult = new PropertyDescriptor("InitSql", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("InitSql", currentResult);
         currentResult.setValue("description", "<p>SQL statement to execute that will initialize newly created physical database connections. Start the statement with SQL followed by a space.</p>  <p>If the Init SQL value begins with <code>\"SQL \"</code>, then the rest of the string following that leading token will be taken as a literal SQL statement that will be used to initialize database connections. If the Init SQL value does not begin with \"SQL \", the value will be treated as the name of a table and the following SQL statement will be used to initialize connections:</p>  <p><code>\"select count(*) from InitSQL\"</code></p>  <p>The table <code>InitSQL</code> must exist and be accessible to the database user for the connection. Most database servers optimize this SQL to avoid a table scan, but it is still a good idea to set <code>InitSQL</code> to the name of a table that is known to have few rows, or even no rows.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialCapacity")) {
         getterName = "getInitialCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialCapacity";
         }

         currentResult = new PropertyDescriptor("InitialCapacity", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("InitialCapacity", currentResult);
         currentResult.setValue("description", "<p>The number of physical connections to create when creating the connection pool in the data source. If unable to create this number of connections, creation of the data source will fail.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCXADebugLevel")) {
         getterName = "getJDBCXADebugLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJDBCXADebugLevel";
         }

         currentResult = new PropertyDescriptor("JDBCXADebugLevel", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("JDBCXADebugLevel", currentResult);
         currentResult.setValue("description", "<p>Specifies level of JDBC debugging for XA drivers, where larger values in the range provide more debugging information. </p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(100));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginDelaySeconds")) {
         getterName = "getLoginDelaySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginDelaySeconds";
         }

         currentResult = new PropertyDescriptor("LoginDelaySeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("LoginDelaySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds to delay before creating each physical database connection. This delay supports database servers that cannot handle multiple connection requests in rapid succession.</p>  <p>The delay takes place both during initial data source creation and during the lifetime of the data source whenever a physical database connection is created.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxCapacity";
         }

         currentResult = new PropertyDescriptor("MaxCapacity", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "<p>The maximum number of physical connections that this connection pool can contain.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(15));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinCapacity")) {
         getterName = "getMinCapacity";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinCapacity";
         }

         currentResult = new PropertyDescriptor("MinCapacity", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("MinCapacity", currentResult);
         currentResult.setValue("description", "<p>The minimum number of physical connections that this connection pool can contain after it is initialized.</p> <ul> <li> Default: <a href=\"#InitialCapacity\">InitialCapacity</a></li> <li> Used only for connection pool shrinking calculations. </li> <li> For compatibility, <code>InitialCapacity</code> is used if <code>MinCapacity</code> is not configured.</li> <li> Once a data source has gone through a suspend/resume, the larger value of either <code>MinCapacity</code> or <code>InitialCapacity</code> is used.</li> </ul> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         setPropertyDescriptorDefault(currentResult, new Integer(1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ProfileConnectionLeakTimeoutSeconds")) {
         getterName = "getProfileConnectionLeakTimeoutSeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProfileConnectionLeakTimeoutSeconds";
         }

         currentResult = new PropertyDescriptor("ProfileConnectionLeakTimeoutSeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ProfileConnectionLeakTimeoutSeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds that a JDBC connection needs to be held by an application before triggering a connection leak diagnostic profiling record.</p>  <p>When set to <code>0</code>, the timeout is disabled.  This attribute only applies when the connection leak diagnostic profiling option is enabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("ProfileHarvestFrequencySeconds")) {
         getterName = "getProfileHarvestFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProfileHarvestFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ProfileHarvestFrequencySeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ProfileHarvestFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds between when WebLogic Server harvests profile data.</p>  <p>When set to <code>0</code>, harvesting of data is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(300));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProfileType")) {
         getterName = "getProfileType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProfileType";
         }

         currentResult = new PropertyDescriptor("ProfileType", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ProfileType", currentResult);
         currentResult.setValue("description", "<p>Specifies that type of profile data to be collected for the JDBC subsystem.</p>  <p>You can specify combinations of the following profile types:</p>  <ul> <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_USAGE - Profile threads currently using connections from the pool of connections in the data source. </li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_RESV_WAIT - Profile threads currently waiting to reserve a connection from the data source.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_LEAK - Profile threads that have reserved a connection from the data source and the connection leaked (was not properly returned to the pool of connections).</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_RESV_FAIL - Profile threads that attempt to reserve a connection from the data source but fail.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_STMT_CACHE_ENTRY - Profile prepared and callable statements added to the statement cache, and profile the threads that originated the cached statements.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_STMT_USAGE - Profile threads currently executing SQL statements from the statement cache.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_LAST_USAGE - Profile the previous thread that last used the connection. This information is useful when debugging problems with connections infected with pending transactions that cause subsequent XA operations on the connections to fail.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_MT_USAGE - Profile threads that erroneously use a connection previously obtained by a different thread.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_UNWRAP_USAGE - Profile threads that have obtained the JDBC delegate object by invoking unwrap or weblogic.common.wrapper.Wrapper.getVendorObj.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_CONN_LOCALTX_LEAK - Profile threads that close JDBC connections that have an active local database transaction.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_NONE - Disable profiling for the data source.</li>  <li>weblogic.jdbc.common.internal.JDBCConstants.PROFILE_TYPE_ALL - Enable all profile types for the data source.</li>  </ul> ");
         setPropertyDescriptorDefault(currentResult, new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SecondsToTrustAnIdlePoolConnection")) {
         getterName = "getSecondsToTrustAnIdlePoolConnection";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSecondsToTrustAnIdlePoolConnection";
         }

         currentResult = new PropertyDescriptor("SecondsToTrustAnIdlePoolConnection", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("SecondsToTrustAnIdlePoolConnection", currentResult);
         currentResult.setValue("description", "<p>The number of seconds within a connection use that WebLogic Server trusts that the connection is still viable and will skip the connection test, either before delivering it to an application or during the periodic connection testing process.</p>  <p>This option is an optimization that minimizes the performance impact of connection testing, especially during heavy traffic.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkFrequencySeconds")) {
         getterName = "getShrinkFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setShrinkFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("ShrinkFrequencySeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("ShrinkFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds to wait before shrinking a connection pool that has incrementally increased to meet demand.</p>  <p>When set to <code>0</code>, shrinking is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(900));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatementCacheSize")) {
         getterName = "getStatementCacheSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStatementCacheSize";
         }

         currentResult = new PropertyDescriptor("StatementCacheSize", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("StatementCacheSize", currentResult);
         currentResult.setValue("description", "<p>The number of prepared and callable statements stored in the cache. (This may increase server performance.)</p>  <p>WebLogic Server can reuse statements in the cache without reloading the statements, which can increase server performance. Each connection in the connection pool has its own cache of statements.</p>  <p>Setting the size of the statement cache to 0 turns off statement caching.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(10));
         currentResult.setValue("legalMax", new Integer(1024));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatementCacheType")) {
         getterName = "getStatementCacheType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStatementCacheType";
         }

         currentResult = new PropertyDescriptor("StatementCacheType", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("StatementCacheType", currentResult);
         currentResult.setValue("description", "<p>The algorithm used for maintaining the prepared statements stored in the statement cache.</p>  <p>Options are: </p> <ul> <li>LRU - when a new prepared or callable statement is used, the least recently used statement is replaced in the cache.</li> <li>FIXED - the first fixed number of prepared and callable statements are cached.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "LRU");
         currentResult.setValue("legalValues", new Object[]{"LRU", "FIXED"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StatementTimeout")) {
         getterName = "getStatementTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStatementTimeout";
         }

         currentResult = new PropertyDescriptor("StatementTimeout", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("StatementTimeout", currentResult);
         currentResult.setValue("description", "<p>The time after which a statement currently being executed will time out.</p>  <p>StatementTimeout relies on underlying JDBC driver support. WebLogic Server passes the time specified to the JDBC driver using the <code>java.sql.Statement.setQueryTimeout()</code> method. If your JDBC driver does not support this method, it may throw an exception and the timeout value is ignored.</p>  <p>A value of <code>-1</code> disables this feature.</p> <p>A value of <code>0</code> means that statements will not time out.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestFrequencySeconds")) {
         getterName = "getTestFrequencySeconds";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestFrequencySeconds";
         }

         currentResult = new PropertyDescriptor("TestFrequencySeconds", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestFrequencySeconds", currentResult);
         currentResult.setValue("description", "<p>The number of seconds a WebLogic Server instance waits between attempts when testing unused connections. (Requires that you specify a Test Table Name.) Connections that fail the test are closed and reopened to re-establish a valid physical connection. If the test fails again, the connection is closed.</p>  <p>In the context of multi data sources, this attribute controls the frequency at which WebLogic Server checks the health of data sources it had previously marked as unhealthy.</p>  <p>When set to <code>0</code>, the feature is disabled.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(120));
         currentResult.setValue("legalMax", new Integer(Integer.MAX_VALUE));
         currentResult.setValue("legalMin", new Integer(0));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestTableName")) {
         getterName = "getTestTableName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestTableName";
         }

         currentResult = new PropertyDescriptor("TestTableName", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestTableName", currentResult);
         currentResult.setValue("description", "<p>The name of the database table to use when testing physical database connections. This name is required when you specify a Test Frequency and enable Test Reserved Connections.</p>  <p>The default SQL code used to test a connection is <code>select count(*) from TestTableName</code></p>  <p>Most database servers optimize this SQL to avoid a table scan, but it is still a good idea to set the Test Table Name to the name of a table that is known to have few rows, or even no rows.</p>  <p>If the Test Table Name begins with <code>SQL</code>, then the rest of the string following that leading token will be taken as a literal SQL statement that will be used to test connections instead of the standard query. For example: <code>SQL BEGIN; Null; END; </code></p>  <p>For an Oracle database, you can reduce the overhead of connection testing by setting Test Table Name to <code>SQL PINGDATABASE</code> which uses the <code>pingDatabase()</code> method to test the Oracle connection. For any JDBC 4.0 database, it is possible to use \"SQL ISVALID\" to use the <code>isValid()</code> method on the connection.</p> ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CredentialMappingEnabled")) {
         getterName = "isCredentialMappingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setCredentialMappingEnabled";
         }

         currentResult = new PropertyDescriptor("CredentialMappingEnabled", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("CredentialMappingEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables Set Client ID on connection for the data source. When an application requests a database connection, WebLogic Server sets a light-weight client ID on the database connection.</p>  <p>By default, it uses the credential mapping to map WebLogic Server user IDs to database user IDs. However, if use-database-credentials is set to true, then the credential mapping is not done and the ID is used directly as a database user ID.</p>  <p>It is currently supported for IBM DB2 driver and Oracle thin driver. Support for this feature will be dropped in a future Oracle thin driver release. Oracle recommends using proxy authentication instead of this feature.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IdentityBasedConnectionPoolingEnabled")) {
         getterName = "isIdentityBasedConnectionPoolingEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIdentityBasedConnectionPoolingEnabled";
         }

         currentResult = new PropertyDescriptor("IdentityBasedConnectionPoolingEnabled", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("IdentityBasedConnectionPoolingEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables identity-based-connection-pooling for the data source. When an application requests a database connection, WebLogic Server picks or creates a physical connection with requested DBMS identity based on a map of WebLogic user IDs and database IDs.</p>  <p>You must also specify the map of WebLogic Server user IDs to database user IDs (credential mapping).</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreInUseConnectionsEnabled")) {
         getterName = "isIgnoreInUseConnectionsEnabled";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreInUseConnectionsEnabled";
         }

         currentResult = new PropertyDescriptor("IgnoreInUseConnectionsEnabled", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("IgnoreInUseConnectionsEnabled", currentResult);
         currentResult.setValue("description", "<p>Enables the data source to be shutdown even if connections obtained from the pool are still in use.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("14.1.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("InvokeBeginEndRequest")) {
         getterName = "isInvokeBeginEndRequest";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInvokeBeginEndRequest";
         }

         currentResult = new PropertyDescriptor("InvokeBeginEndRequest", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("InvokeBeginEndRequest", currentResult);
         currentResult.setValue("description", "<p>When true, and when a JDBC 4.3 compatible driver is used, the connection pool will invoke Connection.beginRequest() prior to returning the connection to the application, and will invoke Connection.endRequest() before the connection is released to the pool.</p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "14.1.1.0.0");
      }

      if (!descriptors.containsKey("PinnedToThread")) {
         getterName = "isPinnedToThread";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPinnedToThread";
         }

         currentResult = new PropertyDescriptor("PinnedToThread", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("PinnedToThread", currentResult);
         currentResult.setValue("description", "<p>Enables an option to improve performance by enabling execute threads to keep a pooled database connection even after the application closes the logical connection.</p>  <p>When enabled:</p> <ul> <li> WebLogic Server pins a database connection from the connection pool to an execution thread the first time an application uses the thread to reserve a connection. When the application finishes using the connection and calls <code>connection.close()</code>, WebLogic Server keeps the connection with the execute thread and does not return it to the connection pool. When an application subsequently requests a connection using the same execute thread, WebLogic Server provides the connection already reserved by the thread.</li>  <li>There is no locking contention on the connection pool that occurs when multiple threads attempt to reserve a connection at the same time. There is no contention for threads that attempt to reserve the same connection from a limited number of database connections.</li>  <li>If an application concurrently reserves more than one connection from the connection pool using the same execute thread, WebLogic Server creates additional database connections and pins them to the thread.</li>  <li>The maximum capacity of the connection pool (maximum number of database connections created in the connection pool) becomes the number of execute threads used to request a connection multiplied by the number of concurrent connections each thread reserves. This may exceed the <code>Maximum Capacity</code> specified for the connection pool. You may need to consider this larger number of connections in your system design and ensure that your database allows for additional associated resources. If your system cannot handle the additional resource requirements or if you see database resource errors after enabling <code>PinnedToThread</code>, Oracle recommends not using <code>PinnedToThread</code>. See <a href=\"http://www.oracle.com/pls/topic/lookup?ctx=wls14110&amp;id=JDBCA198\" rel=\"noopener noreferrer\" target=\"_blank\">Using Pinned-To-Thread Property to Increase Performance</a>.</li> </ul> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RemoveInfectedConnections")) {
         getterName = "isRemoveInfectedConnections";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRemoveInfectedConnections";
         }

         currentResult = new PropertyDescriptor("RemoveInfectedConnections", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("RemoveInfectedConnections", currentResult);
         currentResult.setValue("description", "<p>Specifies whether a connection will be removed from the connection pool after the application uses the underlying vendor connection object.</p>  <p>If you disable removing infected connections, you must make sure that the database connection is suitable for reuse by other applications.</p>  <p>When set to <code>true</code> (the default), the physical connection is not returned to the connection pool after the application closes the logical connection. Instead, the physical connection is closed and recreated.</p>  <p>When set to <code>false</code>, when the application closes the logical connection, the physical connection is returned to the connection pool and can be reused by the application or by another application.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TestConnectionsOnReserve")) {
         getterName = "isTestConnectionsOnReserve";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTestConnectionsOnReserve";
         }

         currentResult = new PropertyDescriptor("TestConnectionsOnReserve", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("TestConnectionsOnReserve", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to test a connection before giving it to a client. (Requires that you specify a Test Table Name.)</p>  <p>The test adds a small delay in serving the client's request for a connection from the pool, but ensures that the client receives a viable connection.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WrapJdbc")) {
         getterName = "isWrapJdbc";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWrapJdbc";
         }

         currentResult = new PropertyDescriptor("WrapJdbc", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("WrapJdbc", currentResult);
         currentResult.setValue("description", "<p>By default, SQL objects for <code>CallableStatement</code>, <code>PreparedStatement</code>, <code>ResultSet</code>, <code>Statement</code>, and <code>DatabaseMetaData</code> are wrapped with a WebLogic wrapper. Wrapping allows features like debugging and connection usage to be performed by the server.</p>  <p>When <code>false</code>, wrapping is disabled. This improves performance, in some cases significantly, and allows for the application to use the native driver objects directly. A value of <code>false</code> also disables data type wrapping.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WrapTypes")) {
         getterName = "isWrapTypes";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setWrapTypes";
         }

         currentResult = new PropertyDescriptor("WrapTypes", JDBCConnectionPoolParamsBean.class, getterName, setterName);
         descriptors.put("WrapTypes", currentResult);
         currentResult.setValue("description", "<p>By default, data type objects for Array, Blob, Clob, NClob, Ref, SQLXML, and Struct, plus ParameterMetaData and ResultSetMetaData objects are wrapped with a WebLogic wrapper.  This allows for features like debugging and connection usage to be done by the server.</p>  <p>The wrapping can be turned off by setting this value to false. This improves performance, in some cases significantly, and allows for the application to use the native driver objects directly.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
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
