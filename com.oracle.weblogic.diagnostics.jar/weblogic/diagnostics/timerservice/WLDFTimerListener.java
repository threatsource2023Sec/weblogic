package weblogic.diagnostics.timerservice;

import com.oracle.weblogic.diagnostics.timerservice.TimerListener;
import weblogic.timers.ScheduleExpression;

public interface WLDFTimerListener extends TimerListener {
   boolean useCalendarSchedule();

   ScheduleExpression getSchedule();
}
