package kodo.jdbc.conf.descriptor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import kodo.conf.descriptor.AbstractStoreBrokerFactoryBean;
import kodo.conf.descriptor.ClientBrokerFactoryBean;
import kodo.conf.descriptor.ClusterRemoteCommitProviderBean;
import kodo.conf.descriptor.CommonsLogFactoryBean;
import kodo.conf.descriptor.CustomPlugInBean;
import kodo.conf.descriptor.DataCacheManagerImplBean;
import kodo.conf.descriptor.DefaultBrokerFactoryBean;
import kodo.conf.descriptor.DefaultBrokerImplBean;
import kodo.conf.descriptor.DefaultClassResolverBean;
import kodo.conf.descriptor.DefaultCompatibilityBean;
import kodo.conf.descriptor.DefaultDataCacheBean;
import kodo.conf.descriptor.DefaultDataCacheManagerBean;
import kodo.conf.descriptor.DefaultDetachStateBean;
import kodo.conf.descriptor.DefaultLockManagerBean;
import kodo.conf.descriptor.DefaultMetaDataFactoryBean;
import kodo.conf.descriptor.DefaultMetaDataRepositoryBean;
import kodo.conf.descriptor.DefaultOrphanedKeyActionBean;
import kodo.conf.descriptor.DefaultProxyManagerBean;
import kodo.conf.descriptor.DefaultQueryCacheBean;
import kodo.conf.descriptor.DefaultQueryCompilationCacheBean;
import kodo.conf.descriptor.DefaultSavepointManagerBean;
import kodo.conf.descriptor.DeprecatedJDOMetaDataFactoryBean;
import kodo.conf.descriptor.DetachOptionsAllBean;
import kodo.conf.descriptor.DetachOptionsFetchGroupsBean;
import kodo.conf.descriptor.DetachOptionsLoadedBean;
import kodo.conf.descriptor.DisabledQueryCacheBean;
import kodo.conf.descriptor.ExceptionOrphanedKeyActionBean;
import kodo.conf.descriptor.GemFireDataCacheBean;
import kodo.conf.descriptor.GemFireQueryCacheBean;
import kodo.conf.descriptor.HTTPTransportBean;
import kodo.conf.descriptor.InMemorySavepointManagerBean;
import kodo.conf.descriptor.JDOMetaDataFactoryBean;
import kodo.conf.descriptor.JMSRemoteCommitProviderBean;
import kodo.conf.descriptor.KodoBrokerBean;
import kodo.conf.descriptor.KodoCompatibilityBean;
import kodo.conf.descriptor.KodoConcurrentDataCacheBean;
import kodo.conf.descriptor.KodoConcurrentQueryCacheBean;
import kodo.conf.descriptor.KodoDataCacheManagerBean;
import kodo.conf.descriptor.KodoPersistenceMetaDataFactoryBean;
import kodo.conf.descriptor.LRUDataCacheBean;
import kodo.conf.descriptor.LRUQueryCacheBean;
import kodo.conf.descriptor.Log4JLogFactoryBean;
import kodo.conf.descriptor.LogFactoryImplBean;
import kodo.conf.descriptor.LogOrphanedKeyActionBean;
import kodo.conf.descriptor.NoneLockManagerBean;
import kodo.conf.descriptor.NoneLogFactoryBean;
import kodo.conf.descriptor.NoneOrphanedKeyActionBean;
import kodo.conf.descriptor.PersistenceCompatibilityBean;
import kodo.conf.descriptor.PersistenceConfigurationPropertyBean;
import kodo.conf.descriptor.ProfilingProxyManagerBean;
import kodo.conf.descriptor.PropertiesBean;
import kodo.conf.descriptor.ProxyManagerImplBean;
import kodo.conf.descriptor.SingleJVMExclusiveLockManagerBean;
import kodo.conf.descriptor.SingleJVMRemoteCommitProviderBean;
import kodo.conf.descriptor.TCPRemoteCommitProviderBean;
import kodo.conf.descriptor.TCPTransportBean;
import kodo.conf.descriptor.TangosolDataCacheBean;
import kodo.conf.descriptor.TangosolQueryCacheBean;
import kodo.conf.descriptor.TimeSeededSeqBean;
import kodo.conf.descriptor.VersionLockManagerBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import weblogic.diagnostics.debug.DebugLogger;

