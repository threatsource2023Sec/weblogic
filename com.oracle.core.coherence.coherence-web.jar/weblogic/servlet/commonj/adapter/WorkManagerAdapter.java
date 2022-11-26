package weblogic.servlet.commonj.adapter;

import com.tangosol.coherence.servlet.commonj.Work;
import com.tangosol.coherence.servlet.commonj.WorkException;
import com.tangosol.coherence.servlet.commonj.WorkItem;
import com.tangosol.coherence.servlet.commonj.WorkListener;
import com.tangosol.coherence.servlet.commonj.WorkManager;

public class WorkManagerAdapter implements WorkManager {
   private commonj.work.WorkManager mWorkManager;

   public WorkManagerAdapter(commonj.work.WorkManager workManager) {
      this.mWorkManager = workManager;
   }

   public WorkItem schedule(Work work) throws WorkException, IllegalArgumentException {
      try {
         return new WorkItemAdapter(this.mWorkManager.schedule(new WorkAdapter(work)));
      } catch (commonj.work.WorkException var3) {
         throw new WorkException(var3.getMessage(), var3.getCause());
      }
   }

   public WorkItem schedule(Work work, WorkListener wl) throws WorkException, IllegalArgumentException {
      try {
         return new WorkItemAdapter(this.mWorkManager.schedule(new WorkAdapter(work), new WorkListenerAdapter(wl)));
      } catch (commonj.work.WorkException var4) {
         throw new WorkException(var4.getMessage(), var4.getCause());
      }
   }

   public boolean shutdown(long timeout) {
      return true;
   }
}
