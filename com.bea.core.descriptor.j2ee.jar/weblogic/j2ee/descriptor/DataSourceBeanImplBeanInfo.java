package weblogic.j2ee.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.descriptor.SettableBeanImplBeanInfo;
import weblogic.management.internal.mbean.BeanInfoHelper;

public class DataSourceBeanImplBeanInfo extends SettableBeanImplBeanInfo {
   public static final Class INTERFACE_CLASS = DataSourceBean.class;

   public DataSourceBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public DataSourceBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("weblogic.j2ee.descriptor.DataSourceBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "weblogic.j2ee.descriptor");
      String description = (new String(" ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "weblogic.j2ee.descriptor.DataSourceBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("ClassName")) {
         getterName = "getClassName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setClassName";
         }

         currentResult = new PropertyDescriptor("ClassName", DataSourceBean.class, getterName, setterName);
         descriptors.put("ClassName", currentResult);
         currentResult.setValue("description", "The data source implementation class. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DatabaseName")) {
         getterName = "getDatabaseName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDatabaseName";
         }

         currentResult = new PropertyDescriptor("DatabaseName", DataSourceBean.class, getterName, setterName);
         descriptors.put("DatabaseName", currentResult);
         currentResult.setValue("description", "The name of the database connected to this data source. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Description")) {
         getterName = "getDescription";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDescription";
         }

         currentResult = new PropertyDescriptor("Description", DataSourceBean.class, getterName, setterName);
         descriptors.put("Description", currentResult);
         currentResult.setValue("description", "A description of this DataSource. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Id")) {
         getterName = "getId";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setId";
         }

         currentResult = new PropertyDescriptor("Id", DataSourceBean.class, getterName, setterName);
         descriptors.put("Id", currentResult);
         currentResult.setValue("description", "Specifies a string which is used to identify this DataSource. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InitialPoolSize")) {
         getterName = "getInitialPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setInitialPoolSize";
         }

         currentResult = new PropertyDescriptor("InitialPoolSize", DataSourceBean.class, getterName, setterName);
         descriptors.put("InitialPoolSize", currentResult);
         currentResult.setValue("description", "The number of physical connections to create when creating the connection pool in the data source. If unable to create this number of connections, creation of the data source will fail. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IsolationLevel")) {
         getterName = "getIsolationLevel";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIsolationLevel";
         }

         currentResult = new PropertyDescriptor("IsolationLevel", DataSourceBean.class, getterName, setterName);
         descriptors.put("IsolationLevel", currentResult);
         currentResult.setValue("description", "The transaction isolation level used for connections. ");
         currentResult.setValue("defaultValueNull", Boolean.TRUE);
         currentResult.setValue("legalValues", new Object[]{"TRANSACTION_READ_UNCOMMITTED", "TRANSACTION_READ_COMMITTED", "TRANSACTION_REPEATABLE_READ", "TRANSACTION_SERIALIZABLE"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LoginTimeout")) {
         getterName = "getLoginTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLoginTimeout";
         }

         currentResult = new PropertyDescriptor("LoginTimeout", DataSourceBean.class, getterName, setterName);
         descriptors.put("LoginTimeout", currentResult);
         currentResult.setValue("description", "The maximum amount of time, in seconds, that this data source waits while attempting to connect to a database. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxIdleTime")) {
         getterName = "getMaxIdleTime";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxIdleTime";
         }

         currentResult = new PropertyDescriptor("MaxIdleTime", DataSourceBean.class, getterName, setterName);
         descriptors.put("MaxIdleTime", currentResult);
         currentResult.setValue("description", "The maximum amount of time, in seconds, a physical connection can remain unused in the pool before the connection is closed. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxPoolSize")) {
         getterName = "getMaxPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxPoolSize";
         }

         currentResult = new PropertyDescriptor("MaxPoolSize", DataSourceBean.class, getterName, setterName);
         descriptors.put("MaxPoolSize", currentResult);
         currentResult.setValue("description", "The maximum number of physical connections that this connection pool can contain. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MaxStatements")) {
         getterName = "getMaxStatements";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMaxStatements";
         }

         currentResult = new PropertyDescriptor("MaxStatements", DataSourceBean.class, getterName, setterName);
         descriptors.put("MaxStatements", currentResult);
         currentResult.setValue("description", "The total number of statements that a connection pool keeps open. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MinPoolSize")) {
         getterName = "getMinPoolSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMinPoolSize";
         }

         currentResult = new PropertyDescriptor("MinPoolSize", DataSourceBean.class, getterName, setterName);
         descriptors.put("MinPoolSize", currentResult);
         currentResult.setValue("description", "The minimum number of physical connections that this connection pool can contain. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", DataSourceBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of the data source. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Password")) {
         getterName = "getPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPassword";
         }

         currentResult = new PropertyDescriptor("Password", DataSourceBean.class, getterName, setterName);
         descriptors.put("Password", currentResult);
         currentResult.setValue("description", "The password to use for connection authentication with the database. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PortNumber")) {
         getterName = "getPortNumber";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setPortNumber";
         }

         currentResult = new PropertyDescriptor("PortNumber", DataSourceBean.class, getterName, setterName);
         descriptors.put("PortNumber", currentResult);
         currentResult.setValue("description", "The port number a server uses to listen for requests. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Properties")) {
         getterName = "getProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("Properties", DataSourceBean.class, getterName, setterName);
         descriptors.put("Properties", currentResult);
         currentResult.setValue("description", "Spcecifies a JDBC DataSource property. This may be a vendor-specific property or a less commonly used DataSource property. ");
         setPropertyDescriptorDefault(currentResult, new JavaEEPropertyBean[0]);
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createProperty");
         currentResult.setValue("destroyer", "destroyProperty");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ServerName")) {
         getterName = "getServerName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setServerName";
         }

         currentResult = new PropertyDescriptor("ServerName", DataSourceBean.class, getterName, setterName);
         descriptors.put("ServerName", currentResult);
         currentResult.setValue("description", "The database server name. ");
         setPropertyDescriptorDefault(currentResult, "localhost");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Url")) {
         getterName = "getUrl";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUrl";
         }

         currentResult = new PropertyDescriptor("Url", DataSourceBean.class, getterName, setterName);
         descriptors.put("Url", currentResult);
         currentResult.setValue("description", "The JDBC URL. If the <code>Url</code> property is specified along with other standard <code>DataSource</code> properties such as <code>ServerName</code>, <code>DatabaseName</code> and <code>PortNumber</code>, the more specific properties take precedence and <code>Url</code> is ignored. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("User")) {
         getterName = "getUser";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setUser";
         }

         currentResult = new PropertyDescriptor("User", DataSourceBean.class, getterName, setterName);
         descriptors.put("User", currentResult);
         currentResult.setValue("description", "The user name to use for connection authentication with the database. ");
         setPropertyDescriptorDefault(currentResult, "");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Transactional")) {
         getterName = "isTransactional";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setTransactional";
         }

         currentResult = new PropertyDescriptor("Transactional", DataSourceBean.class, getterName, setterName);
         descriptors.put("Transactional", currentResult);
         currentResult.setValue("description", "When enabled, connections participate in transactions. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DataSourceBean.class.getMethod("createProperty");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

      mth = DataSourceBean.class.getMethod("destroyProperty", JavaEEPropertyBean.class);
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Properties");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = DataSourceBean.class.getMethod("lookupProperty", String.class);
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         MethodDescriptor currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "finder");
         currentResult.setValue("property", "Properties");
      }

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
