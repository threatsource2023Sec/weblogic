package javax.jdo;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;

public interface PersistenceManager {
   boolean isClosed();

   void close();

   Transaction currentTransaction();

   void evict(Object var1);

   void evictAll(Object... var1);

   void evictAll(Collection var1);

   void evictAll(boolean var1, Class var2);

   void evictAll();

   void refresh(Object var1);

   void refreshAll(Object... var1);

   void refreshAll(Collection var1);

   void refreshAll();

   void refreshAll(JDOException var1);

   Query newQuery();

   Query newQuery(Object var1);

   Query newQuery(String var1);

   Query newQuery(String var1, Object var2);

   Query newQuery(Class var1);

   Query newQuery(Extent var1);

   Query newQuery(Class var1, Collection var2);

   Query newQuery(Class var1, String var2);

   Query newQuery(Class var1, Collection var2, String var3);

   Query newQuery(Extent var1, String var2);

   Query newNamedQuery(Class var1, String var2);

   Extent getExtent(Class var1, boolean var2);

   Extent getExtent(Class var1);

   Object getObjectById(Object var1, boolean var2);

   Object getObjectById(Class var1, Object var2);

   Object getObjectById(Object var1);

   Object getObjectId(Object var1);

   Object getTransactionalObjectId(Object var1);

   Object newObjectIdInstance(Class var1, Object var2);

   Collection getObjectsById(Collection var1, boolean var2);

   Collection getObjectsById(Collection var1);

   /** @deprecated */
   Object[] getObjectsById(Object[] var1, boolean var2);

   Object[] getObjectsById(boolean var1, Object... var2);

   Object[] getObjectsById(Object... var1);

   Object makePersistent(Object var1);

   Object[] makePersistentAll(Object... var1);

   Collection makePersistentAll(Collection var1);

   void deletePersistent(Object var1);

   void deletePersistentAll(Object... var1);

   void deletePersistentAll(Collection var1);

   void makeTransient(Object var1);

   void makeTransientAll(Object... var1);

   void makeTransientAll(Collection var1);

   void makeTransient(Object var1, boolean var2);

   /** @deprecated */
   void makeTransientAll(Object[] var1, boolean var2);

   void makeTransientAll(boolean var1, Object... var2);

   void makeTransientAll(Collection var1, boolean var2);

   void makeTransactional(Object var1);

   void makeTransactionalAll(Object... var1);

   void makeTransactionalAll(Collection var1);

   void makeNontransactional(Object var1);

   void makeNontransactionalAll(Object... var1);

   void makeNontransactionalAll(Collection var1);

   void retrieve(Object var1);

   void retrieve(Object var1, boolean var2);

   void retrieveAll(Collection var1);

   void retrieveAll(Collection var1, boolean var2);

   void retrieveAll(Object... var1);

   /** @deprecated */
   void retrieveAll(Object[] var1, boolean var2);

   void retrieveAll(boolean var1, Object... var2);

   void setUserObject(Object var1);

   Object getUserObject();

   PersistenceManagerFactory getPersistenceManagerFactory();

   Class getObjectIdClass(Class var1);

   void setMultithreaded(boolean var1);

   boolean getMultithreaded();

   void setIgnoreCache(boolean var1);

   boolean getIgnoreCache();

   void setDatastoreReadTimeoutMillis(Integer var1);

   Integer getDatastoreReadTimeoutMillis();

   void setDatastoreWriteTimeoutMillis(Integer var1);

   Integer getDatastoreWriteTimeoutMillis();

   boolean getDetachAllOnCommit();

   void setDetachAllOnCommit(boolean var1);

   boolean getCopyOnAttach();

   void setCopyOnAttach(boolean var1);

   Object detachCopy(Object var1);

   Collection detachCopyAll(Collection var1);

   Object[] detachCopyAll(Object... var1);

   Object putUserObject(Object var1, Object var2);

   Object getUserObject(Object var1);

   Object removeUserObject(Object var1);

   void flush();

   void checkConsistency();

   FetchPlan getFetchPlan();

   Object newInstance(Class var1);

   Sequence getSequence(String var1);

   JDOConnection getDataStoreConnection();

   void addInstanceLifecycleListener(InstanceLifecycleListener var1, Class... var2);

   void removeInstanceLifecycleListener(InstanceLifecycleListener var1);

   Date getServerDate();

   Set getManagedObjects();

   Set getManagedObjects(EnumSet var1);

   Set getManagedObjects(Class... var1);

   Set getManagedObjects(EnumSet var1, Class... var2);

   FetchGroup getFetchGroup(Class var1, String var2);

   void setProperty(String var1, Object var2);

   Map getProperties();

   Set getSupportedProperties();
}
