package com.bea.httppubsub;

import java.io.Serializable;

public interface BayeuxMessage extends Serializable {
   Client getClient();

   TYPE getType();

   String getChannel();

   String toJSONRequestString();

   String toJSONResponseString();

   public static enum TYPE {
      HANDSHAKE,
      CONNECT,
      RECONNECT,
      DISCONNECT,
      SUBSCRIBE,
      UNSUBSCRIBE,
      PUBLISH;
   }
}
