package weblogic.management.runtime;

import javax.jms.JMSException;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;

public interface JMSServerRuntimeMBean extends JMSMessageCursorRuntimeMBean, HealthFeedback {
   HealthState getHealthState();

   JMSSessionPoolRuntimeMBean[] getSessionPoolRuntimes();

   long getSessionPoolsCurrentCount();

   long getSessionPoolsHighCount();

   long getSessionPoolsTotalCount();

   JMSDestinationRuntimeMBean[] getDestinations();

   long getDestinationsCurrentCount();

   long getDestinationsHighCount();

   long getDestinationsTotalCount();

   long getMessagesCurrentCount();

   long getMessagesPendingCount();

   long getMessagesHighCount();

   long getMessagesReceivedCount();

   long getMessagesThresholdTime();

   long getBytesCurrentCount();

   long getBytesPendingCount();

   long getBytesHighCount();

   long getBytesReceivedCount();

   long getBytesThresholdTime();

   void pauseProduction() throws JMSException;

   boolean isProductionPaused();

   String getProductionPausedState();

   void resumeProduction() throws JMSException;

   void pauseInsertion() throws JMSException;

   boolean isInsertionPaused();

   String getInsertionPausedState();

   void resumeInsertion() throws JMSException;

   void pauseConsumption() throws JMSException;

   boolean isConsumptionPaused();

   String getConsumptionPausedState();

   void resumeConsumption() throws JMSException;

   String[] getTransactions();

   String[] getPendingTransactions();

   Integer getTransactionStatus(String var1);

   String getMessages(String var1, Integer var2) throws ManagementException;

   Void forceCommit(String var1) throws ManagementException;

   Void forceRollback(String var1) throws ManagementException;

   int getMessagesPageableCurrentCount();

   long getBytesPageableCurrentCount();

   int getMessagesPagedOutTotalCount();

   int getMessagesPagedInTotalCount();

   long getBytesPagedOutTotalCount();

   long getBytesPagedInTotalCount();

   long getPagingAllocatedWindowBufferBytes();

   long getPagingAllocatedIoBufferBytes();

   long getPagingPhysicalWriteCount();

   LogRuntimeMBean getLogRuntime();

   void setLogRuntime(LogRuntimeMBean var1);
}
