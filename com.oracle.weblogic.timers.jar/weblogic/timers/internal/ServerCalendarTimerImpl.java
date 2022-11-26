package weblogic.timers.internal;

import weblogic.timers.NakedTimerListener;
import weblogic.timers.TimerListener;

public class ServerCalendarTimerImpl extends CalendarTimerImpl {
   ServerCalendarTimerImpl(TimerManagerImpl timerManager, TimerListener listener, ScheduleExpressionWrapper schedule) {
      super(timerManager, listener, schedule);
      this.initContext(listener);
   }

   void initContext(TimerListener listener) {
      if (!(listener instanceof NakedTimerListener)) {
         this.context = new J2EETimerContextImpl();
      }

   }
}
