package org.glassfish.grizzly.servlet;

import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.glassfish.grizzly.http.server.util.Enumerator;
import org.glassfish.grizzly.http.server.util.ParameterMap;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Parameters;
import org.glassfish.grizzly.utils.Charsets;

public class DispatchedHttpServletRequest extends HttpServletRequestWrapper {
   private static final HashSet specials = new HashSet(15);
   protected WebappContext context = null;
   protected String contextPath = null;
   protected boolean crossContext = false;
   protected final DispatcherType dispatcherType;
   private final ParameterMap parameterMap = new ParameterMap();
   private final Parameters mergedParameters = new Parameters();
   private boolean parsedParams = false;
   protected String pathInfo = null;
   private String queryParamString = null;
   protected String queryString = null;
   protected Object requestDispatcherPath = null;
   protected String requestURI = null;
   protected String servletPath = null;
   private HashMap specialAttributes = null;

   public DispatchedHttpServletRequest(HttpServletRequest request, WebappContext context, boolean crossContext, DispatcherType dispatcherType) {
      super(request);
      this.context = context;
      this.crossContext = crossContext;
      this.dispatcherType = dispatcherType;
      this.setRequest(request);
   }

   public Object getAttribute(String name) {
      if (name.equals("org.apache.catalina.core.DISPATCHER_REQUEST_PATH")) {
         return this.requestDispatcherPath != null ? this.requestDispatcherPath.toString() : null;
      } else if (!this.isSpecial(name)) {
         return this.getRequest().getAttribute(name);
      } else {
         Object value = null;
         if (this.specialAttributes != null) {
            value = this.specialAttributes.get(name);
         }

         if (value == null && name.startsWith("javax.servlet.forward")) {
            value = this.getRequest().getAttribute(name);
         }

         return value;
      }
   }

   public Enumeration getAttributeNames() {
      return new AttributeNamesEnumerator();
   }

   public void removeAttribute(String name) {
      if (this.isSpecial(name)) {
         if (this.specialAttributes != null) {
            this.specialAttributes.remove(name);
         }
      } else {
         this.getRequest().removeAttribute(name);
      }

   }

   public void setAttribute(String name, Object value) {
      if (name.equals("org.apache.catalina.core.DISPATCHER_REQUEST_PATH")) {
         this.requestDispatcherPath = value;
      } else {
         if (this.isSpecial(name)) {
            if (this.specialAttributes != null) {
               this.specialAttributes.put(name, value);
            }
         } else {
            this.getRequest().setAttribute(name, value);
         }

      }
   }

   public RequestDispatcher getRequestDispatcher(String path) {
      if (this.context == null) {
         return null;
      } else if (path == null) {
         return null;
      } else if (path.startsWith("/")) {
         return this.context.getRequestDispatcher(path);
      } else {
         String servletPath = (String)this.getAttribute("javax.servlet.include.servlet_path");
         if (servletPath == null) {
            servletPath = this.getServletPath();
         }

         String pathInfo = this.getPathInfo();
         String requestPath = null;
         if (pathInfo == null) {
            requestPath = servletPath;
         } else {
            requestPath = servletPath + pathInfo;
         }

         int pos = requestPath.lastIndexOf(47);
         String relative = null;
         if (pos >= 0) {
            relative = requestPath.substring(0, pos + 1) + path;
         } else {
            relative = requestPath + path;
         }

         return this.context.getRequestDispatcher(relative);
      }
   }

   public DispatcherType getDispatcherType() {
      return this.dispatcherType;
   }

   public String getContextPath() {
      return this.contextPath;
   }

   public String getParameter(String name) {
      if (!this.parsedParams) {
         this.parseParameters();
      }

      return System.getSecurityManager() != null ? (String)AccessController.doPrivileged(new GetParameterPrivilegedAction(name)) : this.mergedParameters.getParameter(name);
   }

   public Map getParameterMap() {
      if (!this.parsedParams) {
         this.parseParameters();
      }

      return (Map)(System.getSecurityManager() != null ? (Map)AccessController.doPrivileged(new GetParameterMapPrivilegedAction()) : this.getParameterMapInternal());
   }

   public Enumeration getParameterNames() {
      if (!this.parsedParams) {
         this.parseParameters();
      }

      return System.getSecurityManager() != null ? new Enumerator((Collection)AccessController.doPrivileged(new GetParameterNamesPrivilegedAction())) : new Enumerator(this.mergedParameters.getParameterNames());
   }

   public String[] getParameterValues(String name) {
      if (!this.parsedParams) {
         this.parseParameters();
      }

      String[] ret;
      if (System.getSecurityManager() != null) {
         ret = (String[])AccessController.doPrivileged(new GetParameterValuePrivilegedAction(name));
         if (ret != null) {
            ret = (String[])ret.clone();
         }
      } else {
         ret = this.mergedParameters.getParameterValues(name);
      }

      return ret;
   }

