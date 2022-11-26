package weblogic.t3.srvr;

import weblogic.management.runtime.ServerStates;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

final class SubsystemRequest extends WorkAdapter implements ServerStates {
   private ServerService slc;
   private Throwable t;
   private boolean done;
   private final boolean concurrent;
   private int action;

   private SubsystemRequest(ServerService slc, boolean concurrent) {
      this.slc = slc;
      this.concurrent = concurrent;
   }

   SubsystemRequest(ServerService slc) {
      this(slc, false);
   }

   SubsystemRequest() {
      this((ServerService)null, false);
   }

   void setRequest(ServerService slc) {
      this.slc = slc;
   }

   public void start(long timeout) throws ServiceFailureException {
      this.action(6, timeout);
   }

   public void run() {
      try {
         switch (this.action) {
            case 3:
               this.slc.halt();
               break;
            case 4:
               this.slc.stop();
               break;
            case 5:
            default:
               throw new ServiceFailureException("Unknown ServerLifeCycle action");
            case 6:
               this.slc.start();
         }

         this.notify(true);
      } catch (Throwable var2) {
         this.notify(var2);
      }

   }

   private void action(int state, long timeout) throws ServiceFailureException {
      try {
         this.action = state;
         WorkManagerFactory.getInstance().getSystem().schedule(this);
         if (!this.concurrent) {
            this.rendezvouz(timeout);
         }
      } finally {
         this.t = null;
         this.done = false;
      }

   }

   private synchronized void rendezvouz(long timeout) throws ServiceFailureException {
      long startTime = System.currentTimeMillis();

      while(this.notDone()) {
         try {
            if (timeout != 0L && System.currentTimeMillis() - startTime >= timeout) {
               this.slc.halt();
               throw T3Srvr.STARTUP_TIMED_OUT;
            }

            this.wait(timeout);
         } catch (InterruptedException var6) {
         }
      }

      if (this.t != null) {
         if (this.t instanceof ServiceFailureException) {
            throw (ServiceFailureException)this.t;
         }

         if (this.t instanceof RuntimeException) {
            throw (RuntimeException)this.t;
         }

         if (this.t instanceof Error) {
            throw (Error)this.t;
         }
      }

   }

   synchronized void notify(Throwable t) {
      this.t = t;
      this.notify();
   }

   private synchronized void notify(boolean ignored) {
      this.done = true;
      this.notify();
   }

   private final boolean notDone() {
      return !this.done && this.t == null;
   }
}
