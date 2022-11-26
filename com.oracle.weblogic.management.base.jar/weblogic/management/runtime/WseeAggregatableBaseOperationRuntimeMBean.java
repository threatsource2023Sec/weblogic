package weblogic.management.runtime;

public interface WseeAggregatableBaseOperationRuntimeMBean extends RuntimeMBean {
   int getInvocationCount();

   long getDispatchTimeTotal();

   long getDispatchTimeHigh();

   long getDispatchTimeLow();

   long getDispatchTimeAverage();

   long getExecutionTimeTotal();

   long getExecutionTimeHigh();

   long getExecutionTimeLow();

   long getExecutionTimeAverage();

   int getResponseCount();

   long getResponseTimeTotal();

   long getResponseTimeHigh();

   long getResponseTimeLow();

   long getResponseTimeAverage();

   int getResponseErrorCount();

   long getLastInvocationTime();

   int getErrorCount();

   String getLastError();

   long getLastErrorTime();

   long getLastResponseTime();

   String getLastResponseError();

   long getLastResponseErrorTime();
}
