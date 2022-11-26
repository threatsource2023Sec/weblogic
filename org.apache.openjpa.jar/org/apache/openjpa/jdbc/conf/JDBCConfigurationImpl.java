package org.apache.openjpa.jdbc.conf;

import javax.sql.DataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.BatchingConstraintUpdateManager;
import org.apache.openjpa.jdbc.kernel.BatchingOperationOrderUpdateManager;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.jdbc.kernel.PessimisticLockManager;
import org.apache.openjpa.jdbc.kernel.UpdateManager;
import org.apache.openjpa.jdbc.meta.MappingDefaults;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.schema.DataSourceFactory;
import org.apache.openjpa.jdbc.schema.DriverDataSource;
import org.apache.openjpa.jdbc.schema.SchemaFactory;
import org.apache.openjpa.jdbc.sql.DBDictionary;
import org.apache.openjpa.jdbc.sql.DBDictionaryFactory;
import org.apache.openjpa.jdbc.sql.SQLFactory;
import org.apache.openjpa.kernel.BrokerImpl;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.conf.IntValue;
import org.apache.openjpa.lib.conf.ObjectValue;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.conf.StringListValue;
import org.apache.openjpa.lib.conf.StringValue;
import org.apache.openjpa.lib.jdbc.ConnectionDecorator;
import org.apache.openjpa.lib.jdbc.DecoratingDataSource;
import org.apache.openjpa.lib.jdbc.JDBCListener;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataFactory;
import serp.bytecode.Instruction;
import serp.bytecode.lowlevel.Entry;

public class JDBCConfigurationImpl extends OpenJPAConfigurationImpl implements JDBCConfiguration {
   public StringValue schema;
   public StringListValue schemas;
   public IntValue transactionIsolation;
   public IntValue resultSetType;
   public IntValue fetchDirection;
   public FetchModeValue eagerFetchMode;
   public FetchModeValue subclassFetchMode;
   public IntValue lrsSize;
   public StringValue synchronizeMappings;
   public ObjectValue jdbcListenerPlugins;
   public ObjectValue connectionDecoratorPlugins;
   public PluginValue dbdictionaryPlugin;
   public ObjectValue updateManagerPlugin;
   public ObjectValue schemaFactoryPlugin;
   public ObjectValue sqlFactoryPlugin;
   public ObjectValue mappingDefaultsPlugin;
   public PluginValue driverDataSourcePlugin;
   public MappingFactoryValue mappingFactoryPlugin;
   private String firstUser;
   private String firstPass;
   private DecoratingDataSource dataSource;
   private DecoratingDataSource dataSource2;

   public JDBCConfigurationImpl() {
      this(true);
   }

   public JDBCConfigurationImpl(boolean loadGlobals) {
      this(true, loadGlobals);
   }

