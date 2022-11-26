package weblogic.management.runtime;

public interface PageFlowActionRuntimeMBean extends RuntimeMBean {
   /** @deprecated */
   @Deprecated
   String getActionName();

   /** @deprecated */
   @Deprecated
   long getSuccessCount();

   /** @deprecated */
   @Deprecated
   long getExceptionCount();

   /** @deprecated */
   @Deprecated
   long getHandledExceptionCount();

   /** @deprecated */
   @Deprecated
   long getSuccessDispatchTimeTotal();

   /** @deprecated */
   @Deprecated
   long getSuccessDispatchTimeHigh();

   /** @deprecated */
   @Deprecated
   long getSuccessDispatchTimeLow();

   /** @deprecated */
   @Deprecated
   long getSuccessDispatchTimeAverage();

   /** @deprecated */
   @Deprecated
   long getHandledExceptionDispatchTimeTotal();

   /** @deprecated */
   @Deprecated
   long getHandledExceptionDispatchTimeHigh();

   /** @deprecated */
   @Deprecated
   long getHandledExceptionDispatchTimeLow();

   /** @deprecated */
   @Deprecated
   long getHandledExceptionDispatchTimeAverage();

   /** @deprecated */
   @Deprecated
   PageFlowError[] getLastExceptions();
}
