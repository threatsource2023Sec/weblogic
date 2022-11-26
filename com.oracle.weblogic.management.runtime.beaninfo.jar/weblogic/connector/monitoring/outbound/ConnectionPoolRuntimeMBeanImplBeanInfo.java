package weblogic.connector.monitoring.outbound;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.logging.LogRuntimeBeanInfo;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;

public class ConnectionPoolRuntimeMBeanImplBeanInfo extends LogRuntimeBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorConnectionPoolRuntimeMBean.class;

   public ConnectionPoolRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionPoolRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.outbound.ConnectionPoolRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "6.1.0.0");
      beanDescriptor.setValue("package", "weblogic.connector.monitoring.outbound");
      String description = (new String("<p>This class is used for monitoring a WebLogic Connector Connection Pool</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveConnectionsCurrentCount")) {
         getterName = "getActiveConnectionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveConnectionsCurrentCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current total active connections.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ActiveConnectionsHighCount")) {
         getterName = "getActiveConnectionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveConnectionsHighCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsHighCount", currentResult);
         currentResult.setValue("description", "<p>The high water mark of active connections in this Connector Pool since the pool was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AverageActiveUsage")) {
         getterName = "getAverageActiveUsage";
         setterName = null;
         currentResult = new PropertyDescriptor("AverageActiveUsage", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AverageActiveUsage", currentResult);
         currentResult.setValue("description", "<p>The running average usage of created connections that are active in the Connector Pool since the pool was last shrunk.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CapacityIncrement")) {
         getterName = "getCapacityIncrement";
         setterName = null;
         currentResult = new PropertyDescriptor("CapacityIncrement", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CapacityIncrement", currentResult);
         currentResult.setValue("description", "<p>The initial capacity configured for this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CloseCount")) {
         getterName = "getCloseCount";
         setterName = null;
         currentResult = new PropertyDescriptor("CloseCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CloseCount", currentResult);
         currentResult.setValue("description", "<p>The number of connections that were closed for the connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryClassName")) {
         getterName = "getConnectionFactoryClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactoryClassName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionFactoryClassName", currentResult);
         currentResult.setValue("description", "<p>The ConnectionFactoryName of this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryName")) {
         getterName = "getConnectionFactoryName";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactoryName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionFactoryName", currentResult);
         currentResult.setValue("description", "<p>For 1.0 link-ref resource adapters only, the base resource adapter's connection factory name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionIdleProfileCount")) {
         getterName = "getConnectionIdleProfileCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionIdleProfileCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionIdleProfileCount", currentResult);
         currentResult.setValue("description", "<p>The number of Idle connection profiles stored for this pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionIdleProfiles")) {
         getterName = "getConnectionIdleProfiles";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionIdleProfiles", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionIdleProfiles", currentResult);
         currentResult.setValue("description", "<p>An array of count LeakProfiles starting at the passed index, in the entire array of Idle profiles.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionLeakProfileCount")) {
         getterName = "getConnectionLeakProfileCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionLeakProfileCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionLeakProfileCount", currentResult);
         currentResult.setValue("description", "<p>The number of Leak connection profiles stored for this pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionLeakProfiles")) {
         getterName = "getConnectionLeakProfiles";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionLeakProfiles", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionLeakProfiles", currentResult);
         currentResult.setValue("description", "<p>An array of count LeakProfiles</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionProfilingEnabled")) {
         getterName = "getConnectionProfilingEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionProfilingEnabled", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionProfilingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether connection profiling is enabled for this pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      String[] seeObjectArray;
      if (!descriptors.containsKey("Connections")) {
         getterName = "getConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("Connections", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Connections", currentResult);
         currentResult.setValue("description", "<p>An array of <code>ConnectorConnectionRuntimeMBeans</code> that each represents the statistics for a Connector Connection.</p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.management.runtime.ConnectorConnectionRuntimeMBean")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsCreatedTotalCount")) {
         getterName = "getConnectionsCreatedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsCreatedTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsCreatedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of Connector connections created in this Connector Pool since the pool is instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsDestroyedByErrorTotalCount")) {
         getterName = "getConnectionsDestroyedByErrorTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsDestroyedByErrorTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsDestroyedByErrorTotalCount", currentResult);
         currentResult.setValue("description", "<p>Return the number of connections that were destroyed because an error event was received.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsDestroyedByShrinkingTotalCount")) {
         getterName = "getConnectionsDestroyedByShrinkingTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsDestroyedByShrinkingTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsDestroyedByShrinkingTotalCount", currentResult);
         currentResult.setValue("description", "<p>Return the number of connections that were destroyed as a result of shrinking.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsDestroyedTotalCount")) {
         getterName = "getConnectionsDestroyedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsDestroyedTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsDestroyedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of Connector connections destroyed in this Connector Pool since the pool is instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsMatchedTotalCount")) {
         getterName = "getConnectionsMatchedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsMatchedTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsMatchedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of times a request for a Connector connections was satisfied via the use of an existing created connection since the pool is instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsRejectedTotalCount")) {
         getterName = "getConnectionsRejectedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsRejectedTotalCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsRejectedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of rejected requests for a Connector connections in this Connector Pool since the pool is instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectorEisType")) {
         getterName = "getConnectorEisType";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectorEisType", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectorEisType", currentResult);
         currentResult.setValue("description", "<p>The EIS type of this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentCapacity")) {
         getterName = "getCurrentCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentCapacity", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentCapacity", currentResult);
         currentResult.setValue("description", "<p>The PoolSize of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EISResourceId")) {
         getterName = "getEISResourceId";
         setterName = null;
         currentResult = new PropertyDescriptor("EISResourceId", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EISResourceId", currentResult);
         currentResult.setValue("description", "<p>The EISResourceId of this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreeConnectionsCurrentCount")) {
         getterName = "getFreeConnectionsCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FreeConnectionsCurrentCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FreeConnectionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current total free connections.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreeConnectionsHighCount")) {
         getterName = "getFreeConnectionsHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("FreeConnectionsHighCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FreeConnectionsHighCount", currentResult);
         currentResult.setValue("description", "<p>The high water mark of free connections in this Connector Pool since the pool was instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreePoolSizeHighWaterMark")) {
         getterName = "getFreePoolSizeHighWaterMark";
         setterName = null;
         currentResult = new PropertyDescriptor("FreePoolSizeHighWaterMark", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FreePoolSizeHighWaterMark", currentResult);
         currentResult.setValue("description", "<p>The FreePoolSizeHighWaterMark of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FreePoolSizeLowWaterMark")) {
         getterName = "getFreePoolSizeLowWaterMark";
         setterName = null;
         currentResult = new PropertyDescriptor("FreePoolSizeLowWaterMark", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("FreePoolSizeLowWaterMark", currentResult);
         currentResult.setValue("description", "<p>The FreePoolSizeLowWaterMark of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.1.2", (String)null, this.targetVersion) && !descriptors.containsKey("HealthState")) {
         getterName = "getHealthState";
         setterName = null;
         currentResult = new PropertyDescriptor("HealthState", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HealthState", currentResult);
         currentResult.setValue("description", "<p>The HealthState mbean for the application. </p> ");
         seeObjectArray = new String[]{BeanInfoHelper.encodeEntities("weblogic.health.HealthState")};
         currentResult.setValue("see", seeObjectArray);
         currentResult.setValue("owner", "");
         currentResult.setValue("since", "12.1.2");
      }

      if (!descriptors.containsKey("HighestNumWaiters")) {
         getterName = "getHighestNumWaiters";
         setterName = null;
         currentResult = new PropertyDescriptor("HighestNumWaiters", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HighestNumWaiters", currentResult);
         currentResult.setValue("description", "<p>Gets the highest number of waiters.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialCapacity")) {
         getterName = "getInitialCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("InitialCapacity", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InitialCapacity", currentResult);
         currentResult.setValue("description", "<p>The initial capacity configured for this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JNDIName")) {
         getterName = "getJNDIName";
         setterName = null;
         currentResult = new PropertyDescriptor("JNDIName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("JNDIName", currentResult);
         currentResult.setValue("description", "<p>The configured JNDI Name for the Connection Factory using this Connector connection pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Key")) {
         getterName = "getKey";
         setterName = null;
         currentResult = new PropertyDescriptor("Key", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Key", currentResult);
         currentResult.setValue("description", "<p>The configured Key for the Connection Factory using this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastShrinkTime")) {
         getterName = "getLastShrinkTime";
         setterName = null;
         currentResult = new PropertyDescriptor("LastShrinkTime", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastShrinkTime", currentResult);
         currentResult.setValue("description", "<p>Return the last time that the pool was shrunk.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFileName")) {
         getterName = "getLogFileName";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFileName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogFileName", currentResult);
         currentResult.setValue("description", "<p>The Log File used by the Resource Adapter for this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogRuntime")) {
         getterName = "getLogRuntime";
         setterName = null;
         currentResult = new PropertyDescriptor("LogRuntime", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogRuntime", currentResult);
         currentResult.setValue("description", "<p>Get the RuntimeMBean that allows monitoring and control of the log file.</p> ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MCFClassName")) {
         getterName = "getMCFClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("MCFClassName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MCFClassName", currentResult);
         currentResult.setValue("description", "Get the MCF class name. ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedConnectionFactoryClassName")) {
         getterName = "getManagedConnectionFactoryClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedConnectionFactoryClassName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedConnectionFactoryClassName", currentResult);
         currentResult.setValue("description", "<p>The ManagedConnectionFactoryName of this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxCapacity")) {
         getterName = "getMaxCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxCapacity", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxCapacity", currentResult);
         currentResult.setValue("description", "<p>The maximum capacity configured for this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIdleTime")) {
         getterName = "getMaxIdleTime";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxIdleTime", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxIdleTime", currentResult);
         currentResult.setValue("description", "<p>The configured MaxIdle time for this pool</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumUnavailableCurrentCount")) {
         getterName = "getNumUnavailableCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("NumUnavailableCurrentCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumUnavailableCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Return the number of unavailable connections.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumUnavailableHighCount")) {
         getterName = "getNumUnavailableHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("NumUnavailableHighCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumUnavailableHighCount", currentResult);
         currentResult.setValue("description", "<p>Return the highest unavailable number of connections at any given time.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumWaiters")) {
         getterName = "getNumWaiters";
         setterName = null;
         currentResult = new PropertyDescriptor("NumWaiters", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumWaiters", currentResult);
         currentResult.setValue("description", "<p>Gets the current number of waiters.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumWaitersCurrentCount")) {
         getterName = "getNumWaitersCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("NumWaitersCurrentCount", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumWaitersCurrentCount", currentResult);
         currentResult.setValue("description", "<p>Return the number of waiters.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberDetectedIdle")) {
         getterName = "getNumberDetectedIdle";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberDetectedIdle", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumberDetectedIdle", currentResult);
         currentResult.setValue("description", "<p>The total number of idle connections detected in the life time of this pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumberDetectedLeaks")) {
         getterName = "getNumberDetectedLeaks";
         setterName = null;
         currentResult = new PropertyDescriptor("NumberDetectedLeaks", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumberDetectedLeaks", currentResult);
         currentResult.setValue("description", "<p>The total number of leaked connections detected in the life time of this pool.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolName")) {
         getterName = "getPoolName";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolName", currentResult);
         currentResult.setValue("description", "<p>The configured Logical Name for the Connection Factory using this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolSizeHighWaterMark")) {
         getterName = "getPoolSizeHighWaterMark";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolSizeHighWaterMark", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolSizeHighWaterMark", currentResult);
         currentResult.setValue("description", "<p>The PoolSizeHighWaterMark of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PoolSizeLowWaterMark")) {
         getterName = "getPoolSizeLowWaterMark";
         setterName = null;
         currentResult = new PropertyDescriptor("PoolSizeLowWaterMark", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("PoolSizeLowWaterMark", currentResult);
         currentResult.setValue("description", "<p>The PoolSizeLowWaterMark of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RecycledTotal")) {
         getterName = "getRecycledTotal";
         setterName = null;
         currentResult = new PropertyDescriptor("RecycledTotal", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RecycledTotal", currentResult);
         currentResult.setValue("description", "<p>The total number of Connector connections that have been recycled in this Connector Pool since the pool is instantiated.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResourceAdapterLinkRefName")) {
         getterName = "getResourceAdapterLinkRefName";
         setterName = null;
         currentResult = new PropertyDescriptor("ResourceAdapterLinkRefName", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ResourceAdapterLinkRefName", currentResult);
         currentResult.setValue("description", "<p>The Resource Adapter Link Reference for cases where this Connection Factory refers to an existing Resource Adapter deployment.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RuntimeTransactionSupport")) {
         getterName = "getRuntimeTransactionSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("RuntimeTransactionSupport", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("RuntimeTransactionSupport", currentResult);
         currentResult.setValue("description", "<p>The real transaction support level in use at runtime.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkCountDownTime")) {
         getterName = "getShrinkCountDownTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ShrinkCountDownTime", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ShrinkCountDownTime", currentResult);
         currentResult.setValue("description", "<p>The amount of time left (in minutes) until an attempt to shrink the pool will be made.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkPeriodMinutes")) {
         getterName = "getShrinkPeriodMinutes";
         setterName = null;
         currentResult = new PropertyDescriptor("ShrinkPeriodMinutes", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ShrinkPeriodMinutes", currentResult);
         currentResult.setValue("description", "<p>The Shrink Period (in minutes) of this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p>Get the state of the pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionSupport")) {
         getterName = "getTransactionSupport";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionSupport", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionSupport", currentResult);
         currentResult.setValue("description", "<p>The static transaction support level, either configured in ra.xml or in @Conector annotation, for the Resource Adapter for this Connector connection pool.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (BeanInfoHelper.isVersionCompliant("12.2.1.0.0", (String)null, this.targetVersion) && !descriptors.containsKey("LogFileStreamOpened")) {
         getterName = "isLogFileStreamOpened";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFileStreamOpened", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LogFileStreamOpened", currentResult);
         currentResult.setValue("description", "Gets the opened state of the log file stream represented by this instance. ");
         currentResult.setValue("since", "12.2.1.0.0");
      }

      if (!descriptors.containsKey("LoggingEnabled")) {
         getterName = "isLoggingEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("LoggingEnabled", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LoggingEnabled", currentResult);
         currentResult.setValue("description", "<p>Indicates whether logging is enabled for this Connector connection pool.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProxyOn")) {
         getterName = "isProxyOn";
         setterName = null;
         currentResult = new PropertyDescriptor("ProxyOn", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ProxyOn", currentResult);
         currentResult.setValue("description", "<p>Return a flag indicating if the proxy is on. Returns true if it is.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ShrinkingEnabled")) {
         getterName = "isShrinkingEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("ShrinkingEnabled", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ShrinkingEnabled", currentResult);
         currentResult.setValue("description", "<p>Specifies whether shrinking of this Connector connection pool is enabled.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Testable")) {
         getterName = "isTestable";
         setterName = null;
         currentResult = new PropertyDescriptor("Testable", ConnectorConnectionPoolRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Testable", currentResult);
         currentResult.setValue("description", "<p>This indicates whether the connection pool is testable or not.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
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
      Method mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("forceLogRotation");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      String[] throwsObjectArray;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If there is an error during the log file rotation.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Forces the rotation of the underlying log immediately. ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("ensureLogOpened");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If the log could not be opened successfully.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Ensures that that the output stream to the underlying is opened if it got closed previously due to errors. ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("flushLog");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException If the log could not be flushed successfully.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "Flushes the buffer to the log file on disk. ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("getConnectionIdleProfiles", Integer.TYPE, Integer.TYPE);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("index", "The starting index of the of the idle profiles. "), createParameterDescriptor("count", "The number of idle profiles needed from the index. ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>An array of count LeakProfiles starting at the passed index, in the entire array of Idle profiles.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("getConnectionLeakProfiles", Integer.TYPE, Integer.TYPE);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("index", "The starting index of the of the leak profiles. "), createParameterDescriptor("count", "The number of leak profiles needed from the index. ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         currentResult.setValue("deprecated", " ");
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>An array of count LeakProfiles starting at the passed index, in the entire array of Leak profiles.</p> ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("testPool");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Test all the available connections in the pool. Returns true if all the connections passed the test and false it at least one failed the test.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("forceReset");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException            If any error occurs during resetting the pool.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Force immediately discard all used/unused connections and recreate connection pool (and using new configuration if user update the pool's configuration). </p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionPoolRuntimeMBean.class.getMethod("reset");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         throwsObjectArray = new String[]{BeanInfoHelper.encodeEntities("ManagementException            If any error occurs during resetting the pool.")};
         currentResult.setValue("throws", throwsObjectArray);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p> Reset connection pool Discard all unused connections and recreate connection pool (and using new configuration if user update the pool's configuration) if no connection from pool is reserved by client application. If any connection from the connection pool is currently in use, the operation fails and false will be returned, otherwise all connections will be reset and true will be returned. </p> ");
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
