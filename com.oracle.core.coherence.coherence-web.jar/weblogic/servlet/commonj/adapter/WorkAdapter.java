package weblogic.servlet.commonj.adapter;

import commonj.work.Work;

public class WorkAdapter implements Work {
   private final com.tangosol.coherence.servlet.commonj.Work mWork;

   public WorkAdapter(com.tangosol.coherence.servlet.commonj.Work work) {
      this.mWork = work;
   }

   public boolean isDaemon() {
      return this.mWork.isDaemon();
   }

   public void release() {
      throw new UnsupportedOperationException("This method is not implemented.");
   }

   public void run() {
      this.mWork.run();
   }
}
