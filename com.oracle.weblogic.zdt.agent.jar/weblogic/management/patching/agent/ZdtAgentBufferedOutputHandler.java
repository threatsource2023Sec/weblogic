package weblogic.management.patching.agent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ZdtAgentBufferedOutputHandler implements ZdtAgentOutputHandler {
   ArrayList allMsgs = new ArrayList();
   private boolean isDebugEnabled = false;

   protected ZdtAgentBufferedOutputHandler(ZdtAgentRequest request) {
      this.isDebugEnabled = Boolean.parseBoolean(request.get(ZdtAgentParam.VERBOSE_PARAM));
   }

   ZdtAgentBufferedOutputHandler(boolean isDebugEnabled) {
      this.isDebugEnabled = isDebugEnabled;
   }

   public void error(String message) {
      this.allMsgs.add(new ZdtAgentLogMessage(System.currentTimeMillis(), Level.SEVERE, message));
   }

   public void error(String message, Exception cause) {
      this.allMsgs.add(new ZdtAgentLogMessage(System.currentTimeMillis(), Level.SEVERE, message, cause));
   }

   public void info(String message) {
      this.allMsgs.add(new ZdtAgentLogMessage(System.currentTimeMillis(), Level.INFO, message));
   }

   public void warning(String message) {
      this.allMsgs.add(new ZdtAgentLogMessage(System.currentTimeMillis(), Level.WARNING, message));
   }

   public boolean isDebugEnabled() {
      return this.isDebugEnabled;
   }

   public void debug(String message) {
      if (this.isDebugEnabled) {
         this.allMsgs.add(new ZdtAgentLogMessage(System.currentTimeMillis(), Level.FINE, message));
      }

   }

   public List getMessages() {
      return this.allMsgs;
   }
}
