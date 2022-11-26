package weblogic.servlet.internal.async;

import java.io.IOException;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.HttpSocket;
import weblogic.servlet.internal.MuxableSocketHTTP;
import weblogic.servlet.internal.RequestEventsFilter;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.io.FilenameEncoder;
import weblogic.work.WorkManager;

public class AsyncContextImpl implements AsyncStateSupport {
   private static final String INTERNAL_ERROR = String.valueOf(500);
   private WebAppServletContext servletContext;
   private ServletRequest request;
   private ServletResponse response;
   private final ServletRequestImpl originalReq;
   private final ServletResponseImpl originalResp;
   private boolean hasOriginalReqAndResp;
   private boolean initWithReqAndResp;
   private long timeout;
   private long timeoutMillis = -1L;
   private final AsyncEventsManager asyncEventsManager;
   private AsyncRequestDispatcher pendingDispatcher;
   private AsyncState currentState;

   public AsyncContextImpl(WebAppServletContext wasc, ServletRequest rq, ServletResponse rs, boolean initWithReqAndResp) {
      this.initialize(wasc, rq, rs, initWithReqAndResp);
      this.currentState = AsyncStates.ASYNC_STARTED;
      this.setupDefaultTimeout(wasc);
      this.originalReq = ServletRequestImpl.getOriginalRequest(rq);
      this.originalResp = ServletResponseImpl.getOriginalResponse(rs);
      this.asyncEventsManager = new AsyncEventsManager(this);
   }

   private void initialize(WebAppServletContext context, ServletRequest req, ServletResponse resp, boolean initWithReqAndResp) {
      this.servletContext = context;
      this.request = req;
      this.response = resp;
      this.initWithReqAndResp = initWithReqAndResp;
      this.hasOriginalReqAndResp = !initWithReqAndResp || !this.isApplicationWrapper(req, resp);
   }

   private void resetMuxableSocket() {
      HttpSocket httpSocket = this.originalReq.getConnection().getConnectionHandler().getRawConnection();
      if (httpSocket instanceof MuxableSocketHTTP) {
         MuxableSocketHTTP ms = (MuxableSocketHTTP)httpSocket;
         ms.setMuxableSocketToSocketInfo();
      }

   }

   private boolean isApplicationWrapper(ServletRequest req, ServletResponse resp) {
      if (resp instanceof ServletResponseWrapper) {
         return true;
      } else {
         return req instanceof ServletRequestWrapper && !(req instanceof RequestEventsFilter.EventsRequestWrapper);
      }
   }

   private void setupDefaultTimeout(WebAppServletContext wasc) {
      this.timeout = (long)(wasc.getConfigManager().getAsyncTimeoutSecs() * 1000);
   }

