package weblogic.management.runtime;

public interface EJBPoolRuntimeMBean extends RuntimeMBean {
   long getAccessTotalCount();

   long getMissTotalCount();

   long getDestroyedTotalCount();

   /** @deprecated */
   @Deprecated
   int getIdleBeansCount();

   int getPooledBeansCurrentCount();

   /** @deprecated */
   @Deprecated
   int getBeansInUseCount();

   int getBeansInUseCurrentCount();

   /** @deprecated */
   @Deprecated
   long getWaiterTotalCount();

   int getWaiterCurrentCount();

   long getTimeoutTotalCount();

   void initializePool();
}
