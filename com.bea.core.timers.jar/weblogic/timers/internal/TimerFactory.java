package weblogic.timers.internal;

import weblogic.timers.TimerListener;

public class TimerFactory {
   TimerImpl createTimerImpl(TimerManagerImpl timerManager, TimerListener listener, long timeout, long period) {
      return new TimerImpl(timerManager, listener, timeout, period);
   }

   CalendarTimerImpl createCalendarTimerImpl(TimerManagerImpl timerManager, TimerListener listener, ScheduleExpressionWrapper schedule) {
      return new CalendarTimerImpl(timerManager, listener, schedule);
   }
}
