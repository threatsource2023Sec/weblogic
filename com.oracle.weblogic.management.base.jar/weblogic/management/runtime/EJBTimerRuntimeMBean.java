package weblogic.management.runtime;

public interface EJBTimerRuntimeMBean extends RuntimeMBean {
   long getTimeoutCount();

   long getCancelledTimerCount();

   int getActiveTimerCount();

   int getDisabledTimerCount();

   void activateDisabledTimers();
}
