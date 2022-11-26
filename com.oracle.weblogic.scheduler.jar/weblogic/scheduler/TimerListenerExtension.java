package weblogic.scheduler;

import weblogic.timers.TimerListener;

public interface TimerListenerExtension extends TimerListener {
   String getDispatchPolicy();

   boolean isTransactional();

   String getApplicationName();

   String getModuleName();
}
