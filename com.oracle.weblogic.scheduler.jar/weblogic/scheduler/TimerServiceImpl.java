package weblogic.scheduler;

import java.security.AccessController;
import java.util.Date;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.internal.ScheduleExpressionWrapper;

public class TimerServiceImpl implements TimerManager {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final long ILLEGAL_INTERVAL = -1L;
   private TimerBasis basis;
   private String name;
   private boolean forGlobalRG;

   public static TimerManager create(String name) {
      return new TimerServiceImpl(name, false);
   }

   public static TimerManager create(String name, boolean forGlobalRG) {
      return new TimerServiceImpl(name, forGlobalRG);
   }

   private TimerServiceImpl(String name, boolean forGlobalRG) {
      this.name = name;
      this.forGlobalRG = forGlobalRG;
   }

   private TimerBasis getTimerBasis() throws TimerException {
      if (this.basis == null) {
         this.basis = TimerBasisAccess.getTimerBasis(this.forGlobalRG);
      }

      return this.basis;
   }

   public Timer schedule(TimerListener to, long duration) {
      if (duration < 0L) {
         throw new IllegalArgumentException("Duration is negative");
      } else {
         try {
            return this.createTimerInternal(to, duration, -1L);
         } catch (TimerException var5) {
            throw new IllegalStateException(var5);
         }
      }
   }

   public Timer schedule(TimerListener to, long initialDuration, long intervalDuration) {
      if (initialDuration < 0L) {
         throw new IllegalArgumentException("Initial duration is negative");
      } else if (intervalDuration < 0L) {
         throw new IllegalArgumentException("Interval duration is negative");
      } else {
         try {
            return this.createTimerInternal(to, initialDuration, intervalDuration);
         } catch (TimerException var7) {
            throw new IllegalStateException(var7);
         }
      }
   }

   public Timer schedule(TimerListener to, Date expiration) throws IllegalArgumentException {
      if (expiration == null) {
         throw new IllegalArgumentException("Expiration is null");
      } else {
         return this.schedule(to, expiration.getTime() - System.currentTimeMillis());
      }
   }

   public Timer schedule(TimerListener to, Date initialExpiration, long intervalDuration) throws IllegalArgumentException {
      if (initialExpiration == null) {
         throw new IllegalArgumentException("Expiration is null");
      } else {
         return this.schedule(to, initialExpiration.getTime() - System.currentTimeMillis(), intervalDuration);
      }
   }

   public weblogic.timers.Timer scheduleAtFixedRate(TimerListener listener, Date firstTime, long period) {
      throw new UnsupportedOperationException("Job scheduler does not support scheduleAtFixedRate(). It only supports schedule()");
   }

   public Timer scheduleAtFixedRate(TimerListener listener, long delay, long period) {
      throw new UnsupportedOperationException("Job scheduler does not support scheduleAtFixedRate(). It only supports schedule()");
   }

   public Timer schedule(TimerListener listener, ScheduleExpression schedule) {
      ScheduleExpressionWrapper scheduleWrapper = ScheduleExpressionWrapper.create(schedule);

      try {
         return this.createTimerInternal(listener, scheduleWrapper);
      } catch (TimerException var5) {
         throw new IllegalStateException(var5);
      }
   }

   public Timer schedule(TimerListener listener, ScheduleExpression schedule, String userKey) throws TimerAlreadyExistsException {
      ScheduleExpressionWrapper scheduleWrapper = ScheduleExpressionWrapper.create(schedule);

      try {
         return this.createTimerInternal(listener, scheduleWrapper, userKey);
      } catch (TimerException var6) {
         throw new IllegalStateException(var6);
      }
   }

   public void resume() {
      throw new IllegalStateException("job scheduler is already running!");
   }

   public void suspend() {
      throw new UnsupportedOperationException("job scheduler cannot be suspended!");
   }

   public void stop() {
      throw new UnsupportedOperationException("job scheduler cannot be stopped!");
   }

   public boolean waitForStop(long timeout_ms) throws InterruptedException {
      throw new UnsupportedOperationException("job scheduler cannot be stopped!");
   }

   public boolean isStopping() {
      return false;
   }

   public boolean isStopped() {
      return false;
   }

   public boolean waitForSuspend(long timeout_ms) throws InterruptedException {
      throw new UnsupportedOperationException("job scheduler cannot be suspended!");
   }

   public boolean isSuspending() {
      return false;
   }

   public boolean isSuspended() {
      return false;
   }

   private Timer createTimerInternal(TimerListener to, long initialDuration, long intervalDuration) throws TimerException {
      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      String id = this.getTimerBasis().createTimer(this.name, to, initialDuration, intervalDuration, user);
      return new TimerImpl(id, this.getTimerBasis());
   }

   private Timer createTimerInternal(TimerListener to, ScheduleExpressionWrapper scheduleWrapper) throws TimerException {
      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      String id = this.getTimerBasis().createCalendarTimer(this.name, to, scheduleWrapper.getNextTimeout(), scheduleWrapper, user);
      return new CalendarTimerImpl(id, this.getTimerBasis());
   }

   private Timer createTimerInternal(TimerListener to, ScheduleExpressionWrapper scheduleWrapper, String userKey) throws TimerException, TimerAlreadyExistsException {
      AuthenticatedSubject user = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      String id = this.getTimerBasis().createCalendarTimer(this.name, to, scheduleWrapper.getNextTimeout(), scheduleWrapper, user, userKey);
      return new CalendarTimerImpl(id, this.getTimerBasis());
   }
}
