package org.apache.openjpa.conf;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.datacache.ConcurrentDataCache;
import org.apache.openjpa.datacache.ConcurrentQueryCache;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.datacache.DataCacheManagerImpl;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.event.BrokerFactoryEventManager;
import org.apache.openjpa.event.OrphanedKeyAction;
import org.apache.openjpa.event.RemoteCommitEventManager;
import org.apache.openjpa.event.RemoteCommitProvider;
import org.apache.openjpa.kernel.BrokerImpl;
import org.apache.openjpa.kernel.InverseManager;
import org.apache.openjpa.kernel.LockManager;
import org.apache.openjpa.kernel.SavepointManager;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.kernel.exps.AggregateListener;
import org.apache.openjpa.kernel.exps.FilterListener;
import org.apache.openjpa.lib.conf.BooleanValue;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.IntValue;
import org.apache.openjpa.lib.conf.ObjectValue;
import org.apache.openjpa.lib.conf.PluginListValue;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.conf.StringListValue;
import org.apache.openjpa.lib.conf.StringValue;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ClassResolver;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.StoreFacadeTypeRegistry;

public class OpenJPAConfigurationImpl extends ConfigurationImpl implements OpenJPAConfiguration {
   private static final Localizer _loc = Localizer.forPackage(OpenJPAConfigurationImpl.class);
   protected MetaDataRepository metaRepository;
   protected RemoteCommitEventManager remoteEventManager;
   public ObjectValue classResolverPlugin;
   public BrokerValue brokerPlugin;
   public ObjectValue dataCachePlugin;
   public ObjectValue dataCacheManagerPlugin;
   public IntValue dataCacheTimeout;
   public ObjectValue queryCachePlugin;
   public BooleanValue dynamicDataStructs;
   public ObjectValue managedRuntimePlugin;
   public BooleanValue transactionMode;
   public IntValue connectionRetainMode;
   public IntValue fetchBatchSize;
   public IntValue maxFetchDepth;
   public StringListValue fetchGroups;
   public IntValue flushBeforeQueries;
   public IntValue lockTimeout;
   public IntValue readLockLevel;
   public IntValue writeLockLevel;
   public ObjectValue seqPlugin;
   public PluginListValue filterListenerPlugins;
   public PluginListValue aggregateListenerPlugins;
   public BooleanValue retryClassRegistration;
   public ObjectValue proxyManagerPlugin;
   public StringValue connectionUserName;
   public StringValue connectionPassword;
   public StringValue connectionURL;
   public StringValue connectionDriverName;
   public ObjectValue connectionFactory;
   public StringValue connectionFactoryName;
   public StringValue connectionProperties;
   public StringValue connectionFactoryProperties;
   public BooleanValue connectionFactoryMode;
   public StringValue connection2UserName;
   public StringValue connection2Password;
   public StringValue connection2URL;
   public StringValue connection2DriverName;
   public StringValue connection2Properties;
   public ObjectValue connectionFactory2;
   public StringValue connectionFactory2Name;
   public StringValue connectionFactory2Properties;
   public BooleanValue optimistic;
   public IntValue autoClear;
   public BooleanValue retainState;
   public IntValue restoreState;
   public ObjectValue detachStatePlugin;
   public BooleanValue ignoreChanges;
   public BooleanValue nontransactionalRead;
   public BooleanValue nontransactionalWrite;
   public BooleanValue multithreaded;
   public StringValue mapping;
   public PluginValue metaFactoryPlugin;
   public ObjectValue metaRepositoryPlugin;
   public ObjectValue lockManagerPlugin;
   public ObjectValue inverseManagerPlugin;
   public ObjectValue savepointManagerPlugin;
   public ObjectValue orphanedKeyPlugin;
   public ObjectValue compatibilityPlugin;
   public QueryCompilationCacheValue queryCompilationCachePlugin;
   public IntValue runtimeUnenhancedClasses;
   public CacheMarshallersValue cacheMarshallerPlugins;
   public BrokerFactoryValue brokerFactoryPlugin;
   public RemoteCommitProviderValue remoteProviderPlugin;
   public AutoDetachValue autoDetach;
   private Collection supportedOptions;
   private String spec;
   private final StoreFacadeTypeRegistry _storeFacadeRegistry;
   private BrokerFactoryEventManager _brokerFactoryEventManager;

   public OpenJPAConfigurationImpl() {
      this(true);
   }

   public OpenJPAConfigurationImpl(boolean loadGlobals) {
      this(true, loadGlobals);
   }

