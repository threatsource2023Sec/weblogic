package weblogic.management.runtime;

public interface SingletonEJBRuntimeMBean extends EJBRuntimeMBean {
   EJBTimerRuntimeMBean getTimerRuntime();

   long getReadLockTotalCount();

   long getWriteLockTotalCount();

   int getReadLockTimeoutTotalCount();

   int getWriteLockTimeoutTotalCount();

   int getWaiterCurrentCount();
}
