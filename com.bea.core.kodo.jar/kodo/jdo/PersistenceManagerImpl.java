package kodo.jdo;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.jdo.Extent;
import javax.jdo.FetchPlan;
import javax.jdo.JDOException;
import javax.jdo.JDOUserException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionMetaData;
import javax.resource.cci.Interaction;
import javax.resource.cci.InteractionSpec;
import javax.resource.cci.LocalTransaction;
import javax.resource.cci.Record;
import javax.resource.cci.ResourceWarning;
import javax.resource.cci.ResultSetInfo;
import javax.transaction.Synchronization;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.event.EndTransactionListener;
import org.apache.openjpa.event.TransactionEvent;
import org.apache.openjpa.kernel.AutoDetach;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.DelegatingBroker;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.lib.util.JavaVersions;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.OpenJPAException;

public class PersistenceManagerImpl implements KodoPersistenceManager, AutoDetach, FindCallbacks, OpCallbacks {
   private static final String USER_OBJECT_KEY = "kodo.jdo.UserObject";
   private final DelegatingBroker _broker;
   private final PersistenceManagerFactoryImpl _pmf;
   private FetchPlan _plan = null;
   private SynchronizationListener _sync = null;
   private JCAHelper _jca = new JCAHelper();

   public PersistenceManagerImpl(PersistenceManagerFactoryImpl pmf, Broker broker) {
      this._pmf = pmf;
      this._broker = new DelegatingBroker(broker, JDOExceptions.TRANSLATOR);
      this._broker.setImplicitBehavior(this, JDOExceptions.TRANSLATOR);
   }

   public Broker getDelegate() {
      return this._broker.getDelegate();
   }

   public ConnectionMetaData getMetaData() throws ResourceException {
      return this._jca;
   }

   public Interaction createInteraction() throws ResourceException {
      return this._jca;
   }

   public LocalTransaction getLocalTransaction() throws ResourceException {
      return this;
   }

   public ResultSetInfo getResultSetInfo() throws ResourceException {
      return this._jca;
   }

   public PersistenceManagerFactory getPersistenceManagerFactory() {
      return this._pmf;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._broker.getConfiguration();
   }

   public FetchPlan getFetchPlan() {
      this._broker.lock();

      FetchPlan var1;
      try {
         if (this._plan == null) {
            this._plan = this._pmf.toFetchPlan(this._broker, this._broker.getFetchConfiguration());
         }

         var1 = this._plan;
      } finally {
         this._broker.unlock();
      }

      return var1;
   }

   public int getConnectionRetainMode() {
      return this._broker.getConnectionRetainMode();
   }

   public boolean isManaged() {
      return this._broker.isManaged();
   }

   public ManagedRuntime getManagedRuntime() {
      return this._broker.getManagedRuntime();
   }

   public boolean getSyncWithManagedTransactions() {
      return this._broker.getSyncWithManagedTransactions();
   }

   public void setSyncWithManagedTransactions(boolean resync) {
      this._broker.setSyncWithManagedTransactions(resync);
   }

   public ClassLoader getClassLoader() {
      return this._broker.getClassLoader();
   }

   public String getConnectionUserName() {
      return this._broker.getConnectionUserName();
   }

   public String getConnectionPassword() {
      return this._broker.getConnectionPassword();
   }

   public boolean getMultithreaded() {
      return this._broker.getMultithreaded();
   }

   public void setMultithreaded(boolean multithreaded) {
      this._broker.setMultithreaded(multithreaded);
   }

   public boolean getIgnoreCache() {
      return this._broker.getIgnoreChanges();
   }

   public void setIgnoreCache(boolean val) {
      this._broker.setIgnoreChanges(val);
   }

   public boolean getNontransactionalRead() {
      return this._broker.getNontransactionalRead();
   }

   public void setNontransactionalRead(boolean val) {
      this._broker.setNontransactionalRead(val);
   }

   public boolean getNontransactionalWrite() {
      return this._broker.getNontransactionalWrite();
   }

   public void setNontransactionalWrite(boolean val) {
      this._broker.setNontransactionalWrite(val);
   }

   public boolean getRestoreValues() {
      return this._broker.getRestoreState() != 0;
   }

   public void setRestoreValues(boolean val) {
      if (val && this._broker.getRestoreState() != 2) {
         this._broker.setRestoreState(1);
      } else if (!val) {
         this._broker.setRestoreState(0);
      }

   }

