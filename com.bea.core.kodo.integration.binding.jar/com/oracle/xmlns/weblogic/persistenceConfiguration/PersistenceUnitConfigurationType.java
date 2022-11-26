package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface PersistenceUnitConfigurationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistenceUnitConfigurationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("persistenceunitconfigurationtype5e3ftype");

   AggregateListenersType getAggregateListeners();

   boolean isNilAggregateListeners();

   boolean isSetAggregateListeners();

   void setAggregateListeners(AggregateListenersType var1);

   AggregateListenersType addNewAggregateListeners();

   void setNilAggregateListeners();

   void unsetAggregateListeners();

   AutoClear.Enum getAutoClear();

   AutoClear xgetAutoClear();

   boolean isNilAutoClear();

   boolean isSetAutoClear();

   void setAutoClear(AutoClear.Enum var1);

   void xsetAutoClear(AutoClear var1);

   void setNilAutoClear();

   void unsetAutoClear();

   AutoDetachType getAutoDetaches();

   boolean isNilAutoDetaches();

   boolean isSetAutoDetaches();

   void setAutoDetaches(AutoDetachType var1);

   AutoDetachType addNewAutoDetaches();

   void setNilAutoDetaches();

   void unsetAutoDetaches();

   DefaultBrokerFactoryType getDefaultBrokerFactory();

   boolean isNilDefaultBrokerFactory();

   boolean isSetDefaultBrokerFactory();

   void setDefaultBrokerFactory(DefaultBrokerFactoryType var1);

   DefaultBrokerFactoryType addNewDefaultBrokerFactory();

   void setNilDefaultBrokerFactory();

   void unsetDefaultBrokerFactory();

   AbstractStoreBrokerFactoryType getAbstractStoreBrokerFactory();

   boolean isNilAbstractStoreBrokerFactory();

   boolean isSetAbstractStoreBrokerFactory();

   void setAbstractStoreBrokerFactory(AbstractStoreBrokerFactoryType var1);

   AbstractStoreBrokerFactoryType addNewAbstractStoreBrokerFactory();

   void setNilAbstractStoreBrokerFactory();

   void unsetAbstractStoreBrokerFactory();

   ClientBrokerFactoryType getClientBrokerFactory();

   boolean isNilClientBrokerFactory();

   boolean isSetClientBrokerFactory();

   void setClientBrokerFactory(ClientBrokerFactoryType var1);

   ClientBrokerFactoryType addNewClientBrokerFactory();

   void setNilClientBrokerFactory();

   void unsetClientBrokerFactory();

   JdbcBrokerFactoryType getJdbcBrokerFactory();

   boolean isNilJdbcBrokerFactory();

   boolean isSetJdbcBrokerFactory();

   void setJdbcBrokerFactory(JdbcBrokerFactoryType var1);

   JdbcBrokerFactoryType addNewJdbcBrokerFactory();

   void setNilJdbcBrokerFactory();

   void unsetJdbcBrokerFactory();

   CustomBrokerFactoryType getCustomBrokerFactory();

   boolean isNilCustomBrokerFactory();

   boolean isSetCustomBrokerFactory();

   void setCustomBrokerFactory(CustomBrokerFactoryType var1);

   CustomBrokerFactoryType addNewCustomBrokerFactory();

   void setNilCustomBrokerFactory();

   void unsetCustomBrokerFactory();

   DefaultBrokerImplType getDefaultBrokerImpl();

   boolean isNilDefaultBrokerImpl();

   boolean isSetDefaultBrokerImpl();

   void setDefaultBrokerImpl(DefaultBrokerImplType var1);

   DefaultBrokerImplType addNewDefaultBrokerImpl();

   void setNilDefaultBrokerImpl();

   void unsetDefaultBrokerImpl();

   KodoBrokerType getKodoBroker();

   boolean isNilKodoBroker();

   boolean isSetKodoBroker();

   void setKodoBroker(KodoBrokerType var1);

   KodoBrokerType addNewKodoBroker();

   void setNilKodoBroker();

   void unsetKodoBroker();

   CustomBrokerImplType getCustomBrokerImpl();

   boolean isNilCustomBrokerImpl();

   boolean isSetCustomBrokerImpl();

   void setCustomBrokerImpl(CustomBrokerImplType var1);

   CustomBrokerImplType addNewCustomBrokerImpl();

   void setNilCustomBrokerImpl();

   void unsetCustomBrokerImpl();

   DefaultClassResolverType getDefaultClassResolver();

   boolean isNilDefaultClassResolver();

   boolean isSetDefaultClassResolver();

   void setDefaultClassResolver(DefaultClassResolverType var1);

   DefaultClassResolverType addNewDefaultClassResolver();

   void setNilDefaultClassResolver();

   void unsetDefaultClassResolver();

   CustomClassResolverType getCustomClassResolver();

   boolean isNilCustomClassResolver();

   boolean isSetCustomClassResolver();

   void setCustomClassResolver(CustomClassResolverType var1);

   CustomClassResolverType addNewCustomClassResolver();

   void setNilCustomClassResolver();

   void unsetCustomClassResolver();

   DefaultCompatibilityType getDefaultCompatibility();

   boolean isNilDefaultCompatibility();

   boolean isSetDefaultCompatibility();

   void setDefaultCompatibility(DefaultCompatibilityType var1);

   DefaultCompatibilityType addNewDefaultCompatibility();

   void setNilDefaultCompatibility();

   void unsetDefaultCompatibility();

   KodoCompatibilityType getCompatibility();

   boolean isNilCompatibility();

   boolean isSetCompatibility();

   void setCompatibility(KodoCompatibilityType var1);

   KodoCompatibilityType addNewCompatibility();

   void setNilCompatibility();

   void unsetCompatibility();

   CustomCompatibilityType getCustomCompatibility();

   boolean isNilCustomCompatibility();

   boolean isSetCustomCompatibility();

   void setCustomCompatibility(CustomCompatibilityType var1);

   CustomCompatibilityType addNewCustomCompatibility();

   void setNilCustomCompatibility();

   void unsetCustomCompatibility();

   String getConnection2DriverName();

   XmlString xgetConnection2DriverName();

   boolean isNilConnection2DriverName();

   boolean isSetConnection2DriverName();

   void setConnection2DriverName(String var1);

   void xsetConnection2DriverName(XmlString var1);

   void setNilConnection2DriverName();

   void unsetConnection2DriverName();

   String getConnection2Password();

   XmlString xgetConnection2Password();

   boolean isNilConnection2Password();

   boolean isSetConnection2Password();

   void setConnection2Password(String var1);

   void xsetConnection2Password(XmlString var1);

   void setNilConnection2Password();

   void unsetConnection2Password();

   PropertiesType getConnection2Properties();

   boolean isNilConnection2Properties();

   boolean isSetConnection2Properties();

   void setConnection2Properties(PropertiesType var1);

   PropertiesType addNewConnection2Properties();

   void setNilConnection2Properties();

   void unsetConnection2Properties();

   String getConnection2Url();

   XmlString xgetConnection2Url();

   boolean isNilConnection2Url();

   boolean isSetConnection2Url();

   void setConnection2Url(String var1);

   void xsetConnection2Url(XmlString var1);

   void setNilConnection2Url();

   void unsetConnection2Url();

   String getConnection2UserName();

   XmlString xgetConnection2UserName();

   boolean isNilConnection2UserName();

   boolean isSetConnection2UserName();

   void setConnection2UserName(String var1);

   void xsetConnection2UserName(XmlString var1);

   void setNilConnection2UserName();

   void unsetConnection2UserName();

   ConnectionDecoratorsType getConnectionDecorators();

   boolean isNilConnectionDecorators();

   boolean isSetConnectionDecorators();

   void setConnectionDecorators(ConnectionDecoratorsType var1);

   ConnectionDecoratorsType addNewConnectionDecorators();

   void setNilConnectionDecorators();

   void unsetConnectionDecorators();

   String getConnectionDriverName();

   XmlString xgetConnectionDriverName();

   boolean isNilConnectionDriverName();

   boolean isSetConnectionDriverName();

   void setConnectionDriverName(String var1);

   void xsetConnectionDriverName(XmlString var1);

   void setNilConnectionDriverName();

   void unsetConnectionDriverName();

   String getConnectionFactory2Name();

   XmlString xgetConnectionFactory2Name();

   boolean isNilConnectionFactory2Name();

   boolean isSetConnectionFactory2Name();

   void setConnectionFactory2Name(String var1);

   void xsetConnectionFactory2Name(XmlString var1);

   void setNilConnectionFactory2Name();

   void unsetConnectionFactory2Name();

   PropertiesType getConnectionFactory2Properties();

   boolean isNilConnectionFactory2Properties();

   boolean isSetConnectionFactory2Properties();

   void setConnectionFactory2Properties(PropertiesType var1);

   PropertiesType addNewConnectionFactory2Properties();

   void setNilConnectionFactory2Properties();

   void unsetConnectionFactory2Properties();

   ConnectionFactoryMode.Enum getConnectionFactoryMode();

   ConnectionFactoryMode xgetConnectionFactoryMode();

   boolean isNilConnectionFactoryMode();

   boolean isSetConnectionFactoryMode();

   void setConnectionFactoryMode(ConnectionFactoryMode.Enum var1);

   void xsetConnectionFactoryMode(ConnectionFactoryMode var1);

   void setNilConnectionFactoryMode();

   void unsetConnectionFactoryMode();

   String getConnectionFactoryName();

   XmlString xgetConnectionFactoryName();

   boolean isNilConnectionFactoryName();

   boolean isSetConnectionFactoryName();

   void setConnectionFactoryName(String var1);

   void xsetConnectionFactoryName(XmlString var1);

   void setNilConnectionFactoryName();

   void unsetConnectionFactoryName();

   PropertiesType getConnectionFactoryProperties();

   boolean isNilConnectionFactoryProperties();

   boolean isSetConnectionFactoryProperties();

   void setConnectionFactoryProperties(PropertiesType var1);

   PropertiesType addNewConnectionFactoryProperties();

   void setNilConnectionFactoryProperties();

   void unsetConnectionFactoryProperties();

   String getConnectionPassword();

   XmlString xgetConnectionPassword();

   boolean isNilConnectionPassword();

   boolean isSetConnectionPassword();

   void setConnectionPassword(String var1);

   void xsetConnectionPassword(XmlString var1);

   void setNilConnectionPassword();

   void unsetConnectionPassword();

   PropertiesType getConnectionProperties();

   boolean isNilConnectionProperties();

   boolean isSetConnectionProperties();

   void setConnectionProperties(PropertiesType var1);

   PropertiesType addNewConnectionProperties();

   void setNilConnectionProperties();

   void unsetConnectionProperties();

   ConnectionRetainMode.Enum getConnectionRetainMode();

   ConnectionRetainMode xgetConnectionRetainMode();

   boolean isNilConnectionRetainMode();

   boolean isSetConnectionRetainMode();

   void setConnectionRetainMode(ConnectionRetainMode.Enum var1);

   void xsetConnectionRetainMode(ConnectionRetainMode var1);

   void setNilConnectionRetainMode();

   void unsetConnectionRetainMode();

   String getConnectionUrl();

   XmlString xgetConnectionUrl();

   boolean isNilConnectionUrl();

   boolean isSetConnectionUrl();

   void setConnectionUrl(String var1);

   void xsetConnectionUrl(XmlString var1);

   void setNilConnectionUrl();

   void unsetConnectionUrl();

   String getConnectionUserName();

   XmlString xgetConnectionUserName();

   boolean isNilConnectionUserName();

   boolean isSetConnectionUserName();

   void setConnectionUserName(String var1);

   void xsetConnectionUserName(XmlString var1);

   void setNilConnectionUserName();

   void unsetConnectionUserName();

   DataCachesType getDataCaches();

   boolean isNilDataCaches();

   boolean isSetDataCaches();

   void setDataCaches(DataCachesType var1);

   DataCachesType addNewDataCaches();

   void setNilDataCaches();

   void unsetDataCaches();

   DefaultDataCacheManagerType getDefaultDataCacheManager();

   boolean isNilDefaultDataCacheManager();

   boolean isSetDefaultDataCacheManager();

   void setDefaultDataCacheManager(DefaultDataCacheManagerType var1);

   DefaultDataCacheManagerType addNewDefaultDataCacheManager();

   void setNilDefaultDataCacheManager();

   void unsetDefaultDataCacheManager();

   KodoDataCacheManagerType getKodoDataCacheManager();

   boolean isNilKodoDataCacheManager();

   boolean isSetKodoDataCacheManager();

   void setKodoDataCacheManager(KodoDataCacheManagerType var1);

   KodoDataCacheManagerType addNewKodoDataCacheManager();

   void setNilKodoDataCacheManager();

   void unsetKodoDataCacheManager();

   DataCacheManagerImplType getDataCacheManagerImpl();

   boolean isNilDataCacheManagerImpl();

   boolean isSetDataCacheManagerImpl();

   void setDataCacheManagerImpl(DataCacheManagerImplType var1);

   DataCacheManagerImplType addNewDataCacheManagerImpl();

   void setNilDataCacheManagerImpl();

   void unsetDataCacheManagerImpl();

   CustomDataCacheManagerType getCustomDataCacheManager();

   boolean isNilCustomDataCacheManager();

   boolean isSetCustomDataCacheManager();

   void setCustomDataCacheManager(CustomDataCacheManagerType var1);

   CustomDataCacheManagerType addNewCustomDataCacheManager();

   void setNilCustomDataCacheManager();

   void unsetCustomDataCacheManager();

   int getDataCacheTimeout();

   XmlInt xgetDataCacheTimeout();

   boolean isSetDataCacheTimeout();

   void setDataCacheTimeout(int var1);

   void xsetDataCacheTimeout(XmlInt var1);

   void unsetDataCacheTimeout();

   AccessDictionaryType getAccessDictionary();

   boolean isNilAccessDictionary();

   boolean isSetAccessDictionary();

   void setAccessDictionary(AccessDictionaryType var1);

   AccessDictionaryType addNewAccessDictionary();

   void setNilAccessDictionary();

   void unsetAccessDictionary();

   Db2DictionaryType getDb2Dictionary();

   boolean isNilDb2Dictionary();

   boolean isSetDb2Dictionary();

   void setDb2Dictionary(Db2DictionaryType var1);

   Db2DictionaryType addNewDb2Dictionary();

   void setNilDb2Dictionary();

   void unsetDb2Dictionary();

   DerbyDictionaryType getDerbyDictionary();

   boolean isNilDerbyDictionary();

   boolean isSetDerbyDictionary();

   void setDerbyDictionary(DerbyDictionaryType var1);

   DerbyDictionaryType addNewDerbyDictionary();

   void setNilDerbyDictionary();

   void unsetDerbyDictionary();

   EmpressDictionaryType getEmpressDictionary();

   boolean isNilEmpressDictionary();

   boolean isSetEmpressDictionary();

   void setEmpressDictionary(EmpressDictionaryType var1);

   EmpressDictionaryType addNewEmpressDictionary();

   void setNilEmpressDictionary();

   void unsetEmpressDictionary();

   FoxproDictionaryType getFoxproDictionary();

   boolean isNilFoxproDictionary();

   boolean isSetFoxproDictionary();

   void setFoxproDictionary(FoxproDictionaryType var1);

   FoxproDictionaryType addNewFoxproDictionary();

   void setNilFoxproDictionary();

   void unsetFoxproDictionary();

   HsqlDictionaryType getHsqlDictionary();

   boolean isNilHsqlDictionary();

   boolean isSetHsqlDictionary();

   void setHsqlDictionary(HsqlDictionaryType var1);

   HsqlDictionaryType addNewHsqlDictionary();

   void setNilHsqlDictionary();

   void unsetHsqlDictionary();

   InformixDictionaryType getInformixDictionary();

   boolean isNilInformixDictionary();

   boolean isSetInformixDictionary();

   void setInformixDictionary(InformixDictionaryType var1);

   InformixDictionaryType addNewInformixDictionary();

   void setNilInformixDictionary();

   void unsetInformixDictionary();

   JdatastoreDictionaryType getJdatastoreDictionary();

   boolean isNilJdatastoreDictionary();

   boolean isSetJdatastoreDictionary();

   void setJdatastoreDictionary(JdatastoreDictionaryType var1);

   JdatastoreDictionaryType addNewJdatastoreDictionary();

   void setNilJdatastoreDictionary();

   void unsetJdatastoreDictionary();

   MysqlDictionaryType getMysqlDictionary();

   boolean isNilMysqlDictionary();

   boolean isSetMysqlDictionary();

   void setMysqlDictionary(MysqlDictionaryType var1);

   MysqlDictionaryType addNewMysqlDictionary();

   void setNilMysqlDictionary();

   void unsetMysqlDictionary();

   OracleDictionaryType getOracleDictionary();

   boolean isNilOracleDictionary();

   boolean isSetOracleDictionary();

   void setOracleDictionary(OracleDictionaryType var1);

   OracleDictionaryType addNewOracleDictionary();

   void setNilOracleDictionary();

   void unsetOracleDictionary();

   PointbaseDictionaryType getPointbaseDictionary();

   boolean isNilPointbaseDictionary();

   boolean isSetPointbaseDictionary();

   void setPointbaseDictionary(PointbaseDictionaryType var1);

   PointbaseDictionaryType addNewPointbaseDictionary();

   void setNilPointbaseDictionary();

   void unsetPointbaseDictionary();

   PostgresDictionaryType getPostgresDictionary();

   boolean isNilPostgresDictionary();

   boolean isSetPostgresDictionary();

   void setPostgresDictionary(PostgresDictionaryType var1);

   PostgresDictionaryType addNewPostgresDictionary();

   void setNilPostgresDictionary();

   void unsetPostgresDictionary();

   SqlServerDictionaryType getSqlServerDictionary();

   boolean isNilSqlServerDictionary();

   boolean isSetSqlServerDictionary();

   void setSqlServerDictionary(SqlServerDictionaryType var1);

   SqlServerDictionaryType addNewSqlServerDictionary();

   void setNilSqlServerDictionary();

   void unsetSqlServerDictionary();

   SybaseDictionaryType getSybaseDictionary();

   boolean isNilSybaseDictionary();

   boolean isSetSybaseDictionary();

   void setSybaseDictionary(SybaseDictionaryType var1);

   SybaseDictionaryType addNewSybaseDictionary();

   void setNilSybaseDictionary();

   void unsetSybaseDictionary();

   CustomDictionaryType getCustomDictionary();

   boolean isNilCustomDictionary();

   boolean isSetCustomDictionary();

   void setCustomDictionary(CustomDictionaryType var1);

   CustomDictionaryType addNewCustomDictionary();

   void setNilCustomDictionary();

   void unsetCustomDictionary();

   DefaultDetachStateType getDefaultDetachState();

   boolean isNilDefaultDetachState();

   boolean isSetDefaultDetachState();

   void setDefaultDetachState(DefaultDetachStateType var1);

   DefaultDetachStateType addNewDefaultDetachState();

   void setNilDefaultDetachState();

   void unsetDefaultDetachState();

   DetachOptionsLoadedType getDetachOptionsLoaded();

   boolean isNilDetachOptionsLoaded();

   boolean isSetDetachOptionsLoaded();

   void setDetachOptionsLoaded(DetachOptionsLoadedType var1);

   DetachOptionsLoadedType addNewDetachOptionsLoaded();

   void setNilDetachOptionsLoaded();

   void unsetDetachOptionsLoaded();

   DetachOptionsFetchGroupsType getDetachOptionsFetchGroups();

   boolean isNilDetachOptionsFetchGroups();

   boolean isSetDetachOptionsFetchGroups();

   void setDetachOptionsFetchGroups(DetachOptionsFetchGroupsType var1);

   DetachOptionsFetchGroupsType addNewDetachOptionsFetchGroups();

   void setNilDetachOptionsFetchGroups();

   void unsetDetachOptionsFetchGroups();

   DetachOptionsAllType getDetachOptionsAll();

   boolean isNilDetachOptionsAll();

   boolean isSetDetachOptionsAll();

   void setDetachOptionsAll(DetachOptionsAllType var1);

   DetachOptionsAllType addNewDetachOptionsAll();

   void setNilDetachOptionsAll();

   void unsetDetachOptionsAll();

   CustomDetachStateType getCustomDetachState();

   boolean isNilCustomDetachState();

   boolean isSetCustomDetachState();

   void setCustomDetachState(CustomDetachStateType var1);

   CustomDetachStateType addNewCustomDetachState();

   void setNilCustomDetachState();

   void unsetCustomDetachState();

   DefaultDriverDataSourceType getDefaultDriverDataSource();

   boolean isNilDefaultDriverDataSource();

   boolean isSetDefaultDriverDataSource();

   void setDefaultDriverDataSource(DefaultDriverDataSourceType var1);

   DefaultDriverDataSourceType addNewDefaultDriverDataSource();

   void setNilDefaultDriverDataSource();

   void unsetDefaultDriverDataSource();

   KodoPoolingDataSourceType getKodoPoolingDataSource();

   boolean isNilKodoPoolingDataSource();

   boolean isSetKodoPoolingDataSource();

   void setKodoPoolingDataSource(KodoPoolingDataSourceType var1);

   KodoPoolingDataSourceType addNewKodoPoolingDataSource();

   void setNilKodoPoolingDataSource();

   void unsetKodoPoolingDataSource();

   SimpleDriverDataSourceType getSimpleDriverDataSource();

   boolean isNilSimpleDriverDataSource();

   boolean isSetSimpleDriverDataSource();

   void setSimpleDriverDataSource(SimpleDriverDataSourceType var1);

   SimpleDriverDataSourceType addNewSimpleDriverDataSource();

   void setNilSimpleDriverDataSource();

   void unsetSimpleDriverDataSource();

   CustomDriverDataSourceType getCustomDriverDataSource();

   boolean isNilCustomDriverDataSource();

   boolean isSetCustomDriverDataSource();

   void setCustomDriverDataSource(CustomDriverDataSourceType var1);

   CustomDriverDataSourceType addNewCustomDriverDataSource();

   void setNilCustomDriverDataSource();

   void unsetCustomDriverDataSource();

   StackExecutionContextNameProviderType getStackExecutionContextNameProvider();

   boolean isNilStackExecutionContextNameProvider();

   boolean isSetStackExecutionContextNameProvider();

   void setStackExecutionContextNameProvider(StackExecutionContextNameProviderType var1);

   StackExecutionContextNameProviderType addNewStackExecutionContextNameProvider();

   void setNilStackExecutionContextNameProvider();

   void unsetStackExecutionContextNameProvider();

   TransactionNameExecutionContextNameProviderType getTransactionNameExecutionContextNameProvider();

   boolean isNilTransactionNameExecutionContextNameProvider();

   boolean isSetTransactionNameExecutionContextNameProvider();

   void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderType var1);

   TransactionNameExecutionContextNameProviderType addNewTransactionNameExecutionContextNameProvider();

   void setNilTransactionNameExecutionContextNameProvider();

   void unsetTransactionNameExecutionContextNameProvider();

   UserObjectExecutionContextNameProviderType getUserObjectExecutionContextNameProvider();

   boolean isNilUserObjectExecutionContextNameProvider();

   boolean isSetUserObjectExecutionContextNameProvider();

   void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderType var1);

   UserObjectExecutionContextNameProviderType addNewUserObjectExecutionContextNameProvider();

   void setNilUserObjectExecutionContextNameProvider();

   void unsetUserObjectExecutionContextNameProvider();

   NoneProfilingType getNoneProfiling();

   boolean isNilNoneProfiling();

   boolean isSetNoneProfiling();

   void setNoneProfiling(NoneProfilingType var1);

   NoneProfilingType addNewNoneProfiling();

   void setNilNoneProfiling();

   void unsetNoneProfiling();

   LocalProfilingType getLocalProfiling();

   boolean isNilLocalProfiling();

   boolean isSetLocalProfiling();

   void setLocalProfiling(LocalProfilingType var1);

   LocalProfilingType addNewLocalProfiling();

   void setNilLocalProfiling();

   void unsetLocalProfiling();

   ExportProfilingType getExportProfiling();

   boolean isNilExportProfiling();

   boolean isSetExportProfiling();

   void setExportProfiling(ExportProfilingType var1);

   ExportProfilingType addNewExportProfiling();

   void setNilExportProfiling();

   void unsetExportProfiling();

   GuiProfilingType getGuiProfiling();

   boolean isNilGuiProfiling();

   boolean isSetGuiProfiling();

   void setGuiProfiling(GuiProfilingType var1);

   GuiProfilingType addNewGuiProfiling();

   void setNilGuiProfiling();

   void unsetGuiProfiling();

   NoneJmxType getNoneJmx();

   boolean isNilNoneJmx();

   boolean isSetNoneJmx();

   void setNoneJmx(NoneJmxType var1);

   NoneJmxType addNewNoneJmx();

   void setNilNoneJmx();

   void unsetNoneJmx();

   LocalJmxType getLocalJmx();

   boolean isNilLocalJmx();

   boolean isSetLocalJmx();

   void setLocalJmx(LocalJmxType var1);

   LocalJmxType addNewLocalJmx();

   void setNilLocalJmx();

   void unsetLocalJmx();

   GuiJmxType getGuiJmx();

   boolean isNilGuiJmx();

   boolean isSetGuiJmx();

   void setGuiJmx(GuiJmxType var1);

   GuiJmxType addNewGuiJmx();

   void setNilGuiJmx();

   void unsetGuiJmx();

   Jmx2JmxType getJmx2Jmx();

   boolean isNilJmx2Jmx();

   boolean isSetJmx2Jmx();

   void setJmx2Jmx(Jmx2JmxType var1);

   Jmx2JmxType addNewJmx2Jmx();

   void setNilJmx2Jmx();

   void unsetJmx2Jmx();

   Mx4J1JmxType getMx4J1Jmx();

   boolean isNilMx4J1Jmx();

   boolean isSetMx4J1Jmx();

   void setMx4J1Jmx(Mx4J1JmxType var1);

   Mx4J1JmxType addNewMx4J1Jmx();

   void setNilMx4J1Jmx();

   void unsetMx4J1Jmx();

   Wls81JmxType getWls81Jmx();

   boolean isNilWls81Jmx();

   boolean isSetWls81Jmx();

   void setWls81Jmx(Wls81JmxType var1);

   Wls81JmxType addNewWls81Jmx();

   void setNilWls81Jmx();

   void unsetWls81Jmx();

   boolean getDynamicDataStructs();

   XmlBoolean xgetDynamicDataStructs();

   boolean isSetDynamicDataStructs();

   void setDynamicDataStructs(boolean var1);

   void xsetDynamicDataStructs(XmlBoolean var1);

   void unsetDynamicDataStructs();

   EagerFetchMode.Enum getEagerFetchMode();

   EagerFetchMode xgetEagerFetchMode();

   boolean isNilEagerFetchMode();

   boolean isSetEagerFetchMode();

   void setEagerFetchMode(EagerFetchMode.Enum var1);

   void xsetEagerFetchMode(EagerFetchMode var1);

   void setNilEagerFetchMode();

   void unsetEagerFetchMode();

   int getFetchBatchSize();

   XmlInt xgetFetchBatchSize();

   boolean isSetFetchBatchSize();

   void setFetchBatchSize(int var1);

   void xsetFetchBatchSize(XmlInt var1);

   void unsetFetchBatchSize();

   FetchDirection.Enum getFetchDirection();

   FetchDirection xgetFetchDirection();

   boolean isNilFetchDirection();

   boolean isSetFetchDirection();

   void setFetchDirection(FetchDirection.Enum var1);

   void xsetFetchDirection(FetchDirection var1);

   void setNilFetchDirection();

   void unsetFetchDirection();

   FetchGroupsType getFetchGroups();

   boolean isNilFetchGroups();

   boolean isSetFetchGroups();

   void setFetchGroups(FetchGroupsType var1);

   FetchGroupsType addNewFetchGroups();

   void setNilFetchGroups();

   void unsetFetchGroups();

   FilterListenersType getFilterListeners();

   boolean isNilFilterListeners();

   boolean isSetFilterListeners();

   void setFilterListeners(FilterListenersType var1);

   FilterListenersType addNewFilterListeners();

   void setNilFilterListeners();

   void unsetFilterListeners();

   FlushBeforeQueries.Enum getFlushBeforeQueries();

   FlushBeforeQueries xgetFlushBeforeQueries();

   boolean isNilFlushBeforeQueries();

   boolean isSetFlushBeforeQueries();

   void setFlushBeforeQueries(FlushBeforeQueries.Enum var1);

   void xsetFlushBeforeQueries(FlushBeforeQueries var1);

   void setNilFlushBeforeQueries();

   void unsetFlushBeforeQueries();

   boolean getIgnoreChanges();

   XmlBoolean xgetIgnoreChanges();

   boolean isSetIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   void xsetIgnoreChanges(XmlBoolean var1);

   void unsetIgnoreChanges();

   InverseManagerType getInverseManager();

   boolean isNilInverseManager();

   boolean isSetInverseManager();

   void setInverseManager(InverseManagerType var1);

   InverseManagerType addNewInverseManager();

   void setNilInverseManager();

   void unsetInverseManager();

   JdbcListenersType getJdbcListeners();

   boolean isNilJdbcListeners();

   boolean isSetJdbcListeners();

   void setJdbcListeners(JdbcListenersType var1);

   JdbcListenersType addNewJdbcListeners();

   void setNilJdbcListeners();

   void unsetJdbcListeners();

   DefaultLockManagerType getDefaultLockManager();

   boolean isNilDefaultLockManager();

   boolean isSetDefaultLockManager();

   void setDefaultLockManager(DefaultLockManagerType var1);

   DefaultLockManagerType addNewDefaultLockManager();

   void setNilDefaultLockManager();

   void unsetDefaultLockManager();

   PessimisticLockManagerType getPessimisticLockManager();

   boolean isNilPessimisticLockManager();

   boolean isSetPessimisticLockManager();

   void setPessimisticLockManager(PessimisticLockManagerType var1);

   PessimisticLockManagerType addNewPessimisticLockManager();

   void setNilPessimisticLockManager();

   void unsetPessimisticLockManager();

   NoneLockManagerType getNoneLockManager();

   boolean isNilNoneLockManager();

   boolean isSetNoneLockManager();

   void setNoneLockManager(NoneLockManagerType var1);

   NoneLockManagerType addNewNoneLockManager();

   void setNilNoneLockManager();

   void unsetNoneLockManager();

   SingleJvmExclusiveLockManagerType getSingleJvmExclusiveLockManager();

   boolean isNilSingleJvmExclusiveLockManager();

   boolean isSetSingleJvmExclusiveLockManager();

   void setSingleJvmExclusiveLockManager(SingleJvmExclusiveLockManagerType var1);

   SingleJvmExclusiveLockManagerType addNewSingleJvmExclusiveLockManager();

   void setNilSingleJvmExclusiveLockManager();

   void unsetSingleJvmExclusiveLockManager();

   VersionLockManagerType getVersionLockManager();

   boolean isNilVersionLockManager();

   boolean isSetVersionLockManager();

   void setVersionLockManager(VersionLockManagerType var1);

   VersionLockManagerType addNewVersionLockManager();

   void setNilVersionLockManager();

   void unsetVersionLockManager();

   CustomLockManagerType getCustomLockManager();

   boolean isNilCustomLockManager();

   boolean isSetCustomLockManager();

   void setCustomLockManager(CustomLockManagerType var1);

   CustomLockManagerType addNewCustomLockManager();

   void setNilCustomLockManager();

   void unsetCustomLockManager();

   int getLockTimeout();

   XmlInt xgetLockTimeout();

   boolean isSetLockTimeout();

   void setLockTimeout(int var1);

   void xsetLockTimeout(XmlInt var1);

   void unsetLockTimeout();

   CommonsLogFactoryType getCommonsLogFactory();

   boolean isNilCommonsLogFactory();

   boolean isSetCommonsLogFactory();

   void setCommonsLogFactory(CommonsLogFactoryType var1);

   CommonsLogFactoryType addNewCommonsLogFactory();

   void setNilCommonsLogFactory();

   void unsetCommonsLogFactory();

   Log4JLogFactoryType getLog4JLogFactory();

   boolean isNilLog4JLogFactory();

   boolean isSetLog4JLogFactory();

   void setLog4JLogFactory(Log4JLogFactoryType var1);

   Log4JLogFactoryType addNewLog4JLogFactory();

   void setNilLog4JLogFactory();

   void unsetLog4JLogFactory();

   LogFactoryImplType getLogFactoryImpl();

   boolean isNilLogFactoryImpl();

   boolean isSetLogFactoryImpl();

   void setLogFactoryImpl(LogFactoryImplType var1);

   LogFactoryImplType addNewLogFactoryImpl();

   void setNilLogFactoryImpl();

   void unsetLogFactoryImpl();

   NoneLogFactoryType getNoneLogFactory();

   boolean isNilNoneLogFactory();

   boolean isSetNoneLogFactory();

   void setNoneLogFactory(NoneLogFactoryType var1);

   NoneLogFactoryType addNewNoneLogFactory();

   void setNilNoneLogFactory();

   void unsetNoneLogFactory();

   CustomLogType getCustomLog();

   boolean isNilCustomLog();

   boolean isSetCustomLog();

   void setCustomLog(CustomLogType var1);

   CustomLogType addNewCustomLog();

   void setNilCustomLog();

   void unsetCustomLog();

   LrsSize.Enum getLrsSize();

   LrsSize xgetLrsSize();

   boolean isNilLrsSize();

   boolean isSetLrsSize();

   void setLrsSize(LrsSize.Enum var1);

   void xsetLrsSize(LrsSize var1);

   void setNilLrsSize();

   void unsetLrsSize();

   String getMapping();

   XmlString xgetMapping();

   boolean isNilMapping();

   boolean isSetMapping();

   void setMapping(String var1);

   void xsetMapping(XmlString var1);

   void setNilMapping();

   void unsetMapping();

   DefaultMappingDefaultsType getDefaultMappingDefaults();

   boolean isNilDefaultMappingDefaults();

   boolean isSetDefaultMappingDefaults();

   void setDefaultMappingDefaults(DefaultMappingDefaultsType var1);

   DefaultMappingDefaultsType addNewDefaultMappingDefaults();

   void setNilDefaultMappingDefaults();

   void unsetDefaultMappingDefaults();

   DeprecatedJdoMappingDefaultsType getDeprecatedJdoMappingDefaults();

   boolean isNilDeprecatedJdoMappingDefaults();

   boolean isSetDeprecatedJdoMappingDefaults();

   void setDeprecatedJdoMappingDefaults(DeprecatedJdoMappingDefaultsType var1);

   DeprecatedJdoMappingDefaultsType addNewDeprecatedJdoMappingDefaults();

   void setNilDeprecatedJdoMappingDefaults();

   void unsetDeprecatedJdoMappingDefaults();

   MappingDefaultsImplType getMappingDefaultsImpl();

   boolean isNilMappingDefaultsImpl();

   boolean isSetMappingDefaultsImpl();

   void setMappingDefaultsImpl(MappingDefaultsImplType var1);

   MappingDefaultsImplType addNewMappingDefaultsImpl();

   void setNilMappingDefaultsImpl();

   void unsetMappingDefaultsImpl();

   PersistenceMappingDefaultsType getPersistenceMappingDefaults();

   boolean isNilPersistenceMappingDefaults();

   boolean isSetPersistenceMappingDefaults();

   void setPersistenceMappingDefaults(PersistenceMappingDefaultsType var1);

   PersistenceMappingDefaultsType addNewPersistenceMappingDefaults();

   void setNilPersistenceMappingDefaults();

   void unsetPersistenceMappingDefaults();

   CustomMappingDefaultsType getCustomMappingDefaults();

   boolean isNilCustomMappingDefaults();

   boolean isSetCustomMappingDefaults();

   void setCustomMappingDefaults(CustomMappingDefaultsType var1);

   CustomMappingDefaultsType addNewCustomMappingDefaults();

   void setNilCustomMappingDefaults();

   void unsetCustomMappingDefaults();

   ExtensionDeprecatedJdoMappingFactoryType getExtensionDeprecatedJdoMappingFactory();

   boolean isNilExtensionDeprecatedJdoMappingFactory();

   boolean isSetExtensionDeprecatedJdoMappingFactory();

   void setExtensionDeprecatedJdoMappingFactory(ExtensionDeprecatedJdoMappingFactoryType var1);

   ExtensionDeprecatedJdoMappingFactoryType addNewExtensionDeprecatedJdoMappingFactory();

   void setNilExtensionDeprecatedJdoMappingFactory();

   void unsetExtensionDeprecatedJdoMappingFactory();

   KodoPersistenceMappingFactoryType getKodoPersistenceMappingFactory();

   boolean isNilKodoPersistenceMappingFactory();

   boolean isSetKodoPersistenceMappingFactory();

   void setKodoPersistenceMappingFactory(KodoPersistenceMappingFactoryType var1);

   KodoPersistenceMappingFactoryType addNewKodoPersistenceMappingFactory();

   void setNilKodoPersistenceMappingFactory();

   void unsetKodoPersistenceMappingFactory();

   MappingFileDeprecatedJdoMappingFactoryType getMappingFileDeprecatedJdoMappingFactory();

   boolean isNilMappingFileDeprecatedJdoMappingFactory();

   boolean isSetMappingFileDeprecatedJdoMappingFactory();

   void setMappingFileDeprecatedJdoMappingFactory(MappingFileDeprecatedJdoMappingFactoryType var1);

   MappingFileDeprecatedJdoMappingFactoryType addNewMappingFileDeprecatedJdoMappingFactory();

   void setNilMappingFileDeprecatedJdoMappingFactory();

   void unsetMappingFileDeprecatedJdoMappingFactory();

   OrmFileJdorMappingFactoryType getOrmFileJdorMappingFactory();

   boolean isNilOrmFileJdorMappingFactory();

   boolean isSetOrmFileJdorMappingFactory();

   void setOrmFileJdorMappingFactory(OrmFileJdorMappingFactoryType var1);

   OrmFileJdorMappingFactoryType addNewOrmFileJdorMappingFactory();

   void setNilOrmFileJdorMappingFactory();

   void unsetOrmFileJdorMappingFactory();

   TableDeprecatedJdoMappingFactoryType getTableDeprecatedJdoMappingFactory();

   boolean isNilTableDeprecatedJdoMappingFactory();

   boolean isSetTableDeprecatedJdoMappingFactory();

   void setTableDeprecatedJdoMappingFactory(TableDeprecatedJdoMappingFactoryType var1);

   TableDeprecatedJdoMappingFactoryType addNewTableDeprecatedJdoMappingFactory();

   void setNilTableDeprecatedJdoMappingFactory();

   void unsetTableDeprecatedJdoMappingFactory();

   TableJdorMappingFactoryType getTableJdorMappingFactory();

   boolean isNilTableJdorMappingFactory();

   boolean isSetTableJdorMappingFactory();

   void setTableJdorMappingFactory(TableJdorMappingFactoryType var1);

   TableJdorMappingFactoryType addNewTableJdorMappingFactory();

   void setNilTableJdorMappingFactory();

   void unsetTableJdorMappingFactory();

   CustomMappingFactoryType getCustomMappingFactory();

   boolean isNilCustomMappingFactory();

   boolean isSetCustomMappingFactory();

   void setCustomMappingFactory(CustomMappingFactoryType var1);

   CustomMappingFactoryType addNewCustomMappingFactory();

   void setNilCustomMappingFactory();

   void unsetCustomMappingFactory();

   DefaultMetaDataFactoryType getDefaultMetaDataFactory();

   boolean isNilDefaultMetaDataFactory();

   boolean isSetDefaultMetaDataFactory();

   void setDefaultMetaDataFactory(DefaultMetaDataFactoryType var1);

   DefaultMetaDataFactoryType addNewDefaultMetaDataFactory();

   void setNilDefaultMetaDataFactory();

   void unsetDefaultMetaDataFactory();

   JdoMetaDataFactoryType getJdoMetaDataFactory();

   boolean isNilJdoMetaDataFactory();

   boolean isSetJdoMetaDataFactory();

   void setJdoMetaDataFactory(JdoMetaDataFactoryType var1);

   JdoMetaDataFactoryType addNewJdoMetaDataFactory();

   void setNilJdoMetaDataFactory();

   void unsetJdoMetaDataFactory();

   DeprecatedJdoMetaDataFactoryType getDeprecatedJdoMetaDataFactory();

   boolean isNilDeprecatedJdoMetaDataFactory();

   boolean isSetDeprecatedJdoMetaDataFactory();

   void setDeprecatedJdoMetaDataFactory(DeprecatedJdoMetaDataFactoryType var1);

   DeprecatedJdoMetaDataFactoryType addNewDeprecatedJdoMetaDataFactory();

   void setNilDeprecatedJdoMetaDataFactory();

   void unsetDeprecatedJdoMetaDataFactory();

   KodoPersistenceMetaDataFactoryType getKodoPersistenceMetaDataFactory();

   boolean isNilKodoPersistenceMetaDataFactory();

   boolean isSetKodoPersistenceMetaDataFactory();

   void setKodoPersistenceMetaDataFactory(KodoPersistenceMetaDataFactoryType var1);

   KodoPersistenceMetaDataFactoryType addNewKodoPersistenceMetaDataFactory();

   void setNilKodoPersistenceMetaDataFactory();

   void unsetKodoPersistenceMetaDataFactory();

   CustomMetaDataFactoryType getCustomMetaDataFactory();

   boolean isNilCustomMetaDataFactory();

   boolean isSetCustomMetaDataFactory();

   void setCustomMetaDataFactory(CustomMetaDataFactoryType var1);

   CustomMetaDataFactoryType addNewCustomMetaDataFactory();

   void setNilCustomMetaDataFactory();

   void unsetCustomMetaDataFactory();

   DefaultMetaDataRepositoryType getDefaultMetaDataRepository();

   boolean isNilDefaultMetaDataRepository();

   boolean isSetDefaultMetaDataRepository();

   void setDefaultMetaDataRepository(DefaultMetaDataRepositoryType var1);

   DefaultMetaDataRepositoryType addNewDefaultMetaDataRepository();

   void setNilDefaultMetaDataRepository();

   void unsetDefaultMetaDataRepository();

   KodoMappingRepositoryType getKodoMappingRepository();

   boolean isNilKodoMappingRepository();

   boolean isSetKodoMappingRepository();

   void setKodoMappingRepository(KodoMappingRepositoryType var1);

   KodoMappingRepositoryType addNewKodoMappingRepository();

   void setNilKodoMappingRepository();

   void unsetKodoMappingRepository();

   CustomMetaDataRepositoryType getCustomMetaDataRepository();

   boolean isNilCustomMetaDataRepository();

   boolean isSetCustomMetaDataRepository();

   void setCustomMetaDataRepository(CustomMetaDataRepositoryType var1);

   CustomMetaDataRepositoryType addNewCustomMetaDataRepository();

   void setNilCustomMetaDataRepository();

   void unsetCustomMetaDataRepository();

   boolean getMultithreaded();

   XmlBoolean xgetMultithreaded();

   boolean isSetMultithreaded();

   void setMultithreaded(boolean var1);

   void xsetMultithreaded(XmlBoolean var1);

   void unsetMultithreaded();

   boolean getNontransactionalRead();

   XmlBoolean xgetNontransactionalRead();

   boolean isSetNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   void xsetNontransactionalRead(XmlBoolean var1);

   void unsetNontransactionalRead();

   boolean getNontransactionalWrite();

   XmlBoolean xgetNontransactionalWrite();

   boolean isSetNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   void xsetNontransactionalWrite(XmlBoolean var1);

   void unsetNontransactionalWrite();

   boolean getOptimistic();

   XmlBoolean xgetOptimistic();

   boolean isSetOptimistic();

   void setOptimistic(boolean var1);

   void xsetOptimistic(XmlBoolean var1);

   void unsetOptimistic();

   DefaultOrphanedKeyActionType getDefaultOrphanedKeyAction();

   boolean isNilDefaultOrphanedKeyAction();

   boolean isSetDefaultOrphanedKeyAction();

   void setDefaultOrphanedKeyAction(DefaultOrphanedKeyActionType var1);

   DefaultOrphanedKeyActionType addNewDefaultOrphanedKeyAction();

   void setNilDefaultOrphanedKeyAction();

   void unsetDefaultOrphanedKeyAction();

   LogOrphanedKeyActionType getLogOrphanedKeyAction();

   boolean isNilLogOrphanedKeyAction();

   boolean isSetLogOrphanedKeyAction();

   void setLogOrphanedKeyAction(LogOrphanedKeyActionType var1);

   LogOrphanedKeyActionType addNewLogOrphanedKeyAction();

   void setNilLogOrphanedKeyAction();

   void unsetLogOrphanedKeyAction();

   ExceptionOrphanedKeyActionType getExceptionOrphanedKeyAction();

   boolean isNilExceptionOrphanedKeyAction();

   boolean isSetExceptionOrphanedKeyAction();

   void setExceptionOrphanedKeyAction(ExceptionOrphanedKeyActionType var1);

   ExceptionOrphanedKeyActionType addNewExceptionOrphanedKeyAction();

   void setNilExceptionOrphanedKeyAction();

   void unsetExceptionOrphanedKeyAction();

   NoneOrphanedKeyActionType getNoneOrphanedKeyAction();

   boolean isNilNoneOrphanedKeyAction();

   boolean isSetNoneOrphanedKeyAction();

   void setNoneOrphanedKeyAction(NoneOrphanedKeyActionType var1);

   NoneOrphanedKeyActionType addNewNoneOrphanedKeyAction();

   void setNilNoneOrphanedKeyAction();

   void unsetNoneOrphanedKeyAction();

   CustomOrphanedKeyActionType getCustomOrphanedKeyAction();

   boolean isNilCustomOrphanedKeyAction();

   boolean isSetCustomOrphanedKeyAction();

   void setCustomOrphanedKeyAction(CustomOrphanedKeyActionType var1);

   CustomOrphanedKeyActionType addNewCustomOrphanedKeyAction();

   void setNilCustomOrphanedKeyAction();

   void unsetCustomOrphanedKeyAction();

   HttpTransportType getHttpTransport();

   boolean isNilHttpTransport();

   boolean isSetHttpTransport();

   void setHttpTransport(HttpTransportType var1);

   HttpTransportType addNewHttpTransport();

   void setNilHttpTransport();

   void unsetHttpTransport();

   TcpTransportType getTcpTransport();

   boolean isNilTcpTransport();

   boolean isSetTcpTransport();

   void setTcpTransport(TcpTransportType var1);

   TcpTransportType addNewTcpTransport();

   void setNilTcpTransport();

   void unsetTcpTransport();

   CustomPersistenceServerType getCustomPersistenceServer();

   boolean isNilCustomPersistenceServer();

   boolean isSetCustomPersistenceServer();

   void setCustomPersistenceServer(CustomPersistenceServerType var1);

   CustomPersistenceServerType addNewCustomPersistenceServer();

   void setNilCustomPersistenceServer();

   void unsetCustomPersistenceServer();

   DefaultProxyManagerType getDefaultProxyManager();

   boolean isNilDefaultProxyManager();

   boolean isSetDefaultProxyManager();

   void setDefaultProxyManager(DefaultProxyManagerType var1);

   DefaultProxyManagerType addNewDefaultProxyManager();

   void setNilDefaultProxyManager();

   void unsetDefaultProxyManager();

   ProfilingProxyManagerType getProfilingProxyManager();

   boolean isNilProfilingProxyManager();

   boolean isSetProfilingProxyManager();

   void setProfilingProxyManager(ProfilingProxyManagerType var1);

   ProfilingProxyManagerType addNewProfilingProxyManager();

   void setNilProfilingProxyManager();

   void unsetProfilingProxyManager();

   ProxyManagerImplType getProxyManagerImpl();

   boolean isNilProxyManagerImpl();

   boolean isSetProxyManagerImpl();

   void setProxyManagerImpl(ProxyManagerImplType var1);

   ProxyManagerImplType addNewProxyManagerImpl();

   void setNilProxyManagerImpl();

   void unsetProxyManagerImpl();

   CustomProxyManagerType getCustomProxyManager();

   boolean isNilCustomProxyManager();

   boolean isSetCustomProxyManager();

   void setCustomProxyManager(CustomProxyManagerType var1);

   CustomProxyManagerType addNewCustomProxyManager();

   void setNilCustomProxyManager();

   void unsetCustomProxyManager();

   QueryCachesType getQueryCaches();

   boolean isNilQueryCaches();

   boolean isSetQueryCaches();

   void setQueryCaches(QueryCachesType var1);

   QueryCachesType addNewQueryCaches();

   void setNilQueryCaches();

   void unsetQueryCaches();

   DefaultQueryCompilationCacheType getDefaultQueryCompilationCache();

   boolean isNilDefaultQueryCompilationCache();

   boolean isSetDefaultQueryCompilationCache();

   void setDefaultQueryCompilationCache(DefaultQueryCompilationCacheType var1);

   DefaultQueryCompilationCacheType addNewDefaultQueryCompilationCache();

   void setNilDefaultQueryCompilationCache();

   void unsetDefaultQueryCompilationCache();

   CacheMapType getCacheMap();

   boolean isNilCacheMap();

   boolean isSetCacheMap();

   void setCacheMap(CacheMapType var1);

   CacheMapType addNewCacheMap();

   void setNilCacheMap();

   void unsetCacheMap();

   ConcurrentHashMapType getConcurrentHashMap();

   boolean isNilConcurrentHashMap();

   boolean isSetConcurrentHashMap();

   void setConcurrentHashMap(ConcurrentHashMapType var1);

   ConcurrentHashMapType addNewConcurrentHashMap();

   void setNilConcurrentHashMap();

   void unsetConcurrentHashMap();

   CustomQueryCompilationCacheType getCustomQueryCompilationCache();

   boolean isNilCustomQueryCompilationCache();

   boolean isSetCustomQueryCompilationCache();

   void setCustomQueryCompilationCache(CustomQueryCompilationCacheType var1);

   CustomQueryCompilationCacheType addNewCustomQueryCompilationCache();

   void setNilCustomQueryCompilationCache();

   void unsetCustomQueryCompilationCache();

   ReadLockLevel.Enum getReadLockLevel();

   ReadLockLevel xgetReadLockLevel();

   boolean isNilReadLockLevel();

   boolean isSetReadLockLevel();

   void setReadLockLevel(ReadLockLevel.Enum var1);

   void xsetReadLockLevel(ReadLockLevel var1);

   void setNilReadLockLevel();

   void unsetReadLockLevel();

   JmsRemoteCommitProviderType getJmsRemoteCommitProvider();

   boolean isNilJmsRemoteCommitProvider();

   boolean isSetJmsRemoteCommitProvider();

   void setJmsRemoteCommitProvider(JmsRemoteCommitProviderType var1);

   JmsRemoteCommitProviderType addNewJmsRemoteCommitProvider();

   void setNilJmsRemoteCommitProvider();

   void unsetJmsRemoteCommitProvider();

   SingleJvmRemoteCommitProviderType getSingleJvmRemoteCommitProvider();

   boolean isNilSingleJvmRemoteCommitProvider();

   boolean isSetSingleJvmRemoteCommitProvider();

   void setSingleJvmRemoteCommitProvider(SingleJvmRemoteCommitProviderType var1);

   SingleJvmRemoteCommitProviderType addNewSingleJvmRemoteCommitProvider();

   void setNilSingleJvmRemoteCommitProvider();

   void unsetSingleJvmRemoteCommitProvider();

   TcpRemoteCommitProviderType getTcpRemoteCommitProvider();

   boolean isNilTcpRemoteCommitProvider();

   boolean isSetTcpRemoteCommitProvider();

   void setTcpRemoteCommitProvider(TcpRemoteCommitProviderType var1);

   TcpRemoteCommitProviderType addNewTcpRemoteCommitProvider();

   void setNilTcpRemoteCommitProvider();

   void unsetTcpRemoteCommitProvider();

   ClusterRemoteCommitProviderType getClusterRemoteCommitProvider();

   boolean isNilClusterRemoteCommitProvider();

   boolean isSetClusterRemoteCommitProvider();

   void setClusterRemoteCommitProvider(ClusterRemoteCommitProviderType var1);

   ClusterRemoteCommitProviderType addNewClusterRemoteCommitProvider();

   void setNilClusterRemoteCommitProvider();

   void unsetClusterRemoteCommitProvider();

   CustomRemoteCommitProviderType getCustomRemoteCommitProvider();

   boolean isNilCustomRemoteCommitProvider();

   boolean isSetCustomRemoteCommitProvider();

   void setCustomRemoteCommitProvider(CustomRemoteCommitProviderType var1);

   CustomRemoteCommitProviderType addNewCustomRemoteCommitProvider();

   void setNilCustomRemoteCommitProvider();

   void unsetCustomRemoteCommitProvider();

   RestoreState.Enum getRestoreState();

   RestoreState xgetRestoreState();

   boolean isNilRestoreState();

   boolean isSetRestoreState();

   void setRestoreState(RestoreState.Enum var1);

   void xsetRestoreState(RestoreState var1);

   void setNilRestoreState();

   void unsetRestoreState();

   ResultSetType.Enum getResultSetType();

   ResultSetType xgetResultSetType();

   boolean isNilResultSetType();

   boolean isSetResultSetType();

   void setResultSetType(ResultSetType.Enum var1);

   void xsetResultSetType(ResultSetType var1);

   void setNilResultSetType();

   void unsetResultSetType();

   boolean getRetainState();

   XmlBoolean xgetRetainState();

   boolean isSetRetainState();

   void setRetainState(boolean var1);

   void xsetRetainState(XmlBoolean var1);

   void unsetRetainState();

   boolean getRetryClassRegistration();

   XmlBoolean xgetRetryClassRegistration();

   boolean isSetRetryClassRegistration();

   void setRetryClassRegistration(boolean var1);

   void xsetRetryClassRegistration(XmlBoolean var1);

   void unsetRetryClassRegistration();

   DefaultSavepointManagerType getDefaultSavepointManager();

   boolean isNilDefaultSavepointManager();

   boolean isSetDefaultSavepointManager();

   void setDefaultSavepointManager(DefaultSavepointManagerType var1);

   DefaultSavepointManagerType addNewDefaultSavepointManager();

   void setNilDefaultSavepointManager();

   void unsetDefaultSavepointManager();

   InMemorySavepointManagerType getInMemorySavepointManager();

   boolean isNilInMemorySavepointManager();

   boolean isSetInMemorySavepointManager();

   void setInMemorySavepointManager(InMemorySavepointManagerType var1);

   InMemorySavepointManagerType addNewInMemorySavepointManager();

   void setNilInMemorySavepointManager();

   void unsetInMemorySavepointManager();

   Jdbc3SavepointManagerType getJdbc3SavepointManager();

   boolean isNilJdbc3SavepointManager();

   boolean isSetJdbc3SavepointManager();

   void setJdbc3SavepointManager(Jdbc3SavepointManagerType var1);

   Jdbc3SavepointManagerType addNewJdbc3SavepointManager();

   void setNilJdbc3SavepointManager();

   void unsetJdbc3SavepointManager();

   OracleSavepointManagerType getOracleSavepointManager();

   boolean isNilOracleSavepointManager();

   boolean isSetOracleSavepointManager();

   void setOracleSavepointManager(OracleSavepointManagerType var1);

   OracleSavepointManagerType addNewOracleSavepointManager();

   void setNilOracleSavepointManager();

   void unsetOracleSavepointManager();

   CustomSavepointManagerType getCustomSavepointManager();

   boolean isNilCustomSavepointManager();

   boolean isSetCustomSavepointManager();

   void setCustomSavepointManager(CustomSavepointManagerType var1);

   CustomSavepointManagerType addNewCustomSavepointManager();

   void setNilCustomSavepointManager();

   void unsetCustomSavepointManager();

   String getSchema();

   XmlString xgetSchema();

   boolean isNilSchema();

   boolean isSetSchema();

   void setSchema(String var1);

   void xsetSchema(XmlString var1);

   void setNilSchema();

   void unsetSchema();

   DefaultSchemaFactoryType getDefaultSchemaFactory();

   boolean isNilDefaultSchemaFactory();

   boolean isSetDefaultSchemaFactory();

   void setDefaultSchemaFactory(DefaultSchemaFactoryType var1);

   DefaultSchemaFactoryType addNewDefaultSchemaFactory();

   void setNilDefaultSchemaFactory();

   void unsetDefaultSchemaFactory();

   DynamicSchemaFactoryType getDynamicSchemaFactory();

   boolean isNilDynamicSchemaFactory();

   boolean isSetDynamicSchemaFactory();

   void setDynamicSchemaFactory(DynamicSchemaFactoryType var1);

   DynamicSchemaFactoryType addNewDynamicSchemaFactory();

   void setNilDynamicSchemaFactory();

   void unsetDynamicSchemaFactory();

   FileSchemaFactoryType getFileSchemaFactory();

   boolean isNilFileSchemaFactory();

   boolean isSetFileSchemaFactory();

   void setFileSchemaFactory(FileSchemaFactoryType var1);

   FileSchemaFactoryType addNewFileSchemaFactory();

   void setNilFileSchemaFactory();

   void unsetFileSchemaFactory();

   LazySchemaFactoryType getLazySchemaFactory();

   boolean isNilLazySchemaFactory();

   boolean isSetLazySchemaFactory();

   void setLazySchemaFactory(LazySchemaFactoryType var1);

   LazySchemaFactoryType addNewLazySchemaFactory();

   void setNilLazySchemaFactory();

   void unsetLazySchemaFactory();

   TableSchemaFactoryType getTableSchemaFactory();

   boolean isNilTableSchemaFactory();

   boolean isSetTableSchemaFactory();

   void setTableSchemaFactory(TableSchemaFactoryType var1);

   TableSchemaFactoryType addNewTableSchemaFactory();

   void setNilTableSchemaFactory();

   void unsetTableSchemaFactory();

   CustomSchemaFactoryType getCustomSchemaFactory();

   boolean isNilCustomSchemaFactory();

   boolean isSetCustomSchemaFactory();

   void setCustomSchemaFactory(CustomSchemaFactoryType var1);

   CustomSchemaFactoryType addNewCustomSchemaFactory();

   void setNilCustomSchemaFactory();

   void unsetCustomSchemaFactory();

   SchemasType getSchemas();

   boolean isNilSchemas();

   boolean isSetSchemas();

   void setSchemas(SchemasType var1);

   SchemasType addNewSchemas();

   void setNilSchemas();

   void unsetSchemas();

   ClassTableJdbcSeqType getClassTableJdbcSeq();

   boolean isNilClassTableJdbcSeq();

   boolean isSetClassTableJdbcSeq();

   void setClassTableJdbcSeq(ClassTableJdbcSeqType var1);

   ClassTableJdbcSeqType addNewClassTableJdbcSeq();

   void setNilClassTableJdbcSeq();

   void unsetClassTableJdbcSeq();

   NativeJdbcSeqType getNativeJdbcSeq();

   boolean isNilNativeJdbcSeq();

   boolean isSetNativeJdbcSeq();

   void setNativeJdbcSeq(NativeJdbcSeqType var1);

   NativeJdbcSeqType addNewNativeJdbcSeq();

   void setNilNativeJdbcSeq();

   void unsetNativeJdbcSeq();

   TableJdbcSeqType getTableJdbcSeq();

   boolean isNilTableJdbcSeq();

   boolean isSetTableJdbcSeq();

   void setTableJdbcSeq(TableJdbcSeqType var1);

   TableJdbcSeqType addNewTableJdbcSeq();

   void setNilTableJdbcSeq();

   void unsetTableJdbcSeq();

   TimeSeededSeqType getTimeSeededSeq();

   boolean isNilTimeSeededSeq();

   boolean isSetTimeSeededSeq();

   void setTimeSeededSeq(TimeSeededSeqType var1);

   TimeSeededSeqType addNewTimeSeededSeq();

   void setNilTimeSeededSeq();

   void unsetTimeSeededSeq();

   ValueTableJdbcSeqType getValueTableJdbcSeq();

   boolean isNilValueTableJdbcSeq();

   boolean isSetValueTableJdbcSeq();

   void setValueTableJdbcSeq(ValueTableJdbcSeqType var1);

   ValueTableJdbcSeqType addNewValueTableJdbcSeq();

   void setNilValueTableJdbcSeq();

   void unsetValueTableJdbcSeq();

   CustomSeqType getCustomSeq();

   boolean isNilCustomSeq();

   boolean isSetCustomSeq();

   void setCustomSeq(CustomSeqType var1);

   CustomSeqType addNewCustomSeq();

   void setNilCustomSeq();

   void unsetCustomSeq();

   DefaultSqlFactoryType getDefaultSqlFactory();

   boolean isNilDefaultSqlFactory();

   boolean isSetDefaultSqlFactory();

   void setDefaultSqlFactory(DefaultSqlFactoryType var1);

   DefaultSqlFactoryType addNewDefaultSqlFactory();

   void setNilDefaultSqlFactory();

   void unsetDefaultSqlFactory();

   KodoSqlFactoryType getKodoSqlFactory();

   boolean isNilKodoSqlFactory();

   boolean isSetKodoSqlFactory();

   void setKodoSqlFactory(KodoSqlFactoryType var1);

   KodoSqlFactoryType addNewKodoSqlFactory();

   void setNilKodoSqlFactory();

   void unsetKodoSqlFactory();

   CustomSqlFactoryType getCustomSqlFactory();

   boolean isNilCustomSqlFactory();

   boolean isSetCustomSqlFactory();

   void setCustomSqlFactory(CustomSqlFactoryType var1);

   CustomSqlFactoryType addNewCustomSqlFactory();

   void setNilCustomSqlFactory();

   void unsetCustomSqlFactory();

   SubclassFetchMode.Enum getSubclassFetchMode();

   SubclassFetchMode xgetSubclassFetchMode();

   boolean isNilSubclassFetchMode();

   boolean isSetSubclassFetchMode();

   void setSubclassFetchMode(SubclassFetchMode.Enum var1);

   void xsetSubclassFetchMode(SubclassFetchMode var1);

   void setNilSubclassFetchMode();

   void unsetSubclassFetchMode();

   String getSynchronizeMappings();

   XmlString xgetSynchronizeMappings();

   boolean isNilSynchronizeMappings();

   boolean isSetSynchronizeMappings();

   void setSynchronizeMappings(String var1);

   void xsetSynchronizeMappings(XmlString var1);

   void setNilSynchronizeMappings();

   void unsetSynchronizeMappings();

   TransactionIsolation.Enum getTransactionIsolation();

   TransactionIsolation xgetTransactionIsolation();

   boolean isNilTransactionIsolation();

   boolean isSetTransactionIsolation();

   void setTransactionIsolation(TransactionIsolation.Enum var1);

   void xsetTransactionIsolation(TransactionIsolation var1);

   void setNilTransactionIsolation();

   void unsetTransactionIsolation();

   TransactionMode.Enum getTransactionMode();

   TransactionMode xgetTransactionMode();

   boolean isNilTransactionMode();

   boolean isSetTransactionMode();

   void setTransactionMode(TransactionMode.Enum var1);

   void xsetTransactionMode(TransactionMode var1);

   void setNilTransactionMode();

   void unsetTransactionMode();

   DefaultUpdateManagerType getDefaultUpdateManager();

   boolean isNilDefaultUpdateManager();

   boolean isSetDefaultUpdateManager();

   void setDefaultUpdateManager(DefaultUpdateManagerType var1);

   DefaultUpdateManagerType addNewDefaultUpdateManager();

   void setNilDefaultUpdateManager();

   void unsetDefaultUpdateManager();

   ConstraintUpdateManagerType getConstraintUpdateManager();

   boolean isNilConstraintUpdateManager();

   boolean isSetConstraintUpdateManager();

   void setConstraintUpdateManager(ConstraintUpdateManagerType var1);

   ConstraintUpdateManagerType addNewConstraintUpdateManager();

   void setNilConstraintUpdateManager();

   void unsetConstraintUpdateManager();

   BatchingOperationOrderUpdateManagerType getBatchingOperationOrderUpdateManager();

   boolean isNilBatchingOperationOrderUpdateManager();

   boolean isSetBatchingOperationOrderUpdateManager();

   void setBatchingOperationOrderUpdateManager(BatchingOperationOrderUpdateManagerType var1);

   BatchingOperationOrderUpdateManagerType addNewBatchingOperationOrderUpdateManager();

   void setNilBatchingOperationOrderUpdateManager();

   void unsetBatchingOperationOrderUpdateManager();

   OperationOrderUpdateManagerType getOperationOrderUpdateManager();

   boolean isNilOperationOrderUpdateManager();

   boolean isSetOperationOrderUpdateManager();

   void setOperationOrderUpdateManager(OperationOrderUpdateManagerType var1);

   OperationOrderUpdateManagerType addNewOperationOrderUpdateManager();

   void setNilOperationOrderUpdateManager();

   void unsetOperationOrderUpdateManager();

   TableLockUpdateManagerType getTableLockUpdateManager();

   boolean isNilTableLockUpdateManager();

   boolean isSetTableLockUpdateManager();

   void setTableLockUpdateManager(TableLockUpdateManagerType var1);

   TableLockUpdateManagerType addNewTableLockUpdateManager();

   void setNilTableLockUpdateManager();

   void unsetTableLockUpdateManager();

   CustomUpdateManagerType getCustomUpdateManager();

   boolean isNilCustomUpdateManager();

   boolean isSetCustomUpdateManager();

   void setCustomUpdateManager(CustomUpdateManagerType var1);

   CustomUpdateManagerType addNewCustomUpdateManager();

   void setNilCustomUpdateManager();

   void unsetCustomUpdateManager();

   WriteLockLevel.Enum getWriteLockLevel();

   WriteLockLevel xgetWriteLockLevel();

   boolean isNilWriteLockLevel();

   boolean isSetWriteLockLevel();

   void setWriteLockLevel(WriteLockLevel.Enum var1);

   void xsetWriteLockLevel(WriteLockLevel var1);

   void setNilWriteLockLevel();

   void unsetWriteLockLevel();

   String getName();

   XmlString xgetName();

   boolean isSetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   void unsetName();

   public static final class Factory {
      public static PersistenceUnitConfigurationType newInstance() {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType newInstance(XmlOptions options) {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(String xmlAsString) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(File file) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(file, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(URL u) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(u, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(InputStream is) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(is, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(Reader r) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(r, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(XMLStreamReader sr) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(sr, PersistenceUnitConfigurationType.type, options);
      }

      public static PersistenceUnitConfigurationType parse(Node node) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      public static PersistenceUnitConfigurationType parse(Node node, XmlOptions options) throws XmlException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(node, PersistenceUnitConfigurationType.type, options);
      }

      /** @deprecated */
      public static PersistenceUnitConfigurationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PersistenceUnitConfigurationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PersistenceUnitConfigurationType)XmlBeans.getContextTypeLoader().parse(xis, PersistenceUnitConfigurationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitConfigurationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PersistenceUnitConfigurationType.type, options);
      }

      private Factory() {
      }
   }

   public interface WriteLockLevel extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WriteLockLevel.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("writelocklevel91ebelemtype");
      Enum NONE = PersistenceUnitConfigurationType.WriteLockLevel.Enum.forString("none");
      Enum READ = PersistenceUnitConfigurationType.WriteLockLevel.Enum.forString("read");
      Enum WRITE = PersistenceUnitConfigurationType.WriteLockLevel.Enum.forString("write");
      int INT_NONE = 1;
      int INT_READ = 2;
      int INT_WRITE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static WriteLockLevel newValue(Object obj) {
            return (WriteLockLevel)PersistenceUnitConfigurationType.WriteLockLevel.type.newValue(obj);
         }

         public static WriteLockLevel newInstance() {
            return (WriteLockLevel)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.WriteLockLevel.type, (XmlOptions)null);
         }

         public static WriteLockLevel newInstance(XmlOptions options) {
            return (WriteLockLevel)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.WriteLockLevel.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_NONE = 1;
         static final int INT_READ = 2;
         static final int INT_WRITE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("none", 1), new Enum("read", 2), new Enum("write", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface TransactionMode extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionMode.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("transactionmodeb20delemtype");
      Enum LOCAL = PersistenceUnitConfigurationType.TransactionMode.Enum.forString("local");
      Enum MANAGED = PersistenceUnitConfigurationType.TransactionMode.Enum.forString("managed");
      int INT_LOCAL = 1;
      int INT_MANAGED = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static TransactionMode newValue(Object obj) {
            return (TransactionMode)PersistenceUnitConfigurationType.TransactionMode.type.newValue(obj);
         }

         public static TransactionMode newInstance() {
            return (TransactionMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.TransactionMode.type, (XmlOptions)null);
         }

         public static TransactionMode newInstance(XmlOptions options) {
            return (TransactionMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.TransactionMode.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_LOCAL = 1;
         static final int INT_MANAGED = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("local", 1), new Enum("managed", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface TransactionIsolation extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransactionIsolation.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("transactionisolation974aelemtype");
      Enum DEFAULT = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("default");
      Enum NONE = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("none");
      Enum READ_COMMITTED = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("read-committed");
      Enum READ_UNCOMMITTED = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("read-uncommitted");
      Enum REPEATABLE_READ = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("repeatable-read");
      Enum SERIALIZABLE = PersistenceUnitConfigurationType.TransactionIsolation.Enum.forString("serializable");
      int INT_DEFAULT = 1;
      int INT_NONE = 2;
      int INT_READ_COMMITTED = 3;
      int INT_READ_UNCOMMITTED = 4;
      int INT_REPEATABLE_READ = 5;
      int INT_SERIALIZABLE = 6;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static TransactionIsolation newValue(Object obj) {
            return (TransactionIsolation)PersistenceUnitConfigurationType.TransactionIsolation.type.newValue(obj);
         }

         public static TransactionIsolation newInstance() {
            return (TransactionIsolation)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.TransactionIsolation.type, (XmlOptions)null);
         }

         public static TransactionIsolation newInstance(XmlOptions options) {
            return (TransactionIsolation)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.TransactionIsolation.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_DEFAULT = 1;
         static final int INT_NONE = 2;
         static final int INT_READ_COMMITTED = 3;
         static final int INT_READ_UNCOMMITTED = 4;
         static final int INT_REPEATABLE_READ = 5;
         static final int INT_SERIALIZABLE = 6;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("default", 1), new Enum("none", 2), new Enum("read-committed", 3), new Enum("read-uncommitted", 4), new Enum("repeatable-read", 5), new Enum("serializable", 6)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface SubclassFetchMode extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SubclassFetchMode.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("subclassfetchmode2936elemtype");
      Enum JOIN = PersistenceUnitConfigurationType.SubclassFetchMode.Enum.forString("join");
      Enum MULTIPLE = PersistenceUnitConfigurationType.SubclassFetchMode.Enum.forString("multiple");
      Enum NONE = PersistenceUnitConfigurationType.SubclassFetchMode.Enum.forString("none");
      Enum PARALLEL = PersistenceUnitConfigurationType.SubclassFetchMode.Enum.forString("parallel");
      Enum SINGLE = PersistenceUnitConfigurationType.SubclassFetchMode.Enum.forString("single");
      int INT_JOIN = 1;
      int INT_MULTIPLE = 2;
      int INT_NONE = 3;
      int INT_PARALLEL = 4;
      int INT_SINGLE = 5;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static SubclassFetchMode newValue(Object obj) {
            return (SubclassFetchMode)PersistenceUnitConfigurationType.SubclassFetchMode.type.newValue(obj);
         }

         public static SubclassFetchMode newInstance() {
            return (SubclassFetchMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.SubclassFetchMode.type, (XmlOptions)null);
         }

         public static SubclassFetchMode newInstance(XmlOptions options) {
            return (SubclassFetchMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.SubclassFetchMode.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_JOIN = 1;
         static final int INT_MULTIPLE = 2;
         static final int INT_NONE = 3;
         static final int INT_PARALLEL = 4;
         static final int INT_SINGLE = 5;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("join", 1), new Enum("multiple", 2), new Enum("none", 3), new Enum("parallel", 4), new Enum("single", 5)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface ResultSetType extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResultSetType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("resultsettypeb420elemtype");
      Enum FORWARD_ONLY = PersistenceUnitConfigurationType.ResultSetType.Enum.forString("forward-only");
      Enum SCROLL_INSENSITIVE = PersistenceUnitConfigurationType.ResultSetType.Enum.forString("scroll-insensitive");
      Enum SCROLL_SENSITIVE = PersistenceUnitConfigurationType.ResultSetType.Enum.forString("scroll-sensitive");
      int INT_FORWARD_ONLY = 1;
      int INT_SCROLL_INSENSITIVE = 2;
      int INT_SCROLL_SENSITIVE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ResultSetType newValue(Object obj) {
            return (ResultSetType)PersistenceUnitConfigurationType.ResultSetType.type.newValue(obj);
         }

         public static ResultSetType newInstance() {
            return (ResultSetType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ResultSetType.type, (XmlOptions)null);
         }

         public static ResultSetType newInstance(XmlOptions options) {
            return (ResultSetType)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ResultSetType.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_FORWARD_ONLY = 1;
         static final int INT_SCROLL_INSENSITIVE = 2;
         static final int INT_SCROLL_SENSITIVE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("forward-only", 1), new Enum("scroll-insensitive", 2), new Enum("scroll-sensitive", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface RestoreState extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RestoreState.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("restorestatead3delemtype");
      Enum ALL = PersistenceUnitConfigurationType.RestoreState.Enum.forString("all");
      Enum FALSE = PersistenceUnitConfigurationType.RestoreState.Enum.forString("false");
      Enum IMMUTABLE = PersistenceUnitConfigurationType.RestoreState.Enum.forString("immutable");
      Enum NONE = PersistenceUnitConfigurationType.RestoreState.Enum.forString("none");
      Enum TRUE = PersistenceUnitConfigurationType.RestoreState.Enum.forString("true");
      int INT_ALL = 1;
      int INT_FALSE = 2;
      int INT_IMMUTABLE = 3;
      int INT_NONE = 4;
      int INT_TRUE = 5;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static RestoreState newValue(Object obj) {
            return (RestoreState)PersistenceUnitConfigurationType.RestoreState.type.newValue(obj);
         }

         public static RestoreState newInstance() {
            return (RestoreState)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.RestoreState.type, (XmlOptions)null);
         }

         public static RestoreState newInstance(XmlOptions options) {
            return (RestoreState)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.RestoreState.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALL = 1;
         static final int INT_FALSE = 2;
         static final int INT_IMMUTABLE = 3;
         static final int INT_NONE = 4;
         static final int INT_TRUE = 5;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("all", 1), new Enum("false", 2), new Enum("immutable", 3), new Enum("none", 4), new Enum("true", 5)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface ReadLockLevel extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReadLockLevel.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("readlocklevel97a4elemtype");
      Enum NONE = PersistenceUnitConfigurationType.ReadLockLevel.Enum.forString("none");
      Enum READ = PersistenceUnitConfigurationType.ReadLockLevel.Enum.forString("read");
      Enum WRITE = PersistenceUnitConfigurationType.ReadLockLevel.Enum.forString("write");
      int INT_NONE = 1;
      int INT_READ = 2;
      int INT_WRITE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ReadLockLevel newValue(Object obj) {
            return (ReadLockLevel)PersistenceUnitConfigurationType.ReadLockLevel.type.newValue(obj);
         }

         public static ReadLockLevel newInstance() {
            return (ReadLockLevel)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ReadLockLevel.type, (XmlOptions)null);
         }

         public static ReadLockLevel newInstance(XmlOptions options) {
            return (ReadLockLevel)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ReadLockLevel.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_NONE = 1;
         static final int INT_READ = 2;
         static final int INT_WRITE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("none", 1), new Enum("read", 2), new Enum("write", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface LrsSize extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LrsSize.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("lrssize54fcelemtype");
      Enum LAST = PersistenceUnitConfigurationType.LrsSize.Enum.forString("last");
      Enum QUERY = PersistenceUnitConfigurationType.LrsSize.Enum.forString("query");
      Enum UNKNOWN = PersistenceUnitConfigurationType.LrsSize.Enum.forString("unknown");
      int INT_LAST = 1;
      int INT_QUERY = 2;
      int INT_UNKNOWN = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static LrsSize newValue(Object obj) {
            return (LrsSize)PersistenceUnitConfigurationType.LrsSize.type.newValue(obj);
         }

         public static LrsSize newInstance() {
            return (LrsSize)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.LrsSize.type, (XmlOptions)null);
         }

         public static LrsSize newInstance(XmlOptions options) {
            return (LrsSize)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.LrsSize.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_LAST = 1;
         static final int INT_QUERY = 2;
         static final int INT_UNKNOWN = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("last", 1), new Enum("query", 2), new Enum("unknown", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface FlushBeforeQueries extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FlushBeforeQueries.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("flushbeforequeriesa3dcelemtype");
      Enum FALSE = PersistenceUnitConfigurationType.FlushBeforeQueries.Enum.forString("false");
      Enum TRUE = PersistenceUnitConfigurationType.FlushBeforeQueries.Enum.forString("true");
      Enum WITH_CONNECTION = PersistenceUnitConfigurationType.FlushBeforeQueries.Enum.forString("with-connection");
      int INT_FALSE = 1;
      int INT_TRUE = 2;
      int INT_WITH_CONNECTION = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static FlushBeforeQueries newValue(Object obj) {
            return (FlushBeforeQueries)PersistenceUnitConfigurationType.FlushBeforeQueries.type.newValue(obj);
         }

         public static FlushBeforeQueries newInstance() {
            return (FlushBeforeQueries)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.FlushBeforeQueries.type, (XmlOptions)null);
         }

         public static FlushBeforeQueries newInstance(XmlOptions options) {
            return (FlushBeforeQueries)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.FlushBeforeQueries.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_FALSE = 1;
         static final int INT_TRUE = 2;
         static final int INT_WITH_CONNECTION = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("false", 1), new Enum("true", 2), new Enum("with-connection", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface FetchDirection extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FetchDirection.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("fetchdirection42d7elemtype");
      Enum FORWARD = PersistenceUnitConfigurationType.FetchDirection.Enum.forString("forward");
      Enum REVERSE = PersistenceUnitConfigurationType.FetchDirection.Enum.forString("reverse");
      Enum UNKNOWN = PersistenceUnitConfigurationType.FetchDirection.Enum.forString("unknown");
      int INT_FORWARD = 1;
      int INT_REVERSE = 2;
      int INT_UNKNOWN = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static FetchDirection newValue(Object obj) {
            return (FetchDirection)PersistenceUnitConfigurationType.FetchDirection.type.newValue(obj);
         }

         public static FetchDirection newInstance() {
            return (FetchDirection)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.FetchDirection.type, (XmlOptions)null);
         }

         public static FetchDirection newInstance(XmlOptions options) {
            return (FetchDirection)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.FetchDirection.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_FORWARD = 1;
         static final int INT_REVERSE = 2;
         static final int INT_UNKNOWN = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("forward", 1), new Enum("reverse", 2), new Enum("unknown", 3)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface EagerFetchMode extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EagerFetchMode.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("eagerfetchmode2766elemtype");
      Enum JOIN = PersistenceUnitConfigurationType.EagerFetchMode.Enum.forString("join");
      Enum MULTIPLE = PersistenceUnitConfigurationType.EagerFetchMode.Enum.forString("multiple");
      Enum NONE = PersistenceUnitConfigurationType.EagerFetchMode.Enum.forString("none");
      Enum PARALLEL = PersistenceUnitConfigurationType.EagerFetchMode.Enum.forString("parallel");
      Enum SINGLE = PersistenceUnitConfigurationType.EagerFetchMode.Enum.forString("single");
      int INT_JOIN = 1;
      int INT_MULTIPLE = 2;
      int INT_NONE = 3;
      int INT_PARALLEL = 4;
      int INT_SINGLE = 5;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static EagerFetchMode newValue(Object obj) {
            return (EagerFetchMode)PersistenceUnitConfigurationType.EagerFetchMode.type.newValue(obj);
         }

         public static EagerFetchMode newInstance() {
            return (EagerFetchMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.EagerFetchMode.type, (XmlOptions)null);
         }

         public static EagerFetchMode newInstance(XmlOptions options) {
            return (EagerFetchMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.EagerFetchMode.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_JOIN = 1;
         static final int INT_MULTIPLE = 2;
         static final int INT_NONE = 3;
         static final int INT_PARALLEL = 4;
         static final int INT_SINGLE = 5;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("join", 1), new Enum("multiple", 2), new Enum("none", 3), new Enum("parallel", 4), new Enum("single", 5)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface ConnectionRetainMode extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionRetainMode.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("connectionretainmodebdd7elemtype");
      Enum ALWAYS = PersistenceUnitConfigurationType.ConnectionRetainMode.Enum.forString("always");
      Enum ON_DEMAND = PersistenceUnitConfigurationType.ConnectionRetainMode.Enum.forString("on-demand");
      Enum PERSISTENCE_MANAGER = PersistenceUnitConfigurationType.ConnectionRetainMode.Enum.forString("persistence-manager");
      Enum TRANSACTION = PersistenceUnitConfigurationType.ConnectionRetainMode.Enum.forString("transaction");
      int INT_ALWAYS = 1;
      int INT_ON_DEMAND = 2;
      int INT_PERSISTENCE_MANAGER = 3;
      int INT_TRANSACTION = 4;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ConnectionRetainMode newValue(Object obj) {
            return (ConnectionRetainMode)PersistenceUnitConfigurationType.ConnectionRetainMode.type.newValue(obj);
         }

         public static ConnectionRetainMode newInstance() {
            return (ConnectionRetainMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ConnectionRetainMode.type, (XmlOptions)null);
         }

         public static ConnectionRetainMode newInstance(XmlOptions options) {
            return (ConnectionRetainMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ConnectionRetainMode.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALWAYS = 1;
         static final int INT_ON_DEMAND = 2;
         static final int INT_PERSISTENCE_MANAGER = 3;
         static final int INT_TRANSACTION = 4;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("always", 1), new Enum("on-demand", 2), new Enum("persistence-manager", 3), new Enum("transaction", 4)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface ConnectionFactoryMode extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConnectionFactoryMode.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("connectionfactorymodeddc0elemtype");
      Enum LOCAL = PersistenceUnitConfigurationType.ConnectionFactoryMode.Enum.forString("local");
      Enum MANAGED = PersistenceUnitConfigurationType.ConnectionFactoryMode.Enum.forString("managed");
      int INT_LOCAL = 1;
      int INT_MANAGED = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ConnectionFactoryMode newValue(Object obj) {
            return (ConnectionFactoryMode)PersistenceUnitConfigurationType.ConnectionFactoryMode.type.newValue(obj);
         }

         public static ConnectionFactoryMode newInstance() {
            return (ConnectionFactoryMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ConnectionFactoryMode.type, (XmlOptions)null);
         }

         public static ConnectionFactoryMode newInstance(XmlOptions options) {
            return (ConnectionFactoryMode)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.ConnectionFactoryMode.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_LOCAL = 1;
         static final int INT_MANAGED = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("local", 1), new Enum("managed", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }

   public interface AutoClear extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AutoClear.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("autoclear2d8aelemtype");
      Enum ALL = PersistenceUnitConfigurationType.AutoClear.Enum.forString("all");
      Enum DATASTORE = PersistenceUnitConfigurationType.AutoClear.Enum.forString("datastore");
      int INT_ALL = 1;
      int INT_DATASTORE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static AutoClear newValue(Object obj) {
            return (AutoClear)PersistenceUnitConfigurationType.AutoClear.type.newValue(obj);
         }

         public static AutoClear newInstance() {
            return (AutoClear)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.AutoClear.type, (XmlOptions)null);
         }

         public static AutoClear newInstance(XmlOptions options) {
            return (AutoClear)XmlBeans.getContextTypeLoader().newInstance(PersistenceUnitConfigurationType.AutoClear.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALL = 1;
         static final int INT_DATASTORE = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("all", 1), new Enum("datastore", 2)});
         private static final long serialVersionUID = 1L;

         public static Enum forString(String s) {
            return (Enum)table.forString(s);
         }

         public static Enum forInt(int i) {
            return (Enum)table.forInt(i);
         }

         private Enum(String s, int i) {
            super(s, i);
         }

         private Object readResolve() {
            return forInt(this.intValue());
         }
      }
   }
}
