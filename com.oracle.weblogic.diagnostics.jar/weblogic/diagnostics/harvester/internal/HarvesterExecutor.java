package weblogic.diagnostics.harvester.internal;

import java.util.concurrent.Executor;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class HarvesterExecutor implements Executor {
   private static HarvesterExecutor SINGLETON = null;
   private WorkManager workManager = WorkManagerFactory.getInstance().getDefault();

   private HarvesterExecutor() {
   }

   static synchronized HarvesterExecutor getInstance() {
      if (SINGLETON == null) {
         SINGLETON = new HarvesterExecutor();
      }

      return SINGLETON;
   }

   public void execute(Runnable command) {
      this.workManager.schedule(command);
   }

   public WorkManager getWorkManager() {
      return this.workManager;
   }
}
