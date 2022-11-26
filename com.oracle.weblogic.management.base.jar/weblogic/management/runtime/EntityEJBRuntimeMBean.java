package weblogic.management.runtime;

public interface EntityEJBRuntimeMBean extends EJBRuntimeMBean {
   EJBPoolRuntimeMBean getPoolRuntime();

   EJBCacheRuntimeMBean getCacheRuntime();

   EJBLockingRuntimeMBean getLockingRuntime();

   EJBTimerRuntimeMBean getTimerRuntime();

   QueryCacheRuntimeMBean getQueryCacheRuntime();
}
