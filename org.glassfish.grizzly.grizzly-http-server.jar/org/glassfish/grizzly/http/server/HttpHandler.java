package org.glassfish.grizzly.http.server;

import java.io.CharConversionException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.util.DispatcherHelper;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.server.util.MappingData;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.http.util.RequestURIRef;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.utils.Charsets;

public abstract class HttpHandler {
   private static final Logger LOGGER = Grizzly.logger(HttpHandler.class);
   private static final RequestExecutorProvider DEFAULT_REQUEST_EXECUTOR_PROVIDER = new RequestExecutorProvider.WorkerThreadProvider();
   private boolean allowEncodedSlash;
   private boolean decodeURL;
   private Charset requestURIEncoding;
   private boolean allowCustomStatusMessage;
   private final String name;

   public HttpHandler() {
      this((String)null);
   }

   public HttpHandler(String name) {
      this.allowEncodedSlash = false;
      this.decodeURL = false;
      this.allowCustomStatusMessage = true;
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   boolean doHandle(Request request, Response response) throws Exception {
      request.setRequestExecutorProvider(this.getRequestExecutorProvider());
      request.setSessionCookieName(this.getSessionCookieName());
      request.setSessionManager(this.getSessionManager(request));
      response.setErrorPageGenerator(this.getErrorPageGenerator(request));
      if (request.requiresAcknowledgement() && !this.sendAcknowledgment(request, response)) {
         return true;
      } else {
         try {
            HttpRequestPacket httpRequestPacket = request.getRequest();
            RequestURIRef requestURIRef = httpRequestPacket.getRequestURIRef();
            requestURIRef.setDefaultURIEncoding(this.requestURIEncoding);
            if (this.decodeURL) {
               try {
                  requestURIRef.getDecodedRequestURIBC(this.allowEncodedSlash);
               } catch (CharConversionException var6) {
                  response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
                  response.setDetailMessage("Invalid URI: " + var6.getMessage());
                  return true;
               }
            }

            response.getResponse().setAllowCustomReasonPhrase(this.allowCustomStatusMessage);
            request.parseSessionId();
            return this.runService(request, response);
         } catch (Exception var7) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLER_SERVICE_ERROR(), var7);
            HtmlHelper.setErrorAndSendErrorPage(request, response, response.getErrorPageGenerator(), 500, HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), var7);
            return true;
         }
      }
   }

   private boolean runService(final Request request, final Response response) throws Exception {
      Executor threadPool = this.getRequestExecutorProvider().getExecutor(request);
      final HttpServerFilter httpServerFilter = request.getServerFilter();
      final Connection connection = request.getContext().getConnection();
      if (threadPool == null) {
         SuspendStatus suspendStatus = response.initSuspendStatus();
         HttpServerProbeNotifier.notifyBeforeService(httpServerFilter, connection, request, this);
         this.service(request, response);
         return !suspendStatus.getAndInvalidate();
      } else {
         final FilterChainContext ctx = request.getContext();
         ctx.suspend();
         threadPool.execute(new Runnable() {
            public void run() {
               SuspendStatus suspendStatus = response.initSuspendStatus();
               boolean wasSuspended = false;

               try {
                  HttpServerProbeNotifier.notifyBeforeService(httpServerFilter, connection, request, HttpHandler.this);
                  HttpHandler.this.service(request, response);
                  wasSuspended = suspendStatus.getAndInvalidate();
               } catch (Throwable var10) {
                  Throwable e = var10;
                  HttpHandler.LOGGER.log(Level.FINE, "service exception", var10);
                  if (!response.isCommitted()) {
                     response.reset();

                     try {
                        HtmlHelper.setErrorAndSendErrorPage(request, response, response.getErrorPageGenerator(), 500, HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), e);
                     } catch (IOException var9) {
                     }
                  }
               } finally {
                  if (!wasSuspended) {
                     ctx.resume();
                  }

               }

            }
         });
         return false;
      }
   }

   public abstract void service(Request var1, Response var2) throws Exception;

   public void start() {
   }

   public void destroy() {
   }

   public boolean isAllowCustomStatusMessage() {
      return this.allowCustomStatusMessage;
   }

   public void setAllowCustomStatusMessage(boolean allowCustomStatusMessage) {
      this.allowCustomStatusMessage = allowCustomStatusMessage;
   }

   public boolean isAllowEncodedSlash() {
      return this.allowEncodedSlash;
   }

   public void setAllowEncodedSlash(boolean allowEncodedSlash) {
      this.allowEncodedSlash = allowEncodedSlash;
   }

   public Charset getRequestURIEncoding() {
      return this.requestURIEncoding;
   }

   public void setRequestURIEncoding(Charset requestURIEncoding) {
      this.requestURIEncoding = requestURIEncoding;
   }

   public void setRequestURIEncoding(String requestURIEncoding) {
      this.requestURIEncoding = Charsets.lookupCharset(requestURIEncoding);
   }

   public RequestExecutorProvider getRequestExecutorProvider() {
      return DEFAULT_REQUEST_EXECUTOR_PROVIDER;
   }

   protected ErrorPageGenerator getErrorPageGenerator(Request request) {
      return request.getHttpFilter().getConfiguration().getDefaultErrorPageGenerator();
   }

   protected String getSessionCookieName() {
      return null;
   }

   protected SessionManager getSessionManager(Request request) {
      return request.getHttpFilter().getConfiguration().getSessionManager();
   }

   protected boolean sendAcknowledgment(Request request, Response response) throws IOException {
      if ("100-continue".equalsIgnoreCase(request.getHeader(Header.Expect))) {
         response.setStatus(HttpStatus.CONINTUE_100);
         response.sendAcknowledgement();
         return true;
      } else {
         response.setStatus(HttpStatus.EXPECTATION_FAILED_417);
         return false;
      }
   }

   protected void setDecodeUrl(boolean decodeURL) {
      this.decodeURL = decodeURL;
   }

   protected static void updatePaths(Request request, MappingData mappingData) {
      request.setContextPath(mappingData.contextPath.toString());
      request.setPathInfo(mappingData.pathInfo.toString());
      request.setHttpHandlerPath(mappingData.wrapperPath.toString());
   }

   protected void setDispatcherHelper(DispatcherHelper dispatcherHelper) {
   }
}
