package weblogic.websocket.tyrus.monitoring;

class MessageStatisticsAggregator implements MessageStatisticsSource {
   private final MessageStatisticsSource[] messageStatisticsSources;

   MessageStatisticsAggregator(MessageStatisticsSource... messageStatisticsSources) {
      this.messageStatisticsSources = messageStatisticsSources;
   }

   public long getMessagesCount() {
      long result = 0L;
      MessageStatisticsSource[] var3 = this.messageStatisticsSources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageStatisticsSource statisticsSource = var3[var5];
         result += statisticsSource.getMessagesCount();
      }

      return result;
   }

   public long getMessagesSize() {
      long result = 0L;
      MessageStatisticsSource[] var3 = this.messageStatisticsSources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageStatisticsSource statisticsSource = var3[var5];
         result += statisticsSource.getMessagesSize();
      }

      return result;
   }

   public long getMinMessageSize() {
      long result = Long.MAX_VALUE;
      MessageStatisticsSource[] var3 = this.messageStatisticsSources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageStatisticsSource statisticsSource = var3[var5];
         result = Math.min(result, statisticsSource.getMinMessageSize());
      }

      return result;
   }

   public long getMaxMessageSize() {
      long result = 0L;
      MessageStatisticsSource[] var3 = this.messageStatisticsSources;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageStatisticsSource statisticsSource = var3[var5];
         result = Math.max(result, statisticsSource.getMaxMessageSize());
      }

      return result;
   }
}
