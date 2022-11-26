package kodo.remote;

import com.solarmetric.remote.ServerRunnable;
import com.solarmetric.remote.StreamDecorator;
import com.solarmetric.remote.Transport;
import org.apache.openjpa.kernel.BrokerFactory;
import org.apache.openjpa.kernel.DelegatingBrokerFactory;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.GeneralException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UnsupportedException;

public class Remote {
   public static final String LOG_REMOTE = "kodo.Remote";
   private static final Localizer _loc = Localizer.forPackage(Remote.class);
   private final BrokerFactory _factory;
   private final PersistenceServerState _state;

   public static Remote getInstance(BrokerFactory factory) {
      return new Remote(factory);
   }

   private Remote(BrokerFactory factory) {
      this._factory = factory;
      this._state = PersistenceServerState.getInstance(factory.getConfiguration());
   }

   public boolean startPersistenceServer() {
      if (this._factory instanceof ClientBrokerFactory || this._factory instanceof DelegatingBrokerFactory && ((DelegatingBrokerFactory)this._factory).getInnermostDelegate() instanceof ClientBrokerFactory) {
         return false;
      } else if (this._state == null) {
         return false;
      } else {
         this._factory.lock();

         boolean var4;
         try {
            if (this._state.getServer() != null) {
               boolean var12 = false;
               return var12;
            }

            Transport.Server server = this._state.getTransport().getServer();
            if (server == null) {
               boolean var13 = false;
               return var13;
            }

            KodoCommandIO io = new KodoCommandIO(this._factory);
            Thread serverThread = new Thread(new ServerRunnable(server, io), "Persistence Server");
            serverThread.setDaemon(true);
            serverThread.start();
            this._state.setServer(server, serverThread);
            var4 = true;
         } catch (OpenJPAException var9) {
            throw var9;
         } catch (Exception var10) {
            throw new GeneralException(_loc.get("start-transport", this._state.getString()), var10);
         } finally {
            this._factory.unlock();
         }

         return var4;
      }
   }

   public boolean joinPersistenceServer() {
      if (this._state == null) {
         return false;
      } else if (this._state.getServerThread() == null) {
         return false;
      } else {
         try {
            this._state.getServerThread().join();
            return true;
         } catch (InterruptedException var2) {
            throw new GeneralException(var2);
         }
      }
   }

   public boolean stopPersistenceServer() {
      if (this._state == null) {
         return false;
      } else {
         this._factory.lock();

         boolean var1;
         try {
            var1 = PersistenceServerState.stopServer(this._state);
         } finally {
            this._factory.unlock();
         }

         return var1;
      }
   }

   public boolean isPersistenceServerRunning() {
      return this._state != null && this._state.getServer() != null;
   }

   public void addTransferListener(RemoteTransferListener listener) {
      BrokerFactory factory = this._factory;
      if (factory instanceof DelegatingBrokerFactory) {
         factory = ((DelegatingBrokerFactory)factory).getInnermostDelegate();
      }

      if (factory instanceof ClientBrokerFactory) {
         ((ClientBrokerFactory)factory).addTransferListener(listener);
      } else {
         throw new UnsupportedException(_loc.get("not-client-factory"));
      }
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      BrokerFactory factory = this._factory;
      if (factory instanceof DelegatingBrokerFactory) {
         factory = ((DelegatingBrokerFactory)factory).getInnermostDelegate();
      }

      if (factory instanceof ClientBrokerFactory) {
         ((ClientBrokerFactory)factory).removeTransferListener(listener);
      } else {
         throw new UnsupportedException(_loc.get("not-client-factory"));
      }
   }

   public Transport getTransport() {
      return this._state == null ? null : this._state.getTransport();
   }

   public StreamDecorator[] getStreamDecorators() {
      return this._state == null ? null : this._state.getStreamDecorators();
   }
}
