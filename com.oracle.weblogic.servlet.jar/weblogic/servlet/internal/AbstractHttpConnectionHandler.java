package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import weblogic.i18n.logging.Loggable;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.http2.Stream;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.RequestExecutor;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.HttpRequestParser;
import weblogic.utils.http.RequestParser;
import weblogic.work.WorkManager;

public abstract class AbstractHttpConnectionHandler {
   protected ServletRequestImpl request;
   protected ServletResponseImpl response;
   protected HttpServer httpServer;
   protected final HttpSocket httpSocket;
   protected long bytesReceived = 0L;
   private final boolean https;
   private final RequestExecutor requestExecutor;
   private static boolean doNotSendContinueHeader = false;
   protected boolean reuseRequestResponse;

   public AbstractHttpConnectionHandler(HttpSocket httpSocket, boolean secure) {
      this.httpSocket = httpSocket;
      this.httpServer = WebServerRegistry.getInstance().getHttpServerManager().defaultHttpServer();
      this.https = secure;
      this.requestExecutor = WebServerRegistry.getInstance().getContainerSupportProvider().createRequestExecutor(this);
   }

   public ServletRequestImpl getServletRequest() {
      return this.request;
   }

   public ServletResponseImpl getServletResponse() {
      return this.response;
   }

   public HttpServer getHttpServer() {
      return this.httpServer;
   }

   public boolean isSecure() {
      return this.https;
   }

   public void disableReuse() {
      this.reuseRequestResponse = false;
   }

   public HttpSocket getRawConnection() {
      return this.httpSocket;
   }

   public abstract InputStream getInputStream();

   public abstract void sendRefreshPage(String var1, int var2) throws IOException;

   public ServerChannel getChannel() {
      return this.httpSocket.getServerChannel();
   }

   public Socket getSocket() {
      return this.httpSocket.getSocket();
   }

   public void closeConnection(IOException ioe) {
      this.httpSocket.closeConnection(ioe);
   }

   public void incrementBytesReceivedCount(long increment) {
      this.bytesReceived += increment;
   }

   public long getBytesReceivedCount() {
      return this.bytesReceived;
   }

   public void sendError(int code) {
      this.sendError(this.response, code);
   }

   protected abstract void initInputStream() throws IOException;

   public abstract void sendError(ServletResponseImpl var1, int var2);

   protected boolean initAndValidateRequest(WebAppServletContext context) throws IOException {
      if (!this.request.validate(this.httpServer)) {
         return false;
      } else if (this.request.getInputHelper().getRequestParser().isMethodTrace() && !this.httpServer.isHttpTraceSupportEnabled()) {
         this.sendError(this.response, 501);
         return false;
      } else {
         this.request.initInputEncoding();
         String resEncoding = context.getResponseCharacterEncoding();
         if (resEncoding != null) {
            this.response.setCharacterEncoding(resEncoding);
         } else if (context.getConfigManager().useDefaultEncoding()) {
            this.response.setDefaultEncoding(context.getConfigManager().getDefaultEncoding());
         }

         this.initInputStream();
         if (!doNotSendContinueHeader && this.isHTTP10Above() && (context.getSecurityManager().getAuthMethod() == null || context.getSecurityManager().isFormAuth())) {
            String expect = this.request.getRequestHeaders().getExpect();
            if ("100-continue".equalsIgnoreCase(expect)) {
               this.request.send100ContinueResponse();
            }
         }

         try {
            this.request.setServletStub(context.resolveDirectRequest(this.request));
            return true;
         } catch (SecurityException var4) {
            this.response.setStatus(403);
            HTTPLogger.logException(context.getLogContext(), var4);
            this.httpSocket.closeConnection((Throwable)null);
            return false;
         }
      }
   }

   private boolean isHTTP10Above() {
      RequestParser parser = this.request.getInputHelper().getRequestParser();
      return parser.isProtocolVersion_1_1() || parser.isProtocolVersion_2();
   }

   public abstract void requeue();

