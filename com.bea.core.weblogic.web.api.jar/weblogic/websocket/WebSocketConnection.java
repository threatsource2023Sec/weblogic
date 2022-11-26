package weblogic.websocket;

import java.io.IOException;
import java.security.Principal;

/** @deprecated */
@Deprecated
public interface WebSocketConnection {
   void send(String var1) throws IOException, IllegalStateException;

   void send(byte[] var1) throws IOException, IllegalStateException;

   void sendPing(byte[] var1) throws IOException, IllegalStateException;

   void sendPong(byte[] var1) throws IOException, IllegalStateException;

   void stream(boolean var1, String var2) throws IOException, IllegalStateException;

   void stream(boolean var1, byte[] var2, int var3, int var4) throws IOException, IllegalStateException;

   void close(int var1) throws IOException;

   void close(int var1, String var2) throws IOException;

   boolean isOpen();

   boolean isSecure();

   String getRemoteAddress();

   int getRemotePort();

   String getRemoteUser();

   String getRequestURI();

   Principal getUserPrincipal();

   WebSocketContext getWebSocketContext();
}
