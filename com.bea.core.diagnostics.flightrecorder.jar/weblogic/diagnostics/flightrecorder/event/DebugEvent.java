package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderDebugEvent;

@EventDefinition(
   name = "Debug",
   description = "Debug information",
   path = "wls/Debug/Debug",
   thread = true,
   stacktrace = true
)
public class DebugEvent extends InstantEvent implements FlightRecorderDebugEvent {
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "ECID",
      description = "The Diagnostics context ID",
      relationKey = "http://www.oracle.com/fmw/ECID"
   )
   protected String ECID;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "RID",
      description = "The Diagnostic Reference ID",
      relationKey = "http://www.oracle.com/fmw/RID"
   )
   protected String RID;
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
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "component",
      description = "The component",
      relationKey = "http://www.oracle.com/wls/component"
   )
   protected String component = null;
   @ValueDefinition(
      name = "message",
      description = "The message"
   )
   protected String message = null;
   @ValueDefinition(
      name = "throwable message",
      description = "The Throwable message"
   )
   protected String throwableMessage = null;

   public String getECID() {
      return this.ECID;
   }

   public void setECID(String ECID) {
      this.ECID = ECID;
   }

   public String getRID() {
      return this.RID;
   }

   public void setRID(String RID) {
      this.RID = RID;
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

   public String getComponent() {
      return this.component;
   }

   public void setComponent(String component) {
      this.component = component;
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getThrowableMessage() {
      return this.throwableMessage;
   }

   public void setThrowableMessage(String throwableMessage) {
      this.throwableMessage = throwableMessage;
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
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
