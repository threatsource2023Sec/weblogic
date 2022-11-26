package weblogic.management.patching;

import weblogic.management.patching.agent.ZdtAgentOutputHandler;
import weblogic.management.patching.agent.ZdtAgentParam;
import weblogic.management.patching.agent.ZdtAgentRequest;
import weblogic.management.patching.commands.PatchingLogger;

public class ZdtAgentLocalOutputHandler implements ZdtAgentOutputHandler {
   private boolean isDebugEnabled = false;

   ZdtAgentLocalOutputHandler(ZdtAgentRequest request) {
      this.isDebugEnabled = Boolean.parseBoolean(request.get(ZdtAgentParam.VERBOSE_PARAM));
   }

   ZdtAgentLocalOutputHandler(boolean isDebugEnabled) {
      this.isDebugEnabled = isDebugEnabled;
   }

   public void error(String message) {
      PatchingLogger.logAgentError(message);
   }

   public void error(String message, Exception cause) {
      PatchingLogger.logAgentErrorWithEx(message, cause);
   }

   public void info(String message) {
      PatchingLogger.logAgentInfo(message);
   }

   public void warning(String message) {
      PatchingLogger.logAgentWarning(message);
   }

   public boolean isDebugEnabled() {
      return this.isDebugEnabled;
   }

   public void debug(String message) {
      if (this.isDebugEnabled) {
         PatchingDebugLogger.debug(message);
      }

   }
}