   public JDBCConfigurationImpl(boolean derivations, boolean loadGlobals) {
      super(false, false);
      this.firstUser = null;
      this.firstPass = null;
      this.dataSource = null;
      this.dataSource2 = null;
      this.schema = this.addString("jdbc.Schema");
      this.schemas = this.addStringList("jdbc.Schemas");
      this.transactionIsolation = this.addInt("jdbc.TransactionIsolation");
      String[] aliases = new String[]{"default", String.valueOf(-1), "none", String.valueOf(0), "read-committed", String.valueOf(2), "read-uncommitted", String.valueOf(1), "repeatable-read", String.valueOf(4), "serializable", String.valueOf(8)};
      this.transactionIsolation.setAliases(aliases);
      this.transactionIsolation.setDefault(aliases[0]);
      this.transactionIsolation.set(-1);
      this.transactionIsolation.setAliasListComprehensive(true);
      this.resultSetType = this.addInt("jdbc.ResultSetType");
      aliases = new String[]{"forward-only", String.valueOf(1003), "scroll-sensitive", String.valueOf(1005), "scroll-insensitive", String.valueOf(1004)};
      this.resultSetType.setAliases(aliases);
      this.resultSetType.setDefault(aliases[0]);
      this.resultSetType.set(1003);
      this.resultSetType.setAliasListComprehensive(true);
      this.fetchDirection = this.addInt("jdbc.FetchDirection");
      aliases = new String[]{"forward", String.valueOf(1000), "reverse", String.valueOf(1001), "unknown", String.valueOf(1002)};
      this.fetchDirection.setAliases(aliases);
      this.fetchDirection.setDefault(aliases[0]);
      this.fetchDirection.set(1000);
      this.fetchDirection.setAliasListComprehensive(true);
      this.eagerFetchMode = new FetchModeValue("jdbc.EagerFetchMode");
      this.eagerFetchMode.setDefault("parallel");
      this.eagerFetchMode.set(2);
      this.addValue(this.eagerFetchMode);
      this.subclassFetchMode = new FetchModeValue("jdbc.SubclassFetchMode");
      this.subclassFetchMode.setDefault("join");
      this.subclassFetchMode.set(1);
      this.addValue(this.subclassFetchMode);
      this.lrsSize = this.addInt("jdbc.LRSSize");
      aliases = new String[]{"query", String.valueOf(2), "unknown", String.valueOf(0), "last", String.valueOf(1)};
      this.lrsSize.setAliases(aliases);
      this.lrsSize.setDefault(aliases[0]);
      this.lrsSize.set(2);
      this.lrsSize.setAliasListComprehensive(true);
      this.synchronizeMappings = this.addString("jdbc.SynchronizeMappings");
      aliases = new String[]{"false", null};
      this.synchronizeMappings.setAliases(aliases);
      this.synchronizeMappings.setDefault(aliases[0]);
      this.jdbcListenerPlugins = this.addPluginList("jdbc.JDBCListeners");
      this.jdbcListenerPlugins.setInstantiatingGetter("getJDBCListenerInstances");
      this.connectionDecoratorPlugins = this.addPluginList("jdbc.ConnectionDecorators");
      this.connectionDecoratorPlugins.setInstantiatingGetter("getConnectionDecoratorInstances");
      this.dbdictionaryPlugin = this.addPlugin("jdbc.DBDictionary", true);
      aliases = new String[]{"access", "org.apache.openjpa.jdbc.sql.AccessDictionary", "db2", "org.apache.openjpa.jdbc.sql.DB2Dictionary", "derby", "org.apache.openjpa.jdbc.sql.DerbyDictionary", "empress", "org.apache.openjpa.jdbc.sql.EmpressDictionary", "foxpro", "org.apache.openjpa.jdbc.sql.FoxProDictionary", "h2", "org.apache.openjpa.jdbc.sql.H2Dictionary", "hsql", "org.apache.openjpa.jdbc.sql.HSQLDictionary", "informix", "org.apache.openjpa.jdbc.sql.InformixDictionary", "jdatastore", "org.apache.openjpa.jdbc.sql.JDataStoreDictionary", "mysql", "org.apache.openjpa.jdbc.sql.MySQLDictionary", "oracle", "org.apache.openjpa.jdbc.sql.OracleDictionary", "pointbase", "org.apache.openjpa.jdbc.sql.PointbaseDictionary", "postgres", "org.apache.openjpa.jdbc.sql.PostgresDictionary", "sqlserver", "org.apache.openjpa.jdbc.sql.SQLServerDictionary", "sybase", "org.apache.openjpa.jdbc.sql.SybaseDictionary"};
      this.dbdictionaryPlugin.setAliases(aliases);
      this.dbdictionaryPlugin.setInstantiatingGetter("getDBDictionaryInstance");
      this.updateManagerPlugin = this.addPlugin("jdbc.UpdateManager", true);
      aliases = new String[]{"default", BatchingConstraintUpdateManager.class.getName(), "operation-order", "org.apache.openjpa.jdbc.kernel.OperationOrderUpdateManager", "constraint", "org.apache.openjpa.jdbc.kernel.ConstraintUpdateManager", "batching-constraint", BatchingConstraintUpdateManager.class.getName(), "batching-operation-order", BatchingOperationOrderUpdateManager.class.getName()};
      this.updateManagerPlugin.setAliases(aliases);
      this.updateManagerPlugin.setDefault(aliases[0]);
      this.updateManagerPlugin.setString(aliases[0]);
      this.updateManagerPlugin.setInstantiatingGetter("getUpdateManagerInstance");
      this.driverDataSourcePlugin = this.addPlugin("jdbc.DriverDataSource", false);
      aliases = new String[]{"simple", "org.apache.openjpa.jdbc.schema.SimpleDriverDataSource"};
      this.driverDataSourcePlugin.setAliases(aliases);
      this.driverDataSourcePlugin.setDefault(aliases[0]);
      this.driverDataSourcePlugin.setString(aliases[0]);
      this.schemaFactoryPlugin = this.addPlugin("jdbc.SchemaFactory", true);
      aliases = new String[]{"dynamic", "org.apache.openjpa.jdbc.schema.DynamicSchemaFactory", "native", "org.apache.openjpa.jdbc.schema.LazySchemaFactory", "file", "org.apache.openjpa.jdbc.schema.FileSchemaFactory", "table", "org.apache.openjpa.jdbc.schema.TableSchemaFactory", "db", "org.apache.openjpa.jdbc.schema.TableSchemaFactory"};
      this.schemaFactoryPlugin.setAliases(aliases);
      this.schemaFactoryPlugin.setDefault(aliases[0]);
      this.schemaFactoryPlugin.setString(aliases[0]);
      this.schemaFactoryPlugin.setInstantiatingGetter("getSchemaFactoryInstance");
      this.sqlFactoryPlugin = this.addPlugin("jdbc.SQLFactory", true);
      aliases = new String[]{"default", "org.apache.openjpa.jdbc.sql.SQLFactoryImpl"};
      this.sqlFactoryPlugin.setAliases(aliases);
      this.sqlFactoryPlugin.setDefault(aliases[0]);
      this.sqlFactoryPlugin.setString(aliases[0]);
      this.sqlFactoryPlugin.setInstantiatingGetter("getSQLFactoryInstance");
      this.mappingFactoryPlugin = new MappingFactoryValue("jdbc.MappingFactory");
      this.addValue(this.mappingFactoryPlugin);
      this.mappingDefaultsPlugin = this.addPlugin("jdbc.MappingDefaults", true);
      aliases = new String[]{"default", "org.apache.openjpa.jdbc.meta.MappingDefaultsImpl"};
      this.mappingDefaultsPlugin.setAliases(aliases);
      this.mappingDefaultsPlugin.setDefault(aliases[0]);
      this.mappingDefaultsPlugin.setString(aliases[0]);
      this.mappingDefaultsPlugin.setInstantiatingGetter("getMappingDefaultsInstance");
      this.brokerFactoryPlugin.setAlias("jdbc", JDBCBrokerFactory.class.getName());
      this.brokerFactoryPlugin.setDefault("jdbc");
      this.brokerFactoryPlugin.setString("jdbc");
      this.metaRepositoryPlugin.setAlias("default", "org.apache.openjpa.jdbc.meta.MappingRepository");
      this.metaRepositoryPlugin.setDefault("default");
      this.metaRepositoryPlugin.setString("default");
      this.lockManagerPlugin.setAlias("pessimistic", PessimisticLockManager.class.getName());
      this.lockManagerPlugin.setDefault("pessimistic");
      this.lockManagerPlugin.setString("pessimistic");
      this.savepointManagerPlugin.setAlias("jdbc", "org.apache.openjpa.jdbc.kernel.JDBC3SavepointManager");
      this.seqPlugin.setAliases(JDBCSeqValue.ALIASES);
      this.seqPlugin.setDefault(JDBCSeqValue.ALIASES[0]);
      this.seqPlugin.setString(JDBCSeqValue.ALIASES[0]);

      try {
         Entry.class.getName();
      } catch (Throwable var6) {
      }

      try {
         Instruction.class.getName();
      } catch (Throwable var5) {
      }

      this.supportedOptions().add("openjpa.option.SQL");
      this.supportedOptions().add("openjpa.option.JDBCConnection");
      this.supportedOptions().remove("openjpa.option.IncrementValue");
      this.supportedOptions().remove("openjpa.option.NullContainer");
      if (derivations) {
         ProductDerivations.beforeConfigurationLoad(this);
      }

      if (loadGlobals) {
         this.loadGlobals();
      }

   }

