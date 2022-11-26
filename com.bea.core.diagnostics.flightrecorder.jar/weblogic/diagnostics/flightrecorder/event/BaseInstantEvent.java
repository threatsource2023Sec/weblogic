package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderBaseEvent;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Base Instant",
   description = "Base information",
   path = "wls/Base_Instant",
   thread = true
)
public class BaseInstantEvent extends InstantEvent implements Throttleable, FlightRecorderBaseEvent {
   private static final boolean ECIDEnabled = FlightRecorderManager.Factory.getInstance().isECIDEnabled();
   private boolean throttled = false;
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
      name = "Transaction ID",
      description = "The transactionID",
      relationKey = "http://www.oracle.com/wls/transactionID"
   )
   protected String transactionID;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Class Name",
      description = "The name of the class the event was generated from"
   )
   protected String className;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Method Name",
      description = "The name of the method the event was generated from"
   )
   protected String methodName;
   @ValueDefinition(
      name = "Return Value",
      description = "The method return value (supplied when available and when deemed important)"
   )
   protected String returnValue;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "User ID",
      description = "The user ID. This is only supplied at Medium volume and above."
   )
   protected String userID;

   protected BaseInstantEvent() {
   }

   protected BaseInstantEvent(BaseTimedEvent timedEvent) {
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

   public void setThrottled(boolean throttled) {
      this.throttled = throttled;
   }

   public boolean getThrottled() {
      return this.throttled;
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
