package org.glassfish.tyrus.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.client.auth.AuthConfig;
import org.glassfish.tyrus.client.auth.AuthenticationException;
import org.glassfish.tyrus.client.auth.Authenticator;
import org.glassfish.tyrus.client.auth.Credentials;
import org.glassfish.tyrus.core.DebugContext;
import org.glassfish.tyrus.core.Handshake;
import org.glassfish.tyrus.core.HandshakeException;
import org.glassfish.tyrus.core.MaskingKeyGenerator;
import org.glassfish.tyrus.core.ProtocolHandler;
import org.glassfish.tyrus.core.RequestContext;
import org.glassfish.tyrus.core.TyrusEndpointWrapper;
import org.glassfish.tyrus.core.TyrusExtension;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.core.Version;
import org.glassfish.tyrus.core.WebSocketException;
import org.glassfish.tyrus.core.DebugContext.Type;
import org.glassfish.tyrus.core.RequestContext.Builder;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.ReadHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.Writer;
import org.glassfish.tyrus.spi.ClientEngine.ClientUpgradeStatus;

public class TyrusClientEngine implements ClientEngine {
   public static final int DEFAULT_INCOMING_BUFFER_SIZE = 4194315;
   private static final Logger LOGGER = Logger.getLogger(TyrusClientEngine.class.getName());
   private static final Version DEFAULT_VERSION;
   private static final int BUFFER_STEP_SIZE = 256;
   private static final int DEFAULT_REDIRECT_THRESHOLD = 5;
   private final ProtocolHandler protocolHandler;
   private final TyrusEndpointWrapper endpointWrapper;
   private final ClientHandshakeListener listener;
   private final Map properties;
   private final URI connectToServerUriParam;
   private final Boolean redirectEnabled;
   private final int redirectThreshold;
   private final DebugContext debugContext;
   private final boolean logUpgradeMessages;
   private volatile Handshake clientHandShake = null;
   private volatile ClientEngine.TimeoutHandler timeoutHandler = null;
   private volatile TyrusClientEngineState clientEngineState;
   private volatile URI redirectLocation;
   private final Set redirectUriHistory;
   private static final ClientEngine.ClientUpgradeInfo UPGRADE_INFO_FAILED;
   private static final ClientEngine.ClientUpgradeInfo UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED;

   TyrusClientEngine(TyrusEndpointWrapper endpointWrapper, ClientHandshakeListener listener, Map properties, URI connectToServerUriParam, DebugContext debugContext) {
      this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.INIT;
      this.redirectLocation = null;
      this.endpointWrapper = endpointWrapper;
      this.listener = listener;
      this.properties = properties;
      this.connectToServerUriParam = connectToServerUriParam;
      MaskingKeyGenerator maskingKeyGenerator = (MaskingKeyGenerator)Utils.getProperty(properties, "org.glassfish.tyrus.client.maskingKeyGenerator", MaskingKeyGenerator.class, (Object)null);
      this.protocolHandler = DEFAULT_VERSION.createHandler(true, maskingKeyGenerator);
      this.redirectUriHistory = Collections.synchronizedSet(new HashSet(5));
      this.redirectEnabled = (Boolean)Utils.getProperty(properties, "org.glassfish.tyrus.client.http.redirect", Boolean.class, false);
      Integer redirectThreshold = (Integer)Utils.getProperty(properties, "org.glassfish.tyrus.client.http.redirect.threshold", Integer.class, 5);
      if (redirectThreshold == null) {
         redirectThreshold = 5;
      }

      this.redirectThreshold = redirectThreshold;
      this.debugContext = debugContext;
      this.logUpgradeMessages = (Boolean)Utils.getProperty(properties, "org.glassfish.tyrus.client.http.logUpgrade", Boolean.class, false);
      debugContext.appendLogMessage(LOGGER, Level.FINE, Type.OTHER, new Object[]{"Redirect enabled: ", this.redirectEnabled});
      if (this.redirectEnabled) {
         debugContext.appendLogMessage(LOGGER, Level.FINE, Type.OTHER, new Object[]{"Redirect threshold: ", redirectThreshold});
      }

   }

