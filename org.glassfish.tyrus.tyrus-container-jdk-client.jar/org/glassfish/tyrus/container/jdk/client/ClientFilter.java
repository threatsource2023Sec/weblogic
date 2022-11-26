package org.glassfish.tyrus.container.jdk.client;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.core.CloseReasons;
import org.glassfish.tyrus.core.TyrusUpgradeResponse;
import org.glassfish.tyrus.core.Utils;
import org.glassfish.tyrus.spi.ClientEngine;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.Connection;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.Writer;

class ClientFilter extends Filter {
   private static final Logger LOGGER = Logger.getLogger(ClientFilter.class.getName());
   private final ClientEngine clientEngine;
   private final HttpResponseParser responseParser = new HttpResponseParser();
   private final Map proxyHeaders;
   private final UpgradeRequest upgradeRequest;
   private final Callable jdkConnector;
   private final AtomicBoolean connectionClosed = new AtomicBoolean(false);
   private volatile boolean proxy;
   private volatile Connection wsConnection;
   private volatile boolean connectedToProxy = false;
   private volatile CompletionHandler connectCompletionHandler;

   ClientFilter(Filter downstreamFilter, ClientEngine clientEngine, Map properties, Callable jdkConnector, UpgradeRequest upgradeRequest) throws DeploymentException {
      super(downstreamFilter);
      this.clientEngine = clientEngine;
      this.proxyHeaders = getProxyHeaders(properties);
      this.jdkConnector = jdkConnector;
      this.upgradeRequest = upgradeRequest;
   }

   void connect(SocketAddress address, boolean proxy, CompletionHandler connectCompletionHandler) {
      this.connectCompletionHandler = connectCompletionHandler;
      this.proxy = proxy;
      this.downstreamFilter.connect(address, this);
   }

   public void processConnect() {
      JdkUpgradeRequest handshakeUpgradeRequest;
      if (this.proxy) {
         handshakeUpgradeRequest = this.createProxyUpgradeRequest(this.upgradeRequest.getRequestURI());
      } else {
         handshakeUpgradeRequest = this.getJdkUpgradeRequest(this.upgradeRequest, this.downstreamFilter);
      }

      this.sendRequest(this.downstreamFilter, handshakeUpgradeRequest);
   }

   private void sendRequest(Filter downstreamFilter, JdkUpgradeRequest handshakeUpgradeRequest) {
      downstreamFilter.write(HttpRequestBuilder.build(handshakeUpgradeRequest), new CompletionHandler() {
         public void failed(Throwable throwable) {
            ClientFilter.this.onError(throwable);
         }
      });
   }

   private JdkUpgradeRequest getJdkUpgradeRequest(UpgradeRequest upgradeRequest, Filter downstreamFilter) {
      downstreamFilter.startSsl();
      return this.createHandshakeUpgradeRequest(upgradeRequest);
   }

   public boolean processRead(ByteBuffer data) {
      if (this.wsConnection != null) {
         this.wsConnection.getReadHandler().handle(data);
      } else {
         TyrusUpgradeResponse tyrusUpgradeResponse;
         try {
            this.responseParser.appendData(data);
            if (!this.responseParser.isComplete()) {
               return false;
            }

            try {
               tyrusUpgradeResponse = this.responseParser.parseUpgradeResponse();
            } finally {
               this.responseParser.clear();
            }
         } catch (ParseException var11) {
            this.clientEngine.processError(var11);
            this.closeConnection();
            return false;
         }

         if (this.proxy && !this.connectedToProxy) {
            if (tyrusUpgradeResponse.getStatus() != 200) {
               this.processError(new IOException("Could not connect to a proxy. The proxy returned the following status code: " + tyrusUpgradeResponse.getStatus()));
               return false;
            }

            this.connectedToProxy = true;
            this.downstreamFilter.startSsl();
            this.sendRequest(this.downstreamFilter, this.createHandshakeUpgradeRequest(this.upgradeRequest));
            return false;
         }

         JdkWriter writer = new JdkWriter(this.downstreamFilter, this.connectionClosed);
         ClientEngine.ClientUpgradeInfo clientUpgradeInfo = this.clientEngine.processResponse(tyrusUpgradeResponse, writer, new Connection.CloseListener() {
            public void close(CloseReason reason) {
               ClientFilter.this.closeConnection();
            }
         });
         switch (clientUpgradeInfo.getUpgradeStatus()) {
            case ANOTHER_UPGRADE_REQUEST_REQUIRED:
               this.closeConnection();

               try {
                  this.jdkConnector.call();
               } catch (Exception var9) {
                  this.closeConnection();
                  this.clientEngine.processError(var9);
               }
               break;
            case SUCCESS:
               this.wsConnection = clientUpgradeInfo.createConnection();
               if (data.hasRemaining()) {
                  this.wsConnection.getReadHandler().handle(data);
               }
               break;
            case UPGRADE_REQUEST_FAILED:
               this.closeConnection();
         }
      }

      return false;
   }

   public void processConnectionClosed() {
      LOGGER.log(Level.FINE, "Connection has been closed by the server");
      if (this.wsConnection != null) {
         this.wsConnection.close(CloseReasons.CLOSED_ABNORMALLY.getCloseReason());
      }
   }

