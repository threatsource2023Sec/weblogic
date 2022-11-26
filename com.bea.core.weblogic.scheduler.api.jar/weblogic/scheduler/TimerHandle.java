package weblogic.scheduler;

import java.io.Serializable;

public interface TimerHandle extends Serializable {
   Timer getTimer() throws NoSuchObjectLocalException, TimerException;
}
