package weblogic.work;

import weblogic.kernel.AuditableThread;

public abstract class WorkManagerImpl implements WorkManager {
   private static final ThreadGroup WEBLOGIC_DAEMON_GROUP = new ThreadGroup("Non-Pooled Threads");
   protected String wmName;
   protected String applicationName;
   protected String moduleName;
   protected boolean isInternal;

   public String getName() {
      return this.wmName;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setInternal() {
      this.isInternal = true;
   }

   public void setInternal(boolean flag) {
      this.isInternal = flag;
   }

   public boolean isInternal() {
      return this.isInternal;
   }

   public void setThreadCount(int threadCount) throws IllegalStateException, SecurityException {
      throw new IllegalStateException("WorkManager [" + this.toString() + "] does not support setting thread count");
   }

   public static void executeDaemonTask(String name, int priority, Runnable runnable) {
      Thread thread = new AuditableThread(WEBLOGIC_DAEMON_GROUP, runnable, name);
      thread.setDaemon(true);
      thread.setPriority(priority);
      thread.start();
   }

   public String toString() {
      return this.applicationName + "@" + this.moduleName + "@" + this.wmName;
   }
}