   public OpenJPAConfigurationImpl(boolean derivations, boolean loadGlobals) {
      super(false);
      this.metaRepository = null;
      this.remoteEventManager = null;
      this.supportedOptions = new HashSet(33);
      this.spec = null;
      this._storeFacadeRegistry = new StoreFacadeTypeRegistry();
      this._brokerFactoryEventManager = new BrokerFactoryEventManager(this);
      this.classResolverPlugin = this.addPlugin("ClassResolver", true);
      String[] aliases = new String[]{"default", "org.apache.openjpa.util.ClassResolverImpl", "spec", "org.apache.openjpa.util.ClassResolverImpl"};
      this.classResolverPlugin.setAliases(aliases);
      this.classResolverPlugin.setDefault(aliases[0]);
      this.classResolverPlugin.setString(aliases[0]);
      this.classResolverPlugin.setInstantiatingGetter("getClassResolverInstance");
      this.brokerFactoryPlugin = new BrokerFactoryValue();
      this.addValue(this.brokerFactoryPlugin);
      this.brokerPlugin = new BrokerValue();
      this.addValue(this.brokerPlugin);
      this.dataCacheManagerPlugin = this.addPlugin("DataCacheManager", true);
      aliases = new String[]{"default", DataCacheManagerImpl.class.getName()};
      this.dataCacheManagerPlugin.setAliases(aliases);
      this.dataCacheManagerPlugin.setDefault(aliases[0]);
      this.dataCacheManagerPlugin.setString(aliases[0]);
      this.dataCacheManagerPlugin.setInstantiatingGetter("getDataCacheManager");
      this.dataCachePlugin = this.addPlugin("DataCache", false);
      aliases = new String[]{"false", null, "true", ConcurrentDataCache.class.getName(), "concurrent", ConcurrentDataCache.class.getName()};
      this.dataCachePlugin.setAliases(aliases);
      this.dataCachePlugin.setDefault(aliases[0]);
      this.dataCachePlugin.setString(aliases[0]);
      this.dataCacheTimeout = this.addInt("DataCacheTimeout");
      this.dataCacheTimeout.setDefault("-1");
      this.dataCacheTimeout.set(-1);
      this.dataCacheTimeout.setDynamic(true);
      this.queryCachePlugin = this.addPlugin("QueryCache", true);
      aliases = new String[]{"true", ConcurrentQueryCache.class.getName(), "concurrent", ConcurrentQueryCache.class.getName(), "false", null};
      this.queryCachePlugin.setAliases(aliases);
      this.queryCachePlugin.setDefault(aliases[0]);
      this.queryCachePlugin.setString(aliases[0]);
      this.dynamicDataStructs = this.addBoolean("DynamicDataStructs");
      this.dynamicDataStructs.setDefault("false");
      this.dynamicDataStructs.set(false);
      this.lockManagerPlugin = this.addPlugin("LockManager", false);
      aliases = new String[]{"none", "org.apache.openjpa.kernel.NoneLockManager", "version", "org.apache.openjpa.kernel.VersionLockManager"};
      this.lockManagerPlugin.setAliases(aliases);
      this.lockManagerPlugin.setDefault(aliases[0]);
      this.lockManagerPlugin.setString(aliases[0]);
      this.inverseManagerPlugin = this.addPlugin("InverseManager", false);
      aliases = new String[]{"false", null, "true", "org.apache.openjpa.kernel.InverseManager"};
      this.inverseManagerPlugin.setAliases(aliases);
      this.inverseManagerPlugin.setDefault(aliases[0]);
      this.inverseManagerPlugin.setString(aliases[0]);
      this.savepointManagerPlugin = this.addPlugin("SavepointManager", true);
      aliases = new String[]{"in-mem", "org.apache.openjpa.kernel.InMemorySavepointManager"};
      this.savepointManagerPlugin.setAliases(aliases);
      this.savepointManagerPlugin.setDefault(aliases[0]);
      this.savepointManagerPlugin.setString(aliases[0]);
      this.savepointManagerPlugin.setInstantiatingGetter("getSavepointManagerInstance");
      this.orphanedKeyPlugin = this.addPlugin("OrphanedKeyAction", true);
      aliases = new String[]{"log", "org.apache.openjpa.event.LogOrphanedKeyAction", "exception", "org.apache.openjpa.event.ExceptionOrphanedKeyAction", "none", "org.apache.openjpa.event.NoneOrphanedKeyAction"};
      this.orphanedKeyPlugin.setAliases(aliases);
      this.orphanedKeyPlugin.setDefault(aliases[0]);
      this.orphanedKeyPlugin.setString(aliases[0]);
      this.orphanedKeyPlugin.setInstantiatingGetter("getOrphanedKeyActionInstance");
      this.remoteProviderPlugin = new RemoteCommitProviderValue();
      this.addValue(this.remoteProviderPlugin);
      this.transactionMode = this.addBoolean("TransactionMode");
      aliases = new String[]{"local", "false", "managed", "true"};
      this.transactionMode.setAliases(aliases);
      this.transactionMode.setDefault(aliases[0]);
      this.managedRuntimePlugin = this.addPlugin("ManagedRuntime", true);
      aliases = new String[]{"auto", "org.apache.openjpa.ee.AutomaticManagedRuntime", "jndi", "org.apache.openjpa.ee.JNDIManagedRuntime", "invocation", "org.apache.openjpa.ee.InvocationManagedRuntime"};
      this.managedRuntimePlugin.setAliases(aliases);
      this.managedRuntimePlugin.setDefault(aliases[0]);
      this.managedRuntimePlugin.setString(aliases[0]);
      this.managedRuntimePlugin.setInstantiatingGetter("getManagedRuntimeInstance");
      this.proxyManagerPlugin = this.addPlugin("ProxyManager", true);
      aliases = new String[]{"default", "org.apache.openjpa.util.ProxyManagerImpl"};
      this.proxyManagerPlugin.setAliases(aliases);
      this.proxyManagerPlugin.setDefault(aliases[0]);
      this.proxyManagerPlugin.setString(aliases[0]);
      this.proxyManagerPlugin.setInstantiatingGetter("getProxyManagerInstance");
      this.mapping = this.addString("Mapping");
      this.metaFactoryPlugin = this.addPlugin("MetaDataFactory", false);
      this.metaRepositoryPlugin = (ObjectValue)this.addValue(new MetaDataRepositoryValue());
      this.connectionFactory = this.addObject("ConnectionFactory");
      this.connectionFactory.setInstantiatingGetter("getConnectionFactory");
      this.connectionFactory2 = this.addObject("ConnectionFactory2");
      this.connectionFactory2.setInstantiatingGetter("getConnectionFactory2");
      this.connectionFactory.setDynamic(true);
      this.connectionFactory2.setDynamic(true);
      this.connectionUserName = this.addString("ConnectionUserName");
      this.connectionPassword = this.addString("ConnectionPassword");
      this.connectionURL = this.addString("ConnectionURL");
      this.connectionDriverName = this.addString("ConnectionDriverName");
      this.connectionFactoryName = this.addString("ConnectionFactoryName");
      this.connectionProperties = this.addString("ConnectionProperties");
      this.connectionFactoryProperties = this.addString("ConnectionFactoryProperties");
      this.connection2UserName = this.addString("Connection2UserName");
      this.connection2Password = this.addString("Connection2Password");
      this.connection2URL = this.addString("Connection2URL");
      this.connection2DriverName = this.addString("Connection2DriverName");
      this.connection2Properties = this.addString("Connection2Properties");
      this.connectionFactory2Properties = this.addString("ConnectionFactory2Properties");
      this.connectionFactory2Name = this.addString("ConnectionFactory2Name");
      this.connectionFactoryMode = this.addBoolean("ConnectionFactoryMode");
      aliases = new String[]{"local", "false", "managed", "true"};
      this.connectionFactoryMode.setAliases(aliases);
      this.connectionFactoryMode.setDefault(aliases[0]);
      this.optimistic = this.addBoolean("Optimistic");
      this.optimistic.setDefault("true");
      this.optimistic.set(true);
      this.autoClear = this.addInt("AutoClear");
      aliases = new String[]{"datastore", String.valueOf(0), "all", String.valueOf(1)};
      this.autoClear.setAliases(aliases);
      this.autoClear.setDefault(aliases[0]);
      this.autoClear.set(0);
      this.autoClear.setAliasListComprehensive(true);
      this.retainState = this.addBoolean("RetainState");
      this.retainState.setDefault("true");
      this.retainState.set(true);
      this.restoreState = this.addInt("RestoreState");
      aliases = new String[]{"none", String.valueOf(0), "false", String.valueOf(0), "immutable", String.valueOf(1), "true", String.valueOf(1), "all", String.valueOf(2)};
      this.restoreState.setAliases(aliases);
      this.restoreState.setDefault(aliases[0]);
      this.restoreState.set(1);
      this.restoreState.setAliasListComprehensive(true);
      this.autoDetach = new AutoDetachValue();
      this.addValue(this.autoDetach);
      this.detachStatePlugin = this.addPlugin("DetachState", true);
      aliases = new String[]{"loaded", DetachOptions.Loaded.class.getName(), "fgs", DetachOptions.FetchGroups.class.getName(), "fetch-groups", DetachOptions.FetchGroups.class.getName(), "all", DetachOptions.All.class.getName()};
      this.detachStatePlugin.setAliases(aliases);
      this.detachStatePlugin.setDefault(aliases[0]);
      this.detachStatePlugin.setString(aliases[0]);
      this.detachStatePlugin.setInstantiatingGetter("getDetachStateInstance");
      this.ignoreChanges = this.addBoolean("IgnoreChanges");
      this.nontransactionalRead = this.addBoolean("NontransactionalRead");
      this.nontransactionalRead.setDefault("true");
      this.nontransactionalRead.set(true);
      this.nontransactionalWrite = this.addBoolean("NontransactionalWrite");
      this.multithreaded = this.addBoolean("Multithreaded");
      this.fetchBatchSize = this.addInt("FetchBatchSize");
      this.fetchBatchSize.setDefault("-1");
      this.fetchBatchSize.set(-1);
      this.fetchBatchSize.setDynamic(true);
      this.maxFetchDepth = this.addInt("MaxFetchDepth");
      this.maxFetchDepth.setDefault("-1");
      this.maxFetchDepth.set(-1);
      this.fetchGroups = this.addStringList("FetchGroups");
      this.fetchGroups.setDefault("default");
      this.fetchGroups.set(new String[]{"default"});
      this.flushBeforeQueries = this.addInt("FlushBeforeQueries");
      aliases = new String[]{"true", String.valueOf(0), "false", String.valueOf(1), "with-connection", String.valueOf(2)};
      this.flushBeforeQueries.setAliases(aliases);
      this.flushBeforeQueries.setDefault(aliases[0]);
      this.flushBeforeQueries.set(0);
      this.flushBeforeQueries.setAliasListComprehensive(true);
      this.lockTimeout = this.addInt("LockTimeout");
      this.lockTimeout.setDefault("-1");
      this.lockTimeout.set(-1);
      this.lockTimeout.setDynamic(true);
      this.readLockLevel = this.addInt("ReadLockLevel");
      aliases = new String[]{"read", String.valueOf(10), "write", String.valueOf(20), "none", String.valueOf(0)};
      this.readLockLevel.setAliases(aliases);
      this.readLockLevel.setDefault(aliases[0]);
      this.readLockLevel.set(10);
      this.readLockLevel.setAliasListComprehensive(true);
      this.writeLockLevel = this.addInt("WriteLockLevel");
      aliases = new String[]{"read", String.valueOf(10), "write", String.valueOf(20), "none", String.valueOf(0)};
      this.writeLockLevel.setAliases(aliases);
      this.writeLockLevel.setDefault(aliases[1]);
      this.writeLockLevel.set(20);
      this.writeLockLevel.setAliasListComprehensive(true);
      this.seqPlugin = new SeqValue("Sequence");
      this.seqPlugin.setInstantiatingGetter("getSequenceInstance");
      this.addValue(this.seqPlugin);
      this.connectionRetainMode = this.addInt("ConnectionRetainMode");
      aliases = new String[]{"on-demand", String.valueOf(0), "transaction", String.valueOf(1), "always", String.valueOf(2), "persistence-manager", String.valueOf(2)};
      this.connectionRetainMode.setAliases(aliases);
      this.connectionRetainMode.setDefault(aliases[0]);
      this.connectionRetainMode.setAliasListComprehensive(true);
      this.connectionRetainMode.set(0);
      this.filterListenerPlugins = this.addPluginList("FilterListeners");
      this.filterListenerPlugins.setInstantiatingGetter("getFilterListenerInstances");
      this.aggregateListenerPlugins = this.addPluginList("AggregateListeners");
      this.aggregateListenerPlugins.setInstantiatingGetter("getAggregateListenerInstances");
      this.retryClassRegistration = this.addBoolean("RetryClassRegistration");
      this.compatibilityPlugin = this.addPlugin("Compatibility", true);
      aliases = new String[]{"default", Compatibility.class.getName()};
      this.compatibilityPlugin.setAliases(aliases);
      this.compatibilityPlugin.setDefault(aliases[0]);
      this.compatibilityPlugin.setString(aliases[0]);
      this.compatibilityPlugin.setInstantiatingGetter("getCompatibilityInstance");
      this.queryCompilationCachePlugin = new QueryCompilationCacheValue("QueryCompilationCache");
      this.queryCompilationCachePlugin.setInstantiatingGetter("getQueryCompilationCacheInstance");
      this.addValue(this.queryCompilationCachePlugin);
      this.runtimeUnenhancedClasses = this.addInt("RuntimeUnenhancedClasses");
      this.runtimeUnenhancedClasses.setAliases(new String[]{"supported", String.valueOf(0), "unsupported", String.valueOf(1), "warn", String.valueOf(2)});
      this.runtimeUnenhancedClasses.setDefault("supported");
      this.runtimeUnenhancedClasses.setString("supported");
      this.runtimeUnenhancedClasses.setAliasListComprehensive(true);
      this.cacheMarshallerPlugins = (CacheMarshallersValue)this.addValue(new CacheMarshallersValue(this));
      this.supportedOptions.add("openjpa.option.NontransactionalRead");
      this.supportedOptions.add("openjpa.option.Optimistic");
      this.supportedOptions.add("openjpa.option.ApplicationIdentity");
      this.supportedOptions.add("openjpa.option.DatastoreIdentity");
      this.supportedOptions.add("openjpa.option.Collection");
      this.supportedOptions.add("openjpa.option.Map");
      this.supportedOptions.add("openjpa.option.Array");
      this.supportedOptions.add("openjpa.option.NullContainer");
      this.supportedOptions.add("openjpa.option.EmbeddedRelation");
      this.supportedOptions.add("openjpa.option.EmbeddedCollectionRelation");
      this.supportedOptions.add("openjpa.option.EmbeddedMapRelation");
      this.supportedOptions.add("openjpa.option.IncrementalFlush");
      this.supportedOptions.add("openjpa.option.AutoassignValue");
      this.supportedOptions.add("openjpa.option.IncrementValue");
      this.supportedOptions.add("openjpa.option.DataStoreConnection");
      if (derivations) {
         ProductDerivations.beforeConfigurationLoad(this);
      }

      if (loadGlobals) {
         this.loadGlobals();
      }

   }

