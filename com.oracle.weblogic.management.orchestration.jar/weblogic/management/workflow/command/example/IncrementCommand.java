package weblogic.management.workflow.command.example;

import java.util.concurrent.atomic.AtomicInteger;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.management.workflow.command.SharedState;

public class IncrementCommand extends AbstractCommand {
   @SharedState(
      name = "sleep.duration"
   )
   private transient long duration;
   @SharedState(
      name = "increment.it"
   )
   private transient AtomicInteger incrementIt;

   public boolean execute() throws Exception {
      if (this.duration > 0L) {
         try {
            Thread.sleep(this.duration);
         } catch (Exception var2) {
         }
      }

      int value = this.incrementIt.incrementAndGet();
      System.out.println("IncrementCommand (" + this.getContext().getWorkflowId() + ") :: " + value);
      return true;
   }
}