public class JDBCConfigurationBeanParser {
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugKodoWeblogic");
   private static final Map _propRenames = new HashMap();
   private static final Set _containers;
   private static final Map _pluginPropRenames;
   private static final Set _storeProps;
   private static final Map _plugins;
   private static final List _props;

   private static List initializeList() {
      List props = new ArrayList();
      Class type = PersistenceUnitConfigurationBean.class;
      Collection properties = getProperties(type);
      Iterator it = properties.iterator();

      while(it.hasNext()) {
         PropertyDescriptor pd = (PropertyDescriptor)it.next();
         if (!"Name".equals(pd.getName())) {
            Property prop = new Property(pd);
            if (!Class[].class.equals(prop.type)) {
               props.add(prop);
            }
         }
      }

      return props;
   }

   public Properties load(PersistenceUnitConfigurationBean bean) {
      Properties props = new Properties();
      Iterator it = _props.iterator();

      while(it.hasNext()) {
         Property prop = (Property)it.next();
         this.loadProperty(bean, prop, props);
      }

      return props;
   }

   private void loadProperty(Object bean, Property prop, Properties props) {
      StringBuffer buf = new StringBuffer();
      Object val = getValue(prop.pd, bean);
      if (val != null) {
         if (logger.isDebugEnabled()) {
            logger.debug("Found prop:" + prop.pd.getName() + ":" + val);
         }

         if (prop.simple) {
            buf.append(val);
         } else if (PropertiesBean.class.isAssignableFrom(prop.type)) {
            if (!this.writePropertiesBean((PropertiesBean)val, buf, true)) {
               buf.append(")");
            }
         } else {
            this.generatePluginString(prop, val, buf);
         }

         if (buf.length() > 0) {
            props.setProperty(prop.property, buf.toString());
            if (logger.isDebugEnabled()) {
               logger.debug("Set " + prop.property + ":" + buf.toString());
            }
         }

      }
   }

   private void generatePluginString(Property prop, Object val, StringBuffer buf) {
      boolean started = false;
      Collection props = getProperties(prop.type);
      boolean hasArrayProps = false;
      Iterator it = props.iterator();

      while(it.hasNext()) {
         PropertyDescriptor pd = (PropertyDescriptor)it.next();
         Class type = pd.getPropertyType();
         if (!Class[].class.equals(type)) {
            if (type.isArray()) {
               hasArrayProps = true;
               if (isSet(pd, val)) {
                  if (type.getComponentType().getName().startsWith("java")) {
                     started = this.appendSimpleArray(pd, val, started, buf);
                  } else {
                     started = this.appendPluginArray(pd, val, prop, started, buf);
                  }
               }
            } else if (prop.container && isSet(pd, val)) {
               Object pVal = getValue(pd, val);
               started = this.appendPlugin(pVal, prop, started, buf);
            }
         }
      }

      if (!hasArrayProps && !prop.container) {
         this.appendPlugin(val, prop, started, buf);
      }

   }

   private boolean appendSimpleArray(PropertyDescriptor pd, Object val, boolean started, StringBuffer buf) {
      Class type = pd.getPropertyType().getComponentType();
      Object[] arrayVal = null;
      if (type.isPrimitive()) {
         if (Integer.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((int[])((int[])val));
         } else if (Short.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((short[])((short[])val));
         } else if (Long.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((long[])((long[])val));
         } else if (Byte.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((byte[])((byte[])val));
         } else if (Boolean.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((boolean[])((boolean[])val));
         } else if (Float.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((float[])((float[])val));
         } else if (Double.TYPE.equals(type)) {
            arrayVal = ArrayUtils.toObject((double[])((double[])val));
         } else if (Character.TYPE.equals(type)) {
            char[] chars = (char[])((char[])val);
            arrayVal = new Character[chars.length];

            for(int i = 0; i < chars.length; ++i) {
               ((Object[])arrayVal)[i] = new Character(chars[i]);
            }
         }
      } else {
         arrayVal = (Object[])((Object[])getValue(pd, val));
      }

      if (arrayVal != null && ((Object[])arrayVal).length != 0) {
         if (started) {
            buf.append(", ");
         }

         for(int i = 0; i < ((Object[])arrayVal).length; ++i) {
            buf.append(((Object[])arrayVal)[i]);
            if (i < ((Object[])arrayVal).length - 1) {
               buf.append(',');
            }
         }

         return true;
      } else {
         return started;
      }
   }

