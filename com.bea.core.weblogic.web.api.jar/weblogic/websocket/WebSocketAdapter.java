package weblogic.websocket;

/** @deprecated */
@Deprecated
public class WebSocketAdapter implements WebSocketListener {
   protected WebSocketContext context;

   protected WebSocketContext getWebSocketContext() {
      return this.context;
   }

   public void init(WebSocketContext context) {
      this.context = context;
   }

   public void destroy() {
   }

   public boolean accept(WSHandshakeRequest request, WSHandshakeResponse response) {
      return true;
   }

   public void onOpen(WebSocketConnection connection) {
   }

   public void onMessage(WebSocketConnection connection, String payload) {
   }

   public void onMessage(WebSocketConnection connection, byte[] payload) {
   }

   public void onFragment(WebSocketConnection connection, boolean last, String payload) {
   }

   public void onFragment(WebSocketConnection connection, boolean last, byte[] payload) {
   }

   public void onPing(WebSocketConnection connection, byte[] payload) {
   }

   public void onPong(WebSocketConnection connection, byte[] payload) {
   }

   public void onTimeout(WebSocketConnection connection) {
   }

   public void onError(WebSocketConnection connection, Throwable error) {
   }

   public void onClose(WebSocketConnection connection, ClosingMessage msg) {
   }
}
