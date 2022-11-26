package org.apache.openjpa.persistence;

import java.util.Collection;
import java.util.EnumSet;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import org.apache.openjpa.conf.OpenJPAConfiguration;

public interface OpenJPAEntityManager extends EntityManager, EntityTransaction {
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_DEMAND = 0;
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_TRANS = 1;
   /** @deprecated */
   @Deprecated
   int CONN_RETAIN_ALWAYS = 2;
   /** @deprecated */
   @Deprecated
   int DETACH_FETCH_GROUPS = 0;
   /** @deprecated */
   @Deprecated
   int DETACH_FGS = 0;
   /** @deprecated */
   @Deprecated
   int DETACH_LOADED = 1;
   /** @deprecated */
   @Deprecated
   int DETACH_ALL = 2;
   /** @deprecated */
   @Deprecated
   int RESTORE_NONE = 0;
   /** @deprecated */
   @Deprecated
   int RESTORE_IMMUTABLE = 1;
   /** @deprecated */
   @Deprecated
   int RESTORE_ALL = 2;
   /** @deprecated */
   @Deprecated
   int DETACH_CLOSE = 2;
   /** @deprecated */
   @Deprecated
   int DETACH_COMMIT = 4;
   /** @deprecated */
   @Deprecated
   int DETACH_NONTXREAD = 8;
   /** @deprecated */
   @Deprecated
   int DETACH_ROLLBACK = 16;
   /** @deprecated */
   @Deprecated
   int CLEAR_DATASTORE = 0;
   /** @deprecated */
   @Deprecated
   int CLEAR_ALL = 1;
   /** @deprecated */
   @Deprecated
   int CALLBACK_FAIL_FAST = 2;
   /** @deprecated */
   @Deprecated
   int CALLBACK_IGNORE = 4;
   /** @deprecated */
   @Deprecated
   int CALLBACK_LOG = 8;
   /** @deprecated */
   @Deprecated
   int CALLBACK_RETHROW = 16;
   /** @deprecated */
   @Deprecated
   int CALLBACK_ROLLBACK = 32;

   OpenJPAEntityManagerFactory getEntityManagerFactory();

   FetchPlan getFetchPlan();

   FetchPlan pushFetchPlan();

   void popFetchPlan();

   ConnectionRetainMode getConnectionRetainMode();

   boolean isTransactionManaged();

   /** @deprecated */
   @Deprecated
   boolean isManaged();

   boolean getSyncWithManagedTransactions();

   void setSyncWithManagedTransactions(boolean var1);

   ClassLoader getClassLoader();

   String getConnectionUserName();

   String getConnectionPassword();

   boolean getMultithreaded();

   void setMultithreaded(boolean var1);

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   boolean getNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   boolean getNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   boolean getOptimistic();

   void setOptimistic(boolean var1);

   RestoreStateType getRestoreState();

   void setRestoreState(RestoreStateType var1);

   boolean getRetainState();

   void setRetainState(boolean var1);

   DetachStateType getDetachState();

   void setDetachState(DetachStateType var1);

   AutoClearType getAutoClear();

   void setAutoClear(AutoClearType var1);

   EnumSet getAutoDetach();

   void setAutoDetach(AutoDetachType var1);

   void setAutoDetach(EnumSet var1);

   void setAutoDetach(AutoDetachType var1, boolean var2);

   boolean getEvictFromStoreCache();

   void setEvictFromStoreCache(boolean var1);

   boolean getPopulateStoreCache();

   void setPopulateStoreCache(boolean var1);

   boolean isTrackChangesByType();

   void setTrackChangesByType(boolean var1);

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   Object[] findAll(Class var1, Object... var2);

   Collection findAll(Class var1, Collection var2);

   Object findCached(Class var1, Object var2);

   Class getObjectIdClass(Class var1);

   OpenJPAEntityTransaction getTransaction();

   void setSavepoint(String var1);

   void rollbackToSavepoint();

   void rollbackToSavepoint(String var1);

   void releaseSavepoint();

   void releaseSavepoint(String var1);

   void preFlush();

   void validateChanges();

   boolean isStoreActive();

   void beginStore();

   boolean containsAll(Object... var1);

   boolean containsAll(Collection var1);

   void persistAll(Object... var1);

   void persistAll(Collection var1);

   void removeAll(Object... var1);

   void removeAll(Collection var1);

   void release(Object var1);

   void releaseAll(Object... var1);

   void releaseAll(Collection var1);

   void retrieve(Object var1);

   void retrieveAll(Object... var1);

   void retrieveAll(Collection var1);

   void refreshAll(Object... var1);

   void refreshAll(Collection var1);

   void refreshAll();

