package weblogic.management.runtime;

public interface KodoPersistenceUnitRuntimeMBean extends PersistenceUnitRuntimeMBean {
   KodoDataCacheRuntimeMBean[] getDataCacheRuntimes();

   KodoQueryCacheRuntimeMBean[] getQueryCacheRuntimes();

   KodoQueryCompilationCacheRuntimeMBean getQueryCompilationCacheRuntime();
}
