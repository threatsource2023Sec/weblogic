package weblogic.management.runtime;

public interface SAFStatisticsCommonMBean extends RuntimeMBean {
   long getMessagesCurrentCount();

   long getMessagesPendingCount();

   long getMessagesHighCount();

   long getMessagesReceivedCount();

   long getMessagesThresholdTime();

   long getBytesPendingCount();

   long getBytesCurrentCount();

   long getBytesHighCount();

   long getBytesReceivedCount();

   long getBytesThresholdTime();

   long getFailedMessagesTotal();
}
