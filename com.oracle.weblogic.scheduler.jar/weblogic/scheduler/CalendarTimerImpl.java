package weblogic.scheduler;

public class CalendarTimerImpl extends TimerImpl {
   CalendarTimerImpl(String id, TimerBasis timerBasis) {
      super(id, timerBasis);
   }

   public boolean isCalendarTimer() {
      return true;
   }
}