   public UpgradeRequest createUpgradeRequest(ClientEngine.TimeoutHandler timeoutHandler) {
      RequestContext requestContext;
      switch (this.clientEngineState) {
         case INIT:
            ClientEndpointConfig config = (ClientEndpointConfig)this.endpointWrapper.getEndpointConfig();
            this.timeoutHandler = timeoutHandler;
            this.clientHandShake = Handshake.createClientHandshake(Builder.create().requestURI(this.connectToServerUriParam).secure("wss".equals(this.connectToServerUriParam.getScheme())).build());
            this.clientHandShake.setExtensions(config.getExtensions());
            this.clientHandShake.setSubProtocols(config.getPreferredSubprotocols());
            this.clientHandShake.prepareRequest();
            requestContext = this.clientHandShake.getRequest();
            config.getConfigurator().beforeRequest(requestContext.getHeaders());
            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.UPGRADE_REQUEST_CREATED;
            this.logUpgradeRequest(requestContext);
            return requestContext;
         case REDIRECT_REQUIRED:
            this.timeoutHandler = timeoutHandler;
            URI requestUri = this.redirectLocation;
            requestContext = Builder.create(this.clientHandShake.getRequest()).requestURI(requestUri).secure("wss".equalsIgnoreCase(requestUri.getScheme())).build();
            Handshake.updateHostAndOrigin(requestContext);
            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.UPGRADE_REQUEST_CREATED;
            this.logUpgradeRequest(requestContext);
            return requestContext;
         case AUTH_REQUIRED:
            UpgradeRequest upgradeRequest = this.clientHandShake.getRequest();
            if (this.clientEngineState.getAuthenticator() != null) {
               if (LOGGER.isLoggable(Level.CONFIG)) {
                  this.debugContext.appendLogMessage(LOGGER, Level.CONFIG, Type.MESSAGE_OUT, new Object[]{"Using authenticator: ", this.clientEngineState.getAuthenticator().getClass().getName()});
               }

               String authorizationHeader;
               try {
                  Credentials credentials = (Credentials)this.properties.get("org.glassfish.tyrus.client.http.auth.Credentials");
                  this.debugContext.appendLogMessage(LOGGER, Level.CONFIG, Type.MESSAGE_OUT, new Object[]{"Using credentials: ", credentials});
                  authorizationHeader = this.clientEngineState.getAuthenticator().generateAuthorizationHeader(upgradeRequest.getRequestURI(), this.clientEngineState.getWwwAuthenticateHeader(), credentials);
               } catch (AuthenticationException var5) {
                  this.listener.onError(var5);
                  return null;
               }

               upgradeRequest.getHeaders().put("Authorization", Collections.singletonList(authorizationHeader));
            }

            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED;
            this.logUpgradeRequest(upgradeRequest);
            return upgradeRequest;
         default:
            this.redirectUriHistory.clear();
            throw new IllegalStateException();
      }
   }

   public ClientEngine.ClientUpgradeInfo processResponse(UpgradeResponse upgradeResponse, Writer writer, Connection.CloseListener closeListener) {
      if (LOGGER.isLoggable(Level.FINE)) {
         this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.MESSAGE_IN, new Object[]{"Received handshake response: \n" + Utils.stringifyUpgradeResponse(upgradeResponse)});
      } else if (this.logUpgradeMessages) {
         this.debugContext.appendStandardOutputMessage(Type.MESSAGE_IN, "Received handshake response: \n" + Utils.stringifyUpgradeResponse(upgradeResponse));
      }

