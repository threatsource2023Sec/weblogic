package org.apache.openjpa.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.enhance.PCEnhancer;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.kernel.AbstractBrokerFactory;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.DelegatingBroker;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.FindCallbacks;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.Query;
import org.apache.openjpa.kernel.Seq;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.QueryMetaData;
import org.apache.openjpa.meta.SequenceMetaData;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.ImplHelper;
import org.apache.openjpa.util.RuntimeExceptionTranslator;
import org.apache.openjpa.util.UserException;

public class EntityManagerImpl implements OpenJPAEntityManagerSPI, Externalizable, FindCallbacks, OpCallbacks, Closeable, OpenJPAEntityTransaction {
   private static final Localizer _loc = Localizer.forPackage(EntityManagerImpl.class);
   private static final Object[] EMPTY_OBJECTS = new Object[0];
   private DelegatingBroker _broker;
   private EntityManagerFactoryImpl _emf;
   private Map _plans = new IdentityHashMap(1);
   private RuntimeExceptionTranslator _ret = PersistenceExceptions.getRollbackTranslator(this);

   public EntityManagerImpl() {
   }

   public EntityManagerImpl(EntityManagerFactoryImpl factory, Broker broker) {
      this.initialize(factory, broker);
   }

   private void initialize(EntityManagerFactoryImpl factory, Broker broker) {
      this._emf = factory;
      this._broker = new DelegatingBroker(broker, this._ret);
      this._broker.setImplicitBehavior(this, this._ret);
   }

   public Broker getBroker() {
      return this._broker.getDelegate();
   }

   public OpenJPAEntityManagerFactory getEntityManagerFactory() {
      return this._emf;
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._broker.getConfiguration();
   }

   public FetchPlan getFetchPlan() {
      this.assertNotCloseInvoked();
      this._broker.lock();

      FetchPlan var3;
      try {
         FetchConfiguration fc = this._broker.getFetchConfiguration();
         FetchPlan fp = (FetchPlan)this._plans.get(fc);
         if (fp == null) {
            fp = this._emf.toFetchPlan(this._broker, fc);
            this._plans.put(fc, fp);
         }

         var3 = fp;
      } finally {
         this._broker.unlock();
      }

      return var3;
   }

   public FetchPlan pushFetchPlan() {
      this.assertNotCloseInvoked();
      this._broker.lock();

      FetchPlan var1;
      try {
         this._broker.pushFetchConfiguration();
         var1 = this.getFetchPlan();
      } finally {
         this._broker.unlock();
      }

      return var1;
   }

   public void popFetchPlan() {
      this.assertNotCloseInvoked();
      this._broker.lock();

      try {
         this._broker.popFetchConfiguration();
      } finally {
         this._broker.unlock();
      }

   }

   public ConnectionRetainMode getConnectionRetainMode() {
      return ConnectionRetainMode.fromKernelConstant(this._broker.getConnectionRetainMode());
   }

