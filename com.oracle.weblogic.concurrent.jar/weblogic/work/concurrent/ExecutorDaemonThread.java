package weblogic.work.concurrent;

import javax.enterprise.concurrent.ManageableThread;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.concurrent.spi.DaemonThreadManagerImpl;
import weblogic.work.concurrent.spi.DaemonThreadTask;

public final class ExecutorDaemonThread extends AbstractManagedThread implements ManageableThread {
   private final DaemonThreadTask target;
   private final DaemonThreadManagerImpl manager;

   public ExecutorDaemonThread(DaemonThreadTask target, DaemonThreadManagerImpl manager) {
      super((Runnable)null);
      this.target = target;
      this.manager = manager;
   }

   public void run() {
      DebugLogger debugLogger = this.target.getDebugLogger();
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug(String.format("daemon thread %s started.", this.getName()));
      }

      super.readyToRun();
      this.manager.threadStart(this);

      try {
         this.target.run();
      } finally {
         this.manager.threadTerminate(this);
         if (debugLogger != null && debugLogger.isDebugEnabled()) {
            debugLogger.debug(String.format("daemon thread %s terminated.", this.getName()));
         }

      }

   }

   public void shutdown(String reason) {
      super.shutdown(reason);
      this.target.shutdown(reason);
   }

   public DaemonThreadTask getDaemonThreadTask() {
      return this.target;
   }

   public ComponentInvocationContext getSubmittingCICInSharing() {
      return this.target.getSubmittingCICInSharing();
   }
}
