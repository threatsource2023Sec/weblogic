package weblogic.j2ee.descriptor;

public interface MessageAdapterBean {
   MessageListenerBean[] getMessageListeners();

   MessageListenerBean createMessageListener();

   void destroyMessageListener(MessageListenerBean var1);

   String getId();

   void setId(String var1);
}
