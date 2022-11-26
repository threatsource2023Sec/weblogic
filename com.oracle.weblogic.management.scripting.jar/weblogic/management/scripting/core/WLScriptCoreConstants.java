package weblogic.management.scripting.core;

public class WLScriptCoreConstants extends WLSTScriptCoreVariables {
   public WLScriptCoreConstants() {
      this.debug = false;
      this.errorInfo = null;
      this.infoHandler = null;
      this.exceptionHandler = null;
      this.nmService = null;
      this.commandType = "";
      this.errorMsg = null;
      this.stdOutputMedium = null;
      this.timeAtError = "";
      this.recording = false;
      this.errOutputMedium = null;
   }

   void initAll() {
      this.errorInfo = null;
      this.commandType = "";
      this.timeAtError = "";
      this.errorMsg = null;
   }

   public void setStdOutputMedium(Object obj) {
      this.stdOutputMedium = obj;
   }

   public void setErrOutputMedium(Object obj) {
      this.errOutputMedium = obj;
   }

   public Object getSTDOutputMedium() {
      return this.stdOutputMedium;
   }

   public void setSTDOutputMedium(Object o) {
      this.stdOutputMedium = o;
   }

   public Object getStandardOutputMedium() {
      return this.stdOutputMedium;
   }

   public void setlogToStandardOut(boolean bool) {
      this.logToStandardOut = bool;
   }

   public void setCommandType(String cmdType) {
      this.commandType = cmdType;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public String getConnected() {
      return this.connected;
   }

   public String getDomainType() {
      return this.domainType;
   }

   public String getDomainName() {
      return this.domainName;
   }
}
