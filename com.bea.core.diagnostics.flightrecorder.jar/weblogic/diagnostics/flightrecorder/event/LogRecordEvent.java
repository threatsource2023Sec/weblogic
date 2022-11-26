package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import java.util.logging.LogRecord;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;

@EventDefinition(
   name = "WLDF LogRecord Snapshot",
   description = "Captures information from LogRecords that are recorded",
   path = "wls/Log/WLDF_Log_Record_Snapshot",
   thread = true
)
public class LogRecordEvent extends InstantEvent implements LogBaseEvent, FlightRecorderEvent {
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
      name = "Level",
      description = "The logging level"
   )
   protected String level = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Logger Name",
      description = "The logger name"
   )
   protected String loggerName = "Default";
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

   public String getLevel() {
      return this.level;
   }

   public void setLevel(String level) {
      this.level = level;
   }

   public String getLoggerName() {
      return this.loggerName;
   }

   public void setLoggerName(String loggerName) {
      this.loggerName = loggerName;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void initialize(Object logRecord) {
      this.initialize((LogRecord)logRecord);
   }

   private void initialize(LogRecord logRecord) {
      if (logRecord != null) {
         this.message = logRecord.getMessage();
         this.level = logRecord.getLevel().toString();
         this.loggerName = logRecord.getLoggerName();
      }
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
      return false;
   }

   public boolean isWLLogRecordEvent() {
      return false;
   }

   public boolean isLogRecordEvent() {
      return true;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
