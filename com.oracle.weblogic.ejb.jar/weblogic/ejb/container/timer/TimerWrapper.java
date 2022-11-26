package weblogic.ejb.container.timer;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.ScheduleExpression;
import javax.ejb.Timer;
import javax.ejb.TimerHandle;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.TimerIntf;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.ejb.container.internal.AllowedMethodsHelper;
import weblogic.logging.Loggable;

public final class TimerWrapper implements Timer {
   private final TimerIntf delegate;

   public TimerWrapper(TimerIntf delegate) {
      this.delegate = delegate;
   }

   public void cancel() {
      this.checkTimerState();
      this.delegate.cancel();
   }

   public TimerHandle getHandle() {
      this.checkTimerState();
      return this.delegate.getHandle();
   }

   public Serializable getInfo() {
      this.checkTimerState();
      return this.delegate.getInfo();
   }

   public Date getNextTimeout() {
      this.checkTimerState();
      return this.delegate.getNextTimeout();
   }

   public long getTimeRemaining() {
      this.checkTimerState();
      return this.delegate.getTimeRemaining();
   }

   public boolean isCalendarTimer() throws IllegalStateException, NoSuchObjectLocalException, EJBException {
      this.checkTimerState();
      return this.delegate.isCalendarTimer();
   }

   public boolean isPersistent() throws IllegalStateException, NoSuchObjectLocalException, EJBException {
      this.checkTimerState();
      return this.delegate.isPersistent();
   }

   public ScheduleExpression getSchedule() throws IllegalStateException, NoSuchObjectLocalException, EJBException {
      this.checkTimerState();
      return this.delegate.getSchedule();
   }

   private void checkTimerState() {
      if (!this.delegate.exists()) {
         throw new NoSuchObjectLocalException("Timer has expired or has been cancelled");
      } else {
         WLEnterpriseBean invokingBean = AllowedMethodsHelper.getBean();
         if (invokingBean != null) {
            int state = invokingBean.__WL_getMethodState();
            Loggable l;
            if (state == 4) {
               l = EJBLogger.logCannotInvokeTimerObjectsFromEjbCreateLoggable();
               throw new IllegalStateException(l.getMessageText());
            }

            if (state == 1024) {
               l = EJBLogger.logCannotInvokeTimerObjectsFromAfterCompletionLoggable();
               throw new IllegalStateException(l.getMessageText());
            }
         }

      }
   }
}
