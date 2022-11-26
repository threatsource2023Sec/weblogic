package weblogic.work.concurrent;

import javax.enterprise.concurrent.ManageableThread;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.kernel.AuditableThread;
import weblogic.kernel.ResettableThreadLocal;

public abstract class AbstractManagedThread extends AuditableThread implements ManageableThread {
   private static final ThreadGroup CONCURRENT_DAEMON_GROUP = new ThreadGroup("Concurrent Daemon Threads");
   volatile boolean shutdown = false;

   public AbstractManagedThread(Runnable target) {
      super(CONCURRENT_DAEMON_GROUP, target);
   }

   public boolean isShutdown() {
      return this.shutdown;
   }

   public void shutdown(String reason) {
      this.shutdown = true;
   }

   protected void readyToRun() {
      super.readyToRun();
      ResettableThreadLocal.resetJavaThreadStorage();
   }

   public abstract ComponentInvocationContext getSubmittingCICInSharing();
}
