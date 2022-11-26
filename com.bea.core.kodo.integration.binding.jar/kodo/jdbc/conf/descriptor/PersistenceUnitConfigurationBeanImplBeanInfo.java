package kodo.jdbc.conf.descriptor;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;
import weblogic.management.internal.mbean.BeanInfoHelper;
import weblogic.management.internal.mbean.BeanInfoImpl;

public class PersistenceUnitConfigurationBeanImplBeanInfo extends BeanInfoImpl {
   public static final Class INTERFACE_CLASS = PersistenceUnitConfigurationBean.class;

   public PersistenceUnitConfigurationBeanImplBeanInfo(boolean readOnly, String targetVersion) throws IntrospectionException {
      super(readOnly, targetVersion);
   }

   public PersistenceUnitConfigurationBeanImplBeanInfo() throws IntrospectionException {
   }

   protected BeanDescriptor buildBeanDescriptor() {
      Class beanClass = null;

      try {
         beanClass = Class.forName("kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBeanImpl");
      } catch (Throwable var4) {
         beanClass = INTERFACE_CLASS;
      }

      BeanDescriptor beanDescriptor = new BeanDescriptor(beanClass, (Class)null);
      beanDescriptor.setValue("package", "kodo.jdbc.conf.descriptor");
      String description = (new String("Contains Kodo specific configuration settings to combine with settings contained in a persistence.xml persistence unit.  The named configuration unit must correspond to an equivalently named persistence unit. ")).intern();
      beanDescriptor.setShortDescription(description);
      beanDescriptor.setValue("description", description);
      beanDescriptor.setValue("interfaceclassname", "kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean");
      beanDescriptor.setValue("generatedByWLSInfoBinder", Boolean.TRUE);
      return beanDescriptor;
   }

