package org.apache.openjpa.slice.jdbc;

import java.util.Map;
import org.apache.openjpa.conf.OpenJPAVersion;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.kernel.JDBCBrokerFactory;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.util.Localizer;

public class DistributedJDBCBrokerFactory extends JDBCBrokerFactory {
   private static final Localizer _loc = Localizer.forPackage(DistributedJDBCBrokerFactory.class);

   public static DistributedJDBCBrokerFactory newInstance(ConfigurationProvider cp) {
      DistributedJDBCConfigurationImpl conf = new DistributedJDBCConfigurationImpl(cp);
      cp.setInto(conf);
      return new DistributedJDBCBrokerFactory(conf);
   }

   public static JDBCBrokerFactory getInstance(ConfigurationProvider cp) {
      Map properties = cp.getProperties();
      Object key = toPoolKey(properties);
      DistributedJDBCBrokerFactory factory = (DistributedJDBCBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = newInstance(cp);
         pool(key, factory);
         return factory;
      }
   }

   public static synchronized JDBCBrokerFactory getInstance(JDBCConfiguration conf) {
      Map properties = conf.toProperties(false);
      Object key = toPoolKey(properties);
      DistributedJDBCBrokerFactory factory = (DistributedJDBCBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = new DistributedJDBCBrokerFactory((DistributedJDBCConfiguration)conf);
         pool(key, factory);
         return factory;
      }
   }

   public DistributedJDBCBrokerFactory(DistributedJDBCConfiguration conf) {
      super(conf);
   }

   public DistributedJDBCConfiguration getConfiguration() {
      return (DistributedJDBCConfiguration)super.getConfiguration();
   }

   protected StoreManager newStoreManager() {
      return new DistributedStoreManager(this.getConfiguration());
   }

   protected Object getFactoryInitializationBanner() {
      return _loc.get("factory-init", (Object)OpenJPAVersion.VERSION_NUMBER);
   }
}
