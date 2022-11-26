package weblogic.management.runtime;

public interface JMSSessionRuntimeMBean extends RuntimeMBean {
   JMSConsumerRuntimeMBean[] getConsumers();

   long getConsumersCurrentCount();

   long getConsumersHighCount();

   long getConsumersTotalCount();

   JMSProducerRuntimeMBean[] getProducers();

   long getProducersCurrentCount();

   long getProducersHighCount();

   long getProducersTotalCount();

   boolean isTransacted();

   String getAcknowledgeMode();

   long getMessagesPendingCount();

   long getMessagesSentCount();

   long getMessagesReceivedCount();

   long getBytesPendingCount();

   long getBytesSentCount();

   long getBytesReceivedCount();
}
