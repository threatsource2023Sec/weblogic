package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import weblogic.diagnostics.flightrecorder.FlightRecorderDebugEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("Debug")
@Name("com.oracle.weblogic.DebugEvent")
@Description("Debug information")
@Category({"WebLogic Server", "Debug"})
@StackTrace
@Enabled
public class DebugEvent extends Event implements FlightRecorderEvent, FlightRecorderDebugEvent {
   @Label("ECID")
   @Description("The Diagnostics context ID")
   @RelationKey("http://www.oracle.com/fmw/ECID")
   protected String ECID;
   @Label("RID")
   @Description("The Diagnostic Reference ID")
   @RelationKey("http://www.oracle.com/fmw/RID")
   protected String RID;
   @Label("Partition Name")
   @Description("The Partition Name")
   @RelationKey("http://www.oracle.com/fmw/partitionName")
   protected String partitionName;
   @Label("Partition Id")
   @Description("The Partition Id")
   @RelationKey("http://www.oracle.com/fmw/partitionId")
   protected String partitionId;
   @Label("component")
   @Description("The component")
   @RelationKey("http://www.oracle.com/wls/component")
   protected String component = null;
   @Label("message")
   @Description("The message")
   protected String message = null;
   @Label("throwable message")
   @Description("The Throwable message")
   protected String throwableMessage = null;
   protected boolean timed = false;

   public DebugEvent() {
   }

   public DebugEvent(boolean timed) {
      this.setTimed(timed);
   }

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

   public void setTimed(boolean timed) {
      this.timed = timed;
   }

   public boolean isEventTimed() {
      return this.timed;
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
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return false;
   }
}
