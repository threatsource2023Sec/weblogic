package javax.websocket.server;

import java.net.URI;
import java.security.Principal;
import java.util.Map;

public interface HandshakeRequest {
   String SEC_WEBSOCKET_KEY = "Sec-WebSocket-Key";
   String SEC_WEBSOCKET_PROTOCOL = "Sec-WebSocket-Protocol";
   String SEC_WEBSOCKET_VERSION = "Sec-WebSocket-Version";
   String SEC_WEBSOCKET_EXTENSIONS = "Sec-WebSocket-Extensions";

   Map getHeaders();

   Principal getUserPrincipal();

   URI getRequestURI();

   boolean isUserInRole(String var1);

   Object getHttpSession();

   Map getParameterMap();

   String getQueryString();
}
