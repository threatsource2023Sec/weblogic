package weblogic.websocket.internal;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.servlet.internal.MuxableSocketHTTP;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.websocket.HandshakeException;
import weblogic.websocket.WSHandshakeRequest;
import weblogic.websocket.WSHandshakeResponse;
import weblogic.websocket.WebSocketListener;

public class UpgradeRequestProcessor {
   private static final String SEC_WS_ACCEPT = "Sec-WebSocket-Accept";
   private static final String SEC_WS_KEY = "Sec-WebSocket-Key";
   private static final String SEC_WS_VERSION = "Sec-WebSocket-Version";
   private static final String SERVER_KEY_HASH = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
   private WebSocketContextImpl wsContext;

   public UpgradeRequestProcessor(WebSocketContextImpl ctx) {
      this.wsContext = ctx;
   }

   public void handle(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
      if (!rsp.isCommitted()) {
         ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
         ServletResponseImpl rspi = reqi.getResponse();
         if (WebSocketDebugLogger.isEnabled()) {
            WebSocketDebugLogger.debug(reqi.toString());
         }

         if (!reqi.getInputHelper().getRequestParser().isProtocolVersion_1_1()) {
            rspi.sendError(400, "WebSocket upgrade request must be HTTP/1.1 request");
            rspi.send();
         } else if (this.wsContext.hasMaxConnections()) {
            rspi.sendError(503);
            rspi.send();
         } else {
            WSHandshakeRequest handshakeReq = null;
            WSHandshakeResponse handshakeRes = null;

            try {
               handshakeReq = new WSHandshakeRequest(this.wsContext, req);
               handshakeRes = new WSHandshakeResponse(handshakeReq, rsp);
            } catch (HandshakeException var11) {
               if (var11.getCode() == 2) {
                  Iterator var8 = this.wsContext.getSupportedVersions().iterator();

                  while(var8.hasNext()) {
                     int version = (Integer)var8.next();
                     rspi.addIntHeader("Sec-WebSocket-Version", version);
                  }
               }

               rspi.sendError(400);
               rspi.send();
               return;
            }

            MuxableSocketHTTP httpSocket = (MuxableSocketHTTP)reqi.getConnection().getConnectionHandler().getRawConnection();
            MuxableWebSocket webSocket = new MuxableWebSocket(httpSocket, this.wsContext, req);
            WebSocketListener listener = this.wsContext.getWebSocketListener();
            if (!listener.accept(handshakeReq, handshakeRes)) {
               rspi.sendError(400, "WebSocket handshake can't be accepted by application");
               rspi.send();
               if (WebSocketDebugLogger.isEnabled()) {
                  WebSocketDebugLogger.debug(rspi.toString());
               }

            } else {
               try {
                  webSocket.upgrade(httpSocket);
                  this.completeHandshake(handshakeReq, handshakeRes);
                  if (WebSocketDebugLogger.isEnabled()) {
                     WebSocketDebugLogger.debug(rspi.toString());
                  }

                  webSocket.setAuthenticatedUser(this.wsContext.getAuthenticatedUser(req));
               } catch (IOException var12) {
                  rspi.sendError(500, "Can't upgrade http request to web socket, error is: " + var12.getMessage());
                  rspi.send();
                  if (WebSocketDebugLogger.isEnabled()) {
                     WebSocketDebugLogger.debug(rspi.toString());
                  }

                  return;
               }

               this.wsContext.addConnection(webSocket);
               PrivilegedCallbacks.onOpen(listener, webSocket);
               webSocket.registerForReadEvent();
            }
         }
      }
   }

   private void completeHandshake(WSHandshakeRequest req, WSHandshakeResponse res) throws IOException {
      if (!res.isCommitted()) {
         res.setStatus(101);
         res.setHeader("Upgrade", "webSocket");
         res.addHeader("Connection", "Upgrade");
         res.setHeader("Sec-WebSocket-Accept", this.generateServerKey(req.getHeader("Sec-WebSocket-Key")));
         res.flushBuffer();
      }
   }

   private String generateServerKey(String clientKey) {
      String key = clientKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

      try {
         MessageDigest instance = MessageDigest.getInstance("SHA-1");
         instance.update(key.getBytes());
         byte[] digest = instance.digest();
         return (new BASE64Encoder()).encodeBuffer(digest);
      } catch (NoSuchAlgorithmException var5) {
         var5.printStackTrace();
         return null;
      }
   }
}
