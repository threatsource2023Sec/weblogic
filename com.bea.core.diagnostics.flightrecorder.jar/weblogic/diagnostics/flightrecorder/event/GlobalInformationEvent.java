package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.InstantEvent;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.flightrecorder.FlightRecorderEvent;

@EventDefinition(
   name = "GlobalInformation",
   description = "Information that applies to all events produced by WLDF in this recording",
   path = "wls/Global_Information",
   thread = true,
   stacktrace = false
)
public class GlobalInformationEvent extends InstantEvent implements GlobalInformationEventInfo, FlightRecorderEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Domain Name",
      description = "Name of the domain"
   )
   protected String domainName;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Server Name",
      description = "Name of the server"
   )
   protected String serverName;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Machine Name",
      description = "Name of the machine the server is running on"
   )
   protected String machineName;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Diagnostic Volume",
      description = "The diagnostic volume currently configured. The volume affects which events WLDF will generate and also affects the contents of the events generated."
   )
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
      return true;
   }
}
