package weblogic.jdbc.common.internal;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import weblogic.management.WebLogicMBeanImplBeanInfo;
import weblogic.management.runtime.JDBCOracleDataSourceInstanceRuntimeMBean;

public class OracleDataSourceInstanceRuntimeImplBeanInfo extends WebLogicMBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = JDBCOracleDataSourceInstanceRuntimeMBean.class;

   public OracleDataSourceInstanceRuntimeImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public OracleDataSourceInstanceRuntimeImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.jdbc.common.internal.OracleDataSourceInstanceRuntimeImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("since", "10.3.4.0");
      beanDescriptor.setValue("package", "weblogic.jdbc.common.internal");
      String description = (new String("Runtime MBean for monitoring a JDBC GridLink Data Source instance. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.management.runtime.JDBCOracleDataSourceInstanceRuntimeMBean");
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
         currentResult = new PropertyDescriptor("ActiveConnectionsCurrentCount", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ActiveConnectionsCurrentCount", currentResult);
         currentResult.setValue("description", "<p> The number of connections currently in use by applications. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionsTotalCount")) {
         getterName = "getConnectionsTotalCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionsTotalCount", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ConnectionsTotalCount", currentResult);
         currentResult.setValue("description", "<p> The cumulative total number of database connections created in this instance since the data source was deployed. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrCapacity")) {
         getterName = "getCurrCapacity";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrCapacity", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrCapacity", currentResult);
         currentResult.setValue("description", "<p> The current count of JDBC connections in the connection pool in the data source for this instance. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CurrentWeight")) {
         getterName = "getCurrentWeight";
         setterName = null;
         currentResult = new PropertyDescriptor("CurrentWeight", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("CurrentWeight", currentResult);
         currentResult.setValue("description", "The current weight of the instance. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InstanceName")) {
         getterName = "getInstanceName";
         setterName = null;
         currentResult = new PropertyDescriptor("InstanceName", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("InstanceName", currentResult);
         currentResult.setValue("description", "The name of this instance. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumAvailable")) {
         getterName = "getNumAvailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumAvailable", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumAvailable", currentResult);
         currentResult.setValue("description", "<p> The number of database connections currently available (not in use) in this data source for this instance. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NumUnavailable")) {
         getterName = "getNumUnavailable";
         setterName = null;
         currentResult = new PropertyDescriptor("NumUnavailable", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("NumUnavailable", currentResult);
         currentResult.setValue("description", "<p> The number of database connections that are currently unavailable (in use or being tested by the system) in this instance. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReserveRequestCount")) {
         getterName = "getReserveRequestCount";
         setterName = null;
         currentResult = new PropertyDescriptor("ReserveRequestCount", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("ReserveRequestCount", currentResult);
         currentResult.setValue("description", "<p> The cumulative, running count of requests for a connection from this instance. </p> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Signature")) {
         getterName = "getSignature";
         setterName = null;
         currentResult = new PropertyDescriptor("Signature", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Signature", currentResult);
         currentResult.setValue("description", "The signature that uniquely identifies the instance. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("State")) {
         getterName = "getState";
         setterName = null;
         currentResult = new PropertyDescriptor("State", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("State", currentResult);
         currentResult.setValue("description", "<p> The current state of the instance within the data source. </p> <p> Possible states are: </p> <ul> <li>Enabled - the instance is enabled indicating that there are connections established</li> <li>Disabled - the instance is disabled due to unavailability</li> </ul> ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AffEnabled")) {
         getterName = "isAffEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("AffEnabled", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("AffEnabled", currentResult);
         currentResult.setValue("description", "The value from the latest load-balancing advisory <code>aff</code> flag for a GridLink data source instance. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Enabled")) {
         getterName = "isEnabled";
         setterName = null;
         currentResult = new PropertyDescriptor("Enabled", JDBCOracleDataSourceInstanceRuntimeMBean.class, getterName, (String)setterName);
         descriptors.put("Enabled", currentResult);
         currentResult.setValue("description", "Indicates whether the instance is enabled or disabled: <ul> <li>true if the instance is enabled.</li> <li>false if the instance is disabled.</li> </ul> ");
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
