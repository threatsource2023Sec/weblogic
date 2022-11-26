package weblogic.jms.dispatcher;

import javax.jms.JMSException;
import weblogic.timers.TimerManager;
import weblogic.work.WorkManager;

public interface DispatcherPartition4rmic {
   String getPartitionId();

   String getPartitionName();

   JMSDispatcher getLocalDispatcher() throws JMSException;

   InvocableManagerDelegate getInvocableManagerDelegate() throws JMSException;

   boolean isPartitionActive();

   WorkManager getFEWorkManager();

   WorkManager getBEWorkManager();

   WorkManager getOneWayWorkManager();

   WorkManager getDefaultWorkManager();

   TimerManager getDefaultTimerManager();

   Object pushComponentInvocationContext();
}
