package weblogic.management.runtime;

public interface StatelessEJBRuntimeMBean extends EJBRuntimeMBean {
   EJBPoolRuntimeMBean getPoolRuntime();

   EJBTimerRuntimeMBean getTimerRuntime();
}
