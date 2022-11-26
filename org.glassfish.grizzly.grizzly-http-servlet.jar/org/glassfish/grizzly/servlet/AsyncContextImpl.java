package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

class AsyncContextImpl implements AsyncContext {
   private static final Logger log = Logger.getLogger(AsyncContextImpl.class.getName());
   private static final long DEFAULT_ASYNC_TIMEOUT_MILLIS = -1L;
   static final ExecutorService pool = Executors.newCachedThreadPool(new AsyncPoolThreadFactory());
   private final HttpServletRequestImpl origRequest;
   private ServletRequest servletRequest;
   private ServletResponse servletResponse;
   private boolean isOriginalRequestAndResponse = false;
   private boolean isStartAsyncWithZeroArg = false;
   private final AtomicBoolean isDispatchInProgress = new AtomicBoolean();
   private final ThreadLocal isDispatchInScope = new ThreadLocal() {
      protected Boolean initialValue() {
         return Boolean.FALSE;
      }
   };
   private final AtomicBoolean isOkToConfigure = new AtomicBoolean(true);
   private long asyncTimeoutMillis = -1L;
   private final LinkedList asyncListenerContexts = new LinkedList();
   private final AtomicInteger startAsyncCounter = new AtomicInteger(0);
   private final ThreadLocal isStartAsyncInScope = new ThreadLocal() {
      protected Boolean initialValue() {
         return Boolean.FALSE;
      }
   };

   AsyncContextImpl(HttpServletRequestImpl origRequest, ServletRequest servletRequest, ServletResponse servletResponse, boolean isStartAsyncWithZeroArg) {
      this.origRequest = origRequest;
      this.init(servletRequest, servletResponse, isStartAsyncWithZeroArg);
   }

   public ServletRequest getRequest() {
      return this.servletRequest;
   }

   HttpServletRequestImpl getOriginalRequest() {
      return this.origRequest;
   }

   public ServletResponse getResponse() {
      return this.servletResponse;
   }

   public boolean hasOriginalRequestAndResponse() {
      return this.isOriginalRequestAndResponse;
   }

   public void dispatch() {
      ApplicationDispatcher dispatcher = (ApplicationDispatcher)this.getZeroArgDispatcher(this.origRequest, this.servletRequest, this.isStartAsyncWithZeroArg);
      this.isDispatchInScope.set(true);
      if (dispatcher != null) {
         if (!this.isDispatchInProgress.compareAndSet(false, true)) {
            throw new IllegalStateException("Asynchronous dispatch already in progress, must call ServletRequest.startAsync first");
         }

         pool.execute(new Handler(this, dispatcher, this.origRequest));
      } else {
         log.warning("Unable to determine target of zero-arg dispatcher");
      }

   }

   public void dispatch(String path) {
      if (path == null) {
         throw new IllegalArgumentException("Null path");
      } else {
         ApplicationDispatcher dispatcher = (ApplicationDispatcher)this.servletRequest.getRequestDispatcher(path);
         this.isDispatchInScope.set(true);
         if (dispatcher != null) {
            if (!this.isDispatchInProgress.compareAndSet(false, true)) {
               throw new IllegalStateException("Asynchronous dispatch already in progress, must call ServletRequest.startAsync first");
            }

            pool.execute(new Handler(this, dispatcher, this.origRequest));
         } else {
            log.log(Level.WARNING, "Unable to acquire RequestDispatcher for {0}", path);
         }

      }
   }

   public void dispatch(ServletContext context, String path) {
      if (path != null && context != null) {
         ApplicationDispatcher dispatcher = (ApplicationDispatcher)context.getRequestDispatcher(path);
         this.isDispatchInScope.set(true);
         if (dispatcher != null) {
            if (!this.isDispatchInProgress.compareAndSet(false, true)) {
               throw new IllegalStateException("Asynchronous dispatch already in progress, must call ServletRequest.startAsync first");
            }

            pool.execute(new Handler(this, dispatcher, this.origRequest));
         } else {
            log.log(Level.WARNING, "Unable to acquire RequestDispatcher for {0}in servlet context {1}", new Object[]{path, context.getContextPath()});
         }

      } else {
         throw new IllegalArgumentException("Null context or path");
      }
   }

   boolean isDispatchInScope() {
      return (Boolean)this.isDispatchInScope.get();
   }

   boolean getAndResetDispatchInScope() {
      boolean flag = (Boolean)this.isDispatchInScope.get();
      this.isDispatchInScope.set(Boolean.FALSE);
      return flag;
   }

   public void complete() {
      this.origRequest.asyncComplete();
   }

   public void start(Runnable run) {
      pool.execute(run);
   }

