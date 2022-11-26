package weblogic.management.runtime;

import java.io.Serializable;

public interface Timer extends Serializable {
   String getTimerManagerName();

   long getTimeout();

   long getPeriod();

   boolean isStopped();

   boolean isCancelled();

   long[] getPastExpirationTimes();

   long getExpirationCount();

   String getListenerClassName();
}
