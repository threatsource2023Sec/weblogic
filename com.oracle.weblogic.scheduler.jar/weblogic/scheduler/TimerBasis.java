package weblogic.scheduler;

import java.util.List;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.timers.TimerListener;
import weblogic.timers.internal.ScheduleExpressionWrapper;

public interface TimerBasis {
   String createTimer(String var1, TimerListener var2, long var3, long var5, AuthenticatedSubject var7) throws TimerException;

   String createCalendarTimer(String var1, TimerListener var2, long var3, ScheduleExpressionWrapper var5, AuthenticatedSubject var6) throws TimerException;

   String createCalendarTimer(String var1, TimerListener var2, long var3, ScheduleExpressionWrapper var5, AuthenticatedSubject var6, String var7) throws TimerException, TimerAlreadyExistsException;

   boolean cancelTimer(String var1) throws NoSuchObjectLocalException, TimerException;

   void advanceIntervalTimer(String var1, TimerListener var2) throws NoSuchObjectLocalException, TimerException;

   void advanceCalendarTimer(String var1, TimerListener var2, ScheduleExpressionWrapper var3, long var4) throws NoSuchObjectLocalException, TimerException;

   TimerState getTimerState(String var1) throws NoSuchObjectLocalException, TimerException;

   List getReadyTimers(int var1) throws TimerException;

   Timer[] getTimers(String var1) throws TimerException;

   Timer[] getTimers(String var1, String var2) throws TimerException;

   Timer getTimerByUserKey(String var1, String var2) throws TimerException;

   void cancelTimers(String var1) throws TimerException;
}
