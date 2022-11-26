package weblogic.work;

import weblogic.kernel.AuditableThread;
import weblogic.kernel.KernelLogger;
import weblogic.kernel.KernelStatus;

public class DaemonWorkThread extends AuditableThread {
   private WorkAdapter work;
   private DaemonTaskWorkManager wm;

   public DaemonWorkThread(ThreadGroup group, WorkAdapter work, String name, DaemonTaskWorkManager wm) {
      super(group, work, name);
      this.work = work;
      this.wm = wm;
   }

   public WorkManagerLifecycleImpl getWorkManager() {
      return this.wm;
   }

   public WorkAdapter getWork() {
      return this.work;
   }

   public void run() {
      try {
         this.wm.addDaemonThread(this);
         if (this.work != null) {
            SelfTuningWorkManagerImpl selfTuningWorkManager = null;
            if (this.wm.delegate instanceof WorkManagerLifecycleImpl) {
               WorkManager delegate = ((WorkManagerLifecycleImpl)this.wm.delegate).delegate;
               if (delegate instanceof SelfTuningWorkManagerImpl) {
                  selfTuningWorkManager = (SelfTuningWorkManagerImpl)delegate;
               }
            }

            if (selfTuningWorkManager != null) {
               selfTuningWorkManager.runWorkUnderContext(this.work);
            } else {
               this.work.run();
            }
         }
      } catch (ThreadDeath var7) {
         if (KernelStatus.isServer()) {
            if (!KernelStatus.isIntentionalShutdown()) {
               KernelLogger.logStopped(this.getName());
            }

            throw var7;
         }
      } catch (Throwable var8) {
         KernelLogger.logExecuteFailed(var8);
      } finally {
         this.wm.removeDaemonThread(this);
         this.wm.workCompleted();
      }

   }
}
