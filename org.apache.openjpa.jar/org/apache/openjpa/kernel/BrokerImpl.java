package org.apache.openjpa.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import org.apache.commons.collections.iterators.IteratorChain;
import org.apache.commons.collections.map.IdentityMap;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.collections.set.MapBackedSet;
import org.apache.openjpa.conf.Compatibility;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCache;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.event.RemoteCommitEventManager;
import org.apache.openjpa.event.TransactionEvent;
import org.apache.openjpa.event.TransactionEventManager;
import org.apache.openjpa.kernel.exps.ExpressionParser;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.ReferenceHashMap;
import org.apache.openjpa.lib.util.ReferenceHashSet;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.MetaDataRepository;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.CallbackException;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.InvalidStateException;
import org.apache.openjpa.util.NoTransactionException;
import org.apache.openjpa.util.ObjectExistsException;
import org.apache.openjpa.util.ObjectId;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.OptimisticException;
import org.apache.openjpa.util.RuntimeExceptionTranslator;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;
import org.apache.openjpa.util.UserException;

public class BrokerImpl implements Broker, FindCallbacks, Cloneable, Serializable {
   protected static final int FLUSH_INC = 0;
   protected static final int FLUSH_COMMIT = 1;
   protected static final int FLUSH_ROLLBACK = 2;
   protected static final int FLUSH_LOGICAL = 3;
   static final int STATUS_INIT = 0;
   static final int STATUS_TRANSIENT = 1;
   static final int STATUS_OID_ASSIGN = 2;
   static final int STATUS_COMMIT_NEW = 3;
   private static final int FLAG_ACTIVE = 2;
   private static final int FLAG_STORE_ACTIVE = 4;
   private static final int FLAG_CLOSE_INVOKED = 8;
   private static final int FLAG_PRESTORING = 16;
   private static final int FLAG_DEREFDELETING = 32;
   private static final int FLAG_FLUSHING = 64;
   private static final int FLAG_STORE_FLUSHING = 128;
   private static final int FLAG_FLUSHED = 256;
   private static final int FLAG_FLUSH_REQUIRED = 512;
   private static final int FLAG_REMOTE_LISTENER = 1024;
   private static final int FLAG_RETAINED_CONN = 2048;
   private static final int FLAG_TRANS_ENDING = 4096;
   private static final Object[] EMPTY_OBJECTS = new Object[0];
   private static final Localizer _loc = Localizer.forPackage(BrokerImpl.class);
   private transient DelegatingStoreManager _store = null;
   private FetchConfiguration _fc = null;
   private String _user = null;
   private String _pass = null;
   private transient Log _log = null;
   private transient Compatibility _compat = null;
   private transient ManagedRuntime _runtime = null;
   private transient LockManager _lm = null;
   private transient InverseManager _im = null;
   private transient ReentrantLock _lock = null;
   private transient OpCallbacks _call = null;
   private transient RuntimeExceptionTranslator _extrans = null;
   private transient AbstractBrokerFactory _factory = null;
   private transient OpenJPAConfiguration _conf = null;
   private transient ClassLoader _loader = null;
   private Synchronization _sync = null;
   private Map _userObjects = null;
   private ManagedCache _cache = null;
   private TransactionalCache _transCache = null;
   private Set _transAdditions = null;
   private Set _derefCache = null;
   private Set _derefAdditions = null;
   private transient Map _loading = null;
   private transient Set _operating = null;
   private Set _persistedClss = null;
   private Set _updatedClss = null;
   private Set _deletedClss = null;
   private Set _pending = null;
   private int findAllDepth = 0;
   private Set _savepointCache = null;
   private LinkedMap _savepoints = null;
   private transient SavepointManager _spm = null;
   private transient ReferenceHashSet _queries = null;
   private transient ReferenceHashSet _extents = null;
   private transient int _operationCount = 0;
   private boolean _nontransRead = false;
   private boolean _nontransWrite = false;
   private boolean _retainState = false;
   private int _autoClear = 0;
   private int _restoreState = 1;
   private boolean _optimistic = false;
   private boolean _ignoreChanges = false;
   private boolean _multithreaded = false;
   private boolean _managed = false;
   private boolean _syncManaged = false;
   private int _connRetainMode = 0;
   private boolean _evictDataCache = false;
   private boolean _populateDataCache = true;
   private boolean _largeTransaction = false;
   private int _autoDetach = 0;
   private int _detachState = 1;
   private boolean _detachedNew = true;
   private boolean _orderDirty = false;
   private int _flags = 0;
   private transient boolean _isSerializing = false;
   private transient boolean _closed = false;
   private transient RuntimeException _closedException = null;
   private TransactionEventManager _transEventManager = null;
   private int _transCallbackMode = 0;
   private LifecycleEventManager _lifeEventManager = null;
   private int _lifeCallbackMode = 0;
   private transient boolean _initializeWasInvoked = false;
   private LinkedList _fcs;

   public void setAuthentication(String user, String pass) {
      this._user = user;
      this._pass = pass;
   }

   public void initialize(AbstractBrokerFactory factory, DelegatingStoreManager sm, boolean managed, int connMode, boolean fromDeserialization) {
      this._initializeWasInvoked = true;
      this._loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      if (!fromDeserialization) {
         this._conf = factory.getConfiguration();
      }

      this._compat = this._conf.getCompatibilityInstance();
      this._factory = factory;
      this._log = this._conf.getLog("openjpa.Runtime");
      if (!fromDeserialization) {
         this._cache = new ManagedCache(this);
      }

      this.initializeOperatingSet();
      this._connRetainMode = connMode;
      this._managed = managed;
      if (managed) {
         this._runtime = this._conf.getManagedRuntimeInstance();
      } else {
         this._runtime = new LocalManagedRuntime(this);
      }

      if (!fromDeserialization) {
         this._lifeEventManager = new LifecycleEventManager();
         this._transEventManager = new TransactionEventManager();
         int cmode = this._conf.getMetaDataRepositoryInstance().getMetaDataFactory().getDefaults().getCallbackMode();
         this.setLifecycleListenerCallbackMode(cmode);
         this.setTransactionListenerCallbackMode(cmode);
         this._factory.configureBroker(this);
      }

      this._store = sm;
      this._lm = this._conf.newLockManagerInstance();
      this._im = this._conf.newInverseManagerInstance();
      this._spm = this._conf.getSavepointManagerInstance();
      this._store.setContext(this);
      this._lm.setContext(this);
      if (this._connRetainMode == 2) {
         this.retainConnection();
      }

      if (!fromDeserialization) {
         this._fc = this._store.newFetchConfiguration();
         this._fc.setContext(this);
      }

      if (this._factory.syncWithManagedTransaction(this, false)) {
         this.beginInternal();
      }

   }

   private void initializeOperatingSet() {
      this._operating = MapBackedSet.decorate(new IdentityMap());
   }

   protected Set getOperatingSet() {
      return Collections.unmodifiableSet(this._operating);
   }

   public Object clone() throws CloneNotSupportedException {
      if (this._initializeWasInvoked) {
         throw new CloneNotSupportedException();
      } else {
         return super.clone();
      }
   }

   protected Map newManagedObjectCache() {
      return new ReferenceHashMap(0, 2);
   }

   public Broker getBroker() {
      return this;
   }

   public void setImplicitBehavior(OpCallbacks call, RuntimeExceptionTranslator ex) {
      if (this._call == null) {
         this._call = call;
      }

      if (this._extrans == null) {
         this._extrans = ex;
      }

   }

   RuntimeExceptionTranslator getInstanceExceptionTranslator() {
      return this._operationCount == 0 ? this._extrans : null;
   }

   public BrokerFactory getBrokerFactory() {
      return this._factory;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._conf;
   }

   public FetchConfiguration getFetchConfiguration() {
      return this._fc;
   }

   public FetchConfiguration pushFetchConfiguration() {
      if (this._fcs == null) {
         this._fcs = new LinkedList();
      }

      this._fcs.add(this._fc);
      this._fc = (FetchConfiguration)this._fc.clone();
      return this._fc;
   }

   public void popFetchConfiguration() {
      if (this._fcs != null && !this._fcs.isEmpty()) {
         this._fc = (FetchConfiguration)this._fcs.removeLast();
      } else {
         throw new UserException(_loc.get("fetch-configuration-stack-empty"));
      }
   }

   public int getConnectionRetainMode() {
      return this._connRetainMode;
   }

   public boolean isManaged() {
      return this._managed;
   }

   public ManagedRuntime getManagedRuntime() {
      return this._runtime;
   }

   public ClassLoader getClassLoader() {
      return this._loader;
   }

   public DelegatingStoreManager getStoreManager() {
      return this._store;
   }

   public LockManager getLockManager() {
      return this._lm;
   }

   public InverseManager getInverseManager() {
      return this._im;
   }

   public String getConnectionUserName() {
      return this._user;
   }

   public String getConnectionPassword() {
      return this._pass;
   }

   public boolean getMultithreaded() {
      return this._multithreaded;
   }

   public void setMultithreaded(boolean multithreaded) {
      this.assertOpen();
      this._multithreaded = multithreaded;
      if (multithreaded && this._lock == null) {
         this._lock = new ReentrantLock();
      } else if (!multithreaded) {
         this._lock = null;
      }

   }

   public boolean getIgnoreChanges() {
      return this._ignoreChanges;
   }

   public void setIgnoreChanges(boolean val) {
      this.assertOpen();
      this._ignoreChanges = val;
   }

   public boolean getNontransactionalRead() {
      return this._nontransRead;
   }

   public void setNontransactionalRead(boolean val) {
      this.assertOpen();
      if ((this._flags & 16) != 0) {
         throw new UserException(_loc.get("illegal-op-in-prestore"));
      } else {
         if (val) {
            Collection var10000 = this._conf.supportedOptions();
            OpenJPAConfiguration var10001 = this._conf;
            if (!var10000.contains("openjpa.option.NontransactionalRead")) {
               throw new UnsupportedException(_loc.get("nontrans-read-not-supported"));
            }
         }

         this._nontransRead = val;
      }
   }

   public boolean getNontransactionalWrite() {
      return this._nontransWrite;
   }

   public void setNontransactionalWrite(boolean val) {
      this.assertOpen();
      if ((this._flags & 16) != 0) {
         throw new UserException(_loc.get("illegal-op-in-prestore"));
      } else {
         this._nontransWrite = val;
      }
   }

   public boolean getOptimistic() {
      return this._optimistic;
   }

   public void setOptimistic(boolean val) {
      this.assertOpen();
      if ((this._flags & 2) != 0) {
         throw new InvalidStateException(_loc.get("trans-active", (Object)"Optimistic"));
      } else {
         if (val) {
            Collection var10000 = this._conf.supportedOptions();
            OpenJPAConfiguration var10001 = this._conf;
            if (!var10000.contains("openjpa.option.Optimistic")) {
               throw new UnsupportedException(_loc.get("optimistic-not-supported"));
            }
         }

         this._optimistic = val;
      }
   }

   public int getRestoreState() {
      return this._restoreState;
   }

   public void setRestoreState(int val) {
      this.assertOpen();
      if ((this._flags & 2) != 0) {
         throw new InvalidStateException(_loc.get("trans-active", (Object)"Restore"));
      } else {
         this._restoreState = val;
      }
   }

   public boolean getRetainState() {
      return this._retainState;
   }

   public void setRetainState(boolean val) {
      this.assertOpen();
      if ((this._flags & 16) != 0) {
         throw new UserException(_loc.get("illegal-op-in-prestore"));
      } else {
         this._retainState = val;
      }
   }

   public int getAutoClear() {
      return this._autoClear;
   }

   public void setAutoClear(int val) {
      this.assertOpen();
      this._autoClear = val;
   }

   public int getAutoDetach() {
      return this._autoDetach;
   }

   public void setAutoDetach(int detachFlags) {
      this.assertOpen();
      this._autoDetach = detachFlags;
   }

   public void setAutoDetach(int detachFlag, boolean on) {
      this.assertOpen();
      if (on) {
         this._autoDetach |= detachFlag;
      } else {
         this._autoDetach &= ~detachFlag;
      }

   }

   public int getDetachState() {
      return this._detachState;
   }

   public void setDetachState(int mode) {
      this.assertOpen();
      this._detachState = mode;
   }

   public boolean isDetachedNew() {
      return this._detachedNew;
   }

   public void setDetachedNew(boolean isNew) {
      this.assertOpen();
      this._detachedNew = isNew;
   }

   public boolean getSyncWithManagedTransactions() {
      return this._syncManaged;
   }

   public void setSyncWithManagedTransactions(boolean sync) {
      this.assertOpen();
      this._syncManaged = sync;
   }

   public boolean getEvictFromDataCache() {
      return this._evictDataCache;
   }

   public void setEvictFromDataCache(boolean evict) {
      this.assertOpen();
      this._evictDataCache = evict;
   }

   public boolean getPopulateDataCache() {
      return this._populateDataCache;
   }

   public void setPopulateDataCache(boolean cache) {
      this.assertOpen();
      this._populateDataCache = cache;
   }

   public boolean isTrackChangesByType() {
      return this._largeTransaction;
   }

   public void setTrackChangesByType(boolean largeTransaction) {
      this.assertOpen();
      this._largeTransaction = largeTransaction;
   }

   public Object getUserObject(Object key) {
      this.beginOperation(false);

      Object var2;
      try {
         var2 = this._userObjects == null ? null : this._userObjects.get(key);
      } finally {
         this.endOperation();
      }

      return var2;
   }

   public Object putUserObject(Object key, Object val) {
      this.beginOperation(false);

      Object var3;
      try {
         if (val != null) {
            if (this._userObjects == null) {
               this._userObjects = new HashMap();
            }

            var3 = this._userObjects.put(key, val);
            return var3;
         }

         var3 = this._userObjects == null ? null : this._userObjects.remove(key);
      } finally {
         this.endOperation();
      }

      return var3;
   }

   public void addLifecycleListener(Object listener, Class[] classes) {
      this.beginOperation(false);

      try {
         this._lifeEventManager.addListener(listener, classes);
      } finally {
         this.endOperation();
      }

   }

   public void removeLifecycleListener(Object listener) {
      this.beginOperation(false);

      try {
         this._lifeEventManager.removeListener(listener);
      } finally {
         this.endOperation();
      }

   }

   public int getLifecycleListenerCallbackMode() {
      return this._lifeCallbackMode;
   }

