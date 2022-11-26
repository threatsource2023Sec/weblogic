package weblogic.protocol;

public interface MessageReceiverStatistics {
   long getBytesReceivedCount();

   long getMessagesReceivedCount();

   long getConnectTime();
}