   private ParameterMap getParameterMapInternal() {
      if (this.parameterMap.isLocked()) {
         return this.parameterMap;
      } else {
         Iterator var1 = this.mergedParameters.getParameterNames().iterator();

         while(var1.hasNext()) {
            String name = (String)var1.next();
            String[] values = this.mergedParameters.getParameterValues(name);
            this.parameterMap.put(name, values);
         }

         this.parameterMap.setLocked(true);
         return this.parameterMap;
      }
   }

   private void parseParameters() {
      if (!this.parsedParams) {
         String enc = this.getCharacterEncoding();
         Charset charset;
         if (enc != null) {
            try {
               charset = Charsets.lookupCharset(enc);
            } catch (Exception var7) {
               charset = Constants.DEFAULT_HTTP_CHARSET;
            }
         } else {
            charset = Constants.DEFAULT_HTTP_CHARSET;
         }

         this.mergedParameters.setEncoding(charset);
         this.mergedParameters.setQueryStringEncoding(charset);
         DataChunk queryDC = DataChunk.newInstance();
         queryDC.setString(this.queryParamString);
         this.mergedParameters.setQuery(queryDC);
         this.mergedParameters.handleQueryParameters();
         Map paramMap = this.getRequest().getParameterMap();
         Iterator var5 = paramMap.entrySet().iterator();

         while(var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            this.mergedParameters.addParameterValues((String)entry.getKey(), (String[])entry.getValue());
         }

         this.parsedParams = true;
      }
   }

   public String getPathInfo() {
      return this.pathInfo;
   }

   public String getQueryString() {
      return this.queryString;
   }

   public String getRequestURI() {
      return this.requestURI;
   }

   public StringBuffer getRequestURL() {
      StringBuffer url = new StringBuffer();
      String scheme = this.getScheme();
      int port = this.getServerPort();
      if (port < 0) {
         port = 80;
      }

      url.append(scheme);
      url.append("://");
      url.append(this.getServerName());
      if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
         url.append(':');
         url.append(port);
      }