   public Collection supportedOptions() {
      return this.supportedOptions;
   }

   public String getSpecification() {
      return this.spec;
   }

   public boolean setSpecification(String spec) {
      if (spec == null) {
         return false;
      } else if (this.spec != null) {
         if (!this.spec.equals(spec) && this.getConfigurationLog().isWarnEnabled()) {
            this.getConfigurationLog().warn(_loc.get("diff-specs", this.spec, spec));
         }

         return false;
      } else {
         this.spec = spec;
         ProductDerivations.afterSpecificationSet(this);
         return true;
      }
   }

   public void setClassResolver(String classResolver) {
      this.classResolverPlugin.setString(classResolver);
   }

   public String getClassResolver() {
      return this.classResolverPlugin.getString();
   }

   public void setClassResolver(ClassResolver classResolver) {
      this.classResolverPlugin.set(classResolver);
   }

   public ClassResolver getClassResolverInstance() {
      if (this.classResolverPlugin.get() == null) {
         this.classResolverPlugin.instantiate(ClassResolver.class, this);
      }

      return (ClassResolver)this.classResolverPlugin.get();
   }

   public void setBrokerFactory(String factory) {
      this.brokerFactoryPlugin.setString(factory);
   }

   public String getBrokerFactory() {
      return this.brokerFactoryPlugin.getString();
   }