   public void setLifecycleListenerCallbackMode(int mode) {
      this.beginOperation(false);

      try {
         this._lifeCallbackMode = mode;
         this._lifeEventManager.setFailFast((mode & 2) != 0);
      } finally {
         this.endOperation();
      }

   }

   public LifecycleEventManager getLifecycleEventManager() {
      return this._lifeEventManager;
   }

   boolean fireLifecycleEvent(Object src, Object related, ClassMetaData meta, int eventType) {
      if (this._lifeEventManager == null) {
         return false;
      } else {
         this.lock();

         Exception[] exs;
         try {
            exs = this._lifeEventManager.fireEvent(src, related, meta, eventType);
         } finally {
            this.unlock();
         }

         this.handleCallbackExceptions(exs, this._lifeCallbackMode);
         return true;
      }
   }

   private void handleCallbackExceptions(Exception[] exceps, int mode) {
      if (exceps.length != 0 && (mode & 4) == 0) {
         Object ce;
         if (exceps.length == 1) {
            ce = new CallbackException(exceps[0]);
         } else {
            ce = (new CallbackException(_loc.get("callback-err"))).setNestedThrowables(exceps);
         }

         if ((mode & 32) != 0 && (this._flags & 2) != 0) {
            ((OpenJPAException)ce).setFatal(true);
            this.setRollbackOnlyInternal((Throwable)ce);
         }

         if ((mode & 8) != 0 && this._log.isWarnEnabled()) {
            this._log.warn(ce);
         }

         if ((mode & 16) != 0) {
            throw ce;
         }
      }
   }

   public void addTransactionListener(Object tl) {
      this.beginOperation(false);

      try {
         this._transEventManager.addListener(tl);
         if (tl instanceof RemoteCommitEventManager) {
            this._flags |= 1024;
         }
      } finally {
         this.endOperation();
      }

   }

   public void removeTransactionListener(Object tl) {
      this.beginOperation(false);

      try {
         if (this._transEventManager.removeListener(tl) && tl instanceof RemoteCommitEventManager) {
            this._flags &= -1025;
         }
      } finally {
         this.endOperation();
      }

   }

   public int getTransactionListenerCallbackMode() {
      return this._transCallbackMode;
   }

   public void setTransactionListenerCallbackMode(int mode) {
      this.beginOperation(false);

      try {
         this._transCallbackMode = mode;
         this._transEventManager.setFailFast((mode & 2) != 0);
      } finally {
         this.endOperation();
      }

   }

   private void fireTransactionEvent(TransactionEvent trans) {
      if (this._transEventManager != null) {
         this.handleCallbackExceptions(this._transEventManager.fireEvent(trans), this._transCallbackMode);
      }

   }

   public Object find(Object oid, boolean validate, FindCallbacks call) {
      int flags = 28;
      if (!validate) {
         flags |= 2;
      }

      return this.find(oid, this._fc, (BitSet)null, (Object)null, flags, call);
   }

   public Object find(Object oid, FetchConfiguration fetch, BitSet exclude, Object edata, int flags) {
      return this.find(oid, fetch, exclude, edata, flags, (FindCallbacks)null);
   }

   protected Object find(Object oid, FetchConfiguration fetch, BitSet exclude, Object edata, int flags, FindCallbacks call) {
      if (call == null) {
         call = this;
      }

      oid = ((FindCallbacks)call).processArgument(oid);
      if (oid == null) {
         if ((flags & 2) == 0) {
            throw new ObjectNotFoundException(_loc.get("null-oid"));
         } else {
            return ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
         }
      } else {
         if (fetch == null) {
            fetch = this._fc;
         }

         this.beginOperation(true);

         Object var8;
         try {
            this.assertNontransactionalRead();
            StateManagerImpl sm = this.getStateManagerImplById(oid, (flags & 16) != 0 || this.hasFlushed());
            Object var9;
            Object var10;
            boolean loaded;
            if (sm != null) {
               if (!this.requiresLoad(sm, true, fetch, edata, flags)) {
                  var8 = ((FindCallbacks)call).processReturn(oid, sm);
                  return var8;
               }

               if (!sm.isLoading()) {
                  if (!sm.isTransactional() && this.useTransactionalState(fetch)) {
                     sm.transactional();
                  }

                  try {
                     loaded = sm.load(fetch, 0, exclude, edata, false);
                  } catch (ObjectNotFoundException var17) {
                     if ((flags & 4) == 0 && (flags & 2) == 0) {
                        var10 = ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
                        return var10;
                     }

                     throw var17;
                  }

                  if (!loaded && (flags & 2) == 0 && this._compat.getValidateTrueChecksStore() && !sm.isTransactional() && !this._store.exists(sm, edata)) {
                     if ((flags & 4) == 0) {
                        var9 = ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
                        return var9;
                     }

                     throw (new ObjectNotFoundException(_loc.get("del-instance", sm.getManagedInstance(), oid))).setFailedObject(sm.getManagedInstance());
                  }
               }

               if ((this._flags & 2) != 0) {
                  int level = fetch.getReadLockLevel();
                  this._lm.lock(sm, level, fetch.getLockTimeout(), edata);
                  sm.readLocked(level, fetch.getWriteLockLevel());
               }

               var8 = ((FindCallbacks)call).processReturn(oid, sm);
               return var8;
            }

            if (!(oid instanceof StateManagerId)) {
               sm = this.newStateManagerImpl(oid, (flags & 8) != 0);
               loaded = this.requiresLoad(sm, false, fetch, edata, flags);
               sm = this.initialize(sm, loaded, fetch, edata);
               if (sm == null) {
                  if ((flags & 2) != 0) {
                     throw new ObjectNotFoundException(oid);
                  }

                  var9 = ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
                  return var9;
               }

               if (loaded) {
                  try {
                     sm.load(fetch, 0, exclude, edata, false);
                  } catch (ObjectNotFoundException var18) {
                     if ((flags & 4) == 0 && (flags & 2) == 0) {
                        var10 = ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
                        return var10;
                     }

                     throw var18;
                  }
               }

               var9 = ((FindCallbacks)call).processReturn(oid, sm);
               return var9;
            }

            var8 = ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
         } catch (OpenJPAException var19) {
            throw var19;
         } catch (RuntimeException var20) {
            throw new GeneralException(var20);
         } finally {
            this.endOperation();
         }

         return var8;
      }
   }

   protected StateManagerImpl initialize(StateManagerImpl sm, boolean load, FetchConfiguration fetch, Object edata) {
      if (!load) {
         sm.initialize(sm.getMetaData().getDescribedType(), PCState.HOLLOW);
      } else {
         PCState state = this.useTransactionalState(fetch) ? PCState.PCLEAN : PCState.PNONTRANS;
         sm.setLoading(true);

         try {
            if (!this._store.initialize(sm, state, fetch, edata)) {
               Object var6 = null;
               return (StateManagerImpl)var6;
            }
         } finally {
            sm.setLoading(false);
         }
      }

      return sm;
   }

   public Object[] findAll(Collection oids, boolean validate, FindCallbacks call) {
      int flags = 28;
      if (!validate) {
         flags |= 2;
      }

      return this.findAll(oids, this._fc, (BitSet)null, (Object)null, flags, call);
   }

   public Object[] findAll(Collection oids, FetchConfiguration fetch, BitSet exclude, Object edata, int flags) {
      return this.findAll(oids, fetch, exclude, edata, flags, (FindCallbacks)null);
   }

   protected Object[] findAll(Collection oids, FetchConfiguration fetch, BitSet exclude, Object edata, int flags, FindCallbacks call) {
      ++this.findAllDepth;
      if (oids == null) {
         throw new NullPointerException("oids == null");
      } else if ((flags & 2) != 0 && oids.contains((Object)null)) {
         throw new UserException(_loc.get("null-oids"));
      } else {
         if (this._loading == null) {
            this._loading = new HashMap((int)((double)oids.size() * 1.33 + 1.0));
         }

         if (call == null) {
            call = this;
         }

         if (fetch == null) {
            fetch = this._fc;
         }

         this.beginOperation(true);

         Object[] var32;
         try {
            this.assertNontransactionalRead();
            List load = null;
            boolean transState = this.useTransactionalState(fetch);
            int idx = 0;

            StateManagerImpl sm;
            Object oid;
            for(Iterator itr = oids.iterator(); itr.hasNext(); ++idx) {
               Object obj = itr.next();
               oid = ((FindCallbacks)call).processArgument(obj);
               if (oid != null && !this._loading.containsKey(obj)) {
                  sm = this.getStateManagerImplById(oid, (flags & 16) != 0 || this.hasFlushed());
                  boolean initialized = sm != null;
                  if (!initialized) {
                     sm = this.newStateManagerImpl(oid, (flags & 8) != 0);
                  }

                  this._loading.put(obj, sm);
                  if (!this.requiresLoad(sm, initialized, fetch, edata, flags)) {
                     if (!initialized) {
                        sm.initialize(sm.getMetaData().getDescribedType(), PCState.HOLLOW);
                     }
                  } else {
                     transState = transState || this.useTransactionalState(fetch);
                     if (initialized && !sm.isTransactional() && transState) {
                        sm.transactional();
                     }

                     if (load == null) {
                        load = new ArrayList(oids.size() - idx);
                     }

                     load.add(sm);
                  }
               }
            }

            if (load != null) {
               PCState state = transState ? PCState.PCLEAN : PCState.PNONTRANS;
               Collection failed = this._store.loadAll(load, state, 0, fetch, edata);
               if (failed != null && !failed.isEmpty()) {
                  if ((flags & 2) != 0) {
                     throw newObjectNotFoundException(failed);
                  }

                  Iterator itr = failed.iterator();

                  while(itr.hasNext()) {
                     this._loading.put(itr.next(), (Object)null);
                  }
               }
            }

            Object[] results = new Object[oids.size()];
            boolean active = (this._flags & 2) != 0;
            int level = fetch.getReadLockLevel();
            idx = 0;

            for(Iterator itr = oids.iterator(); itr.hasNext(); ++idx) {
               oid = itr.next();
               sm = (StateManagerImpl)this._loading.get(oid);
               if (sm != null && this.requiresLoad(sm, true, fetch, edata, flags)) {
                  try {
                     sm.load(fetch, 0, exclude, edata, false);
                     if (active) {
                        this._lm.lock(sm, level, fetch.getLockTimeout(), edata);
                        sm.readLocked(level, fetch.getWriteLockLevel());
                     }
                  } catch (ObjectNotFoundException var24) {
                     if ((flags & 4) != 0 || (flags & 2) != 0) {
                        throw var24;
                     }

                     sm = null;
                  }
               }

               results[idx] = ((FindCallbacks)call).processReturn(oid, sm);
            }

            var32 = results;
         } catch (OpenJPAException var25) {
            throw var25;
         } catch (RuntimeException var26) {
            throw new GeneralException(var26);
         } finally {
            --this.findAllDepth;
            if (this.findAllDepth == 0) {
               this._loading = null;
            }

            this.endOperation();
         }

         return var32;
      }
   }

   private boolean hasFlushed() {
      return (this._flags & 256) != 0;
   }

   private boolean requiresLoad(OpenJPAStateManager sm, boolean initialized, FetchConfiguration fetch, Object edata, int flags) {
      if (!fetch.requiresLoad()) {
         return false;
      } else if ((flags & 2) == 0) {
         return true;
      } else if (edata != null) {
         return true;
      } else if (initialized && sm.getPCState() != PCState.HOLLOW) {
         return false;
      } else if (!initialized && sm.getMetaData().getPCSubclasses().length > 0) {
         return true;
      } else {
         return !this._compat.getValidateFalseReturnsHollow();
      }
   }

   private boolean useTransactionalState(FetchConfiguration fetch) {
      return (this._flags & 2) != 0 && (!this._optimistic || this._autoClear == 1 || fetch.getReadLockLevel() != 0);
   }

   public Object findCached(Object oid, FindCallbacks call) {
      if (call == null) {
         call = this;
      }

      oid = ((FindCallbacks)call).processArgument(oid);
      if (oid == null) {
         return ((FindCallbacks)call).processReturn(oid, (OpenJPAStateManager)null);
      } else {
         this.beginOperation(true);

         Object var4;
         try {
            StateManagerImpl sm = this.getStateManagerImplById(oid, true);
            var4 = ((FindCallbacks)call).processReturn(oid, sm);
         } finally {
            this.endOperation();
         }

         return var4;
      }
   }

   public Class getObjectIdType(Class cls) {
      if (cls == null) {
         return null;
      } else {
         this.beginOperation(false);

         Class var3;
         try {
            ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(cls, this._loader, false);
            if (meta == null || meta.getIdentityType() == 0) {
               var3 = null;
               return var3;
            }

            if (meta.getIdentityType() != 2) {
               var3 = this._store.getDataStoreIdType(meta);
               return var3;
            }

            var3 = meta.getObjectIdType();
         } catch (OpenJPAException var8) {
            throw var8;
         } catch (RuntimeException var9) {
            throw new GeneralException(var9);
         } finally {
            this.endOperation();
         }

         return var3;
      }
   }

   public Object newObjectId(Class cls, Object val) {
      if (val == null) {
         return null;
      } else {
         this.beginOperation(false);

         try {
            ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(cls, this._loader, true);
            Object var4;
            switch (meta.getIdentityType()) {
               case 1:
                  if (val instanceof String && ((String)val).startsWith("openjpasm:")) {
                     StateManagerId var19 = new StateManagerId((String)val);
                     return var19;
                  }

                  var4 = this._store.newDataStoreId(val, meta);
                  return var4;
               case 2:
                  if (!ImplHelper.isAssignable(meta.getObjectIdType(), val.getClass())) {
                     if (val instanceof String && !this._conf.getCompatibilityInstance().getStrictIdentityValues() && !Modifier.isAbstract(cls.getModifiers())) {
                        var4 = PCRegistry.newObjectId(cls, (String)val);
                        return var4;
                     }

                     Object[] arr = val instanceof Object[] ? (Object[])((Object[])val) : new Object[]{val};
                     Object var5 = ApplicationIds.fromPKValues(arr, meta);
                     return var5;
                  } else {
                     if (!meta.isOpenJPAIdentity() && meta.isObjectIdTypeShared()) {
                        ObjectId var17 = new ObjectId(cls, val);
                        return var17;
                     }

                     var4 = val;
                     return var4;
                  }
               default:
                  throw new UserException(_loc.get("meta-unknownid", (Object)cls));
            }
         } catch (IllegalArgumentException var12) {
            throw (new UserException(_loc.get("bad-id-value", val, val.getClass().getName(), cls))).setCause(var12);
         } catch (OpenJPAException var13) {
            throw var13;
         } catch (ClassCastException var14) {
            throw (new UserException(_loc.get("bad-id-value", val, val.getClass().getName(), cls))).setCause(var14);
         } catch (RuntimeException var15) {
            throw new GeneralException(var15);
         } finally {
            this.endOperation();
         }
      }
   }