   private boolean appendPluginArray(PropertyDescriptor pd, Object val, Property owner, boolean started, StringBuffer buf) {
      Object[] arrayVal = (Object[])((Object[])getValue(pd, val));
      if (arrayVal != null && arrayVal.length != 0) {
         if (started) {
            buf.append(", ");
         }

         for(int i = 0; i < arrayVal.length; ++i) {
            boolean wroteSomething = this.appendPlugin(arrayVal[i], owner, false, buf);
            if (wroteSomething) {
               if (i < arrayVal.length - 1) {
                  buf.append(", ");
               }

               started = true;
            }
         }

         return started;
      } else {
         return started;
      }
   }

   private boolean appendPlugin(Object val, Property owner, boolean callerStarted, StringBuffer buf) {
      if (val == null) {
         return callerStarted;
      } else {
         if (callerStarted) {
            buf.append(", ");
         }

         CustomPlugInBean custom = val instanceof CustomPlugInBean ? (CustomPlugInBean)val : null;
         if (custom != null && custom.getClassname() != null) {
            buf.append(custom.getClassname());
         } else {
            String alias = getAlias(val.getClass());
            if (alias != null) {
               buf.append(alias);
            }
         }

         Collection props = getProperties(val.getClass());
         boolean first = true;
         Iterator it = props.iterator();

         while(true) {
            PropertyDescriptor pd;
            do {
               do {
                  if (!it.hasNext()) {
                     first = this.writeCustomProperties(custom, buf, first);
                     if (!first) {
                        buf.append(')');
                     }

                     return true;
                  }

                  pd = (PropertyDescriptor)it.next();
               } while(!isSimple(pd.getPropertyType()));
            } while(custom != null && "classname".equals(pd.getName()));

            Object propVal = getValue(pd, val);
            if (propVal != null) {
               if (first) {
                  first = false;
                  buf.append('(');
               } else {
                  buf.append(", ");
               }

               String name = StringUtils.capitalize(pd.getName());
               String lookup = owner.simpleProperty + "." + name;
               if (_pluginPropRenames.containsKey(lookup)) {
                  name = (String)_pluginPropRenames.get(lookup);
               }

               buf.append(name);
               buf.append("=\"").append(propVal).append('"');
            }
         }
      }
   }

   private boolean writeCustomProperties(CustomPlugInBean custom, StringBuffer buf, boolean first) {
      if (custom == null) {
         return first;
      } else {
         PropertiesBean propsBean = custom.getProperties();
         return propsBean == null ? first : this.writePropertiesBean(propsBean, buf, first);
      }
   }

   private boolean writePropertiesBean(PropertiesBean propsBean, StringBuffer buf, boolean first) {
      if (propsBean == null) {
         return first;
      } else {
         PersistenceConfigurationPropertyBean[] props = propsBean.getProperties();
         if (props == null) {
            return first;
         } else {
            for(int i = 0; i < props.length; ++i) {
               if (first) {
                  first = false;
                  buf.append('(');
               } else {
                  buf.append(", ");
               }

               buf.append(props[i].getName());
               buf.append("=\"");
               buf.append(props[i].getValue());
               buf.append("\"");
            }

            return first;
         }
      }
   }

   private static String getAlias(Class type) {
      Plugin plugin = (Plugin)_plugins.get(type);
      if (plugin != null) {
         return plugin.alias;
      } else {
         Class[] ifaces = type.getInterfaces();

         for(int i = 0; i < ifaces.length; ++i) {
            plugin = (Plugin)_plugins.get(ifaces[i]);
            if (plugin != null) {
               return plugin.alias;
            }
         }

         return type.getName();
      }
   }

