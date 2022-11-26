package org.apache.openjpa.jdbc.kernel;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.conf.JDBCConfiguration;
import org.apache.openjpa.jdbc.conf.JDBCConfigurationImpl;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.MappingTool;
import org.apache.openjpa.kernel.AbstractBrokerFactory;
import org.apache.openjpa.kernel.BrokerImpl;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class JDBCBrokerFactory extends AbstractBrokerFactory {
   private static final Localizer _loc = Localizer.forPackage(JDBCBrokerFactory.class);
   private boolean _synchronizedMappings = false;

   public static JDBCBrokerFactory newInstance(ConfigurationProvider cp) {
      JDBCConfigurationImpl conf = new JDBCConfigurationImpl();
      cp.setInto(conf);
      return new JDBCBrokerFactory(conf);
   }

   public static JDBCBrokerFactory getInstance(ConfigurationProvider cp) {
      Map props = cp.getProperties();
      Object key = toPoolKey(props);
      JDBCBrokerFactory factory = (JDBCBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = newInstance(cp);
         pool(key, factory);
         return factory;
      }
   }

   public JDBCBrokerFactory(JDBCConfiguration conf) {
      super(conf);
   }

   public Properties getProperties() {
      Properties props = super.getProperties();
      String db = "Unknown";

      try {
         JDBCConfiguration conf = (JDBCConfiguration)this.getConfiguration();
         db = conf.getDBDictionaryInstance().platform;
      } catch (RuntimeException var4) {
      }

      props.setProperty("Platform", "OpenJPA JDBC Edition: " + db + " Database");
      return props;
   }

   protected StoreManager newStoreManager() {
      return new JDBCStoreManager();
   }

   protected BrokerImpl newBrokerImpl(String user, String pass) {
      BrokerImpl broker = super.newBrokerImpl(user, pass);
      this.lock();

      BrokerImpl var4;
      try {
         if (!this._synchronizedMappings) {
            this._synchronizedMappings = true;
            this.synchronizeMappings(broker.getClassLoader());
         }

         var4 = broker;
      } finally {
         this.unlock();
      }

      return var4;
   }

   protected void synchronizeMappings(ClassLoader loader) {
      JDBCConfiguration conf = (JDBCConfiguration)this.getConfiguration();
      String action = conf.getSynchronizeMappings();
      if (!StringUtils.isEmpty(action)) {
         MappingRepository repo = conf.getMappingRepositoryInstance();
         Collection classes = repo.loadPersistentTypes(false, loader);
         if (!classes.isEmpty()) {
            String props = Configurations.getProperties(action);
            action = Configurations.getClassName(action);
            MappingTool tool = new MappingTool(conf, action, false);
            Configurations.configureInstance(tool, conf, (String)props, "SynchronizeMappings");
            Iterator itr = classes.iterator();

            while(itr.hasNext()) {
               Class cls = (Class)itr.next();

               try {
                  tool.run(cls);
               } catch (IllegalArgumentException var11) {
                  throw new UserException(_loc.get("bad-synch-mappings", action, Arrays.asList(MappingTool.ACTIONS)));
               }
            }

            tool.record();
         }
      }
   }
}
