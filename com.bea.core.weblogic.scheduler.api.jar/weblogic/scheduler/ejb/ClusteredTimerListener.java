package weblogic.scheduler.ejb;

import java.io.Serializable;
import weblogic.timers.TimerListener;

public interface ClusteredTimerListener extends TimerListener, Serializable {
   String getGroupName();

   boolean isTransactional();

   int getTransactionTimeoutSeconds();

   String getEjbName();

   String getCallbackMethodSignature();
}
