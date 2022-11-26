package weblogic.websocket.tyrus.monitoring;

interface MessageStatisticsSource {
   long getMessagesCount();

   long getMessagesSize();

   long getMinMessageSize();

   long getMaxMessageSize();
}
