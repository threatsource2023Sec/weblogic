package org.glassfish.tyrus.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.websocket.Extension;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;

public final class Handshake {
   private static final int RESPONSE_CODE_VALUE = 101;
   private static final String VERSION = "13";
   private List subProtocols = new ArrayList();
   private List extensions = new ArrayList();
   private RequestContext request;
   private UpgradeRequest incomingRequest;
   private ExtendedExtension.ExtensionContext extensionContext;
   private SecKey secKey;

   private Handshake() {
   }

   public static Handshake createClientHandshake(RequestContext webSocketRequest) {
      Handshake handshake = new Handshake();
      handshake.request = webSocketRequest;
      handshake.secKey = new SecKey();
      return handshake;
   }

   public RequestContext getRequest() {
      return this.request;
   }

   public void setSubProtocols(List subProtocols) {
      this.subProtocols = subProtocols;
   }

   public void setExtensions(List extensions) {
      this.extensions = extensions;
   }

   public UpgradeRequest prepareRequest() {
      Map requestHeaders = this.request.getHeaders();
      updateHostAndOrigin(this.request);
      requestHeaders.put("Connection", Collections.singletonList("Upgrade"));
      requestHeaders.put("Upgrade", Collections.singletonList("websocket"));
      requestHeaders.put("Sec-WebSocket-Key", Collections.singletonList(this.secKey.toString()));
      requestHeaders.put("Sec-WebSocket-Version", Collections.singletonList("13"));
      if (!this.subProtocols.isEmpty()) {
         requestHeaders.put("Sec-WebSocket-Protocol", Collections.singletonList(Utils.getHeaderFromList(this.subProtocols, (Utils.Stringifier)null)));
      }

      if (!this.extensions.isEmpty()) {
         requestHeaders.put("Sec-WebSocket-Extensions", Collections.singletonList(Utils.getHeaderFromList(this.extensions, new Utils.Stringifier() {
            String toString(Extension extension) {
               return TyrusExtension.toString(extension);
            }
         })));
      }

      return this.request;
   }

   public void validateServerResponse(UpgradeResponse response) throws HandshakeException {
      if (101 != response.getStatus()) {
         throw new HandshakeException(response.getStatus(), LocalizationMessages.INVALID_RESPONSE_CODE(101, response.getStatus()));
      } else {
         checkForHeader(response.getFirstHeaderValue("Upgrade"), "Upgrade", "websocket");
         checkForHeader(response.getFirstHeaderValue("Connection"), "Connection", "Upgrade");
         this.secKey.validateServerKey(response.getFirstHeaderValue("Sec-WebSocket-Accept"));
      }
   }

   public static void updateHostAndOrigin(UpgradeRequest upgradeRequest) {
      URI requestUri = upgradeRequest.getRequestURI();
      String host = requestUri.getHost();
      int port = requestUri.getPort();
      if (upgradeRequest.isSecure()) {
         if (port != 443 && port != -1) {
            host = host + ":" + port;
         }
      } else if (port != 80 && port != -1) {
         host = host + ":" + port;
      }

      Map requestHeaders = upgradeRequest.getHeaders();
      requestHeaders.put("Host", Collections.singletonList(host));
      requestHeaders.put("Origin", Collections.singletonList("http://" + host));
   }

   static Handshake createServerHandshake(UpgradeRequest request, ExtendedExtension.ExtensionContext extensionContext) throws HandshakeException {
      Handshake handshake = new Handshake();
      handshake.incomingRequest = request;
      handshake.extensionContext = extensionContext;
      checkForHeader(request.getHeader("Upgrade"), "Upgrade", "WebSocket");
      checkForHeader(request.getHeader("Connection"), "Connection", "Upgrade");
      String protocolHeader = request.getHeader("Sec-WebSocket-Protocol");
      handshake.subProtocols = protocolHeader == null ? Collections.emptyList() : Arrays.asList(protocolHeader.split(","));
      if (request.getHeader("Host") == null) {
         throw new HandshakeException(LocalizationMessages.HEADERS_MISSING());
      } else {
         List value = (List)request.getHeaders().get("Sec-WebSocket-Extensions");
         if (value != null) {
            handshake.extensions = TyrusExtension.fromHeaders(value);
         }

         handshake.secKey = SecKey.generateServerKey(new SecKey(request.getHeader("Sec-WebSocket-Key")));
         return handshake;
      }
   }

   private static void checkForHeader(String currentValue, String header, String validValue) throws HandshakeException {
      validate(header, validValue, currentValue);
   }

   private static void validate(String header, String validValue, String value) throws HandshakeException {
      if (header.equalsIgnoreCase("Connection")) {
         if (value == null || !value.toLowerCase().contains(validValue.toLowerCase())) {
            throw new HandshakeException(LocalizationMessages.INVALID_HEADER(header, value));
         }
      } else if (!validValue.equalsIgnoreCase(value)) {
         throw new HandshakeException(LocalizationMessages.INVALID_HEADER(header, value));
      }

   }

   List respond(UpgradeRequest request, UpgradeResponse response, TyrusEndpointWrapper endpointWrapper) {
      response.setStatus(101);
      response.getHeaders().put("Upgrade", Arrays.asList("websocket"));
      response.getHeaders().put("Connection", Arrays.asList("Upgrade"));
      response.setReasonPhrase("Switching Protocols");
      response.getHeaders().put("Sec-WebSocket-Accept", Arrays.asList(this.secKey.getSecKey()));
      List protocols = (List)request.getHeaders().get("Sec-WebSocket-Protocol");
      List extensions = TyrusExtension.fromString((List)request.getHeaders().get("Sec-WebSocket-Extensions"));
      if (this.subProtocols != null && !this.subProtocols.isEmpty()) {
         String protocol = endpointWrapper.getNegotiatedProtocol(protocols);
         if (protocol != null && !protocol.isEmpty()) {
            response.getHeaders().put("Sec-WebSocket-Protocol", Arrays.asList(protocol));
         }
      }

      List negotiatedExtensions = endpointWrapper.getNegotiatedExtensions(extensions);
      if (!negotiatedExtensions.isEmpty()) {
         response.getHeaders().put("Sec-WebSocket-Extensions", Utils.getStringList(negotiatedExtensions, new Utils.Stringifier() {
            String toString(final Extension extension) {
               return extension instanceof ExtendedExtension ? TyrusExtension.toString(new Extension() {
                  public String getName() {
                     return extension.getName();
                  }

                  public List getParameters() {
                     return ((ExtendedExtension)extension).onExtensionNegotiation(Handshake.this.extensionContext, (List)null);
                  }
               }) : TyrusExtension.toString(extension);
            }
         }));
      }

      endpointWrapper.onHandShakeResponse(this.incomingRequest, response);
      return negotiatedExtensions;
   }
}