      url.append(this.getRequestURI());
      return url;
   }

   public String getServletPath() {
      return this.servletPath;
   }

   void copyMap(Map orig, Map dest) {
      if (orig != null) {
         synchronized(orig) {
            Iterator var4 = orig.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               dest.put(entry.getKey(), entry.getValue());
            }

         }
      }
   }

   void setContextPath(String contextPath) {
      this.contextPath = contextPath;
   }

   void setPathInfo(String pathInfo) {
      this.pathInfo = pathInfo;
   }

   void setQueryString(String queryString) {
      this.queryString = queryString;
   }

   void setRequest(HttpServletRequest request) {
      super.setRequest(request);
      this.requestDispatcherPath = request.getAttribute("org.apache.catalina.core.DISPATCHER_REQUEST_PATH");
      this.contextPath = request.getContextPath();
      this.pathInfo = request.getPathInfo();
      this.queryString = request.getQueryString();
      this.requestURI = request.getRequestURI();
      this.servletPath = request.getServletPath();
   }

   void setRequestURI(String requestURI) {
      this.requestURI = requestURI;
   }

   void setServletPath(String servletPath) {
      this.servletPath = servletPath;
   }

   void setQueryParams(String queryString) {
      this.queryParamString = queryString;
   }

   protected boolean isSpecial(String name) {
      return specials.contains(name);
   }

   void initSpecialAttributes(String requestUri, String contextPath, String servletPath, String pathInfo, String queryString) {
      this.specialAttributes = new HashMap(5);
      switch (this.dispatcherType) {
         case INCLUDE:
            this.specialAttributes.put("javax.servlet.include.request_uri", requestUri);
            this.specialAttributes.put("javax.servlet.include.context_path", contextPath);
            this.specialAttributes.put("javax.servlet.include.servlet_path", servletPath);
            this.specialAttributes.put("javax.servlet.include.path_info", pathInfo);
            this.specialAttributes.put("javax.servlet.include.query_string", queryString);
            break;
         case FORWARD:
         case ERROR:
            this.specialAttributes.put("javax.servlet.forward.request_uri", requestUri);
            this.specialAttributes.put("javax.servlet.forward.context_path", contextPath);
            this.specialAttributes.put("javax.servlet.forward.servlet_path", servletPath);
            this.specialAttributes.put("javax.servlet.forward.path_info", pathInfo);
            this.specialAttributes.put("javax.servlet.forward.query_string", queryString);
            break;
         case ASYNC:
            this.specialAttributes.put("javax.servlet.async.request_uri", requestUri);
            this.specialAttributes.put("javax.servlet.async.context_path", contextPath);
            this.specialAttributes.put("javax.servlet.async.servlet_path", servletPath);
            this.specialAttributes.put("javax.servlet.async.path_info", pathInfo);
            this.specialAttributes.put("javax.servlet.async.query_string", queryString);
      }

   }

   protected String[] mergeValues(Object values1, Object values2) {
      ArrayList results = new ArrayList();
      String[] values;
      if (values1 != null) {
         if (values1 instanceof String) {
            results.add((String)values1);
         } else if (values1 instanceof String[]) {
            values = (String[])((String[])values1);
            Collections.addAll(results, values);
         } else {
            results.add(values1.toString());
         }
      }

      if (values2 != null) {
         if (values2 instanceof String) {
            results.add((String)values2);
         } else if (values2 instanceof String[]) {
            values = (String[])((String[])values2);
            Collections.addAll(results, values);
         } else {
            results.add(values2.toString());
         }
      }

      values = new String[results.size()];
      return (String[])results.toArray(values);
   }

   public void recycle() {
   }

   public HttpServletRequestImpl getRequestFacade() {
      return this.getRequest() instanceof HttpServletRequestImpl ? (HttpServletRequestImpl)this.getRequest() : ((DispatchedHttpServletRequest)this.getRequest()).getRequestFacade();
   }

   static {
      specials.add("javax.servlet.include.request_uri");
      specials.add("javax.servlet.include.context_path");
      specials.add("javax.servlet.include.servlet_path");
      specials.add("javax.servlet.include.path_info");
      specials.add("javax.servlet.include.query_string");
      specials.add("javax.servlet.forward.request_uri");
      specials.add("javax.servlet.forward.context_path");
      specials.add("javax.servlet.forward.servlet_path");
      specials.add("javax.servlet.forward.path_info");
      specials.add("javax.servlet.forward.query_string");
      specials.add("javax.servlet.async.request_uri");
      specials.add("javax.servlet.async.context_path");
      specials.add("javax.servlet.async.servlet_path");
      specials.add("javax.servlet.async.path_info");
      specials.add("javax.servlet.async.query_string");
   }

   private final class GetParameterMapPrivilegedAction implements PrivilegedAction {
      private GetParameterMapPrivilegedAction() {
      }

      public ParameterMap run() {
         return DispatchedHttpServletRequest.this.getParameterMapInternal();
      }

      // $FF: synthetic method
      GetParameterMapPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetParameterValuePrivilegedAction implements PrivilegedAction {
      public final String name;

      public GetParameterValuePrivilegedAction(String name) {
         this.name = name;
      }

      public String[] run() {
         return DispatchedHttpServletRequest.this.mergedParameters.getParameterValues(this.name);
      }
   }

   private final class GetParameterNamesPrivilegedAction implements PrivilegedAction {
      private GetParameterNamesPrivilegedAction() {
      }

      public Set run() {
         return DispatchedHttpServletRequest.this.mergedParameters.getParameterNames();
      }

      // $FF: synthetic method
      GetParameterNamesPrivilegedAction(Object x1) {
         this();
      }
   }

   private final class GetParameterPrivilegedAction implements PrivilegedAction {
      public final String name;

      public GetParameterPrivilegedAction(String name) {
         this.name = name;
      }

      public String run() {
         return DispatchedHttpServletRequest.this.mergedParameters.getParameter(this.name);
      }
   }

   private final class AttributeNamesEnumerator implements Enumeration {
      Enumeration parentEnumeration = null;
      String next = null;
      private Iterator specialNames = null;

      public AttributeNamesEnumerator() {
         this.parentEnumeration = DispatchedHttpServletRequest.this.getRequest().getAttributeNames();
         if (DispatchedHttpServletRequest.this.specialAttributes != null) {
            this.specialNames = DispatchedHttpServletRequest.this.specialAttributes.keySet().iterator();
         }

      }

      public boolean hasMoreElements() {
         return this.specialNames != null && this.specialNames.hasNext() || this.next != null || (this.next = this.findNext()) != null;
      }

      public String nextElement() {
         if (this.specialNames != null && this.specialNames.hasNext()) {
            return (String)this.specialNames.next();
         } else {
            String result = this.next;
            if (this.next != null) {
               this.next = this.findNext();
               return result;
            } else {
               throw new NoSuchElementException();
            }
         }
      }

      protected String findNext() {
         String result = null;

         while(result == null && this.parentEnumeration.hasMoreElements()) {
            String current = (String)this.parentEnumeration.nextElement();
            if (!DispatchedHttpServletRequest.this.isSpecial(current) || !DispatchedHttpServletRequest.this.dispatcherType.equals(DispatcherType.FORWARD) && current.startsWith("javax.servlet.forward") && DispatchedHttpServletRequest.this.getAttribute(current) != null) {
               result = current;
            }
         }

         return result;
      }
   }
}
