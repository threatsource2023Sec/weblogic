package weblogic.diagnostics.flightrecorder.event;

public interface GlobalInformationEventInfo {
   String getServerName();

   void setServerName(String var1);

   String getDomainName();

   void setDomainName(String var1);

   String getMachineName();

   void setMachineName(String var1);

   String getDiagnosticVolume();

   void setDiagnosticVolume(String var1);
}
