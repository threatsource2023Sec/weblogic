package weblogic.servlet.internal.async;

import java.io.IOException;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.EventsManager;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;

class AsyncEventsManager {
   private static final EventHandler onStartHandler = new OnStartHandler();
   private static final EventHandler onCompleteHandler = new OnCompleteHandler();
   private static final EventHandler onTimeoutHandler = new OnTimeoutHandler();
   private static final EventHandler onErrorHandler = new OnErrorHandler();
   private final AsyncContextImpl async;
   private List listeners;

   AsyncEventsManager(AsyncContextImpl asyncContext) {
      this.async = asyncContext;
   }

   void addListener(AsyncListener listener, ServletRequest req, ServletResponse resp) {
      if (listener != null) {
         if (this.listeners == null) {
            this.listeners = new CopyOnWriteArrayList();
         }

         this.listeners.add(new AsyncListenerWrapper(this.async, listener, req, resp));
      }
   }

   void clearListeners() {
      this.listeners = null;
   }

   void notifyStartEvent() throws ServletException {
      this.notifyAsyncEvent(onStartHandler, (Throwable)null);
   }

   void notifyCompleteEvent() throws ServletException {
      this.notifyAsyncEvent(onCompleteHandler, (Throwable)null);
   }

   void notifyTimeoutEvent() throws ServletException {
      this.notifyAsyncEvent(onTimeoutHandler, (Throwable)null);
   }

   void notifyErrorEvent(Throwable t) throws ServletException {
      this.notifyAsyncEvent(onErrorHandler, t);
   }

   void notifyAsyncEvent(EventHandler h, Throwable t) {
      if (this.listeners != null && this.listeners.size() != 0) {
         Thread thread = Thread.currentThread();
         ClassLoader oldClassLoader = this.async.getServletContext().pushEnvironment(thread);

         try {
            this.notifyCDIRequestLifetimeEvent(true);
            SubjectHandle subject = WebAppSecurity.getProvider().getAnonymousSubject();
            Iterator alws = this.listeners.iterator();

            while(alws.hasNext()) {
               Throwable error = this.invokeListener(subject, (AsyncListenerWrapper)alws.next(), h, t);
               if (error != null) {
                  HTTPLogger.logException(error.getMessage(), error);
               }
            }
         } finally {
            try {
               this.notifyCDIRequestLifetimeEvent(false);
            } finally {
               WebAppServletContext.popEnvironment(thread, oldClassLoader);
            }
         }

      }
   }

   private Throwable invokeListener(SubjectHandle subject, final AsyncListenerWrapper alw, final EventHandler h, final Throwable t) {
      PrivilegedAction action = new PrivilegedAction() {
         public Throwable run() {
            AsyncEvent asyncEvent = new AsyncEvent(alw.getAsyncContext(), alw.getRequest(), alw.getResponse(), t);
            return h.handle(alw, asyncEvent);
         }
      };
      return (Throwable)subject.run(action);
   }

   private void notifyCDIRequestLifetimeEvent(boolean initialized) {
      ServletRequestImpl origRequest = this.async.getOriginalRequest();
      WebAppServletContext webContext = origRequest.getContext();
      EventsManager eventsManager = webContext.getEventsManager();
      eventsManager.notifyCDIRequestLifetimeEvent(origRequest, initialized);
   }

   private static class OnCompleteHandler implements EventHandler {
      private OnCompleteHandler() {
      }

      public Throwable handle(AsyncListenerWrapper alw, AsyncEvent event) {
         try {
            alw.getListener().onComplete(event);
            return null;
         } catch (IOException var4) {
            return var4;
         } catch (Throwable var5) {
            return var5;
         }
      }

      // $FF: synthetic method
      OnCompleteHandler(Object x0) {
         this();
      }
   }

   private static class OnErrorHandler implements EventHandler {
      private OnErrorHandler() {
      }

      public Throwable handle(AsyncListenerWrapper alw, AsyncEvent event) {
         try {
            alw.getListener().onError(event);
         } catch (IOException var4) {
            HTTPLogger.logException(var4.getMessage(), var4);
         } catch (Throwable var5) {
            HTTPLogger.logException(var5.getMessage(), var5);
         }

         return null;
      }

      // $FF: synthetic method
      OnErrorHandler(Object x0) {
         this();
      }
   }

   private static class OnTimeoutHandler implements EventHandler {
      private OnTimeoutHandler() {
      }

      public Throwable handle(AsyncListenerWrapper alw, AsyncEvent event) {
         try {
            alw.getListener().onTimeout(event);
            return null;
         } catch (IOException var4) {
            return var4;
         } catch (Throwable var5) {
            return var5;
         }
      }

      // $FF: synthetic method
      OnTimeoutHandler(Object x0) {
         this();
      }
   }

   private static class OnStartHandler implements EventHandler {
      private OnStartHandler() {
      }

      public Throwable handle(AsyncListenerWrapper alw, AsyncEvent event) {
         try {
            alw.getListener().onStartAsync(event);
            return null;
         } catch (IOException var4) {
            return var4;
         } catch (Throwable var5) {
            return var5;
         }
      }

      // $FF: synthetic method
      OnStartHandler(Object x0) {
         this();
      }
   }

   private interface EventHandler {
      Throwable handle(AsyncListenerWrapper var1, AsyncEvent var2);
   }

   private static class AsyncListenerWrapper {
      private final AsyncContextImpl asyncCtx;
      private final AsyncListener listener;
      private final ServletRequest req;
      private final ServletResponse resp;

      AsyncListenerWrapper(AsyncContextImpl asyncCtx, AsyncListener listener, ServletRequest req, ServletResponse resp) {
         this.asyncCtx = asyncCtx;
         this.listener = listener;
         this.req = req;
         this.resp = resp;
      }

      AsyncContextImpl getAsyncContext() {
         return this.asyncCtx;
      }

      AsyncListener getListener() {
         return this.listener;
      }

      ServletRequest getRequest() {
         return this.req;
      }

      ServletResponse getResponse() {
         return this.resp;
      }
   }
}
