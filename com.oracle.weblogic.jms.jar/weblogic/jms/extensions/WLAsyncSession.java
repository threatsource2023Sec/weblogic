package weblogic.jms.extensions;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import weblogic.messaging.dispatcher.CompletionListener;

public interface WLAsyncSession extends WLSession {
   void acknowledgeAsync(WLAcknowledgeInfo var1, CompletionListener var2);

   void sendAsync(MessageProducer var1, Message var2, CompletionListener var3);

   void sendAsync(WLMessageProducer var1, Message var2, int var3, int var4, long var5, CompletionListener var7);

   void sendAsync(WLMessageProducer var1, Destination var2, Message var3, CompletionListener var4);

   void sendAsync(WLMessageProducer var1, Destination var2, Message var3, int var4, int var5, long var6, CompletionListener var8);

   void receiveAsync(MessageConsumer var1, CompletionListener var2);

   void receiveAsync(MessageConsumer var1, long var2, CompletionListener var4);

   void receiveNoWaitAsync(MessageConsumer var1, CompletionListener var2);
}
