package weblogic.kodo.monitoring;

import java.util.Map;
import javax.persistence.EntityManagerFactory;
import kodo.datacache.KodoDataCacheManager;
import kodo.datacache.MonitoringDataCache;
import kodo.datacache.MonitoringQueryCache;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.datacache.DataCacheManager;
import org.apache.openjpa.persistence.OpenJPAEntityManagerFactorySPI;
import weblogic.management.ManagementException;
import weblogic.management.runtime.KodoDataCacheRuntimeMBean;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.management.runtime.KodoQueryCacheRuntimeMBean;
import weblogic.management.runtime.KodoQueryCompilationCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class KodoPersistenceUnitRuntimeMBeanFactory {
   private static KodoPersistenceUnitRuntimeMBeanFactory factory = new KodoPersistenceUnitRuntimeMBeanFactory();

   public static KodoPersistenceUnitRuntimeMBeanFactory getInstance() {
      return factory;
   }

   public KodoPersistenceUnitRuntimeMBean createKodoPersistenceUnitRuntimeMBean(String name, RuntimeMBean parent, EntityManagerFactory entityfactory) throws ManagementException {
      if (!(entityfactory instanceof OpenJPAEntityManagerFactorySPI)) {
         return null;
      } else {
         OpenJPAConfiguration configuration = ((OpenJPAEntityManagerFactorySPI)entityfactory).getConfiguration();
         Map queryCompile = configuration.getQueryCompilationCacheInstance();
         DataCacheManager manager = configuration.getDataCacheManagerInstance();
         if (manager != null && manager instanceof KodoDataCacheManager) {
            KodoPersistenceUnitRuntimeMBeanImpl persistenceUnitRuntimeMBean = new KodoPersistenceUnitRuntimeMBeanImpl(name, parent);
            KodoDataCacheManager kodoManager = (KodoDataCacheManager)manager;
            KodoDataCacheRuntimeMBean[] dataCacheMBeans = this.getDataCacheRuntimeMBean(persistenceUnitRuntimeMBean, kodoManager);
            KodoQueryCacheRuntimeMBean[] queryCacheMBeans = this.getQueryCacheRuntimeMBean(persistenceUnitRuntimeMBean, kodoManager);
            KodoQueryCompilationCacheRuntimeMBean queryCompilationMBeans = this.getQueryCompilationCacheRuntimeMBean(persistenceUnitRuntimeMBean, queryCompile);
            persistenceUnitRuntimeMBean.setDataCacheRuntimes(dataCacheMBeans);
            persistenceUnitRuntimeMBean.setQueryCacheRuntimes(queryCacheMBeans);
            persistenceUnitRuntimeMBean.setQueryCompilationCacheRuntime(queryCompilationMBeans);
            return persistenceUnitRuntimeMBean;
         } else {
            return null;
         }
      }
   }

   private KodoDataCacheRuntimeMBean[] getDataCacheRuntimeMBean(RuntimeMBean parent, KodoDataCacheManager manager) throws ManagementException {
      String[] names = manager.getDataCacheNames();
      if (names != null && names.length != 0) {
         KodoDataCacheRuntimeMBean[] dataCacheMBeans = new KodoDataCacheRuntimeMBean[names.length];

         for(int i = 0; i < names.length; ++i) {
            MonitoringDataCache dataCache = (MonitoringDataCache)manager.getDataCache(names[i]);
            dataCacheMBeans[i] = new KodoDataCacheRuntimeMBeanImpl(dataCache.getName(), parent, manager, dataCache);
         }

         return dataCacheMBeans;
      } else {
         return null;
      }
   }

   private KodoQueryCacheRuntimeMBean[] getQueryCacheRuntimeMBean(RuntimeMBean parent, KodoDataCacheManager manager) throws ManagementException {
      MonitoringQueryCache queryCache = (MonitoringQueryCache)manager.getSystemQueryCache();
      return queryCache != null ? new KodoQueryCacheRuntimeMBean[]{new KodoQueryCacheRuntimeMBeanImpl(parent.getName(), parent, queryCache)} : null;
   }

   private KodoQueryCompilationCacheRuntimeMBean getQueryCompilationCacheRuntimeMBean(RuntimeMBean parent, Map cache) throws ManagementException {
      return cache != null ? new KodoQueryCompilationCacheRuntimeMBeanImpl(parent.getName(), parent, cache) : null;
   }
}
