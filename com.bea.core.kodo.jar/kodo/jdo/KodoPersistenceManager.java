package kodo.jdo;

import java.util.Collection;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;
import javax.jdo.datastore.Sequence;
import javax.resource.cci.Connection;
import javax.resource.cci.LocalTransaction;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.ee.ManagedRuntime;
import org.apache.openjpa.event.CallbackModes;
import org.apache.openjpa.kernel.AutoClear;
import org.apache.openjpa.kernel.ConnectionRetainModes;
import org.apache.openjpa.kernel.LockLevels;
import org.apache.openjpa.lib.util.Closeable;

public interface KodoPersistenceManager extends PersistenceManager, Transaction, Connection, LocalTransaction, javax.resource.spi.LocalTransaction, Closeable, ConnectionRetainModes, LockLevels, AutoClear, CallbackModes {
   OpenJPAConfiguration getConfiguration();

   int getConnectionRetainMode();

   boolean isManaged();

   ManagedRuntime getManagedRuntime();

   boolean getSyncWithManagedTransactions();

   void setSyncWithManagedTransactions(boolean var1);

   ClassLoader getClassLoader();

   String getConnectionUserName();

   String getConnectionPassword();

   boolean getRestoreMutableValues();

   void setRestoreMutableValues(boolean var1);

   boolean getDetachAllOnRollback();

   void setDetachAllOnRollback(boolean var1);

   boolean getDetachAllOnClose();

   void setDetachAllOnClose(boolean var1);

   boolean getDetachAllOnNontransactionalRead();

   void setDetachAllOnNontransactionalRead(boolean var1);

   int getAutoClear();

   void setAutoClear(int var1);

   boolean getEvictFromDataStoreCache();

   void setEvictFromDataStoreCache(boolean var1);

   boolean getPopulateDataStoreCache();

   void setPopulateDataStoreCache(boolean var1);

   boolean isLargeTransaction();

   void setLargeTransaction(boolean var1);

   int getInstanceLifecycleListenerCallbackMode();

   void setInstanceLifecycleListenerCallbackMode(int var1);

   void addTransactionListener(Object var1);

   void removeTransactionListener(Object var1);

   int getTransactionListenerCallbackMode();

   void setTransactionListenerCallbackMode(int var1);

   Object getCachedObjectById(Object var1);

   boolean isStoreActive();

   void beginStore();

   void commitAndResume();

   void rollbackAndResume();

   void setSavepoint(String var1);

   void rollbackToSavepoint();

   void rollbackToSavepoint(String var1);

   void releaseSavepoint();

   void releaseSavepoint(String var1);

   void preFlush();

   void retrieve(Object var1, boolean var2);

   void evictAll(Class var1);

   void evictAll(Extent var1);

   void detachAll();

   Object attachCopy(Object var1, boolean var2);

   Collection attachCopyAll(Collection var1, boolean var2);

   Object[] attachCopyAll(Object[] var1, boolean var2);

   void lockPersistent(Object var1, int var2, int var3);

   void lockPersistent(Object var1);

   void lockPersistentAll(Collection var1, int var2, int var3);

   void lockPersistentAll(Collection var1);

   void lockPersistentAll(Object[] var1, int var2, int var3);

   void lockPersistentAll(Object[] var1);

   boolean cancelAll();

   Sequence getIdentitySequence(Class var1);

   Sequence getFieldSequence(Class var1, String var2);

   Collection getManagedObjects();

   Collection getTransactionalObjects();

   Collection getPendingTransactionalObjects();

   Collection getDirtyObjects();

   boolean getOrderDirtyObjects();

   void setOrderDirtyObjects(boolean var1);

   void makeClassDirty(Class var1);

   Collection getDirtyClasses();

   Collection getAddedClasses();

   Collection getDeletedClasses();

   Collection getUpdatedClasses();
}
