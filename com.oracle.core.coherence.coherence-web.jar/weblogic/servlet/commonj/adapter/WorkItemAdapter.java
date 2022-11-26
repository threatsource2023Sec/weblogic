package weblogic.servlet.commonj.adapter;

import com.tangosol.coherence.servlet.commonj.Work;
import com.tangosol.coherence.servlet.commonj.WorkException;
import com.tangosol.coherence.servlet.commonj.WorkItem;

public class WorkItemAdapter implements WorkItem {
   public WorkItemAdapter(commonj.work.WorkItem workItem) {
   }

   public Work getResult() throws WorkException {
      throw new UnsupportedOperationException("This method is not implemented.");
   }

   public int getStatus() {
      throw new UnsupportedOperationException("This method is not implemented.");
   }
}
