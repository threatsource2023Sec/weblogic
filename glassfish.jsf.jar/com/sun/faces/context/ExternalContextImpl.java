package com.sun.faces.context;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.context.flash.ELFlash;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.TypedCollections;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.context.FlashFactory;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.ClientWindow;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.PushBuilder;

public class ExternalContextImpl extends ExternalContext {
   private static final Logger LOGGER;
   private static final String PUSH_SUPPORTED_ATTRIBUTE_NAME = "com.sun.faces.ExternalContextImpl.PUSH_SUPPORTED";
   private ServletContext servletContext = null;
   private ServletRequest request = null;
   private ServletResponse response = null;
   private ClientWindow clientWindow = null;
   private Map applicationMap = null;
   private Map sessionMap = null;
   private Map requestMap = null;
   private Map requestParameterMap = null;
   private Map requestParameterValuesMap = null;
   private Map requestHeaderMap = null;
   private Map requestHeaderValuesMap = null;
   private Map cookieMap = null;
   private Map initParameterMap = null;
   private Map fallbackContentTypeMap = null;
   private Flash flash;
   private boolean distributable;
   static final Class theUnmodifiableMapClass;

   public ExternalContextImpl(ServletContext sc, ServletRequest request, ServletResponse response) {
      Util.notNull("sc", sc);
      Util.notNull("request", request);
      Util.notNull("response", response);
      this.servletContext = sc;
      this.request = request;
      this.response = response;
      boolean enabled = (Boolean)ContextParamUtils.getValue(this.servletContext, ContextParam.SendPoweredByHeader, Boolean.class);
      if (enabled) {
         ((HttpServletResponse)response).addHeader("X-Powered-By", "JSF/2.3");
      }

      this.distributable = (Boolean)ContextParamUtils.getValue(this.servletContext, ContextParam.EnableDistributable, Boolean.class);
      this.fallbackContentTypeMap = new HashMap(3, 1.0F);
      this.fallbackContentTypeMap.put("js", "text/javascript");
      this.fallbackContentTypeMap.put("css", "text/css");
      this.fallbackContentTypeMap.put("properties", "text/plain");
   }

   public Object getSession(boolean create) {
      return ((HttpServletRequest)this.request).getSession(create);
   }

   public String getSessionId(boolean create) {
      HttpSession session = null;
      String id = null;
      session = (HttpSession)this.getSession(create);
      if (session != null) {
         id = session.getId();
      }

      return id;
   }

   public Object getContext() {
      return this.servletContext;
   }