   public JDBCConfigurationImpl(JDBCConfiguration conf) {
      this(true, false);
      if (conf != null) {
         this.fromProperties(conf.toProperties(false));
      }

   }

   public void setSchema(String schema) {
      this.schema.setString(schema);
   }

   public String getSchema() {
      return this.schema.getString();
   }

   public void setSchemas(String schemas) {
      this.schemas.setString(schemas);
   }

   public String getSchemas() {
      return this.schemas.getString();
   }

   public void setSchemas(String[] schemas) {
      this.schemas.set(schemas);
   }

   public String[] getSchemasList() {
      return this.schemas.get();
   }

   public void setTransactionIsolation(String transactionIsolation) {
      this.transactionIsolation.setString(transactionIsolation);
   }

   public String getTransactionIsolation() {
      return this.transactionIsolation.getString();
   }

   public void setTransactionIsolation(int transactionIsolation) {
      this.transactionIsolation.set(transactionIsolation);
   }

   public int getTransactionIsolationConstant() {
      return this.transactionIsolation.get();
   }

   public void setResultSetType(String resultSetType) {
      this.resultSetType.setString(resultSetType);
   }

   public String getResultSetType() {
      return this.resultSetType.getString();
   }

   public void setResultSetType(int resultSetType) {
      this.resultSetType.set(resultSetType);
   }

