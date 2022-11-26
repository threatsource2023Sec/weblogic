package weblogic.jms.common;

public interface MessageStatisticsLogger {
   void logMessagesThresholdHigh();

   void logMessagesThresholdLow();

   void logBytesThresholdHigh();

   void logBytesThresholdLow();
}