   private static boolean isSet(PropertyDescriptor pd, Object bean) {
      try {
         String name = StringUtils.capitalize(pd.getName());
         name = "is" + name + "Set";
         Method m = bean.getClass().getMethod(name);
         Boolean b = (Boolean)m.invoke(bean, (Object[])null);
         return b != null && b;
      } catch (Exception var5) {
         return true;
      }
   }

   private static Object getValue(PropertyDescriptor pd, Object bean) {
      try {
         return pd.getReadMethod().invoke(bean, (Object[])null);
      } catch (Exception var3) {
         throw new IllegalArgumentException("failed read:" + pd.getName() + " for type:" + bean.getClass().getName(), var3);
      }
   }

   private static Collection getProperties(Class type) {
      Collection props = new ArrayList();

      try {
         BeanInfo info = Introspector.getBeanInfo(type);
         props.addAll(Arrays.asList(info.getPropertyDescriptors()));
         Class[] ifaces = type.getInterfaces();

         for(int i = 0; i < ifaces.length; ++i) {
            info = Introspector.getBeanInfo(ifaces[i]);
            PropertyDescriptor[] pds = info.getPropertyDescriptors();

            for(int j = 0; j < pds.length; ++j) {
               String name = pds[j].getName();
               if ((!Boolean.TYPE.equals(pds[j].getPropertyType()) || !name.endsWith("Set") && !"Editable".equals(name)) && !props.contains(pds[j])) {
                  props.add(pds[j]);
               }
            }
         }

         return props;
      } catch (Exception var8) {
         throw new IllegalArgumentException("Could not load props for " + type.getName(), var8);
      }
   }

   public static boolean isSimple(Class type) {
      if (type.isPrimitive()) {
         return true;
      } else {
         return Boolean.class.equals(type) || Integer.class.equals(type) || Short.class.equals(type) || Long.class.equals(type) || Float.class.equals(type) || Double.class.equals(type) || Character.class.equals(type) || String.class.equals(type);
      }
   }