   public void setBrokerImpl(String broker) {
      this.brokerPlugin.setString(broker);
   }

   public String getBrokerImpl() {
      return this.brokerPlugin.getString();
   }

   public BrokerImpl newBrokerInstance(String user, String pass) {
      BrokerImpl broker = (BrokerImpl)this.brokerPlugin.instantiate(BrokerImpl.class, this);
      if (broker != null) {
         broker.setAuthentication(user, pass);
      }

      return broker;
   }

   public void setDataCacheManager(String mgr) {
      this.dataCacheManagerPlugin.setString(mgr);
   }

   public String getDataCacheManager() {
      return this.dataCacheManagerPlugin.getString();
   }

   public void setDataCacheManager(DataCacheManager dcm) {
      if (dcm != null) {
         dcm.initialize(this, this.dataCachePlugin, this.queryCachePlugin);
      }

      this.dataCacheManagerPlugin.set(dcm);
   }

   public DataCacheManager getDataCacheManagerInstance() {
      DataCacheManager dcm = (DataCacheManager)this.dataCacheManagerPlugin.get();
      if (dcm == null) {
         dcm = (DataCacheManager)this.dataCacheManagerPlugin.instantiate(DataCacheManager.class, this);
         dcm.initialize(this, this.dataCachePlugin, this.queryCachePlugin);
      }

      return dcm;
   }

