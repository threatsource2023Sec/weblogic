package commonj.timers;

import java.util.Date;

public interface TimerManager {
   long IMMEDIATE = 0L;
   long INDEFINITE = Long.MAX_VALUE;

   void suspend();

   boolean isSuspending() throws IllegalStateException;

   boolean isSuspended() throws IllegalStateException;

   boolean waitForSuspend(long var1) throws InterruptedException, IllegalStateException, IllegalArgumentException;

   void resume() throws IllegalStateException;

   void stop() throws IllegalStateException;

   boolean isStopped();

   boolean isStopping();

   boolean waitForStop(long var1) throws InterruptedException, IllegalArgumentException;

   Timer schedule(TimerListener var1, Date var2) throws IllegalArgumentException, IllegalStateException;

   Timer schedule(TimerListener var1, long var2) throws IllegalArgumentException, IllegalStateException;

   Timer schedule(TimerListener var1, Date var2, long var3) throws IllegalArgumentException, IllegalStateException;

   Timer schedule(TimerListener var1, long var2, long var4) throws IllegalArgumentException, IllegalStateException;

   Timer scheduleAtFixedRate(TimerListener var1, Date var2, long var3) throws IllegalArgumentException, IllegalStateException;

   Timer scheduleAtFixedRate(TimerListener var1, long var2, long var4) throws IllegalArgumentException, IllegalStateException;
}
