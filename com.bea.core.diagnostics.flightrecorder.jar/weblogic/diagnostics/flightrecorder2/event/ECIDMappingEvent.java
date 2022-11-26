package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;

@Label("ECIDMapping")
@Name("com.oracle.weblogic.ECIDMappingEvent")
@Description("This is generated when a temporary ECID is replaced with an ECID which propagated in. This can happen when internal events are generated before propagation processing has been done")
@Category({"WebLogic Server"})
@StackTrace
public class ECIDMappingEvent extends Event implements FlightRecorderEvent {
   @Label("ECID")
   @Description("This is the Diagnostics context ID")
   @RelationKey("http://www.oracle.com/fmw/ECID")
   private String ECID;
   @Label("Replaced ECID")
   @Description("The Diagnostics context ID which was replaced")
   @RelationKey("http://www.oracle.com/fmw/ReplacedECID")
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
}
