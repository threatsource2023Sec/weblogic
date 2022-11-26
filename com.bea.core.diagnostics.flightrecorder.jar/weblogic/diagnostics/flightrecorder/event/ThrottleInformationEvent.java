package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;

@EventDefinition(
   name = "ThrottleInformation",
   description = "Information that applies to all events produced by WLDF in this recording",
   path = "wls/Throttle_Information",
   thread = true,
   stacktrace = false
)
public class ThrottleInformationEvent extends InstantEvent implements ThrottleInformationEventInfo, FlightRecorderEvent {
   @ValueDefinition(
      name = "Last Period Duration",
      description = "The time span in millis for the last sampling period used for calculating the throttling"
   )
   long lastPeriodDuration;
   @ValueDefinition(
      name = "Periods Since Last Throttle Change",
      description = "This is the number of sampling periods that passed since the throttle rate has changed"
   )
   int periodsSinceLastThrottleChange;
   @ValueDefinition(
      name = "Requests Selected Last Period  ",
      description = "The number of requests selected for event generation during the last sampling period"
   )
   int requestsSelectedLastPeriod;
   @ValueDefinition(
      name = "Requests Seen Last Period ",
      description = "The total number of requests seen during the last sampling period"
   )
   int requestsSeenLastPeriod;
   @ValueDefinition(
      name = "Events Generated Last Period",
      description = "The number of events generated during the last sampling period"
   )
   int eventsGeneratedLastPeriod;
   @ValueDefinition(
      name = "Average Events Per Request Last Period",
      description = "The average number of events generated per request during the last sampling period"
   )
   float averageEventsPerRequestLastPeriod;
   @ValueDefinition(
      name = "Projected Selected Requests Per Sec Before Cap Check",
      description = "This is an intermediate projection of how many requests per second would be selected over the next sampling period, this is the number BEFORE we check the projection against the max request selection goal"
   )
   int projectedSelectedRequestsPerSecBeforeCapCheck;
   @ValueDefinition(
      name = "Current Throttle Rate",
      description = "The throttling rate that is currently in use, <=1 indicates no throttling"
   )
   int currentThrottleRate;
   @ValueDefinition(
      name = "Previous Throttle Rate",
      description = "The throttling rate that was in use for the last sampling period"
   )
   int previousThrottleRate;

   public long getLastPeriodDuration() {
      return this.lastPeriodDuration;
   }

   public void setLastPeriodDuration(long lastPeriodDuration) {
      this.lastPeriodDuration = lastPeriodDuration;
   }

   public int getPeriodsSinceLastThrottleChange() {
      return this.periodsSinceLastThrottleChange;
   }

   public void setPeriodsSinceLastThrottleChange(int periodsSinceLastThrottleChange) {
      this.periodsSinceLastThrottleChange = periodsSinceLastThrottleChange;
   }

   public int getRequestsSelectedLastPeriod() {
      return this.requestsSelectedLastPeriod;
   }

   public void setRequestsSelectedLastPeriod(int requestsSelectedLastPeriod) {
      this.requestsSelectedLastPeriod = requestsSelectedLastPeriod;
   }

   public int getRequestsSeenLastPeriod() {
      return this.requestsSeenLastPeriod;
   }

   public void setRequestsSeenLastPeriod(int requestsSeenLastPeriod) {
      this.requestsSeenLastPeriod = requestsSeenLastPeriod;
   }

   public int getEventsGeneratedLastPeriod() {
      return this.eventsGeneratedLastPeriod;
   }

   public void setEventsGeneratedLastPeriod(int eventsGeneratedLastPeriod) {
      this.eventsGeneratedLastPeriod = eventsGeneratedLastPeriod;
   }

   public float getAverageEventsPerRequestLastPeriod() {
      return this.averageEventsPerRequestLastPeriod;
   }

   public void setAverageEventsPerRequestLastPeriod(float averageEventsPerRequestLastPeriod) {
      this.averageEventsPerRequestLastPeriod = averageEventsPerRequestLastPeriod;
   }

   public int getProjectedSelectedRequestsPerSecBeforeCapCheck() {
      return this.projectedSelectedRequestsPerSecBeforeCapCheck;
   }

   public void setProjectedSelectedRequestsPerSecBeforeCapCheck(int projectedSelectedRequestsPerSecBeforeCapCheck) {
      this.projectedSelectedRequestsPerSecBeforeCapCheck = projectedSelectedRequestsPerSecBeforeCapCheck;
   }

   public int getCurrentThrottleRate() {
      return this.currentThrottleRate;
   }

   public void setCurrentThrottleRate(int currentThrottleRate) {
      this.currentThrottleRate = currentThrottleRate;
   }

   public int getPreviousThrottleRate() {
      return this.previousThrottleRate;
   }

   public void setPreviousThrottleRate(int previousThrottleRate) {
      this.previousThrottleRate = previousThrottleRate;
   }

   public boolean isEventTimed() {
      return false;
   }

   public void callBegin() {
   }

   public void callEnd() {
   }

   public void callCommit() {
      this.commit();
   }

   public boolean callShouldWrite() {
      return this.shouldWrite();
   }

   public boolean callIsEnabled() {
      return this.getEventInfo().isEnabled();
   }

   public boolean isBaseEvent() {
      return false;
   }

   public boolean isLoggingEvent() {
      return false;
   }

   public boolean isThrottleInformationEvent() {
      return true;
   }

   public boolean isWLLogRecordEvent() {
      return false;
   }

   public boolean isLogRecordEvent() {
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