   public boolean isTransactionManaged() {
      return this._broker.isManaged();
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

   public void setSyncWithManagedTransactions(boolean sync) {
      this.assertNotCloseInvoked();
      this._broker.setSyncWithManagedTransactions(sync);
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
      this.assertNotCloseInvoked();
      this._broker.setMultithreaded(multithreaded);
   }

   public boolean getIgnoreChanges() {
      return this._broker.getIgnoreChanges();
   }

   public void setIgnoreChanges(boolean val) {
      this.assertNotCloseInvoked();
      this._broker.setIgnoreChanges(val);
   }

   public boolean getNontransactionalRead() {
      return this._broker.getNontransactionalRead();
   }

   public void setNontransactionalRead(boolean val) {
      this.assertNotCloseInvoked();
      this._broker.setNontransactionalRead(val);
   }

   public boolean getNontransactionalWrite() {
      return this._broker.getNontransactionalWrite();
   }

   public void setNontransactionalWrite(boolean val) {
      this.assertNotCloseInvoked();
      this._broker.setNontransactionalWrite(val);
   }

   public boolean getOptimistic() {
      return this._broker.getOptimistic();
   }

   public void setOptimistic(boolean val) {
      this.assertNotCloseInvoked();
      this._broker.setOptimistic(val);
   }

   public RestoreStateType getRestoreState() {
      return RestoreStateType.fromKernelConstant(this._broker.getRestoreState());
   }

   public void setRestoreState(RestoreStateType val) {
      this.assertNotCloseInvoked();
      this._broker.setRestoreState(val.toKernelConstant());
   }

   public void setRestoreState(int restore) {
      this.assertNotCloseInvoked();
      this._broker.setRestoreState(restore);
   }

   public boolean getRetainState() {
      return this._broker.getRetainState();
   }

   public void setRetainState(boolean val) {
      this.assertNotCloseInvoked();
      this._broker.setRetainState(val);
   }

   public AutoClearType getAutoClear() {
      return AutoClearType.fromKernelConstant(this._broker.getAutoClear());
   }

   public void setAutoClear(AutoClearType val) {
      this.assertNotCloseInvoked();
      this._broker.setAutoClear(val.toKernelConstant());
   }

   public void setAutoClear(int autoClear) {
      this.assertNotCloseInvoked();
      this._broker.setAutoClear(autoClear);
   }

   public DetachStateType getDetachState() {
      return DetachStateType.fromKernelConstant(this._broker.getDetachState());
   }

   public void setDetachState(DetachStateType type) {
      this.assertNotCloseInvoked();
      this._broker.setDetachState(type.toKernelConstant());
   }

   public void setDetachState(int detach) {
      this.assertNotCloseInvoked();
      this._broker.setDetachState(detach);
   }

   public EnumSet getAutoDetach() {
      return AutoDetachType.toEnumSet(this._broker.getAutoDetach());
   }

   public void setAutoDetach(AutoDetachType flag) {
      this.assertNotCloseInvoked();
      this._broker.setAutoDetach(AutoDetachType.fromEnumSet(EnumSet.of(flag)));
   }

   public void setAutoDetach(EnumSet flags) {
      this.assertNotCloseInvoked();
      this._broker.setAutoDetach(AutoDetachType.fromEnumSet(flags));
   }

   public void setAutoDetach(int autoDetachFlags) {
      this.assertNotCloseInvoked();
      this._broker.setAutoDetach(autoDetachFlags);
   }

   public void setAutoDetach(AutoDetachType value, boolean on) {
      this.assertNotCloseInvoked();
      this._broker.setAutoDetach(AutoDetachType.fromEnumSet(EnumSet.of(value)), on);
   }

   public void setAutoDetach(int flag, boolean on) {
      this.assertNotCloseInvoked();
      this._broker.setAutoDetach(flag, on);
   }

   public boolean getEvictFromStoreCache() {
      return this._broker.getEvictFromDataCache();
   }

   public void setEvictFromStoreCache(boolean evict) {
      this.assertNotCloseInvoked();
      this._broker.setEvictFromDataCache(evict);
   }

   public boolean getPopulateStoreCache() {
      return this._broker.getPopulateDataCache();
   }

   public void setPopulateStoreCache(boolean cache) {
      this.assertNotCloseInvoked();
      this._broker.setPopulateDataCache(cache);
   }

   public boolean isTrackChangesByType() {
      return this._broker.isTrackChangesByType();
   }

   public void setTrackChangesByType(boolean trackByType) {
      this.assertNotCloseInvoked();
      this._broker.setTrackChangesByType(trackByType);
   }

   public boolean isLargeTransaction() {
      return this.isTrackChangesByType();
   }

   public void setLargeTransaction(boolean value) {
      this.setTrackChangesByType(value);
   }

   public Object getUserObject(Object key) {
      return this._broker.getUserObject(key);
   }

   public Object putUserObject(Object key, Object val) {
      this.assertNotCloseInvoked();
      return this._broker.putUserObject(key, val);
   }

   public void addTransactionListener(Object listener) {
      this.assertNotCloseInvoked();
      this._broker.addTransactionListener(listener);
   }

   public void removeTransactionListener(Object listener) {
      this.assertNotCloseInvoked();
      this._broker.removeTransactionListener(listener);
   }

   public EnumSet getTransactionListenerCallbackModes() {
      return CallbackMode.toEnumSet(this._broker.getTransactionListenerCallbackMode());
   }

   public void setTransactionListenerCallbackMode(CallbackMode mode) {
      this.assertNotCloseInvoked();
      this._broker.setTransactionListenerCallbackMode(CallbackMode.fromEnumSet(EnumSet.of(mode)));
   }

   public void setTransactionListenerCallbackMode(EnumSet modes) {
      this.assertNotCloseInvoked();
      this._broker.setTransactionListenerCallbackMode(CallbackMode.fromEnumSet(modes));
   }

   public int getTransactionListenerCallbackMode() {
      return this._broker.getTransactionListenerCallbackMode();
   }

   public void setTransactionListenerCallbackMode(int callbackMode) {
      throw new UnsupportedOperationException();
   }

   public void addLifecycleListener(Object listener, Class... classes) {
      this.assertNotCloseInvoked();
      this._broker.addLifecycleListener(listener, classes);
   }

   public void removeLifecycleListener(Object listener) {
      this.assertNotCloseInvoked();
      this._broker.removeLifecycleListener(listener);
   }

   public EnumSet getLifecycleListenerCallbackModes() {
      return CallbackMode.toEnumSet(this._broker.getLifecycleListenerCallbackMode());
   }

   public void setLifecycleListenerCallbackMode(CallbackMode mode) {
      this.assertNotCloseInvoked();
      this._broker.setLifecycleListenerCallbackMode(CallbackMode.fromEnumSet(EnumSet.of(mode)));
   }

   public void setLifecycleListenerCallbackMode(EnumSet modes) {
      this.assertNotCloseInvoked();
      this._broker.setLifecycleListenerCallbackMode(CallbackMode.fromEnumSet(modes));
   }

   public int getLifecycleListenerCallbackMode() {
      return this._broker.getLifecycleListenerCallbackMode();
   }

   public void setLifecycleListenerCallbackMode(int callbackMode) {
      this.assertNotCloseInvoked();
      this._broker.setLifecycleListenerCallbackMode(callbackMode);
   }

   public Object getReference(Class cls, Object oid) {
      this.assertNotCloseInvoked();
      oid = this._broker.newObjectId(cls, oid);
      return this._broker.find(oid, false, this);
   }

   public Object find(Class cls, Object oid) {
      this.assertNotCloseInvoked();
      oid = this._broker.newObjectId(cls, oid);
      return this._broker.find(oid, true, this);
   }

   public Object[] findAll(Class cls, Object... oids) {
      if (oids.length == 0) {
         return (Object[])((Object[])Array.newInstance(cls, 0));
      } else {
         Collection ret = this.findAll(cls, (Collection)Arrays.asList(oids));
         return ret.toArray((Object[])((Object[])Array.newInstance(cls, ret.size())));
      }
   }

   public Collection findAll(final Class cls, Collection oids) {
      this.assertNotCloseInvoked();
      Object[] objs = this._broker.findAll(oids, true, new FindCallbacks() {
         public Object processArgument(Object oid) {
            return EntityManagerImpl.this._broker.newObjectId(cls, oid);
         }

         public Object processReturn(Object oid, OpenJPAStateManager sm) {
            return EntityManagerImpl.this.processReturn(oid, sm);
         }
      });
      return Arrays.asList(objs);
   }

   public Object findCached(Class cls, Object oid) {
      this.assertNotCloseInvoked();
      return this._broker.findCached(this._broker.newObjectId(cls, oid), this);
   }

   public Class getObjectIdClass(Class cls) {
      this.assertNotCloseInvoked();
      return cls == null ? null : JPAFacadeHelper.fromOpenJPAObjectIdClass(this._broker.getObjectIdType(cls));
   }

   public OpenJPAEntityTransaction getTransaction() {
      if (this._broker.isManaged()) {
         throw new InvalidStateException(_loc.get("get-managed-trans"), (Throwable[])null, (Object)null, false);
      } else {
         return this;
      }
   }

   public void joinTransaction() {
      this.assertNotCloseInvoked();
      if (!this._broker.syncWithManagedTransaction()) {
         throw new TransactionRequiredException(_loc.get("no-managed-trans"), (Throwable[])null, (Object)null, false);
      }
   }

   public void begin() {
      this._broker.begin();
   }

   public void commit() {
      try {
         this._broker.commit();
      } catch (RollbackException var2) {
         throw var2;
      } catch (IllegalStateException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new RollbackException(var4);
      }
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

   public Throwable getRollbackCause() {
      if (!this.isActive()) {
         throw new IllegalStateException(_loc.get("no-transaction").getMessage());
      } else {
         return this._broker.getRollbackCause();
      }
   }

   public boolean getRollbackOnly() {
      if (!this.isActive()) {
         throw new IllegalStateException(_loc.get("no-transaction").getMessage());
      } else {
         return this._broker.getRollbackOnly();
      }
   }

   public void setRollbackOnly() {
      this._broker.setRollbackOnly();
   }

   public void setRollbackOnly(Throwable cause) {
      this._broker.setRollbackOnly(cause);
   }

   public void setSavepoint(String name) {
      this.assertNotCloseInvoked();
      this._broker.setSavepoint(name);
   }

   public void rollbackToSavepoint() {
      this.assertNotCloseInvoked();
      this._broker.rollbackToSavepoint();
   }

   public void rollbackToSavepoint(String name) {
      this.assertNotCloseInvoked();
      this._broker.rollbackToSavepoint(name);
   }

   public void releaseSavepoint() {
      this.assertNotCloseInvoked();
      this._broker.releaseSavepoint();
   }

   public void releaseSavepoint(String name) {
      this.assertNotCloseInvoked();
      this._broker.releaseSavepoint(name);
   }

   public void flush() {
      this.assertNotCloseInvoked();
      this._broker.assertOpen();
      this._broker.assertActiveTransaction();
      this._broker.flush();
   }

   public void preFlush() {
      this.assertNotCloseInvoked();
      this._broker.preFlush();
   }

   public void validateChanges() {
      this.assertNotCloseInvoked();
      this._broker.validateChanges();
   }

   public boolean isActive() {
      return this.isOpen() && this._broker.isActive();
   }

   public boolean isStoreActive() {
      return this._broker.isStoreActive();
   }

   public void beginStore() {
      this._broker.beginStore();
   }

   public boolean contains(Object entity) {
      this.assertNotCloseInvoked();
      if (entity == null) {
         return false;
      } else {
         OpenJPAStateManager sm = this._broker.getStateManager(entity);
         if (sm == null && !ImplHelper.isManagedType(this.getConfiguration(), entity.getClass())) {
            throw new ArgumentException(_loc.get("not-entity", (Object)entity.getClass()), (Throwable[])null, (Object)null, true);
         } else {
            return sm != null && !sm.isDeleted();
         }
      }
   }

   public boolean containsAll(Object... entities) {
      Object[] var2 = entities;
      int var3 = entities.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object entity = var2[var4];
         if (!this.contains(entity)) {
            return false;
         }
      }

      return true;
   }

   public boolean containsAll(Collection entities) {
      Iterator var2 = entities.iterator();

      Object entity;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         entity = var2.next();
      } while(this.contains(entity));

      return false;
   }

