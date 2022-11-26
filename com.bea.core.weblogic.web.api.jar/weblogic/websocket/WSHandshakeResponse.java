package weblogic.websocket;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/** @deprecated */
@Deprecated
public class WSHandshakeResponse extends HttpServletResponseWrapper {
   public WSHandshakeResponse(WSHandshakeRequest request, HttpServletResponse response) {
      super(response);
   }

   public void enableSubProtocol(String protocol) {
      this.addHeader("Sec-WebSocket-Protocol", protocol);
   }

   public void enableExtension(String extension) {
      this.addHeader("Sec-WebSocket-Extensions", extension);
   }

   public String[] getEnabledSubProtocols() {
      return WSHandshakeRequest.split("Sec-WebSocket-Protocol", ",");
   }

   public String[] getEnabledExtensions() {
      return WSHandshakeRequest.split("Sec-WebSocket-Extensions", ",");
   }
}
