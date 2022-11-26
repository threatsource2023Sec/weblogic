package weblogic.work.concurrent;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.concurrent.future.AbstractFutureImpl;
import weblogic.work.concurrent.spi.DaemonThreadTask;

public class ExecutorLongRunningTask extends DaemonThreadTask {
   private AbstractFutureImpl future;

   public ExecutorLongRunningTask(AbstractFutureImpl future, int priority) {
      super(priority);
      this.future = future;
   }

   public void run() {
      this.future.doRun();
   }

   public void shutdown(String reason) {
      this.future.doCancel(true, reason);
   }

   public void cancel() {
   }

   public DebugLogger getDebugLogger() {
      return DebugLogger.getDebugLogger("DebugConcurrentMES");
   }

   public void addSubTask(Runnable work) {
      throw new UnsupportedOperationException();
   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return this.future.getSubmittingCICInSharing();
   }
}
