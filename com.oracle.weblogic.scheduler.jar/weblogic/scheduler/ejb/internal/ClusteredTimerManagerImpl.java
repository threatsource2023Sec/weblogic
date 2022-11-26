package weblogic.scheduler.ejb.internal;

import java.io.Serializable;
import java.util.Date;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.scheduler.NoSuchObjectLocalException;
import weblogic.scheduler.TimerAlreadyExistsException;
import weblogic.scheduler.TimerBasisAccess;
import weblogic.scheduler.TimerException;
import weblogic.scheduler.TimerHandle;
import weblogic.scheduler.TimerMaster;
import weblogic.scheduler.TimerServiceImpl;
import weblogic.scheduler.ejb.ClusteredTimerListener;
import weblogic.scheduler.ejb.ClusteredTimerManager;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;

public final class ClusteredTimerManagerImpl implements ClusteredTimerManager {
   private final String name;
   private final String annotation;
   private final TimerServiceImpl timerManagerDelegate;
   private final String dispatchPolicy;
   private final boolean forGlobalRGApp;
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugEjbTimers");

   public ClusteredTimerManagerImpl(String name, String annotation) {
      this(name, annotation, (String)null);
   }

   public ClusteredTimerManagerImpl(String name, String annotation, String dispatchPolicy) {
      this.name = name;
      this.annotation = annotation;
      this.forGlobalRGApp = ClusteredTimerManagerUtility.isCurrentApplicationFromGlobalDomainResourceGroup();
      this.timerManagerDelegate = (TimerServiceImpl)TimerServiceImpl.create(name, this.forGlobalRGApp);
      if (this.forGlobalRGApp) {
         TimerMaster.ensureStartedForGlobalRG(this);
      }

      this.dispatchPolicy = dispatchPolicy;
   }

