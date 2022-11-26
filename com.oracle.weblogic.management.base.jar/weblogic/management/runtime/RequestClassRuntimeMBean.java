package weblogic.management.runtime;

public interface RequestClassRuntimeMBean extends RuntimeMBean {
   String FAIR_SHARE = "fairshare";
   String RESPONSE_TIME = "responsetime";
   String CONTEXT = "context";

   String getRequestClassType();

   long getCompletedCount();

   long getTotalThreadUse();

   int getPendingRequestCount();

   long getVirtualTimeIncrement();

   long getThreadUseSquares();

   long getDeltaFirst();

   long getDeltaRepeat();

   long getMyLast();

   double getInterval();
}
