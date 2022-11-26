package weblogic.work.concurrent;

import java.util.Date;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.Trigger;

public class TriggerWithContext implements Trigger {
   private final TaskWrapper task;
   private final Trigger trigger;

   public TriggerWithContext(Trigger trigger, TaskWrapper task) {
      this.trigger = trigger;
      this.task = task;
   }

   public Date getNextRunTime(LastExecution arg0, Date arg1) {
      ContextHandleWrapper context = this.task.setupContext();

      Object var5;
      try {
         Date var4 = this.trigger.getNextRunTime(arg0, arg1);
         return var4;
      } catch (Throwable var9) {
         ConcurrencyLogger.logTriggerFail(this.task.getTaskName(), "getNextRunTime", var9);
         var5 = null;
      } finally {
         if (context != null) {
            context.restore();
         }

      }

      return (Date)var5;
   }

   public boolean skipRun(LastExecution arg0, Date arg1) {
      ContextHandleWrapper context = this.task.setupContext();

      boolean var5;
      try {
         boolean var4 = this.trigger.skipRun(arg0, arg1);
         return var4;
      } catch (Throwable var9) {
         ConcurrencyLogger.logTriggerFail(this.task.getTaskName(), "skipRun", var9);
         var5 = true;
      } finally {
         if (context != null) {
            context.restore();
         }

      }

      return var5;
   }
}
