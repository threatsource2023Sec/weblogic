package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.AbstractStoreBrokerFactoryBean;
import kodo.conf.descriptor.AggregateListenersBean;
import kodo.conf.descriptor.AutoDetachBean;
import kodo.conf.descriptor.CacheMapBean;
import kodo.conf.descriptor.ClientBrokerFactoryBean;
import kodo.conf.descriptor.ClusterRemoteCommitProviderBean;
import kodo.conf.descriptor.CommonsLogFactoryBean;
import kodo.conf.descriptor.ConcurrentHashMapBean;
import kodo.conf.descriptor.CustomBrokerFactoryBean;
import kodo.conf.descriptor.CustomBrokerImplBean;
import kodo.conf.descriptor.CustomClassResolverBean;
import kodo.conf.descriptor.CustomCompatibilityBean;
import kodo.conf.descriptor.CustomDataCacheManagerBean;
import kodo.conf.descriptor.CustomDetachStateBean;
import kodo.conf.descriptor.CustomLockManagerBean;
import kodo.conf.descriptor.CustomLogBean;
import kodo.conf.descriptor.CustomMetaDataFactoryBean;
import kodo.conf.descriptor.CustomMetaDataRepositoryBean;
import kodo.conf.descriptor.CustomOrphanedKeyActionBean;
import kodo.conf.descriptor.CustomPersistenceServerBean;
import kodo.conf.descriptor.CustomProxyManagerBean;
import kodo.conf.descriptor.CustomQueryCompilationCacheBean;
import kodo.conf.descriptor.CustomRemoteCommitProviderBean;
import kodo.conf.descriptor.CustomSavepointManagerBean;
import kodo.conf.descriptor.CustomSeqBean;
import kodo.conf.descriptor.DataCacheManagerImplBean;
import kodo.conf.descriptor.DataCachesBean;
import kodo.conf.descriptor.DefaultBrokerFactoryBean;
import kodo.conf.descriptor.DefaultBrokerImplBean;
import kodo.conf.descriptor.DefaultClassResolverBean;
import kodo.conf.descriptor.DefaultCompatibilityBean;
import kodo.conf.descriptor.DefaultDataCacheManagerBean;
import kodo.conf.descriptor.DefaultDetachStateBean;
import kodo.conf.descriptor.DefaultLockManagerBean;
import kodo.conf.descriptor.DefaultMetaDataFactoryBean;
import kodo.conf.descriptor.DefaultMetaDataRepositoryBean;
import kodo.conf.descriptor.DefaultOrphanedKeyActionBean;
import kodo.conf.descriptor.DefaultProxyManagerBean;
import kodo.conf.descriptor.DefaultQueryCompilationCacheBean;
import kodo.conf.descriptor.DefaultSavepointManagerBean;
import kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBean;
import kodo.conf.descriptor.DetachOptionsAllBean;
import kodo.conf.descriptor.DetachOptionsFetchGroupsBean;
import kodo.conf.descriptor.DetachOptionsLoadedBean;
import kodo.conf.descriptor.ExceptionOrphanedKeyActionBean;
import kodo.conf.descriptor.ExportProfilingBean;
import kodo.conf.descriptor.FetchGroupsBean;
import kodo.conf.descriptor.FilterListenersBean;
import kodo.conf.descriptor.GUIJMXBean;
import kodo.conf.descriptor.GUIProfilingBean;
import kodo.conf.descriptor.HTTPTransportBean;
import kodo.conf.descriptor.InMemorySavepointManagerBean;
import kodo.conf.descriptor.InverseManagerBean;
import kodo.conf.descriptor.JDOMetaDataFactoryBean;
import kodo.conf.descriptor.JMSRemoteCommitProviderBean;
import kodo.conf.descriptor.JMX2JMXBean;
import kodo.conf.descriptor.KodoBrokerBean;
import kodo.conf.descriptor.KodoCompatibilityBean;
import kodo.conf.descriptor.KodoDataCacheManagerBean;
import kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBean;
import kodo.conf.descriptor.LocalJMXBean;
import kodo.conf.descriptor.LocalProfilingBean;
import kodo.conf.descriptor.Log4JLogFactoryBean;
import kodo.conf.descriptor.LogFactoryImplBean;
import kodo.conf.descriptor.LogOrphanedKeyActionBean;
import kodo.conf.descriptor.MX4J1JMXBean;
import kodo.conf.descriptor.NoneJMXBean;
import kodo.conf.descriptor.NoneLockManagerBean;
import kodo.conf.descriptor.NoneLogFactoryBean;
import kodo.conf.descriptor.NoneOrphanedKeyActionBean;
import kodo.conf.descriptor.NoneProfilingBean;
import kodo.conf.descriptor.ProfilingProxyManagerBean;
import kodo.conf.descriptor.PropertiesBean;
import kodo.conf.descriptor.ProxyManagerImplBean;
import kodo.conf.descriptor.QueryCachesBean;
import kodo.conf.descriptor.RemoteCommitProviderBean;
import kodo.conf.descriptor.SingleJVMExclusiveLockManagerBean;
import kodo.conf.descriptor.SingleJVMRemoteCommitProviderBean;
import kodo.conf.descriptor.StackExecutionContextNameProviderBean;
import kodo.conf.descriptor.TCPRemoteCommitProviderBean;
import kodo.conf.descriptor.TCPTransportBean;
import kodo.conf.descriptor.TimeSeededSeqBean;
import kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBean;
import kodo.conf.descriptor.UserObjectExecutionContextNameProviderBean;
import kodo.conf.descriptor.VersionLockManagerBean;
import kodo.conf.descriptor.WLS81JMXBean;

