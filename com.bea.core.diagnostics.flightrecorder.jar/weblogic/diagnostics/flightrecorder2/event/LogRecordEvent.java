package weblogic.diagnostics.flightrecorder2.event;

import java.util.logging.LogRecord;
import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("WLDF LogRecord Snapshot")
@Name("com.oracle.weblogic.log.LogRecordEvent")
@Description("Captures information from LogRecords that are recorded")
@Category({"WebLogic Server", "Log"})
public class LogRecordEvent extends Event implements LogBaseEvent, FlightRecorderEvent {
   @Label("Message")
   @Description("The message")
   protected String message = null;
   @Label("Level")
   @Description("The logging level")
   protected String level = null;
   @Label("Logger Name")
   @Description("The logger name")
   protected String loggerName = "Default";
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
