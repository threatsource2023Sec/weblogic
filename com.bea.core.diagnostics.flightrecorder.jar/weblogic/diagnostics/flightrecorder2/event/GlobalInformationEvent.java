package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.StackTrace;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;
import weblogic.diagnostics.flightrecorder.event.GlobalInformationEventInfo;

@Label("GlobalInformation")
@Name("com.oracle.weblogic.GlobalInformationEvent")
@Description("Information that applies to all events produced by WLDF in this recording")
@Category({"WebLogic Server"})
@StackTrace(false)
public class GlobalInformationEvent extends Event implements GlobalInformationEventInfo, FlightRecorderEvent {
   @Label("Domain Name")
   @Description("Name of the domain")
   protected String domainName;
   @Label("Server Name")
   @Description("Name of the server")
   protected String serverName;
   @Label("Machine Name")
   @Description("Name of the machine the server is running on")
   protected String machineName;
   @Label("Diagnostic Volume")
   @Description("The diagnostic volume currently configured. The volume affects which events WLDF will generate and also affects the contents of the events generated.")
   protected String diagnosticVolume;

   public String getDomainName() {
      return this.domainName;
   }

   public void setDomainName(String domainName) {
      this.domainName = domainName;
   }

   public String getServerName() {
      return this.serverName;
   }

   public void setServerName(String serverName) {
      this.serverName = serverName;
   }

   public String getMachineName() {
      return this.machineName;
   }

   public void setMachineName(String machineName) {
      this.machineName = machineName;
   }

   public String getDiagnosticVolume() {
      return this.diagnosticVolume;
   }

   public void setDiagnosticVolume(String diagnosticVolume) {
      this.diagnosticVolume = diagnosticVolume;
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
      return false;
   }

   public boolean isGlobalInformationEvent() {
      return true;
   }
}