public interface PersistenceUnitConfigurationBean {
   String getName();

   void setName(String var1);

   AggregateListenersBean getAggregateListeners();

   String getAutoClear();

   void setAutoClear(String var1);

   AutoDetachBean getAutoDetaches();

   DefaultBrokerFactoryBean getDefaultBrokerFactory();

   DefaultBrokerFactoryBean createDefaultBrokerFactory();

   void destroyDefaultBrokerFactory();

   AbstractStoreBrokerFactoryBean getAbstractStoreBrokerFactory();

   AbstractStoreBrokerFactoryBean createAbstractStoreBrokerFactory();

   void destroyAbstractStoreBrokerFactory();

   ClientBrokerFactoryBean getClientBrokerFactory();

   ClientBrokerFactoryBean createClientBrokerFactory();

   void destroyClientBrokerFactory();

   JDBCBrokerFactoryBean getJDBCBrokerFactory();

   JDBCBrokerFactoryBean createJDBCBrokerFactory();

   void destroyJDBCBrokerFactory();

   CustomBrokerFactoryBean getCustomBrokerFactory();

   CustomBrokerFactoryBean createCustomBrokerFactory();

   void destroyCustomBrokerFactory();

   DefaultBrokerImplBean getDefaultBrokerImpl();

   DefaultBrokerImplBean createDefaultBrokerImpl();

   void destroyDefaultBrokerImpl();

   KodoBrokerBean getKodoBroker();

   KodoBrokerBean createKodoBroker();

   void destroyKodoBroker();

   CustomBrokerImplBean getCustomBrokerImpl();

   CustomBrokerImplBean createCustomBrokerImpl();

   void destroyCustomBrokerImpl();

   DefaultClassResolverBean getDefaultClassResolver();

   DefaultClassResolverBean createDefaultClassResolver();

   void destroyDefaultClassResolver();

   CustomClassResolverBean getCustomClassResolver();

   CustomClassResolverBean createCustomClassResolver();

   void destroyCustomClassResolver();

   DefaultCompatibilityBean getDefaultCompatibility();

   DefaultCompatibilityBean createDefaultCompatibility();

   void destroyDefaultCompatibility();

   KodoCompatibilityBean getCompatibility();

   KodoCompatibilityBean createCompatibilty();

   void destroyCompatibility();

   CustomCompatibilityBean getCustomCompatibility();

   CustomCompatibilityBean createCustomCompatibility();

   void destroyCustomCompatibility();

