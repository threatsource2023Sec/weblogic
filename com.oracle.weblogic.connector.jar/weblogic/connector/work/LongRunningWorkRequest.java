package weblogic.connector.work;

import javax.resource.spi.work.ExecutionContext;
import javax.resource.spi.work.WorkException;
import weblogic.connector.common.Debug;
import weblogic.connector.security.layer.WorkImpl;
import weblogic.connector.security.layer.WorkListenerImpl;

public class LongRunningWorkRequest extends WorkRequest {
   private LongRunningWorkManager lrwm;
   private boolean finished;

   LongRunningWorkRequest(WorkImpl work, long startTimeout, ExecutionContext ec, WorkListenerImpl listener, WorkContextManager ctxManager, LongRunningWorkManager wm) throws WorkException {
      super(work, startTimeout, ec, listener, ctxManager);
      this.lrwm = wm;
   }

   public void run() {
      boolean var9 = false;

      try {
         var9 = true;
         if (Debug.isWorkEnabled()) {
            Debug.work("start to run LongRunning work: " + this.getWork());
         }

         super.run();
         var9 = false;
      } finally {
         if (var9) {
            this.lrwm.unregister(this);
            Debug.work("finished run LongRunning work: " + this.getWork());
            synchronized(this) {
               this.finished = true;
               this.notifyAll();
            }
         }
      }

      this.lrwm.unregister(this);
      Debug.work("finished run LongRunning work: " + this.getWork());
      synchronized(this) {
         this.finished = true;
         this.notifyAll();
      }
   }

   protected void release() {
      if (Debug.isWorkEnabled()) {
         Debug.work("release LongRunning work: " + this.getWork());
      }

      this.getWork().release();
   }

   void blockTillCompletion() {
      super.blockTillCompletion();
      synchronized(this) {
         while(!this.finished) {
            if (Debug.isWorkEnabled()) {
               Debug.work("waiting completion of LongRunning work: " + this.getWork());
            }

            try {
               this.wait();
            } catch (InterruptedException var4) {
            }
         }

      }
   }
}
