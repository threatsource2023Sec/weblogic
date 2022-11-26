package weblogic.scheduler.ejb;

import weblogic.scheduler.TimerAlreadyExistsException;
import weblogic.timers.ScheduleExpression;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;

public interface ClusteredTimerManager extends TimerManager {
   Timer[] getTimers();

   Timer[] getTimers(String var1);

   Timer getTimerByUserKey(String var1);

   Timer schedule(TimerListener var1, ScheduleExpression var2, String var3) throws TimerAlreadyExistsException;

   void cancelAllTimers();
}