   String getConnection2DriverName();

   void setConnection2DriverName(String var1);

   String getConnection2Password();

   void setConnection2Password(String var1);

   byte[] getConnection2PasswordEncrypted();

   void setConnection2PasswordEncrypted(byte[] var1);

   PropertiesBean getConnection2Properties();

   String getConnection2URL();

   void setConnection2URL(String var1);

   String getConnection2UserName();

   void setConnection2UserName(String var1);

   ConnectionDecoratorsBean getConnectionDecorators();

   String getConnectionDriverName();

   void setConnectionDriverName(String var1);

   String getConnectionFactory2Name();

   void setConnectionFactory2Name(String var1);

   PropertiesBean getConnectionFactory2Properties();

   String getConnectionFactoryMode();

   String getConnectionFactoryName();

   void setConnectionFactoryName(String var1);

   PropertiesBean getConnectionFactoryProperties();

   String getConnectionPassword();

   void setConnectionPassword(String var1);

   byte[] getConnectionPasswordEncrypted();

   void setConnectionPasswordEncrypted(byte[] var1);

   PropertiesBean getConnectionProperties();

   String getConnectionRetainMode();

   String getConnectionURL();

   void setConnectionURL(String var1);

   String getConnectionUserName();

   void setConnectionUserName(String var1);

   DataCachesBean getDataCaches();

   DefaultDataCacheManagerBean getDefaultDataCacheManager();

   DefaultDataCacheManagerBean createDefaultDataCacheManager();

   void destroyDefaultDataCacheManager();

   KodoDataCacheManagerBean getKodoDataCacheManager();

   KodoDataCacheManagerBean createKodoDataCacheManager();

   void destroyKodoDataCacheManager();

   DataCacheManagerImplBean getDataCacheManagerImpl();

   DataCacheManagerImplBean createDataCacheManagerImpl();

   void destroyDataCacheManagerImpl();

   CustomDataCacheManagerBean getCustomDataCacheManager();

   CustomDataCacheManagerBean createCustomDataCacheManager();

   void destroyCustomDataCacheManager();

   int getDataCacheTimeout();

   void setDataCacheTimeout(int var1);

   AccessDictionaryBean getAccessDictionary();

   AccessDictionaryBean createAccessDictionary();

   void destroyAccessDictionary();

   DB2DictionaryBean getDB2Dictionary();

   DB2DictionaryBean createDB2Dictionary();

   void destroyDB2Dictionary();

   DerbyDictionaryBean getDerbyDictionary();

   DerbyDictionaryBean createDerbyDictionary();

   void destroyDerbyDictionary();

   EmpressDictionaryBean getEmpressDictionary();

   EmpressDictionaryBean createEmpressDictionary();

   void destroyEmpressDictionary();

   FoxProDictionaryBean getFoxProDictionary();

   FoxProDictionaryBean createFoxProDictionary();

   void destroyFoxProDictionary();

   HSQLDictionaryBean getHSQLDictionary();

   HSQLDictionaryBean createHSQLDictionary();

   void destroyHSQLDictionary();

   InformixDictionaryBean getInformixDictionary();

   InformixDictionaryBean createInformixDictionary();

   void destroyInformixDictionary();

   JDataStoreDictionaryBean getJDataStoreDictionary();

   JDataStoreDictionaryBean createJDataStoreDictionary();

   void destroyJDataStoreDictionary();

   MySQLDictionaryBean getMySQLDictionary();

   MySQLDictionaryBean createMySQLDictionary();

   void destroyMySQLDictionary();

   OracleDictionaryBean getOracleDictionary();

   OracleDictionaryBean createOracleDictionary();

   void destroyOracleDictionary();

   PointbaseDictionaryBean getPointbaseDictionary();

   PointbaseDictionaryBean createPointbaseDictionary();

   void destroyPointbaseDictionary();

   PostgresDictionaryBean getPostgresDictionary();

   PostgresDictionaryBean createPostgresDictionary();

   void destroyPostgresDictionary();

   SQLServerDictionaryBean getSQLServerDictionary();

