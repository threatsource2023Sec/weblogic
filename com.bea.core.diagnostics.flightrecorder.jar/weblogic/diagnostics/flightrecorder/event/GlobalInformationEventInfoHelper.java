package weblogic.diagnostics.flightrecorder.event;

public final class GlobalInformationEventInfoHelper {
   public static void populateExtensions(Object returnValue, GlobalInformationEventInfo target) {
      if (target != null && returnValue != null && returnValue instanceof GlobalInformationEventInfo) {
         GlobalInformationEventInfo info = (GlobalInformationEventInfo)returnValue;
         target.setDomainName(info.getDomainName());
         target.setServerName(info.getServerName());
         target.setMachineName(info.getMachineName());
         target.setDiagnosticVolume(info.getDiagnosticVolume());
      }
   }
}
