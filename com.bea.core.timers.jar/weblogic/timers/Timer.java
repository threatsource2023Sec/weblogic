package weblogic.timers;

public interface Timer {
   long getTimeout();

   long getPeriod();

   TimerListener getListener();

   String getListenerClassName();

   boolean cancel();

   boolean isStopped();

   boolean isCancelled();

   boolean isCalendarTimer();

   ScheduleExpression getSchedule();
}