   public abstract RequestParser createRequestParser();

   public abstract Stream getStream();

   protected abstract boolean needToUpgrade();

   protected void processUpgrade() throws IOException {
   }

   protected void parseHeaders(RequestParser parser) throws HttpRequestParseException {
   }

   protected void IncrementCount() {
   }

   private boolean isProtocolAllowed() {
      String protocol = this.request.getInputHelper().getRequestParser().getProtocol().toUpperCase();
      return protocol.startsWith("HTTP") || protocol.equals("INCLUDED");
   }

   protected abstract void send400Response(Exception var1) throws IOException;

   private HttpServer findHttpServer(String host, String uri) {
      HttpServerManager httpSrvManager = WebServerRegistry.getInstance().getHttpServerManager();
      HttpServer srvr = httpSrvManager.getVirtualHost(this.httpSocket.getServerChannel());
      if (srvr != null) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Found virtual host: " + srvr.getName() + " for channel: " + this.httpSocket.getServerChannel().getChannelName());
         }

         return srvr;
      } else {
         String vtName = this.httpSocket.getServerChannel().getAssociatedVirtualTargetName();
         if (vtName != null) {
            srvr = httpSrvManager.getVirtualTarget(vtName);
            if (srvr != null) {
               return srvr;
            }
         }

         return httpSrvManager.findHttpServer(host, uri);
      }
   }

   public void dispatch() {
      this.request.getHttpAccountingInfo().setInvokeTime(System.currentTimeMillis());

      try {
         String host;
         try {
            RequestParser parser = this.request.getInputHelper().getRequestParser();
            this.parseHeaders(parser);
            this.request.initFromRequestParser(parser);
            if (HTTPDebugLogger.isEnabled()) {
               host = "<closed>";
               Socket sock = this.httpSocket.getSocket();
               if (sock != null) {
                  host = sock.getInetAddress().toString();
               }

               HTTPDebugLogger.debug("Request received from: " + host + ", Secure: " + this.isSecure() + ", Request: " + this.request.toString());
            }

            this.response.getServletOutputStream().setWriteEnabled(!this.request.getInputHelper().getRequestParser().isMethodHead());
            this.IncrementCount();
            if (this.request.getInputHelper().getRequestParser().isMethodTrace() && !this.httpServer.isHttpTraceSupportEnabled()) {
               this.sendError(this.response, 501);
               return;
            }

            if (!this.isProtocolAllowed()) {
               this.sendError(this.response, 505);
            }
         } catch (HttpRequestParseException var4) {
            RequestParser parser = new HttpRequestParser(var4.getMethod(), var4.getProtocol(), var4.getURI());
            parser.setScheme(this.https ? "https" : "http");
            this.request.initFromRequestParser(parser);
            if (var4.getURI() != null) {
               HTTPLogger.logMalformedRequest(var4.getURI().replace('\r', '_').replace('\n', '_'), -1);
            } else {
               HTTPLogger.logMalformedRequest("Can not parse URI from http request", -1);
            }

            this.send400Response(var4);
            return;
         } catch (IllegalArgumentException var5) {
            this.send400Response(var5);
            return;
         }

         String normalizedURI = this.request.getInputHelper().getNormalizedURI();
         host = this.request.getRequestHeaders().getHost();
         this.httpServer = this.findHttpServer(host, normalizedURI);
         this.response.initHttpServer(this.httpServer);
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Http Server: '" + this.httpServer + "' is resolved for request: '" + this.request.toStringSimple() + "'");
         }

         if (this.httpServer.getOnDemandManager() != null && this.httpSocket.handleOnDemandContext(this.request, normalizedURI)) {
            return;
         }

         if (this.needToUpgrade()) {
            this.processUpgrade();
         } else {
            this.resolveServletContext(normalizedURI);
         }
      } catch (IOException var6) {
         HTTPLogger.logDispatchError(var6);
         this.httpSocket.closeConnection((Throwable)null);
      }

   }

   public void resolveServletContext(String normalizedURI) throws IOException {
      ServletContextManager scm = this.httpServer.getServletContextManager();
      ContextVersionManager ctxManager = null;
      if (normalizedURI != null) {
         ctxManager = scm.resolveVersionManagerForURI(normalizedURI);
      }

      if (ctxManager == null) {
         this.handleNoContext((ContextVersionManager)null);
      } else {
         WebAppServletContext context;
         if (ctxManager.isVersioned()) {
            this.request.initContextManager(ctxManager);
            context = this.request.getContext();
            this.request.getSessionHelper().resetSession(true);
            this.request.getRequestParameters().resetQueryParams();
         } else {
            context = ctxManager.getContext(this.request.isAdminChannelRequest());
            this.request.initContext(context);
         }

         if (context == null) {
            this.handleNoContext(ctxManager);
         } else {
            this.response.initContext(context);
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Servlet Context: " + context + " is resolved for request: '" + this.request.toStringSimple() + "'");
            }

            if (this.initAndValidateRequest(context)) {
               WorkManager workManager = this.request.getServletStub().getWorkManager();
               if (workManager == null) {
                  throw new AssertionError("Could not determine WorkManager for : " + this.request);
               } else {
                  Runnable runnable = this.requestExecutor == null ? this.request : this.requestExecutor;
                  this.request.saveMuxerThreadHash(System.identityHashCode(Thread.currentThread()));
                  workManager.schedule((Runnable)runnable);
               }
            }
         }
      }
   }

   public void handleNoContext(ContextVersionManager ctxManager) throws IOException {
      this.response.setStatus(404);
      if (HTTPDebugLogger.isEnabled()) {
         Loggable l = HTTPLogger.logNoContextLoggable(this.httpServer.toString(), this.request.getInputHelper().getNormalizedURI());
         HTTPDebugLogger.debug(l.getMessage());
      }

      this.response.getServletOutputStream().setWriteEnabled(!this.request.getInputHelper().getRequestParser().isMethodHead());
      if (!WebAppShutdownService.isSuspending() && !WebAppShutdownService.isSuspended() && !WebServerRegistry.getInstance().getManagementProvider().isServerInAdminMode()) {
         if (WebServerRegistry.getInstance().getManagementProvider().isServerInResumingMode() && ctxManager != null && ctxManager.getContext(true) != null) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPLogger.logDebug(this.toString() + " handleNoContext - ctxManager=" + ctxManager + ", ServerName=" + this.httpServer.getServerName() + ", ServerState=" + WebServerRegistry.getInstance().getManagementProvider().getServerState() + ", adminCtx=" + ctxManager.getContext(true));
            }

            this.sendError(this.response, 503);
         } else {
            PartitionTableEntry partitionTable = null;

            try {
               partitionTable = PartitionTable.getInstance().lookup(this.getRequestURL());
            } catch (IllegalArgumentException | URISyntaxException var4) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPLogger.logDebug(this.httpServer.toString() + " failed to getPartitionTable from requestURL" + this.getRequestURL() + ", root cause is " + var4.getMessage());
               }
            }

            if (this.httpServer.isPartitionShutdownOnCurrentServer(partitionTable)) {
               this.sendError(this.response, 503);
            } else {
               this.sendError(this.response, 404);
            }
         }
      } else {
         this.sendError(this.response, 503);
      }

   }

   private String getRequestURL() {
      return this.request == null ? null : this.request.getScheme() + "://" + this.request.getRequestHeaders().getHost() + ":" + this.request.getRequestHeaders().getPort() + (this.request.getRequestURI().startsWith("/") ? "" : "/") + this.request.getRequestURI();
   }

   static {
      doNotSendContinueHeader = Boolean.getBoolean("doNotSendContinueHeader");
      if (!"false".equalsIgnoreCase(System.getProperty("com.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager"))) {
         System.setProperty("com.sun.jersey.server.impl.cdi.lookupExtensionInBeanManager", "true");
      }

   }
}