   public void persist(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.persist(entity, this);
   }

   public void persistAll(Object... entities) {
      this.persistAll((Collection)Arrays.asList(entities));
   }

   public void persistAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.persistAll(entities, this);
   }

   public void remove(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.delete(entity, this);
   }

   public void removeAll(Object... entities) {
      this.removeAll((Collection)Arrays.asList(entities));
   }

   public void removeAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.deleteAll(entities, this);
   }

   public void release(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.release(entity, this);
   }

   public void releaseAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.releaseAll(entities, this);
   }

   public void releaseAll(Object... entities) {
      this.releaseAll((Collection)Arrays.asList(entities));
   }

   public void refresh(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.assertWriteOperation();
      this._broker.refresh(entity, this);
   }

   public void refreshAll() {
      this.assertNotCloseInvoked();
      this._broker.assertWriteOperation();
      this._broker.refreshAll(this._broker.getTransactionalObjects(), this);
   }

   public void refreshAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.assertWriteOperation();
      this._broker.refreshAll(entities, this);
   }

   public void refreshAll(Object... entities) {
      this.refreshAll((Collection)Arrays.asList(entities));
   }

   public void retrieve(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.retrieve(entity, true, this);
   }

   public void retrieveAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.retrieveAll(entities, true, this);
   }

   public void retrieveAll(Object... entities) {
      this.retrieveAll((Collection)Arrays.asList(entities));
   }

   public void evict(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.evict(entity, this);
   }

   public void evictAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.evictAll((Collection)entities, this);
   }

   public void evictAll(Object... entities) {
      this.evictAll((Collection)Arrays.asList(entities));
   }

   public void evictAll() {
      this.assertNotCloseInvoked();
      this._broker.evictAll(this);
   }

   public void evictAll(Class cls) {
      this.assertNotCloseInvoked();
      this._broker.evictAll((org.apache.openjpa.kernel.Extent)this._broker.newExtent(cls, true), this);
   }

   public void evictAll(Extent extent) {
      this.assertNotCloseInvoked();
      this._broker.evictAll((org.apache.openjpa.kernel.Extent)((ExtentImpl)extent).getDelegate(), this);
   }

   public Object detachCopy(Object entity) {
      this.assertNotCloseInvoked();
      return this._broker.detach(entity, this);
   }

   public Object[] detachAll(Object... entities) {
      this.assertNotCloseInvoked();
      return this._broker.detachAll(Arrays.asList(entities), this);
   }

   public Collection detachAll(Collection entities) {
      this.assertNotCloseInvoked();
      return Arrays.asList(this._broker.detachAll(entities, this));
   }

   public Object merge(Object entity) {
      this.assertNotCloseInvoked();
      return this._broker.attach(entity, true, this);
   }

   public Object[] mergeAll(Object... entities) {
      return entities.length == 0 ? EMPTY_OBJECTS : this.mergeAll((Collection)Arrays.asList(entities)).toArray();
   }

   public Collection mergeAll(Collection entities) {
      this.assertNotCloseInvoked();
      return Arrays.asList(this._broker.attachAll(entities, true, this));
   }

   public void transactional(Object entity, boolean updateVersion) {
      this.assertNotCloseInvoked();
      this._broker.transactional(entity, updateVersion, this);
   }

   public void transactionalAll(Collection objs, boolean updateVersion) {
      this.assertNotCloseInvoked();
      this._broker.transactionalAll(objs, updateVersion, this);
   }

   public void transactionalAll(Object[] objs, boolean updateVersion) {
      this.assertNotCloseInvoked();
      this._broker.transactionalAll(Arrays.asList(objs), updateVersion, this);
   }

   public void nontransactional(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.nontransactional(entity, this);
   }

   public void nontransactionalAll(Collection objs) {
      this.assertNotCloseInvoked();
      this._broker.nontransactionalAll(objs, this);
   }

   public void nontransactionalAll(Object[] objs) {
      this.assertNotCloseInvoked();
      this._broker.nontransactionalAll(Arrays.asList(objs), this);
   }

   public Generator getNamedGenerator(String name) {
      this.assertNotCloseInvoked();

      try {
         SequenceMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getSequenceMetaData(name, this._broker.getClassLoader(), true);
         Seq seq = meta.getInstance(this._broker.getClassLoader());
         return new GeneratorImpl(seq, name, this._broker, (ClassMetaData)null);
      } catch (RuntimeException var4) {
         throw PersistenceExceptions.toPersistenceException(var4);
      }
   }

   public Generator getIdGenerator(Class forClass) {
      this.assertNotCloseInvoked();

      try {
         ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(forClass, this._broker.getClassLoader(), true);
         Seq seq = this._broker.getIdentitySequence(meta);
         return seq == null ? null : new GeneratorImpl(seq, (String)null, this._broker, meta);
      } catch (Exception var4) {
         throw PersistenceExceptions.toPersistenceException(var4);
      }
   }

   public Generator getFieldGenerator(Class forClass, String fieldName) {
      this.assertNotCloseInvoked();

      try {
         ClassMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getMetaData(forClass, this._broker.getClassLoader(), true);
         FieldMetaData fmd = meta.getField(fieldName);
         if (fmd == null) {
            throw new ArgumentException(_loc.get("no-named-field", forClass, fieldName), (Throwable[])null, (Object)null, false);
         } else {
            Seq seq = this._broker.getValueSequence(fmd);
            return seq == null ? null : new GeneratorImpl(seq, (String)null, this._broker, meta);
         }
      } catch (Exception var6) {
         throw PersistenceExceptions.toPersistenceException(var6);
      }
   }

   public Extent createExtent(Class cls, boolean subclasses) {
      this.assertNotCloseInvoked();
      return new ExtentImpl(this, this._broker.newExtent(cls, subclasses));
   }

   public OpenJPAQuery createQuery(String query) {
      return this.createQuery("javax.persistence.JPQL", query);
   }

   public OpenJPAQuery createQuery(String language, String query) {
      this.assertNotCloseInvoked();

      try {
         Query q = this._broker.newQuery(language, query);
         if ("javax.persistence.JPQL".equals(language)) {
            q.compile();
         }

         return new QueryImpl(this, this._ret, q);
      } catch (RuntimeException var4) {
         throw PersistenceExceptions.toPersistenceException(var4);
      }
   }

   public OpenJPAQuery createQuery(javax.persistence.Query query) {
      if (query == null) {
         return this.createQuery((String)null);
      } else {
         this.assertNotCloseInvoked();
         Query q = ((QueryImpl)query).getDelegate();
         return new QueryImpl(this, this._ret, this._broker.newQuery(q.getLanguage(), q));
      }
   }

   public OpenJPAQuery createNamedQuery(String name) {
      this.assertNotCloseInvoked();
      this._broker.assertOpen();

      try {
         QueryMetaData meta = this._broker.getConfiguration().getMetaDataRepositoryInstance().getQueryMetaData((Class)null, name, this._broker.getClassLoader(), true);
         Query del = this._broker.newQuery(meta.getLanguage(), (Object)null);
         meta.setInto(del);
         del.compile();
         OpenJPAQuery q = new QueryImpl(this, this._ret, del);
         String[] hints = meta.getHintKeys();
         Object[] values = meta.getHintValues();

         for(int i = 0; i < hints.length; ++i) {
            q.setHint(hints[i], values[i]);
         }

         return q;
      } catch (RuntimeException var8) {
         throw PersistenceExceptions.toPersistenceException(var8);
      }
   }

   public OpenJPAQuery createNativeQuery(String query) {
      validateSQL(query);
      return this.createQuery("openjpa.SQL", query);
   }

   public OpenJPAQuery createNativeQuery(String query, Class cls) {
      return this.createNativeQuery(query).setResultClass(cls);
   }

   public OpenJPAQuery createNativeQuery(String query, String mappingName) {
      this.assertNotCloseInvoked();
      validateSQL(query);
      Query kernelQuery = this._broker.newQuery("openjpa.SQL", query);
      kernelQuery.setResultMapping((Class)null, mappingName);
      return new QueryImpl(this, this._ret, kernelQuery);
   }

   private static void validateSQL(String query) {
      if (StringUtils.trimToNull(query) == null) {
         throw new ArgumentException(_loc.get("no-sql"), (Throwable[])null, (Object)null, false);
      }
   }

   public void setFlushMode(FlushModeType flushMode) {
      this.assertNotCloseInvoked();
      this._broker.assertOpen();
      this._broker.getFetchConfiguration().setFlushBeforeQueries(toFlushBeforeQueries(flushMode));
   }

   public FlushModeType getFlushMode() {
      this.assertNotCloseInvoked();
      this._broker.assertOpen();
      return fromFlushBeforeQueries(this._broker.getFetchConfiguration().getFlushBeforeQueries());
   }

   static FlushModeType fromFlushBeforeQueries(int flush) {
      switch (flush) {
         case 0:
            return FlushModeType.AUTO;
         case 1:
            return FlushModeType.COMMIT;
         default:
            return null;
      }
   }

   static int toFlushBeforeQueries(FlushModeType flushMode) {
      if (flushMode == null) {
         return 2;
      } else if (flushMode == FlushModeType.AUTO) {
         return 0;
      } else if (flushMode == FlushModeType.COMMIT) {
         return 1;
      } else {
         throw new ArgumentException(flushMode.toString(), (Throwable[])null, (Object)null, false);
      }
   }

   public void clear() {
      this.assertNotCloseInvoked();
      this._broker.detachAll(this, false);
   }

   public Object getDelegate() {
      this._broker.assertOpen();
      this.assertNotCloseInvoked();
      return this;
   }

   public LockModeType getLockMode(Object entity) {
      this.assertNotCloseInvoked();
      return fromLockLevel(this._broker.getLockLevel(entity));
   }

   public void lock(Object entity, LockModeType mode) {
      this.assertNotCloseInvoked();
      this._broker.lock(entity, toLockLevel(mode), -1, this);
   }

   public void lock(Object entity) {
      this.assertNotCloseInvoked();
      this._broker.lock(entity, this);
   }

   public void lock(Object entity, LockModeType mode, int timeout) {
      this.assertNotCloseInvoked();
      this._broker.lock(entity, toLockLevel(mode), timeout, this);
   }

   public void lockAll(Collection entities) {
      this.assertNotCloseInvoked();
      this._broker.lockAll(entities, this);
   }

   public void lockAll(Collection entities, LockModeType mode, int timeout) {
      this.assertNotCloseInvoked();
      this._broker.lockAll(entities, toLockLevel(mode), timeout, this);
   }

   public void lockAll(Object... entities) {
      this.lockAll((Collection)Arrays.asList(entities));
   }

   public void lockAll(Object[] entities, LockModeType mode, int timeout) {
      this.lockAll((Collection)Arrays.asList(entities), mode, timeout);
   }

   static LockModeType fromLockLevel(int level) {
      if (level < 10) {
         return null;
      } else {
         return level < 20 ? LockModeType.READ : LockModeType.WRITE;
      }
   }

   static int toLockLevel(LockModeType mode) {
      if (mode == null) {
         return 0;
      } else if (mode == LockModeType.READ) {
         return 10;
      } else if (mode == LockModeType.WRITE) {
         return 20;
      } else {
         throw new ArgumentException(mode.toString(), (Throwable[])null, (Object)null, true);
      }
   }

   public boolean cancelAll() {
      return this._broker.cancelAll();
   }

   public Object getConnection() {
      return this._broker.getConnection();
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
      this.assertNotCloseInvoked();
      this._broker.setOrderDirtyObjects(order);
   }

   public void dirtyClass(Class cls) {
      this.assertNotCloseInvoked();
      this._broker.dirtyType(cls);
   }

   public Collection getPersistedClasses() {
      return this._broker.getPersistedTypes();
   }

   public Collection getUpdatedClasses() {
      return this._broker.getUpdatedTypes();
   }

   public Collection getRemovedClasses() {
      return this._broker.getDeletedTypes();
   }

   public Object createInstance(Class cls) {
      this.assertNotCloseInvoked();
      return this._broker.newInstance(cls);
   }

   public void close() {
      this.assertNotCloseInvoked();
      this._broker.close();
   }

   public boolean isOpen() {
      return !this._broker.isCloseInvoked();
   }

   public void dirty(Object o, String field) {
      this.assertNotCloseInvoked();
      OpenJPAStateManager sm = this._broker.getStateManager(o);

      try {
         if (sm != null) {
            sm.dirty(field);
         }

      } catch (Exception var5) {
         throw PersistenceExceptions.toPersistenceException(var5);
      }
   }

   public Object getObjectId(Object o) {
      this.assertNotCloseInvoked();
      return JPAFacadeHelper.fromOpenJPAObjectId(this._broker.getObjectId(o));
   }

   public boolean isDirty(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.isDirty(o);
   }

   public boolean isTransactional(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.isTransactional(o);
   }

   public boolean isPersistent(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.isPersistent(o);
   }

   public boolean isNewlyPersistent(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.isNew(o);
   }

   public boolean isRemoved(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.isDeleted(o);
   }

   public boolean isDetached(Object entity) {
      this.assertNotCloseInvoked();
      return this._broker.isDetached(entity);
   }

   public Object getVersion(Object o) {
      this.assertNotCloseInvoked();
      return this._broker.getVersion(o);
   }

   void assertNotCloseInvoked() {
      if (!this._broker.isClosed() && this._broker.isCloseInvoked()) {
         throw new InvalidStateException(_loc.get("close-invoked"), (Throwable[])null, (Object)null, true);
      }
   }

   public Object processArgument(Object arg) {
      return arg;
   }

   public Object processReturn(Object oid, OpenJPAStateManager sm) {
      return sm != null && !sm.isDeleted() ? sm.getManagedInstance() : null;
   }

   public int processArgument(int op, Object obj, OpenJPAStateManager sm) {
      switch (op) {
         case 1:
            if (sm == null && !this._broker.isDetached(obj)) {
               return 2;
            }

            if (sm != null && !sm.isDetached() && !sm.isPersistent()) {
               return 2;
            }

            if (sm != null && sm.isDeleted()) {
               return 0;
            }
            break;
         case 2:
            if (sm == null) {
               throw (new UserException(_loc.get("not-managed", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
            }
            break;
         case 6:
            if (sm != null && sm.isDeleted()) {
               throw (new UserException(_loc.get("removed", (Object)Exceptions.toString(obj)))).setFailedObject(obj);
            }

            if (sm != null && !sm.isDetached()) {
               return 2;
            }
      }

      return 6;
   }

   public int hashCode() {
      return this._broker.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof EntityManagerImpl) ? false : this._broker.equals(((EntityManagerImpl)other)._broker);
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      try {
         this._ret = PersistenceExceptions.getRollbackTranslator(this);
         Object factoryKey = in.readObject();
         AbstractBrokerFactory factory = AbstractBrokerFactory.getPooledFactoryForKey(factoryKey);
         byte[] brokerBytes = (byte[])((byte[])in.readObject());
         ObjectInputStream innerIn = new BrokerBytesInputStream(brokerBytes, factory.getConfiguration());
         Broker broker = (Broker)innerIn.readObject();
         EntityManagerFactoryImpl emf = (EntityManagerFactoryImpl)JPAFacadeHelper.toEntityManagerFactory(broker.getBrokerFactory());
         broker.putUserObject("org.apache.openjpa.persistence.EntityManager", this);
         this.initialize(emf, broker);
      } catch (RuntimeException var9) {
         RuntimeException re = var9;

         try {
            re = this._ret.translate(re);
         } catch (Exception var8) {
         }

         throw re;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      try {
         Object factoryKey = ((AbstractBrokerFactory)this._broker.getBrokerFactory()).getPoolKey();
         out.writeObject(factoryKey);
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         ObjectOutputStream innerOut = new ObjectOutputStream(baos);
         innerOut.writeObject(this._broker.getDelegate());
         innerOut.flush();
         out.writeObject(baos.toByteArray());
      } catch (RuntimeException var6) {
         RuntimeException re = var6;

         try {
            re = this._ret.translate(re);
         } catch (Exception var5) {
         }

         throw re;
      }
   }

   private static class BrokerBytesInputStream extends ObjectInputStream {
      private OpenJPAConfiguration conf;

      BrokerBytesInputStream(byte[] bytes, OpenJPAConfiguration conf) throws IOException {
         super(new ByteArrayInputStream(bytes));
         if (conf == null) {
            throw new IllegalArgumentException("Illegal null argument to ObjectInputStreamWithLoader");
         } else {
            this.conf = conf;
         }
      }

      private Class primitiveType(char type) {
         switch (type) {
            case 'B':
               return Byte.TYPE;
            case 'C':
               return Character.TYPE;
            case 'D':
               return Double.TYPE;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
               return null;
            case 'F':
               return Float.TYPE;
            case 'I':
               return Integer.TYPE;
            case 'J':
               return Long.TYPE;
            case 'S':
               return Short.TYPE;
            case 'Z':
               return Boolean.TYPE;
         }
      }

      protected Class resolveClass(ObjectStreamClass classDesc) throws IOException, ClassNotFoundException {
         String cname = classDesc.getName();
         if (!cname.startsWith("[")) {
            return this.lookupClass(cname);
         } else {
            int dcount;
            for(dcount = 1; cname.charAt(dcount) == '['; ++dcount) {
            }

            Class component;
            if (cname.charAt(dcount) == 'L') {
               component = this.lookupClass(cname.substring(dcount + 1, cname.length() - 1));
            } else {
               if (cname.length() != dcount + 1) {
                  throw new ClassNotFoundException(cname);
               }

               component = this.primitiveType(cname.charAt(dcount));
            }

            int[] dim = new int[dcount];

            for(int i = 0; i < dcount; ++i) {
               dim[i] = 0;
            }

            return Array.newInstance(component, dim).getClass();
         }
      }

      private Class lookupClass(String className) throws ClassNotFoundException {
         try {
            return Class.forName(className);
         } catch (ClassNotFoundException var6) {
            if (PCEnhancer.isPCSubclassName(className)) {
               String superName = PCEnhancer.toManagedTypeName(className);
               ClassMetaData[] metas = this.conf.getMetaDataRepositoryInstance().getMetaDatas();

               for(int i = 0; i < metas.length; ++i) {
                  if (superName.equals(metas[i].getDescribedType().getName())) {
                     return PCRegistry.getPCType(metas[i].getDescribedType());
                  }
               }

               return Class.forName(className);
            } else {
               throw var6;
            }
         }
      }
   }
}
