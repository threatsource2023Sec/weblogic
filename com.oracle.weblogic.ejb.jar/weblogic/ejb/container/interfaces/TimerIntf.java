package weblogic.ejb.container.interfaces;

import javax.ejb.Timer;

public interface TimerIntf extends Timer {
   boolean exists();

   boolean isAutoCreated();
}
