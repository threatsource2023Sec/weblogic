package weblogic.timers.internal;

import weblogic.invocation.spi.ComponentRequest;
import weblogic.timers.TimerListener;

public class ServerTimerFactory extends TimerFactory {
   TimerImpl createTimerImpl(TimerManagerImpl timerManager, TimerListener listener, long timeout, long period) {
      return (TimerImpl)(listener instanceof ComponentRequest ? new ComponentRequestTimerImpl(timerManager, listener, timeout, period) : new ServerTimerImpl(timerManager, listener, timeout, period));
   }

   CalendarTimerImpl createCalendarTimerImpl(TimerManagerImpl timerManager, TimerListener listener, ScheduleExpressionWrapper schedule) {
      return (CalendarTimerImpl)(listener instanceof ComponentRequest ? new ComponentRequestCalendarTimerImpl(timerManager, listener, schedule) : new ServerCalendarTimerImpl(timerManager, listener, schedule));
   }
}
