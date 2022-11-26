package weblogic.work.concurrent.future;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.work.concurrent.TaskStateNotifier;
import weblogic.work.concurrent.TaskWrapper;

public class ManagedFutureImpl extends AbstractFutureImpl {
   public ManagedFutureImpl(TaskWrapper task, TaskStateNotifier stateNotifier) {
      super(task, stateNotifier, DebugLogger.getDebugLogger("DebugConcurrentMES"));
   }

   public Runnable createOverloadRunnable(final String reason) {
      return new Runnable() {
         public void run() {
            ManagedFutureImpl.this.reject(reason);
         }
      };
   }

   public void run() {
      this.doRun();
   }
}
