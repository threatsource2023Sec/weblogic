package org.apache.openjpa.conf;

import java.util.Collection;
import java.util.Map;
import org.apache.openjpa.datacache.DataCacheManager;
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
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.meta.MetaDataFactory;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.ClassResolver;
import org.apache.openjpa.util.ProxyManager;
import org.apache.openjpa.util.StoreFacadeTypeRegistry;

public interface OpenJPAConfiguration extends Configuration {
   String LOG_METADATA = "openjpa.MetaData";
   String LOG_ENHANCE = "openjpa.Enhance";
   String LOG_RUNTIME = "openjpa.Runtime";
   String LOG_QUERY = "openjpa.Query";
   String LOG_DATACACHE = "openjpa.DataCache";
   String LOG_TOOL = "openjpa.Tool";
   String OPTION_NONTRANS_READ = "openjpa.option.NontransactionalRead";
   String OPTION_OPTIMISTIC = "openjpa.option.Optimistic";
   String OPTION_ID_APPLICATION = "openjpa.option.ApplicationIdentity";
   String OPTION_ID_DATASTORE = "openjpa.option.DatastoreIdentity";
   String OPTION_QUERY_SQL = "openjpa.option.SQL";
   String OPTION_TYPE_COLLECTION = "openjpa.option.Collection";
   String OPTION_TYPE_MAP = "openjpa.option.Map";
   String OPTION_TYPE_ARRAY = "openjpa.option.Array";
   String OPTION_NULL_CONTAINER = "openjpa.option.NullContainer";
   String OPTION_EMBEDDED_RELATION = "openjpa.option.EmbeddedRelation";
   String OPTION_EMBEDDED_COLLECTION_RELATION = "openjpa.option.EmbeddedCollectionRelation";
   String OPTION_EMBEDDED_MAP_RELATION = "openjpa.option.EmbeddedMapRelation";
   String OPTION_INC_FLUSH = "openjpa.option.IncrementalFlush";
   String OPTION_VALUE_AUTOASSIGN = "openjpa.option.AutoassignValue";
   String OPTION_VALUE_INCREMENT = "openjpa.option.IncrementValue";
   String OPTION_DATASTORE_CONNECTION = "openjpa.option.DataStoreConnection";
   String OPTION_JDBC_CONNECTION = "openjpa.option.JDBCConnection";

   Collection supportedOptions();

   String getSpecification();

   boolean setSpecification(String var1);

   String getClassResolver();

   void setClassResolver(String var1);

   ClassResolver getClassResolverInstance();

   void setClassResolver(ClassResolver var1);

   String getBrokerFactory();

   void setBrokerFactory(String var1);

   String getBrokerImpl();

   void setBrokerImpl(String var1);

   BrokerImpl newBrokerInstance(String var1, String var2);

   String getDataCache();

   void setDataCache(String var1);

   String getDataCacheManager();

   void setDataCacheManager(String var1);

   DataCacheManager getDataCacheManagerInstance();

   void setDataCacheManager(DataCacheManager var1);

   int getDataCacheTimeout();

   void setDataCacheTimeout(int var1);

   void setDataCacheTimeout(Integer var1);

   String getQueryCache();

   void setQueryCache(String var1);

   boolean getDynamicDataStructs();

   void setDynamicDataStructs(boolean var1);

   void setDynamicDataStructs(Boolean var1);

   String getLockManager();

   void setLockManager(String var1);

   LockManager newLockManagerInstance();

   String getInverseManager();

   void setInverseManager(String var1);

   InverseManager newInverseManagerInstance();

   String getSavepointManager();

   void setSavepointManager(String var1);

   SavepointManager getSavepointManagerInstance();

   String getOrphanedKeyAction();

   void setOrphanedKeyAction(String var1);

   OrphanedKeyAction getOrphanedKeyActionInstance();

   void setOrphanedKeyAction(OrphanedKeyAction var1);

   String getRemoteCommitProvider();

   void setRemoteCommitProvider(String var1);

   RemoteCommitProvider newRemoteCommitProviderInstance();

   RemoteCommitEventManager getRemoteCommitEventManager();

   void setRemoteCommitEventManager(RemoteCommitEventManager var1);

   String getTransactionMode();

   void setTransactionMode(String var1);

   boolean isTransactionModeManaged();

   void setTransactionModeManaged(boolean var1);

   String getManagedRuntime();

   void setManagedRuntime(String var1);

   ManagedRuntime getManagedRuntimeInstance();

   void setManagedRuntime(ManagedRuntime var1);

   String getProxyManager();

   void setProxyManager(String var1);

   ProxyManager getProxyManagerInstance();

   void setProxyManager(ProxyManager var1);

   String getMapping();

   void setMapping(String var1);

   String getMetaDataFactory();

   void setMetaDataFactory(String var1);

   MetaDataFactory newMetaDataFactoryInstance();

   String getMetaDataRepository();

   void setMetaDataRepository(String var1);

   MetaDataRepository getMetaDataRepositoryInstance();

   boolean metaDataRepositoryAvailable();

