package weblogic.management.runtime;

import javax.jms.Destination;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.management.openmbean.CompositeData;

public interface JMSDestinationRuntimeMBean extends JMSMessageManagementRuntimeMBean {
   Destination getDestination();

   CompositeData getDestinationInfo();

   void createDurableSubscriber(String var1, String var2, String var3, boolean var4) throws InvalidSelectorException, JMSException;

   void destroyJMSDurableSubscriberRuntime(JMSDurableSubscriberRuntimeMBean var1) throws InvalidSelectorException, JMSException;

   JMSDurableSubscriberRuntimeMBean[] getJMSDurableSubscriberRuntimes();

   JMSDurableSubscriberRuntimeMBean[] getDurableSubscribers();

   long getConsumersCurrentCount();

   long getConsumersHighCount();

   long getConsumersTotalCount();

   long getMessagesCurrentCount();

   long getMessagesPendingCount();

   long getMessagesHighCount();

   long getMessagesReceivedCount();

   long getMessagesThresholdTime();

   long getSubscriptionMessagesLimit();

   long getBytesCurrentCount();

   long getBytesPendingCount();

   long getBytesHighCount();

   long getBytesReceivedCount();

   long getBytesThresholdTime();

   String getDestinationType();

   /** @deprecated */
   @Deprecated
   void pause();

   /** @deprecated */
   @Deprecated
   void resume();

   String getState();

   /** @deprecated */
   @Deprecated
   boolean isPaused() throws JMSException;

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

   void lowMemory() throws JMSException;

   void normalMemory() throws JMSException;

   void suspendMessageLogging() throws JMSException;

   void resumeMessageLogging() throws JMSException;

   boolean isMessageLogging() throws JMSException;

   void mydelete() throws JMSException;
}