   SQLServerDictionaryBean createSQLServerDictionary();

   void destroySQLServerDictionary();

   SybaseDictionaryBean getSybaseDictionary();

   SybaseDictionaryBean createSybaseDictionary();

   void destroySybaseDictionary();

   CustomDictionaryBean getCustomDictionary();

   CustomDictionaryBean createCustomDictionary();

   void destroyCustomDictionary();

   Class[] getDBDictionaryTypes();

   DBDictionaryBean getDBDictionary();

   DBDictionaryBean createDBDictionary(Class var1);

   void destroyDBDictionary();

   DefaultDetachStateBean getDefaultDetachState();

   DefaultDetachStateBean createDefaultDetachState();

   void destroyDefaultDetachState();

   DetachOptionsLoadedBean getDetachOptionsLoaded();

   DetachOptionsLoadedBean createDetachOptionsLoaded();

   void destroyDetachOptionsLoaded();

   DetachOptionsFetchGroupsBean getDetachOptionsFetchGroups();

   DetachOptionsFetchGroupsBean createDetachOptionsFetchGroups();

   void destroyDetachOptionsFetchGroups();

   DetachOptionsAllBean getDetachOptionsAll();

   DetachOptionsAllBean createDetachOptionsAll();

   void destroyDetachOptionsAll();

   CustomDetachStateBean getCustomDetachState();

   CustomDetachStateBean createCustomDetachState();

   void destroyCustomDetachState();

   DefaultDriverDataSourceBean getDefaultDriverDataSource();

   DefaultDriverDataSourceBean createDefaultDriverDataSource();

   void destroyDefaultDriverDataSource();

   KodoPoolingDataSourceBean getKodoPoolingDataSource();

   KodoPoolingDataSourceBean createKodoPoolingDataSource();

   void destroyKodoPoolingDataSource();

   SimpleDriverDataSourceBean getSimpleDriverDataSource();

   SimpleDriverDataSourceBean createSimpleDriverDataSource();

   void destroySimpleDriverDataSource();

   CustomDriverDataSourceBean getCustomDriverDataSource();

   CustomDriverDataSourceBean createCustomDriverDataSource();

   void destroyCustomDriverDataSource();

   boolean getDynamicDataStructs();

   void setDynamicDataStructs(boolean var1);

   String getEagerFetchMode();

   int getFetchBatchSize();

   void setFetchBatchSize(int var1);

   String getFetchDirection();

   FetchGroupsBean getFetchGroups();

   FilterListenersBean getFilterListeners();

   String getFlushBeforeQueries();

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   InverseManagerBean getInverseManager();

   InverseManagerBean createInverseManager();

   void destroyInverseManager();

   JDBCListenersBean getJDBCListeners();

   DefaultLockManagerBean getDefaultLockManager();

   DefaultLockManagerBean createDefaultLockManager();

   void destroyDefaultLockManager();

   PessimisticLockManagerBean getPessimisticLockManager();

   PessimisticLockManagerBean createPessimisticLockManager();

   void destroyPessimisticLockManager();

   NoneLockManagerBean getNoneLockManager();

   NoneLockManagerBean createNoneLockManager();

   void destroyNoneLockManager();

   SingleJVMExclusiveLockManagerBean getSingleJVMExclusiveLockManager();

   SingleJVMExclusiveLockManagerBean createSingleJVMExclusiveLockManager();

   void destroySingleJVMExclusiveLockManager();

   VersionLockManagerBean getVersionLockManager();

   VersionLockManagerBean createVersionLockManager();

   void destroyVersionLockManager();

   CustomLockManagerBean getCustomLockManager();

   CustomLockManagerBean createCustomLockManager();

   void destroyCustomLockManager();

   int getLockTimeout();

   void setLockTimeout(int var1);

   CommonsLogFactoryBean getCommonsLogFactory();

   CommonsLogFactoryBean createCommonsLogFactory();

   void destroyCommonsLogFactory();

   Log4JLogFactoryBean getLog4JLogFactory();

   Log4JLogFactoryBean createLog4JLogFactory();

