package kodo.runtime;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import kodo.jdo.KodoDataStoreCache;
import kodo.jdo.PersistenceManagerFactoryImpl;
import kodo.jdo.QueryResultCache;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Broker;

/** @deprecated */
public class KodoPersistenceManagerFactory implements PersistenceManagerFactory {
   private final PersistenceManagerFactoryImpl _pmf;

   public static PersistenceManagerFactory getPersistenceManagerFactory(Map map) {
      return new KodoPersistenceManagerFactory((PersistenceManagerFactoryImpl)PersistenceManagerFactoryImpl.getPersistenceManagerFactory(map));
   }

   public KodoPersistenceManagerFactory(PersistenceManagerFactoryImpl pmf) {
      this._pmf = pmf;
   }

   public PersistenceManagerFactoryImpl getDelegate() {
      return this._pmf;
   }

   public boolean startPersistenceManagerServer() {
      return this.startPersistenceServer();
   }

   public boolean joinPersistenceManagerServer() {
      return this.joinPersistenceServer();
   }

   public boolean stopPersistenceManagerServer() {
      return this.stopPersistenceServer();
   }

   public boolean isPersistenceManagerServerRunning() {
      return this.isPersistenceServerRunning();
   }

   public OpenJPAConfiguration getConfiguration() {
      return this._pmf.getConfiguration();
   }

   public Properties getProperties() {
      return this._pmf.getProperties();
   }

   public Object putUserObject(Object key, Object val) {
      return this._pmf.putUserObject(key, val);
   }

   public Object getUserObject(Object key) {
      return this._pmf.getUserObject(key);
   }

   public DataStoreCache getDataStoreCache() {
      return this._pmf.getDataStoreCache();
   }

   public KodoDataStoreCache getDataStoreCache(String cacheName) {
      return this._pmf.getDataStoreCache(cacheName);
   }

   public QueryResultCache getQueryResultCache() {
      return this._pmf.getQueryResultCache();
   }

   public PersistenceManager getPersistenceManager() {
      OpenJPAConfiguration conf = this.getConfiguration();
      return this.getPersistenceManager(conf.getConnectionUserName(), conf.getConnectionPassword(), conf.isTransactionModeManaged(), conf.getConnectionRetainModeConstant());
   }

   public PersistenceManager getPersistenceManager(String user, String pass) {
      OpenJPAConfiguration conf = this.getConfiguration();
      return this.getPersistenceManager(user, pass, conf.isTransactionModeManaged(), conf.getConnectionRetainModeConstant());
   }

   public KodoPersistenceManager getPersistenceManager(boolean managed, int connRetainMode) {
      OpenJPAConfiguration conf = this.getConfiguration();
      return this.getPersistenceManager(conf.getConnectionUserName(), conf.getConnectionPassword(), managed, connRetainMode);
   }

   public KodoPersistenceManager getPersistenceManager(String user, String pass, boolean managed, int connRetainMode) {
      Broker broker = this._pmf.getDelegate().newBroker(user, pass, managed, connRetainMode, true);
      broker.lock();

      KodoPersistenceManager var7;
      try {
         KodoPersistenceManager pm = (KodoPersistenceManager)broker.getUserObject("kodo.jdo.PersistenceManager");
         if (pm == null) {
            pm = new KodoPersistenceManager(this, broker);
            broker.putUserObject("kodo.jdo.PersistenceManager", pm);
         }

         var7 = pm;
      } finally {
         broker.unlock();
      }

      return var7;
   }

   public boolean startPersistenceServer() {
      return this._pmf.startPersistenceServer();
   }

   public boolean joinPersistenceServer() {
      return this._pmf.joinPersistenceServer();
   }

   public boolean stopPersistenceServer() {
      return this._pmf.stopPersistenceServer();
   }

   public boolean isPersistenceServerRunning() {
      return this._pmf.isPersistenceServerRunning();
   }

