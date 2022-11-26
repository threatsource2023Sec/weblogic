package weblogic.work;

import weblogic.work.commonj.CommonjWorkManagerImpl;

public final class WorkManagerHelper {
   public static MaxThreadsConstraint getMaxThreadsConstraint(WorkManager wm) {
      MaxThreadsConstraint maxThreadsConstraint = null;
      if (wm instanceof ServerWorkManagerImpl) {
         maxThreadsConstraint = ((ServerWorkManagerImpl)wm).getMaxThreadsConstraint();
      }

      if (wm instanceof WorkManagerService) {
         WorkManager local = ((WorkManagerService)wm).getDelegate();
         if (local instanceof ServerWorkManagerImpl) {
            maxThreadsConstraint = ((ServerWorkManagerImpl)local).getMaxThreadsConstraint();
         }
      }

      return maxThreadsConstraint != null && !maxThreadsConstraint.isPartitionMaxThreadsConstraint() ? maxThreadsConstraint : null;
   }

   public static MinThreadsConstraint getMinThreadsConstraint(WorkManager wm) {
      MinThreadsConstraint minThreadsConstraint = null;
      if (wm instanceof SelfTuningWorkManagerImpl) {
         minThreadsConstraint = ((SelfTuningWorkManagerImpl)wm).getMinThreadsConstraint();
      } else if (wm instanceof WorkManagerService) {
         WorkManager local = ((WorkManagerService)wm).getDelegate();
         if (local instanceof ServerWorkManagerImpl) {
            minThreadsConstraint = ((SelfTuningWorkManagerImpl)local).getMinThreadsConstraint();
         }
      }

      return minThreadsConstraint;
   }

   public static MinThreadsConstraint getMinThreadsConstraint(CommonjWorkManagerImpl wm) {
      return getMinThreadsConstraint(wm.getDelegate());
   }

   public static boolean updateMinThreadsConstraint(CommonjWorkManagerImpl wm, int newCount) {
      MinThreadsConstraint minThreadsConstraint = getMinThreadsConstraint(wm);
      if (minThreadsConstraint != null) {
         if (minThreadsConstraint.getConfiguredCount() != newCount) {
            minThreadsConstraint.setCount(newCount);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isDefault(WorkManager wm) {
      if (wm == null) {
         return false;
      } else {
         String interned = wm.getName().intern();
         return interned == "weblogic.kernel.Default" || interned == "default";
      }
   }

   public static void currentThreadMakingProgress() {
      Thread thd = Thread.currentThread();
      if (thd instanceof ExecuteThread) {
         ExecuteThread et = (ExecuteThread)thd;
         RequestManager.updateRequestClass((ServiceClassStatsSupport)et.getWorkManager().getRequestClass(), et);
         et.setLongRunningTask();
      }

   }
}
