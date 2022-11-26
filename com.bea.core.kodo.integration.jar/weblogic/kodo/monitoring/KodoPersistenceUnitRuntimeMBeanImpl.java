package weblogic.kodo.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.KodoDataCacheRuntimeMBean;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.management.runtime.KodoQueryCacheRuntimeMBean;
import weblogic.management.runtime.KodoQueryCompilationCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.persistence.PersistenceUnitRuntimeMBeanImpl;

public class KodoPersistenceUnitRuntimeMBeanImpl extends PersistenceUnitRuntimeMBeanImpl implements KodoPersistenceUnitRuntimeMBean {
   KodoDataCacheRuntimeMBean[] dataCacheRuntimes = null;
   KodoQueryCacheRuntimeMBean[] queryCacheRuntimes = null;
   KodoQueryCompilationCacheRuntimeMBean queryCompilationCacheRuntimes = null;

   public KodoPersistenceUnitRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public void setDataCacheRuntimes(KodoDataCacheRuntimeMBean[] dataCacheRuntimes) {
      this.dataCacheRuntimes = dataCacheRuntimes;
   }

   public void setQueryCacheRuntimes(KodoQueryCacheRuntimeMBean[] queryCacheRuntimes) {
      this.queryCacheRuntimes = queryCacheRuntimes;
   }

   public void setQueryCompilationCacheRuntime(KodoQueryCompilationCacheRuntimeMBean queryCompilationCacheRuntimes) {
      this.queryCompilationCacheRuntimes = queryCompilationCacheRuntimes;
   }

   public KodoDataCacheRuntimeMBean[] getDataCacheRuntimes() {
      return this.dataCacheRuntimes;
   }

   public KodoQueryCacheRuntimeMBean[] getQueryCacheRuntimes() {
      return this.queryCacheRuntimes;
   }

   public KodoQueryCompilationCacheRuntimeMBean getQueryCompilationCacheRuntime() {
      return this.queryCompilationCacheRuntimes;
   }
}