   public void setDataCache(String dataCache) {
      this.dataCachePlugin.setString(dataCache);
   }

   public String getDataCache() {
      return this.dataCachePlugin.getString();
   }

   public void setDataCacheTimeout(int dataCacheTimeout) {
      this.dataCacheTimeout.set(dataCacheTimeout);
   }

   public void setDataCacheTimeout(Integer dataCacheTimeout) {
      if (dataCacheTimeout != null) {
         this.setDataCacheTimeout(dataCacheTimeout);
      }

   }

   public int getDataCacheTimeout() {
      return this.dataCacheTimeout.get();
   }

   public void setQueryCache(String queryCache) {
      this.queryCachePlugin.setString(queryCache);
   }

   public String getQueryCache() {
      return this.queryCachePlugin.getString();
   }

   public boolean getDynamicDataStructs() {
      return this.dynamicDataStructs.get();
   }

   public void setDynamicDataStructs(boolean dynamic) {
      this.dynamicDataStructs.set(dynamic);
   }

   public void setDynamicDataStructs(Boolean dynamic) {
      this.setDynamicDataStructs(dynamic);
   }

   public void setLockManager(String lockManager) {
      this.lockManagerPlugin.setString(lockManager);
   }

   public String getLockManager() {
      return this.lockManagerPlugin.getString();
   }

   public LockManager newLockManagerInstance() {
      return (LockManager)this.lockManagerPlugin.instantiate(LockManager.class, this, false);
   }

   public void setInverseManager(String inverseManager) {
      this.inverseManagerPlugin.setString(inverseManager);
   }

   public String getInverseManager() {
      return this.inverseManagerPlugin.getString();
   }

   public InverseManager newInverseManagerInstance() {
      return (InverseManager)this.inverseManagerPlugin.instantiate(InverseManager.class, this);
   }

   public void setSavepointManager(String savepointManager) {
      this.savepointManagerPlugin.setString(savepointManager);
   }

   public String getSavepointManager() {
      return this.savepointManagerPlugin.getString();
   }

   public SavepointManager getSavepointManagerInstance() {
      if (this.savepointManagerPlugin.get() == null) {
         this.savepointManagerPlugin.instantiate(SavepointManager.class, this);
      }

      return (SavepointManager)this.savepointManagerPlugin.get();
   }

   public void setOrphanedKeyAction(String action) {
      this.orphanedKeyPlugin.setString(action);
   }

   public String getOrphanedKeyAction() {
      return this.orphanedKeyPlugin.getString();
   }

   public OrphanedKeyAction getOrphanedKeyActionInstance() {
      if (this.orphanedKeyPlugin.get() == null) {
         this.orphanedKeyPlugin.instantiate(OrphanedKeyAction.class, this);
      }

      return (OrphanedKeyAction)this.orphanedKeyPlugin.get();
   }

   public void setOrphanedKeyAction(OrphanedKeyAction action) {
      this.orphanedKeyPlugin.set(action);
   }

   public void setRemoteCommitProvider(String remoteCommitProvider) {
      this.remoteProviderPlugin.setString(remoteCommitProvider);
   }

   public String getRemoteCommitProvider() {
      return this.remoteProviderPlugin.getString();
   }

   public RemoteCommitProvider newRemoteCommitProviderInstance() {
      return this.remoteProviderPlugin.instantiateProvider(this);
   }

   public void setRemoteCommitEventManager(RemoteCommitEventManager remoteEventManager) {
      this.remoteEventManager = remoteEventManager;
      this.remoteProviderPlugin.configureEventManager(remoteEventManager);
   }

   public RemoteCommitEventManager getRemoteCommitEventManager() {
      if (this.remoteEventManager == null) {
         this.remoteEventManager = new RemoteCommitEventManager(this);
         this.remoteProviderPlugin.configureEventManager(this.remoteEventManager);
      }

      return this.remoteEventManager;
   }

   public void setTransactionMode(String transactionMode) {
      this.transactionMode.setString(transactionMode);
   }

   public String getTransactionMode() {
      return this.transactionMode.getString();
   }

   public void setTransactionModeManaged(boolean managed) {
      this.transactionMode.set(managed);
   }

   public boolean isTransactionModeManaged() {
      return this.transactionMode.get();
   }

   public void setManagedRuntime(String managedRuntime) {
      this.managedRuntimePlugin.setString(managedRuntime);
   }

   public String getManagedRuntime() {
      return this.managedRuntimePlugin.getString();
   }

   public void setManagedRuntime(ManagedRuntime managedRuntime) {
      this.managedRuntimePlugin.set(managedRuntime);
   }

   public ManagedRuntime getManagedRuntimeInstance() {
      if (this.managedRuntimePlugin.get() == null) {
         this.managedRuntimePlugin.instantiate(ManagedRuntime.class, this);
      }

      return (ManagedRuntime)this.managedRuntimePlugin.get();
   }

   public void setProxyManager(String proxyManager) {
      this.proxyManagerPlugin.setString(proxyManager);
   }

   public String getProxyManager() {
      return this.proxyManagerPlugin.getString();
   }

   public void setProxyManager(ProxyManager proxyManager) {
      this.proxyManagerPlugin.set(proxyManager);
   }

   public ProxyManager getProxyManagerInstance() {
      if (this.proxyManagerPlugin.get() == null) {
         this.proxyManagerPlugin.instantiate(ProxyManager.class, this);
      }

      return (ProxyManager)this.proxyManagerPlugin.get();
   }

