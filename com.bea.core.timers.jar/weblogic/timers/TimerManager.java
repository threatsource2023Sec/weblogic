package weblogic.timers;

import java.util.Date;

public interface TimerManager {
   Timer schedule(TimerListener var1, long var2);

   Timer schedule(TimerListener var1, Date var2);

   Timer schedule(TimerListener var1, long var2, long var4);

   Timer schedule(TimerListener var1, Date var2, long var3);

   Timer schedule(TimerListener var1, ScheduleExpression var2);

   Timer scheduleAtFixedRate(TimerListener var1, Date var2, long var3);

   Timer scheduleAtFixedRate(TimerListener var1, long var2, long var4);

   void resume();

   void suspend();

   void stop();

   boolean waitForStop(long var1) throws InterruptedException;

   boolean isStopping();

   boolean isStopped();

   boolean waitForSuspend(long var1) throws InterruptedException;

   boolean isSuspending();

   boolean isSuspended();
}