   public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
      this._pmf.addInstanceLifecycleListener(listener, classes);
   }

   public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
      this._pmf.removeInstanceLifecycleListener(listener);
   }

   public void addTransferListener(RemoteTransferListener listener) {
      this._pmf.addTransferListener(listener);
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      this._pmf.removeTransferListener(listener);
   }

   public void close() {
      this._pmf.close();
   }

   public boolean isClosed() {
      return this._pmf.isClosed();
   }

   public int hashCode() {
      return this._pmf.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof KodoPersistenceManagerFactory) ? false : this._pmf.equals(((KodoPersistenceManagerFactory)other)._pmf);
      }
   }

   public Collection supportedOptions() {
      return this._pmf.supportedOptions();
   }

   public String getConnectionUserName() {
      return this._pmf.getConnectionUserName();
   }

   public void setConnectionUserName(String val) {
      this._pmf.setConnectionUserName(val);
   }

   public String getConnectionPassword() {
      return this._pmf.getConnectionPassword();
   }

   public void setConnectionPassword(String val) {
      this._pmf.setConnectionPassword(val);
   }

   public String getConnectionURL() {
      return this._pmf.getConnectionURL();
   }

   public void setConnectionURL(String val) {
      this._pmf.setConnectionURL(val);
   }

   public String getConnectionDriverName() {
      return this._pmf.getConnectionDriverName();
   }

   public void setConnectionDriverName(String val) {
      this._pmf.setConnectionDriverName(val);
   }

   public String getConnectionFactoryName() {
      return this._pmf.getConnectionFactoryName();
   }

   public void setConnectionFactoryName(String val) {
      this._pmf.setConnectionFactoryName(val);
   }

   public Object getConnectionFactory() {
      return this._pmf.getConnectionFactory();
   }

   public void setConnectionFactory(Object val) {
      this._pmf.setConnectionFactory(val);
   }

   public String getConnectionFactory2Name() {
      return this._pmf.getConnectionFactory2Name();
   }

   public void setConnectionFactory2Name(String val) {
      this._pmf.setConnectionFactory2Name(val);
   }

   public Object getConnectionFactory2() {
      return this._pmf.getConnectionFactory2();
   }

   public void setConnectionFactory2(Object val) {
      this._pmf.setConnectionFactory2(val);
   }

   public boolean getOptimistic() {
      return this._pmf.getOptimistic();
   }

   public void setOptimistic(boolean val) {
      this._pmf.setOptimistic(val);
   }

   public boolean getRetainValues() {
      return this._pmf.getRetainValues();
   }

   public void setRetainValues(boolean val) {
      this._pmf.setRetainValues(val);
   }

   public boolean getRestoreValues() {
      return this._pmf.getRestoreValues();
   }

   public void setRestoreValues(boolean val) {
      this._pmf.setRestoreValues(val);
   }

   public boolean getNontransactionalRead() {
      return this._pmf.getNontransactionalRead();
   }

   public void setNontransactionalRead(boolean val) {
      this._pmf.setNontransactionalRead(val);
   }

   public boolean getNontransactionalWrite() {
      return this._pmf.getNontransactionalWrite();
   }

   public void setNontransactionalWrite(boolean val) {
      this._pmf.setNontransactionalWrite(val);
   }

   public boolean getIgnoreCache() {
      return this._pmf.getIgnoreCache();
   }

   public void setIgnoreCache(boolean val) {
      this._pmf.setIgnoreCache(val);
   }

   public boolean getMultithreaded() {
      return this._pmf.getMultithreaded();
   }

   public void setMultithreaded(boolean val) {
      this._pmf.setMultithreaded(val);
   }

   public String getMapping() {
      return this._pmf.getMapping();
   }

   public void setMapping(String val) {
      this._pmf.setMapping(val);
   }

   public boolean getDetachAllOnCommit() {
      return this._pmf.getDetachAllOnCommit();
   }

   public void setDetachAllOnCommit(boolean val) {
      this._pmf.setDetachAllOnCommit(val);
   }

   public FetchConfiguration newFetchConfiguration(KodoPersistenceManager pm, org.apache.openjpa.kernel.FetchConfiguration fetch) {
      return new FetchConfiguration(pm, fetch);
   }
}
