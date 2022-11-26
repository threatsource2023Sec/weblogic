package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;

public interface MessageDrivenEJBRuntimeMBean extends EJBRuntimeMBean, HealthFeedback {
   HealthState getHealthState();

   EJBPoolRuntimeMBean getPoolRuntime();

   EJBTimerRuntimeMBean getTimerRuntime();

   boolean isJMSConnectionAlive();

   String getConnectionStatus();

   String getDestination();

   String getJmsClientID();

   String getMDBStatus();

   long getProcessedMessageCount();

   int getSuspendCount();

   Throwable getLastException();

   String getLastExceptionAsString();

   String getApplicationName();

   boolean suspend() throws ManagementException;

   void scheduleSuspend() throws ManagementException;

   boolean resume() throws ManagementException;

   void scheduleResume() throws ManagementException;
}