   public void reInitialize(WebAppServletContext newContext, ServletRequest newRq, ServletResponse newRs, boolean initWithReqAndResp) {
      this.initialize(newContext, newRq, newRs, initWithReqAndResp);
      this.currentState.start(this);

      try {
         this.asyncEventsManager.notifyStartEvent();
      } catch (Throwable var9) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(var9.getMessage(), var9);
         }
      } finally {
         this.asyncEventsManager.clearListeners();
      }

   }

   public void returnToContainer() {
      synchronized(this) {
         this.currentState.returnToContainer(this);
      }
   }

   public void handleError(Throwable error) {
      synchronized(this) {
         this.currentState.notifyError(this, error);
         this.currentState.dispatchErrorPage(this, error);
         this.currentState.returnToContainer(this);
      }
   }

   public void handleTimeout() {
      synchronized(this) {
         this.currentState.notifyTimeout(this);
         if (this.currentState == AsyncStates.ASYNC_WAIT) {
            this.currentState.dispatchErrorPage(this, (Throwable)null);
            this.currentState.returnToContainer(this);
         }

      }
   }

   public void addListener(AsyncListener listener, ServletRequest req, ServletResponse resp) {
      synchronized(this) {
         this.currentState.addListener(this, listener, req, resp);
      }
   }

   public void addListener(AsyncListener listener) {
      synchronized(this) {
         this.currentState.addListener(this, listener, (ServletRequest)null, (ServletResponse)null);
      }
   }

   public AsyncListener createListener(Class clazz) throws ServletException {
      if (clazz == null) {
         throw new ServletException("null class is not allowed.");
      } else {
         try {
            return (AsyncListener)this.servletContext.createInstance(clazz);
         } catch (IllegalAccessException var3) {
            throw new ServletException(var3);
         } catch (InstantiationException var4) {
            throw new ServletException(var4);
         } catch (ClassNotFoundException var5) {
            throw new ServletException(var5);
         } catch (Throwable var6) {
            throw new ServletException(var6);
         }
      }
   }

   public AsyncEventsManager getAsyncEventsManager() {
      return this.asyncEventsManager;
   }

   WebAppServletContext getServletContext() {
      return this.servletContext;
   }

   public void complete() {
      synchronized(this) {
         this.resetMuxableSocket();
         ((ServletOutputStreamImpl)this.originalResp.getOutputStreamNoCheck()).resetChunkOutput();
         this.currentState.complete(this);
      }
   }

   public void dispatch() {
      String path = this.findDispatchTargetPath();
      synchronized(this) {
         this.currentState.dispatch(this, this.servletContext, path);
      }
   }

   public void dispatch(String path) {
      synchronized(this) {
         this.currentState.dispatch(this, this.servletContext, path);
      }
   }

   public void dispatch(ServletContext context, String path) {
      synchronized(this) {
         this.currentState.dispatch(this, context, path);
      }
   }

   public ServletRequest getRequest() {
      if (this.currentState != AsyncStates.DISPATCHED && this.currentState != AsyncStates.COMPLETED) {
         return this.request;
      } else {
         throw new IllegalStateException(HTTPLogger.logGetRequestOrResponseWhenStateIsCompletedOrDispatchedLoggable().getMessage());
      }
   }

   public ServletResponse getResponse() {
      if (this.currentState != AsyncStates.DISPATCHED && this.currentState != AsyncStates.COMPLETED) {
         return this.response;
      } else {
         throw new IllegalStateException(HTTPLogger.logGetRequestOrResponseWhenStateIsCompletedOrDispatchedLoggable().getMessage());
      }
   }

   public boolean hasOriginalRequestAndResponse() {
      return this.hasOriginalReqAndResp;
   }

   public long getTimeout() {
      return this.timeout;
   }

   public void setTimeout(long newTimeout) {
      synchronized(this) {
         this.currentState.setTimeout(this, newTimeout);
      }
   }

   public void start(final Runnable run) {
      WorkManager workManager = this.servletContext.getConfigManager().getAsyncWorkManager();
      workManager.schedule(new Runnable() {
         public void run() {
            try {
               run.run();
            } catch (Throwable var2) {
               HTTPLogger.logException("Exception during executing AsyncContext.start()", var2);
            }

         }
      });
   }

   public void setAsyncState(AsyncState newState) {
      this.currentState = newState;
   }

   public AsyncState getAsyncState() {
      return this.currentState;
   }

   public void setTimeoutInternal(long newTimeout) {
      this.timeout = newTimeout;
   }

   public void registerTimeout() {
      if (this.timeout > 0L) {
         this.servletContext.getAsyncContextTimer().put(this);
         this.timeoutMillis = System.currentTimeMillis() + this.timeout;
      }

   }

   public void unregisterTimeout() {
      this.servletContext.getAsyncContextTimer().remove(this);
   }

   public void registerListener(AsyncListener listener, ServletRequest request, ServletResponse response) {
      this.asyncEventsManager.addListener(listener, request, response);
   }

   public void notifyOnCompleteEvent() {
      try {
         this.asyncEventsManager.notifyCompleteEvent();
      } catch (ServletException var2) {
         this.handleCompleteException(var2);
      }

   }

   public void initDispatcher(ServletContext context, String path) {
      if (path == null) {
         throw new IllegalArgumentException("The dispatch path can NOT be a null.");
      } else {
         String queryString = this.findQueryStringFromPath(path);
         path = this.prependContextPath(context, this.convertRelativePathToAbsolute(this.removeQueryString(path)));
         path = FilenameEncoder.resolveRelativeURIPath(path, true);
         this.pendingDispatcher = new AsyncRequestDispatcher((WebAppServletContext)context, path, queryString);
      }
   }

   public void scheduleDispatch() {
      if (this.pendingDispatcher == null) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Inconsistent state: a null pending dispatcher is detected: " + this);
         }

      } else {
         this.setupContextForCrossCtxDispatch();

         try {
            this.servletContext.getConfigManager().getAsyncWorkManager().schedule(new DispatchHandler(this, this.pendingDispatcher, this.request, this.response));
         } catch (Exception var5) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug(var5.getMessage(), var5);
            }
         } finally {
            this.pendingDispatcher = null;
         }

      }
   }

   public void commitResponse() {
      try {
         this.originalResp.send();
      } catch (IOException var2) {
         this.handleCompleteException(var2);
      }

   }

   ServletRequestImpl getOriginalRequest() {
      return this.originalReq;
   }

   private String findDispatchTargetPath() {
      String path = null;
      if (this.initWithReqAndResp && this.request instanceof HttpServletRequest) {
         path = ((HttpServletRequest)this.request).getRequestURI();
      } else {
         path = (String)this.request.getAttribute("javax.servlet.async.request_uri");
         if (path == null) {
            path = (String)this.request.getAttribute("javax.servlet.forward.request_uri");
         }

         if (path == null) {
            path = this.originalReq.getRequestURI();
         }
      }

      if (path.length() >= this.servletContext.getContextPath().length()) {
         path = path.substring(this.servletContext.getContextPath().length());
      }

      return path;
   }

   private String findQueryStringFromPath(String path) {
      if (path != null && !path.isEmpty()) {
         String queryString = null;
         int queryStringPos = path.indexOf(63);
         if (queryStringPos > 0 && queryStringPos < path.length()) {
            queryString = path.substring(queryStringPos + 1);
            if (queryString.isEmpty()) {
               queryString = null;
            }
         }

         return queryString;
      } else {
         return null;
      }
   }

   private String convertRelativePathToAbsolute(String relativePath) {
      if (relativePath.charAt(0) == '/') {
         return relativePath;
      } else {
         String relUri = this.originalReq.getRelativeUri();
         int lastSlash = relUri.lastIndexOf(47);
         if (lastSlash < 1) {
            relativePath = "/" + relativePath;
         } else {
            relativePath = relUri.substring(0, lastSlash + 1) + relativePath;
         }

         return relativePath;
      }
   }

   private String prependContextPath(ServletContext servletCtx, String path) {
      return servletCtx.getContextPath() + path;
   }

   private String removeQueryString(String path) {
      int queryStringPos = path.indexOf(63);
      if (queryStringPos != -1) {
         path = path.substring(0, queryStringPos);
      }

      return path;
   }

   private void setupContextForCrossCtxDispatch() {
      WebAppServletContext originalContext = (WebAppServletContext)this.request.getServletContext();
      WebAppServletContext targetContext = this.pendingDispatcher.getServletContext();
      if (originalContext != targetContext) {
         this.servletContext = targetContext;
         originalContext.getAsyncContextTimer().remove(this);
         targetContext.getAsyncContextTimer().put(this);
      }

   }

   private void handleCompleteException(Throwable t) {
      try {
         this.asyncEventsManager.notifyErrorEvent(t);
      } catch (Throwable var6) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(var6.getMessage(), var6);
         }
      } finally {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logException(t.getMessage(), t);
         }

      }

   }

   boolean isTimeout(long currentTime) {
      return this.timeout > 0L && this.timeoutMillis < currentTime;
   }

   public boolean isStarted() {
      return this.currentState == AsyncStates.ASYNC_STARTED;
   }

   public boolean isAsyncWait() {
      return this.currentState == AsyncStates.ASYNC_WAIT;
   }

   public boolean isAsyncCompleted() {
      return this.currentState == AsyncStates.COMPLETED;
   }

   public boolean isAsyncCompleting() {
      return this.currentState == AsyncStates.COMPLETING;
   }

   public void dispatchErrorPage(Throwable error) {
      if (!this.response.isCommitted() && this.servletContext.getErrorManager().getErrorLocation(INTERNAL_ERROR) != null) {
         try {
            if (error != null) {
               this.originalReq.setAttribute("javax.servlet.error.exception", error);
            }

            this.originalResp.sendError(500);
         } catch (IOException var3) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPLogger.logException(var3.getMessage(), var3);
            }
         }

      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(super.toString()).append("[Ctx=").append(this.servletContext).append(", Req=").append(this.request).append(", Resp=").append(this.response).append("]");
      return buf.toString();
   }
}