   static {
      _propRenames.put("AutoDetaches", "AutoDetach");
      _propRenames.put("DataCaches", "DataCache");
      _propRenames.put("QueryCaches", "QueryCache");
      _propRenames.put("Schematas", "Schema");
      _containers = new HashSet();
      _containers.add("QueryCache");
      _pluginPropRenames = new HashMap();
      _storeProps = new HashSet();
      _storeProps.addAll(Arrays.asList("ConnectionDecorators", "EagerFetchMode", "FetchDirection", "JDBCListeners", "LRSSize", "Mapping", "ResultSetType", "Schema", "Schemas", "SubclassFetchMode", "SynchronizeMappings", "TransactionIsolation"));
      _plugins = new HashMap();
      _plugins.put(DefaultBrokerImplBean.class, new Plugin("kodo", "BrokerImpl", false));
      _plugins.put(KodoBrokerBean.class, new Plugin("kodo", "BrokerImpl", false));
      _plugins.put(DefaultBrokerFactoryBean.class, new Plugin("jdbc", "BrokerFactory", false));
      _plugins.put(AbstractStoreBrokerFactoryBean.class, new Plugin("abstract-store", "BrokerFactory", false));
      _plugins.put(ClientBrokerFactoryBean.class, new Plugin("remote", "BrokerFactory", false));
      _plugins.put(JDBCBrokerFactoryBean.class, new Plugin((String)null, "BrokerFactory", false));
      _plugins.put(DefaultClassResolverBean.class, new Plugin((String)null, "ClassResolver", false));
      _plugins.put(DefaultCompatibilityBean.class, new Plugin("default", "Compatibility", false));
      _plugins.put(KodoCompatibilityBean.class, new Plugin("default", "Compatibility", false));
      _plugins.put(PersistenceCompatibilityBean.class, new Plugin((String)null, "Compatibility", false));
      _plugins.put(DefaultDataCacheBean.class, new Plugin("true", "DataCache", false));
      _plugins.put(GemFireDataCacheBean.class, new Plugin("gemfire", "DataCache", false));
      _plugins.put(KodoConcurrentDataCacheBean.class, new Plugin("true", "KodoConcurrentDataCache", false));
      _plugins.put(LRUDataCacheBean.class, new Plugin("lru", "DataCache", false));
      _plugins.put(TangosolDataCacheBean.class, new Plugin("tangosol", "DataCache", false));
      _plugins.put(DefaultDataCacheManagerBean.class, new Plugin("kodo", "DataCacheManager", false));
      _plugins.put(DataCacheManagerImplBean.class, new Plugin("default", "DataCacheManagerImpl", false));
      _plugins.put(KodoDataCacheManagerBean.class, new Plugin("kodo", "KodoDataCacheManager", false));
      _plugins.put(DefaultDetachStateBean.class, new Plugin("loaded", "DetachState", false));
      _plugins.put(DetachOptionsAllBean.class, new Plugin("all", "DetachState", false));
      _plugins.put(DetachOptionsFetchGroupsBean.class, new Plugin("fgs", "DetachState", false));
      _plugins.put(DetachOptionsLoadedBean.class, new Plugin("loaded", "DetachState", false));
      _plugins.put(DefaultLockManagerBean.class, new Plugin((String)null, "LockManager", false));
      _plugins.put(NoneLockManagerBean.class, new Plugin("none", "LockManager", false));
      _plugins.put(SingleJVMExclusiveLockManagerBean.class, new Plugin("sjvm", "LockManager", false));
      _plugins.put(VersionLockManagerBean.class, new Plugin("version", "LockManager", false));
      _plugins.put(PessimisticLockManagerBean.class, new Plugin((String)null, "LockManager", false));
      _plugins.put(CommonsLogFactoryBean.class, new Plugin("commons", "Log", false));
      _plugins.put(Log4JLogFactoryBean.class, new Plugin("log4j", "Log", false));
      _plugins.put(LogFactoryImplBean.class, new Plugin("true", "Log", false));
      _plugins.put(NoneLogFactoryBean.class, new Plugin("none", "Log", false));
      _plugins.put(DefaultMetaDataFactoryBean.class, new Plugin((String)null, "MetaDataFactory", false));
      _plugins.put(DeprecatedJDOMetaDataFactoryBean.class, new Plugin("kodo3", "MetaDataFactory", false));
      _plugins.put(JDOMetaDataFactoryBean.class, new Plugin("jdo", "MetaDataFactory", false));
      _plugins.put(KodoPersistenceMetaDataFactoryBean.class, new Plugin("ejb", "MetaDataFactory", false));
      _plugins.put(DefaultMetaDataRepositoryBean.class, new Plugin((String)null, "MetaDataRepository", false));
      _plugins.put(DefaultOrphanedKeyActionBean.class, new Plugin("log", "OrphanedKeyAction", false));
      _plugins.put(ExceptionOrphanedKeyActionBean.class, new Plugin("exception", "OrphanedKeyAction", false));
      _plugins.put(LogOrphanedKeyActionBean.class, new Plugin("log", "OrphanedKeyAction", false));
      _plugins.put(NoneOrphanedKeyActionBean.class, new Plugin("none", "OrphanedKeyAction", false));
      _plugins.put(HTTPTransportBean.class, new Plugin("http", "PersistenceServer", false));
      _plugins.put(TCPTransportBean.class, new Plugin("tcp", "PersistenceServer", false));
      _plugins.put(DefaultProxyManagerBean.class, new Plugin("profiling", "ProxyManager", false));
      _plugins.put(ProfilingProxyManagerBean.class, new Plugin("profiling", "ProfilingProxyManager", false));
      _plugins.put(ProxyManagerImplBean.class, new Plugin("default", "ProxyManagerImpl", false));
      _plugins.put(DefaultQueryCacheBean.class, new Plugin("true", "QueryCache", false));
      _plugins.put(DisabledQueryCacheBean.class, new Plugin("false", "QueryCache", false));
      _plugins.put(GemFireQueryCacheBean.class, new Plugin("gemfire", "QueryCache", false));
      _plugins.put(KodoConcurrentQueryCacheBean.class, new Plugin("true", "KodoConcurrentQueryCache", false));
      _plugins.put(LRUQueryCacheBean.class, new Plugin("lru", "QueryCache", false));
      _plugins.put(TangosolQueryCacheBean.class, new Plugin("tangosol", "QueryCache", false));
      _plugins.put(DefaultQueryCompilationCacheBean.class, new Plugin("true", "QueryCompilationCache", false));
      _plugins.put(JMSRemoteCommitProviderBean.class, new Plugin("jms", "RemoteCommitProvider", false));
      _plugins.put(SingleJVMRemoteCommitProviderBean.class, new Plugin("sjvm", "RemoteCommitProvider", false));
      _plugins.put(TCPRemoteCommitProviderBean.class, new Plugin("tcp", "RemoteCommitProvider", false));
      _plugins.put(ClusterRemoteCommitProviderBean.class, new Plugin("wls-cluster", "RemoteCommitProvider", false));
      _plugins.put(DefaultSavepointManagerBean.class, new Plugin("in-mem", "SavepointManager", false));
      _plugins.put(InMemorySavepointManagerBean.class, new Plugin("in-mem", "SavepointManager", false));
      _plugins.put(JDBC3SavepointManagerBean.class, new Plugin((String)null, "SavepointManager", false));
      _plugins.put(OracleSavepointManagerBean.class, new Plugin((String)null, "SavepointManager", false));
      _plugins.put(TimeSeededSeqBean.class, new Plugin("time", "Sequence", false));
      _plugins.put(ClassTableJDBCSeqBean.class, new Plugin((String)null, "Sequence", false));
      _plugins.put(NativeJDBCSeqBean.class, new Plugin((String)null, "Sequence", false));
      _plugins.put(TableJDBCSeqBean.class, new Plugin((String)null, "Sequence", false));
      _plugins.put(ValueTableJDBCSeqBean.class, new Plugin((String)null, "Sequence", false));
      _plugins.put(DBDictionaryBean.class, new Plugin((String)null, "DBDictionary", true));
      _plugins.put(BuiltInDBDictionaryBean.class, new Plugin((String)null, "DBDictionary", true));
      _plugins.put(CustomDictionaryBean.class, new Plugin((String)null, "DBDictionary", true));
      _plugins.put(AccessDictionaryBean.class, new Plugin("access", "DBDictionary", true));
      _plugins.put(DB2DictionaryBean.class, new Plugin("db2", "DBDictionary", true));
      _plugins.put(DerbyDictionaryBean.class, new Plugin("derby", "DBDictionary", true));
      _plugins.put(EmpressDictionaryBean.class, new Plugin("empress", "DBDictionary", true));
      _plugins.put(FoxProDictionaryBean.class, new Plugin("foxpro", "DBDictionary", true));
      _plugins.put(HSQLDictionaryBean.class, new Plugin("hsql", "DBDictionary", true));
      _plugins.put(InformixDictionaryBean.class, new Plugin("informix", "DBDictionary", true));
      _plugins.put(JDataStoreDictionaryBean.class, new Plugin("jdatastore", "DBDictionary", true));
      _plugins.put(MySQLDictionaryBean.class, new Plugin("mysql", "DBDictionary", true));
      _plugins.put(OracleDictionaryBean.class, new Plugin("oracle", "DBDictionary", true));
      _plugins.put(PointbaseDictionaryBean.class, new Plugin("pointbase", "DBDictionary", true));
      _plugins.put(PostgresDictionaryBean.class, new Plugin("postgres", "DBDictionary", true));
      _plugins.put(SQLServerDictionaryBean.class, new Plugin("sqlserver", "DBDictionary", true));
      _plugins.put(SybaseDictionaryBean.class, new Plugin("sybase", "DBDictionary", true));
      _plugins.put(DefaultDriverDataSourceBean.class, new Plugin((String)null, "DriverDataSource", true));
      _plugins.put(KodoPoolingDataSourceBean.class, new Plugin("kodo", "DriverDataSource", true));
      _plugins.put(SimpleDriverDataSourceBean.class, new Plugin("simple", "DriverDataSource", true));
      _plugins.put(DefaultMappingDefaultsBean.class, new Plugin((String)null, "MappingDefaults", true));
      _plugins.put(DeprecatedJDOMappingDefaultsBean.class, new Plugin("kodo3", "MappingDefaults", true));
      _plugins.put(MappingDefaultsImplBean.class, new Plugin("default", "MappingDefaults", true));
      _plugins.put(PersistenceMappingDefaultsBean.class, new Plugin("ejb", "MappingDefaults", true));
      _plugins.put(ExtensionDeprecatedJDOMappingFactoryBean.class, new Plugin("metadata", "MappingFactory", true));
      _plugins.put(JDORMappingFactoryBean.class, new Plugin("jdo", "MappingFactory", true));
      _plugins.put(KodoPersistenceMappingFactoryBean.class, new Plugin("ejb", "MappingFactory", true));
      _plugins.put(MappingFileDeprecatedJDOMappingFactoryBean.class, new Plugin("file", "MappingFactory", true));
      _plugins.put(ORMFileJDORMappingFactoryBean.class, new Plugin("jdo-orm", "MappingFactory", true));
      _plugins.put(TableDeprecatedJDOMappingFactoryBean.class, new Plugin("db", "MappingFactory", true));
      _plugins.put(TableJDORMappingFactoryBean.class, new Plugin("jdo-table", "MappingFactory", true));
      _plugins.put(KodoMappingRepositoryBean.class, new Plugin("kodo", "MappingRepository", true));
      _plugins.put(DefaultSchemaFactoryBean.class, new Plugin((String)null, "SchemaFactory", true));
      _plugins.put(FileSchemaFactoryBean.class, new Plugin("file", "SchemaFactory", true));
      _plugins.put(DynamicSchemaFactoryBean.class, new Plugin("dynamic", "SchemaFactory", true));
      _plugins.put(LazySchemaFactoryBean.class, new Plugin("lazy", "SchemaFactory", true));
      _plugins.put(TableSchemaFactoryBean.class, new Plugin("table", "TableSchemaFactory", true));
      _plugins.put(DefaultSQLFactoryBean.class, new Plugin((String)null, "SQLFactory", true));
      _plugins.put(KodoSQLFactoryBean.class, new Plugin("kodo", "SQLFactory", true));
      _plugins.put(BatchingOperationOrderUpdateManagerBean.class, new Plugin("batching-operation-order", "UpdateManager", true));
      _plugins.put(ConstraintUpdateManagerBean.class, new Plugin("constraint", "UpdateManager", true));
      _plugins.put(DefaultUpdateManagerBean.class, new Plugin((String)null, "UpdateManager", true));
      _plugins.put(OperationOrderUpdateManagerBean.class, new Plugin("operation-order", "UpdateManager", true));
      _plugins.put(TableLockUpdateManagerBean.class, new Plugin("table-lock", "UpdateManager", true));
      _props = initializeList();
   }

