package weblogic.management.runtime;

import java.io.Serializable;

public interface JobRuntimeMBean extends RuntimeMBean {
   String RUNNING = "Running";
   String CANCELLED = "Cancelled";

   Serializable getTimerListener();

   String getDescription();

   String getID();

   long getPeriod();

   long getTimeout();

   long getLastLocalExecutionTime();

   long getLocalExecutionCount();

   void cancel();

   String getState();
}
