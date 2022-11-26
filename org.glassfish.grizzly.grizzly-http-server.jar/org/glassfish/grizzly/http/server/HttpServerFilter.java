package org.glassfish.grizzly.http.server;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.attributes.Attribute;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.FilterChainEvent;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.filterchain.ShutdownEvent;
import org.glassfish.grizzly.filterchain.TransportFilter;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpPacket;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.Method;
import org.glassfish.grizzly.http.server.util.HtmlHelper;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.utils.DelayedExecutor;
import org.glassfish.grizzly.utils.Futures;

public class HttpServerFilter extends BaseFilter implements MonitoringAware {
   private static final Logger LOGGER = Grizzly.logger(HttpHandler.class);
   private final FlushResponseHandler flushResponseHandler = new FlushResponseHandler();
   private final Attribute httpRequestInProgress;
   private final DelayedExecutor.DelayQueue suspendedResponseQueue;
   private volatile HttpHandler httpHandler;
   private final ServerFilterConfiguration config;
   private AtomicBoolean shuttingDown = new AtomicBoolean();
   private AtomicReference shutdownCompletionFuture;
   private final AtomicInteger activeRequestsCounter = new AtomicInteger();
   protected final DefaultMonitoringConfig monitoringConfig = new DefaultMonitoringConfig(HttpServerProbe.class) {
      public Object createManagementObject() {
         return HttpServerFilter.this.createJmxManagementObject();
      }
   };

   public HttpServerFilter(ServerFilterConfiguration config, DelayedExecutor delayedExecutor) {
      this.config = config;
      this.suspendedResponseQueue = Response.createDelayQueue(delayedExecutor);
      this.httpRequestInProgress = Grizzly.DEFAULT_ATTRIBUTE_BUILDER.createAttribute("HttpServerFilter.Request");
   }

   public HttpHandler getHttpHandler() {
      return this.httpHandler;
   }

   public void setHttpHandler(HttpHandler httpHandler) {
      this.httpHandler = httpHandler;
   }

