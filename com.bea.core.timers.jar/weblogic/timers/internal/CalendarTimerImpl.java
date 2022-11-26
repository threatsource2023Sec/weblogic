package weblogic.timers.internal;

import weblogic.timers.ScheduleExpression;
import weblogic.timers.TimerListener;

public class CalendarTimerImpl extends TimerImpl {
   private ScheduleExpressionWrapper scheduleWrapper;

   CalendarTimerImpl(TimerManagerImpl timerManager, TimerListener listener, ScheduleExpressionWrapper schedule) {
      super(timerManager, listener);
      this.scheduleWrapper = schedule;
      this.timeout = this.scheduleWrapper.getFirstTimeout();
   }

   public ScheduleExpression getSchedule() {
      return this.scheduleWrapper.getSchedule();
   }

   public boolean isCalendarTimer() {
      return true;
   }

   public long getPeriod() {
      return 0L;
   }

   boolean incrementTimeout() {
      this.timeout = this.scheduleWrapper.getNextTimeout();
      return this.timeout != -1L;
   }
}