   public void addListener(AsyncListener listener) {
      if (listener == null) {
         throw new IllegalArgumentException("Null listener");
      } else if (!this.isOkToConfigure.get()) {
         throw new IllegalStateException("Must not call AsyncContext.addListener after the container-initiated dispatch during which ServletRequest.startAsync was called has returned to the container");
      } else {
         synchronized(this.asyncListenerContexts) {
            this.asyncListenerContexts.add(new AsyncListenerContext(listener));
         }
      }
   }

   public void addListener(AsyncListener listener, ServletRequest servletRequest, ServletResponse servletResponse) {
      if (listener != null && servletRequest != null && servletResponse != null) {
         if (!this.isOkToConfigure.get()) {
            throw new IllegalStateException("Must not call AsyncContext.addListener after the container-initiated dispatch during which ServletRequest.startAsync was called has returned to the container");
         } else {
            synchronized(this.asyncListenerContexts) {
               this.asyncListenerContexts.add(new AsyncListenerContext(listener, servletRequest, servletResponse));
            }
         }
      } else {
         throw new IllegalArgumentException("Null listener, request, or response");
      }
   }

   public AsyncListener createListener(Class clazz) throws ServletException {
      AsyncListener listener = null;
      WebappContext ctx = this.origRequest.getContextImpl();
      if (ctx != null) {
         try {
            listener = (AsyncListener)ctx.createListener(clazz);
         } catch (Throwable var5) {
            throw new ServletException(var5);
         }
      }

      return listener;
   }

   public void setTimeout(long timeout) {
      if (!this.isOkToConfigure.get()) {
         throw new IllegalStateException("Must not call AsyncContext.setTimeout after the container-initiated dispatch during which ServletRequest.startAsync was called has returned to the container");
      } else {
         this.asyncTimeoutMillis = timeout;
      }
   }

   public long getTimeout() {
      return this.asyncTimeoutMillis;
   }

   void reinitialize(ServletRequest servletRequest, ServletResponse servletResponse, boolean isStartAsyncWithZeroArg) {
      this.init(servletRequest, servletResponse, isStartAsyncWithZeroArg);
      this.isDispatchInProgress.set(false);
      this.setOkToConfigure(true);
      this.startAsyncCounter.incrementAndGet();
      this.notifyAsyncListeners(AsyncContextImpl.AsyncEventType.START_ASYNC, (Throwable)null);
   }

   boolean isOkToConfigure() {
      return this.isOkToConfigure.get();
   }

   void setOkToConfigure(boolean value) {
      this.isOkToConfigure.set(value);
   }

   private void init(ServletRequest servletRequest, ServletResponse servletResponse, boolean isStartAsyncWithZeroArg) {
      this.servletRequest = servletRequest;
      this.servletResponse = servletResponse;
      this.isOriginalRequestAndResponse = servletRequest instanceof HttpServletRequestImpl && servletResponse instanceof HttpServletResponseImpl || servletRequest instanceof DispatchedHttpServletRequest && servletResponse instanceof DispatchedHttpServletResponse;
      this.isStartAsyncWithZeroArg = isStartAsyncWithZeroArg;
   }

   private RequestDispatcher getZeroArgDispatcher(HttpServletRequestImpl origRequest, ServletRequest servletRequest, boolean isStartAsyncWithZeroArg) {
      String dispatchTarget = null;
      boolean isNamed = false;
      if (!isStartAsyncWithZeroArg && servletRequest instanceof HttpServletRequest) {
         HttpServletRequest req = (HttpServletRequest)servletRequest;
         dispatchTarget = this.getCombinedPath(req);
      } else {
         DispatchTargetsInfo dtInfo = (DispatchTargetsInfo)origRequest.getAttribute("org.apache.catalina.core.ApplicationDispatcher.lastDispatchRequestPathAttr");
         if (dtInfo != null) {
            dispatchTarget = dtInfo.getLastDispatchTarget();
            isNamed = dtInfo.isLastNamedDispatchTarget();
         }

         if (dispatchTarget == null) {
            dispatchTarget = this.getCombinedPath(origRequest);
         }
      }

      RequestDispatcher dispatcher = null;
      if (dispatchTarget != null) {
         dispatcher = isNamed ? servletRequest.getServletContext().getNamedDispatcher(dispatchTarget) : servletRequest.getRequestDispatcher(dispatchTarget);
      }

      return dispatcher;
   }

   private String getCombinedPath(HttpServletRequest req) {
      String servletPath = req.getServletPath();
      if (servletPath == null) {
         return null;
      } else {
         String pathInfo = req.getPathInfo();
         return pathInfo == null ? servletPath : servletPath + pathInfo;
      }
   }

