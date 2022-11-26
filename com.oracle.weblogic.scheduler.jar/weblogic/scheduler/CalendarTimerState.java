package weblogic.scheduler;

import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.TimerListener;
import weblogic.timers.internal.ScheduleExpressionWrapper;

public class CalendarTimerState extends TimerState {
   private ScheduleExpressionWrapper scheduleWrapper;

   public CalendarTimerState(TimerBasis timerBasis, String id, TimerListener to, long timeout, ScheduleExpressionWrapper scheduleWrapper, AuthenticatedSubject user, String domainID) {
      super(timerBasis, id, to, timeout, -1L, user, domainID);
      this.scheduleWrapper = scheduleWrapper;
   }

   public ScheduleExpression getSchedule() {
      return this.scheduleWrapper.getSchedule();
   }

   public boolean isCalendarTimer() {
      return true;
   }

   protected boolean shouldCancelTimer() {
      boolean result = this.scheduleWrapper.getNextTimeout() == -1L;
      return result;
   }

   protected void advanceTimer(TimerBasis basis) throws NoSuchObjectLocalException, TimerException {
      basis.advanceCalendarTimer(this.getId(), this.getTimedObject(), this.scheduleWrapper, this.scheduleWrapper.getNextTimeout());
   }
}
