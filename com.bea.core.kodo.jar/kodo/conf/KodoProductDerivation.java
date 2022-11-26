package kodo.conf;

import java.util.Map;
import kodo.datacache.KodoConcurrentDataCache;
import kodo.datacache.KodoConcurrentQueryCache;
import kodo.datacache.KodoDataCacheManager;
import kodo.datacache.LRUDataCache;
import kodo.datacache.LRUQueryCache;
import kodo.kernel.ExecutionContextNameProviderValue;
import kodo.kernel.FinalizingKodoBroker;
import kodo.kernel.KodoBroker;
import kodo.kernel.SingleJVMExclusiveLockManager;
import kodo.manage.JMXValue;
import kodo.profile.ProfilingProxyManager;
import kodo.profile.ProfilingValue;
import kodo.remote.ClientBrokerFactory;
import kodo.remote.PersistenceServerState;
import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.conf.OpenJPAProductDerivation;
import org.apache.openjpa.lib.conf.AbstractProductDerivation;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginListValue;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.conf.Value;

public class KodoProductDerivation extends AbstractProductDerivation implements OpenJPAProductDerivation {
   public void putBrokerFactoryAliases(Map m) {
      m.put("remote", ClientBrokerFactory.class.getName());
   }

   public int getType() {
      return 100;
   }

   public String getConfigurationPrefix() {
      return "kodo";
   }

   public boolean beforeConfigurationLoad(Configuration c) {
      c.setProductName("kodo");
      if (!(c instanceof OpenJPAConfigurationImpl)) {
         return false;
      } else {
         OpenJPAConfigurationImpl conf = (OpenJPAConfigurationImpl)c;
         conf.getCompatibilityInstance().setSuperclassDiscriminatorStrategyByDefault(false);
         conf.brokerPlugin.setAlias("kodo", FinalizingKodoBroker.class.getName());
         conf.brokerPlugin.setAlias("default", FinalizingKodoBroker.class.getName());
         conf.brokerPlugin.setAlias("non-finalizing", KodoBroker.class.getName());
         conf.brokerPlugin.setString("default");
         conf.lockManagerPlugin.setAlias("sjvm", SingleJVMExclusiveLockManager.class.getName());
         conf.dataCacheManagerPlugin.setAlias("kodo", KodoDataCacheManager.class.getName());
         conf.dataCacheManagerPlugin.setDefault("kodo");
         conf.dataCacheManagerPlugin.setString("kodo");
         Value old = conf.dataCachePlugin;
         conf.dataCachePlugin = new PluginListValue(old.getProperty());
         this.replaceValue(conf, old, conf.dataCachePlugin);
         conf.dataCachePlugin.setAlias("true", KodoConcurrentDataCache.class.getName());
         conf.dataCachePlugin.setAlias("concurrent", KodoConcurrentDataCache.class.getName());
         conf.dataCachePlugin.setAlias("lru", LRUDataCache.class.getName());
         conf.dataCachePlugin.setAlias("tangosol", "kodo.datacache.TangosolDataCache");
         conf.dataCachePlugin.setAlias("gemfire", "kodo.datacache.GemFireDataCache");
         conf.queryCachePlugin.setAlias("true", KodoConcurrentQueryCache.class.getName());
         conf.queryCachePlugin.setAlias("concurrent", KodoConcurrentQueryCache.class.getName());
         conf.queryCachePlugin.setAlias("lru", LRUQueryCache.class.getName());
         conf.queryCachePlugin.setAlias("tangosol", "kodo.datacache.TangosolQueryCache");
         conf.queryCachePlugin.setAlias("gemfire", "kodo.datacache.GemFireQueryCache");
         conf.queryCachePlugin.setDefault("true");
         conf.queryCachePlugin.setString("true");
         conf.proxyManagerPlugin.setAlias("profiling", ProfilingProxyManager.class.getName());
         conf.proxyManagerPlugin.setDefault("profiling");
         conf.proxyManagerPlugin.setString("profiling");
         conf.addValue(new ExecutionContextNameProviderValue());
         conf.addValue(new JMXValue());
         conf.addValue(new ProfilingValue());
         conf.addValue(new LicenseKey(conf));
         conf.addValue(new PersistenceServerState(conf));
         return true;
      }
   }

   private void replaceValue(Configuration conf, Value oldVal, Value newVal) {
      conf.removeValue(oldVal);
      conf.addValue(newVal);
      newVal.setAliases(oldVal.getAliases());
      newVal.setDefault(oldVal.getDefault());
      if (newVal instanceof PluginValue) {
         newVal.setString(oldVal.getString());
      }

   }
}
