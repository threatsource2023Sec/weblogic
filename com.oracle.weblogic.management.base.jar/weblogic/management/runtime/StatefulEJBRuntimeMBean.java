package weblogic.management.runtime;

public interface StatefulEJBRuntimeMBean extends EJBRuntimeMBean {
   EJBCacheRuntimeMBean getCacheRuntime();

   EJBLockingRuntimeMBean getLockingRuntime();
}
