package org.apache.openjpa.kernel;

import java.util.Collection;
import javax.transaction.Synchronization;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.event.CallbackModes;
import org.apache.openjpa.event.LifecycleEventManager;
import org.apache.openjpa.lib.util.Closeable;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.RuntimeExceptionTranslator;

public interface Broker extends Synchronization, Closeable, StoreContext, ConnectionRetainModes, DetachState, LockLevels, RestoreState, AutoClear, AutoDetach, CallbackModes {
   void setImplicitBehavior(OpCallbacks var1, RuntimeExceptionTranslator var2);

   BrokerFactory getBrokerFactory();

   int getConnectionRetainMode();

   ManagedRuntime getManagedRuntime();

   InverseManager getInverseManager();

   boolean getMultithreaded();

   void setMultithreaded(boolean var1);

   boolean getIgnoreChanges();

   void setIgnoreChanges(boolean var1);

   boolean getNontransactionalRead();

   void setNontransactionalRead(boolean var1);

   boolean getNontransactionalWrite();

   void setNontransactionalWrite(boolean var1);

   int getRestoreState();

   void setRestoreState(int var1);

   boolean getOptimistic();

   void setOptimistic(boolean var1);

   boolean getRetainState();

   void setRetainState(boolean var1);

   int getAutoClear();

   void setAutoClear(int var1);

   boolean getSyncWithManagedTransactions();

   void setSyncWithManagedTransactions(boolean var1);

   int getAutoDetach();

   void setAutoDetach(int var1);

   void setAutoDetach(int var1, boolean var2);

   boolean isDetachedNew();

   void setDetachedNew(boolean var1);

   boolean getEvictFromDataCache();

   void setEvictFromDataCache(boolean var1);

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   void addTransactionListener(Object var1);

   void removeTransactionListener(Object var1);

   int getTransactionListenerCallbackMode();

   void setTransactionListenerCallbackMode(int var1);

   void addLifecycleListener(Object var1, Class[] var2);

   void removeLifecycleListener(Object var1);

   LifecycleEventManager getLifecycleEventManager();

   int getLifecycleListenerCallbackMode();

   void setLifecycleListenerCallbackMode(int var1);

   void begin();

   void commit();

   void rollback();

   boolean syncWithManagedTransaction();

   void commitAndResume();

   void rollbackAndResume();

   boolean getRollbackOnly();

   void setRollbackOnly();

   void setRollbackOnly(Throwable var1);

   Throwable getRollbackCause();

   void setSavepoint(String var1);

   void rollbackToSavepoint();

   void rollbackToSavepoint(String var1);

   void releaseSavepoint();

   void releaseSavepoint(String var1);

   void flush();

   void preFlush();

   void validateChanges();

   void persist(Object var1, OpCallbacks var2);

   void persistAll(Collection var1, OpCallbacks var2);

   OpenJPAStateManager persist(Object var1, Object var2, OpCallbacks var3);

   void delete(Object var1, OpCallbacks var2);

   void deleteAll(Collection var1, OpCallbacks var2);

   void release(Object var1, OpCallbacks var2);

   void releaseAll(Collection var1, OpCallbacks var2);

   void refresh(Object var1, OpCallbacks var2);

   void refreshAll(Collection var1, OpCallbacks var2);

   void evict(Object var1, OpCallbacks var2);

   void evictAll(Collection var1, OpCallbacks var2);

   void evictAll(OpCallbacks var1);

   void evictAll(Extent var1, OpCallbacks var2);

   void detachAll(OpCallbacks var1);

   void detachAll(OpCallbacks var1, boolean var2);

   Object detach(Object var1, OpCallbacks var2);

   Object[] detachAll(Collection var1, OpCallbacks var2);

   Object attach(Object var1, boolean var2, OpCallbacks var3);

   Object[] attachAll(Collection var1, boolean var2, OpCallbacks var3);

   Object newInstance(Class var1);

   boolean isDetached(Object var1);

   Extent newExtent(Class var1, boolean var2);

   Query newQuery(String var1, Class var2, Object var3);

   Query newQuery(String var1, Object var2);

   Seq getIdentitySequence(ClassMetaData var1);

   Seq getValueSequence(FieldMetaData var1);

   void lock(Object var1, int var2, int var3, OpCallbacks var4);

   void lock(Object var1, OpCallbacks var2);

   void lockAll(Collection var1, int var2, int var3, OpCallbacks var4);

   void lockAll(Collection var1, OpCallbacks var2);

   boolean cancelAll();

   void dirtyType(Class var1);

   boolean beginOperation(boolean var1);

   boolean endOperation();

   boolean isClosed();

   boolean isCloseInvoked();

   void close();

   void assertOpen();

   void assertActiveTransaction();

   void assertNontransactionalRead();

   void assertWriteOperation();
}
