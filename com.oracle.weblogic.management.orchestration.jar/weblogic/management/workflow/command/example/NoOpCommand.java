package weblogic.management.workflow.command.example;

import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class NoOpCommand extends AbstractCommand {
   @SharedState(
      name = "sleep.duration"
   )
   private transient long duration;

   public boolean execute() throws Exception {
      if (this.duration > 0L) {
         try {
            Thread.sleep(this.duration);
         } catch (Exception var2) {
         }
      }

      return true;
   }
}
