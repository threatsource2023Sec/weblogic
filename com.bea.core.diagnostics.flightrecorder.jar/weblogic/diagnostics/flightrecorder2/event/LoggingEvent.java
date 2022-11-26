package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("WLDF Logging Snapshot")
@Name("com.oracle.weblogic.log.LoggingEvent")
@Description("Captures information from LoggingEvents that are recorded")
@Category({"WebLogic Server", "Log"})
public class LoggingEvent extends Event implements LogBaseEvent, FlightRecorderEvent {
   @Label("Message")
   @Description("The message")
   protected String message = null;
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/Log")
   protected String subsystem = "Log";

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void initialize(Object loggingEvent) {
      this.message = loggingEvent.toString();
   }

   public boolean isEventTimed() {
      return false;
   }

   public void callBegin() {
      this.begin();
   }

   public void callEnd() {
      this.end();
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
      return true;
   }

   public boolean isThrottleInformationEvent() {
      return false;
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
