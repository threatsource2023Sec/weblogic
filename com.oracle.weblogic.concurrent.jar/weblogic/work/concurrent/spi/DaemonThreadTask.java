package weblogic.work.concurrent.spi;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.work.concurrent.ExecutorDaemonThread;

public abstract class DaemonThreadTask implements Runnable {
   private volatile ExecutorDaemonThread thread = null;
   private final int priority;

   public DaemonThreadTask(int priority) {
      this.priority = priority;
   }

   public ExecutorDaemonThread getThread() {
      return this.thread;
   }

   public void setThread(ExecutorDaemonThread thread) {
      this.thread = thread;
   }

   public int getPriority() {
      return this.priority;
   }

   public abstract void cancel();

   public abstract void shutdown(String var1);

   public abstract DebugLogger getDebugLogger();

   public abstract void addSubTask(Runnable var1);

   public abstract ComponentInvocationContext getSubmittingCICInSharing();
}
