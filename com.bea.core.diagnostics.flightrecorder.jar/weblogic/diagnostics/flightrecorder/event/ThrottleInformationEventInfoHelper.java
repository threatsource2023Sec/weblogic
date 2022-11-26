package weblogic.diagnostics.flightrecorder.event;

public final class ThrottleInformationEventInfoHelper {
   public static void populateExtensions(Object returnValue, ThrottleInformationEventInfo target) {
      if (target != null && returnValue != null && returnValue instanceof ThrottleInformationEventInfo) {
         ThrottleInformationEventInfo info = (ThrottleInformationEventInfo)returnValue;
         target.setLastPeriodDuration(info.getLastPeriodDuration());
         target.setPeriodsSinceLastThrottleChange(info.getPeriodsSinceLastThrottleChange());
         target.setRequestsSelectedLastPeriod(info.getRequestsSelectedLastPeriod());
         target.setRequestsSeenLastPeriod(info.getRequestsSeenLastPeriod());
         target.setEventsGeneratedLastPeriod(info.getEventsGeneratedLastPeriod());
         target.setProjectedSelectedRequestsPerSecBeforeCapCheck(info.getProjectedSelectedRequestsPerSecBeforeCapCheck());
         target.setCurrentThrottleRate(info.getCurrentThrottleRate());
         target.setPreviousThrottleRate(info.getPreviousThrottleRate());
         target.setAverageEventsPerRequestLastPeriod(info.getAverageEventsPerRequestLastPeriod());
      }
   }
}
