package kodo.jdo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.jdo.FetchPlan;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import kodo.remote.Remote;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Bootstrap;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.DelegatingBrokerFactory;
import org.apache.openjpa.kernel.DelegatingFetchConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.util.JavaVersions;

public class PersistenceManagerFactoryImpl implements KodoPersistenceManagerFactory, ObjectFactory, Referenceable {
   private final DelegatingBrokerFactory _factory;
   private transient Constructor _conn;
   private transient Constructor _plan;
   private transient Collection _options;
   private transient DataStoreCacheImpl _cache;
   private transient QueryResultCache _queryCache;

   public static PersistenceManagerFactory getPersistenceManagerFactory(Map map) {
      map = JDOProperties.toKodoProperties(map);
      ConfigurationProvider conf = new JDOProductDerivation.ConfigurationProviderImpl(map);
      return KodoJDOHelper.toPersistenceManagerFactory(Bootstrap.getBrokerFactory(conf, (ClassLoader)null));
   }

   public PersistenceManagerFactoryImpl() {
      this(Bootstrap.newBrokerFactory());
   }

   public PersistenceManagerFactoryImpl(BrokerFactory factory) {
      this._conn = null;
      this._plan = null;
      this._options = null;
      this._cache = null;
      this._queryCache = null;
      this._factory = new DelegatingBrokerFactory(factory, JDOExceptions.TRANSLATOR);
   }

   public BrokerFactory getDelegate() {
      return this._factory.getDelegate();
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._factory.getConfiguration();
   }

   public Properties getProperties() {
      return this._factory.getProperties();
   }

   public Object putUserObject(Object key, Object val) {
      return this._factory.putUserObject(key, val);
   }

   public Object getUserObject(Object key) {
      return this._factory.getUserObject(key);
   }

   public DataStoreCache getDataStoreCache() {
      this._factory.lock();

      DataStoreCacheImpl var5;
      try {
         if (this._cache == null) {
            OpenJPAConfiguration conf = this._factory.getConfiguration();
            this._cache = new DataStoreCacheImpl(conf.getDataCacheManagerInstance().getSystemDataCache());
         }

         var5 = this._cache;
      } finally {
         this._factory.unlock();
      }

      return var5;
   }

   public KodoDataStoreCache getDataStoreCache(String cacheName) {
      return new DataStoreCacheImpl(this._factory.getConfiguration().getDataCacheManagerInstance().getDataCache(cacheName));
   }

   public QueryResultCache getQueryResultCache() {
      this._factory.lock();

      QueryResultCache var1;
      try {
         if (this._queryCache == null) {
            this._queryCache = new QueryResultCache(this._factory.getConfiguration().getDataCacheManagerInstance().getSystemQueryCache());
         }

         var1 = this._queryCache;
      } finally {
         this._factory.unlock();
      }

      return var1;
   }

   public PersistenceManager getPersistenceManager() {
      return KodoJDOHelper.toPersistenceManager(this._factory.newBroker());
   }

   public PersistenceManager getPersistenceManager(String user, String pass) {
      OpenJPAConfiguration conf = this.getConfiguration();
      return this.getPersistenceManager(user, pass, conf.isTransactionModeManaged(), conf.getConnectionRetainModeConstant());
   }

   public KodoPersistenceManager getPersistenceManager(boolean managed, int connRetainMode) {
      OpenJPAConfiguration conf = this.getConfiguration();
      return this.getPersistenceManager(conf.getConnectionUserName(), conf.getConnectionPassword(), managed, connRetainMode);
   }

   public KodoPersistenceManager getPersistenceManager(String user, String pass, boolean managed, int connRetainMode) {
      return KodoJDOHelper.toPersistenceManager(this._factory.newBroker(user, pass, managed, connRetainMode, true));
   }

   public boolean startPersistenceServer() {
      return Remote.getInstance(this._factory).startPersistenceServer();
   }

   public boolean joinPersistenceServer() {
      return Remote.getInstance(this._factory).joinPersistenceServer();
   }

   public boolean stopPersistenceServer() {
      return Remote.getInstance(this._factory).stopPersistenceServer();
   }

   public boolean isPersistenceServerRunning() {
      return Remote.getInstance(this._factory).isPersistenceServerRunning();
   }

