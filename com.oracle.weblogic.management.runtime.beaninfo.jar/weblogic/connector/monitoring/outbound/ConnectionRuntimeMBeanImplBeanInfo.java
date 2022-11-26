package weblogic.connector.monitoring.outbound;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.runtime.ConnectorConnectionRuntimeMBean;

public class ConnectionRuntimeMBeanImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = ConnectorConnectionRuntimeMBean.class;

   public ConnectionRuntimeMBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public ConnectionRuntimeMBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.connector.monitoring.outbound.ConnectionRuntimeMBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.connector.monitoring.outbound");
      String description = (new String("<p>This class is used for monitoring individual WebLogic Connector connections</p> ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.ConnectorConnectionRuntimeMBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      Object setterName;
      if (!descriptors.containsKey("ActiveHandlesCurrentCount")) {
         getterName = "getActiveHandlesCurrentCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveHandlesCurrentCount", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveHandlesCurrentCount", currentResult);
         currentResult.setValue("description", "<p>The current total active connection handles for this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ActiveHandlesHighCount")) {
         getterName = "getActiveHandlesHighCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ActiveHandlesHighCount", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveHandlesHighCount", currentResult);
         currentResult.setValue("description", "<p>The high water mark of active connection handles for this connection since the connection was created.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryClassName")) {
         getterName = "getConnectionFactoryClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactoryClassName", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionFactoryClassName", currentResult);
         currentResult.setValue("description", "<p>Returns the connection factory class name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CreationDurationTime")) {
         getterName = "getCreationDurationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("CreationDurationTime", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CreationDurationTime", currentResult);
         currentResult.setValue("description", "<p>Return the time taken to create the connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EISProductName")) {
         getterName = "getEISProductName";
         setterName = null;
         currentResult = new PropertyDescriptor("EISProductName", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EISProductName", currentResult);
         currentResult.setValue("description", "Returns the EISProductName associated with the ManagedConnection's MetaData ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EISProductVersion")) {
         getterName = "getEISProductVersion";
         setterName = null;
         currentResult = new PropertyDescriptor("EISProductVersion", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("EISProductVersion", currentResult);
         currentResult.setValue("description", "<p>Returns the EISProductVersion associated with the ManagedConnection's MetaData.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HandlesCreatedTotalCount")) {
         getterName = "getHandlesCreatedTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("HandlesCreatedTotalCount", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("HandlesCreatedTotalCount", currentResult);
         currentResult.setValue("description", "<p>The total number of connection handles created for this connection since the connection was created.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastUsage")) {
         getterName = "getLastUsage";
         setterName = null;
         currentResult = new PropertyDescriptor("LastUsage", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastUsage", currentResult);
         currentResult.setValue("description", "<p>The last usage time stamp for the connection in milliseconds, as returned by <code>System.currentTimeMillis()</code>.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LastUsageString")) {
         getterName = "getLastUsageString";
         setterName = null;
         currentResult = new PropertyDescriptor("LastUsageString", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("LastUsageString", currentResult);
         currentResult.setValue("description", "<p>The last usage time stamp for the connection as a string.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ManagedConnectionFactoryClassName")) {
         getterName = "getManagedConnectionFactoryClassName";
         setterName = null;
         currentResult = new PropertyDescriptor("ManagedConnectionFactoryClassName", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ManagedConnectionFactoryClassName", currentResult);
         currentResult.setValue("description", "<p>Returns the managed connection factory class name.</p> ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxConnections")) {
         getterName = "getMaxConnections";
         setterName = null;
         currentResult = new PropertyDescriptor("MaxConnections", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("MaxConnections", currentResult);
         currentResult.setValue("description", "<p>Returns the MaxConnections associated with the ManagedConnection's MetaData</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReserveDurationTime")) {
         getterName = "getReserveDurationTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ReserveDurationTime", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReserveDurationTime", currentResult);
         currentResult.setValue("description", "<p>Get the time taken to reserve this connection.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReserveTime")) {
         getterName = "getReserveTime";
         setterName = null;
         currentResult = new PropertyDescriptor("ReserveTime", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReserveTime", currentResult);
         currentResult.setValue("description", "<p>Return the last time the connection was reserved.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StackTrace")) {
         getterName = "getStackTrace";
         setterName = null;
         currentResult = new PropertyDescriptor("StackTrace", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("StackTrace", currentResult);
         currentResult.setValue("description", "<p>The stack trace for the current connection, which will be blank unless connection-profiling-enabled is set to true in weblogic-ra.xml</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("unharvestable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionId")) {
         getterName = "getTransactionId";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionId", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("TransactionId", currentResult);
         currentResult.setValue("description", "<p>Get the Transaction ID of the transaction that this connection is being used with.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserName")) {
         getterName = "getUserName";
         setterName = null;
         currentResult = new PropertyDescriptor("UserName", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("UserName", currentResult);
         currentResult.setValue("description", "<p>Returns the UserName associated with the ManagedConnection's MetaData</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentlyInUse")) {
         getterName = "isCurrentlyInUse";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentlyInUse", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentlyInUse", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the connection is currently in use.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Deletable")) {
         getterName = "isDeletable";
         setterName = null;
         currentResult = new PropertyDescriptor("Deletable", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Deletable", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the connection can be closed manually through the console.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Idle")) {
         getterName = "isIdle";
         setterName = null;
         currentResult = new PropertyDescriptor("Idle", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Idle", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the connection has been idle for a period extending beyond the configured maximum.</p> ");
         currentResult.setValue("deprecated", " ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InTransaction")) {
         getterName = "isInTransaction";
         setterName = null;
         currentResult = new PropertyDescriptor("InTransaction", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InTransaction", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the connection is currently in use in a transaction.</p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Shared")) {
         getterName = "isShared";
         setterName = null;
         currentResult = new PropertyDescriptor("Shared", ConnectorConnectionRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Shared", currentResult);
         currentResult.setValue("description", "<p>Indicates whether the connection is currently being shared by more than one invoker.</p> ");
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
      Method mth = ConnectorConnectionRuntimeMBean.class.getMethod("delete");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Provides a way to manually close a connection through the console.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionRuntimeMBean.class.getMethod("testConnection");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Test the connection. Returns true if the test was successful.</p> ");
         currentResult.setValue("role", "operation");
      }

      mth = ConnectorConnectionRuntimeMBean.class.getMethod("hasError");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", "<p>Return a flag indicating whether the connection has an error or not. A \"true\" is returned if there is an error.</p> ");
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