   protected void buildPropertyDescriptors(Map descriptors) throws IntrospectionException {
      PropertyDescriptor currentResult = null;
      String getterName;
      String setterName;
      if (!descriptors.containsKey("AbstractStoreBrokerFactory")) {
         getterName = "getAbstractStoreBrokerFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("AbstractStoreBrokerFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("AbstractStoreBrokerFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyAbstractStoreBrokerFactory");
         currentResult.setValue("creator", "createAbstractStoreBrokerFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AccessDictionary")) {
         getterName = "getAccessDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("AccessDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("AccessDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createAccessDictionary");
         currentResult.setValue("destroyer", "destroyAccessDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AggregateListeners")) {
         getterName = "getAggregateListeners";
         setterName = null;
         currentResult = new PropertyDescriptor("AggregateListeners", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("AggregateListeners", currentResult);
         currentResult.setValue("description", "A ist of query aggregate listeners to add to the default list of extensions.  Each listener must implement the org.apache.openjpa.kernel.AggregateListener interface. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoClear")) {
         getterName = "getAutoClear";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setAutoClear";
         }

         currentResult = new PropertyDescriptor("AutoClear", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("AutoClear", currentResult);
         currentResult.setValue("description", "If \"datastore\", an object's field values clears when it enters a datastore transaction.  If \"all\", object field values also clear when entering optimistic transactions. ");
         setPropertyDescriptorDefault(currentResult, "datastore");
         currentResult.setValue("legalValues", new Object[]{"all", "datastore"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("AutoDetaches")) {
         getterName = "getAutoDetaches";
         setterName = null;
         currentResult = new PropertyDescriptor("AutoDetaches", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("AutoDetaches", currentResult);
         currentResult.setValue("description", "A list of events upon which the managed instances will be automatically detached.  Events are \"close\", \"commit\", and \"nontx-read\". ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("BatchingOperationOrderUpdateManager")) {
         getterName = "getBatchingOperationOrderUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("BatchingOperationOrderUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("BatchingOperationOrderUpdateManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createBatchingOperationOrderUpdateManager");
         currentResult.setValue("destroyer", "destroyBatchingOperationOrderUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CacheMap")) {
         getterName = "getCacheMap";
         setterName = null;
         currentResult = new PropertyDescriptor("CacheMap", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CacheMap", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCacheMap");
         currentResult.setValue("destroyer", "destroyCacheMap");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClassTableJDBCSeq")) {
         getterName = "getClassTableJDBCSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("ClassTableJDBCSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ClassTableJDBCSeq", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: TableJDBCSeqBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyClassTableJDBCSeq");
         currentResult.setValue("creator", "createClassTableJDBCSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClientBrokerFactory")) {
         getterName = "getClientBrokerFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("ClientBrokerFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ClientBrokerFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyClientBrokerFactory");
         currentResult.setValue("creator", "createClientBrokerFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ClusterRemoteCommitProvider")) {
         getterName = "getClusterRemoteCommitProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("ClusterRemoteCommitProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ClusterRemoteCommitProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createClusterRemoteCommitProvider");
         currentResult.setValue("destroyer", "destroyClusterRemoteCommitProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CommonsLogFactory")) {
         getterName = "getCommonsLogFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CommonsLogFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CommonsLogFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCommonsLogFactory");
         currentResult.setValue("destroyer", "destroyCommonsLogFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Compatibility")) {
         getterName = "getCompatibility";
         setterName = null;
         currentResult = new PropertyDescriptor("Compatibility", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Compatibility", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCompatibilty");
         currentResult.setValue("destroyer", "destroyCompatibility");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConcurrentHashMap")) {
         getterName = "getConcurrentHashMap";
         setterName = null;
         currentResult = new PropertyDescriptor("ConcurrentHashMap", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConcurrentHashMap", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyConcurrentHashMap");
         currentResult.setValue("creator", "createConcurrentHashMap");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2DriverName")) {
         getterName = "getConnection2DriverName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnection2DriverName";
         }

         currentResult = new PropertyDescriptor("Connection2DriverName", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2DriverName", currentResult);
         currentResult.setValue("description", "The class name of the database driver, or an instance of a DataSource to use to connect to the unmanaged data source. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2Password")) {
         getterName = "getConnection2Password";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnection2Password";
         }

         currentResult = new PropertyDescriptor("Connection2Password", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2Password", currentResult);
         currentResult.setValue("description", "The password for the user specified in Connection2UserName ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2PasswordEncrypted")) {
         getterName = "getConnection2PasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnection2PasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("Connection2PasswordEncrypted", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2PasswordEncrypted", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2Properties")) {
         getterName = "getConnection2Properties";
         setterName = null;
         currentResult = new PropertyDescriptor("Connection2Properties", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2Properties", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2URL")) {
         getterName = "getConnection2URL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnection2URL";
         }

         currentResult = new PropertyDescriptor("Connection2URL", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2URL", currentResult);
         currentResult.setValue("description", "The URL for the unmanaged data source. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Connection2UserName")) {
         getterName = "getConnection2UserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnection2UserName";
         }

         currentResult = new PropertyDescriptor("Connection2UserName", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Connection2UserName", currentResult);
         currentResult.setValue("description", "The username for the connection listed in Connection2URL. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionDecorators")) {
         getterName = "getConnectionDecorators";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionDecorators", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionDecorators", currentResult);
         currentResult.setValue("description", "A list of org.apache.openjpa.lib.jdbc.ConnectionDecorator implementations to install on all connection pools. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionDriverName")) {
         getterName = "getConnectionDriverName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionDriverName";
         }

         currentResult = new PropertyDescriptor("ConnectionDriverName", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionDriverName", currentResult);
         currentResult.setValue("description", "The class name of the database driver, or an instance of a DataSource to use to connect to the datasource.  some possible values: \"COM.FirstSQL.Dbcp.DbcpDriver\", \"COM.cloudscape.core.JDBCDriver\", \"COM.ibm.db2.jdbc.app.DB2Driver\", \"COM.ibm.db2.jdbc.net.DB2Driver\", \"centura.java.sqlbase.SqlbaseDriver\", \"com.ddtek.jdbc.db2.DB2Driver\", \"com.ddtek.jdbc.oracle.OracleDriver\", \"com.ddtek.jdbc.sqlserver.SQLServerDriver\", \"com.ddtek.jdbc.sybase.SybaseDriver\", \"com.ibm.as400.access.AS400JDBCDriver\", \"com.imaginary.sql.msql.MsqlDriver\", \"com.inet.tds.TdsDriver\", \"com.informix.jdbc.IfxDriver\", \"com.internetcds.jdbc.tds.Driver\", \"com.jnetdirect.jsql.JSQLDriver\", \"com.mckoi.JDBCDriver\", \"com.microsoft.jdbc.sqlserver.SQLServerDriver\", \"com.mysql.jdbc.DatabaseMetaData\", \"com.mysql.jdbc.Driver\", \"com.pointbase.jdbc.jdbcUniversalDriver\", \"com.sap.dbtech.jdbc.DriverSapDB\", \"com.sybase.jdbc.SybDriver\", \"com.sybase.jdbc2.jdbc.SybDriver\", \"com.thinweb.tds.Driver\", \"in.co.daffodil.db.jdbc.DaffodilDBDriver\", \"interbase.interclient.Driver\", \"intersolv.jdbc.sequelink.SequeLinkDriver\", \"openlink.jdbc2.Driver\", \"oracle.jdbc.driver.OracleDriver\", \"oracle.jdbc.pool.OracleDataSource\", \"org.axiondb.jdbc.AxionDriver\", \"org.enhydra.instantdb.jdbc.idbDriver\", \"org.gjt.mm.mysql.Driver\", \"org.hsql.jdbcDriver\", \"org.hsqldb.jdbcDriver\", \"org.postgresql.Driver\", \"org.sourceforge.jxdbcon.JXDBConDriver\", \"postgres95.PGDriver\", \"postgresql.Driver\", \"solid.jdbc.SolidDriver\", \"sun.jdbc.odbc.JdbcOdbcDriver\", \"weblogic.jdbc.mssqlserver4.Driver\", \"weblogic.jdbc.pool.Driver\" ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactory2Name")) {
         getterName = "getConnectionFactory2Name";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactory2Name";
         }

         currentResult = new PropertyDescriptor("ConnectionFactory2Name", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactory2Name", currentResult);
         currentResult.setValue("description", "The JNDI name of the unmanaged connection factory to use for obtaining connections. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactory2Properties")) {
         getterName = "getConnectionFactory2Properties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactory2Properties", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactory2Properties", currentResult);
         currentResult.setValue("description", "Properties used to configure the javax.sql.DataSource used as the unmanaged ConnectionFactory. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryMode")) {
         getterName = "getConnectionFactoryMode";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactoryMode", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryMode", currentResult);
         currentResult.setValue("description", "The type of datasource to use.  Available options are \"local\" for a standard data source under Kodo's control, or \"managed\" for a data source managed by an application server and automatically enlisted in global transactions. ");
         setPropertyDescriptorDefault(currentResult, "local");
         currentResult.setValue("legalValues", new Object[]{"local", "managed"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryName")) {
         getterName = "getConnectionFactoryName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionFactoryName";
         }

         currentResult = new PropertyDescriptor("ConnectionFactoryName", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryName", currentResult);
         currentResult.setValue("description", "The JNDI name of the connection factory to use for obtaining connections. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionFactoryProperties")) {
         getterName = "getConnectionFactoryProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionFactoryProperties", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionFactoryProperties", currentResult);
         currentResult.setValue("description", "List of bean-like properties used to configure the javax.sql.DataSource used as the ConnectionFactory. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPassword")) {
         getterName = "getConnectionPassword";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPassword";
         }

         currentResult = new PropertyDescriptor("ConnectionPassword", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionPassword", currentResult);
         currentResult.setValue("description", "The password for the user specified in ConnectionUserName. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionPasswordEncrypted")) {
         getterName = "getConnectionPasswordEncrypted";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionPasswordEncrypted";
         }

         currentResult = new PropertyDescriptor("ConnectionPasswordEncrypted", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionPasswordEncrypted", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("encrypted", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionProperties")) {
         getterName = "getConnectionProperties";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionProperties", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionProperties", currentResult);
         currentResult.setValue("description", "List of properties to be passed to the database driver when obtaining a connection.  If the given driver class is a DataSource, these properties will be used to configure the bean properties of the DataSource. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionRetainMode")) {
         getterName = "getConnectionRetainMode";
         setterName = null;
         currentResult = new PropertyDescriptor("ConnectionRetainMode", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionRetainMode", currentResult);
         currentResult.setValue("description", "Controls when connections are pulled from the connection pool. ");
         setPropertyDescriptorDefault(currentResult, "on-demand");
         currentResult.setValue("legalValues", new Object[]{"always", "on-demand", "persistence-manager", "transaction"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionURL")) {
         getterName = "getConnectionURL";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionURL";
         }

         currentResult = new PropertyDescriptor("ConnectionURL", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionURL", currentResult);
         currentResult.setValue("description", "The URL for the data source. Some possible values: \"jdbc:JSQLConnect://<hostname>/database=<database>\", \"jdbc:cloudscape:<database>;create=true\", \"jdbc:daffodilDB_embedded:<database>;create=true\", \"jdbc:datadirect:db2://<hostname>:50000;databaseName=<database>\", \"jdbc:datadirect:oracle://<hostname>:1521;SID=<database>;MaxPooledStatements=0\", \"jdbc:datadirect:sqlserver://<hostname>:1433;SelectMethod=cursor;DatabaseName=<database>\", \"jdbc:datadirect:sybase://<hostname>:5000\", \"jdbc:db2://<hostname>/<database>\", \"jdbc:dbaw://<hostname>:8889/<database>\", \"jdbc:hsqldb:<database>\", \"jdbc:idb:<database>.properties\", \"jdbc:inetdae:<hostname>:1433\", \"jdbc:informix-sqli://<hostname>:1526/<database>:INFORMIXSERVER=<database>\", \"jdbc:interbase://<hostname>//<database>.gdb\", \"jdbc:microsoft:sqlserver://<hostname>:1433;DatabaseName=<database>;SelectMethod=cursor\", \"jdbc:mysql://<hostname>/<database>?autoReconnect=true\", \"jdbc:odbc:<database>\", \"jdbc:openlink://<hostname>/DSN=SQLServerDB/UID=sa/PWD=\", \"jdbc:oracle:thin:@<hostname>:1521:<database>\", \"jdbc:postgresql://<hostname>:5432/<database>\", \"jdbc:postgresql:net//<hostname>/<database>\", \"jdbc:sequelink://<hostname>:4003/[Oracle]\", \"jdbc:sequelink://<hostname>:4004/[Informix];Database=<database>\", \"jdbc:sequelink://<hostname>:4005/[Sybase];Database=<database>\", \"jdbc:sequelink://<hostname>:4006/[SQLServer];Database=<database>\", \"jdbc:sequelink://<hostname>:4011/[ODBC MS Access];Database=<database>\", \"jdbc:solid://<hostname>:<port>/<UID>/<PWD>\", \"jdbc:sybase:Tds:<hostname>:4100/<database>?ServiceName=<database>\", \"jdbc:twtds:sqlserver://<hostname>/<database>\", \"jdbc:weblogic:mssqlserver4:<database>@<hostname>:1433\" ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConnectionUserName")) {
         getterName = "getConnectionUserName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setConnectionUserName";
         }

         currentResult = new PropertyDescriptor("ConnectionUserName", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConnectionUserName", currentResult);
         currentResult.setValue("description", "The user name for the connection listed in ConnectionURL. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ConstraintUpdateManager")) {
         getterName = "getConstraintUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ConstraintUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ConstraintUpdateManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createConstraintUpdateManager");
         currentResult.setValue("destroyer", "destroyConstraintUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomBrokerFactory")) {
         getterName = "getCustomBrokerFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomBrokerFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomBrokerFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomBrokerFactory");
         currentResult.setValue("creator", "createCustomBrokerFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomBrokerImpl")) {
         getterName = "getCustomBrokerImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomBrokerImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomBrokerImpl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomBrokerImpl");
         currentResult.setValue("destroyer", "destroyCustomBrokerImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomClassResolver")) {
         getterName = "getCustomClassResolver";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomClassResolver", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomClassResolver", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomClassResolver");
         currentResult.setValue("destroyer", "destroyCustomClassResolver");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomCompatibility")) {
         getterName = "getCustomCompatibility";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomCompatibility", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomCompatibility", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomCompatibility");
         currentResult.setValue("creator", "createCustomCompatibility");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomDataCacheManager")) {
         getterName = "getCustomDataCacheManager";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomDataCacheManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomDataCacheManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomDataCacheManager");
         currentResult.setValue("destroyer", "destroyCustomDataCacheManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomDetachState")) {
         getterName = "getCustomDetachState";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomDetachState", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomDetachState", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomDetachState");
         currentResult.setValue("destroyer", "destroyCustomDetachState");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomDictionary")) {
         getterName = "getCustomDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomDictionary");
         currentResult.setValue("creator", "createCustomDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomDriverDataSource")) {
         getterName = "getCustomDriverDataSource";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomDriverDataSource", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomDriverDataSource", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomDriverDataSource");
         currentResult.setValue("destroyer", "destroyCustomDriverDataSource");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomLockManager")) {
         getterName = "getCustomLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomLockManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomLockManager");
         currentResult.setValue("destroyer", "destroyCustomLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomLog")) {
         getterName = "getCustomLog";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomLog", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomLog", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomLog");
         currentResult.setValue("destroyer", "destroyCustomLog");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomMappingDefaults")) {
         getterName = "getCustomMappingDefaults";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomMappingDefaults", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomMappingDefaults", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomMappingDefaults");
         currentResult.setValue("destroyer", "destroyCustomMappingDefaults");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomMappingFactory")) {
         getterName = "getCustomMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomMappingFactory");
         currentResult.setValue("destroyer", "destroyCustomMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomMetaDataFactory")) {
         getterName = "getCustomMetaDataFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomMetaDataFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomMetaDataFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomMetaDataFactory");
         currentResult.setValue("creator", "createCustomMetaDataFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomMetaDataRepository")) {
         getterName = "getCustomMetaDataRepository";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomMetaDataRepository", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomMetaDataRepository", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomMetaDataRepository");
         currentResult.setValue("destroyer", "destroyCustomMetaDataRepository");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomOrphanedKeyAction")) {
         getterName = "getCustomOrphanedKeyAction";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomOrphanedKeyAction", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomOrphanedKeyAction", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomOrphanedKeyAction");
         currentResult.setValue("destroyer", "destroyCustomOrphanedKeyAction");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomPersistenceServer")) {
         getterName = "getCustomPersistenceServer";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomPersistenceServer", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomPersistenceServer", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomPersistenceServer");
         currentResult.setValue("destroyer", "destroyCustomPersistenceServer");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomProxyManager")) {
         getterName = "getCustomProxyManager";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomProxyManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomProxyManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomProxyManager");
         currentResult.setValue("creator", "createCustomProxyManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomQueryCompilationCache")) {
         getterName = "getCustomQueryCompilationCache";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomQueryCompilationCache", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomQueryCompilationCache", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomQueryCompilationCache");
         currentResult.setValue("creator", "createCustomQueryCompilationCache");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomRemoteCommitProvider")) {
         getterName = "getCustomRemoteCommitProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomRemoteCommitProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomRemoteCommitProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomRemoteCommitProvider");
         currentResult.setValue("creator", "createCustomRemoteCommitProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomSQLFactory")) {
         getterName = "getCustomSQLFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomSQLFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomSQLFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomSQLFactory");
         currentResult.setValue("destroyer", "destroyCustomSQLFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomSavepointManager")) {
         getterName = "getCustomSavepointManager";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomSavepointManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomSavepointManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomSavepointManager");
         currentResult.setValue("destroyer", "destroyCustomSavepointManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomSchemaFactory")) {
         getterName = "getCustomSchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomSchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomSchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomSchemaFactory");
         currentResult.setValue("destroyer", "destroyCustomSchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomSeq")) {
         getterName = "getCustomSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomSeq", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createCustomSeq");
         currentResult.setValue("destroyer", "destroyCustomSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("CustomUpdateManager")) {
         getterName = "getCustomUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("CustomUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("CustomUpdateManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyCustomUpdateManager");
         currentResult.setValue("creator", "createCustomUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DB2Dictionary")) {
         getterName = "getDB2Dictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("DB2Dictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DB2Dictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDB2Dictionary");
         currentResult.setValue("destroyer", "destroyDB2Dictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataCacheManagerImpl")) {
         getterName = "getDataCacheManagerImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("DataCacheManagerImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DataCacheManagerImpl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDataCacheManagerImpl");
         currentResult.setValue("creator", "createDataCacheManagerImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataCacheTimeout")) {
         getterName = "getDataCacheTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDataCacheTimeout";
         }

         currentResult = new PropertyDescriptor("DataCacheTimeout", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DataCacheTimeout", currentResult);
         currentResult.setValue("description", "The number of milliseconds that data in the data cache is valid for. A value of 0 or less means that by default, cached data does not time out. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DataCaches")) {
         getterName = "getDataCaches";
         setterName = null;
         currentResult = new PropertyDescriptor("DataCaches", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DataCaches", currentResult);
         currentResult.setValue("description", "Plugins used to cache data loaded from the data store.  Must implement org.apache.openjpa.datacache.DataCache. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultBrokerFactory")) {
         getterName = "getDefaultBrokerFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultBrokerFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultBrokerFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: JDBCBrokerFactoryBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultBrokerFactory");
         currentResult.setValue("destroyer", "destroyDefaultBrokerFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultBrokerImpl")) {
         getterName = "getDefaultBrokerImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultBrokerImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultBrokerImpl", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoBrokerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultBrokerImpl");
         currentResult.setValue("creator", "createDefaultBrokerImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultClassResolver")) {
         getterName = "getDefaultClassResolver";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultClassResolver", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultClassResolver", currentResult);
         currentResult.setValue("description", "default subtype: ClassResolverImplBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultClassResolver");
         currentResult.setValue("creator", "createDefaultClassResolver");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultCompatibility")) {
         getterName = "getDefaultCompatibility";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultCompatibility", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultCompatibility", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: CompatibilityBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultCompatibility");
         currentResult.setValue("creator", "createDefaultCompatibility");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDataCacheManager")) {
         getterName = "getDefaultDataCacheManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultDataCacheManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultDataCacheManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoDataCacheManagerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultDataCacheManager");
         currentResult.setValue("creator", "createDefaultDataCacheManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDetachState")) {
         getterName = "getDefaultDetachState";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultDetachState", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultDetachState", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: DetachOptionsLoadedBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultDetachState");
         currentResult.setValue("creator", "createDefaultDetachState");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultDriverDataSource")) {
         getterName = "getDefaultDriverDataSource";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultDriverDataSource", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultDriverDataSource", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoPoolingDataSourceBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultDriverDataSource");
         currentResult.setValue("creator", "createDefaultDriverDataSource");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultLockManager")) {
         getterName = "getDefaultLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultLockManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: PessimisticLockManagerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultLockManager");
         currentResult.setValue("creator", "createDefaultLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMappingDefaults")) {
         getterName = "getDefaultMappingDefaults";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultMappingDefaults", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultMappingDefaults", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: PersistenceappingDefaultsBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultMappingDefaults");
         currentResult.setValue("destroyer", "destroyDefaultMappingDefaults");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMetaDataFactory")) {
         getterName = "getDefaultMetaDataFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultMetaDataFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultMetaDataFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoPersistenceMetaDataFactoryBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultMetaDataFactory");
         currentResult.setValue("creator", "createDefaultMetaDataFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultMetaDataRepository")) {
         getterName = "getDefaultMetaDataRepository";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultMetaDataRepository", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultMetaDataRepository", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoMappingRepositoryBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultMetaDataRepository");
         currentResult.setValue("destroyer", "destroyDefaultMetaDataRepository");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultOrphanedKeyAction")) {
         getterName = "getDefaultOrphanedKeyAction";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultOrphanedKeyAction", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultOrphanedKeyAction", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: LogOrphanedKeyActionBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultOrphanedKeyAction");
         currentResult.setValue("creator", "createDefaultOrphanedKeyAction");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultProxyManager")) {
         getterName = "getDefaultProxyManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultProxyManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultProxyManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: ProfilingProxyManagerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultProxyManager");
         currentResult.setValue("destroyer", "destroyDefaultProxyManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultQueryCompilationCache")) {
         getterName = "getDefaultQueryCompilationCache";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultQueryCompilationCache", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultQueryCompilationCache", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: CacheMapBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultQueryCompilationCache");
         currentResult.setValue("destroyer", "destroyDefaultQueryCompilationCache");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultSQLFactory")) {
         getterName = "getDefaultSQLFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultSQLFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultSQLFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: KodoSQLFactoryBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDefaultSQLFactory");
         currentResult.setValue("destroyer", "destroyDefaultSQLFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultSavepointManager")) {
         getterName = "getDefaultSavepointManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultSavepointManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultSavepointManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: InMemorySavepointManagerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultSavepointManager");
         currentResult.setValue("creator", "createDefaultSavepointManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultSchemaFactory")) {
         getterName = "getDefaultSchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultSchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultSchemaFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: DynamicSchemaFactoryBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultSchemaFactory");
         currentResult.setValue("creator", "createDefaultSchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DefaultUpdateManager")) {
         getterName = "getDefaultUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("DefaultUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DefaultUpdateManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: ConstraintUpdateManagerBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDefaultUpdateManager");
         currentResult.setValue("creator", "createDefaultUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeprecatedJDOMappingDefaults")) {
         getterName = "getDeprecatedJDOMappingDefaults";
         setterName = null;
         currentResult = new PropertyDescriptor("DeprecatedJDOMappingDefaults", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DeprecatedJDOMappingDefaults", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDeprecatedJDOMappingDefaults");
         currentResult.setValue("destroyer", "destroyDeprecatedJDOMappingDefaults");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DeprecatedJDOMetaDataFactory")) {
         getterName = "getDeprecatedJDOMetaDataFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DeprecatedJDOMetaDataFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DeprecatedJDOMetaDataFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDeprecatedJDOMetaDataFactory");
         currentResult.setValue("creator", "createDeprecatedJDOMetaDataFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DerbyDictionary")) {
         getterName = "getDerbyDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("DerbyDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DerbyDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDerbyDictionary");
         currentResult.setValue("creator", "createDerbyDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachOptionsAll")) {
         getterName = "getDetachOptionsAll";
         setterName = null;
         currentResult = new PropertyDescriptor("DetachOptionsAll", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DetachOptionsAll", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDetachOptionsAll");
         currentResult.setValue("creator", "createDetachOptionsAll");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachOptionsFetchGroups")) {
         getterName = "getDetachOptionsFetchGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("DetachOptionsFetchGroups", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DetachOptionsFetchGroups", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDetachOptionsFetchGroups");
         currentResult.setValue("creator", "createDetachOptionsFetchGroups");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DetachOptionsLoaded")) {
         getterName = "getDetachOptionsLoaded";
         setterName = null;
         currentResult = new PropertyDescriptor("DetachOptionsLoaded", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DetachOptionsLoaded", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createDetachOptionsLoaded");
         currentResult.setValue("destroyer", "destroyDetachOptionsLoaded");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicDataStructs")) {
         getterName = "getDynamicDataStructs";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setDynamicDataStructs";
         }

         currentResult = new PropertyDescriptor("DynamicDataStructs", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DynamicDataStructs", currentResult);
         currentResult.setValue("description", "Whether to use dynamic data structs created at runtime to more efficiently store data in data caches. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("DynamicSchemaFactory")) {
         getterName = "getDynamicSchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("DynamicSchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("DynamicSchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyDynamicSchemaFactory");
         currentResult.setValue("creator", "createDynamicSchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EagerFetchMode")) {
         getterName = "getEagerFetchMode";
         setterName = null;
         currentResult = new PropertyDescriptor("EagerFetchMode", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("EagerFetchMode", currentResult);
         currentResult.setValue("description", "Specifies the default eager fetch mode to use.  Either \"none\" to never eagerly-load relations, \"join\" for selecting 1-1 relations along with the target object using inner or outer joins, or \"parallel\" for electing 1-1 relations via joins, and collections (including to-many relations) along with the target object using separate select statements executed in parallel. ");
         setPropertyDescriptorDefault(currentResult, "parallel");
         currentResult.setValue("legalValues", new Object[]{"join", "multiple", "none", "parallel", "single"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("EmpressDictionary")) {
         getterName = "getEmpressDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("EmpressDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("EmpressDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createEmpressDictionary");
         currentResult.setValue("destroyer", "destroyEmpressDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExceptionOrphanedKeyAction")) {
         getterName = "getExceptionOrphanedKeyAction";
         setterName = null;
         currentResult = new PropertyDescriptor("ExceptionOrphanedKeyAction", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ExceptionOrphanedKeyAction", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyExceptionOrphanedKeyAction");
         currentResult.setValue("creator", "createExceptionOrphanedKeyAction");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExportProfiling")) {
         getterName = "getExportProfiling";
         setterName = null;
         currentResult = new PropertyDescriptor("ExportProfiling", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ExportProfiling", currentResult);
         currentResult.setValue("description", "Return Export profiling implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createExportProfiling");
         currentResult.setValue("destroyer", "destroyExportProfiling");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ExtensionDeprecatedJDOMappingFactory")) {
         getterName = "getExtensionDeprecatedJDOMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("ExtensionDeprecatedJDOMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ExtensionDeprecatedJDOMappingFactory", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createExtensionDeprecatedJDOMappingFactory");
         currentResult.setValue("destroyer", "destroyExtensionDeprecatedJDOMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FetchBatchSize")) {
         getterName = "getFetchBatchSize";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setFetchBatchSize";
         }

         currentResult = new PropertyDescriptor("FetchBatchSize", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FetchBatchSize", currentResult);
         currentResult.setValue("description", "The number of rows that will be pre-fetched when an element in a result is accessed.  Use -1 to pre-fetch all results. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FetchDirection")) {
         getterName = "getFetchDirection";
         setterName = null;
         currentResult = new PropertyDescriptor("FetchDirection", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FetchDirection", currentResult);
         currentResult.setValue("description", "The name of the JDBC fetch direction to use.  Standard values are \"forward\", \"reverse\", and \"unknown\" ");
         setPropertyDescriptorDefault(currentResult, "forward");
         currentResult.setValue("legalValues", new Object[]{"forward", "reverse", "unknown"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FetchGroups")) {
         getterName = "getFetchGroups";
         setterName = null;
         currentResult = new PropertyDescriptor("FetchGroups", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FetchGroups", currentResult);
         currentResult.setValue("description", "A list of fetch group names that will be loaded by default when fetching data from the data store. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FileSchemaFactory")) {
         getterName = "getFileSchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("FileSchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FileSchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyFileSchemaFactory");
         currentResult.setValue("creator", "createFileSchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FilterListeners")) {
         getterName = "getFilterListeners";
         setterName = null;
         currentResult = new PropertyDescriptor("FilterListeners", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FilterListeners", currentResult);
         currentResult.setValue("description", "List of query filter listeners to add to the default list of extensions. Each listener must implement the org.apache.kernel.FilterListener interface. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FlushBeforeQueries")) {
         getterName = "getFlushBeforeQueries";
         setterName = null;
         currentResult = new PropertyDescriptor("FlushBeforeQueries", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FlushBeforeQueries", currentResult);
         currentResult.setValue("description", "Whether or not Kodo should automatically flush modifications to the data store before executing queries. ");
         setPropertyDescriptorDefault(currentResult, "true");
         currentResult.setValue("legalValues", new Object[]{"false", "true", "with-connection"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("FoxProDictionary")) {
         getterName = "getFoxProDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("FoxProDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("FoxProDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createFoxProDictionary");
         currentResult.setValue("destroyer", "destroyFoxProDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GUIJMX")) {
         getterName = "getGUIJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("GUIJMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("GUIJMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyGUIJMX");
         currentResult.setValue("creator", "createGUIJMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("GUIProfiling")) {
         getterName = "getGUIProfiling";
         setterName = null;
         currentResult = new PropertyDescriptor("GUIProfiling", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("GUIProfiling", currentResult);
         currentResult.setValue("description", "Return GUI profiling implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createGUIProfiling");
         currentResult.setValue("destroyer", "destroyGUIProfiling");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HSQLDictionary")) {
         getterName = "getHSQLDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("HSQLDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("HSQLDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyHSQLDictionary");
         currentResult.setValue("creator", "createHSQLDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("HTTPTransport")) {
         getterName = "getHTTPTransport";
         setterName = null;
         currentResult = new PropertyDescriptor("HTTPTransport", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("HTTPTransport", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyHTTPTransport");
         currentResult.setValue("creator", "createHTTPTransport");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("IgnoreChanges")) {
         getterName = "getIgnoreChanges";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setIgnoreChanges";
         }

         currentResult = new PropertyDescriptor("IgnoreChanges", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("IgnoreChanges", currentResult);
         currentResult.setValue("description", "If false, them Kodo must consider modifications, deletions, and additions in the current transaction when executing a query. Else, Kodo is free to ignore changes and execute the query directly against the data store. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InMemorySavepointManager")) {
         getterName = "getInMemorySavepointManager";
         setterName = null;
         currentResult = new PropertyDescriptor("InMemorySavepointManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("InMemorySavepointManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyInMemorySavepointManager");
         currentResult.setValue("creator", "createInMemorySavepointManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InformixDictionary")) {
         getterName = "getInformixDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("InformixDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("InformixDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyInformixDictionary");
         currentResult.setValue("creator", "createInformixDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("InverseManager")) {
         getterName = "getInverseManager";
         setterName = null;
         currentResult = new PropertyDescriptor("InverseManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("InverseManager", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: \"false\" ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createInverseManager");
         currentResult.setValue("destroyer", "destroyInverseManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBC3SavepointManager")) {
         getterName = "getJDBC3SavepointManager";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBC3SavepointManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JDBC3SavepointManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJDBC3SavepointManager");
         currentResult.setValue("destroyer", "destroyJDBC3SavepointManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCBrokerFactory")) {
         getterName = "getJDBCBrokerFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCBrokerFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JDBCBrokerFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDBCBrokerFactory");
         currentResult.setValue("creator", "createJDBCBrokerFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDBCListeners")) {
         getterName = "getJDBCListeners";
         setterName = null;
         currentResult = new PropertyDescriptor("JDBCListeners", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JDBCListeners", currentResult);
         currentResult.setValue("description", "A list of org.apache.openjpa.lib.jdbc.JDBCListener implementations to install on all connection pools. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDOMetaDataFactory")) {
         getterName = "getJDOMetaDataFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("JDOMetaDataFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JDOMetaDataFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDOMetaDataFactory");
         currentResult.setValue("creator", "createJDOMetaDataFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JDataStoreDictionary")) {
         getterName = "getJDataStoreDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("JDataStoreDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JDataStoreDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyJDataStoreDictionary");
         currentResult.setValue("creator", "createJDataStoreDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMSRemoteCommitProvider")) {
         getterName = "getJMSRemoteCommitProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("JMSRemoteCommitProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JMSRemoteCommitProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMSRemoteCommitProvider");
         currentResult.setValue("destroyer", "destroyJMSRemoteCommitProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("JMX2JMX")) {
         getterName = "getJMX2JMX";
         setterName = null;
         currentResult = new PropertyDescriptor("JMX2JMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("JMX2JMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createJMX2JMX");
         currentResult.setValue("destroyer", "destroyJMX2JMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoBroker")) {
         getterName = "getKodoBroker";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoBroker", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoBroker", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoBroker");
         currentResult.setValue("creator", "createKodoBroker");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoDataCacheManager")) {
         getterName = "getKodoDataCacheManager";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoDataCacheManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoDataCacheManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoDataCacheManager");
         currentResult.setValue("creator", "createKodoDataCacheManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoMappingRepository")) {
         getterName = "getKodoMappingRepository";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoMappingRepository", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoMappingRepository", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoMappingRepository");
         currentResult.setValue("creator", "createKodoMappingRepository");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoPersistenceMappingFactory")) {
         getterName = "getKodoPersistenceMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPersistenceMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoPersistenceMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoPersistenceMappingFactory");
         currentResult.setValue("creator", "createKodoPersistenceMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoPersistenceMetaDataFactory")) {
         getterName = "getKodoPersistenceMetaDataFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPersistenceMetaDataFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoPersistenceMetaDataFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoPersistenceMetaDataFactory");
         currentResult.setValue("creator", "createKodoPersistenceMetaDataFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoPoolingDataSource")) {
         getterName = "getKodoPoolingDataSource";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoPoolingDataSource", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoPoolingDataSource", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyKodoPoolingDataSource");
         currentResult.setValue("creator", "createKodoPoolingDataSource");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("KodoSQLFactory")) {
         getterName = "getKodoSQLFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("KodoSQLFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("KodoSQLFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createKodoSQLFactory");
         currentResult.setValue("destroyer", "destroyKodoSQLFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LRSSize")) {
         getterName = "getLRSSize";
         setterName = null;
         currentResult = new PropertyDescriptor("LRSSize", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LRSSize", currentResult);
         currentResult.setValue("description", "The mode to use for calculating the size of large result sets. Legal values are \"unknown\", \"last\", and \"query\". ");
         setPropertyDescriptorDefault(currentResult, "query");
         currentResult.setValue("legalValues", new Object[]{"last", "query", "unknown"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LazySchemaFactory")) {
         getterName = "getLazySchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("LazySchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LazySchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createLazySchemaFactory");
         currentResult.setValue("destroyer", "destroyLazySchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalJMX")) {
         getterName = "getLocalJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalJMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LocalJMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLocalJMX");
         currentResult.setValue("creator", "createLocalJMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LocalProfiling")) {
         getterName = "getLocalProfiling";
         setterName = null;
         currentResult = new PropertyDescriptor("LocalProfiling", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LocalProfiling", currentResult);
         currentResult.setValue("description", "Return Local profiling implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createLocalProfiling");
         currentResult.setValue("destroyer", "destroyLocalProfiling");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LockTimeout")) {
         getterName = "getLockTimeout";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setLockTimeout";
         }

         currentResult = new PropertyDescriptor("LockTimeout", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LockTimeout", currentResult);
         currentResult.setValue("description", "The number of milliseconds to wait for an object lock before throwing an exception, or -1 for no limit. ");
         setPropertyDescriptorDefault(currentResult, new Integer(-1));
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("dynamic", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Log4JLogFactory")) {
         getterName = "getLog4JLogFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("Log4JLogFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Log4JLogFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLog4JLogFactory");
         currentResult.setValue("creator", "createLog4JLogFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogFactoryImpl")) {
         getterName = "getLogFactoryImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("LogFactoryImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LogFactoryImpl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLogFactoryImpl");
         currentResult.setValue("creator", "createLogFactoryImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("LogOrphanedKeyAction")) {
         getterName = "getLogOrphanedKeyAction";
         setterName = null;
         currentResult = new PropertyDescriptor("LogOrphanedKeyAction", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("LogOrphanedKeyAction", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyLogOrphanedKeyAction");
         currentResult.setValue("creator", "createLogOrphanedKeyAction");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MX4J1JMX")) {
         getterName = "getMX4J1JMX";
         setterName = null;
         currentResult = new PropertyDescriptor("MX4J1JMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("MX4J1JMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyMX4J1JMX");
         currentResult.setValue("creator", "createMX4J1JMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Mapping")) {
         getterName = "getMapping";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMapping";
         }

         currentResult = new PropertyDescriptor("Mapping", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Mapping", currentResult);
         currentResult.setValue("description", "The name of the mapping to use when multiple mappings per configuration are set. ");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappingDefaultsImpl")) {
         getterName = "getMappingDefaultsImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("MappingDefaultsImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("MappingDefaultsImpl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMappingDefaultsImpl");
         currentResult.setValue("destroyer", "destroyMappingDefaultsImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MappingFileDeprecatedJDOMappingFactory")) {
         getterName = "getMappingFileDeprecatedJDOMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("MappingFileDeprecatedJDOMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("MappingFileDeprecatedJDOMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMappingFileDeprecatedJDOMappingFactory");
         currentResult.setValue("destroyer", "destroyMappingFileDeprecatedJDOMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Multithreaded")) {
         getterName = "getMultithreaded";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setMultithreaded";
         }

         currentResult = new PropertyDescriptor("Multithreaded", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Multithreaded", currentResult);
         currentResult.setValue("description", "If true, then the application to have multiple threads simultaneously accessing a single context or object, so measures must be taken to ensure that the implementation is thread-safe.  Otehrwise, the implementation need not address thread safety. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("MySQLDictionary")) {
         getterName = "getMySQLDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("MySQLDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("MySQLDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createMySQLDictionary");
         currentResult.setValue("destroyer", "destroyMySQLDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Name")) {
         getterName = "getName";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setName";
         }

         currentResult = new PropertyDescriptor("Name", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Name", currentResult);
         currentResult.setValue("description", "The name of the configuration unit.  Must match the name of a persistence unit defined in persistence.xml. ");
         currentResult.setValue("key", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NativeJDBCSeq")) {
         getterName = "getNativeJDBCSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("NativeJDBCSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NativeJDBCSeq", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createNativeJDBCSeq");
         currentResult.setValue("destroyer", "destroyNativeJDBCSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoneJMX")) {
         getterName = "getNoneJMX";
         setterName = null;
         currentResult = new PropertyDescriptor("NoneJMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NoneJMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createNoneJMX");
         currentResult.setValue("destroyer", "destroyNoneJMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoneLockManager")) {
         getterName = "getNoneLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("NoneLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NoneLockManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createNoneLockManager");
         currentResult.setValue("destroyer", "destroyNoneLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoneLogFactory")) {
         getterName = "getNoneLogFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("NoneLogFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NoneLogFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyNoneLogFactory");
         currentResult.setValue("creator", "createNoneLogFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoneOrphanedKeyAction")) {
         getterName = "getNoneOrphanedKeyAction";
         setterName = null;
         currentResult = new PropertyDescriptor("NoneOrphanedKeyAction", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NoneOrphanedKeyAction", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyNoneOrphanedKeyAction");
         currentResult.setValue("creator", "createNoneOrphanedKeyAction");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NoneProfiling")) {
         getterName = "getNoneProfiling";
         setterName = null;
         currentResult = new PropertyDescriptor("NoneProfiling", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NoneProfiling", currentResult);
         currentResult.setValue("description", "Return GUI profiling implementations configured. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createNoneProfiling");
         currentResult.setValue("destroyer", "destroyNoneProfiling");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NontransactionalRead")) {
         getterName = "getNontransactionalRead";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNontransactionalRead";
         }

         currentResult = new PropertyDescriptor("NontransactionalRead", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NontransactionalRead", currentResult);
         currentResult.setValue("description", "If true, then it is possible to read persistent data outside the context of a transaction.  Otherwise, a transaction must be in progress in order to read data. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("NontransactionalWrite")) {
         getterName = "getNontransactionalWrite";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setNontransactionalWrite";
         }

         currentResult = new PropertyDescriptor("NontransactionalWrite", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("NontransactionalWrite", currentResult);
         currentResult.setValue("description", "If true, then it is possible to write to fields of a persistent-nontranssactional object when a transaction is not in progress.  If false, such a write will result in an exception. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ORMFileJDORMappingFactory")) {
         getterName = "getORMFileJDORMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("ORMFileJDORMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ORMFileJDORMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createORMFileJDORMappingFactory");
         currentResult.setValue("destroyer", "destroyORMFileJDORMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OperationOrderUpdateManager")) {
         getterName = "getOperationOrderUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("OperationOrderUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("OperationOrderUpdateManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOperationOrderUpdateManager");
         currentResult.setValue("creator", "createOperationOrderUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Optimistic")) {
         getterName = "getOptimistic";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setOptimistic";
         }

         currentResult = new PropertyDescriptor("Optimistic", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Optimistic", currentResult);
         currentResult.setValue("description", "Selects between optimistic and pessimistic (data store) transactional modes. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OracleDictionary")) {
         getterName = "getOracleDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("OracleDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("OracleDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyOracleDictionary");
         currentResult.setValue("creator", "createOracleDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("OracleSavepointManager")) {
         getterName = "getOracleSavepointManager";
         setterName = null;
         currentResult = new PropertyDescriptor("OracleSavepointManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("OracleSavepointManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createOracleSavepointManager");
         currentResult.setValue("destroyer", "destroyOracleSavepointManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PersistenceMappingDefaults")) {
         getterName = "getPersistenceMappingDefaults";
         setterName = null;
         currentResult = new PropertyDescriptor("PersistenceMappingDefaults", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("PersistenceMappingDefaults", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPersistenceMappingDefaults");
         currentResult.setValue("creator", "createPersistenceMappingDefaults");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PessimisticLockManager")) {
         getterName = "getPessimisticLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("PessimisticLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("PessimisticLockManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createPessimisticLockManager");
         currentResult.setValue("destroyer", "destroyPessimisticLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PointbaseDictionary")) {
         getterName = "getPointbaseDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("PointbaseDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("PointbaseDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPointbaseDictionary");
         currentResult.setValue("creator", "createPointbaseDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("PostgresDictionary")) {
         getterName = "getPostgresDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("PostgresDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("PostgresDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyPostgresDictionary");
         currentResult.setValue("creator", "createPostgresDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProfilingProxyManager")) {
         getterName = "getProfilingProxyManager";
         setterName = null;
         currentResult = new PropertyDescriptor("ProfilingProxyManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ProfilingProxyManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyProfilingProxyManager");
         currentResult.setValue("creator", "createProfilingProxyManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ProxyManagerImpl")) {
         getterName = "getProxyManagerImpl";
         setterName = null;
         currentResult = new PropertyDescriptor("ProxyManagerImpl", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ProxyManagerImpl", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyProxyManagerImpl");
         currentResult.setValue("creator", "createProxyManagerImpl");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("QueryCaches")) {
         getterName = "getQueryCaches";
         setterName = null;
         currentResult = new PropertyDescriptor("QueryCaches", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("QueryCaches", currentResult);
         currentResult.setValue("description", "This is a Kodo plugin. default subtype: DefaultQueryCacheBean ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ReadLockLevel")) {
         getterName = "getReadLockLevel";
         setterName = null;
         currentResult = new PropertyDescriptor("ReadLockLevel", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ReadLockLevel", currentResult);
         currentResult.setValue("description", "The default lock level to use when loading objects within non-optimistic transactions.  Set to none, read, write, or the numeric value of the desired lock level for your lock manager. ");
         setPropertyDescriptorDefault(currentResult, "read");
         currentResult.setValue("legalValues", new Object[]{"none", "read", "write"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RestoreState")) {
         getterName = "getRestoreState";
         setterName = null;
         currentResult = new PropertyDescriptor("RestoreState", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("RestoreState", currentResult);
         currentResult.setValue("description", "If true, then immutable fields in a persistence-capable object that have been changed during a transaction will be restored to their original values upon a rollback.  If set to \"all\", mutable fields will also be restored.  If false, the values will not be changed upon rollback. ");
         setPropertyDescriptorDefault(currentResult, "none");
         currentResult.setValue("legalValues", new Object[]{"all", "false", "immutable", "none", "true"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ResultSetType")) {
         getterName = "getResultSetType";
         setterName = null;
         currentResult = new PropertyDescriptor("ResultSetType", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ResultSetType", currentResult);
         currentResult.setValue("description", "The name of the JDBC result set type to use. ");
         setPropertyDescriptorDefault(currentResult, "forward-only");
         currentResult.setValue("legalValues", new Object[]{"forward-only", "scroll-insensitive", "scroll-sensitive"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetainState")) {
         getterName = "getRetainState";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetainState";
         }

         currentResult = new PropertyDescriptor("RetainState", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("RetainState", currentResult);
         currentResult.setValue("description", "If true, then fields in a persistence-capable object that have been read during a trasnaction must be preserved in memory after the transaction commits.  Otherwise, persisetence-capable objects must transaction to the hollow state upon commit, meaning that subsequent reads will result in a database round-trip. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(true));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("RetryClassRegistration")) {
         getterName = "getRetryClassRegistration";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setRetryClassRegistration";
         }

         currentResult = new PropertyDescriptor("RetryClassRegistration", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("RetryClassRegistration", currentResult);
         currentResult.setValue("description", "Whether to log a warning and defer registration when a registered persistent class cannot be processed, instead of throwing an exception.  Should only be set to true under complex classloader topologies that are causing registration errors. ");
         setPropertyDescriptorDefault(currentResult, new Boolean(false));
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SQLServerDictionary")) {
         getterName = "getSQLServerDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("SQLServerDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SQLServerDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySQLServerDictionary");
         currentResult.setValue("creator", "createSQLServerDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Schema")) {
         getterName = "getSchema";
         setterName = null;
         if (!this.readOnly) {
            setterName = "setSchema";
         }

         currentResult = new PropertyDescriptor("Schema", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Schema", currentResult);
         currentResult.setValue("description", "The default schema for unqualified table names. ");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("Schemata")) {
         getterName = "getSchemata";
         setterName = null;
         currentResult = new PropertyDescriptor("Schemata", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("Schemata", currentResult);
         currentResult.setValue("description", "A list of schemas and/or tables you are using for persistent class tables. ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SimpleDriverDataSource")) {
         getterName = "getSimpleDriverDataSource";
         setterName = null;
         currentResult = new PropertyDescriptor("SimpleDriverDataSource", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SimpleDriverDataSource", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSimpleDriverDataSource");
         currentResult.setValue("destroyer", "destroySimpleDriverDataSource");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleJVMExclusiveLockManager")) {
         getterName = "getSingleJVMExclusiveLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("SingleJVMExclusiveLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SingleJVMExclusiveLockManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySingleJVMExclusiveLockManager");
         currentResult.setValue("creator", "createSingleJVMExclusiveLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SingleJVMRemoteCommitProvider")) {
         getterName = "getSingleJVMRemoteCommitProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("SingleJVMRemoteCommitProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SingleJVMRemoteCommitProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroySingleJVMRemoteCommitProvider");
         currentResult.setValue("creator", "createSingleJVMRemoteCommitProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("StackExecutionContextNameProvider")) {
         getterName = "getStackExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("StackExecutionContextNameProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("StackExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createStackExecutionContextNameProvider");
         currentResult.setValue("destroyer", "destroyStackExecutionContextNameProvider");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SubclassFetchMode")) {
         getterName = "getSubclassFetchMode";
         setterName = null;
         currentResult = new PropertyDescriptor("SubclassFetchMode", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SubclassFetchMode", currentResult);
         currentResult.setValue("description", "Specifies the default subclass fetch mode to use. Eitehr \"none\" to always select data in base class tables only, \"join\" to outer-join to tables for all subclasses, or \"parallel\" to execute a separate select in parallel for each possible subclass.  Parallel mode is only applicable to Query execution; in other situations it mirrors join mode. ");
         setPropertyDescriptorDefault(currentResult, "join");
         currentResult.setValue("legalValues", new Object[]{"join", "multiple", "none", "parallel", "single"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SybaseDictionary")) {
         getterName = "getSybaseDictionary";
         setterName = null;
         currentResult = new PropertyDescriptor("SybaseDictionary", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SybaseDictionary", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createSybaseDictionary");
         currentResult.setValue("destroyer", "destroySybaseDictionary");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("SynchronizeMappings")) {
         getterName = "getSynchronizeMappings";
         setterName = null;
         currentResult = new PropertyDescriptor("SynchronizeMappings", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("SynchronizeMappings", currentResult);
         currentResult.setValue("description", "Controls whether Kodo will attempt to run the mapping tool on all persistent classes to synchronize their mappings and schema at runtime.  some possible values: \"false\" ");
         setPropertyDescriptorDefault(currentResult, "false");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TCPRemoteCommitProvider")) {
         getterName = "getTCPRemoteCommitProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("TCPRemoteCommitProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TCPRemoteCommitProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTCPRemoteCommitProvider");
         currentResult.setValue("creator", "createTCPRemoteCommitProvider");
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TCPTransport")) {
         getterName = "getTCPTransport";
         setterName = null;
         currentResult = new PropertyDescriptor("TCPTransport", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TCPTransport", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTCPTransport");
         currentResult.setValue("destroyer", "destroyTCPTransport");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableDeprecatedJDOMappingFactory")) {
         getterName = "getTableDeprecatedJDOMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("TableDeprecatedJDOMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TableDeprecatedJDOMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTableDeprecatedJDOMappingFactory");
         currentResult.setValue("creator", "createTableDeprecatedJDOMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableJDBCSeq")) {
         getterName = "getTableJDBCSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("TableJDBCSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TableJDBCSeq", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTableJDBCSeq");
         currentResult.setValue("creator", "createTableJDBCSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableJDORMappingFactory")) {
         getterName = "getTableJDORMappingFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("TableJDORMappingFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TableJDORMappingFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTableJDORMappingFactory");
         currentResult.setValue("destroyer", "destroyTableJDORMappingFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableLockUpdateManager")) {
         getterName = "getTableLockUpdateManager";
         setterName = null;
         currentResult = new PropertyDescriptor("TableLockUpdateManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TableLockUpdateManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyTableLockUpdateManager");
         currentResult.setValue("creator", "createTableLockUpdateManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TableSchemaFactory")) {
         getterName = "getTableSchemaFactory";
         setterName = null;
         currentResult = new PropertyDescriptor("TableSchemaFactory", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TableSchemaFactory", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTableSchemaFactory");
         currentResult.setValue("destroyer", "destroyTableSchemaFactory");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TimeSeededSeq")) {
         getterName = "getTimeSeededSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("TimeSeededSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TimeSeededSeq", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTimeSeededSeq");
         currentResult.setValue("destroyer", "destroyTimeSeededSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionIsolation")) {
         getterName = "getTransactionIsolation";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionIsolation", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TransactionIsolation", currentResult);
         currentResult.setValue("description", "The name ofthe JDBC transaction isolation level to use.  \"default\" indicates to use the JDBC driver's default level. ");
         setPropertyDescriptorDefault(currentResult, "default");
         currentResult.setValue("legalValues", new Object[]{"default", "none", "read-committed", "read-uncommitted", "repeatable-read", "serializable"});
         currentResult.setValue("configurable", Boolean.TRUE);
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionMode")) {
         getterName = "getTransactionMode";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionMode", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TransactionMode", currentResult);
         currentResult.setValue("description", "Either \"local\" or \"managed\". ");
         setPropertyDescriptorDefault(currentResult, "managed");
         currentResult.setValue("legalValues", new Object[]{"local", "managed"});
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("TransactionNameExecutionContextNameProvider")) {
         getterName = "getTransactionNameExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("TransactionNameExecutionContextNameProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("TransactionNameExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createTransactionNameExecutionContextNameProvider");
         currentResult.setValue("destroyer", "destroyTransactionNameExecutionContextNameProvider");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("UserObjectExecutionContextNameProvider")) {
         getterName = "getUserObjectExecutionContextNameProvider";
         setterName = null;
         currentResult = new PropertyDescriptor("UserObjectExecutionContextNameProvider", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("UserObjectExecutionContextNameProvider", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("destroyer", "destroyUserObjectExecutionContextNameProvider");
         currentResult.setValue("creator", "createUserObjectExecutionContextNameProvider");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("ValueTableJDBCSeq")) {
         getterName = "getValueTableJDBCSeq";
         setterName = null;
         currentResult = new PropertyDescriptor("ValueTableJDBCSeq", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("ValueTableJDBCSeq", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createValueTableJDBCSeq");
         currentResult.setValue("destroyer", "destroyValueTableJDBCSeq");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("VersionLockManager")) {
         getterName = "getVersionLockManager";
         setterName = null;
         currentResult = new PropertyDescriptor("VersionLockManager", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("VersionLockManager", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createVersionLockManager");
         currentResult.setValue("destroyer", "destroyVersionLockManager");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WLS81JMX")) {
         getterName = "getWLS81JMX";
         setterName = null;
         currentResult = new PropertyDescriptor("WLS81JMX", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("WLS81JMX", currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("relationship", "containment");
         currentResult.setValue("creator", "createWLS81JMX");
         currentResult.setValue("destroyer", "destroyWLS81JMX");
         currentResult.setValue("owner", "");
      }

      if (!descriptors.containsKey("WriteLockLevel")) {
         getterName = "getWriteLockLevel";
         setterName = null;
         currentResult = new PropertyDescriptor("WriteLockLevel", PersistenceUnitConfigurationBean.class, getterName, setterName);
         descriptors.put("WriteLockLevel", currentResult);
         currentResult.setValue("description", "The default lock level to use when changing objects within non-optimistic transactions.  Set to none, read, write, or the numeric value of the desired lock level for your lock manager. ");
         setPropertyDescriptorDefault(currentResult, "write");
         currentResult.setValue("legalValues", new Object[]{"none", "read", "write"});
         currentResult.setValue("owner", "");
      }

      super.buildPropertyDescriptors(descriptors);
   }

   private void fillinFactoryMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultBrokerFactory");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createAbstractStoreBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AbstractStoreBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyAbstractStoreBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AbstractStoreBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createClientBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClientBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyClientBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClientBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJDBCBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJDBCBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBCBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomBrokerFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomBrokerFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultBrokerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultBrokerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultBrokerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultBrokerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoBroker");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoBroker");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoBroker");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoBroker");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomBrokerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomBrokerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomBrokerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomBrokerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultClassResolver");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultClassResolver");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultClassResolver");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultClassResolver");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomClassResolver");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomClassResolver");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomClassResolver");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomClassResolver");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultCompatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultCompatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCompatibilty");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Compatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Compatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomCompatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomCompatibility");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomCompatibility");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDataCacheManagerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataCacheManagerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDataCacheManagerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DataCacheManagerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomDataCacheManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDataCacheManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createAccessDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AccessDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyAccessDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "AccessDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDB2Dictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DB2Dictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDB2Dictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DB2Dictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDerbyDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DerbyDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDerbyDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DerbyDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createEmpressDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EmpressDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyEmpressDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "EmpressDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createFoxProDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FoxProDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyFoxProDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FoxProDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createHSQLDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HSQLDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyHSQLDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HSQLDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createInformixDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InformixDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyInformixDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InformixDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJDataStoreDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDataStoreDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJDataStoreDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDataStoreDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createMySQLDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MySQLDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyMySQLDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MySQLDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createOracleDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OracleDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyOracleDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OracleDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createPointbaseDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PointbaseDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyPointbaseDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PointbaseDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createPostgresDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostgresDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyPostgresDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PostgresDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createSQLServerDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SQLServerDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroySQLServerDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SQLServerDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createSybaseDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SybaseDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroySybaseDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SybaseDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDictionary");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultDetachState");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDetachState");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultDetachState");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDetachState");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDetachOptionsLoaded");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsLoaded");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDetachOptionsLoaded");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsLoaded");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDetachOptionsFetchGroups");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsFetchGroups");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDetachOptionsFetchGroups");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsFetchGroups");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDetachOptionsAll");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsAll");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDetachOptionsAll");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DetachOptionsAll");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomDetachState");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDetachState");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomDetachState");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDetachState");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoPoolingDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPoolingDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoPoolingDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPoolingDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createSimpleDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SimpleDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroySimpleDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SimpleDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomDriverDataSource");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomDriverDataSource");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createInverseManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InverseManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyInverseManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InverseManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createPessimisticLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PessimisticLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyPessimisticLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PessimisticLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNoneLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNoneLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createSingleJVMExclusiveLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingleJVMExclusiveLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroySingleJVMExclusiveLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingleJVMExclusiveLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createVersionLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VersionLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyVersionLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "VersionLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomLockManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomLockManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCommonsLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CommonsLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCommonsLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CommonsLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLog4JLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Log4JLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLog4JLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "Log4JLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLogFactoryImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogFactoryImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLogFactoryImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogFactoryImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNoneLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNoneLogFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneLogFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomLog");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomLog");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomLog");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomLog");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDeprecatedJDOMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeprecatedJDOMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDeprecatedJDOMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeprecatedJDOMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createMappingDefaultsImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MappingDefaultsImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyMappingDefaultsImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MappingDefaultsImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createPersistenceMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyPersistenceMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "PersistenceMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomMappingDefaults");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMappingDefaults");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createExtensionDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExtensionDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyExtensionDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExtensionDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoPersistenceMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPersistenceMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoPersistenceMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPersistenceMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createMappingFileDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MappingFileDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyMappingFileDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MappingFileDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createORMFileJDORMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ORMFileJDORMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyORMFileJDORMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ORMFileJDORMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTableDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTableDeprecatedJDOMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableDeprecatedJDOMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTableJDORMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableJDORMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTableJDORMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableJDORMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomMappingFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMappingFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJDOMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDOMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJDOMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDOMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDeprecatedJDOMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeprecatedJDOMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDeprecatedJDOMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DeprecatedJDOMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoPersistenceMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPersistenceMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoPersistenceMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoPersistenceMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomMetaDataFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMetaDataFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultMetaDataRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMetaDataRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultMetaDataRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultMetaDataRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoMappingRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoMappingRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoMappingRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoMappingRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomMetaDataRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMetaDataRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomMetaDataRepository");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomMetaDataRepository");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLogOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLogOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LogOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createExceptionOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExceptionOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyExceptionOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExceptionOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNoneOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNoneOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomOrphanedKeyAction");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomOrphanedKeyAction");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createHTTPTransport");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HTTPTransport");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyHTTPTransport");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "HTTPTransport");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTCPTransport");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TCPTransport");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTCPTransport");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TCPTransport");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomPersistenceServer");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomPersistenceServer");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomPersistenceServer");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomPersistenceServer");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createProfilingProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ProfilingProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyProfilingProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ProfilingProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createProxyManagerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ProxyManagerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyProxyManagerImpl");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ProxyManagerImpl");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomProxyManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomProxyManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultQueryCompilationCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultQueryCompilationCache");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultQueryCompilationCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultQueryCompilationCache");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCacheMap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CacheMap");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCacheMap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CacheMap");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createConcurrentHashMap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConcurrentHashMap");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyConcurrentHashMap");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConcurrentHashMap");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomQueryCompilationCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomQueryCompilationCache");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomQueryCompilationCache");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomQueryCompilationCache");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJMSRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJMSRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMSRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createSingleJVMRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingleJVMRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroySingleJVMRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "SingleJVMRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTCPRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TCPRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTCPRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TCPRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createClusterRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClusterRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyClusterRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClusterRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomRemoteCommitProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createInMemorySavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InMemorySavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyInMemorySavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "InMemorySavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJDBC3SavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBC3SavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJDBC3SavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JDBC3SavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createOracleSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OracleSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyOracleSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OracleSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomSavepointManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSavepointManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDynamicSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DynamicSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDynamicSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DynamicSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createFileSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyFileSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "FileSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLazySchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LazySchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLazySchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LazySchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTableSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTableSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomSchemaFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSchemaFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createClassTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClassTableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyClassTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ClassTableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNativeJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NativeJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNativeJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NativeJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTimeSeededSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TimeSeededSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTimeSeededSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TimeSeededSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createValueTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ValueTableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyValueTableJDBCSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ValueTableJDBCSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomSeq");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSeq");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createKodoSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyKodoSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "KodoSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomSQLFactory");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomSQLFactory");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDefaultUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDefaultUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "DefaultUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createConstraintUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConstraintUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyConstraintUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ConstraintUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createBatchingOperationOrderUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BatchingOperationOrderUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyBatchingOperationOrderUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "BatchingOperationOrderUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createOperationOrderUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OperationOrderUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyOperationOrderUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "OperationOrderUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTableLockUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableLockUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTableLockUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TableLockUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createCustomUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyCustomUpdateManager");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "CustomUpdateManager");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createStackExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StackExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyStackExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "StackExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createTransactionNameExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionNameExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyTransactionNameExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "TransactionNameExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createUserObjectExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UserObjectExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyUserObjectExecutionContextNameProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "UserObjectExecutionContextNameProvider");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNoneJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNoneJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLocalJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LocalJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLocalJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LocalJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createGUIJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GUIJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyGUIJMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GUIJMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createJMX2JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMX2JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyJMX2JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "JMX2JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createMX4J1JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MX4J1JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyMX4J1JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "MX4J1JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createWLS81JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLS81JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyWLS81JMX");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "WLS81JMX");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createNoneProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyNoneProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "NoneProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createLocalProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LocalProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyLocalProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "LocalProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createExportProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExportProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyExportProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "ExportProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createGUIProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GUIProfiling");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyGUIProfiling");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("role", "factory");
         currentResult.setValue("property", "GUIProfiling");
      }

   }

   private void fillinCollectionMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinFinderMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
   }

   private void fillinOperationMethodInfos(Map descriptors) throws IntrospectionException, NoSuchMethodException {
      Method mth = PersistenceUnitConfigurationBean.class.getMethod("getDBDictionaryTypes");
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      MethodDescriptor currentResult;
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("getDBDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createDBDictionary", Class.class);
      ParameterDescriptor[] parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "Class - Provider type to be created ")};
      String methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyDBDictionary");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("getRemoteCommitProviderTypes");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("getRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("createRemoteCommitProvider", Class.class);
      parameterDescriptors = new ParameterDescriptor[]{createParameterDescriptor("type", "Class - Provider type to be created ")};
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, parameterDescriptors);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
         currentResult.setValue("exclude", Boolean.TRUE);
         currentResult.setValue("role", "operation");
      }

      mth = PersistenceUnitConfigurationBean.class.getMethod("destroyRemoteCommitProvider");
      methodKey = BeanInfoHelper.buildMethodKey(mth);
      if (!descriptors.containsKey(methodKey)) {
         currentResult = new MethodDescriptor(mth, (ParameterDescriptor[])null);
         descriptors.put(methodKey, currentResult);
         currentResult.setValue("description", " ");
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