   MetaDataRepository newMetaDataRepositoryInstance();

   void setMetaDataRepository(MetaDataRepository var1);

   String getConnectionUserName();

   void setConnectionUserName(String var1);

   String getConnectionPassword();

   void setConnectionPassword(String var1);

   String getConnectionURL();

   void setConnectionURL(String var1);

   String getConnectionDriverName();

   void setConnectionDriverName(String var1);

   String getConnectionFactoryName();

   void setConnectionFactoryName(String var1);

   Object getConnectionFactory();

   void setConnectionFactory(Object var1);

   String getConnectionProperties();

   void setConnectionProperties(String var1);

   String getConnectionFactoryProperties();

   void setConnectionFactoryProperties(String var1);

   String getConnectionFactoryMode();

   void setConnectionFactoryMode(String var1);

   boolean isConnectionFactoryModeManaged();

   void setConnectionFactoryModeManaged(boolean var1);

   String getConnection2UserName();

   void setConnection2UserName(String var1);

   String getConnection2Password();

   void setConnection2Password(String var1);

   String getConnection2URL();

   void setConnection2URL(String var1);

   String getConnection2DriverName();

   void setConnection2DriverName(String var1);

   String getConnectionFactory2Name();

   void setConnectionFactory2Name(String var1);

   Object getConnectionFactory2();

   void setConnectionFactory2(Object var1);

   String getConnection2Properties();

   void setConnection2Properties(String var1);

   String getConnectionFactory2Properties();

   void setConnectionFactory2Properties(String var1);

   boolean getOptimistic();

   void setOptimistic(boolean var1);

   void setOptimistic(Boolean var1);

   boolean getRetainState();

   void setRetainState(boolean var1);

   void setRetainState(Boolean var1);

   String getAutoClear();

   void setAutoClear(String var1);

   int getAutoClearConstant();

   void setAutoClear(int var1);

   String getRestoreState();

   void setRestoreState(String var1);

   int getRestoreStateConstant();

   void setRestoreState(int var1);

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   void setIgnoreChanges(Boolean var1);

   String getAutoDetach();

   void setAutoDetach(String var1);

   int getAutoDetachConstant();

   void setAutoDetach(int var1);

   void setDetachState(String var1);

   DetachOptions getDetachStateInstance();

   void setDetachState(DetachOptions var1);

   boolean getNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   void setNontransactionalRead(Boolean var1);

   boolean getNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   void setNontransactionalWrite(Boolean var1);

   boolean getMultithreaded();

   void setMultithreaded(boolean var1);

   void setMultithreaded(Boolean var1);

   int getFetchBatchSize();

   void setFetchBatchSize(int var1);

   void setFetchBatchSize(Integer var1);

   int getMaxFetchDepth();

   void setMaxFetchDepth(int var1);

   void setMaxFetchDepth(Integer var1);

   String getFetchGroups();

   void setFetchGroups(String var1);

   String[] getFetchGroupsList();

   void setFetchGroups(String[] var1);

   String getFlushBeforeQueries();

   void setFlushBeforeQueries(String var1);

   int getFlushBeforeQueriesConstant();

   void setFlushBeforeQueries(int var1);

   int getLockTimeout();

   void setLockTimeout(int var1);

   void setLockTimeout(Integer var1);

   String getReadLockLevel();

   void setReadLockLevel(String var1);

   int getReadLockLevelConstant();

   void setReadLockLevel(int var1);

   String getWriteLockLevel();

   void setWriteLockLevel(String var1);

   int getWriteLockLevelConstant();

   void setWriteLockLevel(int var1);

   String getSequence();

   void setSequence(String var1);

   Seq getSequenceInstance();

   void setSequence(Seq var1);

   String getConnectionRetainMode();

   void setConnectionRetainMode(String var1);

   int getConnectionRetainModeConstant();

   void setConnectionRetainMode(int var1);

   String getFilterListeners();

   void setFilterListeners(String var1);

   FilterListener[] getFilterListenerInstances();

   void setFilterListeners(FilterListener[] var1);

   String getAggregateListeners();

   void setAggregateListeners(String var1);

   AggregateListener[] getAggregateListenerInstances();

   void setAggregateListeners(AggregateListener[] var1);

   boolean getRetryClassRegistration();

   void setRetryClassRegistration(boolean var1);

   void setRetryClassRegistration(Boolean var1);

   String getCompatibility();

   void setCompatibility(String var1);

   Compatibility getCompatibilityInstance();

   String getQueryCompilationCache();

   void setQueryCompilationCache(String var1);

   Map getQueryCompilationCacheInstance();

   StoreFacadeTypeRegistry getStoreFacadeTypeRegistry();

   BrokerFactoryEventManager getBrokerFactoryEventManager();

   String getRuntimeUnenhancedClasses();

   void setRuntimeUnenhancedClasses(String var1);

   int getRuntimeUnenhancedClassesConstant();

   void setRuntimeUnenhancedClasses(int var1);

   String getCacheMarshallers();

   void setCacheMarshallers(String var1);

   Map getCacheMarshallerInstances();
}