   private StateManagerImpl newStateManagerImpl(Object oid, boolean copy) {
      StateManagerImpl sm;
      if (this._loading != null) {
         sm = (StateManagerImpl)this._loading.get(oid);
         if (sm != null && sm.getPersistenceCapable() == null) {
            return sm;
         }
      }

      Class pcType = this._store.getManagedType(oid);
      MetaDataRepository repos = this._conf.getMetaDataRepositoryInstance();
      ClassMetaData meta;
      if (pcType != null) {
         meta = repos.getMetaData(pcType, this._loader, true);
      } else {
         meta = repos.getMetaData(oid, this._loader, true);
      }

      if (copy && this._compat.getCopyObjectIds()) {
         if (meta.getIdentityType() == 2) {
            oid = ApplicationIds.copy(oid, meta);
         } else {
            if (meta.getIdentityType() == 0) {
               throw new UserException(_loc.get("meta-unknownid", (Object)meta));
            }

            oid = this._store.copyDataStoreId(oid, meta);
         }
      }

      sm = this.newStateManagerImpl(oid, meta);
      sm.setObjectId(oid);
      return sm;
   }

   protected StateManagerImpl newStateManagerImpl(Object oid, ClassMetaData meta) {
      return new StateManagerImpl(oid, meta, this);
   }

   public void begin() {
      this.beginOperation(true);

      try {
         if ((this._flags & 2) != 0) {
            throw new InvalidStateException(_loc.get("active"));
         }

         this._factory.syncWithManagedTransaction(this, true);
         this.beginInternal();
      } finally {
         this.endOperation();
      }

   }

   private void beginInternal() {
      try {
         this.beginStoreManagerTransaction(this._optimistic);
         this._flags |= 2;
         if (!this._optimistic) {
            this._fc.setReadLockLevel(this._conf.getReadLockLevelConstant());
            this._fc.setWriteLockLevel(this._conf.getWriteLockLevelConstant());
            this._fc.setLockTimeout(this._conf.getLockTimeout());
         }

         this._lm.beginTransaction();
         if (this._transEventManager.hasBeginListeners()) {
            this.fireTransactionEvent(new TransactionEvent(this, 0, (Collection)null, (Collection)null, (Collection)null, (Collection)null));
         }
      } catch (OpenJPAException var3) {
         if ((this._flags & 2) != 0) {
            this.setRollbackOnlyInternal(var3);
         }

         throw var3.setFatal(true);
      } catch (RuntimeException var4) {
         if ((this._flags & 2) != 0) {
            this.setRollbackOnlyInternal(var4);
         }

         throw (new StoreException(var4)).setFatal(true);
      }

      if (this._pending != null) {
         Iterator it = this._pending.iterator();

         while(it.hasNext()) {
            StateManagerImpl sm = (StateManagerImpl)it.next();
            sm.transactional();
            if (sm.isDirty()) {
               this.setDirty(sm, true);
            }
         }

         this._pending = null;
      }

   }

   public void beginStore() {
      this.beginOperation(true);

      try {
         this.assertTransactionOperation();
         if ((this._flags & 4) == 0) {
            this.beginStoreManagerTransaction(false);
         }
      } catch (OpenJPAException var6) {
         throw var6;
      } catch (RuntimeException var7) {
         throw new StoreException(var7);
      } finally {
         this.endOperation();
      }

   }

   private void beginStoreManagerTransaction(boolean optimistic) {
      if (!optimistic) {
         this.retainConnection();
         this._store.begin();
         this._flags |= 4;
      } else {
         if (this._connRetainMode == 1) {
            this.retainConnection();
         }

         this._store.beginOptimistic();
      }

   }

   private RuntimeException endStoreManagerTransaction(boolean rollback) {
      boolean forcedRollback = false;
      boolean releaseConn = false;
      RuntimeException err = null;

      try {
         if ((this._flags & 4) != 0) {
            releaseConn = this._connRetainMode != 2;
            if (rollback) {
               this._store.rollback();
            } else {
               this._store.commit();
            }
         } else {
            releaseConn = this._connRetainMode == 1;
            this._store.rollbackOptimistic();
         }
      } catch (RuntimeException var14) {
         if (!rollback) {
            forcedRollback = true;

            try {
               this._store.rollback();
            } catch (RuntimeException var12) {
            }
         }

         err = var14;
      } finally {
         this._flags &= -5;
      }

      if (releaseConn) {
         try {
            this.releaseConnection();
         } catch (RuntimeException var13) {
            if (err == null) {
               err = var13;
            }
         }
      }

      if (forcedRollback) {
         throw err;
      } else {
         return err;
      }
   }

