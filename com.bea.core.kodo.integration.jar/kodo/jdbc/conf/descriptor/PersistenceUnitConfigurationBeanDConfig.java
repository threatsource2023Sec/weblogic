package kodo.jdbc.conf.descriptor;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
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
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.spi.config.BasicDConfigBean;
import weblogic.descriptor.DescriptorBean;

public class PersistenceUnitConfigurationBeanDConfig extends BasicDConfigBean {
   private static final boolean debug = Debug.isDebug("config");
   private PersistenceUnitConfigurationBean beanTreeNode;

   public PersistenceUnitConfigurationBeanDConfig(DDBean ddb, DescriptorBean btn, DConfigBean parent) throws ConfigurationException {
      super(ddb);
      this.beanTreeNode = (PersistenceUnitConfigurationBean)btn;
      this.beanTree = btn;
      this.parent = (BasicDConfigBean)parent;
      this.initXpaths();
      this.customInit();
   }

   private void initXpaths() throws ConfigurationException {
      List xlist = new ArrayList();
      this.xpaths = (String[])((String[])xlist.toArray(new String[0]));
   }

   private void customInit() throws ConfigurationException {
   }

   public DConfigBean createDConfigBean(DDBean ddb, DConfigBean parent) throws ConfigurationException {
      return null;
   }

   public String keyPropertyValue() {
      return this.getName();
   }

   public void initKeyPropertyValue(String value) {
      this.setName(value);
   }

   public String getDCBProperties() {
      StringBuffer sb = new StringBuffer();
      sb.append("Name: ");
      sb.append(this.beanTreeNode.getName());
      sb.append("\n");
      return sb.toString();
   }

   public void validate() throws ConfigurationException {
   }

   public String getName() {
      return this.beanTreeNode.getName();
   }