   public void setMapping(String mapping) {
      this.mapping.setString(mapping);
   }

   public String getMapping() {
      return this.mapping.getString();
   }

   public void setMetaDataFactory(String meta) {
      this.metaFactoryPlugin.setString(meta);
   }

   public String getMetaDataFactory() {
      return this.metaFactoryPlugin.getString();
   }

   public MetaDataFactory newMetaDataFactoryInstance() {
      return (MetaDataFactory)this.metaFactoryPlugin.instantiate(MetaDataFactory.class, this);
   }

   public void setMetaDataRepository(String meta) {
      this.metaRepositoryPlugin.setString(meta);
   }

   public String getMetaDataRepository() {
      return this.metaRepositoryPlugin.getString();
   }

   public void setMetaDataRepository(MetaDataRepository meta) {
      this.metaRepository = meta;
   }

   public MetaDataRepository getMetaDataRepositoryInstance() {
      if (this.metaRepository == null) {
         this.metaRepository = this.newMetaDataRepositoryInstance();
      }

      return this.metaRepository;
   }

   public boolean metaDataRepositoryAvailable() {
      return this.metaRepository != null;
   }

   public MetaDataRepository newMetaDataRepositoryInstance() {
      return (MetaDataRepository)this.metaRepositoryPlugin.instantiate(MetaDataRepository.class, this);
   }

   public void setConnectionUserName(String connectionUserName) {
      this.connectionUserName.setString(connectionUserName);
   }

   public String getConnectionUserName() {
      return this.connectionUserName.getString();
   }

   public void setConnectionPassword(String connectionPassword) {
      this.connectionPassword.setString(connectionPassword);
   }

   public String getConnectionPassword() {
      return this.connectionPassword.getString();
   }

   public void setConnectionURL(String connectionURL) {
      this.connectionURL.setString(connectionURL);
   }

   public String getConnectionURL() {
      return this.connectionURL.getString();
   }

   public void setConnectionDriverName(String driverName) {
      this.connectionDriverName.setString(driverName);
   }

   public String getConnectionDriverName() {
      return this.connectionDriverName.getString();
   }

   public void setConnectionProperties(String connectionProperties) {
      this.connectionProperties.setString(connectionProperties);
   }

   public String getConnectionProperties() {
      return this.connectionProperties.getString();
   }

   public void setConnectionFactoryProperties(String connectionFactoryProperties) {
      this.connectionFactoryProperties.setString(connectionFactoryProperties);
   }

   public String getConnectionFactoryProperties() {
      return this.connectionFactoryProperties.getString();
   }

   public String getConnectionFactoryMode() {
      return this.connectionFactoryMode.getString();
   }

   public void setConnectionFactoryMode(String mode) {
      this.connectionFactoryMode.setString(mode);
   }

   public boolean isConnectionFactoryModeManaged() {
      return this.connectionFactoryMode.get();
   }

   public void setConnectionFactoryModeManaged(boolean managed) {
      this.connectionFactoryMode.set(managed);
   }

   public void setConnectionFactoryName(String connectionFactoryName) {
      this.connectionFactoryName.setString(connectionFactoryName);
   }

   public String getConnectionFactoryName() {
      return this.connectionFactoryName.getString();
   }

   public void setConnectionFactory(Object factory) {
      this.connectionFactory.set(factory);
   }

   public Object getConnectionFactory() {
      if (this.connectionFactory.get() == null) {
         this.connectionFactory.set(this.lookupConnectionFactory(this.getConnectionFactoryName()), true);
      }

      return this.connectionFactory.get();
   }