   private static class Property {
      public String simpleProperty;
      public String property;
      public final Class type;
      public final PropertyDescriptor pd;
      public final boolean simple;
      public final boolean container;

      public Property(PropertyDescriptor pd) {
         this.pd = pd;
         this.type = pd.getPropertyType();
         this.simple = JDBCConfigurationBeanParser.isSimple(this.type);
         boolean store = false;
         this.simpleProperty = StringUtils.capitalize(pd.getName());
         Plugin plugin = (Plugin)JDBCConfigurationBeanParser._plugins.get(this.type);
         if (plugin != null) {
            this.simpleProperty = plugin.property;
            store = plugin.store;
         }

         if (JDBCConfigurationBeanParser._propRenames.containsKey(this.simpleProperty)) {
            this.simpleProperty = (String)JDBCConfigurationBeanParser._propRenames.get(this.simpleProperty);
         }

         if (JDBCConfigurationBeanParser._storeProps.contains(this.simpleProperty)) {
            store = true;
         }

         if (store) {
            this.property = "kodo.jdbc." + this.simpleProperty;
         } else {
            this.property = "kodo." + this.simpleProperty;
         }

         this.container = JDBCConfigurationBeanParser._containers.contains(this.simpleProperty);
      }
   }

   private static class Plugin {
      public final String alias;
      public final String property;
      public final boolean store;

      public Plugin(String alias, String property, boolean store) {
         this.alias = alias;
         this.property = property;
         this.store = store;
      }
   }
}