   void evict(Object var1);

   void evictAll(Object... var1);

   void evictAll(Collection var1);

   void evictAll();

   void evictAll(Class var1);

   void evictAll(Extent var1);

   Object detachCopy(Object var1);

   Collection detachAll(Collection var1);

   Object[] detachAll(Object... var1);

   Object[] mergeAll(Object... var1);

   Collection mergeAll(Collection var1);

   void transactional(Object var1, boolean var2);

   void transactionalAll(Collection var1, boolean var2);

   void transactionalAll(Object[] var1, boolean var2);

   void nontransactional(Object var1);

   void nontransactionalAll(Collection var1);

   void nontransactionalAll(Object[] var1);

   Generator getNamedGenerator(String var1);

   Generator getIdGenerator(Class var1);

   Generator getFieldGenerator(Class var1, String var2);

   Extent createExtent(Class var1, boolean var2);

   OpenJPAQuery createQuery(String var1);

   OpenJPAQuery createNamedQuery(String var1);

   OpenJPAQuery createNativeQuery(String var1);

   OpenJPAQuery createNativeQuery(String var1, Class var2);

   OpenJPAQuery createNativeQuery(String var1, String var2);

   OpenJPAQuery createQuery(Query var1);

   OpenJPAQuery createQuery(String var1, String var2);

   LockModeType getLockMode(Object var1);

   void lock(Object var1, LockModeType var2, int var3);

   void lock(Object var1);

   void lockAll(Collection var1, LockModeType var2, int var3);

   void lockAll(Collection var1);

   void lockAll(Object[] var1, LockModeType var2, int var3);

   void lockAll(Object... var1);

   boolean cancelAll();

   Object getConnection();

   Collection getManagedObjects();

   Collection getTransactionalObjects();

   Collection getPendingTransactionalObjects();

   Collection getDirtyObjects();

   boolean getOrderDirtyObjects();

   void setOrderDirtyObjects(boolean var1);

   void dirtyClass(Class var1);

   Collection getPersistedClasses();

   Collection getRemovedClasses();

   Collection getUpdatedClasses();

   Object createInstance(Class var1);

   void dirty(Object var1, String var2);

   Object getObjectId(Object var1);

   boolean isDirty(Object var1);

   boolean isTransactional(Object var1);

   boolean isPersistent(Object var1);

   boolean isNewlyPersistent(Object var1);

   boolean isRemoved(Object var1);

   boolean isDetached(Object var1);

   Object getVersion(Object var1);

   /** @deprecated */
   @Deprecated
   OpenJPAConfiguration getConfiguration();

   /** @deprecated */
   @Deprecated
   void setRestoreState(int var1);

   /** @deprecated */
   @Deprecated
   void setDetachState(int var1);

   /** @deprecated */
   @Deprecated
   void setAutoClear(int var1);

   /** @deprecated */
   @Deprecated
   void setAutoDetach(int var1);

   /** @deprecated */
   @Deprecated
   void setAutoDetach(int var1, boolean var2);

   /** @deprecated */
   @Deprecated
   boolean isLargeTransaction();

   /** @deprecated */
   @Deprecated
   void setLargeTransaction(boolean var1);

   /** @deprecated */
   @Deprecated
   void addTransactionListener(Object var1);

   /** @deprecated */
   @Deprecated
   void removeTransactionListener(Object var1);

   /** @deprecated */
   @Deprecated
   int getTransactionListenerCallbackMode();

   /** @deprecated */
   @Deprecated
   void setTransactionListenerCallbackMode(int var1);

   /** @deprecated */
   @Deprecated
   void addLifecycleListener(Object var1, Class... var2);

   /** @deprecated */
   @Deprecated
   void removeLifecycleListener(Object var1);

   /** @deprecated */
   @Deprecated
   int getLifecycleListenerCallbackMode();

   /** @deprecated */
   @Deprecated
   void setLifecycleListenerCallbackMode(int var1);

   /** @deprecated */
   @Deprecated
   void begin();

   /** @deprecated */
   @Deprecated
   void commit();

   /** @deprecated */
   @Deprecated
   void rollback();

   /** @deprecated */
   @Deprecated
   boolean isActive();

   /** @deprecated */
   @Deprecated
   void commitAndResume();

   /** @deprecated */
   @Deprecated
   void rollbackAndResume();

   /** @deprecated */
   @Deprecated
   void setRollbackOnly();

   /** @deprecated */
   @Deprecated
   void setRollbackOnly(Throwable var1);

   /** @deprecated */
   @Deprecated
   Throwable getRollbackCause();

   /** @deprecated */
   @Deprecated
   boolean getRollbackOnly();
}
