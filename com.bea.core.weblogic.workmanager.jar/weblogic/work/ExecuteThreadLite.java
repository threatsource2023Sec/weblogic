package weblogic.work;

import weblogic.kernel.AuditableThread;
import weblogic.kernel.KernelStatus;

public class ExecuteThreadLite extends AuditableThread {
   private WorkManagerLite wm;
   private int hashcode;
   private Runnable runnable;
   private boolean started;

   ExecuteThreadLite(int which, WorkManagerLite wm, ThreadGroup tg) {
      super(tg, "ExecuteThread: '" + which + "' for queue: '" + wm.getName() + "'");
      this.init(wm);
   }

   protected void init(WorkManagerLite wm) {
      this.wm = wm;
      this.hashcode = this.getName().hashCode();
      this.setDaemon(true);
   }

   public int hashCode() {
      return this.hashcode;
   }

   synchronized void notifyRequest(Runnable runnable) {
      this.runnable = runnable;
      this.notify();
   }

   void setRequest(Runnable runnable) {
      this.runnable = runnable;
   }

   private synchronized void waitForRequest() {
      while(this.runnable == null) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   public void run() {
      synchronized(this) {
         this.started = true;
         this.readyToRun();
         this.notify();
      }

      while(true) {
         while(true) {
            try {
               if (this.runnable != null) {
                  this.execute(this.runnable);
               }

               this.reset();
               this.wm.registerIdle(this);
               if (this.runnable == null) {
                  this.waitForRequest();
               }
            } catch (ThreadDeath var4) {
               if (KernelStatus.isServer()) {
                  throw var4;
               }
            }
         }
      }
   }

   void execute(Runnable work) {
      try {
         work.run();
      } catch (ThreadDeath var3) {
         throw var3;
      } catch (Throwable var4) {
         var4.printStackTrace();
      }

   }

   protected final void reset() {
      super.reset();
      this.runnable = null;
   }

   public boolean isStarted() {
      return this.started;
   }

   public WorkManagerLite getWorkManager() {
      return this.wm;
   }
}
