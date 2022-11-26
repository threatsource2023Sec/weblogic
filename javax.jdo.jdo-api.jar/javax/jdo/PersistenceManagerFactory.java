package javax.jdo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.metadata.JDOMetadata;
import javax.jdo.metadata.TypeMetadata;

public interface PersistenceManagerFactory extends Serializable {
   void close();

   boolean isClosed();

   PersistenceManager getPersistenceManager();

   PersistenceManager getPersistenceManagerProxy();

   PersistenceManager getPersistenceManager(String var1, String var2);

   void setConnectionUserName(String var1);

   String getConnectionUserName();

   void setConnectionPassword(String var1);

   void setConnectionURL(String var1);

   String getConnectionURL();

   void setConnectionDriverName(String var1);

   String getConnectionDriverName();

   void setConnectionFactoryName(String var1);

   String getConnectionFactoryName();

   void setConnectionFactory(Object var1);

   Object getConnectionFactory();

   void setConnectionFactory2Name(String var1);

   String getConnectionFactory2Name();

   void setConnectionFactory2(Object var1);

   Object getConnectionFactory2();

   void setMultithreaded(boolean var1);

   boolean getMultithreaded();

   void setMapping(String var1);

   String getMapping();

   void setOptimistic(boolean var1);

   boolean getOptimistic();

   void setRetainValues(boolean var1);

   boolean getRetainValues();

   void setRestoreValues(boolean var1);

   boolean getRestoreValues();

   void setNontransactionalRead(boolean var1);

   boolean getNontransactionalRead();

   void setNontransactionalWrite(boolean var1);

   boolean getNontransactionalWrite();

   void setIgnoreCache(boolean var1);

   boolean getIgnoreCache();

   boolean getDetachAllOnCommit();

   void setDetachAllOnCommit(boolean var1);

   boolean getCopyOnAttach();

   void setCopyOnAttach(boolean var1);

   void setName(String var1);

   String getName();

   void setPersistenceUnitName(String var1);

   String getPersistenceUnitName();

   void setServerTimeZoneID(String var1);

   String getServerTimeZoneID();

   void setTransactionType(String var1);

   String getTransactionType();

   boolean getReadOnly();

   void setReadOnly(boolean var1);

   String getTransactionIsolationLevel();

   void setTransactionIsolationLevel(String var1);

   void setDatastoreReadTimeoutMillis(Integer var1);

   Integer getDatastoreReadTimeoutMillis();

   void setDatastoreWriteTimeoutMillis(Integer var1);

   Integer getDatastoreWriteTimeoutMillis();

   Properties getProperties();

   Collection supportedOptions();

   DataStoreCache getDataStoreCache();

   void addInstanceLifecycleListener(InstanceLifecycleListener var1, Class[] var2);

   void removeInstanceLifecycleListener(InstanceLifecycleListener var1);

   void addFetchGroups(FetchGroup... var1);

   void removeFetchGroups(FetchGroup... var1);

   void removeAllFetchGroups();

   FetchGroup getFetchGroup(Class var1, String var2);

   Set getFetchGroups();

   void registerMetadata(JDOMetadata var1);

   JDOMetadata newMetadata();

   TypeMetadata getMetadata(String var1);

   Collection getManagedClasses();
}
