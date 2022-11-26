package weblogic.scheduler;

import java.util.Collection;
import java.util.Date;

public interface TimerService {
   Timer createTimer(PersistentTimerListener var1, long var2) throws IllegalArgumentException, TimerException;

   Timer createTimer(PersistentTimerListener var1, long var2, long var4) throws IllegalArgumentException, TimerException;

   Timer createTimer(PersistentTimerListener var1, Date var2) throws IllegalArgumentException, TimerException;

   Timer createTimer(PersistentTimerListener var1, Date var2, long var3) throws IllegalArgumentException, TimerException;

   Collection getTimers();
}
