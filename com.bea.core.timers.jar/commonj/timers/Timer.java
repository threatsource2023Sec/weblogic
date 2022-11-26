package commonj.timers;

public interface Timer {
   boolean cancel();

   TimerListener getTimerListener();

   long getScheduledExecutionTime() throws IllegalStateException;

   long getPeriod();
}
