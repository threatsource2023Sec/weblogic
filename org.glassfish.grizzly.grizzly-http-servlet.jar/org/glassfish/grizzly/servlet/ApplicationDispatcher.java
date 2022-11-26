package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.glassfish.grizzly.Grizzly;

final class ApplicationDispatcher implements RequestDispatcher {
   public static final String LAST_DISPATCH_REQUEST_PATH_ATTR = "org.apache.catalina.core.ApplicationDispatcher.lastDispatchRequestPathAttr";
   private static final Logger LOGGER = Grizzly.logger(ApplicationDispatcher.class);
   private Boolean crossContextFlag = null;
   private String name = null;
   private String pathInfo = null;
   private String queryString = null;
   private String requestURI = null;
   private String servletPath = null;
   private ServletHandler wrapper = null;

   public ApplicationDispatcher(ServletHandler wrapper, String requestURI, String servletPath, String pathInfo, String queryString, String name) {
      this.wrapper = wrapper;
      this.requestURI = requestURI;
      this.servletPath = servletPath;
      this.pathInfo = pathInfo;
      this.queryString = queryString;
      this.name = name;
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "servletPath={0}, pathInfo={1}, queryString={2}, name={3}", new Object[]{this.servletPath, this.pathInfo, queryString, this.name});
      }

   }

   public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {
      this.dispatch(request, response, DispatcherType.FORWARD);
   }

   public void dispatch(ServletRequest request, ServletResponse response, DispatcherType dispatcherType) throws ServletException, IOException {
      if (!DispatcherType.FORWARD.equals(dispatcherType) && !DispatcherType.ERROR.equals(dispatcherType) && !DispatcherType.ASYNC.equals(dispatcherType)) {
         throw new IllegalArgumentException("Illegal dispatcher type");
      } else {
         boolean isCommit = DispatcherType.FORWARD.equals(dispatcherType) || DispatcherType.ERROR.equals(dispatcherType);
         if (System.getSecurityManager() != null) {
            try {
               PrivilegedDispatch dp = new PrivilegedDispatch(request, response, dispatcherType);
               AccessController.doPrivileged(dp);
               if (isCommit && !request.isAsyncStarted()) {
                  closeResponse(response);
               }
            } catch (PrivilegedActionException var7) {
               Exception e = var7.getException();
               if (e instanceof ServletException) {
                  throw (ServletException)e;
               }

               throw (IOException)e;
            }
         } else {
            this.doDispatch(request, response, dispatcherType);
            if (isCommit && !request.isAsyncStarted()) {
               closeResponse(response);
            }
         }

      }
   }

   private void doDispatch(ServletRequest request, ServletResponse response, DispatcherType dispatcherType) throws ServletException, IOException {
      if (!DispatcherType.ASYNC.equals(dispatcherType)) {
         if (response.isCommitted()) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("  Forward on committed response --> ISE");
            }

            throw new IllegalStateException("Cannot forward after response has been committed");
         }

         try {
            response.resetBuffer();
         } catch (IllegalStateException var12) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "Forward resetBuffer() returned ISE: {0}", var12);
            }

            throw var12;
         }
      }

      if (DispatcherType.INCLUDE != dispatcherType) {
         DispatchTargetsInfo dtInfo = (DispatchTargetsInfo)request.getAttribute("org.apache.catalina.core.ApplicationDispatcher.lastDispatchRequestPathAttr");
         if (dtInfo == null) {
            dtInfo = new DispatchTargetsInfo();
            request.setAttribute("org.apache.catalina.core.ApplicationDispatcher.lastDispatchRequestPathAttr", dtInfo);
         }

         if (this.servletPath == null && this.pathInfo == null) {
            dtInfo.addDispatchTarget(this.wrapper.getName(), true);
         } else {
            dtInfo.addDispatchTarget(this.getCombinedPath(), false);
         }
      }

      State state = new State(request, response, dispatcherType);
      ServletRequest sr = this.wrapRequest(state);
      this.wrapResponse(state);
      HttpServletRequest hrequest = state.hrequest;
      HttpServletResponse hresponse = state.hresponse;
      if (hrequest != null && hresponse != null) {
         DispatchedHttpServletRequest wrequest;
         if (this.servletPath == null && this.pathInfo == null) {
            wrequest = (DispatchedHttpServletRequest)sr;
            wrequest.setRequestURI(hrequest.getRequestURI());
            wrequest.setContextPath(hrequest.getContextPath());
            wrequest.setServletPath(hrequest.getServletPath());
            wrequest.setPathInfo(hrequest.getPathInfo());
            wrequest.setQueryString(hrequest.getQueryString());
            this.processRequest(request, response, state);
         } else {
            wrequest = (DispatchedHttpServletRequest)sr;
            if (DispatcherType.FORWARD.equals(dispatcherType) && hrequest.getAttribute("javax.servlet.forward.request_uri") == null || DispatcherType.ASYNC.equals(dispatcherType) && hrequest.getAttribute("javax.servlet.async.request_uri") == null) {
               wrequest.initSpecialAttributes(hrequest.getRequestURI(), hrequest.getContextPath(), hrequest.getServletPath(), hrequest.getPathInfo(), hrequest.getQueryString());
            }

            String targetContextPath = this.wrapper.getContextPath();
            HttpServletRequestImpl requestFacade = wrequest.getRequestFacade();
            String originContextPath = requestFacade.getContextPath();
            if (originContextPath != null && originContextPath.equals(targetContextPath)) {
               targetContextPath = hrequest.getContextPath();
            }

            wrequest.setContextPath(targetContextPath);
            wrequest.setRequestURI(this.requestURI);
            wrequest.setServletPath(this.servletPath);
            wrequest.setPathInfo(this.pathInfo);
            if (this.queryString != null) {
               wrequest.setQueryString(this.queryString);
               wrequest.setQueryParams(this.queryString);
            }

            this.processRequest(request, response, state);
         }
      } else {
         this.processRequest(request, response, state);
      }

      this.recycleRequestWrapper(state);
      this.unwrapRequest(state);
      this.unwrapResponse(state);
   }

   private void processRequest(ServletRequest request, ServletResponse response, State state) throws IOException, ServletException {
      if (request != null) {
         if (state.dispatcherType != DispatcherType.ERROR) {
            state.outerRequest.setAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH", this.getCombinedPath());
            this.invoke(state.outerRequest, response, state);
         } else {
            this.invoke(state.outerRequest, response, state);
         }
      }

   }

   private String getCombinedPath() {
      if (this.servletPath == null) {
         return null;
      } else {
         return this.pathInfo == null ? this.servletPath : this.servletPath + this.pathInfo;
      }
   }

   public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {
      if (System.getSecurityManager() != null) {
         try {
            PrivilegedInclude dp = new PrivilegedInclude(request, response);
            AccessController.doPrivileged(dp);
         } catch (PrivilegedActionException var5) {
            Exception e = var5.getException();
            if (e instanceof ServletException) {
               throw (ServletException)e;
            }

            throw (IOException)e;
         }
      } else {
         this.doInclude(request, response);
      }

   }

   private void doInclude(ServletRequest request, ServletResponse response) throws ServletException, IOException {
      State state = new State(request, response, DispatcherType.INCLUDE);
      this.wrapResponse(state);
      DispatchedHttpServletRequest wrequest;
      if (this.name != null) {
         wrequest = (DispatchedHttpServletRequest)this.wrapRequest(state);
         wrequest.setAttribute("org.apache.catalina.NAMED", this.name);
         if (this.servletPath != null) {
            wrequest.setServletPath(this.servletPath);
         }

         wrequest.setAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH", this.getCombinedPath());

         try {
            this.invoke(state.outerRequest, state.outerResponse, state);
         } finally {
            this.recycleRequestWrapper(state);
            this.unwrapRequest(state);
            this.unwrapResponse(state);
         }
      } else {
         wrequest = (DispatchedHttpServletRequest)this.wrapRequest(state);
         wrequest.initSpecialAttributes(this.requestURI, this.wrapper.getContextPath(), this.servletPath, this.pathInfo, this.queryString);
         wrequest.setQueryParams(this.queryString);
         wrequest.setAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH", this.getCombinedPath());

         try {
            this.invoke(state.outerRequest, state.outerResponse, state);
         } finally {
            this.recycleRequestWrapper(state);
            this.unwrapRequest(state);
            this.unwrapResponse(state);
         }
      }

   }

   private void invoke(ServletRequest request, ServletResponse response, State state) throws IOException, ServletException {
      boolean crossContext = false;
      if (this.crossContextFlag != null && this.crossContextFlag) {
         crossContext = true;
      }

      try {
         this.doInvoke(request, response, crossContext, state);
      } finally {
         this.crossContextFlag = null;
      }

   }

   private void doInvoke(ServletRequest request, ServletResponse response, boolean crossContext, State state) throws IOException, ServletException {
      ClassLoader oldCCL = null;

      try {
         if (crossContext) {
            ClassLoader contextClassLoader = this.wrapper.getClassLoader();
            if (contextClassLoader != null) {
               oldCCL = Thread.currentThread().getContextClassLoader();
               Thread.currentThread().setContextClassLoader(contextClassLoader);
            }
         }

         this.wrapper.doServletService(request, response, state.dispatcherType);
      } finally {
         if (oldCCL != null) {
            Thread.currentThread().setContextClassLoader(oldCCL);
         }

      }

   }

   private void unwrapRequest(State state) {
      if (state.wrapRequest != null) {
         ServletRequest previous = null;

         for(ServletRequest current = state.outerRequest; current != null && !(current instanceof HttpServletRequestImpl); current = ((ServletRequestWrapper)current).getRequest()) {
            if (current == state.wrapRequest) {
               ServletRequest next = ((ServletRequestWrapper)current).getRequest();
               if (previous == null) {
                  state.outerRequest = next;
               } else {
                  ((ServletRequestWrapper)previous).setRequest(next);
               }
               break;
            }

            previous = current;
         }

      }
   }

   private void unwrapResponse(State state) {
      if (state.wrapResponse != null) {
         ServletResponse previous = null;

         for(ServletResponse current = state.outerResponse; current != null && !(current instanceof HttpServletResponseImpl); current = ((ServletResponseWrapper)current).getResponse()) {
            if (current == state.wrapResponse) {
               ServletResponse next = ((ServletResponseWrapper)current).getResponse();
               if (previous == null) {
                  state.outerResponse = next;
               } else {
                  ((ServletResponseWrapper)previous).setResponse(next);
               }
               break;
            }

            previous = current;
         }

      }
   }

   private ServletRequest wrapRequest(State state) {
      ServletRequest previous = null;

      ServletRequest current;
      for(current = state.outerRequest; current != null; current = ((ServletRequestWrapper)current).getRequest()) {
         if (state.hrequest == null && current instanceof HttpServletRequest) {
            state.hrequest = (HttpServletRequest)current;
         }

         if (!(current instanceof ServletRequestWrapper) || current instanceof DispatchedHttpServletRequest) {
            break;
         }

         previous = current;
      }

      if (current == null) {
         throw new IllegalStateException("Can't retrieve container request from " + state.outerRequest);
      } else {
         HttpServletRequest hcurrent = (HttpServletRequest)current;
         boolean crossContext = false;
         if (state.outerRequest instanceof DispatchedHttpServletRequest || state.outerRequest instanceof HttpServletRequest) {
            HttpServletRequest houterRequest = (HttpServletRequest)state.outerRequest;
            Object contextPath = houterRequest.getAttribute("javax.servlet.include.context_path");
            if (contextPath == null) {
               contextPath = houterRequest.getContextPath();
            }

            crossContext = !this.wrapper.getContextPath().equals(contextPath);
         }

         this.crossContextFlag = crossContext;
         ServletRequest wrapperLocal = new DispatchedHttpServletRequest(hcurrent, this.wrapper.getServletCtx(), crossContext, state.dispatcherType);
         if (previous == null) {
            state.outerRequest = wrapperLocal;
         } else {
            ((ServletRequestWrapper)previous).setRequest(wrapperLocal);
         }

         state.wrapRequest = wrapperLocal;
         return wrapperLocal;
      }
   }

   private ServletResponse wrapResponse(State state) {
      ServletResponse previous = null;

      ServletResponse current;
      for(current = state.outerResponse; current != null; current = ((ServletResponseWrapper)current).getResponse()) {
         if (state.hresponse == null && current instanceof HttpServletResponse) {
            state.hresponse = (HttpServletResponse)current;
            if (DispatcherType.INCLUDE != state.dispatcherType) {
               return null;
            }
         }

         if (!(current instanceof ServletResponseWrapper) || current instanceof DispatchedHttpServletResponse) {
            break;
         }

         previous = current;
      }

      HttpServletResponse hcurrent = (HttpServletResponse)current;
      DispatchedHttpServletResponse wrapperLocal = new DispatchedHttpServletResponse(hcurrent, DispatcherType.INCLUDE.equals(state.dispatcherType));
      if (previous == null) {
         state.outerResponse = wrapperLocal;
      } else {
         ((ServletResponseWrapper)previous).setResponse(wrapperLocal);
      }

      state.wrapResponse = wrapperLocal;
      return wrapperLocal;
   }

   private static void closeResponse(ServletResponse response) {
      try {
         PrintWriter writer = response.getWriter();
         writer.flush();
         writer.close();
      } catch (IllegalStateException var5) {
         try {
            ServletOutputStream stream = response.getOutputStream();
            stream.flush();
            stream.close();
         } catch (IllegalStateException var3) {
         } catch (IOException var4) {
         }
      } catch (IOException var6) {
      }

   }

   private void recycleRequestWrapper(State state) {
      if (state.wrapRequest instanceof DispatchedHttpServletRequest) {
         ((DispatchedHttpServletRequest)state.wrapRequest).recycle();
      }

   }

   private static class State {
      ServletRequest outerRequest = null;
      ServletResponse outerResponse = null;
      ServletRequest wrapRequest = null;
      ServletResponse wrapResponse = null;
      final DispatcherType dispatcherType;
      HttpServletRequest hrequest = null;
      HttpServletResponse hresponse = null;

      State(ServletRequest request, ServletResponse response, DispatcherType dispatcherType) {
         this.outerRequest = request;
         this.outerResponse = response;
         this.dispatcherType = dispatcherType;
      }
   }

   private class PrivilegedInclude implements PrivilegedExceptionAction {
      private final ServletRequest request;
      private final ServletResponse response;

      PrivilegedInclude(ServletRequest request, ServletResponse response) {
         this.request = request;
         this.response = response;
      }

      public Void run() throws ServletException, IOException {
         ApplicationDispatcher.this.doInclude(this.request, this.response);
         return null;
      }
   }

   private class PrivilegedDispatch implements PrivilegedExceptionAction {
      private final ServletRequest request;
      private final ServletResponse response;
      private final DispatcherType dispatcherType;

      PrivilegedDispatch(ServletRequest request, ServletResponse response, DispatcherType dispatcherType) {
         this.request = request;
         this.response = response;
         this.dispatcherType = dispatcherType;
      }

      public Void run() throws Exception {
         ApplicationDispatcher.this.doDispatch(this.request, this.response, this.dispatcherType);
         return null;
      }
   }
}