   void destroyLog4JLogFactory();

   LogFactoryImplBean getLogFactoryImpl();

   LogFactoryImplBean createLogFactoryImpl();

   void destroyLogFactoryImpl();

   NoneLogFactoryBean getNoneLogFactory();

   NoneLogFactoryBean createNoneLogFactory();

   void destroyNoneLogFactory();

   CustomLogBean getCustomLog();

   CustomLogBean createCustomLog();

   void destroyCustomLog();

   String getLRSSize();

   String getMapping();

   void setMapping(String var1);

   DefaultMappingDefaultsBean getDefaultMappingDefaults();

   DefaultMappingDefaultsBean createDefaultMappingDefaults();

   void destroyDefaultMappingDefaults();

   DeprecatedJDOMappingDefaultsBean getDeprecatedJDOMappingDefaults();

   DeprecatedJDOMappingDefaultsBean createDeprecatedJDOMappingDefaults();

   void destroyDeprecatedJDOMappingDefaults();

   MappingDefaultsImplBean getMappingDefaultsImpl();

   MappingDefaultsImplBean createMappingDefaultsImpl();

   void destroyMappingDefaultsImpl();

   PersistenceMappingDefaultsBean getPersistenceMappingDefaults();

   PersistenceMappingDefaultsBean createPersistenceMappingDefaults();

   void destroyPersistenceMappingDefaults();

   CustomMappingDefaultsBean getCustomMappingDefaults();

   CustomMappingDefaultsBean createCustomMappingDefaults();

   void destroyCustomMappingDefaults();

   ExtensionDeprecatedJDOMappingFactoryBean getExtensionDeprecatedJDOMappingFactory();

   ExtensionDeprecatedJDOMappingFactoryBean createExtensionDeprecatedJDOMappingFactory();

   void destroyExtensionDeprecatedJDOMappingFactory();

   KodoPersistenceMappingFactoryBean getKodoPersistenceMappingFactory();

   KodoPersistenceMappingFactoryBean createKodoPersistenceMappingFactory();

   void destroyKodoPersistenceMappingFactory();

   MappingFileDeprecatedJDOMappingFactoryBean getMappingFileDeprecatedJDOMappingFactory();

   MappingFileDeprecatedJDOMappingFactoryBean createMappingFileDeprecatedJDOMappingFactory();

   void destroyMappingFileDeprecatedJDOMappingFactory();

   ORMFileJDORMappingFactoryBean getORMFileJDORMappingFactory();

   ORMFileJDORMappingFactoryBean createORMFileJDORMappingFactory();

   void destroyORMFileJDORMappingFactory();

   TableDeprecatedJDOMappingFactoryBean getTableDeprecatedJDOMappingFactory();

   TableDeprecatedJDOMappingFactoryBean createTableDeprecatedJDOMappingFactory();

   void destroyTableDeprecatedJDOMappingFactory();

   TableJDORMappingFactoryBean getTableJDORMappingFactory();

   TableJDORMappingFactoryBean createTableJDORMappingFactory();

   void destroyTableJDORMappingFactory();

   CustomMappingFactoryBean getCustomMappingFactory();

   CustomMappingFactoryBean createCustomMappingFactory();

   void destroyCustomMappingFactory();

   DefaultMetaDataFactoryBean getDefaultMetaDataFactory();

   DefaultMetaDataFactoryBean createDefaultMetaDataFactory();

   void destroyDefaultMetaDataFactory();

   JDOMetaDataFactoryBean getJDOMetaDataFactory();

   JDOMetaDataFactoryBean createJDOMetaDataFactory();

   void destroyJDOMetaDataFactory();

   DeprecatedJDOMetaDataFactoryBean getDeprecatedJDOMetaDataFactory();

   DeprecatedJDOMetaDataFactoryBean createDeprecatedJDOMetaDataFactory();

   void destroyDeprecatedJDOMetaDataFactory();

   KodoPersistenceMetaDataFactoryBean getKodoPersistenceMetaDataFactory();

   KodoPersistenceMetaDataFactoryBean createKodoPersistenceMetaDataFactory();