   public int getResultSetTypeConstant() {
      return this.resultSetType.get();
   }

   public void setFetchDirection(String fetchDirection) {
      this.fetchDirection.setString(fetchDirection);
   }

   public String getFetchDirection() {
      return this.fetchDirection.getString();
   }

   public void setFetchDirection(int fetchDirection) {
      this.fetchDirection.set(fetchDirection);
   }

   public int getFetchDirectionConstant() {
      return this.fetchDirection.get();
   }

   public void setEagerFetchMode(String eagerFetchMode) {
      this.eagerFetchMode.setString(eagerFetchMode);
   }

   public String getEagerFetchMode() {
      return this.eagerFetchMode.getString();
   }

   public void setEagerFetchMode(int eagerFetchMode) {
      this.eagerFetchMode.set(eagerFetchMode);
   }

   public int getEagerFetchModeConstant() {
      return this.eagerFetchMode.get();
   }

   public void setSubclassFetchMode(String subclassFetchMode) {
      this.subclassFetchMode.setString(subclassFetchMode);
   }

   public String getSubclassFetchMode() {
      return this.subclassFetchMode.getString();
   }

   public void setSubclassFetchMode(int subclassFetchMode) {
      this.subclassFetchMode.set(subclassFetchMode);
   }

   public int getSubclassFetchModeConstant() {
      return this.subclassFetchMode.get();
   }

   public void setLRSSize(String lrsSize) {
      this.lrsSize.setString(lrsSize);
   }

   public String getLRSSize() {
      return this.lrsSize.getString();
   }

   public void setLRSSize(int lrsSize) {
      this.lrsSize.set(lrsSize);
   }

   public int getLRSSizeConstant() {
      return this.lrsSize.get();
   }

   public void setSynchronizeMappings(String synchronizeMappings) {
      this.synchronizeMappings.set(synchronizeMappings);
   }

   public String getSynchronizeMappings() {
      return this.synchronizeMappings.get();
   }

