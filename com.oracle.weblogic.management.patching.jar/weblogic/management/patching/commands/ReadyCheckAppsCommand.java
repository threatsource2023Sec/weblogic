package weblogic.management.patching.commands;

import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class ReadyCheckAppsCommand extends AbstractCommand {
   private static final long serialVersionUID = 827089535129410189L;
   @SharedState
   private transient String serverName;
   @SharedState
   private transient long readyCheckAppsTimeoutInMin = 30L;
   @SharedState
   private transient boolean isDryRun = false;

   public boolean execute() throws Exception {
      ServerUtils.pollReadyApp(this.serverName, this.readyCheckAppsTimeoutInMin);
      return true;
   }
}