   public String getContextName() {
      return this.servletContext.getMajorVersion() < 3 && (this.servletContext.getMajorVersion() != 2 || this.servletContext.getMinorVersion() != 5) ? this.servletContext.getServletContextName() : this.servletContext.getServletContextName();
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

   public ClientWindow getClientWindow() {
      return this.clientWindow;
   }

   public void setClientWindow(ClientWindow window) {
      this.clientWindow = window;
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

   public String getApplicationContextPath() {
      return this.servletContext.getContextPath();
   }

   public Map getSessionMap() {
      if (this.sessionMap == null) {
         if (this.distributable) {
            this.sessionMap = new AlwaysPuttingSessionMap((HttpServletRequest)this.request, FacesContext.getCurrentInstance().getApplication().getProjectStage());
         } else {
            this.sessionMap = new SessionMap((HttpServletRequest)this.request, FacesContext.getCurrentInstance().getApplication().getProjectStage());
         }
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

   public String getRealPath(String path) {
      return this.servletContext.getRealPath(path);
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

   public int getRequestContentLength() {
      return this.request.getContentLength();
   }

   public String getResponseCharacterEncoding() {
      return this.response.getCharacterEncoding();
   }

   public String getResponseContentType() {
      return this.response.getContentType();
   }

   public String getInitParameter(String name) {
      if (name == null) {
         throw new NullPointerException("Init parameter name cannot be null");
      } else {
         return this.servletContext.getInitParameter(name);
      }
   }

   public Set getResourcePaths(String path) {
      if (null == path) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "path");
         throw new NullPointerException(message);
      } else {
         return TypedCollections.dynamicallyCastSet(this.servletContext.getResourcePaths(path), String.class);
      }
   }

   public InputStream getResourceAsStream(String path) {
      if (null == path) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "path");
         throw new NullPointerException(message);
      } else {
         return this.servletContext.getResourceAsStream(path);
      }
   }

   public URL getResource(String path) {
      if (null == path) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "path");
         throw new NullPointerException(message);
      } else {
         try {
            URL url = this.servletContext.getResource(path);
            return url;
         } catch (MalformedURLException var4) {
            return null;
         }
      }
   }

   public String encodeActionURL(String url) {
      Util.notNull("url", url);
      FacesContext context = FacesContext.getCurrentInstance();
      ClientWindow cw = context.getExternalContext().getClientWindow();
      boolean appendClientWindow = false;
      if (null != cw) {
         appendClientWindow = cw.isClientWindowRenderModeEnabled(context);
      }

      if (appendClientWindow && -1 == url.indexOf("jfwid") && null != cw) {
         String clientWindowId = cw.getId();
         StringBuilder builder = new StringBuilder(url);
         int q = url.indexOf("?");
         if (-1 == q) {
            builder.append("?");
         } else {
            builder.append("&");
         }

         builder.append("jfwid").append("=").append(clientWindowId);
         Map additionalParams = cw.getQueryURLParameters(context);
         if (null != additionalParams) {
            Iterator var9 = additionalParams.entrySet().iterator();

            while(var9.hasNext()) {
               Map.Entry cur = (Map.Entry)var9.next();
               builder.append("=");
               builder.append((String)cur.getKey()).append("=").append((String)cur.getValue());
            }
         }

         url = builder.toString();
      }

      return ((HttpServletResponse)this.response).encodeURL(url);
   }

   public String encodeResourceURL(String url) {
      String result;
      if (null == url) {
         result = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "url");
         throw new NullPointerException(result);
      } else {
         result = ((HttpServletResponse)this.response).encodeURL(url);
         this.pushIfPossibleAndNecessary(result);
         return result;
      }
   }

   public String encodeWebsocketURL(String url) {
      if (url == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "url"));
      } else {
         HttpServletRequest request = (HttpServletRequest)this.getRequest();
         int port = (Integer)ContextParamUtils.getValue(this.servletContext, ContextParam.WebsocketEndpointPort, Integer.class);

         try {
            URL requestURL = new URL(request.getRequestURL().toString());
            if (port <= 0) {
               port = requestURL.getPort();
            }

            String websocketURL = (new URL(requestURL.getProtocol(), requestURL.getHost(), port, url)).toExternalForm();
            return this.encodeResourceURL(websocketURL.replaceFirst("http", "ws"));
         } catch (MalformedURLException var6) {
            return url;
         }
      }
   }

   public String encodeNamespace(String name) {
      return name;
   }

   public void dispatch(String requestURI) throws IOException, FacesException {
      RequestDispatcher requestDispatcher = this.request.getRequestDispatcher(requestURI);
      if (requestDispatcher == null) {
         ((HttpServletResponse)this.response).sendError(404);
      } else {
         try {
            requestDispatcher.forward(this.request, this.response);
         } catch (ServletException var4) {
            throw new FacesException(var4);
         }
      }
   }

   public void redirect(String requestURI) throws IOException {
      FacesContext ctx = FacesContext.getCurrentInstance();
      this.doLastPhaseActions(ctx, true);
      if (ctx.getPartialViewContext().isPartialRequest()) {
         if (this.getSession(true) instanceof HttpSession && ctx.getResponseComplete()) {
            throw new IllegalStateException();
         }

         ResponseWriter writer = ctx.getResponseWriter();
         PartialResponseWriter pwriter;
         if (writer instanceof PartialResponseWriter) {
            pwriter = (PartialResponseWriter)writer;
         } else {
            pwriter = ctx.getPartialViewContext().getPartialResponseWriter();
         }

         this.setResponseContentType("text/xml");
         this.setResponseCharacterEncoding("UTF-8");
         this.addResponseHeader("Cache-Control", "no-cache");
         pwriter.startDocument();
         pwriter.redirect(requestURI);
         pwriter.endDocument();
      } else {
         ((HttpServletResponse)this.response).sendRedirect(requestURI);
      }

      ctx.responseComplete();
   }

   public void log(String message) {
      if (null == message) {
         String msg = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "message");
         throw new NullPointerException(msg);
      } else {
         this.servletContext.log(message);
      }
   }

   public void log(String message, Throwable throwable) {
      String msg;
      if (null == message) {
         msg = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "message");
         throw new NullPointerException(msg);
      } else if (null == throwable) {
         msg = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "throwable");
         throw new NullPointerException(msg);
      } else {
         this.servletContext.log(message, throwable);
      }
   }

   public String getAuthType() {
      return ((HttpServletRequest)this.request).getAuthType();
   }

   public String getMimeType(String file) {
      String mimeType = this.servletContext.getMimeType(file);
      if (mimeType == null) {
         mimeType = this.getFallbackMimeType(file);
      }

      FacesContext ctx = FacesContext.getCurrentInstance();
      if (mimeType == null && LOGGER.isLoggable(Level.WARNING) && ctx.isProjectStage(ProjectStage.Development)) {
         LOGGER.log(Level.WARNING, "jsf.externalcontext.no.mime.type.found", new Object[]{file});
      }

      return mimeType;
   }

   public String getRemoteUser() {
      return ((HttpServletRequest)this.request).getRemoteUser();
   }

   public Principal getUserPrincipal() {
      return ((HttpServletRequest)this.request).getUserPrincipal();
   }

   public boolean isUserInRole(String role) {
      if (null == role) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "role");
         throw new NullPointerException(message);
      } else {
         return ((HttpServletRequest)this.request).isUserInRole(role);
      }
   }

   public void invalidateSession() {
      HttpSession session = ((HttpServletRequest)this.request).getSession(false);
      if (session != null) {
         session.invalidate();
      }

   }

   public void addResponseCookie(String name, String value, Map properties) {
      HttpServletResponse res = (HttpServletResponse)this.response;
      Cookie cookie = new Cookie(name, value);
      if (properties != null && properties.size() != 0) {
         Iterator var6 = properties.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            String key = (String)entry.getKey();
            ALLOWABLE_COOKIE_PROPERTIES p = ExternalContextImpl.ALLOWABLE_COOKIE_PROPERTIES.valueOf(key);
            Object v = entry.getValue();
            switch (p) {
               case domain:
                  cookie.setDomain((String)v);
                  break;
               case maxAge:
                  cookie.setMaxAge((Integer)v);
                  break;
               case path:
                  cookie.setPath((String)v);
                  break;
               case secure:
                  cookie.setSecure((Boolean)v);
                  break;
               case httpOnly:
                  cookie.setHttpOnly((Boolean)v);
                  break;
               default:
                  throw new IllegalStateException();
            }
         }
      }

      res.addCookie(cookie);
   }

   public OutputStream getResponseOutputStream() throws IOException {
      return this.response.getOutputStream();
   }

   public Writer getResponseOutputWriter() throws IOException {
      return this.response.getWriter();
   }

   public String getRequestScheme() {
      return this.request.getScheme();
   }

   public String getRequestServerName() {
      return this.request.getServerName();
   }

   public int getRequestServerPort() {
      return this.request.getServerPort();
   }

   public void setResponseContentType(String contentType) {
      this.response.setContentType(contentType);
   }

   public void setResponseHeader(String name, String value) {
      ((HttpServletResponse)this.response).setHeader(name, value);
   }

   public void addResponseHeader(String name, String value) {
      ((HttpServletResponse)this.response).addHeader(name, value);
   }

   public void setResponseBufferSize(int size) {
      this.response.setBufferSize(size);
   }

   public boolean isResponseCommitted() {
      return this.response.isCommitted();
   }

   public void responseReset() {
      this.response.reset();
   }

   public void responseSendError(int statusCode, String message) throws IOException {
      if (message == null) {
         ((HttpServletResponse)this.response).sendError(statusCode);
      } else {
         ((HttpServletResponse)this.response).sendError(statusCode, message);
      }

   }

   public void setResponseStatus(int statusCode) {
      ((HttpServletResponse)this.response).setStatus(statusCode);
   }

   public void responseFlushBuffer() throws IOException {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext != null) {
         this.doLastPhaseActions(facesContext, false);
      }

      this.response.flushBuffer();
   }

   public void setResponseContentLength(int length) {
      this.response.setContentLength(length);
   }

   public void setSessionMaxInactiveInterval(int interval) {
      HttpSession session = ((HttpServletRequest)this.request).getSession();
      session.setMaxInactiveInterval(interval);
   }

   public int getResponseBufferSize() {
      return this.response.getBufferSize();
   }

   public int getSessionMaxInactiveInterval() {
      HttpSession session = ((HttpServletRequest)this.request).getSession();
      return session.getMaxInactiveInterval();
   }

   public boolean isSecure() {
      return ((HttpServletRequest)this.request).isSecure();
   }

   public String encodeBookmarkableURL(String baseUrl, Map parameters) {
      FacesContext context = FacesContext.getCurrentInstance();
      String encodingFromContext = (String)context.getAttributes().get("facelets.Encoding");
      if (null == encodingFromContext) {
         encodingFromContext = (String)context.getViewRoot().getAttributes().get("facelets.Encoding");
      }

      String currentResponseEncoding = null != encodingFromContext ? encodingFromContext : this.getResponseCharacterEncoding();
      UrlBuilder builder = new UrlBuilder(baseUrl, currentResponseEncoding);
      builder.addParameters(parameters);
      return builder.createUrl();
   }

   public String encodeRedirectURL(String baseUrl, Map parameters) {
      FacesContext context = FacesContext.getCurrentInstance();
      String encodingFromContext = (String)context.getAttributes().get("facelets.Encoding");
      if (null == encodingFromContext) {
         encodingFromContext = (String)context.getViewRoot().getAttributes().get("facelets.Encoding");
      }

      String currentResponseEncoding = null != encodingFromContext ? encodingFromContext : this.getResponseCharacterEncoding();
      UrlBuilder builder = new UrlBuilder(baseUrl, currentResponseEncoding);
      builder.addParameters(parameters);
      return builder.createUrl();
   }

   public String encodePartialActionURL(String url) {
      if (null == url) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "url");
         throw new NullPointerException(message);
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         String encodingFromContext = (String)context.getAttributes().get("facelets.Encoding");
         if (null == encodingFromContext) {
            encodingFromContext = (String)context.getViewRoot().getAttributes().get("facelets.Encoding");
         }

         String currentResponseEncoding = null != encodingFromContext ? encodingFromContext : this.getResponseCharacterEncoding();
         UrlBuilder builder = new UrlBuilder(url, currentResponseEncoding);
         return ((HttpServletResponse)this.response).encodeURL(builder.createUrl());
      }
   }

   public Flash getFlash() {
      if (null == this.flash) {
         FlashFactory ff = (FlashFactory)FactoryFinder.getFactory("javax.faces.context.FlashFactory");
         this.flash = ff.getFlash(true);
      }

      return this.flash;
   }

   private void pushIfPossibleAndNecessary(String result) {
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext extContext = context.getExternalContext();
      Map attrs = context.getAttributes();
      Object val;
      if (null == (val = attrs.get("com.sun.faces.ExternalContextImpl.PUSH_SUPPORTED")) || (Boolean)val) {
         ApplicationAssociate associate = ApplicationAssociate.getInstance(extContext);
         if (!associate.isPushBuilderSupported()) {
            attrs.putIfAbsent("com.sun.faces.ExternalContextImpl.PUSH_SUPPORTED", Boolean.FALSE);
         } else {
            Set resourceUrls = (Set)FacesContext.getCurrentInstance().getAttributes().computeIfAbsent("com.sun.faces.resourceUrls", (k) -> {
               return new HashSet();
            });
            if (!resourceUrls.contains(result)) {
               resourceUrls.add(result);
               Object pbObj = this.getPushBuilder(context, extContext);
               if (pbObj != null) {
                  ((PushBuilder)pbObj).path(result).push();
               }

            }
         }
      }
   }

   private Object getPushBuilder(FacesContext context, ExternalContext extContext) {
      PushBuilder result = null;
      Object requestObj = extContext.getRequest();
      if (requestObj instanceof HttpServletRequest) {
         Map attrs = context.getAttributes();
         HttpServletRequest hreq = (HttpServletRequest)requestObj;
         boolean isPushSupported = false;
         Object val;
         if ((val = attrs.get("com.sun.faces.ExternalContextImpl.PUSH_SUPPORTED")) != null) {
            isPushSupported = (Boolean)val;
         } else {
            isPushSupported = Util.isEmpty((String)extContext.getRequestHeaderMap().get("If-Modified-Since"));
         }

         if (isPushSupported) {
            isPushSupported = (result = hreq.newPushBuilder()) != null;
         }

         attrs.putIfAbsent("com.sun.faces.ExternalContextImpl.PUSH_SUPPORTED", isPushSupported);
      }

      return result;
   }

   private void doLastPhaseActions(FacesContext context, boolean outgoingResponseIsRedirect) {
      Map attrs = context.getAttributes();

      try {
         attrs.put(ELFlash.ACT_AS_DO_LAST_PHASE_ACTIONS, outgoingResponseIsRedirect);
         this.getFlash().doPostPhaseActions(context);
      } finally {
         attrs.remove(ELFlash.ACT_AS_DO_LAST_PHASE_ACTIONS);
      }

   }

   public String getFallbackMimeType(String file) {
      if (file != null && file.length() != 0) {
         int idx = file.lastIndexOf(46);
         if (idx == -1) {
            return null;
         } else {
            String extension = file.substring(idx + 1);
            return extension.length() == 0 ? null : (String)this.fallbackContentTypeMap.get(extension);
         }
      } else {
         return null;
      }
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
      theUnmodifiableMapClass = Collections.unmodifiableMap(new HashMap()).getClass();
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

   private static enum ALLOWABLE_COOKIE_PROPERTIES {
      domain,
      maxAge,
      path,
      secure,
      httpOnly;
   }
}