   public void setName(String value) {
      this.beanTreeNode.setName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AggregateListenersBean getAggregateListeners() {
      return this.beanTreeNode.getAggregateListeners();
   }

   public String getAutoClear() {
      return this.beanTreeNode.getAutoClear();
   }

   public void setAutoClear(String value) {
      this.beanTreeNode.setAutoClear(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "AutoClear", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AutoDetachBean getAutoDetaches() {
      return this.beanTreeNode.getAutoDetaches();
   }

   public DefaultBrokerFactoryBean getDefaultBrokerFactory() {
      return this.beanTreeNode.getDefaultBrokerFactory();
   }

   public AbstractStoreBrokerFactoryBean getAbstractStoreBrokerFactory() {
      return this.beanTreeNode.getAbstractStoreBrokerFactory();
   }

   public ClientBrokerFactoryBean getClientBrokerFactory() {
      return this.beanTreeNode.getClientBrokerFactory();
   }

   public JDBCBrokerFactoryBean getJDBCBrokerFactory() {
      return this.beanTreeNode.getJDBCBrokerFactory();
   }

   public CustomBrokerFactoryBean getCustomBrokerFactory() {
      return this.beanTreeNode.getCustomBrokerFactory();
   }

   public DefaultBrokerImplBean getDefaultBrokerImpl() {
      return this.beanTreeNode.getDefaultBrokerImpl();
   }

   public KodoBrokerBean getKodoBroker() {
      return this.beanTreeNode.getKodoBroker();
   }

   public CustomBrokerImplBean getCustomBrokerImpl() {
      return this.beanTreeNode.getCustomBrokerImpl();
   }

   public DefaultClassResolverBean getDefaultClassResolver() {
      return this.beanTreeNode.getDefaultClassResolver();
   }

   public CustomClassResolverBean getCustomClassResolver() {
      return this.beanTreeNode.getCustomClassResolver();
   }

   public DefaultCompatibilityBean getDefaultCompatibility() {
      return this.beanTreeNode.getDefaultCompatibility();
   }

   public KodoCompatibilityBean getCompatibility() {
      return this.beanTreeNode.getCompatibility();
   }

   public CustomCompatibilityBean getCustomCompatibility() {
      return this.beanTreeNode.getCustomCompatibility();
   }

   public String getConnection2DriverName() {
      return this.beanTreeNode.getConnection2DriverName();
   }

   public void setConnection2DriverName(String value) {
      this.beanTreeNode.setConnection2DriverName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Connection2DriverName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnection2Password() {
      return this.beanTreeNode.getConnection2Password();
   }

   public void setConnection2Password(String value) {
      this.beanTreeNode.setConnection2Password(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Connection2Password", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getConnection2PasswordEncrypted() {
      return this.beanTreeNode.getConnection2PasswordEncrypted();
   }

   public void setConnection2PasswordEncrypted(byte[] value) {
      this.beanTreeNode.setConnection2PasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Connection2PasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertiesBean getConnection2Properties() {
      return this.beanTreeNode.getConnection2Properties();
   }

   public String getConnection2URL() {
      return this.beanTreeNode.getConnection2URL();
   }

   public void setConnection2URL(String value) {
      this.beanTreeNode.setConnection2URL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Connection2URL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnection2UserName() {
      return this.beanTreeNode.getConnection2UserName();
   }

   public void setConnection2UserName(String value) {
      this.beanTreeNode.setConnection2UserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Connection2UserName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public ConnectionDecoratorsBean getConnectionDecorators() {
      return this.beanTreeNode.getConnectionDecorators();
   }

   public String getConnectionDriverName() {
      return this.beanTreeNode.getConnectionDriverName();
   }

   public void setConnectionDriverName(String value) {
      this.beanTreeNode.setConnectionDriverName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionDriverName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionFactory2Name() {
      return this.beanTreeNode.getConnectionFactory2Name();
   }

   public void setConnectionFactory2Name(String value) {
      this.beanTreeNode.setConnectionFactory2Name(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactory2Name", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertiesBean getConnectionFactory2Properties() {
      return this.beanTreeNode.getConnectionFactory2Properties();
   }

   public String getConnectionFactoryMode() {
      return this.beanTreeNode.getConnectionFactoryMode();
   }

   public String getConnectionFactoryName() {
      return this.beanTreeNode.getConnectionFactoryName();
   }

   public void setConnectionFactoryName(String value) {
      this.beanTreeNode.setConnectionFactoryName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionFactoryName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertiesBean getConnectionFactoryProperties() {
      return this.beanTreeNode.getConnectionFactoryProperties();
   }

   public String getConnectionPassword() {
      return this.beanTreeNode.getConnectionPassword();
   }

   public void setConnectionPassword(String value) {
      this.beanTreeNode.setConnectionPassword(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionPassword", (Object)null, (Object)null));
      this.setModified(true);
   }

   public byte[] getConnectionPasswordEncrypted() {
      return this.beanTreeNode.getConnectionPasswordEncrypted();
   }

   public void setConnectionPasswordEncrypted(byte[] value) {
      this.beanTreeNode.setConnectionPasswordEncrypted(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionPasswordEncrypted", (Object)null, (Object)null));
      this.setModified(true);
   }

   public PropertiesBean getConnectionProperties() {
      return this.beanTreeNode.getConnectionProperties();
   }

   public String getConnectionRetainMode() {
      return this.beanTreeNode.getConnectionRetainMode();
   }

   public String getConnectionURL() {
      return this.beanTreeNode.getConnectionURL();
   }

   public void setConnectionURL(String value) {
      this.beanTreeNode.setConnectionURL(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionURL", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getConnectionUserName() {
      return this.beanTreeNode.getConnectionUserName();
   }

   public void setConnectionUserName(String value) {
      this.beanTreeNode.setConnectionUserName(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "ConnectionUserName", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DataCachesBean getDataCaches() {
      return this.beanTreeNode.getDataCaches();
   }

   public DefaultDataCacheManagerBean getDefaultDataCacheManager() {
      return this.beanTreeNode.getDefaultDataCacheManager();
   }

   public KodoDataCacheManagerBean getKodoDataCacheManager() {
      return this.beanTreeNode.getKodoDataCacheManager();
   }

   public DataCacheManagerImplBean getDataCacheManagerImpl() {
      return this.beanTreeNode.getDataCacheManagerImpl();
   }

   public CustomDataCacheManagerBean getCustomDataCacheManager() {
      return this.beanTreeNode.getCustomDataCacheManager();
   }

   public int getDataCacheTimeout() {
      return this.beanTreeNode.getDataCacheTimeout();
   }

   public void setDataCacheTimeout(int value) {
      this.beanTreeNode.setDataCacheTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DataCacheTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public AccessDictionaryBean getAccessDictionary() {
      return this.beanTreeNode.getAccessDictionary();
   }

   public DB2DictionaryBean getDB2Dictionary() {
      return this.beanTreeNode.getDB2Dictionary();
   }

   public DerbyDictionaryBean getDerbyDictionary() {
      return this.beanTreeNode.getDerbyDictionary();
   }

   public EmpressDictionaryBean getEmpressDictionary() {
      return this.beanTreeNode.getEmpressDictionary();
   }

   public FoxProDictionaryBean getFoxProDictionary() {
      return this.beanTreeNode.getFoxProDictionary();
   }

   public HSQLDictionaryBean getHSQLDictionary() {
      return this.beanTreeNode.getHSQLDictionary();
   }

   public InformixDictionaryBean getInformixDictionary() {
      return this.beanTreeNode.getInformixDictionary();
   }

   public JDataStoreDictionaryBean getJDataStoreDictionary() {
      return this.beanTreeNode.getJDataStoreDictionary();
   }

   public MySQLDictionaryBean getMySQLDictionary() {
      return this.beanTreeNode.getMySQLDictionary();
   }

   public OracleDictionaryBean getOracleDictionary() {
      return this.beanTreeNode.getOracleDictionary();
   }

   public PointbaseDictionaryBean getPointbaseDictionary() {
      return this.beanTreeNode.getPointbaseDictionary();
   }

   public PostgresDictionaryBean getPostgresDictionary() {
      return this.beanTreeNode.getPostgresDictionary();
   }

   public SQLServerDictionaryBean getSQLServerDictionary() {
      return this.beanTreeNode.getSQLServerDictionary();
   }

   public SybaseDictionaryBean getSybaseDictionary() {
      return this.beanTreeNode.getSybaseDictionary();
   }

   public CustomDictionaryBean getCustomDictionary() {
      return this.beanTreeNode.getCustomDictionary();
   }

   public Class[] getDBDictionaryTypes() {
      return this.beanTreeNode.getDBDictionaryTypes();
   }

   public DBDictionaryBean getDBDictionary() {
      return this.beanTreeNode.getDBDictionary();
   }

   public DefaultDetachStateBean getDefaultDetachState() {
      return this.beanTreeNode.getDefaultDetachState();
   }

   public DetachOptionsLoadedBean getDetachOptionsLoaded() {
      return this.beanTreeNode.getDetachOptionsLoaded();
   }

   public DetachOptionsFetchGroupsBean getDetachOptionsFetchGroups() {
      return this.beanTreeNode.getDetachOptionsFetchGroups();
   }

   public DetachOptionsAllBean getDetachOptionsAll() {
      return this.beanTreeNode.getDetachOptionsAll();
   }

   public CustomDetachStateBean getCustomDetachState() {
      return this.beanTreeNode.getCustomDetachState();
   }

   public DefaultDriverDataSourceBean getDefaultDriverDataSource() {
      return this.beanTreeNode.getDefaultDriverDataSource();
   }

   public KodoPoolingDataSourceBean getKodoPoolingDataSource() {
      return this.beanTreeNode.getKodoPoolingDataSource();
   }

   public SimpleDriverDataSourceBean getSimpleDriverDataSource() {
      return this.beanTreeNode.getSimpleDriverDataSource();
   }

   public CustomDriverDataSourceBean getCustomDriverDataSource() {
      return this.beanTreeNode.getCustomDriverDataSource();
   }

   public boolean getDynamicDataStructs() {
      return this.beanTreeNode.getDynamicDataStructs();
   }

   public void setDynamicDataStructs(boolean value) {
      this.beanTreeNode.setDynamicDataStructs(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "DynamicDataStructs", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getEagerFetchMode() {
      return this.beanTreeNode.getEagerFetchMode();
   }

   public int getFetchBatchSize() {
      return this.beanTreeNode.getFetchBatchSize();
   }

   public void setFetchBatchSize(int value) {
      this.beanTreeNode.setFetchBatchSize(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "FetchBatchSize", (Object)null, (Object)null));
      this.setModified(true);
   }

   public String getFetchDirection() {
      return this.beanTreeNode.getFetchDirection();
   }

   public FetchGroupsBean getFetchGroups() {
      return this.beanTreeNode.getFetchGroups();
   }

   public FilterListenersBean getFilterListeners() {
      return this.beanTreeNode.getFilterListeners();
   }

   public String getFlushBeforeQueries() {
      return this.beanTreeNode.getFlushBeforeQueries();
   }

   public boolean getIgnoreChanges() {
      return this.beanTreeNode.getIgnoreChanges();
   }

   public void setIgnoreChanges(boolean value) {
      this.beanTreeNode.setIgnoreChanges(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "IgnoreChanges", (Object)null, (Object)null));
      this.setModified(true);
   }

   public InverseManagerBean getInverseManager() {
      return this.beanTreeNode.getInverseManager();
   }

   public JDBCListenersBean getJDBCListeners() {
      return this.beanTreeNode.getJDBCListeners();
   }

   public DefaultLockManagerBean getDefaultLockManager() {
      return this.beanTreeNode.getDefaultLockManager();
   }

   public PessimisticLockManagerBean getPessimisticLockManager() {
      return this.beanTreeNode.getPessimisticLockManager();
   }

   public NoneLockManagerBean getNoneLockManager() {
      return this.beanTreeNode.getNoneLockManager();
   }

   public SingleJVMExclusiveLockManagerBean getSingleJVMExclusiveLockManager() {
      return this.beanTreeNode.getSingleJVMExclusiveLockManager();
   }

   public VersionLockManagerBean getVersionLockManager() {
      return this.beanTreeNode.getVersionLockManager();
   }

   public CustomLockManagerBean getCustomLockManager() {
      return this.beanTreeNode.getCustomLockManager();
   }

   public int getLockTimeout() {
      return this.beanTreeNode.getLockTimeout();
   }

   public void setLockTimeout(int value) {
      this.beanTreeNode.setLockTimeout(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "LockTimeout", (Object)null, (Object)null));
      this.setModified(true);
   }

   public CommonsLogFactoryBean getCommonsLogFactory() {
      return this.beanTreeNode.getCommonsLogFactory();
   }

   public Log4JLogFactoryBean getLog4JLogFactory() {
      return this.beanTreeNode.getLog4JLogFactory();
   }

   public LogFactoryImplBean getLogFactoryImpl() {
      return this.beanTreeNode.getLogFactoryImpl();
   }

   public NoneLogFactoryBean getNoneLogFactory() {
      return this.beanTreeNode.getNoneLogFactory();
   }

   public CustomLogBean getCustomLog() {
      return this.beanTreeNode.getCustomLog();
   }

   public String getLRSSize() {
      return this.beanTreeNode.getLRSSize();
   }

   public String getMapping() {
      return this.beanTreeNode.getMapping();
   }

   public void setMapping(String value) {
      this.beanTreeNode.setMapping(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Mapping", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DefaultMappingDefaultsBean getDefaultMappingDefaults() {
      return this.beanTreeNode.getDefaultMappingDefaults();
   }

   public DeprecatedJDOMappingDefaultsBean getDeprecatedJDOMappingDefaults() {
      return this.beanTreeNode.getDeprecatedJDOMappingDefaults();
   }

   public MappingDefaultsImplBean getMappingDefaultsImpl() {
      return this.beanTreeNode.getMappingDefaultsImpl();
   }

   public PersistenceMappingDefaultsBean getPersistenceMappingDefaults() {
      return this.beanTreeNode.getPersistenceMappingDefaults();
   }

   public CustomMappingDefaultsBean getCustomMappingDefaults() {
      return this.beanTreeNode.getCustomMappingDefaults();
   }

   public ExtensionDeprecatedJDOMappingFactoryBean getExtensionDeprecatedJDOMappingFactory() {
      return this.beanTreeNode.getExtensionDeprecatedJDOMappingFactory();
   }

   public KodoPersistenceMappingFactoryBean getKodoPersistenceMappingFactory() {
      return this.beanTreeNode.getKodoPersistenceMappingFactory();
   }

   public MappingFileDeprecatedJDOMappingFactoryBean getMappingFileDeprecatedJDOMappingFactory() {
      return this.beanTreeNode.getMappingFileDeprecatedJDOMappingFactory();
   }

   public ORMFileJDORMappingFactoryBean getORMFileJDORMappingFactory() {
      return this.beanTreeNode.getORMFileJDORMappingFactory();
   }

   public TableDeprecatedJDOMappingFactoryBean getTableDeprecatedJDOMappingFactory() {
      return this.beanTreeNode.getTableDeprecatedJDOMappingFactory();
   }

   public TableJDORMappingFactoryBean getTableJDORMappingFactory() {
      return this.beanTreeNode.getTableJDORMappingFactory();
   }

   public CustomMappingFactoryBean getCustomMappingFactory() {
      return this.beanTreeNode.getCustomMappingFactory();
   }

   public DefaultMetaDataFactoryBean getDefaultMetaDataFactory() {
      return this.beanTreeNode.getDefaultMetaDataFactory();
   }

   public JDOMetaDataFactoryBean getJDOMetaDataFactory() {
      return this.beanTreeNode.getJDOMetaDataFactory();
   }

   public DeprecatedJDOMetaDataFactoryBean getDeprecatedJDOMetaDataFactory() {
      return this.beanTreeNode.getDeprecatedJDOMetaDataFactory();
   }

   public KodoPersistenceMetaDataFactoryBean getKodoPersistenceMetaDataFactory() {
      return this.beanTreeNode.getKodoPersistenceMetaDataFactory();
   }

   public CustomMetaDataFactoryBean getCustomMetaDataFactory() {
      return this.beanTreeNode.getCustomMetaDataFactory();
   }

   public DefaultMetaDataRepositoryBean getDefaultMetaDataRepository() {
      return this.beanTreeNode.getDefaultMetaDataRepository();
   }

   public KodoMappingRepositoryBean getKodoMappingRepository() {
      return this.beanTreeNode.getKodoMappingRepository();
   }

   public CustomMetaDataRepositoryBean getCustomMetaDataRepository() {
      return this.beanTreeNode.getCustomMetaDataRepository();
   }

   public boolean getMultithreaded() {
      return this.beanTreeNode.getMultithreaded();
   }

   public void setMultithreaded(boolean value) {
      this.beanTreeNode.setMultithreaded(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Multithreaded", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNontransactionalRead() {
      return this.beanTreeNode.getNontransactionalRead();
   }

   public void setNontransactionalRead(boolean value) {
      this.beanTreeNode.setNontransactionalRead(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NontransactionalRead", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getNontransactionalWrite() {
      return this.beanTreeNode.getNontransactionalWrite();
   }

   public void setNontransactionalWrite(boolean value) {
      this.beanTreeNode.setNontransactionalWrite(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "NontransactionalWrite", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getOptimistic() {
      return this.beanTreeNode.getOptimistic();
   }

   public void setOptimistic(boolean value) {
      this.beanTreeNode.setOptimistic(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Optimistic", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DefaultOrphanedKeyActionBean getDefaultOrphanedKeyAction() {
      return this.beanTreeNode.getDefaultOrphanedKeyAction();
   }

   public LogOrphanedKeyActionBean getLogOrphanedKeyAction() {
      return this.beanTreeNode.getLogOrphanedKeyAction();
   }

   public ExceptionOrphanedKeyActionBean getExceptionOrphanedKeyAction() {
      return this.beanTreeNode.getExceptionOrphanedKeyAction();
   }

   public NoneOrphanedKeyActionBean getNoneOrphanedKeyAction() {
      return this.beanTreeNode.getNoneOrphanedKeyAction();
   }

   public CustomOrphanedKeyActionBean getCustomOrphanedKeyAction() {
      return this.beanTreeNode.getCustomOrphanedKeyAction();
   }

   public HTTPTransportBean getHTTPTransport() {
      return this.beanTreeNode.getHTTPTransport();
   }

   public TCPTransportBean getTCPTransport() {
      return this.beanTreeNode.getTCPTransport();
   }

   public CustomPersistenceServerBean getCustomPersistenceServer() {
      return this.beanTreeNode.getCustomPersistenceServer();
   }

   public DefaultProxyManagerBean getDefaultProxyManager() {
      return this.beanTreeNode.getDefaultProxyManager();
   }

   public ProfilingProxyManagerBean getProfilingProxyManager() {
      return this.beanTreeNode.getProfilingProxyManager();
   }

   public ProxyManagerImplBean getProxyManagerImpl() {
      return this.beanTreeNode.getProxyManagerImpl();
   }

   public CustomProxyManagerBean getCustomProxyManager() {
      return this.beanTreeNode.getCustomProxyManager();
   }

   public QueryCachesBean getQueryCaches() {
      return this.beanTreeNode.getQueryCaches();
   }

   public DefaultQueryCompilationCacheBean getDefaultQueryCompilationCache() {
      return this.beanTreeNode.getDefaultQueryCompilationCache();
   }

   public CacheMapBean getCacheMap() {
      return this.beanTreeNode.getCacheMap();
   }

   public ConcurrentHashMapBean getConcurrentHashMap() {
      return this.beanTreeNode.getConcurrentHashMap();
   }

   public CustomQueryCompilationCacheBean getCustomQueryCompilationCache() {
      return this.beanTreeNode.getCustomQueryCompilationCache();
   }

   public String getReadLockLevel() {
      return this.beanTreeNode.getReadLockLevel();
   }

   public JMSRemoteCommitProviderBean getJMSRemoteCommitProvider() {
      return this.beanTreeNode.getJMSRemoteCommitProvider();
   }

   public SingleJVMRemoteCommitProviderBean getSingleJVMRemoteCommitProvider() {
      return this.beanTreeNode.getSingleJVMRemoteCommitProvider();
   }

   public TCPRemoteCommitProviderBean getTCPRemoteCommitProvider() {
      return this.beanTreeNode.getTCPRemoteCommitProvider();
   }

   public ClusterRemoteCommitProviderBean getClusterRemoteCommitProvider() {
      return this.beanTreeNode.getClusterRemoteCommitProvider();
   }

   public CustomRemoteCommitProviderBean getCustomRemoteCommitProvider() {
      return this.beanTreeNode.getCustomRemoteCommitProvider();
   }

   public Class[] getRemoteCommitProviderTypes() {
      return this.beanTreeNode.getRemoteCommitProviderTypes();
   }

   public RemoteCommitProviderBean getRemoteCommitProvider() {
      return this.beanTreeNode.getRemoteCommitProvider();
   }

   public String getRestoreState() {
      return this.beanTreeNode.getRestoreState();
   }

   public String getResultSetType() {
      return this.beanTreeNode.getResultSetType();
   }

   public boolean getRetainState() {
      return this.beanTreeNode.getRetainState();
   }

   public void setRetainState(boolean value) {
      this.beanTreeNode.setRetainState(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetainState", (Object)null, (Object)null));
      this.setModified(true);
   }

   public boolean getRetryClassRegistration() {
      return this.beanTreeNode.getRetryClassRegistration();
   }

   public void setRetryClassRegistration(boolean value) {
      this.beanTreeNode.setRetryClassRegistration(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "RetryClassRegistration", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DefaultSavepointManagerBean getDefaultSavepointManager() {
      return this.beanTreeNode.getDefaultSavepointManager();
   }

   public InMemorySavepointManagerBean getInMemorySavepointManager() {
      return this.beanTreeNode.getInMemorySavepointManager();
   }

   public JDBC3SavepointManagerBean getJDBC3SavepointManager() {
      return this.beanTreeNode.getJDBC3SavepointManager();
   }

   public OracleSavepointManagerBean getOracleSavepointManager() {
      return this.beanTreeNode.getOracleSavepointManager();
   }

   public CustomSavepointManagerBean getCustomSavepointManager() {
      return this.beanTreeNode.getCustomSavepointManager();
   }

   public String getSchema() {
      return this.beanTreeNode.getSchema();
   }

   public void setSchema(String value) {
      this.beanTreeNode.setSchema(value);
      this.firePropertyChange(new PropertyChangeEvent(this, "Schema", (Object)null, (Object)null));
      this.setModified(true);
   }

   public DefaultSchemaFactoryBean getDefaultSchemaFactory() {
      return this.beanTreeNode.getDefaultSchemaFactory();
   }

   public DynamicSchemaFactoryBean getDynamicSchemaFactory() {
      return this.beanTreeNode.getDynamicSchemaFactory();
   }

   public FileSchemaFactoryBean getFileSchemaFactory() {
      return this.beanTreeNode.getFileSchemaFactory();
   }

   public LazySchemaFactoryBean getLazySchemaFactory() {
      return this.beanTreeNode.getLazySchemaFactory();
   }

   public TableSchemaFactoryBean getTableSchemaFactory() {
      return this.beanTreeNode.getTableSchemaFactory();
   }

   public CustomSchemaFactoryBean getCustomSchemaFactory() {
      return this.beanTreeNode.getCustomSchemaFactory();
   }

   public SchemasBean getSchemata() {
      return this.beanTreeNode.getSchemata();
   }

   public ClassTableJDBCSeqBean getClassTableJDBCSeq() {
      return this.beanTreeNode.getClassTableJDBCSeq();
   }

   public NativeJDBCSeqBean getNativeJDBCSeq() {
      return this.beanTreeNode.getNativeJDBCSeq();
   }

   public TableJDBCSeqBean getTableJDBCSeq() {
      return this.beanTreeNode.getTableJDBCSeq();
   }

   public TimeSeededSeqBean getTimeSeededSeq() {
      return this.beanTreeNode.getTimeSeededSeq();
   }

   public ValueTableJDBCSeqBean getValueTableJDBCSeq() {
      return this.beanTreeNode.getValueTableJDBCSeq();
   }

   public CustomSeqBean getCustomSeq() {
      return this.beanTreeNode.getCustomSeq();
   }

   public DefaultSQLFactoryBean getDefaultSQLFactory() {
      return this.beanTreeNode.getDefaultSQLFactory();
   }

   public KodoSQLFactoryBean getKodoSQLFactory() {
      return this.beanTreeNode.getKodoSQLFactory();
   }

   public CustomSQLFactoryBean getCustomSQLFactory() {
      return this.beanTreeNode.getCustomSQLFactory();
   }

   public String getSubclassFetchMode() {
      return this.beanTreeNode.getSubclassFetchMode();
   }

   public String getSynchronizeMappings() {
      return this.beanTreeNode.getSynchronizeMappings();
   }

   public String getTransactionIsolation() {
      return this.beanTreeNode.getTransactionIsolation();
   }

   public String getTransactionMode() {
      return this.beanTreeNode.getTransactionMode();
   }

   public DefaultUpdateManagerBean getDefaultUpdateManager() {
      return this.beanTreeNode.getDefaultUpdateManager();
   }

   public ConstraintUpdateManagerBean getConstraintUpdateManager() {
      return this.beanTreeNode.getConstraintUpdateManager();
   }

   public BatchingOperationOrderUpdateManagerBean getBatchingOperationOrderUpdateManager() {
      return this.beanTreeNode.getBatchingOperationOrderUpdateManager();
   }

   public OperationOrderUpdateManagerBean getOperationOrderUpdateManager() {
      return this.beanTreeNode.getOperationOrderUpdateManager();
   }

   public TableLockUpdateManagerBean getTableLockUpdateManager() {
      return this.beanTreeNode.getTableLockUpdateManager();
   }

   public CustomUpdateManagerBean getCustomUpdateManager() {
      return this.beanTreeNode.getCustomUpdateManager();
   }

   public String getWriteLockLevel() {
      return this.beanTreeNode.getWriteLockLevel();
   }

   public StackExecutionContextNameProviderBean getStackExecutionContextNameProvider() {
      return this.beanTreeNode.getStackExecutionContextNameProvider();
   }

   public TransactionNameExecutionContextNameProviderBean getTransactionNameExecutionContextNameProvider() {
      return this.beanTreeNode.getTransactionNameExecutionContextNameProvider();
   }

   public UserObjectExecutionContextNameProviderBean getUserObjectExecutionContextNameProvider() {
      return this.beanTreeNode.getUserObjectExecutionContextNameProvider();
   }

   public NoneJMXBean getNoneJMX() {
      return this.beanTreeNode.getNoneJMX();
   }

   public LocalJMXBean getLocalJMX() {
      return this.beanTreeNode.getLocalJMX();
   }

   public GUIJMXBean getGUIJMX() {
      return this.beanTreeNode.getGUIJMX();
   }

   public JMX2JMXBean getJMX2JMX() {
      return this.beanTreeNode.getJMX2JMX();
   }

   public MX4J1JMXBean getMX4J1JMX() {
      return this.beanTreeNode.getMX4J1JMX();
   }

   public WLS81JMXBean getWLS81JMX() {
      return this.beanTreeNode.getWLS81JMX();
   }

   public NoneProfilingBean getNoneProfiling() {
      return this.beanTreeNode.getNoneProfiling();
   }

   public LocalProfilingBean getLocalProfiling() {
      return this.beanTreeNode.getLocalProfiling();
   }

   public ExportProfilingBean getExportProfiling() {
      return this.beanTreeNode.getExportProfiling();
   }

   public GUIProfilingBean getGUIProfiling() {
      return this.beanTreeNode.getGUIProfiling();
   }
}
