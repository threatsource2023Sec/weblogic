package weblogic.messaging.kernel;

public interface Statistics {
   long getBytesCurrent();

   long getBytesHigh();

   long getBytesLow();

   long getBytesPending();

   long getBytesReceived();

   int getMessagesCurrent();

   int getMessagesHigh();

   int getMessagesLow();

   int getMessagesPending();

   long getMessagesReceived();

   long getSubscriptionLimitMessagesDeleted();

   Threshold addByteThreshold(long var1, long var3);

   Threshold addMessageThreshold(long var1, long var3);

   void removeThreshold(Threshold var1);
}
