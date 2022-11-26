package org.apache.openjpa.kernel;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public class DelegatingBroker implements Broker {
   private final Broker _broker;
   private final DelegatingBroker _del;
   private final RuntimeExceptionTranslator _trans;

   public DelegatingBroker(Broker broker) {
      this(broker, (RuntimeExceptionTranslator)null);
   }

   public DelegatingBroker(Broker broker, RuntimeExceptionTranslator trans) {
      this._broker = broker;
      if (broker instanceof DelegatingBroker) {
         this._del = (DelegatingBroker)broker;
      } else {
         this._del = null;
      }

      this._trans = trans;
   }

   public Broker getDelegate() {
      return this._broker;
   }

   public Broker getInnermostDelegate() {
      return this._del == null ? this._broker : this._del.getInnermostDelegate();
   }

   public int hashCode() {
      return this.getInnermostDelegate().hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         if (other instanceof DelegatingBroker) {
            other = ((DelegatingBroker)other).getInnermostDelegate();
         }

         return this.getInnermostDelegate().equals(other);
      }
   }

   protected RuntimeException translate(RuntimeException re) {
      return this._trans == null ? re : this._trans.translate(re);
   }

   public Broker getBroker() {
      return this;
   }

   public OpenJPAConfiguration getConfiguration() {
      try {
         return this._broker.getConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration getFetchConfiguration() {
      try {
         return this._broker.getFetchConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public FetchConfiguration pushFetchConfiguration() {
      try {
         return this._broker.pushFetchConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void popFetchConfiguration() {
      try {
         this._broker.popFetchConfiguration();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public ClassLoader getClassLoader() {
      try {
         return this._broker.getClassLoader();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public LockManager getLockManager() {
      try {
         return this._broker.getLockManager();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public DelegatingStoreManager getStoreManager() {
      try {
         return this._broker.getStoreManager();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getConnectionUserName() {
      try {
         return this._broker.getConnectionUserName();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public String getConnectionPassword() {
      try {
         return this._broker.getConnectionPassword();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object find(Object oid, boolean validate, FindCallbacks call) {
      try {
         return this._broker.find(oid, validate, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Object[] findAll(Collection oids, boolean validate, FindCallbacks call) {
      try {
         return this._broker.findAll(oids, validate, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Object findCached(Object oid, FindCallbacks call) {
      try {
         return this._broker.findCached(oid, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object find(Object oid, FetchConfiguration fetch, BitSet exclude, Object edata, int flags) {
      try {
         return this._broker.find(oid, fetch, exclude, edata, flags);
      } catch (RuntimeException var7) {
         throw this.translate(var7);
      }
   }

   public Object[] findAll(Collection oids, FetchConfiguration fetch, BitSet exclude, Object edata, int flags) {
      try {
         return this._broker.findAll(oids, fetch, exclude, edata, flags);
      } catch (RuntimeException var7) {
         throw this.translate(var7);
      }
   }

   public Iterator extentIterator(Class cls, boolean subs, FetchConfiguration fetch, boolean ignoreChanges) {
      try {
         return this._broker.extentIterator(cls, subs, fetch, ignoreChanges);
      } catch (RuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public void retrieve(Object obj, boolean fgOnly, OpCallbacks call) {
      try {
         this._broker.retrieve(obj, fgOnly, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void retrieveAll(Collection objs, boolean fgOnly, OpCallbacks call) {
      try {
         this._broker.retrieveAll(objs, fgOnly, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public OpenJPAStateManager embed(Object obj, Object id, OpenJPAStateManager owner, ValueMetaData ownerMeta) {
      try {
         return this._broker.embed(obj, id, owner, ownerMeta);
      } catch (RuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public Class getObjectIdType(Class cls) {
      try {
         return this._broker.getObjectIdType(cls);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object newObjectId(Class cls, Object val) {
      try {
         return this._broker.newObjectId(cls, val);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Collection getManagedObjects() {
      try {
         return this._broker.getManagedObjects();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getTransactionalObjects() {
      try {
         return this._broker.getTransactionalObjects();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getPendingTransactionalObjects() {
      try {
         return this._broker.getPendingTransactionalObjects();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getDirtyObjects() {
      try {
         return this._broker.getDirtyObjects();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean getOrderDirtyObjects() {
      try {
         return this._broker.getOrderDirtyObjects();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setOrderDirtyObjects(boolean order) {
      try {
         this._broker.setOrderDirtyObjects(order);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Collection getPersistedTypes() {
      try {
         return this._broker.getPersistedTypes();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getUpdatedTypes() {
      try {
         return this._broker.getUpdatedTypes();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Collection getDeletedTypes() {
      try {
         return this._broker.getDeletedTypes();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public OpenJPAStateManager getStateManager(Object obj) {
      try {
         return this._broker.getStateManager(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getLockLevel(Object obj) {
      try {
         return this._broker.getLockLevel(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object getVersion(Object obj) {
      try {
         return this._broker.getVersion(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isDirty(Object obj) {
      try {
         return this._broker.isDirty(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isTransactional(Object obj) {
      try {
         return this._broker.isTransactional(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isPersistent(Object obj) {
      try {
         return this._broker.isPersistent(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isNew(Object obj) {
      try {
         return this._broker.isNew(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isDeleted(Object obj) {
      try {
         return this._broker.isDeleted(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object getObjectId(Object obj) {
      try {
         return this._broker.getObjectId(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isManaged() {
      try {
         return this._broker.isManaged();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isActive() {
      try {
         return this._broker.isActive();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isStoreActive() {
      try {
         return this._broker.isStoreActive();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean hasConnection() {
      try {
         return this._broker.hasConnection();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public Object getConnection() {
      try {
         return this._broker.getConnection();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void lock() {
      try {
         this._broker.lock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void unlock() {
      try {
         this._broker.unlock();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean beginOperation(boolean read) {
      try {
         return this._broker.beginOperation(read);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean endOperation() {
      try {
         return this._broker.endOperation();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setImplicitBehavior(OpCallbacks call, RuntimeExceptionTranslator ex) {
      try {
         this._broker.setImplicitBehavior(call, ex);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public BrokerFactory getBrokerFactory() {
      try {
         return this._broker.getBrokerFactory();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public int getConnectionRetainMode() {
      try {
         return this._broker.getConnectionRetainMode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public ManagedRuntime getManagedRuntime() {
      try {
         return this._broker.getManagedRuntime();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public InverseManager getInverseManager() {
      try {
         return this._broker.getInverseManager();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean getMultithreaded() {
      try {
         return this._broker.getMultithreaded();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setMultithreaded(boolean multi) {
      try {
         this._broker.setMultithreaded(multi);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getIgnoreChanges() {
      try {
         return this._broker.getIgnoreChanges();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setIgnoreChanges(boolean ignore) {
      try {
         this._broker.setIgnoreChanges(ignore);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getNontransactionalRead() {
      try {
         return this._broker.getNontransactionalRead();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setNontransactionalRead(boolean read) {
      try {
         this._broker.setNontransactionalRead(read);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getNontransactionalWrite() {
      try {
         return this._broker.getNontransactionalWrite();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setNontransactionalWrite(boolean write) {
      try {
         this._broker.setNontransactionalWrite(write);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getRestoreState() {
      try {
         return this._broker.getRestoreState();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setRestoreState(int restore) {
      try {
         this._broker.setRestoreState(restore);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getOptimistic() {
      try {
         return this._broker.getOptimistic();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setOptimistic(boolean opt) {
      try {
         this._broker.setOptimistic(opt);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getRetainState() {
      try {
         return this._broker.getRetainState();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setRetainState(boolean retain) {
      try {
         this._broker.setRetainState(retain);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getAutoClear() {
      try {
         return this._broker.getAutoClear();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setAutoClear(int clear) {
      try {
         this._broker.setAutoClear(clear);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getAutoDetach() {
      try {
         return this._broker.getAutoDetach();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setAutoDetach(int flags) {
      try {
         this._broker.setAutoDetach(flags);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void setAutoDetach(int flag, boolean on) {
      try {
         this._broker.setAutoDetach(flag, on);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public int getDetachState() {
      try {
         return this._broker.getDetachState();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setDetachState(int mode) {
      try {
         this._broker.setDetachState(mode);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isDetachedNew() {
      try {
         return this._broker.isDetachedNew();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setDetachedNew(boolean isNew) {
      try {
         this._broker.setDetachedNew(isNew);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getSyncWithManagedTransactions() {
      try {
         return this._broker.getSyncWithManagedTransactions();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setSyncWithManagedTransactions(boolean sync) {
      try {
         this._broker.setSyncWithManagedTransactions(sync);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getEvictFromDataCache() {
      try {
         return this._broker.getEvictFromDataCache();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setEvictFromDataCache(boolean evict) {
      try {
         this._broker.setEvictFromDataCache(evict);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean getPopulateDataCache() {
      try {
         return this._broker.getPopulateDataCache();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setPopulateDataCache(boolean cache) {
      try {
         this._broker.setPopulateDataCache(cache);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isTrackChangesByType() {
      try {
         return this._broker.isTrackChangesByType();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setTrackChangesByType(boolean largeTransaction) {
      try {
         this._broker.setTrackChangesByType(largeTransaction);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object putUserObject(Object key, Object val) {
      try {
         return this._broker.putUserObject(key, val);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object getUserObject(Object key) {
      try {
         return this._broker.getUserObject(key);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void addTransactionListener(Object listener) {
      try {
         this._broker.addTransactionListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void removeTransactionListener(Object listener) {
      try {
         this._broker.removeTransactionListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getTransactionListenerCallbackMode() {
      try {
         return this._broker.getTransactionListenerCallbackMode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setTransactionListenerCallbackMode(int mode) {
      try {
         this._broker.setTransactionListenerCallbackMode(mode);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void addLifecycleListener(Object listener, Class[] classes) {
      try {
         this._broker.addLifecycleListener(listener, classes);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void removeLifecycleListener(Object listener) {
      try {
         this._broker.removeLifecycleListener(listener);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public int getLifecycleListenerCallbackMode() {
      try {
         return this._broker.getLifecycleListenerCallbackMode();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setLifecycleListenerCallbackMode(int mode) {
      try {
         this._broker.setLifecycleListenerCallbackMode(mode);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public LifecycleEventManager getLifecycleEventManager() {
      try {
         return this._broker.getLifecycleEventManager();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void begin() {
      try {
         this._broker.begin();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void commit() {
      try {
         this._broker.commit();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void rollback() {
      try {
         this._broker.rollback();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean syncWithManagedTransaction() {
      try {
         return this._broker.syncWithManagedTransaction();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void commitAndResume() {
      try {
         this._broker.commitAndResume();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void rollbackAndResume() {
      try {
         this._broker.rollbackAndResume();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setRollbackOnly() {
      try {
         this._broker.setRollbackOnly();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setRollbackOnly(Throwable cause) {
      try {
         this._broker.setRollbackOnly(cause);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Throwable getRollbackCause() {
      try {
         return this._broker.getRollbackCause();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean getRollbackOnly() {
      try {
         return this._broker.getRollbackOnly();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void setSavepoint(String name) {
      try {
         this._broker.setSavepoint(name);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void rollbackToSavepoint() {
      try {
         this._broker.rollbackToSavepoint();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void rollbackToSavepoint(String name) {
      try {
         this._broker.rollbackToSavepoint(name);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void releaseSavepoint() {
      try {
         this._broker.releaseSavepoint();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void releaseSavepoint(String name) {
      try {
         this._broker.releaseSavepoint(name);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void flush() {
      try {
         this._broker.flush();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void preFlush() {
      try {
         this._broker.preFlush();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void validateChanges() {
      try {
         this._broker.validateChanges();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void beginStore() {
      try {
         this._broker.beginStore();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void persist(Object obj, OpCallbacks call) {
      try {
         this._broker.persist(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void persistAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.persistAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public OpenJPAStateManager persist(Object obj, Object id, OpCallbacks call) {
      try {
         return this._broker.persist(obj, id, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void delete(Object obj, OpCallbacks call) {
      try {
         this._broker.delete(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void deleteAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.deleteAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void release(Object obj, OpCallbacks call) {
      try {
         this._broker.release(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void releaseAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.releaseAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void refresh(Object obj, OpCallbacks call) {
      try {
         this._broker.refresh(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void refreshAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.refreshAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void evict(Object obj, OpCallbacks call) {
      try {
         this._broker.evict(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void evictAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.evictAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void evictAll(OpCallbacks call) {
      try {
         this._broker.evictAll(call);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void evictAll(Extent extent, OpCallbacks call) {
      try {
         this._broker.evictAll(extent, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object detach(Object obj, OpCallbacks call) {
      try {
         return this._broker.detach(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object[] detachAll(Collection objs, OpCallbacks call) {
      try {
         return this._broker.detachAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void detachAll(OpCallbacks call) {
      try {
         this._broker.detachAll(call);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void detachAll(OpCallbacks call, boolean flush) {
      try {
         this._broker.detachAll(call, flush);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Object attach(Object obj, boolean copyNew, OpCallbacks call) {
      try {
         return this._broker.attach(obj, copyNew, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Object[] attachAll(Collection objs, boolean copyNew, OpCallbacks call) {
      try {
         return this._broker.attachAll(objs, copyNew, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void transactional(Object pc, boolean updateVersion, OpCallbacks call) {
      try {
         this._broker.transactional(pc, updateVersion, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void transactionalAll(Collection objs, boolean updateVersion, OpCallbacks call) {
      try {
         this._broker.transactionalAll(objs, updateVersion, call);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public void nontransactional(Object pc, OpCallbacks call) {
      try {
         this._broker.nontransactional(pc, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void nontransactionalAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.nontransactionalAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Extent newExtent(Class cls, boolean subs) {
      try {
         return this._broker.newExtent(cls, subs);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Query newQuery(String language, Class cls, Object query) {
      try {
         return this._broker.newQuery(language, cls, query);
      } catch (RuntimeException var5) {
         throw this.translate(var5);
      }
   }

   public Query newQuery(String language, Object query) {
      try {
         return this._broker.newQuery(language, query);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public Seq getIdentitySequence(ClassMetaData meta) {
      try {
         return this._broker.getIdentitySequence(meta);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Seq getValueSequence(FieldMetaData fmd) {
      try {
         return this._broker.getValueSequence(fmd);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void lock(Object obj, int level, int timeout, OpCallbacks call) {
      try {
         this._broker.lock(obj, level, timeout, call);
      } catch (RuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public void lock(Object obj, OpCallbacks call) {
      try {
         this._broker.lock(obj, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public void lockAll(Collection objs, int level, int timeout, OpCallbacks call) {
      try {
         this._broker.lockAll(objs, level, timeout, call);
      } catch (RuntimeException var6) {
         throw this.translate(var6);
      }
   }

   public void lockAll(Collection objs, OpCallbacks call) {
      try {
         this._broker.lockAll(objs, call);
      } catch (RuntimeException var4) {
         throw this.translate(var4);
      }
   }

   public boolean cancelAll() {
      try {
         return this._broker.cancelAll();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void dirtyType(Class cls) {
      try {
         this._broker.dirtyType(cls);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public void close() {
      try {
         this._broker.close();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isClosed() {
      try {
         return this._broker.isClosed();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public boolean isCloseInvoked() {
      try {
         return this._broker.isCloseInvoked();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertOpen() {
      try {
         this._broker.assertOpen();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertActiveTransaction() {
      try {
         this._broker.assertActiveTransaction();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertNontransactionalRead() {
      try {
         this._broker.assertNontransactionalRead();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void assertWriteOperation() {
      try {
         this._broker.assertWriteOperation();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void beforeCompletion() {
      try {
         this._broker.beforeCompletion();
      } catch (RuntimeException var2) {
         throw this.translate(var2);
      }
   }

   public void afterCompletion(int status) {
      try {
         this._broker.afterCompletion(status);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public Object newInstance(Class cls) {
      try {
         return this._broker.newInstance(cls);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }

   public boolean isDetached(Object obj) {
      try {
         return this._broker.isDetached(obj);
      } catch (RuntimeException var3) {
         throw this.translate(var3);
      }
   }
}
