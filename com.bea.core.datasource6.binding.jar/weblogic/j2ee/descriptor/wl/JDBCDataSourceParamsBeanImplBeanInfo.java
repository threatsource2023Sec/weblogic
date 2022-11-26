package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class JDBCDataSourceParamsBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCDataSourceParamsBean.class;

   public JDBCDataSourceParamsBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public JDBCDataSourceParamsBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBeanImpl");
      } catch (Throwable var5) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("configurable", Boolean.TRUE);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor.wl");
      String description = (new String("Contains the basic usage parameters of a data source.  <p> Configuration parameters for the basic usage of a data source are specified using a data source parameters bean. </p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      String[] roleObjectArray = new String[]{BeanInfoHelper.encodeEntities("Deployer")};
      beanDescriptor.setValue("rolesAllowed", roleObjectArray);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.wl.JDBCDataSourceParamsBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AlgorithmType")) {
         getterName = "getAlgorithmType";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAlgorithmType";
         }

         currentResult = new PropertyDescriptor("AlgorithmType", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("AlgorithmType", currentResult);
         currentResult.setValue("description", "<p>The algorithm determines the connection request processing for the multi data source.</p>  <p>You can specify one of the following algorithm types:</p>  <ul> <li><b>Failover</b>  <p>Connection requests are sent to the first data source in the list; if the request fails, the request is sent to the next data source in the list, and so forth. The process is repeated until a valid connection is obtained, or until the end of the list is reached, in which case an exception is thrown.</p> </li>  <li><b>Load balancing</b>  <p>The multi data source distributes connection requests evenly to its member data sources. With this algorithm, the multi data source also provides failover processing. That is, if a request fails, the multi data source sends the request to the next data source in the list until a valid connection is obtained, or until the end of the list is reached, in which case an exception is thrown.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Failover");
         currentResult.setValue("legalValues", new Object[]{"Load-Balancing", "Failover"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("ConnectionPoolFailoverCallbackHandler")) {
         getterName = "getConnectionPoolFailoverCallbackHandler";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPoolFailoverCallbackHandler";
         }

         currentResult = new PropertyDescriptor("ConnectionPoolFailoverCallbackHandler", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("ConnectionPoolFailoverCallbackHandler", currentResult);
         currentResult.setValue("description", "<p>The name of the application class to handle the callback sent when a multi data source is ready to failover or fail back connection requests to another data source within the multi data source.</p>  <p>The name must be the absolute name of an application class that implements the <code>weblogic.jdbc.extensions.ConnectionPoolFailoverCallback</code> interface.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jdbc.extensions.ConnectionPoolFailoverCallback")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataSourceList")) {
         getterName = "getDataSourceList";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataSourceList";
         }

         currentResult = new PropertyDescriptor("DataSourceList", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("DataSourceList", currentResult);
         currentResult.setValue("description", "<p>The list of data sources to which the multi data source will route connection requests. The order of data sources in the list determines the failover order.</p> ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GlobalTransactionsProtocol")) {
         getterName = "getGlobalTransactionsProtocol";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setGlobalTransactionsProtocol";
         }

         currentResult = new PropertyDescriptor("GlobalTransactionsProtocol", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("GlobalTransactionsProtocol", currentResult);
         currentResult.setValue("description", "<p>Determines the transaction protocol (global transaction processing behavior) for the data source. Options include:</p>  <ul> <li> <p>TwoPhaseCommit - Standard XA transaction processing. Requires an XA driver.</p> </li>  <li> <p>LoggingLastResource - A performance enhancement for one non-XA resource.</p> </li>  <li> <p>EmulateTwoPhaseCommit - Enables one non-XA resource to participate in a global transaction, but has some risk to data.</p> </li>  <li> <p>OnePhaseCommit - One-phase XA transaction processing using a non-XA driver. This is the default setting.</p> </li>  <li> <p>None - Support for local transactions only.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "OnePhaseCommit");
         currentResult.setValue("legalValues", new Object[]{"TwoPhaseCommit", "LoggingLastResource", "EmulateTwoPhaseCommit", "OnePhaseCommit", "None"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDINames")) {
         getterName = "getJNDINames";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setJNDINames";
         }

         currentResult = new PropertyDescriptor("JNDINames", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("JNDINames", currentResult);
         currentResult.setValue("description", "<p>The JNDI path for this Data Source. By default, the JNDI name is the name of the data source.</p>  <p>Applications that look up the JNDI path get a <code>javax.sql.DataSource</code> instance that corresponds to this data source. </p> ");
         currentResult.setValue("restDerivedDefault", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ProxySwitchingCallback")) {
         getterName = "getProxySwitchingCallback";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProxySwitchingCallback";
         }

         currentResult = new PropertyDescriptor("ProxySwitchingCallback", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("ProxySwitchingCallback", currentResult);
         currentResult.setValue("description", "<p>The name of the switching callback class for a Proxy data source.</p> <p>This class implements the <code>weblogic.jdbc.extensions.DataSourceSwitchingCallback</code> interface.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jdbc.extensions.DataSourceSwitchingCallback")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("ProxySwitchingProperties")) {
         getterName = "getProxySwitchingProperties";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setProxySwitchingProperties";
         }

         currentResult = new PropertyDescriptor("ProxySwitchingProperties", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("ProxySwitchingProperties", currentResult);
         currentResult.setValue("description", "Specifies the switching properties passed to the switching callback method for a Proxy data source. ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.jdbc.extensions.DataSourceSwitchingCallback")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("RowPrefetchSize")) {
         getterName = "getRowPrefetchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRowPrefetchSize";
         }

         currentResult = new PropertyDescriptor("RowPrefetchSize", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("RowPrefetchSize", currentResult);
         currentResult.setValue("description", "<p>If row prefetching is enabled, specifies the number of result set rows to prefetch for a client.</p>  <p>This parameter applies only to the deprecated JDBC over RMI.</p>  <p>The optimal prefetch size depends on the particulars of the query. In general, increasing this number will increase performance, until a particular value is reached. At that point further increases do not result in any significant performance increase. Very rarely will increased performance result from exceeding 100 rows. The default value should be reasonable for most situations.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(48));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(2));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Scope")) {
         getterName = "getScope";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setScope";
         }

         currentResult = new PropertyDescriptor("Scope", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("Scope", currentResult);
         currentResult.setValue("description", "<p>Specifies the scoping of the data source.</p>  <p>You can specify one of the following scopes:</p>  <ul> <li><b>Global</b>  <p>Specifies that the data source is bound in the cluster-wide JNDI tree with the JNDIName specified so that the data source is available for use to any JDBC client across the cluster.</p>  <p>This is the default setting.</p> </li>  <li><b>Application</b>  <p>Specifies that the data source is bound in the application's local namespace with the JNDIName specified so that the data source is available for use only by JDBC clients within the application.  This can only be used for packaged datasources and is ignored for JDBC System resources.</p> </li> </ul> ");
         setPropertyDescriptorDefault(currentResult, "Global");
         currentResult.setValue("legalValues", new Object[]{"Global", "Application"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StreamChunkSize")) {
         getterName = "getStreamChunkSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setStreamChunkSize";
         }

         currentResult = new PropertyDescriptor("StreamChunkSize", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("StreamChunkSize", currentResult);
         currentResult.setValue("description", "<p>Specifies the data chunk size for steaming data types.</p>  <p>This parameter applies only to the deprecated JDBC over RMI.</p>  <p>Streaming data types (for example resulting from a call to <code>getBinaryStream()</code>) are sent in sized chunks from WebLogic Server to the client as needed.</p> ");
         setPropertyDescriptorDefault(currentResult, new Integer(256));
         currentResult.setValue("legalMax", new Integer(65536));
         currentResult.setValue("legalMin", new Integer(1));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FailoverRequestIfBusy")) {
         getterName = "isFailoverRequestIfBusy";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFailoverRequestIfBusy";
         }

         currentResult = new PropertyDescriptor("FailoverRequestIfBusy", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("FailoverRequestIfBusy", currentResult);
         currentResult.setValue("description", "<p>For multi data sources with the <code>failover</code> algorithm, enables the multi data source to failover connection requests to the next data source if all connections in the current data source are in use.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepConnAfterGlobalTx")) {
         getterName = "isKeepConnAfterGlobalTx";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepConnAfterGlobalTx";
         }

         currentResult = new PropertyDescriptor("KeepConnAfterGlobalTx", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("KeepConnAfterGlobalTx", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to keep the physical database connection associated with the logical connection when committing a global transaction instead releasing it and getting another physical connection when needed.</p>  <p>Setting this option to true may require additional connections to be configured on the database.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KeepConnAfterLocalTx")) {
         getterName = "isKeepConnAfterLocalTx";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setKeepConnAfterLocalTx";
         }

         currentResult = new PropertyDescriptor("KeepConnAfterLocalTx", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("KeepConnAfterLocalTx", currentResult);
         currentResult.setValue("description", "<p>Enables WebLogic Server to keep the physical database connection associated with the logical connection when committing a local transaction instead releasing it and getting another physical connection when needed.</p>  <p>Setting this option to true may require additional connections to be configured on the database.</p>  <p>Use this setting to work around specific problems with JDBC XA drivers.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("deprecated", "10.3.4.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RowPrefetch")) {
         getterName = "isRowPrefetch";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRowPrefetch";
         }

         currentResult = new PropertyDescriptor("RowPrefetch", JDBCDataSourceParamsBean.class, getterName, setterName);
         descriptors.put("RowPrefetch", currentResult);
         currentResult.setValue("description", "<p>Enables multiple rows to be \"prefetched\" (that is, sent from the server to the client) in one server access.</p>  <p>This parameter applies only to the deprecated JDBC over RMI.</p>  <p>When an external client accesses a database using JDBC through WebLogic Server, row prefetching improves performance by fetching multiple rows from the server to the client in one server access. WebLogic Server ignores this setting and does not use row prefetching when the client and WebLogic Server are in the same JVM.</p> ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("deprecated", "12.2.1.0.0 ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = JDBCDataSourceParamsBean.class.getMethod("addJNDIName", String.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jndiName", (String)null)};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Used to specify additional JNDI names for the Data Source. WebLogic Server internally defaults the JNDI name to the name of the data source bean.</p> ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "JNDINames");
      }

      mth = JDBCDataSourceParamsBean.class.getMethod("removeJNDIName", String.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("jndiName", (String)null)};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "collection");
         currentResult.setValue("property", "JNDINames");
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
