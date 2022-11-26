package weblogic.scheduler;

import java.io.Serializable;

public interface Timer extends weblogic.timers.Timer {
   Serializable getInfo() throws NoSuchObjectLocalException, TimerException;

   TimerHandle getHandle() throws NoSuchObjectLocalException, TimerException;
}
