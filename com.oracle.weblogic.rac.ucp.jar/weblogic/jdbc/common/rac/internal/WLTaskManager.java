package weblogic.jdbc.common.rac.internal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import oracle.ucp.util.Task;
import oracle.ucp.util.TaskHandle;
import oracle.ucp.util.TaskManager;
import weblogic.kernel.WLSThread;
import weblogic.work.WorkManager;

public class WLTaskManager implements TaskManager {
   private boolean useWM;
   private WorkManager workManager;
   private ExecutorService executorService;

   public WLTaskManager() {
      this.executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());
   }

   public WLTaskManager(WorkManager wm) {
      this.workManager = wm;
      this.useWM = true;
   }

   public boolean isRunning() {
      return true;
   }

   public void start() {
   }

   public void stop() {
   }

   public TaskHandle submitTask(Task task) {
      WLTask wltask = new WLTask(task);
      WLTaskHandle wltaskHandle = new WLTaskHandle(wltask);
      if (!this.useWM) {
         this.executorService.submit(wltask);
      } else {
         this.workManager.schedule(wltask);
      }

      return wltaskHandle;
   }

   class DaemonThreadFactory implements ThreadFactory {
      public Thread newThread(Runnable r) {
         Thread thread = new WLSThread(r);
         thread.setDaemon(true);
         return thread;
      }
   }
}
