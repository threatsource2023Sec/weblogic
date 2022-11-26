package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;

@EventDefinition(
   name = "WLDF Logging Snapshot",
   description = "Captures information from LoggingEvents that are recorded",
   path = "wls/Log/WLDF_Logging_Snapshot",
   thread = true
)
public class LoggingEvent extends InstantEvent implements LogBaseEvent, FlightRecorderEvent {
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Message",
      description = "The message"
   )
   protected String message = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/Log"
   )
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
