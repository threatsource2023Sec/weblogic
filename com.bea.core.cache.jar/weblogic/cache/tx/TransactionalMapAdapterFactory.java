package weblogic.cache.tx;

import java.util.Map;
import weblogic.cache.CacheMap;
import weblogic.cache.CacheRuntimeException;
import weblogic.cache.MapAdapterFactory;
import weblogic.cache.configuration.CacheBean;
import weblogic.cache.configuration.CacheProperties;
import weblogic.cache.configuration.ConfigurationException;

public class TransactionalMapAdapterFactory implements MapAdapterFactory {
   public CacheMap adapt(CacheMap map, CacheBean conf) {
      CacheProperties.TransactionTypeValue type = conf.getTransactionType();
      if (type == CacheProperties.TransactionTypeValue.None) {
         return map;
      } else if (!conf.getLockingEnabled()) {
         throw new ConfigurationException("You must enable locking to use transactions.", "LockingEnabled", "false");
      } else {
         Object jta;
         try {
            if (conf.getTransactionManager() == CacheProperties.TransactionManagerValue.WLS) {
               jta = new WLSJTAIntegration();
            } else {
               jta = new LocalJTAIntegration();
            }
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new CacheRuntimeException(var7.getMessage(), var7);
         }

         Object trans;
         if (type == CacheProperties.TransactionTypeValue.Pessimistic) {
            trans = new PessimisticMapAdapter(map, (JTAIntegration)jta);
         } else {
            trans = new OptimisticMapAdapter(map, (JTAIntegration)jta);
         }

         this.configure((TransactionalMapAdapter)trans, conf);
         return (CacheMap)trans;
      }
   }

   public CacheMap unwrap(CacheMap map) {
      if (map instanceof TransactionalMapAdapter) {
         map = ((TransactionalMapAdapter)map).getDelegate();
      }

      return map;
   }

   public void reconfigure(CacheMap map, CacheBean oldConf, CacheBean conf, Map diff) {
      if (map instanceof TransactionalMapAdapter) {
         this.configure((TransactionalMapAdapter)map, conf);
      }

   }

   private void configure(TransactionalMapAdapter map, CacheBean conf) {
      map.setIsolation(conf.getTransactionIsolation());
   }
}
