package weblogic.work;

import weblogic.utils.cmm.MemoryPressureListener;

public class SelfTuningMemoryPressureListener implements MemoryPressureListener {
   private final MemoryLevelChangedTask TASK = new MemoryLevelChangedTask();
   private volatile int memLevel = 0;

   SelfTuningMemoryPressureListener() {
   }

   public void handleCMMLevel(int newLevel) {
      this.memLevel = newLevel;
      this.TASK.scheduleIfNeeded();
   }

   final class MemoryLevelChangedTask implements Runnable {
      private boolean scheduled;

      private synchronized boolean trySchedule() {
         if (this.scheduled) {
            return false;
         } else {
            this.scheduled = true;
            return true;
         }
      }

      private synchronized void taskCompleted() {
         this.scheduled = false;
      }

      void scheduleIfNeeded() {
         if (this.trySchedule()) {
            WorkManagerFactory.getInstance().getSystem().schedule(this);
         }

      }

      public void run() {
         try {
            RequestManager.getInstance().handleMemoryPressure(SelfTuningMemoryPressureListener.this.memLevel);
         } finally {
            this.taskCompleted();
         }

      }
   }
}
