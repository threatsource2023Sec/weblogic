package weblogic.diagnostics.flightrecorder2.event;

import com.bea.logging.BaseLogRecord;
import java.util.Properties;
import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("WLDF WLLogRecord Snapshot")
@Name("com.oracle.weblogic.log.WLLogRecordEvent")
@Description("Captures information from WLLogRecords that are recorded")
@Category({"WebLogic Server", "Log"})
public class WLLogRecordEvent extends Event implements LogBaseEvent, FlightRecorderEvent {
   @Label("Message")
   @Description("The message")
   protected String message = null;
   @Label("Level")
   @Description("The logging level")
   protected String level = null;
   @Label("ID")
   @Description("The message ID")
   protected String id = "WL-000000";
   @Label("Logger Name")
   @Description("The logger name")
   protected String loggerName = "Default";
   @Label("User ID")
   @Description("The user ID")
   protected String userID = "";
   @Label("Transaction ID")
   @Description("The transaction ID")
   @RelationKey("http://www.oracle.com/wls/transactionID")
   protected String transactionID = "";
   @Label("ECID")
   @Description("The diagnostic context ID")
   @RelationKey("http://www.oracle.com/fmw/ECID")
   protected String ECID = "";
   @Label("RID")
   @Description("The Diagnostic Reference ID")
   @RelationKey("http://www.oracle.com/fmw/RID")
   protected String RID = "";
   @Label("Partition Name")
   @Description("The Partition Name")
   @RelationKey("http://www.oracle.com/fmw/partitionName")
   protected String partitionName;
   @Label("Partition Id")
   @Description("The Partition Id")
   @RelationKey("http://www.oracle.com/fmw/partitionId")
   protected String partitionId;
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

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getLoggerName() {
      return this.loggerName;
   }

   public void setLoggerName(String loggerName) {
      this.loggerName = loggerName;
   }

   public String getUserID() {
      return this.userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public String getTransactionID() {
      return this.transactionID;
   }

   public void setTransactionID(String transactionID) {
      this.transactionID = transactionID;
   }

   public String getECID() {
      return this.ECID;
   }

   public void setECID(String eCID) {
      this.ECID = eCID;
   }

   public String getRID() {
      return this.RID;
   }

   public void setRID(String rID) {
      this.RID = rID;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public void setPartitionId(String partitionId) {
      this.partitionId = partitionId;
   }

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void initialize(Object baseLogRecord) {
      this.initialize((BaseLogRecord)baseLogRecord);
   }

   private void initialize(BaseLogRecord baseLogRecord) {
      if (baseLogRecord != null) {
         this.message = baseLogRecord.getMessage();
         this.level = baseLogRecord.getLevel().toString();
         this.id = baseLogRecord.getId();
         this.loggerName = baseLogRecord.getLoggerName();
         this.userID = baseLogRecord.getUserId();
         this.transactionID = baseLogRecord.getTransactionId();
         this.ECID = baseLogRecord.getDiagnosticContextId();
         Properties attrs = baseLogRecord.getSupplementalAttributes();
         if (attrs != null && attrs.size() > 0) {
            this.RID = attrs.getProperty("RID", "");
            this.partitionName = attrs.getProperty("partition-name", "");
            this.partitionId = attrs.getProperty("partition-id", "");
         }

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
      return true;
   }

   public boolean isLogRecordEvent() {
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
