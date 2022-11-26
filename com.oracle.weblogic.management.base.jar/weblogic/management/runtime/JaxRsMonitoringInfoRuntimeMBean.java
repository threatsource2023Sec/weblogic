package weblogic.management.runtime;

/** @deprecated */
@Deprecated
public interface JaxRsMonitoringInfoRuntimeMBean extends RuntimeMBean {
   long getStartTime();

   long getInvocationCount();

   long getLastInvocationTime();

   long getExecutionTimeTotal();

   long getExecutionTimeAverage();

   long getExecutionTimeLow();

   long getExecutionTimeHigh();
}
