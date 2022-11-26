package weblogic.management.runtime;

public interface JaxRsExecutionStatisticsRuntimeMBean extends RuntimeMBean {
   long getMinTimeTotal();

   long getMinTimeLast1s();

   long getMinTimeLast1m();

   long getMinTimeLast15s();

   long getMinTimeLast15m();

   long getMinTimeLast1h();

   long getMaxTimeTotal();

   long getMaxTimeLast1s();

   long getMaxTimeLast1m();

   long getMaxTimeLast15s();

   long getMaxTimeLast15m();

   long getMaxTimeLast1h();

   long getAvgTimeTotal();

   long getAvgTimeLast1s();

   long getAvgTimeLast1m();

   long getAvgTimeLast15s();

   long getAvgTimeLast15m();

   long getAvgTimeLast1h();

   double getRequestRateTotal();

   double getRequestRateLast1s();

   double getRequestRateLast1m();

   double getRequestRateLast15s();

   double getRequestRateLast15m();

   double getRequestRateLast1h();

   long getRequestCountTotal();

   long getRequestCountLast1s();

   long getRequestCountLast1m();

   long getRequestCountLast15s();

   long getRequestCountLast15m();

   long getRequestCountLast1h();
}