   void destroyKodoPersistenceMetaDataFactory();

   CustomMetaDataFactoryBean getCustomMetaDataFactory();

   CustomMetaDataFactoryBean createCustomMetaDataFactory();

   void destroyCustomMetaDataFactory();

   DefaultMetaDataRepositoryBean getDefaultMetaDataRepository();

   DefaultMetaDataRepositoryBean createDefaultMetaDataRepository();

   void destroyDefaultMetaDataRepository();

   KodoMappingRepositoryBean getKodoMappingRepository();

   KodoMappingRepositoryBean createKodoMappingRepository();

   void destroyKodoMappingRepository();

   CustomMetaDataRepositoryBean getCustomMetaDataRepository();

   CustomMetaDataRepositoryBean createCustomMetaDataRepository();

   void destroyCustomMetaDataRepository();

   boolean getMultithreaded();

   void setMultithreaded(boolean var1);

   boolean getNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   boolean getNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   boolean getOptimistic();

   void setOptimistic(boolean var1);

   DefaultOrphanedKeyActionBean getDefaultOrphanedKeyAction();

   DefaultOrphanedKeyActionBean createDefaultOrphanedKeyAction();

   void destroyDefaultOrphanedKeyAction();

   LogOrphanedKeyActionBean getLogOrphanedKeyAction();

   LogOrphanedKeyActionBean createLogOrphanedKeyAction();

   void destroyLogOrphanedKeyAction();

   ExceptionOrphanedKeyActionBean getExceptionOrphanedKeyAction();

   ExceptionOrphanedKeyActionBean createExceptionOrphanedKeyAction();

   void destroyExceptionOrphanedKeyAction();

   NoneOrphanedKeyActionBean getNoneOrphanedKeyAction();

   NoneOrphanedKeyActionBean createNoneOrphanedKeyAction();

   void destroyNoneOrphanedKeyAction();

   CustomOrphanedKeyActionBean getCustomOrphanedKeyAction();

   CustomOrphanedKeyActionBean createCustomOrphanedKeyAction();

   void destroyCustomOrphanedKeyAction();

   HTTPTransportBean getHTTPTransport();

   HTTPTransportBean createHTTPTransport();

   void destroyHTTPTransport();

   TCPTransportBean getTCPTransport();

   TCPTransportBean createTCPTransport();

   void destroyTCPTransport();

   CustomPersistenceServerBean getCustomPersistenceServer();

   CustomPersistenceServerBean createCustomPersistenceServer();

   void destroyCustomPersistenceServer();

   DefaultProxyManagerBean getDefaultProxyManager();

   DefaultProxyManagerBean createDefaultProxyManager();

   void destroyDefaultProxyManager();

   ProfilingProxyManagerBean getProfilingProxyManager();

   ProfilingProxyManagerBean createProfilingProxyManager();

   void destroyProfilingProxyManager();

   ProxyManagerImplBean getProxyManagerImpl();

   ProxyManagerImplBean createProxyManagerImpl();

   void destroyProxyManagerImpl();

   CustomProxyManagerBean getCustomProxyManager();

   CustomProxyManagerBean createCustomProxyManager();

   void destroyCustomProxyManager();

   QueryCachesBean getQueryCaches();

   DefaultQueryCompilationCacheBean getDefaultQueryCompilationCache();

   DefaultQueryCompilationCacheBean createDefaultQueryCompilationCache();

   void destroyDefaultQueryCompilationCache();

   CacheMapBean getCacheMap();

   CacheMapBean createCacheMap();

   void destroyCacheMap();

   ConcurrentHashMapBean getConcurrentHashMap();

   ConcurrentHashMapBean createConcurrentHashMap();

   void destroyConcurrentHashMap();

   CustomQueryCompilationCacheBean getCustomQueryCompilationCache();

   CustomQueryCompilationCacheBean createCustomQueryCompilationCache();

   void destroyCustomQueryCompilationCache();

   String getReadLockLevel();

   JMSRemoteCommitProviderBean getJMSRemoteCommitProvider();

