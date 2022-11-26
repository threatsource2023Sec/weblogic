package weblogic.work;

import weblogic.kernel.ExecuteRequest;
import weblogic.kernel.ExecuteThread;

public final class ExecuteRequestAdapter implements ExecuteRequest {
   private Runnable work;

   public ExecuteRequestAdapter(Runnable work) {
      this.work = work;
   }

   public void execute(ExecuteThread thd) throws Exception {
      this.work.run();
   }

   public String toString() {
      return this.work.toString();
   }
}