   void processError(Throwable t) {
      if (this.connectCompletionHandler != null) {
         this.closeConnection();
         this.connectCompletionHandler.failed(t);
      } else {
         LOGGER.log(Level.SEVERE, "Connection error has occurred", t);
         if (this.wsConnection != null) {
            this.wsConnection.close(CloseReasons.CLOSED_ABNORMALLY.getCloseReason());
         }

         this.closeConnection();
      }
   }

   void processSslHandshakeCompleted() {
      this.connectCompletionHandler.completed((Object)null);
      this.connectCompletionHandler = null;
   }

   void close() {
      this.closeConnection();
   }

   private void closeConnection() {
      if (this.connectionClosed.compareAndSet(false, true)) {
         this.downstreamFilter.close();
      }

   }

   private JdkUpgradeRequest createHandshakeUpgradeRequest(final UpgradeRequest upgradeRequest) {
      return new JdkUpgradeRequest(upgradeRequest) {
         public String getHttpMethod() {
            return "GET";
         }

         public String getRequestUri() {
            StringBuilder sb = new StringBuilder();
            URI uri = URI.create(upgradeRequest.getRequestUri());
            sb.append(uri.getPath());
            String query = uri.getQuery();
            if (query != null) {
               sb.append('?').append(query);
            }

            if (sb.length() == 0) {
               sb.append('/');
            }

            return sb.toString();
         }
      };
   }

   private JdkUpgradeRequest createProxyUpgradeRequest(final URI uri) {
      return new JdkUpgradeRequest((UpgradeRequest)null) {
         public String getHttpMethod() {
            return "CONNECT";
         }

         public String getRequestUri() {
            int requestPort = Utils.getWsPort(uri);
            return String.format("%s:%d", uri.getHost(), requestPort);
         }

         public Map getHeaders() {
            Map headers = new HashMap();
            if (ClientFilter.this.proxyHeaders != null) {
               Iterator var2 = ClientFilter.this.proxyHeaders.entrySet().iterator();

               while(var2.hasNext()) {
                  Map.Entry entry = (Map.Entry)var2.next();
                  headers.put(entry.getKey(), Collections.singletonList(entry.getValue()));
               }
            }

            headers.put("Host", Collections.singletonList(uri.getHost()));
            headers.put("ProxyConnection", Collections.singletonList("keep-alive"));
            headers.put("Connection", Collections.singletonList("keep-alive"));
            return headers;
         }
      };
   }

   private static Map getProxyHeaders(Map properties) throws DeploymentException {
      Map proxyHeaders = (Map)Utils.getProperty(properties, "org.glassfish.tyrus.client.proxy.headers", Map.class);
      String wlsProxyUsername = null;
      String wlsProxyPassword = null;
      Object value = properties.get("weblogic.websocket.client.PROXY_USERNAME");
      if (value != null) {
         if (!(value instanceof String)) {
            throw new DeploymentException("weblogic.websocket.client.PROXY_USERNAME only accept String values.");
         }

         wlsProxyUsername = (String)value;
      }

      value = properties.get("weblogic.websocket.client.PROXY_PASSWORD");
      if (value != null) {
         if (!(value instanceof String)) {
            throw new DeploymentException("weblogic.websocket.client.PROXY_PASSWORD only accept String values.");
         }

         wlsProxyPassword = (String)value;
      }

      if (proxyHeaders == null) {
         if (wlsProxyUsername != null && wlsProxyPassword != null) {
            proxyHeaders = new HashMap();
            ((Map)proxyHeaders).put("Proxy-Authorization", "Basic " + Base64.getEncoder().encodeToString((wlsProxyUsername + ":" + wlsProxyPassword).getBytes(Charset.forName("UTF-8"))));
         }
      } else {
         boolean proxyAuthPresent = false;
         Iterator var6 = ((Map)proxyHeaders).entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            if (((String)entry.getKey()).equalsIgnoreCase("Proxy-Authorization")) {
               proxyAuthPresent = true;
            }
         }

         if (!proxyAuthPresent && wlsProxyUsername != null && wlsProxyPassword != null) {
            ((Map)proxyHeaders).put("Proxy-Authorization", "Basic " + Base64.getEncoder().encodeToString((wlsProxyUsername + ":" + wlsProxyPassword).getBytes(Charset.forName("UTF-8"))));
         }
      }

      return (Map)proxyHeaders;
   }

   private static class JdkWriter extends Writer {
      private final Filter downstreamFilter;
      private final AtomicBoolean connectionClosed;

      JdkWriter(Filter downstreamFilter, AtomicBoolean connectionClosed) {
         this.downstreamFilter = downstreamFilter;
         this.connectionClosed = connectionClosed;
      }

      public void close() throws IOException {
         if (this.connectionClosed.compareAndSet(false, true)) {
            this.downstreamFilter.close();
         }

      }

      public void write(ByteBuffer buffer, CompletionHandler completionHandler) {
         this.downstreamFilter.write(buffer, completionHandler);
      }
   }
}
