package com.sun.faces.context;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.TypedCollections;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExternalContextImpl extends ExternalContext {
   private ServletContext servletContext = null;
   private ServletRequest request = null;
   private ServletResponse response = null;
   private Map applicationMap = null;
   private Map sessionMap = null;
   private Map requestMap = null;
   private Map requestParameterMap = null;
   private Map requestParameterValuesMap = null;
   private Map requestHeaderMap = null;
   private Map requestHeaderValuesMap = null;
   private Map cookieMap = null;
   private Map initParameterMap = null;
   static final Class theUnmodifiableMapClass = Collections.unmodifiableMap(new HashMap()).getClass();

   public ExternalContextImpl(ServletContext sc, ServletRequest request, ServletResponse response) {
      try {
         Util.parameterNonNull(sc);
         Util.parameterNonNull(request);
         Util.parameterNonNull(response);
      } catch (Exception var5) {
         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.FACES_CONTEXT_CONSTRUCTION_ERROR"));
      }

      this.servletContext = sc;
      this.request = request;
      this.response = response;
      WebConfiguration config = WebConfiguration.getInstance(sc);
      if (config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SendPoweredByHeader)) {
         ((HttpServletResponse)response).addHeader("X-Powered-By", "JSF/1.2");
      }

   }

   public Object getSession(boolean create) {
      return ((HttpServletRequest)this.request).getSession(create);
   }

   public Object getContext() {
      return this.servletContext;
   }

   public Object getRequest() {
      return this.request;
   }

   public void setRequest(Object request) {
      if (request instanceof ServletRequest) {
         this.request = (ServletRequest)request;
         this.requestHeaderMap = null;
         this.requestHeaderValuesMap = null;
         this.requestHeaderValuesMap = null;
         this.requestMap = null;
         this.requestParameterMap = null;
         this.requestParameterValuesMap = null;
      }

   }

   public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException {
      this.request.setCharacterEncoding(encoding);
   }

   public Object getResponse() {
      return this.response;
   }

   public void setResponse(Object response) {
      if (response instanceof ServletResponse) {
         this.response = (ServletResponse)response;
      }

   }

   public void setResponseCharacterEncoding(String encoding) {
      this.response.setCharacterEncoding(encoding);
   }

   public Map getApplicationMap() {
      if (this.applicationMap == null) {
         this.applicationMap = new ApplicationMap(this.servletContext);
      }

      return this.applicationMap;
   }

   public Map getSessionMap() {
      if (this.sessionMap == null) {
         this.sessionMap = new SessionMap((HttpServletRequest)this.request);
      }

      return this.sessionMap;
   }

   public Map getRequestMap() {
      if (this.requestMap == null) {
         this.requestMap = new RequestMap(this.request);
      }

      return this.requestMap;
   }

   public Map getRequestHeaderMap() {
      if (null == this.requestHeaderMap) {
         this.requestHeaderMap = Collections.unmodifiableMap(new RequestHeaderMap((HttpServletRequest)this.request));
      }

      return this.requestHeaderMap;
   }

   public Map getRequestHeaderValuesMap() {
      if (null == this.requestHeaderValuesMap) {
         this.requestHeaderValuesMap = Collections.unmodifiableMap(new RequestHeaderValuesMap((HttpServletRequest)this.request));
      }

      return this.requestHeaderValuesMap;
   }

   public Map getRequestCookieMap() {
      if (null == this.cookieMap) {
         this.cookieMap = Collections.unmodifiableMap(new RequestCookieMap((HttpServletRequest)this.request));
      }

      return this.cookieMap;
   }

   public Map getInitParameterMap() {
      if (null == this.initParameterMap) {
         this.initParameterMap = Collections.unmodifiableMap(new InitParameterMap(this.servletContext));
      }

      return this.initParameterMap;
   }

   public Map getRequestParameterMap() {
      if (null == this.requestParameterMap) {
         this.requestParameterMap = Collections.unmodifiableMap(new RequestParameterMap(this.request));
      }

      return this.requestParameterMap;
   }

   public Map getRequestParameterValuesMap() {
      if (null == this.requestParameterValuesMap) {
         this.requestParameterValuesMap = Collections.unmodifiableMap(new RequestParameterValuesMap(this.request));
      }

      return this.requestParameterValuesMap;
   }

   public Iterator getRequestParameterNames() {
      final Enumeration namEnum = this.request.getParameterNames();
      return new Iterator() {
         public boolean hasNext() {
            return namEnum.hasMoreElements();
         }

         public String next() {
            return (String)namEnum.nextElement();
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public Locale getRequestLocale() {
      return this.request.getLocale();
   }

   public Iterator getRequestLocales() {
      return new LocalesIterator(this.request.getLocales());
   }

   public String getRequestPathInfo() {
      return ((HttpServletRequest)this.request).getPathInfo();
   }

   public Cookie[] getRequestCookies() {
      return ((HttpServletRequest)this.request).getCookies();
   }

   public String getRequestContextPath() {
      return ((HttpServletRequest)this.request).getContextPath();
   }

   public String getRequestServletPath() {
      return ((HttpServletRequest)this.request).getServletPath();
   }

   public String getRequestCharacterEncoding() {
      return this.request.getCharacterEncoding();
   }

   public String getRequestContentType() {
      return this.request.getContentType();
   }

   public String getResponseCharacterEncoding() {
      return this.response.getCharacterEncoding();
   }

   public String getResponseContentType() {
      return this.response.getContentType();
   }

   public String getInitParameter(String name) {
      return this.servletContext.getInitParameter(name);
   }

   public Set getResourcePaths(String path) {
      return TypedCollections.dynamicallyCastSet(this.servletContext.getResourcePaths(path), String.class);
   }

   public InputStream getResourceAsStream(String path) {
      return this.servletContext.getResourceAsStream(path);
   }

   public URL getResource(String path) {
      try {
         URL url = this.servletContext.getResource(path);
         return url;
      } catch (MalformedURLException var4) {
         return null;
      }
   }

   public String encodeActionURL(String sb) {
      return ((HttpServletResponse)this.response).encodeURL(sb);
   }

   public String encodeResourceURL(String sb) {
      return ((HttpServletResponse)this.response).encodeURL(sb);
   }

   public String encodeNamespace(String aValue) {
      return aValue;
   }

   public String encodeURL(String url) {
      return ((HttpServletResponse)this.response).encodeURL(url);
   }

   public void dispatch(String requestURI) throws IOException, FacesException {
      RequestDispatcher requestDispatcher = this.request.getRequestDispatcher(requestURI);
      if (requestDispatcher == null) {
         ((HttpServletResponse)this.response).sendError(404);
      } else {
         try {
            requestDispatcher.forward(this.request, this.response);
         } catch (IOException var4) {
            throw var4;
         } catch (ServletException var5) {
            throw new FacesException(var5);
         }
      }
   }

   public void redirect(String requestURI) throws IOException {
      ((HttpServletResponse)this.response).sendRedirect(requestURI);
      FacesContext.getCurrentInstance().responseComplete();
   }

   public void log(String message) {
      this.servletContext.log(message);
   }

   public void log(String message, Throwable throwable) {
      this.servletContext.log(message, throwable);
   }

   public String getAuthType() {
      return ((HttpServletRequest)this.request).getAuthType();
   }

   public String getRemoteUser() {
      return ((HttpServletRequest)this.request).getRemoteUser();
   }

   public Principal getUserPrincipal() {
      return ((HttpServletRequest)this.request).getUserPrincipal();
   }

   public boolean isUserInRole(String role) {
      return ((HttpServletRequest)this.request).isUserInRole(role);
   }

   private static class LocalesIterator implements Iterator {
      private Enumeration locales;

      public LocalesIterator(Enumeration locales) {
         this.locales = locales;
      }

      public boolean hasNext() {
         return this.locales.hasMoreElements();
      }

      public Locale next() {
         return (Locale)this.locales.nextElement();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