   public boolean getRestoreMutableValues() {
      return this._broker.getRestoreState() == 2;
   }

   public void setRestoreMutableValues(boolean val) {
      this._broker.setRestoreState(2);
   }

   public boolean getOptimistic() {
      return this._broker.getOptimistic();
   }

   public void setOptimistic(boolean val) {
      this._broker.setOptimistic(val);
   }

   public boolean getRetainValues() {
      return this._broker.getRetainState();
   }

   public void setRetainValues(boolean val) {
      this._broker.setRetainState(val);
   }

   public int getAutoClear() {
      return this._broker.getAutoClear();
   }

   public void setAutoClear(int val) {
      this._broker.setAutoClear(val);
   }

   public boolean getDetachAllOnCommit() {
      return (this._broker.getAutoDetach() & 4) != 0;
   }

   public void setDetachAllOnCommit(boolean detach) {
      this._broker.setAutoDetach(4, detach);
   }

   public boolean getDetachAllOnRollback() {
      return (this._broker.getAutoDetach() & 16) != 0;
   }

   public void setDetachAllOnRollback(boolean detach) {
      this._broker.setAutoDetach(16, detach);
   }

   public boolean getDetachAllOnClose() {
      return (this._broker.getAutoDetach() & 2) != 0;
   }

   public void setDetachAllOnClose(boolean detach) {
      this._broker.setAutoDetach(2, detach);
   }

   public boolean getDetachAllOnNontransactionalRead() {
      return (this._broker.getAutoDetach() & 8) != 0;
   }

   public void setDetachAllOnNontransactionalRead(boolean detach) {
      this._broker.setAutoDetach(8, detach);
   }

   public boolean getEvictFromDataStoreCache() {
      return this._broker.getEvictFromDataCache();
   }

   public void setEvictFromDataStoreCache(boolean evict) {
      this._broker.setEvictFromDataCache(evict);
   }

   public boolean getPopulateDataStoreCache() {
      return this._broker.getPopulateDataCache();
   }

   public void setPopulateDataStoreCache(boolean cache) {
      this._broker.setPopulateDataCache(cache);
   }

   public boolean isLargeTransaction() {
      return this._broker.isTrackChangesByType();
   }

   public void setLargeTransaction(boolean largeTransaction) {
      this._broker.setTrackChangesByType(largeTransaction);
   }

   public Object getUserObject() {
      return this.getUserObject("kodo.jdo.UserObject");
   }

   public void setUserObject(Object obj) {
      this.putUserObject("kodo.jdo.UserObject", obj);
   }

   public Object getUserObject(Object key) {
      return this._broker.getUserObject(key);
   }

   public Object putUserObject(Object key, Object val) {
      return this._broker.putUserObject(key, val);
   }

   public Object removeUserObject(Object key) {
      return this._broker.putUserObject(key, (Object)null);
   }

