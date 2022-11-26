package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;

@EventDefinition(
   name = "ECIDMapping",
   description = "This is generated when a temporary ECID is replaced with an ECID which propagated in. This can happen when internal events are generated before propagation processing has been done",
   path = "wls/ECID_Mapping",
   thread = true,
   stacktrace = true
)
public class ECIDMappingEvent extends InstantEvent implements FlightRecorderEvent {
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "ECID",
      description = "This is the Diagnostics context ID",
      relationKey = "http://www.oracle.com/fmw/ECID"
   )
   private String ECID;
   @UseConstantPool(
      name = "LocalPool"
   )
   @ValueDefinition(
      name = "Replaced ECID",
      description = "The Diagnostics context ID which was replaced",
      relationKey = "http://www.oracle.com/fmw/ReplacedECID"
   )
   private String replacedECID;

   public String getECID() {
      return this.ECID;
   }

   public void setECID(String eCID) {
      this.ECID = eCID;
   }

   public String getReplacedECID() {
      return this.replacedECID;
   }

   public void setReplacedECID(String replacedECID) {
      this.replacedECID = replacedECID;
   }

   protected ECIDMappingEvent() {
   }

   public ECIDMappingEvent(String replacedECID, String ECID) {
      this.replacedECID = replacedECID;
      this.ECID = ECID;
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
}
