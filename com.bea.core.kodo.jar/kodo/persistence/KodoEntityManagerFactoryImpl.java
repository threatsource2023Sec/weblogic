package kodo.persistence;

import com.solarmetric.remote.ServerRunnable;
import com.solarmetric.remote.Transport;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import kodo.remote.ClientBrokerFactory;
import kodo.remote.KodoCommandIO;
import kodo.remote.PersistenceServerState;
import kodo.remote.RemoteTransferListener;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.lib.conf.Value;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.persistence.EntityManagerFactoryImpl;
import org.apache.openjpa.persistence.EntityManagerImpl;
import org.apache.openjpa.persistence.InvalidStateException;
import org.apache.openjpa.util.GeneralException;

/** @deprecated */
public class KodoEntityManagerFactoryImpl extends EntityManagerFactoryImpl implements KodoEntityManagerFactory {
   private static final Localizer _loc = Localizer.forPackage(KodoEntityManagerFactory.class);
   private final ReentrantLock _lock = new ReentrantLock();
   private transient Transport.Server _server = null;
   private transient Thread _serverThread = null;

   public EntityManagerImpl newEntityManagerImpl(Broker broker) {
      return new KodoEntityManagerImpl(this, broker);
   }

   public StoreCache getStoreCache() {
      return new StoreCacheImpl((org.apache.openjpa.persistence.StoreCacheImpl)super.getStoreCache());
   }

   public StoreCache getStoreCache(String name) {
      return new StoreCacheImpl((org.apache.openjpa.persistence.StoreCacheImpl)super.getStoreCache(name));
   }

   public QueryResultCache getQueryResultCache() {
      return new QueryResultCacheImpl((org.apache.openjpa.persistence.QueryResultCacheImpl)super.getQueryResultCache());
   }

   public KodoEntityManager createEntityManager() {
      return (KodoEntityManager)super.createEntityManager();
   }

   public KodoEntityManager createEntityManager(Map props) {
      return (KodoEntityManager)super.createEntityManager(props);
   }

   public void close() {
      if (this.isPersistenceServerRunning()) {
         this.stopPersistenceServer();
      }

      super.close();
   }

   public boolean startPersistenceServer() {
      BrokerFactory bf = this.getBrokerFactory();
      PersistenceServerState serverPlugin = this.getServerPlugin();
      if (bf != null && !(bf instanceof ClientBrokerFactory) && serverPlugin != null) {
         this._lock.lock();

         boolean var4;
         try {
            if (this._server != null) {
               throw new InvalidStateException(_loc.get("server-running"), (Throwable[])null, (Object)null, true);
            }

            synchronized(serverPlugin) {
               if (serverPlugin.getTransport() == null) {
                  serverPlugin.instantiate(Transport.class, bf.getConfiguration(), true);
               }
            }

            Transport trans = serverPlugin.getTransport();
            if (trans == null) {
               var4 = false;
               return var4;
            }

            this._server = trans.getServer();
            if (this._server != null) {
               KodoCommandIO io = new KodoCommandIO(bf);
               this._serverThread = new Thread(new ServerRunnable(this._server, io), "Persistence Server");
               this._serverThread.setDaemon(true);
               this._serverThread.start();
               boolean var5 = true;
               return var5;
            }

            var4 = false;
         } catch (Exception var11) {
            throw new GeneralException(_loc.get("start-transport"), var11);
         } finally {
            this._lock.unlock();
         }

         return var4;
      } else {
         return false;
      }
   }

   public boolean joinPersistenceServer() {
      if (this._serverThread == null) {
         return false;
      } else {
         try {
            this._serverThread.join();
            return true;
         } catch (InterruptedException var2) {
            throw new GeneralException(var2);
         }
      }
   }

   public boolean stopPersistenceServer() {
      if (this._server == null) {
         return false;
      } else {
         try {
            this._server.close();
         } catch (Exception var2) {
         }

         this._serverThread.interrupt();
         this._server = null;
         this._serverThread = null;
         return true;
      }
   }

   public boolean isPersistenceServerRunning() {
      return this._server != null;
   }

   public void addTransferListener(RemoteTransferListener listener) {
      BrokerFactory brokerFactory = this.getBrokerFactory();
      if (brokerFactory instanceof ClientBrokerFactory) {
         ((ClientBrokerFactory)brokerFactory).addTransferListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      BrokerFactory brokerFactory = this.getBrokerFactory();
      if (brokerFactory instanceof ClientBrokerFactory) {
         ((ClientBrokerFactory)brokerFactory).removeTransferListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   private PersistenceServerState getServerPlugin() {
      OpenJPAConfiguration conf = this.getBrokerFactory().getConfiguration();
      Value val = conf.getValue("PersistenceServer");
      return val != null && val instanceof PersistenceServerState ? (PersistenceServerState)val : null;
   }
}
