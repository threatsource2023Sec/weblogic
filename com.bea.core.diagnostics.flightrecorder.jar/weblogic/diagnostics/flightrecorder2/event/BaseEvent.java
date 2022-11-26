package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.FlightRecorderBaseEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.flightrecorder.event.Throttleable;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Base Instant and Timed Event")
@Name("com.oracle.weblogic.BaseEvent")
@Description("Base information")
@Category({"WebLogic Server"})
@Enabled
public abstract class BaseEvent extends Event implements Throttleable, FlightRecorderBaseEvent {
   private static final boolean ECIDEnabled = FlightRecorderManager.Factory.getInstance().isECIDEnabled();
   private boolean throttled = false;
   @Description("The Diagnostics context ID")
   @Label("ECID")
   @RelationKey("http://www.oracle.com/fmw/ECID")
   protected String ECID;
   @Description("The Diagnostic Reference ID")
   @Label("RID")
   @RelationKey("http://www.oracle.com/fmw/RID")
   protected String RID;
   @Description("The Partition Name")
   @Label("Partition Name")
   @RelationKey("http://www.oracle.com/fmw/partitionName")
   protected String partitionName;
   @Description("The Partition Id")
   @Label("Partition Id")
   @RelationKey("http://www.oracle.com/fmw/partitionId")
   protected String partitionId;
   @Description("The transactionID")
   @Label("Transaction ID")
   @RelationKey("http://www.oracle.com/wls/transactionID")
   protected String transactionID;
   @Description("The name of the class the event was generated from")
   @Label("Class Name")
   protected String className;
   @Description("The name of the method the event was generated from")
   @Label("Method Name")
   protected String methodName;
   @Description("The method return value (supplied when available and when deemed important)")
   @Label("Return Value")
   protected String returnValue;
   @Description("The user ID. This is only supplied at Medium volume and above.")
   @Label("User ID")
   protected String userID;

   protected BaseEvent() {
   }

   protected BaseEvent(BaseEvent timedEvent) {
      this.ECID = timedEvent.ECID;
      this.RID = timedEvent.RID;
      this.transactionID = timedEvent.transactionID;
      this.className = timedEvent.className;
      this.methodName = timedEvent.methodName;
      this.userID = timedEvent.userID;
      this.partitionId = timedEvent.partitionId;
      this.partitionName = timedEvent.partitionName;
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

   public String getTransactionID() {
      return this.transactionID;
   }

   public void setTransactionID(String transactionID) {
      this.transactionID = transactionID;
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String className) {
      this.className = className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public void setMethodName(String methodName) {
      this.methodName = methodName;
   }

   public String getReturnValue() {
      return this.returnValue;
   }

   public void setReturnValue(String returnValue) {
      this.returnValue = returnValue;
   }

   public String getUserID() {
      return this.userID;
   }

   public void setUserID(String userID) {
      this.userID = userID;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
   }

   public boolean isECIDEnabled() {
      return ECIDEnabled;
   }

   public boolean getThrottled() {
      return this.throttled;
   }

   public void setThrottled(boolean throttled) {
      this.throttled = throttled;
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
      return true;
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

   public boolean requiresProcessingArgsAfter() {
      return false;
   }

   public void generateInFlight() {
   }

   public boolean willGenerateInFlight() {
      return false;
   }
}
