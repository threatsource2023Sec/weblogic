package kodo.conf.descriptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.utils.codegen.ImplementationFactory;
import weblogic.utils.codegen.RoleInfoImplementationFactory;

public class BeanInfoFactory implements RoleInfoImplementationFactory {
   private static final Map interfaceMap = new HashMap(199);
   private static final ArrayList roleInfoList;
   private static final BeanInfoFactory SINGLETON;

   public static final ImplementationFactory getInstance() {
      return SINGLETON;
   }

   public String getImplementationClassName(String interfaceName) {
      return (String)interfaceMap.get(interfaceName);
   }

   public String[] getInterfaces() {
      Set keySet = interfaceMap.keySet();
      return (String[])((String[])keySet.toArray(new String[keySet.size()]));
   }

   public String[] getInterfacesWithRoleInfo() {
      return (String[])((String[])roleInfoList.toArray(new String[roleInfoList.size()]));
   }

   public String getRoleInfoImplementationFactoryTimestamp() {
      try {
         InputStream is = this.getClass().getResourceAsStream("BeanInfoFactory.tstamp");
         return (new BufferedReader(new InputStreamReader(is))).readLine();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   static {
      interfaceMap.put("kodo.conf.descriptor.AbstractStoreBrokerFactoryBean", "kodo.conf.descriptor.AbstractStoreBrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.AggregateListenerBean", "kodo.conf.descriptor.AggregateListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.AggregateListenersBean", "kodo.conf.descriptor.AggregateListenersBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.AutoDetachBean", "kodo.conf.descriptor.AutoDetachBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.BrokerFactoryBean", "kodo.conf.descriptor.BrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.BrokerImplBean", "kodo.conf.descriptor.BrokerImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CacheMapBean", "kodo.conf.descriptor.CacheMapBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ClassResolverBean", "kodo.conf.descriptor.ClassResolverBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ClientBrokerFactoryBean", "kodo.conf.descriptor.ClientBrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ClusterRemoteCommitProviderBean", "kodo.conf.descriptor.ClusterRemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CommonsLogFactoryBean", "kodo.conf.descriptor.CommonsLogFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ConcurrentHashMapBean", "kodo.conf.descriptor.ConcurrentHashMapBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomAggregateListenerBean", "kodo.conf.descriptor.CustomAggregateListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomBrokerFactoryBean", "kodo.conf.descriptor.CustomBrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomBrokerImplBean", "kodo.conf.descriptor.CustomBrokerImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomClassResolverBean", "kodo.conf.descriptor.CustomClassResolverBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomCompatibilityBean", "kodo.conf.descriptor.CustomCompatibilityBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomDataCacheBean", "kodo.conf.descriptor.CustomDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomDataCacheManagerBean", "kodo.conf.descriptor.CustomDataCacheManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomDetachStateBean", "kodo.conf.descriptor.CustomDetachStateBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomFilterListenerBean", "kodo.conf.descriptor.CustomFilterListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomLockManagerBean", "kodo.conf.descriptor.CustomLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomLogBean", "kodo.conf.descriptor.CustomLogBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomMetaDataFactoryBean", "kodo.conf.descriptor.CustomMetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomMetaDataRepositoryBean", "kodo.conf.descriptor.CustomMetaDataRepositoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomOrphanedKeyActionBean", "kodo.conf.descriptor.CustomOrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomPersistenceServerBean", "kodo.conf.descriptor.CustomPersistenceServerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomPlugInBean", "kodo.conf.descriptor.CustomPlugInBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomProxyManagerBean", "kodo.conf.descriptor.CustomProxyManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomQueryCacheBean", "kodo.conf.descriptor.CustomQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomQueryCompilationCacheBean", "kodo.conf.descriptor.CustomQueryCompilationCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomRemoteCommitProviderBean", "kodo.conf.descriptor.CustomRemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomSavepointManagerBean", "kodo.conf.descriptor.CustomSavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.CustomSeqBean", "kodo.conf.descriptor.CustomSeqBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DataCacheBean", "kodo.conf.descriptor.DataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DataCacheManagerBean", "kodo.conf.descriptor.DataCacheManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DataCacheManagerImplBean", "kodo.conf.descriptor.DataCacheManagerImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DataCachesBean", "kodo.conf.descriptor.DataCachesBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultBrokerFactoryBean", "kodo.conf.descriptor.DefaultBrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultBrokerImplBean", "kodo.conf.descriptor.DefaultBrokerImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultClassResolverBean", "kodo.conf.descriptor.DefaultClassResolverBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultCompatibilityBean", "kodo.conf.descriptor.DefaultCompatibilityBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultDataCacheBean", "kodo.conf.descriptor.DefaultDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultDataCacheManagerBean", "kodo.conf.descriptor.DefaultDataCacheManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultDetachStateBean", "kodo.conf.descriptor.DefaultDetachStateBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultLockManagerBean", "kodo.conf.descriptor.DefaultLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultMetaDataFactoryBean", "kodo.conf.descriptor.DefaultMetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultMetaDataRepositoryBean", "kodo.conf.descriptor.DefaultMetaDataRepositoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultOrphanedKeyActionBean", "kodo.conf.descriptor.DefaultOrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultProxyManagerBean", "kodo.conf.descriptor.DefaultProxyManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultQueryCacheBean", "kodo.conf.descriptor.DefaultQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultQueryCompilationCacheBean", "kodo.conf.descriptor.DefaultQueryCompilationCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DefaultSavepointManagerBean", "kodo.conf.descriptor.DefaultSavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBean", "kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DetachOptionsAllBean", "kodo.conf.descriptor.DetachOptionsAllBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DetachOptionsFetchGroupsBean", "kodo.conf.descriptor.DetachOptionsFetchGroupsBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DetachOptionsLoadedBean", "kodo.conf.descriptor.DetachOptionsLoadedBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DetachStateBean", "kodo.conf.descriptor.DetachStateBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.DisabledQueryCacheBean", "kodo.conf.descriptor.DisabledQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ExceptionOrphanedKeyActionBean", "kodo.conf.descriptor.ExceptionOrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ExecutionContextNameProviderBean", "kodo.conf.descriptor.ExecutionContextNameProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ExportProfilingBean", "kodo.conf.descriptor.ExportProfilingBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.FetchGroupsBean", "kodo.conf.descriptor.FetchGroupsBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.FilterListenerBean", "kodo.conf.descriptor.FilterListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.FilterListenersBean", "kodo.conf.descriptor.FilterListenersBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.GUIJMXBean", "kodo.conf.descriptor.GUIJMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.GUIProfilingBean", "kodo.conf.descriptor.GUIProfilingBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.GemFireDataCacheBean", "kodo.conf.descriptor.GemFireDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.GemFireQueryCacheBean", "kodo.conf.descriptor.GemFireQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.HTTPTransportBean", "kodo.conf.descriptor.HTTPTransportBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.InMemorySavepointManagerBean", "kodo.conf.descriptor.InMemorySavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.InverseManagerBean", "kodo.conf.descriptor.InverseManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.JDOMetaDataFactoryBean", "kodo.conf.descriptor.JDOMetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.JMSRemoteCommitProviderBean", "kodo.conf.descriptor.JMSRemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.JMX2JMXBean", "kodo.conf.descriptor.JMX2JMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.JMXBean", "kodo.conf.descriptor.JMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoBrokerBean", "kodo.conf.descriptor.KodoBrokerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoCompatibilityBean", "kodo.conf.descriptor.KodoCompatibilityBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoConcurrentDataCacheBean", "kodo.conf.descriptor.KodoConcurrentDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoConcurrentQueryCacheBean", "kodo.conf.descriptor.KodoConcurrentQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoDataCacheManagerBean", "kodo.conf.descriptor.KodoDataCacheManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBean", "kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LRUDataCacheBean", "kodo.conf.descriptor.LRUDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LRUQueryCacheBean", "kodo.conf.descriptor.LRUQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LocalJMXBean", "kodo.conf.descriptor.LocalJMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LocalProfilingBean", "kodo.conf.descriptor.LocalProfilingBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LockManagerBean", "kodo.conf.descriptor.LockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.Log4JLogFactoryBean", "kodo.conf.descriptor.Log4JLogFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LogBean", "kodo.conf.descriptor.LogBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LogFactoryImplBean", "kodo.conf.descriptor.LogFactoryImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.LogOrphanedKeyActionBean", "kodo.conf.descriptor.LogOrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.MX4J1JMXBean", "kodo.conf.descriptor.MX4J1JMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.MetaDataFactoryBean", "kodo.conf.descriptor.MetaDataFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.MetaDataRepositoryBean", "kodo.conf.descriptor.MetaDataRepositoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.NoneJMXBean", "kodo.conf.descriptor.NoneJMXBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.NoneLockManagerBean", "kodo.conf.descriptor.NoneLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.NoneLogFactoryBean", "kodo.conf.descriptor.NoneLogFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.NoneOrphanedKeyActionBean", "kodo.conf.descriptor.NoneOrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.NoneProfilingBean", "kodo.conf.descriptor.NoneProfilingBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.OrphanedKeyActionBean", "kodo.conf.descriptor.OrphanedKeyActionBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.PersistenceCompatibilityBean", "kodo.conf.descriptor.PersistenceCompatibilityBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.PersistenceConfigurationPropertyBean", "kodo.conf.descriptor.PersistenceConfigurationPropertyBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.PersistenceServerBean", "kodo.conf.descriptor.PersistenceServerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ProfilingBean", "kodo.conf.descriptor.ProfilingBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ProfilingProxyManagerBean", "kodo.conf.descriptor.ProfilingProxyManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.PropertiesBean", "kodo.conf.descriptor.PropertiesBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ProxyManagerBean", "kodo.conf.descriptor.ProxyManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.ProxyManagerImplBean", "kodo.conf.descriptor.ProxyManagerImplBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.QueryCacheBean", "kodo.conf.descriptor.QueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.QueryCachesBean", "kodo.conf.descriptor.QueryCachesBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.QueryCompilationCacheBean", "kodo.conf.descriptor.QueryCompilationCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.RemoteCommitProviderBean", "kodo.conf.descriptor.RemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.SavepointManagerBean", "kodo.conf.descriptor.SavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.SequenceBean", "kodo.conf.descriptor.SequenceBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.SingleJVMExclusiveLockManagerBean", "kodo.conf.descriptor.SingleJVMExclusiveLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.SingleJVMRemoteCommitProviderBean", "kodo.conf.descriptor.SingleJVMRemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.StackExecutionContextNameProviderBean", "kodo.conf.descriptor.StackExecutionContextNameProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TCPRemoteCommitProviderBean", "kodo.conf.descriptor.TCPRemoteCommitProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TCPTransportBean", "kodo.conf.descriptor.TCPTransportBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TangosolDataCacheBean", "kodo.conf.descriptor.TangosolDataCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TangosolQueryCacheBean", "kodo.conf.descriptor.TangosolQueryCacheBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TimeSeededSeqBean", "kodo.conf.descriptor.TimeSeededSeqBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBean", "kodo.conf.descriptor.TransactionNameExecutionContextNameProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.UserObjectExecutionContextNameProviderBean", "kodo.conf.descriptor.UserObjectExecutionContextNameProviderBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.VersionLockManagerBean", "kodo.conf.descriptor.VersionLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.conf.descriptor.WLS81JMXBean", "kodo.conf.descriptor.WLS81JMXBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.AccessDictionaryBean", "kodo.jdbc.conf.descriptor.AccessDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.BatchingOperationOrderUpdateManagerBean", "kodo.jdbc.conf.descriptor.BatchingOperationOrderUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.BuiltInDBDictionaryBean", "kodo.jdbc.conf.descriptor.BuiltInDBDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBean", "kodo.jdbc.conf.descriptor.ClassTableJDBCSeqBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ConnectionDecoratorBean", "kodo.jdbc.conf.descriptor.ConnectionDecoratorBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ConnectionDecoratorsBean", "kodo.jdbc.conf.descriptor.ConnectionDecoratorsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ConstraintUpdateManagerBean", "kodo.jdbc.conf.descriptor.ConstraintUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomConnectionDecoratorBean", "kodo.jdbc.conf.descriptor.CustomConnectionDecoratorBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomDictionaryBean", "kodo.jdbc.conf.descriptor.CustomDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomDriverDataSourceBean", "kodo.jdbc.conf.descriptor.CustomDriverDataSourceBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomJDBCListenerBean", "kodo.jdbc.conf.descriptor.CustomJDBCListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomMappingDefaultsBean", "kodo.jdbc.conf.descriptor.CustomMappingDefaultsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomMappingFactoryBean", "kodo.jdbc.conf.descriptor.CustomMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomSQLFactoryBean", "kodo.jdbc.conf.descriptor.CustomSQLFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomSchemaFactoryBean", "kodo.jdbc.conf.descriptor.CustomSchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.CustomUpdateManagerBean", "kodo.jdbc.conf.descriptor.CustomUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DB2DictionaryBean", "kodo.jdbc.conf.descriptor.DB2DictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DBDictionaryBean", "kodo.jdbc.conf.descriptor.DBDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DefaultDriverDataSourceBean", "kodo.jdbc.conf.descriptor.DefaultDriverDataSourceBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DefaultMappingDefaultsBean", "kodo.jdbc.conf.descriptor.DefaultMappingDefaultsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DefaultSQLFactoryBean", "kodo.jdbc.conf.descriptor.DefaultSQLFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DefaultSchemaFactoryBean", "kodo.jdbc.conf.descriptor.DefaultSchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DefaultUpdateManagerBean", "kodo.jdbc.conf.descriptor.DefaultUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DeprecatedJDOMappingDefaultsBean", "kodo.jdbc.conf.descriptor.DeprecatedJDOMappingDefaultsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DerbyDictionaryBean", "kodo.jdbc.conf.descriptor.DerbyDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DriverDataSourceBean", "kodo.jdbc.conf.descriptor.DriverDataSourceBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.DynamicSchemaFactoryBean", "kodo.jdbc.conf.descriptor.DynamicSchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.EmpressDictionaryBean", "kodo.jdbc.conf.descriptor.EmpressDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ExtensionDeprecatedJDOMappingFactoryBean", "kodo.jdbc.conf.descriptor.ExtensionDeprecatedJDOMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.FileSchemaFactoryBean", "kodo.jdbc.conf.descriptor.FileSchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.FoxProDictionaryBean", "kodo.jdbc.conf.descriptor.FoxProDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.HSQLDictionaryBean", "kodo.jdbc.conf.descriptor.HSQLDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.InformixDictionaryBean", "kodo.jdbc.conf.descriptor.InformixDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDBC3SavepointManagerBean", "kodo.jdbc.conf.descriptor.JDBC3SavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDBCBrokerFactoryBean", "kodo.jdbc.conf.descriptor.JDBCBrokerFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDBCListenerBean", "kodo.jdbc.conf.descriptor.JDBCListenerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDBCListenersBean", "kodo.jdbc.conf.descriptor.JDBCListenersBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDORMappingFactoryBean", "kodo.jdbc.conf.descriptor.JDORMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.JDataStoreDictionaryBean", "kodo.jdbc.conf.descriptor.JDataStoreDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.KodoMappingRepositoryBean", "kodo.jdbc.conf.descriptor.KodoMappingRepositoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.KodoPersistenceMappingFactoryBean", "kodo.jdbc.conf.descriptor.KodoPersistenceMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.KodoPoolingDataSourceBean", "kodo.jdbc.conf.descriptor.KodoPoolingDataSourceBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.KodoSQLFactoryBean", "kodo.jdbc.conf.descriptor.KodoSQLFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.LazySchemaFactoryBean", "kodo.jdbc.conf.descriptor.LazySchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.MappingDefaultsBean", "kodo.jdbc.conf.descriptor.MappingDefaultsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.MappingDefaultsImplBean", "kodo.jdbc.conf.descriptor.MappingDefaultsImplBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.MappingFactoryBean", "kodo.jdbc.conf.descriptor.MappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBean", "kodo.jdbc.conf.descriptor.MappingFileDeprecatedJDOMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.MySQLDictionaryBean", "kodo.jdbc.conf.descriptor.MySQLDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.NativeJDBCSeqBean", "kodo.jdbc.conf.descriptor.NativeJDBCSeqBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ORMFileJDORMappingFactoryBean", "kodo.jdbc.conf.descriptor.ORMFileJDORMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.OperationOrderUpdateManagerBean", "kodo.jdbc.conf.descriptor.OperationOrderUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.OracleDictionaryBean", "kodo.jdbc.conf.descriptor.OracleDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.OracleSavepointManagerBean", "kodo.jdbc.conf.descriptor.OracleSavepointManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PersistenceConfigurationBean", "kodo.jdbc.conf.descriptor.PersistenceConfigurationBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PersistenceMappingDefaultsBean", "kodo.jdbc.conf.descriptor.PersistenceMappingDefaultsBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBean", "kodo.jdbc.conf.descriptor.PersistenceUnitConfigurationBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PessimisticLockManagerBean", "kodo.jdbc.conf.descriptor.PessimisticLockManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PointbaseDictionaryBean", "kodo.jdbc.conf.descriptor.PointbaseDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.PostgresDictionaryBean", "kodo.jdbc.conf.descriptor.PostgresDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SQLFactoryBean", "kodo.jdbc.conf.descriptor.SQLFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SQLServerDictionaryBean", "kodo.jdbc.conf.descriptor.SQLServerDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SchemaFactoryBean", "kodo.jdbc.conf.descriptor.SchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SchemasBean", "kodo.jdbc.conf.descriptor.SchemasBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBean", "kodo.jdbc.conf.descriptor.SimpleDriverDataSourceBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.SybaseDictionaryBean", "kodo.jdbc.conf.descriptor.SybaseDictionaryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBean", "kodo.jdbc.conf.descriptor.TableDeprecatedJDOMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.TableJDBCSeqBean", "kodo.jdbc.conf.descriptor.TableJDBCSeqBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBean", "kodo.jdbc.conf.descriptor.TableJDORMappingFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.TableLockUpdateManagerBean", "kodo.jdbc.conf.descriptor.TableLockUpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.TableSchemaFactoryBean", "kodo.jdbc.conf.descriptor.TableSchemaFactoryBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.UpdateManagerBean", "kodo.jdbc.conf.descriptor.UpdateManagerBeanImplBeanInfo");
      interfaceMap.put("kodo.jdbc.conf.descriptor.ValueTableJDBCSeqBean", "kodo.jdbc.conf.descriptor.ValueTableJDBCSeqBeanImplBeanInfo");
      roleInfoList = new ArrayList(2);
      roleInfoList.add("kodo.conf.descriptor.PersistenceConfigurationPropertyBean");
      roleInfoList.add("kodo.conf.descriptor.PropertiesBean");
      SINGLETON = new BeanInfoFactory();
   }
}
