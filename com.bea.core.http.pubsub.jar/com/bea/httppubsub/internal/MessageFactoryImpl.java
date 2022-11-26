package com.bea.httppubsub.internal;

import com.bea.httppubsub.EventMessage;
import com.bea.httppubsub.LocalClient;
import com.bea.httppubsub.MessageFactory;
import com.bea.httppubsub.bayeux.messages.EventMessageImpl;

public class MessageFactoryImpl implements MessageFactory {
   public EventMessage createEventMessage(LocalClient client, String channel, String payLoad) {
      if (client != null && payLoad != null && channel != null) {
         EventMessageImpl message = new EventMessageImpl();
         message.setChannel(channel);
         message.setPayLoad(payLoad);
         message.setClient(client);
         message.setClientId(client.getId());
         message.setSuccessful(true);
         return message;
      } else {
         throw new NullPointerException("Parameter client, channel or payLoad is null");
      }
   }
}
