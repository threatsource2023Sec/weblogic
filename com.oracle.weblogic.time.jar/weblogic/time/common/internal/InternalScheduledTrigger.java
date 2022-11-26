package weblogic.time.common.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.SubjectManager;
import weblogic.time.common.Schedulable;
import weblogic.time.common.ScheduledTriggerDef;
import weblogic.time.common.TimeTriggerException;
import weblogic.time.common.Triggerable;
import weblogic.work.WorkManager;

public class InternalScheduledTrigger extends TimeEvent implements ScheduledTriggerDef, PrivilegedAction {
   public static boolean cancelAppTriggers = false;
   Schedulable scheduler;
   TimeEventGenerator teg;
   protected Triggerable trigger;
   private boolean cancelled = false;
   private boolean scheduled = false;
   private AuthenticatedSubject subject;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean runInThread = false;
   private boolean isDaemon;

   public InternalScheduledTrigger() {
   }

   public InternalScheduledTrigger(Schedulable scheduler, Triggerable trigger) {
      this.scheduler = scheduler;
      this.trigger = trigger;
      this.teg = TimeEventGenerator.getOne();
   }

   public InternalScheduledTrigger(Schedulable scheduler, Triggerable trigger, WorkManager manager) {
      this.scheduler = scheduler;
      this.trigger = trigger;
      this.teg = TimeEventGenerator.getOne();
      this.setWorkManager(manager);
   }

   public InternalScheduledTrigger(Schedulable scheduler, Triggerable trigger, TimeEventGenerator teg) {
      this.scheduler = scheduler;
      this.trigger = trigger;
      this.teg = teg;
   }

   public void setRunInThread(boolean runInThread) {
      this.runInThread = runInThread;
   }

   public synchronized int schedule() throws TimeTriggerException {
      this.subject = (AuthenticatedSubject)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return SubjectManager.getSubjectManager().getCurrentSubject(InternalScheduledTrigger.kernelId);
         }
      });
      if (!this.scheduled) {
         this.time = this.scheduler.schedule(System.currentTimeMillis());
         if (this.time > 0L) {
            this.teg.insert(this);
         }
      }

      this.scheduled = true;
      return this.teg.nextTriggerID();
   }

   public void setDaemon(boolean daemon) throws TimeTriggerException {
      if (this.cancelled) {
         throw new TimeTriggerException("Scheduled Trigger has been cancelled");
      } else {
         this.isDaemon = daemon;
      }
   }

   public boolean isDaemon() {
      return this.isDaemon;
   }

   public synchronized boolean cancel() throws TimeTriggerException {
      this.cancelled = true;
      this.scheduled = false;
      return this.teg.delete(this);
   }

   public void executeTimer(long time, WorkManager manager, boolean sendEvent) {
      this.time = time;
      if (this.subject == null) {
         this.subject = (AuthenticatedSubject)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return SubjectManager.getSubjectManager().getCurrentSubject(InternalScheduledTrigger.kernelId);
            }
         });
      }

      if (this.runInThread) {
         this.executeLocally();
      } else if (cancelAppTriggers) {
         this.teg.deleted(this);
         this.destroy();
      } else {
         manager.schedule(new Runnable() {
            public void run() {
               InternalScheduledTrigger.this.execute();
            }
         });
      }

   }

   protected void execute() {
      this.executeLocally();
   }

   private void executeLocally() {
      if (!this.cancelled) {
         SecurityServiceManager.runAs(kernelId, this.subject, this);
         if (!this.cancelled) {
            if (this.time > 0L) {
               this.teg.insert(this);
            } else {
               this.teg.deleted(this);
               this.destroy();
            }
         }
      }

   }

   public Object run() {
      this.trigger.trigger(this.scheduler);
      if (!this.cancelled) {
         this.time = this.scheduler.schedule(this.time);
      }

      return null;
   }

   protected synchronized void destroy() {
      super.destroy();
      this.cancelled = true;
      this.scheduled = false;
   }

   public static void cancelAppTriggers(boolean flag) {
      cancelAppTriggers = flag;
   }
}
