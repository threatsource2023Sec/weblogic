package weblogic.websocket.tyrus.monitoring;

import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WebsocketMessageStatisticsRuntimeMBean;

class WebsocketMessageStatisticsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WebsocketMessageStatisticsRuntimeMBean {
   private final MessageStatisticsSource sentMessageStatistics;
   private final MessageStatisticsSource receivedMessageStatistics;
   private final long monitoringStart;

   WebsocketMessageStatisticsRuntimeMBeanImpl(String name, RuntimeMBean parentMBean, MessageStatisticsSource sentMessageStatistics, MessageStatisticsSource receivedMessageStatistics) throws ManagementException {
      super(name, parentMBean);
      this.sentMessageStatistics = sentMessageStatistics;
      this.receivedMessageStatistics = receivedMessageStatistics;
      this.monitoringStart = System.currentTimeMillis();
   }

   public long getSentMessagesCount() {
      return this.sentMessageStatistics.getMessagesCount();
   }

   public long getMinimalSentMessageSize() {
      return this.sentMessageStatistics.getMinMessageSize();
   }

   public long getMaximalSentMessageSize() {
      return this.sentMessageStatistics.getMaxMessageSize();
   }

   public long getAverageSentMessageSize() {
      return this.sentMessageStatistics.getMessagesCount() == 0L ? 0L : this.sentMessageStatistics.getMessagesSize() / this.sentMessageStatistics.getMessagesCount();
   }

   public long getSentMessagesCountPerSecond() {
      long time = this.getTimeSinceBeginningInSeconds();
      return time == 0L ? 0L : this.getSentMessagesCount() / time;
   }

   public long getReceivedMessagesCount() {
      return this.receivedMessageStatistics.getMessagesCount();
   }

   public long getMinimalReceivedMessageSize() {
      return this.receivedMessageStatistics.getMinMessageSize();
   }

   public long getMaximalReceivedMessageSize() {
      return this.receivedMessageStatistics.getMaxMessageSize();
   }

   public long getAverageReceivedMessageSize() {
      return this.receivedMessageStatistics.getMessagesCount() == 0L ? 0L : this.receivedMessageStatistics.getMessagesSize() / this.receivedMessageStatistics.getMessagesCount();
   }

   public long getReceivedMessagesCountPerSecond() {
      long time = this.getTimeSinceBeginningInSeconds();
      return time == 0L ? 0L : this.getReceivedMessagesCount() / time;
   }

   private long getTimeSinceBeginningInSeconds() {
      long time = System.currentTimeMillis() - this.monitoringStart;
      return time / 1000L;
   }
}
