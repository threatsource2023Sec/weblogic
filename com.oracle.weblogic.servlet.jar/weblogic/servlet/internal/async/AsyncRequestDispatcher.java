package weblogic.servlet.internal.async;

import java.io.IOException;
import java.security.PrivilegedAction;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.servlet.internal.FilterChainImpl;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;

class AsyncRequestDispatcher {
   protected String path;
   protected String queryString;
   protected WebAppServletContext targetServletContext;
   private ServletRequestImpl originalReqi;
   private ServletResponseImpl originalRspi;
   private WebAppServletContext originalServletContext;
   private boolean crossContextDispatch = false;
   protected ServletStubImpl sstub;

   AsyncRequestDispatcher(WebAppServletContext targetCtx, String path, String queryString) {
      this.targetServletContext = targetCtx;
      this.path = path;
      this.queryString = queryString;
   }

   void dispatch(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
      this.initialize(req, resp);
      this.setupAttributes(req, this.originalReqi);

      try {
         this.reInitWithTartgetContextAndPath();
         this.sstub = this.findTargetServletStub(this.originalReqi, req);
         this.securedInvokeServlet(req, resp, this.originalRspi, 4);
      } catch (Throwable var4) {
         this.handleThrowable(var4);
      }

   }

   private void initialize(ServletRequest req, ServletResponse resp) {
      this.originalReqi = ServletRequestImpl.getOriginalRequest(req);
      this.originalRspi = ServletResponseImpl.getOriginalResponse(resp);
      this.originalServletContext = this.originalReqi.getContext();
      this.crossContextDispatch = this.targetServletContext != this.originalServletContext;
   }

   private void setupAttributes(ServletRequest req, ServletRequestImpl reqi) {
      if (req.getAttribute("javax.servlet.async.request_uri") == null) {
         if (req.getAttribute("javax.servlet.forward.request_uri") != null) {
            req.setAttribute("javax.servlet.async.request_uri", req.getAttribute("javax.servlet.forward.request_uri"));
            req.setAttribute("javax.servlet.async.context_path", req.getAttribute("javax.servlet.forward.context_path"));
            req.setAttribute("javax.servlet.async.servlet_path", req.getAttribute("javax.servlet.forward.servlet_path"));
            req.setAttribute("javax.servlet.async.path_info", req.getAttribute("javax.servlet.forward.path_info"));
            req.setAttribute("javax.servlet.async.query_string", req.getAttribute("javax.servlet.forward.query_string"));
            req.setAttribute("javax.servlet.async.mapping", req.getAttribute("javax.servlet.forward.mapping"));
         } else {
            req.setAttribute("javax.servlet.async.request_uri", reqi.getRequestURI());
            req.setAttribute("javax.servlet.async.context_path", reqi.getContextPath());
            req.setAttribute("javax.servlet.async.servlet_path", reqi.getServletPath());
            req.setAttribute("javax.servlet.async.path_info", reqi.getPathInfo());
            req.setAttribute("javax.servlet.async.query_string", reqi.getQueryString());
            req.setAttribute("javax.servlet.async.mapping", reqi.getHttpServletMapping());
         }

      }
   }

   private void reInitWithTartgetContextAndPath() {
      if (this.crossContextDispatch) {
         this.originalReqi.initContext(this.targetServletContext);
         this.originalRspi.initContext(this.targetServletContext);
      }

      this.originalReqi.initFromRequestURI(this.path);
      this.originalReqi.addForwardParameter(this.queryString);
      this.originalReqi.initInputEncoding();
   }

   private ServletStubImpl findTargetServletStub(ServletRequestImpl reqi, ServletRequest req) throws ServletException {
      ServletStubImpl stub = this.targetServletContext.resolveForwardedRequest(reqi, req);
      if (stub == null) {
         throw new ServletException("Cannot dispatch request - servlet for path: '" + this.path + "' not found.");
      } else {
         return stub;
      }
   }

   private void securedInvokeServlet(final ServletRequest req, final ServletResponse resp, final ServletResponseImpl rspi, final int dispatchType) throws IOException, ServletException {
      Object error = this.findSubjectHandle(req).run(new PrivilegedAction() {
         public Object run() {
            try {
               AsyncRequestDispatcher.this.invokeServlet(req, resp, rspi, dispatchType);
               ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
               return reqi != null && reqi.isAsyncMode() ? null : null;
            } catch (Throwable var2) {
               return var2;
            }
         }
      });
      if (error != null) {
         if (error instanceof IOException) {
            throw (IOException)error;
         } else if (error instanceof ServletException) {
            throw (ServletException)error;
         } else {
            throw new ServletException(error.toString(), (Throwable)error);
         }
      }
   }

   private SubjectHandle findSubjectHandle(ServletRequest req) {
      SubjectHandle subject = null;
      ServletRequestImpl reqi = null;

      try {
         reqi = ServletRequestImpl.getOriginalRequest(req);
      } catch (Throwable var5) {
      }

      if (reqi != null) {
         subject = SecurityModule.getCurrentUser(this.getServletContext().getSecurityContext(), reqi);
      }

      return subject != null ? subject : WebAppSecurity.getProvider().getAnonymousSubject();
   }

   private void invokeServlet(ServletRequest req, ServletResponse resp, ServletResponseImpl rspi, int dispatchType) throws IOException, ServletException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(req);
      DispatcherType origDispatcherType = reqi.getDispatcherType();
      boolean needReqEventFilter = false;
      boolean needCDIRequestListener = false;

      try {
         reqi.setDispatcherType(DispatcherType.ASYNC);
         needReqEventFilter = this.targetServletContext.getEventsManager().hasRequestListeners() && (this.crossContextDispatch || req.getAttribute("requestInitEventNotified") == null);
         needCDIRequestListener = this.needToNotifyCDIRequestListenerAgain(this.targetServletContext, req);
         if (!needReqEventFilter && needCDIRequestListener) {
            this.targetServletContext.getEventsManager().notifyCDIRequestLifetimeEvent(req, true);
         }

         if (!this.targetServletContext.getFilterManager().hasFilters() && !needReqEventFilter) {
            this.sstub.execute(req, resp);
         } else {
            FilterChainImpl chain = this.targetServletContext.getFilterManager().getFilterChain(this.sstub, req, rspi, needReqEventFilter, dispatchType);
            if (chain == null) {
               this.sstub.execute(req, resp);
            } else {
               chain.doFilter(req, resp);
            }
         }
      } finally {
         reqi.setDispatcherType(origDispatcherType);
         if (!needReqEventFilter && needCDIRequestListener) {
            this.targetServletContext.getEventsManager().notifyCDIRequestLifetimeEvent(req, false);
         }

      }

   }

   private boolean needToNotifyCDIRequestListenerAgain(WebAppServletContext context, ServletRequest req) {
      if (context.isCDIWebApplication() && context.getEventsManager().hasRequestListeners() && req.getAttribute("requestInitEventNotified") != null && req.getAttribute("requestDestroyEventNotified") != null) {
         req.removeAttribute("requestInitEventNotified");
         req.removeAttribute("requestDestroyEventNotified");
         return true;
      } else {
         return false;
      }
   }

   private void handleThrowable(Throwable t) throws IOException, ServletException, RuntimeException {
      if (t instanceof ServletException) {
         throw (ServletException)t;
      } else if (t instanceof IOException) {
         throw (IOException)t;
      } else if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else {
         throw new ServletException(t);
      }
   }

   WebAppServletContext getServletContext() {
      return this.targetServletContext;
   }
}
