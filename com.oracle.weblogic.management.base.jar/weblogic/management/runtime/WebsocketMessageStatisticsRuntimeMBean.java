package weblogic.management.runtime;

public interface WebsocketMessageStatisticsRuntimeMBean extends RuntimeMBean {
   long getSentMessagesCount();

   long getMinimalSentMessageSize();

   long getMaximalSentMessageSize();

   long getAverageSentMessageSize();

   long getSentMessagesCountPerSecond();

   long getReceivedMessagesCount();

   long getMinimalReceivedMessageSize();

   long getMaximalReceivedMessageSize();

   long getAverageReceivedMessageSize();

   long getReceivedMessagesCountPerSecond();
}
