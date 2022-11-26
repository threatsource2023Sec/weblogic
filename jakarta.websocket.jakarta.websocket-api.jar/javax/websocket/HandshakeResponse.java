package javax.websocket;

import java.util.Map;

public interface HandshakeResponse {
   String SEC_WEBSOCKET_ACCEPT = "Sec-WebSocket-Accept";

   Map getHeaders();
}
