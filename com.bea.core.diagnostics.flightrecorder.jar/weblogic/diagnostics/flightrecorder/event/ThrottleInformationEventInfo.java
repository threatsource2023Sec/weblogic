package weblogic.diagnostics.flightrecorder.event;

public interface ThrottleInformationEventInfo {
   long getLastPeriodDuration();

   void setLastPeriodDuration(long var1);

   int getPeriodsSinceLastThrottleChange();

   void setPeriodsSinceLastThrottleChange(int var1);

   int getRequestsSelectedLastPeriod();

   void setRequestsSelectedLastPeriod(int var1);

   int getRequestsSeenLastPeriod();

   void setRequestsSeenLastPeriod(int var1);

   int getEventsGeneratedLastPeriod();

   void setEventsGeneratedLastPeriod(int var1);

   int getProjectedSelectedRequestsPerSecBeforeCapCheck();

   void setProjectedSelectedRequestsPerSecBeforeCapCheck(int var1);

   int getCurrentThrottleRate();

   void setCurrentThrottleRate(int var1);

   int getPreviousThrottleRate();

   void setPreviousThrottleRate(int var1);

   float getAverageEventsPerRequestLastPeriod();

   void setAverageEventsPerRequestLastPeriod(float var1);
}
