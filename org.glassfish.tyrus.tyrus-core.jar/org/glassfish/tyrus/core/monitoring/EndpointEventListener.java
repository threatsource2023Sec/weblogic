package org.glassfish.tyrus.core.monitoring;

import org.glassfish.tyrus.core.Beta;

@Beta
public interface EndpointEventListener {
   EndpointEventListener NO_OP = new EndpointEventListener() {
      public MessageEventListener onSessionOpened(String sessionId) {
         return MessageEventListener.NO_OP;
      }

      public void onSessionClosed(String sessionId) {
      }

      public void onError(String sessionId, Throwable t) {
      }
   };

   MessageEventListener onSessionOpened(String var1);

   void onSessionClosed(String var1);

   void onError(String var1, Throwable var2);
}