   JMSRemoteCommitProviderBean createJMSRemoteCommitProvider();

   void destroyJMSRemoteCommitProvider();

   SingleJVMRemoteCommitProviderBean getSingleJVMRemoteCommitProvider();

   SingleJVMRemoteCommitProviderBean createSingleJVMRemoteCommitProvider();

   void destroySingleJVMRemoteCommitProvider();

   TCPRemoteCommitProviderBean getTCPRemoteCommitProvider();

   TCPRemoteCommitProviderBean createTCPRemoteCommitProvider();

   void destroyTCPRemoteCommitProvider();

   ClusterRemoteCommitProviderBean getClusterRemoteCommitProvider();

   ClusterRemoteCommitProviderBean createClusterRemoteCommitProvider();

   void destroyClusterRemoteCommitProvider();

   CustomRemoteCommitProviderBean getCustomRemoteCommitProvider();

   CustomRemoteCommitProviderBean createCustomRemoteCommitProvider();

   void destroyCustomRemoteCommitProvider();

   Class[] getRemoteCommitProviderTypes();

   RemoteCommitProviderBean getRemoteCommitProvider();

   RemoteCommitProviderBean createRemoteCommitProvider(Class var1);

   void destroyRemoteCommitProvider();

   String getRestoreState();

   String getResultSetType();

   boolean getRetainState();

   void setRetainState(boolean var1);

   boolean getRetryClassRegistration();

   void setRetryClassRegistration(boolean var1);

   DefaultSavepointManagerBean getDefaultSavepointManager();

   DefaultSavepointManagerBean createDefaultSavepointManager();

   void destroyDefaultSavepointManager();

   InMemorySavepointManagerBean getInMemorySavepointManager();

   InMemorySavepointManagerBean createInMemorySavepointManager();

   void destroyInMemorySavepointManager();

   JDBC3SavepointManagerBean getJDBC3SavepointManager();

   JDBC3SavepointManagerBean createJDBC3SavepointManager();

   void destroyJDBC3SavepointManager();

   OracleSavepointManagerBean getOracleSavepointManager();

   OracleSavepointManagerBean createOracleSavepointManager();

   void destroyOracleSavepointManager();

   CustomSavepointManagerBean getCustomSavepointManager();

   CustomSavepointManagerBean createCustomSavepointManager();

   void destroyCustomSavepointManager();

   String getSchema();

   void setSchema(String var1);

   DefaultSchemaFactoryBean getDefaultSchemaFactory();

   DefaultSchemaFactoryBean createDefaultSchemaFactory();

   void destroyDefaultSchemaFactory();

   DynamicSchemaFactoryBean getDynamicSchemaFactory();

   DynamicSchemaFactoryBean createDynamicSchemaFactory();

   void destroyDynamicSchemaFactory();

   FileSchemaFactoryBean getFileSchemaFactory();

   FileSchemaFactoryBean createFileSchemaFactory();

   void destroyFileSchemaFactory();

   LazySchemaFactoryBean getLazySchemaFactory();

   LazySchemaFactoryBean createLazySchemaFactory();

   void destroyLazySchemaFactory();

   TableSchemaFactoryBean getTableSchemaFactory();

   TableSchemaFactoryBean createTableSchemaFactory();

   void destroyTableSchemaFactory();

   CustomSchemaFactoryBean getCustomSchemaFactory();

   CustomSchemaFactoryBean createCustomSchemaFactory();

   void destroyCustomSchemaFactory();

   SchemasBean getSchemata();

   ClassTableJDBCSeqBean getClassTableJDBCSeq();

   ClassTableJDBCSeqBean createClassTableJDBCSeq();

   void destroyClassTableJDBCSeq();

   NativeJDBCSeqBean getNativeJDBCSeq();

   NativeJDBCSeqBean createNativeJDBCSeq();

   void destroyNativeJDBCSeq();

   TableJDBCSeqBean getTableJDBCSeq();

   TableJDBCSeqBean createTableJDBCSeq();

   void destroyTableJDBCSeq();

   TimeSeededSeqBean getTimeSeededSeq();

