package weblogic.work.concurrent;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.naming.Context;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.timers.Timer;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManager;
import weblogic.work.concurrent.context.ApplicationContextProcessor;
import weblogic.work.concurrent.future.AbstractFutureImpl;
import weblogic.work.concurrent.future.ManagedPeriodFutureImpl;
import weblogic.work.concurrent.future.ManagedScheduledFutureImpl;
import weblogic.work.concurrent.future.ManagedTriggerFutureImpl;
import weblogic.work.concurrent.spi.ConcurrentManagedObjectBuilder;
import weblogic.work.concurrent.spi.ContextProvider;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.LogUtils;

public class ManagedScheduledExecutorServiceImpl extends ManagedExecutorServiceImpl implements ManagedScheduledExecutorService {
   private volatile TimerManager timerManager = null;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentMSES");

   public ManagedScheduledExecutorServiceImpl(ConcurrentManagedObjectBuilder builder) {
      super(builder);
   }

   private ManagedScheduledExecutorServiceImpl(ManagedScheduledExecutorServiceImpl target, ContextProvider provider) {
      super(target, provider);
   }

   private TimerManager getTimerManager() {
      if (this.timerManager != null) {
         return this.timerManager;
      } else {
         synchronized(this) {
            if (this.timerManager != null) {
               return this.timerManager;
            } else {
               String baseName = ConcurrentUtils.getGlobalName(this, "Timer");
               int index = 1;
               String timerName = baseName;
               boolean exists = false;
               ScheduledWorkManager workmanager = new ScheduledWorkManager(this.getWorkManager(), this.daemonThreadManager);

               while(true) {
                  try {
                     this.timerManager = this.createTimerManager(timerName, workmanager);
                     break;
                  } catch (IllegalArgumentException var9) {
                     exists = true;
                     timerName = baseName + String.valueOf(index++);
                  }
               }

               if (exists) {
                  ConcurrencyLogger.logTimerNameConflict(baseName, timerName);
               }

               return this.timerManager;
            }
         }
      }
   }

   TimerManager createTimerManager(String timerName, WorkManager workmanager) {
      return TimerManagerFactory.getTimerManagerFactory().getTimerManager(timerName, workmanager);
   }

   private void setLongRunningTask(AbstractFutureImpl future) {
      ScheduledLongRunningTask task = new ScheduledLongRunningTask(future, this.getPriority());
      future.setDaemonThreadTask(task);
   }

   private void doSubmitCheck(TaskWrapper taskWrapper) {
      this.checkServerStatus();
      this.warnUserObjectCheckSkipped(taskWrapper);
   }

   private ScheduledFuture scheduleOnce(TaskWrapper taskWrapper, long delay, TimeUnit unit) {
      this.doSubmitCheck(taskWrapper);
      this.warnUserObjectCheckSkipped(taskWrapper);
      Date time = ConcurrentUtils.getScheduledTime(delay, unit);
      TaskStateNotifier stateNotifier = new TaskStateNotifier(this, taskWrapper, (BatchTaskListener)null);
      ManagedScheduledFutureImpl future = new ManagedScheduledFutureImpl(taskWrapper, stateNotifier, time);
      if (taskWrapper.isLongRunning()) {
         this.setLongRunningTask(future);
      }

      try {
         Timer timer = this.getTimerManager().schedule(future, time);
         future.setTimer(timer);
      } catch (IllegalStateException var9) {
         throw new RejectedExecutionException(LogUtils.getMessageRejectForStop(this.name));
      }

      stateNotifier.taskSubmitted(future);
      return future;
   }

   public ScheduledFuture schedule(Runnable command, long delay, TimeUnit unit) {
      this.rejectUnsupportedRequestOutOfMES();
      TaskWrapper taskWrapper = new TaskWrapper(command, (Object)null, this.getContextSetupProcessor(), this.taskClassloader);
      return this.scheduleOnce(taskWrapper, delay, unit);
   }

   public ScheduledFuture schedule(Callable callable, long delay, TimeUnit unit) {
      this.rejectUnsupportedRequestOutOfMES();
      TaskWrapper taskWrapper = new TaskWrapper(callable, this.getContextSetupProcessor(), this.taskClassloader);
      return this.scheduleOnce(taskWrapper, delay, unit);
   }

