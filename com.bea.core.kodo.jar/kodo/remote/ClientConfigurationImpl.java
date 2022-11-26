package kodo.remote;

import com.solarmetric.remote.PoolingTransport;
import com.solarmetric.remote.Transport;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class ClientConfigurationImpl extends OpenJPAConfigurationImpl implements ClientConfiguration {
   private static final Localizer _loc = Localizer.forPackage(ClientConfigurationImpl.class);
   protected KodoCommandIO io;

   public ClientConfigurationImpl() {
      this(true, true);
   }

   public ClientConfigurationImpl(boolean derivations, boolean loadGlobals) {
      super(false, false);
      this.io = null;
      this.lockManagerPlugin.setAlias("client", "kodo.remote.ClientLockManager");
      this.lockManagerPlugin.setDefault("client");
      this.lockManagerPlugin.setString("client");
      this.metaRepositoryPlugin.setAlias("default", ClientMetaDataRepository.class.getName());
      this.metaRepositoryPlugin.setDefault("default");
      this.metaRepositoryPlugin.setString("default");
      if (derivations) {
         ProductDerivations.beforeConfigurationLoad(this);
      }

      if (loadGlobals) {
         this.loadGlobals();
      }

   }

   public synchronized KodoCommandIO getCommandIO() {
      if (this.io == null) {
         this.io = new KodoCommandIO(this);
      }

      return this.io;
   }

   public Object getConnectionFactory() {
      synchronized(this.connectionFactory) {
         if (this.connectionFactory.get() == null) {
            Transport transport = PersistenceServerState.getInstance(this).getTransport();
            if (transport == null) {
               throw (new UserException(_loc.get("no-broker-server"))).setFatal(true);
            }

            Object factory = new PoolingTransport(transport, this.getCommandIO());
            String props = this.getConnectionFactoryProperties();
            Configurations.configureInstance(factory, this, props, "ConnectionFactoryProperties");
            this.connectionFactory.set(factory, true);
         }
      }

      return this.connectionFactory.get();
   }
}
