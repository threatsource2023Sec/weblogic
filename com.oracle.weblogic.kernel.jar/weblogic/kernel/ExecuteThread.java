package weblogic.kernel;

import java.lang.reflect.Method;

public class ExecuteThread extends AuditableThread {
   private static final Throwable REQUEST_DEATH = new RequestDeath();
   private ExecuteThreadManager em;
   private int hashcode;
   private ExecuteRequest req = null;
   private boolean printStuckMessage = false;
   private boolean started = false;
   private int executeCount = 0;
   private long timeStamp = 0L;
   private boolean systemThread = false;
   private static final Method PANIC = getPanicMethod();

   ExecuteThread(int which, ExecuteThreadManager e) {
      super("ExecuteThread: '" + which + "' for queue: '" + e.getName() + "'");
      this.init(e);
   }

   ExecuteThread(int which, ExecuteThreadManager e, ThreadGroup tg) {
      super(tg, "ExecuteThread: '" + which + "' for queue: '" + e.getName() + "'");
      this.init(e);
   }

   protected void init(ExecuteThreadManager e) {
      this.em = e;
      this.hashcode = this.getName().hashCode();
   }

   public int hashCode() {
      return this.hashcode;
   }

   boolean isStarted() {
      return this.started;
   }

   public ExecuteRequest getCurrentRequest() {
      return this.req;
   }

   int getExecuteCount() {
      return this.executeCount;
   }

   public void setTimeStamp(long time) {
      this.timeStamp = time;
   }

   long getTimeStamp() {
      return this.timeStamp;
   }

   void setPrintStuckThreadMessage(boolean prtMsg) {
      this.printStuckMessage = prtMsg;
   }

   boolean getPrintStuckThreadMessage() {
      return this.printStuckMessage;
   }

   public void setSystemThread(boolean flag) {
      this.systemThread = flag;
   }

   boolean getSystemThread() {
      return this.systemThread;
   }

   public ExecuteThreadManager getExecuteThreadManager() {
      return this.em;
   }

   synchronized void notifyRequest(ExecuteRequest r) {
      this.setTimeStamp(System.currentTimeMillis());
      this.req = r;
      this.notify();
   }

   void setRequest(ExecuteRequest r) {
      this.req = r;
   }

   private synchronized void waitForRequest() {
      while(this.req == null) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

   }

   protected synchronized void readyToRun() {
      this.started = true;
      super.readyToRun();
      this.notify();
   }

   public void run() {
      this.readyToRun();

      while(true) {
         while(true) {
            try {
               this.reset();
               this.em.registerIdle(this);
               if (this.req == null) {
                  this.waitForRequest();
               }

               this.execute(this.req);
            } catch (ExecuteThreadManager.ShutdownError var2) {
               if (Kernel.isServer() && !Kernel.isIntentionalShutdown()) {
                  KernelLogger.logStopped(this.getName());
               }

               return;
            } catch (ThreadDeath var3) {
               if (Kernel.isServer()) {
                  if (!Kernel.isIntentionalShutdown()) {
                     KernelLogger.logStopped(this.getName());
                  }

                  throw var3;
               }
            }
         }
      }
   }

   void execute(ExecuteRequest er) {
      try {
         ++this.executeCount;
         this.setTimeStamp(System.currentTimeMillis());
         er.execute(this);
      } catch (ThreadDeath var3) {
         throw var3;
      } catch (ExecuteThreadManager.ShutdownError var4) {
         throw var4;
      } catch (RequestDeath var5) {
         KernelLogger.logExecuteCancelled(er.toString());
      } catch (OutOfMemoryError var6) {
         KernelLogger.logExecuteFailed(var6);
         notifyOOME(var6);
      } catch (Throwable var7) {
         if (!Kernel.isApplet()) {
            KernelLogger.logExecuteFailed(var7);
         } else {
            var7.printStackTrace();
         }
      }

   }

   private static void notifyOOME(OutOfMemoryError oome) {
      if (PANIC != null) {
         try {
            PANIC.invoke((Object)null, oome);
         } catch (Exception var2) {
         }
      }

   }

   private static Method getPanicMethod() {
      try {
         return Class.forName("weblogic.health.HealthMonitorService").getMethod("panic", Class.forName("java.lang.Throwable"));
      } catch (Exception var1) {
         return null;
      }
   }

   protected void reset() {
      super.reset();
      this.req = null;
   }

   private static final class RequestDeath extends Error {
      private RequestDeath() {
      }

      // $FF: synthetic method
      RequestDeath(Object x0) {
         this();
      }
   }
}
