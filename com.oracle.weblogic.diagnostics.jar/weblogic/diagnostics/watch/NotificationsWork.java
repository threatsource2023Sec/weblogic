package weblogic.diagnostics.watch;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

class NotificationsWork implements Runnable, TimerListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static TimerManagerFactory timerManagerFactory = TimerManagerFactory.getTimerManagerFactory();
   private static TimerManager actionTimerManager;
   private WatchNotificationInternal notification;
   private WatchNotificationListener[] currentListeners;
   private WatchNotificationListener currentlyExecutingAction;
   private Watch watch;

   public NotificationsWork(WatchNotificationInternal wn, WatchNotificationListener[] listeners, Watch watch) {
      if (wn == null) {
         throw new NullPointerException();
      } else {
         this.notification = wn;
         this.currentListeners = listeners;
         this.watch = watch;
      }
   }

   public void run() {
      String watchName = this.notification.getWatchName();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Performing actions for watch: " + watchName);
      }

      try {
         if (this.currentListeners == null || this.currentListeners.length == 0) {
            return;
         }

         WatchNotificationListener[] var2 = this.currentListeners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WatchNotificationListener listener = var2[var4];
            Timer timeoutTimer = null;

            try {
               if (!listener.isDisabled()) {
                  this.currentlyExecutingAction = listener;
                  listener.reset();
                  DiagnosticsWatchLogger.logActionExecutionStarted(listener.getNotificationName(), (long)listener.getNotificationTimeout());
                  if (listener.getNotificationTimeout() > 0) {
                     timeoutTimer = actionTimerManager.schedule(this, (long)(listener.getNotificationTimeout() * 1000));
                  }

                  listener.processWatchNotification(this.notification);
               }
            } catch (Exception var18) {
               DiagnosticsLogger.logNotificationError(watchName, listener.getNotificationName(), var18);
            } finally {
               if (timeoutTimer != null) {
                  timeoutTimer.cancel();
               }

            }
         }
      } catch (Exception var20) {
         DiagnosticsLogger.logWatchNotificationError(watchName, var20);
      } finally {
         this.currentlyExecutingAction = null;
         this.watch.notificationsWorkCompleted();
      }

   }

   public void timerExpired(Timer t) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Action execution timer expired");
      }

      WatchNotificationListener listener = this.currentlyExecutingAction;
      if (listener != null) {
         DiagnosticsWatchLogger.logActionTimerExpired(listener.getNotificationName(), (long)listener.getNotificationTimeout());
         listener.cancel();
      }

   }

   static {
      actionTimerManager = timerManagerFactory.getTimerManager("WatchActionsTimer", WorkManagerFactory.getInstance().getDefault());
   }
}
