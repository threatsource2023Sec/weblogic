package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import jdk.jfr.Timespan;
import jdk.jfr.Unsigned;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.event.ThrottleInformationEventInfo;

@Label("ThrottleInformation")
@Name("com.oracle.weblogic.ThrottleInformationEvent")
@Description("Information that applies to all events produced by WLDF in this recording")
@Category({"WebLogic Server"})
@StackTrace(false)
public class ThrottleInformationEvent extends Event implements ThrottleInformationEventInfo, FlightRecorderEvent {
   @Label("Last Period Duration")
   @Description("The time span in millis for the last sampling period used for calculating the throttling")
   @Timespan("MILLISECONDS")
   @Unsigned
   long lastPeriodDuration;
   @Label("Periods Since Last Throttle Change")
   @Description("This is the number of sampling periods that passed since the throttle rate has changed")
   @Unsigned
   int periodsSinceLastThrottleChange;
   @Label("Requests Selected Last Period  ")
   @Description("The number of requests selected for event generation during the last sampling period")
   @Unsigned
   int requestsSelectedLastPeriod;
   @Label("Requests Seen Last Period ")
   @Description("The total number of requests seen during the last sampling period")
   @Unsigned
   int requestsSeenLastPeriod;
   @Label("Events Generated Last Period")
   @Description("The number of events generated during the last sampling period")
   @Unsigned
   int eventsGeneratedLastPeriod;
   @Label("Average Events Per Request Last Period")
   @Description("The average number of events generated per request during the last sampling period")
   @Unsigned
   float averageEventsPerRequestLastPeriod;
   @Label("Projected Selected Requests Per Sec Before Cap Check")
   @Description("This is an intermediate projection of how many requests per second would be selected over the next sampling period, this is the number BEFORE we check the projection against the max request selection goal")
   @Unsigned
   int projectedSelectedRequestsPerSecBeforeCapCheck;
   @Label("Current Throttle Rate")
   @Description("The throttling rate that is currently in use, <=1 indicates no throttling")
   @Unsigned
   int currentThrottleRate;
   @Label("Previous Throttle Rate")
   @Description("The throttling rate that was in use for the last sampling period")
   @Unsigned
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
      return this.shouldCommit();
   }

   public boolean callIsEnabled() {
      return this.isEnabled();
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
