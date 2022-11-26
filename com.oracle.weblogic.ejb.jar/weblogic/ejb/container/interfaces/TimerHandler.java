package weblogic.ejb.container.interfaces;

import weblogic.ejb.container.timer.ClusteredTimerImpl;

public interface TimerHandler {
   void executeTimer(ClusteredTimerImpl var1);
}