   public void setJDBCListeners(String jdbcListeners) {
      this.jdbcListenerPlugins.setString(jdbcListeners);
   }

   public String getJDBCListeners() {
      return this.jdbcListenerPlugins.getString();
   }

   public void setJDBCListeners(JDBCListener[] listeners) {
      this.jdbcListenerPlugins.set(listeners);
   }

   public JDBCListener[] getJDBCListenerInstances() {
      if (this.jdbcListenerPlugins.get() == null) {
         this.jdbcListenerPlugins.instantiate(JDBCListener.class, this);
      }

      return (JDBCListener[])((JDBCListener[])this.jdbcListenerPlugins.get());
   }

   public void setConnectionDecorators(String connectionDecorators) {
      this.connectionDecoratorPlugins.setString(connectionDecorators);
   }

   public String getConnectionDecorators() {
      return this.connectionDecoratorPlugins.getString();
   }

   public void setConnectionDecorators(ConnectionDecorator[] decorators) {
      this.connectionDecoratorPlugins.set(decorators);
   }

   public ConnectionDecorator[] getConnectionDecoratorInstances() {
      if (this.connectionDecoratorPlugins.get() == null) {
         this.connectionDecoratorPlugins.instantiate(ConnectionDecorator.class, this);
      }

      return (ConnectionDecorator[])((ConnectionDecorator[])this.connectionDecoratorPlugins.get());
   }

   public void setDBDictionary(String dbdictionary) {
      this.dbdictionaryPlugin.setString(dbdictionary);
   }

   public String getDBDictionary() {
      return this.dbdictionaryPlugin.getString();
   }

   public void setDBDictionary(DBDictionary dbdictionary) {
      if (this.connectionFactory.get() == null && this.connectionFactory2.get() == null) {
         this.dbdictionaryPlugin.set(dbdictionary);
      } else {
         throw new IllegalStateException();
      }
   }

   public DBDictionary getDBDictionaryInstance() {
      DBDictionary dbdictionary = (DBDictionary)this.dbdictionaryPlugin.get();
      if (dbdictionary == null) {
         String clsName = this.dbdictionaryPlugin.getClassName();
         String props = this.dbdictionaryPlugin.getProperties();
         if (!StringUtils.isEmpty(clsName)) {
            dbdictionary = DBDictionaryFactory.newDBDictionary(this, (String)clsName, props);
         } else {
            dbdictionary = DBDictionaryFactory.calculateDBDictionary(this, this.getConnectionURL(), this.getConnectionDriverName(), props);
            if (dbdictionary == null) {
               Log log = this.getLog("openjpa.jdbc.JDBC");
               if (log.isTraceEnabled()) {
                  Localizer loc = Localizer.forPackage(JDBCConfigurationImpl.class);
                  log.trace(loc.get("connecting-for-dictionary"));
               }

               DataSource ds = this.createConnectionFactory();
               dbdictionary = DBDictionaryFactory.newDBDictionary(this, (DataSource)this.getDataSource((StoreContext)null, ds), props);
            }
         }

         this.dbdictionaryPlugin.set(dbdictionary, true);
      }

      return dbdictionary;
   }

   public void setUpdateManager(String updateManager) {
      this.updateManagerPlugin.setString(updateManager);
   }

   public String getUpdateManager() {
      return this.updateManagerPlugin.getString();
   }

   public void setUpdateManager(UpdateManager updateManager) {
      this.updateManagerPlugin.set(updateManager);
   }

   public UpdateManager getUpdateManagerInstance() {
      if (this.updateManagerPlugin.get() == null) {
         this.updateManagerPlugin.instantiate(UpdateManager.class, this);
      }

      return (UpdateManager)this.updateManagerPlugin.get();
   }

   public void setDriverDataSource(String driverDataSource) {
      this.driverDataSourcePlugin.setString(driverDataSource);
   }

   public String getDriverDataSource() {
      return this.driverDataSourcePlugin.getString();
   }