   private ScheduledFuture periodSchedule(Runnable command, Date firstTime, long millsPeriod) {
      TaskWrapper taskWrapper = new TaskWrapper(command, (Object)null, this.getContextSetupProcessor(), this.taskClassloader);
      this.doSubmitCheck(taskWrapper);
      TaskStateNotifier stateNotifier = new TaskStateNotifier(this, taskWrapper, (BatchTaskListener)null);
      ManagedPeriodFutureImpl future = new ManagedPeriodFutureImpl(taskWrapper, stateNotifier, firstTime, millsPeriod);
      if (taskWrapper.isLongRunning()) {
         this.setLongRunningTask(future);
      }

      try {
         Timer timer = null;
         if (millsPeriod > 0L) {
            timer = this.getTimerManager().scheduleAtFixedRate(future, firstTime, millsPeriod);
         } else {
            timer = this.getTimerManager().schedule(future, firstTime, -millsPeriod);
         }

         future.setTimer(timer);
      } catch (IllegalStateException var9) {
         throw new RejectedExecutionException(String.format("Executor %s is stopped", this.getName()));
      }

      stateNotifier.taskSubmitted(future);
      return future;
   }

   public ScheduledFuture scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
      this.rejectUnsupportedRequestOutOfMES();
      if (period <= 0L) {
         throw new IllegalArgumentException(LogUtils.getMessageWrongParaemter("period", period));
      } else {
         Date time = ConcurrentUtils.getScheduledTime(initialDelay, unit);
         long millsPeriod = unit.toMillis(period);
         return this.periodSchedule(command, time, millsPeriod);
      }
   }

   public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
      this.rejectUnsupportedRequestOutOfMES();
      if (delay <= 0L) {
         throw new IllegalArgumentException(LogUtils.getMessageWrongParaemter("delay", delay));
      } else {
         Date time = ConcurrentUtils.getScheduledTime(initialDelay, unit);
         long millsDelay = unit.toMillis(delay);
         return this.periodSchedule(command, time, -millsDelay);
      }
   }

   private ScheduledFuture triggerSchedule(TaskWrapper taskWrapper, Trigger trigger) {
      this.doSubmitCheck(taskWrapper);
      taskWrapper.checkUserObject(trigger, this.taskClassloader);
      Date time = null;
      if (trigger != null) {
         trigger = new TriggerWithContext((Trigger)trigger, taskWrapper);
         time = ((Trigger)trigger).getNextRunTime((LastExecution)null, new Date());
      }

      if (time == null) {
         throw new RejectedExecutionException("Could not get next run time from trigger");
      } else {
         TaskStateNotifier stateNotifier = new TaskStateNotifier(this, taskWrapper, (BatchTaskListener)null);
         ManagedTriggerFutureImpl future = new ManagedTriggerFutureImpl(taskWrapper, stateNotifier, this.getTimerManager(), time, (Trigger)trigger);
         if (taskWrapper.isLongRunning()) {
            this.setLongRunningTask(future);
         }

         try {
            Timer timer = this.getTimerManager().schedule(future, time);
            future.setTimer(timer);
         } catch (IllegalStateException var7) {
            throw new RejectedExecutionException(LogUtils.getMessageRejectForStop(this.name));
         }

         stateNotifier.taskSubmitted(future);
         return future;
      }
   }

   public ScheduledFuture schedule(Runnable command, Trigger trigger) {
      this.rejectUnsupportedRequestOutOfMES();
      TaskWrapper taskWrapper = new TaskWrapper(command, (Object)null, this.getContextSetupProcessor(), this.taskClassloader);
      return this.triggerSchedule(taskWrapper, trigger);
   }

   public ScheduledFuture schedule(Callable callable, Trigger trigger) {
      this.rejectUnsupportedRequestOutOfMES();
      TaskWrapper taskWrapper = new TaskWrapper(callable, this.getContextSetupProcessor(), this.taskClassloader);
      return this.triggerSchedule(taskWrapper, trigger);
   }

   public void terminate() {
      if (this.timerManager != null) {
         try {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(String.format("MSES %s stop timer manager begin", this.name));
            }

            this.timerManager.stop();
         } finally {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(String.format("MSES %s stop timer manager end", this.name));
            }

         }

      }
   }

   public String getJSR236Class() {
      return ManagedScheduledExecutorService.class.getName();
   }

   AbstractConcurrentManagedObject.ConcurrentOpaqueReference createApplicationDelegator(ClassLoader classLoader, Context jndiContext) {
      return new AbstractConcurrentManagedObject.ConcurrentOpaqueReference(new ManagedScheduledExecutorServiceImpl(this, new ApplicationContextProcessor(this.getAppId(), classLoader, jndiContext, 8)));
   }
}