   public Timer schedule(TimerListener timerListener, long initial) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), initial);
      return new TimerWrapper(timer);
   }

   public Timer schedule(TimerListener timerListener, Date date) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), date);
      return new TimerWrapper(timer);
   }

   public Timer schedule(TimerListener timerListener, long initial, long period) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), initial, period);
      return new TimerWrapper(timer);
   }

   public Timer schedule(TimerListener timerListener, Date date, long period) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), date, period);
      return new TimerWrapper(timer);
   }

   public Timer scheduleAtFixedRate(TimerListener timerListener, Date date, long period) {
      weblogic.scheduler.Timer timer = (weblogic.scheduler.Timer)this.timerManagerDelegate.scheduleAtFixedRate(this.getTimerListener(timerListener), date, period);
      return new TimerWrapper(timer);
   }

   public Timer scheduleAtFixedRate(TimerListener timerListener, long initial, long period) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.scheduleAtFixedRate(this.getTimerListener(timerListener), initial, period);
      return new TimerWrapper(timer);
   }

   public Timer schedule(TimerListener timerListener, ScheduleExpression schedule) {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), schedule);
      return new TimerWrapper(timer);
   }

   public Timer schedule(TimerListener timerListener, ScheduleExpression schedule, String userKey) throws TimerAlreadyExistsException {
      weblogic.scheduler.Timer timer = this.timerManagerDelegate.schedule(this.getTimerListener(timerListener), schedule, userKey);
      return new TimerWrapper(timer);
   }

   public void resume() {
      this.timerManagerDelegate.resume();
   }

   public void suspend() {
      this.timerManagerDelegate.suspend();
   }

   public void cancelAllTimers() {
      try {
         TimerBasisAccess.getTimerBasis(this.forGlobalRGApp).cancelTimers(this.name);
      } catch (TimerException var2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("ClusteredTimerManagerImpl.stop(): " + var2, var2);
         }
      }

   }

   public void stop() {
      if (this.forGlobalRGApp) {
         TimerMaster.stopGlobalRGIfNoMoreUsers(this);
      }

   }

   public boolean waitForStop(long gracePeriod) throws InterruptedException {
      return this.timerManagerDelegate.waitForStop(gracePeriod);
   }

   public boolean isStopping() {
      return this.timerManagerDelegate.isStopping();
   }

   public boolean isStopped() {
      return this.timerManagerDelegate.isStopped();
   }

   public boolean waitForSuspend(long gracePeriod) throws InterruptedException {
      return this.timerManagerDelegate.waitForSuspend(gracePeriod);
   }

   public boolean isSuspending() {
      return this.timerManagerDelegate.isSuspending();
   }

   public boolean isSuspended() {
      return this.timerManagerDelegate.isSuspended();
   }

   private TimerListener getTimerListener(TimerListener timerListener) {
      if (timerListener instanceof ClusteredTimerListener) {
         return (TimerListener)(((ClusteredTimerListener)timerListener).isTransactional() ? new TransactionalClusteredTimerListenerWrapper(this.annotation, (ClusteredTimerListener)timerListener, this.dispatchPolicy) : new ClusteredTimerListenerWrapper(this.annotation, (ClusteredTimerListener)timerListener, this.dispatchPolicy));
      } else {
         return timerListener;
      }
   }

   public Timer[] getTimers() {
      try {
         return this.getWrappers(TimerBasisAccess.getTimerBasis(this.forGlobalRGApp).getTimers(this.name));
      } catch (TimerException var2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("ClusteredTimerManagerImpl.getTimers(): " + var2, var2);
         }

         return null;
      }
   }

   public Timer[] getTimers(String groupName) {
      try {
         return this.getWrappers(TimerBasisAccess.getTimerBasis(this.forGlobalRGApp).getTimers(this.name, groupName + "@@"));
      } catch (TimerException var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("ClusteredTimerManagerImpl.getTimers(" + groupName + "): " + var3, var3);
         }

         return null;
      }
   }

   public Timer getTimerByUserKey(String userKey) {
      try {
         weblogic.scheduler.Timer timer = TimerBasisAccess.getTimerBasis(this.forGlobalRGApp).getTimerByUserKey(this.name, userKey);
         return timer == null ? null : new TimerWrapper(timer);
      } catch (TimerException var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("ClusteredTimerManagerImpl.getTimerByUserKey(" + userKey + "): " + var3, var3);
         }

         return null;
      }
   }

   private Timer[] getWrappers(weblogic.scheduler.Timer[] timers) {
      if (timers == null) {
         return null;
      } else {
         Timer[] wrappers = new TimerWrapper[timers.length];

         for(int i = 0; i < timers.length; ++i) {
            wrappers[i] = new TimerWrapper(timers[i]);
         }

         return wrappers;
      }
   }

   private static class TimerHandleWrapper implements TimerHandle {
      private final TimerHandle handle;

      TimerHandleWrapper(TimerHandle handle) {
         this.handle = handle;
      }

      public weblogic.scheduler.Timer getTimer() throws NoSuchObjectLocalException, TimerException {
         return new TimerWrapper(this.handle.getTimer());
      }
   }

   private static class TimerWrapper implements weblogic.scheduler.Timer {
      private final weblogic.scheduler.Timer timer;

      TimerWrapper(weblogic.scheduler.Timer timer) {
         this.timer = timer;
      }

      public long getTimeout() {
         return this.timer.getTimeout();
      }

      public long getPeriod() {
         return this.timer.getPeriod();
      }

      public TimerListener getListener() {
         ClusteredTimerListenerWrapper listenerWrapper = (ClusteredTimerListenerWrapper)this.timer.getListener();
         return listenerWrapper != null ? listenerWrapper.getEJBTimerListener() : null;
      }

      public boolean cancel() {
         return this.timer.cancel();
      }

      public boolean isStopped() {
         return this.timer.isStopped();
      }

      public boolean isCancelled() {
         return this.timer.isCancelled();
      }

      public Serializable getInfo() throws NoSuchObjectLocalException, TimerException {
         return this.timer.getInfo();
      }

      public TimerHandle getHandle() throws NoSuchObjectLocalException, TimerException {
         return new TimerHandleWrapper(this.timer.getHandle());
      }

      public String getListenerClassName() {
         TimerListener listener = this.getListener();
         return listener != null ? listener.getClass().getName() : null;
      }

      public ScheduleExpression getSchedule() {
         return this.timer.getSchedule();
      }

      public boolean isCalendarTimer() {
         return this.timer.isCalendarTimer();
      }
   }
}
