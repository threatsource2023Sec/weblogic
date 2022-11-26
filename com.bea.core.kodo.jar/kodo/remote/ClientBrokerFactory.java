package kodo.remote;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import kodo.conf.KodoVersion;
import org.apache.openjpa.kernel.AbstractBrokerFactory;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.ConfigurationProvider;

public class ClientBrokerFactory extends AbstractBrokerFactory {
   private final List _listeners = Collections.synchronizedList(new LinkedList());

   public static ClientBrokerFactory getInstance(ConfigurationProvider cp) {
      Object key = toPoolKey(cp.getProperties());
      ClientBrokerFactory factory = (ClientBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = newInstance(cp);
         pool(key, factory);
         return factory;
      }
   }

   public static ClientBrokerFactory newInstance(ConfigurationProvider cp) {
      ClientConfiguration conf = new ClientConfigurationImpl();
      cp.setInto(conf);
      return new ClientBrokerFactory(conf);
   }

   public ClientBrokerFactory(ClientConfiguration conf) {
      super(conf);
   }

   protected Object getFactoryInitializationBanner() {
      return KodoVersion.getInitializationBanner();
   }

   public Collection getTransferListeners() {
      return Collections.unmodifiableCollection(this._listeners);
   }

   public void addTransferListener(RemoteTransferListener listener) {
      this._listeners.add(listener);
   }

   public void removeTransferListener(RemoteTransferListener listener) {
      this._listeners.remove(listener);
   }

   public Properties getProperties() {
      Properties props = super.getProperties();
      props.setProperty("Platform", "Kodo Remote Client");
      return props;
   }

   protected StoreManager newStoreManager() {
      return new ClientStoreManager();
   }
}