   public DriverDataSource newDriverDataSourceInstance() {
      return (DriverDataSource)this.driverDataSourcePlugin.instantiate(DriverDataSource.class, this);
   }

   public void setSchemaFactory(String schemaFactory) {
      this.schemaFactoryPlugin.setString(schemaFactory);
   }

   public String getSchemaFactory() {
      return this.schemaFactoryPlugin.getString();
   }

   public void setSchemaFactory(SchemaFactory schemaFactory) {
      this.schemaFactoryPlugin.set(schemaFactory);
   }

   public SchemaFactory getSchemaFactoryInstance() {
      if (this.schemaFactoryPlugin.get() == null) {
         this.schemaFactoryPlugin.instantiate(SchemaFactory.class, this);
      }

      return (SchemaFactory)this.schemaFactoryPlugin.get();
   }

   public void setSQLFactory(String sqlFactory) {
      this.sqlFactoryPlugin.setString(sqlFactory);
   }

   public String getSQLFactory() {
      return this.sqlFactoryPlugin.getString();
   }

   public void setSQLFactory(SQLFactory sqlFactory) {
      this.sqlFactoryPlugin.set(sqlFactory);
   }

   public SQLFactory getSQLFactoryInstance() {
      if (this.sqlFactoryPlugin.get() == null) {
         this.sqlFactoryPlugin.instantiate(SQLFactory.class, this);
      }

      return (SQLFactory)this.sqlFactoryPlugin.get();
   }

   public String getMappingFactory() {
      return this.mappingFactoryPlugin.getString();
   }

   public void setMappingFactory(String mapping) {
      this.mappingFactoryPlugin.setString(mapping);
   }

   public MetaDataFactory newMetaDataFactoryInstance() {
      return this.mappingFactoryPlugin.instantiateMetaDataFactory(this, this.metaFactoryPlugin, this.getMapping());
   }

   public void setMappingDefaults(String mapping) {
      this.mappingDefaultsPlugin.setString(mapping);
   }

   public String getMappingDefaults() {
      return this.mappingDefaultsPlugin.getString();
   }

   public void setMappingDefaults(MappingDefaults mapping) {
      this.mappingDefaultsPlugin.set(mapping);
   }

   public MappingDefaults getMappingDefaultsInstance() {
      if (this.mappingDefaultsPlugin.get() == null) {
         this.mappingDefaultsPlugin.instantiate(MappingDefaults.class, this);
      }

      return (MappingDefaults)this.mappingDefaultsPlugin.get();
   }

   public MappingRepository getMappingRepositoryInstance() {
      return (MappingRepository)this.getMetaDataRepositoryInstance();
   }

   public MappingRepository newMappingRepositoryInstance() {
      return (MappingRepository)this.newMetaDataRepositoryInstance();
   }

   public BrokerImpl newBrokerInstance(String user, String pass) {
      BrokerImpl broker = super.newBrokerInstance(user, pass);
      if (broker != null && user != null && this.firstUser == null) {
         this.firstUser = user;
         this.firstPass = pass;
      }

      return broker;
   }

   public Object getConnectionFactory() {
      if (this.dataSource == null) {
         DecoratingDataSource ds = this.createConnectionFactory();
         this.dataSource = DataSourceFactory.installDBDictionary(this.getDBDictionaryInstance(), ds, this, false);
      }

      return this.dataSource;
   }

   public void setConnectionFactory(Object factory) {
      if (factory != this.connectionFactory.get()) {
         if (factory != null) {
            DecoratingDataSource ds = this.setupConnectionFactory((DataSource)factory, false);
            this.dataSource = DataSourceFactory.installDBDictionary(this.getDBDictionaryInstance(), ds, this, false);
         } else {
            this.connectionFactory.set((Object)null);
         }

      }
   }

