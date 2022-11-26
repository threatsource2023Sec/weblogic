package weblogic.management.utils.fileobserver;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public class FileChangeObserverTimerListener implements TimerListener {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugFileChangeObserver");
   private FileChangeObserverMonitor fileChangeObserverMonitor;

   public FileChangeObserverTimerListener(FileChangeObserverMonitor fileChangeObserverMonitor) {
      this.fileChangeObserverMonitor = fileChangeObserverMonitor;
   }

   public final void timerExpired(Timer timer) {
      try {
         if (debug.isDebugEnabled()) {
            debug.debug("Poll interval reached.  Observing file system for observer " + this);
         }

         this.fileChangeObserverMonitor.observeChanges();
      } catch (Throwable var3) {
         if (debug.isDebugEnabled()) {
            debug.debug("Exception occurred in File System observer.  Exception: " + var3);
         }
      }

   }
}
