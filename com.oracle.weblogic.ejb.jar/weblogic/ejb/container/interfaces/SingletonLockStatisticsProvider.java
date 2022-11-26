package weblogic.ejb.container.interfaces;

public interface SingletonLockStatisticsProvider {
   long getReadLockTotalCount();

   long getWriteLockTotalCount();

   int getWriteLockTimeoutTotalCount();

   int getReadLockTimeoutTotalCount();

   int getWaiterCurrentCount();
}