   private Object lookupConnectionFactory(String name) {
      name = StringUtils.trimToNull(name);
      if (name == null) {
         return null;
      } else {
         try {
            return Configurations.lookup(name);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   public void setConnection2UserName(String connection2UserName) {
      this.connection2UserName.setString(connection2UserName);
   }

   public String getConnection2UserName() {
      return this.connection2UserName.getString();
   }

   public void setConnection2Password(String connection2Password) {
      this.connection2Password.setString(connection2Password);
   }

   public String getConnection2Password() {
      return this.connection2Password.getString();
   }

   public void setConnection2URL(String connection2URL) {
      this.connection2URL.setString(connection2URL);
   }

   public String getConnection2URL() {
      return this.connection2URL.getString();
   }

   public void setConnection2DriverName(String driverName) {
      this.connection2DriverName.setString(driverName);
   }

   public String getConnection2DriverName() {
      return this.connection2DriverName.getString();
   }

   public void setConnection2Properties(String connection2Properties) {
      this.connection2Properties.setString(connection2Properties);
   }

   public String getConnection2Properties() {
      return this.connection2Properties.getString();
   }

   public void setConnectionFactory2Properties(String connectionFactory2Properties) {
      this.connectionFactory2Properties.setString(connectionFactory2Properties);
   }

   public String getConnectionFactory2Properties() {
      return this.connectionFactory2Properties.getString();
   }

   public void setConnectionFactory2Name(String connectionFactory2Name) {
      this.connectionFactory2Name.setString(connectionFactory2Name);
   }

   public String getConnectionFactory2Name() {
      return this.connectionFactory2Name.getString();
   }

   public void setConnectionFactory2(Object factory) {
      this.connectionFactory2.set(factory);
   }

   public Object getConnectionFactory2() {
      if (this.connectionFactory2.get() == null) {
         this.connectionFactory2.set(this.lookupConnectionFactory(this.getConnectionFactory2Name()), false);
      }

      return this.connectionFactory2.get();
   }

   public void setOptimistic(boolean optimistic) {
      this.optimistic.set(optimistic);
   }

   public void setOptimistic(Boolean optimistic) {
      if (optimistic != null) {
         this.setOptimistic(optimistic);
      }

   }

   public boolean getOptimistic() {
      return this.optimistic.get();
   }

   public void setAutoClear(String clear) {
      this.autoClear.setString(clear);
   }

   public String getAutoClear() {
      return this.autoClear.getString();
   }

   public void setAutoClear(int clear) {
      this.autoClear.set(clear);
   }

   public int getAutoClearConstant() {
      return this.autoClear.get();
   }

   public void setRetainState(boolean retainState) {
      this.retainState.set(retainState);
   }

   public void setRetainState(Boolean retainState) {
      if (retainState != null) {
         this.setRetainState(retainState);
      }

   }

   public boolean getRetainState() {
      return this.retainState.get();
   }

   public void setRestoreState(String restoreState) {
      this.restoreState.setString(restoreState);
   }

   public String getRestoreState() {
      return this.restoreState.getString();
   }

   public void setRestoreState(int restoreState) {
      this.restoreState.set(restoreState);
   }

   public int getRestoreStateConstant() {
      return this.restoreState.get();
   }

   public void setAutoDetach(String autoDetach) {
      this.autoDetach.setString(autoDetach);
   }

   public String getAutoDetach() {
      return this.autoDetach.getString();
   }

   public void setAutoDetach(int autoDetachFlags) {
      this.autoDetach.setConstant(autoDetachFlags);
   }

   public int getAutoDetachConstant() {
      return this.autoDetach.getConstant();
   }

   public void setDetachState(String detachState) {
      this.detachStatePlugin.setString(detachState);
   }

   public String getDetachState() {
      return this.detachStatePlugin.getString();
   }

   public void setDetachState(DetachOptions detachState) {
      this.detachStatePlugin.set(detachState);
   }

   public DetachOptions getDetachStateInstance() {
      if (this.detachStatePlugin.get() == null) {
         this.detachStatePlugin.instantiate(DetachOptions.class, this);
      }

      return (DetachOptions)this.detachStatePlugin.get();
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      this.ignoreChanges.set(ignoreChanges);
   }

   public void setIgnoreChanges(Boolean ignoreChanges) {
      if (ignoreChanges != null) {
         this.setIgnoreChanges(ignoreChanges);
      }

   }

   public boolean getIgnoreChanges() {
      return this.ignoreChanges.get();
   }

   public void setNontransactionalRead(boolean nontransactionalRead) {
      this.nontransactionalRead.set(nontransactionalRead);
   }

   public void setNontransactionalRead(Boolean nontransactionalRead) {
      if (nontransactionalRead != null) {
         this.setNontransactionalRead(nontransactionalRead);
      }

   }

   public boolean getNontransactionalRead() {
      return this.nontransactionalRead.get();
   }

   public void setNontransactionalWrite(boolean nontransactionalWrite) {
      this.nontransactionalWrite.set(nontransactionalWrite);
   }

   public void setNontransactionalWrite(Boolean nontransactionalWrite) {
      if (nontransactionalWrite != null) {
         this.setNontransactionalWrite(nontransactionalWrite);
      }

   }

   public boolean getNontransactionalWrite() {
      return this.nontransactionalWrite.get();
   }

   public void setMultithreaded(boolean multithreaded) {
      this.multithreaded.set(multithreaded);
   }

   public void setMultithreaded(Boolean multithreaded) {
      if (multithreaded != null) {
         this.setMultithreaded(multithreaded);
      }

   }

   public boolean getMultithreaded() {
      return this.multithreaded.get();
   }

   public void setFetchBatchSize(int fetchBatchSize) {
      this.fetchBatchSize.set(fetchBatchSize);
   }

   public void setFetchBatchSize(Integer fetchBatchSize) {
      if (fetchBatchSize != null) {
         this.setFetchBatchSize(fetchBatchSize);
      }

   }

   public int getFetchBatchSize() {
      return this.fetchBatchSize.get();
   }

   public void setMaxFetchDepth(int maxFetchDepth) {
      this.maxFetchDepth.set(maxFetchDepth);
   }

   public void setMaxFetchDepth(Integer maxFetchDepth) {
      if (maxFetchDepth != null) {
         this.setMaxFetchDepth(maxFetchDepth);
      }

   }

   public int getMaxFetchDepth() {
      return this.maxFetchDepth.get();
   }

   public void setFetchGroups(String fetchGroups) {
      this.fetchGroups.setString(fetchGroups);
   }

   public String getFetchGroups() {
      return this.fetchGroups.getString();
   }

   public String[] getFetchGroupsList() {
      return this.fetchGroups.get();
   }

   public void setFetchGroups(String[] fetchGroups) {
      this.fetchGroups.set(fetchGroups);
   }

   public void setFlushBeforeQueries(String flush) {
      this.flushBeforeQueries.setString(flush);
   }

   public String getFlushBeforeQueries() {
      return this.flushBeforeQueries.getString();
   }

   public void setFlushBeforeQueries(int flush) {
      this.flushBeforeQueries.set(flush);
   }

   public int getFlushBeforeQueriesConstant() {
      return this.flushBeforeQueries.get();
   }

   public void setLockTimeout(int timeout) {
      this.lockTimeout.set(timeout);
   }

   public void setLockTimeout(Integer timeout) {
      if (timeout != null) {
         this.setLockTimeout(timeout);
      }

   }

   public int getLockTimeout() {
      return this.lockTimeout.get();
   }

   public void setReadLockLevel(String level) {
      this.readLockLevel.setString(level);
   }

   public String getReadLockLevel() {
      return this.readLockLevel.getString();
   }

   public void setReadLockLevel(int level) {
      this.readLockLevel.set(level);
   }

   public int getReadLockLevelConstant() {
      return this.readLockLevel.get();
   }

   public void setWriteLockLevel(String level) {
      this.writeLockLevel.setString(level);
   }

   public String getWriteLockLevel() {
      return this.writeLockLevel.getString();
   }

   public void setWriteLockLevel(int level) {
      this.writeLockLevel.set(level);
   }

   public int getWriteLockLevelConstant() {
      return this.writeLockLevel.get();
   }

   public void setSequence(String sequence) {
      this.seqPlugin.setString(sequence);
   }

   public String getSequence() {
      return this.seqPlugin.getString();
   }

   public void setSequence(Seq seq) {
      this.seqPlugin.set(seq);
   }

   public Seq getSequenceInstance() {
      if (this.seqPlugin.get() == null) {
         this.seqPlugin.instantiate(Seq.class, this);
      }

      return (Seq)this.seqPlugin.get();
   }

   public void setConnectionRetainMode(String connectionRetainMode) {
      this.connectionRetainMode.setString(connectionRetainMode);
   }

   public String getConnectionRetainMode() {
      return this.connectionRetainMode.getString();
   }

   public void setConnectionRetainMode(int connectionRetainMode) {
      this.connectionRetainMode.set(connectionRetainMode);
   }

   public int getConnectionRetainModeConstant() {
      return this.connectionRetainMode.get();
   }

   public void setFilterListeners(String filterListeners) {
      this.filterListenerPlugins.setString(filterListeners);
   }

   public String getFilterListeners() {
      return this.filterListenerPlugins.getString();
   }

   public void setFilterListeners(FilterListener[] listeners) {
      this.filterListenerPlugins.set(listeners);
   }

   public FilterListener[] getFilterListenerInstances() {
      if (this.filterListenerPlugins.get() == null) {
         this.filterListenerPlugins.instantiate(FilterListener.class, this);
      }

      return (FilterListener[])((FilterListener[])this.filterListenerPlugins.get());
   }

   public void setAggregateListeners(String aggregateListeners) {
      this.aggregateListenerPlugins.setString(aggregateListeners);
   }

   public String getAggregateListeners() {
      return this.aggregateListenerPlugins.getString();
   }

   public void setAggregateListeners(AggregateListener[] listeners) {
      this.aggregateListenerPlugins.set(listeners);
   }

   public AggregateListener[] getAggregateListenerInstances() {
      if (this.aggregateListenerPlugins.get() == null) {
         this.aggregateListenerPlugins.instantiate(AggregateListener.class, this);
      }

      return (AggregateListener[])((AggregateListener[])this.aggregateListenerPlugins.get());
   }

   public void setRetryClassRegistration(boolean retry) {
      this.retryClassRegistration.set(retry);
   }

   public void setRetryClassRegistration(Boolean retry) {
      if (retry != null) {
         this.setRetryClassRegistration(retry);
      }

   }

   public boolean getRetryClassRegistration() {
      return this.retryClassRegistration.get();
   }

   public String getCompatibility() {
      return this.compatibilityPlugin.getString();
   }

   public void setCompatibility(String compatibility) {
      this.compatibilityPlugin.setString(compatibility);
   }

   public Compatibility getCompatibilityInstance() {
      if (this.compatibilityPlugin.get() == null) {
         this.compatibilityPlugin.instantiate(Compatibility.class, this);
      }

      return (Compatibility)this.compatibilityPlugin.get();
   }

   public String getQueryCompilationCache() {
      return this.queryCompilationCachePlugin.getString();
   }

   public void setQueryCompilationCache(String queryCompilationCache) {
      this.queryCompilationCachePlugin.setString(queryCompilationCache);
   }

   public Map getQueryCompilationCacheInstance() {
      if (this.queryCompilationCachePlugin.get() == null) {
         this.queryCompilationCachePlugin.instantiate(Map.class, this);
      }

      return (Map)this.queryCompilationCachePlugin.get();
   }

   public StoreFacadeTypeRegistry getStoreFacadeTypeRegistry() {
      return this._storeFacadeRegistry;
   }

   public BrokerFactoryEventManager getBrokerFactoryEventManager() {
      return this._brokerFactoryEventManager;
   }

   public String getRuntimeUnenhancedClasses() {
      return this.runtimeUnenhancedClasses.getString();
   }

   public int getRuntimeUnenhancedClassesConstant() {
      return this.runtimeUnenhancedClasses.get();
   }

   public void setRuntimeUnenhancedClasses(int mode) {
      this.runtimeUnenhancedClasses.set(mode);
   }

   public void setRuntimeUnenhancedClasses(String mode) {
      this.runtimeUnenhancedClasses.setString(mode);
   }

   public String getCacheMarshallers() {
      return this.cacheMarshallerPlugins.getString();
   }

   public void setCacheMarshallers(String marshallers) {
      this.cacheMarshallerPlugins.setString(marshallers);
   }

   public Map getCacheMarshallerInstances() {
      return this.cacheMarshallerPlugins.getInstancesAsMap();
   }

   public void instantiateAll() {
      super.instantiateAll();
      this.getMetaDataRepositoryInstance();
      this.getRemoteCommitEventManager();
      this.cacheMarshallerPlugins.initialize();
   }

   protected void preClose() {
      ImplHelper.close(this.metaRepository);
      ImplHelper.close(this.remoteEventManager);
      super.preClose();
   }

   public Log getConfigurationLog() {
      return this.getLog("openjpa.Runtime");
   }
}