   private DecoratingDataSource setupConnectionFactory(DataSource ds, boolean factory2) {
      if (ds == null) {
         return null;
      } else {
         DecoratingDataSource dds;
         if (ds instanceof DecoratingDataSource) {
            dds = (DecoratingDataSource)ds;
         } else {
            dds = DataSourceFactory.decorateDataSource(ds, this, factory2);
         }

         if (!factory2 && this.connectionFactory.get() != ds) {
            this.connectionFactory.set(dds, true);
         } else if (factory2 && this.connectionFactory2.get() != ds) {
            this.connectionFactory2.set(dds, true);
         }

         return dds;
      }
   }

   public Object getConnectionFactory2() {
      if (this.dataSource2 == null) {
         DataSource ds = (DataSource)super.getConnectionFactory2();
         if (ds == null) {
            String driver = this.getConnection2DriverName();
            if (!StringUtils.isEmpty(driver)) {
               ds = DataSourceFactory.newDataSource(this, true);
            }
         }

         if (ds != null) {
            DecoratingDataSource dds = this.setupConnectionFactory(ds, true);
            this.dataSource2 = DataSourceFactory.installDBDictionary(this.getDBDictionaryInstance(), dds, this, true);
         }
      }

      return this.dataSource2;
   }

   public void setConnectionFactory2(Object factory) {
      if (factory != this.connectionFactory2.get()) {
         if (factory != null) {
            DecoratingDataSource ds = this.setupConnectionFactory((DataSource)factory, true);
            this.dataSource2 = DataSourceFactory.installDBDictionary(this.getDBDictionaryInstance(), ds, this, true);
         } else {
            this.connectionFactory2.set((Object)null);
         }

      }
   }

   private DecoratingDataSource createConnectionFactory() {
      DataSource ds = (DataSource)this.connectionFactory.get();
      if (ds != null) {
         return this.setupConnectionFactory(ds, false);
      } else {
         ds = (DataSource)super.getConnectionFactory();
         if (ds == null) {
            ds = DataSourceFactory.newDataSource(this, false);
         }

         return this.setupConnectionFactory(ds, false);
      }
   }

   public DataSource getDataSource(StoreContext ctx) {
      return this.getDataSource(ctx, (DataSource)this.getConnectionFactory());
   }

   public DataSource getDataSource2(StoreContext ctx) {
      DataSource ds = (DataSource)this.getConnectionFactory2();
      if (ds == null) {
         return this.getDataSource(ctx);
      } else {
         String user = this.getConnection2UserName();
         String pass = this.getConnection2Password();
         if (user == null && pass == null) {
            if (ctx == null) {
               user = this.firstUser;
               pass = this.firstPass;
            } else {
               user = ctx.getConnectionUserName();
               pass = ctx.getConnectionPassword();
            }
         }

         return DataSourceFactory.defaultsDataSource(ds, user, pass);
      }
   }

   private DataSource getDataSource(StoreContext ctx, DataSource ds) {
      String user;
      String pass;
      if (ctx == null) {
         user = this.getConnectionUserName();
         if (user == null) {
            user = this.firstUser;
         }

         pass = this.getConnectionPassword();
         if (pass == null) {
            pass = this.firstPass;
         }
      } else {
         user = ctx.getConnectionUserName();
         pass = ctx.getConnectionPassword();
      }

      return DataSourceFactory.defaultsDataSource(ds, user, pass);
   }

   protected void preClose() {
      if (this.dataSource != null) {
         this.getDBDictionaryInstance().closeDataSource(this.dataSource);
         this.connectionFactory.set((Object)null, true);
      }

      if (this.dataSource2 != null) {
         this.getDBDictionaryInstance().closeDataSource(this.dataSource);
         this.connectionFactory2.set((Object)null, true);
      }

      super.preClose();
   }

   protected boolean isInvalidProperty(String propName) {
      if (super.isInvalidProperty(propName)) {
         return true;
      } else {
         String[] prefixes = ProductDerivations.getConfigurationPrefixes();

         for(int i = 0; i < prefixes.length; ++i) {
            if (propName.toLowerCase().startsWith(prefixes[i] + ".jdbc")) {
               return true;
            }
         }

         return false;
      }
   }
}
