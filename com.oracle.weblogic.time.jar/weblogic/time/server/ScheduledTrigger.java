package weblogic.time.server;

import weblogic.rjvm.PeerGoneEvent;
import weblogic.rjvm.PeerGoneListener;
import weblogic.rjvm.RJVM;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.spi.EndPoint;
import weblogic.time.common.Schedulable;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Triggerable;
import weblogic.time.common.internal.InternalScheduledTrigger;
import weblogic.time.common.internal.TimeEventGenerator;
import weblogic.work.WorkManager;

public final class ScheduledTrigger extends InternalScheduledTrigger implements PeerGoneListener {
   private ClassLoader classLoader;
   private EndPoint clientEndPoint;

   public ScheduledTrigger(Schedulable scheduler, Triggerable trigger) {
      super(scheduler, trigger);
   }

   public ScheduledTrigger(Schedulable scheduler, Triggerable trigger, WorkManager manager) {
      super(scheduler, trigger, manager);
   }

   public ScheduledTrigger(Schedulable scheduler, Triggerable trigger, TimeEventGenerator teg) {
      super(scheduler, trigger, teg);
   }

   protected void execute() {
      if (this.classLoader != null) {
         Thread.currentThread().setContextClassLoader(this.classLoader);
      }

      try {
         super.execute();
      } finally {
         Thread.currentThread().setContextClassLoader((ClassLoader)null);
      }

   }

   public boolean cancel() throws TimeTriggerException {
      this.destroy();
      return super.cancel();
   }

   protected void destroy() {
      if (this.clientEndPoint != null && this.clientEndPoint instanceof RJVM) {
         ((RJVM)this.clientEndPoint).removePeerGoneListener(this);
      }

      this.clientEndPoint = null;
      this.classLoader = null;
   }

   public int schedule() throws TimeTriggerException {
      if (!this.isDaemon()) {
         this.clientEndPoint = ServerHelper.getClientEndPointInternal();
         if (this.clientEndPoint != null && this.clientEndPoint instanceof RJVM) {
            ((RJVM)this.clientEndPoint).addPeerGoneListener(this);
         }
      }

      this.classLoader = Thread.currentThread().getContextClassLoader();
      return super.schedule();
   }

   public void peerGone(PeerGoneEvent e) {
      try {
         this.cancel();
      } catch (TimeTriggerException var3) {
      }

   }

   public String toString() {
      return "Scheduled Trigger";
   }
}
