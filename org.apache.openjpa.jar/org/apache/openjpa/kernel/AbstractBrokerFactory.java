package org.apache.openjpa.kernel;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import org.apache.commons.collections.set.MapBackedSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.BrokerValue;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.datacache.DataCacheStoreManager;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.enhance.ManagedClassSubclasser;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.event.BrokerFactoryEvent;
import org.apache.openjpa.event.RemoteCommitEventManager;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashSet;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public abstract class AbstractBrokerFactory implements BrokerFactory {
   private static final Localizer _loc = Localizer.forPackage(AbstractBrokerFactory.class);
   private static final Map _pool = Collections.synchronizedMap(new HashMap());
   private final OpenJPAConfiguration _conf;
   private transient boolean _readOnly = false;
   private transient boolean _closed = false;
   private transient RuntimeException _closedException = null;
   private Map _userObjects = null;
   private final ReentrantLock _lock = new ReentrantLock();
   private transient ConcurrentHashMap _transactional = new ConcurrentHashMap();
   private transient Set _brokers;
   private transient Collection _pcClassNames = null;
   private transient Collection _pcClassLoaders = null;
   private transient boolean _persistentTypesLoaded = false;
   private transient Map _lifecycleListeners = null;
   private transient List _transactionListeners = null;
   private Object _poolKey;

   protected static Object toPoolKey(Map map) {
      Object key = Configurations.getProperty("Id", map);
      return key != null ? key : map;
   }

   protected static void pool(Object key, AbstractBrokerFactory factory) {
      synchronized(_pool) {
         _pool.put(key, factory);
         factory.setPoolKey(key);
         factory.makeReadOnly();
      }
   }

   public static AbstractBrokerFactory getPooledFactoryForKey(Object key) {
      return (AbstractBrokerFactory)_pool.get(key);
   }

   protected AbstractBrokerFactory(OpenJPAConfiguration config) {
      this._conf = config;
      this._brokers = this.newBrokerSet();
      this.getPcClassLoaders();
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public Broker newBroker() {
      return this.newBroker(this._conf.getConnectionUserName(), this._conf.getConnectionPassword());
   }

   public Broker newBroker(String user, String pass) {
      return this.newBroker(user, pass, this._conf.isTransactionModeManaged(), this._conf.getConnectionRetainModeConstant());
   }

   public Broker newBroker(boolean managed, int connRetainMode) {
      return this.newBroker(this._conf.getConnectionUserName(), this._conf.getConnectionPassword(), managed, connRetainMode);
   }

   public Broker newBroker(String user, String pass, boolean managed, int connRetainMode) {
      return this.newBroker(user, pass, managed, connRetainMode, true);
   }

   public Broker newBroker(String user, String pass, boolean managed, int connRetainMode, boolean findExisting) {
      try {
         this.assertOpen();
         this.makeReadOnly();
         BrokerImpl broker = null;
         if (findExisting) {
            broker = this.findBroker(user, pass, managed);
         }

         if (broker == null) {
            broker = this.newBrokerImpl(user, pass);
            this.initializeBroker(managed, connRetainMode, broker, false);
         }

         return broker;
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (RuntimeException var8) {
         throw new GeneralException(var8);
      }
   }

   void initializeBroker(boolean managed, int connRetainMode, BrokerImpl broker, boolean fromDeserialization) {
      this.assertOpen();
      this.makeReadOnly();
      StoreManager sm = this.newStoreManager();
      DelegatingStoreManager dsm = null;
      if (this._conf.getDataCacheManagerInstance().getSystemDataCache() != null) {
         dsm = new DataCacheStoreManager(sm);
      }

      DelegatingStoreManager dsm = new ROPStoreManager((StoreManager)(dsm == null ? sm : dsm));
      broker.initialize(this, dsm, managed, connRetainMode, fromDeserialization);
      if (!fromDeserialization) {
         this.addListeners(broker);
      }

      RemoteCommitEventManager remote = this._conf.getRemoteCommitEventManager();
      if (remote.areRemoteEventsEnabled()) {
         broker.addTransactionListener(remote);
      }

      this.loadPersistentTypes(broker.getClassLoader());
      this._brokers.add(broker);
      this._conf.setReadOnly(2);
   }

   protected void addListeners(BrokerImpl broker) {
      if (this._lifecycleListeners != null && !this._lifecycleListeners.isEmpty()) {
         Iterator itr = this._lifecycleListeners.entrySet().iterator();

         while(itr.hasNext()) {
            Map.Entry entry = (Map.Entry)itr.next();
            broker.addLifecycleListener(entry.getKey(), (Class[])((Class[])entry.getValue()));
         }
      }

      if (this._transactionListeners != null && !this._transactionListeners.isEmpty()) {
         Iterator itr = this._transactionListeners.iterator();

         while(itr.hasNext()) {
            broker.addTransactionListener(itr.next());
         }
      }

   }

   private void loadPersistentTypes(ClassLoader envLoader) {
      if (!this._persistentTypesLoaded || !this._pcClassNames.isEmpty()) {
         ClassLoader loader = this._conf.getClassResolverInstance().getClassLoader(this.getClass(), envLoader);
         Collection toRedefine = new ArrayList();
         if (!this._persistentTypesLoaded) {
            Collection clss = this._conf.getMetaDataRepositoryInstance().loadPersistentTypes(false, loader);
            if (clss.isEmpty()) {
               this._pcClassNames = Collections.EMPTY_SET;
            } else {
               Collection c = new ArrayList(clss.size());
               Iterator itr = clss.iterator();

               while(itr.hasNext()) {
                  Class cls = (Class)itr.next();
                  c.add(cls.getName());
                  if (this.needsSub(cls)) {
                     toRedefine.add(cls);
                  }
               }

               this.getPcClassLoaders().add(loader);
               this._pcClassNames = c;
            }

            this._persistentTypesLoaded = true;
         } else if (this.getPcClassLoaders().add(loader)) {
            Iterator itr = this._pcClassNames.iterator();

            while(itr.hasNext()) {
               try {
                  Class cls = Class.forName((String)itr.next(), true, loader);
                  if (this.needsSub(cls)) {
                     toRedefine.add(cls);
                  }
               } catch (Throwable var8) {
                  this._conf.getLog("openjpa.Runtime").warn((Object)null, var8);
               }
            }
         }

         ManagedClassSubclasser.prepareUnenhancedClasses(this._conf, toRedefine, envLoader);
      }
   }

   private boolean needsSub(Class cls) {
      return !cls.isInterface() && !PersistenceCapable.class.isAssignableFrom(cls);
   }

   public void addLifecycleListener(Object listener, Class[] classes) {
      this.lock();

      try {
         this.assertOpen();
         if (this._lifecycleListeners == null) {
            this._lifecycleListeners = new HashMap(7);
         }

         this._lifecycleListeners.put(listener, classes);
      } finally {
         this.unlock();
      }

   }

   public void removeLifecycleListener(Object listener) {
      this.lock();

      try {
         this.assertOpen();
         if (this._lifecycleListeners != null) {
            this._lifecycleListeners.remove(listener);
         }
      } finally {
         this.unlock();
      }

   }

   public void addTransactionListener(Object listener) {
      this.lock();

      try {
         this.assertOpen();
         if (this._transactionListeners == null) {
            this._transactionListeners = new LinkedList();
         }

         this._transactionListeners.add(listener);
      } finally {
         this.unlock();
      }

   }

   public void removeTransactionListener(Object listener) {
      this.lock();

      try {
         this.assertOpen();
         if (this._transactionListeners != null) {
            this._transactionListeners.remove(listener);
         }
      } finally {
         this.unlock();
      }

   }

   public boolean isClosed() {
      return this._closed;
   }

   public void close() {
      this.lock();

      try {
         this.assertOpen();
         this.assertNoActiveTransaction();
         synchronized(_pool) {
            if (_pool.get(this._poolKey) == this) {
               _pool.remove(this._poolKey);
            }
         }

         Iterator itr = this._brokers.iterator();

         while(true) {
            if (!itr.hasNext()) {
               if (this._conf.metaDataRepositoryAvailable()) {
                  PCRegistry.removeRegisterClassListener(this._conf.getMetaDataRepositoryInstance());
               }

               this._conf.close();
               this._closed = true;
               Log log = this._conf.getLog("openjpa.Runtime");
               if (log.isTraceEnabled()) {
                  this._closedException = new IllegalStateException();
               }
               break;
            }

            Broker broker = (Broker)itr.next();
            if (broker != null && !broker.isClosed()) {
               broker.close();
            }
         }
      } finally {
         this.unlock();
      }

   }

   public Properties getProperties() {
      Properties props = new Properties();
      props.setProperty("VendorName", "OpenJPA");
      props.setProperty("VersionNumber", OpenJPAVersion.VERSION_NUMBER);
      props.setProperty("VersionId", OpenJPAVersion.VERSION_ID);
      return props;
   }

   public Object getUserObject(Object key) {
      this.lock();

      Object var2;
      try {
         this.assertOpen();
         var2 = this._userObjects == null ? null : this._userObjects.get(key);
      } finally {
         this.unlock();
      }

      return var2;
   }

   public Object putUserObject(Object key, Object val) {
      this.lock();

      Object var3;
      try {
         this.assertOpen();
         if (val != null) {
            if (this._userObjects == null) {
               this._userObjects = new HashMap();
            }

            var3 = this._userObjects.put(key, val);
            return var3;
         }

         var3 = this._userObjects == null ? null : this._userObjects.remove(key);
      } finally {
         this.unlock();
      }

      return var3;
   }

   public void lock() {
      this._lock.lock();
   }

   public void unlock() {
      this._lock.unlock();
   }

   protected Object readResolve() throws ObjectStreamException {
      AbstractBrokerFactory factory = getPooledFactoryForKey(this._poolKey);
      if (factory != null) {
         return factory;
      } else {
         this._transactional = new ConcurrentHashMap();
         this._brokers = this.newBrokerSet();
         String saveLogConfig = this._conf.getLog();
         this._conf.setLog("none");
         this.makeReadOnly();
         this._conf.setLog(saveLogConfig);
         return this;
      }
   }

   private Set newBrokerSet() {
      BrokerValue bv;
      if (this._conf instanceof OpenJPAConfigurationImpl) {
         bv = ((OpenJPAConfigurationImpl)this._conf).brokerPlugin;
      } else {
         bv = (BrokerValue)this._conf.getValue("BrokerImpl");
      }

      return (Set)(FinalizingBrokerImpl.class.isAssignableFrom(bv.getTemplateBrokerType(this._conf)) ? MapBackedSet.decorate(new ConcurrentHashMap(), new Object() {
      }) : new ConcurrentReferenceHashSet(2));
   }

   protected abstract StoreManager newStoreManager();

   protected BrokerImpl findBroker(String user, String pass, boolean managed) {
      return managed ? this.findTransactionalBroker(user, pass) : null;
   }

   protected BrokerImpl newBrokerImpl(String user, String pass) {
      BrokerImpl broker = this._conf.newBrokerInstance(user, pass);
      if (broker == null) {
         throw new UserException(_loc.get("no-broker-class", (Object)this._conf.getBrokerImpl()));
      } else {
         return broker;
      }
   }

   protected void setup() {
   }

   protected BrokerImpl findTransactionalBroker(String user, String pass) {
      ManagedRuntime mr = this._conf.getManagedRuntimeInstance();

      Object txKey;
      try {
         Transaction trans = mr.getTransactionManager().getTransaction();
         txKey = mr.getTransactionKey();
         if (trans == null || trans.getStatus() == 6 || trans.getStatus() == 5) {
            return null;
         }
      } catch (OpenJPAException var9) {
         throw var9;
      } catch (Exception var10) {
         throw new GeneralException(var10);
      }

      Collection brokers = (Collection)this._transactional.get(txKey);
      if (brokers != null) {
         Iterator itr = brokers.iterator();

         while(itr.hasNext()) {
            BrokerImpl broker = (BrokerImpl)itr.next();
            if (StringUtils.equals(broker.getConnectionUserName(), user) && StringUtils.equals(broker.getConnectionPassword(), pass)) {
               return broker;
            }
         }
      }

      return null;
   }

   protected void configureBroker(BrokerImpl broker) {
      broker.setOptimistic(this._conf.getOptimistic());
      broker.setNontransactionalRead(this._conf.getNontransactionalRead());
      broker.setNontransactionalWrite(this._conf.getNontransactionalWrite());
      broker.setRetainState(this._conf.getRetainState());
      broker.setRestoreState(this._conf.getRestoreStateConstant());
      broker.setAutoClear(this._conf.getAutoClearConstant());
      broker.setIgnoreChanges(this._conf.getIgnoreChanges());
      broker.setMultithreaded(this._conf.getMultithreaded());
      broker.setAutoDetach(this._conf.getAutoDetachConstant());
      broker.setDetachState(this._conf.getDetachStateInstance().getDetachState());
   }

   public void makeReadOnly() {
      if (!this._readOnly) {
         this.lock();

         try {
            if (this._readOnly) {
               return;
            }

            this._readOnly = true;
            Log log = this._conf.getLog("openjpa.Runtime");
            if (log.isInfoEnabled()) {
               log.info(this.getFactoryInitializationBanner());
            }

            if (log.isTraceEnabled()) {
               Map props = this._conf.toProperties(true);
               String lineSep = J2DoPrivHelper.getLineSeparator();
               StringBuffer buf = new StringBuffer();
               Iterator itr = props.entrySet().iterator();

               while(itr.hasNext()) {
                  Map.Entry entry = (Map.Entry)itr.next();
                  buf.append(entry.getKey()).append(": ").append(entry.getValue());
                  if (itr.hasNext()) {
                     buf.append(lineSep);
                  }
               }

               log.trace(_loc.get("factory-properties", (Object)buf.toString()));
            }

            this.setup();
            MetaDataRepository repos = this._conf.getMetaDataRepositoryInstance();
            repos.setValidate(8, true);
            repos.setResolve(8, true);
            PCRegistry.addRegisterClassListener(repos);
            this._conf.setReadOnly(1);
            this._conf.instantiateAll();
            this._conf.getBrokerFactoryEventManager().fireEvent(new BrokerFactoryEvent(this, 0));
         } finally {
            this.unlock();
         }

      }
   }

   protected Object getFactoryInitializationBanner() {
      return _loc.get("factory-init", (Object)OpenJPAVersion.VERSION_NUMBER);
   }

   private void assertOpen() {
      if (this._closed) {
         if (this._closedException == null) {
            throw new InvalidStateException(_loc.get("closed-factory-notrace"));
         } else {
            throw (new InvalidStateException(_loc.get("closed-factory"))).setCause(this._closedException);
         }
      }
   }

   private void assertNoActiveTransaction() {
      if (!this._transactional.isEmpty()) {
         Collection excs = new ArrayList(this._transactional.size());
         Iterator trans = this._transactional.values().iterator();

         while(trans.hasNext()) {
            Collection brokers = (Collection)trans.next();
            Iterator itr = brokers.iterator();

            while(itr.hasNext()) {
               excs.add((new InvalidStateException(_loc.get("active"))).setFailedObject(itr.next()));
            }
         }

         if (!excs.isEmpty()) {
            throw (new InvalidStateException(_loc.get("nested-exceps"))).setNestedThrowables((Throwable[])((Throwable[])excs.toArray(new Throwable[excs.size()])));
         }
      }
   }

   boolean syncWithManagedTransaction(BrokerImpl broker, boolean begin) {
      try {
         ManagedRuntime mr = broker.getManagedRuntime();
         TransactionManager tm = mr.getTransactionManager();
         Transaction trans = tm.getTransaction();
         if (trans != null && (trans.getStatus() == 6 || trans.getStatus() == 5)) {
            trans = null;
         }

         if (trans == null && begin) {
            tm.begin();
            trans = tm.getTransaction();
         } else if (trans == null) {
            return false;
         }

         trans.registerSynchronization(broker);
         Object txKey = mr.getTransactionKey();
         Collection brokers = (Collection)this._transactional.get(txKey);
         if (brokers == null) {
            brokers = new ArrayList(2);
            this._transactional.put(txKey, brokers);
            trans.registerSynchronization(new RemoveTransactionSync(txKey));
         }

         ((Collection)brokers).add(broker);
         return true;
      } catch (OpenJPAException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new GeneralException(var9);
      }
   }

   public Collection getOpenBrokers() {
      return Collections.unmodifiableCollection(this._brokers);
   }

   protected void releaseBroker(BrokerImpl broker) {
      this._brokers.remove(broker);
   }

   public Object getPoolKey() {
      return this._poolKey;
   }

   void setPoolKey(Object key) {
      this._poolKey = key;
   }

   private Collection getPcClassLoaders() {
      if (this._pcClassLoaders == null) {
         this._pcClassLoaders = new ConcurrentReferenceHashSet(2);
      }

      return this._pcClassLoaders;
   }

   private class RemoveTransactionSync implements Synchronization {
      private final Object _trans;

      public RemoveTransactionSync(Object trans) {
         this._trans = trans;
      }

      public void beforeCompletion() {
      }

      public void afterCompletion(int status) {
         AbstractBrokerFactory.this._transactional.remove(this._trans);
      }
   }
}