   public void commit() {
      this.beginOperation(false);

      try {
         this.assertTransactionOperation();
         Transaction trans = this._runtime.getTransactionManager().getTransaction();
         if (trans == null) {
            throw new InvalidStateException(_loc.get("null-trans"));
         }

         trans.commit();
      } catch (OpenJPAException var6) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var6);
         }

         throw var6;
      } catch (Exception var7) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var7);
         }

         throw new StoreException(var7);
      } finally {
         this.endOperation();
      }

   }

   public void rollback() {
      this.beginOperation(false);

      try {
         this.assertTransactionOperation();
         Transaction trans = this._runtime.getTransactionManager().getTransaction();
         if (trans != null) {
            trans.rollback();
         }
      } catch (OpenJPAException var6) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var6);
         }

         throw var6;
      } catch (Exception var7) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var7);
         }

         throw new StoreException(var7);
      } finally {
         this.endOperation();
      }

   }

   public boolean syncWithManagedTransaction() {
      this.assertOpen();
      this.lock();

      boolean var1;
      try {
         if ((this._flags & 2) != 0) {
            var1 = true;
            return var1;
         }

         if (!this._managed) {
            throw new InvalidStateException(_loc.get("trans-not-managed"));
         }

         if (!this._factory.syncWithManagedTransaction(this, false)) {
            var1 = false;
            return var1;
         }

         this.beginInternal();
         var1 = true;
      } finally {
         this.unlock();
      }

      return var1;
   }

   public void commitAndResume() {
      this.endAndResume(true);
   }

   public void rollbackAndResume() {
      this.endAndResume(false);
   }

   private void endAndResume(boolean commit) {
      this.beginOperation(false);

      try {
         if (commit) {
            this.commit();
         } else {
            this.rollback();
         }

         this.begin();
      } finally {
         this.endOperation();
      }

   }

   public boolean getRollbackOnly() {
      this.beginOperation(true);

      boolean var2;
      try {
         if ((this._flags & 2) == 0) {
            boolean var10 = false;
            return var10;
         }

         Transaction trans = this._runtime.getTransactionManager().getTransaction();
         if (trans == null) {
            var2 = false;
            return var2;
         }

         var2 = trans.getStatus() == 1;
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new GeneralException(var8);
      } finally {
         this.endOperation();
      }

      return var2;
   }

   public Throwable getRollbackCause() {
      this.beginOperation(true);

      Transaction trans;
      try {
         if ((this._flags & 2) != 0) {
            trans = this._runtime.getTransactionManager().getTransaction();
            Throwable var2;
            if (trans == null) {
               var2 = null;
               return var2;
            }

            if (trans.getStatus() == 1) {
               var2 = this._runtime.getRollbackCause();
               return var2;
            }

            var2 = null;
            return var2;
         }

         trans = null;
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new GeneralException(var8);
      } finally {
         this.endOperation();
      }

      return trans;
   }

   public void setRollbackOnly() {
      this.setRollbackOnly(new UserException());
   }

   public void setRollbackOnly(Throwable cause) {
      this.beginOperation(true);

      try {
         this.assertTransactionOperation();
         this.setRollbackOnlyInternal(cause);
      } finally {
         this.endOperation();
      }

   }

   private void setRollbackOnlyInternal(Throwable cause) {
      try {
         Transaction trans = this._runtime.getTransactionManager().getTransaction();
         if (trans == null) {
            throw new InvalidStateException(_loc.get("null-trans"));
         } else {
            int tranStatus = trans.getStatus();
            if (tranStatus != 6 && tranStatus != 4 && tranStatus != 3) {
               this._runtime.setRollbackOnly(cause);
            } else if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("invalid-tran-status", new Integer(tranStatus), "setRollbackOnly"));
            }

         }
      } catch (OpenJPAException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new GeneralException(var5);
      }
   }

   public void setSavepoint(String name) {
      this.beginOperation(true);

      try {
         this.assertActiveTransaction();
         if (this._savepoints != null && this._savepoints.containsKey(name)) {
            throw new UserException(_loc.get("savepoint-exists", (Object)name));
         }

         if (this.hasFlushed() && !this._spm.supportsIncrementalFlush()) {
            throw new UnsupportedException(_loc.get("savepoint-flush-not-supported"));
         }

         OpenJPASavepoint save = this._spm.newSavepoint(name, this);
         if (this._savepoints != null && !this._savepoints.isEmpty()) {
            if (this._savepointCache == null) {
               save.save(Collections.EMPTY_LIST);
            } else {
               save.save(this._savepointCache);
               this._savepointCache.clear();
            }
         } else {
            save.save(this.getTransactionalStates());
            this._savepoints = new LinkedMap();
         }

         this._savepoints.put(name, save);
      } catch (OpenJPAException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new GeneralException(var8);
      } finally {
         this.endOperation();
      }

   }

   public void releaseSavepoint() {
      this.beginOperation(false);

      try {
         if (this._savepoints == null || this._savepoints.isEmpty()) {
            throw new UserException(_loc.get("no-lastsavepoint"));
         }

         this.releaseSavepoint((String)this._savepoints.get(this._savepoints.size() - 1));
      } finally {
         this.endOperation();
      }

   }

   public void releaseSavepoint(String savepoint) {
      this.beginOperation(false);

      try {
         this.assertActiveTransaction();
         int index = this._savepoints == null ? -1 : this._savepoints.indexOf(savepoint);
         if (index < 0) {
            throw new UserException(_loc.get("no-savepoint", (Object)savepoint));
         }

         OpenJPASavepoint save;
         while(this._savepoints.size() > index + 1) {
            save = (OpenJPASavepoint)this._savepoints.remove(this._savepoints.size() - 1);
            save.release(false);
         }

         save = (OpenJPASavepoint)this._savepoints.remove(index);
         save.release(true);
         if (this._savepointCache != null) {
            this._savepointCache.clear();
         }
      } catch (OpenJPAException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new GeneralException(var9);
      } finally {
         this.endOperation();
      }

   }

   public void rollbackToSavepoint() {
      this.beginOperation(false);

      try {
         if (this._savepoints == null || this._savepoints.isEmpty()) {
            throw new UserException(_loc.get("no-lastsavepoint"));
         }

         this.rollbackToSavepoint((String)this._savepoints.get(this._savepoints.size() - 1));
      } finally {
         this.endOperation();
      }

   }

   public void rollbackToSavepoint(String savepoint) {
      this.beginOperation(false);

      try {
         this.assertActiveTransaction();
         int index = this._savepoints == null ? -1 : this._savepoints.indexOf(savepoint);
         if (index < 0) {
            throw new UserException(_loc.get("no-savepoint", (Object)savepoint));
         }

         OpenJPASavepoint save;
         while(this._savepoints.size() > index + 1) {
            save = (OpenJPASavepoint)this._savepoints.remove(this._savepoints.size() - 1);
            save.release(false);
         }

         save = (OpenJPASavepoint)this._savepoints.remove(index);
         Collection saved = save.rollback(this._savepoints.values());
         if (this._savepointCache != null) {
            this._savepointCache.clear();
         }

         if (this.hasTransactionalObjects()) {
            TransactionalCache oldTransCache = this._transCache;
            TransactionalCache newTransCache = new TransactionalCache(this._orderDirty);
            this._transCache = null;
            Iterator itr = saved.iterator();

            StateManagerImpl sm;
            while(itr.hasNext()) {
               SavepointFieldManager fm = (SavepointFieldManager)itr.next();
               sm = fm.getStateManager();
               sm.rollbackToSavepoint(fm);
               oldTransCache.remove(sm);
               if (sm.isDirty()) {
                  newTransCache.addDirty(sm);
               } else {
                  newTransCache.addClean(sm);
               }
            }

            itr = oldTransCache.iterator();

            while(itr.hasNext()) {
               sm = (StateManagerImpl)itr.next();
               sm.rollback();
               this.removeFromTransaction(sm);
            }

            this._transCache = newTransCache;
         }
      } catch (OpenJPAException var14) {
         throw var14;
      } catch (Exception var15) {
         throw new GeneralException(var15);
      } finally {
         this.endOperation();
      }

   }

   public void flush() {
      this.beginOperation(true);

      try {
         if ((this._flags & 2) == 0 || (this._flags & 128) != 0) {
            return;
         }

         Collection var10000 = this._conf.supportedOptions();
         OpenJPAConfiguration var10001 = this._conf;
         if (!var10000.contains("openjpa.option.IncrementalFlush")) {
            throw new UnsupportedException(_loc.get("incremental-flush-not-supported"));
         }

         if (this._savepoints != null && !this._savepoints.isEmpty() && !this._spm.supportsIncrementalFlush()) {
            throw new UnsupportedException(_loc.get("savepoint-flush-not-supported"));
         }

         try {
            this.flushSafe(0);
            this._flags |= 256;
         } catch (OpenJPAException var6) {
            this.setRollbackOnly(var6);
            throw var6.setFatal(true);
         } catch (RuntimeException var7) {
            this.setRollbackOnly(var7);
            throw (new StoreException(var7)).setFatal(true);
         }
      } finally {
         this.endOperation();
      }

   }

   public void preFlush() {
      this.beginOperation(true);

      try {
         if ((this._flags & 2) != 0) {
            this.flushSafe(3);
         }
      } finally {
         this.endOperation();
      }

   }

   public void validateChanges() {
      this.beginOperation(true);

      try {
         if ((this._flags & 2) != 0) {
            if ((this._flags & 4) != 0) {
               this.flush();
               return;
            }

            Collection var10000 = this._conf.supportedOptions();
            OpenJPAConfiguration var10001 = this._conf;
            if (!var10000.contains("openjpa.option.IncrementalFlush")) {
               throw new UnsupportedException(_loc.get("incremental-flush-not-supported"));
            }

            try {
               this.flushSafe(2);
               return;
            } catch (OpenJPAException var6) {
               throw var6;
            } catch (RuntimeException var7) {
               throw new StoreException(var7);
            }
         }
      } finally {
         this.endOperation();
      }

   }

   public boolean isActive() {
      this.beginOperation(true);

      boolean var1;
      try {
         var1 = (this._flags & 2) != 0;
      } finally {
         this.endOperation();
      }

      return var1;
   }

   public boolean isStoreActive() {
      this.beginOperation(true);

      boolean var1;
      try {
         var1 = (this._flags & 4) != 0;
      } finally {
         this.endOperation();
      }

      return var1;
   }

   boolean isTransactionEnding() {
      return (this._flags & 4096) != 0;
   }

   public boolean beginOperation(boolean syncTrans) {
      this.lock();

      try {
         this.assertOpen();
         if (syncTrans && this._operationCount == 0 && this._syncManaged && (this._flags & 2) == 0) {
            this.syncWithManagedTransaction();
         }

         return this._operationCount++ == 1;
      } catch (OpenJPAException var3) {
         this.unlock();
         throw var3;
      } catch (RuntimeException var4) {
         this.unlock();
         throw new GeneralException(var4);
      }
   }

   public boolean endOperation() {
      boolean var1;
      try {
         if (this._operationCount == 1 && (this._autoDetach & 8) != 0 && (this._flags & 2) == 0) {
            this.detachAllInternal((OpCallbacks)null);
         }

         if (this._operationCount < 1) {
            throw new InternalException(_loc.get("multi-threaded-access"));
         }

         var1 = this._operationCount == 1;
      } catch (OpenJPAException var6) {
         throw var6;
      } catch (RuntimeException var7) {
         throw new GeneralException(var7);
      } finally {
         --this._operationCount;
         if (this._operationCount == 0) {
            this.initializeOperatingSet();
         }

         this.unlock();
      }

      return var1;
   }

   public Synchronization getSynchronization() {
      return this._sync;
   }

   public void setSynchronization(Synchronization sync) {
      this.assertOpen();
      this._sync = sync;
   }

   public void beforeCompletion() {
      this.beginOperation(false);

      try {
         if (this._sync != null) {
            this._sync.beforeCompletion();
         }

         this.flushSafe(1);
      } catch (OpenJPAException var6) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var6);
         }

         throw this.translateManagedCompletionException(var6);
      } catch (RuntimeException var7) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var7);
         }

         throw this.translateManagedCompletionException(new StoreException(var7));
      } finally {
         this.endOperation();
      }

   }

   public void afterCompletion(int status) {
      this.beginOperation(false);

      try {
         this.assertActiveTransaction();
         this._flags |= 4096;
         this.endTransaction(status);
         if (this._sync != null) {
            this._sync.afterCompletion(status);
         }

         if ((this._autoDetach & 4) != 0) {
            this.detachAllInternal((OpCallbacks)null);
         } else if (status == 4 && (this._autoDetach & 16) != 0) {
            this.detachAllInternal((OpCallbacks)null);
         }

         if ((this._flags & 8) != 0 && this._compat.getCloseOnManagedCommit()) {
            this.free();
         }
      } catch (OpenJPAException var7) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var7);
         }

         throw this.translateManagedCompletionException(var7);
      } catch (RuntimeException var8) {
         if (this._log.isTraceEnabled()) {
            this._log.trace(_loc.get("end-trans-error"), var8);
         }

         throw this.translateManagedCompletionException(new StoreException(var8));
      } finally {
         this._flags &= -3;
         this._flags &= -257;
         this._flags &= -4097;
         if (this._transEventManager != null && this._transEventManager.hasEndListeners()) {
            this.fireTransactionEvent(new TransactionEvent(this, status == 3 ? 7 : 8, (Collection)null, (Collection)null, (Collection)null, (Collection)null));
         }

         this.endOperation();
      }

   }

   private RuntimeException translateManagedCompletionException(RuntimeException re) {
      return this._managed && this._extrans != null ? this._extrans.translate(re) : re;
   }

   private void flushSafe(int reason) {
      if ((this._flags & 64) != 0) {
         throw new InvalidStateException(_loc.get("reentrant-flush"));
      } else {
         this._flags |= 64;

         try {
            this.flush(reason);
         } finally {
            this._flags &= -65;
         }

      }
   }

   protected void flush(int reason) {
      Collection transactional = this.getTransactionalStates();
      boolean flush = (this._flags & 512) != 0;
      boolean listeners = (this._transEventManager.hasFlushListeners() || this._transEventManager.hasEndListeners()) && ((this._flags & 1024) == 0 || this._transEventManager.getListeners().size() > 1);
      if (flush || reason == 1 && listeners) {
         Collection mobjs = null;
         this._flags |= 16;

         try {
            Iterator itr;
            if (flush) {
               itr = transactional.iterator();

               while(itr.hasNext()) {
                  ((StateManagerImpl)itr.next()).beforeFlush(reason, this._call);
               }

               this.flushAdditions(transactional, reason);
            }

            this._flags |= 32;
            if (flush && this._derefCache != null && !this._derefCache.isEmpty()) {
               itr = this._derefCache.iterator();

               while(itr.hasNext()) {
                  this.deleteDeref((StateManagerImpl)itr.next());
               }

               this.flushAdditions(transactional, reason);
            }

            if (reason != 3) {
               if ((this._flags & 4) == 0) {
                  this.beginStoreManagerTransaction(false);
               }

               if ((this._transEventManager.hasFlushListeners() || this._transEventManager.hasEndListeners()) && (flush || reason == 1)) {
                  mobjs = new ManagedObjectCollection(transactional);
                  if (reason == 1 && this._transEventManager.hasEndListeners()) {
                     this.fireTransactionEvent(new TransactionEvent(this, 3, mobjs, this._persistedClss, this._updatedClss, this._deletedClss));
                     this.flushAdditions(transactional, reason);
                     flush = (this._flags & 512) != 0;
                  }

                  if (flush && this._transEventManager.hasFlushListeners()) {
                     this.fireTransactionEvent(new TransactionEvent(this, 1, mobjs, this._persistedClss, this._updatedClss, this._deletedClss));
                     this.flushAdditions(transactional, reason);
                  }
               }
            }
         } finally {
            this._flags &= -17;
            this._flags &= -33;
            this._transAdditions = null;
            this._derefAdditions = null;
            if (this._derefCache != null) {
               this._derefCache = null;
            }

         }

         List exceps = null;

         try {
            if (flush && reason != 3) {
               this._flags |= 128;
               exceps = this.add(exceps, this.newFlushException(this._store.flush(transactional)));
            }
         } finally {
            this._flags &= -129;
            if (reason == 2) {
               exceps = this.add(exceps, this.endStoreManagerTransaction(true));
            } else if (reason != 3) {
               this._flags &= -513;
            }

            if (flush) {
               Iterator itr = transactional.iterator();

               while(itr.hasNext()) {
                  StateManagerImpl sm = (StateManagerImpl)itr.next();

                  try {
                     if (sm.getPCState() != PCState.TRANSIENT) {
                        sm.afterFlush(reason);
                        if (reason == 0) {
                           sm.proxyFields(true, false);
                           this._transCache.flushed(sm);
                        }
                     }
                  } catch (Exception var20) {
                     exceps = this.add(exceps, var20);
                  }
               }
            }

         }

         this.throwNestedExceptions(exceps, true);
         if (flush && reason != 2 && reason != 3 && this._transEventManager.hasFlushListeners()) {
            this.fireTransactionEvent(new TransactionEvent(this, 2, mobjs, this._persistedClss, this._updatedClss, this._deletedClss));
         }

      }
   }

   private void flushAdditions(Collection transactional, int reason) {
      boolean loop;
      do {
         loop = this.flushTransAdditions(transactional, reason) | this.deleteDerefAdditions(this._derefCache);
      } while(loop);

   }

   private boolean flushTransAdditions(Collection transactional, int reason) {
      if (this._transAdditions != null && !this._transAdditions.isEmpty()) {
         transactional.addAll(this._transAdditions);
         StateManagerImpl[] states = (StateManagerImpl[])((StateManagerImpl[])this._transAdditions.toArray(new StateManagerImpl[this._transAdditions.size()]));
         this._transAdditions = null;

         for(int i = 0; i < states.length; ++i) {
            states[i].beforeFlush(reason, this._call);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean deleteDerefAdditions(Collection derefs) {
      if (this._derefAdditions != null && !this._derefAdditions.isEmpty()) {
         derefs.addAll(this._derefAdditions);
         StateManagerImpl[] states = (StateManagerImpl[])((StateManagerImpl[])this._derefAdditions.toArray(new StateManagerImpl[this._derefAdditions.size()]));
         this._derefAdditions = null;

         for(int i = 0; i < states.length; ++i) {
            this.deleteDeref(states[i]);
         }

         return true;
      } else {
         return false;
      }
   }

   private void deleteDeref(StateManagerImpl sm) {
      int action = this.processArgument(1, sm.getManagedInstance(), sm, (OpCallbacks)null);
      if ((action & 4) != 0) {
         sm.delete();
      }

      if ((action & 2) != 0) {
         sm.cascadeDelete(this._call);
      }

   }

   private int processArgument(int op, Object obj, OpenJPAStateManager sm, OpCallbacks call) {
      if (call != null) {
         return call.processArgument(op, obj, sm);
      } else {
         return this._call != null ? this._call.processArgument(op, obj, sm) : 6;
      }
   }

   private OpenJPAException newFlushException(Collection exceps) {
      if (exceps != null && !exceps.isEmpty()) {
         Throwable[] t = (Throwable[])((Throwable[])exceps.toArray(new Throwable[exceps.size()]));
         List failed = new ArrayList(t.length);
         boolean opt = true;

         for(int i = 0; opt && i < t.length; ++i) {
            opt = t[i] instanceof OptimisticException;
            if (opt) {
               Object f = ((OptimisticException)t[i]).getFailedObject();
               if (f != null) {
                  failed.add(f);
               }
            }
         }

         if (opt && !failed.isEmpty()) {
            return new OptimisticException(failed, t);
         } else {
            return (OpenJPAException)(opt ? new OptimisticException(t) : (new StoreException(_loc.get("rolled-back"))).setNestedThrowables(t).setFatal(true));
         }
      } else {
         return null;
      }
   }

   protected void endTransaction(int status) {
      boolean rollback = status != 3;
      List exceps = null;

      try {
         exceps = this.add(exceps, this.endStoreManagerTransaction(rollback));
      } catch (RuntimeException var10) {
         rollback = true;
         exceps = this.add(exceps, var10);
      }

      this._fc.setReadLockLevel(0);
      this._fc.setWriteLockLevel(0);
      this._fc.setLockTimeout(-1);
      Object transStates;
      if (this.hasTransactionalObjects()) {
         transStates = this._transCache;
      } else {
         transStates = Collections.EMPTY_LIST;
      }

      Collection mobjs = null;
      if (this._transEventManager.hasEndListeners()) {
         mobjs = new ManagedObjectCollection((Collection)transStates);
         int eventType = rollback ? 5 : 4;
         this.fireTransactionEvent(new TransactionEvent(this, eventType, mobjs, this._persistedClss, this._updatedClss, this._deletedClss));
      }

      this._transCache = null;
      if (this._persistedClss != null) {
         this._persistedClss = null;
      }

      if (this._updatedClss != null) {
         this._updatedClss = null;
      }

      if (this._deletedClss != null) {
         this._deletedClss = null;
      }

      this._cache.clearNew();
      if (this._derefCache != null && !this._derefCache.isEmpty()) {
         Iterator itr = this._derefCache.iterator();

         while(itr.hasNext()) {
            ((StateManagerImpl)itr.next()).setDereferencedDependent(false, false);
         }

         this._derefCache = null;
      }

      Iterator itr = ((Collection)transStates).iterator();

      while(itr.hasNext()) {
         StateManagerImpl sm = (StateManagerImpl)itr.next();

         try {
            if (rollback) {
               sm.setDereferencedDependent(false, false);
               sm.rollback();
            } else {
               sm.commit();
            }
         } catch (RuntimeException var9) {
            exceps = this.add(exceps, var9);
         }
      }

      this._lm.endTransaction();

      while(this._savepoints != null && this._savepoints.size() > 0) {
         OpenJPASavepoint save = (OpenJPASavepoint)this._savepoints.remove(this._savepoints.size() - 1);
         save.release(false);
      }

      this._savepoints = null;
      this._savepointCache = null;
      if (this._transEventManager.hasEndListeners()) {
         this.fireTransactionEvent(new TransactionEvent(this, 6, mobjs, (Collection)null, (Collection)null, (Collection)null));
      }

      if (transStates != Collections.EMPTY_LIST) {
         this._transCache = (TransactionalCache)transStates;
         this._transCache.clear();
      }

      this.throwNestedExceptions(exceps, true);
   }

   public void persist(Object obj, OpCallbacks call) {
      this.persist(obj, (Object)null, true, call);
   }

   public OpenJPAStateManager persist(Object obj, Object id, OpCallbacks call) {
      return this.persist(obj, id, true, call);
   }

   public void persistAll(Collection objs, OpCallbacks call) {
      this.persistAll(objs, true, call);
   }

   public void persistAll(Collection objs, boolean explicit, OpCallbacks call) {
      if (!objs.isEmpty()) {
         this.beginOperation(true);
         List exceps = null;

         try {
            this.assertWriteOperation();
            Iterator itr = objs.iterator();

            while(itr.hasNext()) {
               try {
                  this.persist(itr.next(), explicit, call);
               } catch (UserException var11) {
                  exceps = this.add(exceps, var11);
               }
            }
         } finally {
            this.endOperation();
         }

         this.throwNestedExceptions(exceps, false);
      }
   }

   private List add(List l, Object o) {
      if (o == null) {
         return (List)l;
      } else {
         if (l == null) {
            l = new LinkedList();
         }

         ((List)l).add(o);
         return (List)l;
      }
   }

   private void throwNestedExceptions(List exceps, boolean datastore) {
      if (exceps != null && !exceps.isEmpty()) {
         if (datastore && exceps.size() == 1) {
            throw (RuntimeException)exceps.get(0);
         } else {
            boolean fatal = false;
            Throwable[] t = (Throwable[])((Throwable[])exceps.toArray(new Throwable[exceps.size()]));

            for(int i = 0; i < t.length; ++i) {
               if (t[i] instanceof OpenJPAException && ((OpenJPAException)t[i]).isFatal()) {
                  fatal = true;
               }
            }

            Object err;
            if (datastore) {
               err = new StoreException(_loc.get("nested-exceps"));
            } else {
               err = new UserException(_loc.get("nested-exceps"));
            }

            throw ((OpenJPAException)err).setNestedThrowables(t).setFatal(fatal);
         }
      }
   }

   public void persist(Object obj, boolean explicit, OpCallbacks call) {
      this.persist(obj, (Object)null, explicit, call);
   }

   public OpenJPAStateManager persist(Object obj, Object id, boolean explicit, OpCallbacks call) {
      if (obj == null) {
         return null;
      } else {
         this.beginOperation(true);

         try {
            this.assertWriteOperation();
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if (!this._operating.add(obj)) {
               StateManagerImpl var18 = sm;
               return var18;
            } else {
               int action = this.processArgument(0, obj, sm, call);
               StateManagerImpl var20;
               if (action == 0) {
                  var20 = sm;
                  return var20;
               } else if ((action & 4) == 0) {
                  if (sm != null) {
                     sm.cascadePersist(call);
                  } else {
                     this.cascadeTransient(0, obj, call, "persist");
                  }

                  var20 = sm;
                  return var20;
               } else {
                  PersistenceCapable pc;
                  if (sm != null) {
                     if (sm.isDetached()) {
                        throw (new ObjectExistsException(_loc.get("persist-detached", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
                     }

                     if (!sm.isEmbedded()) {
                        sm.persist();
                        this._cache.persist(sm);
                        if ((action & 2) != 0) {
                           sm.cascadePersist(call);
                        }

                        StateManagerImpl var8 = sm;
                        return var8;
                     }

                     sm.getOwner().dirty(sm.getOwnerIndex());
                     this._cache.persist(sm);
                     pc = sm.getPersistenceCapable();
                  } else {
                     pc = this.assertPersistenceCapable(obj);
                     if (pc.pcIsDetached() == Boolean.TRUE) {
                        throw (new ObjectExistsException(_loc.get("persist-detached", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
                     }
                  }

                  ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(obj.getClass(), this._loader, true);
                  this.fireLifecycleEvent(obj, (Object)null, meta, 0);
                  if (id == null) {
                     if (meta.getIdentityType() == 2) {
                        id = ApplicationIds.create(pc, meta);
                     } else {
                        if (meta.getIdentityType() == 0) {
                           throw new UserException(_loc.get("meta-unknownid", (Object)meta));
                        }

                        id = BrokerImpl.StateManagerId.newInstance(this);
                     }
                  }

                  this.checkForDuplicateId(id, obj);
                  if (sm != null) {
                     pc.pcReplaceStateManager((StateManager)null);
                  }

                  sm = new StateManagerImpl(id, meta, this);
                  if ((this._flags & 2) != 0) {
                     if (explicit) {
                        sm.initialize(pc, PCState.PNEW);
                     } else {
                        sm.initialize(pc, PCState.PNEWPROVISIONAL);
                     }
                  } else {
                     sm.initialize(pc, PCState.PNONTRANSNEW);
                  }

                  if ((action & 2) != 0) {
                     sm.cascadePersist(call);
                  }

                  StateManagerImpl var9 = sm;
                  return var9;
               }
            }
         } catch (OpenJPAException var15) {
            throw var15;
         } catch (RuntimeException var16) {
            throw new GeneralException(var16);
         } finally {
            this.endOperation();
         }
      }
   }

   private void cascadeTransient(int op, Object obj, OpCallbacks call, String errOp) {
      PersistenceCapable pc = this.assertPersistenceCapable(obj);
      if (pc.pcGetStateManager() != null) {
         throw this.newDetachedException(obj, errOp);
      } else {
         ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(obj.getClass(), this._loader, true);
         StateManagerImpl sm = new StateManagerImpl(BrokerImpl.StateManagerId.newInstance(this), meta, this);
         sm.initialize(pc, PCState.TLOADED);

         try {
            switch (op) {
               case 0:
                  sm.cascadePersist(call);
                  break;
               case 1:
                  sm.cascadeDelete(call);
                  break;
               case 2:
                  sm.gatherCascadeRefresh(call);
                  break;
               default:
                  throw new InternalException(String.valueOf(op));
            }
         } finally {
            sm.release(true);
         }

      }
   }

   public void deleteAll(Collection objs, OpCallbacks call) {
      this.beginOperation(true);

      try {
         this.assertWriteOperation();
         List exceps = null;
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            try {
               Object obj = itr.next();
               if (obj != null) {
                  this.delete(obj, this.getStateManagerImpl(obj, true), call);
               }
            } catch (UserException var11) {
               exceps = this.add(exceps, var11);
            }
         }

         this.throwNestedExceptions(exceps, false);
      } finally {
         this.endOperation();
      }

   }

   public void delete(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            this.assertWriteOperation();
            this.delete(obj, this.getStateManagerImpl(obj, true), call);
         } catch (OpenJPAException var9) {
            throw var9;
         } catch (RuntimeException var10) {
            throw new GeneralException(var10);
         } finally {
            this.endOperation();
         }

      }
   }

   void delete(Object obj, StateManagerImpl sm, OpCallbacks call) {
      if (this._operating.add(obj)) {
         int action = this.processArgument(1, obj, sm, call);
         if (action != 0) {
            if ((action & 4) == 0) {
               if (sm != null) {
                  sm.cascadeDelete(call);
               } else {
                  this.cascadeTransient(1, obj, call, "delete");
               }

            } else {
               if (sm != null) {
                  if (sm.isDetached()) {
                     throw this.newDetachedException(obj, "delete");
                  }

                  if ((action & 2) != 0) {
                     sm.cascadeDelete(call);
                  }

                  sm.delete();
               } else if (this.assertPersistenceCapable(obj).pcIsDetached() == Boolean.TRUE) {
                  throw this.newDetachedException(obj, "delete");
               }

            }
         }
      }
   }

   private OpenJPAException newDetachedException(Object obj, String operation) {
      throw (new UserException(_loc.get("bad-detached-op", operation, Exceptions.toString(obj)))).setFailedObject(obj);
   }

   public void releaseAll(Collection objs, OpCallbacks call) {
      this.beginOperation(false);

      try {
         List exceps = null;
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            try {
               this.release(itr.next(), call);
            } catch (UserException var10) {
               exceps = this.add(exceps, var10);
            }
         }

         this.throwNestedExceptions(exceps, false);
      } finally {
         this.endOperation();
      }

   }

   public void release(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(false);

         try {
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            int action = this.processArgument(4, obj, sm, call);
            if (sm == null) {
               return;
            }

            if ((action & 4) != 0 && sm.isPersistent()) {
               boolean pending = sm.isPendingTransactional();
               sm.release(true);
               if (pending) {
                  this.removeFromPendingTransaction(sm);
               }
            }
         } catch (OpenJPAException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw new GeneralException(var12);
         } finally {
            this.endOperation();
         }

      }
   }

   public OpenJPAStateManager embed(Object obj, Object id, OpenJPAStateManager owner, ValueMetaData ownerMeta) {
      this.beginOperation(true);

      StateManagerImpl copySM;
      try {
         StateManagerImpl orig = this.getStateManagerImpl(obj, true);
         if (orig != null) {
            if (orig.getOwner() == owner && orig.getMetaData().getEmbeddingMetaData() == ownerMeta) {
               StateManagerImpl var31 = orig;
               return var31;
            }

            orig.load(this._fc, 1, (BitSet)null, (Object)null, false);
         }

         ClassMetaData meta = ownerMeta.getEmbeddedMetaData();
         if (meta == null) {
            throw new InternalException(_loc.get("bad-embed", (Object)ownerMeta));
         }

         if (id == null) {
            id = BrokerImpl.StateManagerId.newInstance(this);
         }

         StateManagerImpl sm = new StateManagerImpl(id, meta, this);
         sm.setOwner((StateManagerImpl)owner, ownerMeta);
         Class type = meta.getDescribedType();
         PersistenceCapable copy;
         PCState state;
         if (obj != null) {
            PersistenceCapable pc;
            if (orig == null) {
               copySM = sm;
               pc = this.assertPersistenceCapable(obj);
               pc.pcReplaceStateManager(sm);
            } else {
               copySM = orig;
               pc = orig.getPersistenceCapable();
            }

            try {
               copy = PCRegistry.newInstance(type, copySM, false);
               int[] fields = new int[meta.getFields().length];

               for(int i = 0; i < fields.length; fields[i] = i++) {
               }

               copy.pcCopyFields(pc, fields);
               state = PCState.ECOPY;
               copy.pcReplaceStateManager((StateManager)null);
            } finally {
               if (orig == null) {
                  pc.pcReplaceStateManager((StateManager)null);
               }

            }
         } else {
            copy = PCRegistry.newInstance(type, sm, false);
            if ((this._flags & 2) != 0 && !this._optimistic) {
               state = PCState.ECLEAN;
            } else {
               state = PCState.ENONTRANS;
            }
         }

         sm.initialize(copy, state);
         copySM = sm;
      } catch (OpenJPAException var28) {
         throw var28;
      } catch (RuntimeException var29) {
         throw new GeneralException(var29);
      } finally {
         this.endOperation();
      }

      return copySM;
   }

   OpenJPAStateManager copy(OpenJPAStateManager copy, PCState state) {
      this.beginOperation(true);

      StateManagerImpl var12;
      try {
         this.assertOpen();
         Object oid = copy.fetchObjectId();
         Class type = copy.getManagedInstance().getClass();
         if (oid == null) {
            throw new InternalException();
         }

         StateManagerImpl sm = null;
         if (!copy.isEmbedded()) {
            sm = this.getStateManagerImplById(oid, true);
         }

         if (sm == null) {
            MetaDataRepository repos = this._conf.getMetaDataRepositoryInstance();
            ClassMetaData meta = repos.getMetaData(type, this._loader, true);
            sm = new StateManagerImpl(oid, meta, this);
            sm.setObjectId(oid);
            sm.initialize(sm.getMetaData().getDescribedType(), state);
         }

         var12 = sm;
      } finally {
         this.endOperation();
      }

      return var12;
   }

   public void refreshAll(Collection objs, OpCallbacks call) {
      if (!objs.isEmpty()) {
         this.beginOperation(true);

         try {
            this.assertNontransactionalRead();
            Iterator itr = objs.iterator();

            while(itr.hasNext()) {
               this.gatherCascadeRefresh(itr.next(), call);
            }

            if (this._operating.isEmpty()) {
               return;
            }

            if (this._operating.size() == 1) {
               this.refreshInternal(this._operating.iterator().next(), call);
            } else {
               this.refreshInternal((Collection)this._operating, call);
            }
         } finally {
            this.endOperation();
         }

      }
   }

   public void refresh(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            this.assertNontransactionalRead();
            this.gatherCascadeRefresh(obj, call);
            if (!this._operating.isEmpty()) {
               if (this._operating.size() == 1) {
                  this.refreshInternal(this._operating.iterator().next(), call);
               } else {
                  this.refreshInternal((Collection)this._operating, call);
               }

               return;
            }
         } finally {
            this.endOperation();
         }

      }
   }

   void gatherCascadeRefresh(Object obj, OpCallbacks call) {
      if (obj != null) {
         if (this._operating.add(obj)) {
            StateManagerImpl sm = this.getStateManagerImpl(obj, false);
            int action = this.processArgument(2, obj, sm, call);
            if ((action & 2) != 0) {
               if (sm != null) {
                  sm.gatherCascadeRefresh(call);
               } else {
                  this.cascadeTransient(2, obj, call, "refresh");
               }

            }
         }
      }
   }

   protected void refreshInternal(Collection objs, OpCallbacks call) {
      List exceps = null;

      try {
         Collection load = null;
         Iterator itr = objs.iterator();

         StateManagerImpl sm;
         while(itr.hasNext()) {
            Object obj = itr.next();
            if (obj != null) {
               try {
                  sm = this.getStateManagerImpl(obj, true);
                  if ((this.processArgument(2, obj, sm, call) & 4) != 0) {
                     if (sm != null) {
                        if (sm.isDetached()) {
                           throw this.newDetachedException(obj, "refresh");
                        }

                        if (sm.beforeRefresh(true)) {
                           if (load == null) {
                              load = new ArrayList(objs.size());
                           }

                           load.add(sm);
                        }
                     } else if (this.assertPersistenceCapable(obj).pcIsDetached() == Boolean.TRUE) {
                        throw this.newDetachedException(obj, "refresh");
                     }
                  }
               } catch (OpenJPAException var12) {
                  exceps = this.add(exceps, var12);
               }
            }
         }

         if (load != null) {
            Collection failed = this._store.loadAll(load, (PCState)null, 3, this._fc, (Object)null);
            if (failed != null && !failed.isEmpty()) {
               exceps = this.add(exceps, newObjectNotFoundException(failed));
            }

            Iterator itr = load.iterator();

            label91:
            while(true) {
               do {
                  if (!itr.hasNext()) {
                     break label91;
                  }

                  sm = (StateManagerImpl)itr.next();
               } while(failed != null && failed.contains(sm.getId()));

               try {
                  sm.afterRefresh();
                  sm.load(this._fc, 0, (BitSet)null, (Object)null, false);
               } catch (OpenJPAException var11) {
                  exceps = this.add(exceps, var11);
               }
            }
         }

         itr = objs.iterator();

         while(itr.hasNext()) {
            try {
               sm = this.getStateManagerImpl(itr.next(), true);
               if (sm != null && !sm.isDetached()) {
                  this.fireLifecycleEvent(sm.getManagedInstance(), (Object)null, sm.getMetaData(), 17);
               }
            } catch (OpenJPAException var10) {
               exceps = this.add(exceps, var10);
            }
         }
      } catch (OpenJPAException var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw new GeneralException(var14);
      }

      this.throwNestedExceptions(exceps, false);
   }

   protected void refreshInternal(Object obj, OpCallbacks call) {
      try {
         StateManagerImpl sm = this.getStateManagerImpl(obj, true);
         if ((this.processArgument(2, obj, sm, call) & 4) != 0) {
            if (sm != null) {
               if (sm.isDetached()) {
                  throw this.newDetachedException(obj, "refresh");
               }

               if (sm.beforeRefresh(false)) {
                  sm.load(this._fc, 0, (BitSet)null, (Object)null, false);
                  sm.afterRefresh();
               }

               this.fireLifecycleEvent(sm.getManagedInstance(), (Object)null, sm.getMetaData(), 17);
            } else if (this.assertPersistenceCapable(obj).pcIsDetached() == Boolean.TRUE) {
               throw this.newDetachedException(obj, "refresh");
            }

         }
      } catch (OpenJPAException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw new GeneralException(var5);
      }
   }

   public void retrieveAll(Collection objs, boolean dfgOnly, OpCallbacks call) {
      if (!objs.isEmpty()) {
         if (objs.size() == 1) {
            this.retrieve(objs.iterator().next(), dfgOnly, call);
         } else {
            List exceps = null;
            this.beginOperation(true);

            try {
               this.assertOpen();
               this.assertNontransactionalRead();
               Collection load = null;
               Collection sms = new ArrayList(objs.size());
               Iterator itr = objs.iterator();

               StateManagerImpl sm;
               while(itr.hasNext()) {
                  Object obj = itr.next();
                  if (obj != null) {
                     try {
                        sm = this.getStateManagerImpl(obj, true);
                        if ((this.processArgument(3, obj, sm, call) & 4) != 0) {
                           if (sm != null) {
                              if (sm.isDetached()) {
                                 throw this.newDetachedException(obj, "retrieve");
                              }

                              if (sm.isPersistent()) {
                                 sms.add(sm);
                                 if (sm.getPCState() == PCState.HOLLOW) {
                                    if (load == null) {
                                       load = new ArrayList();
                                    }

                                    load.add(sm);
                                 }
                              }
                           } else if (this.assertPersistenceCapable(obj).pcIsDetached() == Boolean.TRUE) {
                              throw this.newDetachedException(obj, "retrieve");
                           }
                        }
                     } catch (UserException var21) {
                        exceps = this.add(exceps, var21);
                     }
                  }
               }

               Collection failed = null;
               if (load != null) {
                  DelegatingStoreManager var10000;
                  byte var27;
                  if (dfgOnly) {
                     var10000 = this._store;
                     var27 = 1;
                  } else {
                     var10000 = this._store;
                     var27 = 2;
                  }

                  int mode = var27;
                  failed = this._store.loadAll(load, (PCState)null, mode, this._fc, (Object)null);
                  if (failed != null && !failed.isEmpty()) {
                     exceps = this.add(exceps, newObjectNotFoundException(failed));
                  }
               }

               Iterator itr = sms.iterator();

               while(itr.hasNext()) {
                  sm = (StateManagerImpl)itr.next();
                  if (failed == null || !failed.contains(sm.getId())) {
                     int mode = dfgOnly ? 0 : 1;

                     try {
                        sm.beforeRead(-1);
                        sm.load(this._fc, mode, (BitSet)null, (Object)null, false);
                     } catch (OpenJPAException var20) {
                        exceps = this.add(exceps, var20);
                     }
                  }
               }
            } catch (OpenJPAException var22) {
               throw var22;
            } catch (RuntimeException var23) {
               throw new GeneralException(var23);
            } finally {
               this.endOperation();
            }

            this.throwNestedExceptions(exceps, false);
         }
      }
   }

   public void retrieve(Object obj, boolean dfgOnly, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            this.assertOpen();
            this.assertNontransactionalRead();
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if ((this.processArgument(3, obj, sm, call) & 4) != 0) {
               if (sm != null) {
                  if (sm.isDetached()) {
                     throw this.newDetachedException(obj, "retrieve");
                  }

                  if (sm.isPersistent()) {
                     int mode = dfgOnly ? 0 : 1;
                     sm.beforeRead(-1);
                     sm.load(this._fc, mode, (BitSet)null, (Object)null, false);
                  }
               } else if (this.assertPersistenceCapable(obj).pcIsDetached() == Boolean.TRUE) {
                  throw this.newDetachedException(obj, "retrieve");
               }

               return;
            }
         } catch (OpenJPAException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw new GeneralException(var12);
         } finally {
            this.endOperation();
         }

      }
   }

   public void evictAll(OpCallbacks call) {
      this.beginOperation(false);

      try {
         Collection c = this.getManagedStates();
         Iterator itr = c.iterator();

         while(itr.hasNext()) {
            StateManagerImpl sm = (StateManagerImpl)itr.next();
            if (sm.isPersistent() && !sm.isDirty()) {
               this.evict(sm.getManagedInstance(), call);
            }
         }
      } finally {
         this.endOperation();
      }

   }

   public void evictAll(Collection objs, OpCallbacks call) {
      List exceps = null;
      this.beginOperation(false);

      try {
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            try {
               this.evict(itr.next(), call);
            } catch (UserException var10) {
               exceps = this.add(exceps, var10);
            }
         }
      } finally {
         this.endOperation();
      }

      this.throwNestedExceptions(exceps, false);
   }

   public void evictAll(Extent extent, OpCallbacks call) {
      if (extent != null) {
         this.beginOperation(false);

         try {
            Collection c = this.getManagedStates();
            Iterator itr = c.iterator();

            while(true) {
               StateManagerImpl sm;
               Class cls;
               do {
                  do {
                     do {
                        if (!itr.hasNext()) {
                           return;
                        }

                        sm = (StateManagerImpl)itr.next();
                     } while(!sm.isPersistent());
                  } while(sm.isDirty());

                  cls = sm.getMetaData().getDescribedType();
               } while(cls != extent.getElementType() && (!extent.hasSubclasses() || !extent.getElementType().isAssignableFrom(cls)));

               this.evict(sm.getManagedInstance(), call);
            }
         } finally {
            this.endOperation();
         }
      }
   }

   public void evict(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(false);

         try {
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if ((this.processArgument(5, obj, sm, call) & 4) == 0) {
               return;
            }

            if (sm != null) {
               sm.evict();
               if (this._evictDataCache && sm.getObjectId() != null) {
                  DataCache cache = sm.getMetaData().getDataCache();
                  if (cache != null) {
                     cache.remove(sm.getObjectId());
                     return;
                  }
               }

               return;
            }
         } catch (OpenJPAException var10) {
            throw var10;
         } catch (RuntimeException var11) {
            throw new GeneralException(var11);
         } finally {
            this.endOperation();
         }

      }
   }

   public Object detach(Object obj, OpCallbacks call) {
      if (obj == null) {
         return null;
      } else {
         if (call == null) {
            call = this._call;
         }

         this.beginOperation(true);

         Object var3;
         try {
            var3 = (new DetachManager(this, false, call)).detach(obj);
         } catch (OpenJPAException var9) {
            throw var9;
         } catch (RuntimeException var10) {
            throw new GeneralException(var10);
         } finally {
            this.endOperation();
         }

         return var3;
      }
   }

   public Object[] detachAll(Collection objs, OpCallbacks call) {
      if (objs == null) {
         return null;
      } else if (objs.isEmpty()) {
         return EMPTY_OBJECTS;
      } else {
         if (call == null) {
            call = this._call;
         }

         this.beginOperation(true);

         Object[] var3;
         try {
            var3 = (new DetachManager(this, false, call)).detachAll(objs);
         } catch (OpenJPAException var9) {
            throw var9;
         } catch (RuntimeException var10) {
            throw new GeneralException(var10);
         } finally {
            this.endOperation();
         }

         return var3;
      }
   }

   public void detachAll(OpCallbacks call) {
      this.detachAll(call, true);
   }

   public void detachAll(OpCallbacks call, boolean flush) {
      this.beginOperation(true);

      try {
         if (flush && (this._flags & 512) != 0) {
            this.flush();
         }

         this.detachAllInternal(call);
      } catch (OpenJPAException var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw new GeneralException(var10);
      } finally {
         this.endOperation();
      }

   }

   private void detachAllInternal(OpCallbacks call) {
      Collection states = this.getManagedStates();
      Iterator itr = states.iterator();

      while(itr.hasNext()) {
         StateManagerImpl sm = (StateManagerImpl)itr.next();
         if (!sm.isPersistent()) {
            itr.remove();
         } else if (!sm.getMetaData().isDetachable()) {
            sm.release(true);
            itr.remove();
         }
      }

      if (!states.isEmpty()) {
         if (call == null) {
            call = this._call;
         }

         (new DetachManager(this, true, call)).detachAll(new ManagedObjectCollection(states));
      }
   }

   public Object attach(Object obj, boolean copyNew, OpCallbacks call) {
      if (obj == null) {
         return null;
      } else {
         this.beginOperation(true);

         Object var4;
         try {
            this.assertWriteOperation();

            try {
               var4 = (new AttachManager(this, copyNew, call)).attach(obj);
            } catch (OptimisticException var11) {
               this.setRollbackOnly(var11);
               throw var11.setFatal(true);
            } catch (OpenJPAException var12) {
               throw var12;
            } catch (RuntimeException var13) {
               throw new GeneralException(var13);
            }
         } finally {
            this.endOperation();
         }

         return var4;
      }
   }

   public Object[] attachAll(Collection objs, boolean copyNew, OpCallbacks call) {
      if (objs == null) {
         return null;
      } else if (objs.isEmpty()) {
         return EMPTY_OBJECTS;
      } else {
         this.beginOperation(true);

         Object[] var4;
         try {
            this.assertWriteOperation();

            try {
               var4 = (new AttachManager(this, copyNew, call)).attachAll(objs);
            } catch (OptimisticException var11) {
               this.setRollbackOnly(var11);
               throw var11.setFatal(true);
            } catch (OpenJPAException var12) {
               throw var12;
            } catch (RuntimeException var13) {
               throw new GeneralException(var13);
            }
         } finally {
            this.endOperation();
         }

         return var4;
      }
   }

   public void nontransactionalAll(Collection objs, OpCallbacks call) {
      this.beginOperation(true);

      try {
         List exceps = null;
         Iterator itr = objs.iterator();

         while(itr.hasNext()) {
            try {
               this.nontransactional(itr.next(), call);
            } catch (UserException var10) {
               exceps = this.add(exceps, var10);
            }
         }

         this.throwNestedExceptions(exceps, false);
      } finally {
         this.endOperation();
      }

   }

   public void nontransactional(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if ((this.processArgument(8, obj, sm, call) & 4) != 0) {
               if (sm != null) {
                  sm.nontransactional();
               }

               return;
            }
         } catch (OpenJPAException var9) {
            throw var9;
         } catch (RuntimeException var10) {
            throw new GeneralException(var10);
         } finally {
            this.endOperation();
         }

      }
   }

   public void transactionalAll(Collection objs, boolean updateVersion, OpCallbacks call) {
      if (!objs.isEmpty()) {
         if (objs.size() == 1) {
            this.transactional(objs.iterator().next(), updateVersion, call);
         } else {
            this.beginOperation(true);

            try {
               Collection load = null;
               Collection sms = new ArrayList(objs.size());
               List exceps = null;
               Iterator itr = objs.iterator();

               while(itr.hasNext()) {
                  Object obj = itr.next();
                  if (obj != null) {
                     try {
                        StateManagerImpl sm = this.getStateManagerImpl(obj, true);
                        if ((this.processArgument(9, obj, sm, call) & 4) != 0) {
                           if (sm == null) {
                              ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(obj.getClass(), this._loader, true);
                              sm = new StateManagerImpl(BrokerImpl.StateManagerId.newInstance(this), meta, this);
                              sm.initialize(this.assertPersistenceCapable(obj), PCState.TCLEAN);
                           } else if (sm.isPersistent()) {
                              this.assertActiveTransaction();
                              sms.add(sm);
                              if (sm.getPCState() == PCState.HOLLOW) {
                                 if (load == null) {
                                    load = new ArrayList();
                                 }

                                 load.add(sm);
                              }

                              sm.setCheckVersion(true);
                              if (updateVersion) {
                                 sm.setUpdateVersion(true);
                              }

                              this._flags |= 512;
                           }
                        }
                     } catch (UserException var18) {
                        exceps = this.add(exceps, var18);
                     }
                  }
               }

               Collection failed = null;
               if (load != null) {
                  DelegatingStoreManager var10003 = this._store;
                  failed = this._store.loadAll(load, (PCState)null, 0, this._fc, (Object)null);
                  if (failed != null && !failed.isEmpty()) {
                     exceps = this.add(exceps, newObjectNotFoundException(failed));
                  }
               }

               this.transactionalStatesAll(sms, failed, exceps);
            } catch (OpenJPAException var19) {
               throw var19;
            } catch (RuntimeException var20) {
               throw new GeneralException(var20);
            } finally {
               this.endOperation();
            }

         }
      }
   }

   public void transactional(Object obj, boolean updateVersion, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if ((this.processArgument(9, obj, sm, call) & 4) == 0) {
               return;
            }

            if (sm != null && sm.isPersistent()) {
               this.assertActiveTransaction();
               sm.transactional();
               sm.load(this._fc, 0, (BitSet)null, (Object)null, false);
               sm.setCheckVersion(true);
               if (updateVersion) {
                  sm.setUpdateVersion(true);
               }

               this._flags |= 512;
            } else if (sm == null) {
               ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(obj.getClass(), this._loader, true);
               Object id = BrokerImpl.StateManagerId.newInstance(this);
               sm = new StateManagerImpl(id, meta, this);
               sm.initialize(this.assertPersistenceCapable(obj), PCState.TCLEAN);
            }
         } catch (OpenJPAException var12) {
            throw var12;
         } catch (RuntimeException var13) {
            throw new GeneralException(var13);
         } finally {
            this.endOperation();
         }

      }
   }

   private void transactionalStatesAll(Collection sms, Collection failed, List exceps) {
      Iterator itr = sms.iterator();

      while(true) {
         StateManagerImpl sm;
         do {
            if (!itr.hasNext()) {
               this.throwNestedExceptions(exceps, false);
               return;
            }

            sm = (StateManagerImpl)itr.next();
         } while(failed != null && failed.contains(sm.getId()));

         try {
            sm.transactional();
            sm.load(this._fc, 0, (BitSet)null, (Object)null, false);
         } catch (OpenJPAException var7) {
            exceps = this.add(exceps, var7);
         }
      }
   }

   public Extent newExtent(Class type, boolean subclasses) {
      return this.newExtent(type, subclasses, (FetchConfiguration)null);
   }

   private Extent newExtent(Class type, boolean subclasses, FetchConfiguration fetch) {
      this.beginOperation(true);

      ExtentImpl var5;
      try {
         ExtentImpl extent = new ExtentImpl(this, type, subclasses, fetch);
         if (this._extents == null) {
            this._extents = new ReferenceHashSet(2);
         }

         this._extents.add(extent);
         var5 = extent;
      } catch (OpenJPAException var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw new GeneralException(var12);
      } finally {
         this.endOperation();
      }

      return var5;
   }

   public Iterator extentIterator(Class type, boolean subclasses, FetchConfiguration fetch, boolean ignoreChanges) {
      Extent extent = this.newExtent(type, subclasses, fetch);
      extent.setIgnoreChanges(ignoreChanges);
      return extent.iterator();
   }

   public Query newQuery(String lang, Class cls, Object query) {
      Query q = this.newQuery(lang, query);
      q.setCandidateType(cls, true);
      return q;
   }

   public Query newQuery(String lang, Object query) {
      if (!(query instanceof Extent) && !(query instanceof Class)) {
         this.beginOperation(false);

         QueryImpl var5;
         try {
            StoreQuery sq = this._store.newQuery(lang);
            if (sq == null) {
               ExpressionParser ep = QueryLanguages.parserForLanguage(lang);
               if (ep != null) {
                  sq = new ExpressionStoreQuery(ep);
               } else {
                  if (!"openjpa.MethodQL".equals(lang)) {
                     throw new UnsupportedException(lang);
                  }

                  sq = new MethodStoreQuery();
               }
            }

            Query q = this.newQueryImpl(lang, (StoreQuery)sq);
            q.setIgnoreChanges(this._ignoreChanges);
            if (query != null) {
               q.setQuery(query);
            }

            if (this._queries == null) {
               this._queries = new ReferenceHashSet(2);
            }

            this._queries.add(q);
            var5 = q;
         } catch (OpenJPAException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw new GeneralException(var12);
         } finally {
            this.endOperation();
         }

         return var5;
      } else {
         throw new UserException(_loc.get("bad-new-query"));
      }
   }

   protected QueryImpl newQueryImpl(String lang, StoreQuery sq) {
      return new QueryImpl(this, lang, sq);
   }

   public Seq getIdentitySequence(ClassMetaData meta) {
      return meta == null ? null : this.getSequence(meta, (FieldMetaData)null);
   }

   public Seq getValueSequence(FieldMetaData fmd) {
      return fmd == null ? null : this.getSequence(fmd.getDefiningMetaData(), fmd);
   }

   private Seq getSequence(ClassMetaData meta, FieldMetaData fmd) {
      int strategy;
      if (fmd == null) {
         strategy = meta.getIdentityStrategy();
      } else {
         strategy = fmd.getValueStrategy();
      }

      SequenceMetaData smd;
      switch (strategy) {
         case 2:
            smd = fmd == null ? meta.getIdentitySequenceMetaData() : fmd.getValueSequenceMetaData();
            return smd.getInstance(this._loader);
         case 3:
         case 4:
         default:
            if (fmd != null) {
               return this._store.getValueSequence(fmd);
            } else {
               FieldMetaData[] pks = meta.getPrimaryKeyFields();
               if (pks != null && pks.length == 1) {
                  smd = pks[0].getValueSequenceMetaData();
               } else {
                  smd = meta.getIdentitySequenceMetaData();
               }

               if (smd != null) {
                  return smd.getInstance(this._loader);
               }

               return this._store.getDataStoreIdSequence(meta);
            }
         case 5:
            return UUIDStringSeq.getInstance();
         case 6:
            return UUIDHexSeq.getInstance();
      }
   }

   public void lock(Object obj, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            this.lock(obj, this._fc.getWriteLockLevel(), this._fc.getLockTimeout(), call);
         } finally {
            this.endOperation();
         }

      }
   }

   public void lock(Object obj, int level, int timeout, OpCallbacks call) {
      if (obj != null) {
         this.beginOperation(true);

         try {
            this.assertActiveTransaction();
            StateManagerImpl sm = this.getStateManagerImpl(obj, true);
            if ((this.processArgument(10, obj, sm, call) & 4) == 0) {
               return;
            }

            if (sm == null || !sm.isPersistent()) {
               return;
            }

            this._lm.lock(sm, level, timeout, (Object)null);
            sm.readLocked(level, level);
         } catch (OpenJPAException var11) {
            throw var11;
         } catch (RuntimeException var12) {
            throw new GeneralException(var12);
         } finally {
            this.endOperation();
         }

      }
   }

   public void lockAll(Collection objs, OpCallbacks call) {
      if (!objs.isEmpty()) {
         this.beginOperation(true);

         try {
            this.lockAll(objs, this._fc.getWriteLockLevel(), this._fc.getLockTimeout(), call);
         } finally {
            this.endOperation();
         }

      }
   }

   public void lockAll(Collection objs, int level, int timeout, OpCallbacks call) {
      if (!objs.isEmpty()) {
         if (objs.size() == 1) {
            this.lock(objs.iterator().next(), level, timeout, call);
         } else {
            this.beginOperation(true);

            try {
               this.assertActiveTransaction();
               Collection sms = new ArrayList(objs.size());
               Iterator itr = objs.iterator();

               while(itr.hasNext()) {
                  Object obj = itr.next();
                  if (obj != null) {
                     StateManagerImpl sm = this.getStateManagerImpl(obj, true);
                     if ((this.processArgument(10, obj, sm, call) & 4) != 0 && sm != null && sm.isPersistent()) {
                        sms.add(sm);
                     }
                  }
               }

               this._lm.lockAll(sms, level, timeout, (Object)null);
               itr = sms.iterator();

               while(itr.hasNext()) {
                  ((StateManagerImpl)itr.next()).readLocked(level, level);
               }
            } catch (OpenJPAException var14) {
               throw var14;
            } catch (RuntimeException var15) {
               throw new GeneralException(var15);
            } finally {
               this.endOperation();
            }

         }
      }
   }

   public boolean cancelAll() {
      this.assertOpen();

      try {
         if ((this._flags & 128) != 0) {
            this.setRollbackOnlyInternal(new UserException());
         }

         return this._store.cancelAll();
      } catch (OpenJPAException var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw new StoreException(var3);
      }
   }

   public Object getConnection() {
      this.assertOpen();
      Collection var10000 = this._conf.supportedOptions();
      OpenJPAConfiguration var10001 = this._conf;
      if (!var10000.contains("openjpa.option.DataStoreConnection")) {
         throw new UnsupportedException(_loc.get("conn-not-supported"));
      } else {
         return this._store.getClientConnection();
      }
   }

   public boolean hasConnection() {
      this.assertOpen();
      return (this._flags & 2048) != 0;
   }

   private void retainConnection() {
      if ((this._flags & 2048) == 0) {
         this._store.retainConnection();
         this._flags |= 2048;
      }

   }

   private void releaseConnection() {
      if ((this._flags & 2048) != 0) {
         this._store.releaseConnection();
         this._flags &= -2049;
      }

   }

   public Collection getManagedObjects() {
      this.beginOperation(false);

      ManagedObjectCollection var1;
      try {
         var1 = new ManagedObjectCollection(this.getManagedStates());
      } finally {
         this.endOperation();
      }

      return var1;
   }

   public Collection getTransactionalObjects() {
      this.beginOperation(false);

      ManagedObjectCollection var1;
      try {
         var1 = new ManagedObjectCollection(this.getTransactionalStates());
      } finally {
         this.endOperation();
      }

      return var1;
   }

   public Collection getPendingTransactionalObjects() {
      this.beginOperation(false);

      ManagedObjectCollection var1;
      try {
         var1 = new ManagedObjectCollection(this.getPendingTransactionalStates());
      } finally {
         this.endOperation();
      }

      return var1;
   }

   public Collection getDirtyObjects() {
      this.beginOperation(false);

      ManagedObjectCollection var1;
      try {
         var1 = new ManagedObjectCollection(this.getDirtyStates());
      } finally {
         this.endOperation();
      }

      return var1;
   }

   public boolean getOrderDirtyObjects() {
      return this._orderDirty;
   }

   public void setOrderDirtyObjects(boolean order) {
      this._orderDirty = order;
   }

   protected Collection getManagedStates() {
      return this._cache.copy();
   }

   protected Collection getTransactionalStates() {
      return (Collection)(!this.hasTransactionalObjects() ? Collections.EMPTY_LIST : this._transCache.copy());
   }

   private boolean hasTransactionalObjects() {
      this._cache.dirtyCheck();
      return this._transCache != null;
   }

   protected Collection getDirtyStates() {
      return (Collection)(!this.hasTransactionalObjects() ? Collections.EMPTY_LIST : this._transCache.copyDirty());
   }

   protected Collection getPendingTransactionalStates() {
      return (Collection)(this._pending == null ? Collections.EMPTY_LIST : new ArrayList(this._pending));
   }

   void setStateManager(Object id, StateManagerImpl sm, int status) {
      this.lock();

      try {
         switch (status) {
            case 0:
               this._cache.add(sm);
               break;
            case 1:
               this._cache.remove(id, sm);
               break;
            case 2:
               this.assignObjectId(this._cache, id, sm);
               break;
            case 3:
               this._cache.commitNew(id, sm);
               break;
            default:
               throw new InternalException();
         }
      } finally {
         this.unlock();
      }

   }

   void addToTransaction(StateManagerImpl sm) {
      if (!sm.isDirty()) {
         this.lock();

         try {
            if (!this.hasTransactionalObjects()) {
               this._transCache = new TransactionalCache(this._orderDirty);
            }

            this._transCache.addClean(sm);
         } finally {
            this.unlock();
         }

      }
   }

   void removeFromTransaction(StateManagerImpl sm) {
      this.lock();

      try {
         if (this._transCache != null) {
            this._transCache.remove(sm);
         }

         if (this._derefCache != null && !sm.isPersistent()) {
            this._derefCache.remove(sm);
         }
      } finally {
         this.unlock();
      }

   }

   void setDirty(StateManagerImpl sm, boolean firstDirty) {
      if (sm.isPersistent()) {
         this._flags |= 512;
      }

      if (this._savepoints != null && !this._savepoints.isEmpty()) {
         if (this._savepointCache == null) {
            this._savepointCache = new HashSet();
         }

         this._savepointCache.add(sm);
      }

      if (firstDirty && sm.isTransactional()) {
         this.lock();

         try {
            if (!this.hasTransactionalObjects()) {
               this._transCache = new TransactionalCache(this._orderDirty);
            }

            this._transCache.addDirty(sm);
            if (sm.isNew()) {
               if (this._persistedClss == null) {
                  this._persistedClss = new HashSet();
               }

               this._persistedClss.add(sm.getMetaData().getDescribedType());
            } else if (sm.isDeleted()) {
               if (this._deletedClss == null) {
                  this._deletedClss = new HashSet();
               }

               this._deletedClss.add(sm.getMetaData().getDescribedType());
            } else {
               if (this._updatedClss == null) {
                  this._updatedClss = new HashSet();
               }

               this._updatedClss.add(sm.getMetaData().getDescribedType());
            }

            if ((this._flags & 16) != 0) {
               if (this._transAdditions == null) {
                  this._transAdditions = new HashSet();
               }

               this._transAdditions.add(sm);
            }
         } finally {
            this.unlock();
         }
      }

   }

   void addToPendingTransaction(StateManagerImpl sm) {
      this.lock();

      try {
         if (this._pending == null) {
            this._pending = new HashSet();
         }

         this._pending.add(sm);
      } finally {
         this.unlock();
      }

   }

   void removeFromPendingTransaction(StateManagerImpl sm) {
      this.lock();

      try {
         if (this._pending != null) {
            this._pending.remove(sm);
         }

         if (this._derefCache != null && !sm.isPersistent()) {
            this._derefCache.remove(sm);
         }
      } finally {
         this.unlock();
      }

   }

   void addDereferencedDependent(StateManagerImpl sm) {
      this.lock();

      try {
         if ((this._flags & 32) != 0) {
            if (this._derefAdditions == null) {
               this._derefAdditions = new HashSet();
            }

            this._derefAdditions.add(sm);
         } else {
            if (this._derefCache == null) {
               this._derefCache = new HashSet();
            }

            this._derefCache.add(sm);
         }
      } finally {
         this.unlock();
      }

   }

   void removeDereferencedDependent(StateManagerImpl sm) {
      this.lock();

      try {
         boolean removed = false;
         if (this._derefAdditions != null) {
            removed = this._derefAdditions.remove(sm);
         }

         if (!removed && (this._derefCache == null || !this._derefCache.remove(sm))) {
            throw (new InvalidStateException(_loc.get("not-derefed", (Object)Exceptions.toString(sm.getManagedInstance())))).setFailedObject(sm.getManagedInstance()).setFatal(true);
         }
      } finally {
         this.unlock();
      }

   }

   public void dirtyType(Class cls) {
      if (cls != null) {
         this.beginOperation(false);

         try {
            if (this._updatedClss == null) {
               this._updatedClss = new HashSet();
            }

            this._updatedClss.add(cls);
         } finally {
            this.endOperation();
         }

      }
   }

   public Collection getPersistedTypes() {
      return (Collection)(this._persistedClss != null && !this._persistedClss.isEmpty() ? Collections.unmodifiableCollection(this._persistedClss) : Collections.EMPTY_LIST);
   }

   public Collection getUpdatedTypes() {
      return (Collection)(this._updatedClss != null && !this._updatedClss.isEmpty() ? Collections.unmodifiableCollection(this._updatedClss) : Collections.EMPTY_LIST);
   }

   public Collection getDeletedTypes() {
      return (Collection)(this._deletedClss != null && !this._deletedClss.isEmpty() ? Collections.unmodifiableCollection(this._deletedClss) : Collections.EMPTY_LIST);
   }

   public boolean isClosed() {
      return this._closed;
   }

   public boolean isCloseInvoked() {
      return this._closed || (this._flags & 8) != 0;
   }

   public void close() {
      this.beginOperation(false);

      try {
         if (!this._managed && (this._flags & 2) != 0) {
            throw new InvalidStateException(_loc.get("active"));
         }

         this._flags |= 8;
         if ((this._flags & 2) == 0) {
            this.free();
         }
      } finally {
         this.endOperation();
      }

   }

   protected void free() {
      RuntimeException err = null;
      if ((this._autoDetach & 2) != 0) {
         try {
            this.detachAllInternal(this._call);
         } catch (RuntimeException var9) {
            err = var9;
         }
      }

      this._sync = null;
      this._userObjects = null;
      this._cache.clear();
      this._transCache = null;
      this._persistedClss = null;
      this._updatedClss = null;
      this._deletedClss = null;
      this._derefCache = null;
      this._pending = null;
      this._loader = null;
      this._transEventManager = null;
      this._lifeEventManager = null;

      while(this._savepoints != null && !this._savepoints.isEmpty()) {
         OpenJPASavepoint save = (OpenJPASavepoint)this._savepoints.remove(this._savepoints.size() - 1);
         save.release(false);
      }

      this._savepoints = null;
      this._savepointCache = null;
      if (this._queries != null) {
         Iterator itr = this._queries.iterator();

         while(itr.hasNext()) {
            try {
               ((Query)itr.next()).closeResources();
            } catch (RuntimeException var8) {
            }
         }

         this._queries = null;
      }

      if (this._extents != null) {
         Iterator itr = this._extents.iterator();

         while(itr.hasNext()) {
            Extent e = (Extent)itr.next();

            try {
               e.closeAll();
            } catch (RuntimeException var7) {
            }
         }

         this._extents = null;
      }

      try {
         this.releaseConnection();
      } catch (RuntimeException var6) {
      }

      this._lm.close();
      this._store.close();
      this._flags = 0;
      this._closed = true;
      if (this._log.isTraceEnabled()) {
         this._closedException = new IllegalStateException();
      }

      this._factory.releaseBroker(this);
      if (err != null) {
         throw err;
      }
   }

   public void lock() {
      if (this._lock != null) {
         this._lock.lock();
      }

   }

   public void unlock() {
      if (this._lock != null) {
         this._lock.unlock();
      }

   }

   public Object newInstance(Class cls) {
      this.assertOpen();
      if (!cls.isInterface() && Modifier.isAbstract(cls.getModifiers())) {
         throw new UnsupportedOperationException(_loc.get("new-abstract", (Object)cls).getMessage());
      } else {
         if (!PCRegistry.isRegistered(cls)) {
            try {
               Class.forName(cls.getName(), true, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(cls)));
            } catch (Throwable var5) {
            }
         }

         if (this._conf.getMetaDataRepositoryInstance().getMetaData(cls, this.getClassLoader(), false) == null) {
            throw new IllegalArgumentException(_loc.get("no-interface-metadata", (Object)cls.getName()).getMessage());
         } else {
            try {
               return PCRegistry.newInstance(cls, (StateManager)null, false);
            } catch (IllegalStateException var4) {
               IllegalArgumentException iae = new IllegalArgumentException(var4.getMessage());
               iae.setStackTrace(var4.getStackTrace());
               throw iae;
            }
         }
      }
   }

   public Object getObjectId(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcFetchObjectId() : null;
   }

   public int getLockLevel(Object o) {
      this.assertOpen();
      if (o == null) {
         return 0;
      } else {
         OpenJPAStateManager sm = this.getStateManager(o);
         return sm == null ? 0 : this.getLockManager().getLockLevel(sm);
      }
   }

   public Object getVersion(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcGetVersion() : null;
   }

   public boolean isDirty(Object obj) {
      this.assertOpen();
      if (ImplHelper.isManageable(obj)) {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(obj, this._conf);
         return pc.pcIsDirty();
      } else {
         return false;
      }
   }

   public boolean isTransactional(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcIsTransactional() : false;
   }

   public boolean isPersistent(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcIsPersistent() : false;
   }

   public boolean isNew(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcIsNew() : false;
   }

   public boolean isDeleted(Object obj) {
      this.assertOpen();
      return ImplHelper.isManageable(obj) ? ImplHelper.toPersistenceCapable(obj, this._conf).pcIsDeleted() : false;
   }

   public boolean isDetached(Object obj) {
      if (!ImplHelper.isManageable(obj)) {
         return false;
      } else {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(obj, this._conf);
         Boolean detached = pc.pcIsDetached();
         if (detached != null) {
            return detached;
         } else {
            ClassMetaData meta = this._conf.getMetaDataRepositoryInstance().getMetaData(ImplHelper.getManagedInstance(pc).getClass(), this._loader, true);
            Object oid = ApplicationIds.create(pc, meta);
            if (oid == null) {
               return false;
            } else {
               return this.find(oid, (FetchConfiguration)null, EXCLUDE_ALL, (Object)null, 0) != null;
            }
         }
      }
   }

   public OpenJPAStateManager getStateManager(Object obj) {
      this.assertOpen();
      return this.getStateManagerImpl(obj, false);
   }

   protected StateManagerImpl getStateManagerImpl(Object obj, boolean assertThisContext) {
      if (ImplHelper.isManageable(obj)) {
         PersistenceCapable pc = ImplHelper.toPersistenceCapable(obj, this._conf);
         if (pc.pcGetGenericContext() == this) {
            return (StateManagerImpl)pc.pcGetStateManager();
         }

         if (assertThisContext && pc.pcGetGenericContext() != null) {
            throw (new UserException(_loc.get("not-managed", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
         }
      }

      return null;
   }

   protected StateManagerImpl getStateManagerImplById(Object oid, boolean allowNew) {
      return this._cache.getById(oid, allowNew);
   }

   protected PersistenceCapable assertPersistenceCapable(Object obj) {
      if (obj == null) {
         return null;
      } else if (ImplHelper.isManageable(obj)) {
         return ImplHelper.toPersistenceCapable(obj, this._conf);
      } else {
         Class[] intfs = obj.getClass().getInterfaces();

         for(int i = 0; intfs != null && i < intfs.length; ++i) {
            if (intfs[i].getName().equals(PersistenceCapable.class.getName())) {
               throw (new UserException(_loc.get("pc-loader-different", Exceptions.toString(obj), (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(PersistenceCapable.class)), (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(intfs[i]))))).setFailedObject(obj);
            }
         }

         throw (new UserException(_loc.get("pc-cast", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
      }
   }

   public void assertOpen() {
      if (this._closed) {
         if (this._closedException == null) {
            throw (new InvalidStateException(_loc.get("closed-notrace"))).setFatal(true);
         } else {
            OpenJPAException e = (new InvalidStateException(_loc.get("closed"), this._closedException)).setFatal(true);
            e.setCause(this._closedException);
            throw e;
         }
      }
   }

   public void assertActiveTransaction() {
      if ((this._flags & 2) == 0) {
         throw new NoTransactionException(_loc.get("not-active"));
      }
   }

   private void assertTransactionOperation() {
      if ((this._flags & 2) == 0) {
         throw new InvalidStateException(_loc.get("not-active"));
      }
   }

   public void assertNontransactionalRead() {
      if ((this._flags & 2) == 0 && !this._nontransRead) {
         throw new InvalidStateException(_loc.get("non-trans-read"));
      }
   }

   public void assertWriteOperation() {
      if ((this._flags & 2) == 0 && (!this._nontransWrite || (this._autoDetach & 8) != 0)) {
         throw new NoTransactionException(_loc.get("write-operation"));
      }
   }

   private static ObjectNotFoundException newObjectNotFoundException(Collection failed) {
      Throwable[] t = new Throwable[failed.size()];
      int idx = 0;

      for(Iterator itr = failed.iterator(); itr.hasNext(); ++idx) {
         t[idx] = new ObjectNotFoundException(itr.next());
      }

      return new ObjectNotFoundException(failed, t);
   }

   public Object processArgument(Object oid) {
      return oid;
   }

   public Object processReturn(Object oid, OpenJPAStateManager sm) {
      return sm == null ? null : sm.getManagedInstance();
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      this.assertOpen();
      this.lock();

      try {
         if (this.isActive()) {
            if (!this.getOptimistic()) {
               throw new InvalidStateException(_loc.get("cant-serialize-pessimistic-broker"));
            }

            if (this.hasFlushed()) {
               throw new InvalidStateException(_loc.get("cant-serialize-flushed-broker"));
            }

            if (this.hasConnection()) {
               throw new InvalidStateException(_loc.get("cant-serialize-connected-broker"));
            }
         }

         try {
            this._isSerializing = true;
            out.writeObject(this._factory.getPoolKey());
            out.defaultWriteObject();
         } finally {
            this._isSerializing = false;
         }
      } finally {
         this.unlock();
      }

   }

   private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
      Object factoryKey = in.readObject();
      AbstractBrokerFactory factory = AbstractBrokerFactory.getPooledFactoryForKey(factoryKey);
      this._conf = factory.getConfiguration();
      in.defaultReadObject();
      factory.initializeBroker(this._managed, this._connRetainMode, this, true);
      this.setMultithreaded(this._multithreaded);
      if (this.isActive() && this._runtime instanceof LocalManagedRuntime) {
         ((LocalManagedRuntime)this._runtime).begin();
      }

   }

   boolean isSerializing() {
      return this._isSerializing;
   }

   protected void assignObjectId(Object cache, Object id, StateManagerImpl sm) {
      ((ManagedCache)cache).assignObjectId(id, sm);
   }

   protected void checkForDuplicateId(Object id, Object obj) {
      StateManagerImpl other = this.getStateManagerImplById(id, false);
      if (other != null && !other.isDeleted() && !other.isNew()) {
         throw (new ObjectExistsException(_loc.get("cache-exists", obj.getClass().getName(), id))).setFailedObject(obj);
      }
   }

   private static class ManagedObjectCollection extends AbstractCollection {
      private final Collection _states;

      public ManagedObjectCollection(Collection states) {
         this._states = states;
      }

      public Collection getStateManagers() {
         return this._states;
      }

      public int size() {
         return this._states.size();
      }

      public Iterator iterator() {
         return new Iterator() {
            private final Iterator _itr;

            {
               this._itr = ManagedObjectCollection.this._states.iterator();
            }

            public boolean hasNext() {
               return this._itr.hasNext();
            }

            public Object next() {
               return ((OpenJPAStateManager)this._itr.next()).getManagedInstance();
            }

            public void remove() {
               throw new UnsupportedException();
            }
         };
      }
   }

   private static class StateManagerId implements Serializable {
      public static final String STRING_PREFIX = "openjpasm:";
      private static long _generator = 0L;
      private final int _bhash;
      private final long _id;

      public static StateManagerId newInstance(Broker b) {
         return new StateManagerId(System.identityHashCode(b), (long)(_generator++));
      }

      private StateManagerId(int bhash, long id) {
         this._bhash = bhash;
         this._id = id;
      }

      public StateManagerId(String str) {
         str = str.substring("openjpasm:".length());
         int idx = str.indexOf(58);
         this._bhash = Integer.parseInt(str.substring(0, idx));
         this._id = Long.parseLong(str.substring(idx + 1));
      }

      public boolean equals(Object other) {
         if (other == this) {
            return true;
         } else if (!(other instanceof StateManagerId)) {
            return false;
         } else {
            StateManagerId sid = (StateManagerId)other;
            return this._bhash == sid._bhash && this._id == sid._id;
         }
      }

      public int hashCode() {
         return (int)(this._id ^ this._id >>> 32);
      }

      public String toString() {
         return "openjpasm:" + this._bhash + ":" + this._id;
      }
   }

   static class TransactionalCache implements Set, Serializable {
      private final boolean _orderDirty;
      private Set _dirty = null;
      private Set _clean = null;

      public TransactionalCache(boolean orderDirty) {
         this._orderDirty = orderDirty;
      }

      public Collection copy() {
         if (this.isEmpty()) {
            return Collections.EMPTY_LIST;
         } else {
            List copy = new ArrayList(this.size());
            Iterator itr;
            if (this._dirty != null) {
               itr = this._dirty.iterator();

               while(itr.hasNext()) {
                  copy.add(itr.next());
               }
            }

            if (this._clean != null) {
               itr = this._clean.iterator();

               while(itr.hasNext()) {
                  copy.add(itr.next());
               }
            }

            return copy;
         }
      }

      public Collection copyDirty() {
         return (Collection)(this._dirty != null && !this._dirty.isEmpty() ? new ArrayList(this._dirty) : Collections.EMPTY_LIST);
      }

      public void flushed(StateManagerImpl sm) {
         if (sm.isDirty() && this._dirty != null && this._dirty.remove(sm)) {
            this.addCleanInternal(sm);
         }

      }

      public void addClean(StateManagerImpl sm) {
         if (this.addCleanInternal(sm) && this._dirty != null) {
            this._dirty.remove(sm);
         }

      }

      private boolean addCleanInternal(StateManagerImpl sm) {
         if (this._clean == null) {
            this._clean = new ReferenceHashSet(1);
         }

         return this._clean.add(sm);
      }

      public void addDirty(StateManagerImpl sm) {
         if (this._dirty == null) {
            if (this._orderDirty) {
               this._dirty = MapBackedSet.decorate(new LinkedMap());
            } else {
               this._dirty = new HashSet();
            }
         }

         if (this._dirty.add(sm)) {
            this.removeCleanInternal(sm);
         }

      }

      public boolean remove(StateManagerImpl sm) {
         return this.removeCleanInternal(sm) || this._dirty != null && this._dirty.remove(sm);
      }

      private boolean removeCleanInternal(StateManagerImpl sm) {
         return this._clean != null && this._clean.remove(sm);
      }

      public Iterator iterator() {
         IteratorChain chain = new IteratorChain();
         if (this._dirty != null && !this._dirty.isEmpty()) {
            chain.addIterator(this._dirty.iterator());
         }

         if (this._clean != null && !this._clean.isEmpty()) {
            chain.addIterator(this._clean.iterator());
         }

         return chain;
      }

      public boolean contains(Object obj) {
         return this._dirty != null && this._dirty.contains(obj) || this._clean != null && this._clean.contains(obj);
      }

      public boolean containsAll(Collection coll) {
         Iterator itr = coll.iterator();

         do {
            if (!itr.hasNext()) {
               return true;
            }
         } while(this.contains(itr.next()));

         return false;
      }

      public void clear() {
         if (this._dirty != null) {
            this._dirty = null;
         }

         if (this._clean != null) {
            this._clean = null;
         }

      }

      public boolean isEmpty() {
         return (this._dirty == null || this._dirty.isEmpty()) && (this._clean == null || this._clean.isEmpty());
      }

      public int size() {
         int size = 0;
         if (this._dirty != null) {
            size += this._dirty.size();
         }

         if (this._clean != null) {
            size += this._clean.size();
         }

         return size;
      }

      public boolean add(Object obj) {
         throw new UnsupportedOperationException();
      }

      public boolean addAll(Collection coll) {
         throw new UnsupportedOperationException();
      }

      public boolean remove(Object obj) {
         throw new UnsupportedOperationException();
      }

      public boolean removeAll(Collection coll) {
         throw new UnsupportedOperationException();
      }

      public boolean retainAll(Collection c) {
         throw new UnsupportedOperationException();
      }

      public Object[] toArray() {
         throw new UnsupportedOperationException();
      }

      public Object[] toArray(Object[] arr) {
         throw new UnsupportedOperationException();
      }
   }
}
