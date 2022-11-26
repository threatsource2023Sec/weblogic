package kodo.jdbc.kernel;

import kodo.conf.KodoVersion;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.ConfigurationProvider;

public class KodoJDBCBrokerFactory extends JDBCBrokerFactory {
   public static JDBCBrokerFactory newInstance(ConfigurationProvider cp) {
      JDBCConfigurationImpl conf = new JDBCConfigurationImpl();
      cp.setInto(conf);
      return new KodoJDBCBrokerFactory(conf);
   }

   public static JDBCBrokerFactory getInstance(ConfigurationProvider cp) {
      Object key = toPoolKey(cp.getProperties());
      KodoJDBCBrokerFactory factory = (KodoJDBCBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = (KodoJDBCBrokerFactory)newInstance(cp);
         pool(key, factory);
         return factory;
      }
   }

   public KodoJDBCBrokerFactory(JDBCConfiguration conf) {
      super(conf);
   }

   protected Object getFactoryInitializationBanner() {
      return KodoVersion.getInitializationBanner();
   }

   protected StoreManager newStoreManager() {
      return new KodoJDBCStoreManager();
   }
}
