package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AbstractStoreBrokerFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AccessDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AggregateListenersType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AutoDetachType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.BatchingOperationOrderUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CacheMapType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClassTableJdbcSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClientBrokerFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClusterRemoteCommitProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CommonsLogFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConcurrentHashMapType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConnectionDecoratorsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ConstraintUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomBrokerFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomBrokerImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomClassResolverType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomCompatibilityType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomDataCacheManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomDetachStateType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomDriverDataSourceType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomLogType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomMappingDefaultsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomMetaDataFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomMetaDataRepositoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomOrphanedKeyActionType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomPersistenceServerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomProxyManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomQueryCompilationCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomRemoteCommitProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomSavepointManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomSchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomSqlFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DataCacheManagerImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DataCachesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Db2DictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultBrokerFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultBrokerImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultClassResolverType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultCompatibilityType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDataCacheManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDetachStateType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultDriverDataSourceType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultMappingDefaultsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultMetaDataFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultMetaDataRepositoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultOrphanedKeyActionType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultProxyManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultQueryCompilationCacheType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultSavepointManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultSchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultSqlFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DefaultUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DeprecatedJdoMappingDefaultsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DeprecatedJdoMetaDataFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DerbyDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DetachOptionsAllType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DetachOptionsFetchGroupsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DetachOptionsLoadedType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.DynamicSchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.EmpressDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExceptionOrphanedKeyActionType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExportProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExtensionDeprecatedJdoMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FetchGroupsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FileSchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FilterListenersType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FoxproDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GuiProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.HsqlDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.HttpTransportType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InMemorySavepointManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InformixDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.InverseManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdatastoreDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Jdbc3SavepointManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdbcBrokerFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdbcListenersType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JdoMetaDataFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.JmsRemoteCommitProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Jmx2JmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoBrokerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoCompatibilityType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoDataCacheManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoMappingRepositoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoPersistenceMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoPersistenceMetaDataFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoPoolingDataSourceType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoSqlFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LazySchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LocalJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LocalProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Log4JLogFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LogFactoryImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LogOrphanedKeyActionType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MappingDefaultsImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MappingFileDeprecatedJdoMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Mx4J1JmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.MysqlDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NativeJdbcSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneJmxType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneLogFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneOrphanedKeyActionType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.NoneProfilingType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OperationOrderUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OracleDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OracleSavepointManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.OrmFileJdorMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceMappingDefaultsType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PersistenceUnitConfigurationType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PessimisticLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PointbaseDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PostgresDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProfilingProxyManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PropertiesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ProxyManagerImplType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.QueryCachesType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SchemasType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SimpleDriverDataSourceType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SingleJvmExclusiveLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SingleJvmRemoteCommitProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SqlServerDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.StackExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.SybaseDictionaryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableDeprecatedJdoMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableJdbcSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableJdorMappingFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableLockUpdateManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TableSchemaFactoryType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TcpRemoteCommitProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TcpTransportType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TimeSeededSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TransactionNameExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.UserObjectExecutionContextNameProviderType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ValueTableJdbcSeqType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.VersionLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.Wls81JmxType;
import javax.xml.namespace.QName;