   TimeSeededSeqBean createTimeSeededSeq();

   void destroyTimeSeededSeq();

   ValueTableJDBCSeqBean getValueTableJDBCSeq();

   ValueTableJDBCSeqBean createValueTableJDBCSeq();

   void destroyValueTableJDBCSeq();

   CustomSeqBean getCustomSeq();

   CustomSeqBean createCustomSeq();

   void destroyCustomSeq();

   DefaultSQLFactoryBean getDefaultSQLFactory();

   DefaultSQLFactoryBean createDefaultSQLFactory();

   void destroyDefaultSQLFactory();

   KodoSQLFactoryBean getKodoSQLFactory();

   KodoSQLFactoryBean createKodoSQLFactory();

   void destroyKodoSQLFactory();

   CustomSQLFactoryBean getCustomSQLFactory();

   CustomSQLFactoryBean createCustomSQLFactory();

   void destroyCustomSQLFactory();

   String getSubclassFetchMode();

   String getSynchronizeMappings();

   String getTransactionIsolation();

   String getTransactionMode();

   DefaultUpdateManagerBean getDefaultUpdateManager();

   DefaultUpdateManagerBean createDefaultUpdateManager();

   void destroyDefaultUpdateManager();

   ConstraintUpdateManagerBean getConstraintUpdateManager();

   ConstraintUpdateManagerBean createConstraintUpdateManager();

   void destroyConstraintUpdateManager();

   BatchingOperationOrderUpdateManagerBean getBatchingOperationOrderUpdateManager();

   BatchingOperationOrderUpdateManagerBean createBatchingOperationOrderUpdateManager();

   void destroyBatchingOperationOrderUpdateManager();

   OperationOrderUpdateManagerBean getOperationOrderUpdateManager();

   OperationOrderUpdateManagerBean createOperationOrderUpdateManager();

   void destroyOperationOrderUpdateManager();

   TableLockUpdateManagerBean getTableLockUpdateManager();

   TableLockUpdateManagerBean createTableLockUpdateManager();

   void destroyTableLockUpdateManager();

   CustomUpdateManagerBean getCustomUpdateManager();

   CustomUpdateManagerBean createCustomUpdateManager();

   void destroyCustomUpdateManager();

   String getWriteLockLevel();

   StackExecutionContextNameProviderBean getStackExecutionContextNameProvider();

   StackExecutionContextNameProviderBean createStackExecutionContextNameProvider();

   void destroyStackExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderBean getTransactionNameExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderBean createTransactionNameExecutionContextNameProvider();

   void destroyTransactionNameExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderBean getUserObjectExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderBean createUserObjectExecutionContextNameProvider();

   void destroyUserObjectExecutionContextNameProvider();

   NoneJMXBean getNoneJMX();

   NoneJMXBean createNoneJMX();

   void destroyNoneJMX();

   LocalJMXBean getLocalJMX();

   LocalJMXBean createLocalJMX();

   void destroyLocalJMX();

   GUIJMXBean getGUIJMX();

   GUIJMXBean createGUIJMX();

   void destroyGUIJMX();

   JMX2JMXBean getJMX2JMX();

   JMX2JMXBean createJMX2JMX();

   void destroyJMX2JMX();

   MX4J1JMXBean getMX4J1JMX();

   MX4J1JMXBean createMX4J1JMX();

   void destroyMX4J1JMX();

   WLS81JMXBean getWLS81JMX();

   WLS81JMXBean createWLS81JMX();

   void destroyWLS81JMX();

   NoneProfilingBean getNoneProfiling();

   NoneProfilingBean createNoneProfiling();

   void destroyNoneProfiling();

   LocalProfilingBean getLocalProfiling();

   LocalProfilingBean createLocalProfiling();

   void destroyLocalProfiling();

   ExportProfilingBean getExportProfiling();

   ExportProfilingBean createExportProfiling();

   void destroyExportProfiling();

   GUIProfilingBean getGUIProfiling();

   GUIProfilingBean createGUIProfiling();

   void destroyGUIProfiling();
}
