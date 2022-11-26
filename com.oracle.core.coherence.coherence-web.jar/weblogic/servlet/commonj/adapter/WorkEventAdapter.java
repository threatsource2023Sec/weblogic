package weblogic.servlet.commonj.adapter;

import com.tangosol.coherence.servlet.commonj.WorkEvent;
import com.tangosol.coherence.servlet.commonj.WorkException;
import com.tangosol.coherence.servlet.commonj.WorkItem;

public class WorkEventAdapter implements WorkEvent {
   private final commonj.work.WorkEvent mWorkEvent;

   public WorkEventAdapter(commonj.work.WorkEvent we) {
      this.mWorkEvent = we;
   }

   public WorkException getException() {
      commonj.work.WorkException we = this.mWorkEvent.getException();
      return we == null ? null : new WorkExceptionAdapter(this.mWorkEvent.getException());
   }

   public int getType() {
      return this.mWorkEvent.getType();
   }

   public WorkItem getWorkItem() {
      return new WorkItemAdapter(this.mWorkEvent.getWorkItem());
   }
}