   public ServerFilterConfiguration getConfiguration() {
      return this.config;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      assert HttpContext.get(ctx) != null;

      Object message = ctx.getMessage();
      Connection connection = ctx.getConnection();
      if (HttpPacket.isHttp(message)) {
         HttpContent httpContent = (HttpContent)message;
         HttpContext context = httpContent.getHttpHeader().getProcessingState().getHttpContext();
         Request handlerRequest = (Request)this.httpRequestInProgress.get(context);
         if (handlerRequest != null) {
            NextAction var22;
            try {
               ctx.suspend();
               NextAction action = ctx.getSuspendAction();
               if (!handlerRequest.getInputBuffer().append(httpContent)) {
                  ctx.completeAndRecycle();
               } else {
                  ctx.resume(ctx.getStopAction());
               }

               var22 = action;
            } finally {
               httpContent.recycle();
            }

            return var22;
         } else {
            HttpRequestPacket request = (HttpRequestPacket)httpContent.getHttpHeader();
            HttpResponsePacket response = request.getResponse();
            handlerRequest = Request.create();
            handlerRequest.parameters.setLimit(this.config.getMaxRequestParameters());
            this.httpRequestInProgress.set(context, handlerRequest);
            Response handlerResponse = handlerRequest.getResponse();
            handlerRequest.initialize(request, ctx, this);
            handlerResponse.initialize(handlerRequest, response, ctx, this.suspendedResponseQueue, this);
            if (this.config.isGracefulShutdownSupported()) {
               this.activeRequestsCounter.incrementAndGet();
               handlerRequest.addAfterServiceListener(this.flushResponseHandler);
            }

            HttpServerProbeNotifier.notifyRequestReceive(this, connection, handlerRequest);
            boolean wasSuspended = false;

            try {
               ctx.setMessage(handlerResponse);
               if (this.shuttingDown.get()) {
                  handlerResponse.getResponse().getProcessingState().setError(true);
                  HtmlHelper.setErrorAndSendErrorPage(handlerRequest, handlerResponse, this.config.getDefaultErrorPageGenerator(), 503, HttpStatus.SERVICE_UNAVAILABLE_503.getReasonPhrase(), "The server is being shutting down...", (Throwable)null);
               } else if (!this.config.isPassTraceRequest() && request.getMethod() == Method.TRACE) {
                  this.onTraceRequest(handlerRequest, handlerResponse);
               } else if (!this.checkMaxPostSize(request.getContentLength())) {
                  handlerResponse.getResponse().getProcessingState().setError(true);
                  HtmlHelper.setErrorAndSendErrorPage(handlerRequest, handlerResponse, this.config.getDefaultErrorPageGenerator(), 413, HttpStatus.REQUEST_ENTITY_TOO_LARGE_413.getReasonPhrase(), "The request payload size exceeds the max post size limitation", (Throwable)null);
               } else {
                  HttpHandler httpHandlerLocal = this.httpHandler;
                  if (httpHandlerLocal != null) {
                     wasSuspended = !httpHandlerLocal.doHandle(handlerRequest, handlerResponse);
                  }
               }
            } catch (Exception var17) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILTER_HTTPHANDLER_INVOCATION_ERROR(), var17);
               request.getProcessingState().setError(true);
               if (!response.isCommitted()) {
                  HtmlHelper.setErrorAndSendErrorPage(handlerRequest, handlerResponse, this.config.getDefaultErrorPageGenerator(), 500, HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR_500.getReasonPhrase(), var17);
               }
            } catch (Throwable var18) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_HTTP_SERVER_FILTER_UNEXPECTED(), var18);
               throw new IllegalStateException(var18);
            }

            return !wasSuspended ? this.afterService(ctx, connection, handlerRequest, handlerResponse) : ctx.getSuspendAction();
         }
      } else {
         Response response = (Response)message;
         Request request = response.getRequest();
         return this.afterService(ctx, connection, request, response);
      }
   }

   public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
      HttpContext context = HttpContext.get(ctx);
      if (context != null) {
         Request request = (Request)this.httpRequestInProgress.get(context);
         if (request != null) {
            ReadHandler handler = request.getInputBuffer().getReadHandler();
            if (handler != null) {
               handler.onError(error);
            }
         }
      }

   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      if (event.type() == ShutdownEvent.TYPE && this.shuttingDown.compareAndSet(false, true)) {
         ShutdownEvent shutDownEvent = (ShutdownEvent)event;
         final FutureImpl future = Futures.createSafeFuture();
         shutDownEvent.addShutdownTask(new Callable() {
            public Filter call() throws Exception {
               return (Filter)future.get();
            }
         });
         this.shutdownCompletionFuture = new AtomicReference(future);
         if (this.activeRequestsCounter.get() == 0) {
            future.result(this);
         }
      }

      return ctx.getInvokeAction();
   }

   public MonitoringConfig getMonitoringConfig() {
      return this.monitoringConfig;
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.http.server.jmx.HttpServerFilter", this, HttpServerFilter.class);
   }

   protected void onTraceRequest(Request request, Response response) throws IOException {
      if (this.config.isTraceEnabled()) {
         HtmlHelper.writeTraceMessage(request, response);
      } else {
         response.setStatus(HttpStatus.METHOD_NOT_ALLOWED_405);
         response.setHeader(Header.Allow, "POST, GET, DELETE, OPTIONS, PUT, HEAD");
      }

   }

   protected String getFullServerName() {
      return this.config.getHttpServerName() + " " + this.config.getHttpServerVersion();
   }

   private NextAction afterService(FilterChainContext ctx, Connection connection, Request request, Response response) throws IOException {
      HttpContext context = request.getRequest().getProcessingState().getHttpContext();
      this.httpRequestInProgress.remove(context);
      response.finish();
      request.onAfterService();
      HttpServerProbeNotifier.notifyRequestComplete(this, connection, response);
      HttpRequestPacket httpRequest = request.getRequest();
      boolean isBroken = httpRequest.isContentBroken();
      if (response.suspendState != Response.SuspendState.CANCELLED) {
         response.recycle();
         request.recycle();
      }

      if (isBroken) {
         NextAction suspendNextAction = ctx.getSuspendAction();
         ctx.completeAndRecycle();
         return suspendNextAction;
      } else {
         return ctx.getStopAction();
      }
   }

   private void onRequestCompleteAndResponseFlushed() {
      int count = this.activeRequestsCounter.decrementAndGet();
      if (count == 0 && this.shuttingDown.get()) {
         FutureImpl shutdownFuture = this.shutdownCompletionFuture != null ? (FutureImpl)this.shutdownCompletionFuture.getAndSet((Object)null) : null;
         if (shutdownFuture != null) {
            shutdownFuture.result(this);
         }
      }

   }

   private boolean checkMaxPostSize(long requestContentLength) {
      long maxPostSize = this.config.getMaxPostSize();
      return requestContentLength <= 0L || maxPostSize < 0L || maxPostSize >= requestContentLength;
   }

   private final class FlushResponseHandler extends EmptyCompletionHandler implements AfterServiceListener {
      private final FilterChainEvent event;

      private FlushResponseHandler() {
         this.event = TransportFilter.createFlushEvent(this);
      }

      public void cancelled() {
         HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
      }

      public void failed(Throwable throwable) {
         HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
      }

      public void completed(Object result) {
         HttpServerFilter.this.onRequestCompleteAndResponseFlushed();
      }

      public void onAfterService(Request request) {
         request.getContext().notifyDownstream(this.event);
      }

      // $FF: synthetic method
      FlushResponseHandler(Object x1) {
         this();
      }
   }
}
