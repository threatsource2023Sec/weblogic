package weblogic.websocket;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/** @deprecated */
@Deprecated
public class WSHandshakeRequest extends HttpServletRequestWrapper {
   private static final String ORIGIN_HEADER = "Origin";
   static final String SEC_WS_PROTOCOL = "Sec-WebSocket-Protocol";
   static final String SEC_WS_EXTENSIONS = "Sec-WebSocket-Extensions";
   static final String SEC_WS_VERSION = "Sec-WebSocket-Version";
   private String origin;
   private String resourcePath;
   private String[] subProtocols;
   private String[] extensions;
   private int version = -1;

   public WSHandshakeRequest(WebSocketContext context, HttpServletRequest request) {
      super(request);
      if (!"GET".equals(request.getMethod())) {
         throw new HandshakeException("Invalid upgrade request");
      } else {
         String upgradeHeader = request.getHeader("Upgrade");
         String[] connectionHeader = split(request.getHeader("Connection"), ",");
         if (upgradeHeader != null && connectionHeader != null) {
            boolean found = false;
            String[] var6 = connectionHeader;
            int var7 = connectionHeader.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String connection = var6[var8];
               if ("Upgrade".equalsIgnoreCase(connection)) {
                  found = true;
               }
            }

            if (!found) {
               throw new HandshakeException("Invalid upgrade request");
            } else if (!"websocket".equalsIgnoreCase(upgradeHeader)) {
               throw new HandshakeException("Invalid upgrade request");
            } else {
               String versionHeader = request.getHeader("Sec-WebSocket-Version");

               try {
                  if (versionHeader != null) {
                     this.version = Integer.parseInt(versionHeader);
                  }

                  if (!context.getSupportedVersions().contains(this.version)) {
                     throw new HandshakeException(2, "Unsupported websocket protocol version " + this.version);
                  }
               } catch (NumberFormatException var10) {
                  throw new HandshakeException("Handshake header Sec-WebSocket-Version: " + versionHeader + " can't be parsed to integer");
               }

               this.resourcePath = request.getRequestURI();
               this.origin = request.getHeader("Origin");
               String hostHeader = request.getHeader("Host");
               if (hostHeader == null) {
                  throw new HandshakeException("Missing \"Host\" header for websocket handshake");
               } else {
                  this.subProtocols = split(request.getHeader("Sec-WebSocket-Protocol"), ",");
                  this.extensions = split(request.getHeader("Sec-WebSocket-Extensions"), ",");
               }
            }
         } else {
            throw new HandshakeException("Invalid upgrade request");
         }
      }
   }

   public String getOrigin() {
      return this.origin;
   }

   public String getResourcePath() {
      return this.resourcePath;
   }

   public String[] getSubProtocols() {
      return this.subProtocols;
   }

   public String[] getExtensions() {
      return this.extensions;
   }

   public int getVersion() {
      return this.version;
   }

   static String[] split(String header, String delimiter) {
      if (header == null) {
         return null;
      } else {
         String[] values = header.split(delimiter);

         for(int i = 0; i < values.length; ++i) {
            values[i] = values[i].trim();
         }

         return values;
      }
   }
}