public class PersistenceUnitConfigurationTypeImpl extends XmlComplexContentImpl implements PersistenceUnitConfigurationType {
   private static final long serialVersionUID = 1L;
   private static final QName AGGREGATELISTENERS$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "aggregate-listeners");
   private static final QName AUTOCLEAR$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-clear");
   private static final QName AUTODETACHES$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-detaches");
   private static final QName DEFAULTBROKERFACTORY$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-broker-factory");
   private static final QName ABSTRACTSTOREBROKERFACTORY$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "abstract-store-broker-factory");
   private static final QName CLIENTBROKERFACTORY$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "client-broker-factory");
   private static final QName JDBCBROKERFACTORY$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jdbc-broker-factory");
   private static final QName CUSTOMBROKERFACTORY$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-broker-factory");
   private static final QName DEFAULTBROKERIMPL$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-broker-impl");
   private static final QName KODOBROKER$18 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-broker");
   private static final QName CUSTOMBROKERIMPL$20 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-broker-impl");
   private static final QName DEFAULTCLASSRESOLVER$22 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-class-resolver");
   private static final QName CUSTOMCLASSRESOLVER$24 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-class-resolver");
   private static final QName DEFAULTCOMPATIBILITY$26 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-compatibility");
   private static final QName COMPATIBILITY$28 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "compatibility");
   private static final QName CUSTOMCOMPATIBILITY$30 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-compatibility");
   private static final QName CONNECTION2DRIVERNAME$32 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection2-driver-name");
   private static final QName CONNECTION2PASSWORD$34 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection2-password");
   private static final QName CONNECTION2PROPERTIES$36 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection2-properties");
   private static final QName CONNECTION2URL$38 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection2-url");
   private static final QName CONNECTION2USERNAME$40 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection2-user-name");
   private static final QName CONNECTIONDECORATORS$42 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-decorators");
   private static final QName CONNECTIONDRIVERNAME$44 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-driver-name");
   private static final QName CONNECTIONFACTORY2NAME$46 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-factory2-name");
   private static final QName CONNECTIONFACTORY2PROPERTIES$48 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-factory2-properties");
   private static final QName CONNECTIONFACTORYMODE$50 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-factory-mode");
   private static final QName CONNECTIONFACTORYNAME$52 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-factory-name");
   private static final QName CONNECTIONFACTORYPROPERTIES$54 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-factory-properties");
   private static final QName CONNECTIONPASSWORD$56 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-password");
   private static final QName CONNECTIONPROPERTIES$58 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-properties");
   private static final QName CONNECTIONRETAINMODE$60 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-retain-mode");
   private static final QName CONNECTIONURL$62 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-url");
   private static final QName CONNECTIONUSERNAME$64 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "connection-user-name");
   private static final QName DATACACHES$66 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "data-caches");
   private static final QName DEFAULTDATACACHEMANAGER$68 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-data-cache-manager");
   private static final QName KODODATACACHEMANAGER$70 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-data-cache-manager");
   private static final QName DATACACHEMANAGERIMPL$72 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "data-cache-manager-impl");
   private static final QName CUSTOMDATACACHEMANAGER$74 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-data-cache-manager");
   private static final QName DATACACHETIMEOUT$76 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "data-cache-timeout");
   private static final QName ACCESSDICTIONARY$78 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "access-dictionary");
   private static final QName DB2DICTIONARY$80 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "db2-dictionary");
   private static final QName DERBYDICTIONARY$82 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "derby-dictionary");
   private static final QName EMPRESSDICTIONARY$84 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "empress-dictionary");
   private static final QName FOXPRODICTIONARY$86 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "foxpro-dictionary");
   private static final QName HSQLDICTIONARY$88 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "hsql-dictionary");
   private static final QName INFORMIXDICTIONARY$90 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "informix-dictionary");
   private static final QName JDATASTOREDICTIONARY$92 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jdatastore-dictionary");
   private static final QName MYSQLDICTIONARY$94 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mysql-dictionary");
   private static final QName ORACLEDICTIONARY$96 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "oracle-dictionary");
   private static final QName POINTBASEDICTIONARY$98 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "pointbase-dictionary");
   private static final QName POSTGRESDICTIONARY$100 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "postgres-dictionary");
   private static final QName SQLSERVERDICTIONARY$102 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sql-server-dictionary");
   private static final QName SYBASEDICTIONARY$104 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sybase-dictionary");
   private static final QName CUSTOMDICTIONARY$106 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-dictionary");
   private static final QName DEFAULTDETACHSTATE$108 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-detach-state");
   private static final QName DETACHOPTIONSLOADED$110 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detach-options-loaded");
   private static final QName DETACHOPTIONSFETCHGROUPS$112 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detach-options-fetch-groups");
   private static final QName DETACHOPTIONSALL$114 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detach-options-all");
   private static final QName CUSTOMDETACHSTATE$116 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-detach-state");
   private static final QName DEFAULTDRIVERDATASOURCE$118 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-driver-data-source");
   private static final QName KODOPOOLINGDATASOURCE$120 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-pooling-data-source");
   private static final QName SIMPLEDRIVERDATASOURCE$122 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "simple-driver-data-source");
   private static final QName CUSTOMDRIVERDATASOURCE$124 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-driver-data-source");
   private static final QName STACKEXECUTIONCONTEXTNAMEPROVIDER$126 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "stack-execution-context-name-provider");
   private static final QName TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "transaction-name-execution-context-name-provider");
   private static final QName USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "user-object-execution-context-name-provider");
   private static final QName NONEPROFILING$132 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-profiling");
   private static final QName LOCALPROFILING$134 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "local-profiling");
   private static final QName EXPORTPROFILING$136 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "export-profiling");
   private static final QName GUIPROFILING$138 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gui-profiling");
   private static final QName NONEJMX$140 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-jmx");
   private static final QName LOCALJMX$142 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "local-jmx");
   private static final QName GUIJMX$144 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gui-jmx");
   private static final QName JMX2JMX$146 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jmx2-jmx");
   private static final QName MX4J1JMX$148 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mx4j1-jmx");
   private static final QName WLS81JMX$150 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "wls81-jmx");
   private static final QName DYNAMICDATASTRUCTS$152 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "dynamic-data-structs");
   private static final QName EAGERFETCHMODE$154 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "eager-fetch-mode");
   private static final QName FETCHBATCHSIZE$156 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "fetch-batch-size");
   private static final QName FETCHDIRECTION$158 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "fetch-direction");
   private static final QName FETCHGROUPS$160 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "fetch-groups");
   private static final QName FILTERLISTENERS$162 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "filter-listeners");
   private static final QName FLUSHBEFOREQUERIES$164 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "flush-before-queries");
   private static final QName IGNORECHANGES$166 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "ignore-changes");
   private static final QName INVERSEMANAGER$168 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "inverse-manager");
   private static final QName JDBCLISTENERS$170 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jdbc-listeners");
   private static final QName DEFAULTLOCKMANAGER$172 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-lock-manager");
   private static final QName PESSIMISTICLOCKMANAGER$174 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "pessimistic-lock-manager");
   private static final QName NONELOCKMANAGER$176 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-lock-manager");
   private static final QName SINGLEJVMEXCLUSIVELOCKMANAGER$178 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "single-jvm-exclusive-lock-manager");
   private static final QName VERSIONLOCKMANAGER$180 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "version-lock-manager");
   private static final QName CUSTOMLOCKMANAGER$182 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-lock-manager");
   private static final QName LOCKTIMEOUT$184 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lock-timeout");
   private static final QName COMMONSLOGFACTORY$186 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "commons-log-factory");
   private static final QName LOG4JLOGFACTORY$188 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "log4j-log-factory");
   private static final QName LOGFACTORYIMPL$190 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "log-factory-impl");
   private static final QName NONELOGFACTORY$192 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-log-factory");
   private static final QName CUSTOMLOG$194 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-log");
   private static final QName LRSSIZE$196 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lrs-size");
   private static final QName MAPPING$198 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mapping");
   private static final QName DEFAULTMAPPINGDEFAULTS$200 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-mapping-defaults");
   private static final QName DEPRECATEDJDOMAPPINGDEFAULTS$202 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "deprecated-jdo-mapping-defaults");
   private static final QName MAPPINGDEFAULTSIMPL$204 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mapping-defaults-impl");
   private static final QName PERSISTENCEMAPPINGDEFAULTS$206 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "persistence-mapping-defaults");
   private static final QName CUSTOMMAPPINGDEFAULTS$208 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-mapping-defaults");
   private static final QName EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "extension-deprecated-jdo-mapping-factory");
   private static final QName KODOPERSISTENCEMAPPINGFACTORY$212 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-persistence-mapping-factory");
   private static final QName MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "mapping-file-deprecated-jdo-mapping-factory");
   private static final QName ORMFILEJDORMAPPINGFACTORY$216 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "orm-file-jdor-mapping-factory");
   private static final QName TABLEDEPRECATEDJDOMAPPINGFACTORY$218 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-deprecated-jdo-mapping-factory");
   private static final QName TABLEJDORMAPPINGFACTORY$220 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-jdor-mapping-factory");
   private static final QName CUSTOMMAPPINGFACTORY$222 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-mapping-factory");
   private static final QName DEFAULTMETADATAFACTORY$224 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-meta-data-factory");
   private static final QName JDOMETADATAFACTORY$226 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jdo-meta-data-factory");
   private static final QName DEPRECATEDJDOMETADATAFACTORY$228 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "deprecated-jdo-meta-data-factory");
   private static final QName KODOPERSISTENCEMETADATAFACTORY$230 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-persistence-meta-data-factory");
   private static final QName CUSTOMMETADATAFACTORY$232 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-meta-data-factory");
   private static final QName DEFAULTMETADATAREPOSITORY$234 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-meta-data-repository");
   private static final QName KODOMAPPINGREPOSITORY$236 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-mapping-repository");
   private static final QName CUSTOMMETADATAREPOSITORY$238 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-meta-data-repository");
   private static final QName MULTITHREADED$240 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "multithreaded");
   private static final QName NONTRANSACTIONALREAD$242 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "nontransactional-read");
   private static final QName NONTRANSACTIONALWRITE$244 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "nontransactional-write");
   private static final QName OPTIMISTIC$246 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "optimistic");
   private static final QName DEFAULTORPHANEDKEYACTION$248 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-orphaned-key-action");
   private static final QName LOGORPHANEDKEYACTION$250 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "log-orphaned-key-action");
   private static final QName EXCEPTIONORPHANEDKEYACTION$252 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "exception-orphaned-key-action");
   private static final QName NONEORPHANEDKEYACTION$254 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "none-orphaned-key-action");
   private static final QName CUSTOMORPHANEDKEYACTION$256 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-orphaned-key-action");
   private static final QName HTTPTRANSPORT$258 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "http-transport");
   private static final QName TCPTRANSPORT$260 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tcp-transport");
   private static final QName CUSTOMPERSISTENCESERVER$262 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-persistence-server");
   private static final QName DEFAULTPROXYMANAGER$264 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-proxy-manager");
   private static final QName PROFILINGPROXYMANAGER$266 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "profiling-proxy-manager");
   private static final QName PROXYMANAGERIMPL$268 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "proxy-manager-impl");
   private static final QName CUSTOMPROXYMANAGER$270 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-proxy-manager");
   private static final QName QUERYCACHES$272 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "query-caches");
   private static final QName DEFAULTQUERYCOMPILATIONCACHE$274 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-query-compilation-cache");
   private static final QName CACHEMAP$276 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cache-map");
   private static final QName CONCURRENTHASHMAP$278 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "concurrent-hash-map");
   private static final QName CUSTOMQUERYCOMPILATIONCACHE$280 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-query-compilation-cache");
   private static final QName READLOCKLEVEL$282 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "read-lock-level");
   private static final QName JMSREMOTECOMMITPROVIDER$284 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jms-remote-commit-provider");
   private static final QName SINGLEJVMREMOTECOMMITPROVIDER$286 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "single-jvm-remote-commit-provider");
   private static final QName TCPREMOTECOMMITPROVIDER$288 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "tcp-remote-commit-provider");
   private static final QName CLUSTERREMOTECOMMITPROVIDER$290 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cluster-remote-commit-provider");
   private static final QName CUSTOMREMOTECOMMITPROVIDER$292 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-remote-commit-provider");
   private static final QName RESTORESTATE$294 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "restore-state");
   private static final QName RESULTSETTYPE$296 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "result-set-type");
   private static final QName RETAINSTATE$298 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "retain-state");
   private static final QName RETRYCLASSREGISTRATION$300 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "retry-class-registration");
   private static final QName DEFAULTSAVEPOINTMANAGER$302 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-savepoint-manager");
   private static final QName INMEMORYSAVEPOINTMANAGER$304 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "in-memory-savepoint-manager");
   private static final QName JDBC3SAVEPOINTMANAGER$306 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "jdbc3-savepoint-manager");
   private static final QName ORACLESAVEPOINTMANAGER$308 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "oracle-savepoint-manager");
   private static final QName CUSTOMSAVEPOINTMANAGER$310 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-savepoint-manager");
   private static final QName SCHEMA$312 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "schema");
   private static final QName DEFAULTSCHEMAFACTORY$314 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-schema-factory");
   private static final QName DYNAMICSCHEMAFACTORY$316 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "dynamic-schema-factory");
   private static final QName FILESCHEMAFACTORY$318 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "file-schema-factory");
   private static final QName LAZYSCHEMAFACTORY$320 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "lazy-schema-factory");
   private static final QName TABLESCHEMAFACTORY$322 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-schema-factory");
   private static final QName CUSTOMSCHEMAFACTORY$324 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-schema-factory");
   private static final QName SCHEMAS$326 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "schemas");
   private static final QName CLASSTABLEJDBCSEQ$328 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "class-table-jdbc-seq");
   private static final QName NATIVEJDBCSEQ$330 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "native-jdbc-seq");
   private static final QName TABLEJDBCSEQ$332 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-jdbc-seq");
   private static final QName TIMESEEDEDSEQ$334 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "time-seeded-seq");
   private static final QName VALUETABLEJDBCSEQ$336 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "value-table-jdbc-seq");
   private static final QName CUSTOMSEQ$338 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-seq");
   private static final QName DEFAULTSQLFACTORY$340 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-sql-factory");
   private static final QName KODOSQLFACTORY$342 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "kodo-sql-factory");
   private static final QName CUSTOMSQLFACTORY$344 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-sql-factory");
   private static final QName SUBCLASSFETCHMODE$346 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "subclass-fetch-mode");
   private static final QName SYNCHRONIZEMAPPINGS$348 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "synchronize-mappings");
   private static final QName TRANSACTIONISOLATION$350 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "transaction-isolation");
   private static final QName TRANSACTIONMODE$352 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "transaction-mode");
   private static final QName DEFAULTUPDATEMANAGER$354 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "default-update-manager");
   private static final QName CONSTRAINTUPDATEMANAGER$356 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "constraint-update-manager");
   private static final QName BATCHINGOPERATIONORDERUPDATEMANAGER$358 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "batching-operation-order-update-manager");
   private static final QName OPERATIONORDERUPDATEMANAGER$360 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "operation-order-update-manager");
   private static final QName TABLELOCKUPDATEMANAGER$362 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "table-lock-update-manager");
   private static final QName CUSTOMUPDATEMANAGER$364 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-update-manager");
   private static final QName WRITELOCKLEVEL$366 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "write-lock-level");
   private static final QName NAME$368 = new QName("", "name");

   public PersistenceUnitConfigurationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AggregateListenersType getAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AggregateListenersType target = null;
         target = (AggregateListenersType)this.get_store().find_element_user(AGGREGATELISTENERS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AggregateListenersType target = null;
         target = (AggregateListenersType)this.get_store().find_element_user(AGGREGATELISTENERS$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AGGREGATELISTENERS$0) != 0;
      }
   }

   public void setAggregateListeners(AggregateListenersType aggregateListeners) {
      this.generatedSetterHelperImpl(aggregateListeners, AGGREGATELISTENERS$0, 0, (short)1);
   }

   public AggregateListenersType addNewAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AggregateListenersType target = null;
         target = (AggregateListenersType)this.get_store().add_element_user(AGGREGATELISTENERS$0);
         return target;
      }
   }

   public void setNilAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AggregateListenersType target = null;
         target = (AggregateListenersType)this.get_store().find_element_user(AGGREGATELISTENERS$0, 0);
         if (target == null) {
            target = (AggregateListenersType)this.get_store().add_element_user(AGGREGATELISTENERS$0);
         }

         target.setNil();
      }
   }

   public void unsetAggregateListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AGGREGATELISTENERS$0, 0);
      }
   }

   public PersistenceUnitConfigurationType.AutoClear.Enum getAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.AutoClear.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.AutoClear xgetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.AutoClear target = null;
         target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         return target;
      }
   }

   public boolean isNilAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.AutoClear target = null;
         target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOCLEAR$2) != 0;
      }
   }

   public void setAutoClear(PersistenceUnitConfigurationType.AutoClear.Enum autoClear) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTOCLEAR$2);
         }

         target.setEnumValue(autoClear);
      }
   }

   public void xsetAutoClear(PersistenceUnitConfigurationType.AutoClear autoClear) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.AutoClear target = null;
         target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().add_element_user(AUTOCLEAR$2);
         }

         target.set(autoClear);
      }
   }

   public void setNilAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.AutoClear target = null;
         target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.AutoClear)this.get_store().add_element_user(AUTOCLEAR$2);
         }

         target.setNil();
      }
   }

   public void unsetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOCLEAR$2, 0);
      }
   }

   public AutoDetachType getAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutoDetachType target = null;
         target = (AutoDetachType)this.get_store().find_element_user(AUTODETACHES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutoDetachType target = null;
         target = (AutoDetachType)this.get_store().find_element_user(AUTODETACHES$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTODETACHES$4) != 0;
      }
   }

   public void setAutoDetaches(AutoDetachType autoDetaches) {
      this.generatedSetterHelperImpl(autoDetaches, AUTODETACHES$4, 0, (short)1);
   }

   public AutoDetachType addNewAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutoDetachType target = null;
         target = (AutoDetachType)this.get_store().add_element_user(AUTODETACHES$4);
         return target;
      }
   }

   public void setNilAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AutoDetachType target = null;
         target = (AutoDetachType)this.get_store().find_element_user(AUTODETACHES$4, 0);
         if (target == null) {
            target = (AutoDetachType)this.get_store().add_element_user(AUTODETACHES$4);
         }

         target.setNil();
      }
   }

   public void unsetAutoDetaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTODETACHES$4, 0);
      }
   }

   public DefaultBrokerFactoryType getDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerFactoryType target = null;
         target = (DefaultBrokerFactoryType)this.get_store().find_element_user(DEFAULTBROKERFACTORY$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerFactoryType target = null;
         target = (DefaultBrokerFactoryType)this.get_store().find_element_user(DEFAULTBROKERFACTORY$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTBROKERFACTORY$6) != 0;
      }
   }

   public void setDefaultBrokerFactory(DefaultBrokerFactoryType defaultBrokerFactory) {
      this.generatedSetterHelperImpl(defaultBrokerFactory, DEFAULTBROKERFACTORY$6, 0, (short)1);
   }

   public DefaultBrokerFactoryType addNewDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerFactoryType target = null;
         target = (DefaultBrokerFactoryType)this.get_store().add_element_user(DEFAULTBROKERFACTORY$6);
         return target;
      }
   }

   public void setNilDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerFactoryType target = null;
         target = (DefaultBrokerFactoryType)this.get_store().find_element_user(DEFAULTBROKERFACTORY$6, 0);
         if (target == null) {
            target = (DefaultBrokerFactoryType)this.get_store().add_element_user(DEFAULTBROKERFACTORY$6);
         }

         target.setNil();
      }
   }

   public void unsetDefaultBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTBROKERFACTORY$6, 0);
      }
   }

   public AbstractStoreBrokerFactoryType getAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AbstractStoreBrokerFactoryType target = null;
         target = (AbstractStoreBrokerFactoryType)this.get_store().find_element_user(ABSTRACTSTOREBROKERFACTORY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AbstractStoreBrokerFactoryType target = null;
         target = (AbstractStoreBrokerFactoryType)this.get_store().find_element_user(ABSTRACTSTOREBROKERFACTORY$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ABSTRACTSTOREBROKERFACTORY$8) != 0;
      }
   }

   public void setAbstractStoreBrokerFactory(AbstractStoreBrokerFactoryType abstractStoreBrokerFactory) {
      this.generatedSetterHelperImpl(abstractStoreBrokerFactory, ABSTRACTSTOREBROKERFACTORY$8, 0, (short)1);
   }

   public AbstractStoreBrokerFactoryType addNewAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AbstractStoreBrokerFactoryType target = null;
         target = (AbstractStoreBrokerFactoryType)this.get_store().add_element_user(ABSTRACTSTOREBROKERFACTORY$8);
         return target;
      }
   }

   public void setNilAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AbstractStoreBrokerFactoryType target = null;
         target = (AbstractStoreBrokerFactoryType)this.get_store().find_element_user(ABSTRACTSTOREBROKERFACTORY$8, 0);
         if (target == null) {
            target = (AbstractStoreBrokerFactoryType)this.get_store().add_element_user(ABSTRACTSTOREBROKERFACTORY$8);
         }

         target.setNil();
      }
   }

   public void unsetAbstractStoreBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ABSTRACTSTOREBROKERFACTORY$8, 0);
      }
   }

   public ClientBrokerFactoryType getClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientBrokerFactoryType target = null;
         target = (ClientBrokerFactoryType)this.get_store().find_element_user(CLIENTBROKERFACTORY$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientBrokerFactoryType target = null;
         target = (ClientBrokerFactoryType)this.get_store().find_element_user(CLIENTBROKERFACTORY$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTBROKERFACTORY$10) != 0;
      }
   }

   public void setClientBrokerFactory(ClientBrokerFactoryType clientBrokerFactory) {
      this.generatedSetterHelperImpl(clientBrokerFactory, CLIENTBROKERFACTORY$10, 0, (short)1);
   }

   public ClientBrokerFactoryType addNewClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientBrokerFactoryType target = null;
         target = (ClientBrokerFactoryType)this.get_store().add_element_user(CLIENTBROKERFACTORY$10);
         return target;
      }
   }

   public void setNilClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientBrokerFactoryType target = null;
         target = (ClientBrokerFactoryType)this.get_store().find_element_user(CLIENTBROKERFACTORY$10, 0);
         if (target == null) {
            target = (ClientBrokerFactoryType)this.get_store().add_element_user(CLIENTBROKERFACTORY$10);
         }

         target.setNil();
      }
   }

   public void unsetClientBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTBROKERFACTORY$10, 0);
      }
   }

   public JdbcBrokerFactoryType getJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcBrokerFactoryType target = null;
         target = (JdbcBrokerFactoryType)this.get_store().find_element_user(JDBCBROKERFACTORY$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcBrokerFactoryType target = null;
         target = (JdbcBrokerFactoryType)this.get_store().find_element_user(JDBCBROKERFACTORY$12, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBCBROKERFACTORY$12) != 0;
      }
   }

   public void setJdbcBrokerFactory(JdbcBrokerFactoryType jdbcBrokerFactory) {
      this.generatedSetterHelperImpl(jdbcBrokerFactory, JDBCBROKERFACTORY$12, 0, (short)1);
   }

   public JdbcBrokerFactoryType addNewJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcBrokerFactoryType target = null;
         target = (JdbcBrokerFactoryType)this.get_store().add_element_user(JDBCBROKERFACTORY$12);
         return target;
      }
   }

   public void setNilJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcBrokerFactoryType target = null;
         target = (JdbcBrokerFactoryType)this.get_store().find_element_user(JDBCBROKERFACTORY$12, 0);
         if (target == null) {
            target = (JdbcBrokerFactoryType)this.get_store().add_element_user(JDBCBROKERFACTORY$12);
         }

         target.setNil();
      }
   }

   public void unsetJdbcBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBCBROKERFACTORY$12, 0);
      }
   }

   public CustomBrokerFactoryType getCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerFactoryType target = null;
         target = (CustomBrokerFactoryType)this.get_store().find_element_user(CUSTOMBROKERFACTORY$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerFactoryType target = null;
         target = (CustomBrokerFactoryType)this.get_store().find_element_user(CUSTOMBROKERFACTORY$14, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMBROKERFACTORY$14) != 0;
      }
   }

   public void setCustomBrokerFactory(CustomBrokerFactoryType customBrokerFactory) {
      this.generatedSetterHelperImpl(customBrokerFactory, CUSTOMBROKERFACTORY$14, 0, (short)1);
   }

   public CustomBrokerFactoryType addNewCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerFactoryType target = null;
         target = (CustomBrokerFactoryType)this.get_store().add_element_user(CUSTOMBROKERFACTORY$14);
         return target;
      }
   }

   public void setNilCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerFactoryType target = null;
         target = (CustomBrokerFactoryType)this.get_store().find_element_user(CUSTOMBROKERFACTORY$14, 0);
         if (target == null) {
            target = (CustomBrokerFactoryType)this.get_store().add_element_user(CUSTOMBROKERFACTORY$14);
         }

         target.setNil();
      }
   }

   public void unsetCustomBrokerFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMBROKERFACTORY$14, 0);
      }
   }

   public DefaultBrokerImplType getDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerImplType target = null;
         target = (DefaultBrokerImplType)this.get_store().find_element_user(DEFAULTBROKERIMPL$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerImplType target = null;
         target = (DefaultBrokerImplType)this.get_store().find_element_user(DEFAULTBROKERIMPL$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTBROKERIMPL$16) != 0;
      }
   }

   public void setDefaultBrokerImpl(DefaultBrokerImplType defaultBrokerImpl) {
      this.generatedSetterHelperImpl(defaultBrokerImpl, DEFAULTBROKERIMPL$16, 0, (short)1);
   }

   public DefaultBrokerImplType addNewDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerImplType target = null;
         target = (DefaultBrokerImplType)this.get_store().add_element_user(DEFAULTBROKERIMPL$16);
         return target;
      }
   }

   public void setNilDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultBrokerImplType target = null;
         target = (DefaultBrokerImplType)this.get_store().find_element_user(DEFAULTBROKERIMPL$16, 0);
         if (target == null) {
            target = (DefaultBrokerImplType)this.get_store().add_element_user(DEFAULTBROKERIMPL$16);
         }

         target.setNil();
      }
   }

   public void unsetDefaultBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTBROKERIMPL$16, 0);
      }
   }

   public KodoBrokerType getKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoBrokerType target = null;
         target = (KodoBrokerType)this.get_store().find_element_user(KODOBROKER$18, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoBrokerType target = null;
         target = (KodoBrokerType)this.get_store().find_element_user(KODOBROKER$18, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOBROKER$18) != 0;
      }
   }

   public void setKodoBroker(KodoBrokerType kodoBroker) {
      this.generatedSetterHelperImpl(kodoBroker, KODOBROKER$18, 0, (short)1);
   }

   public KodoBrokerType addNewKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoBrokerType target = null;
         target = (KodoBrokerType)this.get_store().add_element_user(KODOBROKER$18);
         return target;
      }
   }

   public void setNilKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoBrokerType target = null;
         target = (KodoBrokerType)this.get_store().find_element_user(KODOBROKER$18, 0);
         if (target == null) {
            target = (KodoBrokerType)this.get_store().add_element_user(KODOBROKER$18);
         }

         target.setNil();
      }
   }

   public void unsetKodoBroker() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOBROKER$18, 0);
      }
   }

   public CustomBrokerImplType getCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerImplType target = null;
         target = (CustomBrokerImplType)this.get_store().find_element_user(CUSTOMBROKERIMPL$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerImplType target = null;
         target = (CustomBrokerImplType)this.get_store().find_element_user(CUSTOMBROKERIMPL$20, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMBROKERIMPL$20) != 0;
      }
   }

   public void setCustomBrokerImpl(CustomBrokerImplType customBrokerImpl) {
      this.generatedSetterHelperImpl(customBrokerImpl, CUSTOMBROKERIMPL$20, 0, (short)1);
   }

   public CustomBrokerImplType addNewCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerImplType target = null;
         target = (CustomBrokerImplType)this.get_store().add_element_user(CUSTOMBROKERIMPL$20);
         return target;
      }
   }

   public void setNilCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomBrokerImplType target = null;
         target = (CustomBrokerImplType)this.get_store().find_element_user(CUSTOMBROKERIMPL$20, 0);
         if (target == null) {
            target = (CustomBrokerImplType)this.get_store().add_element_user(CUSTOMBROKERIMPL$20);
         }

         target.setNil();
      }
   }

   public void unsetCustomBrokerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMBROKERIMPL$20, 0);
      }
   }

   public DefaultClassResolverType getDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultClassResolverType target = null;
         target = (DefaultClassResolverType)this.get_store().find_element_user(DEFAULTCLASSRESOLVER$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultClassResolverType target = null;
         target = (DefaultClassResolverType)this.get_store().find_element_user(DEFAULTCLASSRESOLVER$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTCLASSRESOLVER$22) != 0;
      }
   }

   public void setDefaultClassResolver(DefaultClassResolverType defaultClassResolver) {
      this.generatedSetterHelperImpl(defaultClassResolver, DEFAULTCLASSRESOLVER$22, 0, (short)1);
   }

   public DefaultClassResolverType addNewDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultClassResolverType target = null;
         target = (DefaultClassResolverType)this.get_store().add_element_user(DEFAULTCLASSRESOLVER$22);
         return target;
      }
   }

   public void setNilDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultClassResolverType target = null;
         target = (DefaultClassResolverType)this.get_store().find_element_user(DEFAULTCLASSRESOLVER$22, 0);
         if (target == null) {
            target = (DefaultClassResolverType)this.get_store().add_element_user(DEFAULTCLASSRESOLVER$22);
         }

         target.setNil();
      }
   }

   public void unsetDefaultClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTCLASSRESOLVER$22, 0);
      }
   }

   public CustomClassResolverType getCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomClassResolverType target = null;
         target = (CustomClassResolverType)this.get_store().find_element_user(CUSTOMCLASSRESOLVER$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomClassResolverType target = null;
         target = (CustomClassResolverType)this.get_store().find_element_user(CUSTOMCLASSRESOLVER$24, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMCLASSRESOLVER$24) != 0;
      }
   }

   public void setCustomClassResolver(CustomClassResolverType customClassResolver) {
      this.generatedSetterHelperImpl(customClassResolver, CUSTOMCLASSRESOLVER$24, 0, (short)1);
   }

   public CustomClassResolverType addNewCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomClassResolverType target = null;
         target = (CustomClassResolverType)this.get_store().add_element_user(CUSTOMCLASSRESOLVER$24);
         return target;
      }
   }

   public void setNilCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomClassResolverType target = null;
         target = (CustomClassResolverType)this.get_store().find_element_user(CUSTOMCLASSRESOLVER$24, 0);
         if (target == null) {
            target = (CustomClassResolverType)this.get_store().add_element_user(CUSTOMCLASSRESOLVER$24);
         }

         target.setNil();
      }
   }

   public void unsetCustomClassResolver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMCLASSRESOLVER$24, 0);
      }
   }

   public DefaultCompatibilityType getDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultCompatibilityType target = null;
         target = (DefaultCompatibilityType)this.get_store().find_element_user(DEFAULTCOMPATIBILITY$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultCompatibilityType target = null;
         target = (DefaultCompatibilityType)this.get_store().find_element_user(DEFAULTCOMPATIBILITY$26, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTCOMPATIBILITY$26) != 0;
      }
   }

   public void setDefaultCompatibility(DefaultCompatibilityType defaultCompatibility) {
      this.generatedSetterHelperImpl(defaultCompatibility, DEFAULTCOMPATIBILITY$26, 0, (short)1);
   }

   public DefaultCompatibilityType addNewDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultCompatibilityType target = null;
         target = (DefaultCompatibilityType)this.get_store().add_element_user(DEFAULTCOMPATIBILITY$26);
         return target;
      }
   }

   public void setNilDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultCompatibilityType target = null;
         target = (DefaultCompatibilityType)this.get_store().find_element_user(DEFAULTCOMPATIBILITY$26, 0);
         if (target == null) {
            target = (DefaultCompatibilityType)this.get_store().add_element_user(DEFAULTCOMPATIBILITY$26);
         }

         target.setNil();
      }
   }

   public void unsetDefaultCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTCOMPATIBILITY$26, 0);
      }
   }

   public KodoCompatibilityType getCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoCompatibilityType target = null;
         target = (KodoCompatibilityType)this.get_store().find_element_user(COMPATIBILITY$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoCompatibilityType target = null;
         target = (KodoCompatibilityType)this.get_store().find_element_user(COMPATIBILITY$28, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMPATIBILITY$28) != 0;
      }
   }

   public void setCompatibility(KodoCompatibilityType compatibility) {
      this.generatedSetterHelperImpl(compatibility, COMPATIBILITY$28, 0, (short)1);
   }

   public KodoCompatibilityType addNewCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoCompatibilityType target = null;
         target = (KodoCompatibilityType)this.get_store().add_element_user(COMPATIBILITY$28);
         return target;
      }
   }

   public void setNilCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoCompatibilityType target = null;
         target = (KodoCompatibilityType)this.get_store().find_element_user(COMPATIBILITY$28, 0);
         if (target == null) {
            target = (KodoCompatibilityType)this.get_store().add_element_user(COMPATIBILITY$28);
         }

         target.setNil();
      }
   }

   public void unsetCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMPATIBILITY$28, 0);
      }
   }

   public CustomCompatibilityType getCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomCompatibilityType target = null;
         target = (CustomCompatibilityType)this.get_store().find_element_user(CUSTOMCOMPATIBILITY$30, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomCompatibilityType target = null;
         target = (CustomCompatibilityType)this.get_store().find_element_user(CUSTOMCOMPATIBILITY$30, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMCOMPATIBILITY$30) != 0;
      }
   }

   public void setCustomCompatibility(CustomCompatibilityType customCompatibility) {
      this.generatedSetterHelperImpl(customCompatibility, CUSTOMCOMPATIBILITY$30, 0, (short)1);
   }

   public CustomCompatibilityType addNewCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomCompatibilityType target = null;
         target = (CustomCompatibilityType)this.get_store().add_element_user(CUSTOMCOMPATIBILITY$30);
         return target;
      }
   }

   public void setNilCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomCompatibilityType target = null;
         target = (CustomCompatibilityType)this.get_store().find_element_user(CUSTOMCOMPATIBILITY$30, 0);
         if (target == null) {
            target = (CustomCompatibilityType)this.get_store().add_element_user(CUSTOMCOMPATIBILITY$30);
         }

         target.setNil();
      }
   }

   public void unsetCustomCompatibility() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMCOMPATIBILITY$30, 0);
      }
   }

   public String getConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         return target;
      }
   }

   public boolean isNilConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTION2DRIVERNAME$32) != 0;
      }
   }

   public void setConnection2DriverName(String connection2DriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTION2DRIVERNAME$32);
         }

         target.setStringValue(connection2DriverName);
      }
   }

   public void xsetConnection2DriverName(XmlString connection2DriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2DRIVERNAME$32);
         }

         target.set(connection2DriverName);
      }
   }

   public void setNilConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2DRIVERNAME$32, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2DRIVERNAME$32);
         }

         target.setNil();
      }
   }

   public void unsetConnection2DriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTION2DRIVERNAME$32, 0);
      }
   }

   public String getConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         return target;
      }
   }

   public boolean isNilConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTION2PASSWORD$34) != 0;
      }
   }

   public void setConnection2Password(String connection2Password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTION2PASSWORD$34);
         }

         target.setStringValue(connection2Password);
      }
   }

   public void xsetConnection2Password(XmlString connection2Password) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2PASSWORD$34);
         }

         target.set(connection2Password);
      }
   }

   public void setNilConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2PASSWORD$34, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2PASSWORD$34);
         }

         target.setNil();
      }
   }

   public void unsetConnection2Password() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTION2PASSWORD$34, 0);
      }
   }

   public PropertiesType getConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTION2PROPERTIES$36, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTION2PROPERTIES$36, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTION2PROPERTIES$36) != 0;
      }
   }

   public void setConnection2Properties(PropertiesType connection2Properties) {
      this.generatedSetterHelperImpl(connection2Properties, CONNECTION2PROPERTIES$36, 0, (short)1);
   }

   public PropertiesType addNewConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(CONNECTION2PROPERTIES$36);
         return target;
      }
   }

   public void setNilConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTION2PROPERTIES$36, 0);
         if (target == null) {
            target = (PropertiesType)this.get_store().add_element_user(CONNECTION2PROPERTIES$36);
         }

         target.setNil();
      }
   }

   public void unsetConnection2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTION2PROPERTIES$36, 0);
      }
   }

   public String getConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         return target;
      }
   }

   public boolean isNilConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTION2URL$38) != 0;
      }
   }

   public void setConnection2Url(String connection2Url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTION2URL$38);
         }

         target.setStringValue(connection2Url);
      }
   }

   public void xsetConnection2Url(XmlString connection2Url) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2URL$38);
         }

         target.set(connection2Url);
      }
   }

   public void setNilConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2URL$38, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2URL$38);
         }

         target.setNil();
      }
   }

   public void unsetConnection2Url() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTION2URL$38, 0);
      }
   }

   public String getConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         return target;
      }
   }

   public boolean isNilConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTION2USERNAME$40) != 0;
      }
   }

   public void setConnection2UserName(String connection2UserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTION2USERNAME$40);
         }

         target.setStringValue(connection2UserName);
      }
   }

   public void xsetConnection2UserName(XmlString connection2UserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2USERNAME$40);
         }

         target.set(connection2UserName);
      }
   }

   public void setNilConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTION2USERNAME$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTION2USERNAME$40);
         }

         target.setNil();
      }
   }

   public void unsetConnection2UserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTION2USERNAME$40, 0);
      }
   }

   public ConnectionDecoratorsType getConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDecoratorsType target = null;
         target = (ConnectionDecoratorsType)this.get_store().find_element_user(CONNECTIONDECORATORS$42, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDecoratorsType target = null;
         target = (ConnectionDecoratorsType)this.get_store().find_element_user(CONNECTIONDECORATORS$42, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONDECORATORS$42) != 0;
      }
   }

   public void setConnectionDecorators(ConnectionDecoratorsType connectionDecorators) {
      this.generatedSetterHelperImpl(connectionDecorators, CONNECTIONDECORATORS$42, 0, (short)1);
   }

   public ConnectionDecoratorsType addNewConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDecoratorsType target = null;
         target = (ConnectionDecoratorsType)this.get_store().add_element_user(CONNECTIONDECORATORS$42);
         return target;
      }
   }

   public void setNilConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDecoratorsType target = null;
         target = (ConnectionDecoratorsType)this.get_store().find_element_user(CONNECTIONDECORATORS$42, 0);
         if (target == null) {
            target = (ConnectionDecoratorsType)this.get_store().add_element_user(CONNECTIONDECORATORS$42);
         }

         target.setNil();
      }
   }

   public void unsetConnectionDecorators() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONDECORATORS$42, 0);
      }
   }

   public String getConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         return target;
      }
   }

   public boolean isNilConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONDRIVERNAME$44) != 0;
      }
   }

   public void setConnectionDriverName(String connectionDriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONDRIVERNAME$44);
         }

         target.setStringValue(connectionDriverName);
      }
   }

   public void xsetConnectionDriverName(XmlString connectionDriverName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONDRIVERNAME$44);
         }

         target.set(connectionDriverName);
      }
   }

   public void setNilConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONDRIVERNAME$44, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONDRIVERNAME$44);
         }

         target.setNil();
      }
   }

   public void unsetConnectionDriverName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONDRIVERNAME$44, 0);
      }
   }

   public String getConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         return target;
      }
   }

   public boolean isNilConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORY2NAME$46) != 0;
      }
   }

   public void setConnectionFactory2Name(String connectionFactory2Name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORY2NAME$46);
         }

         target.setStringValue(connectionFactory2Name);
      }
   }

   public void xsetConnectionFactory2Name(XmlString connectionFactory2Name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORY2NAME$46);
         }

         target.set(connectionFactory2Name);
      }
   }

   public void setNilConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORY2NAME$46, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORY2NAME$46);
         }

         target.setNil();
      }
   }

   public void unsetConnectionFactory2Name() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY2NAME$46, 0);
      }
   }

   public PropertiesType getConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORY2PROPERTIES$48, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORY2PROPERTIES$48, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORY2PROPERTIES$48) != 0;
      }
   }

   public void setConnectionFactory2Properties(PropertiesType connectionFactory2Properties) {
      this.generatedSetterHelperImpl(connectionFactory2Properties, CONNECTIONFACTORY2PROPERTIES$48, 0, (short)1);
   }

   public PropertiesType addNewConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(CONNECTIONFACTORY2PROPERTIES$48);
         return target;
      }
   }

   public void setNilConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORY2PROPERTIES$48, 0);
         if (target == null) {
            target = (PropertiesType)this.get_store().add_element_user(CONNECTIONFACTORY2PROPERTIES$48);
         }

         target.setNil();
      }
   }

   public void unsetConnectionFactory2Properties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORY2PROPERTIES$48, 0);
      }
   }

   public PersistenceUnitConfigurationType.ConnectionFactoryMode.Enum getConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.ConnectionFactoryMode.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.ConnectionFactoryMode xgetConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionFactoryMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         return target;
      }
   }

   public boolean isNilConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionFactoryMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYMODE$50) != 0;
      }
   }

   public void setConnectionFactoryMode(PersistenceUnitConfigurationType.ConnectionFactoryMode.Enum connectionFactoryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYMODE$50);
         }

         target.setEnumValue(connectionFactoryMode);
      }
   }

   public void xsetConnectionFactoryMode(PersistenceUnitConfigurationType.ConnectionFactoryMode connectionFactoryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionFactoryMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().add_element_user(CONNECTIONFACTORYMODE$50);
         }

         target.set(connectionFactoryMode);
      }
   }

   public void setNilConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionFactoryMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().find_element_user(CONNECTIONFACTORYMODE$50, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ConnectionFactoryMode)this.get_store().add_element_user(CONNECTIONFACTORYMODE$50);
         }

         target.setNil();
      }
   }

   public void unsetConnectionFactoryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYMODE$50, 0);
      }
   }

   public String getConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         return target;
      }
   }

   public boolean isNilConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYNAME$52) != 0;
      }
   }

   public void setConnectionFactoryName(String connectionFactoryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYNAME$52);
         }

         target.setStringValue(connectionFactoryName);
      }
   }

   public void xsetConnectionFactoryName(XmlString connectionFactoryName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYNAME$52);
         }

         target.set(connectionFactoryName);
      }
   }

   public void setNilConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYNAME$52, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYNAME$52);
         }

         target.setNil();
      }
   }

   public void unsetConnectionFactoryName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYNAME$52, 0);
      }
   }

   public PropertiesType getConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORYPROPERTIES$54, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORYPROPERTIES$54, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONFACTORYPROPERTIES$54) != 0;
      }
   }

   public void setConnectionFactoryProperties(PropertiesType connectionFactoryProperties) {
      this.generatedSetterHelperImpl(connectionFactoryProperties, CONNECTIONFACTORYPROPERTIES$54, 0, (short)1);
   }

   public PropertiesType addNewConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(CONNECTIONFACTORYPROPERTIES$54);
         return target;
      }
   }

   public void setNilConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONFACTORYPROPERTIES$54, 0);
         if (target == null) {
            target = (PropertiesType)this.get_store().add_element_user(CONNECTIONFACTORYPROPERTIES$54);
         }

         target.setNil();
      }
   }

   public void unsetConnectionFactoryProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONFACTORYPROPERTIES$54, 0);
      }
   }

   public String getConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         return target;
      }
   }

   public boolean isNilConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPASSWORD$56) != 0;
      }
   }

   public void setConnectionPassword(String connectionPassword) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONPASSWORD$56);
         }

         target.setStringValue(connectionPassword);
      }
   }

   public void xsetConnectionPassword(XmlString connectionPassword) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONPASSWORD$56);
         }

         target.set(connectionPassword);
      }
   }

   public void setNilConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONPASSWORD$56, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONPASSWORD$56);
         }

         target.setNil();
      }
   }

   public void unsetConnectionPassword() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPASSWORD$56, 0);
      }
   }

   public PropertiesType getConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$58, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$58, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONPROPERTIES$58) != 0;
      }
   }

   public void setConnectionProperties(PropertiesType connectionProperties) {
      this.generatedSetterHelperImpl(connectionProperties, CONNECTIONPROPERTIES$58, 0, (short)1);
   }

   public PropertiesType addNewConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(CONNECTIONPROPERTIES$58);
         return target;
      }
   }

   public void setNilConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(CONNECTIONPROPERTIES$58, 0);
         if (target == null) {
            target = (PropertiesType)this.get_store().add_element_user(CONNECTIONPROPERTIES$58);
         }

         target.setNil();
      }
   }

   public void unsetConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONPROPERTIES$58, 0);
      }
   }

   public PersistenceUnitConfigurationType.ConnectionRetainMode.Enum getConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.ConnectionRetainMode.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.ConnectionRetainMode xgetConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionRetainMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         return target;
      }
   }

   public boolean isNilConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionRetainMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONRETAINMODE$60) != 0;
      }
   }

   public void setConnectionRetainMode(PersistenceUnitConfigurationType.ConnectionRetainMode.Enum connectionRetainMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONRETAINMODE$60);
         }

         target.setEnumValue(connectionRetainMode);
      }
   }

   public void xsetConnectionRetainMode(PersistenceUnitConfigurationType.ConnectionRetainMode connectionRetainMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionRetainMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().add_element_user(CONNECTIONRETAINMODE$60);
         }

         target.set(connectionRetainMode);
      }
   }

   public void setNilConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ConnectionRetainMode target = null;
         target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().find_element_user(CONNECTIONRETAINMODE$60, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ConnectionRetainMode)this.get_store().add_element_user(CONNECTIONRETAINMODE$60);
         }

         target.setNil();
      }
   }

   public void unsetConnectionRetainMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONRETAINMODE$60, 0);
      }
   }

   public String getConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         return target;
      }
   }

   public boolean isNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONURL$62) != 0;
      }
   }

   public void setConnectionUrl(String connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONURL$62);
         }

         target.setStringValue(connectionUrl);
      }
   }

   public void xsetConnectionUrl(XmlString connectionUrl) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$62);
         }

         target.set(connectionUrl);
      }
   }

   public void setNilConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONURL$62, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONURL$62);
         }

         target.setNil();
      }
   }

   public void unsetConnectionUrl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONURL$62, 0);
      }
   }

   public String getConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         return target;
      }
   }

   public boolean isNilConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONUSERNAME$64) != 0;
      }
   }

   public void setConnectionUserName(String connectionUserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONUSERNAME$64);
         }

         target.setStringValue(connectionUserName);
      }
   }

   public void xsetConnectionUserName(XmlString connectionUserName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONUSERNAME$64);
         }

         target.set(connectionUserName);
      }
   }

   public void setNilConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONUSERNAME$64, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONUSERNAME$64);
         }

         target.setNil();
      }
   }

   public void unsetConnectionUserName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONUSERNAME$64, 0);
      }
   }

   public DataCachesType getDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCachesType target = null;
         target = (DataCachesType)this.get_store().find_element_user(DATACACHES$66, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCachesType target = null;
         target = (DataCachesType)this.get_store().find_element_user(DATACACHES$66, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATACACHES$66) != 0;
      }
   }

   public void setDataCaches(DataCachesType dataCaches) {
      this.generatedSetterHelperImpl(dataCaches, DATACACHES$66, 0, (short)1);
   }

   public DataCachesType addNewDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCachesType target = null;
         target = (DataCachesType)this.get_store().add_element_user(DATACACHES$66);
         return target;
      }
   }

   public void setNilDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCachesType target = null;
         target = (DataCachesType)this.get_store().find_element_user(DATACACHES$66, 0);
         if (target == null) {
            target = (DataCachesType)this.get_store().add_element_user(DATACACHES$66);
         }

         target.setNil();
      }
   }

   public void unsetDataCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATACACHES$66, 0);
      }
   }

   public DefaultDataCacheManagerType getDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheManagerType target = null;
         target = (DefaultDataCacheManagerType)this.get_store().find_element_user(DEFAULTDATACACHEMANAGER$68, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheManagerType target = null;
         target = (DefaultDataCacheManagerType)this.get_store().find_element_user(DEFAULTDATACACHEMANAGER$68, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDATACACHEMANAGER$68) != 0;
      }
   }

   public void setDefaultDataCacheManager(DefaultDataCacheManagerType defaultDataCacheManager) {
      this.generatedSetterHelperImpl(defaultDataCacheManager, DEFAULTDATACACHEMANAGER$68, 0, (short)1);
   }

   public DefaultDataCacheManagerType addNewDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheManagerType target = null;
         target = (DefaultDataCacheManagerType)this.get_store().add_element_user(DEFAULTDATACACHEMANAGER$68);
         return target;
      }
   }

   public void setNilDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDataCacheManagerType target = null;
         target = (DefaultDataCacheManagerType)this.get_store().find_element_user(DEFAULTDATACACHEMANAGER$68, 0);
         if (target == null) {
            target = (DefaultDataCacheManagerType)this.get_store().add_element_user(DEFAULTDATACACHEMANAGER$68);
         }

         target.setNil();
      }
   }

   public void unsetDefaultDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDATACACHEMANAGER$68, 0);
      }
   }

   public KodoDataCacheManagerType getKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoDataCacheManagerType target = null;
         target = (KodoDataCacheManagerType)this.get_store().find_element_user(KODODATACACHEMANAGER$70, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoDataCacheManagerType target = null;
         target = (KodoDataCacheManagerType)this.get_store().find_element_user(KODODATACACHEMANAGER$70, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODODATACACHEMANAGER$70) != 0;
      }
   }

   public void setKodoDataCacheManager(KodoDataCacheManagerType kodoDataCacheManager) {
      this.generatedSetterHelperImpl(kodoDataCacheManager, KODODATACACHEMANAGER$70, 0, (short)1);
   }

   public KodoDataCacheManagerType addNewKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoDataCacheManagerType target = null;
         target = (KodoDataCacheManagerType)this.get_store().add_element_user(KODODATACACHEMANAGER$70);
         return target;
      }
   }

   public void setNilKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoDataCacheManagerType target = null;
         target = (KodoDataCacheManagerType)this.get_store().find_element_user(KODODATACACHEMANAGER$70, 0);
         if (target == null) {
            target = (KodoDataCacheManagerType)this.get_store().add_element_user(KODODATACACHEMANAGER$70);
         }

         target.setNil();
      }
   }

   public void unsetKodoDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODODATACACHEMANAGER$70, 0);
      }
   }

   public DataCacheManagerImplType getDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCacheManagerImplType target = null;
         target = (DataCacheManagerImplType)this.get_store().find_element_user(DATACACHEMANAGERIMPL$72, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCacheManagerImplType target = null;
         target = (DataCacheManagerImplType)this.get_store().find_element_user(DATACACHEMANAGERIMPL$72, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATACACHEMANAGERIMPL$72) != 0;
      }
   }

   public void setDataCacheManagerImpl(DataCacheManagerImplType dataCacheManagerImpl) {
      this.generatedSetterHelperImpl(dataCacheManagerImpl, DATACACHEMANAGERIMPL$72, 0, (short)1);
   }

   public DataCacheManagerImplType addNewDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCacheManagerImplType target = null;
         target = (DataCacheManagerImplType)this.get_store().add_element_user(DATACACHEMANAGERIMPL$72);
         return target;
      }
   }

   public void setNilDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DataCacheManagerImplType target = null;
         target = (DataCacheManagerImplType)this.get_store().find_element_user(DATACACHEMANAGERIMPL$72, 0);
         if (target == null) {
            target = (DataCacheManagerImplType)this.get_store().add_element_user(DATACACHEMANAGERIMPL$72);
         }

         target.setNil();
      }
   }

   public void unsetDataCacheManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATACACHEMANAGERIMPL$72, 0);
      }
   }

   public CustomDataCacheManagerType getCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheManagerType target = null;
         target = (CustomDataCacheManagerType)this.get_store().find_element_user(CUSTOMDATACACHEMANAGER$74, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheManagerType target = null;
         target = (CustomDataCacheManagerType)this.get_store().find_element_user(CUSTOMDATACACHEMANAGER$74, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMDATACACHEMANAGER$74) != 0;
      }
   }

   public void setCustomDataCacheManager(CustomDataCacheManagerType customDataCacheManager) {
      this.generatedSetterHelperImpl(customDataCacheManager, CUSTOMDATACACHEMANAGER$74, 0, (short)1);
   }

   public CustomDataCacheManagerType addNewCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheManagerType target = null;
         target = (CustomDataCacheManagerType)this.get_store().add_element_user(CUSTOMDATACACHEMANAGER$74);
         return target;
      }
   }

   public void setNilCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDataCacheManagerType target = null;
         target = (CustomDataCacheManagerType)this.get_store().find_element_user(CUSTOMDATACACHEMANAGER$74, 0);
         if (target == null) {
            target = (CustomDataCacheManagerType)this.get_store().add_element_user(CUSTOMDATACACHEMANAGER$74);
         }

         target.setNil();
      }
   }

   public void unsetCustomDataCacheManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMDATACACHEMANAGER$74, 0);
      }
   }

   public int getDataCacheTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATACACHETIMEOUT$76, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDataCacheTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DATACACHETIMEOUT$76, 0);
         return target;
      }
   }

   public boolean isSetDataCacheTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DATACACHETIMEOUT$76) != 0;
      }
   }

   public void setDataCacheTimeout(int dataCacheTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DATACACHETIMEOUT$76, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DATACACHETIMEOUT$76);
         }

         target.setIntValue(dataCacheTimeout);
      }
   }

   public void xsetDataCacheTimeout(XmlInt dataCacheTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DATACACHETIMEOUT$76, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DATACACHETIMEOUT$76);
         }

         target.set(dataCacheTimeout);
      }
   }

   public void unsetDataCacheTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DATACACHETIMEOUT$76, 0);
      }
   }

   public AccessDictionaryType getAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessDictionaryType target = null;
         target = (AccessDictionaryType)this.get_store().find_element_user(ACCESSDICTIONARY$78, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessDictionaryType target = null;
         target = (AccessDictionaryType)this.get_store().find_element_user(ACCESSDICTIONARY$78, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACCESSDICTIONARY$78) != 0;
      }
   }

   public void setAccessDictionary(AccessDictionaryType accessDictionary) {
      this.generatedSetterHelperImpl(accessDictionary, ACCESSDICTIONARY$78, 0, (short)1);
   }

   public AccessDictionaryType addNewAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessDictionaryType target = null;
         target = (AccessDictionaryType)this.get_store().add_element_user(ACCESSDICTIONARY$78);
         return target;
      }
   }

   public void setNilAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AccessDictionaryType target = null;
         target = (AccessDictionaryType)this.get_store().find_element_user(ACCESSDICTIONARY$78, 0);
         if (target == null) {
            target = (AccessDictionaryType)this.get_store().add_element_user(ACCESSDICTIONARY$78);
         }

         target.setNil();
      }
   }

   public void unsetAccessDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACCESSDICTIONARY$78, 0);
      }
   }

   public Db2DictionaryType getDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Db2DictionaryType target = null;
         target = (Db2DictionaryType)this.get_store().find_element_user(DB2DICTIONARY$80, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Db2DictionaryType target = null;
         target = (Db2DictionaryType)this.get_store().find_element_user(DB2DICTIONARY$80, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DB2DICTIONARY$80) != 0;
      }
   }

   public void setDb2Dictionary(Db2DictionaryType db2Dictionary) {
      this.generatedSetterHelperImpl(db2Dictionary, DB2DICTIONARY$80, 0, (short)1);
   }

   public Db2DictionaryType addNewDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Db2DictionaryType target = null;
         target = (Db2DictionaryType)this.get_store().add_element_user(DB2DICTIONARY$80);
         return target;
      }
   }

   public void setNilDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Db2DictionaryType target = null;
         target = (Db2DictionaryType)this.get_store().find_element_user(DB2DICTIONARY$80, 0);
         if (target == null) {
            target = (Db2DictionaryType)this.get_store().add_element_user(DB2DICTIONARY$80);
         }

         target.setNil();
      }
   }

   public void unsetDb2Dictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DB2DICTIONARY$80, 0);
      }
   }

   public DerbyDictionaryType getDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerbyDictionaryType target = null;
         target = (DerbyDictionaryType)this.get_store().find_element_user(DERBYDICTIONARY$82, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerbyDictionaryType target = null;
         target = (DerbyDictionaryType)this.get_store().find_element_user(DERBYDICTIONARY$82, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DERBYDICTIONARY$82) != 0;
      }
   }

   public void setDerbyDictionary(DerbyDictionaryType derbyDictionary) {
      this.generatedSetterHelperImpl(derbyDictionary, DERBYDICTIONARY$82, 0, (short)1);
   }

   public DerbyDictionaryType addNewDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerbyDictionaryType target = null;
         target = (DerbyDictionaryType)this.get_store().add_element_user(DERBYDICTIONARY$82);
         return target;
      }
   }

   public void setNilDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DerbyDictionaryType target = null;
         target = (DerbyDictionaryType)this.get_store().find_element_user(DERBYDICTIONARY$82, 0);
         if (target == null) {
            target = (DerbyDictionaryType)this.get_store().add_element_user(DERBYDICTIONARY$82);
         }

         target.setNil();
      }
   }

   public void unsetDerbyDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DERBYDICTIONARY$82, 0);
      }
   }

   public EmpressDictionaryType getEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmpressDictionaryType target = null;
         target = (EmpressDictionaryType)this.get_store().find_element_user(EMPRESSDICTIONARY$84, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmpressDictionaryType target = null;
         target = (EmpressDictionaryType)this.get_store().find_element_user(EMPRESSDICTIONARY$84, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EMPRESSDICTIONARY$84) != 0;
      }
   }

   public void setEmpressDictionary(EmpressDictionaryType empressDictionary) {
      this.generatedSetterHelperImpl(empressDictionary, EMPRESSDICTIONARY$84, 0, (short)1);
   }

   public EmpressDictionaryType addNewEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmpressDictionaryType target = null;
         target = (EmpressDictionaryType)this.get_store().add_element_user(EMPRESSDICTIONARY$84);
         return target;
      }
   }

   public void setNilEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EmpressDictionaryType target = null;
         target = (EmpressDictionaryType)this.get_store().find_element_user(EMPRESSDICTIONARY$84, 0);
         if (target == null) {
            target = (EmpressDictionaryType)this.get_store().add_element_user(EMPRESSDICTIONARY$84);
         }

         target.setNil();
      }
   }

   public void unsetEmpressDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EMPRESSDICTIONARY$84, 0);
      }
   }

   public FoxproDictionaryType getFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FoxproDictionaryType target = null;
         target = (FoxproDictionaryType)this.get_store().find_element_user(FOXPRODICTIONARY$86, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FoxproDictionaryType target = null;
         target = (FoxproDictionaryType)this.get_store().find_element_user(FOXPRODICTIONARY$86, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FOXPRODICTIONARY$86) != 0;
      }
   }

   public void setFoxproDictionary(FoxproDictionaryType foxproDictionary) {
      this.generatedSetterHelperImpl(foxproDictionary, FOXPRODICTIONARY$86, 0, (short)1);
   }

   public FoxproDictionaryType addNewFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FoxproDictionaryType target = null;
         target = (FoxproDictionaryType)this.get_store().add_element_user(FOXPRODICTIONARY$86);
         return target;
      }
   }

   public void setNilFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FoxproDictionaryType target = null;
         target = (FoxproDictionaryType)this.get_store().find_element_user(FOXPRODICTIONARY$86, 0);
         if (target == null) {
            target = (FoxproDictionaryType)this.get_store().add_element_user(FOXPRODICTIONARY$86);
         }

         target.setNil();
      }
   }

   public void unsetFoxproDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FOXPRODICTIONARY$86, 0);
      }
   }

   public HsqlDictionaryType getHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HsqlDictionaryType target = null;
         target = (HsqlDictionaryType)this.get_store().find_element_user(HSQLDICTIONARY$88, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HsqlDictionaryType target = null;
         target = (HsqlDictionaryType)this.get_store().find_element_user(HSQLDICTIONARY$88, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HSQLDICTIONARY$88) != 0;
      }
   }

   public void setHsqlDictionary(HsqlDictionaryType hsqlDictionary) {
      this.generatedSetterHelperImpl(hsqlDictionary, HSQLDICTIONARY$88, 0, (short)1);
   }

   public HsqlDictionaryType addNewHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HsqlDictionaryType target = null;
         target = (HsqlDictionaryType)this.get_store().add_element_user(HSQLDICTIONARY$88);
         return target;
      }
   }

   public void setNilHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HsqlDictionaryType target = null;
         target = (HsqlDictionaryType)this.get_store().find_element_user(HSQLDICTIONARY$88, 0);
         if (target == null) {
            target = (HsqlDictionaryType)this.get_store().add_element_user(HSQLDICTIONARY$88);
         }

         target.setNil();
      }
   }

   public void unsetHsqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HSQLDICTIONARY$88, 0);
      }
   }

   public InformixDictionaryType getInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InformixDictionaryType target = null;
         target = (InformixDictionaryType)this.get_store().find_element_user(INFORMIXDICTIONARY$90, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InformixDictionaryType target = null;
         target = (InformixDictionaryType)this.get_store().find_element_user(INFORMIXDICTIONARY$90, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INFORMIXDICTIONARY$90) != 0;
      }
   }

   public void setInformixDictionary(InformixDictionaryType informixDictionary) {
      this.generatedSetterHelperImpl(informixDictionary, INFORMIXDICTIONARY$90, 0, (short)1);
   }

   public InformixDictionaryType addNewInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InformixDictionaryType target = null;
         target = (InformixDictionaryType)this.get_store().add_element_user(INFORMIXDICTIONARY$90);
         return target;
      }
   }

   public void setNilInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InformixDictionaryType target = null;
         target = (InformixDictionaryType)this.get_store().find_element_user(INFORMIXDICTIONARY$90, 0);
         if (target == null) {
            target = (InformixDictionaryType)this.get_store().add_element_user(INFORMIXDICTIONARY$90);
         }

         target.setNil();
      }
   }

   public void unsetInformixDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INFORMIXDICTIONARY$90, 0);
      }
   }

   public JdatastoreDictionaryType getJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdatastoreDictionaryType target = null;
         target = (JdatastoreDictionaryType)this.get_store().find_element_user(JDATASTOREDICTIONARY$92, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdatastoreDictionaryType target = null;
         target = (JdatastoreDictionaryType)this.get_store().find_element_user(JDATASTOREDICTIONARY$92, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDATASTOREDICTIONARY$92) != 0;
      }
   }

   public void setJdatastoreDictionary(JdatastoreDictionaryType jdatastoreDictionary) {
      this.generatedSetterHelperImpl(jdatastoreDictionary, JDATASTOREDICTIONARY$92, 0, (short)1);
   }

   public JdatastoreDictionaryType addNewJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdatastoreDictionaryType target = null;
         target = (JdatastoreDictionaryType)this.get_store().add_element_user(JDATASTOREDICTIONARY$92);
         return target;
      }
   }

   public void setNilJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdatastoreDictionaryType target = null;
         target = (JdatastoreDictionaryType)this.get_store().find_element_user(JDATASTOREDICTIONARY$92, 0);
         if (target == null) {
            target = (JdatastoreDictionaryType)this.get_store().add_element_user(JDATASTOREDICTIONARY$92);
         }

         target.setNil();
      }
   }

   public void unsetJdatastoreDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDATASTOREDICTIONARY$92, 0);
      }
   }

   public MysqlDictionaryType getMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MysqlDictionaryType target = null;
         target = (MysqlDictionaryType)this.get_store().find_element_user(MYSQLDICTIONARY$94, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MysqlDictionaryType target = null;
         target = (MysqlDictionaryType)this.get_store().find_element_user(MYSQLDICTIONARY$94, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MYSQLDICTIONARY$94) != 0;
      }
   }

   public void setMysqlDictionary(MysqlDictionaryType mysqlDictionary) {
      this.generatedSetterHelperImpl(mysqlDictionary, MYSQLDICTIONARY$94, 0, (short)1);
   }

   public MysqlDictionaryType addNewMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MysqlDictionaryType target = null;
         target = (MysqlDictionaryType)this.get_store().add_element_user(MYSQLDICTIONARY$94);
         return target;
      }
   }

   public void setNilMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MysqlDictionaryType target = null;
         target = (MysqlDictionaryType)this.get_store().find_element_user(MYSQLDICTIONARY$94, 0);
         if (target == null) {
            target = (MysqlDictionaryType)this.get_store().add_element_user(MYSQLDICTIONARY$94);
         }

         target.setNil();
      }
   }

   public void unsetMysqlDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MYSQLDICTIONARY$94, 0);
      }
   }

   public OracleDictionaryType getOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleDictionaryType target = null;
         target = (OracleDictionaryType)this.get_store().find_element_user(ORACLEDICTIONARY$96, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleDictionaryType target = null;
         target = (OracleDictionaryType)this.get_store().find_element_user(ORACLEDICTIONARY$96, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORACLEDICTIONARY$96) != 0;
      }
   }

   public void setOracleDictionary(OracleDictionaryType oracleDictionary) {
      this.generatedSetterHelperImpl(oracleDictionary, ORACLEDICTIONARY$96, 0, (short)1);
   }

   public OracleDictionaryType addNewOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleDictionaryType target = null;
         target = (OracleDictionaryType)this.get_store().add_element_user(ORACLEDICTIONARY$96);
         return target;
      }
   }

   public void setNilOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleDictionaryType target = null;
         target = (OracleDictionaryType)this.get_store().find_element_user(ORACLEDICTIONARY$96, 0);
         if (target == null) {
            target = (OracleDictionaryType)this.get_store().add_element_user(ORACLEDICTIONARY$96);
         }

         target.setNil();
      }
   }

   public void unsetOracleDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORACLEDICTIONARY$96, 0);
      }
   }

   public PointbaseDictionaryType getPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PointbaseDictionaryType target = null;
         target = (PointbaseDictionaryType)this.get_store().find_element_user(POINTBASEDICTIONARY$98, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PointbaseDictionaryType target = null;
         target = (PointbaseDictionaryType)this.get_store().find_element_user(POINTBASEDICTIONARY$98, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POINTBASEDICTIONARY$98) != 0;
      }
   }

   public void setPointbaseDictionary(PointbaseDictionaryType pointbaseDictionary) {
      this.generatedSetterHelperImpl(pointbaseDictionary, POINTBASEDICTIONARY$98, 0, (short)1);
   }

   public PointbaseDictionaryType addNewPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PointbaseDictionaryType target = null;
         target = (PointbaseDictionaryType)this.get_store().add_element_user(POINTBASEDICTIONARY$98);
         return target;
      }
   }

   public void setNilPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PointbaseDictionaryType target = null;
         target = (PointbaseDictionaryType)this.get_store().find_element_user(POINTBASEDICTIONARY$98, 0);
         if (target == null) {
            target = (PointbaseDictionaryType)this.get_store().add_element_user(POINTBASEDICTIONARY$98);
         }

         target.setNil();
      }
   }

   public void unsetPointbaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POINTBASEDICTIONARY$98, 0);
      }
   }

   public PostgresDictionaryType getPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PostgresDictionaryType target = null;
         target = (PostgresDictionaryType)this.get_store().find_element_user(POSTGRESDICTIONARY$100, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PostgresDictionaryType target = null;
         target = (PostgresDictionaryType)this.get_store().find_element_user(POSTGRESDICTIONARY$100, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POSTGRESDICTIONARY$100) != 0;
      }
   }

   public void setPostgresDictionary(PostgresDictionaryType postgresDictionary) {
      this.generatedSetterHelperImpl(postgresDictionary, POSTGRESDICTIONARY$100, 0, (short)1);
   }

   public PostgresDictionaryType addNewPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PostgresDictionaryType target = null;
         target = (PostgresDictionaryType)this.get_store().add_element_user(POSTGRESDICTIONARY$100);
         return target;
      }
   }

   public void setNilPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PostgresDictionaryType target = null;
         target = (PostgresDictionaryType)this.get_store().find_element_user(POSTGRESDICTIONARY$100, 0);
         if (target == null) {
            target = (PostgresDictionaryType)this.get_store().add_element_user(POSTGRESDICTIONARY$100);
         }

         target.setNil();
      }
   }

   public void unsetPostgresDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POSTGRESDICTIONARY$100, 0);
      }
   }

   public SqlServerDictionaryType getSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlServerDictionaryType target = null;
         target = (SqlServerDictionaryType)this.get_store().find_element_user(SQLSERVERDICTIONARY$102, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlServerDictionaryType target = null;
         target = (SqlServerDictionaryType)this.get_store().find_element_user(SQLSERVERDICTIONARY$102, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SQLSERVERDICTIONARY$102) != 0;
      }
   }

   public void setSqlServerDictionary(SqlServerDictionaryType sqlServerDictionary) {
      this.generatedSetterHelperImpl(sqlServerDictionary, SQLSERVERDICTIONARY$102, 0, (short)1);
   }

   public SqlServerDictionaryType addNewSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlServerDictionaryType target = null;
         target = (SqlServerDictionaryType)this.get_store().add_element_user(SQLSERVERDICTIONARY$102);
         return target;
      }
   }

   public void setNilSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SqlServerDictionaryType target = null;
         target = (SqlServerDictionaryType)this.get_store().find_element_user(SQLSERVERDICTIONARY$102, 0);
         if (target == null) {
            target = (SqlServerDictionaryType)this.get_store().add_element_user(SQLSERVERDICTIONARY$102);
         }

         target.setNil();
      }
   }

   public void unsetSqlServerDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SQLSERVERDICTIONARY$102, 0);
      }
   }

   public SybaseDictionaryType getSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SybaseDictionaryType target = null;
         target = (SybaseDictionaryType)this.get_store().find_element_user(SYBASEDICTIONARY$104, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SybaseDictionaryType target = null;
         target = (SybaseDictionaryType)this.get_store().find_element_user(SYBASEDICTIONARY$104, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYBASEDICTIONARY$104) != 0;
      }
   }

   public void setSybaseDictionary(SybaseDictionaryType sybaseDictionary) {
      this.generatedSetterHelperImpl(sybaseDictionary, SYBASEDICTIONARY$104, 0, (short)1);
   }

   public SybaseDictionaryType addNewSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SybaseDictionaryType target = null;
         target = (SybaseDictionaryType)this.get_store().add_element_user(SYBASEDICTIONARY$104);
         return target;
      }
   }

   public void setNilSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SybaseDictionaryType target = null;
         target = (SybaseDictionaryType)this.get_store().find_element_user(SYBASEDICTIONARY$104, 0);
         if (target == null) {
            target = (SybaseDictionaryType)this.get_store().add_element_user(SYBASEDICTIONARY$104);
         }

         target.setNil();
      }
   }

   public void unsetSybaseDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYBASEDICTIONARY$104, 0);
      }
   }

   public CustomDictionaryType getCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDictionaryType target = null;
         target = (CustomDictionaryType)this.get_store().find_element_user(CUSTOMDICTIONARY$106, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDictionaryType target = null;
         target = (CustomDictionaryType)this.get_store().find_element_user(CUSTOMDICTIONARY$106, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMDICTIONARY$106) != 0;
      }
   }

   public void setCustomDictionary(CustomDictionaryType customDictionary) {
      this.generatedSetterHelperImpl(customDictionary, CUSTOMDICTIONARY$106, 0, (short)1);
   }

   public CustomDictionaryType addNewCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDictionaryType target = null;
         target = (CustomDictionaryType)this.get_store().add_element_user(CUSTOMDICTIONARY$106);
         return target;
      }
   }

   public void setNilCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDictionaryType target = null;
         target = (CustomDictionaryType)this.get_store().find_element_user(CUSTOMDICTIONARY$106, 0);
         if (target == null) {
            target = (CustomDictionaryType)this.get_store().add_element_user(CUSTOMDICTIONARY$106);
         }

         target.setNil();
      }
   }

   public void unsetCustomDictionary() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMDICTIONARY$106, 0);
      }
   }

   public DefaultDetachStateType getDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDetachStateType target = null;
         target = (DefaultDetachStateType)this.get_store().find_element_user(DEFAULTDETACHSTATE$108, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDetachStateType target = null;
         target = (DefaultDetachStateType)this.get_store().find_element_user(DEFAULTDETACHSTATE$108, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDETACHSTATE$108) != 0;
      }
   }

   public void setDefaultDetachState(DefaultDetachStateType defaultDetachState) {
      this.generatedSetterHelperImpl(defaultDetachState, DEFAULTDETACHSTATE$108, 0, (short)1);
   }

   public DefaultDetachStateType addNewDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDetachStateType target = null;
         target = (DefaultDetachStateType)this.get_store().add_element_user(DEFAULTDETACHSTATE$108);
         return target;
      }
   }

   public void setNilDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDetachStateType target = null;
         target = (DefaultDetachStateType)this.get_store().find_element_user(DEFAULTDETACHSTATE$108, 0);
         if (target == null) {
            target = (DefaultDetachStateType)this.get_store().add_element_user(DEFAULTDETACHSTATE$108);
         }

         target.setNil();
      }
   }

   public void unsetDefaultDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDETACHSTATE$108, 0);
      }
   }

   public DetachOptionsLoadedType getDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsLoadedType target = null;
         target = (DetachOptionsLoadedType)this.get_store().find_element_user(DETACHOPTIONSLOADED$110, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsLoadedType target = null;
         target = (DetachOptionsLoadedType)this.get_store().find_element_user(DETACHOPTIONSLOADED$110, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHOPTIONSLOADED$110) != 0;
      }
   }

   public void setDetachOptionsLoaded(DetachOptionsLoadedType detachOptionsLoaded) {
      this.generatedSetterHelperImpl(detachOptionsLoaded, DETACHOPTIONSLOADED$110, 0, (short)1);
   }

   public DetachOptionsLoadedType addNewDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsLoadedType target = null;
         target = (DetachOptionsLoadedType)this.get_store().add_element_user(DETACHOPTIONSLOADED$110);
         return target;
      }
   }

   public void setNilDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsLoadedType target = null;
         target = (DetachOptionsLoadedType)this.get_store().find_element_user(DETACHOPTIONSLOADED$110, 0);
         if (target == null) {
            target = (DetachOptionsLoadedType)this.get_store().add_element_user(DETACHOPTIONSLOADED$110);
         }

         target.setNil();
      }
   }

   public void unsetDetachOptionsLoaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHOPTIONSLOADED$110, 0);
      }
   }

   public DetachOptionsFetchGroupsType getDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsFetchGroupsType target = null;
         target = (DetachOptionsFetchGroupsType)this.get_store().find_element_user(DETACHOPTIONSFETCHGROUPS$112, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsFetchGroupsType target = null;
         target = (DetachOptionsFetchGroupsType)this.get_store().find_element_user(DETACHOPTIONSFETCHGROUPS$112, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHOPTIONSFETCHGROUPS$112) != 0;
      }
   }

   public void setDetachOptionsFetchGroups(DetachOptionsFetchGroupsType detachOptionsFetchGroups) {
      this.generatedSetterHelperImpl(detachOptionsFetchGroups, DETACHOPTIONSFETCHGROUPS$112, 0, (short)1);
   }

   public DetachOptionsFetchGroupsType addNewDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsFetchGroupsType target = null;
         target = (DetachOptionsFetchGroupsType)this.get_store().add_element_user(DETACHOPTIONSFETCHGROUPS$112);
         return target;
      }
   }

   public void setNilDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsFetchGroupsType target = null;
         target = (DetachOptionsFetchGroupsType)this.get_store().find_element_user(DETACHOPTIONSFETCHGROUPS$112, 0);
         if (target == null) {
            target = (DetachOptionsFetchGroupsType)this.get_store().add_element_user(DETACHOPTIONSFETCHGROUPS$112);
         }

         target.setNil();
      }
   }

   public void unsetDetachOptionsFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHOPTIONSFETCHGROUPS$112, 0);
      }
   }

   public DetachOptionsAllType getDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsAllType target = null;
         target = (DetachOptionsAllType)this.get_store().find_element_user(DETACHOPTIONSALL$114, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsAllType target = null;
         target = (DetachOptionsAllType)this.get_store().find_element_user(DETACHOPTIONSALL$114, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHOPTIONSALL$114) != 0;
      }
   }

   public void setDetachOptionsAll(DetachOptionsAllType detachOptionsAll) {
      this.generatedSetterHelperImpl(detachOptionsAll, DETACHOPTIONSALL$114, 0, (short)1);
   }

   public DetachOptionsAllType addNewDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsAllType target = null;
         target = (DetachOptionsAllType)this.get_store().add_element_user(DETACHOPTIONSALL$114);
         return target;
      }
   }

   public void setNilDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DetachOptionsAllType target = null;
         target = (DetachOptionsAllType)this.get_store().find_element_user(DETACHOPTIONSALL$114, 0);
         if (target == null) {
            target = (DetachOptionsAllType)this.get_store().add_element_user(DETACHOPTIONSALL$114);
         }

         target.setNil();
      }
   }

   public void unsetDetachOptionsAll() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHOPTIONSALL$114, 0);
      }
   }

   public CustomDetachStateType getCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDetachStateType target = null;
         target = (CustomDetachStateType)this.get_store().find_element_user(CUSTOMDETACHSTATE$116, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDetachStateType target = null;
         target = (CustomDetachStateType)this.get_store().find_element_user(CUSTOMDETACHSTATE$116, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMDETACHSTATE$116) != 0;
      }
   }

   public void setCustomDetachState(CustomDetachStateType customDetachState) {
      this.generatedSetterHelperImpl(customDetachState, CUSTOMDETACHSTATE$116, 0, (short)1);
   }

   public CustomDetachStateType addNewCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDetachStateType target = null;
         target = (CustomDetachStateType)this.get_store().add_element_user(CUSTOMDETACHSTATE$116);
         return target;
      }
   }

   public void setNilCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDetachStateType target = null;
         target = (CustomDetachStateType)this.get_store().find_element_user(CUSTOMDETACHSTATE$116, 0);
         if (target == null) {
            target = (CustomDetachStateType)this.get_store().add_element_user(CUSTOMDETACHSTATE$116);
         }

         target.setNil();
      }
   }

   public void unsetCustomDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMDETACHSTATE$116, 0);
      }
   }

   public DefaultDriverDataSourceType getDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDriverDataSourceType target = null;
         target = (DefaultDriverDataSourceType)this.get_store().find_element_user(DEFAULTDRIVERDATASOURCE$118, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDriverDataSourceType target = null;
         target = (DefaultDriverDataSourceType)this.get_store().find_element_user(DEFAULTDRIVERDATASOURCE$118, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDRIVERDATASOURCE$118) != 0;
      }
   }

   public void setDefaultDriverDataSource(DefaultDriverDataSourceType defaultDriverDataSource) {
      this.generatedSetterHelperImpl(defaultDriverDataSource, DEFAULTDRIVERDATASOURCE$118, 0, (short)1);
   }

   public DefaultDriverDataSourceType addNewDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDriverDataSourceType target = null;
         target = (DefaultDriverDataSourceType)this.get_store().add_element_user(DEFAULTDRIVERDATASOURCE$118);
         return target;
      }
   }

   public void setNilDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDriverDataSourceType target = null;
         target = (DefaultDriverDataSourceType)this.get_store().find_element_user(DEFAULTDRIVERDATASOURCE$118, 0);
         if (target == null) {
            target = (DefaultDriverDataSourceType)this.get_store().add_element_user(DEFAULTDRIVERDATASOURCE$118);
         }

         target.setNil();
      }
   }

   public void unsetDefaultDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDRIVERDATASOURCE$118, 0);
      }
   }

   public KodoPoolingDataSourceType getKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPoolingDataSourceType target = null;
         target = (KodoPoolingDataSourceType)this.get_store().find_element_user(KODOPOOLINGDATASOURCE$120, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPoolingDataSourceType target = null;
         target = (KodoPoolingDataSourceType)this.get_store().find_element_user(KODOPOOLINGDATASOURCE$120, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOPOOLINGDATASOURCE$120) != 0;
      }
   }

   public void setKodoPoolingDataSource(KodoPoolingDataSourceType kodoPoolingDataSource) {
      this.generatedSetterHelperImpl(kodoPoolingDataSource, KODOPOOLINGDATASOURCE$120, 0, (short)1);
   }

   public KodoPoolingDataSourceType addNewKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPoolingDataSourceType target = null;
         target = (KodoPoolingDataSourceType)this.get_store().add_element_user(KODOPOOLINGDATASOURCE$120);
         return target;
      }
   }

   public void setNilKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPoolingDataSourceType target = null;
         target = (KodoPoolingDataSourceType)this.get_store().find_element_user(KODOPOOLINGDATASOURCE$120, 0);
         if (target == null) {
            target = (KodoPoolingDataSourceType)this.get_store().add_element_user(KODOPOOLINGDATASOURCE$120);
         }

         target.setNil();
      }
   }

   public void unsetKodoPoolingDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOPOOLINGDATASOURCE$120, 0);
      }
   }

   public SimpleDriverDataSourceType getSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDriverDataSourceType target = null;
         target = (SimpleDriverDataSourceType)this.get_store().find_element_user(SIMPLEDRIVERDATASOURCE$122, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDriverDataSourceType target = null;
         target = (SimpleDriverDataSourceType)this.get_store().find_element_user(SIMPLEDRIVERDATASOURCE$122, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SIMPLEDRIVERDATASOURCE$122) != 0;
      }
   }

   public void setSimpleDriverDataSource(SimpleDriverDataSourceType simpleDriverDataSource) {
      this.generatedSetterHelperImpl(simpleDriverDataSource, SIMPLEDRIVERDATASOURCE$122, 0, (short)1);
   }

   public SimpleDriverDataSourceType addNewSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDriverDataSourceType target = null;
         target = (SimpleDriverDataSourceType)this.get_store().add_element_user(SIMPLEDRIVERDATASOURCE$122);
         return target;
      }
   }

   public void setNilSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleDriverDataSourceType target = null;
         target = (SimpleDriverDataSourceType)this.get_store().find_element_user(SIMPLEDRIVERDATASOURCE$122, 0);
         if (target == null) {
            target = (SimpleDriverDataSourceType)this.get_store().add_element_user(SIMPLEDRIVERDATASOURCE$122);
         }

         target.setNil();
      }
   }

   public void unsetSimpleDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SIMPLEDRIVERDATASOURCE$122, 0);
      }
   }

   public CustomDriverDataSourceType getCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDriverDataSourceType target = null;
         target = (CustomDriverDataSourceType)this.get_store().find_element_user(CUSTOMDRIVERDATASOURCE$124, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDriverDataSourceType target = null;
         target = (CustomDriverDataSourceType)this.get_store().find_element_user(CUSTOMDRIVERDATASOURCE$124, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMDRIVERDATASOURCE$124) != 0;
      }
   }

   public void setCustomDriverDataSource(CustomDriverDataSourceType customDriverDataSource) {
      this.generatedSetterHelperImpl(customDriverDataSource, CUSTOMDRIVERDATASOURCE$124, 0, (short)1);
   }

   public CustomDriverDataSourceType addNewCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDriverDataSourceType target = null;
         target = (CustomDriverDataSourceType)this.get_store().add_element_user(CUSTOMDRIVERDATASOURCE$124);
         return target;
      }
   }

   public void setNilCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomDriverDataSourceType target = null;
         target = (CustomDriverDataSourceType)this.get_store().find_element_user(CUSTOMDRIVERDATASOURCE$124, 0);
         if (target == null) {
            target = (CustomDriverDataSourceType)this.get_store().add_element_user(CUSTOMDRIVERDATASOURCE$124);
         }

         target.setNil();
      }
   }

   public void unsetCustomDriverDataSource() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMDRIVERDATASOURCE$124, 0);
      }
   }

   public StackExecutionContextNameProviderType getStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$126, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$126, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STACKEXECUTIONCONTEXTNAMEPROVIDER$126) != 0;
      }
   }

   public void setStackExecutionContextNameProvider(StackExecutionContextNameProviderType stackExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(stackExecutionContextNameProvider, STACKEXECUTIONCONTEXTNAMEPROVIDER$126, 0, (short)1);
   }

   public StackExecutionContextNameProviderType addNewStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().add_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$126);
         return target;
      }
   }

   public void setNilStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType target = null;
         target = (StackExecutionContextNameProviderType)this.get_store().find_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$126, 0);
         if (target == null) {
            target = (StackExecutionContextNameProviderType)this.get_store().add_element_user(STACKEXECUTIONCONTEXTNAMEPROVIDER$126);
         }

         target.setNil();
      }
   }

   public void unsetStackExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STACKEXECUTIONCONTEXTNAMEPROVIDER$126, 0);
      }
   }

   public TransactionNameExecutionContextNameProviderType getTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128) != 0;
      }
   }

   public void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderType transactionNameExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(transactionNameExecutionContextNameProvider, TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128, 0, (short)1);
   }

   public TransactionNameExecutionContextNameProviderType addNewTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().add_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128);
         return target;
      }
   }

   public void setNilTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionNameExecutionContextNameProviderType target = null;
         target = (TransactionNameExecutionContextNameProviderType)this.get_store().find_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128, 0);
         if (target == null) {
            target = (TransactionNameExecutionContextNameProviderType)this.get_store().add_element_user(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128);
         }

         target.setNil();
      }
   }

   public void unsetTransactionNameExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONNAMEEXECUTIONCONTEXTNAMEPROVIDER$128, 0);
      }
   }

   public UserObjectExecutionContextNameProviderType getUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130) != 0;
      }
   }

   public void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderType userObjectExecutionContextNameProvider) {
      this.generatedSetterHelperImpl(userObjectExecutionContextNameProvider, USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130, 0, (short)1);
   }

   public UserObjectExecutionContextNameProviderType addNewUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().add_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130);
         return target;
      }
   }

   public void setNilUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UserObjectExecutionContextNameProviderType target = null;
         target = (UserObjectExecutionContextNameProviderType)this.get_store().find_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130, 0);
         if (target == null) {
            target = (UserObjectExecutionContextNameProviderType)this.get_store().add_element_user(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130);
         }

         target.setNil();
      }
   }

   public void unsetUserObjectExecutionContextNameProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEROBJECTEXECUTIONCONTEXTNAMEPROVIDER$130, 0);
      }
   }

   public NoneProfilingType getNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$132, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$132, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONEPROFILING$132) != 0;
      }
   }

   public void setNoneProfiling(NoneProfilingType noneProfiling) {
      this.generatedSetterHelperImpl(noneProfiling, NONEPROFILING$132, 0, (short)1);
   }

   public NoneProfilingType addNewNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().add_element_user(NONEPROFILING$132);
         return target;
      }
   }

   public void setNilNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneProfilingType target = null;
         target = (NoneProfilingType)this.get_store().find_element_user(NONEPROFILING$132, 0);
         if (target == null) {
            target = (NoneProfilingType)this.get_store().add_element_user(NONEPROFILING$132);
         }

         target.setNil();
      }
   }

   public void unsetNoneProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONEPROFILING$132, 0);
      }
   }

   public LocalProfilingType getLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$134, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$134, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALPROFILING$134) != 0;
      }
   }

   public void setLocalProfiling(LocalProfilingType localProfiling) {
      this.generatedSetterHelperImpl(localProfiling, LOCALPROFILING$134, 0, (short)1);
   }

   public LocalProfilingType addNewLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().add_element_user(LOCALPROFILING$134);
         return target;
      }
   }

   public void setNilLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalProfilingType target = null;
         target = (LocalProfilingType)this.get_store().find_element_user(LOCALPROFILING$134, 0);
         if (target == null) {
            target = (LocalProfilingType)this.get_store().add_element_user(LOCALPROFILING$134);
         }

         target.setNil();
      }
   }

   public void unsetLocalProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALPROFILING$134, 0);
      }
   }

   public ExportProfilingType getExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$136, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$136, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXPORTPROFILING$136) != 0;
      }
   }

   public void setExportProfiling(ExportProfilingType exportProfiling) {
      this.generatedSetterHelperImpl(exportProfiling, EXPORTPROFILING$136, 0, (short)1);
   }

   public ExportProfilingType addNewExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().add_element_user(EXPORTPROFILING$136);
         return target;
      }
   }

   public void setNilExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExportProfilingType target = null;
         target = (ExportProfilingType)this.get_store().find_element_user(EXPORTPROFILING$136, 0);
         if (target == null) {
            target = (ExportProfilingType)this.get_store().add_element_user(EXPORTPROFILING$136);
         }

         target.setNil();
      }
   }

   public void unsetExportProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXPORTPROFILING$136, 0);
      }
   }

   public GuiProfilingType getGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$138, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$138, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GUIPROFILING$138) != 0;
      }
   }

   public void setGuiProfiling(GuiProfilingType guiProfiling) {
      this.generatedSetterHelperImpl(guiProfiling, GUIPROFILING$138, 0, (short)1);
   }

   public GuiProfilingType addNewGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().add_element_user(GUIPROFILING$138);
         return target;
      }
   }

   public void setNilGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiProfilingType target = null;
         target = (GuiProfilingType)this.get_store().find_element_user(GUIPROFILING$138, 0);
         if (target == null) {
            target = (GuiProfilingType)this.get_store().add_element_user(GUIPROFILING$138);
         }

         target.setNil();
      }
   }

   public void unsetGuiProfiling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GUIPROFILING$138, 0);
      }
   }

   public NoneJmxType getNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$140, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$140, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONEJMX$140) != 0;
      }
   }

   public void setNoneJmx(NoneJmxType noneJmx) {
      this.generatedSetterHelperImpl(noneJmx, NONEJMX$140, 0, (short)1);
   }

   public NoneJmxType addNewNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().add_element_user(NONEJMX$140);
         return target;
      }
   }

   public void setNilNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneJmxType target = null;
         target = (NoneJmxType)this.get_store().find_element_user(NONEJMX$140, 0);
         if (target == null) {
            target = (NoneJmxType)this.get_store().add_element_user(NONEJMX$140);
         }

         target.setNil();
      }
   }

   public void unsetNoneJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONEJMX$140, 0);
      }
   }

   public LocalJmxType getLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$142, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$142, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJMX$142) != 0;
      }
   }

   public void setLocalJmx(LocalJmxType localJmx) {
      this.generatedSetterHelperImpl(localJmx, LOCALJMX$142, 0, (short)1);
   }

   public LocalJmxType addNewLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().add_element_user(LOCALJMX$142);
         return target;
      }
   }

   public void setNilLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LocalJmxType target = null;
         target = (LocalJmxType)this.get_store().find_element_user(LOCALJMX$142, 0);
         if (target == null) {
            target = (LocalJmxType)this.get_store().add_element_user(LOCALJMX$142);
         }

         target.setNil();
      }
   }

   public void unsetLocalJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJMX$142, 0);
      }
   }

   public GuiJmxType getGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$144, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$144, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GUIJMX$144) != 0;
      }
   }

   public void setGuiJmx(GuiJmxType guiJmx) {
      this.generatedSetterHelperImpl(guiJmx, GUIJMX$144, 0, (short)1);
   }

   public GuiJmxType addNewGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().add_element_user(GUIJMX$144);
         return target;
      }
   }

   public void setNilGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GuiJmxType target = null;
         target = (GuiJmxType)this.get_store().find_element_user(GUIJMX$144, 0);
         if (target == null) {
            target = (GuiJmxType)this.get_store().add_element_user(GUIJMX$144);
         }

         target.setNil();
      }
   }

   public void unsetGuiJmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GUIJMX$144, 0);
      }
   }

   public Jmx2JmxType getJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$146, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$146, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMX2JMX$146) != 0;
      }
   }

   public void setJmx2Jmx(Jmx2JmxType jmx2Jmx) {
      this.generatedSetterHelperImpl(jmx2Jmx, JMX2JMX$146, 0, (short)1);
   }

   public Jmx2JmxType addNewJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().add_element_user(JMX2JMX$146);
         return target;
      }
   }

   public void setNilJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jmx2JmxType target = null;
         target = (Jmx2JmxType)this.get_store().find_element_user(JMX2JMX$146, 0);
         if (target == null) {
            target = (Jmx2JmxType)this.get_store().add_element_user(JMX2JMX$146);
         }

         target.setNil();
      }
   }

   public void unsetJmx2Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMX2JMX$146, 0);
      }
   }

   public Mx4J1JmxType getMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$148, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$148, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MX4J1JMX$148) != 0;
      }
   }

   public void setMx4J1Jmx(Mx4J1JmxType mx4J1Jmx) {
      this.generatedSetterHelperImpl(mx4J1Jmx, MX4J1JMX$148, 0, (short)1);
   }

   public Mx4J1JmxType addNewMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().add_element_user(MX4J1JMX$148);
         return target;
      }
   }

   public void setNilMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mx4J1JmxType target = null;
         target = (Mx4J1JmxType)this.get_store().find_element_user(MX4J1JMX$148, 0);
         if (target == null) {
            target = (Mx4J1JmxType)this.get_store().add_element_user(MX4J1JMX$148);
         }

         target.setNil();
      }
   }

   public void unsetMx4J1Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MX4J1JMX$148, 0);
      }
   }

   public Wls81JmxType getWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$150, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$150, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLS81JMX$150) != 0;
      }
   }

   public void setWls81Jmx(Wls81JmxType wls81Jmx) {
      this.generatedSetterHelperImpl(wls81Jmx, WLS81JMX$150, 0, (short)1);
   }

   public Wls81JmxType addNewWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().add_element_user(WLS81JMX$150);
         return target;
      }
   }

   public void setNilWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wls81JmxType target = null;
         target = (Wls81JmxType)this.get_store().find_element_user(WLS81JMX$150, 0);
         if (target == null) {
            target = (Wls81JmxType)this.get_store().add_element_user(WLS81JMX$150);
         }

         target.setNil();
      }
   }

   public void unsetWls81Jmx() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLS81JMX$150, 0);
      }
   }

   public boolean getDynamicDataStructs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DYNAMICDATASTRUCTS$152, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDynamicDataStructs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DYNAMICDATASTRUCTS$152, 0);
         return target;
      }
   }

   public boolean isSetDynamicDataStructs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DYNAMICDATASTRUCTS$152) != 0;
      }
   }

   public void setDynamicDataStructs(boolean dynamicDataStructs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DYNAMICDATASTRUCTS$152, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DYNAMICDATASTRUCTS$152);
         }

         target.setBooleanValue(dynamicDataStructs);
      }
   }

   public void xsetDynamicDataStructs(XmlBoolean dynamicDataStructs) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DYNAMICDATASTRUCTS$152, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DYNAMICDATASTRUCTS$152);
         }

         target.set(dynamicDataStructs);
      }
   }

   public void unsetDynamicDataStructs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DYNAMICDATASTRUCTS$152, 0);
      }
   }

   public PersistenceUnitConfigurationType.EagerFetchMode.Enum getEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.EagerFetchMode.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.EagerFetchMode xgetEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.EagerFetchMode target = null;
         target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         return target;
      }
   }

   public boolean isNilEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.EagerFetchMode target = null;
         target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EAGERFETCHMODE$154) != 0;
      }
   }

   public void setEagerFetchMode(PersistenceUnitConfigurationType.EagerFetchMode.Enum eagerFetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EAGERFETCHMODE$154);
         }

         target.setEnumValue(eagerFetchMode);
      }
   }

   public void xsetEagerFetchMode(PersistenceUnitConfigurationType.EagerFetchMode eagerFetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.EagerFetchMode target = null;
         target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().add_element_user(EAGERFETCHMODE$154);
         }

         target.set(eagerFetchMode);
      }
   }

   public void setNilEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.EagerFetchMode target = null;
         target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().find_element_user(EAGERFETCHMODE$154, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.EagerFetchMode)this.get_store().add_element_user(EAGERFETCHMODE$154);
         }

         target.setNil();
      }
   }

   public void unsetEagerFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EAGERFETCHMODE$154, 0);
      }
   }

   public int getFetchBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHBATCHSIZE$156, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetFetchBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FETCHBATCHSIZE$156, 0);
         return target;
      }
   }

   public boolean isSetFetchBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FETCHBATCHSIZE$156) != 0;
      }
   }

   public void setFetchBatchSize(int fetchBatchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHBATCHSIZE$156, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FETCHBATCHSIZE$156);
         }

         target.setIntValue(fetchBatchSize);
      }
   }

   public void xsetFetchBatchSize(XmlInt fetchBatchSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(FETCHBATCHSIZE$156, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(FETCHBATCHSIZE$156);
         }

         target.set(fetchBatchSize);
      }
   }

   public void unsetFetchBatchSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FETCHBATCHSIZE$156, 0);
      }
   }

   public PersistenceUnitConfigurationType.FetchDirection.Enum getFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.FetchDirection.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.FetchDirection xgetFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FetchDirection target = null;
         target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         return target;
      }
   }

   public boolean isNilFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FetchDirection target = null;
         target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FETCHDIRECTION$158) != 0;
      }
   }

   public void setFetchDirection(PersistenceUnitConfigurationType.FetchDirection.Enum fetchDirection) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FETCHDIRECTION$158);
         }

         target.setEnumValue(fetchDirection);
      }
   }

   public void xsetFetchDirection(PersistenceUnitConfigurationType.FetchDirection fetchDirection) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FetchDirection target = null;
         target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().add_element_user(FETCHDIRECTION$158);
         }

         target.set(fetchDirection);
      }
   }

   public void setNilFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FetchDirection target = null;
         target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().find_element_user(FETCHDIRECTION$158, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.FetchDirection)this.get_store().add_element_user(FETCHDIRECTION$158);
         }

         target.setNil();
      }
   }

   public void unsetFetchDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FETCHDIRECTION$158, 0);
      }
   }

   public FetchGroupsType getFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FetchGroupsType target = null;
         target = (FetchGroupsType)this.get_store().find_element_user(FETCHGROUPS$160, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FetchGroupsType target = null;
         target = (FetchGroupsType)this.get_store().find_element_user(FETCHGROUPS$160, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FETCHGROUPS$160) != 0;
      }
   }

   public void setFetchGroups(FetchGroupsType fetchGroups) {
      this.generatedSetterHelperImpl(fetchGroups, FETCHGROUPS$160, 0, (short)1);
   }

   public FetchGroupsType addNewFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FetchGroupsType target = null;
         target = (FetchGroupsType)this.get_store().add_element_user(FETCHGROUPS$160);
         return target;
      }
   }

   public void setNilFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FetchGroupsType target = null;
         target = (FetchGroupsType)this.get_store().find_element_user(FETCHGROUPS$160, 0);
         if (target == null) {
            target = (FetchGroupsType)this.get_store().add_element_user(FETCHGROUPS$160);
         }

         target.setNil();
      }
   }

   public void unsetFetchGroups() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FETCHGROUPS$160, 0);
      }
   }

   public FilterListenersType getFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterListenersType target = null;
         target = (FilterListenersType)this.get_store().find_element_user(FILTERLISTENERS$162, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterListenersType target = null;
         target = (FilterListenersType)this.get_store().find_element_user(FILTERLISTENERS$162, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILTERLISTENERS$162) != 0;
      }
   }

   public void setFilterListeners(FilterListenersType filterListeners) {
      this.generatedSetterHelperImpl(filterListeners, FILTERLISTENERS$162, 0, (short)1);
   }

   public FilterListenersType addNewFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterListenersType target = null;
         target = (FilterListenersType)this.get_store().add_element_user(FILTERLISTENERS$162);
         return target;
      }
   }

   public void setNilFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FilterListenersType target = null;
         target = (FilterListenersType)this.get_store().find_element_user(FILTERLISTENERS$162, 0);
         if (target == null) {
            target = (FilterListenersType)this.get_store().add_element_user(FILTERLISTENERS$162);
         }

         target.setNil();
      }
   }

   public void unsetFilterListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILTERLISTENERS$162, 0);
      }
   }

   public PersistenceUnitConfigurationType.FlushBeforeQueries.Enum getFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.FlushBeforeQueries.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.FlushBeforeQueries xgetFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FlushBeforeQueries target = null;
         target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         return target;
      }
   }

   public boolean isNilFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FlushBeforeQueries target = null;
         target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLUSHBEFOREQUERIES$164) != 0;
      }
   }

   public void setFlushBeforeQueries(PersistenceUnitConfigurationType.FlushBeforeQueries.Enum flushBeforeQueries) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FLUSHBEFOREQUERIES$164);
         }

         target.setEnumValue(flushBeforeQueries);
      }
   }

   public void xsetFlushBeforeQueries(PersistenceUnitConfigurationType.FlushBeforeQueries flushBeforeQueries) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FlushBeforeQueries target = null;
         target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().add_element_user(FLUSHBEFOREQUERIES$164);
         }

         target.set(flushBeforeQueries);
      }
   }

   public void setNilFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.FlushBeforeQueries target = null;
         target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().find_element_user(FLUSHBEFOREQUERIES$164, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.FlushBeforeQueries)this.get_store().add_element_user(FLUSHBEFOREQUERIES$164);
         }

         target.setNil();
      }
   }

   public void unsetFlushBeforeQueries() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLUSHBEFOREQUERIES$164, 0);
      }
   }

   public boolean getIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORECHANGES$166, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORECHANGES$166, 0);
         return target;
      }
   }

   public boolean isSetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNORECHANGES$166) != 0;
      }
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORECHANGES$166, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNORECHANGES$166);
         }

         target.setBooleanValue(ignoreChanges);
      }
   }

   public void xsetIgnoreChanges(XmlBoolean ignoreChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORECHANGES$166, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNORECHANGES$166);
         }

         target.set(ignoreChanges);
      }
   }

   public void unsetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNORECHANGES$166, 0);
      }
   }

   public InverseManagerType getInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InverseManagerType target = null;
         target = (InverseManagerType)this.get_store().find_element_user(INVERSEMANAGER$168, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InverseManagerType target = null;
         target = (InverseManagerType)this.get_store().find_element_user(INVERSEMANAGER$168, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INVERSEMANAGER$168) != 0;
      }
   }

   public void setInverseManager(InverseManagerType inverseManager) {
      this.generatedSetterHelperImpl(inverseManager, INVERSEMANAGER$168, 0, (short)1);
   }

   public InverseManagerType addNewInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InverseManagerType target = null;
         target = (InverseManagerType)this.get_store().add_element_user(INVERSEMANAGER$168);
         return target;
      }
   }

   public void setNilInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InverseManagerType target = null;
         target = (InverseManagerType)this.get_store().find_element_user(INVERSEMANAGER$168, 0);
         if (target == null) {
            target = (InverseManagerType)this.get_store().add_element_user(INVERSEMANAGER$168);
         }

         target.setNil();
      }
   }

   public void unsetInverseManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INVERSEMANAGER$168, 0);
      }
   }

   public JdbcListenersType getJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcListenersType target = null;
         target = (JdbcListenersType)this.get_store().find_element_user(JDBCLISTENERS$170, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcListenersType target = null;
         target = (JdbcListenersType)this.get_store().find_element_user(JDBCLISTENERS$170, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBCLISTENERS$170) != 0;
      }
   }

   public void setJdbcListeners(JdbcListenersType jdbcListeners) {
      this.generatedSetterHelperImpl(jdbcListeners, JDBCLISTENERS$170, 0, (short)1);
   }

   public JdbcListenersType addNewJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcListenersType target = null;
         target = (JdbcListenersType)this.get_store().add_element_user(JDBCLISTENERS$170);
         return target;
      }
   }

   public void setNilJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdbcListenersType target = null;
         target = (JdbcListenersType)this.get_store().find_element_user(JDBCLISTENERS$170, 0);
         if (target == null) {
            target = (JdbcListenersType)this.get_store().add_element_user(JDBCLISTENERS$170);
         }

         target.setNil();
      }
   }

   public void unsetJdbcListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBCLISTENERS$170, 0);
      }
   }

   public DefaultLockManagerType getDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultLockManagerType target = null;
         target = (DefaultLockManagerType)this.get_store().find_element_user(DEFAULTLOCKMANAGER$172, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultLockManagerType target = null;
         target = (DefaultLockManagerType)this.get_store().find_element_user(DEFAULTLOCKMANAGER$172, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTLOCKMANAGER$172) != 0;
      }
   }

   public void setDefaultLockManager(DefaultLockManagerType defaultLockManager) {
      this.generatedSetterHelperImpl(defaultLockManager, DEFAULTLOCKMANAGER$172, 0, (short)1);
   }

   public DefaultLockManagerType addNewDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultLockManagerType target = null;
         target = (DefaultLockManagerType)this.get_store().add_element_user(DEFAULTLOCKMANAGER$172);
         return target;
      }
   }

   public void setNilDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultLockManagerType target = null;
         target = (DefaultLockManagerType)this.get_store().find_element_user(DEFAULTLOCKMANAGER$172, 0);
         if (target == null) {
            target = (DefaultLockManagerType)this.get_store().add_element_user(DEFAULTLOCKMANAGER$172);
         }

         target.setNil();
      }
   }

   public void unsetDefaultLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTLOCKMANAGER$172, 0);
      }
   }

   public PessimisticLockManagerType getPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PessimisticLockManagerType target = null;
         target = (PessimisticLockManagerType)this.get_store().find_element_user(PESSIMISTICLOCKMANAGER$174, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PessimisticLockManagerType target = null;
         target = (PessimisticLockManagerType)this.get_store().find_element_user(PESSIMISTICLOCKMANAGER$174, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PESSIMISTICLOCKMANAGER$174) != 0;
      }
   }

   public void setPessimisticLockManager(PessimisticLockManagerType pessimisticLockManager) {
      this.generatedSetterHelperImpl(pessimisticLockManager, PESSIMISTICLOCKMANAGER$174, 0, (short)1);
   }

   public PessimisticLockManagerType addNewPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PessimisticLockManagerType target = null;
         target = (PessimisticLockManagerType)this.get_store().add_element_user(PESSIMISTICLOCKMANAGER$174);
         return target;
      }
   }

   public void setNilPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PessimisticLockManagerType target = null;
         target = (PessimisticLockManagerType)this.get_store().find_element_user(PESSIMISTICLOCKMANAGER$174, 0);
         if (target == null) {
            target = (PessimisticLockManagerType)this.get_store().add_element_user(PESSIMISTICLOCKMANAGER$174);
         }

         target.setNil();
      }
   }

   public void unsetPessimisticLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PESSIMISTICLOCKMANAGER$174, 0);
      }
   }

   public NoneLockManagerType getNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLockManagerType target = null;
         target = (NoneLockManagerType)this.get_store().find_element_user(NONELOCKMANAGER$176, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLockManagerType target = null;
         target = (NoneLockManagerType)this.get_store().find_element_user(NONELOCKMANAGER$176, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONELOCKMANAGER$176) != 0;
      }
   }

   public void setNoneLockManager(NoneLockManagerType noneLockManager) {
      this.generatedSetterHelperImpl(noneLockManager, NONELOCKMANAGER$176, 0, (short)1);
   }

   public NoneLockManagerType addNewNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLockManagerType target = null;
         target = (NoneLockManagerType)this.get_store().add_element_user(NONELOCKMANAGER$176);
         return target;
      }
   }

   public void setNilNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLockManagerType target = null;
         target = (NoneLockManagerType)this.get_store().find_element_user(NONELOCKMANAGER$176, 0);
         if (target == null) {
            target = (NoneLockManagerType)this.get_store().add_element_user(NONELOCKMANAGER$176);
         }

         target.setNil();
      }
   }

   public void unsetNoneLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONELOCKMANAGER$176, 0);
      }
   }

   public SingleJvmExclusiveLockManagerType getSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmExclusiveLockManagerType target = null;
         target = (SingleJvmExclusiveLockManagerType)this.get_store().find_element_user(SINGLEJVMEXCLUSIVELOCKMANAGER$178, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmExclusiveLockManagerType target = null;
         target = (SingleJvmExclusiveLockManagerType)this.get_store().find_element_user(SINGLEJVMEXCLUSIVELOCKMANAGER$178, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLEJVMEXCLUSIVELOCKMANAGER$178) != 0;
      }
   }

   public void setSingleJvmExclusiveLockManager(SingleJvmExclusiveLockManagerType singleJvmExclusiveLockManager) {
      this.generatedSetterHelperImpl(singleJvmExclusiveLockManager, SINGLEJVMEXCLUSIVELOCKMANAGER$178, 0, (short)1);
   }

   public SingleJvmExclusiveLockManagerType addNewSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmExclusiveLockManagerType target = null;
         target = (SingleJvmExclusiveLockManagerType)this.get_store().add_element_user(SINGLEJVMEXCLUSIVELOCKMANAGER$178);
         return target;
      }
   }

   public void setNilSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmExclusiveLockManagerType target = null;
         target = (SingleJvmExclusiveLockManagerType)this.get_store().find_element_user(SINGLEJVMEXCLUSIVELOCKMANAGER$178, 0);
         if (target == null) {
            target = (SingleJvmExclusiveLockManagerType)this.get_store().add_element_user(SINGLEJVMEXCLUSIVELOCKMANAGER$178);
         }

         target.setNil();
      }
   }

   public void unsetSingleJvmExclusiveLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLEJVMEXCLUSIVELOCKMANAGER$178, 0);
      }
   }

   public VersionLockManagerType getVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionLockManagerType target = null;
         target = (VersionLockManagerType)this.get_store().find_element_user(VERSIONLOCKMANAGER$180, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionLockManagerType target = null;
         target = (VersionLockManagerType)this.get_store().find_element_user(VERSIONLOCKMANAGER$180, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VERSIONLOCKMANAGER$180) != 0;
      }
   }

   public void setVersionLockManager(VersionLockManagerType versionLockManager) {
      this.generatedSetterHelperImpl(versionLockManager, VERSIONLOCKMANAGER$180, 0, (short)1);
   }

   public VersionLockManagerType addNewVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionLockManagerType target = null;
         target = (VersionLockManagerType)this.get_store().add_element_user(VERSIONLOCKMANAGER$180);
         return target;
      }
   }

   public void setNilVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VersionLockManagerType target = null;
         target = (VersionLockManagerType)this.get_store().find_element_user(VERSIONLOCKMANAGER$180, 0);
         if (target == null) {
            target = (VersionLockManagerType)this.get_store().add_element_user(VERSIONLOCKMANAGER$180);
         }

         target.setNil();
      }
   }

   public void unsetVersionLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VERSIONLOCKMANAGER$180, 0);
      }
   }

   public CustomLockManagerType getCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLockManagerType target = null;
         target = (CustomLockManagerType)this.get_store().find_element_user(CUSTOMLOCKMANAGER$182, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLockManagerType target = null;
         target = (CustomLockManagerType)this.get_store().find_element_user(CUSTOMLOCKMANAGER$182, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMLOCKMANAGER$182) != 0;
      }
   }

   public void setCustomLockManager(CustomLockManagerType customLockManager) {
      this.generatedSetterHelperImpl(customLockManager, CUSTOMLOCKMANAGER$182, 0, (short)1);
   }

   public CustomLockManagerType addNewCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLockManagerType target = null;
         target = (CustomLockManagerType)this.get_store().add_element_user(CUSTOMLOCKMANAGER$182);
         return target;
      }
   }

   public void setNilCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLockManagerType target = null;
         target = (CustomLockManagerType)this.get_store().find_element_user(CUSTOMLOCKMANAGER$182, 0);
         if (target == null) {
            target = (CustomLockManagerType)this.get_store().add_element_user(CUSTOMLOCKMANAGER$182);
         }

         target.setNil();
      }
   }

   public void unsetCustomLockManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMLOCKMANAGER$182, 0);
      }
   }

   public int getLockTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKTIMEOUT$184, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetLockTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOCKTIMEOUT$184, 0);
         return target;
      }
   }

   public boolean isSetLockTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCKTIMEOUT$184) != 0;
      }
   }

   public void setLockTimeout(int lockTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCKTIMEOUT$184, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCKTIMEOUT$184);
         }

         target.setIntValue(lockTimeout);
      }
   }

   public void xsetLockTimeout(XmlInt lockTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(LOCKTIMEOUT$184, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(LOCKTIMEOUT$184);
         }

         target.set(lockTimeout);
      }
   }

   public void unsetLockTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCKTIMEOUT$184, 0);
      }
   }

   public CommonsLogFactoryType getCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CommonsLogFactoryType target = null;
         target = (CommonsLogFactoryType)this.get_store().find_element_user(COMMONSLOGFACTORY$186, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CommonsLogFactoryType target = null;
         target = (CommonsLogFactoryType)this.get_store().find_element_user(COMMONSLOGFACTORY$186, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COMMONSLOGFACTORY$186) != 0;
      }
   }

   public void setCommonsLogFactory(CommonsLogFactoryType commonsLogFactory) {
      this.generatedSetterHelperImpl(commonsLogFactory, COMMONSLOGFACTORY$186, 0, (short)1);
   }

   public CommonsLogFactoryType addNewCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CommonsLogFactoryType target = null;
         target = (CommonsLogFactoryType)this.get_store().add_element_user(COMMONSLOGFACTORY$186);
         return target;
      }
   }

   public void setNilCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CommonsLogFactoryType target = null;
         target = (CommonsLogFactoryType)this.get_store().find_element_user(COMMONSLOGFACTORY$186, 0);
         if (target == null) {
            target = (CommonsLogFactoryType)this.get_store().add_element_user(COMMONSLOGFACTORY$186);
         }

         target.setNil();
      }
   }

   public void unsetCommonsLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COMMONSLOGFACTORY$186, 0);
      }
   }

   public Log4JLogFactoryType getLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Log4JLogFactoryType target = null;
         target = (Log4JLogFactoryType)this.get_store().find_element_user(LOG4JLOGFACTORY$188, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Log4JLogFactoryType target = null;
         target = (Log4JLogFactoryType)this.get_store().find_element_user(LOG4JLOGFACTORY$188, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOG4JLOGFACTORY$188) != 0;
      }
   }

   public void setLog4JLogFactory(Log4JLogFactoryType log4JLogFactory) {
      this.generatedSetterHelperImpl(log4JLogFactory, LOG4JLOGFACTORY$188, 0, (short)1);
   }

   public Log4JLogFactoryType addNewLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Log4JLogFactoryType target = null;
         target = (Log4JLogFactoryType)this.get_store().add_element_user(LOG4JLOGFACTORY$188);
         return target;
      }
   }

   public void setNilLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Log4JLogFactoryType target = null;
         target = (Log4JLogFactoryType)this.get_store().find_element_user(LOG4JLOGFACTORY$188, 0);
         if (target == null) {
            target = (Log4JLogFactoryType)this.get_store().add_element_user(LOG4JLOGFACTORY$188);
         }

         target.setNil();
      }
   }

   public void unsetLog4JLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOG4JLOGFACTORY$188, 0);
      }
   }

   public LogFactoryImplType getLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogFactoryImplType target = null;
         target = (LogFactoryImplType)this.get_store().find_element_user(LOGFACTORYIMPL$190, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogFactoryImplType target = null;
         target = (LogFactoryImplType)this.get_store().find_element_user(LOGFACTORYIMPL$190, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGFACTORYIMPL$190) != 0;
      }
   }

   public void setLogFactoryImpl(LogFactoryImplType logFactoryImpl) {
      this.generatedSetterHelperImpl(logFactoryImpl, LOGFACTORYIMPL$190, 0, (short)1);
   }

   public LogFactoryImplType addNewLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogFactoryImplType target = null;
         target = (LogFactoryImplType)this.get_store().add_element_user(LOGFACTORYIMPL$190);
         return target;
      }
   }

   public void setNilLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogFactoryImplType target = null;
         target = (LogFactoryImplType)this.get_store().find_element_user(LOGFACTORYIMPL$190, 0);
         if (target == null) {
            target = (LogFactoryImplType)this.get_store().add_element_user(LOGFACTORYIMPL$190);
         }

         target.setNil();
      }
   }

   public void unsetLogFactoryImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGFACTORYIMPL$190, 0);
      }
   }

   public NoneLogFactoryType getNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLogFactoryType target = null;
         target = (NoneLogFactoryType)this.get_store().find_element_user(NONELOGFACTORY$192, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLogFactoryType target = null;
         target = (NoneLogFactoryType)this.get_store().find_element_user(NONELOGFACTORY$192, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONELOGFACTORY$192) != 0;
      }
   }

   public void setNoneLogFactory(NoneLogFactoryType noneLogFactory) {
      this.generatedSetterHelperImpl(noneLogFactory, NONELOGFACTORY$192, 0, (short)1);
   }

   public NoneLogFactoryType addNewNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLogFactoryType target = null;
         target = (NoneLogFactoryType)this.get_store().add_element_user(NONELOGFACTORY$192);
         return target;
      }
   }

   public void setNilNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneLogFactoryType target = null;
         target = (NoneLogFactoryType)this.get_store().find_element_user(NONELOGFACTORY$192, 0);
         if (target == null) {
            target = (NoneLogFactoryType)this.get_store().add_element_user(NONELOGFACTORY$192);
         }

         target.setNil();
      }
   }

   public void unsetNoneLogFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONELOGFACTORY$192, 0);
      }
   }

   public CustomLogType getCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLogType target = null;
         target = (CustomLogType)this.get_store().find_element_user(CUSTOMLOG$194, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLogType target = null;
         target = (CustomLogType)this.get_store().find_element_user(CUSTOMLOG$194, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMLOG$194) != 0;
      }
   }

   public void setCustomLog(CustomLogType customLog) {
      this.generatedSetterHelperImpl(customLog, CUSTOMLOG$194, 0, (short)1);
   }

   public CustomLogType addNewCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLogType target = null;
         target = (CustomLogType)this.get_store().add_element_user(CUSTOMLOG$194);
         return target;
      }
   }

   public void setNilCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomLogType target = null;
         target = (CustomLogType)this.get_store().find_element_user(CUSTOMLOG$194, 0);
         if (target == null) {
            target = (CustomLogType)this.get_store().add_element_user(CUSTOMLOG$194);
         }

         target.setNil();
      }
   }

   public void unsetCustomLog() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMLOG$194, 0);
      }
   }

   public PersistenceUnitConfigurationType.LrsSize.Enum getLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LRSSIZE$196, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.LrsSize.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.LrsSize xgetLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.LrsSize target = null;
         target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().find_element_user(LRSSIZE$196, 0);
         return target;
      }
   }

   public boolean isNilLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.LrsSize target = null;
         target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().find_element_user(LRSSIZE$196, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LRSSIZE$196) != 0;
      }
   }

   public void setLrsSize(PersistenceUnitConfigurationType.LrsSize.Enum lrsSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LRSSIZE$196, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LRSSIZE$196);
         }

         target.setEnumValue(lrsSize);
      }
   }

   public void xsetLrsSize(PersistenceUnitConfigurationType.LrsSize lrsSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.LrsSize target = null;
         target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().find_element_user(LRSSIZE$196, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().add_element_user(LRSSIZE$196);
         }

         target.set(lrsSize);
      }
   }

   public void setNilLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.LrsSize target = null;
         target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().find_element_user(LRSSIZE$196, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.LrsSize)this.get_store().add_element_user(LRSSIZE$196);
         }

         target.setNil();
      }
   }

   public void unsetLrsSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LRSSIZE$196, 0);
      }
   }

   public String getMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPING$198, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPING$198, 0);
         return target;
      }
   }

   public boolean isNilMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPING$198, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPING$198) != 0;
      }
   }

   public void setMapping(String mapping) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPING$198, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAPPING$198);
         }

         target.setStringValue(mapping);
      }
   }

   public void xsetMapping(XmlString mapping) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPING$198, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPING$198);
         }

         target.set(mapping);
      }
   }

   public void setNilMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPING$198, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPING$198);
         }

         target.setNil();
      }
   }

   public void unsetMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPING$198, 0);
      }
   }

   public DefaultMappingDefaultsType getDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMappingDefaultsType target = null;
         target = (DefaultMappingDefaultsType)this.get_store().find_element_user(DEFAULTMAPPINGDEFAULTS$200, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMappingDefaultsType target = null;
         target = (DefaultMappingDefaultsType)this.get_store().find_element_user(DEFAULTMAPPINGDEFAULTS$200, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTMAPPINGDEFAULTS$200) != 0;
      }
   }

   public void setDefaultMappingDefaults(DefaultMappingDefaultsType defaultMappingDefaults) {
      this.generatedSetterHelperImpl(defaultMappingDefaults, DEFAULTMAPPINGDEFAULTS$200, 0, (short)1);
   }

   public DefaultMappingDefaultsType addNewDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMappingDefaultsType target = null;
         target = (DefaultMappingDefaultsType)this.get_store().add_element_user(DEFAULTMAPPINGDEFAULTS$200);
         return target;
      }
   }

   public void setNilDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMappingDefaultsType target = null;
         target = (DefaultMappingDefaultsType)this.get_store().find_element_user(DEFAULTMAPPINGDEFAULTS$200, 0);
         if (target == null) {
            target = (DefaultMappingDefaultsType)this.get_store().add_element_user(DEFAULTMAPPINGDEFAULTS$200);
         }

         target.setNil();
      }
   }

   public void unsetDefaultMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTMAPPINGDEFAULTS$200, 0);
      }
   }

   public DeprecatedJdoMappingDefaultsType getDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMappingDefaultsType target = null;
         target = (DeprecatedJdoMappingDefaultsType)this.get_store().find_element_user(DEPRECATEDJDOMAPPINGDEFAULTS$202, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMappingDefaultsType target = null;
         target = (DeprecatedJdoMappingDefaultsType)this.get_store().find_element_user(DEPRECATEDJDOMAPPINGDEFAULTS$202, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPRECATEDJDOMAPPINGDEFAULTS$202) != 0;
      }
   }

   public void setDeprecatedJdoMappingDefaults(DeprecatedJdoMappingDefaultsType deprecatedJdoMappingDefaults) {
      this.generatedSetterHelperImpl(deprecatedJdoMappingDefaults, DEPRECATEDJDOMAPPINGDEFAULTS$202, 0, (short)1);
   }

   public DeprecatedJdoMappingDefaultsType addNewDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMappingDefaultsType target = null;
         target = (DeprecatedJdoMappingDefaultsType)this.get_store().add_element_user(DEPRECATEDJDOMAPPINGDEFAULTS$202);
         return target;
      }
   }

   public void setNilDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMappingDefaultsType target = null;
         target = (DeprecatedJdoMappingDefaultsType)this.get_store().find_element_user(DEPRECATEDJDOMAPPINGDEFAULTS$202, 0);
         if (target == null) {
            target = (DeprecatedJdoMappingDefaultsType)this.get_store().add_element_user(DEPRECATEDJDOMAPPINGDEFAULTS$202);
         }

         target.setNil();
      }
   }

   public void unsetDeprecatedJdoMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPRECATEDJDOMAPPINGDEFAULTS$202, 0);
      }
   }

   public MappingDefaultsImplType getMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingDefaultsImplType target = null;
         target = (MappingDefaultsImplType)this.get_store().find_element_user(MAPPINGDEFAULTSIMPL$204, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingDefaultsImplType target = null;
         target = (MappingDefaultsImplType)this.get_store().find_element_user(MAPPINGDEFAULTSIMPL$204, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPINGDEFAULTSIMPL$204) != 0;
      }
   }

   public void setMappingDefaultsImpl(MappingDefaultsImplType mappingDefaultsImpl) {
      this.generatedSetterHelperImpl(mappingDefaultsImpl, MAPPINGDEFAULTSIMPL$204, 0, (short)1);
   }

   public MappingDefaultsImplType addNewMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingDefaultsImplType target = null;
         target = (MappingDefaultsImplType)this.get_store().add_element_user(MAPPINGDEFAULTSIMPL$204);
         return target;
      }
   }

   public void setNilMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingDefaultsImplType target = null;
         target = (MappingDefaultsImplType)this.get_store().find_element_user(MAPPINGDEFAULTSIMPL$204, 0);
         if (target == null) {
            target = (MappingDefaultsImplType)this.get_store().add_element_user(MAPPINGDEFAULTSIMPL$204);
         }

         target.setNil();
      }
   }

   public void unsetMappingDefaultsImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPINGDEFAULTSIMPL$204, 0);
      }
   }

   public PersistenceMappingDefaultsType getPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceMappingDefaultsType target = null;
         target = (PersistenceMappingDefaultsType)this.get_store().find_element_user(PERSISTENCEMAPPINGDEFAULTS$206, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceMappingDefaultsType target = null;
         target = (PersistenceMappingDefaultsType)this.get_store().find_element_user(PERSISTENCEMAPPINGDEFAULTS$206, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENCEMAPPINGDEFAULTS$206) != 0;
      }
   }

   public void setPersistenceMappingDefaults(PersistenceMappingDefaultsType persistenceMappingDefaults) {
      this.generatedSetterHelperImpl(persistenceMappingDefaults, PERSISTENCEMAPPINGDEFAULTS$206, 0, (short)1);
   }

   public PersistenceMappingDefaultsType addNewPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceMappingDefaultsType target = null;
         target = (PersistenceMappingDefaultsType)this.get_store().add_element_user(PERSISTENCEMAPPINGDEFAULTS$206);
         return target;
      }
   }

   public void setNilPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceMappingDefaultsType target = null;
         target = (PersistenceMappingDefaultsType)this.get_store().find_element_user(PERSISTENCEMAPPINGDEFAULTS$206, 0);
         if (target == null) {
            target = (PersistenceMappingDefaultsType)this.get_store().add_element_user(PERSISTENCEMAPPINGDEFAULTS$206);
         }

         target.setNil();
      }
   }

   public void unsetPersistenceMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENCEMAPPINGDEFAULTS$206, 0);
      }
   }

   public CustomMappingDefaultsType getCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingDefaultsType target = null;
         target = (CustomMappingDefaultsType)this.get_store().find_element_user(CUSTOMMAPPINGDEFAULTS$208, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingDefaultsType target = null;
         target = (CustomMappingDefaultsType)this.get_store().find_element_user(CUSTOMMAPPINGDEFAULTS$208, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMMAPPINGDEFAULTS$208) != 0;
      }
   }

   public void setCustomMappingDefaults(CustomMappingDefaultsType customMappingDefaults) {
      this.generatedSetterHelperImpl(customMappingDefaults, CUSTOMMAPPINGDEFAULTS$208, 0, (short)1);
   }

   public CustomMappingDefaultsType addNewCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingDefaultsType target = null;
         target = (CustomMappingDefaultsType)this.get_store().add_element_user(CUSTOMMAPPINGDEFAULTS$208);
         return target;
      }
   }

   public void setNilCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingDefaultsType target = null;
         target = (CustomMappingDefaultsType)this.get_store().find_element_user(CUSTOMMAPPINGDEFAULTS$208, 0);
         if (target == null) {
            target = (CustomMappingDefaultsType)this.get_store().add_element_user(CUSTOMMAPPINGDEFAULTS$208);
         }

         target.setNil();
      }
   }

   public void unsetCustomMappingDefaults() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMMAPPINGDEFAULTS$208, 0);
      }
   }

   public ExtensionDeprecatedJdoMappingFactoryType getExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExtensionDeprecatedJdoMappingFactoryType target = null;
         target = (ExtensionDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExtensionDeprecatedJdoMappingFactoryType target = null;
         target = (ExtensionDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210) != 0;
      }
   }

   public void setExtensionDeprecatedJdoMappingFactory(ExtensionDeprecatedJdoMappingFactoryType extensionDeprecatedJdoMappingFactory) {
      this.generatedSetterHelperImpl(extensionDeprecatedJdoMappingFactory, EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210, 0, (short)1);
   }

   public ExtensionDeprecatedJdoMappingFactoryType addNewExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExtensionDeprecatedJdoMappingFactoryType target = null;
         target = (ExtensionDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210);
         return target;
      }
   }

   public void setNilExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExtensionDeprecatedJdoMappingFactoryType target = null;
         target = (ExtensionDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210, 0);
         if (target == null) {
            target = (ExtensionDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210);
         }

         target.setNil();
      }
   }

   public void unsetExtensionDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXTENSIONDEPRECATEDJDOMAPPINGFACTORY$210, 0);
      }
   }

   public KodoPersistenceMappingFactoryType getKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMappingFactoryType target = null;
         target = (KodoPersistenceMappingFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMAPPINGFACTORY$212, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMappingFactoryType target = null;
         target = (KodoPersistenceMappingFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMAPPINGFACTORY$212, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOPERSISTENCEMAPPINGFACTORY$212) != 0;
      }
   }

   public void setKodoPersistenceMappingFactory(KodoPersistenceMappingFactoryType kodoPersistenceMappingFactory) {
      this.generatedSetterHelperImpl(kodoPersistenceMappingFactory, KODOPERSISTENCEMAPPINGFACTORY$212, 0, (short)1);
   }

   public KodoPersistenceMappingFactoryType addNewKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMappingFactoryType target = null;
         target = (KodoPersistenceMappingFactoryType)this.get_store().add_element_user(KODOPERSISTENCEMAPPINGFACTORY$212);
         return target;
      }
   }

   public void setNilKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMappingFactoryType target = null;
         target = (KodoPersistenceMappingFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMAPPINGFACTORY$212, 0);
         if (target == null) {
            target = (KodoPersistenceMappingFactoryType)this.get_store().add_element_user(KODOPERSISTENCEMAPPINGFACTORY$212);
         }

         target.setNil();
      }
   }

   public void unsetKodoPersistenceMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOPERSISTENCEMAPPINGFACTORY$212, 0);
      }
   }

   public MappingFileDeprecatedJdoMappingFactoryType getMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingFileDeprecatedJdoMappingFactoryType target = null;
         target = (MappingFileDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingFileDeprecatedJdoMappingFactoryType target = null;
         target = (MappingFileDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214) != 0;
      }
   }

   public void setMappingFileDeprecatedJdoMappingFactory(MappingFileDeprecatedJdoMappingFactoryType mappingFileDeprecatedJdoMappingFactory) {
      this.generatedSetterHelperImpl(mappingFileDeprecatedJdoMappingFactory, MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214, 0, (short)1);
   }

   public MappingFileDeprecatedJdoMappingFactoryType addNewMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingFileDeprecatedJdoMappingFactoryType target = null;
         target = (MappingFileDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214);
         return target;
      }
   }

   public void setNilMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MappingFileDeprecatedJdoMappingFactoryType target = null;
         target = (MappingFileDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214, 0);
         if (target == null) {
            target = (MappingFileDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214);
         }

         target.setNil();
      }
   }

   public void unsetMappingFileDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPINGFILEDEPRECATEDJDOMAPPINGFACTORY$214, 0);
      }
   }

   public OrmFileJdorMappingFactoryType getOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrmFileJdorMappingFactoryType target = null;
         target = (OrmFileJdorMappingFactoryType)this.get_store().find_element_user(ORMFILEJDORMAPPINGFACTORY$216, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrmFileJdorMappingFactoryType target = null;
         target = (OrmFileJdorMappingFactoryType)this.get_store().find_element_user(ORMFILEJDORMAPPINGFACTORY$216, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORMFILEJDORMAPPINGFACTORY$216) != 0;
      }
   }

   public void setOrmFileJdorMappingFactory(OrmFileJdorMappingFactoryType ormFileJdorMappingFactory) {
      this.generatedSetterHelperImpl(ormFileJdorMappingFactory, ORMFILEJDORMAPPINGFACTORY$216, 0, (short)1);
   }

   public OrmFileJdorMappingFactoryType addNewOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrmFileJdorMappingFactoryType target = null;
         target = (OrmFileJdorMappingFactoryType)this.get_store().add_element_user(ORMFILEJDORMAPPINGFACTORY$216);
         return target;
      }
   }

   public void setNilOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OrmFileJdorMappingFactoryType target = null;
         target = (OrmFileJdorMappingFactoryType)this.get_store().find_element_user(ORMFILEJDORMAPPINGFACTORY$216, 0);
         if (target == null) {
            target = (OrmFileJdorMappingFactoryType)this.get_store().add_element_user(ORMFILEJDORMAPPINGFACTORY$216);
         }

         target.setNil();
      }
   }

   public void unsetOrmFileJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORMFILEJDORMAPPINGFACTORY$216, 0);
      }
   }

   public TableDeprecatedJdoMappingFactoryType getTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableDeprecatedJdoMappingFactoryType target = null;
         target = (TableDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(TABLEDEPRECATEDJDOMAPPINGFACTORY$218, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableDeprecatedJdoMappingFactoryType target = null;
         target = (TableDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(TABLEDEPRECATEDJDOMAPPINGFACTORY$218, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLEDEPRECATEDJDOMAPPINGFACTORY$218) != 0;
      }
   }

   public void setTableDeprecatedJdoMappingFactory(TableDeprecatedJdoMappingFactoryType tableDeprecatedJdoMappingFactory) {
      this.generatedSetterHelperImpl(tableDeprecatedJdoMappingFactory, TABLEDEPRECATEDJDOMAPPINGFACTORY$218, 0, (short)1);
   }

   public TableDeprecatedJdoMappingFactoryType addNewTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableDeprecatedJdoMappingFactoryType target = null;
         target = (TableDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(TABLEDEPRECATEDJDOMAPPINGFACTORY$218);
         return target;
      }
   }

   public void setNilTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableDeprecatedJdoMappingFactoryType target = null;
         target = (TableDeprecatedJdoMappingFactoryType)this.get_store().find_element_user(TABLEDEPRECATEDJDOMAPPINGFACTORY$218, 0);
         if (target == null) {
            target = (TableDeprecatedJdoMappingFactoryType)this.get_store().add_element_user(TABLEDEPRECATEDJDOMAPPINGFACTORY$218);
         }

         target.setNil();
      }
   }

   public void unsetTableDeprecatedJdoMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLEDEPRECATEDJDOMAPPINGFACTORY$218, 0);
      }
   }

   public TableJdorMappingFactoryType getTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdorMappingFactoryType target = null;
         target = (TableJdorMappingFactoryType)this.get_store().find_element_user(TABLEJDORMAPPINGFACTORY$220, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdorMappingFactoryType target = null;
         target = (TableJdorMappingFactoryType)this.get_store().find_element_user(TABLEJDORMAPPINGFACTORY$220, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLEJDORMAPPINGFACTORY$220) != 0;
      }
   }

   public void setTableJdorMappingFactory(TableJdorMappingFactoryType tableJdorMappingFactory) {
      this.generatedSetterHelperImpl(tableJdorMappingFactory, TABLEJDORMAPPINGFACTORY$220, 0, (short)1);
   }

   public TableJdorMappingFactoryType addNewTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdorMappingFactoryType target = null;
         target = (TableJdorMappingFactoryType)this.get_store().add_element_user(TABLEJDORMAPPINGFACTORY$220);
         return target;
      }
   }

   public void setNilTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdorMappingFactoryType target = null;
         target = (TableJdorMappingFactoryType)this.get_store().find_element_user(TABLEJDORMAPPINGFACTORY$220, 0);
         if (target == null) {
            target = (TableJdorMappingFactoryType)this.get_store().add_element_user(TABLEJDORMAPPINGFACTORY$220);
         }

         target.setNil();
      }
   }

   public void unsetTableJdorMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLEJDORMAPPINGFACTORY$220, 0);
      }
   }

   public CustomMappingFactoryType getCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingFactoryType target = null;
         target = (CustomMappingFactoryType)this.get_store().find_element_user(CUSTOMMAPPINGFACTORY$222, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingFactoryType target = null;
         target = (CustomMappingFactoryType)this.get_store().find_element_user(CUSTOMMAPPINGFACTORY$222, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMMAPPINGFACTORY$222) != 0;
      }
   }

   public void setCustomMappingFactory(CustomMappingFactoryType customMappingFactory) {
      this.generatedSetterHelperImpl(customMappingFactory, CUSTOMMAPPINGFACTORY$222, 0, (short)1);
   }

   public CustomMappingFactoryType addNewCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingFactoryType target = null;
         target = (CustomMappingFactoryType)this.get_store().add_element_user(CUSTOMMAPPINGFACTORY$222);
         return target;
      }
   }

   public void setNilCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMappingFactoryType target = null;
         target = (CustomMappingFactoryType)this.get_store().find_element_user(CUSTOMMAPPINGFACTORY$222, 0);
         if (target == null) {
            target = (CustomMappingFactoryType)this.get_store().add_element_user(CUSTOMMAPPINGFACTORY$222);
         }

         target.setNil();
      }
   }

   public void unsetCustomMappingFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMMAPPINGFACTORY$222, 0);
      }
   }

   public DefaultMetaDataFactoryType getDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataFactoryType target = null;
         target = (DefaultMetaDataFactoryType)this.get_store().find_element_user(DEFAULTMETADATAFACTORY$224, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataFactoryType target = null;
         target = (DefaultMetaDataFactoryType)this.get_store().find_element_user(DEFAULTMETADATAFACTORY$224, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTMETADATAFACTORY$224) != 0;
      }
   }

   public void setDefaultMetaDataFactory(DefaultMetaDataFactoryType defaultMetaDataFactory) {
      this.generatedSetterHelperImpl(defaultMetaDataFactory, DEFAULTMETADATAFACTORY$224, 0, (short)1);
   }

   public DefaultMetaDataFactoryType addNewDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataFactoryType target = null;
         target = (DefaultMetaDataFactoryType)this.get_store().add_element_user(DEFAULTMETADATAFACTORY$224);
         return target;
      }
   }

   public void setNilDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataFactoryType target = null;
         target = (DefaultMetaDataFactoryType)this.get_store().find_element_user(DEFAULTMETADATAFACTORY$224, 0);
         if (target == null) {
            target = (DefaultMetaDataFactoryType)this.get_store().add_element_user(DEFAULTMETADATAFACTORY$224);
         }

         target.setNil();
      }
   }

   public void unsetDefaultMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTMETADATAFACTORY$224, 0);
      }
   }

   public JdoMetaDataFactoryType getJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdoMetaDataFactoryType target = null;
         target = (JdoMetaDataFactoryType)this.get_store().find_element_user(JDOMETADATAFACTORY$226, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdoMetaDataFactoryType target = null;
         target = (JdoMetaDataFactoryType)this.get_store().find_element_user(JDOMETADATAFACTORY$226, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDOMETADATAFACTORY$226) != 0;
      }
   }

   public void setJdoMetaDataFactory(JdoMetaDataFactoryType jdoMetaDataFactory) {
      this.generatedSetterHelperImpl(jdoMetaDataFactory, JDOMETADATAFACTORY$226, 0, (short)1);
   }

   public JdoMetaDataFactoryType addNewJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdoMetaDataFactoryType target = null;
         target = (JdoMetaDataFactoryType)this.get_store().add_element_user(JDOMETADATAFACTORY$226);
         return target;
      }
   }

   public void setNilJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JdoMetaDataFactoryType target = null;
         target = (JdoMetaDataFactoryType)this.get_store().find_element_user(JDOMETADATAFACTORY$226, 0);
         if (target == null) {
            target = (JdoMetaDataFactoryType)this.get_store().add_element_user(JDOMETADATAFACTORY$226);
         }

         target.setNil();
      }
   }

   public void unsetJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDOMETADATAFACTORY$226, 0);
      }
   }

   public DeprecatedJdoMetaDataFactoryType getDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMetaDataFactoryType target = null;
         target = (DeprecatedJdoMetaDataFactoryType)this.get_store().find_element_user(DEPRECATEDJDOMETADATAFACTORY$228, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMetaDataFactoryType target = null;
         target = (DeprecatedJdoMetaDataFactoryType)this.get_store().find_element_user(DEPRECATEDJDOMETADATAFACTORY$228, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEPRECATEDJDOMETADATAFACTORY$228) != 0;
      }
   }

   public void setDeprecatedJdoMetaDataFactory(DeprecatedJdoMetaDataFactoryType deprecatedJdoMetaDataFactory) {
      this.generatedSetterHelperImpl(deprecatedJdoMetaDataFactory, DEPRECATEDJDOMETADATAFACTORY$228, 0, (short)1);
   }

   public DeprecatedJdoMetaDataFactoryType addNewDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMetaDataFactoryType target = null;
         target = (DeprecatedJdoMetaDataFactoryType)this.get_store().add_element_user(DEPRECATEDJDOMETADATAFACTORY$228);
         return target;
      }
   }

   public void setNilDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeprecatedJdoMetaDataFactoryType target = null;
         target = (DeprecatedJdoMetaDataFactoryType)this.get_store().find_element_user(DEPRECATEDJDOMETADATAFACTORY$228, 0);
         if (target == null) {
            target = (DeprecatedJdoMetaDataFactoryType)this.get_store().add_element_user(DEPRECATEDJDOMETADATAFACTORY$228);
         }

         target.setNil();
      }
   }

   public void unsetDeprecatedJdoMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEPRECATEDJDOMETADATAFACTORY$228, 0);
      }
   }

   public KodoPersistenceMetaDataFactoryType getKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMetaDataFactoryType target = null;
         target = (KodoPersistenceMetaDataFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMETADATAFACTORY$230, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMetaDataFactoryType target = null;
         target = (KodoPersistenceMetaDataFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMETADATAFACTORY$230, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOPERSISTENCEMETADATAFACTORY$230) != 0;
      }
   }

   public void setKodoPersistenceMetaDataFactory(KodoPersistenceMetaDataFactoryType kodoPersistenceMetaDataFactory) {
      this.generatedSetterHelperImpl(kodoPersistenceMetaDataFactory, KODOPERSISTENCEMETADATAFACTORY$230, 0, (short)1);
   }

   public KodoPersistenceMetaDataFactoryType addNewKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMetaDataFactoryType target = null;
         target = (KodoPersistenceMetaDataFactoryType)this.get_store().add_element_user(KODOPERSISTENCEMETADATAFACTORY$230);
         return target;
      }
   }

   public void setNilKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoPersistenceMetaDataFactoryType target = null;
         target = (KodoPersistenceMetaDataFactoryType)this.get_store().find_element_user(KODOPERSISTENCEMETADATAFACTORY$230, 0);
         if (target == null) {
            target = (KodoPersistenceMetaDataFactoryType)this.get_store().add_element_user(KODOPERSISTENCEMETADATAFACTORY$230);
         }

         target.setNil();
      }
   }

   public void unsetKodoPersistenceMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOPERSISTENCEMETADATAFACTORY$230, 0);
      }
   }

   public CustomMetaDataFactoryType getCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataFactoryType target = null;
         target = (CustomMetaDataFactoryType)this.get_store().find_element_user(CUSTOMMETADATAFACTORY$232, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataFactoryType target = null;
         target = (CustomMetaDataFactoryType)this.get_store().find_element_user(CUSTOMMETADATAFACTORY$232, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMMETADATAFACTORY$232) != 0;
      }
   }

   public void setCustomMetaDataFactory(CustomMetaDataFactoryType customMetaDataFactory) {
      this.generatedSetterHelperImpl(customMetaDataFactory, CUSTOMMETADATAFACTORY$232, 0, (short)1);
   }

   public CustomMetaDataFactoryType addNewCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataFactoryType target = null;
         target = (CustomMetaDataFactoryType)this.get_store().add_element_user(CUSTOMMETADATAFACTORY$232);
         return target;
      }
   }

   public void setNilCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataFactoryType target = null;
         target = (CustomMetaDataFactoryType)this.get_store().find_element_user(CUSTOMMETADATAFACTORY$232, 0);
         if (target == null) {
            target = (CustomMetaDataFactoryType)this.get_store().add_element_user(CUSTOMMETADATAFACTORY$232);
         }

         target.setNil();
      }
   }

   public void unsetCustomMetaDataFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMMETADATAFACTORY$232, 0);
      }
   }

   public DefaultMetaDataRepositoryType getDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataRepositoryType target = null;
         target = (DefaultMetaDataRepositoryType)this.get_store().find_element_user(DEFAULTMETADATAREPOSITORY$234, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataRepositoryType target = null;
         target = (DefaultMetaDataRepositoryType)this.get_store().find_element_user(DEFAULTMETADATAREPOSITORY$234, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTMETADATAREPOSITORY$234) != 0;
      }
   }

   public void setDefaultMetaDataRepository(DefaultMetaDataRepositoryType defaultMetaDataRepository) {
      this.generatedSetterHelperImpl(defaultMetaDataRepository, DEFAULTMETADATAREPOSITORY$234, 0, (short)1);
   }

   public DefaultMetaDataRepositoryType addNewDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataRepositoryType target = null;
         target = (DefaultMetaDataRepositoryType)this.get_store().add_element_user(DEFAULTMETADATAREPOSITORY$234);
         return target;
      }
   }

   public void setNilDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultMetaDataRepositoryType target = null;
         target = (DefaultMetaDataRepositoryType)this.get_store().find_element_user(DEFAULTMETADATAREPOSITORY$234, 0);
         if (target == null) {
            target = (DefaultMetaDataRepositoryType)this.get_store().add_element_user(DEFAULTMETADATAREPOSITORY$234);
         }

         target.setNil();
      }
   }

   public void unsetDefaultMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTMETADATAREPOSITORY$234, 0);
      }
   }

   public KodoMappingRepositoryType getKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoMappingRepositoryType target = null;
         target = (KodoMappingRepositoryType)this.get_store().find_element_user(KODOMAPPINGREPOSITORY$236, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoMappingRepositoryType target = null;
         target = (KodoMappingRepositoryType)this.get_store().find_element_user(KODOMAPPINGREPOSITORY$236, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOMAPPINGREPOSITORY$236) != 0;
      }
   }

   public void setKodoMappingRepository(KodoMappingRepositoryType kodoMappingRepository) {
      this.generatedSetterHelperImpl(kodoMappingRepository, KODOMAPPINGREPOSITORY$236, 0, (short)1);
   }

   public KodoMappingRepositoryType addNewKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoMappingRepositoryType target = null;
         target = (KodoMappingRepositoryType)this.get_store().add_element_user(KODOMAPPINGREPOSITORY$236);
         return target;
      }
   }

   public void setNilKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoMappingRepositoryType target = null;
         target = (KodoMappingRepositoryType)this.get_store().find_element_user(KODOMAPPINGREPOSITORY$236, 0);
         if (target == null) {
            target = (KodoMappingRepositoryType)this.get_store().add_element_user(KODOMAPPINGREPOSITORY$236);
         }

         target.setNil();
      }
   }

   public void unsetKodoMappingRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOMAPPINGREPOSITORY$236, 0);
      }
   }

   public CustomMetaDataRepositoryType getCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataRepositoryType target = null;
         target = (CustomMetaDataRepositoryType)this.get_store().find_element_user(CUSTOMMETADATAREPOSITORY$238, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataRepositoryType target = null;
         target = (CustomMetaDataRepositoryType)this.get_store().find_element_user(CUSTOMMETADATAREPOSITORY$238, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMMETADATAREPOSITORY$238) != 0;
      }
   }

   public void setCustomMetaDataRepository(CustomMetaDataRepositoryType customMetaDataRepository) {
      this.generatedSetterHelperImpl(customMetaDataRepository, CUSTOMMETADATAREPOSITORY$238, 0, (short)1);
   }

   public CustomMetaDataRepositoryType addNewCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataRepositoryType target = null;
         target = (CustomMetaDataRepositoryType)this.get_store().add_element_user(CUSTOMMETADATAREPOSITORY$238);
         return target;
      }
   }

   public void setNilCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomMetaDataRepositoryType target = null;
         target = (CustomMetaDataRepositoryType)this.get_store().find_element_user(CUSTOMMETADATAREPOSITORY$238, 0);
         if (target == null) {
            target = (CustomMetaDataRepositoryType)this.get_store().add_element_user(CUSTOMMETADATAREPOSITORY$238);
         }

         target.setNil();
      }
   }

   public void unsetCustomMetaDataRepository() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMMETADATAREPOSITORY$238, 0);
      }
   }

   public boolean getMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTITHREADED$240, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTITHREADED$240, 0);
         return target;
      }
   }

   public boolean isSetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTITHREADED$240) != 0;
      }
   }

   public void setMultithreaded(boolean multithreaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTITHREADED$240, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTITHREADED$240);
         }

         target.setBooleanValue(multithreaded);
      }
   }

   public void xsetMultithreaded(XmlBoolean multithreaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTITHREADED$240, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MULTITHREADED$240);
         }

         target.set(multithreaded);
      }
   }

   public void unsetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTITHREADED$240, 0);
      }
   }

   public boolean getNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALREAD$242, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALREAD$242, 0);
         return target;
      }
   }

   public boolean isSetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONTRANSACTIONALREAD$242) != 0;
      }
   }

   public void setNontransactionalRead(boolean nontransactionalRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALREAD$242, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONTRANSACTIONALREAD$242);
         }

         target.setBooleanValue(nontransactionalRead);
      }
   }

   public void xsetNontransactionalRead(XmlBoolean nontransactionalRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALREAD$242, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONTRANSACTIONALREAD$242);
         }

         target.set(nontransactionalRead);
      }
   }

   public void unsetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONTRANSACTIONALREAD$242, 0);
      }
   }

   public boolean getNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALWRITE$244, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALWRITE$244, 0);
         return target;
      }
   }

   public boolean isSetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONTRANSACTIONALWRITE$244) != 0;
      }
   }

   public void setNontransactionalWrite(boolean nontransactionalWrite) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALWRITE$244, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONTRANSACTIONALWRITE$244);
         }

         target.setBooleanValue(nontransactionalWrite);
      }
   }

   public void xsetNontransactionalWrite(XmlBoolean nontransactionalWrite) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALWRITE$244, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONTRANSACTIONALWRITE$244);
         }

         target.set(nontransactionalWrite);
      }
   }

   public void unsetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONTRANSACTIONALWRITE$244, 0);
      }
   }

   public boolean getOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIMISTIC$246, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIMISTIC$246, 0);
         return target;
      }
   }

   public boolean isSetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIMISTIC$246) != 0;
      }
   }

   public void setOptimistic(boolean optimistic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIMISTIC$246, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OPTIMISTIC$246);
         }

         target.setBooleanValue(optimistic);
      }
   }

   public void xsetOptimistic(XmlBoolean optimistic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIMISTIC$246, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(OPTIMISTIC$246);
         }

         target.set(optimistic);
      }
   }

   public void unsetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIMISTIC$246, 0);
      }
   }

   public DefaultOrphanedKeyActionType getDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultOrphanedKeyActionType target = null;
         target = (DefaultOrphanedKeyActionType)this.get_store().find_element_user(DEFAULTORPHANEDKEYACTION$248, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultOrphanedKeyActionType target = null;
         target = (DefaultOrphanedKeyActionType)this.get_store().find_element_user(DEFAULTORPHANEDKEYACTION$248, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTORPHANEDKEYACTION$248) != 0;
      }
   }

   public void setDefaultOrphanedKeyAction(DefaultOrphanedKeyActionType defaultOrphanedKeyAction) {
      this.generatedSetterHelperImpl(defaultOrphanedKeyAction, DEFAULTORPHANEDKEYACTION$248, 0, (short)1);
   }

   public DefaultOrphanedKeyActionType addNewDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultOrphanedKeyActionType target = null;
         target = (DefaultOrphanedKeyActionType)this.get_store().add_element_user(DEFAULTORPHANEDKEYACTION$248);
         return target;
      }
   }

   public void setNilDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultOrphanedKeyActionType target = null;
         target = (DefaultOrphanedKeyActionType)this.get_store().find_element_user(DEFAULTORPHANEDKEYACTION$248, 0);
         if (target == null) {
            target = (DefaultOrphanedKeyActionType)this.get_store().add_element_user(DEFAULTORPHANEDKEYACTION$248);
         }

         target.setNil();
      }
   }

   public void unsetDefaultOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTORPHANEDKEYACTION$248, 0);
      }
   }

   public LogOrphanedKeyActionType getLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogOrphanedKeyActionType target = null;
         target = (LogOrphanedKeyActionType)this.get_store().find_element_user(LOGORPHANEDKEYACTION$250, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogOrphanedKeyActionType target = null;
         target = (LogOrphanedKeyActionType)this.get_store().find_element_user(LOGORPHANEDKEYACTION$250, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOGORPHANEDKEYACTION$250) != 0;
      }
   }

   public void setLogOrphanedKeyAction(LogOrphanedKeyActionType logOrphanedKeyAction) {
      this.generatedSetterHelperImpl(logOrphanedKeyAction, LOGORPHANEDKEYACTION$250, 0, (short)1);
   }

   public LogOrphanedKeyActionType addNewLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogOrphanedKeyActionType target = null;
         target = (LogOrphanedKeyActionType)this.get_store().add_element_user(LOGORPHANEDKEYACTION$250);
         return target;
      }
   }

   public void setNilLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LogOrphanedKeyActionType target = null;
         target = (LogOrphanedKeyActionType)this.get_store().find_element_user(LOGORPHANEDKEYACTION$250, 0);
         if (target == null) {
            target = (LogOrphanedKeyActionType)this.get_store().add_element_user(LOGORPHANEDKEYACTION$250);
         }

         target.setNil();
      }
   }

   public void unsetLogOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOGORPHANEDKEYACTION$250, 0);
      }
   }

   public ExceptionOrphanedKeyActionType getExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionOrphanedKeyActionType target = null;
         target = (ExceptionOrphanedKeyActionType)this.get_store().find_element_user(EXCEPTIONORPHANEDKEYACTION$252, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionOrphanedKeyActionType target = null;
         target = (ExceptionOrphanedKeyActionType)this.get_store().find_element_user(EXCEPTIONORPHANEDKEYACTION$252, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCEPTIONORPHANEDKEYACTION$252) != 0;
      }
   }

   public void setExceptionOrphanedKeyAction(ExceptionOrphanedKeyActionType exceptionOrphanedKeyAction) {
      this.generatedSetterHelperImpl(exceptionOrphanedKeyAction, EXCEPTIONORPHANEDKEYACTION$252, 0, (short)1);
   }

   public ExceptionOrphanedKeyActionType addNewExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionOrphanedKeyActionType target = null;
         target = (ExceptionOrphanedKeyActionType)this.get_store().add_element_user(EXCEPTIONORPHANEDKEYACTION$252);
         return target;
      }
   }

   public void setNilExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExceptionOrphanedKeyActionType target = null;
         target = (ExceptionOrphanedKeyActionType)this.get_store().find_element_user(EXCEPTIONORPHANEDKEYACTION$252, 0);
         if (target == null) {
            target = (ExceptionOrphanedKeyActionType)this.get_store().add_element_user(EXCEPTIONORPHANEDKEYACTION$252);
         }

         target.setNil();
      }
   }

   public void unsetExceptionOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCEPTIONORPHANEDKEYACTION$252, 0);
      }
   }

   public NoneOrphanedKeyActionType getNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneOrphanedKeyActionType target = null;
         target = (NoneOrphanedKeyActionType)this.get_store().find_element_user(NONEORPHANEDKEYACTION$254, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneOrphanedKeyActionType target = null;
         target = (NoneOrphanedKeyActionType)this.get_store().find_element_user(NONEORPHANEDKEYACTION$254, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONEORPHANEDKEYACTION$254) != 0;
      }
   }

   public void setNoneOrphanedKeyAction(NoneOrphanedKeyActionType noneOrphanedKeyAction) {
      this.generatedSetterHelperImpl(noneOrphanedKeyAction, NONEORPHANEDKEYACTION$254, 0, (short)1);
   }

   public NoneOrphanedKeyActionType addNewNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneOrphanedKeyActionType target = null;
         target = (NoneOrphanedKeyActionType)this.get_store().add_element_user(NONEORPHANEDKEYACTION$254);
         return target;
      }
   }

   public void setNilNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NoneOrphanedKeyActionType target = null;
         target = (NoneOrphanedKeyActionType)this.get_store().find_element_user(NONEORPHANEDKEYACTION$254, 0);
         if (target == null) {
            target = (NoneOrphanedKeyActionType)this.get_store().add_element_user(NONEORPHANEDKEYACTION$254);
         }

         target.setNil();
      }
   }

   public void unsetNoneOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONEORPHANEDKEYACTION$254, 0);
      }
   }

   public CustomOrphanedKeyActionType getCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomOrphanedKeyActionType target = null;
         target = (CustomOrphanedKeyActionType)this.get_store().find_element_user(CUSTOMORPHANEDKEYACTION$256, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomOrphanedKeyActionType target = null;
         target = (CustomOrphanedKeyActionType)this.get_store().find_element_user(CUSTOMORPHANEDKEYACTION$256, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMORPHANEDKEYACTION$256) != 0;
      }
   }

   public void setCustomOrphanedKeyAction(CustomOrphanedKeyActionType customOrphanedKeyAction) {
      this.generatedSetterHelperImpl(customOrphanedKeyAction, CUSTOMORPHANEDKEYACTION$256, 0, (short)1);
   }

   public CustomOrphanedKeyActionType addNewCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomOrphanedKeyActionType target = null;
         target = (CustomOrphanedKeyActionType)this.get_store().add_element_user(CUSTOMORPHANEDKEYACTION$256);
         return target;
      }
   }

   public void setNilCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomOrphanedKeyActionType target = null;
         target = (CustomOrphanedKeyActionType)this.get_store().find_element_user(CUSTOMORPHANEDKEYACTION$256, 0);
         if (target == null) {
            target = (CustomOrphanedKeyActionType)this.get_store().add_element_user(CUSTOMORPHANEDKEYACTION$256);
         }

         target.setNil();
      }
   }

   public void unsetCustomOrphanedKeyAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMORPHANEDKEYACTION$256, 0);
      }
   }

   public HttpTransportType getHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpTransportType target = null;
         target = (HttpTransportType)this.get_store().find_element_user(HTTPTRANSPORT$258, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpTransportType target = null;
         target = (HttpTransportType)this.get_store().find_element_user(HTTPTRANSPORT$258, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HTTPTRANSPORT$258) != 0;
      }
   }

   public void setHttpTransport(HttpTransportType httpTransport) {
      this.generatedSetterHelperImpl(httpTransport, HTTPTRANSPORT$258, 0, (short)1);
   }

   public HttpTransportType addNewHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpTransportType target = null;
         target = (HttpTransportType)this.get_store().add_element_user(HTTPTRANSPORT$258);
         return target;
      }
   }

   public void setNilHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HttpTransportType target = null;
         target = (HttpTransportType)this.get_store().find_element_user(HTTPTRANSPORT$258, 0);
         if (target == null) {
            target = (HttpTransportType)this.get_store().add_element_user(HTTPTRANSPORT$258);
         }

         target.setNil();
      }
   }

   public void unsetHttpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HTTPTRANSPORT$258, 0);
      }
   }

   public TcpTransportType getTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpTransportType target = null;
         target = (TcpTransportType)this.get_store().find_element_user(TCPTRANSPORT$260, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpTransportType target = null;
         target = (TcpTransportType)this.get_store().find_element_user(TCPTRANSPORT$260, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TCPTRANSPORT$260) != 0;
      }
   }

   public void setTcpTransport(TcpTransportType tcpTransport) {
      this.generatedSetterHelperImpl(tcpTransport, TCPTRANSPORT$260, 0, (short)1);
   }

   public TcpTransportType addNewTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpTransportType target = null;
         target = (TcpTransportType)this.get_store().add_element_user(TCPTRANSPORT$260);
         return target;
      }
   }

   public void setNilTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpTransportType target = null;
         target = (TcpTransportType)this.get_store().find_element_user(TCPTRANSPORT$260, 0);
         if (target == null) {
            target = (TcpTransportType)this.get_store().add_element_user(TCPTRANSPORT$260);
         }

         target.setNil();
      }
   }

   public void unsetTcpTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TCPTRANSPORT$260, 0);
      }
   }

   public CustomPersistenceServerType getCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomPersistenceServerType target = null;
         target = (CustomPersistenceServerType)this.get_store().find_element_user(CUSTOMPERSISTENCESERVER$262, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomPersistenceServerType target = null;
         target = (CustomPersistenceServerType)this.get_store().find_element_user(CUSTOMPERSISTENCESERVER$262, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMPERSISTENCESERVER$262) != 0;
      }
   }

   public void setCustomPersistenceServer(CustomPersistenceServerType customPersistenceServer) {
      this.generatedSetterHelperImpl(customPersistenceServer, CUSTOMPERSISTENCESERVER$262, 0, (short)1);
   }

   public CustomPersistenceServerType addNewCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomPersistenceServerType target = null;
         target = (CustomPersistenceServerType)this.get_store().add_element_user(CUSTOMPERSISTENCESERVER$262);
         return target;
      }
   }

   public void setNilCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomPersistenceServerType target = null;
         target = (CustomPersistenceServerType)this.get_store().find_element_user(CUSTOMPERSISTENCESERVER$262, 0);
         if (target == null) {
            target = (CustomPersistenceServerType)this.get_store().add_element_user(CUSTOMPERSISTENCESERVER$262);
         }

         target.setNil();
      }
   }

   public void unsetCustomPersistenceServer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMPERSISTENCESERVER$262, 0);
      }
   }

   public DefaultProxyManagerType getDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultProxyManagerType target = null;
         target = (DefaultProxyManagerType)this.get_store().find_element_user(DEFAULTPROXYMANAGER$264, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultProxyManagerType target = null;
         target = (DefaultProxyManagerType)this.get_store().find_element_user(DEFAULTPROXYMANAGER$264, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPROXYMANAGER$264) != 0;
      }
   }

   public void setDefaultProxyManager(DefaultProxyManagerType defaultProxyManager) {
      this.generatedSetterHelperImpl(defaultProxyManager, DEFAULTPROXYMANAGER$264, 0, (short)1);
   }

   public DefaultProxyManagerType addNewDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultProxyManagerType target = null;
         target = (DefaultProxyManagerType)this.get_store().add_element_user(DEFAULTPROXYMANAGER$264);
         return target;
      }
   }

   public void setNilDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultProxyManagerType target = null;
         target = (DefaultProxyManagerType)this.get_store().find_element_user(DEFAULTPROXYMANAGER$264, 0);
         if (target == null) {
            target = (DefaultProxyManagerType)this.get_store().add_element_user(DEFAULTPROXYMANAGER$264);
         }

         target.setNil();
      }
   }

   public void unsetDefaultProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPROXYMANAGER$264, 0);
      }
   }

   public ProfilingProxyManagerType getProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingProxyManagerType target = null;
         target = (ProfilingProxyManagerType)this.get_store().find_element_user(PROFILINGPROXYMANAGER$266, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingProxyManagerType target = null;
         target = (ProfilingProxyManagerType)this.get_store().find_element_user(PROFILINGPROXYMANAGER$266, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROFILINGPROXYMANAGER$266) != 0;
      }
   }

   public void setProfilingProxyManager(ProfilingProxyManagerType profilingProxyManager) {
      this.generatedSetterHelperImpl(profilingProxyManager, PROFILINGPROXYMANAGER$266, 0, (short)1);
   }

   public ProfilingProxyManagerType addNewProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingProxyManagerType target = null;
         target = (ProfilingProxyManagerType)this.get_store().add_element_user(PROFILINGPROXYMANAGER$266);
         return target;
      }
   }

   public void setNilProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProfilingProxyManagerType target = null;
         target = (ProfilingProxyManagerType)this.get_store().find_element_user(PROFILINGPROXYMANAGER$266, 0);
         if (target == null) {
            target = (ProfilingProxyManagerType)this.get_store().add_element_user(PROFILINGPROXYMANAGER$266);
         }

         target.setNil();
      }
   }

   public void unsetProfilingProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROFILINGPROXYMANAGER$266, 0);
      }
   }

   public ProxyManagerImplType getProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyManagerImplType target = null;
         target = (ProxyManagerImplType)this.get_store().find_element_user(PROXYMANAGERIMPL$268, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyManagerImplType target = null;
         target = (ProxyManagerImplType)this.get_store().find_element_user(PROXYMANAGERIMPL$268, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROXYMANAGERIMPL$268) != 0;
      }
   }

   public void setProxyManagerImpl(ProxyManagerImplType proxyManagerImpl) {
      this.generatedSetterHelperImpl(proxyManagerImpl, PROXYMANAGERIMPL$268, 0, (short)1);
   }

   public ProxyManagerImplType addNewProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyManagerImplType target = null;
         target = (ProxyManagerImplType)this.get_store().add_element_user(PROXYMANAGERIMPL$268);
         return target;
      }
   }

   public void setNilProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProxyManagerImplType target = null;
         target = (ProxyManagerImplType)this.get_store().find_element_user(PROXYMANAGERIMPL$268, 0);
         if (target == null) {
            target = (ProxyManagerImplType)this.get_store().add_element_user(PROXYMANAGERIMPL$268);
         }

         target.setNil();
      }
   }

   public void unsetProxyManagerImpl() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROXYMANAGERIMPL$268, 0);
      }
   }

   public CustomProxyManagerType getCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomProxyManagerType target = null;
         target = (CustomProxyManagerType)this.get_store().find_element_user(CUSTOMPROXYMANAGER$270, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomProxyManagerType target = null;
         target = (CustomProxyManagerType)this.get_store().find_element_user(CUSTOMPROXYMANAGER$270, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMPROXYMANAGER$270) != 0;
      }
   }

   public void setCustomProxyManager(CustomProxyManagerType customProxyManager) {
      this.generatedSetterHelperImpl(customProxyManager, CUSTOMPROXYMANAGER$270, 0, (short)1);
   }

   public CustomProxyManagerType addNewCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomProxyManagerType target = null;
         target = (CustomProxyManagerType)this.get_store().add_element_user(CUSTOMPROXYMANAGER$270);
         return target;
      }
   }

   public void setNilCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomProxyManagerType target = null;
         target = (CustomProxyManagerType)this.get_store().find_element_user(CUSTOMPROXYMANAGER$270, 0);
         if (target == null) {
            target = (CustomProxyManagerType)this.get_store().add_element_user(CUSTOMPROXYMANAGER$270);
         }

         target.setNil();
      }
   }

   public void unsetCustomProxyManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMPROXYMANAGER$270, 0);
      }
   }

   public QueryCachesType getQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryCachesType target = null;
         target = (QueryCachesType)this.get_store().find_element_user(QUERYCACHES$272, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryCachesType target = null;
         target = (QueryCachesType)this.get_store().find_element_user(QUERYCACHES$272, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUERYCACHES$272) != 0;
      }
   }

   public void setQueryCaches(QueryCachesType queryCaches) {
      this.generatedSetterHelperImpl(queryCaches, QUERYCACHES$272, 0, (short)1);
   }

   public QueryCachesType addNewQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryCachesType target = null;
         target = (QueryCachesType)this.get_store().add_element_user(QUERYCACHES$272);
         return target;
      }
   }

   public void setNilQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         QueryCachesType target = null;
         target = (QueryCachesType)this.get_store().find_element_user(QUERYCACHES$272, 0);
         if (target == null) {
            target = (QueryCachesType)this.get_store().add_element_user(QUERYCACHES$272);
         }

         target.setNil();
      }
   }

   public void unsetQueryCaches() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUERYCACHES$272, 0);
      }
   }

   public DefaultQueryCompilationCacheType getDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCompilationCacheType target = null;
         target = (DefaultQueryCompilationCacheType)this.get_store().find_element_user(DEFAULTQUERYCOMPILATIONCACHE$274, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCompilationCacheType target = null;
         target = (DefaultQueryCompilationCacheType)this.get_store().find_element_user(DEFAULTQUERYCOMPILATIONCACHE$274, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTQUERYCOMPILATIONCACHE$274) != 0;
      }
   }

   public void setDefaultQueryCompilationCache(DefaultQueryCompilationCacheType defaultQueryCompilationCache) {
      this.generatedSetterHelperImpl(defaultQueryCompilationCache, DEFAULTQUERYCOMPILATIONCACHE$274, 0, (short)1);
   }

   public DefaultQueryCompilationCacheType addNewDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCompilationCacheType target = null;
         target = (DefaultQueryCompilationCacheType)this.get_store().add_element_user(DEFAULTQUERYCOMPILATIONCACHE$274);
         return target;
      }
   }

   public void setNilDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultQueryCompilationCacheType target = null;
         target = (DefaultQueryCompilationCacheType)this.get_store().find_element_user(DEFAULTQUERYCOMPILATIONCACHE$274, 0);
         if (target == null) {
            target = (DefaultQueryCompilationCacheType)this.get_store().add_element_user(DEFAULTQUERYCOMPILATIONCACHE$274);
         }

         target.setNil();
      }
   }

   public void unsetDefaultQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTQUERYCOMPILATIONCACHE$274, 0);
      }
   }

   public CacheMapType getCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheMapType target = null;
         target = (CacheMapType)this.get_store().find_element_user(CACHEMAP$276, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheMapType target = null;
         target = (CacheMapType)this.get_store().find_element_user(CACHEMAP$276, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHEMAP$276) != 0;
      }
   }

   public void setCacheMap(CacheMapType cacheMap) {
      this.generatedSetterHelperImpl(cacheMap, CACHEMAP$276, 0, (short)1);
   }

   public CacheMapType addNewCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheMapType target = null;
         target = (CacheMapType)this.get_store().add_element_user(CACHEMAP$276);
         return target;
      }
   }

   public void setNilCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CacheMapType target = null;
         target = (CacheMapType)this.get_store().find_element_user(CACHEMAP$276, 0);
         if (target == null) {
            target = (CacheMapType)this.get_store().add_element_user(CACHEMAP$276);
         }

         target.setNil();
      }
   }

   public void unsetCacheMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHEMAP$276, 0);
      }
   }

   public ConcurrentHashMapType getConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentHashMapType target = null;
         target = (ConcurrentHashMapType)this.get_store().find_element_user(CONCURRENTHASHMAP$278, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentHashMapType target = null;
         target = (ConcurrentHashMapType)this.get_store().find_element_user(CONCURRENTHASHMAP$278, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONCURRENTHASHMAP$278) != 0;
      }
   }

   public void setConcurrentHashMap(ConcurrentHashMapType concurrentHashMap) {
      this.generatedSetterHelperImpl(concurrentHashMap, CONCURRENTHASHMAP$278, 0, (short)1);
   }

   public ConcurrentHashMapType addNewConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentHashMapType target = null;
         target = (ConcurrentHashMapType)this.get_store().add_element_user(CONCURRENTHASHMAP$278);
         return target;
      }
   }

   public void setNilConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConcurrentHashMapType target = null;
         target = (ConcurrentHashMapType)this.get_store().find_element_user(CONCURRENTHASHMAP$278, 0);
         if (target == null) {
            target = (ConcurrentHashMapType)this.get_store().add_element_user(CONCURRENTHASHMAP$278);
         }

         target.setNil();
      }
   }

   public void unsetConcurrentHashMap() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONCURRENTHASHMAP$278, 0);
      }
   }

   public CustomQueryCompilationCacheType getCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCompilationCacheType target = null;
         target = (CustomQueryCompilationCacheType)this.get_store().find_element_user(CUSTOMQUERYCOMPILATIONCACHE$280, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCompilationCacheType target = null;
         target = (CustomQueryCompilationCacheType)this.get_store().find_element_user(CUSTOMQUERYCOMPILATIONCACHE$280, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMQUERYCOMPILATIONCACHE$280) != 0;
      }
   }

   public void setCustomQueryCompilationCache(CustomQueryCompilationCacheType customQueryCompilationCache) {
      this.generatedSetterHelperImpl(customQueryCompilationCache, CUSTOMQUERYCOMPILATIONCACHE$280, 0, (short)1);
   }

   public CustomQueryCompilationCacheType addNewCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCompilationCacheType target = null;
         target = (CustomQueryCompilationCacheType)this.get_store().add_element_user(CUSTOMQUERYCOMPILATIONCACHE$280);
         return target;
      }
   }

   public void setNilCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomQueryCompilationCacheType target = null;
         target = (CustomQueryCompilationCacheType)this.get_store().find_element_user(CUSTOMQUERYCOMPILATIONCACHE$280, 0);
         if (target == null) {
            target = (CustomQueryCompilationCacheType)this.get_store().add_element_user(CUSTOMQUERYCOMPILATIONCACHE$280);
         }

         target.setNil();
      }
   }

   public void unsetCustomQueryCompilationCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMQUERYCOMPILATIONCACHE$280, 0);
      }
   }

   public PersistenceUnitConfigurationType.ReadLockLevel.Enum getReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.ReadLockLevel.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.ReadLockLevel xgetReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ReadLockLevel target = null;
         target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         return target;
      }
   }

   public boolean isNilReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ReadLockLevel target = null;
         target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(READLOCKLEVEL$282) != 0;
      }
   }

   public void setReadLockLevel(PersistenceUnitConfigurationType.ReadLockLevel.Enum readLockLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(READLOCKLEVEL$282);
         }

         target.setEnumValue(readLockLevel);
      }
   }

   public void xsetReadLockLevel(PersistenceUnitConfigurationType.ReadLockLevel readLockLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ReadLockLevel target = null;
         target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().add_element_user(READLOCKLEVEL$282);
         }

         target.set(readLockLevel);
      }
   }

   public void setNilReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ReadLockLevel target = null;
         target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().find_element_user(READLOCKLEVEL$282, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ReadLockLevel)this.get_store().add_element_user(READLOCKLEVEL$282);
         }

         target.setNil();
      }
   }

   public void unsetReadLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(READLOCKLEVEL$282, 0);
      }
   }

   public JmsRemoteCommitProviderType getJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsRemoteCommitProviderType target = null;
         target = (JmsRemoteCommitProviderType)this.get_store().find_element_user(JMSREMOTECOMMITPROVIDER$284, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsRemoteCommitProviderType target = null;
         target = (JmsRemoteCommitProviderType)this.get_store().find_element_user(JMSREMOTECOMMITPROVIDER$284, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSREMOTECOMMITPROVIDER$284) != 0;
      }
   }

   public void setJmsRemoteCommitProvider(JmsRemoteCommitProviderType jmsRemoteCommitProvider) {
      this.generatedSetterHelperImpl(jmsRemoteCommitProvider, JMSREMOTECOMMITPROVIDER$284, 0, (short)1);
   }

   public JmsRemoteCommitProviderType addNewJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsRemoteCommitProviderType target = null;
         target = (JmsRemoteCommitProviderType)this.get_store().add_element_user(JMSREMOTECOMMITPROVIDER$284);
         return target;
      }
   }

   public void setNilJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JmsRemoteCommitProviderType target = null;
         target = (JmsRemoteCommitProviderType)this.get_store().find_element_user(JMSREMOTECOMMITPROVIDER$284, 0);
         if (target == null) {
            target = (JmsRemoteCommitProviderType)this.get_store().add_element_user(JMSREMOTECOMMITPROVIDER$284);
         }

         target.setNil();
      }
   }

   public void unsetJmsRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSREMOTECOMMITPROVIDER$284, 0);
      }
   }

   public SingleJvmRemoteCommitProviderType getSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmRemoteCommitProviderType target = null;
         target = (SingleJvmRemoteCommitProviderType)this.get_store().find_element_user(SINGLEJVMREMOTECOMMITPROVIDER$286, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmRemoteCommitProviderType target = null;
         target = (SingleJvmRemoteCommitProviderType)this.get_store().find_element_user(SINGLEJVMREMOTECOMMITPROVIDER$286, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLEJVMREMOTECOMMITPROVIDER$286) != 0;
      }
   }

   public void setSingleJvmRemoteCommitProvider(SingleJvmRemoteCommitProviderType singleJvmRemoteCommitProvider) {
      this.generatedSetterHelperImpl(singleJvmRemoteCommitProvider, SINGLEJVMREMOTECOMMITPROVIDER$286, 0, (short)1);
   }

   public SingleJvmRemoteCommitProviderType addNewSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmRemoteCommitProviderType target = null;
         target = (SingleJvmRemoteCommitProviderType)this.get_store().add_element_user(SINGLEJVMREMOTECOMMITPROVIDER$286);
         return target;
      }
   }

   public void setNilSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SingleJvmRemoteCommitProviderType target = null;
         target = (SingleJvmRemoteCommitProviderType)this.get_store().find_element_user(SINGLEJVMREMOTECOMMITPROVIDER$286, 0);
         if (target == null) {
            target = (SingleJvmRemoteCommitProviderType)this.get_store().add_element_user(SINGLEJVMREMOTECOMMITPROVIDER$286);
         }

         target.setNil();
      }
   }

   public void unsetSingleJvmRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLEJVMREMOTECOMMITPROVIDER$286, 0);
      }
   }

   public TcpRemoteCommitProviderType getTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpRemoteCommitProviderType target = null;
         target = (TcpRemoteCommitProviderType)this.get_store().find_element_user(TCPREMOTECOMMITPROVIDER$288, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpRemoteCommitProviderType target = null;
         target = (TcpRemoteCommitProviderType)this.get_store().find_element_user(TCPREMOTECOMMITPROVIDER$288, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TCPREMOTECOMMITPROVIDER$288) != 0;
      }
   }

   public void setTcpRemoteCommitProvider(TcpRemoteCommitProviderType tcpRemoteCommitProvider) {
      this.generatedSetterHelperImpl(tcpRemoteCommitProvider, TCPREMOTECOMMITPROVIDER$288, 0, (short)1);
   }

   public TcpRemoteCommitProviderType addNewTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpRemoteCommitProviderType target = null;
         target = (TcpRemoteCommitProviderType)this.get_store().add_element_user(TCPREMOTECOMMITPROVIDER$288);
         return target;
      }
   }

   public void setNilTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TcpRemoteCommitProviderType target = null;
         target = (TcpRemoteCommitProviderType)this.get_store().find_element_user(TCPREMOTECOMMITPROVIDER$288, 0);
         if (target == null) {
            target = (TcpRemoteCommitProviderType)this.get_store().add_element_user(TCPREMOTECOMMITPROVIDER$288);
         }

         target.setNil();
      }
   }

   public void unsetTcpRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TCPREMOTECOMMITPROVIDER$288, 0);
      }
   }

   public ClusterRemoteCommitProviderType getClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType target = null;
         target = (ClusterRemoteCommitProviderType)this.get_store().find_element_user(CLUSTERREMOTECOMMITPROVIDER$290, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType target = null;
         target = (ClusterRemoteCommitProviderType)this.get_store().find_element_user(CLUSTERREMOTECOMMITPROVIDER$290, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLUSTERREMOTECOMMITPROVIDER$290) != 0;
      }
   }

   public void setClusterRemoteCommitProvider(ClusterRemoteCommitProviderType clusterRemoteCommitProvider) {
      this.generatedSetterHelperImpl(clusterRemoteCommitProvider, CLUSTERREMOTECOMMITPROVIDER$290, 0, (short)1);
   }

   public ClusterRemoteCommitProviderType addNewClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType target = null;
         target = (ClusterRemoteCommitProviderType)this.get_store().add_element_user(CLUSTERREMOTECOMMITPROVIDER$290);
         return target;
      }
   }

   public void setNilClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType target = null;
         target = (ClusterRemoteCommitProviderType)this.get_store().find_element_user(CLUSTERREMOTECOMMITPROVIDER$290, 0);
         if (target == null) {
            target = (ClusterRemoteCommitProviderType)this.get_store().add_element_user(CLUSTERREMOTECOMMITPROVIDER$290);
         }

         target.setNil();
      }
   }

   public void unsetClusterRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLUSTERREMOTECOMMITPROVIDER$290, 0);
      }
   }

   public CustomRemoteCommitProviderType getCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomRemoteCommitProviderType target = null;
         target = (CustomRemoteCommitProviderType)this.get_store().find_element_user(CUSTOMREMOTECOMMITPROVIDER$292, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomRemoteCommitProviderType target = null;
         target = (CustomRemoteCommitProviderType)this.get_store().find_element_user(CUSTOMREMOTECOMMITPROVIDER$292, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMREMOTECOMMITPROVIDER$292) != 0;
      }
   }

   public void setCustomRemoteCommitProvider(CustomRemoteCommitProviderType customRemoteCommitProvider) {
      this.generatedSetterHelperImpl(customRemoteCommitProvider, CUSTOMREMOTECOMMITPROVIDER$292, 0, (short)1);
   }

   public CustomRemoteCommitProviderType addNewCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomRemoteCommitProviderType target = null;
         target = (CustomRemoteCommitProviderType)this.get_store().add_element_user(CUSTOMREMOTECOMMITPROVIDER$292);
         return target;
      }
   }

   public void setNilCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomRemoteCommitProviderType target = null;
         target = (CustomRemoteCommitProviderType)this.get_store().find_element_user(CUSTOMREMOTECOMMITPROVIDER$292, 0);
         if (target == null) {
            target = (CustomRemoteCommitProviderType)this.get_store().add_element_user(CUSTOMREMOTECOMMITPROVIDER$292);
         }

         target.setNil();
      }
   }

   public void unsetCustomRemoteCommitProvider() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMREMOTECOMMITPROVIDER$292, 0);
      }
   }

   public PersistenceUnitConfigurationType.RestoreState.Enum getRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTORESTATE$294, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.RestoreState.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.RestoreState xgetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.RestoreState target = null;
         target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().find_element_user(RESTORESTATE$294, 0);
         return target;
      }
   }

   public boolean isNilRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.RestoreState target = null;
         target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().find_element_user(RESTORESTATE$294, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESTORESTATE$294) != 0;
      }
   }

   public void setRestoreState(PersistenceUnitConfigurationType.RestoreState.Enum restoreState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTORESTATE$294, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESTORESTATE$294);
         }

         target.setEnumValue(restoreState);
      }
   }

   public void xsetRestoreState(PersistenceUnitConfigurationType.RestoreState restoreState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.RestoreState target = null;
         target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().find_element_user(RESTORESTATE$294, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().add_element_user(RESTORESTATE$294);
         }

         target.set(restoreState);
      }
   }

   public void setNilRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.RestoreState target = null;
         target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().find_element_user(RESTORESTATE$294, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.RestoreState)this.get_store().add_element_user(RESTORESTATE$294);
         }

         target.setNil();
      }
   }

   public void unsetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESTORESTATE$294, 0);
      }
   }

   public PersistenceUnitConfigurationType.ResultSetType.Enum getResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.ResultSetType.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.ResultSetType xgetResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ResultSetType target = null;
         target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         return target;
      }
   }

   public boolean isNilResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ResultSetType target = null;
         target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESULTSETTYPE$296) != 0;
      }
   }

   public void setResultSetType(PersistenceUnitConfigurationType.ResultSetType.Enum resultSetType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESULTSETTYPE$296);
         }

         target.setEnumValue(resultSetType);
      }
   }

   public void xsetResultSetType(PersistenceUnitConfigurationType.ResultSetType resultSetType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ResultSetType target = null;
         target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().add_element_user(RESULTSETTYPE$296);
         }

         target.set(resultSetType);
      }
   }

   public void setNilResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.ResultSetType target = null;
         target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().find_element_user(RESULTSETTYPE$296, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.ResultSetType)this.get_store().add_element_user(RESULTSETTYPE$296);
         }

         target.setNil();
      }
   }

   public void unsetResultSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESULTSETTYPE$296, 0);
      }
   }

   public boolean getRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETAINSTATE$298, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETAINSTATE$298, 0);
         return target;
      }
   }

   public boolean isSetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETAINSTATE$298) != 0;
      }
   }

   public void setRetainState(boolean retainState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETAINSTATE$298, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETAINSTATE$298);
         }

         target.setBooleanValue(retainState);
      }
   }

   public void xsetRetainState(XmlBoolean retainState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETAINSTATE$298, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RETAINSTATE$298);
         }

         target.set(retainState);
      }
   }

   public void unsetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETAINSTATE$298, 0);
      }
   }

   public boolean getRetryClassRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRYCLASSREGISTRATION$300, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRetryClassRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETRYCLASSREGISTRATION$300, 0);
         return target;
      }
   }

   public boolean isSetRetryClassRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETRYCLASSREGISTRATION$300) != 0;
      }
   }

   public void setRetryClassRegistration(boolean retryClassRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETRYCLASSREGISTRATION$300, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETRYCLASSREGISTRATION$300);
         }

         target.setBooleanValue(retryClassRegistration);
      }
   }

   public void xsetRetryClassRegistration(XmlBoolean retryClassRegistration) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETRYCLASSREGISTRATION$300, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RETRYCLASSREGISTRATION$300);
         }

         target.set(retryClassRegistration);
      }
   }

   public void unsetRetryClassRegistration() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETRYCLASSREGISTRATION$300, 0);
      }
   }

   public DefaultSavepointManagerType getDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSavepointManagerType target = null;
         target = (DefaultSavepointManagerType)this.get_store().find_element_user(DEFAULTSAVEPOINTMANAGER$302, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSavepointManagerType target = null;
         target = (DefaultSavepointManagerType)this.get_store().find_element_user(DEFAULTSAVEPOINTMANAGER$302, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTSAVEPOINTMANAGER$302) != 0;
      }
   }

   public void setDefaultSavepointManager(DefaultSavepointManagerType defaultSavepointManager) {
      this.generatedSetterHelperImpl(defaultSavepointManager, DEFAULTSAVEPOINTMANAGER$302, 0, (short)1);
   }

   public DefaultSavepointManagerType addNewDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSavepointManagerType target = null;
         target = (DefaultSavepointManagerType)this.get_store().add_element_user(DEFAULTSAVEPOINTMANAGER$302);
         return target;
      }
   }

   public void setNilDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSavepointManagerType target = null;
         target = (DefaultSavepointManagerType)this.get_store().find_element_user(DEFAULTSAVEPOINTMANAGER$302, 0);
         if (target == null) {
            target = (DefaultSavepointManagerType)this.get_store().add_element_user(DEFAULTSAVEPOINTMANAGER$302);
         }

         target.setNil();
      }
   }

   public void unsetDefaultSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTSAVEPOINTMANAGER$302, 0);
      }
   }

   public InMemorySavepointManagerType getInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InMemorySavepointManagerType target = null;
         target = (InMemorySavepointManagerType)this.get_store().find_element_user(INMEMORYSAVEPOINTMANAGER$304, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InMemorySavepointManagerType target = null;
         target = (InMemorySavepointManagerType)this.get_store().find_element_user(INMEMORYSAVEPOINTMANAGER$304, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INMEMORYSAVEPOINTMANAGER$304) != 0;
      }
   }

   public void setInMemorySavepointManager(InMemorySavepointManagerType inMemorySavepointManager) {
      this.generatedSetterHelperImpl(inMemorySavepointManager, INMEMORYSAVEPOINTMANAGER$304, 0, (short)1);
   }

   public InMemorySavepointManagerType addNewInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InMemorySavepointManagerType target = null;
         target = (InMemorySavepointManagerType)this.get_store().add_element_user(INMEMORYSAVEPOINTMANAGER$304);
         return target;
      }
   }

   public void setNilInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InMemorySavepointManagerType target = null;
         target = (InMemorySavepointManagerType)this.get_store().find_element_user(INMEMORYSAVEPOINTMANAGER$304, 0);
         if (target == null) {
            target = (InMemorySavepointManagerType)this.get_store().add_element_user(INMEMORYSAVEPOINTMANAGER$304);
         }

         target.setNil();
      }
   }

   public void unsetInMemorySavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INMEMORYSAVEPOINTMANAGER$304, 0);
      }
   }

   public Jdbc3SavepointManagerType getJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jdbc3SavepointManagerType target = null;
         target = (Jdbc3SavepointManagerType)this.get_store().find_element_user(JDBC3SAVEPOINTMANAGER$306, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jdbc3SavepointManagerType target = null;
         target = (Jdbc3SavepointManagerType)this.get_store().find_element_user(JDBC3SAVEPOINTMANAGER$306, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JDBC3SAVEPOINTMANAGER$306) != 0;
      }
   }

   public void setJdbc3SavepointManager(Jdbc3SavepointManagerType jdbc3SavepointManager) {
      this.generatedSetterHelperImpl(jdbc3SavepointManager, JDBC3SAVEPOINTMANAGER$306, 0, (short)1);
   }

   public Jdbc3SavepointManagerType addNewJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jdbc3SavepointManagerType target = null;
         target = (Jdbc3SavepointManagerType)this.get_store().add_element_user(JDBC3SAVEPOINTMANAGER$306);
         return target;
      }
   }

   public void setNilJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Jdbc3SavepointManagerType target = null;
         target = (Jdbc3SavepointManagerType)this.get_store().find_element_user(JDBC3SAVEPOINTMANAGER$306, 0);
         if (target == null) {
            target = (Jdbc3SavepointManagerType)this.get_store().add_element_user(JDBC3SAVEPOINTMANAGER$306);
         }

         target.setNil();
      }
   }

   public void unsetJdbc3SavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JDBC3SAVEPOINTMANAGER$306, 0);
      }
   }

   public OracleSavepointManagerType getOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleSavepointManagerType target = null;
         target = (OracleSavepointManagerType)this.get_store().find_element_user(ORACLESAVEPOINTMANAGER$308, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleSavepointManagerType target = null;
         target = (OracleSavepointManagerType)this.get_store().find_element_user(ORACLESAVEPOINTMANAGER$308, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORACLESAVEPOINTMANAGER$308) != 0;
      }
   }

   public void setOracleSavepointManager(OracleSavepointManagerType oracleSavepointManager) {
      this.generatedSetterHelperImpl(oracleSavepointManager, ORACLESAVEPOINTMANAGER$308, 0, (short)1);
   }

   public OracleSavepointManagerType addNewOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleSavepointManagerType target = null;
         target = (OracleSavepointManagerType)this.get_store().add_element_user(ORACLESAVEPOINTMANAGER$308);
         return target;
      }
   }

   public void setNilOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OracleSavepointManagerType target = null;
         target = (OracleSavepointManagerType)this.get_store().find_element_user(ORACLESAVEPOINTMANAGER$308, 0);
         if (target == null) {
            target = (OracleSavepointManagerType)this.get_store().add_element_user(ORACLESAVEPOINTMANAGER$308);
         }

         target.setNil();
      }
   }

   public void unsetOracleSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORACLESAVEPOINTMANAGER$308, 0);
      }
   }

   public CustomSavepointManagerType getCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSavepointManagerType target = null;
         target = (CustomSavepointManagerType)this.get_store().find_element_user(CUSTOMSAVEPOINTMANAGER$310, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSavepointManagerType target = null;
         target = (CustomSavepointManagerType)this.get_store().find_element_user(CUSTOMSAVEPOINTMANAGER$310, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMSAVEPOINTMANAGER$310) != 0;
      }
   }

   public void setCustomSavepointManager(CustomSavepointManagerType customSavepointManager) {
      this.generatedSetterHelperImpl(customSavepointManager, CUSTOMSAVEPOINTMANAGER$310, 0, (short)1);
   }

   public CustomSavepointManagerType addNewCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSavepointManagerType target = null;
         target = (CustomSavepointManagerType)this.get_store().add_element_user(CUSTOMSAVEPOINTMANAGER$310);
         return target;
      }
   }

   public void setNilCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSavepointManagerType target = null;
         target = (CustomSavepointManagerType)this.get_store().find_element_user(CUSTOMSAVEPOINTMANAGER$310, 0);
         if (target == null) {
            target = (CustomSavepointManagerType)this.get_store().add_element_user(CUSTOMSAVEPOINTMANAGER$310);
         }

         target.setNil();
      }
   }

   public void unsetCustomSavepointManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMSAVEPOINTMANAGER$310, 0);
      }
   }

   public String getSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMA$312, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$312, 0);
         return target;
      }
   }

   public boolean isNilSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$312, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMA$312) != 0;
      }
   }

   public void setSchema(String schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SCHEMA$312, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SCHEMA$312);
         }

         target.setStringValue(schema);
      }
   }

   public void xsetSchema(XmlString schema) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$312, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMA$312);
         }

         target.set(schema);
      }
   }

   public void setNilSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SCHEMA$312, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SCHEMA$312);
         }

         target.setNil();
      }
   }

   public void unsetSchema() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMA$312, 0);
      }
   }

   public DefaultSchemaFactoryType getDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSchemaFactoryType target = null;
         target = (DefaultSchemaFactoryType)this.get_store().find_element_user(DEFAULTSCHEMAFACTORY$314, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSchemaFactoryType target = null;
         target = (DefaultSchemaFactoryType)this.get_store().find_element_user(DEFAULTSCHEMAFACTORY$314, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTSCHEMAFACTORY$314) != 0;
      }
   }

   public void setDefaultSchemaFactory(DefaultSchemaFactoryType defaultSchemaFactory) {
      this.generatedSetterHelperImpl(defaultSchemaFactory, DEFAULTSCHEMAFACTORY$314, 0, (short)1);
   }

   public DefaultSchemaFactoryType addNewDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSchemaFactoryType target = null;
         target = (DefaultSchemaFactoryType)this.get_store().add_element_user(DEFAULTSCHEMAFACTORY$314);
         return target;
      }
   }

   public void setNilDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSchemaFactoryType target = null;
         target = (DefaultSchemaFactoryType)this.get_store().find_element_user(DEFAULTSCHEMAFACTORY$314, 0);
         if (target == null) {
            target = (DefaultSchemaFactoryType)this.get_store().add_element_user(DEFAULTSCHEMAFACTORY$314);
         }

         target.setNil();
      }
   }

   public void unsetDefaultSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTSCHEMAFACTORY$314, 0);
      }
   }

   public DynamicSchemaFactoryType getDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DynamicSchemaFactoryType target = null;
         target = (DynamicSchemaFactoryType)this.get_store().find_element_user(DYNAMICSCHEMAFACTORY$316, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DynamicSchemaFactoryType target = null;
         target = (DynamicSchemaFactoryType)this.get_store().find_element_user(DYNAMICSCHEMAFACTORY$316, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DYNAMICSCHEMAFACTORY$316) != 0;
      }
   }

   public void setDynamicSchemaFactory(DynamicSchemaFactoryType dynamicSchemaFactory) {
      this.generatedSetterHelperImpl(dynamicSchemaFactory, DYNAMICSCHEMAFACTORY$316, 0, (short)1);
   }

   public DynamicSchemaFactoryType addNewDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DynamicSchemaFactoryType target = null;
         target = (DynamicSchemaFactoryType)this.get_store().add_element_user(DYNAMICSCHEMAFACTORY$316);
         return target;
      }
   }

   public void setNilDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DynamicSchemaFactoryType target = null;
         target = (DynamicSchemaFactoryType)this.get_store().find_element_user(DYNAMICSCHEMAFACTORY$316, 0);
         if (target == null) {
            target = (DynamicSchemaFactoryType)this.get_store().add_element_user(DYNAMICSCHEMAFACTORY$316);
         }

         target.setNil();
      }
   }

   public void unsetDynamicSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DYNAMICSCHEMAFACTORY$316, 0);
      }
   }

   public FileSchemaFactoryType getFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileSchemaFactoryType target = null;
         target = (FileSchemaFactoryType)this.get_store().find_element_user(FILESCHEMAFACTORY$318, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileSchemaFactoryType target = null;
         target = (FileSchemaFactoryType)this.get_store().find_element_user(FILESCHEMAFACTORY$318, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FILESCHEMAFACTORY$318) != 0;
      }
   }

   public void setFileSchemaFactory(FileSchemaFactoryType fileSchemaFactory) {
      this.generatedSetterHelperImpl(fileSchemaFactory, FILESCHEMAFACTORY$318, 0, (short)1);
   }

   public FileSchemaFactoryType addNewFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileSchemaFactoryType target = null;
         target = (FileSchemaFactoryType)this.get_store().add_element_user(FILESCHEMAFACTORY$318);
         return target;
      }
   }

   public void setNilFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FileSchemaFactoryType target = null;
         target = (FileSchemaFactoryType)this.get_store().find_element_user(FILESCHEMAFACTORY$318, 0);
         if (target == null) {
            target = (FileSchemaFactoryType)this.get_store().add_element_user(FILESCHEMAFACTORY$318);
         }

         target.setNil();
      }
   }

   public void unsetFileSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FILESCHEMAFACTORY$318, 0);
      }
   }

   public LazySchemaFactoryType getLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LazySchemaFactoryType target = null;
         target = (LazySchemaFactoryType)this.get_store().find_element_user(LAZYSCHEMAFACTORY$320, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LazySchemaFactoryType target = null;
         target = (LazySchemaFactoryType)this.get_store().find_element_user(LAZYSCHEMAFACTORY$320, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LAZYSCHEMAFACTORY$320) != 0;
      }
   }

   public void setLazySchemaFactory(LazySchemaFactoryType lazySchemaFactory) {
      this.generatedSetterHelperImpl(lazySchemaFactory, LAZYSCHEMAFACTORY$320, 0, (short)1);
   }

   public LazySchemaFactoryType addNewLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LazySchemaFactoryType target = null;
         target = (LazySchemaFactoryType)this.get_store().add_element_user(LAZYSCHEMAFACTORY$320);
         return target;
      }
   }

   public void setNilLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LazySchemaFactoryType target = null;
         target = (LazySchemaFactoryType)this.get_store().find_element_user(LAZYSCHEMAFACTORY$320, 0);
         if (target == null) {
            target = (LazySchemaFactoryType)this.get_store().add_element_user(LAZYSCHEMAFACTORY$320);
         }

         target.setNil();
      }
   }

   public void unsetLazySchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LAZYSCHEMAFACTORY$320, 0);
      }
   }

   public TableSchemaFactoryType getTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableSchemaFactoryType target = null;
         target = (TableSchemaFactoryType)this.get_store().find_element_user(TABLESCHEMAFACTORY$322, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableSchemaFactoryType target = null;
         target = (TableSchemaFactoryType)this.get_store().find_element_user(TABLESCHEMAFACTORY$322, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLESCHEMAFACTORY$322) != 0;
      }
   }

   public void setTableSchemaFactory(TableSchemaFactoryType tableSchemaFactory) {
      this.generatedSetterHelperImpl(tableSchemaFactory, TABLESCHEMAFACTORY$322, 0, (short)1);
   }

   public TableSchemaFactoryType addNewTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableSchemaFactoryType target = null;
         target = (TableSchemaFactoryType)this.get_store().add_element_user(TABLESCHEMAFACTORY$322);
         return target;
      }
   }

   public void setNilTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableSchemaFactoryType target = null;
         target = (TableSchemaFactoryType)this.get_store().find_element_user(TABLESCHEMAFACTORY$322, 0);
         if (target == null) {
            target = (TableSchemaFactoryType)this.get_store().add_element_user(TABLESCHEMAFACTORY$322);
         }

         target.setNil();
      }
   }

   public void unsetTableSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLESCHEMAFACTORY$322, 0);
      }
   }

   public CustomSchemaFactoryType getCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSchemaFactoryType target = null;
         target = (CustomSchemaFactoryType)this.get_store().find_element_user(CUSTOMSCHEMAFACTORY$324, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSchemaFactoryType target = null;
         target = (CustomSchemaFactoryType)this.get_store().find_element_user(CUSTOMSCHEMAFACTORY$324, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMSCHEMAFACTORY$324) != 0;
      }
   }

   public void setCustomSchemaFactory(CustomSchemaFactoryType customSchemaFactory) {
      this.generatedSetterHelperImpl(customSchemaFactory, CUSTOMSCHEMAFACTORY$324, 0, (short)1);
   }

   public CustomSchemaFactoryType addNewCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSchemaFactoryType target = null;
         target = (CustomSchemaFactoryType)this.get_store().add_element_user(CUSTOMSCHEMAFACTORY$324);
         return target;
      }
   }

   public void setNilCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSchemaFactoryType target = null;
         target = (CustomSchemaFactoryType)this.get_store().find_element_user(CUSTOMSCHEMAFACTORY$324, 0);
         if (target == null) {
            target = (CustomSchemaFactoryType)this.get_store().add_element_user(CUSTOMSCHEMAFACTORY$324);
         }

         target.setNil();
      }
   }

   public void unsetCustomSchemaFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMSCHEMAFACTORY$324, 0);
      }
   }

   public SchemasType getSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemasType target = null;
         target = (SchemasType)this.get_store().find_element_user(SCHEMAS$326, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemasType target = null;
         target = (SchemasType)this.get_store().find_element_user(SCHEMAS$326, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCHEMAS$326) != 0;
      }
   }

   public void setSchemas(SchemasType schemas) {
      this.generatedSetterHelperImpl(schemas, SCHEMAS$326, 0, (short)1);
   }

   public SchemasType addNewSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemasType target = null;
         target = (SchemasType)this.get_store().add_element_user(SCHEMAS$326);
         return target;
      }
   }

   public void setNilSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SchemasType target = null;
         target = (SchemasType)this.get_store().find_element_user(SCHEMAS$326, 0);
         if (target == null) {
            target = (SchemasType)this.get_store().add_element_user(SCHEMAS$326);
         }

         target.setNil();
      }
   }

   public void unsetSchemas() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCHEMAS$326, 0);
      }
   }

   public ClassTableJdbcSeqType getClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassTableJdbcSeqType target = null;
         target = (ClassTableJdbcSeqType)this.get_store().find_element_user(CLASSTABLEJDBCSEQ$328, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassTableJdbcSeqType target = null;
         target = (ClassTableJdbcSeqType)this.get_store().find_element_user(CLASSTABLEJDBCSEQ$328, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSTABLEJDBCSEQ$328) != 0;
      }
   }

   public void setClassTableJdbcSeq(ClassTableJdbcSeqType classTableJdbcSeq) {
      this.generatedSetterHelperImpl(classTableJdbcSeq, CLASSTABLEJDBCSEQ$328, 0, (short)1);
   }

   public ClassTableJdbcSeqType addNewClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassTableJdbcSeqType target = null;
         target = (ClassTableJdbcSeqType)this.get_store().add_element_user(CLASSTABLEJDBCSEQ$328);
         return target;
      }
   }

   public void setNilClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClassTableJdbcSeqType target = null;
         target = (ClassTableJdbcSeqType)this.get_store().find_element_user(CLASSTABLEJDBCSEQ$328, 0);
         if (target == null) {
            target = (ClassTableJdbcSeqType)this.get_store().add_element_user(CLASSTABLEJDBCSEQ$328);
         }

         target.setNil();
      }
   }

   public void unsetClassTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSTABLEJDBCSEQ$328, 0);
      }
   }

   public NativeJdbcSeqType getNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NativeJdbcSeqType target = null;
         target = (NativeJdbcSeqType)this.get_store().find_element_user(NATIVEJDBCSEQ$330, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NativeJdbcSeqType target = null;
         target = (NativeJdbcSeqType)this.get_store().find_element_user(NATIVEJDBCSEQ$330, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NATIVEJDBCSEQ$330) != 0;
      }
   }

   public void setNativeJdbcSeq(NativeJdbcSeqType nativeJdbcSeq) {
      this.generatedSetterHelperImpl(nativeJdbcSeq, NATIVEJDBCSEQ$330, 0, (short)1);
   }

   public NativeJdbcSeqType addNewNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NativeJdbcSeqType target = null;
         target = (NativeJdbcSeqType)this.get_store().add_element_user(NATIVEJDBCSEQ$330);
         return target;
      }
   }

   public void setNilNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NativeJdbcSeqType target = null;
         target = (NativeJdbcSeqType)this.get_store().find_element_user(NATIVEJDBCSEQ$330, 0);
         if (target == null) {
            target = (NativeJdbcSeqType)this.get_store().add_element_user(NATIVEJDBCSEQ$330);
         }

         target.setNil();
      }
   }

   public void unsetNativeJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NATIVEJDBCSEQ$330, 0);
      }
   }

   public TableJdbcSeqType getTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdbcSeqType target = null;
         target = (TableJdbcSeqType)this.get_store().find_element_user(TABLEJDBCSEQ$332, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdbcSeqType target = null;
         target = (TableJdbcSeqType)this.get_store().find_element_user(TABLEJDBCSEQ$332, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLEJDBCSEQ$332) != 0;
      }
   }

   public void setTableJdbcSeq(TableJdbcSeqType tableJdbcSeq) {
      this.generatedSetterHelperImpl(tableJdbcSeq, TABLEJDBCSEQ$332, 0, (short)1);
   }

   public TableJdbcSeqType addNewTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdbcSeqType target = null;
         target = (TableJdbcSeqType)this.get_store().add_element_user(TABLEJDBCSEQ$332);
         return target;
      }
   }

   public void setNilTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableJdbcSeqType target = null;
         target = (TableJdbcSeqType)this.get_store().find_element_user(TABLEJDBCSEQ$332, 0);
         if (target == null) {
            target = (TableJdbcSeqType)this.get_store().add_element_user(TABLEJDBCSEQ$332);
         }

         target.setNil();
      }
   }

   public void unsetTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLEJDBCSEQ$332, 0);
      }
   }

   public TimeSeededSeqType getTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeSeededSeqType target = null;
         target = (TimeSeededSeqType)this.get_store().find_element_user(TIMESEEDEDSEQ$334, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeSeededSeqType target = null;
         target = (TimeSeededSeqType)this.get_store().find_element_user(TIMESEEDEDSEQ$334, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMESEEDEDSEQ$334) != 0;
      }
   }

   public void setTimeSeededSeq(TimeSeededSeqType timeSeededSeq) {
      this.generatedSetterHelperImpl(timeSeededSeq, TIMESEEDEDSEQ$334, 0, (short)1);
   }

   public TimeSeededSeqType addNewTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeSeededSeqType target = null;
         target = (TimeSeededSeqType)this.get_store().add_element_user(TIMESEEDEDSEQ$334);
         return target;
      }
   }

   public void setNilTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TimeSeededSeqType target = null;
         target = (TimeSeededSeqType)this.get_store().find_element_user(TIMESEEDEDSEQ$334, 0);
         if (target == null) {
            target = (TimeSeededSeqType)this.get_store().add_element_user(TIMESEEDEDSEQ$334);
         }

         target.setNil();
      }
   }

   public void unsetTimeSeededSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMESEEDEDSEQ$334, 0);
      }
   }

   public ValueTableJdbcSeqType getValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValueTableJdbcSeqType target = null;
         target = (ValueTableJdbcSeqType)this.get_store().find_element_user(VALUETABLEJDBCSEQ$336, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValueTableJdbcSeqType target = null;
         target = (ValueTableJdbcSeqType)this.get_store().find_element_user(VALUETABLEJDBCSEQ$336, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VALUETABLEJDBCSEQ$336) != 0;
      }
   }

   public void setValueTableJdbcSeq(ValueTableJdbcSeqType valueTableJdbcSeq) {
      this.generatedSetterHelperImpl(valueTableJdbcSeq, VALUETABLEJDBCSEQ$336, 0, (short)1);
   }

   public ValueTableJdbcSeqType addNewValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValueTableJdbcSeqType target = null;
         target = (ValueTableJdbcSeqType)this.get_store().add_element_user(VALUETABLEJDBCSEQ$336);
         return target;
      }
   }

   public void setNilValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ValueTableJdbcSeqType target = null;
         target = (ValueTableJdbcSeqType)this.get_store().find_element_user(VALUETABLEJDBCSEQ$336, 0);
         if (target == null) {
            target = (ValueTableJdbcSeqType)this.get_store().add_element_user(VALUETABLEJDBCSEQ$336);
         }

         target.setNil();
      }
   }

   public void unsetValueTableJdbcSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VALUETABLEJDBCSEQ$336, 0);
      }
   }

   public CustomSeqType getCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSeqType target = null;
         target = (CustomSeqType)this.get_store().find_element_user(CUSTOMSEQ$338, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSeqType target = null;
         target = (CustomSeqType)this.get_store().find_element_user(CUSTOMSEQ$338, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMSEQ$338) != 0;
      }
   }

   public void setCustomSeq(CustomSeqType customSeq) {
      this.generatedSetterHelperImpl(customSeq, CUSTOMSEQ$338, 0, (short)1);
   }

   public CustomSeqType addNewCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSeqType target = null;
         target = (CustomSeqType)this.get_store().add_element_user(CUSTOMSEQ$338);
         return target;
      }
   }

   public void setNilCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSeqType target = null;
         target = (CustomSeqType)this.get_store().find_element_user(CUSTOMSEQ$338, 0);
         if (target == null) {
            target = (CustomSeqType)this.get_store().add_element_user(CUSTOMSEQ$338);
         }

         target.setNil();
      }
   }

   public void unsetCustomSeq() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMSEQ$338, 0);
      }
   }

   public DefaultSqlFactoryType getDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSqlFactoryType target = null;
         target = (DefaultSqlFactoryType)this.get_store().find_element_user(DEFAULTSQLFACTORY$340, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSqlFactoryType target = null;
         target = (DefaultSqlFactoryType)this.get_store().find_element_user(DEFAULTSQLFACTORY$340, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTSQLFACTORY$340) != 0;
      }
   }

   public void setDefaultSqlFactory(DefaultSqlFactoryType defaultSqlFactory) {
      this.generatedSetterHelperImpl(defaultSqlFactory, DEFAULTSQLFACTORY$340, 0, (short)1);
   }

   public DefaultSqlFactoryType addNewDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSqlFactoryType target = null;
         target = (DefaultSqlFactoryType)this.get_store().add_element_user(DEFAULTSQLFACTORY$340);
         return target;
      }
   }

   public void setNilDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultSqlFactoryType target = null;
         target = (DefaultSqlFactoryType)this.get_store().find_element_user(DEFAULTSQLFACTORY$340, 0);
         if (target == null) {
            target = (DefaultSqlFactoryType)this.get_store().add_element_user(DEFAULTSQLFACTORY$340);
         }

         target.setNil();
      }
   }

   public void unsetDefaultSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTSQLFACTORY$340, 0);
      }
   }

   public KodoSqlFactoryType getKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoSqlFactoryType target = null;
         target = (KodoSqlFactoryType)this.get_store().find_element_user(KODOSQLFACTORY$342, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoSqlFactoryType target = null;
         target = (KodoSqlFactoryType)this.get_store().find_element_user(KODOSQLFACTORY$342, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(KODOSQLFACTORY$342) != 0;
      }
   }

   public void setKodoSqlFactory(KodoSqlFactoryType kodoSqlFactory) {
      this.generatedSetterHelperImpl(kodoSqlFactory, KODOSQLFACTORY$342, 0, (short)1);
   }

   public KodoSqlFactoryType addNewKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoSqlFactoryType target = null;
         target = (KodoSqlFactoryType)this.get_store().add_element_user(KODOSQLFACTORY$342);
         return target;
      }
   }

   public void setNilKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KodoSqlFactoryType target = null;
         target = (KodoSqlFactoryType)this.get_store().find_element_user(KODOSQLFACTORY$342, 0);
         if (target == null) {
            target = (KodoSqlFactoryType)this.get_store().add_element_user(KODOSQLFACTORY$342);
         }

         target.setNil();
      }
   }

   public void unsetKodoSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(KODOSQLFACTORY$342, 0);
      }
   }

   public CustomSqlFactoryType getCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSqlFactoryType target = null;
         target = (CustomSqlFactoryType)this.get_store().find_element_user(CUSTOMSQLFACTORY$344, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSqlFactoryType target = null;
         target = (CustomSqlFactoryType)this.get_store().find_element_user(CUSTOMSQLFACTORY$344, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMSQLFACTORY$344) != 0;
      }
   }

   public void setCustomSqlFactory(CustomSqlFactoryType customSqlFactory) {
      this.generatedSetterHelperImpl(customSqlFactory, CUSTOMSQLFACTORY$344, 0, (short)1);
   }

   public CustomSqlFactoryType addNewCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSqlFactoryType target = null;
         target = (CustomSqlFactoryType)this.get_store().add_element_user(CUSTOMSQLFACTORY$344);
         return target;
      }
   }

   public void setNilCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomSqlFactoryType target = null;
         target = (CustomSqlFactoryType)this.get_store().find_element_user(CUSTOMSQLFACTORY$344, 0);
         if (target == null) {
            target = (CustomSqlFactoryType)this.get_store().add_element_user(CUSTOMSQLFACTORY$344);
         }

         target.setNil();
      }
   }

   public void unsetCustomSqlFactory() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMSQLFACTORY$344, 0);
      }
   }

   public PersistenceUnitConfigurationType.SubclassFetchMode.Enum getSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.SubclassFetchMode.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.SubclassFetchMode xgetSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.SubclassFetchMode target = null;
         target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         return target;
      }
   }

   public boolean isNilSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.SubclassFetchMode target = null;
         target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUBCLASSFETCHMODE$346) != 0;
      }
   }

   public void setSubclassFetchMode(PersistenceUnitConfigurationType.SubclassFetchMode.Enum subclassFetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUBCLASSFETCHMODE$346);
         }

         target.setEnumValue(subclassFetchMode);
      }
   }

   public void xsetSubclassFetchMode(PersistenceUnitConfigurationType.SubclassFetchMode subclassFetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.SubclassFetchMode target = null;
         target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().add_element_user(SUBCLASSFETCHMODE$346);
         }

         target.set(subclassFetchMode);
      }
   }

   public void setNilSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.SubclassFetchMode target = null;
         target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().find_element_user(SUBCLASSFETCHMODE$346, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.SubclassFetchMode)this.get_store().add_element_user(SUBCLASSFETCHMODE$346);
         }

         target.setNil();
      }
   }

   public void unsetSubclassFetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUBCLASSFETCHMODE$346, 0);
      }
   }

   public String getSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         return target;
      }
   }

   public boolean isNilSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYNCHRONIZEMAPPINGS$348) != 0;
      }
   }

   public void setSynchronizeMappings(String synchronizeMappings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYNCHRONIZEMAPPINGS$348);
         }

         target.setStringValue(synchronizeMappings);
      }
   }

   public void xsetSynchronizeMappings(XmlString synchronizeMappings) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYNCHRONIZEMAPPINGS$348);
         }

         target.set(synchronizeMappings);
      }
   }

   public void setNilSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SYNCHRONIZEMAPPINGS$348, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SYNCHRONIZEMAPPINGS$348);
         }

         target.setNil();
      }
   }

   public void unsetSynchronizeMappings() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYNCHRONIZEMAPPINGS$348, 0);
      }
   }

   public PersistenceUnitConfigurationType.TransactionIsolation.Enum getTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.TransactionIsolation.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.TransactionIsolation xgetTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionIsolation target = null;
         target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         return target;
      }
   }

   public boolean isNilTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionIsolation target = null;
         target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONISOLATION$350) != 0;
      }
   }

   public void setTransactionIsolation(PersistenceUnitConfigurationType.TransactionIsolation.Enum transactionIsolation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONISOLATION$350);
         }

         target.setEnumValue(transactionIsolation);
      }
   }

   public void xsetTransactionIsolation(PersistenceUnitConfigurationType.TransactionIsolation transactionIsolation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionIsolation target = null;
         target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().add_element_user(TRANSACTIONISOLATION$350);
         }

         target.set(transactionIsolation);
      }
   }

   public void setNilTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionIsolation target = null;
         target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().find_element_user(TRANSACTIONISOLATION$350, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.TransactionIsolation)this.get_store().add_element_user(TRANSACTIONISOLATION$350);
         }

         target.setNil();
      }
   }

   public void unsetTransactionIsolation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONISOLATION$350, 0);
      }
   }

   public PersistenceUnitConfigurationType.TransactionMode.Enum getTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.TransactionMode.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.TransactionMode xgetTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionMode target = null;
         target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         return target;
      }
   }

   public boolean isNilTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionMode target = null;
         target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONMODE$352) != 0;
      }
   }

   public void setTransactionMode(PersistenceUnitConfigurationType.TransactionMode.Enum transactionMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSACTIONMODE$352);
         }

         target.setEnumValue(transactionMode);
      }
   }

   public void xsetTransactionMode(PersistenceUnitConfigurationType.TransactionMode transactionMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionMode target = null;
         target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().add_element_user(TRANSACTIONMODE$352);
         }

         target.set(transactionMode);
      }
   }

   public void setNilTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.TransactionMode target = null;
         target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().find_element_user(TRANSACTIONMODE$352, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.TransactionMode)this.get_store().add_element_user(TRANSACTIONMODE$352);
         }

         target.setNil();
      }
   }

   public void unsetTransactionMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONMODE$352, 0);
      }
   }

   public DefaultUpdateManagerType getDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultUpdateManagerType target = null;
         target = (DefaultUpdateManagerType)this.get_store().find_element_user(DEFAULTUPDATEMANAGER$354, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultUpdateManagerType target = null;
         target = (DefaultUpdateManagerType)this.get_store().find_element_user(DEFAULTUPDATEMANAGER$354, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTUPDATEMANAGER$354) != 0;
      }
   }

   public void setDefaultUpdateManager(DefaultUpdateManagerType defaultUpdateManager) {
      this.generatedSetterHelperImpl(defaultUpdateManager, DEFAULTUPDATEMANAGER$354, 0, (short)1);
   }

   public DefaultUpdateManagerType addNewDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultUpdateManagerType target = null;
         target = (DefaultUpdateManagerType)this.get_store().add_element_user(DEFAULTUPDATEMANAGER$354);
         return target;
      }
   }

   public void setNilDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultUpdateManagerType target = null;
         target = (DefaultUpdateManagerType)this.get_store().find_element_user(DEFAULTUPDATEMANAGER$354, 0);
         if (target == null) {
            target = (DefaultUpdateManagerType)this.get_store().add_element_user(DEFAULTUPDATEMANAGER$354);
         }

         target.setNil();
      }
   }

   public void unsetDefaultUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTUPDATEMANAGER$354, 0);
      }
   }

   public ConstraintUpdateManagerType getConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstraintUpdateManagerType target = null;
         target = (ConstraintUpdateManagerType)this.get_store().find_element_user(CONSTRAINTUPDATEMANAGER$356, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstraintUpdateManagerType target = null;
         target = (ConstraintUpdateManagerType)this.get_store().find_element_user(CONSTRAINTUPDATEMANAGER$356, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSTRAINTUPDATEMANAGER$356) != 0;
      }
   }

   public void setConstraintUpdateManager(ConstraintUpdateManagerType constraintUpdateManager) {
      this.generatedSetterHelperImpl(constraintUpdateManager, CONSTRAINTUPDATEMANAGER$356, 0, (short)1);
   }

   public ConstraintUpdateManagerType addNewConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstraintUpdateManagerType target = null;
         target = (ConstraintUpdateManagerType)this.get_store().add_element_user(CONSTRAINTUPDATEMANAGER$356);
         return target;
      }
   }

   public void setNilConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConstraintUpdateManagerType target = null;
         target = (ConstraintUpdateManagerType)this.get_store().find_element_user(CONSTRAINTUPDATEMANAGER$356, 0);
         if (target == null) {
            target = (ConstraintUpdateManagerType)this.get_store().add_element_user(CONSTRAINTUPDATEMANAGER$356);
         }

         target.setNil();
      }
   }

   public void unsetConstraintUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSTRAINTUPDATEMANAGER$356, 0);
      }
   }

   public BatchingOperationOrderUpdateManagerType getBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BatchingOperationOrderUpdateManagerType target = null;
         target = (BatchingOperationOrderUpdateManagerType)this.get_store().find_element_user(BATCHINGOPERATIONORDERUPDATEMANAGER$358, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BatchingOperationOrderUpdateManagerType target = null;
         target = (BatchingOperationOrderUpdateManagerType)this.get_store().find_element_user(BATCHINGOPERATIONORDERUPDATEMANAGER$358, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BATCHINGOPERATIONORDERUPDATEMANAGER$358) != 0;
      }
   }

   public void setBatchingOperationOrderUpdateManager(BatchingOperationOrderUpdateManagerType batchingOperationOrderUpdateManager) {
      this.generatedSetterHelperImpl(batchingOperationOrderUpdateManager, BATCHINGOPERATIONORDERUPDATEMANAGER$358, 0, (short)1);
   }

   public BatchingOperationOrderUpdateManagerType addNewBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BatchingOperationOrderUpdateManagerType target = null;
         target = (BatchingOperationOrderUpdateManagerType)this.get_store().add_element_user(BATCHINGOPERATIONORDERUPDATEMANAGER$358);
         return target;
      }
   }

   public void setNilBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BatchingOperationOrderUpdateManagerType target = null;
         target = (BatchingOperationOrderUpdateManagerType)this.get_store().find_element_user(BATCHINGOPERATIONORDERUPDATEMANAGER$358, 0);
         if (target == null) {
            target = (BatchingOperationOrderUpdateManagerType)this.get_store().add_element_user(BATCHINGOPERATIONORDERUPDATEMANAGER$358);
         }

         target.setNil();
      }
   }

   public void unsetBatchingOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BATCHINGOPERATIONORDERUPDATEMANAGER$358, 0);
      }
   }

   public OperationOrderUpdateManagerType getOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationOrderUpdateManagerType target = null;
         target = (OperationOrderUpdateManagerType)this.get_store().find_element_user(OPERATIONORDERUPDATEMANAGER$360, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationOrderUpdateManagerType target = null;
         target = (OperationOrderUpdateManagerType)this.get_store().find_element_user(OPERATIONORDERUPDATEMANAGER$360, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPERATIONORDERUPDATEMANAGER$360) != 0;
      }
   }

   public void setOperationOrderUpdateManager(OperationOrderUpdateManagerType operationOrderUpdateManager) {
      this.generatedSetterHelperImpl(operationOrderUpdateManager, OPERATIONORDERUPDATEMANAGER$360, 0, (short)1);
   }

   public OperationOrderUpdateManagerType addNewOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationOrderUpdateManagerType target = null;
         target = (OperationOrderUpdateManagerType)this.get_store().add_element_user(OPERATIONORDERUPDATEMANAGER$360);
         return target;
      }
   }

   public void setNilOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OperationOrderUpdateManagerType target = null;
         target = (OperationOrderUpdateManagerType)this.get_store().find_element_user(OPERATIONORDERUPDATEMANAGER$360, 0);
         if (target == null) {
            target = (OperationOrderUpdateManagerType)this.get_store().add_element_user(OPERATIONORDERUPDATEMANAGER$360);
         }

         target.setNil();
      }
   }

   public void unsetOperationOrderUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPERATIONORDERUPDATEMANAGER$360, 0);
      }
   }

   public TableLockUpdateManagerType getTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableLockUpdateManagerType target = null;
         target = (TableLockUpdateManagerType)this.get_store().find_element_user(TABLELOCKUPDATEMANAGER$362, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableLockUpdateManagerType target = null;
         target = (TableLockUpdateManagerType)this.get_store().find_element_user(TABLELOCKUPDATEMANAGER$362, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TABLELOCKUPDATEMANAGER$362) != 0;
      }
   }

   public void setTableLockUpdateManager(TableLockUpdateManagerType tableLockUpdateManager) {
      this.generatedSetterHelperImpl(tableLockUpdateManager, TABLELOCKUPDATEMANAGER$362, 0, (short)1);
   }

   public TableLockUpdateManagerType addNewTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableLockUpdateManagerType target = null;
         target = (TableLockUpdateManagerType)this.get_store().add_element_user(TABLELOCKUPDATEMANAGER$362);
         return target;
      }
   }

   public void setNilTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TableLockUpdateManagerType target = null;
         target = (TableLockUpdateManagerType)this.get_store().find_element_user(TABLELOCKUPDATEMANAGER$362, 0);
         if (target == null) {
            target = (TableLockUpdateManagerType)this.get_store().add_element_user(TABLELOCKUPDATEMANAGER$362);
         }

         target.setNil();
      }
   }

   public void unsetTableLockUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TABLELOCKUPDATEMANAGER$362, 0);
      }
   }

   public CustomUpdateManagerType getCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomUpdateManagerType target = null;
         target = (CustomUpdateManagerType)this.get_store().find_element_user(CUSTOMUPDATEMANAGER$364, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomUpdateManagerType target = null;
         target = (CustomUpdateManagerType)this.get_store().find_element_user(CUSTOMUPDATEMANAGER$364, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMUPDATEMANAGER$364) != 0;
      }
   }

   public void setCustomUpdateManager(CustomUpdateManagerType customUpdateManager) {
      this.generatedSetterHelperImpl(customUpdateManager, CUSTOMUPDATEMANAGER$364, 0, (short)1);
   }

   public CustomUpdateManagerType addNewCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomUpdateManagerType target = null;
         target = (CustomUpdateManagerType)this.get_store().add_element_user(CUSTOMUPDATEMANAGER$364);
         return target;
      }
   }

   public void setNilCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomUpdateManagerType target = null;
         target = (CustomUpdateManagerType)this.get_store().find_element_user(CUSTOMUPDATEMANAGER$364, 0);
         if (target == null) {
            target = (CustomUpdateManagerType)this.get_store().add_element_user(CUSTOMUPDATEMANAGER$364);
         }

         target.setNil();
      }
   }

   public void unsetCustomUpdateManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMUPDATEMANAGER$364, 0);
      }
   }

   public PersistenceUnitConfigurationType.WriteLockLevel.Enum getWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         return target == null ? null : (PersistenceUnitConfigurationType.WriteLockLevel.Enum)target.getEnumValue();
      }
   }

   public PersistenceUnitConfigurationType.WriteLockLevel xgetWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.WriteLockLevel target = null;
         target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         return target;
      }
   }

   public boolean isNilWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.WriteLockLevel target = null;
         target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WRITELOCKLEVEL$366) != 0;
      }
   }

   public void setWriteLockLevel(PersistenceUnitConfigurationType.WriteLockLevel.Enum writeLockLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(WRITELOCKLEVEL$366);
         }

         target.setEnumValue(writeLockLevel);
      }
   }

   public void xsetWriteLockLevel(PersistenceUnitConfigurationType.WriteLockLevel writeLockLevel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.WriteLockLevel target = null;
         target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().add_element_user(WRITELOCKLEVEL$366);
         }

         target.set(writeLockLevel);
      }
   }

   public void setNilWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistenceUnitConfigurationType.WriteLockLevel target = null;
         target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().find_element_user(WRITELOCKLEVEL$366, 0);
         if (target == null) {
            target = (PersistenceUnitConfigurationType.WriteLockLevel)this.get_store().add_element_user(WRITELOCKLEVEL$366);
         }

         target.setNil();
      }
   }

   public void unsetWriteLockLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WRITELOCKLEVEL$366, 0);
      }
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$368);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$368);
         return target;
      }
   }

   public boolean isSetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAME$368) != null;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAME$368);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAME$368);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NAME$368);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NAME$368);
         }

         target.set(name);
      }
   }

   public void unsetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAME$368);
      }
   }

   public static class WriteLockLevelImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.WriteLockLevel {
      private static final long serialVersionUID = 1L;

      public WriteLockLevelImpl(SchemaType sType) {
         super(sType, false);
      }

      protected WriteLockLevelImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class TransactionModeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.TransactionMode {
      private static final long serialVersionUID = 1L;

      public TransactionModeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected TransactionModeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class TransactionIsolationImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.TransactionIsolation {
      private static final long serialVersionUID = 1L;

      public TransactionIsolationImpl(SchemaType sType) {
         super(sType, false);
      }

      protected TransactionIsolationImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class SubclassFetchModeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.SubclassFetchMode {
      private static final long serialVersionUID = 1L;

      public SubclassFetchModeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SubclassFetchModeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class ResultSetTypeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.ResultSetType {
      private static final long serialVersionUID = 1L;

      public ResultSetTypeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ResultSetTypeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class RestoreStateImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.RestoreState {
      private static final long serialVersionUID = 1L;

      public RestoreStateImpl(SchemaType sType) {
         super(sType, false);
      }

      protected RestoreStateImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class ReadLockLevelImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.ReadLockLevel {
      private static final long serialVersionUID = 1L;

      public ReadLockLevelImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ReadLockLevelImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class LrsSizeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.LrsSize {
      private static final long serialVersionUID = 1L;

      public LrsSizeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected LrsSizeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class FlushBeforeQueriesImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.FlushBeforeQueries {
      private static final long serialVersionUID = 1L;

      public FlushBeforeQueriesImpl(SchemaType sType) {
         super(sType, false);
      }

      protected FlushBeforeQueriesImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class FetchDirectionImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.FetchDirection {
      private static final long serialVersionUID = 1L;

      public FetchDirectionImpl(SchemaType sType) {
         super(sType, false);
      }

      protected FetchDirectionImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class EagerFetchModeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.EagerFetchMode {
      private static final long serialVersionUID = 1L;

      public EagerFetchModeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected EagerFetchModeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class ConnectionRetainModeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.ConnectionRetainMode {
      private static final long serialVersionUID = 1L;

      public ConnectionRetainModeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ConnectionRetainModeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class ConnectionFactoryModeImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.ConnectionFactoryMode {
      private static final long serialVersionUID = 1L;

      public ConnectionFactoryModeImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ConnectionFactoryModeImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class AutoClearImpl extends JavaStringEnumerationHolderEx implements PersistenceUnitConfigurationType.AutoClear {
      private static final long serialVersionUID = 1L;

      public AutoClearImpl(SchemaType sType) {
         super(sType, false);
      }

      protected AutoClearImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
