package weblogic.wtc.wls;

import com.bea.core.jatmi.intf.TCTask;
import com.bea.core.jatmi.intf.TCTaskManager;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;
import weblogic.wtc.jatmi.TPException;

public final class WlsTaskManager implements TCTaskManager {
   private static WorkManager _wm = null;

   public WlsTaskManager() {
      setWorkManager(WorkManagerFactory.getInstance().findOrCreate("weblogic.kernel.WTC", -1, -1));
   }

   private static synchronized void setWorkManager(WorkManager wmParam) {
      _wm = wmParam;
   }

   private static synchronized WorkManager getWorkManager() {
      return _wm;
   }

   public void initialize() throws TPException {
   }

   public void shutdown(int type) {
      setWorkManager((WorkManager)null);
   }

   public void schedule(TCTask tsk) {
      WorkManager lWm = getWorkManager();
      if (lWm != null) {
         lWm.schedule(new WlsTaskHandler(tsk));
      }

   }
}
