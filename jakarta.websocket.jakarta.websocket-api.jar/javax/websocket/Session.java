package javax.websocket;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Session extends Closeable {
   WebSocketContainer getContainer();

   void addMessageHandler(MessageHandler var1) throws IllegalStateException;

   void addMessageHandler(Class var1, MessageHandler.Whole var2);

   void addMessageHandler(Class var1, MessageHandler.Partial var2);

   Set getMessageHandlers();

   void removeMessageHandler(MessageHandler var1);

   String getProtocolVersion();

   String getNegotiatedSubprotocol();

   List getNegotiatedExtensions();

   boolean isSecure();

   boolean isOpen();

   long getMaxIdleTimeout();

   void setMaxIdleTimeout(long var1);

   void setMaxBinaryMessageBufferSize(int var1);

   int getMaxBinaryMessageBufferSize();

   void setMaxTextMessageBufferSize(int var1);

   int getMaxTextMessageBufferSize();

   RemoteEndpoint.Async getAsyncRemote();

   RemoteEndpoint.Basic getBasicRemote();

   String getId();

   void close() throws IOException;

   void close(CloseReason var1) throws IOException;

   URI getRequestURI();

   Map getRequestParameterMap();

   String getQueryString();

   Map getPathParameters();

   Map getUserProperties();

   Principal getUserPrincipal();

   Set getOpenSessions();
}
