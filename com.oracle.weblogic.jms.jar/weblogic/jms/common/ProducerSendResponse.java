package weblogic.jms.common;

public interface ProducerSendResponse {
   JMSMessageId getMessageId();

   boolean get90StyleMessageId();

   int getDeliveryMode();

   int getPriority();

   long getTimeToLive();

   long getTimeToDeliver();

   int getRedeliveryLimit();
}
