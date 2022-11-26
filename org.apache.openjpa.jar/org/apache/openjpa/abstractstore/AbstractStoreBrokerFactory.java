package org.apache.openjpa.abstractstore;

import java.security.AccessController;
import java.util.Map;
import java.util.Properties;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.AbstractBrokerFactory;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class AbstractStoreBrokerFactory extends AbstractBrokerFactory {
   private static final String PROP_ABSTRACT_STORE = "abstractstore.AbstractStoreManager";
   private static final Localizer s_loc = Localizer.forPackage(AbstractStoreBrokerFactory.class);
   private String _storeCls = null;
   private String _storeProps = null;
   private String _platform = null;

   public static AbstractStoreBrokerFactory getInstance(ConfigurationProvider cp) {
      Object key = toPoolKey(cp.getProperties());
      AbstractStoreBrokerFactory factory = (AbstractStoreBrokerFactory)getPooledFactoryForKey(key);
      if (factory != null) {
         return factory;
      } else {
         factory = newInstance(cp);
         pool(key, factory);
         return factory;
      }
   }

   public static AbstractStoreBrokerFactory newInstance(ConfigurationProvider cp) {
      Map map = cp.getProperties();
      String storePlugin = (String)map.get(ProductDerivations.getConfigurationKey("abstractstore.AbstractStoreManager", map));
      String storeCls = Configurations.getClassName(storePlugin);
      String storeProps = Configurations.getProperties(storePlugin);
      AbstractStoreManager store = createStoreManager(storeCls, storeProps);
      OpenJPAConfiguration conf = store.newConfiguration();
      cp.setInto(conf);
      conf.supportedOptions().removeAll(store.getUnsupportedOptions());
      return new AbstractStoreBrokerFactory(conf, storeCls, storeProps, store.getPlatform());
   }

   protected AbstractStoreBrokerFactory(OpenJPAConfiguration conf, String storeCls, String storeProps, String platform) {
      super(conf);
      this._storeCls = storeCls;
      this._storeProps = storeProps;
      this._platform = platform;
   }

   public Properties getProperties() {
      Properties props = super.getProperties();
      props.setProperty("Platform", this._platform);
      return props;
   }

   protected StoreManager newStoreManager() {
      return createStoreManager(this._storeCls, this._storeProps);
   }

   private static AbstractStoreManager createStoreManager(String cls, String props) {
      AbstractStoreManager store = (AbstractStoreManager)Configurations.newInstance(cls, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(AbstractStoreManager.class)));
      Configurations.configureInstance(store, (Configuration)null, (String)props, "abstractstore.AbstractStoreManager");
      if (store == null) {
         throw (new UserException(s_loc.get("no-store-manager", (Object)"abstractstore.AbstractStoreManager"))).setFatal(true);
      } else {
         return store;
      }
   }
}
