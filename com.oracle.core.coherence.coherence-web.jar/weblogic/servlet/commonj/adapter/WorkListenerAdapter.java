package weblogic.servlet.commonj.adapter;

import commonj.work.WorkEvent;
import commonj.work.WorkListener;

public class WorkListenerAdapter implements WorkListener {
   private final com.tangosol.coherence.servlet.commonj.WorkListener mWorkListener;

   public WorkListenerAdapter(com.tangosol.coherence.servlet.commonj.WorkListener wl) {
      this.mWorkListener = wl;
   }

   public void workAccepted(WorkEvent we) {
      this.mWorkListener.workAccepted(new WorkEventAdapter(we));
   }

   public void workCompleted(WorkEvent we) {
      this.mWorkListener.workCompleted(new WorkEventAdapter(we));
   }

   public void workRejected(WorkEvent we) {
      this.mWorkListener.workRejected(new WorkEventAdapter(we));
   }

   public void workStarted(WorkEvent we) {
      this.mWorkListener.workStarted(new WorkEventAdapter(we));
   }
}
