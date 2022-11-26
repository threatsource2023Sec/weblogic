package weblogic.management.patching.commands;

import java.util.ArrayList;
import java.util.List;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public abstract class ServerSessionHandlingBase extends AbstractCommand {
   private static final long serialVersionUID = -7941039984365804852L;
   @SharedState
   protected String serverName;
   @SharedState
   protected ArrayList failoverGroups;
   @SharedState
   protected ArrayList origFailoverGroups;

   protected void enableSessionHandling(List updateFailoverGroups) throws Exception, CommandException {
      ServerUtils serverUtils = new ServerUtils();
      if (serverUtils.isRunning(this.serverName)) {
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("Enabling session handling on " + this.serverName + " with failoverGroups: " + serverUtils.failoverGroupsToString(updateFailoverGroups));
         }

         serverUtils.enableSessionHandling(this.serverName, updateFailoverGroups);
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("enableSessionHandling skipping server " + this.serverName + " with failoverGroups: " + serverUtils.failoverGroupsToString(updateFailoverGroups) + " because it is not running.");
      }

   }

   protected List castFailoverGroups(ArrayList failoverGroupsToCast) {
      if (failoverGroupsToCast == null) {
         return null;
      } else {
         List castFailoverGroups = new ArrayList();
         castFailoverGroups.addAll(failoverGroupsToCast);
         return castFailoverGroups;
      }
   }
}
