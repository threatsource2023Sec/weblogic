package weblogic.websocket;

/** @deprecated */
@Deprecated
public interface WebSocketListener {
   void init(WebSocketContext var1);

   void destroy();

   boolean accept(WSHandshakeRequest var1, WSHandshakeResponse var2);

   void onOpen(WebSocketConnection var1);

   void onMessage(WebSocketConnection var1, String var2);

   void onMessage(WebSocketConnection var1, byte[] var2);

   void onFragment(WebSocketConnection var1, boolean var2, String var3);

   void onFragment(WebSocketConnection var1, boolean var2, byte[] var3);

   void onPing(WebSocketConnection var1, byte[] var2);

   void onPong(WebSocketConnection var1, byte[] var2);

   void onTimeout(WebSocketConnection var1);

   void onError(WebSocketConnection var1, Throwable var2);

   void onClose(WebSocketConnection var1, ClosingMessage var2);
}
