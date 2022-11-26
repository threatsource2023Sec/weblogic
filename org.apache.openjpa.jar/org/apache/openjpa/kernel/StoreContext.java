package org.apache.openjpa.kernel;

import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.meta.ValueMetaData;

public interface StoreContext {
   BitSet EXCLUDE_ALL = new BitSet(0);
   int OID_NOVALIDATE = 2;
   int OID_NODELETED = 4;
   int OID_COPY = 8;
   int OID_ALLOW_NEW = 16;

   Broker getBroker();

   OpenJPAConfiguration getConfiguration();

   FetchConfiguration getFetchConfiguration();

   FetchConfiguration pushFetchConfiguration();

   void popFetchConfiguration();

   ClassLoader getClassLoader();

   LockManager getLockManager();

   DelegatingStoreManager getStoreManager();

   String getConnectionUserName();

   String getConnectionPassword();

   Object findCached(Object var1, FindCallbacks var2);

   Object find(Object var1, boolean var2, FindCallbacks var3);

   Object[] findAll(Collection var1, boolean var2, FindCallbacks var3);

   Object find(Object var1, FetchConfiguration var2, BitSet var3, Object var4, int var5);

   Object[] findAll(Collection var1, FetchConfiguration var2, BitSet var3, Object var4, int var5);

   Iterator extentIterator(Class var1, boolean var2, FetchConfiguration var3, boolean var4);

   void retrieve(Object var1, boolean var2, OpCallbacks var3);

   void retrieveAll(Collection var1, boolean var2, OpCallbacks var3);

   OpenJPAStateManager embed(Object var1, Object var2, OpenJPAStateManager var3, ValueMetaData var4);

   Class getObjectIdType(Class var1);

   Object newObjectId(Class var1, Object var2);

   Collection getPersistedTypes();

   Collection getDeletedTypes();

   Collection getUpdatedTypes();

   Collection getManagedObjects();

   Collection getTransactionalObjects();

   Collection getPendingTransactionalObjects();

   Collection getDirtyObjects();

   boolean getOrderDirtyObjects();

   void setOrderDirtyObjects(boolean var1);

   OpenJPAStateManager getStateManager(Object var1);

   int getLockLevel(Object var1);

   Object getVersion(Object var1);

   boolean isDirty(Object var1);

   boolean isTransactional(Object var1);

   void transactional(Object var1, boolean var2, OpCallbacks var3);

   void transactionalAll(Collection var1, boolean var2, OpCallbacks var3);

   void nontransactional(Object var1, OpCallbacks var2);

   void nontransactionalAll(Collection var1, OpCallbacks var2);

   boolean isPersistent(Object var1);

   boolean isNew(Object var1);

   boolean isDeleted(Object var1);

   Object getObjectId(Object var1);

   int getDetachState();

   void setDetachState(int var1);

   boolean getPopulateDataCache();

   void setPopulateDataCache(boolean var1);

   boolean isTrackChangesByType();

   void setTrackChangesByType(boolean var1);

   boolean isManaged();

   boolean isActive();

   boolean isStoreActive();

   void beginStore();

   boolean hasConnection();

   Object getConnection();

   void lock();

   void unlock();
}
