package weblogic.deploy.utils;

import javax.management.Notification;
import javax.management.NotificationListener;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.utils.Debug;

public class TaskCompletionNotificationListener implements NotificationListener {
   private static final long WAIT_PERIOD = 60000L;
   private final DeploymentTaskRuntimeMBean task;
   private boolean isCompleted = false;

   public TaskCompletionNotificationListener(DeploymentTaskRuntimeMBean task) {
      this.task = task;
   }

   public void handleNotification(Notification n, Object handback) {
      synchronized(this) {
         this.isCompleted = true;
         this.notify();
      }
   }

   public void waitForTaskCompletion(long timeout) {
      long givenTimeout = timeout == 0L ? 60000L : timeout;
      long endTime = System.currentTimeMillis() + givenTimeout;
      long timeRemaining = givenTimeout;

      while(!this.isCompleted && !this.isTaskCompleted()) {
         if (timeout != 0L) {
            timeRemaining = endTime - System.currentTimeMillis();
         }

         if (timeRemaining <= 0L) {
            return;
         }

         long currentWaitTime = timeRemaining <= 60000L ? timeRemaining : 60000L;
         this.waitForTaskCompletionInternal(currentWaitTime);
      }

   }

   private void waitForTaskCompletionInternal(long timePeriod) {
      synchronized(this) {
         if (!this.isCompleted) {
            try {
               this.wait(timePeriod);
            } catch (InterruptedException var6) {
            }
         }

      }
   }

   private boolean isTaskCompleted() {
      Debug.assertion(this.task != null);
      return !this.task.isRunning();
   }
}
