package weblogic.diagnostics.flightrecorder.event;

import com.bea.logging.BaseLogRecord;
import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import java.util.Properties;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.LogBaseEvent;

@EventDefinition(
   name = "WLDF WLLogRecord Snapshot",
   description = "Captures information from WLLogRecords that are recorded",
   path = "wls/Log/WLDF_WL_Log_Record_Snapshot",
   thread = true
)
public class WLLogRecordEvent extends InstantEvent implements LogBaseEvent, FlightRecorderEvent {
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
      name = "ID",
      description = "The message ID"
   )
   protected String id = "WL-000000";
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
      name = "User ID",
      description = "The user ID"
   )
   protected String userID = "";
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Transaction ID",
      description = "The transaction ID",
      relationKey = "http://www.oracle.com/wls/transactionID"
   )
   protected String transactionID = "";
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "ECID",
      description = "The diagnostic context ID",
      relationKey = "http://www.oracle.com/fmw/ECID"
   )
   protected String ECID = "";
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "RID",
      description = "The Diagnostic Reference ID",
      relationKey = "http://www.oracle.com/fmw/RID"
   )
   protected String RID = "";
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Partition Name",
      description = "The Partition Name",
      relationKey = "http://www.oracle.com/fmw/partitionName"
   )
   protected String partitionName;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Partition Id",
      description = "The Partition Id",
      relationKey = "http://www.oracle.com/fmw/partitionId"
   )
   protected String partitionId;
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
      return true;
   }

   public boolean isLogRecordEvent() {
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
