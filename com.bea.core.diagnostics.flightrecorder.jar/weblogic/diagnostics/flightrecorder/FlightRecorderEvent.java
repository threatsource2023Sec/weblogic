package weblogic.diagnostics.flightrecorder;

public interface FlightRecorderEvent {
   boolean isEventTimed();

   void callBegin();

   void callEnd();

   void callCommit();

   boolean callShouldWrite();

   boolean callIsEnabled();

   boolean isBaseEvent();

   boolean isLoggingEvent();

   boolean isThrottleInformationEvent();

   boolean isWLLogRecordEvent();

   boolean isLogRecordEvent();

   boolean isGlobalInformationEvent();
}
