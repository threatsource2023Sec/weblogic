package com.sun.faces.push;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;

public class WebsocketEndpoint extends Endpoint {
   static final String PARAM_CHANNEL = "channel";
   public static final String URI_TEMPLATE = "/javax.faces.push/{channel}";
   private static final Logger logger = Logger.getLogger(WebsocketEndpoint.class.getName());
   private static final CloseReason REASON_UNKNOWN_CHANNEL;
   private static final String ERROR_EXCEPTION = "WebsocketEndpoint: An exception occurred during processing web socket request.";

   public void onOpen(Session session, EndpointConfig config) {
      if (WebsocketSessionManager.getInstance().add(session)) {
         session.setMaxIdleTimeout(0L);
      } else {
         try {
            session.close(REASON_UNKNOWN_CHANNEL);
         } catch (IOException var4) {
            this.onError(session, var4);
         }
      }

   }

   public void onError(Session session, Throwable throwable) {
      if (session.isOpen()) {
         session.getUserProperties().put(Throwable.class.getName(), throwable);
      }

   }

   public void onClose(Session session, CloseReason reason) {
      WebsocketSessionManager.getInstance().remove(session, reason);
      Throwable throwable = (Throwable)session.getUserProperties().remove(Throwable.class.getName());
      if (throwable != null && reason.getCloseCode() != CloseCodes.GOING_AWAY) {
         logger.log(Level.SEVERE, "WebsocketEndpoint: An exception occurred during processing web socket request.", throwable);
      }

   }

   static {
      REASON_UNKNOWN_CHANNEL = new CloseReason(CloseCodes.VIOLATED_POLICY, "Unknown channel");
   }
}