      if (this.clientEngineState != TyrusClientEngine.TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED && this.clientEngineState != TyrusClientEngine.TyrusClientEngineState.UPGRADE_REQUEST_CREATED) {
         this.redirectUriHistory.clear();
         throw new IllegalStateException();
      } else if (upgradeResponse == null) {
         throw new IllegalArgumentException(LocalizationMessages.ARGUMENT_NOT_NULL("upgradeResponse"));
      } else {
         switch (upgradeResponse.getStatus()) {
            case 101:
               return this.handleSwitchProtocol(upgradeResponse, writer, closeListener);
            case 300:
            case 301:
            case 302:
            case 303:
            case 307:
            case 308:
               return this.handleRedirect(upgradeResponse);
            case 401:
               return this.handleAuth(upgradeResponse);
            case 503:
               String retryAfterString = null;
               List retryAfterHeader = (List)upgradeResponse.getHeaders().get("Retry-After");
               if (retryAfterHeader != null) {
                  retryAfterString = Utils.getHeaderFromList(retryAfterHeader);
               }

               Long delay;
               if (retryAfterString != null) {
                  try {
                     Date date = Utils.parseHttpDate(retryAfterString);
                     delay = (date.getTime() - System.currentTimeMillis()) / 1000L;
                  } catch (ParseException var10) {
                     try {
                        delay = Long.parseLong(retryAfterString);
                     } catch (NumberFormatException var9) {
                        delay = null;
                     }
                  }
               } else {
                  delay = null;
               }

               this.listener.onError(new RetryAfterException(LocalizationMessages.HANDSHAKE_HTTP_RETRY_AFTER_MESSAGE(), delay));
               return UPGRADE_INFO_FAILED;
            default:
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
               HandshakeException e = new HandshakeException(upgradeResponse.getStatus(), LocalizationMessages.INVALID_RESPONSE_CODE(101, upgradeResponse.getStatus()));
               this.listener.onError(e);
               this.redirectUriHistory.clear();
               return UPGRADE_INFO_FAILED;
         }
      }
   }

   private ClientEngine.ClientUpgradeInfo handleSwitchProtocol(UpgradeResponse upgradeResponse, Writer writer, Connection.CloseListener closeListener) {
      this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.SUCCESS;

      ClientEngine.ClientUpgradeInfo var5;
      try {
         ClientEngine.ClientUpgradeInfo var4 = this.processUpgradeResponse(upgradeResponse, writer, closeListener);
         return var4;
      } catch (HandshakeException var9) {
         this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
         this.listener.onError(var9);
         var5 = UPGRADE_INFO_FAILED;
      } finally {
         this.redirectUriHistory.clear();
      }

      return var5;
   }

   private ClientEngine.ClientUpgradeInfo handleAuth(UpgradeResponse upgradeResponse) {
      if (this.clientEngineState == TyrusClientEngine.TyrusClientEngineState.AUTH_UPGRADE_REQUEST_CREATED) {
         this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
         this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
         return UPGRADE_INFO_FAILED;
      } else {
         AuthConfig authConfig = (AuthConfig)Utils.getProperty(this.properties, "org.glassfish.tyrus.client.http.auth.AuthConfig", AuthConfig.class, AuthConfig.Builder.create().build());
         this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.MESSAGE_OUT, new Object[]{"Using authentication config: ", authConfig});
         if (authConfig == null) {
            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
            this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
            return UPGRADE_INFO_FAILED;
         } else {
            String wwwAuthenticateHeader = null;
            List authHeader = (List)upgradeResponse.getHeaders().get("WWW-Authenticate");
            if (authHeader != null) {
               wwwAuthenticateHeader = Utils.getHeaderFromList(authHeader);
            }

            if (wwwAuthenticateHeader != null && !wwwAuthenticateHeader.equals("")) {
               String[] tokens = wwwAuthenticateHeader.trim().split("\\s+", 2);
               String scheme = tokens[0];
               this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.MESSAGE_OUT, new Object[]{"Using authentication scheme: ", scheme});
               Authenticator authenticator = (Authenticator)authConfig.getAuthenticators().get(scheme);
               if (authenticator == null) {
                  this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
                  this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
                  return UPGRADE_INFO_FAILED;
               } else {
                  this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.AUTH_REQUIRED;
                  this.clientEngineState.setAuthenticator(authenticator);
                  this.clientEngineState.setWwwAuthenticateHeader(wwwAuthenticateHeader);
                  return UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED;
               }
            } else {
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
               this.listener.onError(new AuthenticationException(LocalizationMessages.AUTHENTICATION_FAILED()));
               return UPGRADE_INFO_FAILED;
            }
         }
      }
   }

   private ClientEngine.ClientUpgradeInfo handleRedirect(UpgradeResponse upgradeResponse) {
      if (!this.redirectEnabled) {
         this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
         this.listener.onError(new RedirectException(upgradeResponse.getStatus(), LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NOT_ENABLED(upgradeResponse.getStatus())));
         return UPGRADE_INFO_FAILED;
      } else {
         String locationString = null;
         List locationHeader = (List)upgradeResponse.getHeaders().get("Location");
         if (locationHeader != null) {
            locationString = Utils.getHeaderFromList(locationHeader);
         }

         if (locationString != null && !locationString.equals("")) {
            URI location;
            try {
               location = new URI(locationString);
               String scheme = location.getScheme();
               if ("http".equalsIgnoreCase(scheme)) {
                  scheme = "ws";
               }

               if ("https".equalsIgnoreCase(scheme)) {
                  scheme = "wss";
               }

               int port = Utils.getWsPort(location, scheme);
               location = new URI(scheme, location.getUserInfo(), location.getHost(), port, location.getPath(), location.getQuery(), location.getFragment());
               if (!location.isAbsolute()) {
                  URI baseUri = this.redirectLocation == null ? this.connectToServerUriParam : this.redirectLocation;
                  location = baseUri.resolve(location.normalize());
                  if (LOGGER.isLoggable(Level.FINEST)) {
                     LOGGER.finest("HTTP Redirect - Base URI for resolving target location: " + baseUri);
                     LOGGER.finest("HTTP Redirect - Location URI header: " + locationString);
                     LOGGER.finest("HTTP Redirect - Normalized and resolved Location URI header against base URI: " + location);
                  }
               }
            } catch (URISyntaxException var8) {
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
               this.listener.onError(new RedirectException(upgradeResponse.getStatus(), LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NEW_LOCATION_ERROR(locationString)));
               return UPGRADE_INFO_FAILED;
            }

            boolean alreadyRequested = !this.redirectUriHistory.add(location);
            if (alreadyRequested) {
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
               this.listener.onError(new RedirectException(upgradeResponse.getStatus(), LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_INFINITE_LOOP()));
               return UPGRADE_INFO_FAILED;
            } else if (this.redirectUriHistory.size() > this.redirectThreshold) {
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
               this.listener.onError(new RedirectException(upgradeResponse.getStatus(), LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_MAX_REDIRECTION(this.redirectThreshold)));
               return UPGRADE_INFO_FAILED;
            } else {
               this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.REDIRECT_REQUIRED;
               this.redirectLocation = location;
               return UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED;
            }
         } else {
            this.listener.onError(new RedirectException(upgradeResponse.getStatus(), LocalizationMessages.HANDSHAKE_HTTP_REDIRECTION_NEW_LOCATION_MISSING()));
            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
            return UPGRADE_INFO_FAILED;
         }
      }
   }

   public void processError(Throwable t) {
      if (this.clientEngineState == TyrusClientEngine.TyrusClientEngineState.SUCCESS) {
         throw new IllegalStateException();
      } else {
         if (this.clientEngineState != TyrusClientEngine.TyrusClientEngineState.FAILED) {
            this.clientEngineState = TyrusClientEngine.TyrusClientEngineState.FAILED;
            this.listener.onError(t);
         }

      }
   }

   private void logUpgradeRequest(UpgradeRequest upgradeRequest) {
      if (LOGGER.isLoggable(Level.FINE)) {
         this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.MESSAGE_OUT, new Object[]{"Sending handshake request:\n" + Utils.stringifyUpgradeRequest(upgradeRequest)});
      } else if (this.logUpgradeMessages) {
         this.debugContext.appendStandardOutputMessage(Type.MESSAGE_OUT, "Sending handshake request:\n" + Utils.stringifyUpgradeRequest(upgradeRequest));
      }

   }

   private ClientEngine.ClientUpgradeInfo processUpgradeResponse(UpgradeResponse upgradeResponse, final Writer writer, final Connection.CloseListener closeListener) throws HandshakeException {
      this.clientHandShake.validateServerResponse(upgradeResponse);
      final TyrusWebSocket socket = new TyrusWebSocket(this.protocolHandler, this.endpointWrapper);
      List handshakeResponseExtensions = TyrusExtension.fromHeaders((List)upgradeResponse.getHeaders().get("Sec-WebSocket-Extensions"));
      List extensions = new ArrayList();
      final ExtendedExtension.ExtensionContext extensionContext = new ExtendedExtension.ExtensionContext() {
         private final Map properties = new HashMap();

         public Map getProperties() {
            return this.properties;
         }
      };
      Iterator var8 = handshakeResponseExtensions.iterator();

      label59:
      while(var8.hasNext()) {
         Extension responseExtension = (Extension)var8.next();
         Iterator var10 = ((ClientEndpointConfig)this.endpointWrapper.getEndpointConfig()).getExtensions().iterator();

         while(true) {
            Extension installedExtension;
            String responseExtensionName;
            do {
               do {
                  if (!var10.hasNext()) {
                     continue label59;
                  }

                  installedExtension = (Extension)var10.next();
                  responseExtensionName = responseExtension.getName();
               } while(responseExtensionName == null);
            } while(!responseExtensionName.equals(installedExtension.getName()));

            boolean alreadyAdded = false;
            Iterator var14 = extensions.iterator();

            while(var14.hasNext()) {
               Extension extension = (Extension)var14.next();
               if (extension.getName().equals(responseExtensionName)) {
                  alreadyAdded = true;
               }
            }

            if (!alreadyAdded) {
               if (installedExtension instanceof ExtendedExtension) {
                  ((ExtendedExtension)installedExtension).onHandshakeResponse(extensionContext, responseExtension.getParameters());
               }

               extensions.add(installedExtension);
               this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.OTHER, new Object[]{"Installed extension: ", installedExtension.getName()});
            }
         }
      }

      final Session sessionForRemoteEndpoint = this.endpointWrapper.createSessionForRemoteEndpoint(socket, upgradeResponse.getFirstHeaderValue("Sec-WebSocket-Protocol"), extensions, this.debugContext);
      ((ClientEndpointConfig)this.endpointWrapper.getEndpointConfig()).getConfigurator().afterResponse(upgradeResponse);
      this.protocolHandler.setWriter(writer);
      this.protocolHandler.setWebSocket(socket);
      this.protocolHandler.setExtensions(extensions);
      this.protocolHandler.setExtensionContext(extensionContext);
      socket.onConnect(this.clientHandShake.getRequest(), (String)null, (List)null, (String)null, this.debugContext);
      this.listener.onSessionCreated(sessionForRemoteEndpoint);
      Integer tyrusIncomingBufferSize = (Integer)Utils.getProperty(this.properties, "org.glassfish.tyrus.incomingBufferSize", Integer.class);
      Integer wlsIncomingBufferSize = (Integer)Utils.getProperty(this.endpointWrapper.getEndpointConfig().getUserProperties(), "weblogic.websocket.tyrus.incoming-buffer-size", Integer.class);
      final Integer incomingBufferSize;
      if (tyrusIncomingBufferSize == null && wlsIncomingBufferSize == null) {
         incomingBufferSize = 4194315;
      } else if (wlsIncomingBufferSize != null) {
         incomingBufferSize = wlsIncomingBufferSize;
      } else {
         incomingBufferSize = tyrusIncomingBufferSize;
      }

      this.debugContext.appendLogMessage(LOGGER, Level.FINE, Type.OTHER, new Object[]{"Incoming buffer size: ", incomingBufferSize});
      return new ClientEngine.ClientUpgradeInfo() {
         public ClientEngine.ClientUpgradeStatus getUpgradeStatus() {
            return ClientUpgradeStatus.SUCCESS;
         }

         public Connection createConnection() {
            return new Connection() {
               private final ReadHandler readHandler;

               {
                  this.readHandler = new TyrusReadHandler(TyrusClientEngine.this.protocolHandler, socket, incomingBufferSize, sessionForRemoteEndpoint.getNegotiatedExtensions(), extensionContext);
               }

               public ReadHandler getReadHandler() {
                  return this.readHandler;
               }

               public Writer getWriter() {
                  return writer;
               }

               public Connection.CloseListener getCloseListener() {
                  return closeListener;
               }

               public void close(CloseReason reason) {
                  try {
                     writer.close();
                  } catch (IOException var4) {
                     Logger.getLogger(this.getClass().getName()).log(Level.WARNING, var4.getMessage(), var4);
                  }

                  socket.close(reason.getCloseCode().getCode(), reason.getReasonPhrase());
                  Iterator var2 = sessionForRemoteEndpoint.getNegotiatedExtensions().iterator();

                  while(var2.hasNext()) {
                     Extension extension = (Extension)var2.next();
                     if (extension instanceof ExtendedExtension) {
                        ((ExtendedExtension)extension).destroy(extensionContext);
                     }
                  }

               }
            };
         }
      };
   }

   public ClientEngine.TimeoutHandler getTimeoutHandler() {
      return this.timeoutHandler;
   }

   static {
      DEFAULT_VERSION = Version.DRAFT17;
      UPGRADE_INFO_FAILED = new ClientEngine.ClientUpgradeInfo() {
         public ClientEngine.ClientUpgradeStatus getUpgradeStatus() {
            return ClientUpgradeStatus.UPGRADE_REQUEST_FAILED;
         }

         public Connection createConnection() {
            return null;
         }
      };
      UPGRADE_INFO_ANOTHER_REQUEST_REQUIRED = new ClientEngine.ClientUpgradeInfo() {
         public ClientEngine.ClientUpgradeStatus getUpgradeStatus() {
            return ClientUpgradeStatus.ANOTHER_UPGRADE_REQUEST_REQUIRED;
         }

         public Connection createConnection() {
            return null;
         }
      };
   }

   private static enum TyrusClientEngineState {
      INIT,
      REDIRECT_REQUIRED,
      AUTH_REQUIRED,
      AUTH_UPGRADE_REQUEST_CREATED,
      UPGRADE_REQUEST_CREATED,
      FAILED,
      SUCCESS;

      private volatile Authenticator authenticator;
      private volatile String wwwAuthenticateHeader;

      Authenticator getAuthenticator() {
         return this.authenticator;
      }

      void setAuthenticator(Authenticator authenticator) {
         this.authenticator = authenticator;
      }

      String getWwwAuthenticateHeader() {
         return this.wwwAuthenticateHeader;
      }

      void setWwwAuthenticateHeader(String wwwAuthenticateHeader) {
         this.wwwAuthenticateHeader = wwwAuthenticateHeader;
      }
   }

   private static class TyrusReadHandler implements ReadHandler {
      private final int incomingBufferSize;
      private final ProtocolHandler handler;
      private final TyrusWebSocket socket;
      private final List negotiatedExtensions;
      private final ExtendedExtension.ExtensionContext extensionContext;
      private ByteBuffer buffer = null;

      TyrusReadHandler(ProtocolHandler protocolHandler, TyrusWebSocket socket, int incomingBufferSize, List negotiatedExtensions, ExtendedExtension.ExtensionContext extensionContext) {
         this.handler = protocolHandler;
         this.socket = socket;
         this.incomingBufferSize = incomingBufferSize;
         this.negotiatedExtensions = negotiatedExtensions;
         this.extensionContext = extensionContext;
         protocolHandler.setExtensionContext(extensionContext);
      }

      public void handle(ByteBuffer data) {
         try {
            if (data != null && data.hasRemaining()) {
               if (this.buffer != null) {
                  data = Utils.appendBuffers(this.buffer, data, this.incomingBufferSize, 256);
               } else {
                  int newSize = data.remaining();
                  if (newSize > this.incomingBufferSize) {
                     throw new IllegalArgumentException("Buffer overflow.");
                  }

                  int roundedSize = newSize % 256 > 0 ? (newSize / 256 + 1) * 256 : newSize;
                  ByteBuffer result = ByteBuffer.allocate(roundedSize > this.incomingBufferSize ? newSize : roundedSize);
                  result.flip();
                  data = Utils.appendBuffers(result, data, this.incomingBufferSize, 256);
               }

               while(true) {
                  Frame frame = this.handler.unframe(data);
                  if (frame == null) {
                     this.buffer = data;
                     break;
                  }

                  Iterator var10 = this.negotiatedExtensions.iterator();

                  while(var10.hasNext()) {
                     Extension extension = (Extension)var10.next();
                     if (extension instanceof ExtendedExtension) {
                        try {
                           frame = ((ExtendedExtension)extension).processIncoming(this.extensionContext, frame);
                        } catch (Throwable var6) {
                           TyrusClientEngine.LOGGER.log(Level.FINE, String.format("Extension '%s' threw an exception during processIncoming method invocation: \"%s\".", extension.getName(), var6.getMessage()), var6);
                        }
                     }
                  }

                  this.handler.process(frame, this.socket);
               }
            }
         } catch (WebSocketException var7) {
            TyrusClientEngine.LOGGER.log(Level.FINE, var7.getMessage(), var7);
            this.socket.onClose(new CloseFrame(var7.getCloseReason()));
         } catch (Exception var8) {
            TyrusClientEngine.LOGGER.log(Level.FINE, var8.getMessage(), var8);
            this.socket.onClose(new CloseFrame(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, var8.getMessage())));
         }

      }
   }

   public interface ClientHandshakeListener {
      void onSessionCreated(Session var1);

      void onError(Throwable var1);
   }
}
