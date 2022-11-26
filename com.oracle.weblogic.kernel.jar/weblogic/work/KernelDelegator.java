package weblogic.work;

import weblogic.kernel.ExecuteThread;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.ExecuteQueueMBean;
import weblogic.utils.UnsyncCircularQueue;

public final class KernelDelegator extends WorkManagerImpl {
   private int dispatchId;

   KernelDelegator() {
      this.wmName = "direct";
   }

   KernelDelegator(String name, ExecuteQueueMBean eqmb) {
      Kernel.addExecuteQueue(name, eqmb, true);
      this.dispatchId = Kernel.getDispatchPolicyIndex(name);
      this.wmName = name;
   }

   KernelDelegator(String name, int count) {
      if (count > 0) {
         Kernel.addExecuteQueue(name, count);
         this.dispatchId = Kernel.getDispatchPolicyIndex(name);
      } else {
         this.dispatchId = Kernel.getDispatchPolicyIndex("weblogic.kernel.Default");
      }

      this.wmName = name;
   }

   public int getType() {
      return 2;
   }

   public int getConfiguredThreadCount() {
      return Kernel.getExecuteThreadManager(this.wmName).getExecuteThreadCount();
   }

   public void setThreadCount(int threadCount) {
      Kernel.getExecuteThreadManager(this.wmName).setThreadCount(threadCount);
   }

   public void schedule(Runnable work) {
      if ("direct" == this.wmName) {
         work.run();
      } else {
         try {
            Kernel.execute(new ExecuteRequestAdapter(work), this.dispatchId);
         } catch (UnsyncCircularQueue.FullQueueException var4) {
            Runnable rejection = null;
            if (work instanceof Work) {
               rejection = ((Work)work).overloadAction(var4.getMessage());
            }

            if (rejection == null) {
               WorkManagerLogger.logScheduleFailed(this.wmName, var4);
               throw var4;
            }

            rejection.run();
         }

      }
   }

   public boolean executeIfIdle(Runnable work) {
      Kernel.executeIfIdle(new ExecuteRequestAdapter(work), this.dispatchId);
      return true;
   }

   public boolean scheduleIfBusy(Runnable runnable) {
      if (Kernel.getExecuteQueueDepth(this.dispatchId) > 0) {
         this.schedule(runnable);
         return true;
      } else {
         Thread t = Thread.currentThread();
         if (t instanceof ExecuteThread) {
            ((ExecuteThread)t).setTimeStamp(System.currentTimeMillis());
         }

         return false;
      }
   }

   public int getQueueDepth() {
      return Kernel.getExecuteQueueDepth(this.dispatchId);
   }

   public boolean isThreadOwner(Thread th) {
      if (!(th instanceof ExecuteThread)) {
         return false;
      } else {
         ExecuteThread et = (ExecuteThread)th;
         if (et.getExecuteThreadManager() == null) {
            return false;
         } else {
            return this.wmName.equalsIgnoreCase(et.getExecuteThreadManager().getName());
         }
      }
   }
}