   boolean isStartAsyncInScope() {
      return (Boolean)this.isStartAsyncInScope.get();
   }

   void notifyAsyncListeners(AsyncEventType asyncEventType, Throwable t) {
      LinkedList clone;
      synchronized(this.asyncListenerContexts) {
         if (this.asyncListenerContexts.isEmpty()) {
            return;
         }

         clone = new LinkedList(this.asyncListenerContexts);
         if (asyncEventType.equals(AsyncContextImpl.AsyncEventType.START_ASYNC)) {
            this.asyncListenerContexts.clear();
         }
      }

      Iterator var4 = clone.iterator();

      while(var4.hasNext()) {
         AsyncListenerContext asyncListenerContext = (AsyncListenerContext)var4.next();
         AsyncListener asyncListener = asyncListenerContext.getAsyncListener();
         AsyncEvent asyncEvent = new AsyncEvent(this, asyncListenerContext.getRequest(), asyncListenerContext.getResponse(), t);

         try {
            switch (asyncEventType) {
               case COMPLETE:
                  asyncListener.onComplete(asyncEvent);
                  break;
               case TIMEOUT:
                  asyncListener.onTimeout(asyncEvent);
                  break;
               case ERROR:
                  asyncListener.onError(asyncEvent);
                  break;
               case START_ASYNC:
                  asyncListener.onStartAsync(asyncEvent);
            }
         } catch (IOException var9) {
            log.log(Level.WARNING, "Error invoking AsyncListener", var9);
         }
      }

   }

   void clear() {
      synchronized(this.asyncListenerContexts) {
         this.asyncListenerContexts.clear();
      }
   }

   private static final class AsyncPoolThreadFactory implements ThreadFactory {
      private final ThreadFactory defaultFactory;
      private final AtomicInteger counter;

      private AsyncPoolThreadFactory() {
         this.defaultFactory = Executors.defaultThreadFactory();
         this.counter = new AtomicInteger(0);
      }

      public Thread newThread(Runnable r) {
         Thread t = this.defaultFactory.newThread(r);
         t.setName("grizzly-web-async-thread-" + this.counter.incrementAndGet());
         return t;
      }

      // $FF: synthetic method
      AsyncPoolThreadFactory(Object x0) {
         this();
      }
   }

   private static class AsyncListenerContext {
      private final AsyncListener listener;
      private final ServletRequest request;
      private final ServletResponse response;

      public AsyncListenerContext(AsyncListener listener) {
         this(listener, (ServletRequest)null, (ServletResponse)null);
      }

      public AsyncListenerContext(AsyncListener listener, ServletRequest request, ServletResponse response) {
         this.listener = listener;
         this.request = request;
         this.response = response;
      }

      public AsyncListener getAsyncListener() {
         return this.listener;
      }

      public ServletRequest getRequest() {
         return this.request;
      }

      public ServletResponse getResponse() {
         return this.response;
      }
   }

   static class Handler implements Runnable {
      private final AsyncContextImpl asyncContext;
      private final ApplicationDispatcher dispatcher;
      private final HttpServletRequestImpl origRequest;

      Handler(AsyncContextImpl asyncContext, ApplicationDispatcher dispatcher, HttpServletRequestImpl origRequest) {
         this.asyncContext = asyncContext;
         this.dispatcher = dispatcher;
         this.origRequest = origRequest;
      }

      public void run() {
         this.asyncContext.isStartAsyncInScope.set(Boolean.TRUE);
         this.origRequest.setAttribute("org.apache.catalina.core.DISPATCHER_TYPE", DispatcherType.ASYNC);
         this.origRequest.setAsyncStarted(false);
         int startAsyncCurrent = this.asyncContext.startAsyncCounter.get();

         try {
            this.dispatcher.dispatch(this.asyncContext.getRequest(), this.asyncContext.getResponse(), DispatcherType.ASYNC);
            if (this.asyncContext.startAsyncCounter.compareAndSet(startAsyncCurrent, startAsyncCurrent)) {
               this.asyncContext.complete();
            } else {
               this.origRequest.setAsyncTimeout(this.asyncContext.getTimeout());
            }
         } catch (Throwable var6) {
            this.asyncContext.notifyAsyncListeners(AsyncContextImpl.AsyncEventType.ERROR, var6);
            this.asyncContext.getOriginalRequest().errorDispatchAndComplete(var6);
         } finally {
            this.asyncContext.isStartAsyncInScope.set(Boolean.FALSE);
         }

      }
   }

   static enum AsyncEventType {
      COMPLETE,
      TIMEOUT,
      ERROR,
      START_ASYNC;
   }
}
