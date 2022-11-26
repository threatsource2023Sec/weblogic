package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.descriptor.AbstractStoreBrokerFactoryBean;
import kodo.conf.descriptor.AbstractStoreBrokerFactoryBeanImpl;
import kodo.conf.descriptor.AggregateListenersBean;
import kodo.conf.descriptor.AggregateListenersBeanImpl;
import kodo.conf.descriptor.AutoDetachBean;
import kodo.conf.descriptor.AutoDetachBeanImpl;
import kodo.conf.descriptor.CacheMapBean;
import kodo.conf.descriptor.CacheMapBeanImpl;
import kodo.conf.descriptor.ClientBrokerFactoryBean;
import kodo.conf.descriptor.ClientBrokerFactoryBeanImpl;
import kodo.conf.descriptor.ClusterRemoteCommitProviderBean;
import kodo.conf.descriptor.ClusterRemoteCommitProviderBeanImpl;
import kodo.conf.descriptor.CommonsLogFactoryBean;
import kodo.conf.descriptor.CommonsLogFactoryBeanImpl;
import kodo.conf.descriptor.ConcurrentHashMapBean;
import kodo.conf.descriptor.ConcurrentHashMapBeanImpl;
import kodo.conf.descriptor.CustomBrokerFactoryBean;
import kodo.conf.descriptor.CustomBrokerFactoryBeanImpl;
import kodo.conf.descriptor.CustomBrokerImplBean;
import kodo.conf.descriptor.CustomBrokerImplBeanImpl;
import kodo.conf.descriptor.CustomClassResolverBean;
import kodo.conf.descriptor.CustomClassResolverBeanImpl;
import kodo.conf.descriptor.CustomCompatibilityBean;
import kodo.conf.descriptor.CustomCompatibilityBeanImpl;
import kodo.conf.descriptor.CustomDataCacheManagerBean;
import kodo.conf.descriptor.CustomDataCacheManagerBeanImpl;
import kodo.conf.descriptor.CustomDetachStateBean;
import kodo.conf.descriptor.CustomDetachStateBeanImpl;
import kodo.conf.descriptor.CustomLockManagerBean;
import kodo.conf.descriptor.CustomLockManagerBeanImpl;
import kodo.conf.descriptor.CustomLogBean;
import kodo.conf.descriptor.CustomLogBeanImpl;
import kodo.conf.descriptor.CustomMetaDataFactoryBean;
import kodo.conf.descriptor.CustomMetaDataFactoryBeanImpl;
import kodo.conf.descriptor.CustomMetaDataRepositoryBean;
import kodo.conf.descriptor.CustomMetaDataRepositoryBeanImpl;
import kodo.conf.descriptor.CustomOrphanedKeyActionBean;
import kodo.conf.descriptor.CustomOrphanedKeyActionBeanImpl;
import kodo.conf.descriptor.CustomPersistenceServerBean;
import kodo.conf.descriptor.CustomPersistenceServerBeanImpl;
import kodo.conf.descriptor.CustomProxyManagerBean;
import kodo.conf.descriptor.CustomProxyManagerBeanImpl;
import kodo.conf.descriptor.CustomQueryCompilationCacheBean;
import kodo.conf.descriptor.CustomQueryCompilationCacheBeanImpl;
import kodo.conf.descriptor.CustomRemoteCommitProviderBean;
import kodo.conf.descriptor.CustomRemoteCommitProviderBeanImpl;
import kodo.conf.descriptor.CustomSavepointManagerBean;
import kodo.conf.descriptor.CustomSavepointManagerBeanImpl;
import kodo.conf.descriptor.CustomSeqBean;
import kodo.conf.descriptor.CustomSeqBeanImpl;
import kodo.conf.descriptor.DataCacheManagerImplBean;
import kodo.conf.descriptor.DataCacheManagerImplBeanImpl;
import kodo.conf.descriptor.DataCachesBean;
import kodo.conf.descriptor.DataCachesBeanImpl;
import kodo.conf.descriptor.DefaultBrokerFactoryBean;
import kodo.conf.descriptor.DefaultBrokerFactoryBeanImpl;
import kodo.conf.descriptor.DefaultBrokerImplBean;
import kodo.conf.descriptor.DefaultBrokerImplBeanImpl;
import kodo.conf.descriptor.DefaultClassResolverBean;
import kodo.conf.descriptor.DefaultClassResolverBeanImpl;
import kodo.conf.descriptor.DefaultCompatibilityBean;
import kodo.conf.descriptor.DefaultCompatibilityBeanImpl;
import kodo.conf.descriptor.DefaultDataCacheManagerBean;
import kodo.conf.descriptor.DefaultDataCacheManagerBeanImpl;
import kodo.conf.descriptor.DefaultDetachStateBean;
import kodo.conf.descriptor.DefaultDetachStateBeanImpl;
import kodo.conf.descriptor.DefaultLockManagerBean;
import kodo.conf.descriptor.DefaultLockManagerBeanImpl;
import kodo.conf.descriptor.DefaultMetaDataFactoryBean;
import kodo.conf.descriptor.DefaultMetaDataFactoryBeanImpl;
import kodo.conf.descriptor.DefaultMetaDataRepositoryBean;
import kodo.conf.descriptor.DefaultMetaDataRepositoryBeanImpl;
import kodo.conf.descriptor.DefaultOrphanedKeyActionBean;
import kodo.conf.descriptor.DefaultOrphanedKeyActionBeanImpl;
import kodo.conf.descriptor.DefaultProxyManagerBean;
import kodo.conf.descriptor.DefaultProxyManagerBeanImpl;
import kodo.conf.descriptor.DefaultQueryCompilationCacheBean;
import kodo.conf.descriptor.DefaultQueryCompilationCacheBeanImpl;
import kodo.conf.descriptor.DefaultSavepointManagerBean;
import kodo.conf.descriptor.DefaultSavepointManagerBeanImpl;
import kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBean;
import kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBeanImpl;
import kodo.conf.descriptor.DetachOptionsAllBean;
import kodo.conf.descriptor.DetachOptionsAllBeanImpl;
import kodo.conf.descriptor.DetachOptionsFetchGroupsBean;
import kodo.conf.descriptor.DetachOptionsFetchGroupsBeanImpl;
import kodo.conf.descriptor.DetachOptionsLoadedBean;
import kodo.conf.descriptor.DetachOptionsLoadedBeanImpl;
import kodo.conf.descriptor.ExceptionOrphanedKeyActionBean;
import kodo.conf.descriptor.ExceptionOrphanedKeyActionBeanImpl;
import kodo.conf.descriptor.ExportProfilingBean;
import kodo.conf.descriptor.ExportProfilingBeanImpl;
import kodo.conf.descriptor.FetchGroupsBean;
import kodo.conf.descriptor.FetchGroupsBeanImpl;
import kodo.conf.descriptor.FilterListenersBean;
import kodo.conf.descriptor.FilterListenersBeanImpl;
import kodo.conf.descriptor.GUIJMXBean;
import kodo.conf.descriptor.GUIJMXBeanImpl;
import kodo.conf.descriptor.GUIProfilingBean;
import kodo.conf.descriptor.GUIProfilingBeanImpl;
import kodo.conf.descriptor.HTTPTransportBean;
import kodo.conf.descriptor.HTTPTransportBeanImpl;
import kodo.conf.descriptor.InMemorySavepointManagerBean;
import kodo.conf.descriptor.InMemorySavepointManagerBeanImpl;
import kodo.conf.descriptor.InverseManagerBean;
import kodo.conf.descriptor.InverseManagerBeanImpl;
import kodo.conf.descriptor.JDOMetaDataFactoryBean;
import kodo.conf.descriptor.JDOMetaDataFactoryBeanImpl;
import kodo.conf.descriptor.JMSRemoteCommitProviderBean;
import kodo.conf.descriptor.JMSRemoteCommitProviderBeanImpl;
import kodo.conf.descriptor.JMX2JMXBean;
import kodo.conf.descriptor.JMX2JMXBeanImpl;
import kodo.conf.descriptor.KodoBrokerBean;
import kodo.conf.descriptor.KodoBrokerBeanImpl;
import kodo.conf.descriptor.KodoCompatibilityBean;
import kodo.conf.descriptor.KodoCompatibilityBeanImpl;
import kodo.conf.descriptor.KodoDataCacheManagerBean;
import kodo.conf.descriptor.KodoDataCacheManagerBeanImpl;
import kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBean;
import kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBeanImpl;
import kodo.conf.descriptor.LocalJMXBean;
import kodo.conf.descriptor.LocalJMXBeanImpl;
import kodo.conf.descriptor.LocalProfilingBean;
import kodo.conf.descriptor.LocalProfilingBeanImpl;
import kodo.conf.descriptor.Log4JLogFactoryBean;
import kodo.conf.descriptor.Log4JLogFactoryBeanImpl;
import kodo.conf.descriptor.LogFactoryImplBean;
import kodo.conf.descriptor.LogFactoryImplBeanImpl;
import kodo.conf.descriptor.LogOrphanedKeyActionBean;
import kodo.conf.descriptor.LogOrphanedKeyActionBeanImpl;
import kodo.conf.descriptor.MX4J1JMXBean;
import kodo.conf.descriptor.MX4J1JMXBeanImpl;
import kodo.conf.descriptor.NoneJMXBean;
import kodo.conf.descriptor.NoneJMXBeanImpl;
import kodo.conf.descriptor.NoneLockManagerBean;
import kodo.conf.descriptor.NoneLockManagerBeanImpl;
import kodo.conf.descriptor.NoneLogFactoryBean;
import kodo.conf.descriptor.NoneLogFactoryBeanImpl;
import kodo.conf.descriptor.NoneOrphanedKeyActionBean;
import kodo.conf.descriptor.NoneOrphanedKeyActionBeanImpl;
import kodo.conf.descriptor.NoneProfilingBean;
import kodo.conf.descriptor.NoneProfilingBeanImpl;
import kodo.conf.descriptor.ProfilingProxyManagerBean;
import kodo.conf.descriptor.ProfilingProxyManagerBeanImpl;
import kodo.conf.descriptor.PropertiesBean;
import kodo.conf.descriptor.PropertiesBeanImpl;
import kodo.conf.descriptor.ProxyManagerImplBean;
import kodo.conf.descriptor.ProxyManagerImplBeanImpl;
import kodo.conf.descriptor.QueryCachesBean;
import kodo.conf.descriptor.QueryCachesBeanImpl;
import kodo.conf.descriptor.RemoteCommitProviderBean;
import kodo.conf.descriptor.SingleJVMExclusiveLockManagerBean;
import kodo.conf.descriptor.SingleJVMExclusiveLockManagerBeanImpl;
import kodo.conf.descriptor.SingleJVMRemoteCommitProviderBean;
import kodo.conf.descriptor.SingleJVMRemoteCommitProviderBeanImpl;
import kodo.conf.descriptor.StackExecutionContextNameProviderBean;
import kodo.conf.descriptor.StackExecutionContextNameProviderBeanImpl;
import kodo.conf.descriptor.TCPRemoteCommitProviderBean;
import kodo.conf.descriptor.TCPRemoteCommitProviderBeanImpl;
import kodo.conf.descriptor.TCPTransportBean;
import kodo.conf.descriptor.TCPTransportBeanImpl;
import kodo.conf.descriptor.TimeSeededSeqBean;
import kodo.conf.descriptor.TimeSeededSeqBeanImpl;
import kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBean;
import kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBeanImpl;
import kodo.conf.descriptor.UserObjectExecutionContextNameProviderBean;
import kodo.conf.descriptor.UserObjectExecutionContextNameProviderBeanImpl;
import kodo.conf.descriptor.VersionLockManagerBean;
import kodo.conf.descriptor.VersionLockManagerBeanImpl;
import kodo.conf.descriptor.WLS81JMXBean;
import kodo.conf.descriptor.WLS81JMXBeanImpl;
import kodo.jdbc.conf.customizers.PersistenceUnitConfigurationBeanCustomizer;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PersistenceUnitConfigurationBeanImpl extends AbstractDescriptorBean implements PersistenceUnitConfigurationBean, Serializable {
   private AbstractStoreBrokerFactoryBean _AbstractStoreBrokerFactory;
   private AccessDictionaryBean _AccessDictionary;
   private AggregateListenersBean _AggregateListeners;
   private String _AutoClear;
   private AutoDetachBean _AutoDetaches;
   private BatchingOperationOrderUpdateManagerBean _BatchingOperationOrderUpdateManager;
   private CacheMapBean _CacheMap;
   private ClassTableJDBCSeqBean _ClassTableJDBCSeq;
   private ClientBrokerFactoryBean _ClientBrokerFactory;
   private ClusterRemoteCommitProviderBean _ClusterRemoteCommitProvider;
   private CommonsLogFactoryBean _CommonsLogFactory;
   private KodoCompatibilityBean _Compatibility;
   private ConcurrentHashMapBean _ConcurrentHashMap;
   private String _Connection2DriverName;
   private String _Connection2Password;
   private byte[] _Connection2PasswordEncrypted;
   private PropertiesBean _Connection2Properties;
   private String _Connection2URL;
   private String _Connection2UserName;
   private ConnectionDecoratorsBean _ConnectionDecorators;
   private String _ConnectionDriverName;
   private String _ConnectionFactory2Name;
   private PropertiesBean _ConnectionFactory2Properties;
   private String _ConnectionFactoryMode;
   private String _ConnectionFactoryName;
   private PropertiesBean _ConnectionFactoryProperties;
   private String _ConnectionPassword;
   private byte[] _ConnectionPasswordEncrypted;
   private PropertiesBean _ConnectionProperties;
   private String _ConnectionRetainMode;
   private String _ConnectionURL;
   private String _ConnectionUserName;
   private ConstraintUpdateManagerBean _ConstraintUpdateManager;
   private CustomBrokerFactoryBean _CustomBrokerFactory;
   private CustomBrokerImplBean _CustomBrokerImpl;
   private CustomClassResolverBean _CustomClassResolver;
   private CustomCompatibilityBean _CustomCompatibility;
   private CustomDataCacheManagerBean _CustomDataCacheManager;
   private CustomDetachStateBean _CustomDetachState;
   private CustomDictionaryBean _CustomDictionary;
   private CustomDriverDataSourceBean _CustomDriverDataSource;
   private CustomLockManagerBean _CustomLockManager;
   private CustomLogBean _CustomLog;
   private CustomMappingDefaultsBean _CustomMappingDefaults;
   private CustomMappingFactoryBean _CustomMappingFactory;
   private CustomMetaDataFactoryBean _CustomMetaDataFactory;
   private CustomMetaDataRepositoryBean _CustomMetaDataRepository;
   private CustomOrphanedKeyActionBean _CustomOrphanedKeyAction;
   private CustomPersistenceServerBean _CustomPersistenceServer;
   private CustomProxyManagerBean _CustomProxyManager;
   private CustomQueryCompilationCacheBean _CustomQueryCompilationCache;
   private CustomRemoteCommitProviderBean _CustomRemoteCommitProvider;
   private CustomSQLFactoryBean _CustomSQLFactory;
   private CustomSavepointManagerBean _CustomSavepointManager;
   private CustomSchemaFactoryBean _CustomSchemaFactory;
   private CustomSeqBean _CustomSeq;
   private CustomUpdateManagerBean _CustomUpdateManager;
   private DB2DictionaryBean _DB2Dictionary;
   private DataCacheManagerImplBean _DataCacheManagerImpl;
   private int _DataCacheTimeout;
   private DataCachesBean _DataCaches;
   private DefaultBrokerFactoryBean _DefaultBrokerFactory;
   private DefaultBrokerImplBean _DefaultBrokerImpl;
   private DefaultClassResolverBean _DefaultClassResolver;
   private DefaultCompatibilityBean _DefaultCompatibility;
   private DefaultDataCacheManagerBean _DefaultDataCacheManager;
   private DefaultDetachStateBean _DefaultDetachState;
   private DefaultDriverDataSourceBean _DefaultDriverDataSource;
   private DefaultLockManagerBean _DefaultLockManager;
   private DefaultMappingDefaultsBean _DefaultMappingDefaults;
   private DefaultMetaDataFactoryBean _DefaultMetaDataFactory;
   private DefaultMetaDataRepositoryBean _DefaultMetaDataRepository;
   private DefaultOrphanedKeyActionBean _DefaultOrphanedKeyAction;
   private DefaultProxyManagerBean _DefaultProxyManager;
   private DefaultQueryCompilationCacheBean _DefaultQueryCompilationCache;
   private DefaultSQLFactoryBean _DefaultSQLFactory;
   private DefaultSavepointManagerBean _DefaultSavepointManager;
   private DefaultSchemaFactoryBean _DefaultSchemaFactory;
   private DefaultUpdateManagerBean _DefaultUpdateManager;
   private DeprecatedJDOMappingDefaultsBean _DeprecatedJDOMappingDefaults;
   private DeprecatedJDOMetaDataFactoryBean _DeprecatedJDOMetaDataFactory;
   private DerbyDictionaryBean _DerbyDictionary;
   private DetachOptionsAllBean _DetachOptionsAll;
   private DetachOptionsFetchGroupsBean _DetachOptionsFetchGroups;
   private DetachOptionsLoadedBean _DetachOptionsLoaded;
   private boolean _DynamicDataStructs;
   private DynamicSchemaFactoryBean _DynamicSchemaFactory;
   private String _EagerFetchMode;
   private EmpressDictionaryBean _EmpressDictionary;
   private ExceptionOrphanedKeyActionBean _ExceptionOrphanedKeyAction;
   private ExportProfilingBean _ExportProfiling;
   private ExtensionDeprecatedJDOMappingFactoryBean _ExtensionDeprecatedJDOMappingFactory;
   private int _FetchBatchSize;
   private String _FetchDirection;
   private FetchGroupsBean _FetchGroups;
   private FileSchemaFactoryBean _FileSchemaFactory;
   private FilterListenersBean _FilterListeners;
   private String _FlushBeforeQueries;
   private FoxProDictionaryBean _FoxProDictionary;
   private GUIJMXBean _GUIJMX;
   private GUIProfilingBean _GUIProfiling;
   private HSQLDictionaryBean _HSQLDictionary;
   private HTTPTransportBean _HTTPTransport;
   private boolean _IgnoreChanges;
   private InMemorySavepointManagerBean _InMemorySavepointManager;
   private InformixDictionaryBean _InformixDictionary;
   private InverseManagerBean _InverseManager;
   private JDBC3SavepointManagerBean _JDBC3SavepointManager;
   private JDBCBrokerFactoryBean _JDBCBrokerFactory;
   private JDBCListenersBean _JDBCListeners;
   private JDOMetaDataFactoryBean _JDOMetaDataFactory;
   private JDataStoreDictionaryBean _JDataStoreDictionary;
   private JMSRemoteCommitProviderBean _JMSRemoteCommitProvider;
   private JMX2JMXBean _JMX2JMX;
   private KodoBrokerBean _KodoBroker;
   private KodoDataCacheManagerBean _KodoDataCacheManager;
   private KodoMappingRepositoryBean _KodoMappingRepository;
   private KodoPersistenceMappingFactoryBean _KodoPersistenceMappingFactory;
   private KodoPersistenceMetaDataFactoryBean _KodoPersistenceMetaDataFactory;
   private KodoPoolingDataSourceBean _KodoPoolingDataSource;
   private KodoSQLFactoryBean _KodoSQLFactory;
   private String _LRSSize;
   private LazySchemaFactoryBean _LazySchemaFactory;
   private LocalJMXBean _LocalJMX;
   private LocalProfilingBean _LocalProfiling;
   private int _LockTimeout;
   private Log4JLogFactoryBean _Log4JLogFactory;
   private LogFactoryImplBean _LogFactoryImpl;
   private LogOrphanedKeyActionBean _LogOrphanedKeyAction;
   private MX4J1JMXBean _MX4J1JMX;
   private String _Mapping;
   private MappingDefaultsImplBean _MappingDefaultsImpl;
   private MappingFileDeprecatedJDOMappingFactoryBean _MappingFileDeprecatedJDOMappingFactory;
   private boolean _Multithreaded;
   private MySQLDictionaryBean _MySQLDictionary;
   private String _Name;
   private NativeJDBCSeqBean _NativeJDBCSeq;
   private NoneJMXBean _NoneJMX;
   private NoneLockManagerBean _NoneLockManager;
   private NoneLogFactoryBean _NoneLogFactory;
   private NoneOrphanedKeyActionBean _NoneOrphanedKeyAction;
   private NoneProfilingBean _NoneProfiling;
   private boolean _NontransactionalRead;
   private boolean _NontransactionalWrite;
   private ORMFileJDORMappingFactoryBean _ORMFileJDORMappingFactory;
   private OperationOrderUpdateManagerBean _OperationOrderUpdateManager;
   private boolean _Optimistic;
   private OracleDictionaryBean _OracleDictionary;
   private OracleSavepointManagerBean _OracleSavepointManager;
   private PersistenceMappingDefaultsBean _PersistenceMappingDefaults;
   private PessimisticLockManagerBean _PessimisticLockManager;
   private PointbaseDictionaryBean _PointbaseDictionary;
   private PostgresDictionaryBean _PostgresDictionary;
   private ProfilingProxyManagerBean _ProfilingProxyManager;
   private ProxyManagerImplBean _ProxyManagerImpl;
   private QueryCachesBean _QueryCaches;
   private String _ReadLockLevel;
   private String _RestoreState;
   private String _ResultSetType;
   private boolean _RetainState;
   private boolean _RetryClassRegistration;
   private SQLServerDictionaryBean _SQLServerDictionary;
   private String _Schema;
   private SchemasBean _Schemata;
   private SimpleDriverDataSourceBean _SimpleDriverDataSource;
   private SingleJVMExclusiveLockManagerBean _SingleJVMExclusiveLockManager;
   private SingleJVMRemoteCommitProviderBean _SingleJVMRemoteCommitProvider;
   private StackExecutionContextNameProviderBean _StackExecutionContextNameProvider;
   private String _SubclassFetchMode;
   private SybaseDictionaryBean _SybaseDictionary;
   private String _SynchronizeMappings;
   private TCPRemoteCommitProviderBean _TCPRemoteCommitProvider;
   private TCPTransportBean _TCPTransport;
   private TableDeprecatedJDOMappingFactoryBean _TableDeprecatedJDOMappingFactory;
   private TableJDBCSeqBean _TableJDBCSeq;
   private TableJDORMappingFactoryBean _TableJDORMappingFactory;
   private TableLockUpdateManagerBean _TableLockUpdateManager;
   private TableSchemaFactoryBean _TableSchemaFactory;
   private TimeSeededSeqBean _TimeSeededSeq;
   private String _TransactionIsolation;
   private String _TransactionMode;
   private TransactionNameExecutionContextNameProviderBean _TransactionNameExecutionContextNameProvider;
   private UserObjectExecutionContextNameProviderBean _UserObjectExecutionContextNameProvider;
   private ValueTableJDBCSeqBean _ValueTableJDBCSeq;
   private VersionLockManagerBean _VersionLockManager;
   private WLS81JMXBean _WLS81JMX;
   private String _WriteLockLevel;
   private transient PersistenceUnitConfigurationBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceUnitConfigurationBeanImpl() {
      try {
         this._customizer = new PersistenceUnitConfigurationBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public PersistenceUnitConfigurationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new PersistenceUnitConfigurationBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public PersistenceUnitConfigurationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new PersistenceUnitConfigurationBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public AggregateListenersBean getAggregateListeners() {
      return this._AggregateListeners;
   }

   public boolean isAggregateListenersInherited() {
      return false;
   }

   public boolean isAggregateListenersSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getAggregateListeners());
   }

   public void setAggregateListeners(AggregateListenersBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      AggregateListenersBean _oldVal = this._AggregateListeners;
      this._AggregateListeners = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getAutoClear() {
      return this._AutoClear;
   }

   public boolean isAutoClearInherited() {
      return false;
   }

   public boolean isAutoClearSet() {
      return this._isSet(2);
   }

   public void setAutoClear(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"all", "datastore"};
      param0 = LegalChecks.checkInEnum("AutoClear", param0, _set);
      String _oldVal = this._AutoClear;
      this._AutoClear = param0;
      this._postSet(2, _oldVal, param0);
   }

   public AutoDetachBean getAutoDetaches() {
      return this._AutoDetaches;
   }

   public boolean isAutoDetachesInherited() {
      return false;
   }

   public boolean isAutoDetachesSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getAutoDetaches());
   }

   public void setAutoDetaches(AutoDetachBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      AutoDetachBean _oldVal = this._AutoDetaches;
      this._AutoDetaches = param0;
      this._postSet(3, _oldVal, param0);
   }

   public DefaultBrokerFactoryBean getDefaultBrokerFactory() {
      return this._DefaultBrokerFactory;
   }

   public boolean isDefaultBrokerFactoryInherited() {
      return false;
   }

   public boolean isDefaultBrokerFactorySet() {
      return this._isSet(4);
   }

   public void setDefaultBrokerFactory(DefaultBrokerFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultBrokerFactory() != null && param0 != this.getDefaultBrokerFactory()) {
         throw new BeanAlreadyExistsException(this.getDefaultBrokerFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultBrokerFactoryBean _oldVal = this._DefaultBrokerFactory;
         this._DefaultBrokerFactory = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public DefaultBrokerFactoryBean createDefaultBrokerFactory() {
      DefaultBrokerFactoryBeanImpl _val = new DefaultBrokerFactoryBeanImpl(this, -1);

      try {
         this.setDefaultBrokerFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultBrokerFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultBrokerFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultBrokerFactory((DefaultBrokerFactoryBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public AbstractStoreBrokerFactoryBean getAbstractStoreBrokerFactory() {
      return this._AbstractStoreBrokerFactory;
   }

   public boolean isAbstractStoreBrokerFactoryInherited() {
      return false;
   }

   public boolean isAbstractStoreBrokerFactorySet() {
      return this._isSet(5);
   }

   public void setAbstractStoreBrokerFactory(AbstractStoreBrokerFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAbstractStoreBrokerFactory() != null && param0 != this.getAbstractStoreBrokerFactory()) {
         throw new BeanAlreadyExistsException(this.getAbstractStoreBrokerFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AbstractStoreBrokerFactoryBean _oldVal = this._AbstractStoreBrokerFactory;
         this._AbstractStoreBrokerFactory = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public AbstractStoreBrokerFactoryBean createAbstractStoreBrokerFactory() {
      AbstractStoreBrokerFactoryBeanImpl _val = new AbstractStoreBrokerFactoryBeanImpl(this, -1);

      try {
         this.setAbstractStoreBrokerFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAbstractStoreBrokerFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AbstractStoreBrokerFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAbstractStoreBrokerFactory((AbstractStoreBrokerFactoryBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ClientBrokerFactoryBean getClientBrokerFactory() {
      return this._ClientBrokerFactory;
   }

   public boolean isClientBrokerFactoryInherited() {
      return false;
   }

   public boolean isClientBrokerFactorySet() {
      return this._isSet(6);
   }

   public void setClientBrokerFactory(ClientBrokerFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getClientBrokerFactory() != null && param0 != this.getClientBrokerFactory()) {
         throw new BeanAlreadyExistsException(this.getClientBrokerFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 6)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ClientBrokerFactoryBean _oldVal = this._ClientBrokerFactory;
         this._ClientBrokerFactory = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public ClientBrokerFactoryBean createClientBrokerFactory() {
      ClientBrokerFactoryBeanImpl _val = new ClientBrokerFactoryBeanImpl(this, -1);

      try {
         this.setClientBrokerFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyClientBrokerFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ClientBrokerFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setClientBrokerFactory((ClientBrokerFactoryBean)null);
               this._unSet(6);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JDBCBrokerFactoryBean getJDBCBrokerFactory() {
      return this._JDBCBrokerFactory;
   }

   public boolean isJDBCBrokerFactoryInherited() {
      return false;
   }

   public boolean isJDBCBrokerFactorySet() {
      return this._isSet(7);
   }

   public void setJDBCBrokerFactory(JDBCBrokerFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJDBCBrokerFactory() != null && param0 != this.getJDBCBrokerFactory()) {
         throw new BeanAlreadyExistsException(this.getJDBCBrokerFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 7)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JDBCBrokerFactoryBean _oldVal = this._JDBCBrokerFactory;
         this._JDBCBrokerFactory = param0;
         this._postSet(7, _oldVal, param0);
      }
   }

   public JDBCBrokerFactoryBean createJDBCBrokerFactory() {
      JDBCBrokerFactoryBeanImpl _val = new JDBCBrokerFactoryBeanImpl(this, -1);

      try {
         this.setJDBCBrokerFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJDBCBrokerFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JDBCBrokerFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDBCBrokerFactory((JDBCBrokerFactoryBean)null);
               this._unSet(7);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomBrokerFactoryBean getCustomBrokerFactory() {
      return this._CustomBrokerFactory;
   }

   public boolean isCustomBrokerFactoryInherited() {
      return false;
   }

   public boolean isCustomBrokerFactorySet() {
      return this._isSet(8);
   }

   public void setCustomBrokerFactory(CustomBrokerFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomBrokerFactory() != null && param0 != this.getCustomBrokerFactory()) {
         throw new BeanAlreadyExistsException(this.getCustomBrokerFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomBrokerFactoryBean _oldVal = this._CustomBrokerFactory;
         this._CustomBrokerFactory = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public CustomBrokerFactoryBean createCustomBrokerFactory() {
      CustomBrokerFactoryBeanImpl _val = new CustomBrokerFactoryBeanImpl(this, -1);

      try {
         this.setCustomBrokerFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomBrokerFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomBrokerFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomBrokerFactory((CustomBrokerFactoryBean)null);
               this._unSet(8);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultBrokerImplBean getDefaultBrokerImpl() {
      return this._DefaultBrokerImpl;
   }

   public boolean isDefaultBrokerImplInherited() {
      return false;
   }

   public boolean isDefaultBrokerImplSet() {
      return this._isSet(9);
   }

   public void setDefaultBrokerImpl(DefaultBrokerImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultBrokerImpl() != null && param0 != this.getDefaultBrokerImpl()) {
         throw new BeanAlreadyExistsException(this.getDefaultBrokerImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 9)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultBrokerImplBean _oldVal = this._DefaultBrokerImpl;
         this._DefaultBrokerImpl = param0;
         this._postSet(9, _oldVal, param0);
      }
   }

   public DefaultBrokerImplBean createDefaultBrokerImpl() {
      DefaultBrokerImplBeanImpl _val = new DefaultBrokerImplBeanImpl(this, -1);

      try {
         this.setDefaultBrokerImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultBrokerImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultBrokerImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultBrokerImpl((DefaultBrokerImplBean)null);
               this._unSet(9);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoBrokerBean getKodoBroker() {
      return this._KodoBroker;
   }

   public boolean isKodoBrokerInherited() {
      return false;
   }

   public boolean isKodoBrokerSet() {
      return this._isSet(10);
   }

   public void setKodoBroker(KodoBrokerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoBroker() != null && param0 != this.getKodoBroker()) {
         throw new BeanAlreadyExistsException(this.getKodoBroker() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 10)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoBrokerBean _oldVal = this._KodoBroker;
         this._KodoBroker = param0;
         this._postSet(10, _oldVal, param0);
      }
   }

   public KodoBrokerBean createKodoBroker() {
      KodoBrokerBeanImpl _val = new KodoBrokerBeanImpl(this, -1);

      try {
         this.setKodoBroker(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoBroker() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoBroker;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoBroker((KodoBrokerBean)null);
               this._unSet(10);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomBrokerImplBean getCustomBrokerImpl() {
      return this._CustomBrokerImpl;
   }

   public boolean isCustomBrokerImplInherited() {
      return false;
   }

   public boolean isCustomBrokerImplSet() {
      return this._isSet(11);
   }

   public void setCustomBrokerImpl(CustomBrokerImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomBrokerImpl() != null && param0 != this.getCustomBrokerImpl()) {
         throw new BeanAlreadyExistsException(this.getCustomBrokerImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 11)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomBrokerImplBean _oldVal = this._CustomBrokerImpl;
         this._CustomBrokerImpl = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public CustomBrokerImplBean createCustomBrokerImpl() {
      CustomBrokerImplBeanImpl _val = new CustomBrokerImplBeanImpl(this, -1);

      try {
         this.setCustomBrokerImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomBrokerImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomBrokerImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomBrokerImpl((CustomBrokerImplBean)null);
               this._unSet(11);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultClassResolverBean getDefaultClassResolver() {
      return this._DefaultClassResolver;
   }

   public boolean isDefaultClassResolverInherited() {
      return false;
   }

   public boolean isDefaultClassResolverSet() {
      return this._isSet(12);
   }

   public void setDefaultClassResolver(DefaultClassResolverBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultClassResolver() != null && param0 != this.getDefaultClassResolver()) {
         throw new BeanAlreadyExistsException(this.getDefaultClassResolver() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 12)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultClassResolverBean _oldVal = this._DefaultClassResolver;
         this._DefaultClassResolver = param0;
         this._postSet(12, _oldVal, param0);
      }
   }

   public DefaultClassResolverBean createDefaultClassResolver() {
      DefaultClassResolverBeanImpl _val = new DefaultClassResolverBeanImpl(this, -1);

      try {
         this.setDefaultClassResolver(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultClassResolver() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultClassResolver;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultClassResolver((DefaultClassResolverBean)null);
               this._unSet(12);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomClassResolverBean getCustomClassResolver() {
      return this._CustomClassResolver;
   }

   public boolean isCustomClassResolverInherited() {
      return false;
   }

   public boolean isCustomClassResolverSet() {
      return this._isSet(13);
   }

   public void setCustomClassResolver(CustomClassResolverBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomClassResolver() != null && param0 != this.getCustomClassResolver()) {
         throw new BeanAlreadyExistsException(this.getCustomClassResolver() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 13)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomClassResolverBean _oldVal = this._CustomClassResolver;
         this._CustomClassResolver = param0;
         this._postSet(13, _oldVal, param0);
      }
   }

   public CustomClassResolverBean createCustomClassResolver() {
      CustomClassResolverBeanImpl _val = new CustomClassResolverBeanImpl(this, -1);

      try {
         this.setCustomClassResolver(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomClassResolver() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomClassResolver;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomClassResolver((CustomClassResolverBean)null);
               this._unSet(13);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultCompatibilityBean getDefaultCompatibility() {
      return this._DefaultCompatibility;
   }

   public boolean isDefaultCompatibilityInherited() {
      return false;
   }

   public boolean isDefaultCompatibilitySet() {
      return this._isSet(14);
   }

   public void setDefaultCompatibility(DefaultCompatibilityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultCompatibility() != null && param0 != this.getDefaultCompatibility()) {
         throw new BeanAlreadyExistsException(this.getDefaultCompatibility() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 14)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultCompatibilityBean _oldVal = this._DefaultCompatibility;
         this._DefaultCompatibility = param0;
         this._postSet(14, _oldVal, param0);
      }
   }

   public DefaultCompatibilityBean createDefaultCompatibility() {
      DefaultCompatibilityBeanImpl _val = new DefaultCompatibilityBeanImpl(this, -1);

      try {
         this.setDefaultCompatibility(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultCompatibility() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultCompatibility;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultCompatibility((DefaultCompatibilityBean)null);
               this._unSet(14);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoCompatibilityBean getCompatibility() {
      return this._Compatibility;
   }

   public boolean isCompatibilityInherited() {
      return false;
   }

   public boolean isCompatibilitySet() {
      return this._isSet(15);
   }

   public void setCompatibility(KodoCompatibilityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCompatibility() != null && param0 != this.getCompatibility()) {
         throw new BeanAlreadyExistsException(this.getCompatibility() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 15)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoCompatibilityBean _oldVal = this._Compatibility;
         this._Compatibility = param0;
         this._postSet(15, _oldVal, param0);
      }
   }

   public KodoCompatibilityBean createCompatibilty() {
      KodoCompatibilityBeanImpl _val = new KodoCompatibilityBeanImpl(this, -1);

      try {
         this.setCompatibility(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCompatibility() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Compatibility;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCompatibility((KodoCompatibilityBean)null);
               this._unSet(15);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomCompatibilityBean getCustomCompatibility() {
      return this._CustomCompatibility;
   }

   public boolean isCustomCompatibilityInherited() {
      return false;
   }

   public boolean isCustomCompatibilitySet() {
      return this._isSet(16);
   }

   public void setCustomCompatibility(CustomCompatibilityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomCompatibility() != null && param0 != this.getCustomCompatibility()) {
         throw new BeanAlreadyExistsException(this.getCustomCompatibility() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 16)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomCompatibilityBean _oldVal = this._CustomCompatibility;
         this._CustomCompatibility = param0;
         this._postSet(16, _oldVal, param0);
      }
   }

   public CustomCompatibilityBean createCustomCompatibility() {
      CustomCompatibilityBeanImpl _val = new CustomCompatibilityBeanImpl(this, -1);

      try {
         this.setCustomCompatibility(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomCompatibility() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomCompatibility;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomCompatibility((CustomCompatibilityBean)null);
               this._unSet(16);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getConnection2DriverName() {
      return this._Connection2DriverName;
   }

   public boolean isConnection2DriverNameInherited() {
      return false;
   }

   public boolean isConnection2DriverNameSet() {
      return this._isSet(17);
   }

   public void setConnection2DriverName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Connection2DriverName;
      this._Connection2DriverName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getConnection2Password() {
      byte[] bEncrypted = this.getConnection2PasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("Connection2Password", bEncrypted);
   }

   public boolean isConnection2PasswordInherited() {
      return false;
   }

   public boolean isConnection2PasswordSet() {
      return this.isConnection2PasswordEncryptedSet();
   }

   public void setConnection2Password(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setConnection2PasswordEncrypted(param0 == null ? null : this._encrypt("Connection2Password", param0));
   }

   public byte[] getConnection2PasswordEncrypted() {
      return this._getHelper()._cloneArray(this._Connection2PasswordEncrypted);
   }

   public String getConnection2PasswordEncryptedAsString() {
      byte[] obj = this.getConnection2PasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isConnection2PasswordEncryptedInherited() {
      return false;
   }

   public boolean isConnection2PasswordEncryptedSet() {
      return this._isSet(19);
   }

   public void setConnection2PasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setConnection2PasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PropertiesBean getConnection2Properties() {
      return this._Connection2Properties;
   }

   public boolean isConnection2PropertiesInherited() {
      return false;
   }

   public boolean isConnection2PropertiesSet() {
      return this._isSet(20) || this._isAnythingSet((AbstractDescriptorBean)this.getConnection2Properties());
   }

   public void setConnection2Properties(PropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 20)) {
         this._postCreate(_child);
      }

      PropertiesBean _oldVal = this._Connection2Properties;
      this._Connection2Properties = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getConnection2URL() {
      return this._Connection2URL;
   }

   public boolean isConnection2URLInherited() {
      return false;
   }

   public boolean isConnection2URLSet() {
      return this._isSet(21);
   }

   public void setConnection2URL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Connection2URL;
      this._Connection2URL = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getConnection2UserName() {
      return this._Connection2UserName;
   }

   public boolean isConnection2UserNameInherited() {
      return false;
   }

   public boolean isConnection2UserNameSet() {
      return this._isSet(22);
   }

   public void setConnection2UserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Connection2UserName;
      this._Connection2UserName = param0;
      this._postSet(22, _oldVal, param0);
   }

   public ConnectionDecoratorsBean getConnectionDecorators() {
      return this._ConnectionDecorators;
   }

   public boolean isConnectionDecoratorsInherited() {
      return false;
   }

   public boolean isConnectionDecoratorsSet() {
      return this._isSet(23) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectionDecorators());
   }

   public void setConnectionDecorators(ConnectionDecoratorsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 23)) {
         this._postCreate(_child);
      }

      ConnectionDecoratorsBean _oldVal = this._ConnectionDecorators;
      this._ConnectionDecorators = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getConnectionDriverName() {
      return this._ConnectionDriverName;
   }

   public boolean isConnectionDriverNameInherited() {
      return false;
   }

   public boolean isConnectionDriverNameSet() {
      return this._isSet(24);
   }

   public void setConnectionDriverName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionDriverName;
      this._ConnectionDriverName = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getConnectionFactory2Name() {
      return this._ConnectionFactory2Name;
   }

   public boolean isConnectionFactory2NameInherited() {
      return false;
   }

   public boolean isConnectionFactory2NameSet() {
      return this._isSet(25);
   }

   public void setConnectionFactory2Name(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactory2Name;
      this._ConnectionFactory2Name = param0;
      this._postSet(25, _oldVal, param0);
   }

   public PropertiesBean getConnectionFactory2Properties() {
      return this._ConnectionFactory2Properties;
   }

   public boolean isConnectionFactory2PropertiesInherited() {
      return false;
   }

   public boolean isConnectionFactory2PropertiesSet() {
      return this._isSet(26) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectionFactory2Properties());
   }

   public void setConnectionFactory2Properties(PropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 26)) {
         this._postCreate(_child);
      }

      PropertiesBean _oldVal = this._ConnectionFactory2Properties;
      this._ConnectionFactory2Properties = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getConnectionFactoryMode() {
      return this._ConnectionFactoryMode;
   }

   public boolean isConnectionFactoryModeInherited() {
      return false;
   }

   public boolean isConnectionFactoryModeSet() {
      return this._isSet(27);
   }

   public void setConnectionFactoryMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"local", "managed"};
      param0 = LegalChecks.checkInEnum("ConnectionFactoryMode", param0, _set);
      String _oldVal = this._ConnectionFactoryMode;
      this._ConnectionFactoryMode = param0;
      this._postSet(27, _oldVal, param0);
   }

   public String getConnectionFactoryName() {
      return this._ConnectionFactoryName;
   }

   public boolean isConnectionFactoryNameInherited() {
      return false;
   }

   public boolean isConnectionFactoryNameSet() {
      return this._isSet(28);
   }

   public void setConnectionFactoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryName;
      this._ConnectionFactoryName = param0;
      this._postSet(28, _oldVal, param0);
   }

   public PropertiesBean getConnectionFactoryProperties() {
      return this._ConnectionFactoryProperties;
   }

   public boolean isConnectionFactoryPropertiesInherited() {
      return false;
   }

   public boolean isConnectionFactoryPropertiesSet() {
      return this._isSet(29) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectionFactoryProperties());
   }

   public void setConnectionFactoryProperties(PropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 29)) {
         this._postCreate(_child);
      }

      PropertiesBean _oldVal = this._ConnectionFactoryProperties;
      this._ConnectionFactoryProperties = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getConnectionPassword() {
      byte[] bEncrypted = this.getConnectionPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("ConnectionPassword", bEncrypted);
   }

   public boolean isConnectionPasswordInherited() {
      return false;
   }

   public boolean isConnectionPasswordSet() {
      return this.isConnectionPasswordEncryptedSet();
   }

   public void setConnectionPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setConnectionPasswordEncrypted(param0 == null ? null : this._encrypt("ConnectionPassword", param0));
   }

   public byte[] getConnectionPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._ConnectionPasswordEncrypted);
   }

   public String getConnectionPasswordEncryptedAsString() {
      byte[] obj = this.getConnectionPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isConnectionPasswordEncryptedInherited() {
      return false;
   }

   public boolean isConnectionPasswordEncryptedSet() {
      return this._isSet(31);
   }

   public void setConnectionPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setConnectionPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PropertiesBean getConnectionProperties() {
      return this._ConnectionProperties;
   }

   public boolean isConnectionPropertiesInherited() {
      return false;
   }

   public boolean isConnectionPropertiesSet() {
      return this._isSet(32) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectionProperties());
   }

   public void setConnectionProperties(PropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 32)) {
         this._postCreate(_child);
      }

      PropertiesBean _oldVal = this._ConnectionProperties;
      this._ConnectionProperties = param0;
      this._postSet(32, _oldVal, param0);
   }

   public String getConnectionRetainMode() {
      return this._ConnectionRetainMode;
   }

   public boolean isConnectionRetainModeInherited() {
      return false;
   }

   public boolean isConnectionRetainModeSet() {
      return this._isSet(33);
   }

   public void setConnectionRetainMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"always", "on-demand", "persistence-manager", "transaction"};
      param0 = LegalChecks.checkInEnum("ConnectionRetainMode", param0, _set);
      String _oldVal = this._ConnectionRetainMode;
      this._ConnectionRetainMode = param0;
      this._postSet(33, _oldVal, param0);
   }

   public String getConnectionURL() {
      return this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(34);
   }

   public void setConnectionURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(34, _oldVal, param0);
   }

   public String getConnectionUserName() {
      return this._ConnectionUserName;
   }

   public boolean isConnectionUserNameInherited() {
      return false;
   }

   public boolean isConnectionUserNameSet() {
      return this._isSet(35);
   }

   public void setConnectionUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionUserName;
      this._ConnectionUserName = param0;
      this._postSet(35, _oldVal, param0);
   }

   public DataCachesBean getDataCaches() {
      return this._DataCaches;
   }

   public boolean isDataCachesInherited() {
      return false;
   }

   public boolean isDataCachesSet() {
      return this._isSet(36) || this._isAnythingSet((AbstractDescriptorBean)this.getDataCaches());
   }

   public void setDataCaches(DataCachesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 36)) {
         this._postCreate(_child);
      }

      DataCachesBean _oldVal = this._DataCaches;
      this._DataCaches = param0;
      this._postSet(36, _oldVal, param0);
   }

   public DefaultDataCacheManagerBean getDefaultDataCacheManager() {
      return this._DefaultDataCacheManager;
   }

   public boolean isDefaultDataCacheManagerInherited() {
      return false;
   }

   public boolean isDefaultDataCacheManagerSet() {
      return this._isSet(37);
   }

   public void setDefaultDataCacheManager(DefaultDataCacheManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultDataCacheManager() != null && param0 != this.getDefaultDataCacheManager()) {
         throw new BeanAlreadyExistsException(this.getDefaultDataCacheManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 37)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultDataCacheManagerBean _oldVal = this._DefaultDataCacheManager;
         this._DefaultDataCacheManager = param0;
         this._postSet(37, _oldVal, param0);
      }
   }

   public DefaultDataCacheManagerBean createDefaultDataCacheManager() {
      DefaultDataCacheManagerBeanImpl _val = new DefaultDataCacheManagerBeanImpl(this, -1);

      try {
         this.setDefaultDataCacheManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultDataCacheManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultDataCacheManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultDataCacheManager((DefaultDataCacheManagerBean)null);
               this._unSet(37);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoDataCacheManagerBean getKodoDataCacheManager() {
      return this._KodoDataCacheManager;
   }

   public boolean isKodoDataCacheManagerInherited() {
      return false;
   }

   public boolean isKodoDataCacheManagerSet() {
      return this._isSet(38);
   }

   public void setKodoDataCacheManager(KodoDataCacheManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoDataCacheManager() != null && param0 != this.getKodoDataCacheManager()) {
         throw new BeanAlreadyExistsException(this.getKodoDataCacheManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 38)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoDataCacheManagerBean _oldVal = this._KodoDataCacheManager;
         this._KodoDataCacheManager = param0;
         this._postSet(38, _oldVal, param0);
      }
   }

   public KodoDataCacheManagerBean createKodoDataCacheManager() {
      KodoDataCacheManagerBeanImpl _val = new KodoDataCacheManagerBeanImpl(this, -1);

      try {
         this.setKodoDataCacheManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoDataCacheManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoDataCacheManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoDataCacheManager((KodoDataCacheManagerBean)null);
               this._unSet(38);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DataCacheManagerImplBean getDataCacheManagerImpl() {
      return this._DataCacheManagerImpl;
   }

   public boolean isDataCacheManagerImplInherited() {
      return false;
   }

   public boolean isDataCacheManagerImplSet() {
      return this._isSet(39);
   }

   public void setDataCacheManagerImpl(DataCacheManagerImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDataCacheManagerImpl() != null && param0 != this.getDataCacheManagerImpl()) {
         throw new BeanAlreadyExistsException(this.getDataCacheManagerImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 39)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DataCacheManagerImplBean _oldVal = this._DataCacheManagerImpl;
         this._DataCacheManagerImpl = param0;
         this._postSet(39, _oldVal, param0);
      }
   }

   public DataCacheManagerImplBean createDataCacheManagerImpl() {
      DataCacheManagerImplBeanImpl _val = new DataCacheManagerImplBeanImpl(this, -1);

      try {
         this.setDataCacheManagerImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDataCacheManagerImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DataCacheManagerImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDataCacheManagerImpl((DataCacheManagerImplBean)null);
               this._unSet(39);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomDataCacheManagerBean getCustomDataCacheManager() {
      return this._CustomDataCacheManager;
   }

   public boolean isCustomDataCacheManagerInherited() {
      return false;
   }

   public boolean isCustomDataCacheManagerSet() {
      return this._isSet(40);
   }

   public void setCustomDataCacheManager(CustomDataCacheManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomDataCacheManager() != null && param0 != this.getCustomDataCacheManager()) {
         throw new BeanAlreadyExistsException(this.getCustomDataCacheManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 40)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomDataCacheManagerBean _oldVal = this._CustomDataCacheManager;
         this._CustomDataCacheManager = param0;
         this._postSet(40, _oldVal, param0);
      }
   }

   public CustomDataCacheManagerBean createCustomDataCacheManager() {
      CustomDataCacheManagerBeanImpl _val = new CustomDataCacheManagerBeanImpl(this, -1);

      try {
         this.setCustomDataCacheManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomDataCacheManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomDataCacheManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomDataCacheManager((CustomDataCacheManagerBean)null);
               this._unSet(40);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public int getDataCacheTimeout() {
      return this._DataCacheTimeout;
   }

   public boolean isDataCacheTimeoutInherited() {
      return false;
   }

   public boolean isDataCacheTimeoutSet() {
      return this._isSet(41);
   }

   public void setDataCacheTimeout(int param0) {
      int _oldVal = this._DataCacheTimeout;
      this._DataCacheTimeout = param0;
      this._postSet(41, _oldVal, param0);
   }

   public AccessDictionaryBean getAccessDictionary() {
      return this._AccessDictionary;
   }

   public boolean isAccessDictionaryInherited() {
      return false;
   }

   public boolean isAccessDictionarySet() {
      return this._isSet(42);
   }

   public void setAccessDictionary(AccessDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAccessDictionary() != null && param0 != this.getAccessDictionary()) {
         throw new BeanAlreadyExistsException(this.getAccessDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 42)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AccessDictionaryBean _oldVal = this._AccessDictionary;
         this._AccessDictionary = param0;
         this._postSet(42, _oldVal, param0);
      }
   }

   public AccessDictionaryBean createAccessDictionary() {
      AccessDictionaryBeanImpl _val = new AccessDictionaryBeanImpl(this, -1);

      try {
         this.setAccessDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAccessDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AccessDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAccessDictionary((AccessDictionaryBean)null);
               this._unSet(42);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DB2DictionaryBean getDB2Dictionary() {
      return this._DB2Dictionary;
   }

   public boolean isDB2DictionaryInherited() {
      return false;
   }

   public boolean isDB2DictionarySet() {
      return this._isSet(43);
   }

   public void setDB2Dictionary(DB2DictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDB2Dictionary() != null && param0 != this.getDB2Dictionary()) {
         throw new BeanAlreadyExistsException(this.getDB2Dictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 43)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DB2DictionaryBean _oldVal = this._DB2Dictionary;
         this._DB2Dictionary = param0;
         this._postSet(43, _oldVal, param0);
      }
   }

   public DB2DictionaryBean createDB2Dictionary() {
      DB2DictionaryBeanImpl _val = new DB2DictionaryBeanImpl(this, -1);

      try {
         this.setDB2Dictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDB2Dictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DB2Dictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDB2Dictionary((DB2DictionaryBean)null);
               this._unSet(43);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DerbyDictionaryBean getDerbyDictionary() {
      return this._DerbyDictionary;
   }

   public boolean isDerbyDictionaryInherited() {
      return false;
   }

   public boolean isDerbyDictionarySet() {
      return this._isSet(44);
   }

   public void setDerbyDictionary(DerbyDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDerbyDictionary() != null && param0 != this.getDerbyDictionary()) {
         throw new BeanAlreadyExistsException(this.getDerbyDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 44)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DerbyDictionaryBean _oldVal = this._DerbyDictionary;
         this._DerbyDictionary = param0;
         this._postSet(44, _oldVal, param0);
      }
   }

   public DerbyDictionaryBean createDerbyDictionary() {
      DerbyDictionaryBeanImpl _val = new DerbyDictionaryBeanImpl(this, -1);

      try {
         this.setDerbyDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDerbyDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DerbyDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDerbyDictionary((DerbyDictionaryBean)null);
               this._unSet(44);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public EmpressDictionaryBean getEmpressDictionary() {
      return this._EmpressDictionary;
   }

   public boolean isEmpressDictionaryInherited() {
      return false;
   }

   public boolean isEmpressDictionarySet() {
      return this._isSet(45);
   }

   public void setEmpressDictionary(EmpressDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEmpressDictionary() != null && param0 != this.getEmpressDictionary()) {
         throw new BeanAlreadyExistsException(this.getEmpressDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 45)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EmpressDictionaryBean _oldVal = this._EmpressDictionary;
         this._EmpressDictionary = param0;
         this._postSet(45, _oldVal, param0);
      }
   }

   public EmpressDictionaryBean createEmpressDictionary() {
      EmpressDictionaryBeanImpl _val = new EmpressDictionaryBeanImpl(this, -1);

      try {
         this.setEmpressDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEmpressDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._EmpressDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEmpressDictionary((EmpressDictionaryBean)null);
               this._unSet(45);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public FoxProDictionaryBean getFoxProDictionary() {
      return this._FoxProDictionary;
   }

   public boolean isFoxProDictionaryInherited() {
      return false;
   }

   public boolean isFoxProDictionarySet() {
      return this._isSet(46);
   }

   public void setFoxProDictionary(FoxProDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFoxProDictionary() != null && param0 != this.getFoxProDictionary()) {
         throw new BeanAlreadyExistsException(this.getFoxProDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 46)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         FoxProDictionaryBean _oldVal = this._FoxProDictionary;
         this._FoxProDictionary = param0;
         this._postSet(46, _oldVal, param0);
      }
   }

   public FoxProDictionaryBean createFoxProDictionary() {
      FoxProDictionaryBeanImpl _val = new FoxProDictionaryBeanImpl(this, -1);

      try {
         this.setFoxProDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFoxProDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FoxProDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFoxProDictionary((FoxProDictionaryBean)null);
               this._unSet(46);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public HSQLDictionaryBean getHSQLDictionary() {
      return this._HSQLDictionary;
   }

   public boolean isHSQLDictionaryInherited() {
      return false;
   }

   public boolean isHSQLDictionarySet() {
      return this._isSet(47);
   }

   public void setHSQLDictionary(HSQLDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getHSQLDictionary() != null && param0 != this.getHSQLDictionary()) {
         throw new BeanAlreadyExistsException(this.getHSQLDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 47)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         HSQLDictionaryBean _oldVal = this._HSQLDictionary;
         this._HSQLDictionary = param0;
         this._postSet(47, _oldVal, param0);
      }
   }

   public HSQLDictionaryBean createHSQLDictionary() {
      HSQLDictionaryBeanImpl _val = new HSQLDictionaryBeanImpl(this, -1);

      try {
         this.setHSQLDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyHSQLDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._HSQLDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHSQLDictionary((HSQLDictionaryBean)null);
               this._unSet(47);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public InformixDictionaryBean getInformixDictionary() {
      return this._InformixDictionary;
   }

   public boolean isInformixDictionaryInherited() {
      return false;
   }

   public boolean isInformixDictionarySet() {
      return this._isSet(48);
   }

   public void setInformixDictionary(InformixDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInformixDictionary() != null && param0 != this.getInformixDictionary()) {
         throw new BeanAlreadyExistsException(this.getInformixDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 48)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InformixDictionaryBean _oldVal = this._InformixDictionary;
         this._InformixDictionary = param0;
         this._postSet(48, _oldVal, param0);
      }
   }

   public InformixDictionaryBean createInformixDictionary() {
      InformixDictionaryBeanImpl _val = new InformixDictionaryBeanImpl(this, -1);

      try {
         this.setInformixDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInformixDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._InformixDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInformixDictionary((InformixDictionaryBean)null);
               this._unSet(48);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JDataStoreDictionaryBean getJDataStoreDictionary() {
      return this._JDataStoreDictionary;
   }

   public boolean isJDataStoreDictionaryInherited() {
      return false;
   }

   public boolean isJDataStoreDictionarySet() {
      return this._isSet(49);
   }

   public void setJDataStoreDictionary(JDataStoreDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJDataStoreDictionary() != null && param0 != this.getJDataStoreDictionary()) {
         throw new BeanAlreadyExistsException(this.getJDataStoreDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 49)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JDataStoreDictionaryBean _oldVal = this._JDataStoreDictionary;
         this._JDataStoreDictionary = param0;
         this._postSet(49, _oldVal, param0);
      }
   }

   public JDataStoreDictionaryBean createJDataStoreDictionary() {
      JDataStoreDictionaryBeanImpl _val = new JDataStoreDictionaryBeanImpl(this, -1);

      try {
         this.setJDataStoreDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJDataStoreDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JDataStoreDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDataStoreDictionary((JDataStoreDictionaryBean)null);
               this._unSet(49);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MySQLDictionaryBean getMySQLDictionary() {
      return this._MySQLDictionary;
   }

   public boolean isMySQLDictionaryInherited() {
      return false;
   }

   public boolean isMySQLDictionarySet() {
      return this._isSet(50);
   }

   public void setMySQLDictionary(MySQLDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMySQLDictionary() != null && param0 != this.getMySQLDictionary()) {
         throw new BeanAlreadyExistsException(this.getMySQLDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 50)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MySQLDictionaryBean _oldVal = this._MySQLDictionary;
         this._MySQLDictionary = param0;
         this._postSet(50, _oldVal, param0);
      }
   }

   public MySQLDictionaryBean createMySQLDictionary() {
      MySQLDictionaryBeanImpl _val = new MySQLDictionaryBeanImpl(this, -1);

      try {
         this.setMySQLDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMySQLDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MySQLDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMySQLDictionary((MySQLDictionaryBean)null);
               this._unSet(50);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OracleDictionaryBean getOracleDictionary() {
      return this._OracleDictionary;
   }

   public boolean isOracleDictionaryInherited() {
      return false;
   }

   public boolean isOracleDictionarySet() {
      return this._isSet(51);
   }

   public void setOracleDictionary(OracleDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOracleDictionary() != null && param0 != this.getOracleDictionary()) {
         throw new BeanAlreadyExistsException(this.getOracleDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 51)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OracleDictionaryBean _oldVal = this._OracleDictionary;
         this._OracleDictionary = param0;
         this._postSet(51, _oldVal, param0);
      }
   }

   public OracleDictionaryBean createOracleDictionary() {
      OracleDictionaryBeanImpl _val = new OracleDictionaryBeanImpl(this, -1);

      try {
         this.setOracleDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOracleDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OracleDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOracleDictionary((OracleDictionaryBean)null);
               this._unSet(51);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PointbaseDictionaryBean getPointbaseDictionary() {
      return this._PointbaseDictionary;
   }

   public boolean isPointbaseDictionaryInherited() {
      return false;
   }

   public boolean isPointbaseDictionarySet() {
      return this._isSet(52);
   }

   public void setPointbaseDictionary(PointbaseDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPointbaseDictionary() != null && param0 != this.getPointbaseDictionary()) {
         throw new BeanAlreadyExistsException(this.getPointbaseDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 52)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PointbaseDictionaryBean _oldVal = this._PointbaseDictionary;
         this._PointbaseDictionary = param0;
         this._postSet(52, _oldVal, param0);
      }
   }

   public PointbaseDictionaryBean createPointbaseDictionary() {
      PointbaseDictionaryBeanImpl _val = new PointbaseDictionaryBeanImpl(this, -1);

      try {
         this.setPointbaseDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPointbaseDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PointbaseDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPointbaseDictionary((PointbaseDictionaryBean)null);
               this._unSet(52);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PostgresDictionaryBean getPostgresDictionary() {
      return this._PostgresDictionary;
   }

   public boolean isPostgresDictionaryInherited() {
      return false;
   }

   public boolean isPostgresDictionarySet() {
      return this._isSet(53);
   }

   public void setPostgresDictionary(PostgresDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPostgresDictionary() != null && param0 != this.getPostgresDictionary()) {
         throw new BeanAlreadyExistsException(this.getPostgresDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 53)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PostgresDictionaryBean _oldVal = this._PostgresDictionary;
         this._PostgresDictionary = param0;
         this._postSet(53, _oldVal, param0);
      }
   }

   public PostgresDictionaryBean createPostgresDictionary() {
      PostgresDictionaryBeanImpl _val = new PostgresDictionaryBeanImpl(this, -1);

      try {
         this.setPostgresDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPostgresDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PostgresDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPostgresDictionary((PostgresDictionaryBean)null);
               this._unSet(53);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SQLServerDictionaryBean getSQLServerDictionary() {
      return this._SQLServerDictionary;
   }

   public boolean isSQLServerDictionaryInherited() {
      return false;
   }

   public boolean isSQLServerDictionarySet() {
      return this._isSet(54);
   }

   public void setSQLServerDictionary(SQLServerDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSQLServerDictionary() != null && param0 != this.getSQLServerDictionary()) {
         throw new BeanAlreadyExistsException(this.getSQLServerDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 54)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SQLServerDictionaryBean _oldVal = this._SQLServerDictionary;
         this._SQLServerDictionary = param0;
         this._postSet(54, _oldVal, param0);
      }
   }

   public SQLServerDictionaryBean createSQLServerDictionary() {
      SQLServerDictionaryBeanImpl _val = new SQLServerDictionaryBeanImpl(this, -1);

      try {
         this.setSQLServerDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySQLServerDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SQLServerDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSQLServerDictionary((SQLServerDictionaryBean)null);
               this._unSet(54);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SybaseDictionaryBean getSybaseDictionary() {
      return this._SybaseDictionary;
   }

   public boolean isSybaseDictionaryInherited() {
      return false;
   }

   public boolean isSybaseDictionarySet() {
      return this._isSet(55);
   }

   public void setSybaseDictionary(SybaseDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSybaseDictionary() != null && param0 != this.getSybaseDictionary()) {
         throw new BeanAlreadyExistsException(this.getSybaseDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 55)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SybaseDictionaryBean _oldVal = this._SybaseDictionary;
         this._SybaseDictionary = param0;
         this._postSet(55, _oldVal, param0);
      }
   }

   public SybaseDictionaryBean createSybaseDictionary() {
      SybaseDictionaryBeanImpl _val = new SybaseDictionaryBeanImpl(this, -1);

      try {
         this.setSybaseDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySybaseDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SybaseDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSybaseDictionary((SybaseDictionaryBean)null);
               this._unSet(55);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomDictionaryBean getCustomDictionary() {
      return this._CustomDictionary;
   }

   public boolean isCustomDictionaryInherited() {
      return false;
   }

   public boolean isCustomDictionarySet() {
      return this._isSet(56);
   }

   public void setCustomDictionary(CustomDictionaryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomDictionary() != null && param0 != this.getCustomDictionary()) {
         throw new BeanAlreadyExistsException(this.getCustomDictionary() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 56)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomDictionaryBean _oldVal = this._CustomDictionary;
         this._CustomDictionary = param0;
         this._postSet(56, _oldVal, param0);
      }
   }

   public CustomDictionaryBean createCustomDictionary() {
      CustomDictionaryBeanImpl _val = new CustomDictionaryBeanImpl(this, -1);

      try {
         this.setCustomDictionary(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomDictionary() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomDictionary;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomDictionary((CustomDictionaryBean)null);
               this._unSet(56);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Class[] getDBDictionaryTypes() {
      return this._customizer.getDBDictionaryTypes();
   }

   public DBDictionaryBean getDBDictionary() {
      return this._customizer.getDBDictionary();
   }

   public DBDictionaryBean createDBDictionary(Class param0) {
      return this._customizer.createDBDictionary(param0);
   }

   public void destroyDBDictionary() {
      this._customizer.destroyDBDictionary();
   }

   public DefaultDetachStateBean getDefaultDetachState() {
      return this._DefaultDetachState;
   }

   public boolean isDefaultDetachStateInherited() {
      return false;
   }

   public boolean isDefaultDetachStateSet() {
      return this._isSet(57);
   }

   public void setDefaultDetachState(DefaultDetachStateBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultDetachState() != null && param0 != this.getDefaultDetachState()) {
         throw new BeanAlreadyExistsException(this.getDefaultDetachState() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 57)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultDetachStateBean _oldVal = this._DefaultDetachState;
         this._DefaultDetachState = param0;
         this._postSet(57, _oldVal, param0);
      }
   }

   public DefaultDetachStateBean createDefaultDetachState() {
      DefaultDetachStateBeanImpl _val = new DefaultDetachStateBeanImpl(this, -1);

      try {
         this.setDefaultDetachState(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultDetachState() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultDetachState;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultDetachState((DefaultDetachStateBean)null);
               this._unSet(57);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DetachOptionsLoadedBean getDetachOptionsLoaded() {
      return this._DetachOptionsLoaded;
   }

   public boolean isDetachOptionsLoadedInherited() {
      return false;
   }

   public boolean isDetachOptionsLoadedSet() {
      return this._isSet(58);
   }

   public void setDetachOptionsLoaded(DetachOptionsLoadedBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDetachOptionsLoaded() != null && param0 != this.getDetachOptionsLoaded()) {
         throw new BeanAlreadyExistsException(this.getDetachOptionsLoaded() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 58)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DetachOptionsLoadedBean _oldVal = this._DetachOptionsLoaded;
         this._DetachOptionsLoaded = param0;
         this._postSet(58, _oldVal, param0);
      }
   }

   public DetachOptionsLoadedBean createDetachOptionsLoaded() {
      DetachOptionsLoadedBeanImpl _val = new DetachOptionsLoadedBeanImpl(this, -1);

      try {
         this.setDetachOptionsLoaded(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDetachOptionsLoaded() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DetachOptionsLoaded;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDetachOptionsLoaded((DetachOptionsLoadedBean)null);
               this._unSet(58);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DetachOptionsFetchGroupsBean getDetachOptionsFetchGroups() {
      return this._DetachOptionsFetchGroups;
   }

   public boolean isDetachOptionsFetchGroupsInherited() {
      return false;
   }

   public boolean isDetachOptionsFetchGroupsSet() {
      return this._isSet(59);
   }

   public void setDetachOptionsFetchGroups(DetachOptionsFetchGroupsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDetachOptionsFetchGroups() != null && param0 != this.getDetachOptionsFetchGroups()) {
         throw new BeanAlreadyExistsException(this.getDetachOptionsFetchGroups() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 59)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DetachOptionsFetchGroupsBean _oldVal = this._DetachOptionsFetchGroups;
         this._DetachOptionsFetchGroups = param0;
         this._postSet(59, _oldVal, param0);
      }
   }

   public DetachOptionsFetchGroupsBean createDetachOptionsFetchGroups() {
      DetachOptionsFetchGroupsBeanImpl _val = new DetachOptionsFetchGroupsBeanImpl(this, -1);

      try {
         this.setDetachOptionsFetchGroups(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDetachOptionsFetchGroups() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DetachOptionsFetchGroups;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDetachOptionsFetchGroups((DetachOptionsFetchGroupsBean)null);
               this._unSet(59);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DetachOptionsAllBean getDetachOptionsAll() {
      return this._DetachOptionsAll;
   }

   public boolean isDetachOptionsAllInherited() {
      return false;
   }

   public boolean isDetachOptionsAllSet() {
      return this._isSet(60);
   }

   public void setDetachOptionsAll(DetachOptionsAllBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDetachOptionsAll() != null && param0 != this.getDetachOptionsAll()) {
         throw new BeanAlreadyExistsException(this.getDetachOptionsAll() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 60)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DetachOptionsAllBean _oldVal = this._DetachOptionsAll;
         this._DetachOptionsAll = param0;
         this._postSet(60, _oldVal, param0);
      }
   }

   public DetachOptionsAllBean createDetachOptionsAll() {
      DetachOptionsAllBeanImpl _val = new DetachOptionsAllBeanImpl(this, -1);

      try {
         this.setDetachOptionsAll(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDetachOptionsAll() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DetachOptionsAll;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDetachOptionsAll((DetachOptionsAllBean)null);
               this._unSet(60);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomDetachStateBean getCustomDetachState() {
      return this._CustomDetachState;
   }

   public boolean isCustomDetachStateInherited() {
      return false;
   }

   public boolean isCustomDetachStateSet() {
      return this._isSet(61);
   }

   public void setCustomDetachState(CustomDetachStateBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomDetachState() != null && param0 != this.getCustomDetachState()) {
         throw new BeanAlreadyExistsException(this.getCustomDetachState() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 61)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomDetachStateBean _oldVal = this._CustomDetachState;
         this._CustomDetachState = param0;
         this._postSet(61, _oldVal, param0);
      }
   }

   public CustomDetachStateBean createCustomDetachState() {
      CustomDetachStateBeanImpl _val = new CustomDetachStateBeanImpl(this, -1);

      try {
         this.setCustomDetachState(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomDetachState() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomDetachState;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomDetachState((CustomDetachStateBean)null);
               this._unSet(61);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultDriverDataSourceBean getDefaultDriverDataSource() {
      return this._DefaultDriverDataSource;
   }

   public boolean isDefaultDriverDataSourceInherited() {
      return false;
   }

   public boolean isDefaultDriverDataSourceSet() {
      return this._isSet(62);
   }

   public void setDefaultDriverDataSource(DefaultDriverDataSourceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultDriverDataSource() != null && param0 != this.getDefaultDriverDataSource()) {
         throw new BeanAlreadyExistsException(this.getDefaultDriverDataSource() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 62)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultDriverDataSourceBean _oldVal = this._DefaultDriverDataSource;
         this._DefaultDriverDataSource = param0;
         this._postSet(62, _oldVal, param0);
      }
   }

   public DefaultDriverDataSourceBean createDefaultDriverDataSource() {
      DefaultDriverDataSourceBeanImpl _val = new DefaultDriverDataSourceBeanImpl(this, -1);

      try {
         this.setDefaultDriverDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultDriverDataSource() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultDriverDataSource;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultDriverDataSource((DefaultDriverDataSourceBean)null);
               this._unSet(62);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoPoolingDataSourceBean getKodoPoolingDataSource() {
      return this._KodoPoolingDataSource;
   }

   public boolean isKodoPoolingDataSourceInherited() {
      return false;
   }

   public boolean isKodoPoolingDataSourceSet() {
      return this._isSet(63);
   }

   public void setKodoPoolingDataSource(KodoPoolingDataSourceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoPoolingDataSource() != null && param0 != this.getKodoPoolingDataSource()) {
         throw new BeanAlreadyExistsException(this.getKodoPoolingDataSource() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 63)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoPoolingDataSourceBean _oldVal = this._KodoPoolingDataSource;
         this._KodoPoolingDataSource = param0;
         this._postSet(63, _oldVal, param0);
      }
   }

   public KodoPoolingDataSourceBean createKodoPoolingDataSource() {
      KodoPoolingDataSourceBeanImpl _val = new KodoPoolingDataSourceBeanImpl(this, -1);

      try {
         this.setKodoPoolingDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoPoolingDataSource() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoPoolingDataSource;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoPoolingDataSource((KodoPoolingDataSourceBean)null);
               this._unSet(63);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SimpleDriverDataSourceBean getSimpleDriverDataSource() {
      return this._SimpleDriverDataSource;
   }

   public boolean isSimpleDriverDataSourceInherited() {
      return false;
   }

   public boolean isSimpleDriverDataSourceSet() {
      return this._isSet(64);
   }

   public void setSimpleDriverDataSource(SimpleDriverDataSourceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSimpleDriverDataSource() != null && param0 != this.getSimpleDriverDataSource()) {
         throw new BeanAlreadyExistsException(this.getSimpleDriverDataSource() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 64)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SimpleDriverDataSourceBean _oldVal = this._SimpleDriverDataSource;
         this._SimpleDriverDataSource = param0;
         this._postSet(64, _oldVal, param0);
      }
   }

   public SimpleDriverDataSourceBean createSimpleDriverDataSource() {
      SimpleDriverDataSourceBeanImpl _val = new SimpleDriverDataSourceBeanImpl(this, -1);

      try {
         this.setSimpleDriverDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySimpleDriverDataSource() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SimpleDriverDataSource;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSimpleDriverDataSource((SimpleDriverDataSourceBean)null);
               this._unSet(64);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomDriverDataSourceBean getCustomDriverDataSource() {
      return this._CustomDriverDataSource;
   }

   public boolean isCustomDriverDataSourceInherited() {
      return false;
   }

   public boolean isCustomDriverDataSourceSet() {
      return this._isSet(65);
   }

   public void setCustomDriverDataSource(CustomDriverDataSourceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomDriverDataSource() != null && param0 != this.getCustomDriverDataSource()) {
         throw new BeanAlreadyExistsException(this.getCustomDriverDataSource() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 65)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomDriverDataSourceBean _oldVal = this._CustomDriverDataSource;
         this._CustomDriverDataSource = param0;
         this._postSet(65, _oldVal, param0);
      }
   }

   public CustomDriverDataSourceBean createCustomDriverDataSource() {
      CustomDriverDataSourceBeanImpl _val = new CustomDriverDataSourceBeanImpl(this, -1);

      try {
         this.setCustomDriverDataSource(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomDriverDataSource() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomDriverDataSource;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomDriverDataSource((CustomDriverDataSourceBean)null);
               this._unSet(65);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean getDynamicDataStructs() {
      return this._DynamicDataStructs;
   }

   public boolean isDynamicDataStructsInherited() {
      return false;
   }

   public boolean isDynamicDataStructsSet() {
      return this._isSet(66);
   }

   public void setDynamicDataStructs(boolean param0) {
      boolean _oldVal = this._DynamicDataStructs;
      this._DynamicDataStructs = param0;
      this._postSet(66, _oldVal, param0);
   }

   public String getEagerFetchMode() {
      return this._EagerFetchMode;
   }

   public boolean isEagerFetchModeInherited() {
      return false;
   }

   public boolean isEagerFetchModeSet() {
      return this._isSet(67);
   }

   public void setEagerFetchMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"join", "multiple", "none", "parallel", "single"};
      param0 = LegalChecks.checkInEnum("EagerFetchMode", param0, _set);
      String _oldVal = this._EagerFetchMode;
      this._EagerFetchMode = param0;
      this._postSet(67, _oldVal, param0);
   }

   public int getFetchBatchSize() {
      return this._FetchBatchSize;
   }

   public boolean isFetchBatchSizeInherited() {
      return false;
   }

   public boolean isFetchBatchSizeSet() {
      return this._isSet(68);
   }

   public void setFetchBatchSize(int param0) {
      int _oldVal = this._FetchBatchSize;
      this._FetchBatchSize = param0;
      this._postSet(68, _oldVal, param0);
   }

   public String getFetchDirection() {
      return this._FetchDirection;
   }

   public boolean isFetchDirectionInherited() {
      return false;
   }

   public boolean isFetchDirectionSet() {
      return this._isSet(69);
   }

   public void setFetchDirection(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"forward", "reverse", "unknown"};
      param0 = LegalChecks.checkInEnum("FetchDirection", param0, _set);
      String _oldVal = this._FetchDirection;
      this._FetchDirection = param0;
      this._postSet(69, _oldVal, param0);
   }

   public FetchGroupsBean getFetchGroups() {
      return this._FetchGroups;
   }

   public boolean isFetchGroupsInherited() {
      return false;
   }

   public boolean isFetchGroupsSet() {
      return this._isSet(70) || this._isAnythingSet((AbstractDescriptorBean)this.getFetchGroups());
   }

   public void setFetchGroups(FetchGroupsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 70)) {
         this._postCreate(_child);
      }

      FetchGroupsBean _oldVal = this._FetchGroups;
      this._FetchGroups = param0;
      this._postSet(70, _oldVal, param0);
   }

   public FilterListenersBean getFilterListeners() {
      return this._FilterListeners;
   }

   public boolean isFilterListenersInherited() {
      return false;
   }

   public boolean isFilterListenersSet() {
      return this._isSet(71) || this._isAnythingSet((AbstractDescriptorBean)this.getFilterListeners());
   }

   public void setFilterListeners(FilterListenersBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 71)) {
         this._postCreate(_child);
      }

      FilterListenersBean _oldVal = this._FilterListeners;
      this._FilterListeners = param0;
      this._postSet(71, _oldVal, param0);
   }

   public String getFlushBeforeQueries() {
      return this._FlushBeforeQueries;
   }

   public boolean isFlushBeforeQueriesInherited() {
      return false;
   }

   public boolean isFlushBeforeQueriesSet() {
      return this._isSet(72);
   }

   public void setFlushBeforeQueries(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"false", "true", "with-connection"};
      param0 = LegalChecks.checkInEnum("FlushBeforeQueries", param0, _set);
      String _oldVal = this._FlushBeforeQueries;
      this._FlushBeforeQueries = param0;
      this._postSet(72, _oldVal, param0);
   }

   public boolean getIgnoreChanges() {
      return this._IgnoreChanges;
   }

   public boolean isIgnoreChangesInherited() {
      return false;
   }

   public boolean isIgnoreChangesSet() {
      return this._isSet(73);
   }

   public void setIgnoreChanges(boolean param0) {
      boolean _oldVal = this._IgnoreChanges;
      this._IgnoreChanges = param0;
      this._postSet(73, _oldVal, param0);
   }

   public InverseManagerBean getInverseManager() {
      return this._InverseManager;
   }

   public boolean isInverseManagerInherited() {
      return false;
   }

   public boolean isInverseManagerSet() {
      return this._isSet(74);
   }

   public void setInverseManager(InverseManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInverseManager() != null && param0 != this.getInverseManager()) {
         throw new BeanAlreadyExistsException(this.getInverseManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 74)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InverseManagerBean _oldVal = this._InverseManager;
         this._InverseManager = param0;
         this._postSet(74, _oldVal, param0);
      }
   }

   public InverseManagerBean createInverseManager() {
      InverseManagerBeanImpl _val = new InverseManagerBeanImpl(this, -1);

      try {
         this.setInverseManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInverseManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._InverseManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInverseManager((InverseManagerBean)null);
               this._unSet(74);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JDBCListenersBean getJDBCListeners() {
      return this._JDBCListeners;
   }

   public boolean isJDBCListenersInherited() {
      return false;
   }

   public boolean isJDBCListenersSet() {
      return this._isSet(75) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCListeners());
   }

   public void setJDBCListeners(JDBCListenersBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 75)) {
         this._postCreate(_child);
      }

      JDBCListenersBean _oldVal = this._JDBCListeners;
      this._JDBCListeners = param0;
      this._postSet(75, _oldVal, param0);
   }

   public DefaultLockManagerBean getDefaultLockManager() {
      return this._DefaultLockManager;
   }

   public boolean isDefaultLockManagerInherited() {
      return false;
   }

   public boolean isDefaultLockManagerSet() {
      return this._isSet(76);
   }

   public void setDefaultLockManager(DefaultLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultLockManager() != null && param0 != this.getDefaultLockManager()) {
         throw new BeanAlreadyExistsException(this.getDefaultLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 76)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultLockManagerBean _oldVal = this._DefaultLockManager;
         this._DefaultLockManager = param0;
         this._postSet(76, _oldVal, param0);
      }
   }

   public DefaultLockManagerBean createDefaultLockManager() {
      DefaultLockManagerBeanImpl _val = new DefaultLockManagerBeanImpl(this, -1);

      try {
         this.setDefaultLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultLockManager((DefaultLockManagerBean)null);
               this._unSet(76);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PessimisticLockManagerBean getPessimisticLockManager() {
      return this._PessimisticLockManager;
   }

   public boolean isPessimisticLockManagerInherited() {
      return false;
   }

   public boolean isPessimisticLockManagerSet() {
      return this._isSet(77);
   }

   public void setPessimisticLockManager(PessimisticLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPessimisticLockManager() != null && param0 != this.getPessimisticLockManager()) {
         throw new BeanAlreadyExistsException(this.getPessimisticLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 77)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PessimisticLockManagerBean _oldVal = this._PessimisticLockManager;
         this._PessimisticLockManager = param0;
         this._postSet(77, _oldVal, param0);
      }
   }

   public PessimisticLockManagerBean createPessimisticLockManager() {
      PessimisticLockManagerBeanImpl _val = new PessimisticLockManagerBeanImpl(this, -1);

      try {
         this.setPessimisticLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPessimisticLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PessimisticLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPessimisticLockManager((PessimisticLockManagerBean)null);
               this._unSet(77);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NoneLockManagerBean getNoneLockManager() {
      return this._NoneLockManager;
   }

   public boolean isNoneLockManagerInherited() {
      return false;
   }

   public boolean isNoneLockManagerSet() {
      return this._isSet(78);
   }

   public void setNoneLockManager(NoneLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneLockManager() != null && param0 != this.getNoneLockManager()) {
         throw new BeanAlreadyExistsException(this.getNoneLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 78)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneLockManagerBean _oldVal = this._NoneLockManager;
         this._NoneLockManager = param0;
         this._postSet(78, _oldVal, param0);
      }
   }

   public NoneLockManagerBean createNoneLockManager() {
      NoneLockManagerBeanImpl _val = new NoneLockManagerBeanImpl(this, -1);

      try {
         this.setNoneLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneLockManager((NoneLockManagerBean)null);
               this._unSet(78);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SingleJVMExclusiveLockManagerBean getSingleJVMExclusiveLockManager() {
      return this._SingleJVMExclusiveLockManager;
   }

   public boolean isSingleJVMExclusiveLockManagerInherited() {
      return false;
   }

   public boolean isSingleJVMExclusiveLockManagerSet() {
      return this._isSet(79);
   }

   public void setSingleJVMExclusiveLockManager(SingleJVMExclusiveLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSingleJVMExclusiveLockManager() != null && param0 != this.getSingleJVMExclusiveLockManager()) {
         throw new BeanAlreadyExistsException(this.getSingleJVMExclusiveLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 79)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SingleJVMExclusiveLockManagerBean _oldVal = this._SingleJVMExclusiveLockManager;
         this._SingleJVMExclusiveLockManager = param0;
         this._postSet(79, _oldVal, param0);
      }
   }

   public SingleJVMExclusiveLockManagerBean createSingleJVMExclusiveLockManager() {
      SingleJVMExclusiveLockManagerBeanImpl _val = new SingleJVMExclusiveLockManagerBeanImpl(this, -1);

      try {
         this.setSingleJVMExclusiveLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySingleJVMExclusiveLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SingleJVMExclusiveLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSingleJVMExclusiveLockManager((SingleJVMExclusiveLockManagerBean)null);
               this._unSet(79);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public VersionLockManagerBean getVersionLockManager() {
      return this._VersionLockManager;
   }

   public boolean isVersionLockManagerInherited() {
      return false;
   }

   public boolean isVersionLockManagerSet() {
      return this._isSet(80);
   }

   public void setVersionLockManager(VersionLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getVersionLockManager() != null && param0 != this.getVersionLockManager()) {
         throw new BeanAlreadyExistsException(this.getVersionLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 80)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         VersionLockManagerBean _oldVal = this._VersionLockManager;
         this._VersionLockManager = param0;
         this._postSet(80, _oldVal, param0);
      }
   }

   public VersionLockManagerBean createVersionLockManager() {
      VersionLockManagerBeanImpl _val = new VersionLockManagerBeanImpl(this, -1);

      try {
         this.setVersionLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyVersionLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._VersionLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setVersionLockManager((VersionLockManagerBean)null);
               this._unSet(80);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomLockManagerBean getCustomLockManager() {
      return this._CustomLockManager;
   }

   public boolean isCustomLockManagerInherited() {
      return false;
   }

   public boolean isCustomLockManagerSet() {
      return this._isSet(81);
   }

   public void setCustomLockManager(CustomLockManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomLockManager() != null && param0 != this.getCustomLockManager()) {
         throw new BeanAlreadyExistsException(this.getCustomLockManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 81)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomLockManagerBean _oldVal = this._CustomLockManager;
         this._CustomLockManager = param0;
         this._postSet(81, _oldVal, param0);
      }
   }

   public CustomLockManagerBean createCustomLockManager() {
      CustomLockManagerBeanImpl _val = new CustomLockManagerBeanImpl(this, -1);

      try {
         this.setCustomLockManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomLockManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomLockManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomLockManager((CustomLockManagerBean)null);
               this._unSet(81);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public int getLockTimeout() {
      return this._LockTimeout;
   }

   public boolean isLockTimeoutInherited() {
      return false;
   }

   public boolean isLockTimeoutSet() {
      return this._isSet(82);
   }

   public void setLockTimeout(int param0) {
      int _oldVal = this._LockTimeout;
      this._LockTimeout = param0;
      this._postSet(82, _oldVal, param0);
   }

   public CommonsLogFactoryBean getCommonsLogFactory() {
      return this._CommonsLogFactory;
   }

   public boolean isCommonsLogFactoryInherited() {
      return false;
   }

   public boolean isCommonsLogFactorySet() {
      return this._isSet(83);
   }

   public void setCommonsLogFactory(CommonsLogFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCommonsLogFactory() != null && param0 != this.getCommonsLogFactory()) {
         throw new BeanAlreadyExistsException(this.getCommonsLogFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 83)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CommonsLogFactoryBean _oldVal = this._CommonsLogFactory;
         this._CommonsLogFactory = param0;
         this._postSet(83, _oldVal, param0);
      }
   }

   public CommonsLogFactoryBean createCommonsLogFactory() {
      CommonsLogFactoryBeanImpl _val = new CommonsLogFactoryBeanImpl(this, -1);

      try {
         this.setCommonsLogFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCommonsLogFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CommonsLogFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCommonsLogFactory((CommonsLogFactoryBean)null);
               this._unSet(83);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Log4JLogFactoryBean getLog4JLogFactory() {
      return this._Log4JLogFactory;
   }

   public boolean isLog4JLogFactoryInherited() {
      return false;
   }

   public boolean isLog4JLogFactorySet() {
      return this._isSet(84);
   }

   public void setLog4JLogFactory(Log4JLogFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLog4JLogFactory() != null && param0 != this.getLog4JLogFactory()) {
         throw new BeanAlreadyExistsException(this.getLog4JLogFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 84)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         Log4JLogFactoryBean _oldVal = this._Log4JLogFactory;
         this._Log4JLogFactory = param0;
         this._postSet(84, _oldVal, param0);
      }
   }

   public Log4JLogFactoryBean createLog4JLogFactory() {
      Log4JLogFactoryBeanImpl _val = new Log4JLogFactoryBeanImpl(this, -1);

      try {
         this.setLog4JLogFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLog4JLogFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Log4JLogFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLog4JLogFactory((Log4JLogFactoryBean)null);
               this._unSet(84);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LogFactoryImplBean getLogFactoryImpl() {
      return this._LogFactoryImpl;
   }

   public boolean isLogFactoryImplInherited() {
      return false;
   }

   public boolean isLogFactoryImplSet() {
      return this._isSet(85);
   }

   public void setLogFactoryImpl(LogFactoryImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLogFactoryImpl() != null && param0 != this.getLogFactoryImpl()) {
         throw new BeanAlreadyExistsException(this.getLogFactoryImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 85)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LogFactoryImplBean _oldVal = this._LogFactoryImpl;
         this._LogFactoryImpl = param0;
         this._postSet(85, _oldVal, param0);
      }
   }

   public LogFactoryImplBean createLogFactoryImpl() {
      LogFactoryImplBeanImpl _val = new LogFactoryImplBeanImpl(this, -1);

      try {
         this.setLogFactoryImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLogFactoryImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LogFactoryImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLogFactoryImpl((LogFactoryImplBean)null);
               this._unSet(85);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NoneLogFactoryBean getNoneLogFactory() {
      return this._NoneLogFactory;
   }

   public boolean isNoneLogFactoryInherited() {
      return false;
   }

   public boolean isNoneLogFactorySet() {
      return this._isSet(86);
   }

   public void setNoneLogFactory(NoneLogFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneLogFactory() != null && param0 != this.getNoneLogFactory()) {
         throw new BeanAlreadyExistsException(this.getNoneLogFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 86)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneLogFactoryBean _oldVal = this._NoneLogFactory;
         this._NoneLogFactory = param0;
         this._postSet(86, _oldVal, param0);
      }
   }

   public NoneLogFactoryBean createNoneLogFactory() {
      NoneLogFactoryBeanImpl _val = new NoneLogFactoryBeanImpl(this, -1);

      try {
         this.setNoneLogFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneLogFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneLogFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneLogFactory((NoneLogFactoryBean)null);
               this._unSet(86);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomLogBean getCustomLog() {
      return this._CustomLog;
   }

   public boolean isCustomLogInherited() {
      return false;
   }

   public boolean isCustomLogSet() {
      return this._isSet(87);
   }

   public void setCustomLog(CustomLogBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomLog() != null && param0 != this.getCustomLog()) {
         throw new BeanAlreadyExistsException(this.getCustomLog() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 87)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomLogBean _oldVal = this._CustomLog;
         this._CustomLog = param0;
         this._postSet(87, _oldVal, param0);
      }
   }

   public CustomLogBean createCustomLog() {
      CustomLogBeanImpl _val = new CustomLogBeanImpl(this, -1);

      try {
         this.setCustomLog(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomLog() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomLog;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomLog((CustomLogBean)null);
               this._unSet(87);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getLRSSize() {
      return this._LRSSize;
   }

   public boolean isLRSSizeInherited() {
      return false;
   }

   public boolean isLRSSizeSet() {
      return this._isSet(88);
   }

   public void setLRSSize(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"last", "query", "unknown"};
      param0 = LegalChecks.checkInEnum("LRSSize", param0, _set);
      String _oldVal = this._LRSSize;
      this._LRSSize = param0;
      this._postSet(88, _oldVal, param0);
   }

   public String getMapping() {
      return this._Mapping;
   }

   public boolean isMappingInherited() {
      return false;
   }

   public boolean isMappingSet() {
      return this._isSet(89);
   }

   public void setMapping(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Mapping;
      this._Mapping = param0;
      this._postSet(89, _oldVal, param0);
   }

   public DefaultMappingDefaultsBean getDefaultMappingDefaults() {
      return this._DefaultMappingDefaults;
   }

   public boolean isDefaultMappingDefaultsInherited() {
      return false;
   }

   public boolean isDefaultMappingDefaultsSet() {
      return this._isSet(90);
   }

   public void setDefaultMappingDefaults(DefaultMappingDefaultsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultMappingDefaults() != null && param0 != this.getDefaultMappingDefaults()) {
         throw new BeanAlreadyExistsException(this.getDefaultMappingDefaults() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 90)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultMappingDefaultsBean _oldVal = this._DefaultMappingDefaults;
         this._DefaultMappingDefaults = param0;
         this._postSet(90, _oldVal, param0);
      }
   }

   public DefaultMappingDefaultsBean createDefaultMappingDefaults() {
      DefaultMappingDefaultsBeanImpl _val = new DefaultMappingDefaultsBeanImpl(this, -1);

      try {
         this.setDefaultMappingDefaults(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultMappingDefaults() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultMappingDefaults;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultMappingDefaults((DefaultMappingDefaultsBean)null);
               this._unSet(90);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DeprecatedJDOMappingDefaultsBean getDeprecatedJDOMappingDefaults() {
      return this._DeprecatedJDOMappingDefaults;
   }

   public boolean isDeprecatedJDOMappingDefaultsInherited() {
      return false;
   }

   public boolean isDeprecatedJDOMappingDefaultsSet() {
      return this._isSet(91);
   }

   public void setDeprecatedJDOMappingDefaults(DeprecatedJDOMappingDefaultsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDeprecatedJDOMappingDefaults() != null && param0 != this.getDeprecatedJDOMappingDefaults()) {
         throw new BeanAlreadyExistsException(this.getDeprecatedJDOMappingDefaults() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 91)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DeprecatedJDOMappingDefaultsBean _oldVal = this._DeprecatedJDOMappingDefaults;
         this._DeprecatedJDOMappingDefaults = param0;
         this._postSet(91, _oldVal, param0);
      }
   }

   public DeprecatedJDOMappingDefaultsBean createDeprecatedJDOMappingDefaults() {
      DeprecatedJDOMappingDefaultsBeanImpl _val = new DeprecatedJDOMappingDefaultsBeanImpl(this, -1);

      try {
         this.setDeprecatedJDOMappingDefaults(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDeprecatedJDOMappingDefaults() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DeprecatedJDOMappingDefaults;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDeprecatedJDOMappingDefaults((DeprecatedJDOMappingDefaultsBean)null);
               this._unSet(91);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MappingDefaultsImplBean getMappingDefaultsImpl() {
      return this._MappingDefaultsImpl;
   }

   public boolean isMappingDefaultsImplInherited() {
      return false;
   }

   public boolean isMappingDefaultsImplSet() {
      return this._isSet(92);
   }

   public void setMappingDefaultsImpl(MappingDefaultsImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMappingDefaultsImpl() != null && param0 != this.getMappingDefaultsImpl()) {
         throw new BeanAlreadyExistsException(this.getMappingDefaultsImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 92)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MappingDefaultsImplBean _oldVal = this._MappingDefaultsImpl;
         this._MappingDefaultsImpl = param0;
         this._postSet(92, _oldVal, param0);
      }
   }

   public MappingDefaultsImplBean createMappingDefaultsImpl() {
      MappingDefaultsImplBeanImpl _val = new MappingDefaultsImplBeanImpl(this, -1);

      try {
         this.setMappingDefaultsImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMappingDefaultsImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MappingDefaultsImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMappingDefaultsImpl((MappingDefaultsImplBean)null);
               this._unSet(92);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PersistenceMappingDefaultsBean getPersistenceMappingDefaults() {
      return this._PersistenceMappingDefaults;
   }

   public boolean isPersistenceMappingDefaultsInherited() {
      return false;
   }

   public boolean isPersistenceMappingDefaultsSet() {
      return this._isSet(93);
   }

   public void setPersistenceMappingDefaults(PersistenceMappingDefaultsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPersistenceMappingDefaults() != null && param0 != this.getPersistenceMappingDefaults()) {
         throw new BeanAlreadyExistsException(this.getPersistenceMappingDefaults() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 93)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PersistenceMappingDefaultsBean _oldVal = this._PersistenceMappingDefaults;
         this._PersistenceMappingDefaults = param0;
         this._postSet(93, _oldVal, param0);
      }
   }

   public PersistenceMappingDefaultsBean createPersistenceMappingDefaults() {
      PersistenceMappingDefaultsBeanImpl _val = new PersistenceMappingDefaultsBeanImpl(this, -1);

      try {
         this.setPersistenceMappingDefaults(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceMappingDefaults() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PersistenceMappingDefaults;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPersistenceMappingDefaults((PersistenceMappingDefaultsBean)null);
               this._unSet(93);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomMappingDefaultsBean getCustomMappingDefaults() {
      return this._CustomMappingDefaults;
   }

   public boolean isCustomMappingDefaultsInherited() {
      return false;
   }

   public boolean isCustomMappingDefaultsSet() {
      return this._isSet(94);
   }

   public void setCustomMappingDefaults(CustomMappingDefaultsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomMappingDefaults() != null && param0 != this.getCustomMappingDefaults()) {
         throw new BeanAlreadyExistsException(this.getCustomMappingDefaults() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 94)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomMappingDefaultsBean _oldVal = this._CustomMappingDefaults;
         this._CustomMappingDefaults = param0;
         this._postSet(94, _oldVal, param0);
      }
   }

   public CustomMappingDefaultsBean createCustomMappingDefaults() {
      CustomMappingDefaultsBeanImpl _val = new CustomMappingDefaultsBeanImpl(this, -1);

      try {
         this.setCustomMappingDefaults(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomMappingDefaults() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomMappingDefaults;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomMappingDefaults((CustomMappingDefaultsBean)null);
               this._unSet(94);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ExtensionDeprecatedJDOMappingFactoryBean getExtensionDeprecatedJDOMappingFactory() {
      return this._ExtensionDeprecatedJDOMappingFactory;
   }

   public boolean isExtensionDeprecatedJDOMappingFactoryInherited() {
      return false;
   }

   public boolean isExtensionDeprecatedJDOMappingFactorySet() {
      return this._isSet(95);
   }

   public void setExtensionDeprecatedJDOMappingFactory(ExtensionDeprecatedJDOMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExtensionDeprecatedJDOMappingFactory() != null && param0 != this.getExtensionDeprecatedJDOMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getExtensionDeprecatedJDOMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 95)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ExtensionDeprecatedJDOMappingFactoryBean _oldVal = this._ExtensionDeprecatedJDOMappingFactory;
         this._ExtensionDeprecatedJDOMappingFactory = param0;
         this._postSet(95, _oldVal, param0);
      }
   }

   public ExtensionDeprecatedJDOMappingFactoryBean createExtensionDeprecatedJDOMappingFactory() {
      ExtensionDeprecatedJDOMappingFactoryBeanImpl _val = new ExtensionDeprecatedJDOMappingFactoryBeanImpl(this, -1);

      try {
         this.setExtensionDeprecatedJDOMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExtensionDeprecatedJDOMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExtensionDeprecatedJDOMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExtensionDeprecatedJDOMappingFactory((ExtensionDeprecatedJDOMappingFactoryBean)null);
               this._unSet(95);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoPersistenceMappingFactoryBean getKodoPersistenceMappingFactory() {
      return this._KodoPersistenceMappingFactory;
   }

   public boolean isKodoPersistenceMappingFactoryInherited() {
      return false;
   }

   public boolean isKodoPersistenceMappingFactorySet() {
      return this._isSet(96);
   }

   public void setKodoPersistenceMappingFactory(KodoPersistenceMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoPersistenceMappingFactory() != null && param0 != this.getKodoPersistenceMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getKodoPersistenceMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 96)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoPersistenceMappingFactoryBean _oldVal = this._KodoPersistenceMappingFactory;
         this._KodoPersistenceMappingFactory = param0;
         this._postSet(96, _oldVal, param0);
      }
   }

   public KodoPersistenceMappingFactoryBean createKodoPersistenceMappingFactory() {
      KodoPersistenceMappingFactoryBeanImpl _val = new KodoPersistenceMappingFactoryBeanImpl(this, -1);

      try {
         this.setKodoPersistenceMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoPersistenceMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoPersistenceMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoPersistenceMappingFactory((KodoPersistenceMappingFactoryBean)null);
               this._unSet(96);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MappingFileDeprecatedJDOMappingFactoryBean getMappingFileDeprecatedJDOMappingFactory() {
      return this._MappingFileDeprecatedJDOMappingFactory;
   }

   public boolean isMappingFileDeprecatedJDOMappingFactoryInherited() {
      return false;
   }

   public boolean isMappingFileDeprecatedJDOMappingFactorySet() {
      return this._isSet(97);
   }

   public void setMappingFileDeprecatedJDOMappingFactory(MappingFileDeprecatedJDOMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMappingFileDeprecatedJDOMappingFactory() != null && param0 != this.getMappingFileDeprecatedJDOMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getMappingFileDeprecatedJDOMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 97)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MappingFileDeprecatedJDOMappingFactoryBean _oldVal = this._MappingFileDeprecatedJDOMappingFactory;
         this._MappingFileDeprecatedJDOMappingFactory = param0;
         this._postSet(97, _oldVal, param0);
      }
   }

   public MappingFileDeprecatedJDOMappingFactoryBean createMappingFileDeprecatedJDOMappingFactory() {
      MappingFileDeprecatedJDOMappingFactoryBeanImpl _val = new MappingFileDeprecatedJDOMappingFactoryBeanImpl(this, -1);

      try {
         this.setMappingFileDeprecatedJDOMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMappingFileDeprecatedJDOMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MappingFileDeprecatedJDOMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMappingFileDeprecatedJDOMappingFactory((MappingFileDeprecatedJDOMappingFactoryBean)null);
               this._unSet(97);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ORMFileJDORMappingFactoryBean getORMFileJDORMappingFactory() {
      return this._ORMFileJDORMappingFactory;
   }

   public boolean isORMFileJDORMappingFactoryInherited() {
      return false;
   }

   public boolean isORMFileJDORMappingFactorySet() {
      return this._isSet(98);
   }

   public void setORMFileJDORMappingFactory(ORMFileJDORMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getORMFileJDORMappingFactory() != null && param0 != this.getORMFileJDORMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getORMFileJDORMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 98)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ORMFileJDORMappingFactoryBean _oldVal = this._ORMFileJDORMappingFactory;
         this._ORMFileJDORMappingFactory = param0;
         this._postSet(98, _oldVal, param0);
      }
   }

   public ORMFileJDORMappingFactoryBean createORMFileJDORMappingFactory() {
      ORMFileJDORMappingFactoryBeanImpl _val = new ORMFileJDORMappingFactoryBeanImpl(this, -1);

      try {
         this.setORMFileJDORMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyORMFileJDORMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ORMFileJDORMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setORMFileJDORMappingFactory((ORMFileJDORMappingFactoryBean)null);
               this._unSet(98);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TableDeprecatedJDOMappingFactoryBean getTableDeprecatedJDOMappingFactory() {
      return this._TableDeprecatedJDOMappingFactory;
   }

   public boolean isTableDeprecatedJDOMappingFactoryInherited() {
      return false;
   }

   public boolean isTableDeprecatedJDOMappingFactorySet() {
      return this._isSet(99);
   }

   public void setTableDeprecatedJDOMappingFactory(TableDeprecatedJDOMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTableDeprecatedJDOMappingFactory() != null && param0 != this.getTableDeprecatedJDOMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getTableDeprecatedJDOMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 99)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TableDeprecatedJDOMappingFactoryBean _oldVal = this._TableDeprecatedJDOMappingFactory;
         this._TableDeprecatedJDOMappingFactory = param0;
         this._postSet(99, _oldVal, param0);
      }
   }

   public TableDeprecatedJDOMappingFactoryBean createTableDeprecatedJDOMappingFactory() {
      TableDeprecatedJDOMappingFactoryBeanImpl _val = new TableDeprecatedJDOMappingFactoryBeanImpl(this, -1);

      try {
         this.setTableDeprecatedJDOMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableDeprecatedJDOMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TableDeprecatedJDOMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTableDeprecatedJDOMappingFactory((TableDeprecatedJDOMappingFactoryBean)null);
               this._unSet(99);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TableJDORMappingFactoryBean getTableJDORMappingFactory() {
      return this._TableJDORMappingFactory;
   }

   public boolean isTableJDORMappingFactoryInherited() {
      return false;
   }

   public boolean isTableJDORMappingFactorySet() {
      return this._isSet(100);
   }

   public void setTableJDORMappingFactory(TableJDORMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTableJDORMappingFactory() != null && param0 != this.getTableJDORMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getTableJDORMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 100)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TableJDORMappingFactoryBean _oldVal = this._TableJDORMappingFactory;
         this._TableJDORMappingFactory = param0;
         this._postSet(100, _oldVal, param0);
      }
   }

   public TableJDORMappingFactoryBean createTableJDORMappingFactory() {
      TableJDORMappingFactoryBeanImpl _val = new TableJDORMappingFactoryBeanImpl(this, -1);

      try {
         this.setTableJDORMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableJDORMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TableJDORMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTableJDORMappingFactory((TableJDORMappingFactoryBean)null);
               this._unSet(100);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomMappingFactoryBean getCustomMappingFactory() {
      return this._CustomMappingFactory;
   }

   public boolean isCustomMappingFactoryInherited() {
      return false;
   }

   public boolean isCustomMappingFactorySet() {
      return this._isSet(101);
   }

   public void setCustomMappingFactory(CustomMappingFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomMappingFactory() != null && param0 != this.getCustomMappingFactory()) {
         throw new BeanAlreadyExistsException(this.getCustomMappingFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 101)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomMappingFactoryBean _oldVal = this._CustomMappingFactory;
         this._CustomMappingFactory = param0;
         this._postSet(101, _oldVal, param0);
      }
   }

   public CustomMappingFactoryBean createCustomMappingFactory() {
      CustomMappingFactoryBeanImpl _val = new CustomMappingFactoryBeanImpl(this, -1);

      try {
         this.setCustomMappingFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomMappingFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomMappingFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomMappingFactory((CustomMappingFactoryBean)null);
               this._unSet(101);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultMetaDataFactoryBean getDefaultMetaDataFactory() {
      return this._DefaultMetaDataFactory;
   }

   public boolean isDefaultMetaDataFactoryInherited() {
      return false;
   }

   public boolean isDefaultMetaDataFactorySet() {
      return this._isSet(102);
   }

   public void setDefaultMetaDataFactory(DefaultMetaDataFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultMetaDataFactory() != null && param0 != this.getDefaultMetaDataFactory()) {
         throw new BeanAlreadyExistsException(this.getDefaultMetaDataFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 102)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultMetaDataFactoryBean _oldVal = this._DefaultMetaDataFactory;
         this._DefaultMetaDataFactory = param0;
         this._postSet(102, _oldVal, param0);
      }
   }

   public DefaultMetaDataFactoryBean createDefaultMetaDataFactory() {
      DefaultMetaDataFactoryBeanImpl _val = new DefaultMetaDataFactoryBeanImpl(this, -1);

      try {
         this.setDefaultMetaDataFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultMetaDataFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultMetaDataFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultMetaDataFactory((DefaultMetaDataFactoryBean)null);
               this._unSet(102);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JDOMetaDataFactoryBean getJDOMetaDataFactory() {
      return this._JDOMetaDataFactory;
   }

   public boolean isJDOMetaDataFactoryInherited() {
      return false;
   }

   public boolean isJDOMetaDataFactorySet() {
      return this._isSet(103);
   }

   public void setJDOMetaDataFactory(JDOMetaDataFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJDOMetaDataFactory() != null && param0 != this.getJDOMetaDataFactory()) {
         throw new BeanAlreadyExistsException(this.getJDOMetaDataFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 103)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JDOMetaDataFactoryBean _oldVal = this._JDOMetaDataFactory;
         this._JDOMetaDataFactory = param0;
         this._postSet(103, _oldVal, param0);
      }
   }

   public JDOMetaDataFactoryBean createJDOMetaDataFactory() {
      JDOMetaDataFactoryBeanImpl _val = new JDOMetaDataFactoryBeanImpl(this, -1);

      try {
         this.setJDOMetaDataFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJDOMetaDataFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JDOMetaDataFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDOMetaDataFactory((JDOMetaDataFactoryBean)null);
               this._unSet(103);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DeprecatedJDOMetaDataFactoryBean getDeprecatedJDOMetaDataFactory() {
      return this._DeprecatedJDOMetaDataFactory;
   }

   public boolean isDeprecatedJDOMetaDataFactoryInherited() {
      return false;
   }

   public boolean isDeprecatedJDOMetaDataFactorySet() {
      return this._isSet(104);
   }

   public void setDeprecatedJDOMetaDataFactory(DeprecatedJDOMetaDataFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDeprecatedJDOMetaDataFactory() != null && param0 != this.getDeprecatedJDOMetaDataFactory()) {
         throw new BeanAlreadyExistsException(this.getDeprecatedJDOMetaDataFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 104)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DeprecatedJDOMetaDataFactoryBean _oldVal = this._DeprecatedJDOMetaDataFactory;
         this._DeprecatedJDOMetaDataFactory = param0;
         this._postSet(104, _oldVal, param0);
      }
   }

   public DeprecatedJDOMetaDataFactoryBean createDeprecatedJDOMetaDataFactory() {
      DeprecatedJDOMetaDataFactoryBeanImpl _val = new DeprecatedJDOMetaDataFactoryBeanImpl(this, -1);

      try {
         this.setDeprecatedJDOMetaDataFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDeprecatedJDOMetaDataFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DeprecatedJDOMetaDataFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDeprecatedJDOMetaDataFactory((DeprecatedJDOMetaDataFactoryBean)null);
               this._unSet(104);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoPersistenceMetaDataFactoryBean getKodoPersistenceMetaDataFactory() {
      return this._KodoPersistenceMetaDataFactory;
   }

   public boolean isKodoPersistenceMetaDataFactoryInherited() {
      return false;
   }

   public boolean isKodoPersistenceMetaDataFactorySet() {
      return this._isSet(105);
   }

   public void setKodoPersistenceMetaDataFactory(KodoPersistenceMetaDataFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoPersistenceMetaDataFactory() != null && param0 != this.getKodoPersistenceMetaDataFactory()) {
         throw new BeanAlreadyExistsException(this.getKodoPersistenceMetaDataFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 105)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoPersistenceMetaDataFactoryBean _oldVal = this._KodoPersistenceMetaDataFactory;
         this._KodoPersistenceMetaDataFactory = param0;
         this._postSet(105, _oldVal, param0);
      }
   }

   public KodoPersistenceMetaDataFactoryBean createKodoPersistenceMetaDataFactory() {
      KodoPersistenceMetaDataFactoryBeanImpl _val = new KodoPersistenceMetaDataFactoryBeanImpl(this, -1);

      try {
         this.setKodoPersistenceMetaDataFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoPersistenceMetaDataFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoPersistenceMetaDataFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoPersistenceMetaDataFactory((KodoPersistenceMetaDataFactoryBean)null);
               this._unSet(105);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomMetaDataFactoryBean getCustomMetaDataFactory() {
      return this._CustomMetaDataFactory;
   }

   public boolean isCustomMetaDataFactoryInherited() {
      return false;
   }

   public boolean isCustomMetaDataFactorySet() {
      return this._isSet(106);
   }

   public void setCustomMetaDataFactory(CustomMetaDataFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomMetaDataFactory() != null && param0 != this.getCustomMetaDataFactory()) {
         throw new BeanAlreadyExistsException(this.getCustomMetaDataFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 106)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomMetaDataFactoryBean _oldVal = this._CustomMetaDataFactory;
         this._CustomMetaDataFactory = param0;
         this._postSet(106, _oldVal, param0);
      }
   }

   public CustomMetaDataFactoryBean createCustomMetaDataFactory() {
      CustomMetaDataFactoryBeanImpl _val = new CustomMetaDataFactoryBeanImpl(this, -1);

      try {
         this.setCustomMetaDataFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomMetaDataFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomMetaDataFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomMetaDataFactory((CustomMetaDataFactoryBean)null);
               this._unSet(106);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultMetaDataRepositoryBean getDefaultMetaDataRepository() {
      return this._DefaultMetaDataRepository;
   }

   public boolean isDefaultMetaDataRepositoryInherited() {
      return false;
   }

   public boolean isDefaultMetaDataRepositorySet() {
      return this._isSet(107);
   }

   public void setDefaultMetaDataRepository(DefaultMetaDataRepositoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultMetaDataRepository() != null && param0 != this.getDefaultMetaDataRepository()) {
         throw new BeanAlreadyExistsException(this.getDefaultMetaDataRepository() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 107)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultMetaDataRepositoryBean _oldVal = this._DefaultMetaDataRepository;
         this._DefaultMetaDataRepository = param0;
         this._postSet(107, _oldVal, param0);
      }
   }

   public DefaultMetaDataRepositoryBean createDefaultMetaDataRepository() {
      DefaultMetaDataRepositoryBeanImpl _val = new DefaultMetaDataRepositoryBeanImpl(this, -1);

      try {
         this.setDefaultMetaDataRepository(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultMetaDataRepository() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultMetaDataRepository;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultMetaDataRepository((DefaultMetaDataRepositoryBean)null);
               this._unSet(107);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoMappingRepositoryBean getKodoMappingRepository() {
      return this._KodoMappingRepository;
   }

   public boolean isKodoMappingRepositoryInherited() {
      return false;
   }

   public boolean isKodoMappingRepositorySet() {
      return this._isSet(108);
   }

   public void setKodoMappingRepository(KodoMappingRepositoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoMappingRepository() != null && param0 != this.getKodoMappingRepository()) {
         throw new BeanAlreadyExistsException(this.getKodoMappingRepository() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 108)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoMappingRepositoryBean _oldVal = this._KodoMappingRepository;
         this._KodoMappingRepository = param0;
         this._postSet(108, _oldVal, param0);
      }
   }

   public KodoMappingRepositoryBean createKodoMappingRepository() {
      KodoMappingRepositoryBeanImpl _val = new KodoMappingRepositoryBeanImpl(this, -1);

      try {
         this.setKodoMappingRepository(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoMappingRepository() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoMappingRepository;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoMappingRepository((KodoMappingRepositoryBean)null);
               this._unSet(108);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomMetaDataRepositoryBean getCustomMetaDataRepository() {
      return this._CustomMetaDataRepository;
   }

   public boolean isCustomMetaDataRepositoryInherited() {
      return false;
   }

   public boolean isCustomMetaDataRepositorySet() {
      return this._isSet(109);
   }

   public void setCustomMetaDataRepository(CustomMetaDataRepositoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomMetaDataRepository() != null && param0 != this.getCustomMetaDataRepository()) {
         throw new BeanAlreadyExistsException(this.getCustomMetaDataRepository() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 109)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomMetaDataRepositoryBean _oldVal = this._CustomMetaDataRepository;
         this._CustomMetaDataRepository = param0;
         this._postSet(109, _oldVal, param0);
      }
   }

   public CustomMetaDataRepositoryBean createCustomMetaDataRepository() {
      CustomMetaDataRepositoryBeanImpl _val = new CustomMetaDataRepositoryBeanImpl(this, -1);

      try {
         this.setCustomMetaDataRepository(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomMetaDataRepository() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomMetaDataRepository;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomMetaDataRepository((CustomMetaDataRepositoryBean)null);
               this._unSet(109);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean getMultithreaded() {
      return this._Multithreaded;
   }

   public boolean isMultithreadedInherited() {
      return false;
   }

   public boolean isMultithreadedSet() {
      return this._isSet(110);
   }

   public void setMultithreaded(boolean param0) {
      boolean _oldVal = this._Multithreaded;
      this._Multithreaded = param0;
      this._postSet(110, _oldVal, param0);
   }

   public boolean getNontransactionalRead() {
      return this._NontransactionalRead;
   }

   public boolean isNontransactionalReadInherited() {
      return false;
   }

   public boolean isNontransactionalReadSet() {
      return this._isSet(111);
   }

   public void setNontransactionalRead(boolean param0) {
      boolean _oldVal = this._NontransactionalRead;
      this._NontransactionalRead = param0;
      this._postSet(111, _oldVal, param0);
   }

   public boolean getNontransactionalWrite() {
      return this._NontransactionalWrite;
   }

   public boolean isNontransactionalWriteInherited() {
      return false;
   }

   public boolean isNontransactionalWriteSet() {
      return this._isSet(112);
   }

   public void setNontransactionalWrite(boolean param0) {
      boolean _oldVal = this._NontransactionalWrite;
      this._NontransactionalWrite = param0;
      this._postSet(112, _oldVal, param0);
   }

   public boolean getOptimistic() {
      return this._Optimistic;
   }

   public boolean isOptimisticInherited() {
      return false;
   }

   public boolean isOptimisticSet() {
      return this._isSet(113);
   }

   public void setOptimistic(boolean param0) {
      boolean _oldVal = this._Optimistic;
      this._Optimistic = param0;
      this._postSet(113, _oldVal, param0);
   }

   public DefaultOrphanedKeyActionBean getDefaultOrphanedKeyAction() {
      return this._DefaultOrphanedKeyAction;
   }

   public boolean isDefaultOrphanedKeyActionInherited() {
      return false;
   }

   public boolean isDefaultOrphanedKeyActionSet() {
      return this._isSet(114);
   }

   public void setDefaultOrphanedKeyAction(DefaultOrphanedKeyActionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultOrphanedKeyAction() != null && param0 != this.getDefaultOrphanedKeyAction()) {
         throw new BeanAlreadyExistsException(this.getDefaultOrphanedKeyAction() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 114)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultOrphanedKeyActionBean _oldVal = this._DefaultOrphanedKeyAction;
         this._DefaultOrphanedKeyAction = param0;
         this._postSet(114, _oldVal, param0);
      }
   }

   public DefaultOrphanedKeyActionBean createDefaultOrphanedKeyAction() {
      DefaultOrphanedKeyActionBeanImpl _val = new DefaultOrphanedKeyActionBeanImpl(this, -1);

      try {
         this.setDefaultOrphanedKeyAction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultOrphanedKeyAction() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultOrphanedKeyAction;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultOrphanedKeyAction((DefaultOrphanedKeyActionBean)null);
               this._unSet(114);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LogOrphanedKeyActionBean getLogOrphanedKeyAction() {
      return this._LogOrphanedKeyAction;
   }

   public boolean isLogOrphanedKeyActionInherited() {
      return false;
   }

   public boolean isLogOrphanedKeyActionSet() {
      return this._isSet(115);
   }

   public void setLogOrphanedKeyAction(LogOrphanedKeyActionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLogOrphanedKeyAction() != null && param0 != this.getLogOrphanedKeyAction()) {
         throw new BeanAlreadyExistsException(this.getLogOrphanedKeyAction() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 115)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LogOrphanedKeyActionBean _oldVal = this._LogOrphanedKeyAction;
         this._LogOrphanedKeyAction = param0;
         this._postSet(115, _oldVal, param0);
      }
   }

   public LogOrphanedKeyActionBean createLogOrphanedKeyAction() {
      LogOrphanedKeyActionBeanImpl _val = new LogOrphanedKeyActionBeanImpl(this, -1);

      try {
         this.setLogOrphanedKeyAction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLogOrphanedKeyAction() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LogOrphanedKeyAction;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLogOrphanedKeyAction((LogOrphanedKeyActionBean)null);
               this._unSet(115);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ExceptionOrphanedKeyActionBean getExceptionOrphanedKeyAction() {
      return this._ExceptionOrphanedKeyAction;
   }

   public boolean isExceptionOrphanedKeyActionInherited() {
      return false;
   }

   public boolean isExceptionOrphanedKeyActionSet() {
      return this._isSet(116);
   }

   public void setExceptionOrphanedKeyAction(ExceptionOrphanedKeyActionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExceptionOrphanedKeyAction() != null && param0 != this.getExceptionOrphanedKeyAction()) {
         throw new BeanAlreadyExistsException(this.getExceptionOrphanedKeyAction() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 116)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ExceptionOrphanedKeyActionBean _oldVal = this._ExceptionOrphanedKeyAction;
         this._ExceptionOrphanedKeyAction = param0;
         this._postSet(116, _oldVal, param0);
      }
   }

   public ExceptionOrphanedKeyActionBean createExceptionOrphanedKeyAction() {
      ExceptionOrphanedKeyActionBeanImpl _val = new ExceptionOrphanedKeyActionBeanImpl(this, -1);

      try {
         this.setExceptionOrphanedKeyAction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExceptionOrphanedKeyAction() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExceptionOrphanedKeyAction;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExceptionOrphanedKeyAction((ExceptionOrphanedKeyActionBean)null);
               this._unSet(116);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NoneOrphanedKeyActionBean getNoneOrphanedKeyAction() {
      return this._NoneOrphanedKeyAction;
   }

   public boolean isNoneOrphanedKeyActionInherited() {
      return false;
   }

   public boolean isNoneOrphanedKeyActionSet() {
      return this._isSet(117);
   }

   public void setNoneOrphanedKeyAction(NoneOrphanedKeyActionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneOrphanedKeyAction() != null && param0 != this.getNoneOrphanedKeyAction()) {
         throw new BeanAlreadyExistsException(this.getNoneOrphanedKeyAction() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 117)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneOrphanedKeyActionBean _oldVal = this._NoneOrphanedKeyAction;
         this._NoneOrphanedKeyAction = param0;
         this._postSet(117, _oldVal, param0);
      }
   }

   public NoneOrphanedKeyActionBean createNoneOrphanedKeyAction() {
      NoneOrphanedKeyActionBeanImpl _val = new NoneOrphanedKeyActionBeanImpl(this, -1);

      try {
         this.setNoneOrphanedKeyAction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneOrphanedKeyAction() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneOrphanedKeyAction;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneOrphanedKeyAction((NoneOrphanedKeyActionBean)null);
               this._unSet(117);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomOrphanedKeyActionBean getCustomOrphanedKeyAction() {
      return this._CustomOrphanedKeyAction;
   }

   public boolean isCustomOrphanedKeyActionInherited() {
      return false;
   }

   public boolean isCustomOrphanedKeyActionSet() {
      return this._isSet(118);
   }

   public void setCustomOrphanedKeyAction(CustomOrphanedKeyActionBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomOrphanedKeyAction() != null && param0 != this.getCustomOrphanedKeyAction()) {
         throw new BeanAlreadyExistsException(this.getCustomOrphanedKeyAction() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 118)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomOrphanedKeyActionBean _oldVal = this._CustomOrphanedKeyAction;
         this._CustomOrphanedKeyAction = param0;
         this._postSet(118, _oldVal, param0);
      }
   }

   public CustomOrphanedKeyActionBean createCustomOrphanedKeyAction() {
      CustomOrphanedKeyActionBeanImpl _val = new CustomOrphanedKeyActionBeanImpl(this, -1);

      try {
         this.setCustomOrphanedKeyAction(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomOrphanedKeyAction() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomOrphanedKeyAction;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomOrphanedKeyAction((CustomOrphanedKeyActionBean)null);
               this._unSet(118);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public HTTPTransportBean getHTTPTransport() {
      return this._HTTPTransport;
   }

   public boolean isHTTPTransportInherited() {
      return false;
   }

   public boolean isHTTPTransportSet() {
      return this._isSet(119);
   }

   public void setHTTPTransport(HTTPTransportBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getHTTPTransport() != null && param0 != this.getHTTPTransport()) {
         throw new BeanAlreadyExistsException(this.getHTTPTransport() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 119)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         HTTPTransportBean _oldVal = this._HTTPTransport;
         this._HTTPTransport = param0;
         this._postSet(119, _oldVal, param0);
      }
   }

   public HTTPTransportBean createHTTPTransport() {
      HTTPTransportBeanImpl _val = new HTTPTransportBeanImpl(this, -1);

      try {
         this.setHTTPTransport(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyHTTPTransport() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._HTTPTransport;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHTTPTransport((HTTPTransportBean)null);
               this._unSet(119);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TCPTransportBean getTCPTransport() {
      return this._TCPTransport;
   }

   public boolean isTCPTransportInherited() {
      return false;
   }

   public boolean isTCPTransportSet() {
      return this._isSet(120);
   }

   public void setTCPTransport(TCPTransportBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTCPTransport() != null && param0 != this.getTCPTransport()) {
         throw new BeanAlreadyExistsException(this.getTCPTransport() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 120)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TCPTransportBean _oldVal = this._TCPTransport;
         this._TCPTransport = param0;
         this._postSet(120, _oldVal, param0);
      }
   }

   public TCPTransportBean createTCPTransport() {
      TCPTransportBeanImpl _val = new TCPTransportBeanImpl(this, -1);

      try {
         this.setTCPTransport(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTCPTransport() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TCPTransport;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTCPTransport((TCPTransportBean)null);
               this._unSet(120);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomPersistenceServerBean getCustomPersistenceServer() {
      return this._CustomPersistenceServer;
   }

   public boolean isCustomPersistenceServerInherited() {
      return false;
   }

   public boolean isCustomPersistenceServerSet() {
      return this._isSet(121);
   }

   public void setCustomPersistenceServer(CustomPersistenceServerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomPersistenceServer() != null && param0 != this.getCustomPersistenceServer()) {
         throw new BeanAlreadyExistsException(this.getCustomPersistenceServer() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 121)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomPersistenceServerBean _oldVal = this._CustomPersistenceServer;
         this._CustomPersistenceServer = param0;
         this._postSet(121, _oldVal, param0);
      }
   }

   public CustomPersistenceServerBean createCustomPersistenceServer() {
      CustomPersistenceServerBeanImpl _val = new CustomPersistenceServerBeanImpl(this, -1);

      try {
         this.setCustomPersistenceServer(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomPersistenceServer() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomPersistenceServer;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomPersistenceServer((CustomPersistenceServerBean)null);
               this._unSet(121);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultProxyManagerBean getDefaultProxyManager() {
      return this._DefaultProxyManager;
   }

   public boolean isDefaultProxyManagerInherited() {
      return false;
   }

   public boolean isDefaultProxyManagerSet() {
      return this._isSet(122);
   }

   public void setDefaultProxyManager(DefaultProxyManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultProxyManager() != null && param0 != this.getDefaultProxyManager()) {
         throw new BeanAlreadyExistsException(this.getDefaultProxyManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 122)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultProxyManagerBean _oldVal = this._DefaultProxyManager;
         this._DefaultProxyManager = param0;
         this._postSet(122, _oldVal, param0);
      }
   }

   public DefaultProxyManagerBean createDefaultProxyManager() {
      DefaultProxyManagerBeanImpl _val = new DefaultProxyManagerBeanImpl(this, -1);

      try {
         this.setDefaultProxyManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultProxyManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultProxyManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultProxyManager((DefaultProxyManagerBean)null);
               this._unSet(122);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ProfilingProxyManagerBean getProfilingProxyManager() {
      return this._ProfilingProxyManager;
   }

   public boolean isProfilingProxyManagerInherited() {
      return false;
   }

   public boolean isProfilingProxyManagerSet() {
      return this._isSet(123);
   }

   public void setProfilingProxyManager(ProfilingProxyManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getProfilingProxyManager() != null && param0 != this.getProfilingProxyManager()) {
         throw new BeanAlreadyExistsException(this.getProfilingProxyManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 123)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ProfilingProxyManagerBean _oldVal = this._ProfilingProxyManager;
         this._ProfilingProxyManager = param0;
         this._postSet(123, _oldVal, param0);
      }
   }

   public ProfilingProxyManagerBean createProfilingProxyManager() {
      ProfilingProxyManagerBeanImpl _val = new ProfilingProxyManagerBeanImpl(this, -1);

      try {
         this.setProfilingProxyManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProfilingProxyManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ProfilingProxyManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setProfilingProxyManager((ProfilingProxyManagerBean)null);
               this._unSet(123);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ProxyManagerImplBean getProxyManagerImpl() {
      return this._ProxyManagerImpl;
   }

   public boolean isProxyManagerImplInherited() {
      return false;
   }

   public boolean isProxyManagerImplSet() {
      return this._isSet(124);
   }

   public void setProxyManagerImpl(ProxyManagerImplBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getProxyManagerImpl() != null && param0 != this.getProxyManagerImpl()) {
         throw new BeanAlreadyExistsException(this.getProxyManagerImpl() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 124)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ProxyManagerImplBean _oldVal = this._ProxyManagerImpl;
         this._ProxyManagerImpl = param0;
         this._postSet(124, _oldVal, param0);
      }
   }

   public ProxyManagerImplBean createProxyManagerImpl() {
      ProxyManagerImplBeanImpl _val = new ProxyManagerImplBeanImpl(this, -1);

      try {
         this.setProxyManagerImpl(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProxyManagerImpl() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ProxyManagerImpl;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setProxyManagerImpl((ProxyManagerImplBean)null);
               this._unSet(124);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomProxyManagerBean getCustomProxyManager() {
      return this._CustomProxyManager;
   }

   public boolean isCustomProxyManagerInherited() {
      return false;
   }

   public boolean isCustomProxyManagerSet() {
      return this._isSet(125);
   }

   public void setCustomProxyManager(CustomProxyManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomProxyManager() != null && param0 != this.getCustomProxyManager()) {
         throw new BeanAlreadyExistsException(this.getCustomProxyManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 125)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomProxyManagerBean _oldVal = this._CustomProxyManager;
         this._CustomProxyManager = param0;
         this._postSet(125, _oldVal, param0);
      }
   }

   public CustomProxyManagerBean createCustomProxyManager() {
      CustomProxyManagerBeanImpl _val = new CustomProxyManagerBeanImpl(this, -1);

      try {
         this.setCustomProxyManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomProxyManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomProxyManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomProxyManager((CustomProxyManagerBean)null);
               this._unSet(125);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public QueryCachesBean getQueryCaches() {
      return this._QueryCaches;
   }

   public boolean isQueryCachesInherited() {
      return false;
   }

   public boolean isQueryCachesSet() {
      return this._isSet(126) || this._isAnythingSet((AbstractDescriptorBean)this.getQueryCaches());
   }

   public void setQueryCaches(QueryCachesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 126)) {
         this._postCreate(_child);
      }

      QueryCachesBean _oldVal = this._QueryCaches;
      this._QueryCaches = param0;
      this._postSet(126, _oldVal, param0);
   }

   public DefaultQueryCompilationCacheBean getDefaultQueryCompilationCache() {
      return this._DefaultQueryCompilationCache;
   }

   public boolean isDefaultQueryCompilationCacheInherited() {
      return false;
   }

   public boolean isDefaultQueryCompilationCacheSet() {
      return this._isSet(127);
   }

   public void setDefaultQueryCompilationCache(DefaultQueryCompilationCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultQueryCompilationCache() != null && param0 != this.getDefaultQueryCompilationCache()) {
         throw new BeanAlreadyExistsException(this.getDefaultQueryCompilationCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 127)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultQueryCompilationCacheBean _oldVal = this._DefaultQueryCompilationCache;
         this._DefaultQueryCompilationCache = param0;
         this._postSet(127, _oldVal, param0);
      }
   }

   public DefaultQueryCompilationCacheBean createDefaultQueryCompilationCache() {
      DefaultQueryCompilationCacheBeanImpl _val = new DefaultQueryCompilationCacheBeanImpl(this, -1);

      try {
         this.setDefaultQueryCompilationCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultQueryCompilationCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultQueryCompilationCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultQueryCompilationCache((DefaultQueryCompilationCacheBean)null);
               this._unSet(127);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CacheMapBean getCacheMap() {
      return this._CacheMap;
   }

   public boolean isCacheMapInherited() {
      return false;
   }

   public boolean isCacheMapSet() {
      return this._isSet(128);
   }

   public void setCacheMap(CacheMapBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCacheMap() != null && param0 != this.getCacheMap()) {
         throw new BeanAlreadyExistsException(this.getCacheMap() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 128)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CacheMapBean _oldVal = this._CacheMap;
         this._CacheMap = param0;
         this._postSet(128, _oldVal, param0);
      }
   }

   public CacheMapBean createCacheMap() {
      CacheMapBeanImpl _val = new CacheMapBeanImpl(this, -1);

      try {
         this.setCacheMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCacheMap() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CacheMap;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCacheMap((CacheMapBean)null);
               this._unSet(128);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ConcurrentHashMapBean getConcurrentHashMap() {
      return this._ConcurrentHashMap;
   }

   public boolean isConcurrentHashMapInherited() {
      return false;
   }

   public boolean isConcurrentHashMapSet() {
      return this._isSet(129);
   }

   public void setConcurrentHashMap(ConcurrentHashMapBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConcurrentHashMap() != null && param0 != this.getConcurrentHashMap()) {
         throw new BeanAlreadyExistsException(this.getConcurrentHashMap() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 129)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConcurrentHashMapBean _oldVal = this._ConcurrentHashMap;
         this._ConcurrentHashMap = param0;
         this._postSet(129, _oldVal, param0);
      }
   }

   public ConcurrentHashMapBean createConcurrentHashMap() {
      ConcurrentHashMapBeanImpl _val = new ConcurrentHashMapBeanImpl(this, -1);

      try {
         this.setConcurrentHashMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConcurrentHashMap() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConcurrentHashMap;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConcurrentHashMap((ConcurrentHashMapBean)null);
               this._unSet(129);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomQueryCompilationCacheBean getCustomQueryCompilationCache() {
      return this._CustomQueryCompilationCache;
   }

   public boolean isCustomQueryCompilationCacheInherited() {
      return false;
   }

   public boolean isCustomQueryCompilationCacheSet() {
      return this._isSet(130);
   }

   public void setCustomQueryCompilationCache(CustomQueryCompilationCacheBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomQueryCompilationCache() != null && param0 != this.getCustomQueryCompilationCache()) {
         throw new BeanAlreadyExistsException(this.getCustomQueryCompilationCache() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 130)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomQueryCompilationCacheBean _oldVal = this._CustomQueryCompilationCache;
         this._CustomQueryCompilationCache = param0;
         this._postSet(130, _oldVal, param0);
      }
   }

   public CustomQueryCompilationCacheBean createCustomQueryCompilationCache() {
      CustomQueryCompilationCacheBeanImpl _val = new CustomQueryCompilationCacheBeanImpl(this, -1);

      try {
         this.setCustomQueryCompilationCache(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomQueryCompilationCache() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomQueryCompilationCache;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomQueryCompilationCache((CustomQueryCompilationCacheBean)null);
               this._unSet(130);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getReadLockLevel() {
      return this._ReadLockLevel;
   }

   public boolean isReadLockLevelInherited() {
      return false;
   }

   public boolean isReadLockLevelSet() {
      return this._isSet(131);
   }

   public void setReadLockLevel(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "read", "write"};
      param0 = LegalChecks.checkInEnum("ReadLockLevel", param0, _set);
      String _oldVal = this._ReadLockLevel;
      this._ReadLockLevel = param0;
      this._postSet(131, _oldVal, param0);
   }

   public JMSRemoteCommitProviderBean getJMSRemoteCommitProvider() {
      return this._JMSRemoteCommitProvider;
   }

   public boolean isJMSRemoteCommitProviderInherited() {
      return false;
   }

   public boolean isJMSRemoteCommitProviderSet() {
      return this._isSet(132);
   }

   public void setJMSRemoteCommitProvider(JMSRemoteCommitProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJMSRemoteCommitProvider() != null && param0 != this.getJMSRemoteCommitProvider()) {
         throw new BeanAlreadyExistsException(this.getJMSRemoteCommitProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 132)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JMSRemoteCommitProviderBean _oldVal = this._JMSRemoteCommitProvider;
         this._JMSRemoteCommitProvider = param0;
         this._postSet(132, _oldVal, param0);
      }
   }

   public JMSRemoteCommitProviderBean createJMSRemoteCommitProvider() {
      JMSRemoteCommitProviderBeanImpl _val = new JMSRemoteCommitProviderBeanImpl(this, -1);

      try {
         this.setJMSRemoteCommitProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJMSRemoteCommitProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JMSRemoteCommitProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMSRemoteCommitProvider((JMSRemoteCommitProviderBean)null);
               this._unSet(132);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SingleJVMRemoteCommitProviderBean getSingleJVMRemoteCommitProvider() {
      return this._SingleJVMRemoteCommitProvider;
   }

   public boolean isSingleJVMRemoteCommitProviderInherited() {
      return false;
   }

   public boolean isSingleJVMRemoteCommitProviderSet() {
      return this._isSet(133);
   }

   public void setSingleJVMRemoteCommitProvider(SingleJVMRemoteCommitProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSingleJVMRemoteCommitProvider() != null && param0 != this.getSingleJVMRemoteCommitProvider()) {
         throw new BeanAlreadyExistsException(this.getSingleJVMRemoteCommitProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 133)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SingleJVMRemoteCommitProviderBean _oldVal = this._SingleJVMRemoteCommitProvider;
         this._SingleJVMRemoteCommitProvider = param0;
         this._postSet(133, _oldVal, param0);
      }
   }

   public SingleJVMRemoteCommitProviderBean createSingleJVMRemoteCommitProvider() {
      SingleJVMRemoteCommitProviderBeanImpl _val = new SingleJVMRemoteCommitProviderBeanImpl(this, -1);

      try {
         this.setSingleJVMRemoteCommitProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySingleJVMRemoteCommitProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SingleJVMRemoteCommitProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSingleJVMRemoteCommitProvider((SingleJVMRemoteCommitProviderBean)null);
               this._unSet(133);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TCPRemoteCommitProviderBean getTCPRemoteCommitProvider() {
      return this._TCPRemoteCommitProvider;
   }

   public boolean isTCPRemoteCommitProviderInherited() {
      return false;
   }

   public boolean isTCPRemoteCommitProviderSet() {
      return this._isSet(134);
   }

   public void setTCPRemoteCommitProvider(TCPRemoteCommitProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTCPRemoteCommitProvider() != null && param0 != this.getTCPRemoteCommitProvider()) {
         throw new BeanAlreadyExistsException(this.getTCPRemoteCommitProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 134)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TCPRemoteCommitProviderBean _oldVal = this._TCPRemoteCommitProvider;
         this._TCPRemoteCommitProvider = param0;
         this._postSet(134, _oldVal, param0);
      }
   }

   public TCPRemoteCommitProviderBean createTCPRemoteCommitProvider() {
      TCPRemoteCommitProviderBeanImpl _val = new TCPRemoteCommitProviderBeanImpl(this, -1);

      try {
         this.setTCPRemoteCommitProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTCPRemoteCommitProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TCPRemoteCommitProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTCPRemoteCommitProvider((TCPRemoteCommitProviderBean)null);
               this._unSet(134);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ClusterRemoteCommitProviderBean getClusterRemoteCommitProvider() {
      return this._ClusterRemoteCommitProvider;
   }

   public boolean isClusterRemoteCommitProviderInherited() {
      return false;
   }

   public boolean isClusterRemoteCommitProviderSet() {
      return this._isSet(135);
   }

   public void setClusterRemoteCommitProvider(ClusterRemoteCommitProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getClusterRemoteCommitProvider() != null && param0 != this.getClusterRemoteCommitProvider()) {
         throw new BeanAlreadyExistsException(this.getClusterRemoteCommitProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 135)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ClusterRemoteCommitProviderBean _oldVal = this._ClusterRemoteCommitProvider;
         this._ClusterRemoteCommitProvider = param0;
         this._postSet(135, _oldVal, param0);
      }
   }

   public ClusterRemoteCommitProviderBean createClusterRemoteCommitProvider() {
      ClusterRemoteCommitProviderBeanImpl _val = new ClusterRemoteCommitProviderBeanImpl(this, -1);

      try {
         this.setClusterRemoteCommitProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyClusterRemoteCommitProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ClusterRemoteCommitProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setClusterRemoteCommitProvider((ClusterRemoteCommitProviderBean)null);
               this._unSet(135);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomRemoteCommitProviderBean getCustomRemoteCommitProvider() {
      return this._CustomRemoteCommitProvider;
   }

   public boolean isCustomRemoteCommitProviderInherited() {
      return false;
   }

   public boolean isCustomRemoteCommitProviderSet() {
      return this._isSet(136);
   }

   public void setCustomRemoteCommitProvider(CustomRemoteCommitProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomRemoteCommitProvider() != null && param0 != this.getCustomRemoteCommitProvider()) {
         throw new BeanAlreadyExistsException(this.getCustomRemoteCommitProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 136)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomRemoteCommitProviderBean _oldVal = this._CustomRemoteCommitProvider;
         this._CustomRemoteCommitProvider = param0;
         this._postSet(136, _oldVal, param0);
      }
   }

   public CustomRemoteCommitProviderBean createCustomRemoteCommitProvider() {
      CustomRemoteCommitProviderBeanImpl _val = new CustomRemoteCommitProviderBeanImpl(this, -1);

      try {
         this.setCustomRemoteCommitProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomRemoteCommitProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomRemoteCommitProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomRemoteCommitProvider((CustomRemoteCommitProviderBean)null);
               this._unSet(136);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Class[] getRemoteCommitProviderTypes() {
      return this._customizer.getRemoteCommitProviderTypes();
   }

   public RemoteCommitProviderBean getRemoteCommitProvider() {
      return this._customizer.getRemoteCommitProvider();
   }

   public RemoteCommitProviderBean createRemoteCommitProvider(Class param0) {
      return this._customizer.createRemoteCommitProvider(param0);
   }

   public void destroyRemoteCommitProvider() {
      this._customizer.destroyRemoteCommitProvider();
   }

   public String getRestoreState() {
      return this._RestoreState;
   }

   public boolean isRestoreStateInherited() {
      return false;
   }

   public boolean isRestoreStateSet() {
      return this._isSet(137);
   }

   public void setRestoreState(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"all", "false", "immutable", "none", "true"};
      param0 = LegalChecks.checkInEnum("RestoreState", param0, _set);
      String _oldVal = this._RestoreState;
      this._RestoreState = param0;
      this._postSet(137, _oldVal, param0);
   }

   public String getResultSetType() {
      return this._ResultSetType;
   }

   public boolean isResultSetTypeInherited() {
      return false;
   }

   public boolean isResultSetTypeSet() {
      return this._isSet(138);
   }

   public void setResultSetType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"forward-only", "scroll-insensitive", "scroll-sensitive"};
      param0 = LegalChecks.checkInEnum("ResultSetType", param0, _set);
      String _oldVal = this._ResultSetType;
      this._ResultSetType = param0;
      this._postSet(138, _oldVal, param0);
   }

   public boolean getRetainState() {
      return this._RetainState;
   }

   public boolean isRetainStateInherited() {
      return false;
   }

   public boolean isRetainStateSet() {
      return this._isSet(139);
   }

   public void setRetainState(boolean param0) {
      boolean _oldVal = this._RetainState;
      this._RetainState = param0;
      this._postSet(139, _oldVal, param0);
   }

   public boolean getRetryClassRegistration() {
      return this._RetryClassRegistration;
   }

   public boolean isRetryClassRegistrationInherited() {
      return false;
   }

   public boolean isRetryClassRegistrationSet() {
      return this._isSet(140);
   }

   public void setRetryClassRegistration(boolean param0) {
      boolean _oldVal = this._RetryClassRegistration;
      this._RetryClassRegistration = param0;
      this._postSet(140, _oldVal, param0);
   }

   public DefaultSavepointManagerBean getDefaultSavepointManager() {
      return this._DefaultSavepointManager;
   }

   public boolean isDefaultSavepointManagerInherited() {
      return false;
   }

   public boolean isDefaultSavepointManagerSet() {
      return this._isSet(141);
   }

   public void setDefaultSavepointManager(DefaultSavepointManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultSavepointManager() != null && param0 != this.getDefaultSavepointManager()) {
         throw new BeanAlreadyExistsException(this.getDefaultSavepointManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 141)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultSavepointManagerBean _oldVal = this._DefaultSavepointManager;
         this._DefaultSavepointManager = param0;
         this._postSet(141, _oldVal, param0);
      }
   }

   public DefaultSavepointManagerBean createDefaultSavepointManager() {
      DefaultSavepointManagerBeanImpl _val = new DefaultSavepointManagerBeanImpl(this, -1);

      try {
         this.setDefaultSavepointManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultSavepointManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultSavepointManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultSavepointManager((DefaultSavepointManagerBean)null);
               this._unSet(141);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public InMemorySavepointManagerBean getInMemorySavepointManager() {
      return this._InMemorySavepointManager;
   }

   public boolean isInMemorySavepointManagerInherited() {
      return false;
   }

   public boolean isInMemorySavepointManagerSet() {
      return this._isSet(142);
   }

   public void setInMemorySavepointManager(InMemorySavepointManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInMemorySavepointManager() != null && param0 != this.getInMemorySavepointManager()) {
         throw new BeanAlreadyExistsException(this.getInMemorySavepointManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 142)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InMemorySavepointManagerBean _oldVal = this._InMemorySavepointManager;
         this._InMemorySavepointManager = param0;
         this._postSet(142, _oldVal, param0);
      }
   }

   public InMemorySavepointManagerBean createInMemorySavepointManager() {
      InMemorySavepointManagerBeanImpl _val = new InMemorySavepointManagerBeanImpl(this, -1);

      try {
         this.setInMemorySavepointManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInMemorySavepointManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._InMemorySavepointManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInMemorySavepointManager((InMemorySavepointManagerBean)null);
               this._unSet(142);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JDBC3SavepointManagerBean getJDBC3SavepointManager() {
      return this._JDBC3SavepointManager;
   }

   public boolean isJDBC3SavepointManagerInherited() {
      return false;
   }

   public boolean isJDBC3SavepointManagerSet() {
      return this._isSet(143);
   }

   public void setJDBC3SavepointManager(JDBC3SavepointManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJDBC3SavepointManager() != null && param0 != this.getJDBC3SavepointManager()) {
         throw new BeanAlreadyExistsException(this.getJDBC3SavepointManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 143)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JDBC3SavepointManagerBean _oldVal = this._JDBC3SavepointManager;
         this._JDBC3SavepointManager = param0;
         this._postSet(143, _oldVal, param0);
      }
   }

   public JDBC3SavepointManagerBean createJDBC3SavepointManager() {
      JDBC3SavepointManagerBeanImpl _val = new JDBC3SavepointManagerBeanImpl(this, -1);

      try {
         this.setJDBC3SavepointManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJDBC3SavepointManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JDBC3SavepointManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDBC3SavepointManager((JDBC3SavepointManagerBean)null);
               this._unSet(143);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OracleSavepointManagerBean getOracleSavepointManager() {
      return this._OracleSavepointManager;
   }

   public boolean isOracleSavepointManagerInherited() {
      return false;
   }

   public boolean isOracleSavepointManagerSet() {
      return this._isSet(144);
   }

   public void setOracleSavepointManager(OracleSavepointManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOracleSavepointManager() != null && param0 != this.getOracleSavepointManager()) {
         throw new BeanAlreadyExistsException(this.getOracleSavepointManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 144)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OracleSavepointManagerBean _oldVal = this._OracleSavepointManager;
         this._OracleSavepointManager = param0;
         this._postSet(144, _oldVal, param0);
      }
   }

   public OracleSavepointManagerBean createOracleSavepointManager() {
      OracleSavepointManagerBeanImpl _val = new OracleSavepointManagerBeanImpl(this, -1);

      try {
         this.setOracleSavepointManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOracleSavepointManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OracleSavepointManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOracleSavepointManager((OracleSavepointManagerBean)null);
               this._unSet(144);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomSavepointManagerBean getCustomSavepointManager() {
      return this._CustomSavepointManager;
   }

   public boolean isCustomSavepointManagerInherited() {
      return false;
   }

   public boolean isCustomSavepointManagerSet() {
      return this._isSet(145);
   }

   public void setCustomSavepointManager(CustomSavepointManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomSavepointManager() != null && param0 != this.getCustomSavepointManager()) {
         throw new BeanAlreadyExistsException(this.getCustomSavepointManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 145)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomSavepointManagerBean _oldVal = this._CustomSavepointManager;
         this._CustomSavepointManager = param0;
         this._postSet(145, _oldVal, param0);
      }
   }

   public CustomSavepointManagerBean createCustomSavepointManager() {
      CustomSavepointManagerBeanImpl _val = new CustomSavepointManagerBeanImpl(this, -1);

      try {
         this.setCustomSavepointManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomSavepointManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomSavepointManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomSavepointManager((CustomSavepointManagerBean)null);
               this._unSet(145);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getSchema() {
      return this._Schema;
   }

   public boolean isSchemaInherited() {
      return false;
   }

   public boolean isSchemaSet() {
      return this._isSet(146);
   }

   public void setSchema(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Schema;
      this._Schema = param0;
      this._postSet(146, _oldVal, param0);
   }

   public DefaultSchemaFactoryBean getDefaultSchemaFactory() {
      return this._DefaultSchemaFactory;
   }

   public boolean isDefaultSchemaFactoryInherited() {
      return false;
   }

   public boolean isDefaultSchemaFactorySet() {
      return this._isSet(147);
   }

   public void setDefaultSchemaFactory(DefaultSchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultSchemaFactory() != null && param0 != this.getDefaultSchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getDefaultSchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 147)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultSchemaFactoryBean _oldVal = this._DefaultSchemaFactory;
         this._DefaultSchemaFactory = param0;
         this._postSet(147, _oldVal, param0);
      }
   }

   public DefaultSchemaFactoryBean createDefaultSchemaFactory() {
      DefaultSchemaFactoryBeanImpl _val = new DefaultSchemaFactoryBeanImpl(this, -1);

      try {
         this.setDefaultSchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultSchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultSchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultSchemaFactory((DefaultSchemaFactoryBean)null);
               this._unSet(147);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DynamicSchemaFactoryBean getDynamicSchemaFactory() {
      return this._DynamicSchemaFactory;
   }

   public boolean isDynamicSchemaFactoryInherited() {
      return false;
   }

   public boolean isDynamicSchemaFactorySet() {
      return this._isSet(148);
   }

   public void setDynamicSchemaFactory(DynamicSchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDynamicSchemaFactory() != null && param0 != this.getDynamicSchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getDynamicSchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 148)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DynamicSchemaFactoryBean _oldVal = this._DynamicSchemaFactory;
         this._DynamicSchemaFactory = param0;
         this._postSet(148, _oldVal, param0);
      }
   }

   public DynamicSchemaFactoryBean createDynamicSchemaFactory() {
      DynamicSchemaFactoryBeanImpl _val = new DynamicSchemaFactoryBeanImpl(this, -1);

      try {
         this.setDynamicSchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDynamicSchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DynamicSchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDynamicSchemaFactory((DynamicSchemaFactoryBean)null);
               this._unSet(148);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public FileSchemaFactoryBean getFileSchemaFactory() {
      return this._FileSchemaFactory;
   }

   public boolean isFileSchemaFactoryInherited() {
      return false;
   }

   public boolean isFileSchemaFactorySet() {
      return this._isSet(149);
   }

   public void setFileSchemaFactory(FileSchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFileSchemaFactory() != null && param0 != this.getFileSchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getFileSchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 149)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         FileSchemaFactoryBean _oldVal = this._FileSchemaFactory;
         this._FileSchemaFactory = param0;
         this._postSet(149, _oldVal, param0);
      }
   }

   public FileSchemaFactoryBean createFileSchemaFactory() {
      FileSchemaFactoryBeanImpl _val = new FileSchemaFactoryBeanImpl(this, -1);

      try {
         this.setFileSchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFileSchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FileSchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFileSchemaFactory((FileSchemaFactoryBean)null);
               this._unSet(149);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LazySchemaFactoryBean getLazySchemaFactory() {
      return this._LazySchemaFactory;
   }

   public boolean isLazySchemaFactoryInherited() {
      return false;
   }

   public boolean isLazySchemaFactorySet() {
      return this._isSet(150);
   }

   public void setLazySchemaFactory(LazySchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLazySchemaFactory() != null && param0 != this.getLazySchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getLazySchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 150)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LazySchemaFactoryBean _oldVal = this._LazySchemaFactory;
         this._LazySchemaFactory = param0;
         this._postSet(150, _oldVal, param0);
      }
   }

   public LazySchemaFactoryBean createLazySchemaFactory() {
      LazySchemaFactoryBeanImpl _val = new LazySchemaFactoryBeanImpl(this, -1);

      try {
         this.setLazySchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLazySchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LazySchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLazySchemaFactory((LazySchemaFactoryBean)null);
               this._unSet(150);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TableSchemaFactoryBean getTableSchemaFactory() {
      return this._TableSchemaFactory;
   }

   public boolean isTableSchemaFactoryInherited() {
      return false;
   }

   public boolean isTableSchemaFactorySet() {
      return this._isSet(151);
   }

   public void setTableSchemaFactory(TableSchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTableSchemaFactory() != null && param0 != this.getTableSchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getTableSchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 151)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TableSchemaFactoryBean _oldVal = this._TableSchemaFactory;
         this._TableSchemaFactory = param0;
         this._postSet(151, _oldVal, param0);
      }
   }

   public TableSchemaFactoryBean createTableSchemaFactory() {
      TableSchemaFactoryBeanImpl _val = new TableSchemaFactoryBeanImpl(this, -1);

      try {
         this.setTableSchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableSchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TableSchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTableSchemaFactory((TableSchemaFactoryBean)null);
               this._unSet(151);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomSchemaFactoryBean getCustomSchemaFactory() {
      return this._CustomSchemaFactory;
   }

   public boolean isCustomSchemaFactoryInherited() {
      return false;
   }

   public boolean isCustomSchemaFactorySet() {
      return this._isSet(152);
   }

   public void setCustomSchemaFactory(CustomSchemaFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomSchemaFactory() != null && param0 != this.getCustomSchemaFactory()) {
         throw new BeanAlreadyExistsException(this.getCustomSchemaFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 152)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomSchemaFactoryBean _oldVal = this._CustomSchemaFactory;
         this._CustomSchemaFactory = param0;
         this._postSet(152, _oldVal, param0);
      }
   }

   public CustomSchemaFactoryBean createCustomSchemaFactory() {
      CustomSchemaFactoryBeanImpl _val = new CustomSchemaFactoryBeanImpl(this, -1);

      try {
         this.setCustomSchemaFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomSchemaFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomSchemaFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomSchemaFactory((CustomSchemaFactoryBean)null);
               this._unSet(152);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SchemasBean getSchemata() {
      return this._Schemata;
   }

   public boolean isSchemataInherited() {
      return false;
   }

   public boolean isSchemataSet() {
      return this._isSet(153) || this._isAnythingSet((AbstractDescriptorBean)this.getSchemata());
   }

   public void setSchemata(SchemasBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 153)) {
         this._postCreate(_child);
      }

      SchemasBean _oldVal = this._Schemata;
      this._Schemata = param0;
      this._postSet(153, _oldVal, param0);
   }

   public ClassTableJDBCSeqBean getClassTableJDBCSeq() {
      return this._ClassTableJDBCSeq;
   }

   public boolean isClassTableJDBCSeqInherited() {
      return false;
   }

   public boolean isClassTableJDBCSeqSet() {
      return this._isSet(154);
   }

   public void setClassTableJDBCSeq(ClassTableJDBCSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getClassTableJDBCSeq() != null && param0 != this.getClassTableJDBCSeq()) {
         throw new BeanAlreadyExistsException(this.getClassTableJDBCSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 154)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ClassTableJDBCSeqBean _oldVal = this._ClassTableJDBCSeq;
         this._ClassTableJDBCSeq = param0;
         this._postSet(154, _oldVal, param0);
      }
   }

   public ClassTableJDBCSeqBean createClassTableJDBCSeq() {
      ClassTableJDBCSeqBeanImpl _val = new ClassTableJDBCSeqBeanImpl(this, -1);

      try {
         this.setClassTableJDBCSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyClassTableJDBCSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ClassTableJDBCSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setClassTableJDBCSeq((ClassTableJDBCSeqBean)null);
               this._unSet(154);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NativeJDBCSeqBean getNativeJDBCSeq() {
      return this._NativeJDBCSeq;
   }

   public boolean isNativeJDBCSeqInherited() {
      return false;
   }

   public boolean isNativeJDBCSeqSet() {
      return this._isSet(155);
   }

   public void setNativeJDBCSeq(NativeJDBCSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNativeJDBCSeq() != null && param0 != this.getNativeJDBCSeq()) {
         throw new BeanAlreadyExistsException(this.getNativeJDBCSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 155)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NativeJDBCSeqBean _oldVal = this._NativeJDBCSeq;
         this._NativeJDBCSeq = param0;
         this._postSet(155, _oldVal, param0);
      }
   }

   public NativeJDBCSeqBean createNativeJDBCSeq() {
      NativeJDBCSeqBeanImpl _val = new NativeJDBCSeqBeanImpl(this, -1);

      try {
         this.setNativeJDBCSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNativeJDBCSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NativeJDBCSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNativeJDBCSeq((NativeJDBCSeqBean)null);
               this._unSet(155);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TableJDBCSeqBean getTableJDBCSeq() {
      return this._TableJDBCSeq;
   }

   public boolean isTableJDBCSeqInherited() {
      return false;
   }

   public boolean isTableJDBCSeqSet() {
      return this._isSet(156);
   }

   public void setTableJDBCSeq(TableJDBCSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTableJDBCSeq() != null && param0 != this.getTableJDBCSeq()) {
         throw new BeanAlreadyExistsException(this.getTableJDBCSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 156)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TableJDBCSeqBean _oldVal = this._TableJDBCSeq;
         this._TableJDBCSeq = param0;
         this._postSet(156, _oldVal, param0);
      }
   }

   public TableJDBCSeqBean createTableJDBCSeq() {
      TableJDBCSeqBeanImpl _val = new TableJDBCSeqBeanImpl(this, -1);

      try {
         this.setTableJDBCSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableJDBCSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TableJDBCSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTableJDBCSeq((TableJDBCSeqBean)null);
               this._unSet(156);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TimeSeededSeqBean getTimeSeededSeq() {
      return this._TimeSeededSeq;
   }

   public boolean isTimeSeededSeqInherited() {
      return false;
   }

   public boolean isTimeSeededSeqSet() {
      return this._isSet(157);
   }

   public void setTimeSeededSeq(TimeSeededSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTimeSeededSeq() != null && param0 != this.getTimeSeededSeq()) {
         throw new BeanAlreadyExistsException(this.getTimeSeededSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 157)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TimeSeededSeqBean _oldVal = this._TimeSeededSeq;
         this._TimeSeededSeq = param0;
         this._postSet(157, _oldVal, param0);
      }
   }

   public TimeSeededSeqBean createTimeSeededSeq() {
      TimeSeededSeqBeanImpl _val = new TimeSeededSeqBeanImpl(this, -1);

      try {
         this.setTimeSeededSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTimeSeededSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TimeSeededSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTimeSeededSeq((TimeSeededSeqBean)null);
               this._unSet(157);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ValueTableJDBCSeqBean getValueTableJDBCSeq() {
      return this._ValueTableJDBCSeq;
   }

   public boolean isValueTableJDBCSeqInherited() {
      return false;
   }

   public boolean isValueTableJDBCSeqSet() {
      return this._isSet(158);
   }

   public void setValueTableJDBCSeq(ValueTableJDBCSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getValueTableJDBCSeq() != null && param0 != this.getValueTableJDBCSeq()) {
         throw new BeanAlreadyExistsException(this.getValueTableJDBCSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 158)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ValueTableJDBCSeqBean _oldVal = this._ValueTableJDBCSeq;
         this._ValueTableJDBCSeq = param0;
         this._postSet(158, _oldVal, param0);
      }
   }

   public ValueTableJDBCSeqBean createValueTableJDBCSeq() {
      ValueTableJDBCSeqBeanImpl _val = new ValueTableJDBCSeqBeanImpl(this, -1);

      try {
         this.setValueTableJDBCSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyValueTableJDBCSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ValueTableJDBCSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setValueTableJDBCSeq((ValueTableJDBCSeqBean)null);
               this._unSet(158);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomSeqBean getCustomSeq() {
      return this._CustomSeq;
   }

   public boolean isCustomSeqInherited() {
      return false;
   }

   public boolean isCustomSeqSet() {
      return this._isSet(159);
   }

   public void setCustomSeq(CustomSeqBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomSeq() != null && param0 != this.getCustomSeq()) {
         throw new BeanAlreadyExistsException(this.getCustomSeq() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 159)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomSeqBean _oldVal = this._CustomSeq;
         this._CustomSeq = param0;
         this._postSet(159, _oldVal, param0);
      }
   }

   public CustomSeqBean createCustomSeq() {
      CustomSeqBeanImpl _val = new CustomSeqBeanImpl(this, -1);

      try {
         this.setCustomSeq(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomSeq() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomSeq;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomSeq((CustomSeqBean)null);
               this._unSet(159);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public DefaultSQLFactoryBean getDefaultSQLFactory() {
      return this._DefaultSQLFactory;
   }

   public boolean isDefaultSQLFactoryInherited() {
      return false;
   }

   public boolean isDefaultSQLFactorySet() {
      return this._isSet(160);
   }

   public void setDefaultSQLFactory(DefaultSQLFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultSQLFactory() != null && param0 != this.getDefaultSQLFactory()) {
         throw new BeanAlreadyExistsException(this.getDefaultSQLFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 160)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultSQLFactoryBean _oldVal = this._DefaultSQLFactory;
         this._DefaultSQLFactory = param0;
         this._postSet(160, _oldVal, param0);
      }
   }

   public DefaultSQLFactoryBean createDefaultSQLFactory() {
      DefaultSQLFactoryBeanImpl _val = new DefaultSQLFactoryBeanImpl(this, -1);

      try {
         this.setDefaultSQLFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultSQLFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultSQLFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultSQLFactory((DefaultSQLFactoryBean)null);
               this._unSet(160);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public KodoSQLFactoryBean getKodoSQLFactory() {
      return this._KodoSQLFactory;
   }

   public boolean isKodoSQLFactoryInherited() {
      return false;
   }

   public boolean isKodoSQLFactorySet() {
      return this._isSet(161);
   }

   public void setKodoSQLFactory(KodoSQLFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getKodoSQLFactory() != null && param0 != this.getKodoSQLFactory()) {
         throw new BeanAlreadyExistsException(this.getKodoSQLFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 161)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         KodoSQLFactoryBean _oldVal = this._KodoSQLFactory;
         this._KodoSQLFactory = param0;
         this._postSet(161, _oldVal, param0);
      }
   }

   public KodoSQLFactoryBean createKodoSQLFactory() {
      KodoSQLFactoryBeanImpl _val = new KodoSQLFactoryBeanImpl(this, -1);

      try {
         this.setKodoSQLFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyKodoSQLFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._KodoSQLFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setKodoSQLFactory((KodoSQLFactoryBean)null);
               this._unSet(161);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomSQLFactoryBean getCustomSQLFactory() {
      return this._CustomSQLFactory;
   }

   public boolean isCustomSQLFactoryInherited() {
      return false;
   }

   public boolean isCustomSQLFactorySet() {
      return this._isSet(162);
   }

   public void setCustomSQLFactory(CustomSQLFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomSQLFactory() != null && param0 != this.getCustomSQLFactory()) {
         throw new BeanAlreadyExistsException(this.getCustomSQLFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 162)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomSQLFactoryBean _oldVal = this._CustomSQLFactory;
         this._CustomSQLFactory = param0;
         this._postSet(162, _oldVal, param0);
      }
   }

   public CustomSQLFactoryBean createCustomSQLFactory() {
      CustomSQLFactoryBeanImpl _val = new CustomSQLFactoryBeanImpl(this, -1);

      try {
         this.setCustomSQLFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomSQLFactory() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomSQLFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomSQLFactory((CustomSQLFactoryBean)null);
               this._unSet(162);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getSubclassFetchMode() {
      return this._SubclassFetchMode;
   }

   public boolean isSubclassFetchModeInherited() {
      return false;
   }

   public boolean isSubclassFetchModeSet() {
      return this._isSet(163);
   }

   public void setSubclassFetchMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"join", "multiple", "none", "parallel", "single"};
      param0 = LegalChecks.checkInEnum("SubclassFetchMode", param0, _set);
      String _oldVal = this._SubclassFetchMode;
      this._SubclassFetchMode = param0;
      this._postSet(163, _oldVal, param0);
   }

   public String getSynchronizeMappings() {
      return this._SynchronizeMappings;
   }

   public boolean isSynchronizeMappingsInherited() {
      return false;
   }

   public boolean isSynchronizeMappingsSet() {
      return this._isSet(164);
   }

   public void setSynchronizeMappings(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SynchronizeMappings;
      this._SynchronizeMappings = param0;
      this._postSet(164, _oldVal, param0);
   }

   public String getTransactionIsolation() {
      return this._TransactionIsolation;
   }

   public boolean isTransactionIsolationInherited() {
      return false;
   }

   public boolean isTransactionIsolationSet() {
      return this._isSet(165);
   }

   public void setTransactionIsolation(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"default", "none", "read-committed", "read-uncommitted", "repeatable-read", "serializable"};
      param0 = LegalChecks.checkInEnum("TransactionIsolation", param0, _set);
      String _oldVal = this._TransactionIsolation;
      this._TransactionIsolation = param0;
      this._postSet(165, _oldVal, param0);
   }

   public String getTransactionMode() {
      return this._TransactionMode;
   }

   public boolean isTransactionModeInherited() {
      return false;
   }

   public boolean isTransactionModeSet() {
      return this._isSet(166);
   }

   public void setTransactionMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"local", "managed"};
      param0 = LegalChecks.checkInEnum("TransactionMode", param0, _set);
      String _oldVal = this._TransactionMode;
      this._TransactionMode = param0;
      this._postSet(166, _oldVal, param0);
   }

   public DefaultUpdateManagerBean getDefaultUpdateManager() {
      return this._DefaultUpdateManager;
   }

   public boolean isDefaultUpdateManagerInherited() {
      return false;
   }

   public boolean isDefaultUpdateManagerSet() {
      return this._isSet(167);
   }

   public void setDefaultUpdateManager(DefaultUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDefaultUpdateManager() != null && param0 != this.getDefaultUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getDefaultUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 167)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DefaultUpdateManagerBean _oldVal = this._DefaultUpdateManager;
         this._DefaultUpdateManager = param0;
         this._postSet(167, _oldVal, param0);
      }
   }

   public DefaultUpdateManagerBean createDefaultUpdateManager() {
      DefaultUpdateManagerBeanImpl _val = new DefaultUpdateManagerBeanImpl(this, -1);

      try {
         this.setDefaultUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDefaultUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DefaultUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDefaultUpdateManager((DefaultUpdateManagerBean)null);
               this._unSet(167);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ConstraintUpdateManagerBean getConstraintUpdateManager() {
      return this._ConstraintUpdateManager;
   }

   public boolean isConstraintUpdateManagerInherited() {
      return false;
   }

   public boolean isConstraintUpdateManagerSet() {
      return this._isSet(168);
   }

   public void setConstraintUpdateManager(ConstraintUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConstraintUpdateManager() != null && param0 != this.getConstraintUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getConstraintUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 168)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConstraintUpdateManagerBean _oldVal = this._ConstraintUpdateManager;
         this._ConstraintUpdateManager = param0;
         this._postSet(168, _oldVal, param0);
      }
   }

   public ConstraintUpdateManagerBean createConstraintUpdateManager() {
      ConstraintUpdateManagerBeanImpl _val = new ConstraintUpdateManagerBeanImpl(this, -1);

      try {
         this.setConstraintUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConstraintUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConstraintUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConstraintUpdateManager((ConstraintUpdateManagerBean)null);
               this._unSet(168);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public BatchingOperationOrderUpdateManagerBean getBatchingOperationOrderUpdateManager() {
      return this._BatchingOperationOrderUpdateManager;
   }

   public boolean isBatchingOperationOrderUpdateManagerInherited() {
      return false;
   }

   public boolean isBatchingOperationOrderUpdateManagerSet() {
      return this._isSet(169);
   }

   public void setBatchingOperationOrderUpdateManager(BatchingOperationOrderUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getBatchingOperationOrderUpdateManager() != null && param0 != this.getBatchingOperationOrderUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getBatchingOperationOrderUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 169)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         BatchingOperationOrderUpdateManagerBean _oldVal = this._BatchingOperationOrderUpdateManager;
         this._BatchingOperationOrderUpdateManager = param0;
         this._postSet(169, _oldVal, param0);
      }
   }

   public BatchingOperationOrderUpdateManagerBean createBatchingOperationOrderUpdateManager() {
      BatchingOperationOrderUpdateManagerBeanImpl _val = new BatchingOperationOrderUpdateManagerBeanImpl(this, -1);

      try {
         this.setBatchingOperationOrderUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyBatchingOperationOrderUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._BatchingOperationOrderUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setBatchingOperationOrderUpdateManager((BatchingOperationOrderUpdateManagerBean)null);
               this._unSet(169);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OperationOrderUpdateManagerBean getOperationOrderUpdateManager() {
      return this._OperationOrderUpdateManager;
   }

   public boolean isOperationOrderUpdateManagerInherited() {
      return false;
   }

   public boolean isOperationOrderUpdateManagerSet() {
      return this._isSet(170);
   }

   public void setOperationOrderUpdateManager(OperationOrderUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOperationOrderUpdateManager() != null && param0 != this.getOperationOrderUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getOperationOrderUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 170)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OperationOrderUpdateManagerBean _oldVal = this._OperationOrderUpdateManager;
         this._OperationOrderUpdateManager = param0;
         this._postSet(170, _oldVal, param0);
      }
   }

   public OperationOrderUpdateManagerBean createOperationOrderUpdateManager() {
      OperationOrderUpdateManagerBeanImpl _val = new OperationOrderUpdateManagerBeanImpl(this, -1);

      try {
         this.setOperationOrderUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOperationOrderUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OperationOrderUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOperationOrderUpdateManager((OperationOrderUpdateManagerBean)null);
               this._unSet(170);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TableLockUpdateManagerBean getTableLockUpdateManager() {
      return this._TableLockUpdateManager;
   }

   public boolean isTableLockUpdateManagerInherited() {
      return false;
   }

   public boolean isTableLockUpdateManagerSet() {
      return this._isSet(171);
   }

   public void setTableLockUpdateManager(TableLockUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTableLockUpdateManager() != null && param0 != this.getTableLockUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getTableLockUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 171)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TableLockUpdateManagerBean _oldVal = this._TableLockUpdateManager;
         this._TableLockUpdateManager = param0;
         this._postSet(171, _oldVal, param0);
      }
   }

   public TableLockUpdateManagerBean createTableLockUpdateManager() {
      TableLockUpdateManagerBeanImpl _val = new TableLockUpdateManagerBeanImpl(this, -1);

      try {
         this.setTableLockUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTableLockUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TableLockUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTableLockUpdateManager((TableLockUpdateManagerBean)null);
               this._unSet(171);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CustomUpdateManagerBean getCustomUpdateManager() {
      return this._CustomUpdateManager;
   }

   public boolean isCustomUpdateManagerInherited() {
      return false;
   }

   public boolean isCustomUpdateManagerSet() {
      return this._isSet(172);
   }

   public void setCustomUpdateManager(CustomUpdateManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCustomUpdateManager() != null && param0 != this.getCustomUpdateManager()) {
         throw new BeanAlreadyExistsException(this.getCustomUpdateManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 172)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CustomUpdateManagerBean _oldVal = this._CustomUpdateManager;
         this._CustomUpdateManager = param0;
         this._postSet(172, _oldVal, param0);
      }
   }

   public CustomUpdateManagerBean createCustomUpdateManager() {
      CustomUpdateManagerBeanImpl _val = new CustomUpdateManagerBeanImpl(this, -1);

      try {
         this.setCustomUpdateManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomUpdateManager() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CustomUpdateManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCustomUpdateManager((CustomUpdateManagerBean)null);
               this._unSet(172);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getWriteLockLevel() {
      return this._WriteLockLevel;
   }

   public boolean isWriteLockLevelInherited() {
      return false;
   }

   public boolean isWriteLockLevelSet() {
      return this._isSet(173);
   }

   public void setWriteLockLevel(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "read", "write"};
      param0 = LegalChecks.checkInEnum("WriteLockLevel", param0, _set);
      String _oldVal = this._WriteLockLevel;
      this._WriteLockLevel = param0;
      this._postSet(173, _oldVal, param0);
   }

   public StackExecutionContextNameProviderBean getStackExecutionContextNameProvider() {
      return this._StackExecutionContextNameProvider;
   }

   public boolean isStackExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isStackExecutionContextNameProviderSet() {
      return this._isSet(174);
   }

   public void setStackExecutionContextNameProvider(StackExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getStackExecutionContextNameProvider() != null && param0 != this.getStackExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getStackExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 174)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         StackExecutionContextNameProviderBean _oldVal = this._StackExecutionContextNameProvider;
         this._StackExecutionContextNameProvider = param0;
         this._postSet(174, _oldVal, param0);
      }
   }

   public StackExecutionContextNameProviderBean createStackExecutionContextNameProvider() {
      StackExecutionContextNameProviderBeanImpl _val = new StackExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setStackExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStackExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._StackExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)null);
               this._unSet(174);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TransactionNameExecutionContextNameProviderBean getTransactionNameExecutionContextNameProvider() {
      return this._TransactionNameExecutionContextNameProvider;
   }

   public boolean isTransactionNameExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isTransactionNameExecutionContextNameProviderSet() {
      return this._isSet(175);
   }

   public void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTransactionNameExecutionContextNameProvider() != null && param0 != this.getTransactionNameExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getTransactionNameExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 175)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TransactionNameExecutionContextNameProviderBean _oldVal = this._TransactionNameExecutionContextNameProvider;
         this._TransactionNameExecutionContextNameProvider = param0;
         this._postSet(175, _oldVal, param0);
      }
   }

   public TransactionNameExecutionContextNameProviderBean createTransactionNameExecutionContextNameProvider() {
      TransactionNameExecutionContextNameProviderBeanImpl _val = new TransactionNameExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setTransactionNameExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTransactionNameExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TransactionNameExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)null);
               this._unSet(175);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public UserObjectExecutionContextNameProviderBean getUserObjectExecutionContextNameProvider() {
      return this._UserObjectExecutionContextNameProvider;
   }

   public boolean isUserObjectExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isUserObjectExecutionContextNameProviderSet() {
      return this._isSet(176);
   }

   public void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getUserObjectExecutionContextNameProvider() != null && param0 != this.getUserObjectExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getUserObjectExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 176)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         UserObjectExecutionContextNameProviderBean _oldVal = this._UserObjectExecutionContextNameProvider;
         this._UserObjectExecutionContextNameProvider = param0;
         this._postSet(176, _oldVal, param0);
      }
   }

   public UserObjectExecutionContextNameProviderBean createUserObjectExecutionContextNameProvider() {
      UserObjectExecutionContextNameProviderBeanImpl _val = new UserObjectExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setUserObjectExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyUserObjectExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._UserObjectExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)null);
               this._unSet(176);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NoneJMXBean getNoneJMX() {
      return this._NoneJMX;
   }

   public boolean isNoneJMXInherited() {
      return false;
   }

   public boolean isNoneJMXSet() {
      return this._isSet(177);
   }

   public void setNoneJMX(NoneJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneJMX() != null && param0 != this.getNoneJMX()) {
         throw new BeanAlreadyExistsException(this.getNoneJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 177)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneJMXBean _oldVal = this._NoneJMX;
         this._NoneJMX = param0;
         this._postSet(177, _oldVal, param0);
      }
   }

   public NoneJMXBean createNoneJMX() {
      NoneJMXBeanImpl _val = new NoneJMXBeanImpl(this, -1);

      try {
         this.setNoneJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneJMX((NoneJMXBean)null);
               this._unSet(177);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LocalJMXBean getLocalJMX() {
      return this._LocalJMX;
   }

   public boolean isLocalJMXInherited() {
      return false;
   }

   public boolean isLocalJMXSet() {
      return this._isSet(178);
   }

   public void setLocalJMX(LocalJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLocalJMX() != null && param0 != this.getLocalJMX()) {
         throw new BeanAlreadyExistsException(this.getLocalJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 178)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LocalJMXBean _oldVal = this._LocalJMX;
         this._LocalJMX = param0;
         this._postSet(178, _oldVal, param0);
      }
   }

   public LocalJMXBean createLocalJMX() {
      LocalJMXBeanImpl _val = new LocalJMXBeanImpl(this, -1);

      try {
         this.setLocalJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocalJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LocalJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLocalJMX((LocalJMXBean)null);
               this._unSet(178);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public GUIJMXBean getGUIJMX() {
      return this._GUIJMX;
   }

   public boolean isGUIJMXInherited() {
      return false;
   }

   public boolean isGUIJMXSet() {
      return this._isSet(179);
   }

   public void setGUIJMX(GUIJMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGUIJMX() != null && param0 != this.getGUIJMX()) {
         throw new BeanAlreadyExistsException(this.getGUIJMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 179)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GUIJMXBean _oldVal = this._GUIJMX;
         this._GUIJMX = param0;
         this._postSet(179, _oldVal, param0);
      }
   }

   public GUIJMXBean createGUIJMX() {
      GUIJMXBeanImpl _val = new GUIJMXBeanImpl(this, -1);

      try {
         this.setGUIJMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGUIJMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GUIJMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGUIJMX((GUIJMXBean)null);
               this._unSet(179);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public JMX2JMXBean getJMX2JMX() {
      return this._JMX2JMX;
   }

   public boolean isJMX2JMXInherited() {
      return false;
   }

   public boolean isJMX2JMXSet() {
      return this._isSet(180);
   }

   public void setJMX2JMX(JMX2JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getJMX2JMX() != null && param0 != this.getJMX2JMX()) {
         throw new BeanAlreadyExistsException(this.getJMX2JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 180)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         JMX2JMXBean _oldVal = this._JMX2JMX;
         this._JMX2JMX = param0;
         this._postSet(180, _oldVal, param0);
      }
   }

   public JMX2JMXBean createJMX2JMX() {
      JMX2JMXBeanImpl _val = new JMX2JMXBeanImpl(this, -1);

      try {
         this.setJMX2JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJMX2JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._JMX2JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJMX2JMX((JMX2JMXBean)null);
               this._unSet(180);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public MX4J1JMXBean getMX4J1JMX() {
      return this._MX4J1JMX;
   }

   public boolean isMX4J1JMXInherited() {
      return false;
   }

   public boolean isMX4J1JMXSet() {
      return this._isSet(181);
   }

   public void setMX4J1JMX(MX4J1JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMX4J1JMX() != null && param0 != this.getMX4J1JMX()) {
         throw new BeanAlreadyExistsException(this.getMX4J1JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 181)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MX4J1JMXBean _oldVal = this._MX4J1JMX;
         this._MX4J1JMX = param0;
         this._postSet(181, _oldVal, param0);
      }
   }

   public MX4J1JMXBean createMX4J1JMX() {
      MX4J1JMXBeanImpl _val = new MX4J1JMXBeanImpl(this, -1);

      try {
         this.setMX4J1JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMX4J1JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MX4J1JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMX4J1JMX((MX4J1JMXBean)null);
               this._unSet(181);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WLS81JMXBean getWLS81JMX() {
      return this._WLS81JMX;
   }

   public boolean isWLS81JMXInherited() {
      return false;
   }

   public boolean isWLS81JMXSet() {
      return this._isSet(182);
   }

   public void setWLS81JMX(WLS81JMXBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWLS81JMX() != null && param0 != this.getWLS81JMX()) {
         throw new BeanAlreadyExistsException(this.getWLS81JMX() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 182)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WLS81JMXBean _oldVal = this._WLS81JMX;
         this._WLS81JMX = param0;
         this._postSet(182, _oldVal, param0);
      }
   }

   public WLS81JMXBean createWLS81JMX() {
      WLS81JMXBeanImpl _val = new WLS81JMXBeanImpl(this, -1);

      try {
         this.setWLS81JMX(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWLS81JMX() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WLS81JMX;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWLS81JMX((WLS81JMXBean)null);
               this._unSet(182);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public NoneProfilingBean getNoneProfiling() {
      return this._NoneProfiling;
   }

   public boolean isNoneProfilingInherited() {
      return false;
   }

   public boolean isNoneProfilingSet() {
      return this._isSet(183);
   }

   public void setNoneProfiling(NoneProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneProfiling() != null && param0 != this.getNoneProfiling()) {
         throw new BeanAlreadyExistsException(this.getNoneProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 183)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneProfilingBean _oldVal = this._NoneProfiling;
         this._NoneProfiling = param0;
         this._postSet(183, _oldVal, param0);
      }
   }

   public NoneProfilingBean createNoneProfiling() {
      NoneProfilingBeanImpl _val = new NoneProfilingBeanImpl(this, -1);

      try {
         this.setNoneProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneProfiling((NoneProfilingBean)null);
               this._unSet(183);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LocalProfilingBean getLocalProfiling() {
      return this._LocalProfiling;
   }

   public boolean isLocalProfilingInherited() {
      return false;
   }

   public boolean isLocalProfilingSet() {
      return this._isSet(184);
   }

   public void setLocalProfiling(LocalProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLocalProfiling() != null && param0 != this.getLocalProfiling()) {
         throw new BeanAlreadyExistsException(this.getLocalProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 184)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LocalProfilingBean _oldVal = this._LocalProfiling;
         this._LocalProfiling = param0;
         this._postSet(184, _oldVal, param0);
      }
   }

   public LocalProfilingBean createLocalProfiling() {
      LocalProfilingBeanImpl _val = new LocalProfilingBeanImpl(this, -1);

      try {
         this.setLocalProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocalProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LocalProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLocalProfiling((LocalProfilingBean)null);
               this._unSet(184);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ExportProfilingBean getExportProfiling() {
      return this._ExportProfiling;
   }

   public boolean isExportProfilingInherited() {
      return false;
   }

   public boolean isExportProfilingSet() {
      return this._isSet(185);
   }

   public void setExportProfiling(ExportProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExportProfiling() != null && param0 != this.getExportProfiling()) {
         throw new BeanAlreadyExistsException(this.getExportProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 185)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ExportProfilingBean _oldVal = this._ExportProfiling;
         this._ExportProfiling = param0;
         this._postSet(185, _oldVal, param0);
      }
   }

   public ExportProfilingBean createExportProfiling() {
      ExportProfilingBeanImpl _val = new ExportProfilingBeanImpl(this, -1);

      try {
         this.setExportProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExportProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExportProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExportProfiling((ExportProfilingBean)null);
               this._unSet(185);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public GUIProfilingBean getGUIProfiling() {
      return this._GUIProfiling;
   }

   public boolean isGUIProfilingInherited() {
      return false;
   }

   public boolean isGUIProfilingSet() {
      return this._isSet(186);
   }

   public void setGUIProfiling(GUIProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGUIProfiling() != null && param0 != this.getGUIProfiling()) {
         throw new BeanAlreadyExistsException(this.getGUIProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 186)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GUIProfilingBean _oldVal = this._GUIProfiling;
         this._GUIProfiling = param0;
         this._postSet(186, _oldVal, param0);
      }
   }

   public GUIProfilingBean createGUIProfiling() {
      GUIProfilingBeanImpl _val = new GUIProfilingBeanImpl(this, -1);

      try {
         this.setGUIProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGUIProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GUIProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGUIProfiling((GUIProfilingBean)null);
               this._unSet(186);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
   }

   public void setConnection2PasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._Connection2PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: Connection2PasswordEncrypted of PersistenceUnitConfigurationBean");
      } else {
         this._getHelper()._clearArray(this._Connection2PasswordEncrypted);
         this._Connection2PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(19, _oldVal, param0);
      }
   }

   public void setConnectionPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._ConnectionPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: ConnectionPasswordEncrypted of PersistenceUnitConfigurationBean");
      } else {
         this._getHelper()._clearArray(this._ConnectionPasswordEncrypted);
         this._ConnectionPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(31, _oldVal, param0);
      }
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 18) {
            this._markSet(19, false);
         }

         if (idx == 30) {
            this._markSet(31, false);
         }
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isAggregateListenersSet() || this.isAutoDetachesSet() || this.isConnection2PropertiesSet() || this.isConnectionDecoratorsSet() || this.isConnectionFactory2PropertiesSet() || this.isConnectionFactoryPropertiesSet() || this.isConnectionPropertiesSet() || this.isDataCachesSet() || this.isFetchGroupsSet() || this.isFilterListenersSet() || this.isJDBCListenersSet() || this.isQueryCachesSet() || this.isSchemataSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._AbstractStoreBrokerFactory = null;
               if (initOne) {
                  break;
               }
            case 42:
               this._AccessDictionary = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._AggregateListeners = new AggregateListenersBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._AggregateListeners);
               if (initOne) {
                  break;
               }
            case 2:
               this._AutoClear = "datastore";
               if (initOne) {
                  break;
               }
            case 3:
               this._AutoDetaches = new AutoDetachBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._AutoDetaches);
               if (initOne) {
                  break;
               }
            case 169:
               this._BatchingOperationOrderUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 128:
               this._CacheMap = null;
               if (initOne) {
                  break;
               }
            case 154:
               this._ClassTableJDBCSeq = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._ClientBrokerFactory = null;
               if (initOne) {
                  break;
               }
            case 135:
               this._ClusterRemoteCommitProvider = null;
               if (initOne) {
                  break;
               }
            case 83:
               this._CommonsLogFactory = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._Compatibility = null;
               if (initOne) {
                  break;
               }
            case 129:
               this._ConcurrentHashMap = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._Connection2DriverName = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._Connection2PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._Connection2PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._Connection2Properties = new PropertiesBeanImpl(this, 20);
               this._postCreate((AbstractDescriptorBean)this._Connection2Properties);
               if (initOne) {
                  break;
               }
            case 21:
               this._Connection2URL = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._Connection2UserName = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._ConnectionDecorators = new ConnectionDecoratorsBeanImpl(this, 23);
               this._postCreate((AbstractDescriptorBean)this._ConnectionDecorators);
               if (initOne) {
                  break;
               }
            case 24:
               this._ConnectionDriverName = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ConnectionFactory2Name = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._ConnectionFactory2Properties = new PropertiesBeanImpl(this, 26);
               this._postCreate((AbstractDescriptorBean)this._ConnectionFactory2Properties);
               if (initOne) {
                  break;
               }
            case 27:
               this._ConnectionFactoryMode = "local";
               if (initOne) {
                  break;
               }
            case 28:
               this._ConnectionFactoryName = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._ConnectionFactoryProperties = new PropertiesBeanImpl(this, 29);
               this._postCreate((AbstractDescriptorBean)this._ConnectionFactoryProperties);
               if (initOne) {
                  break;
               }
            case 30:
               this._ConnectionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._ConnectionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._ConnectionProperties = new PropertiesBeanImpl(this, 32);
               this._postCreate((AbstractDescriptorBean)this._ConnectionProperties);
               if (initOne) {
                  break;
               }
            case 33:
               this._ConnectionRetainMode = "on-demand";
               if (initOne) {
                  break;
               }
            case 34:
               this._ConnectionURL = null;
               if (initOne) {
                  break;
               }
            case 35:
               this._ConnectionUserName = null;
               if (initOne) {
                  break;
               }
            case 168:
               this._ConstraintUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._CustomBrokerFactory = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._CustomBrokerImpl = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._CustomClassResolver = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._CustomCompatibility = null;
               if (initOne) {
                  break;
               }
            case 40:
               this._CustomDataCacheManager = null;
               if (initOne) {
                  break;
               }
            case 61:
               this._CustomDetachState = null;
               if (initOne) {
                  break;
               }
            case 56:
               this._CustomDictionary = null;
               if (initOne) {
                  break;
               }
            case 65:
               this._CustomDriverDataSource = null;
               if (initOne) {
                  break;
               }
            case 81:
               this._CustomLockManager = null;
               if (initOne) {
                  break;
               }
            case 87:
               this._CustomLog = null;
               if (initOne) {
                  break;
               }
            case 94:
               this._CustomMappingDefaults = null;
               if (initOne) {
                  break;
               }
            case 101:
               this._CustomMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 106:
               this._CustomMetaDataFactory = null;
               if (initOne) {
                  break;
               }
            case 109:
               this._CustomMetaDataRepository = null;
               if (initOne) {
                  break;
               }
            case 118:
               this._CustomOrphanedKeyAction = null;
               if (initOne) {
                  break;
               }
            case 121:
               this._CustomPersistenceServer = null;
               if (initOne) {
                  break;
               }
            case 125:
               this._CustomProxyManager = null;
               if (initOne) {
                  break;
               }
            case 130:
               this._CustomQueryCompilationCache = null;
               if (initOne) {
                  break;
               }
            case 136:
               this._CustomRemoteCommitProvider = null;
               if (initOne) {
                  break;
               }
            case 162:
               this._CustomSQLFactory = null;
               if (initOne) {
                  break;
               }
            case 145:
               this._CustomSavepointManager = null;
               if (initOne) {
                  break;
               }
            case 152:
               this._CustomSchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 159:
               this._CustomSeq = null;
               if (initOne) {
                  break;
               }
            case 172:
               this._CustomUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 43:
               this._DB2Dictionary = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._DataCacheManagerImpl = null;
               if (initOne) {
                  break;
               }
            case 41:
               this._DataCacheTimeout = -1;
               if (initOne) {
                  break;
               }
            case 36:
               this._DataCaches = new DataCachesBeanImpl(this, 36);
               this._postCreate((AbstractDescriptorBean)this._DataCaches);
               if (initOne) {
                  break;
               }
            case 4:
               this._DefaultBrokerFactory = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._DefaultBrokerImpl = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._DefaultClassResolver = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._DefaultCompatibility = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._DefaultDataCacheManager = null;
               if (initOne) {
                  break;
               }
            case 57:
               this._DefaultDetachState = null;
               if (initOne) {
                  break;
               }
            case 62:
               this._DefaultDriverDataSource = null;
               if (initOne) {
                  break;
               }
            case 76:
               this._DefaultLockManager = null;
               if (initOne) {
                  break;
               }
            case 90:
               this._DefaultMappingDefaults = null;
               if (initOne) {
                  break;
               }
            case 102:
               this._DefaultMetaDataFactory = null;
               if (initOne) {
                  break;
               }
            case 107:
               this._DefaultMetaDataRepository = null;
               if (initOne) {
                  break;
               }
            case 114:
               this._DefaultOrphanedKeyAction = null;
               if (initOne) {
                  break;
               }
            case 122:
               this._DefaultProxyManager = null;
               if (initOne) {
                  break;
               }
            case 127:
               this._DefaultQueryCompilationCache = null;
               if (initOne) {
                  break;
               }
            case 160:
               this._DefaultSQLFactory = null;
               if (initOne) {
                  break;
               }
            case 141:
               this._DefaultSavepointManager = null;
               if (initOne) {
                  break;
               }
            case 147:
               this._DefaultSchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 167:
               this._DefaultUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 91:
               this._DeprecatedJDOMappingDefaults = null;
               if (initOne) {
                  break;
               }
            case 104:
               this._DeprecatedJDOMetaDataFactory = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._DerbyDictionary = null;
               if (initOne) {
                  break;
               }
            case 60:
               this._DetachOptionsAll = null;
               if (initOne) {
                  break;
               }
            case 59:
               this._DetachOptionsFetchGroups = null;
               if (initOne) {
                  break;
               }
            case 58:
               this._DetachOptionsLoaded = null;
               if (initOne) {
                  break;
               }
            case 66:
               this._DynamicDataStructs = false;
               if (initOne) {
                  break;
               }
            case 148:
               this._DynamicSchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 67:
               this._EagerFetchMode = "parallel";
               if (initOne) {
                  break;
               }
            case 45:
               this._EmpressDictionary = null;
               if (initOne) {
                  break;
               }
            case 116:
               this._ExceptionOrphanedKeyAction = null;
               if (initOne) {
                  break;
               }
            case 185:
               this._ExportProfiling = null;
               if (initOne) {
                  break;
               }
            case 95:
               this._ExtensionDeprecatedJDOMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 68:
               this._FetchBatchSize = -1;
               if (initOne) {
                  break;
               }
            case 69:
               this._FetchDirection = "forward";
               if (initOne) {
                  break;
               }
            case 70:
               this._FetchGroups = new FetchGroupsBeanImpl(this, 70);
               this._postCreate((AbstractDescriptorBean)this._FetchGroups);
               if (initOne) {
                  break;
               }
            case 149:
               this._FileSchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 71:
               this._FilterListeners = new FilterListenersBeanImpl(this, 71);
               this._postCreate((AbstractDescriptorBean)this._FilterListeners);
               if (initOne) {
                  break;
               }
            case 72:
               this._FlushBeforeQueries = "true";
               if (initOne) {
                  break;
               }
            case 46:
               this._FoxProDictionary = null;
               if (initOne) {
                  break;
               }
            case 179:
               this._GUIJMX = null;
               if (initOne) {
                  break;
               }
            case 186:
               this._GUIProfiling = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._HSQLDictionary = null;
               if (initOne) {
                  break;
               }
            case 119:
               this._HTTPTransport = null;
               if (initOne) {
                  break;
               }
            case 73:
               this._IgnoreChanges = false;
               if (initOne) {
                  break;
               }
            case 142:
               this._InMemorySavepointManager = null;
               if (initOne) {
                  break;
               }
            case 48:
               this._InformixDictionary = null;
               if (initOne) {
                  break;
               }
            case 74:
               this._InverseManager = null;
               if (initOne) {
                  break;
               }
            case 143:
               this._JDBC3SavepointManager = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._JDBCBrokerFactory = null;
               if (initOne) {
                  break;
               }
            case 75:
               this._JDBCListeners = new JDBCListenersBeanImpl(this, 75);
               this._postCreate((AbstractDescriptorBean)this._JDBCListeners);
               if (initOne) {
                  break;
               }
            case 103:
               this._JDOMetaDataFactory = null;
               if (initOne) {
                  break;
               }
            case 49:
               this._JDataStoreDictionary = null;
               if (initOne) {
                  break;
               }
            case 132:
               this._JMSRemoteCommitProvider = null;
               if (initOne) {
                  break;
               }
            case 180:
               this._JMX2JMX = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._KodoBroker = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._KodoDataCacheManager = null;
               if (initOne) {
                  break;
               }
            case 108:
               this._KodoMappingRepository = null;
               if (initOne) {
                  break;
               }
            case 96:
               this._KodoPersistenceMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 105:
               this._KodoPersistenceMetaDataFactory = null;
               if (initOne) {
                  break;
               }
            case 63:
               this._KodoPoolingDataSource = null;
               if (initOne) {
                  break;
               }
            case 161:
               this._KodoSQLFactory = null;
               if (initOne) {
                  break;
               }
            case 88:
               this._LRSSize = "query";
               if (initOne) {
                  break;
               }
            case 150:
               this._LazySchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 178:
               this._LocalJMX = null;
               if (initOne) {
                  break;
               }
            case 184:
               this._LocalProfiling = null;
               if (initOne) {
                  break;
               }
            case 82:
               this._LockTimeout = -1;
               if (initOne) {
                  break;
               }
            case 84:
               this._Log4JLogFactory = null;
               if (initOne) {
                  break;
               }
            case 85:
               this._LogFactoryImpl = null;
               if (initOne) {
                  break;
               }
            case 115:
               this._LogOrphanedKeyAction = null;
               if (initOne) {
                  break;
               }
            case 181:
               this._MX4J1JMX = null;
               if (initOne) {
                  break;
               }
            case 89:
               this._Mapping = null;
               if (initOne) {
                  break;
               }
            case 92:
               this._MappingDefaultsImpl = null;
               if (initOne) {
                  break;
               }
            case 97:
               this._MappingFileDeprecatedJDOMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 110:
               this._Multithreaded = false;
               if (initOne) {
                  break;
               }
            case 50:
               this._MySQLDictionary = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 155:
               this._NativeJDBCSeq = null;
               if (initOne) {
                  break;
               }
            case 177:
               this._NoneJMX = null;
               if (initOne) {
                  break;
               }
            case 78:
               this._NoneLockManager = null;
               if (initOne) {
                  break;
               }
            case 86:
               this._NoneLogFactory = null;
               if (initOne) {
                  break;
               }
            case 117:
               this._NoneOrphanedKeyAction = null;
               if (initOne) {
                  break;
               }
            case 183:
               this._NoneProfiling = null;
               if (initOne) {
                  break;
               }
            case 111:
               this._NontransactionalRead = true;
               if (initOne) {
                  break;
               }
            case 112:
               this._NontransactionalWrite = false;
               if (initOne) {
                  break;
               }
            case 98:
               this._ORMFileJDORMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 170:
               this._OperationOrderUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 113:
               this._Optimistic = true;
               if (initOne) {
                  break;
               }
            case 51:
               this._OracleDictionary = null;
               if (initOne) {
                  break;
               }
            case 144:
               this._OracleSavepointManager = null;
               if (initOne) {
                  break;
               }
            case 93:
               this._PersistenceMappingDefaults = null;
               if (initOne) {
                  break;
               }
            case 77:
               this._PessimisticLockManager = null;
               if (initOne) {
                  break;
               }
            case 52:
               this._PointbaseDictionary = null;
               if (initOne) {
                  break;
               }
            case 53:
               this._PostgresDictionary = null;
               if (initOne) {
                  break;
               }
            case 123:
               this._ProfilingProxyManager = null;
               if (initOne) {
                  break;
               }
            case 124:
               this._ProxyManagerImpl = null;
               if (initOne) {
                  break;
               }
            case 126:
               this._QueryCaches = new QueryCachesBeanImpl(this, 126);
               this._postCreate((AbstractDescriptorBean)this._QueryCaches);
               if (initOne) {
                  break;
               }
            case 131:
               this._ReadLockLevel = "read";
               if (initOne) {
                  break;
               }
            case 137:
               this._RestoreState = "none";
               if (initOne) {
                  break;
               }
            case 138:
               this._ResultSetType = "forward-only";
               if (initOne) {
                  break;
               }
            case 139:
               this._RetainState = true;
               if (initOne) {
                  break;
               }
            case 140:
               this._RetryClassRegistration = false;
               if (initOne) {
                  break;
               }
            case 54:
               this._SQLServerDictionary = null;
               if (initOne) {
                  break;
               }
            case 146:
               this._Schema = null;
               if (initOne) {
                  break;
               }
            case 153:
               this._Schemata = new SchemasBeanImpl(this, 153);
               this._postCreate((AbstractDescriptorBean)this._Schemata);
               if (initOne) {
                  break;
               }
            case 64:
               this._SimpleDriverDataSource = null;
               if (initOne) {
                  break;
               }
            case 79:
               this._SingleJVMExclusiveLockManager = null;
               if (initOne) {
                  break;
               }
            case 133:
               this._SingleJVMRemoteCommitProvider = null;
               if (initOne) {
                  break;
               }
            case 174:
               this._StackExecutionContextNameProvider = null;
               if (initOne) {
                  break;
               }
            case 163:
               this._SubclassFetchMode = "join";
               if (initOne) {
                  break;
               }
            case 55:
               this._SybaseDictionary = null;
               if (initOne) {
                  break;
               }
            case 164:
               this._SynchronizeMappings = "false";
               if (initOne) {
                  break;
               }
            case 134:
               this._TCPRemoteCommitProvider = null;
               if (initOne) {
                  break;
               }
            case 120:
               this._TCPTransport = null;
               if (initOne) {
                  break;
               }
            case 99:
               this._TableDeprecatedJDOMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 156:
               this._TableJDBCSeq = null;
               if (initOne) {
                  break;
               }
            case 100:
               this._TableJDORMappingFactory = null;
               if (initOne) {
                  break;
               }
            case 171:
               this._TableLockUpdateManager = null;
               if (initOne) {
                  break;
               }
            case 151:
               this._TableSchemaFactory = null;
               if (initOne) {
                  break;
               }
            case 157:
               this._TimeSeededSeq = null;
               if (initOne) {
                  break;
               }
            case 165:
               this._TransactionIsolation = "default";
               if (initOne) {
                  break;
               }
            case 166:
               this._TransactionMode = "managed";
               if (initOne) {
                  break;
               }
            case 175:
               this._TransactionNameExecutionContextNameProvider = null;
               if (initOne) {
                  break;
               }
            case 176:
               this._UserObjectExecutionContextNameProvider = null;
               if (initOne) {
                  break;
               }
            case 158:
               this._ValueTableJDBCSeq = null;
               if (initOne) {
                  break;
               }
            case 80:
               this._VersionLockManager = null;
               if (initOne) {
                  break;
               }
            case 182:
               this._WLS81JMX = null;
               if (initOne) {
                  break;
               }
            case 173:
               this._WriteLockLevel = "write";
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
            case 5:
            case 35:
            case 38:
            case 41:
            case 42:
            case 44:
            case 45:
            case 46:
            case 47:
            default:
               break;
            case 6:
               if (s.equals("schema")) {
                  return 146;
               }
               break;
            case 7:
               if (s.equals("gui-jmx")) {
                  return 179;
               }

               if (s.equals("mapping")) {
                  return 89;
               }

               if (s.equals("schemas")) {
                  return 153;
               }
               break;
            case 8:
               if (s.equals("jmx2-jmx")) {
                  return 180;
               }

               if (s.equals("lrs-size")) {
                  return 88;
               }

               if (s.equals("none-jmx")) {
                  return 177;
               }
               break;
            case 9:
               if (s.equals("cache-map")) {
                  return 128;
               }

               if (s.equals("local-jmx")) {
                  return 178;
               }

               if (s.equals("mx4j1-jmx")) {
                  return 181;
               }

               if (s.equals("wls81-jmx")) {
                  return 182;
               }
               break;
            case 10:
               if (s.equals("auto-clear")) {
                  return 2;
               }

               if (s.equals("custom-log")) {
                  return 87;
               }

               if (s.equals("custom-seq")) {
                  return 159;
               }

               if (s.equals("optimistic")) {
                  return 113;
               }
               break;
            case 11:
               if (s.equals("data-caches")) {
                  return 36;
               }

               if (s.equals("kodo-broker")) {
                  return 10;
               }
               break;
            case 12:
               if (s.equals("fetch-groups")) {
                  return 70;
               }

               if (s.equals("lock-timeout")) {
                  return 82;
               }

               if (s.equals("query-caches")) {
                  return 126;
               }

               if (s.equals("retain-state")) {
                  return 139;
               }
               break;
            case 13:
               if (s.equals("auto-detaches")) {
                  return 3;
               }

               if (s.equals("compatibility")) {
                  return 15;
               }

               if (s.equals("gui-profiling")) {
                  return 186;
               }

               if (s.equals("multithreaded")) {
                  return 110;
               }

               if (s.equals("restore-state")) {
                  return 137;
               }

               if (s.equals("tcp-transport")) {
                  return 120;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 34;
               }

               if (s.equals("db2-dictionary")) {
                  return 43;
               }

               if (s.equals("http-transport")) {
                  return 119;
               }

               if (s.equals("ignore-changes")) {
                  return 73;
               }

               if (s.equals("jdbc-listeners")) {
                  return 75;
               }

               if (s.equals("none-profiling")) {
                  return 183;
               }

               if (s.equals("table-jdbc-seq")) {
                  return 156;
               }
               break;
            case 15:
               if (s.equals("connection2-url")) {
                  return 21;
               }

               if (s.equals("fetch-direction")) {
                  return 69;
               }

               if (s.equals("hsql-dictionary")) {
                  return 47;
               }

               if (s.equals("inverse-manager")) {
                  return 74;
               }

               if (s.equals("local-profiling")) {
                  return 184;
               }

               if (s.equals("native-jdbc-seq")) {
                  return 155;
               }

               if (s.equals("read-lock-level")) {
                  return 131;
               }

               if (s.equals("result-set-type")) {
                  return 138;
               }

               if (s.equals("time-seeded-seq")) {
                  return 157;
               }
               break;
            case 16:
               if (s.equals("derby-dictionary")) {
                  return 44;
               }

               if (s.equals("eager-fetch-mode")) {
                  return 67;
               }

               if (s.equals("export-profiling")) {
                  return 185;
               }

               if (s.equals("fetch-batch-size")) {
                  return 68;
               }

               if (s.equals("filter-listeners")) {
                  return 71;
               }

               if (s.equals("kodo-sql-factory")) {
                  return 161;
               }

               if (s.equals("log-factory-impl")) {
                  return 85;
               }

               if (s.equals("mysql-dictionary")) {
                  return 50;
               }

               if (s.equals("none-log-factory")) {
                  return 86;
               }

               if (s.equals("transaction-mode")) {
                  return 166;
               }

               if (s.equals("write-lock-level")) {
                  return 173;
               }
               break;
            case 17:
               if (s.equals("access-dictionary")) {
                  return 42;
               }

               if (s.equals("custom-dictionary")) {
                  return 56;
               }

               if (s.equals("foxpro-dictionary")) {
                  return 46;
               }

               if (s.equals("log4j-log-factory")) {
                  return 84;
               }

               if (s.equals("none-lock-manager")) {
                  return 78;
               }

               if (s.equals("oracle-dictionary")) {
                  return 51;
               }

               if (s.equals("sybase-dictionary")) {
                  return 55;
               }
               break;
            case 18:
               if (s.equals("custom-broker-impl")) {
                  return 11;
               }

               if (s.equals("custom-sql-factory")) {
                  return 162;
               }

               if (s.equals("data-cache-timeout")) {
                  return 41;
               }

               if (s.equals("detach-options-all")) {
                  return 60;
               }

               if (s.equals("empress-dictionary")) {
                  return 45;
               }

               if (s.equals("proxy-manager-impl")) {
                  return 124;
               }
               break;
            case 19:
               if (s.equals("aggregate-listeners")) {
                  return 1;
               }

               if (s.equals("commons-log-factory")) {
                  return 83;
               }

               if (s.equals("concurrent-hash-map")) {
                  return 129;
               }

               if (s.equals("connection-password")) {
                  return 30;
               }

               if (s.equals("connection-password")) {
                  return 31;
               }

               if (s.equals("custom-detach-state")) {
                  return 61;
               }

               if (s.equals("custom-lock-manager")) {
                  return 81;
               }

               if (s.equals("default-broker-impl")) {
                  return 9;
               }

               if (s.equals("default-sql-factory")) {
                  return 160;
               }

               if (s.equals("file-schema-factory")) {
                  return 149;
               }

               if (s.equals("informix-dictionary")) {
                  return 48;
               }

               if (s.equals("jdbc-broker-factory")) {
                  return 7;
               }

               if (s.equals("lazy-schema-factory")) {
                  return 150;
               }

               if (s.equals("postgres-dictionary")) {
                  return 53;
               }

               if (s.equals("subclass-fetch-mode")) {
                  return 163;
               }
               break;
            case 20:
               if (s.equals("class-table-jdbc-seq")) {
                  return 154;
               }

               if (s.equals("connection2-password")) {
                  return 18;
               }

               if (s.equals("connection2-password")) {
                  return 19;
               }

               if (s.equals("connection-user-name")) {
                  return 35;
               }

               if (s.equals("custom-compatibility")) {
                  return 16;
               }

               if (s.equals("custom-proxy-manager")) {
                  return 125;
               }

               if (s.equals("default-detach-state")) {
                  return 57;
               }

               if (s.equals("default-lock-manager")) {
                  return 76;
               }

               if (s.equals("dynamic-data-structs")) {
                  return 66;
               }

               if (s.equals("flush-before-queries")) {
                  return 72;
               }

               if (s.equals("pointbase-dictionary")) {
                  return 52;
               }

               if (s.equals("synchronize-mappings")) {
                  return 164;
               }

               if (s.equals("table-schema-factory")) {
                  return 151;
               }

               if (s.equals("value-table-jdbc-seq")) {
                  return 158;
               }

               if (s.equals("version-lock-manager")) {
                  return 80;
               }
               break;
            case 21:
               if (s.equals("client-broker-factory")) {
                  return 6;
               }

               if (s.equals("connection2-user-name")) {
                  return 22;
               }

               if (s.equals("connection-decorators")) {
                  return 23;
               }

               if (s.equals("connection-properties")) {
                  return 32;
               }

               if (s.equals("custom-broker-factory")) {
                  return 8;
               }

               if (s.equals("custom-class-resolver")) {
                  return 13;
               }

               if (s.equals("custom-schema-factory")) {
                  return 152;
               }

               if (s.equals("custom-update-manager")) {
                  return 172;
               }

               if (s.equals("default-compatibility")) {
                  return 14;
               }

               if (s.equals("default-proxy-manager")) {
                  return 122;
               }

               if (s.equals("detach-options-loaded")) {
                  return 58;
               }

               if (s.equals("jdo-meta-data-factory")) {
                  return 103;
               }

               if (s.equals("jdatastore-dictionary")) {
                  return 49;
               }

               if (s.equals("mapping-defaults-impl")) {
                  return 92;
               }

               if (s.equals("nontransactional-read")) {
                  return 111;
               }

               if (s.equals("sql-server-dictionary")) {
                  return 54;
               }

               if (s.equals("transaction-isolation")) {
                  return 165;
               }
               break;
            case 22:
               if (s.equals("connection2-properties")) {
                  return 20;
               }

               if (s.equals("connection-driver-name")) {
                  return 24;
               }

               if (s.equals("connection-retain-mode")) {
                  return 33;
               }

               if (s.equals("custom-mapping-factory")) {
                  return 101;
               }

               if (s.equals("default-broker-factory")) {
                  return 4;
               }

               if (s.equals("default-class-resolver")) {
                  return 12;
               }

               if (s.equals("default-schema-factory")) {
                  return 147;
               }

               if (s.equals("default-update-manager")) {
                  return 167;
               }

               if (s.equals("dynamic-schema-factory")) {
                  return 148;
               }

               if (s.equals("nontransactional-write")) {
                  return 112;
               }
               break;
            case 23:
               if (s.equals("connection2-driver-name")) {
                  return 17;
               }

               if (s.equals("connection-factory-mode")) {
                  return 27;
               }

               if (s.equals("connection-factory-name")) {
                  return 28;
               }

               if (s.equals("custom-mapping-defaults")) {
                  return 94;
               }

               if (s.equals("data-cache-manager-impl")) {
                  return 39;
               }

               if (s.equals("jdbc3-savepoint-manager")) {
                  return 143;
               }

               if (s.equals("kodo-data-cache-manager")) {
                  return 38;
               }

               if (s.equals("kodo-mapping-repository")) {
                  return 108;
               }

               if (s.equals("log-orphaned-key-action")) {
                  return 115;
               }

               if (s.equals("profiling-proxy-manager")) {
                  return 123;
               }
               break;
            case 24:
               if (s.equals("connection-factory2-name")) {
                  return 25;
               }

               if (s.equals("custom-meta-data-factory")) {
                  return 106;
               }

               if (s.equals("custom-savepoint-manager")) {
                  return 145;
               }

               if (s.equals("default-mapping-defaults")) {
                  return 90;
               }

               if (s.equals("kodo-pooling-data-source")) {
                  return 63;
               }

               if (s.equals("none-orphaned-key-action")) {
                  return 117;
               }

               if (s.equals("oracle-savepoint-manager")) {
                  return 144;
               }

               if (s.equals("pessimistic-lock-manager")) {
                  return 77;
               }

               if (s.equals("retry-class-registration")) {
                  return 140;
               }
               break;
            case 25:
               if (s.equals("constraint-update-manager")) {
                  return 168;
               }

               if (s.equals("custom-data-cache-manager")) {
                  return 40;
               }

               if (s.equals("custom-driver-data-source")) {
                  return 65;
               }

               if (s.equals("custom-persistence-server")) {
                  return 121;
               }

               if (s.equals("default-meta-data-factory")) {
                  return 102;
               }

               if (s.equals("default-savepoint-manager")) {
                  return 141;
               }

               if (s.equals("simple-driver-data-source")) {
                  return 64;
               }

               if (s.equals("table-lock-update-manager")) {
                  return 171;
               }
               break;
            case 26:
               if (s.equals("custom-orphaned-key-action")) {
                  return 118;
               }

               if (s.equals("default-data-cache-manager")) {
                  return 37;
               }

               if (s.equals("default-driver-data-source")) {
                  return 62;
               }

               if (s.equals("jms-remote-commit-provider")) {
                  return 132;
               }

               if (s.equals("tcp-remote-commit-provider")) {
                  return 134;
               }

               if (s.equals("table-jdor-mapping-factory")) {
                  return 100;
               }
               break;
            case 27:
               if (s.equals("custom-meta-data-repository")) {
                  return 109;
               }

               if (s.equals("default-orphaned-key-action")) {
                  return 114;
               }

               if (s.equals("detach-options-fetch-groups")) {
                  return 59;
               }

               if (s.equals("in-memory-savepoint-manager")) {
                  return 142;
               }
               break;
            case 28:
               if (s.equals("default-meta-data-repository")) {
                  return 107;
               }

               if (s.equals("persistence-mapping-defaults")) {
                  return 93;
               }
               break;
            case 29:
               if (s.equals("abstract-store-broker-factory")) {
                  return 5;
               }

               if (s.equals("connection-factory-properties")) {
                  return 29;
               }

               if (s.equals("custom-remote-commit-provider")) {
                  return 136;
               }

               if (s.equals("exception-orphaned-key-action")) {
                  return 116;
               }

               if (s.equals("orm-file-jdor-mapping-factory")) {
                  return 98;
               }
               break;
            case 30:
               if (s.equals("cluster-remote-commit-provider")) {
                  return 135;
               }

               if (s.equals("connection-factory2-properties")) {
                  return 26;
               }

               if (s.equals("custom-query-compilation-cache")) {
                  return 130;
               }

               if (s.equals("operation-order-update-manager")) {
                  return 170;
               }
               break;
            case 31:
               if (s.equals("default-query-compilation-cache")) {
                  return 127;
               }

               if (s.equals("deprecated-jdo-mapping-defaults")) {
                  return 91;
               }
               break;
            case 32:
               if (s.equals("deprecated-jdo-meta-data-factory")) {
                  return 104;
               }

               if (s.equals("kodo-persistence-mapping-factory")) {
                  return 96;
               }
               break;
            case 33:
               if (s.equals("single-jvm-exclusive-lock-manager")) {
                  return 79;
               }

               if (s.equals("single-jvm-remote-commit-provider")) {
                  return 133;
               }
               break;
            case 34:
               if (s.equals("kodo-persistence-meta-data-factory")) {
                  return 105;
               }
               break;
            case 36:
               if (s.equals("table-deprecated-jdo-mapping-factory")) {
                  return 99;
               }
               break;
            case 37:
               if (s.equals("stack-execution-context-name-provider")) {
                  return 174;
               }
               break;
            case 39:
               if (s.equals("batching-operation-order-update-manager")) {
                  return 169;
               }
               break;
            case 40:
               if (s.equals("extension-deprecated-jdo-mapping-factory")) {
                  return 95;
               }
               break;
            case 43:
               if (s.equals("mapping-file-deprecated-jdo-mapping-factory")) {
                  return 97;
               }

               if (s.equals("user-object-execution-context-name-provider")) {
                  return 176;
               }
               break;
            case 48:
               if (s.equals("transaction-name-execution-context-name-provider")) {
                  return 175;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new AggregateListenersBeanImpl.SchemaHelper2();
            case 2:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 41:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 82:
            case 88:
            case 89:
            case 110:
            case 111:
            case 112:
            case 113:
            case 131:
            case 137:
            case 138:
            case 139:
            case 140:
            case 146:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            default:
               return super.getSchemaHelper(propIndex);
            case 3:
               return new AutoDetachBeanImpl.SchemaHelper2();
            case 4:
               return new DefaultBrokerFactoryBeanImpl.SchemaHelper2();
            case 5:
               return new AbstractStoreBrokerFactoryBeanImpl.SchemaHelper2();
            case 6:
               return new ClientBrokerFactoryBeanImpl.SchemaHelper2();
            case 7:
               return new JDBCBrokerFactoryBeanImpl.SchemaHelper2();
            case 8:
               return new CustomBrokerFactoryBeanImpl.SchemaHelper2();
            case 9:
               return new DefaultBrokerImplBeanImpl.SchemaHelper2();
            case 10:
               return new KodoBrokerBeanImpl.SchemaHelper2();
            case 11:
               return new CustomBrokerImplBeanImpl.SchemaHelper2();
            case 12:
               return new DefaultClassResolverBeanImpl.SchemaHelper2();
            case 13:
               return new CustomClassResolverBeanImpl.SchemaHelper2();
            case 14:
               return new DefaultCompatibilityBeanImpl.SchemaHelper2();
            case 15:
               return new KodoCompatibilityBeanImpl.SchemaHelper2();
            case 16:
               return new CustomCompatibilityBeanImpl.SchemaHelper2();
            case 20:
               return new PropertiesBeanImpl.SchemaHelper2();
            case 23:
               return new ConnectionDecoratorsBeanImpl.SchemaHelper2();
            case 26:
               return new PropertiesBeanImpl.SchemaHelper2();
            case 29:
               return new PropertiesBeanImpl.SchemaHelper2();
            case 32:
               return new PropertiesBeanImpl.SchemaHelper2();
            case 36:
               return new DataCachesBeanImpl.SchemaHelper2();
            case 37:
               return new DefaultDataCacheManagerBeanImpl.SchemaHelper2();
            case 38:
               return new KodoDataCacheManagerBeanImpl.SchemaHelper2();
            case 39:
               return new DataCacheManagerImplBeanImpl.SchemaHelper2();
            case 40:
               return new CustomDataCacheManagerBeanImpl.SchemaHelper2();
            case 42:
               return new AccessDictionaryBeanImpl.SchemaHelper2();
            case 43:
               return new DB2DictionaryBeanImpl.SchemaHelper2();
            case 44:
               return new DerbyDictionaryBeanImpl.SchemaHelper2();
            case 45:
               return new EmpressDictionaryBeanImpl.SchemaHelper2();
            case 46:
               return new FoxProDictionaryBeanImpl.SchemaHelper2();
            case 47:
               return new HSQLDictionaryBeanImpl.SchemaHelper2();
            case 48:
               return new InformixDictionaryBeanImpl.SchemaHelper2();
            case 49:
               return new JDataStoreDictionaryBeanImpl.SchemaHelper2();
            case 50:
               return new MySQLDictionaryBeanImpl.SchemaHelper2();
            case 51:
               return new OracleDictionaryBeanImpl.SchemaHelper2();
            case 52:
               return new PointbaseDictionaryBeanImpl.SchemaHelper2();
            case 53:
               return new PostgresDictionaryBeanImpl.SchemaHelper2();
            case 54:
               return new SQLServerDictionaryBeanImpl.SchemaHelper2();
            case 55:
               return new SybaseDictionaryBeanImpl.SchemaHelper2();
            case 56:
               return new CustomDictionaryBeanImpl.SchemaHelper2();
            case 57:
               return new DefaultDetachStateBeanImpl.SchemaHelper2();
            case 58:
               return new DetachOptionsLoadedBeanImpl.SchemaHelper2();
            case 59:
               return new DetachOptionsFetchGroupsBeanImpl.SchemaHelper2();
            case 60:
               return new DetachOptionsAllBeanImpl.SchemaHelper2();
            case 61:
               return new CustomDetachStateBeanImpl.SchemaHelper2();
            case 62:
               return new DefaultDriverDataSourceBeanImpl.SchemaHelper2();
            case 63:
               return new KodoPoolingDataSourceBeanImpl.SchemaHelper2();
            case 64:
               return new SimpleDriverDataSourceBeanImpl.SchemaHelper2();
            case 65:
               return new CustomDriverDataSourceBeanImpl.SchemaHelper2();
            case 70:
               return new FetchGroupsBeanImpl.SchemaHelper2();
            case 71:
               return new FilterListenersBeanImpl.SchemaHelper2();
            case 74:
               return new InverseManagerBeanImpl.SchemaHelper2();
            case 75:
               return new JDBCListenersBeanImpl.SchemaHelper2();
            case 76:
               return new DefaultLockManagerBeanImpl.SchemaHelper2();
            case 77:
               return new PessimisticLockManagerBeanImpl.SchemaHelper2();
            case 78:
               return new NoneLockManagerBeanImpl.SchemaHelper2();
            case 79:
               return new SingleJVMExclusiveLockManagerBeanImpl.SchemaHelper2();
            case 80:
               return new VersionLockManagerBeanImpl.SchemaHelper2();
            case 81:
               return new CustomLockManagerBeanImpl.SchemaHelper2();
            case 83:
               return new CommonsLogFactoryBeanImpl.SchemaHelper2();
            case 84:
               return new Log4JLogFactoryBeanImpl.SchemaHelper2();
            case 85:
               return new LogFactoryImplBeanImpl.SchemaHelper2();
            case 86:
               return new NoneLogFactoryBeanImpl.SchemaHelper2();
            case 87:
               return new CustomLogBeanImpl.SchemaHelper2();
            case 90:
               return new DefaultMappingDefaultsBeanImpl.SchemaHelper2();
            case 91:
               return new DeprecatedJDOMappingDefaultsBeanImpl.SchemaHelper2();
            case 92:
               return new MappingDefaultsImplBeanImpl.SchemaHelper2();
            case 93:
               return new PersistenceMappingDefaultsBeanImpl.SchemaHelper2();
            case 94:
               return new CustomMappingDefaultsBeanImpl.SchemaHelper2();
            case 95:
               return new ExtensionDeprecatedJDOMappingFactoryBeanImpl.SchemaHelper2();
            case 96:
               return new KodoPersistenceMappingFactoryBeanImpl.SchemaHelper2();
            case 97:
               return new MappingFileDeprecatedJDOMappingFactoryBeanImpl.SchemaHelper2();
            case 98:
               return new ORMFileJDORMappingFactoryBeanImpl.SchemaHelper2();
            case 99:
               return new TableDeprecatedJDOMappingFactoryBeanImpl.SchemaHelper2();
            case 100:
               return new TableJDORMappingFactoryBeanImpl.SchemaHelper2();
            case 101:
               return new CustomMappingFactoryBeanImpl.SchemaHelper2();
            case 102:
               return new DefaultMetaDataFactoryBeanImpl.SchemaHelper2();
            case 103:
               return new JDOMetaDataFactoryBeanImpl.SchemaHelper2();
            case 104:
               return new DeprecatedJDOMetaDataFactoryBeanImpl.SchemaHelper2();
            case 105:
               return new KodoPersistenceMetaDataFactoryBeanImpl.SchemaHelper2();
            case 106:
               return new CustomMetaDataFactoryBeanImpl.SchemaHelper2();
            case 107:
               return new DefaultMetaDataRepositoryBeanImpl.SchemaHelper2();
            case 108:
               return new KodoMappingRepositoryBeanImpl.SchemaHelper2();
            case 109:
               return new CustomMetaDataRepositoryBeanImpl.SchemaHelper2();
            case 114:
               return new DefaultOrphanedKeyActionBeanImpl.SchemaHelper2();
            case 115:
               return new LogOrphanedKeyActionBeanImpl.SchemaHelper2();
            case 116:
               return new ExceptionOrphanedKeyActionBeanImpl.SchemaHelper2();
            case 117:
               return new NoneOrphanedKeyActionBeanImpl.SchemaHelper2();
            case 118:
               return new CustomOrphanedKeyActionBeanImpl.SchemaHelper2();
            case 119:
               return new HTTPTransportBeanImpl.SchemaHelper2();
            case 120:
               return new TCPTransportBeanImpl.SchemaHelper2();
            case 121:
               return new CustomPersistenceServerBeanImpl.SchemaHelper2();
            case 122:
               return new DefaultProxyManagerBeanImpl.SchemaHelper2();
            case 123:
               return new ProfilingProxyManagerBeanImpl.SchemaHelper2();
            case 124:
               return new ProxyManagerImplBeanImpl.SchemaHelper2();
            case 125:
               return new CustomProxyManagerBeanImpl.SchemaHelper2();
            case 126:
               return new QueryCachesBeanImpl.SchemaHelper2();
            case 127:
               return new DefaultQueryCompilationCacheBeanImpl.SchemaHelper2();
            case 128:
               return new CacheMapBeanImpl.SchemaHelper2();
            case 129:
               return new ConcurrentHashMapBeanImpl.SchemaHelper2();
            case 130:
               return new CustomQueryCompilationCacheBeanImpl.SchemaHelper2();
            case 132:
               return new JMSRemoteCommitProviderBeanImpl.SchemaHelper2();
            case 133:
               return new SingleJVMRemoteCommitProviderBeanImpl.SchemaHelper2();
            case 134:
               return new TCPRemoteCommitProviderBeanImpl.SchemaHelper2();
            case 135:
               return new ClusterRemoteCommitProviderBeanImpl.SchemaHelper2();
            case 136:
               return new CustomRemoteCommitProviderBeanImpl.SchemaHelper2();
            case 141:
               return new DefaultSavepointManagerBeanImpl.SchemaHelper2();
            case 142:
               return new InMemorySavepointManagerBeanImpl.SchemaHelper2();
            case 143:
               return new JDBC3SavepointManagerBeanImpl.SchemaHelper2();
            case 144:
               return new OracleSavepointManagerBeanImpl.SchemaHelper2();
            case 145:
               return new CustomSavepointManagerBeanImpl.SchemaHelper2();
            case 147:
               return new DefaultSchemaFactoryBeanImpl.SchemaHelper2();
            case 148:
               return new DynamicSchemaFactoryBeanImpl.SchemaHelper2();
            case 149:
               return new FileSchemaFactoryBeanImpl.SchemaHelper2();
            case 150:
               return new LazySchemaFactoryBeanImpl.SchemaHelper2();
            case 151:
               return new TableSchemaFactoryBeanImpl.SchemaHelper2();
            case 152:
               return new CustomSchemaFactoryBeanImpl.SchemaHelper2();
            case 153:
               return new SchemasBeanImpl.SchemaHelper2();
            case 154:
               return new ClassTableJDBCSeqBeanImpl.SchemaHelper2();
            case 155:
               return new NativeJDBCSeqBeanImpl.SchemaHelper2();
            case 156:
               return new TableJDBCSeqBeanImpl.SchemaHelper2();
            case 157:
               return new TimeSeededSeqBeanImpl.SchemaHelper2();
            case 158:
               return new ValueTableJDBCSeqBeanImpl.SchemaHelper2();
            case 159:
               return new CustomSeqBeanImpl.SchemaHelper2();
            case 160:
               return new DefaultSQLFactoryBeanImpl.SchemaHelper2();
            case 161:
               return new KodoSQLFactoryBeanImpl.SchemaHelper2();
            case 162:
               return new CustomSQLFactoryBeanImpl.SchemaHelper2();
            case 167:
               return new DefaultUpdateManagerBeanImpl.SchemaHelper2();
            case 168:
               return new ConstraintUpdateManagerBeanImpl.SchemaHelper2();
            case 169:
               return new BatchingOperationOrderUpdateManagerBeanImpl.SchemaHelper2();
            case 170:
               return new OperationOrderUpdateManagerBeanImpl.SchemaHelper2();
            case 171:
               return new TableLockUpdateManagerBeanImpl.SchemaHelper2();
            case 172:
               return new CustomUpdateManagerBeanImpl.SchemaHelper2();
            case 174:
               return new StackExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 175:
               return new TransactionNameExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 176:
               return new UserObjectExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 177:
               return new NoneJMXBeanImpl.SchemaHelper2();
            case 178:
               return new LocalJMXBeanImpl.SchemaHelper2();
            case 179:
               return new GUIJMXBeanImpl.SchemaHelper2();
            case 180:
               return new JMX2JMXBeanImpl.SchemaHelper2();
            case 181:
               return new MX4J1JMXBeanImpl.SchemaHelper2();
            case 182:
               return new WLS81JMXBeanImpl.SchemaHelper2();
            case 183:
               return new NoneProfilingBeanImpl.SchemaHelper2();
            case 184:
               return new LocalProfilingBeanImpl.SchemaHelper2();
            case 185:
               return new ExportProfilingBeanImpl.SchemaHelper2();
            case 186:
               return new GUIProfilingBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "aggregate-listeners";
            case 2:
               return "auto-clear";
            case 3:
               return "auto-detaches";
            case 4:
               return "default-broker-factory";
            case 5:
               return "abstract-store-broker-factory";
            case 6:
               return "client-broker-factory";
            case 7:
               return "jdbc-broker-factory";
            case 8:
               return "custom-broker-factory";
            case 9:
               return "default-broker-impl";
            case 10:
               return "kodo-broker";
            case 11:
               return "custom-broker-impl";
            case 12:
               return "default-class-resolver";
            case 13:
               return "custom-class-resolver";
            case 14:
               return "default-compatibility";
            case 15:
               return "compatibility";
            case 16:
               return "custom-compatibility";
            case 17:
               return "connection2-driver-name";
            case 18:
               return "connection2-password";
            case 19:
               return "connection2-password";
            case 20:
               return "connection2-properties";
            case 21:
               return "connection2-url";
            case 22:
               return "connection2-user-name";
            case 23:
               return "connection-decorators";
            case 24:
               return "connection-driver-name";
            case 25:
               return "connection-factory2-name";
            case 26:
               return "connection-factory2-properties";
            case 27:
               return "connection-factory-mode";
            case 28:
               return "connection-factory-name";
            case 29:
               return "connection-factory-properties";
            case 30:
               return "connection-password";
            case 31:
               return "connection-password";
            case 32:
               return "connection-properties";
            case 33:
               return "connection-retain-mode";
            case 34:
               return "connection-url";
            case 35:
               return "connection-user-name";
            case 36:
               return "data-caches";
            case 37:
               return "default-data-cache-manager";
            case 38:
               return "kodo-data-cache-manager";
            case 39:
               return "data-cache-manager-impl";
            case 40:
               return "custom-data-cache-manager";
            case 41:
               return "data-cache-timeout";
            case 42:
               return "access-dictionary";
            case 43:
               return "db2-dictionary";
            case 44:
               return "derby-dictionary";
            case 45:
               return "empress-dictionary";
            case 46:
               return "foxpro-dictionary";
            case 47:
               return "hsql-dictionary";
            case 48:
               return "informix-dictionary";
            case 49:
               return "jdatastore-dictionary";
            case 50:
               return "mysql-dictionary";
            case 51:
               return "oracle-dictionary";
            case 52:
               return "pointbase-dictionary";
            case 53:
               return "postgres-dictionary";
            case 54:
               return "sql-server-dictionary";
            case 55:
               return "sybase-dictionary";
            case 56:
               return "custom-dictionary";
            case 57:
               return "default-detach-state";
            case 58:
               return "detach-options-loaded";
            case 59:
               return "detach-options-fetch-groups";
            case 60:
               return "detach-options-all";
            case 61:
               return "custom-detach-state";
            case 62:
               return "default-driver-data-source";
            case 63:
               return "kodo-pooling-data-source";
            case 64:
               return "simple-driver-data-source";
            case 65:
               return "custom-driver-data-source";
            case 66:
               return "dynamic-data-structs";
            case 67:
               return "eager-fetch-mode";
            case 68:
               return "fetch-batch-size";
            case 69:
               return "fetch-direction";
            case 70:
               return "fetch-groups";
            case 71:
               return "filter-listeners";
            case 72:
               return "flush-before-queries";
            case 73:
               return "ignore-changes";
            case 74:
               return "inverse-manager";
            case 75:
               return "jdbc-listeners";
            case 76:
               return "default-lock-manager";
            case 77:
               return "pessimistic-lock-manager";
            case 78:
               return "none-lock-manager";
            case 79:
               return "single-jvm-exclusive-lock-manager";
            case 80:
               return "version-lock-manager";
            case 81:
               return "custom-lock-manager";
            case 82:
               return "lock-timeout";
            case 83:
               return "commons-log-factory";
            case 84:
               return "log4j-log-factory";
            case 85:
               return "log-factory-impl";
            case 86:
               return "none-log-factory";
            case 87:
               return "custom-log";
            case 88:
               return "lrs-size";
            case 89:
               return "mapping";
            case 90:
               return "default-mapping-defaults";
            case 91:
               return "deprecated-jdo-mapping-defaults";
            case 92:
               return "mapping-defaults-impl";
            case 93:
               return "persistence-mapping-defaults";
            case 94:
               return "custom-mapping-defaults";
            case 95:
               return "extension-deprecated-jdo-mapping-factory";
            case 96:
               return "kodo-persistence-mapping-factory";
            case 97:
               return "mapping-file-deprecated-jdo-mapping-factory";
            case 98:
               return "orm-file-jdor-mapping-factory";
            case 99:
               return "table-deprecated-jdo-mapping-factory";
            case 100:
               return "table-jdor-mapping-factory";
            case 101:
               return "custom-mapping-factory";
            case 102:
               return "default-meta-data-factory";
            case 103:
               return "jdo-meta-data-factory";
            case 104:
               return "deprecated-jdo-meta-data-factory";
            case 105:
               return "kodo-persistence-meta-data-factory";
            case 106:
               return "custom-meta-data-factory";
            case 107:
               return "default-meta-data-repository";
            case 108:
               return "kodo-mapping-repository";
            case 109:
               return "custom-meta-data-repository";
            case 110:
               return "multithreaded";
            case 111:
               return "nontransactional-read";
            case 112:
               return "nontransactional-write";
            case 113:
               return "optimistic";
            case 114:
               return "default-orphaned-key-action";
            case 115:
               return "log-orphaned-key-action";
            case 116:
               return "exception-orphaned-key-action";
            case 117:
               return "none-orphaned-key-action";
            case 118:
               return "custom-orphaned-key-action";
            case 119:
               return "http-transport";
            case 120:
               return "tcp-transport";
            case 121:
               return "custom-persistence-server";
            case 122:
               return "default-proxy-manager";
            case 123:
               return "profiling-proxy-manager";
            case 124:
               return "proxy-manager-impl";
            case 125:
               return "custom-proxy-manager";
            case 126:
               return "query-caches";
            case 127:
               return "default-query-compilation-cache";
            case 128:
               return "cache-map";
            case 129:
               return "concurrent-hash-map";
            case 130:
               return "custom-query-compilation-cache";
            case 131:
               return "read-lock-level";
            case 132:
               return "jms-remote-commit-provider";
            case 133:
               return "single-jvm-remote-commit-provider";
            case 134:
               return "tcp-remote-commit-provider";
            case 135:
               return "cluster-remote-commit-provider";
            case 136:
               return "custom-remote-commit-provider";
            case 137:
               return "restore-state";
            case 138:
               return "result-set-type";
            case 139:
               return "retain-state";
            case 140:
               return "retry-class-registration";
            case 141:
               return "default-savepoint-manager";
            case 142:
               return "in-memory-savepoint-manager";
            case 143:
               return "jdbc3-savepoint-manager";
            case 144:
               return "oracle-savepoint-manager";
            case 145:
               return "custom-savepoint-manager";
            case 146:
               return "schema";
            case 147:
               return "default-schema-factory";
            case 148:
               return "dynamic-schema-factory";
            case 149:
               return "file-schema-factory";
            case 150:
               return "lazy-schema-factory";
            case 151:
               return "table-schema-factory";
            case 152:
               return "custom-schema-factory";
            case 153:
               return "schemas";
            case 154:
               return "class-table-jdbc-seq";
            case 155:
               return "native-jdbc-seq";
            case 156:
               return "table-jdbc-seq";
            case 157:
               return "time-seeded-seq";
            case 158:
               return "value-table-jdbc-seq";
            case 159:
               return "custom-seq";
            case 160:
               return "default-sql-factory";
            case 161:
               return "kodo-sql-factory";
            case 162:
               return "custom-sql-factory";
            case 163:
               return "subclass-fetch-mode";
            case 164:
               return "synchronize-mappings";
            case 165:
               return "transaction-isolation";
            case 166:
               return "transaction-mode";
            case 167:
               return "default-update-manager";
            case 168:
               return "constraint-update-manager";
            case 169:
               return "batching-operation-order-update-manager";
            case 170:
               return "operation-order-update-manager";
            case 171:
               return "table-lock-update-manager";
            case 172:
               return "custom-update-manager";
            case 173:
               return "write-lock-level";
            case 174:
               return "stack-execution-context-name-provider";
            case 175:
               return "transaction-name-execution-context-name-provider";
            case 176:
               return "user-object-execution-context-name-provider";
            case 177:
               return "none-jmx";
            case 178:
               return "local-jmx";
            case 179:
               return "gui-jmx";
            case 180:
               return "jmx2-jmx";
            case 181:
               return "mx4j1-jmx";
            case 182:
               return "wls81-jmx";
            case 183:
               return "none-profiling";
            case 184:
               return "local-profiling";
            case 185:
               return "export-profiling";
            case 186:
               return "gui-profiling";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            case 27:
            case 28:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 41:
            case 66:
            case 67:
            case 68:
            case 69:
            case 72:
            case 73:
            case 82:
            case 88:
            case 89:
            case 110:
            case 111:
            case 112:
            case 113:
            case 131:
            case 137:
            case 138:
            case 139:
            case 140:
            case 146:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            default:
               return super.isBean(propIndex);
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 20:
               return true;
            case 23:
               return true;
            case 26:
               return true;
            case 29:
               return true;
            case 32:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            case 40:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
               return true;
            case 57:
               return true;
            case 58:
               return true;
            case 59:
               return true;
            case 60:
               return true;
            case 61:
               return true;
            case 62:
               return true;
            case 63:
               return true;
            case 64:
               return true;
            case 65:
               return true;
            case 70:
               return true;
            case 71:
               return true;
            case 74:
               return true;
            case 75:
               return true;
            case 76:
               return true;
            case 77:
               return true;
            case 78:
               return true;
            case 79:
               return true;
            case 80:
               return true;
            case 81:
               return true;
            case 83:
               return true;
            case 84:
               return true;
            case 85:
               return true;
            case 86:
               return true;
            case 87:
               return true;
            case 90:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 93:
               return true;
            case 94:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 97:
               return true;
            case 98:
               return true;
            case 99:
               return true;
            case 100:
               return true;
            case 101:
               return true;
            case 102:
               return true;
            case 103:
               return true;
            case 104:
               return true;
            case 105:
               return true;
            case 106:
               return true;
            case 107:
               return true;
            case 108:
               return true;
            case 109:
               return true;
            case 114:
               return true;
            case 115:
               return true;
            case 116:
               return true;
            case 117:
               return true;
            case 118:
               return true;
            case 119:
               return true;
            case 120:
               return true;
            case 121:
               return true;
            case 122:
               return true;
            case 123:
               return true;
            case 124:
               return true;
            case 125:
               return true;
            case 126:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 129:
               return true;
            case 130:
               return true;
            case 132:
               return true;
            case 133:
               return true;
            case 134:
               return true;
            case 135:
               return true;
            case 136:
               return true;
            case 141:
               return true;
            case 142:
               return true;
            case 143:
               return true;
            case 144:
               return true;
            case 145:
               return true;
            case 147:
               return true;
            case 148:
               return true;
            case 149:
               return true;
            case 150:
               return true;
            case 151:
               return true;
            case 152:
               return true;
            case 153:
               return true;
            case 154:
               return true;
            case 155:
               return true;
            case 156:
               return true;
            case 157:
               return true;
            case 158:
               return true;
            case 159:
               return true;
            case 160:
               return true;
            case 161:
               return true;
            case 162:
               return true;
            case 167:
               return true;
            case 168:
               return true;
            case 169:
               return true;
            case 170:
               return true;
            case 171:
               return true;
            case 172:
               return true;
            case 174:
               return true;
            case 175:
               return true;
            case 176:
               return true;
            case 177:
               return true;
            case 178:
               return true;
            case 179:
               return true;
            case 180:
               return true;
            case 181:
               return true;
            case 182:
               return true;
            case 183:
               return true;
            case 184:
               return true;
            case 185:
               return true;
            case 186:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
            case 25:
            case 27:
            case 28:
            case 37:
            case 38:
            case 39:
            case 40:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            default:
               return super.isConfigurable(propIndex);
            case 24:
               return true;
            case 26:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
            case 36:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            case 47:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
               return true;
            case 68:
               return true;
            case 82:
               return true;
            case 126:
               return true;
            case 132:
               return true;
            case 133:
               return true;
            case 134:
               return true;
            case 135:
               return true;
            case 136:
               return true;
            case 146:
               return true;
            case 153:
               return true;
            case 165:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceUnitConfigurationBeanImpl bean;

      protected Helper(PersistenceUnitConfigurationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "AggregateListeners";
            case 2:
               return "AutoClear";
            case 3:
               return "AutoDetaches";
            case 4:
               return "DefaultBrokerFactory";
            case 5:
               return "AbstractStoreBrokerFactory";
            case 6:
               return "ClientBrokerFactory";
            case 7:
               return "JDBCBrokerFactory";
            case 8:
               return "CustomBrokerFactory";
            case 9:
               return "DefaultBrokerImpl";
            case 10:
               return "KodoBroker";
            case 11:
               return "CustomBrokerImpl";
            case 12:
               return "DefaultClassResolver";
            case 13:
               return "CustomClassResolver";
            case 14:
               return "DefaultCompatibility";
            case 15:
               return "Compatibility";
            case 16:
               return "CustomCompatibility";
            case 17:
               return "Connection2DriverName";
            case 18:
               return "Connection2Password";
            case 19:
               return "Connection2PasswordEncrypted";
            case 20:
               return "Connection2Properties";
            case 21:
               return "Connection2URL";
            case 22:
               return "Connection2UserName";
            case 23:
               return "ConnectionDecorators";
            case 24:
               return "ConnectionDriverName";
            case 25:
               return "ConnectionFactory2Name";
            case 26:
               return "ConnectionFactory2Properties";
            case 27:
               return "ConnectionFactoryMode";
            case 28:
               return "ConnectionFactoryName";
            case 29:
               return "ConnectionFactoryProperties";
            case 30:
               return "ConnectionPassword";
            case 31:
               return "ConnectionPasswordEncrypted";
            case 32:
               return "ConnectionProperties";
            case 33:
               return "ConnectionRetainMode";
            case 34:
               return "ConnectionURL";
            case 35:
               return "ConnectionUserName";
            case 36:
               return "DataCaches";
            case 37:
               return "DefaultDataCacheManager";
            case 38:
               return "KodoDataCacheManager";
            case 39:
               return "DataCacheManagerImpl";
            case 40:
               return "CustomDataCacheManager";
            case 41:
               return "DataCacheTimeout";
            case 42:
               return "AccessDictionary";
            case 43:
               return "DB2Dictionary";
            case 44:
               return "DerbyDictionary";
            case 45:
               return "EmpressDictionary";
            case 46:
               return "FoxProDictionary";
            case 47:
               return "HSQLDictionary";
            case 48:
               return "InformixDictionary";
            case 49:
               return "JDataStoreDictionary";
            case 50:
               return "MySQLDictionary";
            case 51:
               return "OracleDictionary";
            case 52:
               return "PointbaseDictionary";
            case 53:
               return "PostgresDictionary";
            case 54:
               return "SQLServerDictionary";
            case 55:
               return "SybaseDictionary";
            case 56:
               return "CustomDictionary";
            case 57:
               return "DefaultDetachState";
            case 58:
               return "DetachOptionsLoaded";
            case 59:
               return "DetachOptionsFetchGroups";
            case 60:
               return "DetachOptionsAll";
            case 61:
               return "CustomDetachState";
            case 62:
               return "DefaultDriverDataSource";
            case 63:
               return "KodoPoolingDataSource";
            case 64:
               return "SimpleDriverDataSource";
            case 65:
               return "CustomDriverDataSource";
            case 66:
               return "DynamicDataStructs";
            case 67:
               return "EagerFetchMode";
            case 68:
               return "FetchBatchSize";
            case 69:
               return "FetchDirection";
            case 70:
               return "FetchGroups";
            case 71:
               return "FilterListeners";
            case 72:
               return "FlushBeforeQueries";
            case 73:
               return "IgnoreChanges";
            case 74:
               return "InverseManager";
            case 75:
               return "JDBCListeners";
            case 76:
               return "DefaultLockManager";
            case 77:
               return "PessimisticLockManager";
            case 78:
               return "NoneLockManager";
            case 79:
               return "SingleJVMExclusiveLockManager";
            case 80:
               return "VersionLockManager";
            case 81:
               return "CustomLockManager";
            case 82:
               return "LockTimeout";
            case 83:
               return "CommonsLogFactory";
            case 84:
               return "Log4JLogFactory";
            case 85:
               return "LogFactoryImpl";
            case 86:
               return "NoneLogFactory";
            case 87:
               return "CustomLog";
            case 88:
               return "LRSSize";
            case 89:
               return "Mapping";
            case 90:
               return "DefaultMappingDefaults";
            case 91:
               return "DeprecatedJDOMappingDefaults";
            case 92:
               return "MappingDefaultsImpl";
            case 93:
               return "PersistenceMappingDefaults";
            case 94:
               return "CustomMappingDefaults";
            case 95:
               return "ExtensionDeprecatedJDOMappingFactory";
            case 96:
               return "KodoPersistenceMappingFactory";
            case 97:
               return "MappingFileDeprecatedJDOMappingFactory";
            case 98:
               return "ORMFileJDORMappingFactory";
            case 99:
               return "TableDeprecatedJDOMappingFactory";
            case 100:
               return "TableJDORMappingFactory";
            case 101:
               return "CustomMappingFactory";
            case 102:
               return "DefaultMetaDataFactory";
            case 103:
               return "JDOMetaDataFactory";
            case 104:
               return "DeprecatedJDOMetaDataFactory";
            case 105:
               return "KodoPersistenceMetaDataFactory";
            case 106:
               return "CustomMetaDataFactory";
            case 107:
               return "DefaultMetaDataRepository";
            case 108:
               return "KodoMappingRepository";
            case 109:
               return "CustomMetaDataRepository";
            case 110:
               return "Multithreaded";
            case 111:
               return "NontransactionalRead";
            case 112:
               return "NontransactionalWrite";
            case 113:
               return "Optimistic";
            case 114:
               return "DefaultOrphanedKeyAction";
            case 115:
               return "LogOrphanedKeyAction";
            case 116:
               return "ExceptionOrphanedKeyAction";
            case 117:
               return "NoneOrphanedKeyAction";
            case 118:
               return "CustomOrphanedKeyAction";
            case 119:
               return "HTTPTransport";
            case 120:
               return "TCPTransport";
            case 121:
               return "CustomPersistenceServer";
            case 122:
               return "DefaultProxyManager";
            case 123:
               return "ProfilingProxyManager";
            case 124:
               return "ProxyManagerImpl";
            case 125:
               return "CustomProxyManager";
            case 126:
               return "QueryCaches";
            case 127:
               return "DefaultQueryCompilationCache";
            case 128:
               return "CacheMap";
            case 129:
               return "ConcurrentHashMap";
            case 130:
               return "CustomQueryCompilationCache";
            case 131:
               return "ReadLockLevel";
            case 132:
               return "JMSRemoteCommitProvider";
            case 133:
               return "SingleJVMRemoteCommitProvider";
            case 134:
               return "TCPRemoteCommitProvider";
            case 135:
               return "ClusterRemoteCommitProvider";
            case 136:
               return "CustomRemoteCommitProvider";
            case 137:
               return "RestoreState";
            case 138:
               return "ResultSetType";
            case 139:
               return "RetainState";
            case 140:
               return "RetryClassRegistration";
            case 141:
               return "DefaultSavepointManager";
            case 142:
               return "InMemorySavepointManager";
            case 143:
               return "JDBC3SavepointManager";
            case 144:
               return "OracleSavepointManager";
            case 145:
               return "CustomSavepointManager";
            case 146:
               return "Schema";
            case 147:
               return "DefaultSchemaFactory";
            case 148:
               return "DynamicSchemaFactory";
            case 149:
               return "FileSchemaFactory";
            case 150:
               return "LazySchemaFactory";
            case 151:
               return "TableSchemaFactory";
            case 152:
               return "CustomSchemaFactory";
            case 153:
               return "Schemata";
            case 154:
               return "ClassTableJDBCSeq";
            case 155:
               return "NativeJDBCSeq";
            case 156:
               return "TableJDBCSeq";
            case 157:
               return "TimeSeededSeq";
            case 158:
               return "ValueTableJDBCSeq";
            case 159:
               return "CustomSeq";
            case 160:
               return "DefaultSQLFactory";
            case 161:
               return "KodoSQLFactory";
            case 162:
               return "CustomSQLFactory";
            case 163:
               return "SubclassFetchMode";
            case 164:
               return "SynchronizeMappings";
            case 165:
               return "TransactionIsolation";
            case 166:
               return "TransactionMode";
            case 167:
               return "DefaultUpdateManager";
            case 168:
               return "ConstraintUpdateManager";
            case 169:
               return "BatchingOperationOrderUpdateManager";
            case 170:
               return "OperationOrderUpdateManager";
            case 171:
               return "TableLockUpdateManager";
            case 172:
               return "CustomUpdateManager";
            case 173:
               return "WriteLockLevel";
            case 174:
               return "StackExecutionContextNameProvider";
            case 175:
               return "TransactionNameExecutionContextNameProvider";
            case 176:
               return "UserObjectExecutionContextNameProvider";
            case 177:
               return "NoneJMX";
            case 178:
               return "LocalJMX";
            case 179:
               return "GUIJMX";
            case 180:
               return "JMX2JMX";
            case 181:
               return "MX4J1JMX";
            case 182:
               return "WLS81JMX";
            case 183:
               return "NoneProfiling";
            case 184:
               return "LocalProfiling";
            case 185:
               return "ExportProfiling";
            case 186:
               return "GUIProfiling";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AbstractStoreBrokerFactory")) {
            return 5;
         } else if (propName.equals("AccessDictionary")) {
            return 42;
         } else if (propName.equals("AggregateListeners")) {
            return 1;
         } else if (propName.equals("AutoClear")) {
            return 2;
         } else if (propName.equals("AutoDetaches")) {
            return 3;
         } else if (propName.equals("BatchingOperationOrderUpdateManager")) {
            return 169;
         } else if (propName.equals("CacheMap")) {
            return 128;
         } else if (propName.equals("ClassTableJDBCSeq")) {
            return 154;
         } else if (propName.equals("ClientBrokerFactory")) {
            return 6;
         } else if (propName.equals("ClusterRemoteCommitProvider")) {
            return 135;
         } else if (propName.equals("CommonsLogFactory")) {
            return 83;
         } else if (propName.equals("Compatibility")) {
            return 15;
         } else if (propName.equals("ConcurrentHashMap")) {
            return 129;
         } else if (propName.equals("Connection2DriverName")) {
            return 17;
         } else if (propName.equals("Connection2Password")) {
            return 18;
         } else if (propName.equals("Connection2PasswordEncrypted")) {
            return 19;
         } else if (propName.equals("Connection2Properties")) {
            return 20;
         } else if (propName.equals("Connection2URL")) {
            return 21;
         } else if (propName.equals("Connection2UserName")) {
            return 22;
         } else if (propName.equals("ConnectionDecorators")) {
            return 23;
         } else if (propName.equals("ConnectionDriverName")) {
            return 24;
         } else if (propName.equals("ConnectionFactory2Name")) {
            return 25;
         } else if (propName.equals("ConnectionFactory2Properties")) {
            return 26;
         } else if (propName.equals("ConnectionFactoryMode")) {
            return 27;
         } else if (propName.equals("ConnectionFactoryName")) {
            return 28;
         } else if (propName.equals("ConnectionFactoryProperties")) {
            return 29;
         } else if (propName.equals("ConnectionPassword")) {
            return 30;
         } else if (propName.equals("ConnectionPasswordEncrypted")) {
            return 31;
         } else if (propName.equals("ConnectionProperties")) {
            return 32;
         } else if (propName.equals("ConnectionRetainMode")) {
            return 33;
         } else if (propName.equals("ConnectionURL")) {
            return 34;
         } else if (propName.equals("ConnectionUserName")) {
            return 35;
         } else if (propName.equals("ConstraintUpdateManager")) {
            return 168;
         } else if (propName.equals("CustomBrokerFactory")) {
            return 8;
         } else if (propName.equals("CustomBrokerImpl")) {
            return 11;
         } else if (propName.equals("CustomClassResolver")) {
            return 13;
         } else if (propName.equals("CustomCompatibility")) {
            return 16;
         } else if (propName.equals("CustomDataCacheManager")) {
            return 40;
         } else if (propName.equals("CustomDetachState")) {
            return 61;
         } else if (propName.equals("CustomDictionary")) {
            return 56;
         } else if (propName.equals("CustomDriverDataSource")) {
            return 65;
         } else if (propName.equals("CustomLockManager")) {
            return 81;
         } else if (propName.equals("CustomLog")) {
            return 87;
         } else if (propName.equals("CustomMappingDefaults")) {
            return 94;
         } else if (propName.equals("CustomMappingFactory")) {
            return 101;
         } else if (propName.equals("CustomMetaDataFactory")) {
            return 106;
         } else if (propName.equals("CustomMetaDataRepository")) {
            return 109;
         } else if (propName.equals("CustomOrphanedKeyAction")) {
            return 118;
         } else if (propName.equals("CustomPersistenceServer")) {
            return 121;
         } else if (propName.equals("CustomProxyManager")) {
            return 125;
         } else if (propName.equals("CustomQueryCompilationCache")) {
            return 130;
         } else if (propName.equals("CustomRemoteCommitProvider")) {
            return 136;
         } else if (propName.equals("CustomSQLFactory")) {
            return 162;
         } else if (propName.equals("CustomSavepointManager")) {
            return 145;
         } else if (propName.equals("CustomSchemaFactory")) {
            return 152;
         } else if (propName.equals("CustomSeq")) {
            return 159;
         } else if (propName.equals("CustomUpdateManager")) {
            return 172;
         } else if (propName.equals("DB2Dictionary")) {
            return 43;
         } else if (propName.equals("DataCacheManagerImpl")) {
            return 39;
         } else if (propName.equals("DataCacheTimeout")) {
            return 41;
         } else if (propName.equals("DataCaches")) {
            return 36;
         } else if (propName.equals("DefaultBrokerFactory")) {
            return 4;
         } else if (propName.equals("DefaultBrokerImpl")) {
            return 9;
         } else if (propName.equals("DefaultClassResolver")) {
            return 12;
         } else if (propName.equals("DefaultCompatibility")) {
            return 14;
         } else if (propName.equals("DefaultDataCacheManager")) {
            return 37;
         } else if (propName.equals("DefaultDetachState")) {
            return 57;
         } else if (propName.equals("DefaultDriverDataSource")) {
            return 62;
         } else if (propName.equals("DefaultLockManager")) {
            return 76;
         } else if (propName.equals("DefaultMappingDefaults")) {
            return 90;
         } else if (propName.equals("DefaultMetaDataFactory")) {
            return 102;
         } else if (propName.equals("DefaultMetaDataRepository")) {
            return 107;
         } else if (propName.equals("DefaultOrphanedKeyAction")) {
            return 114;
         } else if (propName.equals("DefaultProxyManager")) {
            return 122;
         } else if (propName.equals("DefaultQueryCompilationCache")) {
            return 127;
         } else if (propName.equals("DefaultSQLFactory")) {
            return 160;
         } else if (propName.equals("DefaultSavepointManager")) {
            return 141;
         } else if (propName.equals("DefaultSchemaFactory")) {
            return 147;
         } else if (propName.equals("DefaultUpdateManager")) {
            return 167;
         } else if (propName.equals("DeprecatedJDOMappingDefaults")) {
            return 91;
         } else if (propName.equals("DeprecatedJDOMetaDataFactory")) {
            return 104;
         } else if (propName.equals("DerbyDictionary")) {
            return 44;
         } else if (propName.equals("DetachOptionsAll")) {
            return 60;
         } else if (propName.equals("DetachOptionsFetchGroups")) {
            return 59;
         } else if (propName.equals("DetachOptionsLoaded")) {
            return 58;
         } else if (propName.equals("DynamicDataStructs")) {
            return 66;
         } else if (propName.equals("DynamicSchemaFactory")) {
            return 148;
         } else if (propName.equals("EagerFetchMode")) {
            return 67;
         } else if (propName.equals("EmpressDictionary")) {
            return 45;
         } else if (propName.equals("ExceptionOrphanedKeyAction")) {
            return 116;
         } else if (propName.equals("ExportProfiling")) {
            return 185;
         } else if (propName.equals("ExtensionDeprecatedJDOMappingFactory")) {
            return 95;
         } else if (propName.equals("FetchBatchSize")) {
            return 68;
         } else if (propName.equals("FetchDirection")) {
            return 69;
         } else if (propName.equals("FetchGroups")) {
            return 70;
         } else if (propName.equals("FileSchemaFactory")) {
            return 149;
         } else if (propName.equals("FilterListeners")) {
            return 71;
         } else if (propName.equals("FlushBeforeQueries")) {
            return 72;
         } else if (propName.equals("FoxProDictionary")) {
            return 46;
         } else if (propName.equals("GUIJMX")) {
            return 179;
         } else if (propName.equals("GUIProfiling")) {
            return 186;
         } else if (propName.equals("HSQLDictionary")) {
            return 47;
         } else if (propName.equals("HTTPTransport")) {
            return 119;
         } else if (propName.equals("IgnoreChanges")) {
            return 73;
         } else if (propName.equals("InMemorySavepointManager")) {
            return 142;
         } else if (propName.equals("InformixDictionary")) {
            return 48;
         } else if (propName.equals("InverseManager")) {
            return 74;
         } else if (propName.equals("JDBC3SavepointManager")) {
            return 143;
         } else if (propName.equals("JDBCBrokerFactory")) {
            return 7;
         } else if (propName.equals("JDBCListeners")) {
            return 75;
         } else if (propName.equals("JDOMetaDataFactory")) {
            return 103;
         } else if (propName.equals("JDataStoreDictionary")) {
            return 49;
         } else if (propName.equals("JMSRemoteCommitProvider")) {
            return 132;
         } else if (propName.equals("JMX2JMX")) {
            return 180;
         } else if (propName.equals("KodoBroker")) {
            return 10;
         } else if (propName.equals("KodoDataCacheManager")) {
            return 38;
         } else if (propName.equals("KodoMappingRepository")) {
            return 108;
         } else if (propName.equals("KodoPersistenceMappingFactory")) {
            return 96;
         } else if (propName.equals("KodoPersistenceMetaDataFactory")) {
            return 105;
         } else if (propName.equals("KodoPoolingDataSource")) {
            return 63;
         } else if (propName.equals("KodoSQLFactory")) {
            return 161;
         } else if (propName.equals("LRSSize")) {
            return 88;
         } else if (propName.equals("LazySchemaFactory")) {
            return 150;
         } else if (propName.equals("LocalJMX")) {
            return 178;
         } else if (propName.equals("LocalProfiling")) {
            return 184;
         } else if (propName.equals("LockTimeout")) {
            return 82;
         } else if (propName.equals("Log4JLogFactory")) {
            return 84;
         } else if (propName.equals("LogFactoryImpl")) {
            return 85;
         } else if (propName.equals("LogOrphanedKeyAction")) {
            return 115;
         } else if (propName.equals("MX4J1JMX")) {
            return 181;
         } else if (propName.equals("Mapping")) {
            return 89;
         } else if (propName.equals("MappingDefaultsImpl")) {
            return 92;
         } else if (propName.equals("MappingFileDeprecatedJDOMappingFactory")) {
            return 97;
         } else if (propName.equals("Multithreaded")) {
            return 110;
         } else if (propName.equals("MySQLDictionary")) {
            return 50;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("NativeJDBCSeq")) {
            return 155;
         } else if (propName.equals("NoneJMX")) {
            return 177;
         } else if (propName.equals("NoneLockManager")) {
            return 78;
         } else if (propName.equals("NoneLogFactory")) {
            return 86;
         } else if (propName.equals("NoneOrphanedKeyAction")) {
            return 117;
         } else if (propName.equals("NoneProfiling")) {
            return 183;
         } else if (propName.equals("NontransactionalRead")) {
            return 111;
         } else if (propName.equals("NontransactionalWrite")) {
            return 112;
         } else if (propName.equals("ORMFileJDORMappingFactory")) {
            return 98;
         } else if (propName.equals("OperationOrderUpdateManager")) {
            return 170;
         } else if (propName.equals("Optimistic")) {
            return 113;
         } else if (propName.equals("OracleDictionary")) {
            return 51;
         } else if (propName.equals("OracleSavepointManager")) {
            return 144;
         } else if (propName.equals("PersistenceMappingDefaults")) {
            return 93;
         } else if (propName.equals("PessimisticLockManager")) {
            return 77;
         } else if (propName.equals("PointbaseDictionary")) {
            return 52;
         } else if (propName.equals("PostgresDictionary")) {
            return 53;
         } else if (propName.equals("ProfilingProxyManager")) {
            return 123;
         } else if (propName.equals("ProxyManagerImpl")) {
            return 124;
         } else if (propName.equals("QueryCaches")) {
            return 126;
         } else if (propName.equals("ReadLockLevel")) {
            return 131;
         } else if (propName.equals("RestoreState")) {
            return 137;
         } else if (propName.equals("ResultSetType")) {
            return 138;
         } else if (propName.equals("RetainState")) {
            return 139;
         } else if (propName.equals("RetryClassRegistration")) {
            return 140;
         } else if (propName.equals("SQLServerDictionary")) {
            return 54;
         } else if (propName.equals("Schema")) {
            return 146;
         } else if (propName.equals("Schemata")) {
            return 153;
         } else if (propName.equals("SimpleDriverDataSource")) {
            return 64;
         } else if (propName.equals("SingleJVMExclusiveLockManager")) {
            return 79;
         } else if (propName.equals("SingleJVMRemoteCommitProvider")) {
            return 133;
         } else if (propName.equals("StackExecutionContextNameProvider")) {
            return 174;
         } else if (propName.equals("SubclassFetchMode")) {
            return 163;
         } else if (propName.equals("SybaseDictionary")) {
            return 55;
         } else if (propName.equals("SynchronizeMappings")) {
            return 164;
         } else if (propName.equals("TCPRemoteCommitProvider")) {
            return 134;
         } else if (propName.equals("TCPTransport")) {
            return 120;
         } else if (propName.equals("TableDeprecatedJDOMappingFactory")) {
            return 99;
         } else if (propName.equals("TableJDBCSeq")) {
            return 156;
         } else if (propName.equals("TableJDORMappingFactory")) {
            return 100;
         } else if (propName.equals("TableLockUpdateManager")) {
            return 171;
         } else if (propName.equals("TableSchemaFactory")) {
            return 151;
         } else if (propName.equals("TimeSeededSeq")) {
            return 157;
         } else if (propName.equals("TransactionIsolation")) {
            return 165;
         } else if (propName.equals("TransactionMode")) {
            return 166;
         } else if (propName.equals("TransactionNameExecutionContextNameProvider")) {
            return 175;
         } else if (propName.equals("UserObjectExecutionContextNameProvider")) {
            return 176;
         } else if (propName.equals("ValueTableJDBCSeq")) {
            return 158;
         } else if (propName.equals("VersionLockManager")) {
            return 80;
         } else if (propName.equals("WLS81JMX")) {
            return 182;
         } else {
            return propName.equals("WriteLockLevel") ? 173 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAbstractStoreBrokerFactory() != null) {
            iterators.add(new ArrayIterator(new AbstractStoreBrokerFactoryBean[]{this.bean.getAbstractStoreBrokerFactory()}));
         }

         if (this.bean.getAccessDictionary() != null) {
            iterators.add(new ArrayIterator(new AccessDictionaryBean[]{this.bean.getAccessDictionary()}));
         }

         if (this.bean.getAggregateListeners() != null) {
            iterators.add(new ArrayIterator(new AggregateListenersBean[]{this.bean.getAggregateListeners()}));
         }

         if (this.bean.getAutoDetaches() != null) {
            iterators.add(new ArrayIterator(new AutoDetachBean[]{this.bean.getAutoDetaches()}));
         }

         if (this.bean.getBatchingOperationOrderUpdateManager() != null) {
            iterators.add(new ArrayIterator(new BatchingOperationOrderUpdateManagerBean[]{this.bean.getBatchingOperationOrderUpdateManager()}));
         }

         if (this.bean.getCacheMap() != null) {
            iterators.add(new ArrayIterator(new CacheMapBean[]{this.bean.getCacheMap()}));
         }

         if (this.bean.getClassTableJDBCSeq() != null) {
            iterators.add(new ArrayIterator(new ClassTableJDBCSeqBean[]{this.bean.getClassTableJDBCSeq()}));
         }

         if (this.bean.getClientBrokerFactory() != null) {
            iterators.add(new ArrayIterator(new ClientBrokerFactoryBean[]{this.bean.getClientBrokerFactory()}));
         }

         if (this.bean.getClusterRemoteCommitProvider() != null) {
            iterators.add(new ArrayIterator(new ClusterRemoteCommitProviderBean[]{this.bean.getClusterRemoteCommitProvider()}));
         }

         if (this.bean.getCommonsLogFactory() != null) {
            iterators.add(new ArrayIterator(new CommonsLogFactoryBean[]{this.bean.getCommonsLogFactory()}));
         }

         if (this.bean.getCompatibility() != null) {
            iterators.add(new ArrayIterator(new KodoCompatibilityBean[]{this.bean.getCompatibility()}));
         }

         if (this.bean.getConcurrentHashMap() != null) {
            iterators.add(new ArrayIterator(new ConcurrentHashMapBean[]{this.bean.getConcurrentHashMap()}));
         }

         if (this.bean.getConnection2Properties() != null) {
            iterators.add(new ArrayIterator(new PropertiesBean[]{this.bean.getConnection2Properties()}));
         }

         if (this.bean.getConnectionDecorators() != null) {
            iterators.add(new ArrayIterator(new ConnectionDecoratorsBean[]{this.bean.getConnectionDecorators()}));
         }

         if (this.bean.getConnectionFactory2Properties() != null) {
            iterators.add(new ArrayIterator(new PropertiesBean[]{this.bean.getConnectionFactory2Properties()}));
         }

         if (this.bean.getConnectionFactoryProperties() != null) {
            iterators.add(new ArrayIterator(new PropertiesBean[]{this.bean.getConnectionFactoryProperties()}));
         }

         if (this.bean.getConnectionProperties() != null) {
            iterators.add(new ArrayIterator(new PropertiesBean[]{this.bean.getConnectionProperties()}));
         }

         if (this.bean.getConstraintUpdateManager() != null) {
            iterators.add(new ArrayIterator(new ConstraintUpdateManagerBean[]{this.bean.getConstraintUpdateManager()}));
         }

         if (this.bean.getCustomBrokerFactory() != null) {
            iterators.add(new ArrayIterator(new CustomBrokerFactoryBean[]{this.bean.getCustomBrokerFactory()}));
         }

         if (this.bean.getCustomBrokerImpl() != null) {
            iterators.add(new ArrayIterator(new CustomBrokerImplBean[]{this.bean.getCustomBrokerImpl()}));
         }

         if (this.bean.getCustomClassResolver() != null) {
            iterators.add(new ArrayIterator(new CustomClassResolverBean[]{this.bean.getCustomClassResolver()}));
         }

         if (this.bean.getCustomCompatibility() != null) {
            iterators.add(new ArrayIterator(new CustomCompatibilityBean[]{this.bean.getCustomCompatibility()}));
         }

         if (this.bean.getCustomDataCacheManager() != null) {
            iterators.add(new ArrayIterator(new CustomDataCacheManagerBean[]{this.bean.getCustomDataCacheManager()}));
         }

         if (this.bean.getCustomDetachState() != null) {
            iterators.add(new ArrayIterator(new CustomDetachStateBean[]{this.bean.getCustomDetachState()}));
         }

         if (this.bean.getCustomDictionary() != null) {
            iterators.add(new ArrayIterator(new CustomDictionaryBean[]{this.bean.getCustomDictionary()}));
         }

         if (this.bean.getCustomDriverDataSource() != null) {
            iterators.add(new ArrayIterator(new CustomDriverDataSourceBean[]{this.bean.getCustomDriverDataSource()}));
         }

         if (this.bean.getCustomLockManager() != null) {
            iterators.add(new ArrayIterator(new CustomLockManagerBean[]{this.bean.getCustomLockManager()}));
         }

         if (this.bean.getCustomLog() != null) {
            iterators.add(new ArrayIterator(new CustomLogBean[]{this.bean.getCustomLog()}));
         }

         if (this.bean.getCustomMappingDefaults() != null) {
            iterators.add(new ArrayIterator(new CustomMappingDefaultsBean[]{this.bean.getCustomMappingDefaults()}));
         }

         if (this.bean.getCustomMappingFactory() != null) {
            iterators.add(new ArrayIterator(new CustomMappingFactoryBean[]{this.bean.getCustomMappingFactory()}));
         }

         if (this.bean.getCustomMetaDataFactory() != null) {
            iterators.add(new ArrayIterator(new CustomMetaDataFactoryBean[]{this.bean.getCustomMetaDataFactory()}));
         }

         if (this.bean.getCustomMetaDataRepository() != null) {
            iterators.add(new ArrayIterator(new CustomMetaDataRepositoryBean[]{this.bean.getCustomMetaDataRepository()}));
         }

         if (this.bean.getCustomOrphanedKeyAction() != null) {
            iterators.add(new ArrayIterator(new CustomOrphanedKeyActionBean[]{this.bean.getCustomOrphanedKeyAction()}));
         }

         if (this.bean.getCustomPersistenceServer() != null) {
            iterators.add(new ArrayIterator(new CustomPersistenceServerBean[]{this.bean.getCustomPersistenceServer()}));
         }

         if (this.bean.getCustomProxyManager() != null) {
            iterators.add(new ArrayIterator(new CustomProxyManagerBean[]{this.bean.getCustomProxyManager()}));
         }

         if (this.bean.getCustomQueryCompilationCache() != null) {
            iterators.add(new ArrayIterator(new CustomQueryCompilationCacheBean[]{this.bean.getCustomQueryCompilationCache()}));
         }

         if (this.bean.getCustomRemoteCommitProvider() != null) {
            iterators.add(new ArrayIterator(new CustomRemoteCommitProviderBean[]{this.bean.getCustomRemoteCommitProvider()}));
         }

         if (this.bean.getCustomSQLFactory() != null) {
            iterators.add(new ArrayIterator(new CustomSQLFactoryBean[]{this.bean.getCustomSQLFactory()}));
         }

         if (this.bean.getCustomSavepointManager() != null) {
            iterators.add(new ArrayIterator(new CustomSavepointManagerBean[]{this.bean.getCustomSavepointManager()}));
         }

         if (this.bean.getCustomSchemaFactory() != null) {
            iterators.add(new ArrayIterator(new CustomSchemaFactoryBean[]{this.bean.getCustomSchemaFactory()}));
         }

         if (this.bean.getCustomSeq() != null) {
            iterators.add(new ArrayIterator(new CustomSeqBean[]{this.bean.getCustomSeq()}));
         }

         if (this.bean.getCustomUpdateManager() != null) {
            iterators.add(new ArrayIterator(new CustomUpdateManagerBean[]{this.bean.getCustomUpdateManager()}));
         }

         if (this.bean.getDB2Dictionary() != null) {
            iterators.add(new ArrayIterator(new DB2DictionaryBean[]{this.bean.getDB2Dictionary()}));
         }

         if (this.bean.getDataCacheManagerImpl() != null) {
            iterators.add(new ArrayIterator(new DataCacheManagerImplBean[]{this.bean.getDataCacheManagerImpl()}));
         }

         if (this.bean.getDataCaches() != null) {
            iterators.add(new ArrayIterator(new DataCachesBean[]{this.bean.getDataCaches()}));
         }

         if (this.bean.getDefaultBrokerFactory() != null) {
            iterators.add(new ArrayIterator(new DefaultBrokerFactoryBean[]{this.bean.getDefaultBrokerFactory()}));
         }

         if (this.bean.getDefaultBrokerImpl() != null) {
            iterators.add(new ArrayIterator(new DefaultBrokerImplBean[]{this.bean.getDefaultBrokerImpl()}));
         }

         if (this.bean.getDefaultClassResolver() != null) {
            iterators.add(new ArrayIterator(new DefaultClassResolverBean[]{this.bean.getDefaultClassResolver()}));
         }

         if (this.bean.getDefaultCompatibility() != null) {
            iterators.add(new ArrayIterator(new DefaultCompatibilityBean[]{this.bean.getDefaultCompatibility()}));
         }

         if (this.bean.getDefaultDataCacheManager() != null) {
            iterators.add(new ArrayIterator(new DefaultDataCacheManagerBean[]{this.bean.getDefaultDataCacheManager()}));
         }

         if (this.bean.getDefaultDetachState() != null) {
            iterators.add(new ArrayIterator(new DefaultDetachStateBean[]{this.bean.getDefaultDetachState()}));
         }

         if (this.bean.getDefaultDriverDataSource() != null) {
            iterators.add(new ArrayIterator(new DefaultDriverDataSourceBean[]{this.bean.getDefaultDriverDataSource()}));
         }

         if (this.bean.getDefaultLockManager() != null) {
            iterators.add(new ArrayIterator(new DefaultLockManagerBean[]{this.bean.getDefaultLockManager()}));
         }

         if (this.bean.getDefaultMappingDefaults() != null) {
            iterators.add(new ArrayIterator(new DefaultMappingDefaultsBean[]{this.bean.getDefaultMappingDefaults()}));
         }

         if (this.bean.getDefaultMetaDataFactory() != null) {
            iterators.add(new ArrayIterator(new DefaultMetaDataFactoryBean[]{this.bean.getDefaultMetaDataFactory()}));
         }

         if (this.bean.getDefaultMetaDataRepository() != null) {
            iterators.add(new ArrayIterator(new DefaultMetaDataRepositoryBean[]{this.bean.getDefaultMetaDataRepository()}));
         }

         if (this.bean.getDefaultOrphanedKeyAction() != null) {
            iterators.add(new ArrayIterator(new DefaultOrphanedKeyActionBean[]{this.bean.getDefaultOrphanedKeyAction()}));
         }

         if (this.bean.getDefaultProxyManager() != null) {
            iterators.add(new ArrayIterator(new DefaultProxyManagerBean[]{this.bean.getDefaultProxyManager()}));
         }

         if (this.bean.getDefaultQueryCompilationCache() != null) {
            iterators.add(new ArrayIterator(new DefaultQueryCompilationCacheBean[]{this.bean.getDefaultQueryCompilationCache()}));
         }

         if (this.bean.getDefaultSQLFactory() != null) {
            iterators.add(new ArrayIterator(new DefaultSQLFactoryBean[]{this.bean.getDefaultSQLFactory()}));
         }

         if (this.bean.getDefaultSavepointManager() != null) {
            iterators.add(new ArrayIterator(new DefaultSavepointManagerBean[]{this.bean.getDefaultSavepointManager()}));
         }

         if (this.bean.getDefaultSchemaFactory() != null) {
            iterators.add(new ArrayIterator(new DefaultSchemaFactoryBean[]{this.bean.getDefaultSchemaFactory()}));
         }

         if (this.bean.getDefaultUpdateManager() != null) {
            iterators.add(new ArrayIterator(new DefaultUpdateManagerBean[]{this.bean.getDefaultUpdateManager()}));
         }

         if (this.bean.getDeprecatedJDOMappingDefaults() != null) {
            iterators.add(new ArrayIterator(new DeprecatedJDOMappingDefaultsBean[]{this.bean.getDeprecatedJDOMappingDefaults()}));
         }

         if (this.bean.getDeprecatedJDOMetaDataFactory() != null) {
            iterators.add(new ArrayIterator(new DeprecatedJDOMetaDataFactoryBean[]{this.bean.getDeprecatedJDOMetaDataFactory()}));
         }

         if (this.bean.getDerbyDictionary() != null) {
            iterators.add(new ArrayIterator(new DerbyDictionaryBean[]{this.bean.getDerbyDictionary()}));
         }

         if (this.bean.getDetachOptionsAll() != null) {
            iterators.add(new ArrayIterator(new DetachOptionsAllBean[]{this.bean.getDetachOptionsAll()}));
         }

         if (this.bean.getDetachOptionsFetchGroups() != null) {
            iterators.add(new ArrayIterator(new DetachOptionsFetchGroupsBean[]{this.bean.getDetachOptionsFetchGroups()}));
         }

         if (this.bean.getDetachOptionsLoaded() != null) {
            iterators.add(new ArrayIterator(new DetachOptionsLoadedBean[]{this.bean.getDetachOptionsLoaded()}));
         }

         if (this.bean.getDynamicSchemaFactory() != null) {
            iterators.add(new ArrayIterator(new DynamicSchemaFactoryBean[]{this.bean.getDynamicSchemaFactory()}));
         }

         if (this.bean.getEmpressDictionary() != null) {
            iterators.add(new ArrayIterator(new EmpressDictionaryBean[]{this.bean.getEmpressDictionary()}));
         }

         if (this.bean.getExceptionOrphanedKeyAction() != null) {
            iterators.add(new ArrayIterator(new ExceptionOrphanedKeyActionBean[]{this.bean.getExceptionOrphanedKeyAction()}));
         }

         if (this.bean.getExportProfiling() != null) {
            iterators.add(new ArrayIterator(new ExportProfilingBean[]{this.bean.getExportProfiling()}));
         }

         if (this.bean.getExtensionDeprecatedJDOMappingFactory() != null) {
            iterators.add(new ArrayIterator(new ExtensionDeprecatedJDOMappingFactoryBean[]{this.bean.getExtensionDeprecatedJDOMappingFactory()}));
         }

         if (this.bean.getFetchGroups() != null) {
            iterators.add(new ArrayIterator(new FetchGroupsBean[]{this.bean.getFetchGroups()}));
         }

         if (this.bean.getFileSchemaFactory() != null) {
            iterators.add(new ArrayIterator(new FileSchemaFactoryBean[]{this.bean.getFileSchemaFactory()}));
         }

         if (this.bean.getFilterListeners() != null) {
            iterators.add(new ArrayIterator(new FilterListenersBean[]{this.bean.getFilterListeners()}));
         }

         if (this.bean.getFoxProDictionary() != null) {
            iterators.add(new ArrayIterator(new FoxProDictionaryBean[]{this.bean.getFoxProDictionary()}));
         }

         if (this.bean.getGUIJMX() != null) {
            iterators.add(new ArrayIterator(new GUIJMXBean[]{this.bean.getGUIJMX()}));
         }

         if (this.bean.getGUIProfiling() != null) {
            iterators.add(new ArrayIterator(new GUIProfilingBean[]{this.bean.getGUIProfiling()}));
         }

         if (this.bean.getHSQLDictionary() != null) {
            iterators.add(new ArrayIterator(new HSQLDictionaryBean[]{this.bean.getHSQLDictionary()}));
         }

         if (this.bean.getHTTPTransport() != null) {
            iterators.add(new ArrayIterator(new HTTPTransportBean[]{this.bean.getHTTPTransport()}));
         }

         if (this.bean.getInMemorySavepointManager() != null) {
            iterators.add(new ArrayIterator(new InMemorySavepointManagerBean[]{this.bean.getInMemorySavepointManager()}));
         }

         if (this.bean.getInformixDictionary() != null) {
            iterators.add(new ArrayIterator(new InformixDictionaryBean[]{this.bean.getInformixDictionary()}));
         }

         if (this.bean.getInverseManager() != null) {
            iterators.add(new ArrayIterator(new InverseManagerBean[]{this.bean.getInverseManager()}));
         }

         if (this.bean.getJDBC3SavepointManager() != null) {
            iterators.add(new ArrayIterator(new JDBC3SavepointManagerBean[]{this.bean.getJDBC3SavepointManager()}));
         }

         if (this.bean.getJDBCBrokerFactory() != null) {
            iterators.add(new ArrayIterator(new JDBCBrokerFactoryBean[]{this.bean.getJDBCBrokerFactory()}));
         }

         if (this.bean.getJDBCListeners() != null) {
            iterators.add(new ArrayIterator(new JDBCListenersBean[]{this.bean.getJDBCListeners()}));
         }

         if (this.bean.getJDOMetaDataFactory() != null) {
            iterators.add(new ArrayIterator(new JDOMetaDataFactoryBean[]{this.bean.getJDOMetaDataFactory()}));
         }

         if (this.bean.getJDataStoreDictionary() != null) {
            iterators.add(new ArrayIterator(new JDataStoreDictionaryBean[]{this.bean.getJDataStoreDictionary()}));
         }

         if (this.bean.getJMSRemoteCommitProvider() != null) {
            iterators.add(new ArrayIterator(new JMSRemoteCommitProviderBean[]{this.bean.getJMSRemoteCommitProvider()}));
         }

         if (this.bean.getJMX2JMX() != null) {
            iterators.add(new ArrayIterator(new JMX2JMXBean[]{this.bean.getJMX2JMX()}));
         }

         if (this.bean.getKodoBroker() != null) {
            iterators.add(new ArrayIterator(new KodoBrokerBean[]{this.bean.getKodoBroker()}));
         }

         if (this.bean.getKodoDataCacheManager() != null) {
            iterators.add(new ArrayIterator(new KodoDataCacheManagerBean[]{this.bean.getKodoDataCacheManager()}));
         }

         if (this.bean.getKodoMappingRepository() != null) {
            iterators.add(new ArrayIterator(new KodoMappingRepositoryBean[]{this.bean.getKodoMappingRepository()}));
         }

         if (this.bean.getKodoPersistenceMappingFactory() != null) {
            iterators.add(new ArrayIterator(new KodoPersistenceMappingFactoryBean[]{this.bean.getKodoPersistenceMappingFactory()}));
         }

         if (this.bean.getKodoPersistenceMetaDataFactory() != null) {
            iterators.add(new ArrayIterator(new KodoPersistenceMetaDataFactoryBean[]{this.bean.getKodoPersistenceMetaDataFactory()}));
         }

         if (this.bean.getKodoPoolingDataSource() != null) {
            iterators.add(new ArrayIterator(new KodoPoolingDataSourceBean[]{this.bean.getKodoPoolingDataSource()}));
         }

         if (this.bean.getKodoSQLFactory() != null) {
            iterators.add(new ArrayIterator(new KodoSQLFactoryBean[]{this.bean.getKodoSQLFactory()}));
         }

         if (this.bean.getLazySchemaFactory() != null) {
            iterators.add(new ArrayIterator(new LazySchemaFactoryBean[]{this.bean.getLazySchemaFactory()}));
         }

         if (this.bean.getLocalJMX() != null) {
            iterators.add(new ArrayIterator(new LocalJMXBean[]{this.bean.getLocalJMX()}));
         }

         if (this.bean.getLocalProfiling() != null) {
            iterators.add(new ArrayIterator(new LocalProfilingBean[]{this.bean.getLocalProfiling()}));
         }

         if (this.bean.getLog4JLogFactory() != null) {
            iterators.add(new ArrayIterator(new Log4JLogFactoryBean[]{this.bean.getLog4JLogFactory()}));
         }

         if (this.bean.getLogFactoryImpl() != null) {
            iterators.add(new ArrayIterator(new LogFactoryImplBean[]{this.bean.getLogFactoryImpl()}));
         }

         if (this.bean.getLogOrphanedKeyAction() != null) {
            iterators.add(new ArrayIterator(new LogOrphanedKeyActionBean[]{this.bean.getLogOrphanedKeyAction()}));
         }

         if (this.bean.getMX4J1JMX() != null) {
            iterators.add(new ArrayIterator(new MX4J1JMXBean[]{this.bean.getMX4J1JMX()}));
         }

         if (this.bean.getMappingDefaultsImpl() != null) {
            iterators.add(new ArrayIterator(new MappingDefaultsImplBean[]{this.bean.getMappingDefaultsImpl()}));
         }

         if (this.bean.getMappingFileDeprecatedJDOMappingFactory() != null) {
            iterators.add(new ArrayIterator(new MappingFileDeprecatedJDOMappingFactoryBean[]{this.bean.getMappingFileDeprecatedJDOMappingFactory()}));
         }

         if (this.bean.getMySQLDictionary() != null) {
            iterators.add(new ArrayIterator(new MySQLDictionaryBean[]{this.bean.getMySQLDictionary()}));
         }

         if (this.bean.getNativeJDBCSeq() != null) {
            iterators.add(new ArrayIterator(new NativeJDBCSeqBean[]{this.bean.getNativeJDBCSeq()}));
         }

         if (this.bean.getNoneJMX() != null) {
            iterators.add(new ArrayIterator(new NoneJMXBean[]{this.bean.getNoneJMX()}));
         }

         if (this.bean.getNoneLockManager() != null) {
            iterators.add(new ArrayIterator(new NoneLockManagerBean[]{this.bean.getNoneLockManager()}));
         }

         if (this.bean.getNoneLogFactory() != null) {
            iterators.add(new ArrayIterator(new NoneLogFactoryBean[]{this.bean.getNoneLogFactory()}));
         }

         if (this.bean.getNoneOrphanedKeyAction() != null) {
            iterators.add(new ArrayIterator(new NoneOrphanedKeyActionBean[]{this.bean.getNoneOrphanedKeyAction()}));
         }

         if (this.bean.getNoneProfiling() != null) {
            iterators.add(new ArrayIterator(new NoneProfilingBean[]{this.bean.getNoneProfiling()}));
         }

         if (this.bean.getORMFileJDORMappingFactory() != null) {
            iterators.add(new ArrayIterator(new ORMFileJDORMappingFactoryBean[]{this.bean.getORMFileJDORMappingFactory()}));
         }

         if (this.bean.getOperationOrderUpdateManager() != null) {
            iterators.add(new ArrayIterator(new OperationOrderUpdateManagerBean[]{this.bean.getOperationOrderUpdateManager()}));
         }

         if (this.bean.getOracleDictionary() != null) {
            iterators.add(new ArrayIterator(new OracleDictionaryBean[]{this.bean.getOracleDictionary()}));
         }

         if (this.bean.getOracleSavepointManager() != null) {
            iterators.add(new ArrayIterator(new OracleSavepointManagerBean[]{this.bean.getOracleSavepointManager()}));
         }

         if (this.bean.getPersistenceMappingDefaults() != null) {
            iterators.add(new ArrayIterator(new PersistenceMappingDefaultsBean[]{this.bean.getPersistenceMappingDefaults()}));
         }

         if (this.bean.getPessimisticLockManager() != null) {
            iterators.add(new ArrayIterator(new PessimisticLockManagerBean[]{this.bean.getPessimisticLockManager()}));
         }

         if (this.bean.getPointbaseDictionary() != null) {
            iterators.add(new ArrayIterator(new PointbaseDictionaryBean[]{this.bean.getPointbaseDictionary()}));
         }

         if (this.bean.getPostgresDictionary() != null) {
            iterators.add(new ArrayIterator(new PostgresDictionaryBean[]{this.bean.getPostgresDictionary()}));
         }

         if (this.bean.getProfilingProxyManager() != null) {
            iterators.add(new ArrayIterator(new ProfilingProxyManagerBean[]{this.bean.getProfilingProxyManager()}));
         }

         if (this.bean.getProxyManagerImpl() != null) {
            iterators.add(new ArrayIterator(new ProxyManagerImplBean[]{this.bean.getProxyManagerImpl()}));
         }

         if (this.bean.getQueryCaches() != null) {
            iterators.add(new ArrayIterator(new QueryCachesBean[]{this.bean.getQueryCaches()}));
         }

         if (this.bean.getSQLServerDictionary() != null) {
            iterators.add(new ArrayIterator(new SQLServerDictionaryBean[]{this.bean.getSQLServerDictionary()}));
         }

         if (this.bean.getSchemata() != null) {
            iterators.add(new ArrayIterator(new SchemasBean[]{this.bean.getSchemata()}));
         }

         if (this.bean.getSimpleDriverDataSource() != null) {
            iterators.add(new ArrayIterator(new SimpleDriverDataSourceBean[]{this.bean.getSimpleDriverDataSource()}));
         }

         if (this.bean.getSingleJVMExclusiveLockManager() != null) {
            iterators.add(new ArrayIterator(new SingleJVMExclusiveLockManagerBean[]{this.bean.getSingleJVMExclusiveLockManager()}));
         }

         if (this.bean.getSingleJVMRemoteCommitProvider() != null) {
            iterators.add(new ArrayIterator(new SingleJVMRemoteCommitProviderBean[]{this.bean.getSingleJVMRemoteCommitProvider()}));
         }

         if (this.bean.getStackExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new StackExecutionContextNameProviderBean[]{this.bean.getStackExecutionContextNameProvider()}));
         }

         if (this.bean.getSybaseDictionary() != null) {
            iterators.add(new ArrayIterator(new SybaseDictionaryBean[]{this.bean.getSybaseDictionary()}));
         }

         if (this.bean.getTCPRemoteCommitProvider() != null) {
            iterators.add(new ArrayIterator(new TCPRemoteCommitProviderBean[]{this.bean.getTCPRemoteCommitProvider()}));
         }

         if (this.bean.getTCPTransport() != null) {
            iterators.add(new ArrayIterator(new TCPTransportBean[]{this.bean.getTCPTransport()}));
         }

         if (this.bean.getTableDeprecatedJDOMappingFactory() != null) {
            iterators.add(new ArrayIterator(new TableDeprecatedJDOMappingFactoryBean[]{this.bean.getTableDeprecatedJDOMappingFactory()}));
         }

         if (this.bean.getTableJDBCSeq() != null) {
            iterators.add(new ArrayIterator(new TableJDBCSeqBean[]{this.bean.getTableJDBCSeq()}));
         }

         if (this.bean.getTableJDORMappingFactory() != null) {
            iterators.add(new ArrayIterator(new TableJDORMappingFactoryBean[]{this.bean.getTableJDORMappingFactory()}));
         }

         if (this.bean.getTableLockUpdateManager() != null) {
            iterators.add(new ArrayIterator(new TableLockUpdateManagerBean[]{this.bean.getTableLockUpdateManager()}));
         }

         if (this.bean.getTableSchemaFactory() != null) {
            iterators.add(new ArrayIterator(new TableSchemaFactoryBean[]{this.bean.getTableSchemaFactory()}));
         }

         if (this.bean.getTimeSeededSeq() != null) {
            iterators.add(new ArrayIterator(new TimeSeededSeqBean[]{this.bean.getTimeSeededSeq()}));
         }

         if (this.bean.getTransactionNameExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new TransactionNameExecutionContextNameProviderBean[]{this.bean.getTransactionNameExecutionContextNameProvider()}));
         }

         if (this.bean.getUserObjectExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new UserObjectExecutionContextNameProviderBean[]{this.bean.getUserObjectExecutionContextNameProvider()}));
         }

         if (this.bean.getValueTableJDBCSeq() != null) {
            iterators.add(new ArrayIterator(new ValueTableJDBCSeqBean[]{this.bean.getValueTableJDBCSeq()}));
         }

         if (this.bean.getVersionLockManager() != null) {
            iterators.add(new ArrayIterator(new VersionLockManagerBean[]{this.bean.getVersionLockManager()}));
         }

         if (this.bean.getWLS81JMX() != null) {
            iterators.add(new ArrayIterator(new WLS81JMXBean[]{this.bean.getWLS81JMX()}));
         }

         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            childValue = this.computeChildHashValue(this.bean.getAbstractStoreBrokerFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getAccessDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getAggregateListeners());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isAutoClearSet()) {
               buf.append("AutoClear");
               buf.append(String.valueOf(this.bean.getAutoClear()));
            }

            childValue = this.computeChildHashValue(this.bean.getAutoDetaches());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getBatchingOperationOrderUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCacheMap());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClassTableJDBCSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClientBrokerFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClusterRemoteCommitProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCommonsLogFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCompatibility());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getConcurrentHashMap());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnection2DriverNameSet()) {
               buf.append("Connection2DriverName");
               buf.append(String.valueOf(this.bean.getConnection2DriverName()));
            }

            if (this.bean.isConnection2PasswordSet()) {
               buf.append("Connection2Password");
               buf.append(String.valueOf(this.bean.getConnection2Password()));
            }

            if (this.bean.isConnection2PasswordEncryptedSet()) {
               buf.append("Connection2PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnection2PasswordEncrypted())));
            }

            childValue = this.computeChildHashValue(this.bean.getConnection2Properties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnection2URLSet()) {
               buf.append("Connection2URL");
               buf.append(String.valueOf(this.bean.getConnection2URL()));
            }

            if (this.bean.isConnection2UserNameSet()) {
               buf.append("Connection2UserName");
               buf.append(String.valueOf(this.bean.getConnection2UserName()));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectionDecorators());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnectionDriverNameSet()) {
               buf.append("ConnectionDriverName");
               buf.append(String.valueOf(this.bean.getConnectionDriverName()));
            }

            if (this.bean.isConnectionFactory2NameSet()) {
               buf.append("ConnectionFactory2Name");
               buf.append(String.valueOf(this.bean.getConnectionFactory2Name()));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectionFactory2Properties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnectionFactoryModeSet()) {
               buf.append("ConnectionFactoryMode");
               buf.append(String.valueOf(this.bean.getConnectionFactoryMode()));
            }

            if (this.bean.isConnectionFactoryNameSet()) {
               buf.append("ConnectionFactoryName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryName()));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectionFactoryProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnectionPasswordSet()) {
               buf.append("ConnectionPassword");
               buf.append(String.valueOf(this.bean.getConnectionPassword()));
            }

            if (this.bean.isConnectionPasswordEncryptedSet()) {
               buf.append("ConnectionPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnectionPasswordEncrypted())));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectionProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnectionRetainModeSet()) {
               buf.append("ConnectionRetainMode");
               buf.append(String.valueOf(this.bean.getConnectionRetainMode()));
            }

            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            if (this.bean.isConnectionUserNameSet()) {
               buf.append("ConnectionUserName");
               buf.append(String.valueOf(this.bean.getConnectionUserName()));
            }

            childValue = this.computeChildHashValue(this.bean.getConstraintUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomBrokerFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomBrokerImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomClassResolver());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomCompatibility());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomDataCacheManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomDetachState());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomDriverDataSource());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomLog());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomMappingDefaults());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomMetaDataFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomMetaDataRepository());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomOrphanedKeyAction());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomPersistenceServer());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomProxyManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomQueryCompilationCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomRemoteCommitProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomSQLFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomSavepointManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomSchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCustomUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDB2Dictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDataCacheManagerImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDataCacheTimeoutSet()) {
               buf.append("DataCacheTimeout");
               buf.append(String.valueOf(this.bean.getDataCacheTimeout()));
            }

            childValue = this.computeChildHashValue(this.bean.getDataCaches());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultBrokerFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultBrokerImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultClassResolver());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultCompatibility());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultDataCacheManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultDetachState());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultDriverDataSource());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultMappingDefaults());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultMetaDataFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultMetaDataRepository());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultOrphanedKeyAction());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultProxyManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultQueryCompilationCache());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultSQLFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultSavepointManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultSchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDeprecatedJDOMappingDefaults());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDeprecatedJDOMetaDataFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDerbyDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDetachOptionsAll());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDetachOptionsFetchGroups());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDetachOptionsLoaded());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDynamicDataStructsSet()) {
               buf.append("DynamicDataStructs");
               buf.append(String.valueOf(this.bean.getDynamicDataStructs()));
            }

            childValue = this.computeChildHashValue(this.bean.getDynamicSchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEagerFetchModeSet()) {
               buf.append("EagerFetchMode");
               buf.append(String.valueOf(this.bean.getEagerFetchMode()));
            }

            childValue = this.computeChildHashValue(this.bean.getEmpressDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getExceptionOrphanedKeyAction());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getExportProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getExtensionDeprecatedJDOMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFetchBatchSizeSet()) {
               buf.append("FetchBatchSize");
               buf.append(String.valueOf(this.bean.getFetchBatchSize()));
            }

            if (this.bean.isFetchDirectionSet()) {
               buf.append("FetchDirection");
               buf.append(String.valueOf(this.bean.getFetchDirection()));
            }

            childValue = this.computeChildHashValue(this.bean.getFetchGroups());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFileSchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFilterListeners());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFlushBeforeQueriesSet()) {
               buf.append("FlushBeforeQueries");
               buf.append(String.valueOf(this.bean.getFlushBeforeQueries()));
            }

            childValue = this.computeChildHashValue(this.bean.getFoxProDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getGUIJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getGUIProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getHSQLDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getHTTPTransport());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIgnoreChangesSet()) {
               buf.append("IgnoreChanges");
               buf.append(String.valueOf(this.bean.getIgnoreChanges()));
            }

            childValue = this.computeChildHashValue(this.bean.getInMemorySavepointManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getInformixDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getInverseManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBC3SavepointManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCBrokerFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCListeners());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDOMetaDataFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDataStoreDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJMSRemoteCommitProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJMX2JMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoBroker());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoDataCacheManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoMappingRepository());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoPersistenceMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoPersistenceMetaDataFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoPoolingDataSource());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getKodoSQLFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLRSSizeSet()) {
               buf.append("LRSSize");
               buf.append(String.valueOf(this.bean.getLRSSize()));
            }

            childValue = this.computeChildHashValue(this.bean.getLazySchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLocalJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLocalProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLockTimeoutSet()) {
               buf.append("LockTimeout");
               buf.append(String.valueOf(this.bean.getLockTimeout()));
            }

            childValue = this.computeChildHashValue(this.bean.getLog4JLogFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLogFactoryImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLogOrphanedKeyAction());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getMX4J1JMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMappingSet()) {
               buf.append("Mapping");
               buf.append(String.valueOf(this.bean.getMapping()));
            }

            childValue = this.computeChildHashValue(this.bean.getMappingDefaultsImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getMappingFileDeprecatedJDOMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMultithreadedSet()) {
               buf.append("Multithreaded");
               buf.append(String.valueOf(this.bean.getMultithreaded()));
            }

            childValue = this.computeChildHashValue(this.bean.getMySQLDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = this.computeChildHashValue(this.bean.getNativeJDBCSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneJMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneLogFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneOrphanedKeyAction());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNontransactionalReadSet()) {
               buf.append("NontransactionalRead");
               buf.append(String.valueOf(this.bean.getNontransactionalRead()));
            }

            if (this.bean.isNontransactionalWriteSet()) {
               buf.append("NontransactionalWrite");
               buf.append(String.valueOf(this.bean.getNontransactionalWrite()));
            }

            childValue = this.computeChildHashValue(this.bean.getORMFileJDORMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getOperationOrderUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isOptimisticSet()) {
               buf.append("Optimistic");
               buf.append(String.valueOf(this.bean.getOptimistic()));
            }

            childValue = this.computeChildHashValue(this.bean.getOracleDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getOracleSavepointManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPersistenceMappingDefaults());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPessimisticLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPointbaseDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPostgresDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getProfilingProxyManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getProxyManagerImpl());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getQueryCaches());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isReadLockLevelSet()) {
               buf.append("ReadLockLevel");
               buf.append(String.valueOf(this.bean.getReadLockLevel()));
            }

            if (this.bean.isRestoreStateSet()) {
               buf.append("RestoreState");
               buf.append(String.valueOf(this.bean.getRestoreState()));
            }

            if (this.bean.isResultSetTypeSet()) {
               buf.append("ResultSetType");
               buf.append(String.valueOf(this.bean.getResultSetType()));
            }

            if (this.bean.isRetainStateSet()) {
               buf.append("RetainState");
               buf.append(String.valueOf(this.bean.getRetainState()));
            }

            if (this.bean.isRetryClassRegistrationSet()) {
               buf.append("RetryClassRegistration");
               buf.append(String.valueOf(this.bean.getRetryClassRegistration()));
            }

            childValue = this.computeChildHashValue(this.bean.getSQLServerDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSchemaSet()) {
               buf.append("Schema");
               buf.append(String.valueOf(this.bean.getSchema()));
            }

            childValue = this.computeChildHashValue(this.bean.getSchemata());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSimpleDriverDataSource());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSingleJVMExclusiveLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSingleJVMRemoteCommitProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStackExecutionContextNameProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSubclassFetchModeSet()) {
               buf.append("SubclassFetchMode");
               buf.append(String.valueOf(this.bean.getSubclassFetchMode()));
            }

            childValue = this.computeChildHashValue(this.bean.getSybaseDictionary());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSynchronizeMappingsSet()) {
               buf.append("SynchronizeMappings");
               buf.append(String.valueOf(this.bean.getSynchronizeMappings()));
            }

            childValue = this.computeChildHashValue(this.bean.getTCPRemoteCommitProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTCPTransport());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTableDeprecatedJDOMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTableJDBCSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTableJDORMappingFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTableLockUpdateManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTableSchemaFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimeSeededSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTransactionIsolationSet()) {
               buf.append("TransactionIsolation");
               buf.append(String.valueOf(this.bean.getTransactionIsolation()));
            }

            if (this.bean.isTransactionModeSet()) {
               buf.append("TransactionMode");
               buf.append(String.valueOf(this.bean.getTransactionMode()));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactionNameExecutionContextNameProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getUserObjectExecutionContextNameProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getValueTableJDBCSeq());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getVersionLockManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWLS81JMX());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWriteLockLevelSet()) {
               buf.append("WriteLockLevel");
               buf.append(String.valueOf(this.bean.getWriteLockLevel()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            PersistenceUnitConfigurationBeanImpl otherTyped = (PersistenceUnitConfigurationBeanImpl)other;
            this.computeChildDiff("AbstractStoreBrokerFactory", this.bean.getAbstractStoreBrokerFactory(), otherTyped.getAbstractStoreBrokerFactory(), false);
            this.computeChildDiff("AccessDictionary", this.bean.getAccessDictionary(), otherTyped.getAccessDictionary(), false);
            this.computeSubDiff("AggregateListeners", this.bean.getAggregateListeners(), otherTyped.getAggregateListeners());
            this.computeDiff("AutoClear", this.bean.getAutoClear(), otherTyped.getAutoClear(), false);
            this.computeSubDiff("AutoDetaches", this.bean.getAutoDetaches(), otherTyped.getAutoDetaches());
            this.computeChildDiff("BatchingOperationOrderUpdateManager", this.bean.getBatchingOperationOrderUpdateManager(), otherTyped.getBatchingOperationOrderUpdateManager(), false);
            this.computeChildDiff("CacheMap", this.bean.getCacheMap(), otherTyped.getCacheMap(), false);
            this.computeChildDiff("ClassTableJDBCSeq", this.bean.getClassTableJDBCSeq(), otherTyped.getClassTableJDBCSeq(), false);
            this.computeChildDiff("ClientBrokerFactory", this.bean.getClientBrokerFactory(), otherTyped.getClientBrokerFactory(), false);
            this.computeChildDiff("ClusterRemoteCommitProvider", this.bean.getClusterRemoteCommitProvider(), otherTyped.getClusterRemoteCommitProvider(), false);
            this.computeChildDiff("CommonsLogFactory", this.bean.getCommonsLogFactory(), otherTyped.getCommonsLogFactory(), false);
            this.computeChildDiff("Compatibility", this.bean.getCompatibility(), otherTyped.getCompatibility(), false);
            this.computeChildDiff("ConcurrentHashMap", this.bean.getConcurrentHashMap(), otherTyped.getConcurrentHashMap(), false);
            this.computeDiff("Connection2DriverName", this.bean.getConnection2DriverName(), otherTyped.getConnection2DriverName(), false);
            this.computeDiff("Connection2PasswordEncrypted", this.bean.getConnection2PasswordEncrypted(), otherTyped.getConnection2PasswordEncrypted(), false);
            this.computeSubDiff("Connection2Properties", this.bean.getConnection2Properties(), otherTyped.getConnection2Properties());
            this.computeDiff("Connection2URL", this.bean.getConnection2URL(), otherTyped.getConnection2URL(), false);
            this.computeDiff("Connection2UserName", this.bean.getConnection2UserName(), otherTyped.getConnection2UserName(), false);
            this.computeSubDiff("ConnectionDecorators", this.bean.getConnectionDecorators(), otherTyped.getConnectionDecorators());
            this.computeDiff("ConnectionDriverName", this.bean.getConnectionDriverName(), otherTyped.getConnectionDriverName(), false);
            this.computeDiff("ConnectionFactory2Name", this.bean.getConnectionFactory2Name(), otherTyped.getConnectionFactory2Name(), false);
            this.computeSubDiff("ConnectionFactory2Properties", this.bean.getConnectionFactory2Properties(), otherTyped.getConnectionFactory2Properties());
            this.computeDiff("ConnectionFactoryMode", this.bean.getConnectionFactoryMode(), otherTyped.getConnectionFactoryMode(), false);
            this.computeDiff("ConnectionFactoryName", this.bean.getConnectionFactoryName(), otherTyped.getConnectionFactoryName(), false);
            this.computeSubDiff("ConnectionFactoryProperties", this.bean.getConnectionFactoryProperties(), otherTyped.getConnectionFactoryProperties());
            this.computeDiff("ConnectionPasswordEncrypted", this.bean.getConnectionPasswordEncrypted(), otherTyped.getConnectionPasswordEncrypted(), false);
            this.computeSubDiff("ConnectionProperties", this.bean.getConnectionProperties(), otherTyped.getConnectionProperties());
            this.computeDiff("ConnectionRetainMode", this.bean.getConnectionRetainMode(), otherTyped.getConnectionRetainMode(), false);
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), false);
            this.computeDiff("ConnectionUserName", this.bean.getConnectionUserName(), otherTyped.getConnectionUserName(), false);
            this.computeChildDiff("ConstraintUpdateManager", this.bean.getConstraintUpdateManager(), otherTyped.getConstraintUpdateManager(), false);
            this.computeChildDiff("CustomBrokerFactory", this.bean.getCustomBrokerFactory(), otherTyped.getCustomBrokerFactory(), false);
            this.computeChildDiff("CustomBrokerImpl", this.bean.getCustomBrokerImpl(), otherTyped.getCustomBrokerImpl(), false);
            this.computeChildDiff("CustomClassResolver", this.bean.getCustomClassResolver(), otherTyped.getCustomClassResolver(), false);
            this.computeChildDiff("CustomCompatibility", this.bean.getCustomCompatibility(), otherTyped.getCustomCompatibility(), false);
            this.computeChildDiff("CustomDataCacheManager", this.bean.getCustomDataCacheManager(), otherTyped.getCustomDataCacheManager(), false);
            this.computeChildDiff("CustomDetachState", this.bean.getCustomDetachState(), otherTyped.getCustomDetachState(), false);
            this.computeChildDiff("CustomDictionary", this.bean.getCustomDictionary(), otherTyped.getCustomDictionary(), false);
            this.computeChildDiff("CustomDriverDataSource", this.bean.getCustomDriverDataSource(), otherTyped.getCustomDriverDataSource(), false);
            this.computeChildDiff("CustomLockManager", this.bean.getCustomLockManager(), otherTyped.getCustomLockManager(), false);
            this.computeChildDiff("CustomLog", this.bean.getCustomLog(), otherTyped.getCustomLog(), false);
            this.computeChildDiff("CustomMappingDefaults", this.bean.getCustomMappingDefaults(), otherTyped.getCustomMappingDefaults(), false);
            this.computeChildDiff("CustomMappingFactory", this.bean.getCustomMappingFactory(), otherTyped.getCustomMappingFactory(), false);
            this.computeChildDiff("CustomMetaDataFactory", this.bean.getCustomMetaDataFactory(), otherTyped.getCustomMetaDataFactory(), false);
            this.computeChildDiff("CustomMetaDataRepository", this.bean.getCustomMetaDataRepository(), otherTyped.getCustomMetaDataRepository(), false);
            this.computeChildDiff("CustomOrphanedKeyAction", this.bean.getCustomOrphanedKeyAction(), otherTyped.getCustomOrphanedKeyAction(), false);
            this.computeChildDiff("CustomPersistenceServer", this.bean.getCustomPersistenceServer(), otherTyped.getCustomPersistenceServer(), false);
            this.computeChildDiff("CustomProxyManager", this.bean.getCustomProxyManager(), otherTyped.getCustomProxyManager(), false);
            this.computeChildDiff("CustomQueryCompilationCache", this.bean.getCustomQueryCompilationCache(), otherTyped.getCustomQueryCompilationCache(), false);
            this.computeChildDiff("CustomRemoteCommitProvider", this.bean.getCustomRemoteCommitProvider(), otherTyped.getCustomRemoteCommitProvider(), false);
            this.computeChildDiff("CustomSQLFactory", this.bean.getCustomSQLFactory(), otherTyped.getCustomSQLFactory(), false);
            this.computeChildDiff("CustomSavepointManager", this.bean.getCustomSavepointManager(), otherTyped.getCustomSavepointManager(), false);
            this.computeChildDiff("CustomSchemaFactory", this.bean.getCustomSchemaFactory(), otherTyped.getCustomSchemaFactory(), false);
            this.computeChildDiff("CustomSeq", this.bean.getCustomSeq(), otherTyped.getCustomSeq(), false);
            this.computeChildDiff("CustomUpdateManager", this.bean.getCustomUpdateManager(), otherTyped.getCustomUpdateManager(), false);
            this.computeChildDiff("DB2Dictionary", this.bean.getDB2Dictionary(), otherTyped.getDB2Dictionary(), false);
            this.computeChildDiff("DataCacheManagerImpl", this.bean.getDataCacheManagerImpl(), otherTyped.getDataCacheManagerImpl(), false);
            this.computeDiff("DataCacheTimeout", this.bean.getDataCacheTimeout(), otherTyped.getDataCacheTimeout(), true);
            this.computeSubDiff("DataCaches", this.bean.getDataCaches(), otherTyped.getDataCaches());
            this.computeChildDiff("DefaultBrokerFactory", this.bean.getDefaultBrokerFactory(), otherTyped.getDefaultBrokerFactory(), false);
            this.computeChildDiff("DefaultBrokerImpl", this.bean.getDefaultBrokerImpl(), otherTyped.getDefaultBrokerImpl(), false);
            this.computeChildDiff("DefaultClassResolver", this.bean.getDefaultClassResolver(), otherTyped.getDefaultClassResolver(), false);
            this.computeChildDiff("DefaultCompatibility", this.bean.getDefaultCompatibility(), otherTyped.getDefaultCompatibility(), false);
            this.computeChildDiff("DefaultDataCacheManager", this.bean.getDefaultDataCacheManager(), otherTyped.getDefaultDataCacheManager(), false);
            this.computeChildDiff("DefaultDetachState", this.bean.getDefaultDetachState(), otherTyped.getDefaultDetachState(), false);
            this.computeChildDiff("DefaultDriverDataSource", this.bean.getDefaultDriverDataSource(), otherTyped.getDefaultDriverDataSource(), false);
            this.computeChildDiff("DefaultLockManager", this.bean.getDefaultLockManager(), otherTyped.getDefaultLockManager(), false);
            this.computeChildDiff("DefaultMappingDefaults", this.bean.getDefaultMappingDefaults(), otherTyped.getDefaultMappingDefaults(), false);
            this.computeChildDiff("DefaultMetaDataFactory", this.bean.getDefaultMetaDataFactory(), otherTyped.getDefaultMetaDataFactory(), false);
            this.computeChildDiff("DefaultMetaDataRepository", this.bean.getDefaultMetaDataRepository(), otherTyped.getDefaultMetaDataRepository(), false);
            this.computeChildDiff("DefaultOrphanedKeyAction", this.bean.getDefaultOrphanedKeyAction(), otherTyped.getDefaultOrphanedKeyAction(), false);
            this.computeChildDiff("DefaultProxyManager", this.bean.getDefaultProxyManager(), otherTyped.getDefaultProxyManager(), false);
            this.computeChildDiff("DefaultQueryCompilationCache", this.bean.getDefaultQueryCompilationCache(), otherTyped.getDefaultQueryCompilationCache(), false);
            this.computeChildDiff("DefaultSQLFactory", this.bean.getDefaultSQLFactory(), otherTyped.getDefaultSQLFactory(), false);
            this.computeChildDiff("DefaultSavepointManager", this.bean.getDefaultSavepointManager(), otherTyped.getDefaultSavepointManager(), false);
            this.computeChildDiff("DefaultSchemaFactory", this.bean.getDefaultSchemaFactory(), otherTyped.getDefaultSchemaFactory(), false);
            this.computeChildDiff("DefaultUpdateManager", this.bean.getDefaultUpdateManager(), otherTyped.getDefaultUpdateManager(), false);
            this.computeChildDiff("DeprecatedJDOMappingDefaults", this.bean.getDeprecatedJDOMappingDefaults(), otherTyped.getDeprecatedJDOMappingDefaults(), false);
            this.computeChildDiff("DeprecatedJDOMetaDataFactory", this.bean.getDeprecatedJDOMetaDataFactory(), otherTyped.getDeprecatedJDOMetaDataFactory(), false);
            this.computeChildDiff("DerbyDictionary", this.bean.getDerbyDictionary(), otherTyped.getDerbyDictionary(), false);
            this.computeChildDiff("DetachOptionsAll", this.bean.getDetachOptionsAll(), otherTyped.getDetachOptionsAll(), false);
            this.computeChildDiff("DetachOptionsFetchGroups", this.bean.getDetachOptionsFetchGroups(), otherTyped.getDetachOptionsFetchGroups(), false);
            this.computeChildDiff("DetachOptionsLoaded", this.bean.getDetachOptionsLoaded(), otherTyped.getDetachOptionsLoaded(), false);
            this.computeDiff("DynamicDataStructs", this.bean.getDynamicDataStructs(), otherTyped.getDynamicDataStructs(), false);
            this.computeChildDiff("DynamicSchemaFactory", this.bean.getDynamicSchemaFactory(), otherTyped.getDynamicSchemaFactory(), false);
            this.computeDiff("EagerFetchMode", this.bean.getEagerFetchMode(), otherTyped.getEagerFetchMode(), false);
            this.computeChildDiff("EmpressDictionary", this.bean.getEmpressDictionary(), otherTyped.getEmpressDictionary(), false);
            this.computeChildDiff("ExceptionOrphanedKeyAction", this.bean.getExceptionOrphanedKeyAction(), otherTyped.getExceptionOrphanedKeyAction(), false);
            this.computeChildDiff("ExportProfiling", this.bean.getExportProfiling(), otherTyped.getExportProfiling(), false);
            this.computeChildDiff("ExtensionDeprecatedJDOMappingFactory", this.bean.getExtensionDeprecatedJDOMappingFactory(), otherTyped.getExtensionDeprecatedJDOMappingFactory(), false);
            this.computeDiff("FetchBatchSize", this.bean.getFetchBatchSize(), otherTyped.getFetchBatchSize(), true);
            this.computeDiff("FetchDirection", this.bean.getFetchDirection(), otherTyped.getFetchDirection(), false);
            this.computeSubDiff("FetchGroups", this.bean.getFetchGroups(), otherTyped.getFetchGroups());
            this.computeChildDiff("FileSchemaFactory", this.bean.getFileSchemaFactory(), otherTyped.getFileSchemaFactory(), false);
            this.computeSubDiff("FilterListeners", this.bean.getFilterListeners(), otherTyped.getFilterListeners());
            this.computeDiff("FlushBeforeQueries", this.bean.getFlushBeforeQueries(), otherTyped.getFlushBeforeQueries(), false);
            this.computeChildDiff("FoxProDictionary", this.bean.getFoxProDictionary(), otherTyped.getFoxProDictionary(), false);
            this.computeChildDiff("GUIJMX", this.bean.getGUIJMX(), otherTyped.getGUIJMX(), false);
            this.computeChildDiff("GUIProfiling", this.bean.getGUIProfiling(), otherTyped.getGUIProfiling(), false);
            this.computeChildDiff("HSQLDictionary", this.bean.getHSQLDictionary(), otherTyped.getHSQLDictionary(), false);
            this.computeChildDiff("HTTPTransport", this.bean.getHTTPTransport(), otherTyped.getHTTPTransport(), false);
            this.computeDiff("IgnoreChanges", this.bean.getIgnoreChanges(), otherTyped.getIgnoreChanges(), false);
            this.computeChildDiff("InMemorySavepointManager", this.bean.getInMemorySavepointManager(), otherTyped.getInMemorySavepointManager(), false);
            this.computeChildDiff("InformixDictionary", this.bean.getInformixDictionary(), otherTyped.getInformixDictionary(), false);
            this.computeChildDiff("InverseManager", this.bean.getInverseManager(), otherTyped.getInverseManager(), false);
            this.computeChildDiff("JDBC3SavepointManager", this.bean.getJDBC3SavepointManager(), otherTyped.getJDBC3SavepointManager(), false);
            this.computeChildDiff("JDBCBrokerFactory", this.bean.getJDBCBrokerFactory(), otherTyped.getJDBCBrokerFactory(), false);
            this.computeSubDiff("JDBCListeners", this.bean.getJDBCListeners(), otherTyped.getJDBCListeners());
            this.computeChildDiff("JDOMetaDataFactory", this.bean.getJDOMetaDataFactory(), otherTyped.getJDOMetaDataFactory(), false);
            this.computeChildDiff("JDataStoreDictionary", this.bean.getJDataStoreDictionary(), otherTyped.getJDataStoreDictionary(), false);
            this.computeChildDiff("JMSRemoteCommitProvider", this.bean.getJMSRemoteCommitProvider(), otherTyped.getJMSRemoteCommitProvider(), false);
            this.computeChildDiff("JMX2JMX", this.bean.getJMX2JMX(), otherTyped.getJMX2JMX(), false);
            this.computeChildDiff("KodoBroker", this.bean.getKodoBroker(), otherTyped.getKodoBroker(), false);
            this.computeChildDiff("KodoDataCacheManager", this.bean.getKodoDataCacheManager(), otherTyped.getKodoDataCacheManager(), false);
            this.computeChildDiff("KodoMappingRepository", this.bean.getKodoMappingRepository(), otherTyped.getKodoMappingRepository(), false);
            this.computeChildDiff("KodoPersistenceMappingFactory", this.bean.getKodoPersistenceMappingFactory(), otherTyped.getKodoPersistenceMappingFactory(), false);
            this.computeChildDiff("KodoPersistenceMetaDataFactory", this.bean.getKodoPersistenceMetaDataFactory(), otherTyped.getKodoPersistenceMetaDataFactory(), false);
            this.computeChildDiff("KodoPoolingDataSource", this.bean.getKodoPoolingDataSource(), otherTyped.getKodoPoolingDataSource(), false);
            this.computeChildDiff("KodoSQLFactory", this.bean.getKodoSQLFactory(), otherTyped.getKodoSQLFactory(), false);
            this.computeDiff("LRSSize", this.bean.getLRSSize(), otherTyped.getLRSSize(), false);
            this.computeChildDiff("LazySchemaFactory", this.bean.getLazySchemaFactory(), otherTyped.getLazySchemaFactory(), false);
            this.computeChildDiff("LocalJMX", this.bean.getLocalJMX(), otherTyped.getLocalJMX(), false);
            this.computeChildDiff("LocalProfiling", this.bean.getLocalProfiling(), otherTyped.getLocalProfiling(), false);
            this.computeDiff("LockTimeout", this.bean.getLockTimeout(), otherTyped.getLockTimeout(), true);
            this.computeChildDiff("Log4JLogFactory", this.bean.getLog4JLogFactory(), otherTyped.getLog4JLogFactory(), false);
            this.computeChildDiff("LogFactoryImpl", this.bean.getLogFactoryImpl(), otherTyped.getLogFactoryImpl(), false);
            this.computeChildDiff("LogOrphanedKeyAction", this.bean.getLogOrphanedKeyAction(), otherTyped.getLogOrphanedKeyAction(), false);
            this.computeChildDiff("MX4J1JMX", this.bean.getMX4J1JMX(), otherTyped.getMX4J1JMX(), false);
            this.computeDiff("Mapping", this.bean.getMapping(), otherTyped.getMapping(), false);
            this.computeChildDiff("MappingDefaultsImpl", this.bean.getMappingDefaultsImpl(), otherTyped.getMappingDefaultsImpl(), false);
            this.computeChildDiff("MappingFileDeprecatedJDOMappingFactory", this.bean.getMappingFileDeprecatedJDOMappingFactory(), otherTyped.getMappingFileDeprecatedJDOMappingFactory(), false);
            this.computeDiff("Multithreaded", this.bean.getMultithreaded(), otherTyped.getMultithreaded(), false);
            this.computeChildDiff("MySQLDictionary", this.bean.getMySQLDictionary(), otherTyped.getMySQLDictionary(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("NativeJDBCSeq", this.bean.getNativeJDBCSeq(), otherTyped.getNativeJDBCSeq(), false);
            this.computeChildDiff("NoneJMX", this.bean.getNoneJMX(), otherTyped.getNoneJMX(), false);
            this.computeChildDiff("NoneLockManager", this.bean.getNoneLockManager(), otherTyped.getNoneLockManager(), false);
            this.computeChildDiff("NoneLogFactory", this.bean.getNoneLogFactory(), otherTyped.getNoneLogFactory(), false);
            this.computeChildDiff("NoneOrphanedKeyAction", this.bean.getNoneOrphanedKeyAction(), otherTyped.getNoneOrphanedKeyAction(), false);
            this.computeChildDiff("NoneProfiling", this.bean.getNoneProfiling(), otherTyped.getNoneProfiling(), false);
            this.computeDiff("NontransactionalRead", this.bean.getNontransactionalRead(), otherTyped.getNontransactionalRead(), false);
            this.computeDiff("NontransactionalWrite", this.bean.getNontransactionalWrite(), otherTyped.getNontransactionalWrite(), false);
            this.computeChildDiff("ORMFileJDORMappingFactory", this.bean.getORMFileJDORMappingFactory(), otherTyped.getORMFileJDORMappingFactory(), false);
            this.computeChildDiff("OperationOrderUpdateManager", this.bean.getOperationOrderUpdateManager(), otherTyped.getOperationOrderUpdateManager(), false);
            this.computeDiff("Optimistic", this.bean.getOptimistic(), otherTyped.getOptimistic(), false);
            this.computeChildDiff("OracleDictionary", this.bean.getOracleDictionary(), otherTyped.getOracleDictionary(), false);
            this.computeChildDiff("OracleSavepointManager", this.bean.getOracleSavepointManager(), otherTyped.getOracleSavepointManager(), false);
            this.computeChildDiff("PersistenceMappingDefaults", this.bean.getPersistenceMappingDefaults(), otherTyped.getPersistenceMappingDefaults(), false);
            this.computeChildDiff("PessimisticLockManager", this.bean.getPessimisticLockManager(), otherTyped.getPessimisticLockManager(), false);
            this.computeChildDiff("PointbaseDictionary", this.bean.getPointbaseDictionary(), otherTyped.getPointbaseDictionary(), false);
            this.computeChildDiff("PostgresDictionary", this.bean.getPostgresDictionary(), otherTyped.getPostgresDictionary(), false);
            this.computeChildDiff("ProfilingProxyManager", this.bean.getProfilingProxyManager(), otherTyped.getProfilingProxyManager(), false);
            this.computeChildDiff("ProxyManagerImpl", this.bean.getProxyManagerImpl(), otherTyped.getProxyManagerImpl(), false);
            this.computeSubDiff("QueryCaches", this.bean.getQueryCaches(), otherTyped.getQueryCaches());
            this.computeDiff("ReadLockLevel", this.bean.getReadLockLevel(), otherTyped.getReadLockLevel(), false);
            this.computeDiff("RestoreState", this.bean.getRestoreState(), otherTyped.getRestoreState(), false);
            this.computeDiff("ResultSetType", this.bean.getResultSetType(), otherTyped.getResultSetType(), false);
            this.computeDiff("RetainState", this.bean.getRetainState(), otherTyped.getRetainState(), false);
            this.computeDiff("RetryClassRegistration", this.bean.getRetryClassRegistration(), otherTyped.getRetryClassRegistration(), false);
            this.computeChildDiff("SQLServerDictionary", this.bean.getSQLServerDictionary(), otherTyped.getSQLServerDictionary(), false);
            this.computeDiff("Schema", this.bean.getSchema(), otherTyped.getSchema(), false);
            this.computeSubDiff("Schemata", this.bean.getSchemata(), otherTyped.getSchemata());
            this.computeChildDiff("SimpleDriverDataSource", this.bean.getSimpleDriverDataSource(), otherTyped.getSimpleDriverDataSource(), false);
            this.computeChildDiff("SingleJVMExclusiveLockManager", this.bean.getSingleJVMExclusiveLockManager(), otherTyped.getSingleJVMExclusiveLockManager(), false);
            this.computeChildDiff("SingleJVMRemoteCommitProvider", this.bean.getSingleJVMRemoteCommitProvider(), otherTyped.getSingleJVMRemoteCommitProvider(), false);
            this.computeChildDiff("StackExecutionContextNameProvider", this.bean.getStackExecutionContextNameProvider(), otherTyped.getStackExecutionContextNameProvider(), false);
            this.computeDiff("SubclassFetchMode", this.bean.getSubclassFetchMode(), otherTyped.getSubclassFetchMode(), false);
            this.computeChildDiff("SybaseDictionary", this.bean.getSybaseDictionary(), otherTyped.getSybaseDictionary(), false);
            this.computeDiff("SynchronizeMappings", this.bean.getSynchronizeMappings(), otherTyped.getSynchronizeMappings(), false);
            this.computeChildDiff("TCPRemoteCommitProvider", this.bean.getTCPRemoteCommitProvider(), otherTyped.getTCPRemoteCommitProvider(), false);
            this.computeChildDiff("TCPTransport", this.bean.getTCPTransport(), otherTyped.getTCPTransport(), false);
            this.computeChildDiff("TableDeprecatedJDOMappingFactory", this.bean.getTableDeprecatedJDOMappingFactory(), otherTyped.getTableDeprecatedJDOMappingFactory(), false);
            this.computeChildDiff("TableJDBCSeq", this.bean.getTableJDBCSeq(), otherTyped.getTableJDBCSeq(), false);
            this.computeChildDiff("TableJDORMappingFactory", this.bean.getTableJDORMappingFactory(), otherTyped.getTableJDORMappingFactory(), false);
            this.computeChildDiff("TableLockUpdateManager", this.bean.getTableLockUpdateManager(), otherTyped.getTableLockUpdateManager(), false);
            this.computeChildDiff("TableSchemaFactory", this.bean.getTableSchemaFactory(), otherTyped.getTableSchemaFactory(), false);
            this.computeChildDiff("TimeSeededSeq", this.bean.getTimeSeededSeq(), otherTyped.getTimeSeededSeq(), false);
            this.computeDiff("TransactionIsolation", this.bean.getTransactionIsolation(), otherTyped.getTransactionIsolation(), false);
            this.computeDiff("TransactionMode", this.bean.getTransactionMode(), otherTyped.getTransactionMode(), false);
            this.computeChildDiff("TransactionNameExecutionContextNameProvider", this.bean.getTransactionNameExecutionContextNameProvider(), otherTyped.getTransactionNameExecutionContextNameProvider(), false);
            this.computeChildDiff("UserObjectExecutionContextNameProvider", this.bean.getUserObjectExecutionContextNameProvider(), otherTyped.getUserObjectExecutionContextNameProvider(), false);
            this.computeChildDiff("ValueTableJDBCSeq", this.bean.getValueTableJDBCSeq(), otherTyped.getValueTableJDBCSeq(), false);
            this.computeChildDiff("VersionLockManager", this.bean.getVersionLockManager(), otherTyped.getVersionLockManager(), false);
            this.computeChildDiff("WLS81JMX", this.bean.getWLS81JMX(), otherTyped.getWLS81JMX(), false);
            this.computeDiff("WriteLockLevel", this.bean.getWriteLockLevel(), otherTyped.getWriteLockLevel(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceUnitConfigurationBeanImpl original = (PersistenceUnitConfigurationBeanImpl)event.getSourceBean();
            PersistenceUnitConfigurationBeanImpl proposed = (PersistenceUnitConfigurationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AbstractStoreBrokerFactory")) {
                  if (type == 2) {
                     original.setAbstractStoreBrokerFactory((AbstractStoreBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getAbstractStoreBrokerFactory()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AbstractStoreBrokerFactory", (DescriptorBean)original.getAbstractStoreBrokerFactory());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("AccessDictionary")) {
                  if (type == 2) {
                     original.setAccessDictionary((AccessDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getAccessDictionary()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AccessDictionary", (DescriptorBean)original.getAccessDictionary());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("AggregateListeners")) {
                  if (type == 2) {
                     original.setAggregateListeners((AggregateListenersBean)this.createCopy((AbstractDescriptorBean)proposed.getAggregateListeners()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AggregateListeners", (DescriptorBean)original.getAggregateListeners());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("AutoClear")) {
                  original.setAutoClear(proposed.getAutoClear());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("AutoDetaches")) {
                  if (type == 2) {
                     original.setAutoDetaches((AutoDetachBean)this.createCopy((AbstractDescriptorBean)proposed.getAutoDetaches()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AutoDetaches", (DescriptorBean)original.getAutoDetaches());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("BatchingOperationOrderUpdateManager")) {
                  if (type == 2) {
                     original.setBatchingOperationOrderUpdateManager((BatchingOperationOrderUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getBatchingOperationOrderUpdateManager()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("BatchingOperationOrderUpdateManager", (DescriptorBean)original.getBatchingOperationOrderUpdateManager());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 169);
               } else if (prop.equals("CacheMap")) {
                  if (type == 2) {
                     original.setCacheMap((CacheMapBean)this.createCopy((AbstractDescriptorBean)proposed.getCacheMap()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CacheMap", (DescriptorBean)original.getCacheMap());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 128);
               } else if (prop.equals("ClassTableJDBCSeq")) {
                  if (type == 2) {
                     original.setClassTableJDBCSeq((ClassTableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getClassTableJDBCSeq()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClassTableJDBCSeq", (DescriptorBean)original.getClassTableJDBCSeq());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 154);
               } else if (prop.equals("ClientBrokerFactory")) {
                  if (type == 2) {
                     original.setClientBrokerFactory((ClientBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getClientBrokerFactory()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClientBrokerFactory", (DescriptorBean)original.getClientBrokerFactory());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ClusterRemoteCommitProvider")) {
                  if (type == 2) {
                     original.setClusterRemoteCommitProvider((ClusterRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getClusterRemoteCommitProvider()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClusterRemoteCommitProvider", (DescriptorBean)original.getClusterRemoteCommitProvider());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 135);
               } else if (prop.equals("CommonsLogFactory")) {
                  if (type == 2) {
                     original.setCommonsLogFactory((CommonsLogFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCommonsLogFactory()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CommonsLogFactory", (DescriptorBean)original.getCommonsLogFactory());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 83);
               } else if (prop.equals("Compatibility")) {
                  if (type == 2) {
                     original.setCompatibility((KodoCompatibilityBean)this.createCopy((AbstractDescriptorBean)proposed.getCompatibility()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Compatibility", (DescriptorBean)original.getCompatibility());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ConcurrentHashMap")) {
                  if (type == 2) {
                     original.setConcurrentHashMap((ConcurrentHashMapBean)this.createCopy((AbstractDescriptorBean)proposed.getConcurrentHashMap()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConcurrentHashMap", (DescriptorBean)original.getConcurrentHashMap());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 129);
               } else if (prop.equals("Connection2DriverName")) {
                  original.setConnection2DriverName(proposed.getConnection2DriverName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (!prop.equals("Connection2Password")) {
                  if (prop.equals("Connection2PasswordEncrypted")) {
                     original.setConnection2PasswordEncrypted(proposed.getConnection2PasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("Connection2Properties")) {
                     if (type == 2) {
                        original.setConnection2Properties((PropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnection2Properties()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("Connection2Properties", (DescriptorBean)original.getConnection2Properties());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("Connection2URL")) {
                     original.setConnection2URL(proposed.getConnection2URL());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("Connection2UserName")) {
                     original.setConnection2UserName(proposed.getConnection2UserName());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("ConnectionDecorators")) {
                     if (type == 2) {
                        original.setConnectionDecorators((ConnectionDecoratorsBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionDecorators()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("ConnectionDecorators", (DescriptorBean)original.getConnectionDecorators());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("ConnectionDriverName")) {
                     original.setConnectionDriverName(proposed.getConnectionDriverName());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("ConnectionFactory2Name")) {
                     original.setConnectionFactory2Name(proposed.getConnectionFactory2Name());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  } else if (prop.equals("ConnectionFactory2Properties")) {
                     if (type == 2) {
                        original.setConnectionFactory2Properties((PropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionFactory2Properties()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("ConnectionFactory2Properties", (DescriptorBean)original.getConnectionFactory2Properties());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("ConnectionFactoryMode")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("ConnectionFactoryName")) {
                     original.setConnectionFactoryName(proposed.getConnectionFactoryName());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (prop.equals("ConnectionFactoryProperties")) {
                     if (type == 2) {
                        original.setConnectionFactoryProperties((PropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionFactoryProperties()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("ConnectionFactoryProperties", (DescriptorBean)original.getConnectionFactoryProperties());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (!prop.equals("ConnectionPassword")) {
                     if (prop.equals("ConnectionPasswordEncrypted")) {
                        original.setConnectionPasswordEncrypted(proposed.getConnectionPasswordEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("ConnectionProperties")) {
                        if (type == 2) {
                           original.setConnectionProperties((PropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionProperties()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ConnectionProperties", (DescriptorBean)original.getConnectionProperties());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("ConnectionRetainMode")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (prop.equals("ConnectionURL")) {
                        original.setConnectionURL(proposed.getConnectionURL());
                        original._conditionalUnset(update.isUnsetUpdate(), 34);
                     } else if (prop.equals("ConnectionUserName")) {
                        original.setConnectionUserName(proposed.getConnectionUserName());
                        original._conditionalUnset(update.isUnsetUpdate(), 35);
                     } else if (prop.equals("ConstraintUpdateManager")) {
                        if (type == 2) {
                           original.setConstraintUpdateManager((ConstraintUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getConstraintUpdateManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ConstraintUpdateManager", (DescriptorBean)original.getConstraintUpdateManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 168);
                     } else if (prop.equals("CustomBrokerFactory")) {
                        if (type == 2) {
                           original.setCustomBrokerFactory((CustomBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomBrokerFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomBrokerFactory", (DescriptorBean)original.getCustomBrokerFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 8);
                     } else if (prop.equals("CustomBrokerImpl")) {
                        if (type == 2) {
                           original.setCustomBrokerImpl((CustomBrokerImplBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomBrokerImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomBrokerImpl", (DescriptorBean)original.getCustomBrokerImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("CustomClassResolver")) {
                        if (type == 2) {
                           original.setCustomClassResolver((CustomClassResolverBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomClassResolver()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomClassResolver", (DescriptorBean)original.getCustomClassResolver());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     } else if (prop.equals("CustomCompatibility")) {
                        if (type == 2) {
                           original.setCustomCompatibility((CustomCompatibilityBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomCompatibility()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomCompatibility", (DescriptorBean)original.getCustomCompatibility());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (prop.equals("CustomDataCacheManager")) {
                        if (type == 2) {
                           original.setCustomDataCacheManager((CustomDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomDataCacheManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomDataCacheManager", (DescriptorBean)original.getCustomDataCacheManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 40);
                     } else if (prop.equals("CustomDetachState")) {
                        if (type == 2) {
                           original.setCustomDetachState((CustomDetachStateBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomDetachState()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomDetachState", (DescriptorBean)original.getCustomDetachState());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 61);
                     } else if (prop.equals("CustomDictionary")) {
                        if (type == 2) {
                           original.setCustomDictionary((CustomDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomDictionary", (DescriptorBean)original.getCustomDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 56);
                     } else if (prop.equals("CustomDriverDataSource")) {
                        if (type == 2) {
                           original.setCustomDriverDataSource((CustomDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomDriverDataSource()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomDriverDataSource", (DescriptorBean)original.getCustomDriverDataSource());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 65);
                     } else if (prop.equals("CustomLockManager")) {
                        if (type == 2) {
                           original.setCustomLockManager((CustomLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomLockManager", (DescriptorBean)original.getCustomLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 81);
                     } else if (prop.equals("CustomLog")) {
                        if (type == 2) {
                           original.setCustomLog((CustomLogBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomLog()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomLog", (DescriptorBean)original.getCustomLog());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 87);
                     } else if (prop.equals("CustomMappingDefaults")) {
                        if (type == 2) {
                           original.setCustomMappingDefaults((CustomMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomMappingDefaults()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomMappingDefaults", (DescriptorBean)original.getCustomMappingDefaults());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 94);
                     } else if (prop.equals("CustomMappingFactory")) {
                        if (type == 2) {
                           original.setCustomMappingFactory((CustomMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomMappingFactory", (DescriptorBean)original.getCustomMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 101);
                     } else if (prop.equals("CustomMetaDataFactory")) {
                        if (type == 2) {
                           original.setCustomMetaDataFactory((CustomMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomMetaDataFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomMetaDataFactory", (DescriptorBean)original.getCustomMetaDataFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 106);
                     } else if (prop.equals("CustomMetaDataRepository")) {
                        if (type == 2) {
                           original.setCustomMetaDataRepository((CustomMetaDataRepositoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomMetaDataRepository()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomMetaDataRepository", (DescriptorBean)original.getCustomMetaDataRepository());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 109);
                     } else if (prop.equals("CustomOrphanedKeyAction")) {
                        if (type == 2) {
                           original.setCustomOrphanedKeyAction((CustomOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomOrphanedKeyAction()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomOrphanedKeyAction", (DescriptorBean)original.getCustomOrphanedKeyAction());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 118);
                     } else if (prop.equals("CustomPersistenceServer")) {
                        if (type == 2) {
                           original.setCustomPersistenceServer((CustomPersistenceServerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomPersistenceServer()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomPersistenceServer", (DescriptorBean)original.getCustomPersistenceServer());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 121);
                     } else if (prop.equals("CustomProxyManager")) {
                        if (type == 2) {
                           original.setCustomProxyManager((CustomProxyManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomProxyManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomProxyManager", (DescriptorBean)original.getCustomProxyManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 125);
                     } else if (prop.equals("CustomQueryCompilationCache")) {
                        if (type == 2) {
                           original.setCustomQueryCompilationCache((CustomQueryCompilationCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomQueryCompilationCache()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomQueryCompilationCache", (DescriptorBean)original.getCustomQueryCompilationCache());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 130);
                     } else if (prop.equals("CustomRemoteCommitProvider")) {
                        if (type == 2) {
                           original.setCustomRemoteCommitProvider((CustomRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomRemoteCommitProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomRemoteCommitProvider", (DescriptorBean)original.getCustomRemoteCommitProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 136);
                     } else if (prop.equals("CustomSQLFactory")) {
                        if (type == 2) {
                           original.setCustomSQLFactory((CustomSQLFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomSQLFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomSQLFactory", (DescriptorBean)original.getCustomSQLFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 162);
                     } else if (prop.equals("CustomSavepointManager")) {
                        if (type == 2) {
                           original.setCustomSavepointManager((CustomSavepointManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomSavepointManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomSavepointManager", (DescriptorBean)original.getCustomSavepointManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 145);
                     } else if (prop.equals("CustomSchemaFactory")) {
                        if (type == 2) {
                           original.setCustomSchemaFactory((CustomSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomSchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomSchemaFactory", (DescriptorBean)original.getCustomSchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 152);
                     } else if (prop.equals("CustomSeq")) {
                        if (type == 2) {
                           original.setCustomSeq((CustomSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomSeq()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomSeq", (DescriptorBean)original.getCustomSeq());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 159);
                     } else if (prop.equals("CustomUpdateManager")) {
                        if (type == 2) {
                           original.setCustomUpdateManager((CustomUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getCustomUpdateManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("CustomUpdateManager", (DescriptorBean)original.getCustomUpdateManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 172);
                     } else if (prop.equals("DB2Dictionary")) {
                        if (type == 2) {
                           original.setDB2Dictionary((DB2DictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getDB2Dictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DB2Dictionary", (DescriptorBean)original.getDB2Dictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 43);
                     } else if (prop.equals("DataCacheManagerImpl")) {
                        if (type == 2) {
                           original.setDataCacheManagerImpl((DataCacheManagerImplBean)this.createCopy((AbstractDescriptorBean)proposed.getDataCacheManagerImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DataCacheManagerImpl", (DescriptorBean)original.getDataCacheManagerImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 39);
                     } else if (prop.equals("DataCacheTimeout")) {
                        original.setDataCacheTimeout(proposed.getDataCacheTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 41);
                     } else if (prop.equals("DataCaches")) {
                        if (type == 2) {
                           original.setDataCaches((DataCachesBean)this.createCopy((AbstractDescriptorBean)proposed.getDataCaches()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DataCaches", (DescriptorBean)original.getDataCaches());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 36);
                     } else if (prop.equals("DefaultBrokerFactory")) {
                        if (type == 2) {
                           original.setDefaultBrokerFactory((DefaultBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultBrokerFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultBrokerFactory", (DescriptorBean)original.getDefaultBrokerFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 4);
                     } else if (prop.equals("DefaultBrokerImpl")) {
                        if (type == 2) {
                           original.setDefaultBrokerImpl((DefaultBrokerImplBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultBrokerImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultBrokerImpl", (DescriptorBean)original.getDefaultBrokerImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 9);
                     } else if (prop.equals("DefaultClassResolver")) {
                        if (type == 2) {
                           original.setDefaultClassResolver((DefaultClassResolverBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultClassResolver()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultClassResolver", (DescriptorBean)original.getDefaultClassResolver());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     } else if (prop.equals("DefaultCompatibility")) {
                        if (type == 2) {
                           original.setDefaultCompatibility((DefaultCompatibilityBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultCompatibility()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultCompatibility", (DescriptorBean)original.getDefaultCompatibility());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (prop.equals("DefaultDataCacheManager")) {
                        if (type == 2) {
                           original.setDefaultDataCacheManager((DefaultDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultDataCacheManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultDataCacheManager", (DescriptorBean)original.getDefaultDataCacheManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 37);
                     } else if (prop.equals("DefaultDetachState")) {
                        if (type == 2) {
                           original.setDefaultDetachState((DefaultDetachStateBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultDetachState()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultDetachState", (DescriptorBean)original.getDefaultDetachState());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 57);
                     } else if (prop.equals("DefaultDriverDataSource")) {
                        if (type == 2) {
                           original.setDefaultDriverDataSource((DefaultDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultDriverDataSource()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultDriverDataSource", (DescriptorBean)original.getDefaultDriverDataSource());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 62);
                     } else if (prop.equals("DefaultLockManager")) {
                        if (type == 2) {
                           original.setDefaultLockManager((DefaultLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultLockManager", (DescriptorBean)original.getDefaultLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 76);
                     } else if (prop.equals("DefaultMappingDefaults")) {
                        if (type == 2) {
                           original.setDefaultMappingDefaults((DefaultMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultMappingDefaults()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultMappingDefaults", (DescriptorBean)original.getDefaultMappingDefaults());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 90);
                     } else if (prop.equals("DefaultMetaDataFactory")) {
                        if (type == 2) {
                           original.setDefaultMetaDataFactory((DefaultMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultMetaDataFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultMetaDataFactory", (DescriptorBean)original.getDefaultMetaDataFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 102);
                     } else if (prop.equals("DefaultMetaDataRepository")) {
                        if (type == 2) {
                           original.setDefaultMetaDataRepository((DefaultMetaDataRepositoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultMetaDataRepository()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultMetaDataRepository", (DescriptorBean)original.getDefaultMetaDataRepository());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 107);
                     } else if (prop.equals("DefaultOrphanedKeyAction")) {
                        if (type == 2) {
                           original.setDefaultOrphanedKeyAction((DefaultOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultOrphanedKeyAction()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultOrphanedKeyAction", (DescriptorBean)original.getDefaultOrphanedKeyAction());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 114);
                     } else if (prop.equals("DefaultProxyManager")) {
                        if (type == 2) {
                           original.setDefaultProxyManager((DefaultProxyManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultProxyManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultProxyManager", (DescriptorBean)original.getDefaultProxyManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 122);
                     } else if (prop.equals("DefaultQueryCompilationCache")) {
                        if (type == 2) {
                           original.setDefaultQueryCompilationCache((DefaultQueryCompilationCacheBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultQueryCompilationCache()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultQueryCompilationCache", (DescriptorBean)original.getDefaultQueryCompilationCache());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 127);
                     } else if (prop.equals("DefaultSQLFactory")) {
                        if (type == 2) {
                           original.setDefaultSQLFactory((DefaultSQLFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultSQLFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultSQLFactory", (DescriptorBean)original.getDefaultSQLFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 160);
                     } else if (prop.equals("DefaultSavepointManager")) {
                        if (type == 2) {
                           original.setDefaultSavepointManager((DefaultSavepointManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultSavepointManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultSavepointManager", (DescriptorBean)original.getDefaultSavepointManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 141);
                     } else if (prop.equals("DefaultSchemaFactory")) {
                        if (type == 2) {
                           original.setDefaultSchemaFactory((DefaultSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultSchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultSchemaFactory", (DescriptorBean)original.getDefaultSchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 147);
                     } else if (prop.equals("DefaultUpdateManager")) {
                        if (type == 2) {
                           original.setDefaultUpdateManager((DefaultUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultUpdateManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DefaultUpdateManager", (DescriptorBean)original.getDefaultUpdateManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 167);
                     } else if (prop.equals("DeprecatedJDOMappingDefaults")) {
                        if (type == 2) {
                           original.setDeprecatedJDOMappingDefaults((DeprecatedJDOMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)proposed.getDeprecatedJDOMappingDefaults()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DeprecatedJDOMappingDefaults", (DescriptorBean)original.getDeprecatedJDOMappingDefaults());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 91);
                     } else if (prop.equals("DeprecatedJDOMetaDataFactory")) {
                        if (type == 2) {
                           original.setDeprecatedJDOMetaDataFactory((DeprecatedJDOMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDeprecatedJDOMetaDataFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DeprecatedJDOMetaDataFactory", (DescriptorBean)original.getDeprecatedJDOMetaDataFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 104);
                     } else if (prop.equals("DerbyDictionary")) {
                        if (type == 2) {
                           original.setDerbyDictionary((DerbyDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getDerbyDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DerbyDictionary", (DescriptorBean)original.getDerbyDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 44);
                     } else if (prop.equals("DetachOptionsAll")) {
                        if (type == 2) {
                           original.setDetachOptionsAll((DetachOptionsAllBean)this.createCopy((AbstractDescriptorBean)proposed.getDetachOptionsAll()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DetachOptionsAll", (DescriptorBean)original.getDetachOptionsAll());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 60);
                     } else if (prop.equals("DetachOptionsFetchGroups")) {
                        if (type == 2) {
                           original.setDetachOptionsFetchGroups((DetachOptionsFetchGroupsBean)this.createCopy((AbstractDescriptorBean)proposed.getDetachOptionsFetchGroups()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DetachOptionsFetchGroups", (DescriptorBean)original.getDetachOptionsFetchGroups());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 59);
                     } else if (prop.equals("DetachOptionsLoaded")) {
                        if (type == 2) {
                           original.setDetachOptionsLoaded((DetachOptionsLoadedBean)this.createCopy((AbstractDescriptorBean)proposed.getDetachOptionsLoaded()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DetachOptionsLoaded", (DescriptorBean)original.getDetachOptionsLoaded());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 58);
                     } else if (prop.equals("DynamicDataStructs")) {
                        original.setDynamicDataStructs(proposed.getDynamicDataStructs());
                        original._conditionalUnset(update.isUnsetUpdate(), 66);
                     } else if (prop.equals("DynamicSchemaFactory")) {
                        if (type == 2) {
                           original.setDynamicSchemaFactory((DynamicSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getDynamicSchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("DynamicSchemaFactory", (DescriptorBean)original.getDynamicSchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 148);
                     } else if (prop.equals("EagerFetchMode")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 67);
                     } else if (prop.equals("EmpressDictionary")) {
                        if (type == 2) {
                           original.setEmpressDictionary((EmpressDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getEmpressDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("EmpressDictionary", (DescriptorBean)original.getEmpressDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 45);
                     } else if (prop.equals("ExceptionOrphanedKeyAction")) {
                        if (type == 2) {
                           original.setExceptionOrphanedKeyAction((ExceptionOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)proposed.getExceptionOrphanedKeyAction()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ExceptionOrphanedKeyAction", (DescriptorBean)original.getExceptionOrphanedKeyAction());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 116);
                     } else if (prop.equals("ExportProfiling")) {
                        if (type == 2) {
                           original.setExportProfiling((ExportProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getExportProfiling()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ExportProfiling", (DescriptorBean)original.getExportProfiling());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 185);
                     } else if (prop.equals("ExtensionDeprecatedJDOMappingFactory")) {
                        if (type == 2) {
                           original.setExtensionDeprecatedJDOMappingFactory((ExtensionDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getExtensionDeprecatedJDOMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ExtensionDeprecatedJDOMappingFactory", (DescriptorBean)original.getExtensionDeprecatedJDOMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 95);
                     } else if (prop.equals("FetchBatchSize")) {
                        original.setFetchBatchSize(proposed.getFetchBatchSize());
                        original._conditionalUnset(update.isUnsetUpdate(), 68);
                     } else if (prop.equals("FetchDirection")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 69);
                     } else if (prop.equals("FetchGroups")) {
                        if (type == 2) {
                           original.setFetchGroups((FetchGroupsBean)this.createCopy((AbstractDescriptorBean)proposed.getFetchGroups()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("FetchGroups", (DescriptorBean)original.getFetchGroups());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 70);
                     } else if (prop.equals("FileSchemaFactory")) {
                        if (type == 2) {
                           original.setFileSchemaFactory((FileSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getFileSchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("FileSchemaFactory", (DescriptorBean)original.getFileSchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 149);
                     } else if (prop.equals("FilterListeners")) {
                        if (type == 2) {
                           original.setFilterListeners((FilterListenersBean)this.createCopy((AbstractDescriptorBean)proposed.getFilterListeners()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("FilterListeners", (DescriptorBean)original.getFilterListeners());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 71);
                     } else if (prop.equals("FlushBeforeQueries")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 72);
                     } else if (prop.equals("FoxProDictionary")) {
                        if (type == 2) {
                           original.setFoxProDictionary((FoxProDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getFoxProDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("FoxProDictionary", (DescriptorBean)original.getFoxProDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 46);
                     } else if (prop.equals("GUIJMX")) {
                        if (type == 2) {
                           original.setGUIJMX((GUIJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getGUIJMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("GUIJMX", (DescriptorBean)original.getGUIJMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 179);
                     } else if (prop.equals("GUIProfiling")) {
                        if (type == 2) {
                           original.setGUIProfiling((GUIProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getGUIProfiling()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("GUIProfiling", (DescriptorBean)original.getGUIProfiling());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 186);
                     } else if (prop.equals("HSQLDictionary")) {
                        if (type == 2) {
                           original.setHSQLDictionary((HSQLDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getHSQLDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("HSQLDictionary", (DescriptorBean)original.getHSQLDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 47);
                     } else if (prop.equals("HTTPTransport")) {
                        if (type == 2) {
                           original.setHTTPTransport((HTTPTransportBean)this.createCopy((AbstractDescriptorBean)proposed.getHTTPTransport()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("HTTPTransport", (DescriptorBean)original.getHTTPTransport());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 119);
                     } else if (prop.equals("IgnoreChanges")) {
                        original.setIgnoreChanges(proposed.getIgnoreChanges());
                        original._conditionalUnset(update.isUnsetUpdate(), 73);
                     } else if (prop.equals("InMemorySavepointManager")) {
                        if (type == 2) {
                           original.setInMemorySavepointManager((InMemorySavepointManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getInMemorySavepointManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("InMemorySavepointManager", (DescriptorBean)original.getInMemorySavepointManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 142);
                     } else if (prop.equals("InformixDictionary")) {
                        if (type == 2) {
                           original.setInformixDictionary((InformixDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getInformixDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("InformixDictionary", (DescriptorBean)original.getInformixDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 48);
                     } else if (prop.equals("InverseManager")) {
                        if (type == 2) {
                           original.setInverseManager((InverseManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getInverseManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("InverseManager", (DescriptorBean)original.getInverseManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 74);
                     } else if (prop.equals("JDBC3SavepointManager")) {
                        if (type == 2) {
                           original.setJDBC3SavepointManager((JDBC3SavepointManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBC3SavepointManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JDBC3SavepointManager", (DescriptorBean)original.getJDBC3SavepointManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 143);
                     } else if (prop.equals("JDBCBrokerFactory")) {
                        if (type == 2) {
                           original.setJDBCBrokerFactory((JDBCBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCBrokerFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JDBCBrokerFactory", (DescriptorBean)original.getJDBCBrokerFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 7);
                     } else if (prop.equals("JDBCListeners")) {
                        if (type == 2) {
                           original.setJDBCListeners((JDBCListenersBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCListeners()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JDBCListeners", (DescriptorBean)original.getJDBCListeners());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 75);
                     } else if (prop.equals("JDOMetaDataFactory")) {
                        if (type == 2) {
                           original.setJDOMetaDataFactory((JDOMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getJDOMetaDataFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JDOMetaDataFactory", (DescriptorBean)original.getJDOMetaDataFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 103);
                     } else if (prop.equals("JDataStoreDictionary")) {
                        if (type == 2) {
                           original.setJDataStoreDictionary((JDataStoreDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getJDataStoreDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JDataStoreDictionary", (DescriptorBean)original.getJDataStoreDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 49);
                     } else if (prop.equals("JMSRemoteCommitProvider")) {
                        if (type == 2) {
                           original.setJMSRemoteCommitProvider((JMSRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getJMSRemoteCommitProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JMSRemoteCommitProvider", (DescriptorBean)original.getJMSRemoteCommitProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 132);
                     } else if (prop.equals("JMX2JMX")) {
                        if (type == 2) {
                           original.setJMX2JMX((JMX2JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getJMX2JMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("JMX2JMX", (DescriptorBean)original.getJMX2JMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 180);
                     } else if (prop.equals("KodoBroker")) {
                        if (type == 2) {
                           original.setKodoBroker((KodoBrokerBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoBroker()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoBroker", (DescriptorBean)original.getKodoBroker());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 10);
                     } else if (prop.equals("KodoDataCacheManager")) {
                        if (type == 2) {
                           original.setKodoDataCacheManager((KodoDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoDataCacheManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoDataCacheManager", (DescriptorBean)original.getKodoDataCacheManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 38);
                     } else if (prop.equals("KodoMappingRepository")) {
                        if (type == 2) {
                           original.setKodoMappingRepository((KodoMappingRepositoryBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoMappingRepository()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoMappingRepository", (DescriptorBean)original.getKodoMappingRepository());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 108);
                     } else if (prop.equals("KodoPersistenceMappingFactory")) {
                        if (type == 2) {
                           original.setKodoPersistenceMappingFactory((KodoPersistenceMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoPersistenceMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoPersistenceMappingFactory", (DescriptorBean)original.getKodoPersistenceMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 96);
                     } else if (prop.equals("KodoPersistenceMetaDataFactory")) {
                        if (type == 2) {
                           original.setKodoPersistenceMetaDataFactory((KodoPersistenceMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoPersistenceMetaDataFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoPersistenceMetaDataFactory", (DescriptorBean)original.getKodoPersistenceMetaDataFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 105);
                     } else if (prop.equals("KodoPoolingDataSource")) {
                        if (type == 2) {
                           original.setKodoPoolingDataSource((KodoPoolingDataSourceBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoPoolingDataSource()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoPoolingDataSource", (DescriptorBean)original.getKodoPoolingDataSource());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 63);
                     } else if (prop.equals("KodoSQLFactory")) {
                        if (type == 2) {
                           original.setKodoSQLFactory((KodoSQLFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getKodoSQLFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("KodoSQLFactory", (DescriptorBean)original.getKodoSQLFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 161);
                     } else if (prop.equals("LRSSize")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 88);
                     } else if (prop.equals("LazySchemaFactory")) {
                        if (type == 2) {
                           original.setLazySchemaFactory((LazySchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getLazySchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("LazySchemaFactory", (DescriptorBean)original.getLazySchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 150);
                     } else if (prop.equals("LocalJMX")) {
                        if (type == 2) {
                           original.setLocalJMX((LocalJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getLocalJMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("LocalJMX", (DescriptorBean)original.getLocalJMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 178);
                     } else if (prop.equals("LocalProfiling")) {
                        if (type == 2) {
                           original.setLocalProfiling((LocalProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getLocalProfiling()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("LocalProfiling", (DescriptorBean)original.getLocalProfiling());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 184);
                     } else if (prop.equals("LockTimeout")) {
                        original.setLockTimeout(proposed.getLockTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 82);
                     } else if (prop.equals("Log4JLogFactory")) {
                        if (type == 2) {
                           original.setLog4JLogFactory((Log4JLogFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getLog4JLogFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("Log4JLogFactory", (DescriptorBean)original.getLog4JLogFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 84);
                     } else if (prop.equals("LogFactoryImpl")) {
                        if (type == 2) {
                           original.setLogFactoryImpl((LogFactoryImplBean)this.createCopy((AbstractDescriptorBean)proposed.getLogFactoryImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("LogFactoryImpl", (DescriptorBean)original.getLogFactoryImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 85);
                     } else if (prop.equals("LogOrphanedKeyAction")) {
                        if (type == 2) {
                           original.setLogOrphanedKeyAction((LogOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)proposed.getLogOrphanedKeyAction()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("LogOrphanedKeyAction", (DescriptorBean)original.getLogOrphanedKeyAction());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 115);
                     } else if (prop.equals("MX4J1JMX")) {
                        if (type == 2) {
                           original.setMX4J1JMX((MX4J1JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getMX4J1JMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("MX4J1JMX", (DescriptorBean)original.getMX4J1JMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 181);
                     } else if (prop.equals("Mapping")) {
                        original.setMapping(proposed.getMapping());
                        original._conditionalUnset(update.isUnsetUpdate(), 89);
                     } else if (prop.equals("MappingDefaultsImpl")) {
                        if (type == 2) {
                           original.setMappingDefaultsImpl((MappingDefaultsImplBean)this.createCopy((AbstractDescriptorBean)proposed.getMappingDefaultsImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("MappingDefaultsImpl", (DescriptorBean)original.getMappingDefaultsImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 92);
                     } else if (prop.equals("MappingFileDeprecatedJDOMappingFactory")) {
                        if (type == 2) {
                           original.setMappingFileDeprecatedJDOMappingFactory((MappingFileDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getMappingFileDeprecatedJDOMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("MappingFileDeprecatedJDOMappingFactory", (DescriptorBean)original.getMappingFileDeprecatedJDOMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 97);
                     } else if (prop.equals("Multithreaded")) {
                        original.setMultithreaded(proposed.getMultithreaded());
                        original._conditionalUnset(update.isUnsetUpdate(), 110);
                     } else if (prop.equals("MySQLDictionary")) {
                        if (type == 2) {
                           original.setMySQLDictionary((MySQLDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getMySQLDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("MySQLDictionary", (DescriptorBean)original.getMySQLDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 50);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 0);
                     } else if (prop.equals("NativeJDBCSeq")) {
                        if (type == 2) {
                           original.setNativeJDBCSeq((NativeJDBCSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getNativeJDBCSeq()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NativeJDBCSeq", (DescriptorBean)original.getNativeJDBCSeq());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 155);
                     } else if (prop.equals("NoneJMX")) {
                        if (type == 2) {
                           original.setNoneJMX((NoneJMXBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneJMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NoneJMX", (DescriptorBean)original.getNoneJMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 177);
                     } else if (prop.equals("NoneLockManager")) {
                        if (type == 2) {
                           original.setNoneLockManager((NoneLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NoneLockManager", (DescriptorBean)original.getNoneLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 78);
                     } else if (prop.equals("NoneLogFactory")) {
                        if (type == 2) {
                           original.setNoneLogFactory((NoneLogFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneLogFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NoneLogFactory", (DescriptorBean)original.getNoneLogFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 86);
                     } else if (prop.equals("NoneOrphanedKeyAction")) {
                        if (type == 2) {
                           original.setNoneOrphanedKeyAction((NoneOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneOrphanedKeyAction()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NoneOrphanedKeyAction", (DescriptorBean)original.getNoneOrphanedKeyAction());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 117);
                     } else if (prop.equals("NoneProfiling")) {
                        if (type == 2) {
                           original.setNoneProfiling((NoneProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneProfiling()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("NoneProfiling", (DescriptorBean)original.getNoneProfiling());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 183);
                     } else if (prop.equals("NontransactionalRead")) {
                        original.setNontransactionalRead(proposed.getNontransactionalRead());
                        original._conditionalUnset(update.isUnsetUpdate(), 111);
                     } else if (prop.equals("NontransactionalWrite")) {
                        original.setNontransactionalWrite(proposed.getNontransactionalWrite());
                        original._conditionalUnset(update.isUnsetUpdate(), 112);
                     } else if (prop.equals("ORMFileJDORMappingFactory")) {
                        if (type == 2) {
                           original.setORMFileJDORMappingFactory((ORMFileJDORMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getORMFileJDORMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ORMFileJDORMappingFactory", (DescriptorBean)original.getORMFileJDORMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 98);
                     } else if (prop.equals("OperationOrderUpdateManager")) {
                        if (type == 2) {
                           original.setOperationOrderUpdateManager((OperationOrderUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getOperationOrderUpdateManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("OperationOrderUpdateManager", (DescriptorBean)original.getOperationOrderUpdateManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 170);
                     } else if (prop.equals("Optimistic")) {
                        original.setOptimistic(proposed.getOptimistic());
                        original._conditionalUnset(update.isUnsetUpdate(), 113);
                     } else if (prop.equals("OracleDictionary")) {
                        if (type == 2) {
                           original.setOracleDictionary((OracleDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getOracleDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("OracleDictionary", (DescriptorBean)original.getOracleDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 51);
                     } else if (prop.equals("OracleSavepointManager")) {
                        if (type == 2) {
                           original.setOracleSavepointManager((OracleSavepointManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getOracleSavepointManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("OracleSavepointManager", (DescriptorBean)original.getOracleSavepointManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 144);
                     } else if (prop.equals("PersistenceMappingDefaults")) {
                        if (type == 2) {
                           original.setPersistenceMappingDefaults((PersistenceMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)proposed.getPersistenceMappingDefaults()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("PersistenceMappingDefaults", (DescriptorBean)original.getPersistenceMappingDefaults());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 93);
                     } else if (prop.equals("PessimisticLockManager")) {
                        if (type == 2) {
                           original.setPessimisticLockManager((PessimisticLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getPessimisticLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("PessimisticLockManager", (DescriptorBean)original.getPessimisticLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 77);
                     } else if (prop.equals("PointbaseDictionary")) {
                        if (type == 2) {
                           original.setPointbaseDictionary((PointbaseDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getPointbaseDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("PointbaseDictionary", (DescriptorBean)original.getPointbaseDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 52);
                     } else if (prop.equals("PostgresDictionary")) {
                        if (type == 2) {
                           original.setPostgresDictionary((PostgresDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getPostgresDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("PostgresDictionary", (DescriptorBean)original.getPostgresDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 53);
                     } else if (prop.equals("ProfilingProxyManager")) {
                        if (type == 2) {
                           original.setProfilingProxyManager((ProfilingProxyManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getProfilingProxyManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ProfilingProxyManager", (DescriptorBean)original.getProfilingProxyManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 123);
                     } else if (prop.equals("ProxyManagerImpl")) {
                        if (type == 2) {
                           original.setProxyManagerImpl((ProxyManagerImplBean)this.createCopy((AbstractDescriptorBean)proposed.getProxyManagerImpl()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ProxyManagerImpl", (DescriptorBean)original.getProxyManagerImpl());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 124);
                     } else if (prop.equals("QueryCaches")) {
                        if (type == 2) {
                           original.setQueryCaches((QueryCachesBean)this.createCopy((AbstractDescriptorBean)proposed.getQueryCaches()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("QueryCaches", (DescriptorBean)original.getQueryCaches());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 126);
                     } else if (prop.equals("ReadLockLevel")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 131);
                     } else if (prop.equals("RestoreState")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 137);
                     } else if (prop.equals("ResultSetType")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 138);
                     } else if (prop.equals("RetainState")) {
                        original.setRetainState(proposed.getRetainState());
                        original._conditionalUnset(update.isUnsetUpdate(), 139);
                     } else if (prop.equals("RetryClassRegistration")) {
                        original.setRetryClassRegistration(proposed.getRetryClassRegistration());
                        original._conditionalUnset(update.isUnsetUpdate(), 140);
                     } else if (prop.equals("SQLServerDictionary")) {
                        if (type == 2) {
                           original.setSQLServerDictionary((SQLServerDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getSQLServerDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SQLServerDictionary", (DescriptorBean)original.getSQLServerDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 54);
                     } else if (prop.equals("Schema")) {
                        original.setSchema(proposed.getSchema());
                        original._conditionalUnset(update.isUnsetUpdate(), 146);
                     } else if (prop.equals("Schemata")) {
                        if (type == 2) {
                           original.setSchemata((SchemasBean)this.createCopy((AbstractDescriptorBean)proposed.getSchemata()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("Schemata", (DescriptorBean)original.getSchemata());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 153);
                     } else if (prop.equals("SimpleDriverDataSource")) {
                        if (type == 2) {
                           original.setSimpleDriverDataSource((SimpleDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)proposed.getSimpleDriverDataSource()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SimpleDriverDataSource", (DescriptorBean)original.getSimpleDriverDataSource());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 64);
                     } else if (prop.equals("SingleJVMExclusiveLockManager")) {
                        if (type == 2) {
                           original.setSingleJVMExclusiveLockManager((SingleJVMExclusiveLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getSingleJVMExclusiveLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SingleJVMExclusiveLockManager", (DescriptorBean)original.getSingleJVMExclusiveLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 79);
                     } else if (prop.equals("SingleJVMRemoteCommitProvider")) {
                        if (type == 2) {
                           original.setSingleJVMRemoteCommitProvider((SingleJVMRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getSingleJVMRemoteCommitProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SingleJVMRemoteCommitProvider", (DescriptorBean)original.getSingleJVMRemoteCommitProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 133);
                     } else if (prop.equals("StackExecutionContextNameProvider")) {
                        if (type == 2) {
                           original.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getStackExecutionContextNameProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("StackExecutionContextNameProvider", (DescriptorBean)original.getStackExecutionContextNameProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 174);
                     } else if (prop.equals("SubclassFetchMode")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 163);
                     } else if (prop.equals("SybaseDictionary")) {
                        if (type == 2) {
                           original.setSybaseDictionary((SybaseDictionaryBean)this.createCopy((AbstractDescriptorBean)proposed.getSybaseDictionary()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("SybaseDictionary", (DescriptorBean)original.getSybaseDictionary());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 55);
                     } else if (prop.equals("SynchronizeMappings")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 164);
                     } else if (prop.equals("TCPRemoteCommitProvider")) {
                        if (type == 2) {
                           original.setTCPRemoteCommitProvider((TCPRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getTCPRemoteCommitProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TCPRemoteCommitProvider", (DescriptorBean)original.getTCPRemoteCommitProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 134);
                     } else if (prop.equals("TCPTransport")) {
                        if (type == 2) {
                           original.setTCPTransport((TCPTransportBean)this.createCopy((AbstractDescriptorBean)proposed.getTCPTransport()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TCPTransport", (DescriptorBean)original.getTCPTransport());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 120);
                     } else if (prop.equals("TableDeprecatedJDOMappingFactory")) {
                        if (type == 2) {
                           original.setTableDeprecatedJDOMappingFactory((TableDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getTableDeprecatedJDOMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TableDeprecatedJDOMappingFactory", (DescriptorBean)original.getTableDeprecatedJDOMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 99);
                     } else if (prop.equals("TableJDBCSeq")) {
                        if (type == 2) {
                           original.setTableJDBCSeq((TableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getTableJDBCSeq()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TableJDBCSeq", (DescriptorBean)original.getTableJDBCSeq());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 156);
                     } else if (prop.equals("TableJDORMappingFactory")) {
                        if (type == 2) {
                           original.setTableJDORMappingFactory((TableJDORMappingFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getTableJDORMappingFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TableJDORMappingFactory", (DescriptorBean)original.getTableJDORMappingFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 100);
                     } else if (prop.equals("TableLockUpdateManager")) {
                        if (type == 2) {
                           original.setTableLockUpdateManager((TableLockUpdateManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getTableLockUpdateManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TableLockUpdateManager", (DescriptorBean)original.getTableLockUpdateManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 171);
                     } else if (prop.equals("TableSchemaFactory")) {
                        if (type == 2) {
                           original.setTableSchemaFactory((TableSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getTableSchemaFactory()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TableSchemaFactory", (DescriptorBean)original.getTableSchemaFactory());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 151);
                     } else if (prop.equals("TimeSeededSeq")) {
                        if (type == 2) {
                           original.setTimeSeededSeq((TimeSeededSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getTimeSeededSeq()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TimeSeededSeq", (DescriptorBean)original.getTimeSeededSeq());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 157);
                     } else if (prop.equals("TransactionIsolation")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 165);
                     } else if (prop.equals("TransactionMode")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 166);
                     } else if (prop.equals("TransactionNameExecutionContextNameProvider")) {
                        if (type == 2) {
                           original.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactionNameExecutionContextNameProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("TransactionNameExecutionContextNameProvider", (DescriptorBean)original.getTransactionNameExecutionContextNameProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 175);
                     } else if (prop.equals("UserObjectExecutionContextNameProvider")) {
                        if (type == 2) {
                           original.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getUserObjectExecutionContextNameProvider()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("UserObjectExecutionContextNameProvider", (DescriptorBean)original.getUserObjectExecutionContextNameProvider());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 176);
                     } else if (prop.equals("ValueTableJDBCSeq")) {
                        if (type == 2) {
                           original.setValueTableJDBCSeq((ValueTableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)proposed.getValueTableJDBCSeq()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("ValueTableJDBCSeq", (DescriptorBean)original.getValueTableJDBCSeq());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 158);
                     } else if (prop.equals("VersionLockManager")) {
                        if (type == 2) {
                           original.setVersionLockManager((VersionLockManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getVersionLockManager()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("VersionLockManager", (DescriptorBean)original.getVersionLockManager());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 80);
                     } else if (prop.equals("WLS81JMX")) {
                        if (type == 2) {
                           original.setWLS81JMX((WLS81JMXBean)this.createCopy((AbstractDescriptorBean)proposed.getWLS81JMX()));
                        } else {
                           if (type != 3) {
                              throw new AssertionError("Invalid type: " + type);
                           }

                           original._destroySingleton("WLS81JMX", (DescriptorBean)original.getWLS81JMX());
                        }

                        original._conditionalUnset(update.isUnsetUpdate(), 182);
                     } else if (prop.equals("WriteLockLevel")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 173);
                     } else {
                        super.applyPropertyUpdate(event, update);
                     }
                  }
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            PersistenceUnitConfigurationBeanImpl copy = (PersistenceUnitConfigurationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AbstractStoreBrokerFactory")) && this.bean.isAbstractStoreBrokerFactorySet() && !copy._isSet(5)) {
               Object o = this.bean.getAbstractStoreBrokerFactory();
               copy.setAbstractStoreBrokerFactory((AbstractStoreBrokerFactoryBean)null);
               copy.setAbstractStoreBrokerFactory(o == null ? null : (AbstractStoreBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AccessDictionary")) && this.bean.isAccessDictionarySet() && !copy._isSet(42)) {
               Object o = this.bean.getAccessDictionary();
               copy.setAccessDictionary((AccessDictionaryBean)null);
               copy.setAccessDictionary(o == null ? null : (AccessDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AggregateListeners")) && this.bean.isAggregateListenersSet() && !copy._isSet(1)) {
               Object o = this.bean.getAggregateListeners();
               copy.setAggregateListeners((AggregateListenersBean)null);
               copy.setAggregateListeners(o == null ? null : (AggregateListenersBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("AutoClear")) && this.bean.isAutoClearSet()) {
               copy.setAutoClear(this.bean.getAutoClear());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoDetaches")) && this.bean.isAutoDetachesSet() && !copy._isSet(3)) {
               Object o = this.bean.getAutoDetaches();
               copy.setAutoDetaches((AutoDetachBean)null);
               copy.setAutoDetaches(o == null ? null : (AutoDetachBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("BatchingOperationOrderUpdateManager")) && this.bean.isBatchingOperationOrderUpdateManagerSet() && !copy._isSet(169)) {
               Object o = this.bean.getBatchingOperationOrderUpdateManager();
               copy.setBatchingOperationOrderUpdateManager((BatchingOperationOrderUpdateManagerBean)null);
               copy.setBatchingOperationOrderUpdateManager(o == null ? null : (BatchingOperationOrderUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CacheMap")) && this.bean.isCacheMapSet() && !copy._isSet(128)) {
               Object o = this.bean.getCacheMap();
               copy.setCacheMap((CacheMapBean)null);
               copy.setCacheMap(o == null ? null : (CacheMapBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClassTableJDBCSeq")) && this.bean.isClassTableJDBCSeqSet() && !copy._isSet(154)) {
               Object o = this.bean.getClassTableJDBCSeq();
               copy.setClassTableJDBCSeq((ClassTableJDBCSeqBean)null);
               copy.setClassTableJDBCSeq(o == null ? null : (ClassTableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClientBrokerFactory")) && this.bean.isClientBrokerFactorySet() && !copy._isSet(6)) {
               Object o = this.bean.getClientBrokerFactory();
               copy.setClientBrokerFactory((ClientBrokerFactoryBean)null);
               copy.setClientBrokerFactory(o == null ? null : (ClientBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterRemoteCommitProvider")) && this.bean.isClusterRemoteCommitProviderSet() && !copy._isSet(135)) {
               Object o = this.bean.getClusterRemoteCommitProvider();
               copy.setClusterRemoteCommitProvider((ClusterRemoteCommitProviderBean)null);
               copy.setClusterRemoteCommitProvider(o == null ? null : (ClusterRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CommonsLogFactory")) && this.bean.isCommonsLogFactorySet() && !copy._isSet(83)) {
               Object o = this.bean.getCommonsLogFactory();
               copy.setCommonsLogFactory((CommonsLogFactoryBean)null);
               copy.setCommonsLogFactory(o == null ? null : (CommonsLogFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Compatibility")) && this.bean.isCompatibilitySet() && !copy._isSet(15)) {
               Object o = this.bean.getCompatibility();
               copy.setCompatibility((KodoCompatibilityBean)null);
               copy.setCompatibility(o == null ? null : (KodoCompatibilityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConcurrentHashMap")) && this.bean.isConcurrentHashMapSet() && !copy._isSet(129)) {
               Object o = this.bean.getConcurrentHashMap();
               copy.setConcurrentHashMap((ConcurrentHashMapBean)null);
               copy.setConcurrentHashMap(o == null ? null : (ConcurrentHashMapBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Connection2DriverName")) && this.bean.isConnection2DriverNameSet()) {
               copy.setConnection2DriverName(this.bean.getConnection2DriverName());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("Connection2PasswordEncrypted")) && this.bean.isConnection2PasswordEncryptedSet()) {
               o = this.bean.getConnection2PasswordEncrypted();
               copy.setConnection2PasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            PropertiesBean o;
            if ((excludeProps == null || !excludeProps.contains("Connection2Properties")) && this.bean.isConnection2PropertiesSet() && !copy._isSet(20)) {
               o = this.bean.getConnection2Properties();
               copy.setConnection2Properties((PropertiesBean)null);
               copy.setConnection2Properties(o == null ? null : (PropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Connection2URL")) && this.bean.isConnection2URLSet()) {
               copy.setConnection2URL(this.bean.getConnection2URL());
            }

            if ((excludeProps == null || !excludeProps.contains("Connection2UserName")) && this.bean.isConnection2UserNameSet()) {
               copy.setConnection2UserName(this.bean.getConnection2UserName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionDecorators")) && this.bean.isConnectionDecoratorsSet() && !copy._isSet(23)) {
               Object o = this.bean.getConnectionDecorators();
               copy.setConnectionDecorators((ConnectionDecoratorsBean)null);
               copy.setConnectionDecorators(o == null ? null : (ConnectionDecoratorsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionDriverName")) && this.bean.isConnectionDriverNameSet()) {
               copy.setConnectionDriverName(this.bean.getConnectionDriverName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactory2Name")) && this.bean.isConnectionFactory2NameSet()) {
               copy.setConnectionFactory2Name(this.bean.getConnectionFactory2Name());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactory2Properties")) && this.bean.isConnectionFactory2PropertiesSet() && !copy._isSet(26)) {
               o = this.bean.getConnectionFactory2Properties();
               copy.setConnectionFactory2Properties((PropertiesBean)null);
               copy.setConnectionFactory2Properties(o == null ? null : (PropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryMode")) && this.bean.isConnectionFactoryModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryName")) && this.bean.isConnectionFactoryNameSet()) {
               copy.setConnectionFactoryName(this.bean.getConnectionFactoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryProperties")) && this.bean.isConnectionFactoryPropertiesSet() && !copy._isSet(29)) {
               o = this.bean.getConnectionFactoryProperties();
               copy.setConnectionFactoryProperties((PropertiesBean)null);
               copy.setConnectionFactoryProperties(o == null ? null : (PropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionPasswordEncrypted")) && this.bean.isConnectionPasswordEncryptedSet()) {
               o = this.bean.getConnectionPasswordEncrypted();
               copy.setConnectionPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionProperties")) && this.bean.isConnectionPropertiesSet() && !copy._isSet(32)) {
               o = this.bean.getConnectionProperties();
               copy.setConnectionProperties((PropertiesBean)null);
               copy.setConnectionProperties(o == null ? null : (PropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionRetainMode")) && this.bean.isConnectionRetainModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionUserName")) && this.bean.isConnectionUserNameSet()) {
               copy.setConnectionUserName(this.bean.getConnectionUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConstraintUpdateManager")) && this.bean.isConstraintUpdateManagerSet() && !copy._isSet(168)) {
               Object o = this.bean.getConstraintUpdateManager();
               copy.setConstraintUpdateManager((ConstraintUpdateManagerBean)null);
               copy.setConstraintUpdateManager(o == null ? null : (ConstraintUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomBrokerFactory")) && this.bean.isCustomBrokerFactorySet() && !copy._isSet(8)) {
               Object o = this.bean.getCustomBrokerFactory();
               copy.setCustomBrokerFactory((CustomBrokerFactoryBean)null);
               copy.setCustomBrokerFactory(o == null ? null : (CustomBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomBrokerImpl")) && this.bean.isCustomBrokerImplSet() && !copy._isSet(11)) {
               Object o = this.bean.getCustomBrokerImpl();
               copy.setCustomBrokerImpl((CustomBrokerImplBean)null);
               copy.setCustomBrokerImpl(o == null ? null : (CustomBrokerImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomClassResolver")) && this.bean.isCustomClassResolverSet() && !copy._isSet(13)) {
               Object o = this.bean.getCustomClassResolver();
               copy.setCustomClassResolver((CustomClassResolverBean)null);
               copy.setCustomClassResolver(o == null ? null : (CustomClassResolverBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomCompatibility")) && this.bean.isCustomCompatibilitySet() && !copy._isSet(16)) {
               Object o = this.bean.getCustomCompatibility();
               copy.setCustomCompatibility((CustomCompatibilityBean)null);
               copy.setCustomCompatibility(o == null ? null : (CustomCompatibilityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomDataCacheManager")) && this.bean.isCustomDataCacheManagerSet() && !copy._isSet(40)) {
               Object o = this.bean.getCustomDataCacheManager();
               copy.setCustomDataCacheManager((CustomDataCacheManagerBean)null);
               copy.setCustomDataCacheManager(o == null ? null : (CustomDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomDetachState")) && this.bean.isCustomDetachStateSet() && !copy._isSet(61)) {
               Object o = this.bean.getCustomDetachState();
               copy.setCustomDetachState((CustomDetachStateBean)null);
               copy.setCustomDetachState(o == null ? null : (CustomDetachStateBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomDictionary")) && this.bean.isCustomDictionarySet() && !copy._isSet(56)) {
               Object o = this.bean.getCustomDictionary();
               copy.setCustomDictionary((CustomDictionaryBean)null);
               copy.setCustomDictionary(o == null ? null : (CustomDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomDriverDataSource")) && this.bean.isCustomDriverDataSourceSet() && !copy._isSet(65)) {
               Object o = this.bean.getCustomDriverDataSource();
               copy.setCustomDriverDataSource((CustomDriverDataSourceBean)null);
               copy.setCustomDriverDataSource(o == null ? null : (CustomDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomLockManager")) && this.bean.isCustomLockManagerSet() && !copy._isSet(81)) {
               Object o = this.bean.getCustomLockManager();
               copy.setCustomLockManager((CustomLockManagerBean)null);
               copy.setCustomLockManager(o == null ? null : (CustomLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomLog")) && this.bean.isCustomLogSet() && !copy._isSet(87)) {
               Object o = this.bean.getCustomLog();
               copy.setCustomLog((CustomLogBean)null);
               copy.setCustomLog(o == null ? null : (CustomLogBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomMappingDefaults")) && this.bean.isCustomMappingDefaultsSet() && !copy._isSet(94)) {
               Object o = this.bean.getCustomMappingDefaults();
               copy.setCustomMappingDefaults((CustomMappingDefaultsBean)null);
               copy.setCustomMappingDefaults(o == null ? null : (CustomMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomMappingFactory")) && this.bean.isCustomMappingFactorySet() && !copy._isSet(101)) {
               Object o = this.bean.getCustomMappingFactory();
               copy.setCustomMappingFactory((CustomMappingFactoryBean)null);
               copy.setCustomMappingFactory(o == null ? null : (CustomMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomMetaDataFactory")) && this.bean.isCustomMetaDataFactorySet() && !copy._isSet(106)) {
               Object o = this.bean.getCustomMetaDataFactory();
               copy.setCustomMetaDataFactory((CustomMetaDataFactoryBean)null);
               copy.setCustomMetaDataFactory(o == null ? null : (CustomMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomMetaDataRepository")) && this.bean.isCustomMetaDataRepositorySet() && !copy._isSet(109)) {
               Object o = this.bean.getCustomMetaDataRepository();
               copy.setCustomMetaDataRepository((CustomMetaDataRepositoryBean)null);
               copy.setCustomMetaDataRepository(o == null ? null : (CustomMetaDataRepositoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomOrphanedKeyAction")) && this.bean.isCustomOrphanedKeyActionSet() && !copy._isSet(118)) {
               Object o = this.bean.getCustomOrphanedKeyAction();
               copy.setCustomOrphanedKeyAction((CustomOrphanedKeyActionBean)null);
               copy.setCustomOrphanedKeyAction(o == null ? null : (CustomOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomPersistenceServer")) && this.bean.isCustomPersistenceServerSet() && !copy._isSet(121)) {
               Object o = this.bean.getCustomPersistenceServer();
               copy.setCustomPersistenceServer((CustomPersistenceServerBean)null);
               copy.setCustomPersistenceServer(o == null ? null : (CustomPersistenceServerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomProxyManager")) && this.bean.isCustomProxyManagerSet() && !copy._isSet(125)) {
               Object o = this.bean.getCustomProxyManager();
               copy.setCustomProxyManager((CustomProxyManagerBean)null);
               copy.setCustomProxyManager(o == null ? null : (CustomProxyManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomQueryCompilationCache")) && this.bean.isCustomQueryCompilationCacheSet() && !copy._isSet(130)) {
               Object o = this.bean.getCustomQueryCompilationCache();
               copy.setCustomQueryCompilationCache((CustomQueryCompilationCacheBean)null);
               copy.setCustomQueryCompilationCache(o == null ? null : (CustomQueryCompilationCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomRemoteCommitProvider")) && this.bean.isCustomRemoteCommitProviderSet() && !copy._isSet(136)) {
               Object o = this.bean.getCustomRemoteCommitProvider();
               copy.setCustomRemoteCommitProvider((CustomRemoteCommitProviderBean)null);
               copy.setCustomRemoteCommitProvider(o == null ? null : (CustomRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomSQLFactory")) && this.bean.isCustomSQLFactorySet() && !copy._isSet(162)) {
               Object o = this.bean.getCustomSQLFactory();
               copy.setCustomSQLFactory((CustomSQLFactoryBean)null);
               copy.setCustomSQLFactory(o == null ? null : (CustomSQLFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomSavepointManager")) && this.bean.isCustomSavepointManagerSet() && !copy._isSet(145)) {
               Object o = this.bean.getCustomSavepointManager();
               copy.setCustomSavepointManager((CustomSavepointManagerBean)null);
               copy.setCustomSavepointManager(o == null ? null : (CustomSavepointManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomSchemaFactory")) && this.bean.isCustomSchemaFactorySet() && !copy._isSet(152)) {
               Object o = this.bean.getCustomSchemaFactory();
               copy.setCustomSchemaFactory((CustomSchemaFactoryBean)null);
               copy.setCustomSchemaFactory(o == null ? null : (CustomSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomSeq")) && this.bean.isCustomSeqSet() && !copy._isSet(159)) {
               Object o = this.bean.getCustomSeq();
               copy.setCustomSeq((CustomSeqBean)null);
               copy.setCustomSeq(o == null ? null : (CustomSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomUpdateManager")) && this.bean.isCustomUpdateManagerSet() && !copy._isSet(172)) {
               Object o = this.bean.getCustomUpdateManager();
               copy.setCustomUpdateManager((CustomUpdateManagerBean)null);
               copy.setCustomUpdateManager(o == null ? null : (CustomUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DB2Dictionary")) && this.bean.isDB2DictionarySet() && !copy._isSet(43)) {
               Object o = this.bean.getDB2Dictionary();
               copy.setDB2Dictionary((DB2DictionaryBean)null);
               copy.setDB2Dictionary(o == null ? null : (DB2DictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DataCacheManagerImpl")) && this.bean.isDataCacheManagerImplSet() && !copy._isSet(39)) {
               Object o = this.bean.getDataCacheManagerImpl();
               copy.setDataCacheManagerImpl((DataCacheManagerImplBean)null);
               copy.setDataCacheManagerImpl(o == null ? null : (DataCacheManagerImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DataCacheTimeout")) && this.bean.isDataCacheTimeoutSet()) {
               copy.setDataCacheTimeout(this.bean.getDataCacheTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DataCaches")) && this.bean.isDataCachesSet() && !copy._isSet(36)) {
               Object o = this.bean.getDataCaches();
               copy.setDataCaches((DataCachesBean)null);
               copy.setDataCaches(o == null ? null : (DataCachesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultBrokerFactory")) && this.bean.isDefaultBrokerFactorySet() && !copy._isSet(4)) {
               Object o = this.bean.getDefaultBrokerFactory();
               copy.setDefaultBrokerFactory((DefaultBrokerFactoryBean)null);
               copy.setDefaultBrokerFactory(o == null ? null : (DefaultBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultBrokerImpl")) && this.bean.isDefaultBrokerImplSet() && !copy._isSet(9)) {
               Object o = this.bean.getDefaultBrokerImpl();
               copy.setDefaultBrokerImpl((DefaultBrokerImplBean)null);
               copy.setDefaultBrokerImpl(o == null ? null : (DefaultBrokerImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultClassResolver")) && this.bean.isDefaultClassResolverSet() && !copy._isSet(12)) {
               Object o = this.bean.getDefaultClassResolver();
               copy.setDefaultClassResolver((DefaultClassResolverBean)null);
               copy.setDefaultClassResolver(o == null ? null : (DefaultClassResolverBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultCompatibility")) && this.bean.isDefaultCompatibilitySet() && !copy._isSet(14)) {
               Object o = this.bean.getDefaultCompatibility();
               copy.setDefaultCompatibility((DefaultCompatibilityBean)null);
               copy.setDefaultCompatibility(o == null ? null : (DefaultCompatibilityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDataCacheManager")) && this.bean.isDefaultDataCacheManagerSet() && !copy._isSet(37)) {
               Object o = this.bean.getDefaultDataCacheManager();
               copy.setDefaultDataCacheManager((DefaultDataCacheManagerBean)null);
               copy.setDefaultDataCacheManager(o == null ? null : (DefaultDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDetachState")) && this.bean.isDefaultDetachStateSet() && !copy._isSet(57)) {
               Object o = this.bean.getDefaultDetachState();
               copy.setDefaultDetachState((DefaultDetachStateBean)null);
               copy.setDefaultDetachState(o == null ? null : (DefaultDetachStateBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDriverDataSource")) && this.bean.isDefaultDriverDataSourceSet() && !copy._isSet(62)) {
               Object o = this.bean.getDefaultDriverDataSource();
               copy.setDefaultDriverDataSource((DefaultDriverDataSourceBean)null);
               copy.setDefaultDriverDataSource(o == null ? null : (DefaultDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultLockManager")) && this.bean.isDefaultLockManagerSet() && !copy._isSet(76)) {
               Object o = this.bean.getDefaultLockManager();
               copy.setDefaultLockManager((DefaultLockManagerBean)null);
               copy.setDefaultLockManager(o == null ? null : (DefaultLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMappingDefaults")) && this.bean.isDefaultMappingDefaultsSet() && !copy._isSet(90)) {
               Object o = this.bean.getDefaultMappingDefaults();
               copy.setDefaultMappingDefaults((DefaultMappingDefaultsBean)null);
               copy.setDefaultMappingDefaults(o == null ? null : (DefaultMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMetaDataFactory")) && this.bean.isDefaultMetaDataFactorySet() && !copy._isSet(102)) {
               Object o = this.bean.getDefaultMetaDataFactory();
               copy.setDefaultMetaDataFactory((DefaultMetaDataFactoryBean)null);
               copy.setDefaultMetaDataFactory(o == null ? null : (DefaultMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMetaDataRepository")) && this.bean.isDefaultMetaDataRepositorySet() && !copy._isSet(107)) {
               Object o = this.bean.getDefaultMetaDataRepository();
               copy.setDefaultMetaDataRepository((DefaultMetaDataRepositoryBean)null);
               copy.setDefaultMetaDataRepository(o == null ? null : (DefaultMetaDataRepositoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultOrphanedKeyAction")) && this.bean.isDefaultOrphanedKeyActionSet() && !copy._isSet(114)) {
               Object o = this.bean.getDefaultOrphanedKeyAction();
               copy.setDefaultOrphanedKeyAction((DefaultOrphanedKeyActionBean)null);
               copy.setDefaultOrphanedKeyAction(o == null ? null : (DefaultOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultProxyManager")) && this.bean.isDefaultProxyManagerSet() && !copy._isSet(122)) {
               Object o = this.bean.getDefaultProxyManager();
               copy.setDefaultProxyManager((DefaultProxyManagerBean)null);
               copy.setDefaultProxyManager(o == null ? null : (DefaultProxyManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultQueryCompilationCache")) && this.bean.isDefaultQueryCompilationCacheSet() && !copy._isSet(127)) {
               Object o = this.bean.getDefaultQueryCompilationCache();
               copy.setDefaultQueryCompilationCache((DefaultQueryCompilationCacheBean)null);
               copy.setDefaultQueryCompilationCache(o == null ? null : (DefaultQueryCompilationCacheBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultSQLFactory")) && this.bean.isDefaultSQLFactorySet() && !copy._isSet(160)) {
               Object o = this.bean.getDefaultSQLFactory();
               copy.setDefaultSQLFactory((DefaultSQLFactoryBean)null);
               copy.setDefaultSQLFactory(o == null ? null : (DefaultSQLFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultSavepointManager")) && this.bean.isDefaultSavepointManagerSet() && !copy._isSet(141)) {
               Object o = this.bean.getDefaultSavepointManager();
               copy.setDefaultSavepointManager((DefaultSavepointManagerBean)null);
               copy.setDefaultSavepointManager(o == null ? null : (DefaultSavepointManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultSchemaFactory")) && this.bean.isDefaultSchemaFactorySet() && !copy._isSet(147)) {
               Object o = this.bean.getDefaultSchemaFactory();
               copy.setDefaultSchemaFactory((DefaultSchemaFactoryBean)null);
               copy.setDefaultSchemaFactory(o == null ? null : (DefaultSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultUpdateManager")) && this.bean.isDefaultUpdateManagerSet() && !copy._isSet(167)) {
               Object o = this.bean.getDefaultUpdateManager();
               copy.setDefaultUpdateManager((DefaultUpdateManagerBean)null);
               copy.setDefaultUpdateManager(o == null ? null : (DefaultUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeprecatedJDOMappingDefaults")) && this.bean.isDeprecatedJDOMappingDefaultsSet() && !copy._isSet(91)) {
               Object o = this.bean.getDeprecatedJDOMappingDefaults();
               copy.setDeprecatedJDOMappingDefaults((DeprecatedJDOMappingDefaultsBean)null);
               copy.setDeprecatedJDOMappingDefaults(o == null ? null : (DeprecatedJDOMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeprecatedJDOMetaDataFactory")) && this.bean.isDeprecatedJDOMetaDataFactorySet() && !copy._isSet(104)) {
               Object o = this.bean.getDeprecatedJDOMetaDataFactory();
               copy.setDeprecatedJDOMetaDataFactory((DeprecatedJDOMetaDataFactoryBean)null);
               copy.setDeprecatedJDOMetaDataFactory(o == null ? null : (DeprecatedJDOMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DerbyDictionary")) && this.bean.isDerbyDictionarySet() && !copy._isSet(44)) {
               Object o = this.bean.getDerbyDictionary();
               copy.setDerbyDictionary((DerbyDictionaryBean)null);
               copy.setDerbyDictionary(o == null ? null : (DerbyDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DetachOptionsAll")) && this.bean.isDetachOptionsAllSet() && !copy._isSet(60)) {
               Object o = this.bean.getDetachOptionsAll();
               copy.setDetachOptionsAll((DetachOptionsAllBean)null);
               copy.setDetachOptionsAll(o == null ? null : (DetachOptionsAllBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DetachOptionsFetchGroups")) && this.bean.isDetachOptionsFetchGroupsSet() && !copy._isSet(59)) {
               Object o = this.bean.getDetachOptionsFetchGroups();
               copy.setDetachOptionsFetchGroups((DetachOptionsFetchGroupsBean)null);
               copy.setDetachOptionsFetchGroups(o == null ? null : (DetachOptionsFetchGroupsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DetachOptionsLoaded")) && this.bean.isDetachOptionsLoadedSet() && !copy._isSet(58)) {
               Object o = this.bean.getDetachOptionsLoaded();
               copy.setDetachOptionsLoaded((DetachOptionsLoadedBean)null);
               copy.setDetachOptionsLoaded(o == null ? null : (DetachOptionsLoadedBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DynamicDataStructs")) && this.bean.isDynamicDataStructsSet()) {
               copy.setDynamicDataStructs(this.bean.getDynamicDataStructs());
            }

            if ((excludeProps == null || !excludeProps.contains("DynamicSchemaFactory")) && this.bean.isDynamicSchemaFactorySet() && !copy._isSet(148)) {
               Object o = this.bean.getDynamicSchemaFactory();
               copy.setDynamicSchemaFactory((DynamicSchemaFactoryBean)null);
               copy.setDynamicSchemaFactory(o == null ? null : (DynamicSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EagerFetchMode")) && this.bean.isEagerFetchModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("EmpressDictionary")) && this.bean.isEmpressDictionarySet() && !copy._isSet(45)) {
               Object o = this.bean.getEmpressDictionary();
               copy.setEmpressDictionary((EmpressDictionaryBean)null);
               copy.setEmpressDictionary(o == null ? null : (EmpressDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ExceptionOrphanedKeyAction")) && this.bean.isExceptionOrphanedKeyActionSet() && !copy._isSet(116)) {
               Object o = this.bean.getExceptionOrphanedKeyAction();
               copy.setExceptionOrphanedKeyAction((ExceptionOrphanedKeyActionBean)null);
               copy.setExceptionOrphanedKeyAction(o == null ? null : (ExceptionOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ExportProfiling")) && this.bean.isExportProfilingSet() && !copy._isSet(185)) {
               Object o = this.bean.getExportProfiling();
               copy.setExportProfiling((ExportProfilingBean)null);
               copy.setExportProfiling(o == null ? null : (ExportProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ExtensionDeprecatedJDOMappingFactory")) && this.bean.isExtensionDeprecatedJDOMappingFactorySet() && !copy._isSet(95)) {
               Object o = this.bean.getExtensionDeprecatedJDOMappingFactory();
               copy.setExtensionDeprecatedJDOMappingFactory((ExtensionDeprecatedJDOMappingFactoryBean)null);
               copy.setExtensionDeprecatedJDOMappingFactory(o == null ? null : (ExtensionDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FetchBatchSize")) && this.bean.isFetchBatchSizeSet()) {
               copy.setFetchBatchSize(this.bean.getFetchBatchSize());
            }

            if ((excludeProps == null || !excludeProps.contains("FetchDirection")) && this.bean.isFetchDirectionSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("FetchGroups")) && this.bean.isFetchGroupsSet() && !copy._isSet(70)) {
               Object o = this.bean.getFetchGroups();
               copy.setFetchGroups((FetchGroupsBean)null);
               copy.setFetchGroups(o == null ? null : (FetchGroupsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FileSchemaFactory")) && this.bean.isFileSchemaFactorySet() && !copy._isSet(149)) {
               Object o = this.bean.getFileSchemaFactory();
               copy.setFileSchemaFactory((FileSchemaFactoryBean)null);
               copy.setFileSchemaFactory(o == null ? null : (FileSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FilterListeners")) && this.bean.isFilterListenersSet() && !copy._isSet(71)) {
               Object o = this.bean.getFilterListeners();
               copy.setFilterListeners((FilterListenersBean)null);
               copy.setFilterListeners(o == null ? null : (FilterListenersBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FlushBeforeQueries")) && this.bean.isFlushBeforeQueriesSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("FoxProDictionary")) && this.bean.isFoxProDictionarySet() && !copy._isSet(46)) {
               Object o = this.bean.getFoxProDictionary();
               copy.setFoxProDictionary((FoxProDictionaryBean)null);
               copy.setFoxProDictionary(o == null ? null : (FoxProDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GUIJMX")) && this.bean.isGUIJMXSet() && !copy._isSet(179)) {
               Object o = this.bean.getGUIJMX();
               copy.setGUIJMX((GUIJMXBean)null);
               copy.setGUIJMX(o == null ? null : (GUIJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GUIProfiling")) && this.bean.isGUIProfilingSet() && !copy._isSet(186)) {
               Object o = this.bean.getGUIProfiling();
               copy.setGUIProfiling((GUIProfilingBean)null);
               copy.setGUIProfiling(o == null ? null : (GUIProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("HSQLDictionary")) && this.bean.isHSQLDictionarySet() && !copy._isSet(47)) {
               Object o = this.bean.getHSQLDictionary();
               copy.setHSQLDictionary((HSQLDictionaryBean)null);
               copy.setHSQLDictionary(o == null ? null : (HSQLDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("HTTPTransport")) && this.bean.isHTTPTransportSet() && !copy._isSet(119)) {
               Object o = this.bean.getHTTPTransport();
               copy.setHTTPTransport((HTTPTransportBean)null);
               copy.setHTTPTransport(o == null ? null : (HTTPTransportBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreChanges")) && this.bean.isIgnoreChangesSet()) {
               copy.setIgnoreChanges(this.bean.getIgnoreChanges());
            }

            if ((excludeProps == null || !excludeProps.contains("InMemorySavepointManager")) && this.bean.isInMemorySavepointManagerSet() && !copy._isSet(142)) {
               Object o = this.bean.getInMemorySavepointManager();
               copy.setInMemorySavepointManager((InMemorySavepointManagerBean)null);
               copy.setInMemorySavepointManager(o == null ? null : (InMemorySavepointManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("InformixDictionary")) && this.bean.isInformixDictionarySet() && !copy._isSet(48)) {
               Object o = this.bean.getInformixDictionary();
               copy.setInformixDictionary((InformixDictionaryBean)null);
               copy.setInformixDictionary(o == null ? null : (InformixDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("InverseManager")) && this.bean.isInverseManagerSet() && !copy._isSet(74)) {
               Object o = this.bean.getInverseManager();
               copy.setInverseManager((InverseManagerBean)null);
               copy.setInverseManager(o == null ? null : (InverseManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBC3SavepointManager")) && this.bean.isJDBC3SavepointManagerSet() && !copy._isSet(143)) {
               Object o = this.bean.getJDBC3SavepointManager();
               copy.setJDBC3SavepointManager((JDBC3SavepointManagerBean)null);
               copy.setJDBC3SavepointManager(o == null ? null : (JDBC3SavepointManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCBrokerFactory")) && this.bean.isJDBCBrokerFactorySet() && !copy._isSet(7)) {
               Object o = this.bean.getJDBCBrokerFactory();
               copy.setJDBCBrokerFactory((JDBCBrokerFactoryBean)null);
               copy.setJDBCBrokerFactory(o == null ? null : (JDBCBrokerFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCListeners")) && this.bean.isJDBCListenersSet() && !copy._isSet(75)) {
               Object o = this.bean.getJDBCListeners();
               copy.setJDBCListeners((JDBCListenersBean)null);
               copy.setJDBCListeners(o == null ? null : (JDBCListenersBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDOMetaDataFactory")) && this.bean.isJDOMetaDataFactorySet() && !copy._isSet(103)) {
               Object o = this.bean.getJDOMetaDataFactory();
               copy.setJDOMetaDataFactory((JDOMetaDataFactoryBean)null);
               copy.setJDOMetaDataFactory(o == null ? null : (JDOMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDataStoreDictionary")) && this.bean.isJDataStoreDictionarySet() && !copy._isSet(49)) {
               Object o = this.bean.getJDataStoreDictionary();
               copy.setJDataStoreDictionary((JDataStoreDictionaryBean)null);
               copy.setJDataStoreDictionary(o == null ? null : (JDataStoreDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JMSRemoteCommitProvider")) && this.bean.isJMSRemoteCommitProviderSet() && !copy._isSet(132)) {
               Object o = this.bean.getJMSRemoteCommitProvider();
               copy.setJMSRemoteCommitProvider((JMSRemoteCommitProviderBean)null);
               copy.setJMSRemoteCommitProvider(o == null ? null : (JMSRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JMX2JMX")) && this.bean.isJMX2JMXSet() && !copy._isSet(180)) {
               Object o = this.bean.getJMX2JMX();
               copy.setJMX2JMX((JMX2JMXBean)null);
               copy.setJMX2JMX(o == null ? null : (JMX2JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoBroker")) && this.bean.isKodoBrokerSet() && !copy._isSet(10)) {
               Object o = this.bean.getKodoBroker();
               copy.setKodoBroker((KodoBrokerBean)null);
               copy.setKodoBroker(o == null ? null : (KodoBrokerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoDataCacheManager")) && this.bean.isKodoDataCacheManagerSet() && !copy._isSet(38)) {
               Object o = this.bean.getKodoDataCacheManager();
               copy.setKodoDataCacheManager((KodoDataCacheManagerBean)null);
               copy.setKodoDataCacheManager(o == null ? null : (KodoDataCacheManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoMappingRepository")) && this.bean.isKodoMappingRepositorySet() && !copy._isSet(108)) {
               Object o = this.bean.getKodoMappingRepository();
               copy.setKodoMappingRepository((KodoMappingRepositoryBean)null);
               copy.setKodoMappingRepository(o == null ? null : (KodoMappingRepositoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoPersistenceMappingFactory")) && this.bean.isKodoPersistenceMappingFactorySet() && !copy._isSet(96)) {
               Object o = this.bean.getKodoPersistenceMappingFactory();
               copy.setKodoPersistenceMappingFactory((KodoPersistenceMappingFactoryBean)null);
               copy.setKodoPersistenceMappingFactory(o == null ? null : (KodoPersistenceMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoPersistenceMetaDataFactory")) && this.bean.isKodoPersistenceMetaDataFactorySet() && !copy._isSet(105)) {
               Object o = this.bean.getKodoPersistenceMetaDataFactory();
               copy.setKodoPersistenceMetaDataFactory((KodoPersistenceMetaDataFactoryBean)null);
               copy.setKodoPersistenceMetaDataFactory(o == null ? null : (KodoPersistenceMetaDataFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoPoolingDataSource")) && this.bean.isKodoPoolingDataSourceSet() && !copy._isSet(63)) {
               Object o = this.bean.getKodoPoolingDataSource();
               copy.setKodoPoolingDataSource((KodoPoolingDataSourceBean)null);
               copy.setKodoPoolingDataSource(o == null ? null : (KodoPoolingDataSourceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("KodoSQLFactory")) && this.bean.isKodoSQLFactorySet() && !copy._isSet(161)) {
               Object o = this.bean.getKodoSQLFactory();
               copy.setKodoSQLFactory((KodoSQLFactoryBean)null);
               copy.setKodoSQLFactory(o == null ? null : (KodoSQLFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LRSSize")) && this.bean.isLRSSizeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("LazySchemaFactory")) && this.bean.isLazySchemaFactorySet() && !copy._isSet(150)) {
               Object o = this.bean.getLazySchemaFactory();
               copy.setLazySchemaFactory((LazySchemaFactoryBean)null);
               copy.setLazySchemaFactory(o == null ? null : (LazySchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalJMX")) && this.bean.isLocalJMXSet() && !copy._isSet(178)) {
               Object o = this.bean.getLocalJMX();
               copy.setLocalJMX((LocalJMXBean)null);
               copy.setLocalJMX(o == null ? null : (LocalJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalProfiling")) && this.bean.isLocalProfilingSet() && !copy._isSet(184)) {
               Object o = this.bean.getLocalProfiling();
               copy.setLocalProfiling((LocalProfilingBean)null);
               copy.setLocalProfiling(o == null ? null : (LocalProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LockTimeout")) && this.bean.isLockTimeoutSet()) {
               copy.setLockTimeout(this.bean.getLockTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("Log4JLogFactory")) && this.bean.isLog4JLogFactorySet() && !copy._isSet(84)) {
               Object o = this.bean.getLog4JLogFactory();
               copy.setLog4JLogFactory((Log4JLogFactoryBean)null);
               copy.setLog4JLogFactory(o == null ? null : (Log4JLogFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LogFactoryImpl")) && this.bean.isLogFactoryImplSet() && !copy._isSet(85)) {
               Object o = this.bean.getLogFactoryImpl();
               copy.setLogFactoryImpl((LogFactoryImplBean)null);
               copy.setLogFactoryImpl(o == null ? null : (LogFactoryImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LogOrphanedKeyAction")) && this.bean.isLogOrphanedKeyActionSet() && !copy._isSet(115)) {
               Object o = this.bean.getLogOrphanedKeyAction();
               copy.setLogOrphanedKeyAction((LogOrphanedKeyActionBean)null);
               copy.setLogOrphanedKeyAction(o == null ? null : (LogOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MX4J1JMX")) && this.bean.isMX4J1JMXSet() && !copy._isSet(181)) {
               Object o = this.bean.getMX4J1JMX();
               copy.setMX4J1JMX((MX4J1JMXBean)null);
               copy.setMX4J1JMX(o == null ? null : (MX4J1JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Mapping")) && this.bean.isMappingSet()) {
               copy.setMapping(this.bean.getMapping());
            }

            if ((excludeProps == null || !excludeProps.contains("MappingDefaultsImpl")) && this.bean.isMappingDefaultsImplSet() && !copy._isSet(92)) {
               Object o = this.bean.getMappingDefaultsImpl();
               copy.setMappingDefaultsImpl((MappingDefaultsImplBean)null);
               copy.setMappingDefaultsImpl(o == null ? null : (MappingDefaultsImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MappingFileDeprecatedJDOMappingFactory")) && this.bean.isMappingFileDeprecatedJDOMappingFactorySet() && !copy._isSet(97)) {
               Object o = this.bean.getMappingFileDeprecatedJDOMappingFactory();
               copy.setMappingFileDeprecatedJDOMappingFactory((MappingFileDeprecatedJDOMappingFactoryBean)null);
               copy.setMappingFileDeprecatedJDOMappingFactory(o == null ? null : (MappingFileDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Multithreaded")) && this.bean.isMultithreadedSet()) {
               copy.setMultithreaded(this.bean.getMultithreaded());
            }

            if ((excludeProps == null || !excludeProps.contains("MySQLDictionary")) && this.bean.isMySQLDictionarySet() && !copy._isSet(50)) {
               Object o = this.bean.getMySQLDictionary();
               copy.setMySQLDictionary((MySQLDictionaryBean)null);
               copy.setMySQLDictionary(o == null ? null : (MySQLDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NativeJDBCSeq")) && this.bean.isNativeJDBCSeqSet() && !copy._isSet(155)) {
               Object o = this.bean.getNativeJDBCSeq();
               copy.setNativeJDBCSeq((NativeJDBCSeqBean)null);
               copy.setNativeJDBCSeq(o == null ? null : (NativeJDBCSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneJMX")) && this.bean.isNoneJMXSet() && !copy._isSet(177)) {
               Object o = this.bean.getNoneJMX();
               copy.setNoneJMX((NoneJMXBean)null);
               copy.setNoneJMX(o == null ? null : (NoneJMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneLockManager")) && this.bean.isNoneLockManagerSet() && !copy._isSet(78)) {
               Object o = this.bean.getNoneLockManager();
               copy.setNoneLockManager((NoneLockManagerBean)null);
               copy.setNoneLockManager(o == null ? null : (NoneLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneLogFactory")) && this.bean.isNoneLogFactorySet() && !copy._isSet(86)) {
               Object o = this.bean.getNoneLogFactory();
               copy.setNoneLogFactory((NoneLogFactoryBean)null);
               copy.setNoneLogFactory(o == null ? null : (NoneLogFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneOrphanedKeyAction")) && this.bean.isNoneOrphanedKeyActionSet() && !copy._isSet(117)) {
               Object o = this.bean.getNoneOrphanedKeyAction();
               copy.setNoneOrphanedKeyAction((NoneOrphanedKeyActionBean)null);
               copy.setNoneOrphanedKeyAction(o == null ? null : (NoneOrphanedKeyActionBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneProfiling")) && this.bean.isNoneProfilingSet() && !copy._isSet(183)) {
               Object o = this.bean.getNoneProfiling();
               copy.setNoneProfiling((NoneProfilingBean)null);
               copy.setNoneProfiling(o == null ? null : (NoneProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NontransactionalRead")) && this.bean.isNontransactionalReadSet()) {
               copy.setNontransactionalRead(this.bean.getNontransactionalRead());
            }

            if ((excludeProps == null || !excludeProps.contains("NontransactionalWrite")) && this.bean.isNontransactionalWriteSet()) {
               copy.setNontransactionalWrite(this.bean.getNontransactionalWrite());
            }

            if ((excludeProps == null || !excludeProps.contains("ORMFileJDORMappingFactory")) && this.bean.isORMFileJDORMappingFactorySet() && !copy._isSet(98)) {
               Object o = this.bean.getORMFileJDORMappingFactory();
               copy.setORMFileJDORMappingFactory((ORMFileJDORMappingFactoryBean)null);
               copy.setORMFileJDORMappingFactory(o == null ? null : (ORMFileJDORMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("OperationOrderUpdateManager")) && this.bean.isOperationOrderUpdateManagerSet() && !copy._isSet(170)) {
               Object o = this.bean.getOperationOrderUpdateManager();
               copy.setOperationOrderUpdateManager((OperationOrderUpdateManagerBean)null);
               copy.setOperationOrderUpdateManager(o == null ? null : (OperationOrderUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Optimistic")) && this.bean.isOptimisticSet()) {
               copy.setOptimistic(this.bean.getOptimistic());
            }

            if ((excludeProps == null || !excludeProps.contains("OracleDictionary")) && this.bean.isOracleDictionarySet() && !copy._isSet(51)) {
               Object o = this.bean.getOracleDictionary();
               copy.setOracleDictionary((OracleDictionaryBean)null);
               copy.setOracleDictionary(o == null ? null : (OracleDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("OracleSavepointManager")) && this.bean.isOracleSavepointManagerSet() && !copy._isSet(144)) {
               Object o = this.bean.getOracleSavepointManager();
               copy.setOracleSavepointManager((OracleSavepointManagerBean)null);
               copy.setOracleSavepointManager(o == null ? null : (OracleSavepointManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceMappingDefaults")) && this.bean.isPersistenceMappingDefaultsSet() && !copy._isSet(93)) {
               Object o = this.bean.getPersistenceMappingDefaults();
               copy.setPersistenceMappingDefaults((PersistenceMappingDefaultsBean)null);
               copy.setPersistenceMappingDefaults(o == null ? null : (PersistenceMappingDefaultsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PessimisticLockManager")) && this.bean.isPessimisticLockManagerSet() && !copy._isSet(77)) {
               Object o = this.bean.getPessimisticLockManager();
               copy.setPessimisticLockManager((PessimisticLockManagerBean)null);
               copy.setPessimisticLockManager(o == null ? null : (PessimisticLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PointbaseDictionary")) && this.bean.isPointbaseDictionarySet() && !copy._isSet(52)) {
               Object o = this.bean.getPointbaseDictionary();
               copy.setPointbaseDictionary((PointbaseDictionaryBean)null);
               copy.setPointbaseDictionary(o == null ? null : (PointbaseDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PostgresDictionary")) && this.bean.isPostgresDictionarySet() && !copy._isSet(53)) {
               Object o = this.bean.getPostgresDictionary();
               copy.setPostgresDictionary((PostgresDictionaryBean)null);
               copy.setPostgresDictionary(o == null ? null : (PostgresDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ProfilingProxyManager")) && this.bean.isProfilingProxyManagerSet() && !copy._isSet(123)) {
               Object o = this.bean.getProfilingProxyManager();
               copy.setProfilingProxyManager((ProfilingProxyManagerBean)null);
               copy.setProfilingProxyManager(o == null ? null : (ProfilingProxyManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ProxyManagerImpl")) && this.bean.isProxyManagerImplSet() && !copy._isSet(124)) {
               Object o = this.bean.getProxyManagerImpl();
               copy.setProxyManagerImpl((ProxyManagerImplBean)null);
               copy.setProxyManagerImpl(o == null ? null : (ProxyManagerImplBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("QueryCaches")) && this.bean.isQueryCachesSet() && !copy._isSet(126)) {
               Object o = this.bean.getQueryCaches();
               copy.setQueryCaches((QueryCachesBean)null);
               copy.setQueryCaches(o == null ? null : (QueryCachesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ReadLockLevel")) && this.bean.isReadLockLevelSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("RestoreState")) && this.bean.isRestoreStateSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ResultSetType")) && this.bean.isResultSetTypeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("RetainState")) && this.bean.isRetainStateSet()) {
               copy.setRetainState(this.bean.getRetainState());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryClassRegistration")) && this.bean.isRetryClassRegistrationSet()) {
               copy.setRetryClassRegistration(this.bean.getRetryClassRegistration());
            }

            if ((excludeProps == null || !excludeProps.contains("SQLServerDictionary")) && this.bean.isSQLServerDictionarySet() && !copy._isSet(54)) {
               Object o = this.bean.getSQLServerDictionary();
               copy.setSQLServerDictionary((SQLServerDictionaryBean)null);
               copy.setSQLServerDictionary(o == null ? null : (SQLServerDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Schema")) && this.bean.isSchemaSet()) {
               copy.setSchema(this.bean.getSchema());
            }

            if ((excludeProps == null || !excludeProps.contains("Schemata")) && this.bean.isSchemataSet() && !copy._isSet(153)) {
               Object o = this.bean.getSchemata();
               copy.setSchemata((SchemasBean)null);
               copy.setSchemata(o == null ? null : (SchemasBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SimpleDriverDataSource")) && this.bean.isSimpleDriverDataSourceSet() && !copy._isSet(64)) {
               Object o = this.bean.getSimpleDriverDataSource();
               copy.setSimpleDriverDataSource((SimpleDriverDataSourceBean)null);
               copy.setSimpleDriverDataSource(o == null ? null : (SimpleDriverDataSourceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SingleJVMExclusiveLockManager")) && this.bean.isSingleJVMExclusiveLockManagerSet() && !copy._isSet(79)) {
               Object o = this.bean.getSingleJVMExclusiveLockManager();
               copy.setSingleJVMExclusiveLockManager((SingleJVMExclusiveLockManagerBean)null);
               copy.setSingleJVMExclusiveLockManager(o == null ? null : (SingleJVMExclusiveLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SingleJVMRemoteCommitProvider")) && this.bean.isSingleJVMRemoteCommitProviderSet() && !copy._isSet(133)) {
               Object o = this.bean.getSingleJVMRemoteCommitProvider();
               copy.setSingleJVMRemoteCommitProvider((SingleJVMRemoteCommitProviderBean)null);
               copy.setSingleJVMRemoteCommitProvider(o == null ? null : (SingleJVMRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StackExecutionContextNameProvider")) && this.bean.isStackExecutionContextNameProviderSet() && !copy._isSet(174)) {
               Object o = this.bean.getStackExecutionContextNameProvider();
               copy.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)null);
               copy.setStackExecutionContextNameProvider(o == null ? null : (StackExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SubclassFetchMode")) && this.bean.isSubclassFetchModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("SybaseDictionary")) && this.bean.isSybaseDictionarySet() && !copy._isSet(55)) {
               Object o = this.bean.getSybaseDictionary();
               copy.setSybaseDictionary((SybaseDictionaryBean)null);
               copy.setSybaseDictionary(o == null ? null : (SybaseDictionaryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronizeMappings")) && this.bean.isSynchronizeMappingsSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("TCPRemoteCommitProvider")) && this.bean.isTCPRemoteCommitProviderSet() && !copy._isSet(134)) {
               Object o = this.bean.getTCPRemoteCommitProvider();
               copy.setTCPRemoteCommitProvider((TCPRemoteCommitProviderBean)null);
               copy.setTCPRemoteCommitProvider(o == null ? null : (TCPRemoteCommitProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TCPTransport")) && this.bean.isTCPTransportSet() && !copy._isSet(120)) {
               Object o = this.bean.getTCPTransport();
               copy.setTCPTransport((TCPTransportBean)null);
               copy.setTCPTransport(o == null ? null : (TCPTransportBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TableDeprecatedJDOMappingFactory")) && this.bean.isTableDeprecatedJDOMappingFactorySet() && !copy._isSet(99)) {
               Object o = this.bean.getTableDeprecatedJDOMappingFactory();
               copy.setTableDeprecatedJDOMappingFactory((TableDeprecatedJDOMappingFactoryBean)null);
               copy.setTableDeprecatedJDOMappingFactory(o == null ? null : (TableDeprecatedJDOMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TableJDBCSeq")) && this.bean.isTableJDBCSeqSet() && !copy._isSet(156)) {
               Object o = this.bean.getTableJDBCSeq();
               copy.setTableJDBCSeq((TableJDBCSeqBean)null);
               copy.setTableJDBCSeq(o == null ? null : (TableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TableJDORMappingFactory")) && this.bean.isTableJDORMappingFactorySet() && !copy._isSet(100)) {
               Object o = this.bean.getTableJDORMappingFactory();
               copy.setTableJDORMappingFactory((TableJDORMappingFactoryBean)null);
               copy.setTableJDORMappingFactory(o == null ? null : (TableJDORMappingFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TableLockUpdateManager")) && this.bean.isTableLockUpdateManagerSet() && !copy._isSet(171)) {
               Object o = this.bean.getTableLockUpdateManager();
               copy.setTableLockUpdateManager((TableLockUpdateManagerBean)null);
               copy.setTableLockUpdateManager(o == null ? null : (TableLockUpdateManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TableSchemaFactory")) && this.bean.isTableSchemaFactorySet() && !copy._isSet(151)) {
               Object o = this.bean.getTableSchemaFactory();
               copy.setTableSchemaFactory((TableSchemaFactoryBean)null);
               copy.setTableSchemaFactory(o == null ? null : (TableSchemaFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimeSeededSeq")) && this.bean.isTimeSeededSeqSet() && !copy._isSet(157)) {
               Object o = this.bean.getTimeSeededSeq();
               copy.setTimeSeededSeq((TimeSeededSeqBean)null);
               copy.setTimeSeededSeq(o == null ? null : (TimeSeededSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionIsolation")) && this.bean.isTransactionIsolationSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionMode")) && this.bean.isTransactionModeSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionNameExecutionContextNameProvider")) && this.bean.isTransactionNameExecutionContextNameProviderSet() && !copy._isSet(175)) {
               Object o = this.bean.getTransactionNameExecutionContextNameProvider();
               copy.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)null);
               copy.setTransactionNameExecutionContextNameProvider(o == null ? null : (TransactionNameExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("UserObjectExecutionContextNameProvider")) && this.bean.isUserObjectExecutionContextNameProviderSet() && !copy._isSet(176)) {
               Object o = this.bean.getUserObjectExecutionContextNameProvider();
               copy.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)null);
               copy.setUserObjectExecutionContextNameProvider(o == null ? null : (UserObjectExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ValueTableJDBCSeq")) && this.bean.isValueTableJDBCSeqSet() && !copy._isSet(158)) {
               Object o = this.bean.getValueTableJDBCSeq();
               copy.setValueTableJDBCSeq((ValueTableJDBCSeqBean)null);
               copy.setValueTableJDBCSeq(o == null ? null : (ValueTableJDBCSeqBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("VersionLockManager")) && this.bean.isVersionLockManagerSet() && !copy._isSet(80)) {
               Object o = this.bean.getVersionLockManager();
               copy.setVersionLockManager((VersionLockManagerBean)null);
               copy.setVersionLockManager(o == null ? null : (VersionLockManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WLS81JMX")) && this.bean.isWLS81JMXSet() && !copy._isSet(182)) {
               Object o = this.bean.getWLS81JMX();
               copy.setWLS81JMX((WLS81JMXBean)null);
               copy.setWLS81JMX(o == null ? null : (WLS81JMXBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WriteLockLevel")) && this.bean.isWriteLockLevelSet()) {
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getAbstractStoreBrokerFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getAccessDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getAggregateListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getAutoDetaches(), clazz, annotation);
         this.inferSubTree(this.bean.getBatchingOperationOrderUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCacheMap(), clazz, annotation);
         this.inferSubTree(this.bean.getClassTableJDBCSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getClientBrokerFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getClusterRemoteCommitProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getCommonsLogFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCompatibility(), clazz, annotation);
         this.inferSubTree(this.bean.getConcurrentHashMap(), clazz, annotation);
         this.inferSubTree(this.bean.getConnection2Properties(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionDecorators(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactory2Properties(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionFactoryProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectionProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getConstraintUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomBrokerFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomBrokerImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomClassResolver(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomCompatibility(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomDataCacheManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomDetachState(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomDriverDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomLog(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomMappingDefaults(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomMetaDataFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomMetaDataRepository(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomOrphanedKeyAction(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomPersistenceServer(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomProxyManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomQueryCompilationCache(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomRemoteCommitProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomSQLFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomSavepointManager(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomSchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getCustomUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDB2Dictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getDataCacheManagerImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getDataCaches(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultBrokerFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultBrokerImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultClassResolver(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultCompatibility(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultDataCacheManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultDetachState(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultDriverDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultMappingDefaults(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultMetaDataFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultMetaDataRepository(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultOrphanedKeyAction(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultProxyManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultQueryCompilationCache(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultSQLFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultSavepointManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultSchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getDeprecatedJDOMappingDefaults(), clazz, annotation);
         this.inferSubTree(this.bean.getDeprecatedJDOMetaDataFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDerbyDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getDetachOptionsAll(), clazz, annotation);
         this.inferSubTree(this.bean.getDetachOptionsFetchGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getDetachOptionsLoaded(), clazz, annotation);
         this.inferSubTree(this.bean.getDynamicSchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getEmpressDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getExceptionOrphanedKeyAction(), clazz, annotation);
         this.inferSubTree(this.bean.getExportProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getExtensionDeprecatedJDOMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getFetchGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getFileSchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getFilterListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getFoxProDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getGUIJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getGUIProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getHSQLDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getHTTPTransport(), clazz, annotation);
         this.inferSubTree(this.bean.getInMemorySavepointManager(), clazz, annotation);
         this.inferSubTree(this.bean.getInformixDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getInverseManager(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBC3SavepointManager(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCBrokerFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getJDOMetaDataFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getJDataStoreDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getJMSRemoteCommitProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getJMX2JMX(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoBroker(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoDataCacheManager(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoMappingRepository(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoPersistenceMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoPersistenceMetaDataFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoPoolingDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getKodoSQLFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getLazySchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getLog4JLogFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getLogFactoryImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getLogOrphanedKeyAction(), clazz, annotation);
         this.inferSubTree(this.bean.getMX4J1JMX(), clazz, annotation);
         this.inferSubTree(this.bean.getMappingDefaultsImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getMappingFileDeprecatedJDOMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getMySQLDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getNativeJDBCSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneJMX(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneLogFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneOrphanedKeyAction(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getORMFileJDORMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getOperationOrderUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getOracleDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getOracleSavepointManager(), clazz, annotation);
         this.inferSubTree(this.bean.getPersistenceMappingDefaults(), clazz, annotation);
         this.inferSubTree(this.bean.getPessimisticLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getPointbaseDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getPostgresDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getProfilingProxyManager(), clazz, annotation);
         this.inferSubTree(this.bean.getProxyManagerImpl(), clazz, annotation);
         this.inferSubTree(this.bean.getQueryCaches(), clazz, annotation);
         this.inferSubTree(this.bean.getSQLServerDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getSchemata(), clazz, annotation);
         this.inferSubTree(this.bean.getSimpleDriverDataSource(), clazz, annotation);
         this.inferSubTree(this.bean.getSingleJVMExclusiveLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getSingleJVMRemoteCommitProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getStackExecutionContextNameProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getSybaseDictionary(), clazz, annotation);
         this.inferSubTree(this.bean.getTCPRemoteCommitProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getTCPTransport(), clazz, annotation);
         this.inferSubTree(this.bean.getTableDeprecatedJDOMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getTableJDBCSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getTableJDORMappingFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getTableLockUpdateManager(), clazz, annotation);
         this.inferSubTree(this.bean.getTableSchemaFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getTimeSeededSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionNameExecutionContextNameProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getUserObjectExecutionContextNameProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getValueTableJDBCSeq(), clazz, annotation);
         this.inferSubTree(this.bean.getVersionLockManager(), clazz, annotation);
         this.inferSubTree(this.bean.getWLS81JMX(), clazz, annotation);
      }
   }
}