   public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
      this._broker.addLifecycleListener(new LifecycleListenerAdapter(listener), classes);
   }

   public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
      this._broker.removeLifecycleListener(new LifecycleListenerAdapter(listener));
   }

   public int getInstanceLifecycleListenerCallbackMode() {
      return this._broker.getLifecycleListenerCallbackMode();
   }

   public void setInstanceLifecycleListenerCallbackMode(int mode) {
      this._broker.setLifecycleListenerCallbackMode(mode);
   }

   public void addTransactionListener(Object listener) {
      this._broker.addTransactionListener(listener);
   }

   public void removeTransactionListener(Object listener) {
      this._broker.removeTransactionListener(listener);
   }

   public int getTransactionListenerCallbackMode() {
      return this._broker.getTransactionListenerCallbackMode();
   }

   public void setTransactionListenerCallbackMode(int mode) {
      this._broker.setTransactionListenerCallbackMode(mode);
   }

   public Object getObjectById(Object oid) {
      return this.getObjectById(oid, true);
   }

   public Object getObjectById(Object oid, boolean validate) {
      return this._broker.find(oid, validate, this);
   }

   public Object getObjectById(Class cls, Object val) {
      val = KodoJDOHelper.toKodoObjectId(val, this);
      return this._broker.find(this._broker.newObjectId(cls, val), true, this);
   }

   public Collection getObjectsById(Collection oids) {
      return this.getObjectsById(oids, true);
   }

   public Collection getObjectsById(Collection oids, boolean validate) {
      return Arrays.asList(this._broker.findAll(oids, validate, this));
   }

   public Object[] getObjectsById(Object[] oids) {
      return this.getObjectsById(oids, true);
   }

   public Object[] getObjectsById(Object[] oids, boolean validate) {
      return this._broker.findAll(Arrays.asList(oids), validate, this);
   }

   public Object getCachedObjectById(Object oid) {
      return this._broker.findCached(oid, this);
   }

   public Object getObjectId(Object pc) {
      return KodoJDOHelper.fromKodoObjectId(this._broker.getObjectId(pc));
   }

   public Object getTransactionalObjectId(Object pc) {
      return this.getObjectId(pc);
   }

   public Class getObjectIdClass(Class cls) {
      return KodoJDOHelper.fromKodoObjectIdClass(this._broker.getObjectIdType(cls));
   }

   public Object newObjectIdInstance(Class cls, Object val) {
      return KodoJDOHelper.fromKodoObjectId(this._broker.newObjectId(cls, val));
   }

   public Object newInstance(Class cls) {
      try {
         return this._broker.newInstance(cls);
      } catch (IllegalArgumentException var4) {
         JDOUserException e = new JDOUserException(var4.getMessage());
         if (!JavaVersions.transferStackTrace(var4, e)) {
            e.initCause(var4);
         }

         throw e;
      }
   }

   public Transaction currentTransaction() {
      return this;
   }

   public PersistenceManager getPersistenceManager() {
      return this;
   }

   public void begin() {
      this._broker.begin();
   }

   public void commit() {
      this._broker.commit();
   }

   public void rollback() {
      this._broker.rollback();
   }

   public void commitAndResume() {
      this._broker.commitAndResume();
   }

   public void rollbackAndResume() {
      this._broker.rollbackAndResume();
   }

   public boolean getRollbackOnly() {
      return this._broker.getRollbackOnly();
   }

   public void setRollbackOnly() {
      this._broker.setRollbackOnly();
   }

   public void setSavepoint(String name) {
      this._broker.setSavepoint(name);
   }

   public void rollbackToSavepoint() {
      this._broker.rollbackToSavepoint();
   }

   public void rollbackToSavepoint(String name) {
      this._broker.rollbackToSavepoint(name);
   }

   public void releaseSavepoint() {
      this._broker.releaseSavepoint();
   }

   public void releaseSavepoint(String name) {
      this._broker.releaseSavepoint(name);
   }

   public void flush() {
      this._broker.flush();
   }

   public void preFlush() {
      this._broker.preFlush();
   }

   public void checkConsistency() {
      this._broker.validateChanges();
   }

   public boolean isActive() {
      return this._broker.isActive();
   }

   public boolean isStoreActive() {
      return this._broker.isStoreActive();
   }

   public void beginStore() {
      this._broker.beginStore();
   }

   public Synchronization getSynchronization() {
      return this._sync == null ? null : this._sync.getDelegate();
   }

   public void setSynchronization(Synchronization sync) {
      this._broker.lock();

      try {
         if (this._sync != null) {
            this._broker.removeTransactionListener(this._sync);
         }

         if (sync != null) {
            this._sync = new SynchronizationListener(sync);
            this._broker.addTransactionListener(this._sync);
         } else {
            this._sync = null;
         }
      } finally {
         this._broker.unlock();
      }

   }

   public Object makePersistent(Object pc) {
      return this._broker.attach(pc, false, this);
   }

   public Object[] makePersistentAll(Object[] pcs) {
      return this._broker.attachAll(Arrays.asList(pcs), false, this);
   }

   public Collection makePersistentAll(Collection pcs) {
      return Arrays.asList(this._broker.attachAll(pcs, false, this));
   }

   public void deletePersistentAll(Collection pcs) {
      this._broker.deleteAll(pcs, this);
   }

   public void deletePersistentAll(Object[] pcs) {
      this.deletePersistentAll((Collection)Arrays.asList(pcs));
   }

   public void deletePersistent(Object pc) {
      this._broker.delete(pc, this);
   }

   public void makeTransientAll(Collection pcs) {
      this.makeTransientAll(pcs, false);
   }

   public void makeTransientAll(Collection pcs, boolean fetchPlan) {
      this._broker.releaseAll(pcs, this);
   }

   public void makeTransientAll(Object[] pcs) {
      this.makeTransientAll(pcs, false);
   }

   public void makeTransientAll(Object[] pcs, boolean fetchPlan) {
      this.makeTransientAll((Collection)Arrays.asList(pcs), fetchPlan);
   }

   public void makeTransient(Object pc) {
      this.makeTransient(pc, false);
   }

   public void makeTransient(Object pc, boolean fetchPlan) {
      this._broker.release(pc, this);
   }

   public void refreshAll() {
      this._broker.refreshAll(this._broker.getTransactionalObjects(), this);
   }

   public void refreshAll(Collection pcs) {
      this._broker.refreshAll(pcs, this);
   }

   public void refreshAll(Object[] pcs) {
      this.refreshAll((Collection)Arrays.asList(pcs));
   }

   public void refresh(Object pc) {
      this._broker.refresh(pc, this);
   }

   public void refreshAll(JDOException je) {
      Collection failed = null;

      try {
         failed = this.getAllFailedObjects(je, (Collection)null, true);
      } catch (Exception var4) {
         throw JDOExceptions.toJDOException(var4);
      }

      if (failed != null) {
         this.refreshAll(failed);
      }

   }

   public void makeNontransactionalAll(Collection pcs) {
      this._broker.nontransactionalAll(pcs, this);
   }

   public void makeNontransactionalAll(Object[] pcs) {
      this.makeNontransactionalAll((Collection)Arrays.asList(pcs));
   }

   public void makeNontransactional(Object pc) {
      this._broker.nontransactional(pc, this);
   }

   public void makeTransactionalAll(Collection pcs) {
      this._broker.transactionalAll(pcs, false, this);
   }

   public void makeTransactionalAll(Object[] pcs) {
      this.makeTransactionalAll((Collection)Arrays.asList(pcs));
   }

   public void makeTransactional(Object pc) {
      this._broker.transactional(pc, false, this);
   }

   private Collection getAllFailedObjects(JDOException je, Collection all, boolean top) {
      Throwable[] nested = je.getNestedExceptions();

      for(int i = 0; nested != null && i < nested.length; ++i) {
         if (nested[i] instanceof JDOException) {
            all = this.getAllFailedObjects((JDOException)nested[i], (Collection)all, false);
         }
      }

      if (ImplHelper.isManageable(je.getFailedObject())) {
         if (all == null) {
            if (top) {
               return Collections.singleton(je.getFailedObject());
            }

            all = new HashSet();
         }

         ((Collection)all).add(je.getFailedObject());
      }

      return (Collection)all;
   }

   public void retrieveAll(Collection pcs) {
      this.retrieveAll(pcs, false);
   }

   public void retrieveAll(Collection pcs, boolean dfgOnly) {
      this._broker.retrieveAll(pcs, dfgOnly, this);
   }

   public void retrieveAll(Object[] pcs) {
      this.retrieveAll((Collection)Arrays.asList(pcs));
   }

   public void retrieveAll(Object[] pcs, boolean dfgOnly) {
      this.retrieveAll((Collection)Arrays.asList(pcs), dfgOnly);
   }

   public void retrieve(Object pc) {
      this.retrieve(pc, false);
   }

   public void retrieve(Object pc, boolean dfgOnly) {
      this._broker.retrieve(pc, dfgOnly, this);
   }

   public void evictAll() {
      this._broker.evictAll(this);
   }

   public void evictAll(Collection pcs) {
      this._broker.evictAll(pcs, this);
   }

   public void evictAll(Object[] pcs) {
      this.evictAll((Collection)Arrays.asList(pcs));
   }

   public void evictAll(Class cls) {
      this._broker.evictAll(this._broker.newExtent(cls, true), this);
   }

   public void evictAll(Extent extent) {
      this._broker.evictAll(((ExtentImpl)extent).getDelegate(), this);
   }

   public void evict(Object pc) {
      this._broker.evict(pc, this);
   }

   public Object detachCopy(Object pc) {
      if (this._broker.isActive()) {
         this.makePersistent(pc);
      }

      return this._broker.detach(pc, this);
   }

   public Object[] detachCopyAll(Object[] pcs) {
      if (this._broker.isActive()) {
         this.makePersistentAll(pcs);
      }

      return this._broker.detachAll(Arrays.asList(pcs), this);
   }

   public Collection detachCopyAll(Collection pcs) {
      if (this._broker.isActive()) {
         this.makePersistentAll(pcs);
      }

      return Arrays.asList(this._broker.detachAll(pcs, this));
   }

   public void detachAll() {
      this._broker.detachAll(this);
   }

   public Object attachCopy(Object pc, boolean transactional) {
      Object attached = this._broker.attach(pc, true, this);
      if (transactional) {
         this.makeTransactional(attached);
      }

      return attached;
   }

   public Object[] attachCopyAll(Object[] pcs, boolean transactional) {
      Object[] attached = this._broker.attachAll(Arrays.asList(pcs), true, this);
      if (transactional) {
         this.makeTransactionalAll(attached);
      }

      return attached;
   }

   public Collection attachCopyAll(Collection pcs, boolean transactional) {
      Collection attached = Arrays.asList(this._broker.attachAll(pcs, true, this));
      if (transactional) {
         this.makeTransactionalAll((Collection)attached);
      }

      return attached;
   }

   public Extent getExtent(Class type) {
      return this.getExtent(type, true);
   }

   public Extent getExtent(Class type, boolean subclasses) {
      return new ExtentImpl(this, this._broker.newExtent(type, subclasses));
   }

   public Query newQuery() {
      return this.newQuery((Object)null);
   }

   public Query newQuery(Class cls) {
      return this.newQuery(cls, (String)null);
   }

   public Query newQuery(Class cls, Collection cln) {
      return this.newQuery(cls, cln, (String)null);
   }

   public Query newQuery(Extent extent) {
      return this.newQuery((Extent)extent, (String)null);
   }

   public Query newQuery(Class cls, Collection cln, String query) {
      Query q = this.newQuery(cls, query);
      q.setCandidates(cln);
      return q;
   }

   public Query newQuery(Extent extent, String query) {
      Query q = this.newQuery(query);
      q.setCandidates(extent);
      return q;
   }

   public Query newQuery(Class cls, String query) {
      return this.newQueryInternal(this._broker.newQuery("javax.jdo.query.JDOQL", cls, query));
   }

   public Query newQuery(String query) {
      return this.newQuery((Object)query);
   }

   public Query newQuery(Object query) {
      String lang;
      if (query instanceof QueryImpl) {
         org.apache.openjpa.kernel.Query del = ((QueryImpl)query).getDelegate();
         query = del;
         lang = del.getLanguage();
      } else {
         lang = "javax.jdo.query.JDOQL";
      }

      return this.newQueryInternal(this._broker.newQuery(lang, query));
   }

   public Query newQuery(String language, Object query) {
      if (query instanceof QueryImpl) {
         query = ((QueryImpl)query).getDelegate();
      }

      return this.newQueryInternal(this._broker.newQuery(toQueryLanguageName(language), query));
   }

   public Query newNamedQuery(Class cls, String name) {
      try {
         QueryMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getQueryMetaData(cls, name, this._broker.getClassLoader(), true);
         org.apache.openjpa.kernel.Query del = this._broker.newQuery(toQueryLanguageName(meta.getLanguage()), (Object)null);
         meta.setInto(del);
         del.compile();
         Query q = this.newQueryInternal(del);
         String[] hints = meta.getHintKeys();
         Object[] values = meta.getHintValues();

         for(int i = 0; i < hints.length; ++i) {
            q.addExtension(hints[i], values[i]);
         }

         return q;
      } catch (OpenJPAException var9) {
         throw JDOExceptions.toJDOException(var9.setFatal(false));
      } catch (RuntimeException var10) {
         throw JDOExceptions.toJDOException(var10);
      }
   }

   private static String toQueryLanguageName(String language) {
      if ("javax.jdo.query.SQL".equals(language)) {
         return "openjpa.SQL";
      } else {
         return "kodo.MethodQL".equals(language) ? "openjpa.MethodQL" : language;
      }
   }

   protected Query newQueryInternal(org.apache.openjpa.kernel.Query del) {
      return new QueryImpl(this, del);
   }

   public Sequence getSequence(String name) {
      try {
         SequenceMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getSequenceMetaData(name, this._broker.getClassLoader(), true);
         Seq seq = meta.getInstance(this._broker.getClassLoader());
         return (Sequence)(seq instanceof SeqAdapter ? ((SeqAdapter)seq).getDelegate() : new SequenceImpl(seq, name, this._broker, (ClassMetaData)null));
      } catch (RuntimeException var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public Sequence getIdentitySequence(Class forClass) {
      try {
         ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(forClass, this._broker.getClassLoader(), true);
         Seq seq = this._broker.getIdentitySequence(meta);
         return seq == null ? null : new SequenceImpl(seq, (String)null, this._broker, meta);
      } catch (Exception var4) {
         throw JDOExceptions.toJDOException(var4);
      }
   }

   public Sequence getFieldSequence(Class forClass, String fieldName) {
      try {
         ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(forClass, this._broker.getClassLoader(), true);
         FieldMetaData fmd = meta.getField(fieldName);
         if (fmd == null) {
            throw new UserException(PersistenceManagerImpl.Loc._loc.get("no-named-field", forClass, fieldName).getMessage(), (Throwable[])null, (Object)null);
         } else {
            Seq seq = this._broker.getValueSequence(fmd);
            return seq == null ? null : new SequenceImpl(seq, (String)null, this._broker, meta);
         }
      } catch (Exception var6) {
         throw JDOExceptions.toJDOException(var6);
      }
   }

   public void lockPersistent(Object pc) {
      this._broker.lock(pc, this);
   }

   public void lockPersistent(Object pc, int level, int timeout) {
      this._broker.lock(pc, level, timeout, this);
   }

   public void lockPersistentAll(Collection pcs) {
      this._broker.lockAll(pcs, this);
   }

   public void lockPersistentAll(Collection pcs, int level, int timeout) {
      this._broker.lockAll(pcs, level, timeout, this);
   }

   public void lockPersistentAll(Object[] pcs) {
      this.lockPersistentAll((Collection)Arrays.asList(pcs));
   }

   public void lockPersistentAll(Object[] pcs, int level, int timeout) {
      this.lockPersistentAll((Collection)Arrays.asList(pcs), level, timeout);
   }

   public boolean cancelAll() {
      return this._broker.cancelAll();
   }

   public JDOConnection getDataStoreConnection() {
      this._broker.lock();

      JDOConnection var1;
      try {
         if (this._broker.isActive() && !this._broker.isStoreActive()) {
            this._broker.beginStore();
         }

         var1 = this._pmf.toJDOConnection(this._broker, this._broker.getConnection());
      } finally {
         this._broker.unlock();
      }

      return var1;
   }

   public Collection getManagedObjects() {
      return this._broker.getManagedObjects();
   }

   public Collection getTransactionalObjects() {
      return this._broker.getTransactionalObjects();
   }

   public Collection getPendingTransactionalObjects() {
      return this._broker.getPendingTransactionalObjects();
   }

   public Collection getDirtyObjects() {
      return this._broker.getDirtyObjects();
   }

   public boolean getOrderDirtyObjects() {
      return this._broker.getOrderDirtyObjects();
   }

   public void setOrderDirtyObjects(boolean order) {
      this._broker.setOrderDirtyObjects(order);
   }

   public void makeClassDirty(Class cls) {
      this._broker.dirtyType(cls);
   }

   public Collection getDirtyClasses() {
      Collection added = this.getAddedClasses();
      Collection updated = this.getUpdatedClasses();
      Collection deleted = this.getDeletedClasses();
      int size = added.size() + updated.size() + deleted.size();
      if (size == 0) {
         return Collections.EMPTY_SET;
      } else {
         Collection dirty = new HashSet((int)((double)size * 1.33 + 1.0));
         dirty.addAll(added);
         dirty.addAll(updated);
         dirty.addAll(deleted);
         return dirty;
      }
   }

   public Collection getAddedClasses() {
      return this._broker.getPersistedTypes();
   }

   public Collection getUpdatedClasses() {
      return this._broker.getUpdatedTypes();
   }

   public Collection getDeletedClasses() {
      return this._broker.getDeletedTypes();
   }

   public void close() {
      this._broker.close();
   }

   public boolean isClosed() {
      return this._broker.isClosed();
   }

   public int hashCode() {
      return this._broker.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof PersistenceManagerImpl) ? false : this._broker.equals(((PersistenceManagerImpl)other)._broker);
      }
   }

   public Object processArgument(Object oid) {
      return KodoJDOHelper.toKodoObjectId(oid, this);
   }

   public Object processReturn(Object oid, OpenJPAStateManager sm) {
      if (sm != null && !sm.isDeleted()) {
         return sm.getManagedInstance();
      } else {
         throw new ObjectNotFoundException(oid);
      }
   }

   public int processArgument(int op, Object obj, OpenJPAStateManager sm) {
      switch (op) {
         case 0:
            if (sm != null && sm.isDeleted()) {
               return 0;
            }
            break;
         case 1:
            if (sm == null) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("not-managed", Exceptions.toString(obj)))).setFailedObject(obj);
            }
         case 2:
         case 3:
         case 5:
         default:
            break;
         case 4:
         case 9:
            if (sm == null && !ImplHelper.isManageable(obj)) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("not-pc", Exceptions.toString(obj)))).setFailedObject(obj);
            }
            break;
         case 6:
            if (sm != null && !sm.isDetached() && sm.isPersistent() && !sm.isEmbedded() && !sm.isProvisional()) {
               return 0;
            }
            break;
         case 7:
            if (sm == null) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("not-managed", Exceptions.toString(obj)))).setFailedObject(obj);
            }

            if (sm.isDeleted()) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("detach-deleted", Exceptions.toString(obj)))).setFailedObject(obj);
            }
            break;
         case 8:
            if (sm == null) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("cannot-nontransactional-unmanaged", Exceptions.toString(obj)))).setFailedObject(obj);
            }

            if (sm.isDirty()) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("cannot-nontransactional-dirty", Exceptions.toString(obj)))).setFailedObject(obj);
            }

            if (sm.isDetached()) {
               throw (new org.apache.openjpa.util.UserException(PersistenceManagerImpl.Loc._loc.get("cannot-nontransactional-detached", Exceptions.toString(obj)))).setFailedObject(obj);
            }
      }

      return 6;
   }

   private class JCAHelper implements Interaction, ResultSetInfo, ConnectionMetaData {
      private JCAHelper() {
      }

      public void clearWarnings() {
      }

      public Record execute(InteractionSpec spec, Record input) throws ResourceException {
         throw new NotSupportedException("execute");
      }

      public boolean execute(InteractionSpec spec, Record input, Record output) throws ResourceException {
         throw new NotSupportedException("execute");
      }

      public Connection getConnection() {
         return PersistenceManagerImpl.this;
      }

      public ResourceWarning getWarnings() {
         return null;
      }

      public void close() {
      }

      public boolean deletesAreDetected(int type) {
         return true;
      }

      public boolean insertsAreDetected(int type) {
         return true;
      }

      public boolean othersDeletesAreVisible(int type) {
         return true;
      }

      public boolean othersInsertsAreVisible(int type) {
         return true;
      }

      public boolean othersUpdatesAreVisible(int type) {
         return true;
      }

      public boolean ownDeletesAreVisible(int type) {
         return true;
      }

      public boolean ownInsertsAreVisible(int type) {
         return true;
      }

      public boolean ownUpdatesAreVisible(int type) {
         return true;
      }

      public boolean supportsResultSetType(int type) {
         return true;
      }

      public boolean supportsResultTypeConcurrency(int type, int concurrency) {
         return true;
      }

      public boolean updatesAreDetected(int type) {
         return true;
      }

      public String getEISProductName() {
         return PersistenceManagerImpl.this.getConfiguration().getConnectionDriverName();
      }

      public String getEISProductVersion() {
         return "";
      }

      public String getUserName() {
         return PersistenceManagerImpl.this._broker.getConnectionUserName();
      }

      // $FF: synthetic method
      JCAHelper(Object x1) {
         this();
      }
   }

   private static class SynchronizationListener implements EndTransactionListener {
      private final Synchronization _sync;

      public SynchronizationListener(Synchronization sync) {
         this._sync = sync;
      }

      Synchronization getDelegate() {
         return this._sync;
      }

      public void beforeCommit(TransactionEvent envet) {
         this._sync.beforeCompletion();
      }

      public void afterCommit(TransactionEvent event) {
      }

      public void afterRollback(TransactionEvent event) {
      }

      public void afterCommitComplete(TransactionEvent event) {
         this._sync.afterCompletion(3);
      }

      public void afterRollbackComplete(TransactionEvent event) {
         this._sync.afterCompletion(4);
      }

      public void afterStateTransitions(TransactionEvent event) {
      }
   }

   private static class Loc {
      private static final Localizer _loc = Localizer.forPackage(PersistenceManagerImpl.class);
   }
}
