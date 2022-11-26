package weblogic.scheduler;

import java.io.Serializable;
import weblogic.timers.TimerListener;

public interface PersistentTimerListener extends TimerListener, Serializable {
   Serializable getInfo();
}
