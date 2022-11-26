package weblogic.management.runtime;

public interface EJBLockingRuntimeMBean extends RuntimeMBean {
   int getLockEntriesCurrentCount();

   long getLockManagerAccessCount();

   long getWaiterTotalCount();

   int getWaiterCurrentCount();

   long getTimeoutTotalCount();
}
