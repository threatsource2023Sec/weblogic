package org.glassfish.tyrus.spi;

import javax.websocket.server.HandshakeRequest;

public abstract class UpgradeRequest implements HandshakeRequest {
   public static final String WEBSOCKET = "websocket";
   public static final String RESPONSE_CODE_MESSAGE = "Switching Protocols";
   public static final String UPGRADE = "Upgrade";
   public static final String CONNECTION = "Connection";
   public static final String HOST = "Host";
   public static final String SEC_WS_ORIGIN_HEADER = "Sec-WebSocket-Origin";
   public static final String ORIGIN_HEADER = "Origin";
   public static final String CLUSTER_CONNECTION_ID_HEADER = "tyrus-cluster-connection-id";
   public static final String SERVER_KEY_HASH = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
   public static final String AUTHORIZATION = "Authorization";
   public static final String ENABLE_TRACING_HEADER = "X-Tyrus-Tracing-Accept";
   public static final String TRACING_THRESHOLD = "X-Tyrus-Tracing-Threshold";

   public abstract String getHeader(String var1);

   public abstract String getRequestUri();

   public abstract boolean isSecure();
}