   public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
      this._factory.addLifecycleListener(new LifecycleListenerAdapter(listener), classes);
   }

   public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
      this._factory.removeLifecycleListener(new LifecycleListenerAdapter(listener));
   }

   public void addTransferListener(RemoteTransferListener listener) {
      Remote.getInstance(this._factory).addTransferListener(listener);
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      Remote.getInstance(this._factory).removeTransferListener(listener);
   }

   public void close() {
      this._factory.close();
   }

   public boolean isClosed() {
      return this._factory.isClosed();
   }

   public int hashCode() {
      return this._factory.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof PersistenceManagerFactoryImpl) ? false : this._factory.equals(((PersistenceManagerFactoryImpl)other)._factory);
      }
   }

   public Collection supportedOptions() {
      if (this._options != null) {
         return this._options;
      } else {
         Collection opts = new HashSet();
         opts.add("javax.jdo.option.TransientTransactional");
         opts.add("javax.jdo.option.NontransactionalWrite");
         opts.add("javax.jdo.option.RetainValues");
         opts.add("javax.jdo.option.UnconstrainedQueryVariables");
         opts.add("javax.jdo.option.PreDirtyEvent");
         opts.add("javax.jdo.query.JDOQL");
         Collection kodoopts = null;

         try {
            kodoopts = this._factory.getConfiguration().supportedOptions();
         } catch (RuntimeException var4) {
            throw JDOExceptions.toJDOException(var4);
         }

         if (kodoopts.contains("openjpa.option.Optimistic")) {
            opts.add("javax.jdo.option.Optimistic");
         }

         if (kodoopts.contains("openjpa.option.NontransactionalRead")) {
            opts.add("javax.jdo.option.NontransactionalRead");
         }

         if (kodoopts.contains("openjpa.option.ApplicationIdentity")) {
            opts.add("javax.jdo.option.ApplicationIdentity");
         }

         if (kodoopts.contains("openjpa.option.DatastoreIdentity")) {
            opts.add("javax.jdo.option.DatastoreIdentity");
         }

         if (kodoopts.contains("openjpa.option.SQL")) {
            opts.add("javax.jdo.query.SQL");
         }

         if (kodoopts.contains("openjpa.option.Collection")) {
            opts.add("javax.jdo.option.ArrayList");
            opts.add("javax.jdo.option.LinkedList");
            opts.add("javax.jdo.option.TreeSet");
            opts.add("javax.jdo.option.Vector");
            opts.add("javax.jdo.option.List");
            opts.add("javax.jdo.option.Array");
         }

         if (kodoopts.contains("openjpa.option.Map")) {
            opts.add("javax.jdo.option.TreeMap");
         }

         if (kodoopts.contains("openjpa.option.NullContainer")) {
            opts.add("javax.jdo.option.NullCollection");
         }

         if (kodoopts.contains("openjpa.option.DataStoreConnection")) {
            opts.add("javax.jdo.option.GetDataStoreConnection");
         }

         if (kodoopts.contains("openjpa.option.JDBCConnection")) {
            opts.add("javax.jdo.option.GetJDBCConnection");
            opts.add("javax.jdo.option.version.DateTime");
            opts.add("javax.jdo.option.version.StateImage");
            opts.add("javax.jdo.option.mapping.HeterogeneousObjectType");
            opts.add("javax.jdo.option.mapping.HeterogeneousInterfaceType");
            opts.add("javax.jdo.option.mapping.JoinedTablePerClass");
            opts.add("javax.jdo.option.mapping.JoinedTablePerConcreteClass");
            opts.add("javax.jdo.option.mapping.NonJoinedTablePerConcreteClass");
            opts.add("javax.jdo.option.mapping.RelationSubclassTable");
         }

         this._options = Collections.unmodifiableCollection(opts);
         return this._options;
      }
   }

   public String getConnectionUserName() {
      try {
         return this._factory.getConfiguration().getConnectionUserName();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionUserName(String val) {
      try {
         this._factory.getConfiguration().setConnectionUserName(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getConnectionPassword() {
      try {
         return this._factory.getConfiguration().getConnectionPassword();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionPassword(String val) {
      try {
         this._factory.getConfiguration().setConnectionPassword(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getConnectionURL() {
      try {
         return this._factory.getConfiguration().getConnectionURL();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionURL(String val) {
      try {
         this._factory.getConfiguration().setConnectionURL(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getConnectionDriverName() {
      try {
         return this._factory.getConfiguration().getConnectionDriverName();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionDriverName(String val) {
      try {
         this._factory.getConfiguration().setConnectionDriverName(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getConnectionFactoryName() {
      try {
         return this._factory.getConfiguration().getConnectionFactoryName();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionFactoryName(String val) {
      try {
         this._factory.getConfiguration().setConnectionFactoryName(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public Object getConnectionFactory() {
      try {
         return this._factory.getConfiguration().getConnectionFactory();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionFactory(Object val) {
      try {
         this._factory.getConfiguration().setConnectionFactory(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getConnectionFactory2Name() {
      try {
         return this._factory.getConfiguration().getConnectionFactory2Name();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionFactory2Name(String val) {
      try {
         this._factory.getConfiguration().setConnectionFactory2Name(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public Object getConnectionFactory2() {
      try {
         return this._factory.getConfiguration().getConnectionFactory2();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setConnectionFactory2(Object val) {
      try {
         this._factory.getConfiguration().setConnectionFactory2(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getOptimistic() {
      try {
         return this._factory.getConfiguration().getOptimistic();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setOptimistic(boolean val) {
      try {
         this._factory.getConfiguration().setOptimistic(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getRetainValues() {
      try {
         return this._factory.getConfiguration().getRetainState();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setRetainValues(boolean val) {
      try {
         this._factory.getConfiguration().setRetainState(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getRestoreValues() {
      try {
         return this._factory.getConfiguration().getRestoreStateConstant() != 0;
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setRestoreValues(boolean val) {
      try {
         int restore = val ? 1 : 0;
         this._factory.getConfiguration().setRestoreState(restore);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getNontransactionalRead() {
      try {
         return this._factory.getConfiguration().getNontransactionalRead();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setNontransactionalRead(boolean val) {
      try {
         this._factory.getConfiguration().setNontransactionalRead(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getNontransactionalWrite() {
      try {
         return this._factory.getConfiguration().getNontransactionalWrite();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setNontransactionalWrite(boolean val) {
      try {
         this._factory.getConfiguration().setNontransactionalWrite(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getIgnoreCache() {
      try {
         return this._factory.getConfiguration().getIgnoreChanges();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setIgnoreCache(boolean val) {
      try {
         this._factory.getConfiguration().setIgnoreChanges(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getMultithreaded() {
      try {
         return this._factory.getConfiguration().getMultithreaded();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setMultithreaded(boolean val) {
      try {
         this._factory.getConfiguration().setMultithreaded(val);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public String getMapping() {
      try {
         return this._factory.getConfiguration().getMapping();
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setMapping(String mapping) {
      try {
         this._factory.getConfiguration().setMapping(mapping);
      } catch (IllegalStateException var3) {
         throw this.toUserException(var3);
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public boolean getDetachAllOnCommit() {
      try {
         return (this._factory.getConfiguration().getAutoDetachConstant() & 4) != 0;
      } catch (RuntimeException var2) {
         throw JDOExceptions.toJDOException(var2);
      }
   }

   public void setDetachAllOnCommit(boolean val) {
      try {
         OpenJPAConfiguration conf = this._factory.getConfiguration();
         int flags = conf.getAutoDetachConstant();
         this._factory.getConfiguration().setAutoDetach(flags | 4);
      } catch (IllegalStateException var4) {
         throw this.toUserException(var4);
      } catch (RuntimeException var5) {
         throw JDOExceptions.toJDOException(var5);
      }
   }

   private UserException toUserException(IllegalStateException ise) {
      UserException ue;
      if (JavaVersions.VERSION >= 4) {
         ue = new UserException(ise.getMessage(), (Throwable[])null, (Object)null);
         JavaVersions.transferStackTrace(ise, ue);
      } else {
         ue = new UserException(ise.getMessage(), new Throwable[]{ise}, (Object)null);
      }

      return ue;
   }

   JDOConnection toJDOConnection(Broker broker, Object brokerConnection) {
      if (brokerConnection == null) {
         return null;
      } else {
         try {
            if (this._conn == null) {
               Class storeType = broker == null ? null : broker.getStoreManager().getInnermostDelegate().getClass();
               Class cls = this._factory.getConfiguration().getStoreFacadeTypeRegistry().getImplementation(JDOConnection.class, storeType);
               if (cls == null) {
                  cls = JDOConnectionImpl.class;
               }

               this._conn = cls.getConstructor(Object.class);
            }

            return (JDOConnection)this._conn.newInstance(brokerConnection);
         } catch (InvocationTargetException var5) {
            throw JDOExceptions.toJDOException(var5.getTargetException());
         } catch (Exception var6) {
            throw JDOExceptions.toJDOException(var6);
         }
      }
   }

   KodoFetchPlan toFetchPlan(Broker broker, FetchConfiguration fetch) {
      if (fetch == null) {
         return null;
      } else {
         if (fetch instanceof DelegatingFetchConfiguration) {
            FetchConfiguration inner = ((DelegatingFetchConfiguration)fetch).getInnermostDelegate();
         }

         try {
            if (this._plan == null) {
               Class storeType = broker == null ? null : broker.getStoreManager().getInnermostDelegate().getClass();
               Class cls = this._factory.getConfiguration().getStoreFacadeTypeRegistry().getImplementation(FetchPlan.class, storeType);
               if (cls == null) {
                  cls = FetchPlanImpl.class;
               }

               this._plan = cls.getConstructor(FetchConfiguration.class);
            }

            return (KodoFetchPlan)this._plan.newInstance(fetch);
         } catch (InvocationTargetException var6) {
            throw JDOExceptions.toJDOException(var6.getTargetException());
         } catch (Exception var7) {
            throw JDOExceptions.toJDOException(var7);
         }
      }
   }

   public Reference getReference() {
      Reference ref = new Reference(this.getClass().getName(), this.getClass().getName(), (String)null);
      Iterator i = this.getProperties().entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry entry = (Map.Entry)i.next();
         ref.add(new StringRefAddr((String)entry.getKey(), (String)entry.getValue()));
      }

      return ref;
   }

   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws Exception {
      if (!(obj instanceof Reference)) {
         return null;
      } else {
         Reference ref = (Reference)obj;
         Properties props = new Properties();
         Enumeration e = ref.getAll();

         while(e.hasMoreElements()) {
            Object next = e.nextElement();
            if (next instanceof StringRefAddr) {
               props.setProperty(((StringRefAddr)next).getType(), ((StringRefAddr)next).getContent().toString());
            }
         }

         return getPersistenceManagerFactory(props);
      }
   }
}
